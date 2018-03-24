package client.essp.tc.search;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.table.TableColumn;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.common.mail.DtoMail;
import c2s.essp.tc.weeklyreport.DtoTcKey;
import c2s.essp.tc.weeklyreport.DtoTcSearchCondition;
import c2s.essp.tc.weeklyreport.DtoTcSearchResult;
import c2s.essp.tc.weeklyreport.DtoWeeklyReport;
import client.essp.common.mailUtil.IMailProvider;
import client.essp.common.mailUtil.VWJMailButton;
import client.essp.common.view.VWTableWorkArea;
import client.essp.common.view.VWWorkArea;
import client.framework.common.Constant;
import client.framework.model.VMColumnConfig;
import client.framework.view.common.comMSG;
import client.framework.view.vwcomp.VWJCheckBox;
import client.framework.view.vwcomp.VWJReal;
import client.framework.view.vwcomp.VWJText;
import com.wits.util.Parameter;
import com.wits.util.TestPanel;
import client.framework.view.vwcomp.VWJButton;
import java.awt.event.*;
import client.framework.view.vwcomp.VWJDate;
import client.essp.common.loginId.VWJLoginId;

public class VwTcSearchResult extends VWTableWorkArea{
    static final String actionIdSearchByPm = "FTCSearchByPmAction";
    static final String actionIdSearchByDm = "FTCSearchByDmAction";
    static final String actionIdSendMail = "FTCSendMail";

    /**
     * define common data
     */
    private List tcList = new ArrayList();
    DtoTcSearchCondition dtoCondition = new DtoTcSearchCondition();
    boolean isParameterValid = true;

    JButton btnLoad;
    JButton btnMail;
    JButton btnChooseAll;
//    JButton btnChooseNone;
//    JButton btnChooseUnLocked;
//    JButton btnChooseLocked;
    boolean chooseAllFlag = true;
//    boolean chooseNoneFlag = true;
//    boolean chooseUnLockedFlag = true;
//    boolean chooseLockedFlag = true;

    DtoMail dtoMail;

    public VwTcSearchResult() {
        try {
            VWJText text = new VWJText();
            VWJReal real = new VWJReal();
            VWJDate date = new VWJDate();
            real.setMaxInputIntegerDigit(2);
            real.setMaxInputDecimalDigit(1);

            Object[][] configs = new Object[][] {
                                 {"", "selected", VMColumnConfig.EDITABLE, new VWJCheckBox()},
                                 {"Worker", "user", VMColumnConfig.UNEDITABLE, new VWJLoginId()},
                                 {"Begin Period", "BeginPeriod", VMColumnConfig.UNEDITABLE, date},
                                 {"End Period", "EndPeriod", VMColumnConfig.UNEDITABLE, date},
                                 {"Status", "status", VMColumnConfig.UNEDITABLE, text},
            };

            super.jbInit(configs, DtoTcSearchResult.class);

            getTable().setSortable(true);

            TableColumn columnSelect = getTable().getColumnModel().getColumn(0);
            columnSelect.setPreferredWidth(40);
            columnSelect.setMaxWidth(40);
            columnSelect.setMinWidth(40);
        } catch (Exception e) {
            e.printStackTrace();
        }

        addUICEvent();
    }

    private void addUICEvent() {
        btnChooseAll = new VWJButton("All");
        btnChooseAll.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedChooseAll();
            }
        });

        /*
        btnChooseNone = new VWJButton("All None");
        btnChooseNone.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedChooseNone();
            }
        });
        btnChooseUnLocked = new VWJButton("All UnLocked");
        btnChooseUnLocked.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedChooseUnLocked();
            }
        });
        btnChooseLocked = new VWJButton("All Locked");
        btnChooseLocked.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedChooseLocked();
            }
                 });
                 this.getButtonPanel().add(btnChooseNone);
                 this.getButtonPanel().add(btnChooseUnLocked);
                 this.getButtonPanel().add(btnChooseLocked);

        */

        this.getButtonPanel().add(btnChooseAll);

        btnMail = new VWJMailButton(){
            protected void sendMail(){
                sendMailTc();
            }
        };
        this.getButtonPanel().add(btnMail);


        btnLoad = this.getButtonPanel().addLoadButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedLoad(e);
            }
        });
    }

    public void actionPerformedLoad(ActionEvent e) {
        resetUI();
    }

    public void actionPerformedChooseAll(){
        for (Iterator iter = tcList.iterator(); iter.hasNext(); ) {
            DtoTcSearchResult result = (DtoTcSearchResult) iter.next();

            result.setSelected(Boolean.toString(chooseAllFlag));
        }

        getModel().fireTableDataChanged();

        chooseAllFlag = chooseAllFlag ? false : true;
    }

