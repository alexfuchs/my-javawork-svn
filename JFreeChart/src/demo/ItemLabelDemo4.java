package demo;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.HorizontalAlignment;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RefineryUtilities;

public class ItemLabelDemo4 extends ApplicationFrame
{
  public ItemLabelDemo4(String paramString)
  {
    super(paramString);
    JPanel localJPanel = createDemoPanel();
    localJPanel.setPreferredSize(new Dimension(500, 270));
    setContentPane(localJPanel);
  }

  private static CategoryDataset createDataset()
  {
    DefaultCategoryDataset localDefaultCategoryDataset = new DefaultCategoryDataset();
    localDefaultCategoryDataset.addValue(212.0D, "Classes", "JDK 1.0");
    localDefaultCategoryDataset.addValue(504.0D, "Classes", "JDK 1.1");
    localDefaultCategoryDataset.addValue(1520.0D, "Classes", "JDK 1.2");
    localDefaultCategoryDataset.addValue(1842.0D, "Classes", "JDK 1.3");
    localDefaultCategoryDataset.addValue(2991.0D, "Classes", "JDK 1.4");
    return localDefaultCategoryDataset;
  }

  private static JFreeChart createChart(CategoryDataset paramCategoryDataset)
  {
    JFreeChart localJFreeChart = ChartFactory.createLineChart("Java Standard Class Library", "Release", "Class Count", paramCategoryDataset, PlotOrientation.VERTICAL, false, true, false);
    localJFreeChart.addSubtitle(new TextTitle("Number of Classes By Release"));
    TextTitle localTextTitle = new TextTitle("Source: Java In A Nutshell (4th Edition) by David Flanagan (O'Reilly)");
    localTextTitle.setFont(new Font("SansSerif", 0, 10));
    localTextTitle.setPosition(RectangleEdge.BOTTOM);
    localTextTitle.setHorizontalAlignment(HorizontalAlignment.RIGHT);
    localJFreeChart.addSubtitle(localTextTitle);
    ChartUtilities.applyCurrentTheme(localJFreeChart);
    CategoryPlot localCategoryPlot = (CategoryPlot)localJFreeChart.getPlot();
    NumberAxis localNumberAxis = (NumberAxis)localCategoryPlot.getRangeAxis();
    localNumberAxis.setUpperMargin(0.15D);
    localNumberAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
    LineAndShapeRenderer localLineAndShapeRenderer = (LineAndShapeRenderer)localCategoryPlot.getRenderer();
    localLineAndShapeRenderer.setBaseShapesVisible(true);
    localLineAndShapeRenderer.setDrawOutlines(true);
    localLineAndShapeRenderer.setUseFillPaint(true);
    localLineAndShapeRenderer.setBaseFillPaint(Color.white);
    localLineAndShapeRenderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
    localLineAndShapeRenderer.setBaseItemLabelsVisible(true);
    return localJFreeChart;
  }

  public static JPanel createDemoPanel()
  {
    JFreeChart localJFreeChart = createChart(createDataset());
    return new ChartPanel(localJFreeChart);
  }

  public static void main(String[] paramArrayOfString)
  {
    ItemLabelDemo4 localItemLabelDemo4 = new ItemLabelDemo4("JFreeChart: ItemLabelDemo4.java");
    localItemLabelDemo4.pack();
    RefineryUtilities.centerFrameOnScreen(localItemLabelDemo4);
    localItemLabelDemo4.setVisible(true);
  }
}