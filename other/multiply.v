`timescale 1ns / 1ps
//*************************************************************************
//   > 文件名: multiply.v
//   > 描述  ：乘法器模块，低效率的迭代乘法算法，使用两个乘数绝对值参与运算
//   > 作者  : LOONGSON
//   > 日期  : 2016-04-14
//*************************************************************************
module multiply(              // 乘法器
    input         clk,        // 时钟
    input         mult_begin, // 乘法开始信号
    input  [31:0] mult_op1,   // 乘法源操作数1
    input  [31:0] mult_op2,   // 乘法源操作数2
    output [63:0] product,    // 乘积
    output        mult_end    // 乘法结束信号
);

 //补充完成乘法器模块的实现过程
 localparam S_IDLE = 3'b001;
    localparam S_CALC = 3'b010;
    localparam S_SIGN = 3'b100;

    reg [2:0]  state;
    reg [31:0] op1_r;
    reg [63:0] product_r;
    reg [5:0]  count;
    reg        sign;
    reg        mult_end_r;

    assign product  = product_r;
    assign mult_end = mult_end_r;

    always @(posedge clk) begin
        case (state)
            S_IDLE: begin
                mult_end_r <= 1'b0;
                if (mult_begin) begin
                    op1_r <= mult_op1[31] ? -mult_op1 : mult_op1;
                    product_r <= {32'd0, (mult_op2[31] ? -mult_op2 : mult_op2)};
                    sign <= mult_op1[31] ^ mult_op2[31];
                    count <= 6'd0;
                    state <= S_CALC;
                end
            end

            S_CALC: begin
                if (count < 32) begin
                    if (product_r[0]) begin
                        // 使用33位加法，保留进位
                        product_r <= {{1'b0, product_r[63:32]} + {1'b0, op1_r}, product_r[31:1]};
                    end else begin
                        product_r <= product_r >> 1;
                    end
                    count <= count + 1;
                end else begin
                    state <= S_SIGN;
                end
            end

            S_SIGN: begin
                if (sign) begin
                    product_r <= -product_r;
                end
                mult_end_r <= 1'b1;
                state <= S_IDLE;
            end

            default: begin
                state <= S_IDLE;
            end
        endcase
    end

    initial begin
        state      = S_IDLE;
        op1_r      = 32'd0;
        product_r  = 64'd0;
        count      = 6'd0;
        sign       = 1'b0;
        mult_end_r = 1'b0;
    end
endmodule
