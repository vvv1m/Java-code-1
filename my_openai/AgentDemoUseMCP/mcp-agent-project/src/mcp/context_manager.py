class ContextManager:
    def __init__(self):
        self.context = {}

    def set_context(self, key, value):
        self.context[key] = value

    def get_context(self, key):
        return self.context.get(key, None)

    def clear_context(self):
        self.context.clear()

    def update_context(self, updates):
        self.context.update(updates)

    def get_all_context(self):
        return self.context.copy()