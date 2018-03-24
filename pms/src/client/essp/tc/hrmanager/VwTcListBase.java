package client.essp.tc.hrmanager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.table.TableColumn;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.tc.weeklyreport.DtoTcKey;
import c2s.essp.tc.weeklyreport.DtoWeeklyReport;
import c2s.essp.tc.weeklyreport.DtoWeeklyReportSumByHr;
import client.essp.common.TableUitl.TableColumnChooseDisplay;
import client.essp.common.TableUitl.TableColumnWidthSetter;
import client.essp.common.view.VWTableWorkArea;
import client.essp.common.view.VWWorkArea;
import client.essp.tc.common.RowRendererTwoNumber;
import client.framework.common.Constant;
import client.framework.model.VMColumnConfig;
import client.framework.view.common.comMSG;
import client.framework.view.vwcomp.VWJCheckBox;
import client.framework.view.vwcomp.VWJDate;
import client.framework.view.vwcomp.VWJNumber;
import client.framework.view.vwcomp.VWJReal;
import client.framework.view.vwcomp.VWJTable;
import client.framework.view.vwcomp.VWJText;
import com.wits.util.Parameter;
import com.wits.util.TestPanel;
import com.wits.util.comDate;
import client.essp.common.excelUtil.VWJButtonExcel;
import c2s.essp.common.excelUtil.IExcelParameter;
import client.framework.common.Global;
import client.framework.view.vwcomp.VWJPopupEditor;
import client.essp.tc.hrmanager.mail.VwEmployListPopArea;

import c2s.dto.DtoUtil;
import javax.swing.ImageIcon;
import client.image.ComImage;

public class VwTcListBase extends VWTableWorkArea {
    static final String actionIdList = "FTCHrManageWeeklyReportSumOnWeekListAction";
    static final String actionIdUpdate = "FTCHrManageStandardTimecardUpdateAction";
    static final String actionIdLockOff = "FTCHrManageWeeklyReportLockOffAction";
    static final String actionIdExcel = "FTcExcelAction";
    static final String actionIdStatusExcel = "FTcStatusExcelAction";
    static final String actionIdFreeze = "FTCHrManageFreezeAction";

    //cotrol data
    boolean isSaveOk = true;
    boolean isParameterValid = true;

    /**
     * define common data
     */
    private List tcList = new ArrayList();
    private Date beginPeriod;
    private Date endPeriod;
    private Long acntRid;
    private String periodType;
    private Boolean frozen ;

    JButton btnSave;
    JButton btnLoad;
    JButton btnFreeze ;

    VWJButtonExcel btnExcel;

    private VwEmployListPopArea popWin;

