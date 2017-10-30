package mainapp

// A Scala version of the Convolutional Neural Network for MNIST written in Java using the
// DeepLearning4J library.
// Authoritative and original Java version:
//      https://github.com/deeplearning4j/dl4j-examples/blob/master/dl4j-examples/src/main/java/org/deeplearning4j/examples/convolution/LenetMnistExample.java
// The following users are credited as the collaborators on above file (in comments in file above):
//   LenetMnistExample.java:   Created by agibsonccc on 9/16/15.
//   LenetMnistExample.java:   Modified by dmichelin on 12/10/2016 to add documentation

import scala.collection.JavaConverters._

import org.deeplearning4j.datasets.iterator.impl.MnistDataSetIterator
import org.deeplearning4j.eval.Evaluation
import org.deeplearning4j.nn.api.OptimizationAlgorithm
import org.deeplearning4j.nn.conf.{MultiLayerConfiguration, NeuralNetConfiguration, Updater}
import org.deeplearning4j.nn.conf.inputs.InputType
import org.deeplearning4j.nn.conf.layers.{ConvolutionLayer, DenseLayer, OutputLayer, SubsamplingLayer}
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork
import org.deeplearning4j.nn.weights.WeightInit
import org.deeplearning4j.optimize.listeners.{ScoreIterationListener, PerformanceListener}
import org.deeplearning4j.ui.api.UIServer
import org.deeplearning4j.ui.weights.ConvolutionalIterationListener

import org.nd4j.linalg.activations.Activation
import org.nd4j.linalg.api.ndarray.INDArray
import org.nd4j.linalg.dataset.DataSet
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator
import org.nd4j.linalg.factory.Nd4j
import org.nd4j.linalg.learning.config.Nesterovs
import org.nd4j.linalg.lossfunctions.LossFunctions
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION

// import org.deeplearning4j.nn.conf.LearningRatePolicy

object LenetMnistExample {

  // True means we are in Development so we need to visualize the neural training, false Production
  val flagVisualizeDevelopmentOrProduction: Boolean = false

  def main(args: Array[String]): Unit = {
    val nChannels = 1          // Number of input channels
    val outputNum = 10         // number of possible outcomes (number of MNIST digits)
    val batchSize = 64         // Test batch size
    val nEpochs = 1            // Number of training epochs
    val iterations = 1         // Number of training iterations
    val seed = 123

    /* Avoid messages about caught exceptions and stack traces from the Reflections Java Runtime API, like:
     *
     *    18:21:01.823 [main] WARN  org.reflections.Reflections - could not create Vfs.Dir from url. ignoring the exception and continuing
     *    org.reflections.ReflectionsException: could not create Vfs.Dir from url, no matching UrlType was found [file:/System/Library/Java/Extensions/libJ3DAudio.jnilib]
     */
    ReflectionsHelper.registerUrlTypes()

    Nd4j.ENFORCE_NUMERICAL_STABILITY = true

    println("Loading MNIST data to training dataset and test dataset....")
    val mnistTrain = new MnistDataSetIterator(batchSize, true, 12345)
    val mnistTest = new MnistDataSetIterator(batchSize, false, 12345)

    println("Building CNN model....")
    val nnBuilder =
      new NeuralNetConfiguration.Builder()
        .seed(seed)
        .iterations(iterations)      // Training iterations as above
        .regularization(true).l2(0.00001)
        .learningRate(0.02).biasLearningRate(0.003)
             /*
                 Uncomment the following for learning decay and bias
              */
        // .learningRateDecayPolicy(LearningRatePolicy.Inverse).lrPolicyDecayRate(0.001).lrPolicyPower(0.75)
        .weightInit(WeightInit.XAVIER)
        .optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT)
        .updater(new Nesterovs(0.9))   // 0.9 is the momentum
        .list()
        .layer(0, new ConvolutionLayer.Builder(5, 5)
                    // nIn and nOut specify depth. nIn here is the nChannels and nOut is the number of filters to be applied
                    .nIn(nChannels)
                    .stride(1, 1)
                    .nOut(20)
                    // .activation("identity")
                    .activation(Activation.IDENTITY)
                    .build()
              )
        .layer(1, new SubsamplingLayer.Builder(SubsamplingLayer.PoolingType.PNORM)
                    .pnorm(2)
                    .kernelSize(2, 2)
                    .stride(2, 2)
                    .build()
              )
        .layer(2, new ConvolutionLayer.Builder(5, 5)
                    // Note that nIn need not be specified in later layers
                    .stride(1, 1)
                    .nOut(50)
                    // .activation("identity")
                    .activation(Activation.IDENTITY)
                    .build()
              )
        .layer(3, new SubsamplingLayer.Builder(SubsamplingLayer.PoolingType.MAX)
                    .kernelSize(2, 2)
                    .stride(2, 2)
                    .build()
              )
        .layer(4, new DenseLayer.Builder()
                    // .activation("relu")
                    .activation(Activation.RELU)
                    .nOut(500)
                    .build()
              )
        .layer(5, new OutputLayer.Builder(LossFunctions.LossFunction.NEGATIVELOGLIKELIHOOD)
                    .nOut(outputNum)
                    // .activation("softmax")
                    .activation(Activation.SOFTMAX)
                    .build()
              )
        .setInputType(InputType.convolutionalFlat(28, 28, 1))    // See note below
        .backprop(true).pretrain(false)

