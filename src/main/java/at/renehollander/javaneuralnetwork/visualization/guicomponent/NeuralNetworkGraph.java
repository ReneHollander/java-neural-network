package at.renehollander.javaneuralnetwork.visualization.guicomponent;

import at.renehollander.javaneuralnetwork.Layer;
import at.renehollander.javaneuralnetwork.NeuralNetwork;
import at.renehollander.javaneuralnetwork.Neuron;
import at.renehollander.javaneuralnetwork.visualization.NeuralNetworkGraphComponent;
import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

import javax.swing.*;
import java.awt.*;

public class NeuralNetworkGraph implements GUIComponent {

    private NeuralNetwork neuralNetwork;

    private mxGraph graph;
    private mxHierarchicalLayout layout;
    private mxGraphComponent graphComponent;

    public NeuralNetworkGraph(NeuralNetwork neuralNetwork) {
        this.neuralNetwork = neuralNetwork;

        graph = new mxGraph();
        graphComponent = new NeuralNetworkGraphComponent(graph);
        graphComponent.setEnabled(false);
        graphComponent.setFoldingEnabled(true);
        layout = new mxHierarchicalLayout(graph, SwingConstants.WEST);
        loadNeurons();
    }

    public void update() {
        graph.refresh();

//        if (graphComponent != null) {
//            SwingUtilities.invokeLater(() -> {
//                graphComponent.setGraph(createAndLayout());
//                graphComponent.refresh();
//            });
//        }
    }

    private void loadNeurons() {
        Object parent = graph.getDefaultParent();

        graph.getModel().beginUpdate();
        try {
            Layer[] layers = neuralNetwork.getLayers();
            Object[][] neurons = new Object[layers.length][];
            for (int i = 0; i < layers.length; i++) {
                Layer currLayer = layers[i];
                neurons[i] = new Object[currLayer.neurons.length];
                for (int j = 0; j < currLayer.neurons.length; j++) {
                    Neuron currNeuron = currLayer.neurons[j];
                    neurons[i][j] = graph.insertVertex(parent, null, currNeuron, 0, 0, 100, 100);
                }
            }

            for (int layerNum = 0; layerNum < neurons.length - 1; ++layerNum) {
                Object[] curr = neurons[layerNum];
                Object[] next = neurons[layerNum + 1];
                for (int n = 0; n < curr.length; ++n) {
                    Object neu = curr[n];
                    for (int m = 0; m < next.length; ++m) {
                        graph.insertEdge(parent, null, "", neu, next[m]);
                    }
                }
            }

        } finally {
            graph.getModel().endUpdate();
        }
        layout.execute(graph.getDefaultParent());
        layout.execute(graph.getDefaultParent());
        layout.execute(graph.getDefaultParent());
        layout.execute(graph.getDefaultParent());
        layout.execute(graph.getDefaultParent());
    }

    @Override
    public Component getComponent() {
        return graphComponent;
    }
}
