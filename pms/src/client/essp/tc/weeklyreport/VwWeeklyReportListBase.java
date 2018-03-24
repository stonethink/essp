package client.essp.tc.weeklyreport;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.table.TableColumn;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.tc.weeklyreport.DtoHoursOnWeek;
import c2s.essp.tc.weeklyreport.DtoTcKey;
import c2s.essp.tc.weeklyreport.DtoWeeklyReport;
import client.essp.common.view.VWTableWorkArea;
import client.essp.tc.common.TableListener;
import client.framework.common.Constant;
import client.framework.view.common.comMSG;
import client.framework.view.event.RowSelectedListener;
import client.framework.view.vwcomp.IVWPopupEditorEvent;
import client.framework.view.vwcomp.VWJPopupEditor;
import client.framework.view.vwcomp.VWJTable;
import com.wits.util.Parameter;
import c2s.essp.tc.weeklyreport.DtoWeeklyReportSumByHr;
import client.framework.view.vwcomp.VWJLabel;
import com.wits.util.comDate;
import java.awt.Rectangle;
import javax.swing.SwingConstants;

public class VwWeeklyReportListBase extends VWTableWorkArea implements IVWPopupEditorEvent {
    public VwWeeklyReportListBase() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    static final String actionIdList = "FTCWeeklyReportListAction";
    static final String actionIdUpdate = "FTCWeeklyReportUpdateAction";
    static final String actionIdDelete = "FTCWeeklyReportDeleteAction";
    static final String actionIdLock = "FTCWeeklyReportLockAction";
    static final String actionIdInit = "FTCWeeklyReportInitFromDailyreportAction";
    static final String actionIdCopy = "FTCWeeklyReportCopyAction";
    static final String actionIdSend = "FTCWeeklyReportSendAction";
    //cotrol data
    boolean isSaveOk = true;
    boolean isParameterValid = true;
    boolean isLocked = false;
    boolean hasRejected = false;
    private boolean isDataEditable = false;
    String confirmStatus = null;

    /**
     * define common data
     */
    private List weeklyReportList = new ArrayList();
    private boolean[] workDayBitmap = new boolean[7];
    private Date beginPeriod;
    private Date endPeriod;
    private String userId;
    private StringBuffer checkMessage;//Warning和Error信息
    private StringBuffer errorMessage;//Error信息
    protected VWJLabel dteBeginPeriod = new VWJLabel();
    protected VWJLabel dteEndPeriod = new VWJLabel();
    protected VWJLabel lblnull = new VWJLabel();
    VWJLabel lblAnd = new VWJLabel();


    JButton btnSave;
    JButton btnDel;
    JButton btnLoad;
    JButton btnAdd;
    JButton btnUpdate;
    JButton btnLock;
    JButton btnInit;
    JButton btnCopy;
    JButton btnUnLock;
    VwBelongTo vwBelongTo = null;
    VwWeeklyReportGeneral vwWeeklyReportGeneral = null;
    VwWeeklyReportGeneral vwWeeklyReportGeneralView = null;


    //设置表格的外观
    protected void initUI() {
        try {
            model.setDtoType(DtoWeeklyReport.class);
            this.add(table.getScrollPane(), null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //不容许调整行的顺序
        table.getTableHeader().setReorderingAllowed(false);

        for (int i = 0; i < VMTableWeeklyReport.WEEK_TITLE.length; i++) {
            TableColumn weekColumn = getTable().getColumn(VMTableWeeklyReport.WEEK_TITLE[i]);
            weekColumn.setPreferredWidth(55);
            weekColumn.setMaxWidth(60);
        }

        TableColumn sumColumn = getTable().getColumn("Sum");
        sumColumn.setPreferredWidth(65);
        sumColumn.setMaxWidth(70);

        getTable().getScrollPane().setBorder(null);

        this.setMinimumSize(new Dimension( -1, this.getMinmizeTableHeight()));
        this.setPreferredSize(new Dimension( -1, this.getPreferredTableHeight()));
        this.setMaximumSize(new Dimension( -1, this.getMaxmizeTableHeight()));

        addUICEvent();
        setButtonVisible();
        setEnableModel();
    }

    protected void setColumnnWidth(String columnTitle, int width){
        TableColumn weekColumn = getTable().getColumn(columnTitle);
        weekColumn.setPreferredWidth(width);
    }

    private void addUICEvent() {
        //捕获事件－－－－

        this.getButtonPanel().add(dteBeginPeriod);
        this.getButtonPanel().add(lblAnd);
        this.getButtonPanel().add(dteEndPeriod);
        this.getButtonPanel().add(lblnull);
        btnInit = this.getButtonPanel().addButton("init.gif");
        btnInit.setToolTipText("Init weekly report from daily report");
        btnInit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedInit(e);
            }
        });

