import os
from openai import OpenAI

deepseek_api_key = os.getenv("DEEPSEEK_API_KEY")

# 初始化客户端
client = OpenAI(
    api_key= deepseek_api_key,  # 替换为您的实际API密钥
    base_url="https://api.deepseek.com/v1"
)

def chat_with_deepseek(message):
    try:
        response = client.chat.completions.create(
            model="deepseek-chat",
            messages=[
                {"role": "user", "content": message}
            ],
            stream=False
        )
        return response.choices[0].message.content
    except Exception as e:
        return f"错误: {str(e)}"

# 使用示例
if __name__ == "__main__":
    result = chat_with_deepseek("你好，请介绍一下你自己")
    print(result)