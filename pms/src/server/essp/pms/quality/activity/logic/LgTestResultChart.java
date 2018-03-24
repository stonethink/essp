package server.essp.pms.quality.activity.logic;

import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.data.category.DefaultCategoryDataset;
import java.io.ByteArrayOutputStream;
import java.awt.Color;
import java.io.IOException;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import javax.imageio.ImageIO;
import org.jfree.chart.JFreeChart;
import java.awt.image.BufferedImage;
import c2s.essp.pms.quality.activity.DtoQualityActivity;
import com.sun.org.apache.xalan.internal.xsltc.dom.Axis;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.axis.DateAxis;
import org.jfree.ui.RectangleEdge;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.ui.LengthAdjustmentType;
import org.jfree.ui.RectangleAnchor;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.ui.TextAnchor;
import org.jfree.chart.title.LegendTitle;

public class LgTestResultChart implements Axis {
    public LgTestResultChart() {
    }

    public ByteArrayOutputStream getTestResultChart(DtoQualityActivity
        dtoActivityQuality) {
        Double planDensity = dtoActivityQuality.getPlanDensity();
        Double planDefectRate = dtoActivityQuality.getPlanDefectRate();
        Double actualDensity = dtoActivityQuality.getActualDensity();
        Double actualDefectRate = dtoActivityQuality.getActualDefectRate();
        if(planDensity == null) {
            planDensity = new Double(0);
        }
        if(planDefectRate == null) {
            planDefectRate = new Double(0);
        }
        if(actualDensity == null) {
            actualDensity = new Double(0);
        }
        if(actualDefectRate == null) {
            actualDefectRate = new Double(0);
        }




        XYDataset xydataset = createDataset(actualDensity, actualDefectRate,
                                            planDensity, planDefectRate);

//       JFreeChart chart = createDataset(xydataset);
        JFreeChart chart = ChartFactory.createScatterPlot("Test Result Chart",
            "Density", "Defect Rate", xydataset, PlotOrientation.VERTICAL, true, true, false);

//        LegendTitle legendtitle = (LegendTitle) chart.getSubtitle(0);
//
//        legendtitle.setPosition(RectangleEdge.RIGHT);
//
        XYPlot xyplot = chart.getXYPlot();
//
//        DateAxis dateaxis = new DateAxis("Density");
//
//        dateaxis.setUpperMargin(0.5D);
//
//        xyplot.setDomainAxis(dateaxis);
//
//        ValueAxis valueaxis = xyplot.getRangeAxis();
//
//        valueaxis.setUpperMargin(0.29999999999999999D);
//
//        valueaxis.setLowerMargin(0.5D);


//        ValueMarker valuemarker = new ValueMarker(actualDensity.doubleValue());
//
//        valuemarker.setLabelOffsetType(LengthAdjustmentType.EXPAND);
//
//        valuemarker.setPaint(Color.red);
//
//        valuemarker.setLabel("Actual Defect Rate");
//
//        valuemarker.setLabelAnchor(RectangleAnchor.BOTTOM_RIGHT);
//
//        valuemarker.setLabelTextAnchor(TextAnchor.TOP_RIGHT);
//
//        xyplot.addRangeMarker(valuemarker);


        ValueMarker valuemarker1 = new ValueMarker(planDefectRate.doubleValue());

        valuemarker1.setLabelOffsetType(LengthAdjustmentType.EXPAND);

        valuemarker1.setPaint(Color.blue);

        valuemarker1.setLabel("Plan Defect Rate");

        valuemarker1.setLabelAnchor(RectangleAnchor.TOP_RIGHT);

        valuemarker1.setLabelTextAnchor(TextAnchor.BOTTOM_RIGHT);

        xyplot.addRangeMarker(valuemarker1);

//
//        ValueMarker valuemarker2 = new ValueMarker(actualDensity.doubleValue());
//
//        valuemarker2.setPaint(Color.red);
//
//        valuemarker2.setLabel("Actual Density");
//
//        valuemarker2.setLabelAnchor(RectangleAnchor.TOP_LEFT);
//
//        valuemarker2.setLabelTextAnchor(TextAnchor.TOP_RIGHT);
//
//        xyplot.addDomainMarker(valuemarker2);


        ValueMarker valuemarker3 = new ValueMarker(planDensity.doubleValue());

        valuemarker3.setPaint(Color.blue);

        valuemarker3.setLabel("PlanDensity");

        valuemarker3.setLabelAnchor(RectangleAnchor.TOP_RIGHT);

        valuemarker3.setLabelTextAnchor(TextAnchor.TOP_LEFT);

        xyplot.addDomainMarker(valuemarker3);

//        DefaultCategoryDataset defaultcategorydataset = new DefaultCategoryDataset();
//
//
//            defaultcategorydataset.addValue(planDefectRate, "plan", planDensity);
//            defaultcategorydataset.addValue(actualDefectRate, "actual",
//                                            actualDensity);
//
//        JFreeChart chart = ChartFactory.createLineChart("Test Result Chart",
//            "DEnsity", "Defect Rate", defaultcategorydataset,
//            PlotOrientation.VERTICAL, true, true, false);
//        CategoryPlot categoryplot = (CategoryPlot) chart.getPlot();
//        categoryplot.setBackgroundPaint(Color.lightGray);
//        categoryplot.setRangeGridlinePaint(Color.white);
//        categoryplot.setRangeGridlinesVisible(true);
//        categoryplot.setDomainGridlinePaint(Color.white);
//
////        XYPlot xyplot = (XYPlot)chart.getPlot(); //获得 plot：XYPlot！！
////        xyplot.setDomainGridlinePaint(Color.white); //网格线颜色
////        xyplot.setRangeGridlinePaint(Color.white);
//        NumberAxis numberaxis = (NumberAxis) categoryplot.getRangeAxis();
//       numberaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
//
//
////

        BufferedImage image = chart.createBufferedImage(400, 270);
        ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "png", byteArrayOut);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return byteArrayOut;
    }

    private static XYDataset createDataset(Double aX, Double aY, Double pX,
                                           Double pY)

    {
        XYSeriesCollection xySeriesCollection = new XYSeriesCollection();
        xySeriesCollection.addSeries(createXYSeries(aX, aY));
        xySeriesCollection.addSeries(createXYSeriesP(pX, pY));
        return xySeriesCollection;
    }

    private static XYSeries createXYSeries(Double aX, Double aY) {
        XYSeries xySeries = new XYSeries("Actual", true);
        xySeries.add(aX.doubleValue(), aY.doubleValue());
        return xySeries;
    }

    private static XYSeries createXYSeriesP(Double pX, Double pY) {
        XYSeries xySeries = new XYSeries("Plan", true);
        xySeries.add(pX.doubleValue(), pY.doubleValue());
        return xySeries;
    }


}
