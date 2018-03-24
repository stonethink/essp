package client.essp.cbs.budget.labor;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JViewport;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import c2s.dto.IDto;
import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.cbs.budget.DtoCbsBudget;
import c2s.essp.cbs.budget.DtoResBudgtSum;
import client.essp.cbs.budget.ChoosePeriod;
import client.essp.common.view.VWGeneralWorkArea;
import client.framework.view.event.RowSelectedListener;
import client.framework.view.vwcomp.IVWPopupEditorEvent;
import client.framework.view.vwcomp.VWJPopupEditor;
import client.framework.view.vwcomp.VWJTable;
import com.wits.util.Parameter;
import client.framework.common.Global;

public class VwLaborBudget extends VWGeneralWorkArea  implements
    IVWPopupEditorEvent {
    static final String actionIdList = "FCbsLaborBudgetListAction";
    static final String actionIdCaculate = "FCbsLaborBudgetCaculateAction";
    static final String actionIdSave = "FCbsLaborBudgetSaveAction";
    private JSplitPane splitPane;

    private DtoCbsBudget budget;
    protected VwLaborBudgetSum laborBudgetSum;
    protected VwLaborBudgetMonth laborBudgetMonth;
    private boolean isParameerValid = true;
    JButton caculateBt ;
    private boolean refreshParent = true; //判断关闭窗口是否需刷新父窗口
    public VwLaborBudget(){
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        addUICEvent();
        setScroll();
    }

    private void setScroll() {
        JScrollPane sumScrollPane = laborBudgetSum.getTable().getScrollPane();
        JScrollPane monScrollPane = laborBudgetMonth.getTable().getScrollPane();

        sumScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        sumScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        sumScrollPane.setWheelScrollingEnabled(true);

        monScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        monScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        monScrollPane.setAutoscrolls(true);

        JViewport viewPortSum = sumScrollPane.getViewport();
        JViewport viewPortMon = monScrollPane.getViewport();
        viewPortSum.addChangeListener(new ScrollChangeListener(sumScrollPane,monScrollPane));
        viewPortMon.addChangeListener(new ScrollChangeListener(monScrollPane,sumScrollPane));
    }

    private void addUICEvent() {
        this.getButtonPanel().addSaveButton(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                actionPerformedSave();
            }
        });

        this.getButtonPanel().addLoadButton(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                resetUI();
            }
        });

        caculateBt  = this.getButtonPanel().addButton("calc.gif");
        caculateBt.setToolTipText("Recalculate labor cost");
        caculateBt.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                actionPerformedCaculate();
            }
        });
        laborBudgetMonth.getTable().getModel().addTableModelListener(new TableModelListener(){
            public void tableChanged(TableModelEvent e) {
                if(e.getType() == e.UPDATE){ //月数据更改同时刷新合计数据
                    laborBudgetSum.fireDataChanged();
                    laborBudgetSum.getTable().refreshTable();
                }
            }
        });

        laborBudgetSum.getTable().addRowSelectedListener(new RowSelectedListener(){
            public void processRowSelected() {
                processRowSelectedBgt(laborBudgetSum.getTable(),laborBudgetMonth.getTable());
            }
        });

        laborBudgetMonth.getTable().addRowSelectedListener(new RowSelectedListener(){
            public void processRowSelected() {
                processRowSelectedBgt(laborBudgetMonth.getTable(),laborBudgetSum.getTable());
            }
        });
    }
    /**
     * 同步两边表格的选择的行
     * @param initTable VWJTable
     * @param passiveTable VWJTable
     */
    protected void processRowSelectedBgt(VWJTable initTable,VWJTable passiveTable){
        int initRow = initTable.getSelectedRow();
        int passiveRow = passiveTable.getSelectedRow();
        if(initRow != passiveRow){
            passiveTable.setSelectRow(initRow);
            passiveTable.fireSelected();
        }
    }

    private void actionPerformedCaculate(){
        ChoosePeriod vwPeriod = new ChoosePeriod();
        Parameter param = new Parameter();
        param.put("endDate",budget.getToMonth());
        param.put("beginDate",Global.todayDate);
        vwPeriod.setParameter(param);
        VWJPopupEditor pop = new VWJPopupEditor(this.getParentWindow(), "Labor Budget",
                                                vwPeriod, vwPeriod);
        pop.show();
        boolean isOK = vwPeriod.isOK();
        if(isOK){
            Date begin = vwPeriod.getBeginDate();
            Date end = vwPeriod.getEndDate();
            InputInfo inputInfo = new InputInfo();
            inputInfo.setActionId(actionIdCaculate);
            inputInfo.setInputObj("budgetRid", budget.getRid());
            inputInfo.setInputObj("acntRid", budget.getAcntRid());
            inputInfo.setInputObj("beginDate", begin);
            inputInfo.setInputObj("endDate", end);
            ReturnInfo returnInfo = accessData(inputInfo);
            if (!returnInfo.isError()) {
                List laborBgtList = (List) returnInfo.getReturnObj(
                    "laborBgtList");
                if (laborBgtList != null) {
                    laborBudgetSum.getTable().setRows(laborBgtList);
                    laborBudgetMonth.getTable().setRows(laborBgtList);
                }
            }
            refreshParent = true;
        }
    }
    private void actionPerformedSave(){
        if(checkModified())
            saveData();
    }
    private void jbInit() {
        this.setLayout(new BorderLayout());
        laborBudgetSum = new VwLaborBudgetSum();
        laborBudgetMonth = new VwLaborBudgetMonth();

        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setBorder(BorderFactory.createEmptyBorder());
        splitPane.setDividerLocation(500);
        this.splitPane.setTopComponent(laborBudgetSum);
        this.splitPane.setBottomComponent(laborBudgetMonth);
        this.add(splitPane, BorderLayout.CENTER);
    }
    public void setParameter(Parameter param){
        budget = (DtoCbsBudget) param.get("budget");
        if(budget == null || budget.getRid() == null || budget.getAcntRid() == null)
            isParameerValid = false;
        else
            isParameerValid = true;
    }
    public void resetUI(){
        refreshParent = false;
        if (!isParameerValid) {
            this.getButtonPanel().setVisible(false);
            return;
        }
        this.getButtonPanel().setVisible(true);
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(this.actionIdList);
        inputInfo.setInputObj("budgetRid",budget.getRid());
        inputInfo.setInputObj("acntRid",budget.getAcntRid());
        ReturnInfo returnInfo = accessData(inputInfo);
        if(!returnInfo.isError()){
            List laborBgtList = (List) returnInfo.getReturnObj("laborBgtList");
            if(laborBgtList != null){
                laborBudgetSum.getTable().setRows(laborBgtList);
                laborBudgetMonth.getTable().setRows(laborBgtList);
                laborBudgetMonth.setMonthList(budget.getMonthList());
                laborBudgetMonth.resetUI();
            }
        }
    }
    private boolean checkModified(){
        DtoResBudgtSum dto = (DtoResBudgtSum) laborBudgetSum.getModel().getRow(0);
        if(dto == null || laborBudgetSum.getModel().getRows().size() <= 1)
            return false;
        return dto.isChanged();
    }
    private boolean saveData(){
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(this.actionIdSave);
        inputInfo.setInputObj("laborBgtList",laborBudgetSum.getModel().getRows());
        ReturnInfo returnInfo = accessData(inputInfo);
        refreshParent = true;
        if( !returnInfo.isError()){
            DtoResBudgtSum dto = (DtoResBudgtSum) laborBudgetSum.getModel().getRow(0);
            dto.setOp(IDto.OP_NOCHANGE);
            return true;
        }
        return false;
    }
    public DtoCbsBudget getBudget() {
        return budget;
    }

    public void setBudget(DtoCbsBudget budget) {
        this.budget = budget;
        laborBudgetMonth.setMonthList(budget.getMonthList());
    }

    public boolean onClickOK(ActionEvent e) {
        if(checkModified()){
            return saveData();
        }else {
            return true;
        }
    }

    public boolean onClickCancel(ActionEvent e) {
        return true;
    }

    public boolean needRefreashParent(){
        return this.refreshParent;
    }

}
