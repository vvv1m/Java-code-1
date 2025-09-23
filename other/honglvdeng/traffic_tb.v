`timescale 1ns / 1ps

module tb_honglvdeng;

// 输入信号
reg clk_50M;
reg reset_btn;
reg AS1, AS2, BS1, BS2;

// 输出信号
wire AG1, AG2, AGL1, AGL2, AY1, AY2, AR1, AR2;
wire BG1, BG2, BGL1, BGL2, BY1, BY2, BR1, BR2;
wire [2:0] video_red, video_green;
wire [1:0] video_blue;
wire video_hsync, video_vsync, video_clk, video_de;

// 添加内部信号观察 - 计时器相关
wire [7:0] main_road_countdown;
wire [7:0] side_road_countdown;
wire [3:0] main_road_state;
wire [3:0] side_road_state;
wire [3:0] current_state;
wire [1:0] sensor_combo;
wire main_timer_timeout;
wire side_timer_timeout;
wire [7:0] main_timer_count;
wire [7:0] side_timer_count;

// 实例化被测试模块
traffic_light_top uut (
    .clk_50M(clk_50M),
    .reset_btn(reset_btn),
    .AS1(AS1), .AS2(AS2),
    .BS1(BS1), .BS2(BS2),
    .video_red(video_red),
    .video_green(video_green),
    .video_blue(video_blue),
    .video_hsync(video_hsync),
    .video_vsync(video_vsync),
    .video_clk(video_clk),
    .video_de(video_de),
    .AG1(AG1), .AG2(AG2),
    .AGL1(AGL1), .AGL2(AGL2),
    .AY1(AY1), .AY2(AY2),
    .AR1(AR1), .AR2(AR2),
    .BG1(BG1), .BG2(BG2),
    .BGL1(BGL1), .BGL2(BGL2),
    .BY1(BY1), .BY2(BY2),
    .BR1(BR1), .BR2(BR2)
);

// 连接内部信号到观察信号
assign main_road_countdown = uut.main_road_countdown;
assign side_road_countdown = uut.side_road_countdown;
assign main_road_state = uut.main_road_state;
assign side_road_state = uut.side_road_state;
assign current_state = uut.traffic_ctrl.current_state;
assign sensor_combo = uut.traffic_ctrl.sensor_combo;
assign main_timer_timeout = uut.traffic_ctrl.main_timer_timeout;
assign side_timer_timeout = uut.traffic_ctrl.side_timer_timeout;
assign main_timer_count = uut.traffic_ctrl.main_timer_count;
assign side_timer_count = uut.traffic_ctrl.side_timer_count;

// 产生时钟信号 - 50MHz
initial begin
    clk_50M = 0;
    forever #10 clk_50M = ~clk_50M; // 50MHz时钟周期为20ns
end

// 产生复位信号和传感器信号
initial begin
    // 初始化
    reset_btn = 1;
    AS1 = 0; AS2 = 0; BS1 = 0; BS2 = 0;
    #100;
    reset_btn = 0;

    // 测试序列1: 仅主道有车 (AS=1, BS=0) - 观察主道循环
    #1000;
    AS1 = 1; AS2 = 0; BS1 = 0; BS2 = 0;
    #60000; // 观察完整的主道循环 (27+3+17+3=50秒周期)

    // 测试序列2: 无车状态 (AS=0, BS=0)
    AS1 = 0; AS2 = 0; BS1 = 0; BS2 = 0;
    #20000; // 观察主道继续循环

    // 测试序列3: 仅支道有车 (AS=0, BS=1) - 观察切换过程
    AS1 = 0; AS2 = 0; BS1 = 1; BS2 = 0;
    #80000; // 观察切换到支道和支道循环

    // 测试序列4: 双方都有车 (AS=1, BS=1) - 观察完整周期或黄灯过渡
    AS1 = 1; AS2 = 0; BS1 = 1; BS2 = 0;
    #100000; // 观察完整周期或相关逻辑

    // 测试序列5: 从双方有车回到仅主道有车
    AS1 = 1; AS2 = 0; BS1 = 0; BS2 = 0;
    #60000; // 观察退出机制

    $finish;
end

// 生成波形文件 - 包含所有重要信号，重点添加计时器信号
initial begin
    $dumpfile("traffic_light_detailed.vcd");
    $dumpvars(0, tb_honglvdeng);
    
    // === 基础信号 ===
    $dumpvars(1, clk_50M, reset_btn);
    $dumpvars(1, AS1, AS2, BS1, BS2);
    
    // === 红绿灯输出信号 ===
    $dumpvars(1, AG1, AG2, AGL1, AGL2, AY1, AY2, AR1, AR2);
    $dumpvars(1, BG1, BG2, BGL1, BGL2, BY1, BY2, BR1, BR2);
    
    // === 控制器状态信号 ===
    $dumpvars(1, uut.traffic_ctrl.current_state);
    $dumpvars(1, uut.traffic_ctrl.next_state);
    $dumpvars(1, uut.traffic_ctrl.sensor_combo);
    $dumpvars(1, uut.traffic_ctrl.from_state);
    
    // === 重点：主道计时器相关信号 ===
    $dumpvars(1, uut.traffic_ctrl.main_timer_count);          // 主道计时器当前计数
    $dumpvars(1, uut.traffic_ctrl.main_timer_timeout);        // 主道计时器超时信号
    $dumpvars(1, uut.traffic_ctrl.main_timer_init);           // 主道计时器初始值
    $dumpvars(1, uut.traffic_ctrl.main_timer_load);           // 主道计时器加载信号
    $dumpvars(1, uut.traffic_ctrl.main_timer_enable);         // 主道计时器使能信号
    
    // === 重点：支道计时器相关信号 ===
    $dumpvars(1, uut.traffic_ctrl.side_timer_count);          // 支道计时器当前计数
    $dumpvars(1, uut.traffic_ctrl.side_timer_timeout);        // 支道计时器超时信号
    $dumpvars(1, uut.traffic_ctrl.side_timer_init);           // 支道计时器初始值
    $dumpvars(1, uut.traffic_ctrl.side_timer_load);           // 支道计时器加载信号
    $dumpvars(1, uut.traffic_ctrl.side_timer_enable);         // 支道计时器使能信号
    
    // === 计时器内部详细信号 - 主道计时器实例 ===
    $dumpvars(2, uut.traffic_ctrl.main_timer_inst.count);     // 主道计时器内部计数
    $dumpvars(2, uut.traffic_ctrl.main_timer_inst.timeout);   // 主道计时器内部超时
    $dumpvars(2, uut.traffic_ctrl.main_timer_inst.clk_div);   // 主道计时器分频计数器
    $dumpvars(2, uut.traffic_ctrl.main_timer_inst.clk_1s);    // 主道计时器1秒时钟脉冲
    $dumpvars(2, uut.traffic_ctrl.main_timer_inst.enable);    // 主道计时器内部使能
    $dumpvars(2, uut.traffic_ctrl.main_timer_inst.load);      // 主道计时器内部加载
    $dumpvars(2, uut.traffic_ctrl.main_timer_inst.init_value); // 主道计时器内部初始值
    
    // === 计时器内部详细信号 - 支道计时器实例 ===
    $dumpvars(2, uut.traffic_ctrl.side_timer_inst.count);     // 支道计时器内部计数
    $dumpvars(2, uut.traffic_ctrl.side_timer_inst.timeout);   // 支道计时器内部超时
    $dumpvars(2, uut.traffic_ctrl.side_timer_inst.clk_div);   // 支道计时器分频计数器
    $dumpvars(2, uut.traffic_ctrl.side_timer_inst.clk_1s);    // 支道计时器1秒时钟脉冲
    $dumpvars(2, uut.traffic_ctrl.side_timer_inst.enable);    // 支道计时器内部使能
    $dumpvars(2, uut.traffic_ctrl.side_timer_inst.load);      // 支道计时器内部加载
    $dumpvars(2, uut.traffic_ctrl.side_timer_inst.init_value); // 支道计时器内部初始值
    
    // === 顶层显示信号 ===
    $dumpvars(1, uut.main_road_countdown);                    // 主道倒计时显示
    $dumpvars(1, uut.side_road_countdown);                    // 支道倒计时显示
    $dumpvars(1, uut.main_road_state);                        // 主道状态显示
    $dumpvars(1, uut.side_road_state);                        // 支道状态显示
    
    // === LCD相关BCD转换信号（可选） ===
    $dumpvars(1, uut.countA_shi, uut.countA_ge);              // 主道倒计时BCD
    $dumpvars(1, uut.countC_shi, uut.countC_ge);              // 支道倒计时BCD
    $dumpvars(1, uut.countD_shi, uut.countD_ge);              // 传感器状态BCD
end

// 保留简化的监控输出用于控制台显示
initial begin
    $monitor("Time=%0t, State=%0d, MainTimer=%0d, SideTimer=%0d, MainLights=%b%b%b%b, SideLights=%b%b%b%b, Sensors=%b%b", 
             $time, 
             current_state,
             main_timer_count,
             side_timer_count,
             AG1|AG2, AGL1|AGL2, AY1|AY2, AR1|AR2,
             BG1|BG2, BGL1|BGL2, BY1|BY2, BR1|BR2,
             AS1|AS2, BS1|BS2);
end

// 增强的事件监控 - 计时器相关事件
always @(posedge clk_50M) begin
    // 状态变化监控
    if (uut.traffic_ctrl.current_state != uut.traffic_ctrl.next_state) begin
        $display("*** Time=%0t: 状态变化 %0d -> %0d, 主计时器=%0d, 支计时器=%0d ***", 
                 $time, uut.traffic_ctrl.current_state, uut.traffic_ctrl.next_state,
                 main_timer_count, side_timer_count);
    end
    
    // 主道计时器事件监控
    if (uut.traffic_ctrl.main_timer_timeout) begin
        $display("*** Time=%0t: 主道计时器超时! 状态=%0d, 计数=%0d ***", 
                 $time, current_state, main_timer_count);
    end
    
    if (uut.traffic_ctrl.main_timer_load) begin
        $display("*** Time=%0t: 主道计时器加载新值=%0d, 状态=%0d ***", 
                 $time, uut.traffic_ctrl.main_timer_init, current_state);
    end
    
    // 支道计时器事件监控
    if (uut.traffic_ctrl.side_timer_timeout) begin
        $display("*** Time=%0t: 支道计时器超时! 状态=%0d, 计数=%0d ***", 
                 $time, current_state, side_timer_count);
    end
    
    if (uut.traffic_ctrl.side_timer_load) begin
        $display("*** Time=%0t: 支道计时器加载新值=%0d, 状态=%0d ***", 
                 $time, uut.traffic_ctrl.side_timer_init, current_state);
    end
    
    // 1秒时钟脉冲监控
    if (uut.traffic_ctrl.main_timer_inst.clk_1s) begin
        $display(">>> Time=%0t: 主道计时器1秒脉冲, 计数从%0d递减 <<<", 
                 $time, main_timer_count);
    end
    
    if (uut.traffic_ctrl.side_timer_inst.clk_1s) begin
        $display(">>> Time=%0t: 支道计时器1秒脉冲, 计数从%0d递减 <<<", 
                 $time, side_timer_count);
    end
end

// 仿真超时保护
initial begin
    #500000; // 500μs仿真时间
    $display("Simulation timeout!");
    $finish;
end

endmodule