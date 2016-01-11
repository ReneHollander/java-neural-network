package at.renehollander.javaneuralnetwork.data;

import java.util.Iterator;

public class XORDataProvider implements Iterator<Data>, Iterable<Data> {

    private Data[] datas;

    public XORDataProvider() {
        datas = new Data[4];
        datas[0] = new Data(new double[]{0, 0}, new double[]{0});
        datas[1] = new Data(new double[]{0, 1}, new double[]{1});
        datas[2] = new Data(new double[]{1, 0}, new double[]{1});
        datas[3] = new Data(new double[]{1, 1}, new double[]{0});
    }

    @Override
    public boolean hasNext() {
        return true;
    }

    @Override
    public Data next() {
        return datas[(int) (Math.random() * datas.length)];
    }

    @Override
    public Iterator<Data> iterator() {
        return this;
    }
}
