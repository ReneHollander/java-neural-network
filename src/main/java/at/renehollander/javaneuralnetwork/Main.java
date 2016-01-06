package at.renehollander.javaneuralnetwork;

import java.text.DecimalFormat;

public class Main {

    private static final double LEARN_RATE = 0.009;

    public static void main(String[] args) {
        NeuralNetwork neuralNetwork = new NeuralNetwork(new ActivationFunction.OneToThePowerOfTwo(), new NormalizationFunction.NormalizationFunction1(0.0, 1.0, 0.0, 1.0), 2, 2, 1);
        DecimalFormat df = new DecimalFormat("##.#####");
        while (true) {
            System.out.println("0^0=" + df.format(neuralNetwork.learn(LEARN_RATE, new double[]{0, 0}, new double[]{0})[0]));
            System.out.println("1^0=" + df.format(neuralNetwork.learn(LEARN_RATE, new double[]{1, 0}, new double[]{1})[0]));
            System.out.println("0^1=" + df.format(neuralNetwork.learn(LEARN_RATE, new double[]{0, 1}, new double[]{1})[0]));
            System.out.println("1^1=" + df.format(neuralNetwork.learn(LEARN_RATE, new double[]{1, 1}, new double[]{0})[0]));
            System.out.println(neuralNetwork);
        }
    }

}
