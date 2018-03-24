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
    private int thickValue = 8;//状态条厚度
    private int sideBlank = 4; //空白
    public static final String BEGIN_VALUE_CHANGED = "begin value changed";
    public static final String END_VALUE_CHANGED = "end value changed";
    public static final String ALL_VALUE_CHANGED = "all value changed";
    public static final String VALUE_CHANGED_CANCEL = "all value changed cancel";

    private boolean drag = false;

    transient private Point dragBeginPoint;//拖动起始点
    transient private Point dragToPoint;
    private boolean moveToBegin = false;
    private boolean moveToEnd = false;
    private double beginValue = 9; //选中起始值
    private double endValue = 12; //选中结束值
    private double maxValue = 19; //刻度下限值
    private double minValue = 8; //刻度上限值
    private double minStep = 0.25; //分辨率，比如只精克到15分钟

    protected Color trackColor = Color.DARK_GRAY;

    private boolean showScale = true;
    private java.awt.Color scaleColor = Color.black;
    private java.awt.Font scaleFont = new Font("Dialog",Font.PLAIN,8);
    private double scaleStep = 1;
    private boolean showBar = true;
    private int scaleHeight = 8;
    private java.text.NumberFormat nf = java.text.NumberFormat.getInstance();
    private boolean autoGrowBound = false;
    private double topGrow = 24;//最大增长终点
    private double bottGrow = 0;//最大增长起点

    /**
     * 取将要成为的起始值，在拖动时有效
     * @return 开始值
     */
    public double getWillBeginValue(){
        if(!drag)
            return Double.NaN;
        double dRtn;
        if (this.moveToBegin) { //设置起点
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
     * 取将要成为的结束值
     * @return
     */
    public double getWillEndValue(){
        if(!drag)
            return Double.NaN;
        double dRtn ;
        if (this.moveToBegin) { //设置起点
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
     * 默认的构造方法
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
     * 默认的初始化方法，用JBuilder自动产生的。
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
     * 取横向缩放比例：显示/实际
     * @return
     */
    private double getWidthScale() {
        java.awt.Dimension dim = this.getSize();
        return (double) (dim.width - this.sideBlank * 2) /
            (double) (this.maxValue - this.minValue);
    }

    /**
     * 取数据条所在的矩形
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
     * 取点击某点所表示的数值，如果点到数据条之外，则返回 NaN
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
     * 按比例取某一点所代表的数值
     * @param pointX
     * @return
     */
    private double getValueAtPoint(int pointX){
        double rx = getWidthScale();
        return (pointX - this.sideBlank) / rx + this.minValue;
    }

    /**
     * 计算某个数值应该在哪个点上
     * @return
     */
     private int getPositionOfValue(double aValue){
         double rx = getWidthScale();
         return  this.sideBlank + (int)((aValue - this.minValue) * rx);
     }

    /**
     * 取数据范围条所在的矩形
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

    //画刻度
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
    //画数据条
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


        //画拖动轨迹
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
     * 重写的组件画法，为了简化，没有彩MVC的方式，直接在这个类中画
     * @param g
     */
    public void paint(Graphics g) {
        //画刻度
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
     * 取最小值，刻度范围的下限度
     * @return
     */
    public double getMinValue() {
        return minValue;
    }

    /**
     * 设置最小值，刻度范围的下限
     * @param minValue
     */
    public void setMinValue(double minValue) {
        this.minValue = minValue;
    }

    /**
     * 取最大值，刻度范围的上限
     * @return
     */
    public double getMaxValue() {
        return maxValue;
    }

    /**
     * 设置刻度范围的上限
     * @param maxValue
     */
    public void setMaxValue(double maxValue) {
        this.maxValue = maxValue;
    }

    /**
     * 取数据起始值
     * @return
     */
    public double getBeginValue() {
        return beginValue;
    }

    /**
     * 设置数据起始值
     * @param beginValue
     */
    public void setBeginValue(double beginValue) {
        this.beginValue = beginValue;
    }

    /**
     * 取数据止值
     * @return
     */
    public double getEndValue() {
        return endValue;
    }

    /**
     * 设置数据止值
     * @param endValue
     */
    public void setEndValue(double endValue) {
        this.endValue = endValue;
    }

    /**
     * 取数据条厚度
     * @return
     */
    public int getThickValue() {
        return thickValue;
    }

    /**
     * 设置数据条厚度
     * @param thickValue
     */
    public void setThickValue(int thickValue) {
        this.thickValue = thickValue;
    }

    /**
     * 取边部空白量
     */
    public int getSideBlank() {
        return sideBlank;
    }

    /**
     * 设置边部空白量
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
     * 处理鼠标移动事件,改变鼠标形状.
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
     * 鼠标拖动,改变拖动值,亲画拖动轨迹
     * @param e
     */
    void this_mouseDragged(MouseEvent e) {
        //System.out.println("this_mouseDragged:" + e);
        this.dragToPoint = e.getPoint();
        this.repaint();
    }

    /**
     * 鼠标点下,标志拖动开始
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
     * 鼠标抬起,标志拖动结束,所修改的值生效.
     * @param e
     */
    void this_mouseReleased(MouseEvent e) {
        if(this.drag){
            //System.out.println("this_mouseReleased="+e);
            java.awt.Dimension dim = this.getSize();
            if(e.getY() <= (dim.height * 5 + 100) && e.getX() != this.dragBeginPoint.x){ //宽度超范围,或没动，忽略拖动
                double oldBegin = this.beginValue;
                double oldEnd = this.endValue;
                int xPos = e.getX();
                double newValue = adjValue(this.getValueAtPoint(xPos));
                if (this.moveToBegin) { //设置起点
                    this.setBeginValue(newValue);
                    adjValue();
                    super.firePropertyChange(BEGIN_VALUE_CHANGED, oldBegin,
                                             newValue);
                }
                else if (this.moveToEnd) { //设置终点
                    this.setEndValue(newValue);
                    adjValue();
                    super.firePropertyChange(END_VALUE_CHANGED, oldEnd,
                                             newValue);
                }
                else { //重新设计起点和终点
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

            //自动增长数值范围
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

    //调整取值范围到数据取值的上限焉下限之间
    void adjValue(){
        //控制最大值
        if(!Double.isNaN(topGrow) && this.endValue > this.topGrow){
            this.endValue = this.topGrow;
        }
        //控制最小值
        if(!Double.isInfinite(this.bottGrow) && this.beginValue < this.bottGrow){
            this.beginValue = this.bottGrow;
        }
    }

    /**
     * 按最小分辨率和上下限调整数值
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
     * 取是否显示刻度
     * @return
     */
    public boolean isShowScale() {
        return showScale;
    }

    /**
     * 设置是否显示刻度
     * @return
     */
    public void setShowScale(boolean showScale) {
        this.showScale = showScale;
    }
    /**
     * 显示刻度的颜色
     * @return
     */
    public java.awt.Color getScaleColor() {
        return scaleColor;
    }
    /**
     * 设置刻度的颜色
     * @return
     */
    public void setScaleColor(java.awt.Color scaleColor) {
        this.scaleColor = scaleColor;
    }
    /**
     * 取刻度的步长
     * @return
     */
    public double getScaleStep() {
        return scaleStep;
    }
    /**
     * 设置刻度的步长
     * @return
     */
    public void setScaleStep(double scaleStep) {
        this.scaleStep = scaleStep;
    }
    /**
     * 是否显示时间条
     * @return
     */
    public boolean isShowBar() {
        return showBar;
    }
    /**
     * 取是否显示时间条
     * @param showBar
     */
    public void setShowBar(boolean showBar) {
        this.showBar = showBar;
    }

    /**
     * 取拖动的分辨率
     * @return
     */
    public double getMinStep() {
        return minStep;
    }

    /**
     * 设置取拖动的分辨率
     * @param minStep
     */
    public void setMinStep(double minStep) {
        this.minStep = minStep;
    }

    /**
     * 取在拖动超广度时的自动修改刻度属性
     * @return
     */
    public boolean isAutoGrowBound() {
        return autoGrowBound;
    }

    /**
     * 设置在拖动超广度时的自动修改刻度属性
     * @param autoGrowBound
     */
    public void setAutoGrowBound(boolean autoGrowBound) {
        this.autoGrowBound = autoGrowBound;
    }

    /**
     * 取数据取值上限
     * @return
     */
    public double getTopGrow() {
        return topGrow;
    }

    /**
     * 设置数据取值上限
     * @param topGrow
     */
    public void setTopGrow(double topGrow) {
        this.topGrow = topGrow;
    }

    /**
     * 取数据下限
     * @return
     */
    public double getBottGrow() {
        return bottGrow;
    }

    /**
     * 设置数据取值下限
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
