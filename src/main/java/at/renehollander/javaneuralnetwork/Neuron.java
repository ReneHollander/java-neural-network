package at.renehollander.javaneuralnetwork;

import java.util.Arrays;

public class Neuron {

    public double[] gewicht;
    public double bias = 0;
    public double value = 0;
    public double error = 0;

    @Override
    public String toString() {
        return "Neuron{" +
                "gewicht=" + Arrays.toString(gewicht) +
                ", bias=" + bias +
                ", value=" + value +
                ", error=" + error +
                '}';
    }
}
