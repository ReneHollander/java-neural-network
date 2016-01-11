package at.renehollander.javaneuralnetwork.data;

import java.util.Arrays;
import java.util.Objects;

public class Data {

    private double[] input;
    private double[] result;

    public Data(double[] input, double[] result) {
        this.input = Objects.requireNonNull(input);
        this.result = Objects.requireNonNull(result);
    }

    public Data(double[] input) {
        this.input = Objects.requireNonNull(input);
        this.result = null;
    }

    public double[] getInput() {
        return input;
    }

    public double[] getResult() {
        return result;
    }

    public boolean hasTrainingData() {
        return result != null;
    }

    @Override
    public String toString() {
        return "Data{" +
                "input=" + Arrays.toString(input) +
                ", result=" + Arrays.toString(result) +
                '}';
    }
}
