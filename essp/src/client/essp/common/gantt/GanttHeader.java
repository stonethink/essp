package client.essp.common.gantt;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Calendar;
import java.util.Locale;

import javax.swing.JComponent;
import javax.swing.JPanel;
import com.wits.util.TestPanel;

public class GanttHeader extends JComponent{
    GanttUI ganttUI;
    int height;

    int DEFAULT_HEIGHT = 30;
    private Font dateFont = new Font("SimSun", 0, 12); //日期字体
    private Font weekFont = new Font("SimSun", 0, 12); //星期字体
    private Color lineColor = Color.gray;
    private Color fontColor = Color.black;
    private java.text.DateFormat dateFormate = new java.text.SimpleDateFormat(
            "yyyy-MM-dd",
            Locale.US);
    private java.text.DateFormat weekFormate = new java.text.SimpleDateFormat(
            "E",
            Locale.US);


    public GanttHeader(GanttUI ganttUI ) {
        this.ganttUI = ganttUI;
        this.height = DEFAULT_HEIGHT;
    }

    public void setHeight(int height){
        this.height=height;
    }

    public int getHeight(){
        return this.height;
    }

    public Dimension getPreferredSize(){
        Dimension dim = super.getPreferredSize();
        if(dim == null ){
            dim = new Dimension();
        }
        dim.width = getMaxWidth();
        dim.height = height;
        return dim;
    }

    private int getMaxWidth(){
        return ganttUI.getPointOfTime(ganttUI.getMaxTime())
                -  ganttUI.getPointOfTime(ganttUI.getMinTime());

    }


    /**
     * 作图
     * @param g
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // 有什么用？
        Color oldClr = g.getColor(); // 有什么用？

        g.setColor(Color.black);

        Rectangle clip = g.getClipBounds();
        Point upperLeft = clip.getLocation();
        int xBegin = upperLeft.x;
        int xEnd = xBegin + clip.width; //画结束点 X

        //test
//        g.drawRect(xBegin+5,5, xEnd-xBegin-10, 100 );

        //只画当前画面的，当前画面的x轴从xBegin到xEnd,y轴从0开始
        drawLine(g, xBegin, xEnd);
        drawDate(g, xBegin, xEnd);

        g.setColor(oldClr);
    }

    /**
     * 画日期分隔线和星期分隔线
     * @param g
     * @param xBegin 图的起始横坐标
     * @param xEnd 图的结束横坐标
     */
    private void drawLine(Graphics g, int xBegin, int xEnd) {
        Calendar cal = new java.util.GregorianCalendar(); //用于计算的日历对象

        //从视口起始开始画
        cal.setTimeInMillis(ganttUI.getTimeAtPoint(xBegin));
        //将时间清零
        cal.set(cal.HOUR_OF_DAY, 0);
        cal.set(cal.MINUTE, 0);
        cal.set(cal.SECOND, 0);

        g.setColor(this.lineColor);

        g.drawLine(xBegin, 0, xEnd, 0);
        g.drawLine(xBegin, 29, xEnd, 29);
        while (cal.getTimeInMillis() <= ganttUI.getTimeAtPoint(xEnd)) {
            int xPos = ganttUI.getPointOfTime(cal.getTimeInMillis());
            if (cal.get(cal.DAY_OF_WEEK) == cal.SUNDAY) {
                g.drawLine(xPos, 0, xPos, 28);
            } else {
                g.drawLine(xPos, 14, xPos, 28);
            }
            //继续画下一天的
            cal.set(cal.DATE, cal.get(cal.DATE) + 1);
        }
        //画一条长横线
        g.drawLine(xBegin, 14, xEnd, 14);
    }

    /**
     * 根据起始横坐标和结束横坐标日期字符串和星期字符串
     * @param g
     * @param xBegin 图的起始横坐标
     * @param timeEnd 图的结束横坐标
     */
    private void drawDate(Graphics g, int xBegin, int xEnd) {
        long timeBegin = ganttUI.getTimeAtPoint(xBegin);
        long timeEnd = ganttUI.getTimeAtPoint(xEnd);
        drawDate(g, timeBegin, timeEnd);
    }

    /**
     * 根据起始时间和结束时间画日期字符串和星期字符串
     * @param g
     * @param xBegin 图的起始时间
     * @param timeEnd 图的结束时间
     */
    private void drawDate(Graphics g, long timeBegin, long timeEnd) {
        Calendar cal = new java.util.GregorianCalendar(); //用于计算的日历对象

        //从视口起始开始画
        cal.setTimeInMillis(timeBegin);
        //将时间清零,仅保留日期
        cal.set(cal.HOUR_OF_DAY, 0);
        cal.set(cal.MINUTE, 0);
        cal.set(cal.SECOND, 0);

        g.setColor(fontColor);

        while (cal.getTimeInMillis() <= timeEnd) {
            int xPos = ganttUI.getPointOfTime(cal.getTimeInMillis());
            if (cal.get(cal.DAY_OF_WEEK) == cal.SUNDAY) {
                g.setFont(dateFont);
                g.drawString(dateFormate.format(cal.getTime()), xPos + 2, 12);
            }
            g.setFont(dateFont);
            g.drawString(weekFormate.format(cal.getTime()).substring(0, 2),
                         xPos + 2,
                         26);
            //继续画下一天的
            cal.set(cal.DATE, cal.get(cal.DATE) + 1);
        }
    }

    public static void main(String args[]){
        JPanel p = new JPanel();
        p.setLayout(null);

        JPanel pc = new JPanel(null);
        pc.setBounds( 10,10,600,400 );
        p.add(pc);

        Calendar begin = Calendar.getInstance();
        begin.set(2005,8,1);
        Calendar end = Calendar.getInstance();
        end.set(2005,8,31);

        DefaultCanttUI ui = new DefaultCanttUI(begin.getTimeInMillis()
                                               , end.getTimeInMillis());
        GanttHeader header = new GanttHeader(ui);
        javax.swing.JScrollPane scrollPane = new javax.swing.JScrollPane(header);
        scrollPane.setBounds( 10,10,550,350 );
        header.setBounds( 0,0,540,340 );
        header.setBorder( javax.swing.BorderFactory.createLineBorder(java.awt.Color.red));
        pc.add(scrollPane);

        TestPanel.show(p);
    }

    }
