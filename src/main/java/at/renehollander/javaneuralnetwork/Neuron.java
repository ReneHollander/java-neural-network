package at.renehollander.javaneuralnetwork;

import java.util.Arrays;

import static java.lang.Math.tanh;

public class Neuron {

    public static double eta = 0.15;
    public static double alpha = 0.5;

    private int index;

    private Connection[] outputWeights;
    private double outputValue = 0;
    private double gradient = 0;

    public Neuron(int numOutputs, int index) {
        this.index = index;
        outputWeights = new Connection[numOutputs];
        for (int c = 0; c < numOutputs; ++c) {
            Connection conn = new Connection();
            conn.weight = Math.random();
            outputWeights[c] = conn;
        }
    }

    public double getOutputValue() {
        return outputValue;
    }

    public void setOutputValue(double outputValue) {
        this.outputValue = outputValue;
    }

    public void feedForward(Layer prevLayer) {
        double sum = 0.0;
        // Sum the previous layer's outputs (which are our inputs)
        // Include the bias node from the previous layer.
        for (int n = 0; n < prevLayer.neurons.length; ++n) {
            sum += prevLayer.neurons[n].getOutputValue() * prevLayer.neurons[n].outputWeights[index].weight;
        }
        outputValue = transferFunction(sum);
    }

    public void calcOutputGradients(double targetVal) {
        double delta = targetVal - outputValue;
        gradient = delta * transferFunctionDerivative(outputValue);
    }

    public void calcHiddenGradients(Layer nextLayer) {
        double dow = sumDOW(nextLayer);
        gradient = dow * transferFunctionDerivative(outputValue);
    }

    public void updateInputWeights(Layer prevLayer) {
        // The weights to be updated are in the Connection container
        // in the neurons in the preceding layer

        for (int n = 0; n < prevLayer.neurons.length; ++n) {
            Neuron neuron = prevLayer.neurons[n];
            double oldDeltaWeight = neuron.outputWeights[index].deltaWeight;

            double newDeltaWeight =
                    // Individual input, magnified by the gradient and train rate:
                    eta
                            * neuron.getOutputValue()
                            * gradient
                            // Also add momentum = a fraction of the previous delta weight;
                            + alpha
                            * oldDeltaWeight;

            neuron.outputWeights[index].deltaWeight = newDeltaWeight;
            neuron.outputWeights[index].weight += newDeltaWeight;
        }
    }

    private double sumDOW(Layer nextLayer) {
        double sum = 0.0;
        // Sum our contributions of the errors at the nodes we feed.
        for (int n = 0; n < nextLayer.neurons.length; ++n) {
            sum += outputWeights[n].weight * nextLayer.neurons[n].gradient;
        }
        return sum;
    }

    public static double transferFunction(double x) {
        // tanh - output range [-1.0..1.0]
        return tanh(x);
    }

    public static double transferFunctionDerivative(double x) {
        // tanh derivative
        return 1.0 - x * x;
    }

    @Override
    public String toString() {
        return "Neuron{" +
                "index=" + index +
                ", outputWeights=" + Arrays.toString(outputWeights) +
                ", outputValue=" + outputValue +
                ", gradient=" + gradient +
                '}';
    }
}