        btnCopy = this.getButtonPanel().addButton("copy.png");
        btnCopy.setToolTipText("Copy from last week");
        btnCopy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedCopy(e);
            }
        });

        btnAdd = this.getButtonPanel().addAddButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedAdd(e);
            }
        });

        btnUpdate = this.getButtonPanel().addEditButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedUpdate(e);
            }
        });

        btnDel = this.getButtonPanel().addDelButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedDel(e);
            }
        });

        btnSave = this.getButtonPanel().addSaveButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedSave(e);
            }
        });

        btnLock = this.getButtonPanel().addButton("lock_unopen.png");
        btnLock.setToolTipText("Lock weekly report");
        btnLock.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedLock(e);
            }
        });

        btnUnLock = this.getButtonPanel().addButton("lock_open.gif");
        btnUnLock.setToolTipText("UnLock weekly report");
        btnUnLock.setVisible(false);
        btnUnLock.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedUnLock(e);
            }
        });

        btnLoad = this.getButtonPanel().addLoadButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedLoad(e);
            }
        });

        this.getTable().addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                mouseClickedTable(e);
            }
        });
        this.getTable().addRowSelectedListener(new RowSelectedListener() {
            public void processRowSelected() {
                setEnableModel();
            }
        });
    }

    protected void actionPerformedAdd(ActionEvent e) {
        DtoWeeklyReport dto = createDto();

        vwWeeklyReportGeneral = getVwWeeklyReportGeneral();
        Parameter param = createParameterForGeneralWorkArea(dto);
        vwWeeklyReportGeneral.setParameter(param);
        vwWeeklyReportGeneral.refreshWorkArea();

        VWJPopupEditor poputEditor = new VWJPopupEditor(
                getParentWindow(), "Add Weekly Report"
                , vwWeeklyReportGeneral, this);

        int i = poputEditor.showConfirm();
        if (i == Constant.OK) {
            vwWeeklyReportGeneral.convertUI2Dto();
            getTable().addRow(dto);
        }
    }

    public DtoWeeklyReport createDto() {
        DtoWeeklyReport dto = new DtoWeeklyReport();
        dto.setBeginPeriod(this.beginPeriod);
        dto.setEndPeriod(this.endPeriod);
        dto.setUserId(userId);
        dto.setIsActivity("1");
        dto.setConfirmStatus(DtoWeeklyReport.STATUS_UNLOCK);
        return dto;
    }

    protected void actionPerformedUpdate(ActionEvent e) {
        DtoWeeklyReport dto = (DtoWeeklyReport)this.getSelectedData();
        if (dto == null) {
            return;
        }

        vwWeeklyReportGeneral = getVwWeeklyReportGeneral();
        Parameter param = createParameterForGeneralWorkArea(dto);
        vwWeeklyReportGeneral.setParameter(param);
        vwWeeklyReportGeneral.refreshWorkArea();

        VWJPopupEditor poputEditor = new VWJPopupEditor(
                getParentWindow(), "Update Weekly Report"
                , vwWeeklyReportGeneral, this);

        int i = poputEditor.showConfirm();
        if (i == Constant.OK) {
            vwWeeklyReportGeneral.convertUI2Dto();
            if( getModel() instanceof VMTableWeeklyReport ){
                ((VMTableWeeklyReport)getModel()).reCaculateSumAll();
            }
            this.getTable().refreshTable();


        }
    }

    protected void actionPerformedLock(ActionEvent e) {
//        if (checkModified() || isLocked == false) {
            if(weeklyReportList == null || weeklyReportList.size() ==0 ){
                comMSG.dispErrorDialog("Please fill weekly report first!");
                return;
            }

            String msg = "";

            if(this.errorMessage.length() > 0){
                msg = "\r\n\r\n" + errorMessage.toString() + "\r\n\r\n";
            }
            if( this.checkMessage.length() > 0 ){
                msg = this.checkMessage.toString()+"\r\n\r\n";
            }
            msg += "Do you want to lock the weekly report?";

            int opt = comMSG.dispConfirmDialog(msg);
            if( opt == Constant.OK ){

                if (validateData() == true) {
                    InputInfo inputInfo = new InputInfo();
                    inputInfo.setActionId(this.actionIdLock);
                    inputInfo.setInputObj(DtoTcKey.USER_ID, this.userId);
                    inputInfo.setInputObj(DtoTcKey.BEGIN_PERIOD, this.beginPeriod);
                    inputInfo.setInputObj(DtoTcKey.END_PERIOD, this.endPeriod);

                    inputInfo.setInputObj(DtoTcKey.WEEKLY_REPORT_LIST, weeklyReportList);

                    ReturnInfo returnInfo = accessData(inputInfo);
                    if (returnInfo.isError() == false) {
                        weeklyReportList = (List) returnInfo.getReturnObj(DtoTcKey.WEEKLY_REPORT_LIST);

                        Boolean lockFlag = (Boolean) returnInfo.getReturnObj(DtoTcKey.IS_LOCKED);
                        this.isLocked = lockFlag.booleanValue();

                        String checkMsg = (String) returnInfo.getReturnObj(DtoTcKey.MESSAGE);
                        if (checkMsg != null) {
                            comMSG.dispErrorDialog(checkMsg);
                        } else {
                            this.getTable().setRows(weeklyReportList);
                            //comMSG.dispMessageDialog("Lock OK.");
                        }
                    }
                }
            }
//        }
    }

    protected void actionPerformedUnLock(ActionEvent e) {

    }

    protected void actionPerformedInit(ActionEvent e) {
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(this.actionIdInit);

        inputInfo.setInputObj(DtoTcKey.WEEKLY_REPORT_LIST, getModel().getRows());
        inputInfo.setInputObj(DtoTcKey.BEGIN_PERIOD, beginPeriod);
        inputInfo.setInputObj(DtoTcKey.END_PERIOD, endPeriod);

        ReturnInfo returnInfo = accessData(inputInfo);
        if (returnInfo.isError() == false) {

            List initData = (List) returnInfo.getReturnObj(DtoTcKey.WEEKLY_REPORT_INIT_FROM_DAILYREPORT_LIST);
            int rowNum = getModel().getRowCount();
            for (Iterator iter = initData.iterator(); iter.hasNext(); ) {
                Object item = (Object) iter.next();
                this.getModel().addRow(getModel().getRowCount(), item);
            }

            if (initData.size() > 0) {
                this.getTable().setSelectRow(rowNum);
            }
            //comMSG.dispMessageDialog("Init OK.");
        }
    }

    protected void actionPerformedCopy(ActionEvent e) {
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(this.actionIdCopy);

        inputInfo.setInputObj(DtoTcKey.WEEKLY_REPORT_LIST, getModel().getRows());
        inputInfo.setInputObj(DtoTcKey.BEGIN_PERIOD, beginPeriod);
        inputInfo.setInputObj(DtoTcKey.END_PERIOD, endPeriod);

        ReturnInfo returnInfo = accessData(inputInfo);
        if (returnInfo.isError() == false) {

            List copyData = (List) returnInfo.getReturnObj(DtoTcKey.WEEKLY_REPORT_COPY_LIST);
            int rowNum = getModel().getRowCount();
            for (Iterator iter = copyData.iterator(); iter.hasNext(); ) {
                Object item = (Object) iter.next();
                this.getModel().addRow(getModel().getRowCount(), item);
            }

            if (copyData.size() > 0) {
                this.getTable().setSelectRow(rowNum);
            }
            //comMSG.dispMessageDialog("Copy OK.");
        }
    }


    protected void actionPerformedDel(ActionEvent e) {
        int option = comMSG.dispConfirmDialog("Do you delete the data?");
        if (option == Constant.OK) {
            DtoWeeklyReport dto = (DtoWeeklyReport)this.getSelectedData();
            if (dto != null) {
                getTable().deleteRow();
            }
        }
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
        setEnableModel();

        ((VMTableWeeklyReport)getModel()).notifyTableChanged(TableListener.EVENT_REFRESH_ACTION);
    }

    protected void mouseClickedTable(MouseEvent e) {

        if (isDataEditable() == false) {
            //当前点击的数据不能被修改
            return;
        }

        if (e.getClickCount() != 2) {
            return;
        }

        int col = getTable().getSelectedColumn();
        if (col != 0 && col != 1) {
            //doesn't click 'account' or 'activity'
            return;
        }

        if (getTable().getSelectedRow() < 0) {
            return;
        }

        /*
        DtoWeeklyReport selectWeeklyReport = (DtoWeeklyReport) getTable().getSelectedData();
        Long acntRid = null;
        Long activityRid = null;
        Long codeValueRid = null;

        if (selectWeeklyReport != null) {
            acntRid = selectWeeklyReport.getAcntRid();
            activityRid = selectWeeklyReport.getActivityRid();
            codeValueRid = selectWeeklyReport.getCodeValueRid();
        }

        if (vwBelongTo == null) {
            vwBelongTo = new VwBelongTo();
            vwBelongTo.cmbAcntCode.setEnabled(false);
            vwBelongTo.cmbAcntName.setEnabled(false);
        }

        vwBelongTo.setParameter(acntRid, activityRid, codeValueRid);
        VWJPopupEditor poputEditor = new VWJPopupEditor(
                getParentWindow(), "Select account/activity"
                , vwBelongTo
                , this);

        int i = poputEditor.showConfirm();
        if (i == Constant.OK) {
            acntRid = vwBelongTo.getAcntRid();
            activityRid = vwBelongTo.getActivityRid();
            codeValueRid = vwBelongTo.getCodeValueRid();

            selectWeeklyReport.setAcntRid(acntRid);
            selectWeeklyReport.setAcntName(vwBelongTo.getAcntName());
            if (activityRid != null) {
                selectWeeklyReport.setActivityRid(activityRid);
                selectWeeklyReport.setActivityName(vwBelongTo.getActivityName());
                selectWeeklyReport.setCodeValueRid(null);
                selectWeeklyReport.setCodeValueName(null);
                selectWeeklyReport.setIsActivity("1");
            } else {
                selectWeeklyReport.setActivityRid(null);
                selectWeeklyReport.setActivityName(null);
                selectWeeklyReport.setCodeValueRid(codeValueRid);
                selectWeeklyReport.setCodeValueName(vwBelongTo.getCodeValueName());
                selectWeeklyReport.setIsActivity("0");
            }

            selectWeeklyReport.setOp(IDto.OP_MODIFY);
            getTable().refreshTable();
        }
        */

       actionPerformedUpdate(null);
    }

    protected VwWeeklyReportGeneral getVwWeeklyReportGeneral() {
        if (vwWeeklyReportGeneral == null) {
            vwWeeklyReportGeneral = new VwWeeklyReportGeneral();
        }

        return this.vwWeeklyReportGeneral;
    }

