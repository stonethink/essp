package client.essp.common.gantt;

import javax.swing.JComponent;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.AlphaComposite;
import java.awt.Rectangle;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.geom.Rectangle2D;
import java.awt.Paint;
import java.awt.Polygon;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.TexturePaint;
import java.awt.Dimension;
import java.util.Date;
import java.awt.Point;
import java.util.Calendar;
import javax.swing.event.MouseInputAdapter;
import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import client.image.ComImage;
import c2s.essp.common.gantt.IGanttDto;
import client.framework.common.Global;

public class GanttGraphic extends JComponent {
    int DEFAULT_ROW_HEIGHT = 16;


    //�����Ƿ�Ӧ�ý���Щ��Ϣ����һ��������config����
    //���������ߵ���ɫ
    private Color lineColor = Color.gray;

    //��������bar����ɫ
    private Color mutiLineColor = new Color(220, 220, 220);

    public static final long ADAY_MSEL = 3600 * 24 * 1000; //һ��ĺ�����

    GanttModel dataModel;
    GanttRowRender rowRender;
    GanttUI ganttUI;
    int rowHeight;

    List ganttListenerList = new ArrayList();
    MouseHelper mouseHelper ;

    public GanttGraphic(GanttModel model, GanttUI ganttUI) {
        setModel(model);
        setGanttUI(ganttUI);
        setRowRender(new DefaultGanttRowRender());
        rowHeight = DEFAULT_ROW_HEIGHT;

        mouseHelper = new MouseHelper();
        addMouseMotionListener(mouseHelper);
        addMouseListener(mouseHelper);
    }

    /***************** �����ǻ��Ʒ���***********************************/
    public void paintComponent(Graphics g) {
        //Super paint component
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        //�����������ߺ�bar
        paintWeekLine(g2d);


        //���ð�ͼ�뱳���Ľ������
        int rule = AlphaComposite.SRC_OVER;
        float alpha = 0.75f;
        g2d.setComposite(AlphaComposite.getInstance(rule, alpha));

        //row
        for (int row = 0; row < dataModel.getRowCount(); row++) {
            int x0 = ganttUI.getPointOfTime(dataModel.getRowBeginTime(row));
            int x1 = ganttUI.getPointOfTime(dataModel.getRowEndTime(row));
            Rectangle outerBounds = new Rectangle(x0, rowHeight * row, x1 - x0,
                                                  rowHeight);

            int rowType = dataModel.getRowStyle(row);
            double processRate = dataModel.getProcessRate(row);

            rowRender.paint(g, outerBounds, row, processRate, rowType);

            //�����ӹ�ϵ
            Rectangle srcBounds = rowRender.getRowBounds(outerBounds, row,
                    processRate, rowType);
            for (int j = 0; j < dataModel.getLinkCount(row); j++) {
                int linkRow = dataModel.getLinkRow(row, j);
                if (linkRow < 0 || linkRow >= dataModel.getRowCount()) {
                    continue;
                }

                int linkType = dataModel.getLinkType(row, j);
                double linkProcessRate = dataModel.getProcessRate(linkRow);

                int linkX0 = ganttUI.getPointOfTime(dataModel.getRowBeginTime(
                        linkRow));
                int linkX1 = ganttUI.getPointOfTime(dataModel.getRowEndTime(
                        linkRow));
                if( linkX0<0 || linkX1 <0 ){
                    continue;
                }
                Rectangle linkOuterBounds = new Rectangle(linkX0,
                        rowHeight * linkRow, linkX1 - linkX0,
                        rowHeight);
                Rectangle destBounds = rowRender.getRowBounds(linkOuterBounds, linkRow,
                    linkProcessRate, dataModel.getRowStyle(linkRow));

                rowRender.paintLink(g, srcBounds, destBounds, row, rowType,
                                    linkType);
            }
        }
    }


