package client.essp.tc.hrmanager;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.common.calendar.WorkCalendar;
import c2s.essp.common.calendar.WrokCalendarFactory;
import c2s.essp.tc.weeklyreport.DtoTcKey;
import client.essp.common.view.VWGeneralWorkArea;
import client.essp.tc.common.VWJDatePeriod2;
import client.framework.model.VMComboBox;
import client.framework.view.event.DataChangedListener;
import client.framework.view.vwcomp.VWJComboBox;
import client.framework.view.vwcomp.VWJDispReal;
import client.framework.view.vwcomp.VWJLabel;
import com.wits.util.TestPanel;
import c2s.essp.tc.weeklyreport.TcConstant;
import client.framework.common.Global;

public class VwPeriod extends VWGeneralWorkArea{
    final static String actionIdInitAccountList = "FTCHrManageInitAccountListAction";

    protected VWJComboBox cmbAccount = new VWJComboBox();
    protected VWJLabel lblAccount = new VWJLabel();
    VWJLabel lblPeriod = new VWJLabel();
    VWJLabel lblStandardTc = new VWJLabel();

    public VWJDatePeriod2 dtePeriod = new VWJDatePeriod2();
    VWJDispReal dspStandardTc = new VWJDispReal();

    Long acntRid = null;

    public VwPeriod() {
        try {
            jbInit();
            addUICEvent();
            initUI();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void jbInit()throws Exception{
        this.setLayout(null);
        this.setMinimumSize(new Dimension( -1, 30));
        this.setPreferredSize(new Dimension( -1, 30));
        this.setMaximumSize(new Dimension( -1, 30));

        lblAccount.setText("Account:");
        lblAccount.setBounds(new Rectangle(10, 8, 59, 20));
        cmbAccount.setBounds(new Rectangle(59, 8, 178, 20));

        lblPeriod.setText("Period:");
        lblPeriod.setBounds(new Rectangle(246, 6, 50, 24));
        dtePeriod.setBounds(new Rectangle(289, 6, 283, 24));

        lblStandardTc.setText("Standard Hours:");
        lblStandardTc.setBounds(new Rectangle(600, 6, 96, 24));
        dspStandardTc.setBounds(new Rectangle(690, 8, 47, 20));
        dspStandardTc.setMaxInputDecimalDigit(1);
        dspStandardTc.setMaxInputIntegerDigit(4);

        this.add(lblAccount);
        this.add(lblStandardTc);
        this.add(cmbAccount);
        this.add(dspStandardTc);
        this.add(lblPeriod);
        this.add(dtePeriod);

        cmbAccount.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    itemStateChangedAccount();
                }
            }
        });

        this.dtePeriod.addDataChangedListener(new DataChangedListener() {
            public void processDataChanged() {
                //日期选择
                fireDataChanged();
            }
        });
    }

    private void addUICEvent(){
        this.dtePeriod.addDataChangedListener(new DataChangedListener(){
            public void processDataChanged(){
                caculateStandardTc();

                fireDataChanged();
            }
        });
    }

    private void caculateStandardTc(){
        Date beginPeriod = dtePeriod.getBeginPeriod();
        Date endPeriod =  dtePeriod.getEndPeriod();
        if( beginPeriod != null && endPeriod != null ){
            Calendar c = Calendar.getInstance();
            c.setTime(beginPeriod);

            Calendar d = Calendar.getInstance();
            d.setTime(endPeriod);

            WorkCalendar workCalendar = WrokCalendarFactory.clientCreate();
            int numOfDay = workCalendar.getWorkDayNum(c, d);
            this.dspStandardTc.setValue(numOfDay * TcConstant.STANDARD_HOUR_ONE_DAY);
        }
    }

    private void initUI() {
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(actionIdInitAccountList);
        ReturnInfo returnInfo = accessData(inputInfo);
        if (returnInfo.isError() == false) {
            List acntRidList = (List) returnInfo.getReturnObj(DtoTcKey.ACCOUNT_RID_LIST);
            List acntNameList = (List) returnInfo.getReturnObj(DtoTcKey.ACCOUNT_NAME_LIST);
            //Long acntRid = (Long) returnInfo.getReturnObj(DtoTcKey.ACNT_RID);

            String[] names = new String[acntNameList.size()];
            for (int i = 0; i < acntNameList.size(); i++) {
                names[i] = (String) acntNameList.get(i);
            }

            VMComboBox vmComboBox = VMComboBox.toVMComboBox(names, acntRidList.toArray());
            this.cmbAccount.setModel(vmComboBox);

            //初始设置时不触发事件
            if (acntRidList.size() > 0) {
                this.acntRid = (Long)acntRidList.get(0);
                this.cmbAccount.setUICValue(acntRid);
            } else {
                this.acntRid = null;
                this.cmbAccount.setUICValue(null);
            }
        }
    }

    public void reInitUI(){
        initUI();
    }

    protected void itemStateChangedAccount() {
        Long selectAcnt = (Long) cmbAccount.getUICValue();
        if (selectAcnt != null && selectAcnt.equals(acntRid) == false) {
            this.acntRid = selectAcnt;
            fireDataChanged();
        }
    }

    public Long getSelectAcnt() {
        return this.acntRid;
    }


    public static void main(String args[]){
        VwPeriod p = new VwPeriod();
        p.dtePeriod.setTheDate(Global.todayDate);
        TestPanel.show(p);
    }
}
