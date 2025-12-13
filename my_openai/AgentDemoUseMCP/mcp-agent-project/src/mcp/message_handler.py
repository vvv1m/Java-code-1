from .protocol import MCPProtocol

class MessageHandler:
    def __init__(self, context_manager=None):
        self.agents = {}
        self.protocol = MCPProtocol()
        self.context_manager = context_manager

    def register_agent(self, agent_name, agent):
        """注册代理"""
        self.agents[agent_name] = agent
        print(f"Agent '{agent_name}' registered successfully.")

    def handle_message(self, agent_name, message):
        """处理消息"""
        if not self.protocol.validate_message(message):
            raise ValueError("Invalid message format")
        
        if agent_name in self.agents:
            agent = self.agents[agent_name]
            response = agent.process_message(message)
            return response
        else:
            raise ValueError(f"Agent '{agent_name}' not found.")

    def process_message(self, message):
        """处理通用消息"""
        if not self.protocol.validate_message(message):
            return {"status": "error", "message": "Invalid format"}
        
        # 编码消息
        encoded = self.protocol.encode_message(message)
        
        # 如果有上下文管理器，更新上下文
        if self.context_manager:
            self.context_manager.set_context("last_processed", encoded)
        
        return {"status": "success", "data": encoded}

    def broadcast_message(self, message):
        """向所有注册的代理广播消息"""
        responses = {}
        for agent_name, agent in self.agents.items():
            try:
                responses[agent_name] = agent.process_message(message)
            except Exception as e:
                responses[agent_name] = {"status": "error", "error": str(e)}
        return responses