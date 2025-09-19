`timescale 1ns / 1ps
//主道 20s红 3s黄 27s绿 RoadA
//支道 17s绿 3s黄 30s红 RoadB
//两个车道同时有车 BothHaveCar = 1 -》采取上述计时方式
//四个车道四个车道传感器 AS1 AS2 BS1 BS2，AS中有一个为1说明主道有车，支道同理
//主道灯：AG1 绿灯，AGL1 左转绿灯，AY1 黄灯，AR1 红灯
//		AG2 AGL2 AY2 AR2功能同上
//		相同功能的灯用去掉数字的标识代指，因为同时亮起与熄灭 如AG1和AG2由AG控制
//支道灯：BG1 BGL1 BY1 BR1 BG2 BGL2 BY2 BR2 功能同上
//需要八种计时器
//3s计时器 7s计时器 12s计时器 17s计时器 27s计时器 30s计时器 45s计时器，或许可以通过一个可传入初始值的倒计时器实现
//运转情况：默认主道起始绿灯，支道起始红灯
//当AS为1 BS为1时主道 AG-27s -》AY-3s -》AGL-12s -》AY-3s -》AR-30s
//				支道 BR-45s -》BY-3s -》BGL-7s -》BY-3s -》BG-17s
//当AS为1 BS为0
//若仅一个方向有车时，处理方法是：该方向原来为红灯时，另一个方向立即由绿灯变为黄灯，3秒钟后再由黄灯变为红灯，同时本方
//向由红灯变为绿灯。该方向原为绿灯时，续保持绿灯。当另一方向有车来时，作两个方向均有车处理

//重写逻辑：1当AS = BS = 0或AS = 1 BS = 0，双方都没车或仅主道有车 保持主道直行灯和左转灯为绿，且倒计时为99，支道为红灯，且倒计时为99
//2当切换至AS = 0 BS = 1，只有支道有车时，主道改为黄灯，倒计时3s，三s后变为红灯，倒计时显示为99
//主道变为黄灯时支道也变为黄灯，同时倒计时3s，结束后支道变为绿灯，倒计时显示为99；
//3当从逻辑1的情况切换至AS = 1 BS = 1时，将主道倒计时改为27s，亮直行绿灯，倒计时结束后改为3s，亮黄灯，结束后改为12s亮左转绿灯，结束后改为30s，亮红灯，结束后重新改为27s，亮直行绿灯，循环执行
//  当主道将倒计时改为27s时，支道倒计时改为45s，亮红灯，结束后改为3s，亮黄灯，结束后改为7s亮左转绿灯，结束后改为3s亮黄灯，结束后改为17s亮直行绿灯，结束后重新改为45s亮红灯，循环执行
//4当从逻辑2的情况切换至AS = 1 BS = 1时，主道支道倒计时同时改为3，亮黄灯，结束后执行3逻辑内容
//逻辑有误，目前只发现由逻辑1转变到逻辑3时主道27s倒计时结束后直接变成12s倒计时，没有亮3s黄灯
//逻辑有误，主道和支道都跳过了黄灯阶段
//更改逻辑：状态机整体逻辑为，由MAIN_GREEN_HOLD为起始状态，检测sensor_combo，若为00/10，则下一状态还为MAIN_GREEN_HOLD，若为01，则直接转到状态BOTH_YELLOW_3S,

module traffic_light_top(
    input wire clk_50M,
    input wire reset_btn,
    input wire AS1, AS2,      // 主道传感器
    input wire BS1, BS2,      // 支道传感器
    
    // LCD显示输出
    output wire [2:0] video_red,
    output wire [2:0] video_green,
    output wire [1:0] video_blue,
    output wire       video_hsync,
    output wire       video_vsync,
    output wire       video_clk,
    output wire       video_de,
    
    // 红绿灯输出
    output wire AG1, AG2,      // 主道绿灯
    output wire AGL1, AGL2,    // 主道左转绿灯
    output wire AY1, AY2,      // 主道黄灯
    output wire AR1, AR2,      // 主道红灯
    output wire BG1, BG2,      // 支道绿灯
    output wire BGL1, BGL2,    // 支道左转绿灯
    output wire BY1, BY2,      // 支道黄灯
    output wire BR1, BR2       // 支道红灯
);

// 红绿灯控制器的内部信号
wire [7:0] main_road_countdown;    // 主道倒计时
wire [7:0] side_road_countdown;    // 支道倒计时
wire [3:0] main_road_state;        // 主道当前状态
wire [3:0] side_road_state;        // 支道当前状态

// 传感器信号
wire AS, BS;
assign AS = AS1 | AS2;
assign BS = BS1 | BS2;

// 实例化红绿灯控制器
traffic_light_controller traffic_ctrl(
    .clk(clk_50M),
    .reset(reset_btn),
    .AS1(AS1), 
    .AS2(AS2),
    .BS1(BS1), 
    .BS2(BS2),
    
    .AG1(AG1), .AG2(AG2),
    .AGL1(AGL1), .AGL2(AGL2),
    .AY1(AY1), .AY2(AY2),
    .AR1(AR1), .AR2(AR2),
    .BG1(BG1), .BG2(BG2),
    .BGL1(BGL1), .BGL2(BGL2),
    .BY1(BY1), .BY2(BY2),
    .BR1(BR1), .BR2(BR2),
    
    // 新增输出端口
    .main_road_countdown(main_road_countdown),
    .side_road_countdown(side_road_countdown),
    .main_road_state(main_road_state),
    .side_road_state(side_road_state)
);

// LCD显示用的BCD转换
wire [3:0] countA_shi, countA_ge;  // 主道倒计时的十位和个位
wire [3:0] countB_shi, countB_ge;  // 未使用
wire [3:0] countC_shi, countC_ge;  // 支道倒计时的十位和个位（修改）
wire [3:0] countD_shi, countD_ge;  // 传感器状态：十位=AS，个位=BS（修改）
wire [3:0] countE_shi, countE_ge;  // 未使用
wire [3:0] countF_shi, countF_ge;  // 未使用

// 主道倒计时BCD转换
assign countA_shi = main_road_countdown / 10;
assign countA_ge = main_road_countdown % 10;

// countB未使用，置为0
assign countB_shi = 4'b0000;
assign countB_ge = 4'b0000;

// 支道倒计时BCD转换（修改：使用countC显示支道倒计时）
assign countC_shi = side_road_countdown / 10;
assign countC_ge = side_road_countdown % 10;

// 传感器状态显示（修改：使用countD显示AS和BS）
assign countD_shi = {3'b000, AS};  // 主道传感器状态
assign countD_ge = {3'b000, BS};   // 支道传感器状态

// 其他计数器置为0
assign countE_shi = 4'b0000;
assign countE_ge = 4'b0000;
assign countF_shi = 4'b0000;
assign countF_ge = 4'b0000;

// 实例化LCD显示模块
lcd_top lcd_display(
    .clk_50M(clk_50M),
    .reset_btn(reset_btn),
    
    .countA_shi(countA_shi),
    .countA_ge(countA_ge),
    .countB_shi(countB_shi),
    .countB_ge(countB_ge),
    .countC_shi(countC_shi),
    .countC_ge(countC_ge),
    .countD_shi(countD_shi),
    .countD_ge(countD_ge),
    .countE_shi(countE_shi),
    .countE_ge(countE_ge),
    .countF_shi(countF_shi),
    .countF_ge(countF_ge),
    
    .video_red(video_red),
    .video_green(video_green),
    .video_blue(video_blue),
    .video_hsync(video_hsync),
    .video_vsync(video_vsync),
    .video_clk(video_clk),
    .video_de(video_de)
);

endmodule

// 重新设计的红绿灯控制器 - 按照新的逻辑要求
module traffic_light_controller(
    input wire clk,
    input wire reset,
    input wire AS1, AS2,      // 主道传感器
    input wire BS1, BS2,      // 支道传感器
    
    // 红绿灯输出
    output reg AG1, AG2,      // 主道绿灯
    output reg AGL1, AGL2,    // 主道左转绿灯
    output reg AY1, AY2,      // 主道黄灯
    output reg AR1, AR2,      // 主道红灯
    output reg BG1, BG2,      // 支道绿灯
    output reg BGL1, BGL2,    // 支道左转绿灯
    output reg BY1, BY2,      // 支道黄灯
    output reg BR1, BR2,      // 支道红灯
    
    // 显示用输出
    output wire [7:0] main_road_countdown,  
    output wire [7:0] side_road_countdown,  
    output wire [3:0] main_road_state,      
    output wire [3:0] side_road_state       
);

// 定义状态 - 根据新逻辑重新设计
parameter [3:0] 
    // 逻辑1: AS=0,BS=0 或 AS=1,BS=0 状态
    MAIN_GREEN_HOLD = 4'd0,     // 主道绿灯保持，倒计时99
    
    // 逻辑2: AS=0,BS=1 切换状态
    BOTH_YELLOW_3S = 4'd1,      // 主道黄灯，支道黄灯，同时倒计时3s
    SIDE_GREEN_HOLD = 4'd2,     // 支道绿灯保持，倒计时99
    
    // 逻辑3&4: AS=1,BS=1 完整周期状态
    MAIN_GREEN_27S = 4'd3,      // 主道绿灯27s，支道红灯45s
    MAIN_YELLOW1_3S = 4'd4,     // 主道黄灯3s，支道红灯继续
    MAIN_LEFT_12S = 4'd5,       // 主道左转绿灯12s，支道红灯继续
    MAIN_YELLOW2_3S = 4'd6,     // 主道黄灯3s，支道红灯继续
    SIDE_GREEN_17S = 4'd7,      // 支道绿灯17s，主道红灯30s
    SIDE_YELLOW1_3S = 4'd8,     // 支道黄灯3s，主道红灯继续
    SIDE_LEFT_7S = 4'd9,        // 支道左转绿灯7s，主道红灯继续
    SIDE_YELLOW2_3S = 4'd10;    // 支道黄灯3s，主道红灯继续

// 定义时间常量
parameter TIME_3S  = 8'd3;
parameter TIME_7S  = 8'd7;
parameter TIME_12S = 8'd12;
parameter TIME_17S = 8'd17;
parameter TIME_27S = 8'd27;
parameter TIME_30S = 8'd30;
parameter TIME_45S = 8'd45;
parameter TIME_99S = 8'd99;  // 新增99秒显示

// 状态机信号
reg [3:0] current_state, next_state;
reg [3:0] from_state; // 记录转换前的状态，用于判断逻辑3还是逻辑4

// 传感器信号组合
wire AS, BS;
wire [1:0] sensor_combo;
assign AS = AS1 | AS2;
assign BS = BS1 | BS2;
assign sensor_combo = {AS, BS};

// 双计时器信号 - 主道和支道分别计时
reg main_timer_load, main_timer_enable;
reg side_timer_load, side_timer_enable;
reg [7:0] main_timer_init, side_timer_init;
wire [7:0] main_timer_count, side_timer_count;
wire main_timer_timeout, side_timer_timeout;

// 主道计时器
countdown_timer main_timer_inst(
    .clk(clk),
    .reset(reset),
    .enable(main_timer_enable),
    .load(main_timer_load),
    .init_value(main_timer_init),
    .count(main_timer_count),
    .timeout(main_timer_timeout)
);

// 支道计时器
countdown_timer side_timer_inst(
    .clk(clk),
    .reset(reset),
    .enable(side_timer_enable),
    .load(side_timer_load),
    .init_value(side_timer_init),
    .count(side_timer_count),
    .timeout(side_timer_timeout)
);

// 状态转换逻辑
always @(posedge clk or posedge reset) begin
    if (reset) begin
        current_state <= MAIN_GREEN_HOLD;
        from_state <= MAIN_GREEN_HOLD;
    end else begin
        if (current_state != next_state) begin
            from_state <= current_state;
        end
        current_state <= next_state;
    end
end

// 下一状态逻辑 - 根据4种逻辑实现
always @(*) begin
    next_state = current_state; // 默认保持当前状态
    
    case (current_state)
        MAIN_GREEN_HOLD: begin
            if (sensor_combo == 2'b01) begin
                next_state = BOTH_YELLOW_3S;
            end else if (sensor_combo == 2'b11) begin
                next_state = MAIN_GREEN_27S;
            end else begin
                next_state = MAIN_GREEN_HOLD;
            end
        end
        
        // 逻辑2: 双向黄灯过渡状态
        BOTH_YELLOW_3S: begin
            if (main_timer_timeout && side_timer_timeout) begin
                case (sensor_combo)
                    2'b01: next_state = SIDE_GREEN_HOLD;    // 仍只有支道有车
                    2'b11: next_state = MAIN_GREEN_27S;     // 变成两道都有车，逻辑3
                    default: next_state = MAIN_GREEN_HOLD;  // 其他情况回到逻辑1
                endcase
            end else begin
                next_state = BOTH_YELLOW_3S;
            end
        end
        
        // 逻辑2: 支道绿灯保持状态
        SIDE_GREEN_HOLD: begin
            case (sensor_combo)
                2'b01: next_state = SIDE_GREEN_HOLD;        // 仍只有支道有车，保持
                2'b11: next_state = BOTH_YELLOW_3S;         // 主道也有车了，逻辑4（通过双黄灯）
                default: next_state = MAIN_GREEN_HOLD;      // 支道无车，回到逻辑1
            endcase
        end
        
        // 逻辑3&4: 完整周期状态
        MAIN_GREEN_27S: begin
            if (sensor_combo == 2'b11) begin
                if (main_timer_timeout) begin
                    next_state = MAIN_YELLOW1_3S;  // 超时后进入黄灯
                end else begin
                    next_state = MAIN_GREEN_27S;   // 未超时保持当前状态
                end
            end else begin
                // 传感器状态改变，退出完整周期
                case(sensor_combo)
                    2'b10: next_state = MAIN_GREEN_HOLD;
                    2'b01: next_state = BOTH_YELLOW_3S;
                    2'b00: next_state = MAIN_GREEN_HOLD;
                    default: next_state = MAIN_GREEN_HOLD;
                endcase
            end
        end
        
        MAIN_YELLOW1_3S: begin
            if (sensor_combo == 2'b11) begin
                if (main_timer_timeout) begin
                    next_state = MAIN_LEFT_12S;
                end else begin
                    next_state = MAIN_YELLOW1_3S;
                end
            end else begin
                case(sensor_combo)
                    2'b10: next_state = MAIN_GREEN_HOLD;
                    2'b01: next_state = BOTH_YELLOW_3S;
                    2'b00: next_state = MAIN_GREEN_HOLD;
                endcase
            end
        end
        
        MAIN_LEFT_12S: begin
            if (sensor_combo == 2'b11) begin
                if (main_timer_timeout) begin
                    next_state = MAIN_YELLOW2_3S;
                end else begin
                    next_state = MAIN_LEFT_12S;
                end
            end else begin
                case(sensor_combo)
                    2'b10: next_state = MAIN_GREEN_HOLD;
                    2'b01: next_state = BOTH_YELLOW_3S;
                    2'b00: next_state = MAIN_GREEN_HOLD;
                endcase
            end
        end
        
        MAIN_YELLOW2_3S: begin
            if (sensor_combo == 2'b11) begin
                if (main_timer_timeout) begin
                    next_state = SIDE_GREEN_17S;  // 修复：应该先到支道绿灯
                end else begin
                    next_state = MAIN_YELLOW2_3S;
                end
            end else begin
                case(sensor_combo)
                    2'b10: next_state = MAIN_GREEN_HOLD;
                    2'b01: next_state = BOTH_YELLOW_3S;
                    2'b00: next_state = MAIN_GREEN_HOLD;
                endcase
            end
        end
        
        SIDE_GREEN_17S: begin
            if (sensor_combo == 2'b11) begin
                if (side_timer_timeout) begin  // 使用支道计时器
                    next_state = SIDE_YELLOW1_3S;
                end else begin
                    next_state = SIDE_GREEN_17S;
                end
            end else begin
                case(sensor_combo)
                    2'b10: next_state = MAIN_GREEN_HOLD;
                    2'b01: next_state = BOTH_YELLOW_3S;
                    2'b00: next_state = MAIN_GREEN_HOLD;
                endcase
            end
        end
        
        SIDE_YELLOW1_3S: begin
            if (sensor_combo == 2'b11) begin
                if (side_timer_timeout) begin
                    next_state = SIDE_LEFT_7S;
                end else begin
                    next_state = SIDE_YELLOW1_3S;
                end
            end else begin
                case(sensor_combo)
                    2'b10: next_state = MAIN_GREEN_HOLD;
                    2'b01: next_state = BOTH_YELLOW_3S;
                    2'b00: next_state = MAIN_GREEN_HOLD;
                endcase
            end
        end
        
        SIDE_LEFT_7S: begin
            if (sensor_combo == 2'b11) begin
                if (side_timer_timeout) begin
                    next_state = SIDE_YELLOW2_3S;
                end else begin
                    next_state = SIDE_LEFT_7S;
                end
            end else begin
                case(sensor_combo)
                    2'b10: next_state = MAIN_GREEN_HOLD;
                    2'b01: next_state = BOTH_YELLOW_3S;
                    2'b00: next_state = MAIN_GREEN_HOLD;
                endcase
            end
        end
        
        SIDE_YELLOW2_3S: begin
            if (sensor_combo == 2'b11) begin
                if (side_timer_timeout) begin
                    next_state = MAIN_GREEN_27S;  // 重新开始周期
                end else begin
                    next_state = SIDE_YELLOW2_3S;
                end
            end else begin
                case(sensor_combo)
                    2'b10: next_state = MAIN_GREEN_HOLD;
                    2'b01: next_state = BOTH_YELLOW_3S;
                    2'b00: next_state = MAIN_GREEN_HOLD;
                endcase
            end
        end
        
        default: next_state = MAIN_GREEN_HOLD;
    endcase
end

// 计时器控制逻辑
always @(posedge clk or posedge reset) begin
    if (reset) begin
        main_timer_enable <= 1'b1;
        main_timer_load <= 1'b1;
        side_timer_enable <= 1'b1;
        side_timer_load <= 1'b1;
    end else begin
        main_timer_enable <= 1'b1;
        side_timer_enable <= 1'b1;
        main_timer_load <= (current_state != next_state);
        side_timer_load <= (current_state != next_state);
    end
end

// 计时器初值设置
always @(*) begin
    case (current_state)
        MAIN_GREEN_HOLD: begin
            main_timer_init = TIME_99S;
            side_timer_init = TIME_99S;
        end
        
        BOTH_YELLOW_3S: begin
            main_timer_init = TIME_3S;
            side_timer_init = TIME_3S;
        end
        
        SIDE_GREEN_HOLD: begin
            main_timer_init = TIME_99S;
            side_timer_init = TIME_99S;
        end
        
        MAIN_GREEN_27S: begin
            main_timer_init = TIME_27S;
            side_timer_init = TIME_45S;
        end
        
        MAIN_YELLOW1_3S: begin
            main_timer_init = TIME_3S;
            side_timer_init = TIME_45S - TIME_27S;
        end
        
        MAIN_LEFT_12S: begin
            main_timer_init = TIME_12S;
            side_timer_init = TIME_45S - TIME_27S - TIME_3S;
        end
        
        MAIN_YELLOW2_3S: begin
            main_timer_init = TIME_3S;
            side_timer_init = TIME_45S - TIME_27S - TIME_3S - TIME_12S;
        end
        
        SIDE_GREEN_17S: begin
            main_timer_init = TIME_30S;
            side_timer_init = TIME_17S;
        end

        SIDE_YELLOW1_3S: begin
            main_timer_init = TIME_30S - TIME_17S;
            side_timer_init = TIME_3S;
        end
        
        SIDE_LEFT_7S: begin
            main_timer_init = TIME_30S - TIME_17S - TIME_3S;
            side_timer_init = TIME_7S;
        end
        
        SIDE_YELLOW2_3S: begin
            main_timer_init = TIME_30S - TIME_17S - TIME_3S - TIME_7S;
            side_timer_init = TIME_3S;
        end

        

        default: begin
            main_timer_init = TIME_99S;
            side_timer_init = TIME_99S;
        end

        
    endcase
end

// 输出逻辑
always @(*) begin
    // 默认全部灯关闭
    {AG1, AG2, AGL1, AGL2, AY1, AY2, AR1, AR2} = 8'b0;
    {BG1, BG2, BGL1, BGL2, BY1, BY2, BR1, BR2} = 8'b0;
    
    case (current_state)
        MAIN_GREEN_HOLD: begin
            {AG1, AG2, AGL1, AGL2} = 4'b1111;  // 主道直行和左转都绿灯
            {BR1, BR2} = 2'b11;                 // 支道红灯
        end
        
        BOTH_YELLOW_3S: begin
            {AY1, AY2} = 2'b11;    // 主道黄灯
            {BY1, BY2} = 2'b11;    // 支道黄灯
        end
        
        SIDE_GREEN_HOLD: begin
            {AR1, AR2} = 2'b11;                 // 主道红灯
            {BG1, BG2, BGL1, BGL2} = 4'b1111;   // 支道直行和左转都绿灯
        end
        
        MAIN_GREEN_27S: begin
            {AG1, AG2} = 2'b11;    // 主道绿灯
            {BR1, BR2} = 2'b11;    // 支道红灯
        end
        
        MAIN_YELLOW1_3S: begin
            {AY1, AY2} = 2'b11;    // 主道黄灯
            {BR1, BR2} = 2'b11;    // 支道红灯
        end
        
        MAIN_LEFT_12S: begin
            {AGL1, AGL2} = 2'b11;  // 主道左转绿灯
            {BR1, BR2} = 2'b11;    // 支道红灯
        end

        MAIN_YELLOW2_3S: begin
            {AY1, AY2} = 2'b11;    // 主道黄灯
            {BR1, BR2} = 2'b11;    // 支道红灯
        end
        SIDE_GREEN_17S: begin
            {AR1, AR2} = 2'b11;    // 主道红灯
            {BG1, BG2} = 2'b11;    // 支道绿灯
        end
        
        SIDE_YELLOW1_3S: begin
            {AR1, AR2} = 2'b11;    // 主道红灯
            {BY1, BY2} = 2'b11;    // 支道黄灯
        end
        
        SIDE_LEFT_7S: begin
            {AR1, AR2} = 2'b11;    // 主道红灯
            {BGL1, BGL2} = 2'b11;  // 支道左转绿灯
        end
        SIDE_YELLOW2_3S: begin
            {AR1, AR2} = 2'b11;    // 主道红灯
            {BY1, BY2} = 2'b11;    // 支道黄灯
        end
        default: begin
            {AR1, AR2} = 2'b11;    // 默认主道红灯
            {BR1, BR2} = 2'b11;    // 默认支道红灯
        end
    endcase
end

// 倒计时显示逻辑
assign main_road_countdown = main_timer_count;
assign side_road_countdown = side_timer_count;
assign main_road_state = current_state;
assign side_road_state = sensor_combo;

endmodule

//module countdown_timer(
//    input wire clk,           
//    input wire reset,         
//    input wire enable,        
//    input wire load,          
//    input wire [7:0] init_value,  
//    output reg [7:0] count,   
//    output wire timeout       
//);
//reg [25:0] clk_div;  // 修改为26位
//    localparam CLK_DIV_MAX = 26'd49_999_999;
//wire clk_1s;

//always @(posedge clk or posedge reset) begin
//    if (reset) begin
//        clk_div <= 0;
//    end else if (enable) begin
//        if (clk_div >= CLK_DIV_MAX) begin  // 使用 >= 而不是 ==
//            clk_div <= 0;
//        end else begin
//            clk_div <= clk_div + 1;
//        end
//    end
//end

//assign clk_1s = (clk_div == CLK_DIV_MAX);

//// 修改倒计时逻辑
//always @(posedge clk or posedge reset) begin
//    if (reset) begin
//        count <= 8'd0;
//    end else if (load) begin
//        count <= init_value;
//    end else if (enable && clk_1s && count > 0) begin
//        count <= count - 1;
//    end
//end

//// 修改超时条件 - 只在从1变为0的瞬间触发
//assign timeout = (count == 8'd1) && enable && clk_1s;

//endmodule

module countdown_timer(
    input wire clk,           
    input wire reset,         
    input wire enable,        
    input wire load,          
    input wire [7:0] init_value,  
    output reg [7:0] count,   
    output wire timeout       
);
reg [15:0] clk_div;
    localparam CLK_DIV_MAX = 16'd999;  // 仿真用快速时钟
wire clk_1s;

always @(posedge clk or posedge reset) begin
    if (reset) begin
        clk_div <= 0;
    end else if (enable) begin
        if (clk_div >= CLK_DIV_MAX) begin  // 使用 >= 而不是 ==
            clk_div <= 0;
        end else begin
            clk_div <= clk_div + 1;
        end
    end
end

assign clk_1s = (clk_div == CLK_DIV_MAX);

// 修改倒计时逻辑
always @(posedge clk or posedge reset) begin
    if (reset) begin
        count <= 8'd0;
    end else if (load) begin
        count <= init_value;
    end else if (enable && clk_1s && count > 0) begin
        count <= count - 1;
    end
end

// 修改超时条件 - 只在从1变为0的瞬间触发
assign timeout = (count == 8'd1) && enable && clk_1s;

endmodule
