import os
from pathlib import Path
from dotenv import load_dotenv

# 加载环境变量
env_path = Path(__file__).parent.parent.parent / '.env'
if env_path.exists():
    load_dotenv(env_path)

class Settings:
    def __init__(self):
        self.api_key = os.getenv("API_KEY", "your_default_api_key")
        self.gemini_api_key = os.getenv("GEMINI_API_KEY", "")
        self.deepseek_api_key = os.getenv("DEEPSEEK_API_KEY", "")
        self.environment = os.getenv("ENVIRONMENT", "development")
        self.debug_mode = os.getenv("DEBUG_MODE", "True").lower() in ("true", "1", "t")
        self.database_url = os.getenv("DATABASE_URL", "sqlite:///default.db")
        self.log_level = os.getenv("LOG_LEVEL", "INFO")

    def __repr__(self):
        return (f"Settings(environment={self.environment}, "
                f"debug_mode={self.debug_mode}, "
                f"log_level={self.log_level})")

    def validate(self):
        """验证配置"""
        if not self.gemini_api_key and not self.deepseek_api_key:
            raise ValueError("At least one API key must be set")
        return True

def load_settings() -> Settings:
    """加载配置"""
    settings = Settings()
    settings.validate()
    return settings

settings = Settings()