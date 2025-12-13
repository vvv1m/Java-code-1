from .base_agent import BaseAgent

class MCPAgent(BaseAgent):
    def __init__(self, name, context_manager, message_handler):
        super().__init__(name)
        self.context_manager = context_manager
        self.message_handler = message_handler
        self.running = False

    def initialize(self):
        """初始化代理"""
        self.running = True
        print(f"{self.name} initialized successfully.")

    def process_message(self, message):
        """处理接收到的消息"""
        if not isinstance(message, dict):
            raise ValueError("Message must be a dictionary")
        
        # 处理消息
        self.message_handler.handle_message(self.name, message)
        # 更新上下文
        self.context_manager.update_context({
            "last_message": message,
            "processed_at": self._get_timestamp()
        })
        
        return {"status": "processed", "message": message}

    def respond(self, response):
        """生成响应"""
        return f"{self.name} responds: {response}"

    def shutdown(self):
        """关闭代理"""
        self.running = False
        self.context_manager.clear_context()
        print(f"{self.name} shut down successfully.")

    def listen_for_message(self):
        """监听消息（示例实现）"""
        # 这里应该实现实际的消息监听逻辑
        return {"type": "test", "content": "sample message"}

    def generate_response(self, message):
        """生成响应消息"""
        return f"Processed: {message.get('content', 'no content')}"

    def _get_timestamp(self):
        """获取当前时间戳"""
        from datetime import datetime
        return datetime.now().isoformat()

    def run(self):
        """主运行逻辑"""
        self.initialize()
        print(f"{self.name} is running...")
        
        # 示例：处理几条消息后停止
        for i in range(3):
            message = self.listen_for_message()
            result = self.process_message(message)
            response = self.generate_response(message)
            print(self.respond(response))
        
        self.shutdown()