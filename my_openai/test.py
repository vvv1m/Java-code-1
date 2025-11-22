import os
from openai import OpenAI
from dotenv import load_dotenv
import google.generativeai as genai

load_dotenv()

gemini_api_key = os.getenv("GEMINI_API_KEY")
# 检查 API Key 是否加载成功
if not gemini_api_key:
    raise ValueError("未找到 GEMINI_API_KEY 环境变量！请检查 .env 文件")


# 初始化客户端
client = OpenAI(
    api_key=gemini_api_key,  # 替换为您的实际API密钥
    base_url="https://aistudio.google.com/prompts/new_chat"
)

def chat_with_deepseek(message):
    try:
        response = client.chat.completions.create(
            model="gemini-chat",
            messages=[
                {"role": "user", "content": message}
            ],
            stream=False
        )
        return response.choices[0].message.content
    except Exception as e:
        return f"错误: {str(e)}"
def chat_with_gemini(message):
    """
    使用 Google Gemini API 进行对话
    """
    try:
        # 创建模型实例
        model = genai.GenerativeModel('gemini-pro')
        
        # 发送消息并获取响应
        response = model.generate_content(message)
        
        return response.text
    
    except Exception as e:
        return f"错误: {str(e)}"

# 使用示例
if __name__ == "__main__":
    result = chat_with_gemini("你好，请介绍一下你自己")
    print(result)