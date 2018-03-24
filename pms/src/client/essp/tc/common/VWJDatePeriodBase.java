package client.essp.tc.common;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;
import java.util.Date;

import javax.swing.ButtonGroup;
import javax.swing.SwingConstants;

import c2s.essp.common.calendar.WorkCalendar;
import c2s.essp.common.calendar.WorkCalendarConstant;
import client.essp.common.view.VWGeneralWorkArea;
import client.framework.view.vwcomp.VWJDate;
import client.framework.view.vwcomp.VWJLabel;
import client.framework.view.vwcomp.VWJRadioButton;
import com.wits.util.comDate;
import c2s.essp.common.calendar.WrokCalendarFactory;
import client.framework.view.vwcomp.VWJButton;
import java.awt.event.*;

public class VWJDatePeriodBase extends VWGeneralWorkArea {
    public final static int PERIOD_ONE_MONTH = 0;
    public final static int PERIOD_ONE_WEEK = 1;

    protected WorkCalendar workCalendar;
    protected Date theDate = null;
    protected int theType = -1;
    protected boolean locked = false;

    protected Date oldBeginDate = null;
    protected Date oldEndDate = null;

    protected VWJDate dteBeginPeriod = new VWJDate();
    protected VWJDate dteEndPeriod = new VWJDate();
    protected VWJLabel lblAnd = new VWJLabel();

    protected VWJRadioButton radWeek = new VWJRadioButton();
    protected VWJRadioButton radMonth = new VWJRadioButton();

    protected VWJButton btnLater = new VWJButton();
    protected VWJButton btnEarly = new VWJButton();

    public VWJDatePeriodBase() {
        this( PERIOD_ONE_WEEK );
    }

    public VWJDatePeriodBase(int theType) {
        workCalendar = WrokCalendarFactory.clientCreate();

        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        setTheType(theType);
    }