//    public void actionPerformedChooseNone(){
//        for (Iterator iter = tcList.iterator(); iter.hasNext(); ) {
//            DtoTcSearchResult result = (DtoTcSearchResult) iter.next();
//
//            if (DtoWeeklyReport.STATUS_NONE.equals(result.getStatus()) == true
//                    || DtoWeeklyReport.STATUS_REJECTED.equals(result.getStatus()) == true) {
//                result.setSelected(Boolean.toString(chooseNoneFlag));
//            }
//        }
//
//        getModel().fireTableDataChanged();
//
//        chooseNoneFlag = chooseNoneFlag ? false : true;
//    }
//
//    public void actionPerformedChooseUnLocked(){
//        for (Iterator iter = tcList.iterator(); iter.hasNext(); ) {
//            DtoTcSearchResult result = (DtoTcSearchResult) iter.next();
//
//            if (DtoWeeklyReport.STATUS_UNLOCK.equals(result.getStatus()) == true
//                    || DtoWeeklyReport.STATUS_REJECTED.equals(result.getStatus()) == true) {
//                result.setSelected(Boolean.toString(chooseUnLockedFlag));
//            }
//        }
//
//        getModel().fireTableDataChanged();
//
//        chooseUnLockedFlag = chooseUnLockedFlag ? false : true;
//    }
//
//    public void actionPerformedChooseLocked(){
//        for (Iterator iter = tcList.iterator(); iter.hasNext(); ) {
//            DtoTcSearchResult result = (DtoTcSearchResult) iter.next();
//
//            if (DtoWeeklyReport.STATUS_LOCK.equals(result.getStatus()) == true) {
//                result.setSelected(Boolean.toString(chooseLockedFlag));
//            }
//        }
//
//        getModel().fireTableDataChanged();
//
//        chooseLockedFlag = chooseLockedFlag ? false : true;
//    }



    protected void sendMailTc() {
        List mailUserList = new ArrayList();

        StringBuffer userIds = new StringBuffer();
        StringBuffer msg = new StringBuffer();
        for (Iterator iter = tcList.iterator(); iter.hasNext(); ) {
            DtoTcSearchResult result = (DtoTcSearchResult) iter.next();
            if (Boolean.valueOf(result.getSelected()).booleanValue()) {

                mailUserList.add(result);

                if( DtoWeeklyReport.STATUS_CONFIRMED.equals( result.getStatus() ) ){
                    msg.append("The " + result.getUser() + "'s weekly report on week[" +result.getPeriod()+ "] is already confirmed.\r\n");
                }
            }
        }
        if( msg.length() > 0 ){
            msg.append("Will filter the confirmed user.\r\n");
            msg.append("Continue?");
            int i = comMSG.dispConfirmDialog(msg.toString());
            if( i == Constant.CANCEL ){
                return;
            }
        }

        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(actionIdSendMail);
        inputInfo.setInputObj(DtoTcKey.MAIL_USER_LIST, mailUserList);

        ReturnInfo returnInfo = accessData(inputInfo);
        if (returnInfo.isError() == false) {
            comMSG.dispMessageDialog("Send mail ok.");
        }
    }

    public void setParameter(Parameter param) {
        super.setParameter(param);

        this.dtoCondition = (DtoTcSearchCondition) param.get(DtoTcKey.SEARCH_CONDITION);
        if( dtoCondition == null ){
            isParameterValid = false;
        }
    }

    //Ò³ÃæË¢ÐÂ£­£­£­£­£­
    protected void resetUI() {
        if (isParameterValid == false) {
            tcList = new ArrayList();
            getTable().setRows(tcList);
        } else {
            InputInfo inputInfo = createInputInfoForList();

            ReturnInfo returnInfo = accessData(inputInfo);

            if (returnInfo.isError() == false) {
                tcList = (List) returnInfo.getReturnObj(DtoTcKey.SEARCH_RESULT);

                getTable().setRows(tcList);
//                chooseLockedFlag = true;
//                chooseNoneFlag = true;
//                chooseUnLockedFlag = true;
                chooseAllFlag = true;
            }
        }
    }

    protected InputInfo createInputInfoForList() {
        InputInfo inputInfo = new InputInfo();

        if( dtoCondition.getAcntRid() != null ){
            inputInfo.setActionId(actionIdSearchByPm);
        }else{
            inputInfo.setActionId(actionIdSearchByDm);
        }

        inputInfo.setInputObj(DtoTcKey.SEARCH_CONDITION, this.dtoCondition);

        return inputInfo;
    }

    protected ReturnInfo accessDataT(InputInfo inputInfo) {
        ReturnInfo returnInfo = new ReturnInfo();

        List dtoList = new ArrayList();
        DtoTcSearchResult dto1 = new DtoTcSearchResult();
        dto1.setUser("x");
        dto1.setStatus("locked");
        dtoList.add(dto1);

        DtoTcSearchResult dto2 = new DtoTcSearchResult();
        dto2.setUser("y");
        dto2.setStatus("None");
        dtoList.add(dto2);

        DtoTcSearchResult dto3 = new DtoTcSearchResult();
        dto3.setUser("z");
        dto3.setStatus("Waiting");
        dtoList.add(dto3);

        returnInfo.setReturnObj(DtoTcKey.SEARCH_RESULT, dtoList);
        return returnInfo;
    }

    public static void main(String args[]) {
        VWWorkArea w1 = new VWWorkArea();
        VWWorkArea workArea = new VwTcSearchResult();

        w1.addTab("Timecard", workArea);
        TestPanel.show(w1);

        List acntRidList = new ArrayList();
        acntRidList.add(new Long(1));
        Parameter param = new Parameter();
        DtoTcSearchCondition dto = new DtoTcSearchCondition();
        dto.setBeginPeriod(new Date(105,11,3));
        dto.setEndPeriod(new Date(105,11,9));
        param.put(DtoTcKey.SEARCH_CONDITION, dto);
        param.put(DtoTcKey.ACCOUNT_RID_LIST, acntRidList);
        workArea.setParameter(param);
        workArea.refreshWorkArea();
    }
}
