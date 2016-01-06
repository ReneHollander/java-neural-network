package at.renehollander.javaneuralnetwork.visualization;


import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.labels.XYToolTipGenerator;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYStepRenderer;
import org.jfree.chart.urls.StandardXYURLGenerator;
import org.jfree.chart.urls.XYURLGenerator;
import org.jfree.chart.util.ParamChecks;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import javax.swing.*;
import java.awt.*;

public class Visualization extends ApplicationFrame {

    private JPanel mainPanel;
    private JPanel topBarPanel;

    public Visualization() {
        super("Visualization");

        mainPanel = new JPanel(new GridLayout(2, 2));
        topBarPanel = new JPanel(new GridLayout(0, 3));
        topBarPanel.setPreferredSize(new Dimension(0, 50));
        topBarPanel.setMinimumSize(new Dimension(0, 50));
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.add(mainPanel, BorderLayout.SOUTH);
        contentPanel.add(topBarPanel, BorderLayout.CENTER);
        setContentPane(contentPanel);
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public JPanel getTopBarPanel() {
        return topBarPanel;
    }

    public void visualize() {
        this.pack();
        RefineryUtilities.centerFrameOnScreen(this);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public static JFreeChart createXYStepChart(String title, String xAxisLabel, String yAxisLabel, XYDataset dataset, PlotOrientation orientation, boolean legend, boolean tooltips, boolean urls) {
        ParamChecks.nullNotPermitted(orientation, "orientation");
        NumberAxis xAxis = new NumberAxis(xAxisLabel);
        NumberAxis yAxis = new NumberAxis(yAxisLabel);
        yAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        XYToolTipGenerator toolTipGenerator = null;
        if (tooltips) {
            toolTipGenerator = new StandardXYToolTipGenerator();
        }

        XYURLGenerator urlGenerator = null;
        if (urls) {
            urlGenerator = new StandardXYURLGenerator();
        }
        XYItemRenderer renderer = new XYStepRenderer(toolTipGenerator, urlGenerator);

        XYPlot plot = new XYPlot(dataset, xAxis, yAxis, null);
        plot.setRenderer(renderer);
        plot.setOrientation(orientation);
        plot.setDomainCrosshairVisible(false);
        plot.setRangeCrosshairVisible(false);
        JFreeChart chart = new JFreeChart(title, JFreeChart.DEFAULT_TITLE_FONT, plot, legend);
        ChartFactory.getChartTheme().apply(chart);
        return chart;

    }

}

