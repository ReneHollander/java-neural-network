package at.renehollander.javaneuralnetwork.visualization.guicomponent;

import at.renehollander.javaneuralnetwork.visualization.Visualization;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.awt.*;

public class InputValueGraph implements GUIComponent {

    private final int sampleCount;
    private XYSeries[] inputs;
    private JFreeChart chart;

    private NumberAxis domain;

    public InputValueGraph(int sampleCount, int numInputNodes) {
        this.sampleCount = sampleCount;
        XYSeriesCollection dataset = new XYSeriesCollection();
        inputs = new XYSeries[numInputNodes];
        for (int i = 0; i < inputs.length; i++) {
            inputs[i] = new XYSeries("Input " + i);
            dataset.addSeries(inputs[i]);
        }

        chart = Visualization.createXYStepChart(
                "Input Values",      // chart title
                "Passes",                      // x axis label
                "",                      // y axis label
                dataset,                  // data
                PlotOrientation.VERTICAL,
                true,                     // include legend
                true,                     // tooltips
                false                     // urls
        );

        XYPlot xyPlot = (XYPlot) chart.getPlot();
        xyPlot.setDomainCrosshairVisible(true);
        xyPlot.setRangeCrosshairVisible(true);
        domain = (NumberAxis) xyPlot.getDomainAxis();
        domain.setRange(0, 10);
        domain.setTickUnit(new NumberTickUnit(1));
        domain.setVerticalTickLabels(true);
        NumberAxis range = (NumberAxis) xyPlot.getRangeAxis();
        range.setRange(-0.5, (numInputNodes * 2) - 0.5);

        chart.setBackgroundPaint(Color.white);
    }

    public void addSample(int pass, double[] values) {
        for (int i = 0; i < values.length; i++) {
            XYSeries series = inputs[i];
            double value = values[i];
            series.add(pass, (i * 2) + value);
            domain.setRange(series.getItemCount() - sampleCount, series.getItemCount());
        }
    }

    @Override
    public Component getComponent() {
        return new ChartPanel(chart);
    }


}
