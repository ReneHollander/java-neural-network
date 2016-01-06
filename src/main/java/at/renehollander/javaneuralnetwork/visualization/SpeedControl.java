package at.renehollander.javaneuralnetwork.visualization;

import at.renehollander.javaneuralnetwork.visualization.guicomponent.GUIComponent;

import javax.swing.*;
import java.awt.*;

public class SpeedControl implements GUIComponent {

    private JSlider slider;

    public SpeedControl() {
        slider = new JSlider(0, 1000);
    }

    @Override
    public Component getComponent() {
        return slider;
    }

    public int getSpeed() {
        return slider.getValue();
    }

}
