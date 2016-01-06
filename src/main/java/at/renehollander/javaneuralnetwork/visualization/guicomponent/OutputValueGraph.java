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

public class OutputValueGraph implements GUIComponent {

    private final int sampleCount;
    private XYSeries[] results;
    private XYSeries[] expecteds;
    private JFreeChart chart;

    private NumberAxis domain;

    public OutputValueGraph(int sampleCount, int numInputNodes) {
        this.sampleCount = sampleCount;
        XYSeriesCollection dataset = new XYSeriesCollection();
        results = new XYSeries[numInputNodes];
        expecteds = new XYSeries[numInputNodes];
        for (int i = 0; i < numInputNodes; i++) {
            results[i] = new XYSeries("Result " + i);
            expecteds[i] = new XYSeries("Expected " + i);
            dataset.addSeries(results[i]);
            dataset.addSeries(expecteds[i]);
        }

        chart = Visualization.createXYStepChart(
                "Results and Expected Values",      // chart title
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
//        range.setTickUnit(new NumberTickUnit(1));

        chart.setBackgroundPaint(Color.white);
    }

    public void addSample(int pass, double[] results, double[] expecteds) {
        for (int i = 0; i < results.length; i++) {
            XYSeries resultsSeries = this.results[i];
            XYSeries expectedsSeries = this.expecteds[i];
            double value = results[i];
            double expected = expecteds[i];
            resultsSeries.add(pass, (i * 2) + value);
            expectedsSeries.add(pass, (i * 2) + expected);
            domain.setRange(resultsSeries.getItemCount() - sampleCount, resultsSeries.getItemCount());
        }
    }

    @Override
    public Component getComponent() {
        return new ChartPanel(chart);
    }

}
