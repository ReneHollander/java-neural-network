package at.renehollander.javaneuralnetwork;

import java.io.*;

public class TrainingData implements Closeable {

    private BufferedReader reader;

    private int[] topology;

    private String[] readAndSplit(String expectedToken, String exceptionText) throws IOException {
        expectedToken += ":";
        String line = reader.readLine();
        if (line == null) return null;
        if (!line.startsWith(expectedToken)) throw new IOException(exceptionText);
        return line.substring(expectedToken.length(), line.length()).trim().split(" ");
    }

    public TrainingData(File file) throws IOException {
        reader = new BufferedReader(new FileReader(file));

        String[] items = readAndSplit("topology", "data file has to start with topology");
        topology = new int[items.length];
        for (int i = 0; i < items.length; i++) {
            topology[i] = Integer.parseInt(items[i].trim());
        }
    }

    public int[] getTopology() {
        return topology;
    }

    private double[] getNextValues(String type) throws IOException {
        String[] items = readAndSplit(type, "expected " + type + " data");
        if (items == null) return null;
        double[] ret = new double[items.length];
        for (int i = 0; i < items.length; i++) {
            ret[i] = Double.parseDouble(items[i].trim());
        }
        return ret;
    }

    public double[] getNextInputValues() throws IOException {
        return getNextValues("in");
    }

    public double[] getNextOutputValues() throws IOException {
        return getNextValues("out");
    }

    @Override
    public void close() throws IOException {
        reader.close();
    }
}
