package client.essp.common.gantt;

import java.awt.Rectangle;
import java.awt.Graphics;

public interface GanttRowRender {

    public static int LINK_SF = 1;
    public static int LINK_SS = 2;
    public static int LINK_FS = 3;
    public static int LINK_FF = 4;

    public static int LEAF = 128;
    public static int TASK = 256;
    public static int PROCESS = 512;
    public static int ACTUAL_TASK = 1024;
    public static int SELECTED = 2048;


    Rectangle getRowBounds(Rectangle rowBounds,int row, double processRate, int rowType);
    void paint( Graphics g, Rectangle outerBounds, int row, double processRate, int rowType );
    void paintLink( Graphics g, Rectangle srcBounds, Rectangle destBounds, int row, int rowType, int linkType);
}
