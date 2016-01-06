package at.renehollander.javaneuralnetwork;

import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.layout.mxGraphLayout;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import javax.swing.*;

//public class Visualization {
//
//    private NeuralNetwork neuralNetwork;
//
//    private JFrame frame;
//    private mxGraphComponent graphComponent;
//    private mxGraphLayout layout;
//
//    public Visualization(NeuralNetwork neuralNetwork) {
//        this.neuralNetwork = neuralNetwork;
//
//        frame = new JFrame();
//        frame.setTitle("Visualization");
//        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        frame.setAutoRequestFocus(false);
//        frame.toBack();
//        frame.setSize(800, 600);
//
//        graphComponent = new mxGraphComponent(createAndLayout());
//        graphComponent.setEnabled(false);
//        frame.getContentPane().add(graphComponent);
//
//        frame.setVisible(true);
//    }
//
//    public void update() {
//        if (graphComponent != null) {
//            SwingUtilities.invokeLater(() -> {
//                frame.setTitle(neuralNetwork.toString());
//                graphComponent.setGraph(createAndLayout());
//                graphComponent.refresh();
//            });
//        }
//    }
//
//    public void close() {
//        if (frame != null) {
//            frame.dispose();
//        }
//        graphComponent = null;
//        layout = null;
//        frame = null;
//    }
//
//    private void addNeuron(SimpleGraph<Neuron, DefaultEdge> graph, Neuron[] neurons) {
//        for (Neuron neuron : neurons) {
//            graph.addVertex(neuron);
//            for (int i = 0; i < neuron.getConnectedTo().length; i++) {
//                graph.addVertex(neuron.getConnectedTo()[i]);
//                graph.addEdge(neuron, neuron.getConnectedTo()[i]);
//            }
//        }
//    }
//
//    private JGraphXAdapter<Neuron, DefaultEdge> createAndLayout() {
//
//        SimpleGraph<Neuron, DefaultEdge> graph = new SimpleGraph<Neuron, DefaultEdge>(DefaultEdge.class);
//
//        addNeuron(graph, this.neuralNetwork.getInputLayer());
//        for (Neuron[] neurons : this.neuralNetwork.getHiddenLayers()) {
//            addNeuron(graph, neurons);
//        }
//        addNeuron(graph, this.neuralNetwork.getOutputLayer());
//
//        JGraphXAdapter<Neuron, DefaultEdge> jgxAdapter = new JGraphXAdapter<Neuron, DefaultEdge>(graph);
//        jgxAdapter.getStylesheet().getDefaultEdgeStyle().put(mxConstants.STYLE_NOLABEL, "1");
//        layout = new mxHierarchicalLayout(jgxAdapter);
//        layout.execute(jgxAdapter.getDefaultParent());
//        return jgxAdapter;
//    }
//
//}
