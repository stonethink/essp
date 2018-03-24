package client.essp.tc.pmmanage.onWeek;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.table.TableColumn;

import c2s.dto.IDto;
import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.tc.weeklyreport.DtoTcKey;
import c2s.essp.tc.weeklyreport.DtoWeeklyReport;
import c2s.essp.tc.weeklyreport.DtoWeeklyReportSumByPm;
import client.essp.common.view.VWTableWorkArea;
import client.essp.tc.common.RowRendererTwoNumber;
import client.essp.tc.weeklyreport.TableWeeklyReport;
import client.essp.tc.weeklyreport.VMTableWeeklyReport;
import client.framework.common.Constant;
import client.framework.view.common.comMSG;
import client.framework.view.vwcomp.VWJTable;
import com.wits.util.Parameter;
import com.wits.util.comDate;
import client.essp.tc.common.IPeriodModel;
import client.essp.tc.common.RowRenderer;
import client.essp.common.excelUtil.VWJButtonExcel;
import c2s.essp.common.excelUtil.IExcelParameter;
import client.framework.common.Global;
import client.essp.tc.pmmanage.VwExport;
import client.framework.view.vwcomp.VWJPopupEditor;
import client.essp.tc.common.VWJDatePeriodBase;

public class VwTcListOnWeekBase extends VWTableWorkArea {
    static final String actionIdConfirm =
        "FTCPmManageWeeklyReportSumConfirmAction";
    static final String actionIdConfirmAll =
        "FTCPmManageWeeklyReportSumConfirmAllAction";
    static final String actionIdUpdate =
        "FTCPmManageWeeklyReportSumUpdateAction";
    static final String actionIdSend = "FTCPmManageWeeklyReportSendAction";
    static final String actionIdExport = "FTCPmManageWeeklyReportExportAction";
    //cotrol data
    boolean isSaveOk = true;
    boolean isParameterValid = true;

    /**
     * define common data
     */
    private List tcList = new ArrayList();
    private Date beginPeriod;
    private Date endPeriod;
    private String exportType = "week";

    JButton btnLoad;
    JButton btnSave;
    VWJButtonExcel btnExport;

    //设置表格的外观
    protected void initUI() {
        try {
            model.setDtoType(DtoWeeklyReportSumByPm.class);

            this.add(table.getScrollPane(), null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //不容许调整行的顺序
        table.getTableHeader().setReorderingAllowed(false);

        for (int i = 0; i < VMTableWeeklyReport.WEEK_TITLE.length; i++) {
            TableColumn weekColumn = getTable().getColumn(VMTableWeeklyReport.
                WEEK_TITLE[i]);
            weekColumn.setPreferredWidth(40);
            weekColumn.setMaxWidth(45);
            RowRenderer rowRendererReal = new RowRenderer(RowRenderer.real);
            weekColumn.setCellRenderer(rowRendererReal);
        }

        TableColumn sumColumn = getTable().getColumn(VMTableTcOnWeek.
            SUMMARY_TITLE);
        sumColumn.setPreferredWidth(50);
        sumColumn.setMaxWidth(55);
        RowRenderer rowRendererReal = new RowRenderer(RowRenderer.real);
        sumColumn.setCellRenderer(rowRendererReal);

        RowRendererTwoNumber rowRenderer = new RowRendererTwoNumber();
        //overtime column
        TableColumn overtimeColumn = getTable().getColumn(VMTableTcOnWeek.
            OVERTIME_TITLE);
        overtimeColumn.setPreferredWidth(60);
        overtimeColumn.setMaxWidth(65);
        overtimeColumn.setCellRenderer(rowRenderer);

        //allocate column
        TableColumn allocateColumn = getTable().getColumn(VMTableTcOnWeek.
            ALLOCATE_TITLE);
        allocateColumn.setPreferredWidth(60);
        allocateColumn.setMaxWidth(65);
        allocateColumn.setCellRenderer(rowRenderer);

        getTable().getScrollPane().setBorder(null);

        //本画面的高度
        this.setMinimumSize(new Dimension( -1, this.getMinmizeTableHeight()));
        this.setPreferredSize(new Dimension( -1, this.getPreferredTableHeight()));
        this.setMaximumSize(new Dimension( -1, this.getMaxmizeTableHeight()));

        addUICEvent();
        setButtonVisible();
    }

    private void addUICEvent() {

        btnSave = this.getButtonPanel().addSaveButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedSave(e);
            }
        });

        btnLoad = this.getButtonPanel().addLoadButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedLoad(e);
            }
        });
