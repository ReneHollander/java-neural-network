package at.renehollander.javaneuralnetwork;

import java.util.Arrays;

public class Layer {
    public Neuron[] neurons;

    @Override
    public String toString() {
        return "Layer{" +
                "neurons=" + Arrays.toString(neurons) +
                '}';
    }
}
