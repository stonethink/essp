package com.uic.timeBar;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

/**
 * <p>Title: test</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author cxx
 * @version 1.0
 */

public class TimeBar
    extends javax.swing.JComponent {
    private int thickValue = 8;//״̬�����
    private int sideBlank = 4; //�հ�
    public static final String BEGIN_VALUE_CHANGED = "begin value changed";
    public static final String END_VALUE_CHANGED = "end value changed";
    public static final String ALL_VALUE_CHANGED = "all value changed";
    public static final String VALUE_CHANGED_CANCEL = "all value changed cancel";

    private boolean drag = false;

    transient private Point dragBeginPoint;//�϶���ʼ��
    transient private Point dragToPoint;
    private boolean moveToBegin = false;
    private boolean moveToEnd = false;
    private double beginValue = 9; //ѡ����ʼֵ
    private double endValue = 12; //ѡ�н���ֵ
    private double maxValue = 19; //�̶�����ֵ
    private double minValue = 8; //�̶�����ֵ
    private double minStep = 0.25; //�ֱ��ʣ�����ֻ���˵�15����

    protected Color trackColor = Color.DARK_GRAY;

    private boolean showScale = true;
    private java.awt.Color scaleColor = Color.black;
    private java.awt.Font scaleFont = new Font("Dialog",Font.PLAIN,8);
    private double scaleStep = 1;
    private boolean showBar = true;
    private int scaleHeight = 8;
    private java.text.NumberFormat nf = java.text.NumberFormat.getInstance();
    private boolean autoGrowBound = false;
    private double topGrow = 24;//��������յ�
    private double bottGrow = 0;//����������

    /**
     * ȡ��Ҫ��Ϊ����ʼֵ�����϶�ʱ��Ч
     * @return ��ʼֵ
     */
    public double getWillBeginValue(){
        if(!drag)
            return Double.NaN;
        double dRtn;
        if (this.moveToBegin) { //�������
            dRtn = Math.min(this.getValueAtPoint(this.dragToPoint.x),this.endValue);
        }
        else if(this.moveToEnd){
            dRtn = Math.min(this.getValueAtPoint(this.dragToPoint.x), this.beginValue);
        }
        else{
            dRtn = this.getValueAtPoint(Math.min(this.dragBeginPoint.x, this.dragToPoint.x));
        }
        return adjValue(dRtn);
    }
    /**
     * ȡ��Ҫ��Ϊ�Ľ���ֵ
     * @return
     */
    public double getWillEndValue(){
        if(!drag)
            return Double.NaN;
        double dRtn ;
        if (this.moveToBegin) { //�������
            dRtn = Math.max(this.getValueAtPoint(this.dragToPoint.x),this.endValue);
        }
        else if(this.moveToEnd){
            dRtn = Math.max(this.getValueAtPoint(this.dragToPoint.x), this.beginValue);
        }
        else{
            dRtn = this.getValueAtPoint(Math.max(this.dragBeginPoint.x, this.dragToPoint.x));
        }
        return adjValue(dRtn);
    }
    /**
     * Ĭ�ϵĹ��췽��
     */
    public TimeBar() {
        try {
            jbInit();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Ĭ�ϵĳ�ʼ����������JBuilder�Զ������ġ�
     * @throws java.lang.Exception
     */
    void jbInit() throws Exception {
        this.setForeground(SystemColor.activeCaption);
        this.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                this_mousePressed(e);
            }
            public void mouseReleased(MouseEvent e) {
                this_mouseReleased(e);
            }
        });
        this.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(MouseEvent e) {
                this_mouseMoved(e);
            }

            public void mouseDragged(MouseEvent e) {
                this_mouseDragged(e);
            }
        });

    }

    /**
     * ȡ�������ű�������ʾ/ʵ��
     * @return
     */
    private double getWidthScale() {
        java.awt.Dimension dim = this.getSize();
        return (double) (dim.width - this.sideBlank * 2) /
            (double) (this.maxValue - this.minValue);
    }

    /**
     * ȡ���������ڵľ���
     * @return
     */
    private java.awt.Rectangle getBarRectangle() {
        //double rx = getWidthScale();
        //double dX = rx * (this.beginValue - this.minValue);
        //double dWidth = rx * (this.endValue - this.beginValue);
        double dX = getPositionOfValue(this.beginValue);
        double dWidth = getPositionOfValue(this.endValue) - getPositionOfValue(this.beginValue);
        if(dWidth == 0.0){
            dWidth = 1;
        }
        int iHeight = this.thickValue;
        double dY = 0.0;
        if(showScale) {
            dY = ( (double)this.getSize().height - this.thickValue + this.scaleHeight) / 2.0;
        }
        else {
            dY = ( (double)this.getSize().height - this.thickValue) / 2.0;
        }
        Rectangle rRtn = new Rectangle( (int) dX, (int) dY, (int) dWidth,
                                       iHeight);
        return rRtn;
    }

    /**
     * ȡ���ĳ������ʾ����ֵ������㵽������֮�⣬�򷵻� NaN
     * @param point
     * @return
     */
    public double getValueAtPoint(Point point){
        if(this.getBoundRectangle().contains(point)){
            return this.adjValue(this.getValueAtPoint(point.x));
        }
        else {
            return Double.NaN;
        }
    }

    /**
     * ������ȡĳһ�����������ֵ
     * @param pointX
     * @return
     */
    private double getValueAtPoint(int pointX){
        double rx = getWidthScale();
        return (pointX - this.sideBlank) / rx + this.minValue;
    }

    /**
     * ����ĳ����ֵӦ�����ĸ�����
     * @return
     */
     private int getPositionOfValue(double aValue){
         double rx = getWidthScale();
         return  this.sideBlank + (int)((aValue - this.minValue) * rx);
     }

    /**
     * ȡ���ݷ�Χ�����ڵľ���
     * @return
     */
    private java.awt.Rectangle getBoundRectangle() {
        double dX = this.sideBlank;
        double dWidth = this.getSize().getWidth() - 2 * this.sideBlank;
        int iHeight = this.thickValue;
        double dY = 0.0;
        if(showScale) {
            dY = ( (double)this.getSize().height - this.thickValue + this.scaleHeight) / 2.0;
        }
        else {
            dY = ( (double)this.getSize().height - this.thickValue) / 2.0;
        }
        Rectangle rRtn = new Rectangle( (int) dX, (int) dY, (int) dWidth,
                                       iHeight);
        return rRtn;
    }

    //���̶�
    private void drawScale(Graphics g){
        Color oldColor = g.getColor();
        g.setColor(this.getScaleColor());
        Font oldFont = g.getFont();
        g.setFont(scaleFont);
        int yFont = this.scaleFont.getSize() + 3;
        int yLine = yFont + 3;
        double rx = this.getWidthScale();
        double dMax = this.maxValue;
        for(double xValue = this.minValue; xValue < dMax; xValue += this.scaleStep){
            int x = this.getPositionOfValue(xValue);
            String str = nf.format(xValue);
            g.drawString(str, x, yFont);
            g.drawLine(x,yLine,x,yFont);
        }
        int x0 = this.getPositionOfValue(this.minValue);
        int xn = this.getPositionOfValue(this.maxValue);
        g.drawLine(x0, yLine, xn, yLine);

        g.setFont(oldFont);
        g.setColor(oldColor);
    }
    //��������
    private void drawBar(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        java.awt.Rectangle boundRect = getBoundRectangle();
        java.awt.Rectangle fillRect = getBarRectangle();
        g.drawRect(boundRect.x, boundRect.y, boundRect.width, boundRect.height);
        g.fillRect(fillRect.x, fillRect.y, fillRect.width, fillRect.height);
        if (moveToBegin) {
            g.drawLine(fillRect.x - 2, fillRect.y - this.thickValue / 2,
                       fillRect.x - 2,
                       fillRect.y + this.thickValue + this.thickValue / 2);
            g.drawLine(fillRect.x, fillRect.y - this.thickValue / 2, fillRect.x,
                       fillRect.y + this.thickValue + this.thickValue / 2);
        }
        else if (moveToEnd) {
            int x = fillRect.x + fillRect.width;
            g.drawLine(x + 2, fillRect.y - this.thickValue / 2, x + 2,
                       fillRect.y + this.thickValue + this.thickValue / 2);
            g.drawLine(x, fillRect.y - this.thickValue / 2, x,
                       fillRect.y + this.thickValue + this.thickValue / 2);
        }


        //���϶��켣
        if(dragBeginPoint != null && dragToPoint != null){
            int x = Math.min(this.dragBeginPoint.x, this.dragToPoint.x) ;
            int y = boundRect.y;
            int width = Math.abs( this.dragToPoint.x - dragBeginPoint.x );
            g.setColor(this.trackColor);
            int rule = AlphaComposite.SRC_OVER;
            float alpha = 0.5f;
            g2d.setComposite(AlphaComposite.getInstance(rule,alpha));
            g.fillRect(x, y, width, boundRect.height);

            //System.out.println("x="+x +"y="+y+"width="+width);
            //System.out.println("dragBeginPoint.x="+dragBeginPoint.x + ",dragToPoint.x="+dragToPoint.x);
        }
    }

    /**
     * ��д�����������Ϊ�˼򻯣�û�в�MVC�ķ�ʽ��ֱ����������л�
     * @param g
     */
    public void paint(Graphics g) {
        //���̶�
        if(this.showScale){
            drawScale(g);
        }
        if(this.showBar){
            drawBar(g);
        }
        super.paintComponent(g);
    }

    //for test
    public static void main(String[] args) throws Exception {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        client.essp.common.view.UISetting.settingUIManager();
        TimeBar timeBar1 = new TimeBar();
        timeBar1.setPreferredSize(new Dimension(200, 40));
        javax.swing.JOptionPane.showMessageDialog(null, timeBar1);
        System.exit(0);
    }

    /**
     * ȡ��Сֵ���̶ȷ�Χ�����޶�
     * @return
     */
    public double getMinValue() {
        return minValue;
    }

    /**
     * ������Сֵ���̶ȷ�Χ������
     * @param minValue
     */
    public void setMinValue(double minValue) {
        this.minValue = minValue;
    }

    /**
     * ȡ���ֵ���̶ȷ�Χ������
     * @return
     */
    public double getMaxValue() {
        return maxValue;
    }

    /**
     * ���ÿ̶ȷ�Χ������
     * @param maxValue
     */
    public void setMaxValue(double maxValue) {
        this.maxValue = maxValue;
    }

    /**
     * ȡ������ʼֵ
     * @return
     */
    public double getBeginValue() {
        return beginValue;
    }

    /**
     * ����������ʼֵ
     * @param beginValue
     */
    public void setBeginValue(double beginValue) {
        this.beginValue = beginValue;
    }

    /**
     * ȡ����ֵֹ
     * @return
     */
    public double getEndValue() {
        return endValue;
    }

    /**
     * ��������ֵֹ
     * @param endValue
     */
    public void setEndValue(double endValue) {
        this.endValue = endValue;
    }

    /**
     * ȡ���������
     * @return
     */
    public int getThickValue() {
        return thickValue;
    }

    /**
     * �������������
     * @param thickValue
     */
    public void setThickValue(int thickValue) {
        this.thickValue = thickValue;
    }

    /**
     * ȡ�߲��հ���
     */
    public int getSideBlank() {
        return sideBlank;
    }

    /**
     * ���ñ߲��հ���
     */
    public void setSideBlank(int sideBlank) {
        this.sideBlank = sideBlank;
    }

    private boolean isInBeginPosition(Point point, Rectangle fillRect) {
        return Math.abs(point.getX() - fillRect.x) <= 2 &&
            Math.abs(point.getY() - fillRect.y - this.thickValue / 2) <=
            this.thickValue;
    }

    private boolean isInEndPosition(Point point, Rectangle fillRect) {
        int x = fillRect.x + fillRect.width;

        return Math.abs(point.getX() - x) <= 2 &&
            Math.abs(point.getY() - fillRect.y - this.thickValue / 2) <=
            this.thickValue;
    }

    /**
     * ��������ƶ��¼�,�ı������״.
     * @param e
     */
    void this_mouseMoved(MouseEvent e) {
        //System.out.println("this_mouseMoved:"+e);
        java.awt.Rectangle fillRect = getBarRectangle();
        boolean oldMoveToBegin = this.moveToBegin;
        boolean oldMoveToEnd = this.moveToEnd;
        if (isInBeginPosition(e.getPoint(), fillRect)) {
            this.moveToBegin = true;
        }
        else {
            this.moveToBegin = false;
        }
        if (isInEndPosition(e.getPoint(), fillRect)) {
            this.moveToEnd = true;
            this.setCursor(Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR));
        }
        else {
            this.moveToEnd = false;
            this.setCursor(Cursor.getDefaultCursor());
       }

       if (moveToBegin || moveToEnd) {
           this.setCursor(Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR));
       }
       else {
           this.setCursor(Cursor.getDefaultCursor());
       }

       if (this.moveToBegin != oldMoveToBegin ||
            this.moveToEnd != oldMoveToEnd) {
            this.repaint();
        }
    }

    /**
     * ����϶�,�ı��϶�ֵ,�׻��϶��켣
     * @param e
     */
    void this_mouseDragged(MouseEvent e) {
        //System.out.println("this_mouseDragged:" + e);
        this.dragToPoint = e.getPoint();
        this.repaint();
    }

    /**
     * ������,��־�϶���ʼ
     * @param e
     */
    void this_mousePressed(MouseEvent e) {
        if (this.moveToBegin || this.moveToEnd ||
            this.getBoundRectangle().contains(e.getPoint())) {
            this.drag = true;
            this.dragBeginPoint = (Point)e.getPoint();//.clone();
        }
    }

    /**
     * ���̧��,��־�϶�����,���޸ĵ�ֵ��Ч.
     * @param e
     */
    void this_mouseReleased(MouseEvent e) {
        if(this.drag){
            //System.out.println("this_mouseReleased="+e);
            java.awt.Dimension dim = this.getSize();
            if(e.getY() <= (dim.height * 5 + 100) && e.getX() != this.dragBeginPoint.x){ //��ȳ���Χ,��û���������϶�
                double oldBegin = this.beginValue;
                double oldEnd = this.endValue;
                int xPos = e.getX();
                double newValue = adjValue(this.getValueAtPoint(xPos));
                if (this.moveToBegin) { //�������
                    this.setBeginValue(newValue);
                    adjValue();
                    super.firePropertyChange(BEGIN_VALUE_CHANGED, oldBegin,
                                             newValue);
                }
                else if (this.moveToEnd) { //�����յ�
                    this.setEndValue(newValue);
                    adjValue();
                    super.firePropertyChange(END_VALUE_CHANGED, oldEnd,
                                             newValue);
                }
                else { //������������յ�
                    double dragBeginValue = adjValue(this.getValueAtPoint(this.
                        dragBeginPoint.x));
                    if (dragBeginValue < newValue) {
                        this.setBeginValue(dragBeginValue);
                        this.setEndValue(newValue);
                    }
                    else {
                        this.setEndValue(dragBeginValue);
                        this.setBeginValue(newValue);
                    }
                    adjValue();
                    super.firePropertyChange(this.ALL_VALUE_CHANGED, null,
                                             null);

                }
                if (this.endValue < this.beginValue) {
                    double dt = beginValue;
                    beginValue = endValue;
                    endValue = dt;
                }
            }
            else{
                super.firePropertyChange(this.VALUE_CHANGED_CANCEL, null,
                                             null);
            }

            //�Զ�������ֵ��Χ
            if(this.isAutoGrowBound()){
                if(this.endValue >= this.maxValue){
                    double newMax = this.endValue + this.minStep;
                    if(newMax > this.topGrow){
                        newMax = topGrow;
                        this.endValue = newMax;
                    }
                    this.setMaxValue(newMax);
                }
                if(this.beginValue <= this.minValue){
                    double newMin = this.minValue - this.minStep;
                    if(newMin < this.bottGrow){
                        newMin = bottGrow;
                        this.beginValue = newMin;
                    }
                    this.setMinValue(newMin);
                }
            }
            this.drag = false;
            this.moveToBegin = false;
            this.moveToEnd = false;
            this.dragBeginPoint = null;
            this.dragToPoint = null;
            this.repaint();
        }
    }

    //����ȡֵ��Χ������ȡֵ������������֮��
    void adjValue(){
        //�������ֵ
        if(!Double.isNaN(topGrow) && this.endValue > this.topGrow){
            this.endValue = this.topGrow;
        }
        //������Сֵ
        if(!Double.isInfinite(this.bottGrow) && this.beginValue < this.bottGrow){
            this.beginValue = this.bottGrow;
        }
    }

    /**
     * ����С�ֱ��ʺ������޵�����ֵ
     * @param value
     * @return
     */
    protected double adjValue(double value){
        if(value <= this.bottGrow){
            return this.bottGrow;
        }
        if(value >= this.topGrow){
            return this.topGrow;
        }
        if(this.minStep <= 0.0){
            return value;
        }
        return minStep * Math.round(value / minStep + minStep/2);
    }

    /**
     * ȡ�Ƿ���ʾ�̶�
     * @return
     */
    public boolean isShowScale() {
        return showScale;
    }

    /**
     * �����Ƿ���ʾ�̶�
     * @return
     */
    public void setShowScale(boolean showScale) {
        this.showScale = showScale;
    }
    /**
     * ��ʾ�̶ȵ���ɫ
     * @return
     */
    public java.awt.Color getScaleColor() {
        return scaleColor;
    }
    /**
     * ���ÿ̶ȵ���ɫ
     * @return
     */
    public void setScaleColor(java.awt.Color scaleColor) {
        this.scaleColor = scaleColor;
    }
    /**
     * ȡ�̶ȵĲ���
     * @return
     */
    public double getScaleStep() {
        return scaleStep;
    }
    /**
     * ���ÿ̶ȵĲ���
     * @return
     */
    public void setScaleStep(double scaleStep) {
        this.scaleStep = scaleStep;
    }
    /**
     * �Ƿ���ʾʱ����
     * @return
     */
    public boolean isShowBar() {
        return showBar;
    }
    /**
     * ȡ�Ƿ���ʾʱ����
     * @param showBar
     */
    public void setShowBar(boolean showBar) {
        this.showBar = showBar;
    }

    /**
     * ȡ�϶��ķֱ���
     * @return
     */
    public double getMinStep() {
        return minStep;
    }

    /**
     * ����ȡ�϶��ķֱ���
     * @param minStep
     */
    public void setMinStep(double minStep) {
        this.minStep = minStep;
    }

    /**
     * ȡ���϶������ʱ���Զ��޸Ŀ̶�����
     * @return
     */
    public boolean isAutoGrowBound() {
        return autoGrowBound;
    }

    /**
     * �������϶������ʱ���Զ��޸Ŀ̶�����
     * @param autoGrowBound
     */
    public void setAutoGrowBound(boolean autoGrowBound) {
        this.autoGrowBound = autoGrowBound;
    }

    /**
     * ȡ����ȡֵ����
     * @return
     */
    public double getTopGrow() {
        return topGrow;
    }

    /**
     * ��������ȡֵ����
     * @param topGrow
     */
    public void setTopGrow(double topGrow) {
        this.topGrow = topGrow;
    }

    /**
     * ȡ��������
     * @return
     */
    public double getBottGrow() {
        return bottGrow;
    }

    /**
     * ��������ȡֵ����
     * @param bottGrow
     */
    public void setBottGrow(double bottGrow) {
        this.bottGrow = bottGrow;
    }
    public boolean isDrag() {
        return drag;
    }
    public boolean isMoveToBegin() {
        return moveToBegin;
    }
    public boolean isMoveToEnd() {
        return moveToEnd;
    }

}
