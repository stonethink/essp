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
    private Font dateFont = new Font("SimSun", 0, 12); //��������
    private Font weekFont = new Font("SimSun", 0, 12); //��������
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
     * ��ͼ
     * @param g
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // ��ʲô�ã�
        Color oldClr = g.getColor(); // ��ʲô�ã�

        g.setColor(Color.black);

        Rectangle clip = g.getClipBounds();
        Point upperLeft = clip.getLocation();
        int xBegin = upperLeft.x;
        int xEnd = xBegin + clip.width; //�������� X

        //test
//        g.drawRect(xBegin+5,5, xEnd-xBegin-10, 100 );

        //ֻ����ǰ����ģ���ǰ�����x���xBegin��xEnd,y���0��ʼ
        drawLine(g, xBegin, xEnd);
        drawDate(g, xBegin, xEnd);

        g.setColor(oldClr);
    }

    /**
     * �����ڷָ��ߺ����ڷָ���
     * @param g
     * @param xBegin ͼ����ʼ������
     * @param xEnd ͼ�Ľ���������
     */
    private void drawLine(Graphics g, int xBegin, int xEnd) {
        Calendar cal = new java.util.GregorianCalendar(); //���ڼ������������

        //���ӿ���ʼ��ʼ��
        cal.setTimeInMillis(ganttUI.getTimeAtPoint(xBegin));
        //��ʱ������
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
            //��������һ���
            cal.set(cal.DATE, cal.get(cal.DATE) + 1);
        }
        //��һ��������
        g.drawLine(xBegin, 14, xEnd, 14);
    }

    /**
     * ������ʼ������ͽ��������������ַ����������ַ���
     * @param g
     * @param xBegin ͼ����ʼ������
     * @param timeEnd ͼ�Ľ���������
     */
    private void drawDate(Graphics g, int xBegin, int xEnd) {
        long timeBegin = ganttUI.getTimeAtPoint(xBegin);
        long timeEnd = ganttUI.getTimeAtPoint(xEnd);
        drawDate(g, timeBegin, timeEnd);
    }

    /**
     * ������ʼʱ��ͽ���ʱ�仭�����ַ����������ַ���
     * @param g
     * @param xBegin ͼ����ʼʱ��
     * @param timeEnd ͼ�Ľ���ʱ��
     */
    private void drawDate(Graphics g, long timeBegin, long timeEnd) {
        Calendar cal = new java.util.GregorianCalendar(); //���ڼ������������

        //���ӿ���ʼ��ʼ��
        cal.setTimeInMillis(timeBegin);
        //��ʱ������,����������
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
            //��������һ���
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
