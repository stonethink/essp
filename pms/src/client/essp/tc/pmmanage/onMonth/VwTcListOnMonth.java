package client.essp.tc.pmmanage.onMonth;

import java.awt.Dimension;
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
import c2s.essp.tc.weeklyreport.DtoWeeklyReportSumOnMonthByPm;
import client.essp.common.view.VWTableWorkArea;
import client.essp.common.view.VWWorkArea;
import client.essp.tc.weeklyreport.TableWeeklyReport;
import client.framework.model.VMColumnConfig;
import client.framework.model.VMComboBox;
import client.framework.view.vwcomp.VWJComboBox;
import client.framework.view.vwcomp.VWJReal;
import client.framework.view.vwcomp.VWJTable;
import client.framework.view.vwcomp.VWJText;
import com.wits.util.Parameter;
import com.wits.util.TestPanel;
import com.wits.util.comDate;
import c2s.dto.DtoUtil;
import client.essp.tc.common.RowRendererTwoNumber;
import client.essp.tc.common.TableListener;
import c2s.essp.common.excelUtil.IExcelParameter;
import client.essp.common.excelUtil.VWJButtonExcel;
import client.framework.common.Global;
import client.framework.view.vwcomp.VWJPopupEditor;
import client.essp.tc.pmmanage.VwExport;
import client.framework.common.Constant;
import client.essp.tc.common.VWJDatePeriodBase;
import client.framework.view.common.comMSG;
import javax.swing.ImageIcon;
import client.image.ComImage;
import client.essp.common.loginId.VWJLoginId;

public class VwTcListOnMonth extends VWTableWorkArea {
    static final String actionIdList = "FTCPmManageWeeklyReportSumOnMonthListAction";
    static final String actionIdGetForUser = "FTCPmManageWeeklyReportSumOnMonthGetForUserAction";
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
    private Long acntRid;
    private String exportType = "period";

    JButton btnLoad;
    VWJButtonExcel btnExport;

