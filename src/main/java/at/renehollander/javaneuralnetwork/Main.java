package at.renehollander.javaneuralnetwork;

import at.renehollander.javaneuralnetwork.data.Data;
import at.renehollander.javaneuralnetwork.data.XORDataProvider;
import at.renehollander.javaneuralnetwork.util.OnlineNormalEstimator;
import at.renehollander.javaneuralnetwork.visualization.SpeedControl;
import at.renehollander.javaneuralnetwork.visualization.Visualization;
import at.renehollander.javaneuralnetwork.visualization.guicomponent.ErrorGraph;
import at.renehollander.javaneuralnetwork.visualization.guicomponent.InputValueGraph;
import at.renehollander.javaneuralnetwork.visualization.guicomponent.NetInformationPanel;
import at.renehollander.javaneuralnetwork.visualization.guicomponent.OutputValueGraph;

import java.io.IOException;

public class Main {

    public static void startWithGUI() throws InterruptedException {
        int[] topology = new int[]{2, 4, 1};
        XORDataProvider xorDataProvider = new XORDataProvider();
        NeuralNetwork net = new NeuralNetwork(topology);

        Visualization visualization = new Visualization();

        ErrorGraph errorGraph = new ErrorGraph();
        InputValueGraph inputValueGraph = new InputValueGraph(10, net.getLayers()[0].neurons.length);
        OutputValueGraph outputValueGraph = new OutputValueGraph(10, net.getLayers()[net.getLayers().length - 1].neurons.length);
        NetInformationPanel netInformationPanel = new NetInformationPanel(net);
        visualization.getMainPanel().add(inputValueGraph.getComponent());
        visualization.getMainPanel().add(errorGraph.getComponent());
        visualization.getMainPanel().add(outputValueGraph.getComponent());
        visualization.getMainPanel().add(netInformationPanel.getComponent());
        SpeedControl speedControl = new SpeedControl();
        visualization.getTopBarPanel().add(speedControl.getComponent());
        visualization.visualize();

        int trainingPass = 0;

        for (Data d : xorDataProvider) {
            ++trainingPass;

            long start = System.nanoTime();
            double[] results = net.process(d);
            long stop = System.nanoTime();

            inputValueGraph.addSample(trainingPass, d.getInput());
            outputValueGraph.addSample(trainingPass, results, d.getResult());
            errorGraph.addErrorSample(trainingPass, net.getError());
            errorGraph.addRecentAverageErrorSample(trainingPass, net.getRecentAverageError());
            netInformationPanel.update(trainingPass);

//            System.out.println("Pass " + trainingPass);
//            System.out.println("Inputs: " + Arrays.toString(d.getInput()));
//            System.out.println("Results: " + Arrays.toString(results));
//            System.out.println("Expected Results: " + Arrays.toString(d.getResult()));
//            System.out.println("Recent average error: " + net.getRecentAverageError());
//            System.out.println("Time to process: " + (stop - start) + "ns");
//            System.out.println();

            if (speedControl.getSpeed() != 0) {
                Thread.sleep(speedControl.getSpeed());
            }
        }
    }

    public static void startWithoutGUI() throws InterruptedException {
        int[] topology = new int[]{2, 4, 1};
        XORDataProvider xorDataProvider = new XORDataProvider();
        NeuralNetwork net = new NeuralNetwork(topology);

        int trainingPass = 0;

        OnlineNormalEstimator onlineNormalEstimator = new OnlineNormalEstimator();

        for (Data d : xorDataProvider) {
            ++trainingPass;

            long start = System.nanoTime();
            double[] results = net.process(d);
            long stop = System.nanoTime();

//            System.out.println("Pass " + trainingPass);
//            System.out.println("Inputs: " + Arrays.toString(d.getInput()));
//            System.out.println("Results: " + Arrays.toString(results));
//            System.out.println("Expected Results: " + Arrays.toString(d.getResult()));
//            System.out.println("Recent average error: " + net.getRecentAverageError());
            if (trainingPass > 1000000) {
                onlineNormalEstimator.handle(stop - start);

                if (onlineNormalEstimator.numSamples() % 1000000 == 0) {
                    System.out.println("Time Report:");
                    System.out.println("Passes:\t" + onlineNormalEstimator.numSamples());
                    System.out.println("Mean:\t" + onlineNormalEstimator.mean() + "ns");
                    System.out.println("Std:\t" + onlineNormalEstimator.standardDeviation() + "ns");
                    System.out.println("Var:\t" + onlineNormalEstimator.variance() + "ns");
                    System.out.println();
                    System.out.flush();
                }
            }

//            System.out.println();

        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        startWithGUI();
//        startWithoutGUI();
    }

}
