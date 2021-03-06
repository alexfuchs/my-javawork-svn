package demo;

import java.awt.BasicStroke;
import java.awt.Dimension;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYStepRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

public class XYStepRendererDemo1 extends ApplicationFrame
{
  public XYStepRendererDemo1(String paramString)
  {
    super(paramString);
    JPanel localJPanel = createDemoPanel();
    localJPanel.setPreferredSize(new Dimension(500, 300));
    setContentPane(localJPanel);
  }

  private static JFreeChart createChart(XYDataset paramXYDataset)
  {
    JFreeChart localJFreeChart = ChartFactory.createXYLineChart("XYStepRenderer Demo 1", "X", "Y", paramXYDataset, PlotOrientation.VERTICAL, true, true, false);
    XYPlot localXYPlot = (XYPlot)localJFreeChart.getPlot();
    localXYPlot.setDomainPannable(true);
    localXYPlot.setRangePannable(true);
    XYStepRenderer localXYStepRenderer = new XYStepRenderer();
    localXYStepRenderer.setBaseShapesVisible(true);
    localXYStepRenderer.setSeriesStroke(0, new BasicStroke(2.0F));
    localXYStepRenderer.setSeriesStroke(1, new BasicStroke(2.0F));
    localXYStepRenderer.setBaseToolTipGenerator(new StandardXYToolTipGenerator());
    localXYStepRenderer.setDefaultEntityRadius(6);
    localXYPlot.setRenderer(localXYStepRenderer);
    return localJFreeChart;
  }

  private static XYDataset createDataset()
  {
    XYSeries localXYSeries1 = new XYSeries("Series 1");
    localXYSeries1.add(1.0D, 3.0D);
    localXYSeries1.add(2.0D, 4.0D);
    localXYSeries1.add(3.0D, 2.0D);
    localXYSeries1.add(6.0D, 3.0D);
    XYSeries localXYSeries2 = new XYSeries("Series 2");
    localXYSeries2.add(1.0D, 7.0D);
    localXYSeries2.add(2.0D, 6.0D);
    localXYSeries2.add(3.0D, 9.0D);
    localXYSeries2.add(4.0D, 5.0D);
    localXYSeries2.add(6.0D, 4.0D);
    XYSeriesCollection localXYSeriesCollection = new XYSeriesCollection();
    localXYSeriesCollection.addSeries(localXYSeries1);
    localXYSeriesCollection.addSeries(localXYSeries2);
    return localXYSeriesCollection;
  }

  public static JPanel createDemoPanel()
  {
    JFreeChart localJFreeChart = createChart(createDataset());
    ChartPanel localChartPanel = new ChartPanel(localJFreeChart);
    localChartPanel.setMouseWheelEnabled(true);
    return localChartPanel;
  }

  public static void main(String[] paramArrayOfString)
  {
    XYStepRendererDemo1 localXYStepRendererDemo1 = new XYStepRendererDemo1("JFreeChart: XYStepRendererDemo1.java");
    localXYStepRendererDemo1.pack();
    RefineryUtilities.centerFrameOnScreen(localXYStepRendererDemo1);
    localXYStepRendererDemo1.setVisible(true);
  }
}