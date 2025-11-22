`timescale 1ns / 1ps
//*************************************************************************
//   > 文件名: alu.v
//   > 描述  ：ALU模块，可做12种操作
//   > 作者  : LOONGSON
//   > 日期  : 2016-04-14
//*************************************************************************
module alu(
    input  [11:0] alu_control,  // ALU控制信号
    input  [31:0] alu_src1,     // ALU操作数1,为补码
    input  [31:0] alu_src2,     // ALU操作数2，为补码
    output [31:0] alu_result    // ALU结果
    );

    // ALU控制信号，独热码
    wire alu_add;   //加法操作 y
    wire alu_sub;   //减法操作 
    wire alu_slt;   //有符号比较，小于置位，复用加法器做减法 y
    wire alu_sltu;  //无符号比较，小于置位，复用加法器做减法 y
    wire alu_and;   //按位与 y
    wire alu_nor;   //按位或非
    wire alu_or;    //按位或 y
    wire alu_xor;   //按位异或 y
    wire alu_sll;   //逻辑左移 y
    wire alu_srl;   //逻辑右移
    wire alu_sra;   //算术右移
    wire alu_lui;   //高位加载

    assign alu_add  = alu_control[11]; //y
    assign alu_sub  = alu_control[10];
    assign alu_slt  = alu_control[ 9]; //y
    assign alu_sltu = alu_control[ 8]; //y
    assign alu_and  = alu_control[ 7]; //y
    assign alu_nor  = alu_control[ 6];
    assign alu_or   = alu_control[ 5]; //y
    assign alu_xor  = alu_control[ 4]; //y
    assign alu_sll  = alu_control[ 3]; //y
    assign alu_srl  = alu_control[ 2];
    assign alu_sra  = alu_control[ 1];
    assign alu_lui  = alu_control[ 0];

    wire [31:0] add_sub_result;
    wire [31:0] slt_result;
    wire [31:0] sltu_result;
    wire [31:0] and_result;
    wire [31:0] nor_result;
    wire [31:0] or_result;
    wire [31:0] xor_result;
    wire [31:0] sll_result;
    wire [31:0] srl_result;
    wire [31:0] sra_result;
    wire [31:0] lui_result;

  
//-----{加法器}begin
//add,sub,slt,sltu均使用该模块
    wire [31:0] adder_operand1;
    wire [31:0] adder_operand2;
    wire        adder_cin     ;
    wire [31:0] adder_result  ;
    wire        adder_cout    ;
    assign adder_operand1 = alu_src1; 
    assign adder_operand2 = alu_add ? alu_src2 : ~alu_src2; 
    assign adder_cin      = ~alu_add; //减法需要cin 
    adder adder_module(
    .operand1(adder_operand1),
    .operand2(adder_operand2),
    .cin     (adder_cin     ),
    .result  (adder_result  ),
    .cout    (adder_cout    )
    );

    //加减结果
    assign add_sub_result = adder_result;

 
//请自行补充其他运算的实现方法



    // 有符号数比较
    // 如果 alu_src1 < alu_src2 (有符号), 则结果为1, 否则为0
    assign slt_result = (alu_src1[31] & ~alu_src2[31]) ? 32'd1 :  // src1负，src2正
                        (~alu_src1[31] & alu_src2[31]) ? 32'd0 :  // src1正，src2负
                        adder_result[31] ? 32'd1 : 32'd0;         // 同号看减法结果



    // 无符号数比较
    // 如果 alu_src1 < alu_src2 (无符号), 则结果为1, 否则为0
    assign sltu_result = ~adder_cout ? 32'd1 : 32'd0;  // 无进位说明src1<src2


    //按位与 AND

    assign and_result = alu_src1 & alu_src2;


    //按位或 OR

    assign or_result = alu_src1 | alu_src2;


    // 按位异或 XOR

    assign xor_result = alu_src1 ^ alu_src2;



    // 逻辑左移，低位补0
    assign sll_result = alu_src2 << alu_src1[4:0];  // 移位数取低5位(0-31)


    //最终结果选择

    assign alu_result = ({32{alu_add | alu_sub}} & add_sub_result)
                    | ({32{alu_slt           }} & slt_result   )
                    | ({32{alu_sltu          }} & sltu_result  )
                    | ({32{alu_and           }} & and_result   )
                    | ({32{alu_or            }} & or_result    )
                    | ({32{alu_xor           }} & xor_result   )
                    | ({32{alu_sll           }} & sll_result   );


endmodule
