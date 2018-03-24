package client.essp.common.calendar;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.beans.PropertyChangeEvent;

import java.text.ParseException;

import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.plaf.basic.BasicArrowButton;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Enovation</p>
 * @author Xuxi.Chen
 * @version 1.0
 */
public class CalendarSelectComponent extends JComponent {
    private CalendarWin  win1;
    private Calendar     data          = null; // Calendar.getInstance();
    private BorderLayout borderLayout1 = new BorderLayout();
    private JTextField   jTextField1   = new JTextField();
    private JButton      jButton1      = new BasicArrowButton(SwingConstants.SOUTH);
    private boolean      keyTyped;

    public CalendarSelectComponent() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        CalendarSelectComponent calendarSelectComponent1 = new CalendarSelectComponent();
        JOptionPane.showMessageDialog(null, calendarSelectComponent1);
    }

    private void jbInit() throws Exception {
        jTextField1.setText("");
        jTextField1.addFocusListener(new CalendarSelectComponentJTextField1FocusAdapter(this));
        jTextField1.addKeyListener(new CalendarSelectComponentJTextField1KeyAdapter(this));
        this.setLayout(borderLayout1);
        jButton1.setPreferredSize(new Dimension(30, 5));
        jButton1.addActionListener(new CalendarSelectComponentJButton1ActionAdapter(this));
        this.add(jTextField1, BorderLayout.CENTER);
        this.add(jButton1, BorderLayout.EAST);
    }

    void jButton1ActionPerformed(ActionEvent e) {
        if (win1 == null) {
            win1 = new CalendarWin(this.jTextField1, this.jButton1);
            win1.addPropertyChangeListener(new CalendarSelectComponentWin1PropertyChangeAdapter(this));
        }

        this.win1.setSelectDate(data);

        //        Point p = this.textField.getLocationOnScreen();
        //        win1.setLocation(p.x, p.y + 15);
        win1.show();
    }

    public void setVisible(boolean visible) {
        super.setVisible(visible);

        if ((this.win1 != null) && !visible) {
            this.win1.hide();
        }
    }

    public Date getValue() {
        return (this.data == null) ? null : this.data.getTime();
    }

    public void setValue(Date date) {
        if (date == null) {
            this.data = null;
            this.jTextField1.setText("");
        } else {
            if (this.data == null) {
                this.data = Calendar.getInstance();
            }

            this.data.setTime(date);

            String calText = CalendarWin.calToString(this.data,
                                                     CalendarWin.defDateformat);
            this.jTextField1.setText(calText);
        }
    }

    void win1PropertyChange(PropertyChangeEvent e) {
        if (e.getPropertyName().equals(CalendarWin.DATE_SELECTED)) {
            this.data = win1.getJPanelMain().getSelectCalendar();
        }
    }

    public boolean isEditable() {
        return jTextField1.isEditable();
    }

    public void setEditable(boolean editable) {
        //this.editable = editable;
        this.jTextField1.setEditable(editable);
        this.jButton1.setEnabled(editable);
    }

    void jTextField1KeyTyped(KeyEvent e) {
        keyTyped = true;
    }

    void jTextField1FocusLost(FocusEvent e) {
        if (keyTyped) { //解析用户输入的内容，如果解析错误，重新设成原先的值。

            String inputText = this.jTextField1.getText();

            if ((inputText != null) && (inputText.length() > 0)) {
                try {
                    Date newDate = CalendarWin.defDateformat.parse(inputText);
                    this.data.setTime(newDate);
                } catch (ParseException ex) {
                    if (this.data != null) {
                        String calText = CalendarWin.calToString(this.data,
                                                                 CalendarWin.defDateformat);
                        this.jTextField1.setText(calText);
                    }
                }
            }

            keyTyped = false;
        }
    }

    public CalendarWin getWin1() {
        return win1;
    }

    public JTextField getJTextField1() {
        return jTextField1;
    }
}


class CalendarSelectComponentJButton1ActionAdapter
    implements java.awt.event.ActionListener {
    CalendarSelectComponent adaptee;

    CalendarSelectComponentJButton1ActionAdapter(CalendarSelectComponent adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton1ActionPerformed(e);
    }
}


class CalendarSelectComponentWin1PropertyChangeAdapter
    implements java.beans.PropertyChangeListener {
    CalendarSelectComponent adaptee;

    CalendarSelectComponentWin1PropertyChangeAdapter(CalendarSelectComponent adaptee) {
        this.adaptee = adaptee;
    }

    public void propertyChange(PropertyChangeEvent e) {
        adaptee.win1PropertyChange(e);
    }
}


class CalendarSelectComponentJTextField1KeyAdapter extends KeyAdapter {
    CalendarSelectComponent adaptee;

    CalendarSelectComponentJTextField1KeyAdapter(CalendarSelectComponent adaptee) {
        this.adaptee = adaptee;
    }

    public void keyTyped(KeyEvent e) {
        adaptee.jTextField1KeyTyped(e);
    }
}


class CalendarSelectComponentJTextField1FocusAdapter extends FocusAdapter {
    CalendarSelectComponent adaptee;

    CalendarSelectComponentJTextField1FocusAdapter(CalendarSelectComponent adaptee) {
        this.adaptee = adaptee;
    }

    public void focusLost(FocusEvent e) {
        adaptee.jTextField1FocusLost(e);
    }
}
