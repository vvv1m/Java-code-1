class ToolRegistry:
    def __init__(self):
        self.tools = {}

    def register_tool(self, name, tool):
        if name in self.tools:
            raise ValueError(f"Tool '{name}' is already registered.")
        self.tools[name] = tool

    def get_tool(self, name):
        return self.tools.get(name, None)

    def list_tools(self):
        return list(self.tools.keys())