//        btnExport = new VWJButtonExcel(new IExcelParameter(){
//            public String getUrlAddress() {
//                VwExport vwExport =new VwExport(beginPeriod,endPeriod);
//                vwExport.setTheType(VWJDatePeriodBase.PERIOD_ONE_WEEK);
//                VWJPopupEditor poputEditor = new VWJPopupEditor(
//                null, "Export Weekly Report"
//                , vwExport, 0);
//                int result = poputEditor.showConfirm();
//                comMSG.dispErrorDialog(beginPeriod.toString());
//                if(result == Constant.CANCEL) {
//                    return "";
//                }
//                StringBuffer sb = new StringBuffer(Global.appRoot);
//                sb.append(IExcelParameter.DEFAULT_EXCEL_JSP_ADDRESS);
//                sb.append("?");
//                sb.append(IExcelParameter.ACTION_ID);
//                sb.append("=");
//                sb.append(actionIdExport);
//                sb.append("&exportType=week");
//                sb.append("&beginPeriod=");
//                sb.append(comDate.dateToString(beginPeriod));
//                sb.append("&endPeriod=");
//                sb.append(comDate.dateToString(endPeriod));
//                return sb.toString();
//            }
//        });
        btnExport = new VWJButtonExcel(null);
        ActionListener[] actListeners = btnExport.getActionListeners();
        for(int i = 0; i<actListeners.length;i++) {
            btnExport.removeActionListener(actListeners[i]);
        }
        btnExport.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                VwExport vwExport = new VwExport(beginPeriod, endPeriod);
                vwExport.setTheType(VWJDatePeriodBase.PERIOD_ONE_WEEK);
                VWJPopupEditor poputEditor = new VWJPopupEditor(
                    getParentWindow(), "Export Weekly Report"
                    , vwExport, 2);
                poputEditor.setSize(400, 150);
                int result = poputEditor.showConfirm();
                if (result != Constant.OK) {
                    return;
                }
                if(vwExport.getTheType() == VWJDatePeriodBase.PERIOD_ONE_MONTH) {
                     exportType = "period";
                 } else {
                     exportType = "week";
                 }
                final Date resultBegin = vwExport.getCurBeginDate();
                final Date resultEnd = vwExport.getCurEndDate();
                btnExport.setParameter(new IExcelParameter() {
                    public String getUrlAddress() {
                        StringBuffer sb = new StringBuffer(Global.appRoot);
                        sb.append(IExcelParameter.DEFAULT_EXCEL_JSP_ADDRESS);
                        sb.append("?");
                        sb.append(IExcelParameter.ACTION_ID);
                        sb.append("=");
                        sb.append(actionIdExport);
                        sb.append("&exportType=");
                         sb.append(exportType);
                        sb.append("&acntRid=");
                        sb.append(getAcntRidForConfirm());
                        sb.append("&beginPeriod=");
                        sb.append(comDate.dateToString(resultBegin));
                        sb.append("&endPeriod=");
                        sb.append(comDate.dateToString(resultEnd));
                        return sb.toString();
                    }
                });
                btnExport.accessData();
            }
        });

        this.getButtonPanel().add(btnExport);

    }

//    protected void exportReport() {
//        btnExport.setParameter(new IExcelParameter() {
//            public String getUrlAddress() {
//                StringBuffer sb = new StringBuffer(Global.appRoot);
//                sb.append(IExcelParameter.DEFAULT_EXCEL_JSP_ADDRESS);
//                sb.append("?");
//                sb.append(IExcelParameter.ACTION_ID);
//                sb.append("=");
//                sb.append(actionIdExport);
//                sb.append("&exportType=week");
//                sb.append("&acntRid=");
//                sb.append(getAcntRidForConfirm());
//                sb.append("&beginPeriod=");
//                sb.append(comDate.dateToString(exportBegin));
//                sb.append("&endPeriod=");
//                sb.append(comDate.dateToString(exportEnd));
//                return sb.toString();
//            }
//        });
//        btnExport.accessData();
//    }

    protected void setButtonVisible() {
    }

    protected void setButtonEnabled() {
        if (this.isParameterValid == false) {
            btnSave.setEnabled(false);
            btnExport.setEnabled(false);
        } else {
            btnSave.setEnabled(true);
            btnExport.setEnabled(true);
        }
    }


    protected void actionPerformedSave(ActionEvent e) {
        if (checkModified()) {
            saveData();
        }
    }

    protected void actionPerformedLoad(ActionEvent e) {
        resetUI();
    }

