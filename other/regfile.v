`timescale 1ns / 1ps
//*************************************************************************
//   > 文件名: regfile.v
//   > 描述  ：寄存器堆模块，同步写，异步读
//   > 作者  : LOONGSON
//   > 日期  : 2016-04-14
//*************************************************************************
module regfile(
    input             clk,
    input             wen,
    input      [4 :0] raddr1,
    input      [4 :0] raddr2,
    input      [4 :0] waddr,
    input      [31:0] wdata,
    output reg [31:0] rdata1,
    output reg [31:0] rdata2,
    input      [4 :0] test_addr,
    output reg [31:0] test_data
    );
    reg [31:0] rf[31:0];
     
    // three ported register file
    // read two ports combinationally
    // write third port on rising edge of clock
    // register 0 hardwired to 0

//补充完成异步读同步写寄存器堆的代码
    always @(posedge clk) begin
        if (wen && waddr != 5'b00000) begin
            // 写使能有效且目标地址不是 $0 寄存器时，写入数据
            rf[waddr] <= wdata;
        end
    end
    
    // 异步读操作 - 读端口1
    always @(*) begin
        if (raddr1 == 5'b00000) begin
            // $0 寄存器永远读出 0
            rdata1 = 32'h00000000;
        end else begin
            rdata1 = rf[raddr1];
        end
    end
    
    // 异步读操作 - 读端口2
    always @(*) begin
        if (raddr2 == 5'b00000) begin
            // $0 寄存器永远读出 0
            rdata2 = 32'h00000000;
        end else begin
            rdata2 = rf[raddr2];
        end
    end
    
    // 测试端口 - 异步读取任意寄存器的值
    always @(*) begin
        if (test_addr == 5'b00000) begin
            test_data = 32'h00000000;
        end else begin
            test_data = rf[test_addr];
        end
    end
    
    // 初始化所有寄存器为 0（可选，用于仿真）
    integer i;
    initial begin
        for (i = 0; i < 32; i = i + 1) begin
            rf[i] = 32'h00000000;
        end
    end


endmodule
