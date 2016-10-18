# DeepLearning4J_MNIST_Scala_Test

MNIST handwritten digits recognition using DeepLearning4J in Scala (taken from its Java original)

This is a simple Scala version of the MNIST classifiers in Java using the DeepLearning4J libraries.

Authoritative and original Multi-Layer-Perceptron for MNIST in Java/DeepLearning4J: [https://github.com/deeplearning4j/dl4j-examples/blob/master/dl4j-examples/src/main/java/org/deeplearning4j/examples/feedforward/mnist/MLPMnistTwoLayerExample.java](https://github.com/deeplearning4j/dl4j-examples/blob/master/dl4j-examples/src/main/java/org/deeplearning4j/examples/feedforward/mnist/MLPMnistTwoLayerExample.java)

# WIP

This project is a *work in progress*. The implementation is *incomplete* and subject to change. The documentation can be inaccurate.

# First Results:

The training shows progress lines in the standard-output. And the end, some results of the MNIST classifier using, e.g., a Multi-Layer-Perceptron in the [./mlp](./mlp) subdirectory:

        Examples labeled as 7 classified by model as 0: 2 times
        Examples labeled as 7 classified by model as 1: 4 times
        Examples labeled as 7 classified by model as 2: 5 times
        Examples labeled as 7 classified by model as 3: 2 times
        Examples labeled as 7 classified by model as 7: 1005 times
        Examples labeled as 7 classified by model as 8: 3 times
        Examples labeled as 7 classified by model as 9: 7 times
        Examples labeled as 8 classified by model as 0: 3 times
        Examples labeled as 8 classified by model as 1: 1 times
        Examples labeled as 8 classified by model as 2: 2 times
        Examples labeled as 8 classified by model as 3: 6 times
        Examples labeled as 8 classified by model as 4: 2 times
        Examples labeled as 8 classified by model as 5: 4 times
        Examples labeled as 8 classified by model as 6: 2 times
        Examples labeled as 8 classified by model as 7: 3 times
        Examples labeled as 8 classified by model as 8: 947 times
        Examples labeled as 8 classified by model as 9: 4 times
        Examples labeled as 9 classified by model as 0: 3 times
        Examples labeled as 9 classified by model as 1: 2 times
        Examples labeled as 9 classified by model as 3: 4 times
        Examples labeled as 9 classified by model as 4: 6 times
        Examples labeled as 9 classified by model as 5: 2 times
        Examples labeled as 9 classified by model as 6: 1 times
        Examples labeled as 9 classified by model as 7: 4 times
        Examples labeled as 9 classified by model as 8: 1 times
        Examples labeled as 9 classified by model as 9: 986 times
        
        ==========================Scores========================================
         Accuracy:  0.9816
         Precision: 0.9815
         Recall:    0.9815
         F1 Score:  0.9815