    /*
    Regarding the .setInputType(InputType.convolutionalFlat(28, 28, 1)) line: This does a few things.
    (a) It adds preprocessors, which handle things like the transition between the convolutional/subsampling layer
        and the dense layers
    (b) Does some additional configuration validation
    (c) Where necessary, sets the nIn (number of input neurons, or input depth in the case of CNNs) values for each
        layer based on the size of the previous layer (but it won't override values manually set by the user)

    In earlier versions of DL4J, the (now deprecated) ConvolutionLayerSetup class was used instead for this.
    InputTypes can be used with other layer types too (RNNs, MLPs etc) not just CNNs.
    For normal images (when using ImageRecordReader) use InputType.convolutional(height, width, depth).
    MNIST record reader is a special case, that outputs 28x28 pixel grayscale (nChannels=1) images, in a "flattened"
    row vector format (i.e., 1x784 vectors), hence the "convolutionalFlat" input type used here.
     */
    // The builder needs the dimensions of the image along with the number of channels. these are 28x28 images in one channel

    val nnConf = nnBuilder.build()
    val nnModel = new MultiLayerNetwork(nnConf)
    nnModel.init()


    println("Training CNN model....")

    // See interface:
    //      https://deeplearning4j.org/doc/org/deeplearning4j/optimize/api/IterationListener.html
    // for other possible iteration listeners below, e.g.,
    // org.deeplearning4j.optimize.listeners.ComposableIterationListener
    //
    // The original Java code has this instruction:
    //       nnModel.setListeners(new ScoreIterationListener(1))
    // You may have multiple listeners active at the same time on a same neural network:
    // https://deeplearning4j.org/doc/org/deeplearning4j/nn/multilayer/MultiLayerNetwork.html#setListeners-java.util.Collection-
    nnModel.setListeners(
      if (flagVisualizeDevelopmentOrProduction) {
        // this is a Development environment
        val uiServer = UIServer.getInstance
        println("Visualization of the training in Development at " +
                s"http://localhost:${uiServer.getPort}/activations")

        new ConvolutionalIterationListener(10, true)
      } else {
        // this is a Production environment, so the ConvolutionalIterationListener http plot slows
        // down the training and should not be necessary:
        // log the timing performance every 10 iterations, reporting as well the nnModel.score() at
        // the end of each group of 10-iterations:
        new PerformanceListener.Builder()
                                 .reportIteration(true)
                                 .reportTime(true)
                                 .reportSample(true)
                                 .reportBatch(false)
                                 .reportScore(true)
                                 .setFrequency(10)
                                 .build()
      }
    )

    for { epoch <- 0 until nEpochs } {
      nnModel.fit(mnistTrain)
      println(s"*** Completed epoch $epoch ***")

      println(s"Evaluating CNN model on training set after epoch $epoch ....")
      val eval = new Evaluation(outputNum)
      mnistTest.asScala foreach {
        mnistCharDataSet => {
          val output = nnModel.output(mnistCharDataSet.getFeatureMatrix, false)
          eval.eval(mnistCharDataSet.getLabels, output)
        }
      }
      println(eval.stats())
      mnistTest.reset()
    }

    println("****************Example finished********************")
    if (flagVisualizeDevelopmentOrProduction) {
      val uiServer = UIServer.getInstance
      println(s"Press Ctrl-C to stop server at http://localhost:${uiServer.getPort}/\n" +
              s"(E.g., checking http://localhost:${uiServer.getPort}/activations )")
    }
  }
}