    /**
     * �����������ߺ�bar
     * @param g
     */
    private void paintWeekLine(Graphics2D g2d) {
        Calendar cal = Calendar.getInstance();

        //ȡ��ǰ���޼����εĴ�С
        Rectangle clip = g2d.getClipBounds();
        Point upperLeft = clip.getLocation();

        //ȷ����ʼ���������ĺ�����
        int xBegin = upperLeft.x;
        int xEnd = xBegin + clip.width; //�������� X

        long timeEnd = ganttUI.getTimeAtPoint(xEnd);

        //���ӿ���ʼ��ʼ��
        cal.setTimeInMillis(ganttUI.getTimeAtPoint(xBegin));
        //��ʱ������
        cal.set(cal.HOUR_OF_DAY, 0);
        cal.set(cal.MINUTE, 0);
        cal.set(cal.SECOND, 0);

//        long todayTime = new Date().getTime();
        long todayTime = Global.todayDate.getTime();
        while (cal.getTimeInMillis() <= timeEnd) {
            int xPos = ganttUI.getPointOfTime(cal.getTimeInMillis());
            int dayOfWeek = cal.get(cal.DAY_OF_WEEK);
            if (dayOfWeek == cal.SUNDAY || dayOfWeek == cal.SATURDAY) {

                g2d.setColor(mutiLineColor);
                //Ҫ������һ�쿪ʼ�������Լ� 1
                g2d.fillRect(xPos, upperLeft.y, ganttUI.getOneDayDots() + 1,
                             clip.height);
            }

            boolean isToday = todayTime - cal.getTimeInMillis() > 0
                && todayTime - cal.getTimeInMillis() < ADAY_MSEL;
            if (dayOfWeek == cal.SUNDAY || isToday) {
                g2d.setColor(this.lineColor);
                g2d.drawLine(xPos, upperLeft.y, xPos,
                             upperLeft.y + clip.height);
            }
            //��������һ���
            cal.set(cal.DATE, cal.get(cal.DATE) + 1);
        }
    }

    public Dimension getPreferredSize() {
        Dimension dim = new Dimension();

        dim.width = ganttUI.getPointOfTime(ganttUI.getMaxTime())
                    - ganttUI.getPointOfTime(ganttUI.getMinTime());
        dim.height = dataModel.getRowCount() * rowHeight;

        return dim;
    }

    public void setModel(GanttModel dataModel) {
        this.dataModel = dataModel;
    }

    public void setGanttUI(GanttUI ganttUI) {
        this.ganttUI = ganttUI;
    }

    public void setRowRender(GanttRowRender rowRender) {
        this.rowRender = rowRender;
    }

    public void setHeaderRender(GanttUI ganttUI) {
        this.ganttUI = ganttUI;
    }

    public void setRowHeight(int rowHeight){
        this.rowHeight = rowHeight;
    }

    public int getRowHeight(){
        return this.rowHeight;
    }

    public int getSelectedRow(){
        return mouseHelper.selectedRow;
    }

    public void addGanttListener(GanttListener l){
        this.ganttListenerList.add(l);
    }

    public void fireGanttChanged(int eventType){
        for( int i = 0 ; i < this.ganttListenerList.size(); i++ ){
            GanttListener l = (GanttListener)this.ganttListenerList.get(i);
            l.ganttChanged(eventType);
        }
    }

    public void removeGanttListener(GanttListener l){
        this.ganttListenerList.remove(l);
    }



