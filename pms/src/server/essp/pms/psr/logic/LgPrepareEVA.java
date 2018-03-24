package server.essp.pms.psr.logic;

import java.util.List;
import java.util.ArrayList;
import db.essp.pms.PmsStatusReportHistory;
import server.essp.pms.psr.bean.EVASheetBean;
import com.wits.util.comDate;
import java.io.ByteArrayOutputStream;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.axis.NumberAxis;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.util.ShapeUtilities;
import java.awt.Color;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.CategoryAxis;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class LgPrepareEVA {
    public List getEVADataList(List allPSRData) {
        List result = new ArrayList();
        for (int i = 0; i < allPSRData.size(); i++) {
            PmsStatusReportHistory h = (PmsStatusReportHistory) allPSRData.get(
                i);
            EVASheetBean bean = new EVASheetBean();
            String period = comDate.dateToString(h.getStartdate()) + "~~" +
                            comDate.dateToString(h.getFinishdate());
            bean.setPeriod(period);
            bean.setPv(h.getPv());
            /**
             * EV = BAC(预算) * 完工比例
             */
            double ev = h.getCompleterate().doubleValue() / 100 *
                        h.getBudget().doubleValue();
            bean.setEv(new Double(ev));
            /**
             * AC = Actual Cost，实际花费的成本
             */
            bean.setAc(h.getAc());
            bean.setBac(h.getBudget());
            /**
             * CPI(Cost Performance Index,成本绩效指数) = EV / AC
             */
            double cpi = bean.getEv().doubleValue() / bean.getAc().doubleValue();
            bean.setCpi(new Double(cpi));
            /**
             * SPI(进度绩效指数) = EV / PV
             */
            double spi = bean.getEv().doubleValue() / bean.getPv().doubleValue();
            bean.setSpi(new Double(spi));
            /**
             * EAC(Estimate at completion,完工估算) = AC + (BAC - EV)/CPI
             */
            double eac = bean.getAc().doubleValue() +
                         (bean.getBac().doubleValue() -
                          bean.getEv().doubleValue()) /
                         bean.getCpi().doubleValue();
            bean.setEac(new Double(eac));
            double planRate = bean.getPv().doubleValue() /
                              bean.getBac().doubleValue();
            bean.setPlanRate(new Double(planRate));
            double actualRate = h.getCompleterate().doubleValue() / 100;
            bean.setActualRate(new Double(actualRate));
            result.add(bean);
        }
        return result;
    }

    public ByteArrayOutputStream getLineChartForEVA(List allEVAList) {
        DefaultCategoryDataset defaultcategorydataset = new
            DefaultCategoryDataset();
        for (int i = 0; i < allEVAList.size(); i++) {
            EVASheetBean bean = (EVASheetBean) allEVAList.get(i);
            String period = bean.getPeriod();
            defaultcategorydataset.addValue(bean.getPv(), "PV", period);
            defaultcategorydataset.addValue(bean.getEv(), "EV", period);
            defaultcategorydataset.addValue(bean.getAc(), "AC", period);
            defaultcategorydataset.addValue(bean.getBac(), "BAC", period);
            defaultcategorydataset.addValue(bean.getEac(), "EAC", period);
        }

        JFreeChart chart = ChartFactory.createLineChart("EVA",
            "Period", "ph", defaultcategorydataset,
            PlotOrientation.VERTICAL, true, true, false);
        CategoryPlot categoryplot = (CategoryPlot) chart.getPlot();
        categoryplot.setBackgroundPaint(Color.lightGray);
        categoryplot.setRangeGridlinePaint(Color.white);

        NumberAxis numberaxis = (NumberAxis) categoryplot.getRangeAxis();
        numberaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        LineAndShapeRenderer lineandshaperenderer = (LineAndShapeRenderer)
            categoryplot.getRenderer();
        lineandshaperenderer.setSeriesShapesVisible(0, true);
        lineandshaperenderer.setSeriesShapesVisible(1, true);
        lineandshaperenderer.setSeriesShapesVisible(2, true);
        lineandshaperenderer.setSeriesShapesVisible(3, true);
        lineandshaperenderer.setSeriesShapesVisible(4, true);
//        lineandshaperenderer.setSeriesLinesVisible(2, false);//设置线条不可见
        lineandshaperenderer.setSeriesShape(4, ShapeUtilities.createDiamond(4F));
        lineandshaperenderer.setDrawOutlines(true);
        lineandshaperenderer.setUseFillPaint(true);
        lineandshaperenderer.setFillPaint(Color.white);
        CategoryAxis categoryaxis = categoryplot.getDomainAxis();
        categoryaxis.setCategoryLabelPositions(CategoryLabelPositions.
                                               createUpRotationLabelPositions(
            0.52359877559829882D));

        BufferedImage image = chart.createBufferedImage(1000, 500);
        ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "png", byteArrayOut);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return byteArrayOut;
    }

    public ByteArrayOutputStream getLineChartForPerformance(List allEVAList) {
        DefaultCategoryDataset defaultcategorydataset = new
            DefaultCategoryDataset();
        for (int i = 0; i < allEVAList.size(); i++) {
            EVASheetBean bean = (EVASheetBean) allEVAList.get(i);
            String period = bean.getPeriod();
            defaultcategorydataset.addValue(bean.getSpi(), "SPI", period);
            defaultcategorydataset.addValue(bean.getCpi(), "CPI", period);
        }

        JFreeChart chart = ChartFactory.createLineChart("Performance Index",
            "Period", "Index", defaultcategorydataset, PlotOrientation.VERTICAL, true, true, false);
        CategoryPlot categoryplot = (CategoryPlot) chart.getPlot();
        categoryplot.setBackgroundPaint(Color.lightGray);
        categoryplot.setRangeGridlinePaint(Color.white);

//        NumberAxis numberaxis = (NumberAxis) categoryplot.getRangeAxis();
//        numberaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        LineAndShapeRenderer lineandshaperenderer = (LineAndShapeRenderer)
            categoryplot.getRenderer();
        lineandshaperenderer.setSeriesShapesVisible(0, true);
        lineandshaperenderer.setSeriesShapesVisible(1, true);
        lineandshaperenderer.setSeriesShape(2, ShapeUtilities.createDiamond(4F));
        lineandshaperenderer.setDrawOutlines(true);
        lineandshaperenderer.setUseFillPaint(true);
        lineandshaperenderer.setFillPaint(Color.white);
        CategoryAxis categoryaxis = categoryplot.getDomainAxis();
        categoryaxis.setCategoryLabelPositions(CategoryLabelPositions.
                                               createUpRotationLabelPositions(
            0.52359877559829882D));

        BufferedImage image = chart.createBufferedImage(1000, 450);
        ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "png", byteArrayOut);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return byteArrayOut;
    }

}
