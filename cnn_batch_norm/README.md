# DeepLearning4J_MNIST_Scala_Test (Convolutional Neural Network)

MNIST handwritten digits recognition using a Convolutional Neural Network in DeepLearning4J in Scala (taken from its Java original)

Some hyper-parameter tuning on the original version. This subdirectory has a version with an extra layer, a batch normalization layer in the CNN for MNIST. The initializers and data samplers (seeds, etc) are random.

To run, use, with SBT (Simple Build Tool), do a:

        $ sbt run
          ...
          ==========================Scores========================================
           Accuracy:        0.9898
           Precision:       0.9898
           Recall:          0.9897
           F1 Score:        0.9897