    public static boolean isType(int rowType, int equalType) {
        if ((rowType & equalType) != 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * ȡĳһ����������
     * @param p
     * @return
     */
    protected int getRowAtPoint(Point p) {
        return p.y / this.getRowHeight();
    }


    //�ж�һ�����Ƿ���һ�������λ��
    protected int detectPosition(Point p) {
        int row = getRowAtPoint(p);

        int x0 = ganttUI.getPointOfTime(dataModel.getRowBeginTime(row));
        int x1 = ganttUI.getPointOfTime(dataModel.getRowEndTime(row));
        Rectangle outerBounds = new Rectangle(x0, rowHeight * row, x1 - x0,
                                              rowHeight);

        int rowType = dataModel.getRowStyle(row);
        double processRate = dataModel.getProcessRate(row);



        Rectangle rowBar = rowRender.getRowBounds(outerBounds, row,
                                                  processRate,rowType );

        if( rowBar.contains(p) ){
            return 1;
        }else{
            return 0;
        }
    }


    class MouseHelper extends MouseInputAdapter {
        int selectedRow = -1;

        public void mouseMoved(MouseEvent e) {
            if( detectPosition( e.getPoint() ) == 1 ){
                //���ù����״
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }else{
                setCursor(Cursor.getDefaultCursor());
            }
        }

        public void mouseClicked(MouseEvent e) {
            //if( detectPosition( e.getPoint() ) == 1 ){
                int sRow = getRowAtPoint(e.getPoint());
                if (selectedRow != sRow) {
                    selectedRow = sRow;
                    fireGanttChanged(GanttListener.ROW_SELECTED);
                }

                setCursor(Cursor.getDefaultCursor());
           // }
        }

    }

    class DefaultGanttRowRender implements GanttRowRender {
        //��ͷָ��ķ���
        public static final int ARROW_UP = 1;
        public static final int ARROW_RIGHT = 2;
        public static final int ARROW_DOWN = 3;
        public static final int ARROW_LEFT = 4;

        Paint defFillPaint = null;

        public DefaultGanttRowRender() {
            try {
                BufferedImage bufImg = ImageIO.read(ComImage.class.getResource("fill.gif"));
                Rectangle2D anchor = new Rectangle2D.Double(0, 0,
                        bufImg.getWidth(), bufImg.getHeight());
                defFillPaint = new TexturePaint(bufImg, anchor);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        public Rectangle getRowBounds(Rectangle outerBounds, int row,
                                      double processRate, int rowType) {
            if (GanttGraphic.isType(rowType, IGanttDto.NONE_STYLE) == true) {
                return new Rectangle();
            }

            if (GanttGraphic.isType(rowType, IGanttDto.TASK) == true) {
                if (GanttGraphic.isType(rowType, IGanttDto.LEAF) == true) {
                    return getTaskLeafRowBounds(outerBounds, row, rowType);
                } else {
                    return getTaskNoLeafRowBounds(outerBounds, row, rowType);
                }
            } else if (GanttGraphic.isType(rowType, IGanttDto.ACTUAL_TASK) == true) {
                return getActualTaskRowBounds(outerBounds, row, rowType);
            } else if (GanttGraphic.isType(rowType, IGanttDto.PROCESS) == true) {
                return getProcessRowBounds(outerBounds, row, processRate, rowType);
            } else {
                return new Rectangle(0,0,0,0);
            }
        }

        public void paint(Graphics g, Rectangle outerBounds, int row,
                          double processRate, int rowType) {
            if (GanttGraphic.isType(rowType, IGanttDto.NONE_STYLE) == true) {
                return ;
            }

            Graphics2D g2d = (Graphics2D) g;
            if (GanttGraphic.isType(rowType, IGanttDto.TASK) == true) {
                if (GanttGraphic.isType(rowType, IGanttDto.LEAF) == true) {
                    paintTaskLeaf(g2d, outerBounds, row, rowType);
                } else {
                    paintTaskNoLeaf(g2d, outerBounds, row, rowType);
                }
            }

            if (GanttGraphic.isType(rowType, IGanttDto.ACTUAL_TASK) == true) {
                paintActualTask(g2d, outerBounds, row, rowType);
            }

            if (GanttGraphic.isType(rowType, IGanttDto.PROCESS) == true) {
                paintProcess(g2d, outerBounds, row, processRate, rowType);
            }
        }

        public void paintLink(Graphics g, Rectangle srcBounds,
                              Rectangle destBounds, int row, int rowType,
                              int linkType) {
            Graphics2D g2d = (Graphics2D) g;
            if (GanttGraphic.isType(rowType, IGanttDto.LEAF)) {
                g2d.setColor(Color.BLUE);
            } else {
                g2d.setColor(Color.BLACK);
            }

            int x0;
            int y0 = srcBounds.y + srcBounds.height / 2;
            int x1;
            int y1 = destBounds.y + destBounds.height / 2;
            if (GanttGraphic.isType(linkType, IGanttDto.LINK_FS) == true) {
                //            x0 = getTaskEndPosX(row);
                //            x1 = getTaskBeginPosX(oneLinkedRow);
                x0 = srcBounds.x + srcBounds.width;
                x1 = destBounds.x;
                paintFS(g2d, x0, y0, x1, y1);
            } else if (GanttGraphic.isType(linkType, IGanttDto.LINK_SF) == true) {

                x0 = srcBounds.x;
                x1 = destBounds.x + destBounds.width;
                paintSF(g2d, x0, y0, x1, y1);
            } else if (GanttGraphic.isType(linkType, IGanttDto.LINK_SS) == true) {

                x0 = srcBounds.x;
                x1 = destBounds.x;
                paintSS(g2d, x0, y0, x1, y1);
            } else if (GanttGraphic.isType(linkType, IGanttDto.LINK_FF) == true) {

                x0 = srcBounds.x + srcBounds.width;
                x1 = destBounds.x + destBounds.width;
                paintFF(g2d, x0, y0, x1, y1);
            }
        }


        protected Rectangle getTaskLeafRowBounds(Rectangle outerBounds,
                                                 int iRow, int rowType) {
            int rowHeight = outerBounds.height;
            int x0 = outerBounds.x;
            int x1 = x0 + outerBounds.width + 1; //Ҫ������һ�쿪ʼ�������Լ� 1
            int y0;
            int y1; //rowHeight * (iRow +1) - 4;

            //��Ҷ�ӽڵ�
            y0 = rowHeight * iRow + 3; //���еı���Щ����
            y1 = y0 + 10; //���ø߶ȵ�λ��

            Rectangle bar = new Rectangle(x0, y0, (x1 - x0),
                                          (y1 - y0));

            return bar;
        }

        protected void paintTaskLeaf(Graphics2D g2d, Rectangle outerBounds,
                                     int iRow, int rowType) {
            //��Ҷ�ӽڵ�
            Rectangle clip = g2d.getClipBounds();
            Rectangle bar = getTaskLeafRowBounds(outerBounds, iRow, rowType);
            if (clip.intersects(bar)) { //����صľͲ����ˡ�

                g2d.setPaint(defFillPaint);

                g2d.fillRect(bar.x, bar.y, bar.width, bar.height);
//                if (GanttGraphic.isType(rowType, SELECTED) == false) {
                if( mouseHelper.selectedRow != iRow ){
                    g2d.setColor(Color.BLUE);
                } else {
                    //����д���ˣ�Ҫ�޸�
                    g2d.setColor(Color.white);
                }

                g2d.drawRect(bar.x, bar.y, bar.width, bar.height);
            }
        }

        protected Rectangle getTaskNoLeafRowBounds(Rectangle outerBounds,
                int iRow, int rowType) {
            int x0 = outerBounds.x;
            int x1 = x0 + outerBounds.width + 1; //Ҫ������һ�쿪ʼ�������Լ� 1
            int y0;
            int y1; //rowHeight * (iRow +1) - 4;

            y0 = outerBounds.height * iRow + 8;
            y1 = y0 + 4; //���ø߶ȵ�λ��

            Rectangle bar = new Rectangle(x0, y0, (x1 - x0),
                                          (y1 - y0));

            return bar;
        }

        protected void paintTaskNoLeaf(Graphics2D g2d, Rectangle outerBounds,
                                       int iRow, int rowType) {
            //��һ����ͷ�����Ǳ�ǵĶ����
            Rectangle clip = g2d.getClipBounds();
            Rectangle bar = getTaskNoLeafRowBounds(outerBounds, iRow, rowType);

            g2d.setColor(Color.BLACK);
//        y0 = rowHeight * iRow + 12;
//        y1 = y0;
            int x0 = bar.x;
            int x1 = x0 + bar.width;
            int y0 = bar.y + bar.height;
            int y1 = y0;

            Polygon polygon = new Polygon();
            polygon.addPoint(x0 + 3, y0 - 3);
            polygon.addPoint(x0, y0);
            polygon.addPoint(x0 - 3, y0 - 3);
            polygon.addPoint(x0 - 3, y0 - 3 - 4);
            polygon.addPoint(x1 + 3, y1 - 3 - 4);
            polygon.addPoint(x1 + 3, y1 - 3);
            polygon.addPoint(x1, y1);
            polygon.addPoint(x1 - 3, y1 - 3);

            if (polygon.intersects(clip)) { //����صľͲ�����
                g2d.fillPolygon(polygon);
            }
        }

        //����
        protected Rectangle getProcessRowBounds(Rectangle outerBounds, int iRow,
                                                double processRate, int rowType) {
            int x0;
            int x1;
            int y0;
            int y1;

            x0 = outerBounds.x;
            //�����յ�����
            x1 = outerBounds.x +
                 (int) Math.round(outerBounds.width * processRate / 100);
        //            x1 = outerBounds.x + outerBounds.width;

            y0 = outerBounds.height * iRow + 6;
            y1 = y0 + 4;

            Rectangle bar = new Rectangle(x0, y0, (x1 - x0), (y1 - y0));
            return bar;
        }

        //����
        protected void paintProcess(Graphics2D g2d, Rectangle outerBounds,
                                    int iRow, double processRate, int rowType) {
            //��һ����ͷ�����Ǳ�ǵĶ����
            Rectangle clip = g2d.getClipBounds();
            Rectangle bar = getProcessRowBounds(outerBounds, iRow, processRate, rowType);

            //д���ˣ�Ҫ��
            g2d.setPaint(Color.BLACK);

            if (clip.intersects(bar)) { //����صľͲ����ˡ�
                g2d.fillRect(bar.x, bar.y, bar.width, bar.height);
                g2d.setColor(Color.BLACK);
                g2d.drawRect(bar.x, bar.y, bar.width, bar.height);
            }
        }

        //
        protected Rectangle getActualTaskRowBounds(Rectangle outerBounds,
                int iRow,
                int rowType) {
            int x0;
            int x1;
            int y0;
            int y1;

            x0 = outerBounds.x;
            x1 = x0 + outerBounds.width + 1; //Ҫ������һ�쿪ʼ�������Լ� 1
            y0 = outerBounds.height * iRow + 6;
            y1 = y0 + 5;

            Rectangle bar = new java.awt.Rectangle(x0, y0, (x1 - x0), (y1 - y0));

            return bar;
        }

        protected void paintActualTask(Graphics2D g2d, Rectangle outerBounds,
                                       int iRow, int rowType) {
            //��һ����ͷ�����Ǳ�ǵĶ����
            Rectangle clip = g2d.getClipBounds();
            Rectangle bar = getActualTaskRowBounds(outerBounds, iRow, rowType);

            //д���ˣ�Ҫ��
            g2d.setPaint(Color.BLUE);

            if (clip.intersects(bar)) { //����صľͲ����ˡ�
                g2d.fillRect(bar.x, bar.y, bar.width, bar.height);
                g2d.setColor(Color.BLUE);
                g2d.drawRect(bar.x, bar.y, bar.width, bar.height);
            }
        }


        /**
         * ������ΪFS�ĺ�̹�ϵ
         * @param g
         */
        private void paintFS(Graphics2D g2d, int x0, int y0, int x1, int y1) {
            int iDirect;

            if (y1 > y0 && x1 >= x0) {
                iDirect = ARROW_DOWN;
                g2d.drawLine(x0, y0, x1 + 4, y0);
                g2d.drawLine(x1 + 4, y0, x1 + 4, y1 - 5);
                Polygon polygon = makeEndPolygon(x1 + 4, y1 - 5, iDirect);
                g2d.fill(polygon);
                g2d.drawPolygon(polygon);
            }
            if (y1 < y0 && x1 >= x0) {
                iDirect = ARROW_UP;
                g2d.drawLine(x0, y0, x1 + 4, y0);
                g2d.drawLine(x1 + 4, y0, x1 + 4, y1 + 5);
                Polygon polygon = makeEndPolygon(x1 + 4, y1 + 5, iDirect);
                g2d.fill(polygon);
                g2d.drawPolygon(polygon);
            }

            if (y1 > y0 && x1 < x0) {
                iDirect = ARROW_RIGHT;
                g2d.drawLine(x0, y0, x0 + 4, y0);
                g2d.drawLine(x0 + 4, y0, x0 + 4, y0 + 8);
                g2d.drawLine(x0 + 4, y0 + 8, x1 - 8, y0 + 8);
                g2d.drawLine(x1 - 8, y0 + 8, x1 - 8, y1);
                g2d.drawLine(x1 - 8, y1, x1, y1);
                Polygon polygon = makeEndPolygon(x1, y1, iDirect);
                g2d.fill(polygon);
                g2d.drawPolygon(polygon);
            }

            if (y1 < y0 && x1 < x0) {
                iDirect = ARROW_RIGHT;
                g2d.drawLine(x0, y0, x0 + 4, y0);
                g2d.drawLine(x0 + 4, y0, x0 + 4, y0 - 8);
                g2d.drawLine(x0 + 4, y0 - 8, x1 - 8, y0 - 8);
                g2d.drawLine(x1 - 8, y0 - 8, x1 - 8, y1);
                g2d.drawLine(x1 - 8, y1, x1, y1);
                Polygon polygon = makeEndPolygon(x1, y1, iDirect);
                g2d.fill(polygon);
                g2d.drawPolygon(polygon);
            }
        }

        /**
         * ������ΪSF�ĺ�̹�ϵ
         * @param g
         */
        private void paintSF(Graphics2D g2d, int x0, int y0, int x1, int y1) {
            int iDirect;

            if (x0 >= x1 && y0 < y1) {
                iDirect = ARROW_DOWN;
                g2d.drawLine(x0, y0, x1 - 4, y0);
                g2d.drawLine(x1 - 4, y0, x1 - 4, y1 - 5);
                Polygon polygon = makeEndPolygon(x1 - 4, y1 - 5, iDirect);
                g2d.fill(polygon);
                g2d.drawPolygon(polygon);
            }
            if (x0 >= x1 && y0 > y1) {
                iDirect = ARROW_UP;
                g2d.drawLine(x0, y0, x1 - 4, y0);
                g2d.drawLine(x1 - 4, y0, x1 - 4, y1 + 5);
                Polygon polygon = makeEndPolygon(x1 - 4, y1 + 5, iDirect);
                g2d.fill(polygon);
                g2d.drawPolygon(polygon);
            }

            if (x0 < x1 && y0 < y1) {
                iDirect = ARROW_LEFT;
                g2d.drawLine(x0, y0, x0 - 4, y0);
                g2d.drawLine(x0 - 4, y0, x0 - 4, y1 - 8);
                g2d.drawLine(x0 - 4, y1 - 8, x1 + 8, y1 - 8);
                g2d.drawLine(x1 + 8, y1 - 8, x1 + 8, y1);
                g2d.drawLine(x1 + 8, y1, x1 + 2, y1);
                Polygon polygon = makeEndPolygon(x1 + 2, y1, iDirect);
                g2d.fill(polygon);
                g2d.drawPolygon(polygon);
            }

            if (x0 < x1 && y0 > y1) {
                iDirect = ARROW_LEFT;
                g2d.drawLine(x0, y0, x0 - 4, y0);
                g2d.drawLine(x0 - 4, y0, x0 - 4, y0 - 8);
                g2d.drawLine(x0 - 4, y0 - 8, x1 + 8, y0 - 8);
                g2d.drawLine(x1 + 8, y0 - 8, x1 + 8, y1);
                g2d.drawLine(x1 + 8, y1, x1 + 2, y1);
                Polygon polygon = makeEndPolygon(x1 + 2, y1, iDirect);
                g2d.fill(polygon);
                g2d.drawPolygon(polygon);
            }

        }

        /**
         * ������ΪSS�ĺ�̹�ϵ
         * @param g
         */
        private void paintSS(Graphics2D g2d, int x0, int y0, int x1, int y1) {
            int iDirect = ARROW_RIGHT;
            if (x0 < x1) {
                g2d.drawLine(x0, y0, x0 - 4, y0);
                g2d.drawLine(x0 - 4, y0, x0 - 4, y1);
                g2d.drawLine(x0 - 4, y1, x1, y1);
            } else {
                g2d.drawLine(x1, y1, x1 - 8, y1);
                g2d.drawLine(x1 - 8, y1, x1 - 8, y0);
                g2d.drawLine(x1 - 8, y0, x0, y0);
            }
            Polygon polygon = makeEndPolygon(x1, y1, iDirect);
            g2d.fill(polygon);
            g2d.drawPolygon(polygon);
        }

        /**
         * ������ΪFF�ĺ�̹�ϵ
         * @param g
         */
        private void paintFF(Graphics2D g2d, int x0, int y0, int x1, int y1) {
            int iDirect = ARROW_LEFT;
            if (x0 > x1) {
                g2d.drawLine(x0, y0, x0 + 4, y0);
                g2d.drawLine(x0 + 4, y0, x0 + 4, y1);
                g2d.drawLine(x0 + 4, y1, x1, y1);
            } else {
                g2d.drawLine(x1, y1, x1 + 8, y1);
                g2d.drawLine(x1 + 8, y1, x1 + 8, y0);
                g2d.drawLine(x1 + 8, y0, x0, y0);
            }
            Polygon polygon = makeEndPolygon(x1 + 2, y1, iDirect);
            g2d.fill(polygon);
            g2d.drawPolygon(polygon);
        }


        /**
         * ��һ�������Σ������ӹ�ϵʱ����,��Ϊ�����ǻ�һֱ�ߣ����Բ���
         * @param x
         * @param y
         * @param directFlag ����
         */
//��һ�������Σ�directFlag��һ������
        private Polygon makeEndPolygon(int x2, int y2, int directFlag) {
            Polygon polygon = new Polygon();
            polygon.addPoint(x2, y2);
            switch (directFlag) {
            case ARROW_UP:
                polygon.addPoint(x2 - 3, y2 + 3);
                polygon.addPoint(x2 + 3, y2 + 3);
                break;
            case ARROW_RIGHT:
                polygon.addPoint(x2 - 3, y2 - 3);
                polygon.addPoint(x2 - 3, y2 + 3);
                break;
            case ARROW_DOWN:
                polygon.addPoint(x2 - 3, y2 - 3);
                polygon.addPoint(x2 + 3, y2 - 3);
                break;
            case ARROW_LEFT:
                polygon.addPoint(x2 + 3, y2 - 3);
                polygon.addPoint(x2 + 3, y2 + 3);
                break;
            }
            return polygon;
        }
    }

}
