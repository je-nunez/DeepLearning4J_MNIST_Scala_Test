# DeepLearning4J_MNIST_Scala_Test

MNIST handwritten digits recognition using DeepLearning4J in Scala (taken from its Java original)

This is a simple Scala version of the MNIST classifiers in Java using the DeepLearning4J libraries.

Authoritative and original Multi-Layer-Perceptron for MNIST in Java/DeepLearning4J: 

[https://github.com/deeplearning4j/dl4j-examples/blob/master/dl4j-examples/src/main/java/org/deeplearning4j/examples/feedforward/mnist/MLPMnistTwoLayerExample.java](https://github.com/deeplearning4j/dl4j-examples/blob/master/dl4j-examples/src/main/java/org/deeplearning4j/examples/feedforward/mnist/MLPMnistTwoLayerExample.java)

Authoritative and original Convolutional Neural Network for MNIST in Java/DeepLearning4J: 

[https://github.com/deeplearning4j/dl4j-examples/blob/master/dl4j-examples/src/main/java/org/deeplearning4j/examples/convolution/LenetMnistExample.java](https://github.com/deeplearning4j/dl4j-examples/blob/master/dl4j-examples/src/main/java/org/deeplearning4j/examples/convolution/LenetMnistExample.java)

Note: The following users are credited as the collaborators on the original Java file for the CNN, `LenetMnistExample.java` (in comments inside Java file):

       LenetMnistExample.java:   Created by agibsonccc on 9/16/15.
       LenetMnistExample.java:   Modified by dmichelin on 12/10/2016 to add documentation

# WIP

This project is a *work in progress*. The implementation is *incomplete* and subject to change. The documentation can be inaccurate.

# First Results:

The training shows progress lines in the standard-output. And the end, some results of the MNIST classifier using a Multi-Layer-Perceptron in the [./mlp](./mlp) subdirectory:

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

Some results of the MNIST classifier using a Convolutional Neural Network in the [./cnn](./cnn) subdirectory:

        Examples labeled as 7 classified by model as 0: 1 times
        Examples labeled as 7 classified by model as 1: 2 times
        Examples labeled as 7 classified by model as 2: 8 times
        Examples labeled as 7 classified by model as 3: 1 times
        Examples labeled as 7 classified by model as 7: 1012 times
        Examples labeled as 7 classified by model as 8: 1 times
        Examples labeled as 7 classified by model as 9: 3 times
        Examples labeled as 8 classified by model as 0: 4 times
        Examples labeled as 8 classified by model as 2: 2 times
        Examples labeled as 8 classified by model as 3: 1 times
        Examples labeled as 8 classified by model as 4: 2 times
        Examples labeled as 8 classified by model as 6: 1 times
        Examples labeled as 8 classified by model as 7: 3 times
        Examples labeled as 8 classified by model as 8: 960 times
        Examples labeled as 8 classified by model as 9: 1 times
        Examples labeled as 9 classified by model as 0: 6 times
        Examples labeled as 9 classified by model as 1: 2 times
        Examples labeled as 9 classified by model as 3: 4 times
        Examples labeled as 9 classified by model as 4: 16 times
        Examples labeled as 9 classified by model as 5: 3 times
        Examples labeled as 9 classified by model as 7: 6 times
        Examples labeled as 9 classified by model as 8: 2 times
        Examples labeled as 9 classified by model as 9: 970 times
        
        ==========================Scores========================================
         Accuracy:  0.9836
         Precision: 0.9836
         Recall:    0.9836
         F1 Score:  0.9836

# Visualization of the progress of the Training of the Neural Network by DeepLearning4J

DeepLearning4J has a web UI integrated on top of an embedded Eclipse Jetty web server, which allows to visualize many things during the training. For example, the activations at:

       http://localhost:<jetty-port>/activations

(DeepLearning4J can automatically open this URL for you, so you don't need to know the actual `<jetty-port>` it is using or open this link in your web browser).

[visualization_of_the_progression_of_the_training_in_the_DeepLearning4J_Eclipse_Jetty_UI.png](extras/visualization_of_the_progression_of_the_training_in_the_DeepLearning4J_Eclipse_Jetty_UI.png)

In general, the visualization of the neural network training for DeepLearning4J is explained here: [https://deeplearning4j.org/visualization](https://deeplearning4j.org/visualization).

Other paths that DeepLearning4J defines:

        GET     /rl (org.deeplearning4j.ui.rl.RlDropwiz)
        GET     /rl/state (org.deeplearning4j.ui.rl.RlDropwiz)
        POST    /rl/state (org.deeplearning4j.ui.rl.RlDropwiz)
        GET     /flow (org.deeplearning4j.ui.flow.FlowDropwiz)
        GET     /flow/action/{id} (org.deeplearning4j.ui.flow.FlowDropwiz)
        GET     /flow/info (org.deeplearning4j.ui.flow.FlowDropwiz)
        GET     /flow/state (org.deeplearning4j.ui.flow.FlowDropwiz)
        POST    /flow/action/{id} (org.deeplearning4j.ui.flow.FlowDropwiz)
        POST    /flow/info (org.deeplearning4j.ui.flow.FlowDropwiz)
        POST    /flow/state (org.deeplearning4j.ui.flow.FlowDropwiz)
        GET     /word2vec (org.deeplearning4j.ui.nearestneighbors.word2vec.NearestNeighborsDropwiz)
        GET     /word2vec/{path} (org.deeplearning4j.ui.nearestneighbors.word2vec.NearestNeighborsDropwiz)
        POST    /word2vec/upload (org.deeplearning4j.ui.nearestneighbors.word2vec.NearestNeighborsDropwiz)
        POST    /word2vec/vocab (org.deeplearning4j.ui.nearestneighbors.word2vec.NearestNeighborsDropwiz)
        POST    /word2vec/words (org.deeplearning4j.ui.nearestneighbors.word2vec.NearestNeighborsDropwiz)
        GET     /api/coords (org.deeplearning4j.ui.api.ApiResource)
        GET     /api/{path} (org.deeplearning4j.ui.api.ApiResource)
        POST    /api/coords (org.deeplearning4j.ui.api.ApiResource)
        POST    /api/update (org.deeplearning4j.ui.api.ApiResource)
        POST    /api/upload (org.deeplearning4j.ui.api.ApiResource)
        GET     / (org.deeplearning4j.ui.defaults.DefaultDropwiz)
        GET     /events (org.deeplearning4j.ui.defaults.DefaultDropwiz)
        GET     /sessions (org.deeplearning4j.ui.defaults.DefaultDropwiz)
        GET     /whatsup (org.deeplearning4j.ui.defaults.DefaultDropwiz)
        GET     /activations (org.deeplearning4j.ui.activation.ActivationsDropwiz)
        GET     /activations/img (org.deeplearning4j.ui.activation.ActivationsDropwiz)
        POST    /activations/update (org.deeplearning4j.ui.activation.ActivationsDropwiz)
        GET     /filters (org.deeplearning4j.ui.renders.RendersDropwiz)
        GET     /filters/img (org.deeplearning4j.ui.renders.RendersDropwiz)
        POST    /filters/update (org.deeplearning4j.ui.renders.RendersDropwiz)
        GET     /weights (org.deeplearning4j.ui.weights.WeightDropwiz)
        GET     /weights/data (org.deeplearning4j.ui.weights.WeightDropwiz)
        GET     /weights/updated (org.deeplearning4j.ui.weights.WeightDropwiz)
        POST    /weights/update (org.deeplearning4j.ui.weights.WeightDropwiz)
        GET     /tsne (org.deeplearning4j.ui.tsne.TsneDropwiz)
        GET     /tsne/{path} (org.deeplearning4j.ui.tsne.TsneDropwiz)
        POST    /tsne/update (org.deeplearning4j.ui.tsne.TsneDropwiz)
        POST    /tsne/upload (org.deeplearning4j.ui.tsne.TsneDropwiz)
        POST    /tsne/vocab (org.deeplearning4j.ui.tsne.TsneDropwiz)

The last subset of paths, the t-SNE (`/tsne/` in the DeepLearning4J UI) is exposed in https://lvdmaaten.github.io/tsne/ and in 'Visualizing Data using t-SNE', by Laurens van der Maaten and Geoffrey Hinton ( [http://jmlr.org/papers/volume9/vandermaaten08a/vandermaaten08a.pdf](http://jmlr.org/papers/volume9/vandermaaten08a/vandermaaten08a.pdf) ). Example: `org.deeplearning4j.plot.Tsne` and `org.deeplearning4j.plot.BarnesHutTsne`.