//    private VwWeeklyReportGeneral getVwWeeklyReportGeneralView() {
//        if (vwWeeklyReportGeneralView == null) {
//            vwWeeklyReportGeneralView = new VwWeeklyReportGeneral();
//            VWUtil.setUIEnabled(vwWeeklyReportGeneralView, false);
//        }
//
//        return this.vwWeeklyReportGeneralView;
//    }

    public void setConfirmStatus(String confirmStatus){

    }

    protected boolean checkModified() {
        for (int i = 0; i < this.weeklyReportList.size(); i++) {
            DtoWeeklyReport dtoWeeklyReport = (DtoWeeklyReport) weeklyReportList.get(i);

            if (dtoWeeklyReport.isChanged() == true) {
                return true;
            }
        }

        return false;
    }


    private boolean validateData() {
        boolean bValid = true;
        StringBuffer msg = new StringBuffer("");

        BigDecimal maxHourOnDay = new BigDecimal(24);
        for (int i = 0; i < this.getTable().getRowCount(); i++) {
            DtoWeeklyReport dto = (DtoWeeklyReport)this.getModel().getRow(i);
            if (dto.getAcntRid() == null) {
                msg.append("Row " + (i + 1) + " ： Must input account.\r\n");
                bValid = false;
            }

            if( dto.getActivityRid() == null && dto.getCodeValueRid() == null
                    && !dto.isWeeklyReportSum()){
                msg.append("Row " + (i + 1) + " ： Must input activity(or code).\r\n");
                bValid = false;
            }

            for (int j = DtoWeeklyReport.SATURDAY; j < DtoWeeklyReport.FRIDAY; j++) {
                BigDecimal hour = dto.getHour(j);
                if (hour != null && maxHourOnDay.compareTo(hour) == -1) {
                    msg.append("Row " + (i + 1) + " ： The work hour of "
                               + VMTableWeeklyReport.WEEK_TITLE[j] + " is bigger than 24.\r\n");
                    bValid = false;
                }
            }
        }

        DtoHoursOnWeek hoursOnWeek = ((VMTableWeeklyReport) getModel()).getHoursOnWeek();
        for (int j = DtoWeeklyReport.SATURDAY; j < DtoWeeklyReport.FRIDAY; j++) {
            BigDecimal hour = hoursOnWeek.getHour(j);
            if (hour != null && maxHourOnDay.compareTo(hour) == -1) {
                msg.append("The total work hour of "
                           + VMTableWeeklyReport.WEEK_TITLE[j] + " is bigger than 24.\r\n");
                bValid = false;
            }
        }

        if (bValid == false) {
            comMSG.dispErrorDialog(msg.toString());
        }
        return bValid;
    }

    protected void saveData() {
        InputInfo inputInfo = createInputInfoForUpdate();

        ReturnInfo returnInfo = accessData(inputInfo);
        if (returnInfo.isError() == false) {
            weeklyReportList = (List) returnInfo.getReturnObj(DtoTcKey.WEEKLY_REPORT_LIST);
            String checkMsg = (String) returnInfo.getReturnObj(DtoTcKey.MESSAGE);

//            if (checkMsg != null) {
//                comMSG.dispMessageDialog(checkMsg);
//            }
            this.getTable().setRows(weeklyReportList);
        }
    }

    public void setParameter(Parameter param) {
        super.setParameter(param);
        this.beginPeriod = (Date) (param.get(DtoTcKey.BEGIN_PERIOD));
        this.endPeriod = (Date) (param.get(DtoTcKey.END_PERIOD));
        this.userId = (String) (param.get(DtoTcKey.USER_ID));

        if (this.beginPeriod == null || this.endPeriod == null || userId == null) {
            this.isParameterValid = false;
        } else {
            isParameterValid = true;
        }
    }

    //页面刷新－－－－－
    protected void resetUI() {


        dteBeginPeriod.setText(comDate.dateToString(beginPeriod,"yyyy-MM-dd"));
        lblAnd.setText(" ~ ");
        dteEndPeriod.setText(comDate.dateToString(endPeriod,"yyyy-MM-dd"));
        lblnull.setText("                   ");

        if (isParameterValid == false) {
            weeklyReportList = new ArrayList();
            getTable().setRows(weeklyReportList);
        } else {
            InputInfo inputInfo = createInputInfoForList();
            ReturnInfo returnInfo = accessData(inputInfo);

            if (returnInfo.isError() == false) {
                weeklyReportList = (List) returnInfo.getReturnObj(DtoTcKey.
                        WEEKLY_REPORT_LIST);
                Boolean lockFlag = (Boolean) returnInfo.getReturnObj(DtoTcKey.IS_LOCKED);
                if( this.isLocked != lockFlag.booleanValue() ){
                    isLocked = lockFlag.booleanValue();
                    if( isLocked ){
                        ((VMTableWeeklyReport) getModel()).notifyTableChanged(TableListener.EVENT_LOCK_On);
                    }else{
                        ((VMTableWeeklyReport) getModel()).notifyTableChanged(TableListener.EVENT_LOCK_OFF);
                    }
                }


                confirmStatus = (String)returnInfo.getReturnObj(DtoTcKey.CONFIRM_STATUS);
                boolean[] newWorkDayBitmap = (boolean[])returnInfo.getReturnObj(DtoTcKey.WORK_DAY_BITMAP);
                if( newWorkDayBitmap != null ){
                    for (int i = 0; i < workDayBitmap.length; i++) {
                        workDayBitmap[i] = newWorkDayBitmap[i];
                    }
                }
//                setHasRejectedFlag();

                ((VMTableWeeklyReport) getModel()).setPeriod(this.beginPeriod,
                        this.endPeriod);
                getTable().setRows(weeklyReportList);
            }
        }
    }