    public VwTcListBase() {
        try {

            //table
            model = new VMTableTc(getConfigs());
            model.setDtoType(DtoWeeklyReportSumByHr.class);
            table = new VWJTable(model);
            table.setSortable(true);
            TableColumnWidthSetter.set(table);

            this.add(table.getScrollPane(), null);

            initUI();
            addUICEvent();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    protected Object[][] getConfigs() {
        VWJDate date = new VWJDate();
        date.setCanSelect(true);
        VWJText text = new VWJText();
        VWJReal real = new VWJReal() {
            public void setUICValue(Object value) {
                if (DtoWeeklyReport.BIG_DECIMAL_0.equals(value)) {
                    super.setUICValue(null);
                } else {
                    super.setUICValue(value);
                }
            }
        };
        real.setMaxInputIntegerDigit(4);
        real.setMaxInputDecimalDigit(2);
        real.setCanNegative(true);

        final VWJCheckBox checkBox = new VWJCheckBox();
//        checkBox.addActionListener(new ActionListener(){
//            public void actionPerformed(ActionEvent e){
//                if( checkBox.isSelected() == true ){
//                    actionPerformedLockOff(e);
//                }
//            }
//        });

        VWJNumber num = new VWJNumber();

        Object[][] configs = new Object[][] {
                             {VMTableTc.LOCK_TITLE, "locked", VMColumnConfig.UNEDITABLE, checkBox},
                             {"LoginId", "userId", VMColumnConfig.UNEDITABLE, text},
                             {"Name", "chineseName", VMColumnConfig.UNEDITABLE, text},
                             {"Begin Period", "realBeginPeriod", VMColumnConfig.UNEDITABLE, date},
                             {"End Period", "realEndPeriod", VMColumnConfig.UNEDITABLE, date},
                             {VMTableTc.STANDARD_TITLE, "", VMColumnConfig.UNEDITABLE, real},
                             {VMTableTc.ACTUAL_TITLE, "", VMColumnConfig.UNEDITABLE, real},
                             {VMTableTc.NORMAL_TITLE, "", VMColumnConfig.UNEDITABLE, real},
                             {VMTableTc.VARIENT_TITLE, "", VMColumnConfig.UNEDITABLE, real},
                             {VMTableTc.PAY_TIME_TITLE, "", VMColumnConfig.UNEDITABLE, real},
                             {VMTableTc.OVERTIME_TITLE, "", VMColumnConfig.UNEDITABLE, real},
                             {VMTableTc.LEAVE_ALL_TITLE, "", VMColumnConfig.UNEDITABLE, real},
                             {VMTableTc.LEAVE_HALF_TITLE, "", VMColumnConfig.UNEDITABLE, real, Boolean.TRUE},
                             {VMTableTc.LEAVE_NONE_TITLE, "", VMColumnConfig.UNEDITABLE, real, Boolean.TRUE},
                             {VMTableTc.ABSENT_TITLE, "absent", VMColumnConfig.UNEDITABLE, real, Boolean.TRUE},
                             {VMTableTc.VIALOT_TITLE, "violat", VMColumnConfig.UNEDITABLE, num, Boolean.TRUE},
        };

        return configs;
    }

    private void initUI(){
        //set render
        RowRendererTwoNumber rowRender = new RowRendererTwoNumber();
        String[] hourColumnsWithTwoNumber = new String[]{
                             VMTableTc.ACTUAL_TITLE,
                             VMTableTc.OVERTIME_TITLE,
                             VMTableTc.LEAVE_ALL_TITLE,
                             VMTableTc.LEAVE_HALF_TITLE,
                             VMTableTc.LEAVE_NONE_TITLE,
        };
        for (int i = 0; i < hourColumnsWithTwoNumber.length; i++) {
            try {
                TableColumn col = getTable().getColumn(hourColumnsWithTwoNumber[i]);
                col.setCellRenderer(rowRender);
            } catch (Exception ex) {
            }
        }

        setColumnWidth(VMTableTc.STANDARD_TITLE, 60);
        setColumnWidth(VMTableTc.ACTUAL_TITLE, 70);
        setColumnWidth(VMTableTc.NORMAL_TITLE, 60);
        setColumnWidth(VMTableTc.VARIENT_TITLE, 60);
        setColumnWidth(VMTableTc.PAY_TIME_TITLE, 60);
        setColumnWidth(VMTableTc.OVERTIME_TITLE, 60);
        setColumnWidth(VMTableTc.LEAVE_ALL_TITLE, 70);
        setColumnWidth(VMTableTc.LEAVE_HALF_TITLE, 70);
        setColumnWidth(VMTableTc.LEAVE_NONE_TITLE, 70);
        setColumnWidth(VMTableTc.ABSENT_TITLE, 50);
        setColumnWidth(VMTableTc.VIALOT_TITLE, 50);

    }

    private void setColumnWidth(String columnTitle, int width){
        try {
            TableColumn col = getTable().getColumn(columnTitle);
            col.setPreferredWidth(width);
        } catch (Exception ex) {
        }
    }

    private void addUICEvent() {
        //捕获事件－－－－
        btnSave = this.getButtonPanel().addSaveButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedSave(e);
            }
        });

