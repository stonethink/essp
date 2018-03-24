package client.essp.tc.search;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.SwingConstants;

import c2s.essp.tc.weeklyreport.DtoTcSearchCondition;
import client.essp.common.view.VWGeneralWorkArea;
import client.framework.view.vwcomp.VWJCheckBox;
import client.framework.view.vwcomp.VWJDate;
import client.framework.view.vwcomp.VWJLabel;
import client.framework.view.vwcomp.VWUtil;
import com.wits.util.TestPanel;
import c2s.essp.common.calendar.WrokCalendarFactory;
import c2s.essp.common.calendar.WorkCalendar;
import c2s.essp.common.calendar.WorkCalendarConstant;
import client.framework.common.Global;
import com.wits.util.comDate;
import client.framework.view.common.comMSG;

public class VwTcSearchConditionBase extends VWGeneralWorkArea{

    VWJLabel lblPeriod = new VWJLabel();

    VWJLabel lblStatus = new VWJLabel();
    VWJCheckBox chkNone = new VWJCheckBox();
    VWJCheckBox chkLocked = new VWJCheckBox();
    VWJCheckBox chkNotLocked = new VWJCheckBox();
    VWJCheckBox chkRejected = new VWJCheckBox();
    VWJCheckBox chkConfirmed = new VWJCheckBox();

    VWJDate dteBeginPeriod = new VWJDate();
    VWJDate dteEndPeriod = new VWJDate();
    VWJLabel lblAnd = new VWJLabel();

    public VwTcSearchConditionBase() {
        try {
            jbInit();

            Date todayDate = Global.todayDate;
            WorkCalendar workCalendar = WrokCalendarFactory.clientCreate();
            Calendar theCal = Calendar.getInstance();
            theCal.setTime(todayDate);

            Calendar beginCal = workCalendar.beginDayOfWeek(theCal, WorkCalendarConstant.SPLIT_WEEK_BY_MONTH_END);
            beginCal.set(Calendar.HOUR_OF_DAY, 0);
            beginCal.set(Calendar.MINUTE, 0);
            beginCal.set(Calendar.SECOND, 0);
            beginCal.set(Calendar.MILLISECOND, 0);
            Calendar endCal = workCalendar.endDayOfWeek(theCal, WorkCalendarConstant.SPLIT_WEEK_BY_MONTH_END);
            endCal.set(Calendar.HOUR_OF_DAY, 0);
            endCal.set(Calendar.MINUTE, 0);
            endCal.set(Calendar.SECOND, 0);
            endCal.set(Calendar.MILLISECOND, 0);

            Date newBeginDate = beginCal.getTime();
            Date newEndDate = endCal.getTime();
            dteBeginPeriod.setUICValue(newBeginDate);
            dteEndPeriod.setUICValue(newEndDate);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void jbInit()throws Exception{
        this.setLayout(null);

        lblPeriod.setText("Period");
        lblPeriod.setBounds(new Rectangle(26, 42, 100, 20));

        dteBeginPeriod.setBounds(new Rectangle(130, 42, 100, 20));
        dteBeginPeriod.setCanSelect(true);
        lblAnd.setBounds(new Rectangle(230, 44, 40, 20));
        lblAnd.setText("~");
        lblAnd.setHorizontalAlignment(SwingConstants.CENTER);
        dteEndPeriod.setBounds(new Rectangle(270, 42, 100, 20));
        dteEndPeriod.setCanSelect(true);

        lblStatus.setText("Status");
        lblStatus.setBounds(new Rectangle(26, 78, 100, 20));
        chkNone.setText("None");
        chkNone.setHorizontalAlignment(SwingConstants.LEFT);
        chkNone.setBounds(new Rectangle(130, 78, 52, 20));
        chkNotLocked.setText("Not Locked");
        chkNotLocked.setHorizontalAlignment(SwingConstants.LEFT);
        chkNotLocked.setBounds(new Rectangle(180, 78, 85, 20));
        chkLocked.setText("Locked");
        chkLocked.setHorizontalAlignment(SwingConstants.LEFT);
        chkLocked.setBounds(new Rectangle(264, 78, 64, 20));

        chkRejected.setText("Rejected");
        chkRejected.setHorizontalAlignment(SwingConstants.LEFT);
        chkRejected.setBounds(new Rectangle(327, 78, 85, 20));

        chkConfirmed.setText("Confirmed");
        chkConfirmed.setHorizontalAlignment(SwingConstants.LEFT);
        chkConfirmed.setBounds(new Rectangle(412, 78, 85, 20));

        this.add(lblPeriod);
        this.add(lblStatus);
        this.add(chkNone);
        this.add(dteBeginPeriod);
        this.add(dteEndPeriod);
        this.add(lblAnd);
        this.add(chkNotLocked);
        this.add(chkRejected);
        this.add(chkConfirmed);
        this.add(chkLocked);
    }

    protected void setUIComponentName() {
        dteBeginPeriod.setName("beginPeriod");
        dteEndPeriod.setName("endPeriod");
        chkNone.setName("noneStatus");
        chkNotLocked.setName("unLockedStatus");
        chkLocked.setName("lockedStatus");
        chkRejected.setName("rejectedStatus");
        chkConfirmed.setName("confirmedStatus");
    }

    protected List getCompList(){
        List compList = new ArrayList();
        compList.add(dteBeginPeriod);
        compList.add(dteEndPeriod);
        compList.add(chkNone);
        compList.add(chkNotLocked);
        compList.add(chkLocked);
        compList.add(chkRejected);
        compList.add(chkConfirmed);
        return compList;
    }

    public boolean getCondition(DtoTcSearchCondition dto){
        VWUtil.convertUI2Dto(dto, this);
        Date begin=(Date)this.dteBeginPeriod.getUICValue();
        Date end=(Date)this.dteEndPeriod.getUICValue();
        Calendar c = Calendar.getInstance();
        long b = 0;
        long e = 0;
        if( begin != null ){
            c.setTime(begin);
            c.set(Calendar.HOUR_OF_DAY, 0);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);
            c.set(Calendar.MILLISECOND, 0);
            dto.setBeginPeriod(c.getTime());

            b = c.getTimeInMillis();
        }else{
            comMSG.dispErrorDialog("Must input begin period.");
            return false;
        }
        if( end != null ){
            c.setTime(end);
            c.set(Calendar.HOUR_OF_DAY, 0);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);
            c.set(Calendar.MILLISECOND, 0);
            dto.setEndPeriod(c.getTime());

            e = c.getTimeInMillis();
        }else{
            comMSG.dispErrorDialog("Must input end period.");
            return false;
        }

        if( b > e ){
            comMSG.dispErrorDialog("The begin period > end period.");
            return false;
        }

        return true;
    }

    public static void main(String args[]){
        TestPanel.show(new VwTcSearchConditionBase());
    }
}
