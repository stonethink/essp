package com.uic.timeBar;

import javax.swing.*;
//import javax.swing.event.*;
//import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.text.*;
import java.beans.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Enovation</p>
 * @author Xuxi.Chen
 * @version 1.0
 */

public class PanelTimeBar extends JPanel {
    static java.text.NumberFormat nf = java.text.NumberFormat.getInstance();
    static final String HOUR_MINU_SEP = ":"; //小时与分的分隔
    BorderLayout borderLayout1 = new BorderLayout();
    TimeBar timeBar = new TimeBar();
    JPanel jPanel1 = new JPanel();
    JTextField jTextFieldBeginTime = new JTextField();
    JPanel jPanel2 = new JPanel();
    JTextField jTextFieldEndTime = new JTextField();
    FlowLayout flowLayout1 = new FlowLayout();
    private java.util.Calendar beginTime;
    private java.util.Calendar endTime;
    JLabel jLabel1 = new JLabel();
    private transient Vector actionListeners;

    public PanelTimeBar(Calendar beginTime, Calendar endTime) {
        this();
        this.setBeginTime(beginTime);
        this.setEndTime(endTime);
    }

    public PanelTimeBar() {
        try {
            jbInit();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.setLayout(borderLayout1);
        jTextFieldBeginTime.setBorder(null);
        jTextFieldBeginTime.setPreferredSize(new Dimension(32, 22));
        setTimeText();
//    jTextFieldBeginTime.setText(toMinuDeg(timeBar.getBeginValue()));
//    jTextFieldEndTime.setText(toMinuDeg(timeBar.getEndValue()));
        jTextFieldEndTime.setBorder(null);
        jTextFieldEndTime.setPreferredSize(new Dimension(32, 22));
        jTextFieldEndTime.setToolTipText("");
        jTextFieldEndTime.setVerifyInputWhenFocusTarget(true);
        jPanel1.setLayout(flowLayout1);
        jPanel2.setLayout(flowLayout1);
        flowLayout1.setHgap(0);
        flowLayout1.setVgap(0);
        //timeBar.setForeground(UIManager.getColor("ComboBox.selectionBackground"));
        //timeBar.setForeground(new Color(66,97,148));
        timeBar.setForeground(new Color(77,112,170));
        timeBar.setMinValue(8.0);
        timeBar.setMaxValue(20.0);
        timeBar.addMouseListener(new PanelTimeBar_timeBar_mouseAdapter(this));
        timeBar.addMouseMotionListener(new PanelTimeBar_timeBar_mouseMotionAdapter(this));
        timeBar.addPropertyChangeListener(new
            PanelTimeBar_timeBar_propertyChangeAdapter(this));
        jLabel1.setText("~");
        this.add(timeBar, BorderLayout.CENTER);
        this.add(jPanel1, BorderLayout.WEST);
        jPanel1.add(jTextFieldBeginTime, null);
        jPanel1.add(jLabel1, null);
        jPanel1.add(jTextFieldEndTime, null);
        this.add(jPanel2, BorderLayout.EAST);
        setPreferredSize(new Dimension(123, 26));
        jTextFieldBeginTime.addActionListener(new
            PanelTimeBar_jTextFieldBeginTime_actionAdapter(this));
        jTextFieldEndTime.addActionListener(new
            PanelTimeBar_jTextFieldEndTime_actionAdapter(this));
        jTextFieldBeginTime.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(FocusEvent e) {
                jTextFieldBeginTime_focusLost(e);
            }
        });
        jTextFieldEndTime.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(FocusEvent e) {
                jTextFieldEndTime_focusLost(e);
            }
        });
    }

    protected void setTimeText() {
        if (this.timeBar.isShowBar()) {
            jTextFieldBeginTime.setText(toMinuDeg(timeBar.getBeginValue()));
            jTextFieldEndTime.setText(toMinuDeg(timeBar.getEndValue()));
        }
    }

    /**
     * 将hh:mm时间较成十制制小时
     * @param degMinu
     * @return
     */
    static double toDecDeg(String hourMinu) throws ParseException {
        java.util.StringTokenizer tok = new StringTokenizer(hourMinu,
            HOUR_MINU_SEP);
        String strHH = tok.nextToken();
        String strMM = "00";
        if (tok.hasMoreElements()) {
            strMM = tok.nextToken();
        }
        double dHour = 0.0;
        double dMin = 0.0;
        dHour = nf.parse(strHH).doubleValue();
        dMin = nf.parse(strMM).doubleValue();

        return dHour + dMin / 60.0;
    }

    /**
     * 将十进制小时转成hh:mm形式字串
     * @param <any>
     * @return
     */
    static String toMinuDeg(double hour) {
        int dHour = (int) Math.floor(hour);
        double dMinu = (hour - dHour) * 60.0;
        nf.setMinimumIntegerDigits(2);
        nf.setMaximumFractionDigits(0);
        return dHour + HOUR_MINU_SEP + nf.format(dMinu);
    }

    //for test
    public static void main(String[] args) throws Exception {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        client.essp.common.view.UISetting.settingUIManager();
        PanelTimeBar panelTimeBar = new PanelTimeBar();
        panelTimeBar.setPreferredSize(new Dimension(400, 40));
        javax.swing.JOptionPane.showMessageDialog(null, panelTimeBar);
        System.exit(0);
    }

    void timeBar_propertyChange(PropertyChangeEvent e) {
        String propName = e.getPropertyName();
        if (propName.equals(TimeBar.ALL_VALUE_CHANGED) ||
            propName.equals(TimeBar.BEGIN_VALUE_CHANGED) ||
            propName.equals(TimeBar.VALUE_CHANGED_CANCEL) ||
            propName.equals(TimeBar.END_VALUE_CHANGED)) {
            setTimeText();
            this.fireActionPerformed(new ActionEvent(this,0,propName));
        }
    }

    /**
     * 取时间条组件
     * @return
     */
    public TimeBar getTimeBar() {
        return timeBar;
    }

    /**
     * 取结束时间文字组件
     * @return
     */
    public JTextField getJTextFieldEndTime() {
        return jTextFieldEndTime;
    }
    /**
     * 取开始时间文字组件
     * @return
     */
    public JTextField getJTextFieldBeginTime() {
        return jTextFieldBeginTime;
    }

    void jTextFieldBeginTime_actionPerformed(ActionEvent e) {
        try {
            this.timeBar.setBeginValue(toDecDeg(this.jTextFieldBeginTime.
                                                 getText()));
            this.fireActionPerformed(new ActionEvent(this,0,TimeBar.BEGIN_VALUE_CHANGED));
        }
        catch (ParseException ex) {
            //ex.printStackTrace();
        }
        this.setTimeText();
        this.timeBar.repaint();
    }

    void jTextFieldEndTime_actionPerformed(ActionEvent e) {
        try {
            this.timeBar.setEndValue(toDecDeg(this.jTextFieldEndTime.getText()));
            this.fireActionPerformed(new ActionEvent(this,0,TimeBar.END_VALUE_CHANGED));
        }
        catch (ParseException ex) {
            ex.printStackTrace();
        }
        this.setTimeText();
        this.timeBar.repaint();
    }

    public void jTextFieldBeginTime_focusLost(FocusEvent e) {
        if (!e.isTemporary()) {
            try {
                this.timeBar.setBeginValue(toDecDeg(this.jTextFieldBeginTime.
                                                     getText()));
            }
            catch (ParseException ex) {
                //ex.printStackTrace();
            }
            this.setTimeText();
            this.timeBar.repaint();
        }
    }

    public void jTextFieldEndTime_focusLost(FocusEvent e) {
        if (!e.isTemporary()) {
            try {
                this.timeBar.setEndValue(toDecDeg(this.jTextFieldEndTime.getText()));
            }
            catch (ParseException ex) {
                ex.printStackTrace();
            }
            this.setTimeText();
            this.timeBar.repaint();
        }
    }
    /**
     * 取数据.
     * @return 包含起始点两个元素的日期弄数组
     */
    public java.util.Calendar[] getData() {
        return new Calendar[]{
            this.getBeginTime(),
            this.getEndTime()
        };
    }

    /**
     * 设置数据
     * @param data 包含起始点两个元素的日期弄数组
     */
    public void setData(java.util.Calendar[] data) {
        this.setBeginTime(data[0]);
        this.setEndTime(data[1]);
    }

    /**
     * 取开始时间
     * @return 一个日历型对象,只有时间的有意义的.
     */
    public java.util.Calendar getBeginTime() {
        return putCalendarHour(beginTime, this.timeBar.getBeginValue());
    }

    /**
     * 设置时间条的起点
     * @param beginTime 日历型对象,只有时间的有意义的.
     */
    public void setBeginTime(java.util.Calendar beginTime) {
        if (beginTime != null) {
            this.beginTime = beginTime;
            this.timeBar.setBeginValue(getCalendarHour(beginTime));
            this.setTimeText();
        }
    }

    /**
     * 取终止时间
     * @return 日历型对象,只有时间是有意义的.
     */
    public java.util.Calendar getEndTime() {
        return putCalendarHour(endTime, this.timeBar.getEndValue());
    }

    /**
     * 将一个十进帛的时间值写到一个日历对象中去
     * @param aDate
     * @param hour
     * @return
     */
    static Calendar putCalendarHour(java.util.Calendar aDate, double hour) {
        aDate.set(Calendar.HOUR_OF_DAY, (int) Math.floor(hour));
        aDate.set(Calendar.MINUTE, (int) (60.0 * (hour - Math.floor(hour))));
        return aDate;
    }

    /**
     * 按十进制小时值返回小时数
     * @param aDate
     * @return
     */
    static double getCalendarHour(Calendar aDate) {
        return aDate.get(Calendar.HOUR_OF_DAY) +
            aDate.get(Calendar.MINUTE) / 60.0;
    }

    /**
     * 设置终止时间
     * @param endTime
     */
    public void setEndTime(java.util.Calendar endTime) {
        if (endTime != null) {
            this.endTime = endTime;
            double hours = getCalendarHour(endTime);
            if( Math.abs(hours) <= 0.0000001 ){
                this.timeBar.setEndValue(
                    (endTime.get(Calendar.DATE) == beginTime.get(Calendar.DATE)) ?
                    0 : 24 );
            }
            else {
                this.timeBar.setEndValue(hours);
            }
            this.setTimeText();
        }
    }

    void timeBar_mouseDragged(MouseEvent e) {
        if(this.timeBar.isDrag()){
            this.jTextFieldBeginTime.setText(toMinuDeg(timeBar.
                getWillBeginValue()));
            this.jTextFieldEndTime.setText(toMinuDeg(timeBar.getWillEndValue()));
        }
    }
    public synchronized void removeActionListener(ActionListener l) {
        if (actionListeners != null && actionListeners.contains(l)) {
            Vector v = (Vector) actionListeners.clone();
            v.removeElement(l);
            actionListeners = v;
        }
    }
    public synchronized void addActionListener(ActionListener l) {
        Vector v = actionListeners == null ? new Vector(2) : (Vector) actionListeners.clone();
        if (!v.contains(l)) {
            v.addElement(l);
            actionListeners = v;
        }
    }
    protected void fireActionPerformed(ActionEvent e) {
        if (actionListeners != null) {
            Vector listeners = actionListeners;
            int count = listeners.size();
            for (int i = 0; i < count; i++) {
                ((ActionListener) listeners.elementAt(i)).actionPerformed(e);
            }
        }
    }

    void timeBar_mousePressed(MouseEvent e) {
        double d = timeBar.getValueAtPoint(e.getPoint());
        if(!Double.isNaN(d)){
            if(timeBar.isMoveToBegin()){
                //this.jTextFieldBeginTime.setText(toMinuDeg(d));
            }
            else if(timeBar.isMoveToEnd()){
                //this.jTextFieldEndTime.setText(toMinuDeg(d));
            }
            else{
                this.jTextFieldBeginTime.setText(toMinuDeg(d));
                this.jTextFieldEndTime.setText(toMinuDeg(d));
            }
        }
    }

    void timeBar_mouseReleased(MouseEvent e) {
        setTimeText();
        //        this.jTextFieldBeginTime.setText(toMinuDeg(timeBar.getBeginValue()));
        //        this.jTextFieldEndTime.setText(toMinuDeg(timeBar.getEndValue()));
    }


}