        btnFreeze = this.getButtonPanel().addButton("lock_unopen.png");
        btnFreeze.setToolTipText("Freeze Attendance Record");
//        this.getButtonPanel().addButton(btnFreeze);
        btnFreeze.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                actionPerformedFreeze();
            }
        });

        btnLoad = this.getButtonPanel().addLoadButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedLoad(e);
            }
        });


        TableColumnChooseDisplay tableColumnChooseDisplay = new TableColumnChooseDisplay(getTable(), this){
            protected void setColumnConfigOfModel() {
                super.setColumnConfigOfModel();
                initUI();
            }
        };
        JButton btnDisp = tableColumnChooseDisplay.getDisplayButton();
        this.getButtonPanel().add(btnDisp);

        btnExcel = new VWJButtonExcel(new IExcelParameter(){
            public String getUrlAddress(){
                StringBuffer sb = new StringBuffer(Global.appRoot);
                sb.append(IExcelParameter.DEFAULT_EXCEL_JSP_ADDRESS);
                sb.append("?");
                sb.append(IExcelParameter.ACTION_ID);
                sb.append("=");
                sb.append(actionIdExcel);
                sb.append("&");
                sb.append("acntRid=");
                sb.append(acntRid.longValue());
                sb.append("&beginPeriod=");
                sb.append(comDate.dateToString(beginPeriod, "yyyyMMdd"));
                sb.append("&endPeriod=");
                sb.append(comDate.dateToString(endPeriod, "yyyyMMdd"));
                sb.append("&periodType=");
                sb.append(periodType);
                return sb.toString();
            }
        });
        btnExcel.setToolTipText("Weekly Report");

        this.getButtonPanel().addButton(btnExcel);
        VWJButtonExcel btnStatus = new VWJButtonExcel(new IExcelParameter(){
            public String getUrlAddress(){
                StringBuffer sb = new StringBuffer(Global.appRoot);
                sb.append(IExcelParameter.DEFAULT_EXCEL_JSP_ADDRESS);
                sb.append("?");
                sb.append(IExcelParameter.ACTION_ID);
                sb.append("=");
                sb.append(actionIdStatusExcel);
                sb.append("&");
                sb.append("acntRid=");
                sb.append(acntRid.longValue());
                sb.append("&beginPeriod=");
                sb.append(comDate.dateToString(beginPeriod, comDate.pattenDate));
                sb.append("&endPeriod=");
                sb.append(comDate.dateToString(endPeriod, comDate.pattenDate));
                return sb.toString();
            }
        });
        btnStatus.setToolTipText("Weekly Report Status");
        this.getButtonPanel().addButton(btnStatus);

        JButton btnMail = this.getButtonPanel().addButton("email.jpg");
        btnMail.setToolTipText("send email");
        btnMail.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                actionPerformedMail();
            }
        });

    }
    //冻结或解冻
    protected void actionPerformedFreeze(){
        InputInfo inputInfo = new InputInfo();

        inputInfo.setActionId(this.actionIdFreeze);
        inputInfo.setInputObj(DtoTcKey.BEGIN_PERIOD, this.beginPeriod);
        inputInfo.setInputObj(DtoTcKey.END_PERIOD, this.endPeriod);
        inputInfo.setInputObj(DtoTcKey.ACNT_RID, this.acntRid);
        inputInfo.setInputObj(DtoTcKey.FROZEN,this.frozen);

        ReturnInfo returnInfo = accessData(inputInfo);
        frozen = (Boolean) returnInfo.getReturnObj(DtoTcKey.FROZEN);
        setFreezeBtnIcon();
    }
    //若该周已冻结,显示"解冻",否则显示"冻结"
    private void setFreezeBtnIcon() {
        if(frozen != null && frozen.booleanValue()){
            ImageIcon img = new ImageIcon(ComImage.getImage("lock_unopen.png"));
            btnFreeze.setIcon(img);
        }else{
            ImageIcon img = new ImageIcon(ComImage.getImage("lock_open.gif"));
            btnFreeze.setIcon(img);
        }
    }

    protected void actionPerformedMail(){
        if(popWin == null)
            popWin = new VwEmployListPopArea();
        Parameter param = new Parameter();
        List copy = null;
        try {
            copy = DtoUtil.list2List(tcList, DtoWeeklyReportSumByHr.class);
        } catch (Exception ex) {
            comMSG.dispErrorDialog("error copy list!",ex);
        }
        //将tcList拷贝一份传如弹出窗口,在弹出窗口以List每个Object的lock属性来判断是否选择了该人员
        param.put(DtoTcKey.WEEKLY_REPORT_LIST,copy);
        param.put(DtoTcKey.BEGIN_PERIOD,beginPeriod);
        param.put(DtoTcKey.END_PERIOD,endPeriod);
        param.put(DtoTcKey.ACNT_RID,acntRid);
        param.put(DtoTcKey.PERIOD_TYPE,periodType);
        popWin.setParameter(param);
        popWin.resetUI();
        VWJPopupEditor pop = new VWJPopupEditor(this.getParentWindow(), "Labor Resource",
                                                popWin, popWin.vwEmployeeList);
        pop.show();
    }

    protected void actionPerformedSave(ActionEvent e) {
        if (checkModified()) {
            if (validateData() == true) {
                saveData();
            }
        }
    }

    protected void actionPerformedLoad(ActionEvent e) {
        resetUI();
    }

    //解锁
    protected void setLocked(boolean isLocked) {
        DtoWeeklyReportSumByHr dto = (DtoWeeklyReportSumByHr) getSelectedData();
        if (dto != null) {
            dto.setLocked(Boolean.toString(isLocked));
            this.getModel().fireTableRowsUpdated(getTable().getSelectedRow(), 0);
        }
    }

    private boolean checkModified() {
        for (int i = 0; i < this.tcList.size(); i++) {
            DtoWeeklyReportSumByHr dtoWeeklyReport = (DtoWeeklyReportSumByHr) tcList.get(i);

            if (dtoWeeklyReport.isChanged() == true) {
                return true;
            }
        }

        return false;
    }


    private boolean validateData() {
        boolean bValid = true;
        StringBuffer msg = new StringBuffer("");
        //modified by xr 2006/03/21
        if(tcList != null){
            for(int i = 0;i < tcList.size() ;i ++){
                DtoWeeklyReportSumByHr dtoWeeklyReport = (DtoWeeklyReportSumByHr) tcList.get(i);
                Date realBegin = dtoWeeklyReport.getRealBeginPeriod();
                Date readEnd = dtoWeeklyReport.getRealEndPeriod();
                if(realBegin != null && readEnd != null){
                    if(realBegin.getTime() > readEnd.getTime()){
                        msg.append("Row["+(i+1)+"]:begin date can not be larger than end date!");
                        bValid = false;
                    }
                    if(beginPeriod.getTime() > realBegin.getTime() ||
                       endPeriod.getTime() < readEnd.getTime()){
                        msg.append("Row["+(i+1)+"]:The period must be between " +
                                   comDate.dateToString(beginPeriod) + " and " +
                                   comDate.dateToString(endPeriod) + "!");
                        bValid = false;
                    }
                }
            }
        }
        if (bValid == false) {
            comMSG.dispErrorDialog(msg.toString());
        }
        return bValid;
    }

    private void saveData() {
        InputInfo inputInfo = createInputInfoForUpdate();

        ReturnInfo returnInfo = accessData(inputInfo);
        if (returnInfo.isError() == false) {
            tcList = (List) returnInfo.getReturnObj(DtoTcKey.WEEKLY_REPORT_LIST);

            this.getTable().setRows(tcList);
        }
    }

    public void setParameter(Parameter param) {
        //judge whether the parameter is changed
        boolean parameterChanged = false;
        Date newBeginPeriod = (Date) (param.get(DtoTcKey.BEGIN_PERIOD));
        Date newEndPeriod = (Date) (param.get(DtoTcKey.END_PERIOD));
        Long newAcntRid = (Long) (param.get(DtoTcKey.ACNT_RID));
        if ( newBeginPeriod == null || newEndPeriod == null || newAcntRid== null ) {
            parameterChanged = true;
        } else {
            if (beginPeriod == null || comDate.compareDate(this.beginPeriod, newBeginPeriod) != 0
                || endPeriod == null || comDate.compareDate(this.endPeriod, newEndPeriod) != 0
            || acntRid == null || acntRid.equals(newAcntRid) == false
                    ) {
                parameterChanged = true;
            }
        }

        this.beginPeriod = (Date) (param.get(DtoTcKey.BEGIN_PERIOD));
        this.endPeriod = (Date) (param.get(DtoTcKey.END_PERIOD));
        this.acntRid = (Long) param.get(DtoTcKey.ACNT_RID);
        this.periodType = (String)param.get(DtoTcKey.PERIOD_TYPE);

        if (this.beginPeriod == null || this.endPeriod == null || acntRid == null) {
            this.isParameterValid = false;
        } else {
            isParameterValid = true;
        }

        if( parameterChanged == true ){
            super.setParameter(param);
            btnExcel.setParameter(new IExcelParameter(){
            public String getUrlAddress(){
                StringBuffer sb = new StringBuffer(Global.appRoot);
                sb.append(IExcelParameter.DEFAULT_EXCEL_JSP_ADDRESS);
                sb.append("?");
                sb.append(IExcelParameter.ACTION_ID);
                sb.append("=");
                sb.append(actionIdExcel);
                sb.append("&");
                sb.append("acntRid=");
                sb.append(acntRid.longValue());
                sb.append("&beginPeriod=");
                sb.append(comDate.dateToString(beginPeriod, "yyyyMMdd"));
                sb.append("&endPeriod=");
                sb.append(comDate.dateToString(endPeriod, "yyyyMMdd"));
                sb.append("&periodType=");
                sb.append(periodType);
                return sb.toString();
            }
        });

        }
    }

    //页面刷新－－－－－
    protected void resetUI() {

        if (this.isParameterValid == false) {
            tcList = new ArrayList();
            getTable().setRows(tcList);
        } else {

            InputInfo inputInfo = createInputInfoForList();
            ReturnInfo returnInfo = accessData(inputInfo);

            if (returnInfo.isError() == false) {
                tcList = (List) returnInfo.getReturnObj(DtoTcKey.
                                                        WEEKLY_REPORT_LIST);
                this.getTable().setRows(tcList);
                frozen = (Boolean) returnInfo.getReturnObj(DtoTcKey.FROZEN);
                setFreezeBtnIcon();
            }
        }
    }

    protected InputInfo createInputInfoForList() {
        InputInfo inputInfo = new InputInfo();

        inputInfo.setActionId(this.actionIdList);
        inputInfo.setInputObj(DtoTcKey.BEGIN_PERIOD, this.beginPeriod);
        inputInfo.setInputObj(DtoTcKey.END_PERIOD, this.endPeriod);
        inputInfo.setInputObj(DtoTcKey.ACNT_RID, this.acntRid);
        return inputInfo;
    }

    protected InputInfo createInputInfoForUpdate() {
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(this.actionIdUpdate);
        inputInfo.setInputObj(DtoTcKey.WEEKLY_REPORT_LIST, tcList);
        inputInfo.setInputObj(DtoTcKey.BEGIN_PERIOD, beginPeriod);
        inputInfo.setInputObj(DtoTcKey.END_PERIOD, endPeriod);
        return inputInfo;
    }

    protected ReturnInfo accessDataT(InputInfo inputInfo) {
        ReturnInfo returnInfo = new ReturnInfo();

        List dtoList = new ArrayList();
        DtoWeeklyReportSumByHr dto1 = new DtoWeeklyReportSumByHr();
        dto1.setLocked("true");
        dto1.setUserId("stone.shi");
        dto1.setBeginPeriod(new Date(105,11,3));
        dto1.setEndPeriod(new Date(105,11,9));
        dto1.setRealBeginPeriod(new Date(105,11,3));
        dto1.setRealEndPeriod(new Date(105,11,9));
        dto1.setStandardHour(new BigDecimal(40));
        dto1.setActualHour(new BigDecimal(40));
        dto1.setActualHourConfirmed(new BigDecimal(30));

        dto1.setOvertimeSum(new BigDecimal(8));
        dto1.setOvertimeSumConfirmed(new BigDecimal(0));

        dto1.setLeaveSum(new BigDecimal(8));
        dto1.setLeaveSumConfirmed(new BigDecimal(0));
        dto1.setLeaveOfPayAll(new BigDecimal(0));
        dto1.setLeaveOfPayAllConfirmed(new BigDecimal(0));
        dto1.setLeaveOfPayHalf(new BigDecimal(0));
        dto1.setLeaveOfPayHalfConfirmed(new BigDecimal(0));
        dto1.setLeaveOfPayNone(new BigDecimal(8));
        dto1.setLeaveOfPayNoneConfirmed(new BigDecimal(0));

        dto1.setAbsent(new BigDecimal(0));
        dto1.setViolat(new Long(99));
        dtoList.add(dto1);

        returnInfo.setReturnObj(DtoTcKey.WEEKLY_REPORT_LIST, dtoList);
        return returnInfo;
    }

    public void saveWorkArea() {
        if (checkModified()) {
            isSaveOk = false;
            if (confirmSaveWorkArea("Do you save the weekly report?") == true) {
                if (validateData() == true) {
                    saveData();
                    this.isSaveOk = true;
                }
            } else {
                isSaveOk = true;
            }
        } else {
            isSaveOk = true;
        }
    }

    public boolean isSaveOk() {
        return this.isSaveOk;
    }

    public static void main(String[] args) {
        VWWorkArea w1 = new VWWorkArea();
        VwTcListBase workArea = new VwTcListBase();

        w1.addTab("Weekly Report", workArea);
        TestPanel.show(w1);

        Parameter param = new Parameter();
        param.put(DtoTcKey.BEGIN_PERIOD, new Date(105,11,3));
        param.put(DtoTcKey.END_PERIOD, new Date(105,11,9));
        param.put(DtoTcKey.ACNT_RID, new Long(1));
        workArea.setParameter(param);
        workArea.refreshWorkArea();
    }

}
