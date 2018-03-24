package client.essp.tc.hrmanager.mail;

import java.awt.Dimension;

import c2s.essp.tc.weeklyreport.DtoWeeklyReport;
import c2s.essp.tc.weeklyreport.DtoWeeklyReportSumByHr;
import client.essp.common.TableUitl.TableColumnWidthSetter;
import client.essp.common.view.VWTableWorkArea;
import client.framework.model.VMColumnConfig;
import client.framework.view.vwcomp.VWJCheckBox;
import client.framework.view.vwcomp.VWJDate;
import client.framework.view.vwcomp.VWJReal;
import client.framework.view.vwcomp.VWJTable;
import client.framework.view.vwcomp.VWJText;
import client.essp.tc.hrmanager.*;
import client.framework.view.vwcomp.IVWPopupEditorEvent;
import java.awt.event.ActionEvent;
import c2s.essp.tc.weeklyreport.DtoTcKey;
import java.util.List;
import com.wits.util.Parameter;
import java.util.ArrayList;
import java.math.BigDecimal;
import java.awt.event.ActionListener;
import client.framework.view.vwcomp.VWJButton;
import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import client.framework.view.common.comMSG;
import java.util.Date;

public class VwEmployeeList extends VWTableWorkArea implements IVWPopupEditorEvent{
    static final String actionIdEmail = "FTCHrManageWeeklyReportMailAction";

    private List tcList;
    private VWJButton btnChooseAll;
    private VWJButton btnChooseVarient;
    private VWJButton btnSend;
    private boolean chooseAll = false;
    private boolean chooseVarient = false;
    private Date beginPeriod;
    private Date endPeriod;
    private Long acntRid;
    private String periodType;
    public VwEmployeeList() {
        try {
            jbInit();
            addUICEvent();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * addUICEvent
     */
    private void addUICEvent() {
        btnChooseAll = new VWJButton("All");
        btnChooseAll.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedChooseAll();
            }
        });
        this.getButtonPanel().addButton(btnChooseAll);
        btnChooseVarient = new VWJButton("Varient");
        btnChooseVarient.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                actionPerformedChooseVarient();
            }
        });
        this.getButtonPanel().addButton(btnChooseVarient);
        btnSend = new VWJButton("Send");
        btnSend.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                actionPerformedSend();
            }
        });
        this.getButtonPanel().addButton(btnSend);
    }
    //全选或取消全选
    private void actionPerformedChooseAll(){
        if(tcList == null || tcList.size() == 0)
            return;
        for(int i  =0 ;i < tcList.size();i ++){
            DtoWeeklyReportSumByHr dto = (DtoWeeklyReportSumByHr) tcList.get(i);
            dto.setLocked(Boolean.toString(chooseAll));
        }
        chooseAll = !chooseAll;
        getModel().fireTableDataChanged();
    }
    //选择有差值和或取消选择
    private void actionPerformedChooseVarient(){
        for(int i  =0 ;i < tcList.size();i ++){
            DtoWeeklyReportSumByHr dto = (DtoWeeklyReportSumByHr) tcList.get(i);
            BigDecimal varientHours = dto.getVarientHours();
            if( varientHours != null && varientHours.doubleValue() < 0D )
                dto.setLocked(Boolean.toString(chooseVarient));
        }
        chooseVarient = !chooseVarient;
        getModel().fireTableDataChanged();
    }
    //发送Mail
    private void actionPerformedSend(){
        InputInfo inputInfo = inputInfo = new InputInfo();
        inputInfo.setActionId(this.actionIdEmail);
        List chosen = new ArrayList();
        for(int i = 0;i < tcList.size() ;i ++){
            DtoWeeklyReportSumByHr dto = (DtoWeeklyReportSumByHr) tcList.get(i);
            Boolean isChosen = new Boolean(dto.getLocked());
            if(isChosen.booleanValue())
                chosen.add(dto);
        }
        if(chosen.size() == 0){
            comMSG.dispMessageDialog("Please choose employee to send mail!");
            return;
        }
        inputInfo.setInputObj(DtoTcKey.WEEKLY_REPORT_LIST,chosen);
        inputInfo.setInputObj(DtoTcKey.BEGIN_PERIOD,beginPeriod);
        inputInfo.setInputObj(DtoTcKey.END_PERIOD,endPeriod);
        inputInfo.setInputObj(DtoTcKey.ACNT_RID,acntRid);
        inputInfo.setInputObj(DtoTcKey.PERIOD_TYPE,periodType);
        ReturnInfo returnInfo = accessData(inputInfo);
        if (returnInfo.isError() == false) {
            comMSG.dispMessageDialog("Send mail successfully!");
        }
    }
    protected void jbInit() throws Exception {
        this.setPreferredSize(new Dimension(700, 260));
        Object[][] configs = getConfig();
        model = new VMTableTc(configs);

        model.setDtoType(DtoWeeklyReportSumByHr.class);
        table = new VWJTable(model);
        table.setSortable(true);
        TableColumnWidthSetter.set(table);

        this.add(table.getScrollPane(), null);

    }

    private Object[][] getConfig() {
        VWJDate date = new VWJDate();
        VWJText text = new VWJText();
        VWJReal real = new VWJReal() {
            public void setUICValue(Object value) {
                if (DtoWeeklyReport.BIG_DECIMAL_0.equals(value) ||
                        !(value instanceof BigDecimal)) {
                    super.setUICValue(null);
                } else {
                    super.setUICValue(value);
                }
            }
        };
        real.setMaxInputIntegerDigit(4);
        real.setMaxInputDecimalDigit(2);
        real.setCanNegative(true);
        VWJCheckBox checkBox = new VWJCheckBox();
        Object[][] configs = new Object[][] {
                             {"Check" ,"locked", VMColumnConfig.EDITABLE, checkBox},
                             {"LoginId", "userId", VMColumnConfig.UNEDITABLE, text},
                             {"Name", "chineseName", VMColumnConfig.UNEDITABLE, text},
                             {"Begin Period", "realBeginPeriod", VMColumnConfig.UNEDITABLE, date},
                             {"End Period", "realEndPeriod", VMColumnConfig.UNEDITABLE, date},
                             {VMTableTc.STANDARD_TITLE, "", VMColumnConfig.UNEDITABLE, real},
                             {VMTableTc.ACTUAL_TITLE, "", VMColumnConfig.UNEDITABLE, real},
                             {VMTableTc.NORMAL_TITLE, "", VMColumnConfig.UNEDITABLE, real},
                             {VMTableTc.VARIENT_TITLE, "", VMColumnConfig.UNEDITABLE, real},
                             {VMTableTc.PAY_TIME_TITLE, "", VMColumnConfig.UNEDITABLE, real}
        };
        return configs;
    }

    public void setParameter(Parameter param){
        super.setParameter(param);
        this.beginPeriod = (Date) (param.get(DtoTcKey.BEGIN_PERIOD));
        this.endPeriod = (Date) (param.get(DtoTcKey.END_PERIOD));
        this.acntRid = (Long) param.get(DtoTcKey.ACNT_RID);
        this.periodType = (String)param.get(DtoTcKey.PERIOD_TYPE);
        tcList = (List) param.get(DtoTcKey.WEEKLY_REPORT_LIST);
    }
    public void resetUI(){
        if(tcList == null)
            tcList = new ArrayList();
        this.getTable().setRows(tcList);
        //先取消所有选择的人
        actionPerformedChooseAll();
    }

    public boolean onClickOK(ActionEvent e) {
        return true;
    }

    public boolean onClickCancel(ActionEvent e) {
        return true;
    }
}