//    private void setHasRejectedFlag() {
//        this.hasRejected = false;
//        for (Iterator iter = this.weeklyReportList.iterator(); iter.hasNext(); ) {
//            DtoWeeklyReport item = (DtoWeeklyReport) iter.next();
//            if (item.isRejected()) {
//                this.hasRejected = true;
//                break;
//            }
//        }
//    }

    protected InputInfo createInputInfoForList() {
        InputInfo inputInfo = new InputInfo();

        inputInfo.setActionId(this.actionIdList);
        inputInfo.setInputObj(DtoTcKey.BEGIN_PERIOD, this.beginPeriod);
        inputInfo.setInputObj(DtoTcKey.END_PERIOD, this.endPeriod);
        inputInfo.setInputObj(DtoTcKey.USER_ID, this.userId);

        return inputInfo;
    }

    protected InputInfo createInputInfoForUpdate() {
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(this.actionIdUpdate);
        inputInfo.setInputObj(DtoTcKey.WEEKLY_REPORT_LIST, weeklyReportList);
        inputInfo.setInputObj(DtoTcKey.USER_ID, userId);
        inputInfo.setInputObj(DtoTcKey.BEGIN_PERIOD, beginPeriod);
        inputInfo.setInputObj(DtoTcKey.END_PERIOD, endPeriod);
        return inputInfo;
    }

    protected Parameter createParameterForGeneralWorkArea(DtoWeeklyReport dto) {
        Parameter param = new Parameter();
        param.put(DtoTcKey.DTO, dto);
        param.put(DtoTcKey.BEGIN_PERIOD, beginPeriod);
        param.put(DtoTcKey.END_PERIOD, endPeriod);
        return param;
    }

    protected ReturnInfo accessDataT(InputInfo inputInfo) {
        ReturnInfo returnInfo = new ReturnInfo();

        List dtoList = new ArrayList();
        DtoWeeklyReport dto1 = new DtoWeeklyReport();
        dto1.setAcntRid(new Long(11));
        dto1.setActivityRid(new Long(11));
        dto1.setSatHour(new BigDecimal(6));
        dto1.setSunHour(new BigDecimal(7));
        dto1.setMonHour(new BigDecimal(1));
        dto1.setTueHour(new BigDecimal(2));
        dto1.setWedHour(new BigDecimal(3));
        dto1.setThuHour(new BigDecimal(4));
        dto1.setFriHour(new BigDecimal(5));
        dtoList.add(dto1);

        DtoWeeklyReport dto2 = new DtoWeeklyReport();
        dto2.setAcntRid(new Long(22));
        dto2.setActivityRid(new Long(11));
        dto2.setWedHour(new BigDecimal(11));
        dtoList.add(dto2);

        DtoWeeklyReport dto3 = new DtoWeeklyReport();
        dto3.setAcntRid(new Long(33));
        dto3.setActivityRid(new Long(11));
        dto3.setWedHour(new BigDecimal(11));
        dtoList.add(dto3);
        dtoList.add(new DtoWeeklyReport());
        dtoList.add(new DtoWeeklyReport());

        returnInfo.setReturnObj(DtoTcKey.WEEKLY_REPORT_LIST, dtoList);
        return returnInfo;
    }

    protected void setEnableModel() {
    }

    protected void setButtonVisible() {
    }

    protected boolean isDataEditable() {
        return this.isDataEditable;
    }

    protected void setDataEditable(boolean isDataEditable) {
        this.isDataEditable = isDataEditable;
        ((VMTableWeeklyReport) getModel()).setDataEditable(isDataEditable);
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

    public void saveWorkAreaDirectly() {
        if (checkModified()) {
            isSaveOk = false;

            if (validateData() == true) {
                saveData();
                this.isSaveOk = true;
            }
        } else {
            isSaveOk = true;
        }
    }


    public boolean isSaveOk() {
        return this.isSaveOk;
    }

    public boolean onClickOK(ActionEvent e) {
        return true;
    }

    public boolean onClickCancel(ActionEvent e) {
        return true;
    }

    public String getUserId(){
        return this.userId;
    }

    public Date getBeginPeriod(){
        return this.beginPeriod;
    }

    public Date getEndPeriod(){
        return this.endPeriod;
    }

    public boolean[] getWorkDayBitmap(){
        return this.workDayBitmap;
    }

    public void setCheckMessage(StringBuffer sb){
        this.checkMessage = sb;
    }

    public void setErrorMessage(StringBuffer errorMessage) {
        this.errorMessage = errorMessage;
    }

    //表的高度
    public int getPreferredTableHeight() {
        int actualHeight = getTable().getRowCount() * VWJTable.TABLE_ROW_HEIGHT + TableWeeklyReport.HEADER_HEIGHT;
        return actualHeight > getMinmizeTableHeight() ? actualHeight : getMinmizeTableHeight();
    }

    //表的最小高度
    public int getMinmizeTableHeight() {
        return 4 * VWJTable.TABLE_ROW_HEIGHT + TableWeeklyReport.HEADER_HEIGHT;
    }

    //得到表的最小高度
    public int getMaxmizeTableHeight() {
        return 16 * VWJTable.TABLE_ROW_HEIGHT + TableWeeklyReport.HEADER_HEIGHT;
    }

    protected void jbInit() throws Exception {
    }

}
