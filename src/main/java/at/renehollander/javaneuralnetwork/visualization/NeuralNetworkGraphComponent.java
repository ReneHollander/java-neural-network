package at.renehollander.javaneuralnetwork.visualization;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.swing.view.mxInteractiveCanvas;
import com.mxgraph.view.mxGraph;

public class NeuralNetworkGraphComponent extends mxGraphComponent {

    public NeuralNetworkGraphComponent(mxGraph g) {
        super(g);
    }

    public mxInteractiveCanvas createCanvas() {
        return new NeuralNetworkCanvas(this);
    }
}