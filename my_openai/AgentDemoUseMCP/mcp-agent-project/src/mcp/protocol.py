import json
from typing import Dict, Any

class MCPProtocol:
    def __init__(self):
        self.message_format = "JSON"
        self.version = "1.0"

    def encode_message(self, message: Dict[str, Any]) -> Dict[str, Any]:
        """将消息编码为MCP协议格式"""
        if not isinstance(message, dict):
            raise ValueError("Message must be a dictionary")
        
        return {
            "format": self.message_format,
            "version": self.version,
            "content": message,
            "metadata": {
                "encoded": True,
                "timestamp": self._get_timestamp()
            }
        }

    def decode_message(self, encoded_message: Dict[str, Any]) -> Any:
        """将编码的消息解码为原始格式"""
        if not isinstance(encoded_message, dict):
            raise ValueError("Encoded message must be a dictionary")
        
        return encoded_message.get("content", "")

    def validate_message(self, message: Any) -> bool:
        """验证消息是否符合MCP协议规范"""
        if not isinstance(message, dict):
            return False
        
        # 检查必需字段
        required_fields = ["type", "content"]
        return all(field in message for field in required_fields)

    def format_message(self, message: Dict[str, Any]) -> Dict[str, Any]:
        """格式化消息"""
        if not self.validate_message(message):
            # 如果消息不完整，尝试修复
            if "type" not in message:
                message["type"] = "unknown"
            if "content" not in message:
                message["content"] = ""
        
        return message

    def get_message_format(self) -> str:
        """获取当前消息格式"""
        return self.message_format

    def _get_timestamp(self) -> str:
        """获取当前时间戳"""
        from datetime import datetime
        return datetime.now().isoformat()

    def serialize(self, message: Dict[str, Any]) -> str:
        """序列化消息为JSON字符串"""
        return json.dumps(message, ensure_ascii=False)

    def deserialize(self, message_str: str) -> Dict[str, Any]:
        """反序列化JSON字符串为消息"""
        try:
            return json.loads(message_str)
        except json.JSONDecodeError as e:
            raise ValueError(f"Failed to deserialize message: {e}")