import unittest
from src.mcp.protocol import MCPProtocol
from src.mcp.message_handler import MessageHandler

class TestMCP(unittest.TestCase):

    def setUp(self):
        self.protocol = MCPProtocol()
        self.message_handler = MessageHandler()

    def test_protocol_message_format(self):
        message = {"type": "test", "content": "This is a test message."}
        formatted_message = self.protocol.format_message(message)
        self.assertIn("type", formatted_message)
        self.assertIn("content", formatted_message)

    def test_message_handler_process_message(self):
        message = {"type": "test", "content": "This is a test message."}
        response = self.message_handler.process_message(message)
        self.assertEqual(response["status"], "success")

if __name__ == "__main__":
    unittest.main()