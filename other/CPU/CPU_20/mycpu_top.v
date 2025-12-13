module mycpu_top(
    input  wire        clk,
    input  wire        resetn, //低有效
    // inst sram interface
    output wire        inst_sram_we,
    output wire [31:0] inst_sram_addr,  //指令存储器读的地址
    output wire [31:0] inst_sram_wdata, //指令存储器写的数据
    input  wire [31:0] inst_sram_rdata, //指令存储器读出的数据 --- 当前指令
    // data sram interface
    output wire        data_sram_we, //写使能
    output wire [31:0] data_sram_addr, //指令存储器地址
    output wire [31:0] data_sram_wdata, 
    input  wire [31:0] data_sram_rdata, //数据存储器读出的数据
    // trace debug interface
    output wire [31:0] debug_wb_pc, //调试用pc值
    output wire [ 3:0] debug_wb_rf_we, //调试用寄存器写使能
    output wire [ 4:0] debug_wb_rf_wnum, //调试用寄存器写地址
    output wire [31:0] debug_wb_rf_wdata //调试用寄存器写数据
);
reg         reset;
always @(posedge clk) reset <= ~resetn;

reg         valid; //用于决定当前指令是否有效
always @(posedge clk) begin
    if (reset) begin
        valid <= 1'b0; //复位时无效
    end
    else begin
        valid <= 1'b1;
    end
end

wire [31:0] seq_pc; //顺序pc值 --- pc+4
wire [31:0] nextpc;
wire        br_taken;
wire [31:0] br_target;
wire [31:0] inst; //当前指令
reg  [31:0] pc;

wire [11:0] alu_op; //12位ALU操作码
wire        load_op; //加载操作表示
wire        src1_is_pc; //alu源操作数1是pc
wire        src2_is_imm; //同上，是立即数
wire        res_from_mem; //结果来自内存
wire        dst_is_r1; //目的寄存器是r1
wire        gr_we; //通用寄存器写使能
wire        mem_we;
wire        src_reg_is_rd; //源寄存器是rd
wire [4: 0] dest; //目的寄存器地址
wire [31:0] rj_value;
wire [31:0] rkd_value;
wire [31:0] imm; //扩展后的立即数
wire [31:0] br_offs; //分支偏移量
wire [31:0] jirl_offs; //JIRL指令的跳转偏移

wire [ 5:0] op_31_26;
wire [ 3:0] op_25_22;
wire [ 1:0] op_21_20;
wire [ 4:0] op_19_15;
wire [ 4:0] rd; //目的寄存器编号
wire [ 4:0] rj; //源寄存器1编号
wire [ 4:0] rk; //源寄存器2编号
wire [11:0] i12;
wire [19:0] i20;
wire [15:0] i16;
wire [25:0] i26;

wire [63:0] op_31_26_d;
wire [15:0] op_25_22_d;
wire [ 3:0] op_21_20_d;
wire [31:0] op_19_15_d;

wire        inst_add_w; //寄存器中两数相加 
wire        inst_sub_w; //寄存器中两数相减
wire        inst_slt; //slt rd，rj，rk 将rj与rk中数据视为有符号整数比较，前小于后将rd置1，反之置0
wire        inst_sltu; //格式同上，视为无符号数比较
wire        inst_nor; // 按位逻辑或非运算，其他同and
wire        inst_and; //and rd，rj，rk 将rj与rk中数据按位逻辑与运算后存入rd
wire        inst_or; //按位逻辑或运算，其他同and
wire        inst_xor; //按位逻辑异或运算，其他同and
wire        inst_slli_w; //slliw rd，rj，ui5 将rj中数据逻辑左移，结果写入rd，位移量为立即数i5
wire        inst_srli_w; //逻辑右移
wire        inst_srai_w; //算术右移
wire        inst_addi_w; //立即数加法 12位立即数
wire        inst_ld_w;
wire        inst_st_w;
wire        inst_jirl; //jirl rd，rj，i16 无条件跳转，将该指令的PC+4存入rd，跳转目标是i16逻辑左移两位并符号位扩展所得偏移值加上rj中的值
wire        inst_b; //b  i26 无条件跳转，目标地址是将i26逻辑左移2位再符号扩展，所得偏移值+PC
wire        inst_bl;//无条件跳转，同时将该指令的PC值+4的结果写到一号通用寄存器r1
wire        inst_beq; //beq rj，rd，i16 比较rjrd中值，相等则跳转
wire        inst_bne; //不相等则跳转
wire        inst_lu12i_w; //lu12i.w rd, si20 将20比特立即数最低位链接上12比特0后写入rd --- 即高位加载

wire        need_ui5;
wire        need_si12;
wire        need_si16;
wire        need_si20;
wire        need_si26;
wire        src2_is_4;