class PanelTimeBar_timeBar_propertyChangeAdapter implements java.beans.
    PropertyChangeListener {
    PanelTimeBar adaptee;

    PanelTimeBar_timeBar_propertyChangeAdapter(PanelTimeBar adaptee) {
        this.adaptee = adaptee;
    }

    public void propertyChange(PropertyChangeEvent e) {
        adaptee.timeBar_propertyChange(e);
    }
}

class PanelTimeBar_jTextFieldBeginTime_actionAdapter implements java.awt.event.
    ActionListener {
    PanelTimeBar adaptee;

    PanelTimeBar_jTextFieldBeginTime_actionAdapter(PanelTimeBar adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jTextFieldBeginTime_actionPerformed(e);
    }
}

class PanelTimeBar_jTextFieldEndTime_actionAdapter implements java.awt.event.
    ActionListener {
    PanelTimeBar adaptee;

    PanelTimeBar_jTextFieldEndTime_actionAdapter(PanelTimeBar adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jTextFieldEndTime_actionPerformed(e);
    }
}

class PanelTimeBar_timeBar_mouseMotionAdapter extends java.awt.event.MouseMotionAdapter {
    PanelTimeBar adaptee;

    PanelTimeBar_timeBar_mouseMotionAdapter(PanelTimeBar adaptee) {
        this.adaptee = adaptee;
    }
    public void mouseDragged(MouseEvent e) {
        adaptee.timeBar_mouseDragged(e);
    }
}

class PanelTimeBar_timeBar_mouseAdapter extends java.awt.event.MouseAdapter {
    PanelTimeBar adaptee;

    PanelTimeBar_timeBar_mouseAdapter(PanelTimeBar adaptee) {
        this.adaptee = adaptee;
    }
    public void mousePressed(MouseEvent e) {
        adaptee.timeBar_mousePressed(e);
    }
    public void mouseReleased(MouseEvent e) {
        adaptee.timeBar_mouseReleased(e);
    }
}
