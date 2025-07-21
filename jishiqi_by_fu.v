module LS160_LCD(
    input  wire clk_50M,
    input  wire reset_btn, 
    input  wire KEY0,      // 正计时启动/暂停
    input  wire KEY1,      // 倒计时启动/暂停
    input  wire KEY2,      // 模式切换

    input  wire KEY4,      // 倒计时设置/开始切换
    input  wire KEY5,      // 频率调整按钮
    input  wire KEY6,      // 显示精度调整按钮
    input wire button1, 
    input wire button2,
    output wire [2:0] video_red,
    output wire [2:0] video_green,
    output wire [1:0] video_blue,
    output wire       video_hsync,
    output wire       video_vsync,
    output wire       video_clk,
    output wire       video_de,
    output reg        led
);

    // 1Hz分频
    wire clk_1hz, clk_2hz, clk_4hz, clk_10hz, clk_20hz, clk_40hz;
    wire pulse;
    clock_1hz u1(
        .CLK(clk_50M),
        .CLRn(~reset_btn), // 高电平复位
        .T1hz(clk_1hz)
    );

    // 2Hz分频
    clock_2hz u2(
        .CLK(clk_50M),
        .CLRn(~reset_btn),
        .T2hz(clk_2hz)
    );
    
    // 4Hz分频
    clock_4hz u3(
        .CLK(clk_50M),
        .CLRn(~reset_btn),
        .T4hz(clk_4hz)
    );

    // 0.1秒精度时钟
    clock_10hz u4(
        .CLK(clk_50M),
        .CLRn(~reset_btn),
        .T10hz(clk_10hz)
    );
    
    clock_20hz u5(
        .CLK(clk_50M),
        .CLRn(~reset_btn),
        .T20hz(clk_20hz)
    );
    
    clock_40hz u6(
        .CLK(clk_50M),
        .CLRn(~reset_btn),
        .T40hz(clk_40hz)
    );

    // 模式与运行控制
    reg running;   // 计时使能
    reg [9:0] seconds;      // 0~999 (支持0.1秒精度)
    reg [9:0] countdown_init = 30; // 倒计时初始值

    reg [1:0] freq_mode;     // 00=1Hz, 01=2Hz, 10=4Hz
    reg precision_mode;      // 0=1秒, 1=0.1秒
    wire setting_mode;        // 0=设置模式, 1=计时模式



    // 按键消抖
    wire key5_pulse;
    wire button1_pulse, button2_pulse;
    key_debounce kdb1(.clk(clk_50M), .key(button1), .key_pulse(button1_pulse));
    key_debounce kdb2(.clk(clk_50M), .key(button2), .key_pulse(button2_pulse));
    key_debounce kd5(.clk(clk_50M), .key(KEY5), .key_pulse(key5_pulse));

    // 脉冲选择器
    assign pulse = (precision_mode) ? 
                   ((freq_mode == 2'b00) ? clk_10hz : 
                    (freq_mode == 2'b01) ? clk_20hz : clk_40hz) :
                   ((freq_mode == 2'b00) ? clk_1hz : 
                    (freq_mode == 2'b01) ? clk_2hz : clk_4hz);

    // KEY4控制倒计时设置/开始模式
    assign setting_mode = KEY4; // KEY4=0为设置，KEY4=1为计时

    //KEY5使用脉冲切换
    always @(posedge clk_50M or posedge reset_btn) begin
        if (reset_btn)
            freq_mode <= 2'b00;
        else if (key5_pulse) begin
            case (freq_mode)
                2'b00: freq_mode <= 2'b01; // 1Hz -> 2Hz
                2'b01: freq_mode <= 2'b10; // 2Hz -> 4Hz
                2'b10: freq_mode <= 2'b00; // 4Hz -> 1Hz
                default: freq_mode <= 2'b00;
            endcase
        end
    end

    // KEY6精度模式直接由KEY6控制
    always @(*) begin
        precision_mode = KEY6; // 0=1秒，1=0.1秒
    end

    // 倒计时初始值设置
    always @(posedge clk_50M) begin
        if (!setting_mode && KEY2 == 1) begin // 倒计时设置模式
            if (button2_pulse) begin // button2增加
                if (precision_mode) begin
                    if (countdown_init < 999)
                        countdown_init <= countdown_init + 1;
                end else begin
                    if (countdown_init < 99)
                        countdown_init <= countdown_init + 1;
                end
            end else if (button1_pulse) begin // 用button1减少
                if (precision_mode) begin
                    if (countdown_init > 100) // 最小10.0秒
                        countdown_init <= countdown_init - 1;
                end else begin
                    if (countdown_init > 10) // 最小10秒
                        countdown_init <= countdown_init - 1;
                end
            end
        end
    end




    
    // 模式直接由KEY2控制
    wire mode = KEY2; // 0: 正计时, 1: 倒计时

    // 计时使能控制
    always @(posedge clk_50M or posedge reset_btn) begin
        if (reset_btn)
            running <= 0;
        else if (mode == 0)
            running <= KEY0;
        else
            running <= KEY1;
    end

    // 1. 添加pulse边沿检测
    reg pulse_reg1, pulse_reg2;
    wire pulse_edge;

    always @(posedge clk_50M or posedge reset_btn) begin
        if (reset_btn) begin
            pulse_reg1 <= 1'b0;
            pulse_reg2 <= 1'b0;
        end else begin
            pulse_reg1 <= pulse;
            pulse_reg2 <= pulse_reg1;
        end
    end

    assign pulse_edge = pulse_reg1 & ~pulse_reg2;

    // 2. 修改秒计数器
    always @(posedge clk_50M or posedge reset_btn) begin
        if (reset_btn)
            seconds <= (mode == 0) ? 0 : countdown_init;
        else if (running && pulse_edge) begin
            if (mode == 0) begin // 正计时
                if (precision_mode) begin
                    // 0.1秒精度：计数到999 (99.9秒)
                    if (seconds < 999)
                        seconds <= seconds + 1;
                    else
                        seconds <= 0;
                end else begin
                    // 1秒精度：计数到59秒
                    if (seconds < 59)
                        seconds <= seconds + 1;
                    else
                        seconds <= 0;
                end
            end else begin // 倒计时
                if (seconds > 0)
                    seconds <= seconds - 1;
                else
                    seconds <= 0;
            end
        end
    end


    // LED闪烁控制（倒计时剩余5秒时闪烁）
    reg [22:0] led_cnt;
    wire led_threshold = precision_mode ? (seconds <= 50) : (seconds <= 5);
    
    always @(posedge clk_50M or posedge reset_btn) begin
        if (reset_btn) begin
            led <= 0;
            led_cnt <= 0;
        end else if (mode == 1 && running && led_threshold) begin
            led_cnt <= led_cnt + 1;
            if (led_cnt >= 25_000_000) begin // 0.5秒闪烁
                led <= ~led;
                led_cnt <= 0;
            end
        end else begin
            led <= 0;
            led_cnt <= 0;
        end
    end


    wire [9:0] display_value = (setting_mode == 1'b0 && mode == 1'b1) ? countdown_init : seconds;
    wire [3:0] shi = precision_mode ? (display_value / 100) : (display_value / 10);
    wire [3:0] ge = precision_mode ? ((display_value / 10) % 10) : (display_value % 10);
    wire [3:0] xiaoshu = precision_mode ? (display_value % 10) : 4'd0;

    lcd_top u7(
        .clk_50M(clk_50M),
        .reset_btn(reset_btn),
        .countA_shi(shi),
        .countA_ge(ge),
        .countB_shi(xiaoshu), 
        // ...其余端口不变...
        .countB_ge(freq_mode[1:0]), 
        .countC_shi(precision_mode ? 4'h1 : 4'h0), // 显示精度模式
        .countC_ge(setting_mode ? 4'h1 : 4'h0),    // 显示设置模式
        .countD_shi(mode ? 4'h1 : 4'h0),           // 显示计时模式
        .countD_ge(running ? 4'h1 : 4'h0),         // 显示运行状态
        .countE_shi(countdown_init / 10),
        .countE_ge(countdown_init % 10),
        .countF_shi(xiaoshu),// 小数位
        .countF_ge(freq_mode[1:0]),// 显示频率模式
        .video_red(video_red),
        .video_green(video_green),
        .video_blue(video_blue),
        .video_hsync(video_hsync),
        .video_vsync(video_vsync),
        .video_clk(video_clk),
        .video_de(video_de)
    );
endmodule

// 按键消抖模块
module key_debounce(
    input wire clk,
    input wire key,
    output wire key_pulse
);
    reg [19:0] cnt;
    reg key_reg1, key_reg2;
    
    always @(posedge clk) begin
        key_reg1 <= key;
        key_reg2 <= key_reg1;
        
        if (key_reg2 != key_reg1)
            cnt <= 0;
        else if (cnt < 1000000)
            cnt <= cnt + 1;
    end
    
    assign key_pulse = (cnt == 999999) && key_reg2;
endmodule


module LS160( 
input  wire CLK,
input  wire CLRn,
input  wire LDn,
input  wire ENP,
input  wire ENT,
input  wire [3:0] D,
output reg [3:0] Q,
output wire RCO);
assign RCO = Q[3]&(~Q[2])&(~Q[1])&Q[0]&ENT;
always @( posedge CLK or negedge CLRn ) 
begin	 
	if ( ~CLRn ) Q <= 4'b0000;
		else if ( ~LDn ) Q <= D;
			else if (ENT && ENP) begin					
				if ( Q < 4'b1001 ) 
				    Q <= Q + 1;
					else Q <= 4'b0000; 
				end
	end
endmodule 