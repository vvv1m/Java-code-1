import asyncio
import os
import sys
from typing import Optional
from contextlib import AsyncExitStack

from mcp import ClientSession, StdioServerParameters
from mcp.client.stdio import stdio_client
from openai import OpenAI
from dotenv import load_dotenv

load_dotenv()

class MCPClient:
    """MCP 客户端 - 使用 DeepSeek 和 MCP 工具"""
    
    def __init__(self):
        """初始化客户端"""
        # API Key
        self.api_key = os.getenv("DEEPSEEK_API_KEY")
        if not self.api_key:
            raise ValueError("未找到 DEEPSEEK_API_KEY 环境变量！")
        
        # DeepSeek 客户端
        self.client = OpenAI(
            api_key=self.api_key,
            base_url="https://api.deepseek.com"
        )
        
        # MCP 会话
        self.session: Optional[ClientSession] = None
        self.exit_stack = AsyncExitStack()
        # 对话历史
        self.conversation_history = []
    async def connect_to_server(self,server_script_path: str):
        """
        connect to mcp server   
        
        server_script_path: str the path to mcp server: mcp_server.py
        """
        
        server_params = StdioServerParameters(
            command="python",
            args=[server_script_path],
            env=None
        )
        """build stdio"""
        stdio_transport = await self.exit_stack.enter_async_context(
            stdio_client(server_params)
        )
        self.stdio, self.write = stdio_transport
        """bulid MCP chat"""
        self.session = await self.exit_stack.enter_async_context(
            ClientSession(self.stdio, self.write)
        )
        await self.session.initialize()

        response = await self.session.list_tools()
        tools = response.tools
        print(f"\n✅ 已成功连接到 MCP 服务器")
        print(f"📦 可用工具: {[tool.name for tool in tools]}\n")
        """no problem"""

    async def get_available_tools(self) -> list:
        """获取所有可用工具的 OpenAI 格式"""
        response = await self.session.list_tools()
    
        openai_tools = []
        for tool in response.tools:
            # ✅ 清理 inputSchema，移除不必要的字段
            if hasattr(tool, 'inputSchema') and tool.inputSchema:
                parameters = tool.inputSchema.copy()
                # 移除 title 字段（DeepSeek 不需要）
                parameters.pop('title', None)
                if 'properties' in parameters:
                    for prop_name, prop_value in parameters['properties'].items():
                        if isinstance(prop_value, dict):
                            prop_value.pop('title', None)
            else:
                parameters = {
                    "type": "object",
                    "properties": {},
                    "required": []
                }
            
            openai_tool = {
                "type": "function",
                "function": {
                    "name": tool.name,
                    "description": tool.description or f"调用 {tool.name} 工具",
                    "parameters": parameters
                }
            }
            openai_tools.append(openai_tool)
        
        return openai_tools
    
    async def call_tool(self, tool_name: str, arguments: dict) -> str:
        """
        调用 MCP 工具
        
        Args:
            tool_name: 工具名称
            arguments: 工具参数字典
            
        Returns:
            工具执行结果
        """
        try:
            result = await self.session.call_tool(tool_name, arguments=arguments)
            # 提取文本内容
            contents = []
            for content in result.content:
                if hasattr(content, 'text'):
                    contents.append(content.text)
            return "\n".join(contents)
        except Exception as e:
            return f"工具调用失败: {str(e)}"
    def get_system_prompt(self) -> str:
        """获取系统提示词"""
        return """你是一个专业的星际争霸2战术顾问 AI 助手。

你的能力：
1. 查询各种族（人族、虫族、神族）的兵种信息
2. 分析兵种的详细数据（生命值、攻击力、造价、特性等）
3. 提供兵种克制关系建议
4. 根据对局情况设计战术策略

你的风格：
模仿sc2中的scv语气，在每次回答最后都加上“哎，pz太imba了，弱小的人族只能无助的流泪”

工作流程：
1. 理解用户的问题或需求
2. 使用工具获取相关的兵种数据
3. 基于数据进行分析和推理
4. 给出详细的战术建议和执行步骤

注意事项：
- 必须使用工具获取数据，不要凭空想象兵种属性
- 考虑实际对局中的资源限制和时间窗口
- 提供多种战术选择供用户参考
- 解释战术背后的逻辑和原理
- 回答要结构清晰，使用编号列表等格式
- 适当使用星际争霸术语，如 timing、微操、运营等"""
    async def process_query(self, query: str, show_thinking: bool = False) -> str:
        """
        使用 DeepSeek 和 MCP 工具处理用户查询
        
        Args:
            query: 用户查询内容
            show_thinking: 是否显示思考过程
            
        Returns:
            AI 回复内容
        """
        # ✅ 添加系统提示词
        messages = [
            {"role": "system", "content": self.get_system_prompt()}
        ]
        
        # 添加对话历史
        messages.extend(self.conversation_history)
        
        # 添加当前用户消息
        messages.append({"role": "user", "content": query})
        
        try:
            if show_thinking:
                print("💭 正在思考", end="", flush=True)
            
            # 获取可用工具
            available_tools = await self.get_available_tools()
            
            # 第一次调用 DeepSeek - 可能会请求调用工具
            response = self.client.chat.completions.create(
                model="deepseek-chat",
                messages=messages,
                tools=available_tools,
                tool_choice="auto",
                temperature=0.7
            )
            
            assistant_message = response.choices[0].message
            
            # 检查 AI 是否需要调用工具
            if assistant_message.tool_calls:
                import json
                
                if show_thinking:
                    print(".", end="", flush=True)
                
                # 添加助手的工具调用消息
                messages.append({
                    "role": "assistant",
                    "content": None,
                    "tool_calls": [{
                        "id": tc.id,
                        "type": "function",
                        "function": {
                            "name": tc.function.name,
                            "arguments": tc.function.arguments
                        }
                    } for tc in assistant_message.tool_calls]
                })
                
                # 执行所有工具调用
                for tool_call in assistant_message.tool_calls:
                    function_name = tool_call.function.name
                    function_args = json.loads(tool_call.function.arguments)
                    
                    if show_thinking:
                        print(".", end="", flush=True)
                    else:
                        print(f"🔧 调用工具: {function_name}({function_args})")
                    
                    # 调用 MCP 工具
                    tool_result = await self.call_tool(function_name, function_args)
                    
                    # 添加工具调用结果
                    messages.append({
                        "role": "tool",
                        "tool_call_id": tool_call.id,
                        "content": tool_result
                    })
                
                if show_thinking:
                    print(".", end="", flush=True)
                
                # 第二次调用 DeepSeek - 基于工具结果生成最终回复
                second_response = self.client.chat.completions.create(
                    model="deepseek-chat",
                    messages=messages,
                    temperature=0.7
                )
                
                final_message = second_response.choices[0].message.content
            else:
                # 不需要调用工具，直接返回
                final_message = assistant_message.content
            
            if show_thinking:
                print(" ✓\n", flush=True)
            
            # 更新对话历史
            self.conversation_history.append({"role": "user", "content": query})
            self.conversation_history.append({"role": "assistant", "content": final_message})
            
            # 限制对话历史长度，避免占用过多 token
            if len(self.conversation_history) > 20:
                self.conversation_history = self.conversation_history[-20:]
            
            return final_message
            
        except Exception as e:
            if show_thinking:
                print(" ✗\n", flush=True)
            return f"处理查询时出错: {str(e)}"
    
    async def chat_loop(self):
        """运行交互式聊天循环"""
        print("\n" + "=" * 70)
        print("🎮  sc2 你的scv！！！")
        print("=" * 70)
        print("\n你这家伙有什么事，我正忙着呢，要问我一些事？好吧，你是不是要问...")
        print("\n💡我们泰伦该如何战胜imba的神族")
        print("\n💡我们泰伦该如何战胜imba的虫族")
        print("\n💡我们的50块枪兵好兄弟的战斗力如何")
        print("\n💡如果问完了就输入quit吧，我还要忙着采矿呢！")
        print("💡 输入 '/reset' 重置对话历史")
        print("💡 输入 '/thinking' 切换思考过程显示\n")
        
        show_thinking = False

        while True:
            try:
                query = input("💬 你: ").strip()

                # 处理退出命令
                if query.lower() in ['quit', 'exit', 'q', '退出']:
                    print("\n👋 再见！GL HF (Good Luck, Have Fun)!\n")
                    break
                
                # 处理特殊命令
                if query == '/reset':
                    self.conversation_history = []
                    print("✅ 对话历史已重置\n")
                    continue
                
                if query == '/thinking':
                    show_thinking = not show_thinking
                    status = "开启" if show_thinking else "关闭"
                    print(f"✅ 思考过程显示已{status}\n")
                    continue

                # 跳过空输入
                if not query:
                    continue

                # 处理查询
                print()
                response = await self.process_query(query, show_thinking)
                print(f"🤖 AI: {response}\n")

            except KeyboardInterrupt:
                print("\n\n👋 再见！GL HF!\n")
                break
            except Exception as e:
                print(f"\n❌ 错误: {str(e)}\n")


    async def cleanup(self):
        await self.exit_stack.aclose()

async def main():
    """main"""
    default_server = "mcp_server.py"

    server_path = sys.argv[1] if len(sys.argv) > 1 else default_server

    client = MCPClient()
    try:
        await client.connect_to_server(server_path)
        # 运行交互式聊天
        await client.chat_loop()
    except Exception as e:
        print(f"\n❌ 程序错误: {e}")
        import traceback
        traceback.print_exc()
        
    finally:
        # 清理资源
        await client.cleanup()
        print("clean completed")
if __name__ == "__main__":
    try:
        asyncio.run(main())
    except KeyboardInterrupt:
        print("\n程序已退出")