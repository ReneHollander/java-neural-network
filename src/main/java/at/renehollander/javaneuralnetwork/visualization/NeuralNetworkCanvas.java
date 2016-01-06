package at.renehollander.javaneuralnetwork.visualization;

import at.renehollander.javaneuralnetwork.Neuron;
import com.mxgraph.model.mxCell;
import com.mxgraph.shape.mxIShape;
import com.mxgraph.swing.view.mxInteractiveCanvas;
import com.mxgraph.view.mxCellState;

import java.util.Map;

public class NeuralNetworkCanvas extends mxInteractiveCanvas {
    public NeuralNetworkCanvas(NeuralNetworkGraphComponent myGraphComponent) {
        super(myGraphComponent);
    }

    public Object drawCell(mxCellState state) {

        mxCell cell = (mxCell) state.getCell();

        Map<String, Object> style = state.getStyle();
        mxIShape shape = getShape(style);

        if (g != null && shape != null) {
            shape.paintShape(this, state);

            if (cell.isVertex() && cell.getValue() instanceof Neuron) {
                Neuron neuron = (Neuron) cell.getValue();
                state.setLabel(neuron.getOutputValue() + "");
            }
        }

        return shape;
    }
}
