package net.sourceforge.ganttproject.chart;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import net.sourceforge.ganttproject.ChartComponentBase.AbstractChartImplementation;

public class RenderedResourceChartImage extends RenderedChartImage {

    private AbstractChartImplementation myChartImplementation;

    public RenderedResourceChartImage(ChartModelBase chartModel,
            AbstractChartImplementation chartImplementation, BufferedImage resourceTreeImage, int chartWidth, int chartHeight) {
        super(chartModel, resourceTreeImage, chartWidth, chartHeight);
        System.err
                .println("[RenderedResourceChartImage] RenderedResourceChartImage(): BufferedImage would take "+chartWidth*chartHeight*3/1024+"M");
        myChartImplementation = chartImplementation;
        // TODO Auto-generated constructor stub
    }

    protected void paintChart(Graphics g) {
        myChartImplementation.paintComponent(g);
    }

}
