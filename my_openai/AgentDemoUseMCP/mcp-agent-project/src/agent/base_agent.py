class BaseAgent:
    def __init__(self, name):
        self.name = name

    def initialize(self):
        raise NotImplementedError("Subclasses should implement this method.")

    def process_message(self, message):
        raise NotImplementedError("Subclasses should implement this method.")

    def shutdown(self):
        raise NotImplementedError("Subclasses should implement this method.")