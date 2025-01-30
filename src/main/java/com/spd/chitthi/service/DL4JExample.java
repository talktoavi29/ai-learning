//package com.spd.chitthi.service;
//
//import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
//import org.deeplearning4j.nn.conf.layers.DenseLayer;
//import org.deeplearning4j.nn.conf.layers.OutputLayer;
//import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
//import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
//import org.nd4j.linalg.activations.Activation;
//import org.nd4j.linalg.api.ndarray.INDArray;
//import org.nd4j.linalg.dataset.DataSet;
//import org.nd4j.linalg.factory.Nd4j;
//import org.nd4j.linalg.lossfunctions.LossFunctions;
//
//public class DL4JExample {
//    public static void DL4JExampleMethod() {
//        // Configure the neural network
//        MultiLayerNetwork model = new MultiLayerNetwork(new NeuralNetConfiguration.Builder()
//                .seed(123)
//                .list()
//                .layer(0, new DenseLayer.Builder()
//                        .nIn(2)
//                        .nOut(3)
//                        .activation(Activation.RELU)
//                        .build())
//                .layer(1, new OutputLayer.Builder()
//                        .nIn(3)
//                        .nOut(1)
//                        .activation(Activation.SIGMOID)
//                        .lossFunction(LossFunctions.LossFunction.MSE)
//                        .build())
//                .build());
//        model.init();
//        model.setListeners(new ScoreIterationListener(10));
//
//        INDArray input = Nd4j.create(new double[][] {
//                {0, 0},
//                {0, 1},
//                {1, 0},
//                {1, 1}
//        });
//        INDArray labels = Nd4j.create(new double[][] {
//                {0}, {1}, {1}, {0}
//        });
//        DataSet dataSet = new DataSet(input, labels);
//
//        for (int i = 0; i < 1000; i++) {
//            model.fit(dataSet);
//        }
//
//        INDArray output = model.output(input);
//        System.out.println("Predictions:");
//        System.out.println(output);
//    }
//}
