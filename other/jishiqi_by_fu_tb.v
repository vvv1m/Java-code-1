`timescale 1ns/1ps

module LS160_LCD_tb;

    reg clk_50M;
    reg reset_btn;
    reg KEY0, KEY1, KEY2, KEY4, KEY5, KEY6;
    wire [2:0] video_red;
    wire [2:0] video_green;
    wire [1:0] video_blue;
    wire video_hsync, video_vsync, video_clk, video_de;
    wire led;

    // 实例化待测模块
    LS160_LCD uut (
        .clk_50M(clk_50M),
        .reset_btn(reset_btn),
        .KEY0(KEY0),
        .KEY1(KEY1),
        .KEY2(KEY2),
        .KEY4(KEY4),
        .KEY5(KEY5),
        .KEY6(KEY6),
        .video_red(video_red),
        .video_green(video_green),
        .video_blue(video_blue),
        .video_hsync(video_hsync),
        .video_vsync(video_vsync),
        .video_clk(video_clk),
        .video_de(video_de),
        .led(led)
    );

    // 50MHz时钟
    initial clk_50M = 0;
    always #10 clk_50M = ~clk_50M; // 20ns周期=50MHz

    initial begin
        $display("time\tmode\trunning\tseconds\tsetting_mode\tcountdown_init");
        $monitor("%t\t%b\t%b\t%d\t%b\t%d", $time, uut.mode, uut.running, uut.seconds, uut.setting_mode, uut.countdown_init);

        // 初始化
        reset_btn = 1;
        KEY0 = 0;
        KEY1 = 0;
        KEY2 = 0;
        KEY4 = 0;
        KEY5 = 0;
        KEY6 = 0;
        #100;
        reset_btn = 0;

        // ----------- 正计时功能演示 -----------
        // KEY2=0，KEY0=1，开始正计时
        KEY2 = 0;
        #100;
        KEY0 = 1;
        #2_200_000; // 仿真2.2ms（0.5ms分频，2ms能看到计数变化）

        // 暂停
        KEY0 = 0;
        #1_000_000;

        // 继续
        KEY0 = 1;
        #4_000_000;

        // 暂停
        KEY0 = 0;
        #500_000;

        // ----------- 切换到倒计时功能 -----------
        // KEY2=1，KEY1=1，开始倒计时
        KEY2 = 1;
        #100;
        KEY1 = 1;
        #2_200_000; // 仿真2.2ms

        // 暂停
        KEY1 = 0;
        #1_000_000;

        // 继续
        KEY1 = 1;
        #2_000_000;

        // 暂停
        KEY1 = 0;
        #500_000;

        // 复位并保持所有按键为0，延时更长时间
        reset_btn = 1;
        KEY0 = 0;
        KEY1 = 0;
        KEY2 = 0;
        #100_000;
        reset_btn = 0;
        #5_000_000;    // 延时5ms，确保seconds稳定在初值

        // 再启动正计时
        KEY2 = 0;
        #100_000;
        KEY0 = 1;
        #2_000_000;
        // 结束仿真
        KEY0 = 0;
        KEY2 = 1;
        KEY1 = 1;
        reset_btn = 1;
        #100_000;
        reset_btn = 0;
        #100_000;
        
        //测试拓展功能
        KEY4 = 1; #40 KEY4 = 0;
        #1000;
        //初始化倒计时
        KEY0 = 1; #40 KEY0 = 0;#40
        KEY0 = 1; #40 KEY0 = 0;#40
        KEY0 = 1; #40 KEY0 = 0;#40
        KEY0 = 1; #40 KEY0 = 0;#40
        KEY0 = 1; #40 KEY0 = 0;#40
        KEY0 = 1; #40 KEY0 = 0;#40
        KEY0 = 1; #40 KEY0 = 0;#40
        KEY0 = 1; #40 KEY0 = 0;#40
        KEY0 = 1; #40 KEY0 = 0;#40
        KEY0 = 1; #40 KEY0 = 0;#40
        KEY1 = 1; #40 KEY1 = 0;#40
        KEY1 = 1; #40 KEY1 = 0;#40
        KEY1 = 1; #40 KEY1 = 0;#40
        KEY1 = 1; #40 KEY1 = 0;#40
        //准备启动计时
        KEY4 = 1; #40 KEY4 = 0;#40
        KEY1 = 1;
        #2_000_000;
        //倒计时暂停
        KEY1 = 0;
        //切换为正计时
        KEY2 = 0;
        reset_btn = 1;#100
        reset_btn = 0;#100
        KEY0 = 1;
        #2_000_000;
        KEY5 = 1; #40 KEY5 = 0;#40
        #200_000;
        KEY5 = 1; #40 KEY5 = 0; #40
        #200_000;
        KEY5 = 1; #40 KEY5 = 0; #40
        #200_000;
        KEY6 = 1; #40 KEY6 = 0;
        #2_000_000;
        
        $stop;
    end

endmodule