wire [ 4:0] rf_raddr1;
wire [31:0] rf_rdata1;
wire [ 4:0] rf_raddr2;
wire [31:0] rf_rdata2;
wire        rf_we   ;
wire [ 4:0] rf_waddr;
wire [31:0] rf_wdata;

wire [31:0] alu_src1   ;
wire [31:0] alu_src2   ;
wire [31:0] alu_result ;
wire [31:0] final_result;
wire [31:0] mem_result;
wire rj_eq_rd;
assign seq_pc       = pc + 3'h4;
assign nextpc       = br_taken ? br_target : seq_pc;
//潜在问题 --- reset是resetn的同步版本，延迟一个时钟周期 resetn从低变高，reset维持高电平一个周期，这个周期内，pc被设置为0x1bfffffc，下个周期reset变低，pc加4更新，变为0x1c000000
always @(posedge clk) begin
    if (reset) begin
        pc <= 32'h1bfffffc;     //trick: to make nextpc be 0x1c000000 during reset 
    end
    else begin
        pc <= nextpc;
    end
end

assign inst_sram_we    = 1'b0;
assign inst_sram_addr  = pc;
assign inst_sram_wdata = 32'b0;
assign inst            = inst_sram_rdata;

assign op_31_26  = inst[31:26];
assign op_25_22  = inst[25:22];
assign op_21_20  = inst[21:20];
assign op_19_15  = inst[19:15];

assign rd   = inst[ 4: 0];
assign rj   = inst[ 9: 5];
assign rk   = inst[14:10];

assign i12  = inst[21:10];
assign i20  = inst[24: 5];
assign i16  = inst[25:10];
assign i26  = {inst[ 9: 0], inst[25:10]};

decoder_6_64 u_dec0(.in(op_31_26 ), .out(op_31_26_d ));
decoder_4_16 u_dec1(.in(op_25_22 ), .out(op_25_22_d ));
decoder_2_4  u_dec2(.in(op_21_20 ), .out(op_21_20_d ));
decoder_5_32 u_dec3(.in(op_19_15 ), .out(op_19_15_d ));

