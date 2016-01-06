package at.renehollander.javaneuralnetwork;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws IOException {

        TrainingData trainData = new TrainingData(new File("./trainingData.txt"));

        NeuralNetwork net = new NeuralNetwork(trainData.getTopology());

        int trainingPass = 0;

        while (true) {
            ++trainingPass;
            double[] inputValues = trainData.getNextInputValues();
            double[] outputValues = trainData.getNextOutputValues();
            if (inputValues == null || outputValues == null) break;

            System.out.println("Pass " + trainingPass);
            System.out.println("Inputs: " + Arrays.toString(inputValues));

            net.feedForward(inputValues);
            double[] results = net.getResults();
            net.backProp(outputValues);

            System.out.println("Results: " + Arrays.toString(results));
            System.out.println("Expected Results: " + Arrays.toString(outputValues));

            System.out.println("Recent average error: " + net.getRecentAverageError());
            System.out.println();

        }

        System.out.println("Done");

    }

}
