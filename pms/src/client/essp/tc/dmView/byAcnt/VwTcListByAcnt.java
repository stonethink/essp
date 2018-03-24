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
import c2s.essp.tc.weeklyreport.DtoWeeklyReportSumByDmAcnt;
import client.essp.common.view.VWTableWorkArea;
import client.essp.common.view.VWWorkArea;
import client.framework.model.VMColumnConfig;
import client.framework.view.vwcomp.VWJReal;
import client.framework.view.vwcomp.VWJTable;
import client.framework.view.vwcomp.VWJText;
import com.wits.util.Parameter;
import com.wits.util.TestPanel;
import com.wits.util.comDate;
import client.essp.tc.common.RowRendererTwoNumber;
import javax.swing.table.TableColumn;

public class VwTcListByAcnt extends VWTableWorkArea {
    static final String actionIdList = "FTCDmViewWeeklyReportSumByAcntListAction";
    static final String actionIdIncSubList = "FTCDmViewWeeklyReportSumIncSubByAcntListAction";

    //cotrol data
    boolean isSaveOk = true;
    boolean isParameterValid = true;

    /**
     * define common data
     */
    private List tcList = new ArrayList();
    private Date beginPeriod;
    private Date endPeriod;
    String orgId = null;
    boolean isIncSub = false;

    JButton btnLoad;

    public VwTcListByAcnt() {
        try {
            VWJText text = new VWJText();
            VWJReal real = new VWJReal();
            real.setMaxInputDecimalDigit(2);

            Object[][] configs = new Object[][] {
                                 {"Account", "acntName", VMColumnConfig.UNEDITABLE, text},
                                 {"Account Type", "acntType", VMColumnConfig.UNEDITABLE, text},
                                 {"Actual hours", "", VMColumnConfig.UNEDITABLE, real},
                                 {"Overtime", "", VMColumnConfig.UNEDITABLE, real},
//                                 {"Allocated Hours", "", VMColumnConfig.UNEDITABLE, real},
//                                 {"Confirm", "confirmStatus",VMColumnConfig.UNEDITABLE, text},
            };

            model = new VMTcListByAcnt(configs);
            model.setDtoType(DtoWeeklyReportSumByDmAcnt.class);
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
        //actual hour column
        TableColumn actualHOurColumn = getTable().getColumn("Actual hours");
        actualHOurColumn.setCellRenderer(rowRenderer);
        //overtime column
        TableColumn overtimeColumn = getTable().getColumn("Overtime");
        overtimeColumn.setCellRenderer(rowRenderer);
//        //allocate column
//        TableColumn allocateColumn = getTable().getColumn("Allocated Hours");
//        allocateColumn.setCellRenderer(rowRenderer);

        getTable().getScrollPane().setBorder(null);
    }

    public void actionPerformedLoad(ActionEvent e) {
        resetUI();
    }

    public void setParameter(Parameter param) {

        //judge whether the parameter is changed
        boolean parameterChanged = false;
        Date newBeginPeriod = (Date) (param.get(DtoTcKey.BEGIN_PERIOD));
        Date newEndPeriod = (Date) (param.get(DtoTcKey.END_PERIOD));
        String newOrgId = (String) param.get(DtoTcKey.ORG);
        Boolean newIncSubFlag = (Boolean) param.get(DtoTcKey.INC_SUB);
        if (newIncSubFlag == null) {
            newIncSubFlag = Boolean.FALSE;
        }
        if (newBeginPeriod == null || newEndPeriod == null || newOrgId == null) {
            parameterChanged = true;
        } else {
            if (beginPeriod == null || comDate.compareDate(this.beginPeriod, newBeginPeriod) != 0
                || endPeriod == null || comDate.compareDate(this.endPeriod, newEndPeriod) != 0
                || orgId == null || newOrgId.equals(orgId) == false ||
                newIncSubFlag.booleanValue() != isIncSub
                    ) {
                parameterChanged = true;
            }
        }

        //set parameter to local variant
        this.beginPeriod = newBeginPeriod;
        this.endPeriod = newEndPeriod;
        this.orgId = newOrgId;
        this.isIncSub = newIncSubFlag.booleanValue();
        if (this.beginPeriod == null || this.endPeriod == null || orgId == null) {
            this.isParameterValid = false;
        } else {
            isParameterValid = true;
        }

        //只有参数变化了才会设置刷新标志
        if (parameterChanged) {
            super.setParameter(param);
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
    }

    protected InputInfo createInputInfoForList() {
        InputInfo inputInfo = new InputInfo();
        if (isIncSub) {
            inputInfo.setActionId(actionIdIncSubList);
        } else {
            inputInfo.setActionId(actionIdList);
        }
        inputInfo.setInputObj(DtoTcKey.ORG, this.orgId);
        inputInfo.setInputObj(DtoTcKey.BEGIN_PERIOD, this.beginPeriod);
        inputInfo.setInputObj(DtoTcKey.END_PERIOD, this.endPeriod);

        return inputInfo;
    }

    public static void main(String args[]) {
        VWWorkArea w1 = new VWWorkArea();
        VWWorkArea workArea = new VwTcListByAcnt();

        w1.addTab("Timecard", workArea);
        TestPanel.show(w1);

        Parameter param = new Parameter();
        param.put(DtoTcKey.BEGIN_PERIOD, new Date(105, 1, 1));
        param.put(DtoTcKey.END_PERIOD, new Date(105, 12, 30));
        param.put(DtoTcKey.ORG, "UNIT00000328");
        workArea.setParameter(param);
        workArea.refreshWorkArea();
    }
}
