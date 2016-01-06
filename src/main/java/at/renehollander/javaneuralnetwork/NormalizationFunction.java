package at.renehollander.javaneuralnetwork;

public abstract class NormalizationFunction {

    final double aMin;
    final double aMax;
    final double newAMin;
    final double newAMax;

    public NormalizationFunction(double aMin, double aMax, double newAMin, double newAMax) {
        this.aMin = aMin;
        this.aMax = aMax;
        this.newAMin = newAMin;
        this.newAMax = newAMax;
    }

    public abstract double normalize(double in);

    public abstract double denormalize(double in);

    public double[] normalize(double[] in) {
        for (int i = 0; i < in.length; i++) {
            in[i] = normalize(in[i]);
        }
        return in;
    }

    public double[] denormalize(double[] in) {
        for (int i = 0; i < in.length; i++) {
            in[i] = denormalize(in[i]);
        }
        return in;
    }

    public static class NormalizationFunction1 extends NormalizationFunction {

        public NormalizationFunction1(double aMin, double aMax, double newAMin, double newAMax) {
            super(aMin, aMax, newAMin, newAMax);
        }

        @Override
        public double normalize(double in) {
            return (in - this.aMin) * (newAMax - newAMin) / (aMax - aMin) + newAMin;
        }

        @Override
        public double denormalize(double in) {
            return (in - newAMin) * (aMax - aMin) / (newAMax - newAMin) + aMin;
        }
    }

}
