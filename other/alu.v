`timescale 1ns / 1ps
module alu(
    input  [11:0] alu_control,
    input  [31:0] alu_src1,
    input  [31:0] alu_src2,
    output [31:0] alu_result
    );

    // ALU控制信号，独热码
    wire alu_add;
    wire alu_sub;
    wire alu_slt;
    wire alu_sltu;
    wire alu_and;
    wire alu_nor;
    wire alu_or;
    wire alu_xor;
    wire alu_sll;
    wire alu_srl;
    wire alu_sra;
    wire alu_lui;

    assign alu_add  = alu_control[11];
    assign alu_sub  = alu_control[10];
    assign alu_slt  = alu_control[ 9];
    assign alu_sltu = alu_control[ 8];
    assign alu_and  = alu_control[ 7];
    assign alu_nor  = alu_control[ 6];
    assign alu_or   = alu_control[ 5];
    assign alu_xor  = alu_control[ 4];
    assign alu_sll  = alu_control[ 3];
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


    //有符号比较 SLT
    assign slt_result = (alu_src1[31] & ~alu_src2[31]) ? 32'd1 :
                        (~alu_src1[31] & alu_src2[31]) ? 32'd0 :
                        adder_result[31] ? 32'd1 : 32'd0;

    //无符号比较 SLTU
    assign sltu_result = ~adder_cout ? 32'd1 : 32'd0;


    //按位与 AND
    assign and_result = alu_src1 & alu_src2;

    //按位或非 NOR
    assign nor_result = ~(alu_src1 | alu_src2);

    //按位或 OR
    assign or_result = alu_src1 | alu_src2;

    //按位异或 XOR
    assign xor_result = alu_src1 ^ alu_src2;

    //逻辑左移 SLL
    assign sll_result = alu_src2 << alu_src1[4:0];

    //逻辑右移 SR
    assign srl_result = alu_src2 >> alu_src1[4:0];

    //算术右移 SRA
    assign sra_result = $signed(alu_src2) >>> alu_src1[4:0];

    //高位加载 LUI
    assign lui_result = {alu_src2[15:0], 16'b0};

    //最终结果选择
    assign alu_result = ({32{alu_add | alu_sub}} & add_sub_result)
                      | ({32{alu_slt           }} & slt_result   )
                      | ({32{alu_sltu          }} & sltu_result  )
                      | ({32{alu_and           }} & and_result   )
                      | ({32{alu_nor           }} & nor_result   )
                      | ({32{alu_or            }} & or_result    )
                      | ({32{alu_xor           }} & xor_result   )
                      | ({32{alu_sll           }} & sll_result   )
                      | ({32{alu_srl           }} & srl_result   )
                      | ({32{alu_sra           }} & sra_result   )
                      | ({32{alu_lui           }} & lui_result   );

endmodule