//第一个进程，同步时序always块，形式固定
always@(posedge clk or negedge rst_n)
begin
	if(!rst_n)
		current_state <= idle;
	else 
		current_state <= next_state;
end
//第二个always，组合逻辑模块，描述状态迁移条件判断
always@(*)
begin
	case(current_state)
	idle:
		begin
			if(...)
				next_state = 
			else
				next_state = 
		end
        s0:
		begin
			
		end
	s1:
		begin
			
		end
	default:
		begin
			next_state = idle;
		end
	endcase
end
//第三个进程，描述输出，同步时序always块
always@(posedge clk or negedge rst_n)
begin
	if(!rst_n)
		dout <= 1'b0;
	else 
		begin
			case(next_state)
			idle 	    : dout <= ;
			s0 	 	    : dout <= ;
			s1	 	    : dout <= ;
			default	    : dout <= ;
			endcase 
		end
end