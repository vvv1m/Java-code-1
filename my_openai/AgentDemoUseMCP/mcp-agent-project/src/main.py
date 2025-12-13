import sys
from pathlib import Path

# 添加项目根目录到 Python 路径
project_root = Path(__file__).parent
sys.path.insert(0, str(project_root))

from agent.mcp_agent import MCPAgent
from mcp.context_manager import ContextManager
from mcp.message_handler import MessageHandler
from tools.tool_registry import ToolRegistry
from config.settings import load_settings

def main():
    """主函数"""
    print("Starting MCP Agent Project...")
    
    try:
        # Load configuration settings
        settings = load_settings()
        print(f"Settings loaded: {settings}")

        # Initialize components
        context_manager = ContextManager()
        message_handler = MessageHandler(context_manager)
        tool_registry = ToolRegistry()

        # Register some example tools
        tool_registry.register_tool("echo", lambda x: x)
        tool_registry.register_tool("upper", lambda x: x.upper() if isinstance(x, str) else x)

        # Create the MCP Agent
        agent = MCPAgent(
            name="MCP-Agent-1",
            context_manager=context_manager,
            message_handler=message_handler
        )

        # Register agent with message handler
        message_handler.register_agent(agent.name, agent)

        # Start the agent
        print(f"\nStarting agent: {agent.name}")
        agent.run()

        print("\nMCP Agent Project completed successfully.")

    except Exception as e:
        print(f"Error: {e}", file=sys.stderr)
        sys.exit(1)

if __name__ == "__main__":
    main()