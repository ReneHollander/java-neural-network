package at.renehollander.javaneuralnetwork;

import java.util.Arrays;

public class NeuralNetwork {

    private static double recentAverageSmoothingFactor = 100.0;

    private Layer[] layers;
    private double error;
    private double recentAverageError;

    public NeuralNetwork(int... topology) {

        error = 0;
        recentAverageError = 0;

        layers = new Layer[topology.length];
        for (int layerNum = 0; layerNum < topology.length; ++layerNum) {
            Layer layer = new Layer();
            layers[layerNum] = layer;
            int numOutputs = layerNum == topology.length - 1 ? 0 : topology[layerNum + 1];

            // We have a new layer, now fill it with neurons, and
            // add a bias neuron in each layer.
            layer.neurons = new Neuron[topology[layerNum]];
            for (int neuronNum = 0; neuronNum < topology[layerNum]; ++neuronNum) {
                layer.neurons[neuronNum] = new Neuron(numOutputs, neuronNum);
            }

            // Force the bias node's output to 1.0 (it was the last neuron pushed in this layer):
            layer.neurons[layer.neurons.length - 1].setOutputValue(1);
        }
    }

    public void feedForward(double[] inputVals) {
        Layer first = layers[0];
        if (inputVals.length != first.neurons.length)
            throw new IllegalArgumentException("Wrong amount of inputVals. Expected " + first.neurons.length);

        // Assign (latch) the input values into the input neurons
        for (int i = 0; i < inputVals.length; ++i) {
            first.neurons[i].setOutputValue(inputVals[i]);
        }

        // forward propagate
        for (int layerNum = 1; layerNum < layers.length; ++layerNum) {
            Layer prev = layers[layerNum - 1];
            Layer curr = layers[layerNum];
            for (int n = 0; n < curr.neurons.length; ++n) {
                curr.neurons[n].feedForward(prev);
            }
        }
    }

    public void backProp(double[] targetVals) {
// Calculate overall net error (RMS of output neuron errors)

        Layer outputLayer = layers[layers.length - 1];
        error = 0.0;

        for (int n = 0; n < outputLayer.neurons.length; ++n) {
            double delta = targetVals[n] - outputLayer.neurons[n].getOutputValue();
            error += delta * delta;
        }
        error /= outputLayer.neurons.length; // get average error squared
        error = Math.sqrt(error); // RMS

        // Implement a recent average measurement

        recentAverageError = (recentAverageError * recentAverageSmoothingFactor + error) / (recentAverageSmoothingFactor + 1.0);

        // Calculate output layer gradients

        for (int n = 0; n < outputLayer.neurons.length; ++n) {
            outputLayer.neurons[n].calcOutputGradients(targetVals[n]);
        }

        // Calculate hidden layer gradients
        for (int layerNum = layers.length - 2; layerNum > 0; --layerNum) {
            Layer hiddenLayer = layers[layerNum];
            Layer nextLayer = layers[layerNum + 1];

            for (int n = 0; n < hiddenLayer.neurons.length; ++n) {
                hiddenLayer.neurons[n].calcHiddenGradients(nextLayer);
            }
        }

        // For all layers from outputs to first hidden layer,
        // update connection weights

        for (int layerNum = layers.length - 1; layerNum > 0; --layerNum) {
            Layer layer = layers[layerNum];
            Layer prevLayer = layers[layerNum - 1];

            for (int n = 0; n < layer.neurons.length; ++n) {
                layer.neurons[n].updateInputWeights(prevLayer);
            }
        }
    }

    public Layer[] getLayers() {
        return layers;
    }

    public double[] getResults() {
        Layer last = layers[layers.length - 1];
        double[] ret = new double[last.neurons.length];
        for (int n = 0; n < last.neurons.length; ++n) {
            ret[n] = last.neurons[n].getOutputValue();
        }
        return ret;
    }

    public double getRecentAverageError() {
        return recentAverageError;
    }

    @Override
    public String toString() {
        return "NeuralNetwork{" +
                "layers=" + Arrays.toString(layers) +
                ", error=" + error +
                ", recentAverageError=" + recentAverageError +
                '}';
    }
}