//    protected void popupMenuCanceledConfirm(PopupMenuEvent e) {
//        DtoWeeklyReportSumByPm dto = (DtoWeeklyReportSumByPm) getSelectedData();
//        if (dto != null) {
//            VWJComboBox cmbConfirm = (VWJComboBox) e.getSource();
//            String confirmStatus = (String) cmbConfirm.getUICValue();
//            if (confirmStatus.equals(dto.getConfirmStatus()) == false) {
//                ((VMTableTcOnWeek) getModel()).statusChanged();
//            }
//        }
//    }
//
//    protected void actionPerformedConfirm(ActionEvent e) {
//        DtoWeeklyReportSumByPm dto = (DtoWeeklyReportSumByPm)getSelectedData();
//        if( dto != null ){
//            VWJComboBox cmbConfirm = (VWJComboBox)e.getSource();
//            String confirmStatus = (String)cmbConfirm.getUICValue();
//            if( confirmStatus.equals(dto.getConfirmStatus()) == false ){
//                ( (VMTableTcOnWeek)getModel() ).statusChanged();
//            }
//        }
//    }

    private boolean checkModified() {
        for (int i = 0; i < this.tcList.size(); i++) {
            DtoWeeklyReportSumByPm dtoWeeklyReportSum = (DtoWeeklyReportSumByPm)
                tcList.get(i);
            if ((dtoWeeklyReportSum.getConfirmStatus()).equals("Rejected")) {
                InputInfo inputInfo = new InputInfo();
                inputInfo.setActionId(actionIdSend);
                inputInfo.setInputObj(DtoTcKey.DTO, dtoWeeklyReportSum);
                ReturnInfo returnInfo = accessData(inputInfo);
            }
            if (dtoWeeklyReportSum.isChanged() == true) {
                return true;
            }
        }

        return false;
    }


    private void saveData() {
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(actionIdUpdate);
        inputInfo.setInputObj(DtoTcKey.WEEKLY_REPORT_SUM_LIST, this.tcList);

        ReturnInfo returnInfo = accessData(inputInfo);
        if (returnInfo.isError() == false) {
            //set the op
            for (Iterator iter = this.tcList.iterator(); iter.hasNext(); ) {
                DtoWeeklyReportSumByPm dto = (DtoWeeklyReportSumByPm) iter.next();
                dto.setOp(IDto.OP_NOCHANGE);
            }
        }
    }

    public void setParameter(Parameter param) {
        this.isParameterValid = true;

        //judge whether the parameter is changed
        boolean parameterChanged = setParameterExtend(param);
        Date newBeginPeriod = (Date) (param.get(DtoTcKey.BEGIN_PERIOD));
        Date newEndPeriod = (Date) (param.get(DtoTcKey.END_PERIOD));
        if (newBeginPeriod == null || newEndPeriod == null) {
            parameterChanged = true;
        } else {
            if (beginPeriod == null ||
                comDate.compareDate(this.beginPeriod, newBeginPeriod) != 0
                || endPeriod == null ||
                comDate.compareDate(this.endPeriod, newEndPeriod) != 0
                ) {
                parameterChanged = true;
            }
        }

        //set parameter to local variant
        this.beginPeriod = (Date) (param.get(DtoTcKey.BEGIN_PERIOD));
        this.endPeriod = (Date) (param.get(DtoTcKey.END_PERIOD));
        if (this.beginPeriod == null || this.endPeriod == null) {
            this.isParameterValid = false;
        }

        //只有参数变化了才会设置刷新标志
        if (parameterChanged) {
            super.setParameter(param);
        }
    }

    protected boolean setParameterExtend(Parameter param) {
        return false;
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
                    WEEKLY_REPORT_SUM_LIST);

                ((IPeriodModel) getModel()).setPeriod(this.beginPeriod,
                    this.endPeriod);
                getTable().setRows(tcList);
            }
        }

        setButtonEnabled();
    }

    public void refreshRow() {
        int selectedRow = getTable().getSelectedRow();
        if (selectedRow >= 0) {
            refreshRow(selectedRow);
        }
    }

    //刷新行row的数据，该数据重新从数据库中取得
    //需重载此方法
    protected void refreshRow(int row) {

    }

    protected InputInfo createInputInfoForList() {
        InputInfo inputInfo = new InputInfo();

        inputInfo.setInputObj(DtoTcKey.BEGIN_PERIOD, this.beginPeriod);
        inputInfo.setInputObj(DtoTcKey.END_PERIOD, this.endPeriod);

        return inputInfo;
    }

    protected InputInfo createInputInfoForUser(String userId) {
        InputInfo inputInfo = new InputInfo();

        inputInfo.setInputObj(DtoTcKey.BEGIN_PERIOD, this.beginPeriod);
        inputInfo.setInputObj(DtoTcKey.END_PERIOD, this.endPeriod);
        inputInfo.setInputObj(DtoTcKey.USER_ID, userId);

        return inputInfo;
    }

    protected ReturnInfo accessDataT(InputInfo inputInfo) {
        ReturnInfo returnInfo = new ReturnInfo();

        List dtoList = new ArrayList();
        DtoWeeklyReportSumByPm dto1 = new DtoWeeklyReportSumByPm();
        dto1.setUserId("x");
        dto1.setJobCode("y");
        dto1.setWedHour(new BigDecimal(0));
        dtoList.add(dto1);

        DtoWeeklyReportSumByPm dto2 = new DtoWeeklyReportSumByPm();
        dto2.setUserId("x");
        dto2.setJobCode("y");
        dto2.setWedHour(new BigDecimal(0));
        dtoList.add(dto2);

        DtoWeeklyReportSumByPm dto3 = new DtoWeeklyReportSumByPm();
        dto3.setUserId("x");
        dto3.setJobCode("y");
        dto3.setWedHour(new BigDecimal(0));
        dtoList.add(dto3);

        returnInfo.setReturnObj(DtoTcKey.WEEKLY_REPORT_SUM_LIST, dtoList);
        return returnInfo;
    }

    public void refreshSumHours() {
        int selectRow = this.getTable().getSelectedRow();
        if (selectRow >= 0) {
            ((IPeriodModel) getModel()).refreshSumHours(selectRow);
        }
    }

    public void confirm() {
        DtoWeeklyReportSumByPm dto = (DtoWeeklyReportSumByPm) getSelectedData();
        if (dto != null) {
            InputInfo inputInfo = new InputInfo();
            inputInfo.setActionId(actionIdConfirm);
            inputInfo.setInputObj(DtoTcKey.DTO, dto);
            ReturnInfo returnInfo = accessData(inputInfo);
            if (returnInfo.isError() == false) {

                ((VMTableTcOnWeek) getModel()).confirm(getTable().
                    getSelectedRow(), dto.getConfirmStatus());

//                String msg = (String)returnInfo.getReturnObj(DtoTcKey.MESSAGE);
//                if( msg != null ){
//
//                    comMSG.dispMessageDialog(msg);
//                }
            }
        }
    }

    /**
     * 设置表格TableByWorker的“所有行”的confirmStatus
     * 对当前选中行，其改变不存放到数据库中（因为它的数据已经捞出来了）
     * 对其他行，到数据库中改变其confirmStatus
     */
    public boolean confirmAll(String confirmStatus) {
        int opt;
        if (DtoWeeklyReport.STATUS_CONFIRMED.equals(confirmStatus)) {
            opt = comMSG.dispConfirmDialog(
                "Do you want to confirm all of the job?");
        } else if (DtoWeeklyReport.STATUS_REJECTED.equals(confirmStatus)) {
            opt = comMSG.dispConfirmDialog(
                "Do you want to reject all of the job?");
        } else if (DtoWeeklyReport.STATUS_LOCK.equals(confirmStatus)) {
            opt = comMSG.dispConfirmDialog(
                "Do you want to retrieve all of the job to locked status?");
        } else {
            return false;
        }
        if (opt == Constant.OK) {

            InputInfo inputInfo = new InputInfo();
            inputInfo.setActionId(actionIdConfirmAll);
            inputInfo.setInputObj(DtoTcKey.ACNT_RID, getAcntRidForConfirm());
            inputInfo.setInputObj(DtoTcKey.BEGIN_PERIOD, beginPeriod);
            inputInfo.setInputObj(DtoTcKey.END_PERIOD, endPeriod);
            inputInfo.setInputObj(DtoTcKey.CONFIRM_ALL_STATUS, confirmStatus);
            ReturnInfo returnInfo = accessData(inputInfo);
            if (returnInfo.isError() == false) {

                ((VMTableTcOnWeek) getModel()).confirmAll(confirmStatus);

                String msg = (String) returnInfo.getReturnObj(DtoTcKey.MESSAGE);
                //if( msg != null ){
                //   comMSG.dispMessageDialog(msg);
                //}
            }
            return true;
        }
        return false;
    }

    //如果没有confirm功能，则不需要重载这个方法
    protected Long getAcntRidForConfirm() {
        return null;
    }

    //表的高度
    public int getPreferredTableHeight() {
        int actualHeight = getTable().getRowCount() * VWJTable.TABLE_ROW_HEIGHT +
                           TableWeeklyReport.HEADER_HEIGHT;
        return actualHeight > getMinmizeTableHeight() ? actualHeight :
            getMinmizeTableHeight();
    }

    //表的最小高度
    public int getMinmizeTableHeight() {
        return 1 * VWJTable.TABLE_ROW_HEIGHT + TableWeeklyReport.HEADER_HEIGHT;
    }

    //得到表的最小高度
    public int getMaxmizeTableHeight() {
        return 16 * VWJTable.TABLE_ROW_HEIGHT + TableWeeklyReport.HEADER_HEIGHT;
    }

}
