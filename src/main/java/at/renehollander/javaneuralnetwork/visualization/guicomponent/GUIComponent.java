package at.renehollander.javaneuralnetwork.visualization.guicomponent;

import at.renehollander.javaneuralnetwork.visualization.Visualization;

import java.awt.*;

public interface GUIComponent {

    Component getComponent();


    default void initialize(Visualization visualization) {
    }

}
