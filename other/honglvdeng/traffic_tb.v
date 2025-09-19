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
// 产生时钟信号
 
    initial begin
 
        clk_50M = 0;
 
        forever #10 clk_50M = ~clk_50M; // 50MHz时钟周期为20ns
 
    end
 
 
 
    // 产生复位信号和传感器信号
 
    initial begin
 
        // 初始化
 
        reset_btn = 1;
 
        AS1 = 0;
        AS2 = 0;
        BS1 = 0;
        BS2 = 0;
        #50;
 
        reset_btn = 0;
 
 
 
        // 模拟传感器信号
 
        #100;
 
        AS1 = 1;
        AS2 = 1;
        BS1 = 0;
        BS2 = 0;
 
        #50000; // 模拟50秒时间
 
        AS1 = 0;
        AS2 = 0;
        BS1 = 0;
        BS2 = 0;
 
        #1000;
 
        AS1 = 1;
        AS2 = 1;
        BS1 = 1;
        BS2 = 1;
 
        #50000; // 模拟50秒时间
 
        AS1 = 0;
        AS2 = 0;
        BS1 = 1;
        BS2 = 1;
 
        #50000; // 模拟50秒时间
 
        AS1 = 0;
        AS2 = 0;
        BS1 = 0;
        BS2 = 0;
 
    end
 
 
 
    // 仿真停止时间
 
    initial begin
 
        #200000; // 延长仿真时间，确保每种状态都超过一个周期
 
        $finish;
 
    end
 
 
 
    // 显示输出信号
 
    initial begin
        $monitor("Time=%0t, MainCount=%0d, SideCount=%0d, MainLights=%b%b%b%b, SideLights=%b%b%b%b, Sensors=%b%b", 
             $time, 
             uut.main_road_countdown, 
             uut.side_road_countdown,
             AG1|AG2, AGL1|AGL2, AY1|AY2, AR1|AR2,
             BG1|BG2, BGL1|BGL2, BY1|BY2, BR1|BR2,
             AS1|AS2, BS1|BS2);
     end
 
endmodule