    private void jbInit() throws Exception {
        this.setLayout(null);
        ButtonGroup btnGroup = new ButtonGroup();
        btnGroup.add(radWeek);
        btnGroup.add(radMonth);

        radWeek.setText("One Week");
        radMonth.setText("One Month");

        dteBeginPeriod.setCanSelect(true);
        dteEndPeriod.setCanSelect(true);
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(radWeek);
        buttonGroup.add(radMonth);

        lblAnd.setText("~");
        lblAnd.setHorizontalAlignment(SwingConstants.CENTER);

        btnLater.setText(">");
        btnEarly.setText("<");

        radWeek.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
//                    if (radWeek.isSelected() == true) {
                    setTheType(PERIOD_ONE_WEEK);
//                    } else if (radMonth.isSelected() == true) {
//                        setTheType(PERIOD_ONE_MONTH);
//                    }
                } else {
                    setTheType(PERIOD_ONE_MONTH);
                }
            }
        });

        dteBeginPeriod.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    setTheDate((Date) dteBeginPeriod.getUICValue());
                }
            }
        });
        dteBeginPeriod.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                setTheDate((Date) dteBeginPeriod.getUICValue());
            }
        });
        dteBeginPeriod.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (isEnabled() && e.getClickCount() == 2) {
                    setTheDate((Date) dteBeginPeriod.getUICValue());
                }
            }
        });

        dteEndPeriod.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    setTheDate((Date) dteEndPeriod.getUICValue());
                }
            }
        });
        dteEndPeriod.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                setTheDate((Date) dteEndPeriod.getUICValue());
            }
        });
        dteEndPeriod.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (isEnabled() && e.getClickCount() == 2) {
                    setTheDate((Date) dteEndPeriod.getUICValue());
                }
            }
        });

        btnEarly.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                early();
            }
        });

        btnLater.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                later();
            }
        });

        this.add(radMonth);
        this.add(radWeek);
        this.add(lblAnd);
        this.add(dteBeginPeriod);
        this.add(dteEndPeriod);
        this.add(btnEarly);
        this.add(btnLater);

    }

    public void setTheDate(Date theDate) {
        if (theDate == null) {
            return;
        }

        if (theDate.equals(this.theDate) == false) {
            this.theDate = theDate;
            resetPeriod();
        }
    }

    public void setTheType(int theType) {
        if (this.theType != theType) {
            this.theType = theType;

            if (theType == PERIOD_ONE_WEEK) {
                radWeek.setSelected(true);
                radMonth.setSelected(false);
            } else if (theType == PERIOD_ONE_MONTH) {
                radWeek.setSelected(false);
                radMonth.setSelected(true);
            }

            resetPeriod();
        }
    }


    //当改变theDate或theType，调用本方法来刷新显示
    protected void resetPeriod() {
        if (theDate == null) {
            return;
        }

        Date beginDate = null;
        Date endDate = null;

        Calendar theCal = Calendar.getInstance();
        theCal.setTime(theDate);
        theCal.set(Calendar.HOUR_OF_DAY, 0);
        theCal.set(Calendar.MINUTE, 0);
        theCal.set(Calendar.SECOND, 0);
        theCal.set(Calendar.MILLISECOND, 0);

        if (this.theType == PERIOD_ONE_WEEK) {
            Calendar beginCal = workCalendar.beginDayOfWeek(theCal, WorkCalendarConstant.SPLIT_WEEK_BY_MONTH_END);
            Calendar endCal = workCalendar.endDayOfWeek(theCal, WorkCalendarConstant.SPLIT_WEEK_BY_MONTH_END);

            beginDate = beginCal.getTime();
            endDate = endCal.getTime();
        } else if (this.theType == PERIOD_ONE_MONTH) {
            Calendar beginCal = workCalendar.beginDayOfMonth(theCal);
            Calendar endCal = workCalendar.endDayOfMonth(theCal);

            beginDate = beginCal.getTime();
            endDate = endCal.getTime();
        }

        this.dteBeginPeriod.setUICValue(beginDate);
        this.dteEndPeriod.setUICValue(endDate);

        if (compareDate(oldBeginDate, beginDate) == false ||
            compareDate(oldEndDate, endDate) == false) {

            oldBeginDate = beginDate;
            oldEndDate = endDate;

            fireDataChanged();
        }
    }

    protected void early(){
        Calendar dc = Calendar.getInstance();
        dc.setTime(theDate);

        Calendar[] bE = null;
        if( PERIOD_ONE_WEEK == getTheType() ){
            bE = workCalendar.getNextBEWeekDay(dc, -1);
        }else if( PERIOD_ONE_MONTH == getTheType() ){
            bE = workCalendar.getNextBEMonthDay(dc, -1);
        }
        if (bE != null) {
            setTheDate(bE[0].getTime());
        }
    }

    protected void later(){
        Calendar dc = Calendar.getInstance();
        dc.setTime(theDate);

        Calendar[] bE = null;
        if( PERIOD_ONE_WEEK == getTheType() ){
            bE = workCalendar.getNextBEWeekDay(dc, 1);
        }else if( PERIOD_ONE_MONTH == getTheType() ){
            bE = workCalendar.getNextBEMonthDay(dc, 1);
        }
        if (bE != null) {
            setTheDate(bE[0].getTime());
        }
    }

    public static boolean compareDate(Date date1, Date date2) {
        int y1 = 0;
        int m1 = 0;
        int d1 = 0;
        int y2 = 0;
        int m2 = 0;
        int d2 = 0;

        Calendar c = Calendar.getInstance();
        if (date1 != null) {
            c.setTime(date1);
            y1 = c.get(Calendar.YEAR);
            m1 = c.get(Calendar.MONTH);
            d1 = c.get(Calendar.DAY_OF_MONTH);
        }
        if (date2 != null) {
            c.setTime(date2);
            y2 = c.get(Calendar.YEAR);
            m2 = c.get(Calendar.MONTH);
            d2 = c.get(Calendar.DAY_OF_MONTH);
        }

        if (y1 == y2 && m1 == m2 && d1 == d2) {
            return true;
        } else {
            return false;
        }
    }

    public Date getBeginPeriod() {
        return (Date) dteBeginPeriod.getUICValue();
    }

    public Date getEndPeriod() {
        return (Date) dteEndPeriod.getUICValue();
    }

    public VWJDate getBeginPeriodComp() {
        return this.dteBeginPeriod;
    }

    public VWJDate getEndPeriodComp() {
        return this.dteEndPeriod;
    }

    public VWJRadioButton getRadWeekComp() {
        return this.radWeek;
    }

    public VWJRadioButton getRadMonthComp() {
        return this.radMonth;
    }

    public int getTheType() {
        return this.theType;
    }

}
