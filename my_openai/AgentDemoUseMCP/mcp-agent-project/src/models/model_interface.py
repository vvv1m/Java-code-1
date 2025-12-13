class ModelInterface:
    def predict(self, input_data):
        raise NotImplementedError("This method should be overridden by subclasses.")

    def train(self, training_data):
        raise NotImplementedError("This method should be overridden by subclasses.")

    def evaluate(self, test_data):
        raise NotImplementedError("This method should be overridden by subclasses.")