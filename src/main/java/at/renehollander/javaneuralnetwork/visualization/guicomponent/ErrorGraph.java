package at.renehollander.javaneuralnetwork.visualization.guicomponent;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.awt.*;

public class ErrorGraph implements GUIComponent {

    private XYSeries errorSeries;
    private JFreeChart chart;

    public ErrorGraph() {
        XYSeriesCollection dataset = new XYSeriesCollection();
        errorSeries = new XYSeries("Error");
        dataset.addSeries(errorSeries);

        chart = ChartFactory.createXYLineChart(
                "Error",      // chart title
                "Passes",                      // x axis label
                "Amount",                      // y axis label
                dataset,                  // data
                PlotOrientation.VERTICAL,
                true,                     // include legend
                true,                     // tooltips
                false                     // urls
        );

        chart.setBackgroundPaint(Color.white);
    }

    public XYSeries getSeries() {
        return this.errorSeries;
    }

    public void addSample(int pass, double error) {
        getSeries().add(pass, error);
    }

    @Override
    public Component getComponent() {
        return new ChartPanel(chart);
    }
}
