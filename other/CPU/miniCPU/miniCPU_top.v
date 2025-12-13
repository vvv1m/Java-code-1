module minicpu_top(
    input  wire        clk,
    input  wire        resetn,

    //指令SRAM接口
    output wire        inst_sram_we,        //写使能
    output wire [31:0] inst_sram_addr,      //地址
    output wire [31:0] inst_sram_wdata,     //写数据
    input  wire [31:0] inst_sram_rdata,     //读数据

    //数据SRAM接口
    output wire        data_sram_we,        //写使能
    output wire [31:0] data_sram_addr,      //地址
    output wire [31:0] data_sram_wdata,     //写数据
    input  wire [31:0] data_sram_rdata      //读数据
);

reg  [31:0] pc;     //计数器
wire [31:0] nextpc; //next-pc下一条指令的地址
//根据手册，复位后 PC 的复位值是 0x1C000000
   
wire [31:0] inst;   //当前指令
//下列对应指令格式
wire [ 5:0] op_31_26;   //主操作码
wire [ 3:0] op_25_22;   //功能码
wire [ 1:0] op_21_20;   //附加码
wire [ 4:0] op_19_15;   //附加码
wire [ 4:0] rd;         //目标寄存器
wire [ 4:0] rj;         //源寄存器1
wire [ 4:0] rk;         //源寄存器2

wire [11:0] i12;        //12位立即数
wire [15:0] i16;        //16位立即数

wire [63:0] op_31_26_d; //64位独热
wire [15:0] op_25_22_d; //16位独热
wire [ 3:0] op_21_20_d; //4位独热
wire [31:0] op_19_15_d; //32位独热
//操作对应的指令
wire        inst_add_w;     //带符号32位加法 将rj + rk的结果存到rd


//来自于操作手册
//其中指令码的
//31..15 位（opcode 域）必须是 0b00000000000100000（前导字符 “0b” 表示后续是二进制数）。一
//旦取回来的指令码的这个域满足这个值，那么这条指令就是 add.w 指令。此时，指令码的 9..5 位
//的数值表示 rj 寄存器号，14..10 位的数值表示 rk 寄存器号，4..0 位的数值表示 rd 寄存器号。



wire        inst_addi_w;    //寄存器+立即数
wire        inst_ld_w;      //从内存加载数字
wire        inst_st_w;      //存储字到内存
wire        inst_bne;       //不相等时跳转
//控制信号
wire        src2_is_imm;    //ALU第二操作数是立即数
wire        res_from_mem;   //结果来自内存
wire        gr_we;          //寄存器写使能
wire        mem_we;         //内存写使能
wire        src_reg_is_rd;  //源寄存器是rd

//数据通路信号
wire [31:0] rj_value;       //rj的值
wire [31:0] rkd_value;      //rk或rd的值
wire [ 4:0] rf_raddr1;      //寄存器读地址1
wire [ 4:0] rf_raddr2;      //寄存器读地址2
wire [31:0] rf_wdata;       //寄存器写数据
wire [ 4:0] rf_waddr;       // 添加写地址信号

//分支跳转信号
wire        br_taken;       //分支是否发生
wire        rj_eq_rd;       //rj == rd？
wire [31:0] br_offs;        //分支偏移量
wire [31:0] br_target;      //分支目标地址

//ALU相关信号
wire [31:0] imm;            //符号扩展后的立即数
wire [31:0] alu_src1;       //ALU第一操作数
wire [31:0] alu_src2;       //ALU第二操作数
wire [31:0] alu_result;     //ALU运算结果

//
always@(posedge clk) begin
    if(!resetn)begin
        pc <= 32'h1c000000;
    end
    else begin 
        pc <= nextpc;
    end
end

assign inst_sram_we    = 1'b0;
assign inst_sram_addr  = pc;
assign inst_sram_wdata = 32'b0;
assign inst            = inst_sram_rdata;

