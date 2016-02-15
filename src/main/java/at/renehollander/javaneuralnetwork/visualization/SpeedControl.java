package at.renehollander.javaneuralnetwork.visualization;

import at.renehollander.javaneuralnetwork.visualization.guicomponent.GUIComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class SpeedControl implements GUIComponent, KeyListener {

    private JSlider slider;
    private boolean paused = false;

    public SpeedControl() {
        slider = new JSlider(0, 1000);
    }

    @Override
    public Component getComponent() {
        return slider;
    }

    public int getSpeed() {
        return slider.getMaximum() - slider.getValue();
    }


    public boolean isPaused() {
        return this.paused;
    }

    @Override
    public void initialize(Visualization visualization) {
        visualization.addKeyListener(this);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_PLUS || e.getKeyCode() == KeyEvent.VK_ADD) {
            slider.setValue(slider.getValue() + 100);
        } else if (e.getKeyCode() == KeyEvent.VK_MINUS || e.getKeyCode() == KeyEvent.VK_SUBTRACT) {
            slider.setValue(slider.getValue() - 100);
        } else if (e.getKeyCode() == KeyEvent.VK_P) {
            this.paused ^= true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
