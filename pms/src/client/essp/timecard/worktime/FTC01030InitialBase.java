package client.essp.timecard.worktime;

import client.essp.common.calendar.EditableCalendarPanel;
import client.essp.common.view.VWJPanel;
import client.essp.common.view.VWWorkArea;
import client.framework.view.IVWWorkArea;
import client.framework.view.common.comFORM;
import client.framework.view.vwcomp.VWJComboBox;
import client.framework.view.vwcomp.VWJDate;
import client.framework.view.vwcomp.VWJLabel;
import client.framework.view.vwcomp.VWJRadioButton;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;


public class FTC01030InitialBase extends VWWorkArea implements IVWWorkArea {
    protected static final int PERIODROWSIZE = 5; //可设置的工作区间数

    //基本设置部分
    protected VWJPanel     basicPanel  = new VWJPanel();
    protected TitledBorder basicBorder = new TitledBorder("Basic Settings");

    //月周起止
    protected VWJLabel    lblMonthStart = new VWJLabel();
    protected VWJComboBox cbxMonthStart = new VWJComboBox();
    protected VWJLabel    lblWeekStart  = new VWJLabel();
    protected VWJComboBox cbxWeekStart  = new VWJComboBox();

    //工作日工作时间段
    protected VWJLabel   lblWorkPeriod = new VWJLabel();
    protected VWJLabel[] lblTo    = new VWJLabel[PERIODROWSIZE];
    protected VWJDate[]  txtStart = new VWJDate[PERIODROWSIZE];
    protected VWJDate[]  txtEnd   = new VWJDate[PERIODROWSIZE];

    //日历设置部分
    protected VWJPanel              calendarPanel  = new VWJPanel();
    protected TitledBorder          calendarBorder = new TitledBorder("Calendar Settings");
    protected EditableCalendarPanel calendar       = new EditableCalendarPanel();
    protected VWJRadioButton        rdbDefault     = new VWJRadioButton();
    protected VWJRadioButton        rdbWorkDay     = new VWJRadioButton();
    protected VWJRadioButton        rdbHoliday     = new VWJRadioButton();
    private Font                    fontArial      = new Font("Arial", 0, 12);

    public FTC01030InitialBase() {
        try {
            jbInit();
            setUIComponentName();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.setLayout(null);
        this.setPreferredSize(new Dimension(800, 505));
        this.setMaximumSize(new Dimension(1024, 768));
        this.setMinimumSize(new Dimension(500, 200));

        //基本设置区
        basicBorder.setTitleColor(Color.BLUE);
        basicPanel.setBounds(10, 10, 390, 495);
        basicPanel.setLayout(null);
        basicPanel.setBorder(basicBorder);
        this.add(basicPanel);

        //设置月开始
        lblMonthStart.setText("Month Start From");
        lblMonthStart.setBounds(20, 50, 160, 20);
        basicPanel.add(lblMonthStart);

        cbxMonthStart.setBounds(200, 47, 160, 25);
        cbxMonthStart.setFont(fontArial);
        cbxMonthStart.setBorder(BorderFactory.createLoweredBevelBorder());

        for (int i = 1; i <= 31; i++) {
            cbxMonthStart.addItem("" + i);
        }

        basicPanel.add(cbxMonthStart);

        //设置周开始
        lblWeekStart.setText("Week Start From");
        lblWeekStart.setBounds(20, 80, 160, 20);
        basicPanel.add(lblWeekStart);

        cbxWeekStart.setBounds(200, 77, 160, 25);
        cbxWeekStart.setFont(fontArial);
        cbxWeekStart.setBorder(BorderFactory.createLoweredBevelBorder());
        cbxWeekStart.addItem("Sunday");
        cbxWeekStart.addItem("Monday");
        cbxWeekStart.addItem("Tuesday");
        cbxWeekStart.addItem("Wednesday");
        cbxWeekStart.addItem("Thursday");
        cbxWeekStart.addItem("Friday");
        cbxWeekStart.addItem("Saturday");
        basicPanel.add(cbxWeekStart);

        //设置工作时间区间
        lblWorkPeriod.setText("Work Time Periods:");
        lblWorkPeriod.setBounds(20, 180, 160, 20);
        basicPanel.add(lblWorkPeriod);

        java.util.List enterList = new ArrayList();

        for (int rowCount = 0; rowCount < PERIODROWSIZE; rowCount++) {
            txtStart[rowCount] = new VWJDate();
            txtEnd[rowCount]   = new VWJDate();
            lblTo[rowCount]    = new VWJLabel();
            txtStart[rowCount].setBounds(20, 210 + (30 * rowCount), 160, 20);
            txtStart[rowCount].setFont(fontArial);
            txtStart[rowCount].setDataType("HHMM");

            lblTo[rowCount].setText(" ~ ");
            lblTo[rowCount].setFont(fontArial);
            lblTo[rowCount].setBounds(190, 210 + (30 * rowCount), 15, 20);

            txtEnd[rowCount].setBounds(210, 210 + (30 * rowCount), 160, 20);
            txtEnd[rowCount].setFont(fontArial);
            txtEnd[rowCount].setDataType("HHMM");

            basicPanel.add(txtStart[rowCount]);
            basicPanel.add(lblTo[rowCount]);
            basicPanel.add(txtEnd[rowCount]);
            enterList.add(txtStart[rowCount]);
            enterList.add(txtEnd[rowCount]);
        }

        txtEnd[PERIODROWSIZE - 1].setNextFocusableComponent(cbxMonthStart);
        enterList.add(txtStart[0]);
        comFORM.setEnterOrder(this, enterList);

        //日历设置区
        calendarBorder.setTitleColor(Color.blue);
        calendarPanel.setBorder(calendarBorder);
        calendarPanel.setBounds(400, 10, 390, 495);
        calendarPanel.setLayout(null);
        this.add(calendarPanel);

        //日历控件
        calendar.setBounds(15, 40, 360, 265);
        calendar.setBorder(BorderFactory.createLoweredBevelBorder());
        calendar.getTable().setPreferredSize(new Dimension(350, 210));
        calendar.getTable().setRowHeight(35);

        calendarPanel.add(calendar);

        //日历设置按钮（Radio）
        rdbWorkDay.setText("Set to Workday");
        rdbWorkDay.setFont(fontArial);
        rdbWorkDay.setBounds(15, 325, 115, 30);
        rdbWorkDay.setBackground(Color.white);

        calendarPanel.add(rdbWorkDay);

        rdbHoliday.setText("Set to Holiday");
        rdbHoliday.setFont(fontArial);
        rdbHoliday.setBounds(135, 325, 115, 30);
        rdbHoliday.setBackground(Color.white);

        calendarPanel.add(rdbHoliday);

        rdbDefault.setText("Set to Default");
        rdbDefault.setFont(fontArial);
        rdbDefault.setBounds(255, 325, 115, 30);
        rdbDefault.setBackground(Color.white);

        calendarPanel.add(rdbDefault);

        cbxMonthStart.requestFocus();
    }

    private void setUIComponentName() {
        for (int rowCount = 0; rowCount < PERIODROWSIZE; rowCount++) {
            txtStart[rowCount].setName("wtStarttime" + rowCount);
            txtEnd[rowCount].setName("wtEndtime" + rowCount);
        }
    }
}
