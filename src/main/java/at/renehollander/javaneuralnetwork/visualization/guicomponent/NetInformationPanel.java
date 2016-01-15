package at.renehollander.javaneuralnetwork.visualization.guicomponent;

import at.renehollander.javaneuralnetwork.NeuralNetwork;

import javax.swing.*;
import java.awt.*;

public class NetInformationPanel implements GUIComponent {

    private NeuralNetwork net;

    private JPanel panel;
    private JLabel recentAverageError = new JLabel();
    private JLabel lastError = new JLabel();
    private JLabel trainingPasses = new JLabel();

    public NetInformationPanel(NeuralNetwork net) {
        this.net = net;

        panel = new JPanel(new GridLayout(0, 2));

        panel.add(new JLabel("Recent Average Error"));
        panel.add(recentAverageError);

        panel.add(new JLabel("Last Error"));
        panel.add(lastError);

        panel.add(new JLabel("Training Passes"));
        panel.add(trainingPasses);
    }

    @Override
    public Component getComponent() {
        return panel;
    }

    public void update(int trainingPass) {
        recentAverageError.setText(String.valueOf(net.getRecentAverageError()));
        lastError.setText(String.valueOf(net.getError()));
        trainingPasses.setText(String.valueOf(trainingPass));
    }
}
