`timescale 1ns / 1ps

module tb_data_ram_ip();

    reg clk;
    reg [7:0] a;          // 8位地址（256个字）
    reg [3:0] we;         // 写使能
    reg [31:0] d;         // 写数据
    wire [31:0] spo;      // 单端口输出
    
    reg [7:0] dpra;       // 双端口读地址
    wire [31:0] dpo;      // 双端口输出
    
    integer error_count;
    
    // 实例化 IP 核生成的 data_ram
    data_ram dut (
        .clk(clk),
        .a(a),
        .we(we),
        .d(d),
        .spo(spo),
        .dpra(dpra),
        .dpo(dpo)
    );
    
    // 时钟生成
    initial clk = 0;
    always #5 clk = ~clk;
    
    // 测试流程
    initial begin
        error_count = 0;
        a = 0; we = 0; d = 0; dpra = 0;
        
        $display("=== Data RAM IP Test Start ===\n");
        
        repeat(3) @(posedge clk);
        
        // 测试1: 写入数据
        $display("[Test 1] Write Test");
        a = 8'h00; d = 32'hDEADBEEF; we = 4'b1111;
        @(posedge clk); #1; we = 4'b0000;
        @(posedge clk); #1;
        
        if (spo == 32'hDEADBEEF) begin
            $display("  ✓ Addr 0x00: Write 0xDEADBEEF - PASS (Read: 0x%h)", spo);
        end else begin
            $display("  ✗ Addr 0x00: FAIL (Expected: 0xDEADBEEF, Got: 0x%h)", spo);
            error_count = error_count + 1;
        end
        
        a = 8'h01; d = 32'h12345678; we = 4'b1111;
        @(posedge clk); #1; we = 4'b0000;
        @(posedge clk); #1;
        
        if (spo == 32'h12345678) begin
            $display("  ✓ Addr 0x01: Write 0x12345678 - PASS (Read: 0x%h)", spo);
        end else begin
            $display("  ✗ Addr 0x01: FAIL (Expected: 0x12345678, Got: 0x%h)", spo);
            error_count = error_count + 1;
        end
        
        // 测试2: 双端口读取
        $display("\n[Test 2] Dual-Port Read Test");
        a = 8'h00; dpra = 8'h01;
        @(posedge clk); #1;
        
        $display("  Main port (addr 0x00): 0x%h", spo);
        $display("  Dual port (addr 0x01): 0x%h", dpo);
        
        if (spo == 32'hDEADBEEF && dpo == 32'h12345678) begin
            $display("  ✓ Dual-port read - PASS");
        end else begin
            $display("  ✗ Dual-port read - FAIL");
            error_count = error_count + 1;
        end
        
        // 测试3: 字节写入
        $display("\n[Test 3] Byte Write Test");
        a = 8'h02; d = 32'hFFFFFFFF; we = 4'b1111;
        @(posedge clk); #1; we = 4'b0000;
        @(posedge clk);
        
        d = 32'h000000AA; we = 4'b0001;
        @(posedge clk); #1; we = 4'b0000;
        @(posedge clk); #1;
        
        if (spo == 32'hFFFFFFAA) begin
            $display("  ✓ Byte write - PASS (Result: 0x%h)", spo);
        end else begin
            $display("  ✗ Byte write - FAIL (Expected: 0xFFFFFFAA, Got: 0x%h)", spo);
            error_count = error_count + 1;
        end
        
        // 总结
        #100;
        $display("\n=== Test Summary ===");
        if (error_count == 0)
            $display("ALL TESTS PASSED! ✓");
        else
            $display("%0d ERRORS FOUND! ✗", error_count);
        
        $finish;
    end
    
    initial begin
        $dumpfile("tb_ram_ip.vcd");
        $dumpvars(0, tb_data_ram_ip);
    end
    
    initial #10000 $finish;

endmodule