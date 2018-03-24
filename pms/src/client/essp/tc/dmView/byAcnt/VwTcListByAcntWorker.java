package client.essp.tc.dmView.byAcnt;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.tc.weeklyreport.DtoTcKey;
import c2s.essp.tc.weeklyreport.DtoWeeklyReportSumByDmAcntWorker;
import client.essp.common.view.VWTableWorkArea;
import client.essp.common.view.VWWorkArea;
import client.framework.model.VMColumnConfig;
import client.framework.view.vwcomp.VWJReal;
import client.framework.view.vwcomp.VWJTable;
import client.framework.view.vwcomp.VWJText;
import com.wits.util.Parameter;
import com.wits.util.TestPanel;
import client.essp.tc.common.RowRendererTwoNumber;
import javax.swing.table.TableColumn;
import client.essp.common.loginId.VWJLoginId;

public class VwTcListByAcntWorker extends VWTableWorkArea {
    static final String actionIdList = "FTCDmViewWeeklyReportSumByAcntUserListAction";

    //cotrol data
    boolean isSaveOk = true;
    boolean isParameterValid = true;

    /**
     * define common data
     */
    private List tcList = new ArrayList();
    private Date beginPeriod;
    private Date endPeriod;
    Long acntRid = null;
    Object[][] configsOnWeek;
    Object[][] configsOnMonth;

    JButton btnLoad;

    public VwTcListByAcntWorker() {
        try {
            VWJText text = new VWJText();
            VWJReal real = new VWJReal();
            real.setMaxInputDecimalDigit(2);

            configsOnWeek = new Object[][] {
                            {"Worker", "userId", VMColumnConfig.UNEDITABLE, new VWJLoginId()},
                            {"Job Code", "jobCode", VMColumnConfig.UNEDITABLE, text},
                            {"Actual hours","", VMColumnConfig.UNEDITABLE, real},
                            {"Overtime", "", VMColumnConfig.UNEDITABLE, real},
                            {"Allocated Hours", "",VMColumnConfig.UNEDITABLE, real},
                            {"Confirm", "confirmStatus", VMColumnConfig.UNEDITABLE, text},
                            {"Comments", "comments",VMColumnConfig.UNEDITABLE, text},
            };

            configsOnMonth = new Object[][] {
                            {"Worker", "userId", VMColumnConfig.UNEDITABLE, text},
                            {"Job Code", "jobCode", VMColumnConfig.UNEDITABLE, text},
                            {"Period", "periodInfo", VMColumnConfig.UNEDITABLE,text},
                            {"Actual hours","", VMColumnConfig.UNEDITABLE, real},
                            {"Overtime", "", VMColumnConfig.UNEDITABLE, real},
                            {"Allocated Hours", "",VMColumnConfig.UNEDITABLE, real},
                            {"Confirm", "confirmStatus", VMColumnConfig.UNEDITABLE, text},
                            {"Comments", "comments",VMColumnConfig.UNEDITABLE, text},
            };

            model = new VMTcListByAcntWorker(configsOnWeek);
            model.setDtoType(DtoWeeklyReportSumByDmAcntWorker.class);
            table = new VWJTable(model);

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
    }

    //设置表格的外观
    private void initUI() {
        //不容许调整行的顺序
        table.getTableHeader().setReorderingAllowed(false);

        RowRendererTwoNumber rowRenderer = new RowRendererTwoNumber();
        //overtime column
        TableColumn overtimeColumn = getTable().getColumn("Overtime");
        overtimeColumn.setCellRenderer(rowRenderer);
        //allocate column
        TableColumn allocateColumn = getTable().getColumn("Allocated Hours");
        allocateColumn.setCellRenderer(rowRenderer);

        getTable().getScrollPane().setBorder(null);
    }

    public void actionPerformedLoad(ActionEvent e) {
        resetUI();
    }

    public void setParameter(Parameter param) {
        //set parameter to local variant
        this.beginPeriod = (Date) param.get(DtoTcKey.BEGIN_PERIOD);
        this.endPeriod = (Date) param.get(DtoTcKey.END_PERIOD);
        this.acntRid = (Long) param.get(DtoTcKey.ACNT_RID);

        if (this.beginPeriod == null || this.endPeriod == null || acntRid == null) {
            this.isParameterValid = false;
        } else {
            isParameterValid = true;
        }

        super.setParameter(param);
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
    }

    protected InputInfo createInputInfoForList() {
        InputInfo inputInfo = new InputInfo();

        inputInfo.setActionId(actionIdList);
        inputInfo.setInputObj(DtoTcKey.ACNT_RID, this.acntRid);
        inputInfo.setInputObj(DtoTcKey.BEGIN_PERIOD, this.beginPeriod);
        inputInfo.setInputObj(DtoTcKey.END_PERIOD, this.endPeriod);

        return inputInfo;
    }

    public void setWeekVisible(){
        getModel().getColumnConfigs().clear();
        getModel().getColumnMap().clear();
        getModel().setColumnConfigs(configsOnWeek);
        resetRenderAndEditor();
    }

    public void setMonthVisible(){
        getModel().getColumnConfigs().clear();
        getModel().getColumnMap().clear();
        getModel().setColumnConfigs(configsOnMonth);
        resetRenderAndEditor();
    }

    private void resetRenderAndEditor() {
        getTable().setRenderAndEditor();

        TableColumn periodColumn = null;
        try {
            periodColumn = getTable().getColumn("Period");
            periodColumn.setPreferredWidth(150);
        } catch (Exception ex) {
        }

        RowRendererTwoNumber rowRenderer = new RowRendererTwoNumber();
        //overtime column
        TableColumn overtimeColumn = getTable().getColumn("Overtime");
        overtimeColumn.setCellRenderer(rowRenderer);
        //allocate column
        TableColumn allocateColumn = getTable().getColumn("Allocated Hours");
        allocateColumn.setCellRenderer(rowRenderer);
    }

    public static void main(String args[]) {
        VWWorkArea w1 = new VWWorkArea();
        VWWorkArea workArea = new VwTcListByAcntWorker();

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