    public VwTcListOnMonth() {
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
        real.setMaxInputDecimalDigit(1);

        VWJComboBox cmbStatus = new VWJComboBox();
        VMComboBox vmStatus = VMComboBox.
                              toVMComboBox(new String[] {DtoWeeklyReport.STATUS_UNLOCK
                                           ,DtoWeeklyReport.STATUS_LOCK
                                           , DtoWeeklyReport.STATUS_CONFIRMED
                                           , DtoWeeklyReport.STATUS_REJECTED});
        cmbStatus.setModel(vmStatus);

        Object[][] configs = new Object[][] {
                             {"Worker", "userId", VMColumnConfig.UNEDITABLE, new VWJLoginId()},
                             {"Job Code", "jobCode", VMColumnConfig.UNEDITABLE, text},
                             {VMTableTcOnMonth.SUMMARY_TITLE, "", VMColumnConfig.UNEDITABLE, real},
                             {"Overtime", "", VMColumnConfig.UNEDITABLE,real},
                             {"Allocated Hours", "", VMColumnConfig.UNEDITABLE, real},
        };

        try {
            model = new VMTableTcOnMonth(configs);
            model.setDtoType(DtoWeeklyReportSumOnMonthByPm.class);
            table = new TableTcOnMonth(model);

            this.add(table.getScrollPane(), null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        addUICEvent();
        initUI();
    }

    private void addUICEvent() {
        btnLoad = this.getButtonPanel().addLoadButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedLoad(e);
            }
        });
        btnExport = new VWJButtonExcel(null);
        ActionListener[] actListeners = btnExport.getActionListeners();
        for(int i = 0; i<actListeners.length;i++) {
            btnExport.removeActionListener(actListeners[i]);
        }
         btnExport.addActionListener(new ActionListener() {
             public void actionPerformed(ActionEvent e) {
                 VwExport vwExport = new VwExport(beginPeriod, endPeriod);
                 vwExport.setTheType(VWJDatePeriodBase.PERIOD_ONE_MONTH);
                 VWJPopupEditor poputEditor = new VWJPopupEditor(
                     getParentWindow(), "Export Weekly Report"
                     , vwExport, 2);
                 poputEditor.setSize(400,150);
                 int result = poputEditor.showConfirm();
                 if (result != Constant.OK) {
                     return;
                 }
                 if(vwExport.getTheType() == VWJDatePeriodBase.PERIOD_ONE_WEEK) {
                     exportType = "week";
                 } else {
                     exportType = "period";
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
                         sb.append(acntRid);
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

    //设置表格的外观
    private void initUI() {
        //不容许调整行的顺序
        table.getTableHeader().setReorderingAllowed(false);

        RowRendererTwoNumber rowRenderer = new RowRendererTwoNumber();
        //actual hour column
        TableColumn actualColumn = getTable().getColumn(VMTableTcOnMonth.SUMMARY_TITLE);
        actualColumn.setCellRenderer(rowRenderer);

        //overtime column
        TableColumn overtimeColumn = getTable().getColumn("Overtime");
        overtimeColumn.setCellRenderer(rowRenderer);

        //allocate column
        TableColumn allocateColumn = getTable().getColumn("Allocated Hours");
        allocateColumn.setCellRenderer(rowRenderer);

        getTable().getScrollPane().setBorder(null);

        //本画面的高度
        this.setMinimumSize(new Dimension( -1, this.getMinmizeTableHeight()));
        this.setPreferredSize(new Dimension( -1, this.getPreferredTableHeight()));
        this.setMaximumSize(new Dimension( -1, this.getMaxmizeTableHeight()));
    }

    public void actionPerformedLoad(ActionEvent e) {
        resetUI();
    }

    public void setParameter(Parameter param) {
        isParameterValid = true;
        boolean parameterChanged = false;

        //judge whether the parameter is changed
        parameterChanged = setParameterExtend(param);
        Date newBeginPeriod = (Date) (param.get(DtoTcKey.BEGIN_PERIOD));
        Date newEndPeriod = (Date) (param.get(DtoTcKey.END_PERIOD));
        if (newBeginPeriod == null || newEndPeriod == null) {
            parameterChanged = true;
        } else {
            if (beginPeriod == null || comDate.compareDate(this.beginPeriod, newBeginPeriod) != 0
                || endPeriod == null || comDate.compareDate(this.endPeriod, newEndPeriod) != 0
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
        Long newAcntRid = (Long) param.get(DtoTcKey.ACNT_RID);
        if (newAcntRid == null) {
            this.acntRid = newAcntRid;
            this.isParameterValid = false;
            return true;
        } else {
            if (newAcntRid.equals(this.acntRid)) {
                return false;
            } else {
                this.acntRid = newAcntRid;
                return true;
            }
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
                tcList = (List) returnInfo.getReturnObj(DtoTcKey.WEEKLY_REPORT_SUM_LIST);

                getTable().setRows(tcList);
            }
        }
        setButtonEnabled();
    }

    public void refreshRow(){
        int selectedRow = getTable().getSelectedRow();
        if( selectedRow >= 0 ){
            refreshRow(selectedRow);
        }
    }

    //刷新行row的数据，该数据重新从数据库中取得
    public void refreshRow(int row) {
        if (this.isParameterValid == true) {
            DtoWeeklyReportSumOnMonthByPm dto = (DtoWeeklyReportSumOnMonthByPm)getModel().getRow(row);
            if( dto != null ){
                InputInfo inputInfo = createInputInfoForUser(dto.getUserId());

                ReturnInfo returnInfo = accessData(inputInfo);
                if (returnInfo.isError() == false) {
                    DtoWeeklyReportSumOnMonthByPm newDto = (DtoWeeklyReportSumOnMonthByPm) returnInfo.getReturnObj(DtoTcKey.DTO);

                    ((VMTableTcOnMonth)getModel()).updateRow(row, newDto);
                }

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

    protected InputInfo createInputInfoForUser(String userId) {
        InputInfo inputInfo = new InputInfo();

        inputInfo.setActionId(this.actionIdGetForUser);
        inputInfo.setInputObj(DtoTcKey.BEGIN_PERIOD, this.beginPeriod);
        inputInfo.setInputObj(DtoTcKey.END_PERIOD, this.endPeriod);
        inputInfo.setInputObj(DtoTcKey.ACNT_RID, this.acntRid);
        inputInfo.setInputObj(DtoTcKey.USER_ID, userId);

        return inputInfo;
    }

    protected ReturnInfo accessDataT(InputInfo inputInfo) {
        ReturnInfo returnInfo = new ReturnInfo();

        List dtoList = new ArrayList();
        DtoWeeklyReportSumOnMonthByPm dto1 = new DtoWeeklyReportSumOnMonthByPm();
        dto1.setUserId("x");
        dto1.setJobCode("y");
        dto1.setSumHour(new BigDecimal(1));
        dtoList.add(dto1);

        DtoWeeklyReportSumOnMonthByPm dto2 = new DtoWeeklyReportSumOnMonthByPm();
        dto2.setUserId("x");
        dto2.setJobCode("y");
        dto2.setSumHour(new BigDecimal(2));
        dtoList.add(dto2);

        DtoWeeklyReportSumOnMonthByPm dto3 = new DtoWeeklyReportSumOnMonthByPm();
        dto3.setUserId("x");
        dto3.setJobCode("y");
        dto3.setSumHour(new BigDecimal(3));
        dtoList.add(dto3);

        returnInfo.setReturnObj(DtoTcKey.WEEKLY_REPORT_SUM_LIST, dtoList);
        return returnInfo;
    }

    public void setRefreshFlag(){
        super.setParameter(null);
    }

    //表的高度
    public int getPreferredTableHeight() {
        int actualHeight = getTable().getRowCount() * VWJTable.TABLE_ROW_HEIGHT + TableWeeklyReport.HEADER_HEIGHT;
        return actualHeight > getMinmizeTableHeight() ? actualHeight : getMinmizeTableHeight();
    }

    //表的最小高度
    public int getMinmizeTableHeight() {
        return 1 * VWJTable.TABLE_ROW_HEIGHT + TableWeeklyReport.HEADER_HEIGHT;
    }

    //得到表的最大高度
    public int getMaxmizeTableHeight() {
        return 16 * VWJTable.TABLE_ROW_HEIGHT + TableWeeklyReport.HEADER_HEIGHT;
    }

    protected void setButtonEnabled() {
        if (this.isParameterValid == false) {
            btnExport.setEnabled(false);
        } else {
            btnExport.setEnabled(true);
        }
    }


    public static void main(String args[]) {
        VWWorkArea w1 = new VWWorkArea();
        VWWorkArea workArea = new VwTcListOnMonth();

        w1.addTab("Timecard", workArea);
        TestPanel.show(w1);

        Parameter param = new Parameter();
        param.put(DtoTcKey.BEGIN_PERIOD, new Date(105, 1, 1));
        param.put(DtoTcKey.END_PERIOD, new Date(105, 12, 30));
        param.put(DtoTcKey.ACNT_RID, new Long(1));
        workArea.setParameter(param);
        workArea.refreshWorkArea();
    }
}
