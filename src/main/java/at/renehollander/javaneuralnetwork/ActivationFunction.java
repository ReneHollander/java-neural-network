package at.renehollander.javaneuralnetwork;

@FunctionalInterface
public interface ActivationFunction {
    double apply(double in);

    class OneToThePowerOfTwo implements ActivationFunction {
        @Override
        public double apply(double in) {
            return 1.0 / (1 + Math.pow(Math.E, -1.0 * in));
        }
    }

}
