package at.renehollander.javaneuralnetwork;

import java.util.Arrays;

public class Layer {

    public final Neuron[] neurons;

    public Layer(int neuronCount) {
        this.neurons = new Neuron[neuronCount];
        for (int i = 0; i < neuronCount; i++) {
            this.neurons[i] = new Neuron();
        }
    }

    public double[] getValues() {
        double[] values = new double[neurons.length];
        for (int i = 0; i < neurons.length; i++) {
            values[i] = neurons[i].value;
        }
        return values;
    }

    @Override
    public String toString() {
        return "Layer{" +
                "neurons=" + Arrays.toString(neurons) +
                '}';
    }
}