assign op_31_26 = inst[31:26];
assign op_25_22 = inst[25:22];
assign op_21_20 = inst[21:20];
assign op_19_15 = inst[19:15];
assign rd       = inst[ 4: 0];
assign rj       = inst[ 9: 5];
assign rk       = inst[14:10];
assign i12      = inst[21:10];//从指令中提取12位，用于算术运算和访存指令
assign i16      = inst[25:10];//从指令中提取16位，用于分支跳转指令

decoder_6_64 u_dec0(.in(op_31_26 ), .co(op_31_26_d ));
decoder_4_16 u_dec1(.in(op_25_22 ), .co(op_25_22_d ));
decoder_2_4  u_dec2(.in(op_21_20 ), .co(op_21_20_d ));
decoder_5_32 u_dec3(.in(op_19_15 ), .co(op_19_15_d ));

assign inst_add_w  = op_31_26_d[6'h00] & op_25_22_d[4'h0] & op_21_20_d[2'h1] & op_19_15_d[5'h00];
assign inst_addi_w = op_31_26_d[6'h00] & op_25_22_d[4'ha];
assign inst_ld_w   = op_31_26_d[6'h0a] & op_25_22_d[4'h2];
assign inst_st_w   = op_31_26_d[6'h0a] & op_25_22_d[4'h6];//在这里实现inst_st_w指令的译码
//查手册 stw 001010 0110

assign inst_bne    = op_31_26_d[6'h17];

assign src2_is_imm   = inst_addi_w | inst_ld_w | inst_st_w;//在这里实现立即数选择信号
//与下面的alu src2信号形成联动，如果是这三个操作的第二操作数是立即数
//分别是 立即数加法，读取内存，存到内存
assign res_from_mem  = inst_ld_w;
assign gr_we         = inst_add_w | inst_ld_w | inst_addi_w;
assign mem_we        = inst_st_w;
assign src_reg_is_rd = inst_bne | inst_st_w;

assign rf_raddr1 = rj;
assign rf_raddr2 = src_reg_is_rd ? rd :rk;
regfile u_regfile(
    .clk    (clk      ),
    .raddr1 (rf_raddr1         ),
    .rdata1 (rj_value),
    .raddr2 (rf_raddr2         ),
    .rdata2 (rkd_value),
    .we     (gr_we    ),
    .waddr  (rf_waddr         ), //原本代码中并无这项，新建
    .wdata  (rf_wdata )
    );//在空出的括号里完成引脚匹配

assign br_offs   = {{14{i16[15]}}, i16[15:0], 2'b00};//在这里完成br_offs信号的生成
//将16位立即数扩展到32位
//  从左到右依次解释
//  {14{i16[15]}} --- 取i16的符号位，即最高位 复制14次
//  第二部分就是i16
//  第三部分是左移两位，补零，因为一条指令是4字节，最终结果需要的是跳转字节数，所以要乘4
assign br_target = pc + br_offs;
assign rj_eq_rd  = (rj_value == rkd_value);
assign br_taken  = inst_bne  && !rj_eq_rd; //如果指令是不相等时跳转且确实不相等赋1
assign nextpc    = br_taken ? br_target : (pc + 4);//在这里实现nextpc信号的生成
//是否需要跳转，跳转则采用目标地址，不跳转则正常+4

assign imm      = {{20{i12[11]}},i12[11:0]}; // 12位立即数扩展到32位
//前段取i12最高位复制20次，加上i12凑32位 
assign alu_src1 = rj_value;
assign alu_src2 = src2_is_imm ? imm : rkd_value;//在这里实现alu_src2信号
//第二个操作数是不是立即数 是则取用imm，不是则用rkd_value
assign alu_result = alu_src1+alu_src2;

assign data_sram_we    = mem_we;
assign data_sram_addr  = alu_result;
assign data_sram_wdata = rkd_value;

assign rf_wdata = res_from_mem ? data_sram_rdata : alu_result;//在这里完成写回寄存器值的选择
//判断数据来自哪里，为1来自内存
endmodule
                         