assign inst_add_w  = op_31_26_d[6'h00] & op_25_22_d[4'h0] & op_21_20_d[2'h1] & op_19_15_d[5'h00];
assign inst_sub_w  = op_31_26_d[6'h00] & op_25_22_d[4'h0] & op_21_20_d[2'h1] & op_19_15_d[5'h02];
assign inst_slt    = op_31_26_d[6'h00] & op_25_22_d[4'h0] & op_21_20_d[2'h1] & op_19_15_d[5'h04];
assign inst_sltu   = op_31_26_d[6'h00] & op_25_22_d[4'h0] & op_21_20_d[2'h1] & op_19_15_d[5'h05];
assign inst_nor    = op_31_26_d[6'h00] & op_25_22_d[4'h0] & op_21_20_d[2'h1] & op_19_15_d[5'h08];
assign inst_and    = op_31_26_d[6'h00] & op_25_22_d[4'h0] & op_21_20_d[2'h1] & op_19_15_d[5'h09];
assign inst_or     = op_31_26_d[6'h00] & op_25_22_d[4'h0] & op_21_20_d[2'h1] & op_19_15_d[5'h0a];
assign inst_xor    = op_31_26_d[6'h00] & op_25_22_d[4'h0] & op_21_20_d[2'h1] & op_19_15_d[5'h0b];
assign inst_slli_w = op_31_26_d[6'h00] & op_25_22_d[4'h1] & op_21_20_d[2'h0] & op_19_15_d[5'h01];
assign inst_srli_w = op_31_26_d[6'h00] & op_25_22_d[4'h1] & op_21_20_d[2'h0] & op_19_15_d[5'h09];
assign inst_srai_w = op_31_26_d[6'h00] & op_25_22_d[4'h1] & op_21_20_d[2'h0] & op_19_15_d[5'h11];
assign inst_addi_w = op_31_26_d[6'h00] & op_25_22_d[4'ha];
assign inst_ld_w   = op_31_26_d[6'h0a] & op_25_22_d[4'h2];
assign inst_st_w   = op_31_26_d[6'h0a] & op_25_22_d[4'h6];
assign inst_jirl   = op_31_26_d[6'h13];
assign inst_b      = op_31_26_d[6'h14];
assign inst_bl     = op_31_26_d[6'h15];
assign inst_beq    = op_31_26_d[6'h16];
assign inst_bne    = op_31_26_d[6'h17];
assign inst_lu12i_w= op_31_26_d[6'h05] & ~inst[25]; //后部分为了检验剩余位是否为零
//下列是为了告知ALU做什么操作
assign alu_op[ 0] = inst_add_w | inst_addi_w | inst_ld_w | inst_st_w //加法
                    | inst_jirl | inst_bl;
assign alu_op[ 1] = inst_sub_w; 
assign alu_op[ 2] = inst_slt; 
assign alu_op[ 3] = inst_sltu;
assign alu_op[ 4] = inst_and;
assign alu_op[ 5] = inst_nor;
assign alu_op[ 6] = inst_or;
assign alu_op[ 7] = inst_xor;
assign alu_op[ 8] = inst_slli_w;
assign alu_op[ 9] = inst_srli_w;
assign alu_op[10] = inst_srai_w;
assign alu_op[11] = inst_lu12i_w;
// 表明指令需要的立即数
assign need_ui5   =  inst_slli_w | inst_srli_w | inst_srai_w; //五位无符号
assign need_si12  =  inst_addi_w | inst_ld_w | inst_st_w;
assign need_si16  =  inst_jirl | inst_beq | inst_bne;
assign need_si20  =  inst_lu12i_w;
assign need_si26  =  inst_b | inst_bl;
assign src2_is_4  =  inst_jirl | inst_bl; //常数4

assign imm = src2_is_4 ? 32'h4                      :
             need_si20 ? {i20[19:0], 12'b0}         :
             need_ui5 ? rk                          :
/* need_si12*/{{20{i12[11]}}, i12[11:0]} ;//默认情况，i5包含于i12，在使用的时候仅取imm[4:0]即可，但是好像没做区分，所以还是把ui5分开

assign br_offs = need_si26 ? {{ 4{i26[25]}}, i26[25:0], 2'b0} :
                             {{14{i16[15]}}, i16[15:0], 2'b0} ;

assign jirl_offs = {{14{i16[15]}}, i16[15:0], 2'b0};

assign src_reg_is_rd = inst_beq | inst_bne | inst_st_w; //第二个读端口读的是rd

assign src1_is_pc    = inst_jirl | inst_bl; //这些计算的第一个操作数来自于pc

assign src2_is_imm   = inst_slli_w |
                       inst_srli_w |
                       inst_srai_w |
                       inst_addi_w |
                       inst_ld_w   |
                       inst_st_w   |
                       inst_lu12i_w|
                       inst_jirl   |
                       inst_bl     ;

assign res_from_mem  = inst_ld_w;
assign dst_is_r1     = inst_bl; //写入的目的地是r1
assign gr_we         = ~inst_st_w & ~inst_beq & ~inst_bne & ~inst_b; //如果不是这些运算，则给通用寄存器的写权限
assign mem_we        = inst_st_w;
assign dest          = dst_is_r1 ? 5'd1 : rd; //通用架构00001表示寄存器1

assign rf_raddr1 = rj;
assign rf_raddr2 = src_reg_is_rd ? rd :rk;
regfile u_regfile(
    .clk    (clk      ),
    .raddr1 (rf_raddr1),
    .rdata1 (rf_rdata1),
    .raddr2 (rf_raddr2),
    .rdata2 (rf_rdata2),
    .we     (rf_we    ),
    .waddr  (rf_waddr ),
    .wdata  (rf_wdata )
    );

assign rj_value  = rf_rdata1;
assign rkd_value = rf_rdata2;

assign rj_eq_rd = (rj_value == rkd_value);
assign br_taken = (   inst_beq  &&  rj_eq_rd
                   || inst_bne  && !rj_eq_rd
                   || inst_jirl
                   || inst_bl
                   || inst_b
                  ) && valid;
assign br_target = (inst_beq || inst_bne || inst_bl || inst_b) ? (pc + br_offs) :
                                                   /*inst_jirl*/ (rj_value + jirl_offs);

assign alu_src1 = src1_is_pc  ? pc[31:0] : rj_value;
assign alu_src2 = src2_is_imm ? imm : rkd_value;

alu u_alu(
    .alu_op     (alu_op    ),
    .alu_src1   (alu_src1  ),
    .alu_src2   (alu_src2  ),
    .alu_result (alu_result)
    );

assign data_sram_we    = mem_we && valid;//设计层次上的硬件接口层，实际写使能信号
assign data_sram_addr  = alu_result;
assign data_sram_wdata = rkd_value;

assign mem_result   = data_sram_rdata;  
assign final_result = res_from_mem ? mem_result : alu_result;

assign rf_we    = gr_we && valid;
assign rf_waddr = dest;
assign rf_wdata = final_result;

// debug info generate
assign debug_wb_pc       = pc;
assign debug_wb_rf_we   = {4{rf_we}};
assign debug_wb_rf_wnum  = dest;
assign debug_wb_rf_wdata = final_result;

endmodule
