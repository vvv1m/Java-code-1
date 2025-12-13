from typing import Any
import httpx
from mcp.server.fastmcp import FastMCP
import tool

mcp = FastMCP("sc2 helper")
# ✅ 使用装饰器方式注册工具
@mcp.tool()
def get_counter_info(race: str) -> str:
    return tool.get_counter_info(race)

@mcp.tool()
def get_starcraft_unit_info(race: str) -> str:
    return tool.get_starcraft_unit_info(race)

@mcp.tool()
def get_unit_details(unit_name: str) -> str:
    return tool.get_unit_details(unit_name)

@mcp.tool()
def must_run_code() -> str:
    return tool.must_run_code()
# def main():
#     print("Welcome to a pzimba world")
#     mcp.run("stdio")
if __name__ == "__main__":
    mcp.run()
"""can running ---> no problem """