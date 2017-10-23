package mainapp

// A Scala version of the Multi-Layer-Perceptron for MNIST written in Java using the
// DeepLearning4J library.
// Authoritative and original Java version:
//      https://github.com/deeplearning4j/dl4j-examples/blob/master/dl4j-examples/src/main/java/org/deeplearning4j/examples/feedforward/mnist/MLPMnistTwoLayerExample.java


import org.nd4j.linalg.dataset.api.iterator.DataSetIterator
import org.deeplearning4j.datasets.iterator.impl.MnistDataSetIterator
import org.deeplearning4j.eval.Evaluation
import org.deeplearning4j.nn.api.OptimizationAlgorithm
import org.deeplearning4j.nn.conf.{MultiLayerConfiguration, NeuralNetConfiguration}
import org.deeplearning4j.nn.conf.layers.{DenseLayer, OutputLayer}
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork
import org.deeplearning4j.nn.weights.WeightInit
import org.deeplearning4j.optimize.listeners.ScoreIterationListener
import org.nd4j.linalg.api.ndarray.INDArray
import org.nd4j.linalg.dataset.DataSet
import org.nd4j.linalg.activations.Activation
import org.nd4j.linalg.learning.config.Nesterovs
import org.nd4j.linalg.lossfunctions.LossFunctions.LossFunction


object MLPMnistTwoLayerExample {

  def main(args: Array[String]): Unit = {

    val (numRows, numColumns) = (28, 28)     // input dimensions of an MNIST image sample
    val outputNum = 10                       // number of different digits to classify
    val batchSize = 64
    val rngSeed = 123
    val numEpochs = 15
    val rate = 0.0015                        // alpha

    /* Avoid messages about caught exceptions and stack traces from the Reflections Java Runtime API, like:
     * 
     *    18:21:01.823 [main] WARN  org.reflections.Reflections - could not create Vfs.Dir from url. ignoring the exception and continuing
     *    org.reflections.ReflectionsException: could not create Vfs.Dir from url, no matching UrlType was found [file:/System/Library/Java/Extensions/libJ3DAudio.jnilib]
     */
    ReflectionsHelper.registerUrlTypes()


    // Get the DataSetIterators for the training and the test datasets
    val mnistTrain = new MnistDataSetIterator(batchSize, true, rngSeed)
    val mnistTest = new MnistDataSetIterator(batchSize, false, rngSeed)

    println("Building neural-network model....")
    val nnConf =
      new NeuralNetConfiguration.Builder()
        .seed(rngSeed)
        .optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT)
        .iterations(1)
        // .activation("relu")
        .activation(Activation.RELU)
        .weightInit(WeightInit.XAVIER)
        .learningRate(rate)
        .updater(new Nesterovs(0.98))   // 0.98 is the momentum
        .regularization(true).l2(rate * 0.005)
        .list()
        .layer(0, new DenseLayer.Builder()                 // input neural layer
                       .nIn(numRows * numColumns)
                       .nOut(500)
                       .build()
              )
        .layer(1, new DenseLayer.Builder()                 // hidden neural layer
                       .nIn(500)
                       .nOut(100)
                       .build()
              )
        .layer(2, new OutputLayer.Builder(LossFunction.NEGATIVELOGLIKELIHOOD)
                       // .activation("softmax")
                       .activation(Activation.SOFTMAX)
                       .nIn(100)
                       .nOut(outputNum)
                       .build()
              )
        .pretrain(false).backprop(true)
        .build()

    val nnModel = new MultiLayerNetwork(nnConf)
    nnModel.init()
    nnModel.setListeners(new ScoreIterationListener(5))

    println("Training the NN model....")
    for { epoch <- 0 until numEpochs } {
        println(s"Starting fitting in epoch $epoch")
        nnModel.fit(mnistTrain)
    }


    println("Evaluating the NN model on a test data-set....")
    val eval = new Evaluation(outputNum)
    while (mnistTest.hasNext) {
        val nextDataSet = mnistTest.next
        val predictedOutput = nnModel.output(nextDataSet.getFeatureMatrix)
        eval.eval(nextDataSet.getLabels, predictedOutput)
    }

    println(eval.stats())
    println("****************Example finished********************")

  }

}
