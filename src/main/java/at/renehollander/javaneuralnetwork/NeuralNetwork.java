package at.renehollander.javaneuralnetwork;

import java.util.Arrays;
import java.util.Random;

public class NeuralNetwork {

    private final ActivationFunction activationFunction;
    private final NormalizationFunction normalizationFunction;


    private Layer[] layers;

    public NeuralNetwork(ActivationFunction activationFunction, NormalizationFunction normalizationFunction, int... neurons) {
        this.activationFunction = activationFunction;
        this.normalizationFunction = normalizationFunction;

        layers = new Layer[neurons.length];
        for (int i = 0; i < layers.length; i++) {
            layers[i] = new Layer(neurons[i]);
        }

        for (int i = 1; i < layers.length; ++i) {
            Layer prev = layers[i - 1];
            Layer curr = layers[i];
            for (int j = 0; j < curr.neurons.length; ++j) {
                Neuron n = curr.neurons[j];
                n.gewicht = randomDoubleArray(prev.neurons.length, 0, 1);
            }
        }
    }

    public double[] compute(double[] input) {
        Layer layer0 = layers[0];
        for (int i = 0; i < layer0.neurons.length; i++) {
            layer0.neurons[i].value = this.normalizationFunction.normalize(input[i]);
        }

        for (int i = 1; i < layers.length; i++) {
            Layer prev = layers[i - 1];
            Layer curr = layers[i];
            for (Neuron n1 : curr.neurons) {
                double val = n1.bias;
                for (int j = 0; j < prev.neurons.length; j++) {
                    Neuron n2 = prev.neurons[j];
                    val += n2.value * n1.gewicht[j];
                }
                n1.value = activationFunction.apply(val);
            }
        }
        return Arrays.copyOf(layers[layers.length - 1].getValues(), layers[layers.length - 1].getValues().length);
    }

    public double[] learn(double learn_rate, double[] input, double[] results) {
        double[] computeres = this.normalizationFunction.denormalize(compute(input));

        for (int i = layers.length - 1; i >= 1; --i) {
            Layer prev = layers[i - 1];
            Layer curr = layers[i];
            if (i == layers.length - 1) {
                for (int j = 0; j < curr.neurons.length; ++j) {
                    Neuron n = curr.neurons[j];
                    n.error = n.value * (1 - n.value) * this.normalizationFunction.normalize(results[j]) - n.value;
                }
            } else {
                Layer next = layers[i + 1];
                for (int j = 0; j < curr.neurons.length; ++j) {
                    Neuron n = curr.neurons[j];
                    n.error = 0;
                    for (int k = 0; k < next.neurons.length; ++k) {
                        Neuron n2 = next.neurons[k];
                        n.error += n2.error * n2.gewicht[j];
                    }
                    n.error *= n.value * (1 - n.value);
                }
            }
            for (int j = 0; j < curr.neurons.length; ++j) {
                Neuron n = curr.neurons[j];
                n.bias += learn_rate * n.error;
                for (int k = 0; k < prev.neurons.length; ++k) {
                    Neuron n2 = prev.neurons[k];
                    n.gewicht[k] += learn_rate * n2.value * n.error;
                }
            }
        }

        return computeres;
    }

    private static final Random RANDOM = new Random();

    private static double[] randomDoubleArray(int count, double min, double max) {
        double[] ret = new double[count];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = min + (max - min) * RANDOM.nextDouble();
        }
        return ret;
    }

    @Override
    public String toString() {
        return "NeuralNetwork{" +
                "layers=" + Arrays.toString(layers) +
                '}';
    }
}
