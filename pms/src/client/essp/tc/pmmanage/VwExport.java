package client.essp.tc.pmmanage;

import client.essp.common.view.VWGeneralWorkArea;
import com.wits.util.TestPanel;
import client.essp.tc.common.VWJDatePeriodBase;
import java.util.Date;
import java.util.Calendar;
import c2s.essp.common.calendar.WorkCalendarConstant;
import client.framework.view.vwcomp.VWJLabel;
import java.awt.Rectangle;
import client.framework.common.Global;
import java.awt.event.ActionEvent;
import client.framework.view.vwcomp.IVWPopupEditorEvent;
import com.wits.util.Parameter;
import client.framework.view.common.comMSG;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class VwExport extends VWJDatePeriodBase implements IVWPopupEditorEvent{
    protected VWJLabel lblPeriod = new VWJLabel();
    private Calendar curCal = Calendar.getInstance();
    private Date curBeginDate;
    private Date curEndDate;
    public VwExport(Date begin,Date end) {
        super();
        curBeginDate = begin;
        curEndDate = end;
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        radMonth.setText("By Period");
        dteBeginPeriod.setUICValue(curBeginDate);
        dteEndPeriod.setUICValue(curEndDate);
        curCal.setTime(begin);
        theDate=begin;
    }
    private void jbInit() throws Exception {

        radWeek.setBounds(new Rectangle(20, 20, 79, 12));
        radMonth.setBounds(new Rectangle(20, 32, 79, 12));

        lblPeriod.setText("Period: ");
        lblPeriod.setBounds(new Rectangle(108, 22, 48, 20));

        dteBeginPeriod.setBounds(new Rectangle(144, 22, 76, 20));
        dteEndPeriod.setBounds(new Rectangle(290, 22, 96, 20));

        btnEarly.setBounds(new Rectangle(227, 22, 15, 20));
        btnLater.setBounds(new Rectangle(270, 22, 15, 20));

        lblAnd.setBounds(new Rectangle(245, 31, 28, 10));

        this.add(lblPeriod);
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
            setTheDatePeriod(bE[0].getTime());
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
            setTheDatePeriod(bE[0].getTime());
        }
    }

    public Date getCurBeginDate() {
        return curBeginDate;
    }

    public Date getCurEndDate() {
       return curEndDate;
   }

    public boolean onClickOK(ActionEvent e) {
        return checkDate();
    }
    public boolean onClickCancel(ActionEvent e) {
        return true;
    }

    public void setTheDate(Date theDate) {
        if (theDate == null) {
            return;
        }
        curBeginDate = (Date)dteBeginPeriod.getUICValue();
        curEndDate = (Date)dteEndPeriod.getUICValue();
        if (theDate.equals(this.theDate) == false) {
            this.theDate = theDate;
            if (this.theType == PERIOD_ONE_WEEK) {
            resetPeriod();
            } else {
                checkDate();
            }
        }
    }

    private boolean checkDate() {
        if(curBeginDate == null) {
            comMSG.dispErrorDialog("Please input begin date!");
            return false;
        }
        if(curEndDate == null) {
            comMSG.dispErrorDialog("Please input end date!");
            return false;
        }
        if(curBeginDate.compareTo(curEndDate) > 0) {
            comMSG.dispErrorDialog("begin date should be smaller than end!");
            return false;
        }
        return true;
    }
    public void setTheDatePeriod(Date theDate) {
        if (theDate == null) {
            return;
        }
        curBeginDate = (Date)dteBeginPeriod.getUICValue();
        curEndDate = (Date)dteEndPeriod.getUICValue();
        if (theDate.equals(this.theDate) == false) {
            this.theDate = theDate;
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

        curCal.setTime(theDate);
        curCal.set(Calendar.HOUR_OF_DAY, 0);
        curCal.set(Calendar.MINUTE, 0);
        curCal.set(Calendar.SECOND, 0);
        curCal.set(Calendar.MILLISECOND, 0);

        if (this.theType == PERIOD_ONE_WEEK) {
            Calendar beginCal = workCalendar.beginDayOfWeek(curCal, WorkCalendarConstant.SPLIT_WEEK_BY_MONTH_END);
            Calendar endCal = workCalendar.endDayOfWeek(curCal, WorkCalendarConstant.SPLIT_WEEK_BY_MONTH_END);

            beginDate = beginCal.getTime();
            endDate = endCal.getTime();
        } else if (this.theType == PERIOD_ONE_MONTH) {
            beginDate = curCal.getTime();
            endDate = curCal.getTime();
            beginDate.setDate(1);
            endDate.setMonth(endDate.getMonth()+1);
            endDate.setDate(0);
        }

        this.dteBeginPeriod.setUICValue(beginDate);
        this.dteEndPeriod.setUICValue(endDate);

        if (compareDate(oldBeginDate, beginDate) == false ||
            compareDate(oldEndDate, endDate) == false) {

            oldBeginDate = beginDate;
            oldEndDate = endDate;

            fireDataChanged();
        }
        curBeginDate = beginDate;
        curEndDate = endDate;
    }


    public static void main(String[] args) {
        VwExport vwexport = new VwExport(new Date(),new Date());
        vwexport.setTheDate(Global.todayDate);
        TestPanel.show(vwexport);
    }
}
