package at.renehollander.javaneuralnetwork;

import at.renehollander.javaneuralnetwork.visualization.SpeedControl;
import at.renehollander.javaneuralnetwork.visualization.Visualization;
import at.renehollander.javaneuralnetwork.visualization.guicomponent.ErrorGraph;
import at.renehollander.javaneuralnetwork.visualization.guicomponent.InputValueGraph;
import at.renehollander.javaneuralnetwork.visualization.guicomponent.NeuralNetworkGraph;
import at.renehollander.javaneuralnetwork.visualization.guicomponent.OutputValueGraph;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        TrainingData trainData = new TrainingData(new File("./trainingData.txt"));
        NeuralNetwork net = new NeuralNetwork(trainData.getTopology());

        Visualization visualization = new Visualization();

        ErrorGraph errorGraph = new ErrorGraph();
        InputValueGraph inputValueGraph = new InputValueGraph(10, net.getLayers()[0].neurons.length);
        OutputValueGraph outputValueGraph = new OutputValueGraph(10, net.getLayers()[net.getLayers().length - 1].neurons.length);
        NeuralNetworkGraph neuralNetworkGraph = new NeuralNetworkGraph(net);
        visualization.getMainPanel().add(inputValueGraph.getComponent());
        visualization.getMainPanel().add(errorGraph.getComponent());
        visualization.getMainPanel().add(outputValueGraph.getComponent());
        visualization.getMainPanel().add(neuralNetworkGraph.getComponent());
        SpeedControl speedControl = new SpeedControl();
        visualization.getTopBarPanel().add(speedControl.getComponent());
        visualization.visualize();

        int trainingPass = 0;

        for (int i = 0; i < 2; i++) {
            trainData = new TrainingData(new File("./trainingData.txt"));
            while (true) {
                ++trainingPass;
                double[] inputValues = trainData.getNextInputValues();
                double[] outputValues = trainData.getNextOutputValues();
                if (inputValues == null || outputValues == null) break;

                inputValueGraph.addSample(trainingPass, inputValues);

                System.out.println("Pass " + trainingPass);
                System.out.println("Inputs: " + Arrays.toString(inputValues));

                net.feedForward(inputValues);
                double[] results = net.getResults();
                outputValueGraph.addSample(trainingPass, results, outputValues);
                net.backProp(outputValues);

                System.out.println("Results: " + Arrays.toString(results));
                System.out.println("Expected Results: " + Arrays.toString(outputValues));

                errorGraph.addErrorSample(trainingPass, net.getError());
                errorGraph.addRecentAverageErrorSample(trainingPass, net.getRecentAverageError());

                System.out.println("Recent average error: " + net.getRecentAverageError());
                System.out.println();

                neuralNetworkGraph.update();

                if (speedControl.getSpeed() != 0) {
                    Thread.sleep(speedControl.getSpeed());
                }
            }
        }

        System.out.println("Done");

    }

}
