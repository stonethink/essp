package client.essp.cbs.budget;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JViewport;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;

import c2s.dto.IDto;
import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.cbs.budget.DtoBudgetLog;
import c2s.essp.cbs.budget.DtoBudgetParam;
import c2s.essp.cbs.budget.DtoCbsBudget;
import client.essp.cbs.budget.labor.ScrollChangeListener;
import client.essp.common.view.VWGeneralWorkArea;
import client.framework.view.event.RowSelectedListener;
import client.framework.view.vwcomp.VWJPopupEditor;
import client.framework.view.vwcomp.VWJTreeTable;
import com.wits.util.Parameter;
import c2s.essp.cbs.CbsConstant;
import client.framework.common.Global;

public class VwBudget extends VWGeneralWorkArea{
    static final String actionIdGetBudget = "FCbsBudgetGetAction";
    static final String actionIdSaveBudget = "FCbsBudgetSaveAction";
    static final String actionIdInitLog = "FCbsBudgetInitLogAction";
    private JSplitPane splitPane;
    DtoBudgetParam budgetParam;
    DtoCbsBudget budget;

    VwBudgetSum budgetSum;
    VwBudgetMonth budgetMonth;
    VwLaborBgtPopWin laborBgtPop;
    VwBudgetLog budgetLog;
    private boolean isParameerValid = true;
    private boolean isSaveOk = true;
    VwCurrencyLabel currencyLabel;

    VwAntiBgtPopWin vwAntiBudget ;
    JButton proBudgetBt ;
    public VwBudget(){
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        addUICEvent();
    }

    private void jbInit() {
        this.setLayout(new BorderLayout());
        budgetSum = new VwBudgetSum();
        budgetMonth = new VwBudgetMonth(budgetSum.getTreeTable());

        Dimension preSize = new Dimension(800,budgetSum.getTreeTable().getTableHeader().getPreferredSize().height);
        budgetMonth.getTable().getTableHeader().setPreferredSize(preSize);

        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setBorder(BorderFactory.createEmptyBorder());
        splitPane.setDividerLocation(350);
        this.splitPane.setTopComponent(budgetSum);
        this.splitPane.setBottomComponent(budgetMonth);
        this.add(splitPane, BorderLayout.CENTER);
        setColumnWidth();
        setScrollPane();
    }
    //设置滚动条同步
    private void setScrollPane(){
        JScrollPane sumScrollPane = budgetSum.getTreeTable().getScrollPane();
        JScrollPane monScrollPane = budgetMonth.getTable().getScrollPane();

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
    private void setColumnWidth() {
        VWJTreeTable table = budgetSum.getTreeTable();
        table.getColumnModel().getColumn(0).setPreferredWidth(100);
        table.getColumnModel().getColumn(1).setPreferredWidth(80);
        table.getColumnModel().getColumn(2).setPreferredWidth(80);
    }


    public void setParameter(Parameter param){
        super.setParameter(param);
        budgetParam = (DtoBudgetParam) param.get("budgetParam");
        if(budgetParam == null || budgetParam.getAcntRid() == null
           || budgetParam.getBudgetType() == null ){
            isParameerValid = false;
        }else{
            isParameerValid = true;
        }
    }

    public void resetUI(){
        if(!isParameerValid){
            this.getButtonPanel().setVisible(false);
            return;
        }
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(this.actionIdGetBudget);
        inputInfo.setInputObj("acntRid",budgetParam.getAcntRid());
        inputInfo.setInputObj("budgetType",budgetParam.getBudgetType());
        inputInfo.setInputObj("budgetRid",budgetParam.getBudgetRid());
        ReturnInfo returnInfo = accessData(inputInfo);
        if(!returnInfo.isError()){
            budget = (DtoCbsBudget) returnInfo.getReturnObj("budget");
            if(budget != null){
                budgetSum.setBudget(budget);
                budgetMonth.setBudget(budget);
                budgetSum.setReadOnly(budgetParam.isReadOnly());
                budgetMonth.setReadOnly(budgetParam.isReadOnly());
                budgetSum.resetUI();
                budgetMonth.resetUI();
                budgetParam.setBudgetRid(budget.getRid());
                budgetParam.setCurrency(budget.getCurrency());
                currencyLabel.setCurrency(budget.getCurrency());
//                budgetSum.setTreeInited(false);
            }
            if(budgetParam.isReadOnly()){
                this.getButtonPanel().setVisible(false);
            }else{
                this.getButtonPanel().setVisible(true);
            }
        }

        //建议预算加上查看期望预算的Button
        if(CbsConstant.PROPOSED_BUDGET.equals(budgetParam.getBudgetType())){
            proBudgetBt.setVisible(true);
        }else{
            proBudgetBt.setVisible(false);
        }
    }
    private void addUICEvent() {
        currencyLabel = new VwCurrencyLabel();
        this.getButtonPanel().add(currencyLabel);

        JButton laborBt = this.getButtonPanel().addButton("humanAllocate.gif");
        laborBt.setToolTipText("labor budget");
        laborBt.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                actionPerformedLaborBgt();
            }
        });

        JButton logBt = this.getButtonPanel().addButton("pmLog.png");
        logBt.setToolTipText("log budget");
        logBt.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                actionPerformedLog();
            }
        });
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

        JButton displayBt = this.getButtonPanel().addButton("column.png");
        displayBt.setToolTipText("display");
        displayBt.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                actionPerformedChoosePeriod();
            }
        });


        proBudgetBt = this.getButtonPanel().addButton("calc.gif");
        proBudgetBt.setToolTipText("Anticipated Budget");
        proBudgetBt.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                Parameter param6 = new Parameter();//期望预算参数
                DtoBudgetParam antiBudgetParam = new DtoBudgetParam();
                antiBudgetParam.setAcntRid(budgetParam.getAcntRid());
                antiBudgetParam.setBudgetType(CbsConstant.ANTICIPATED_BUDGET);//项目经理负责建议预算,EBS Manager负责期望预算
                antiBudgetParam.setIsReadOnly(budgetParam.isManager());
                antiBudgetParam.setIsCanModifyPrice(budgetParam.isManager());
                param6.put("budgetParam",antiBudgetParam);
                if(vwAntiBudget == null)
                    vwAntiBudget = new VwAntiBgtPopWin();
                vwAntiBudget.setParameter(param6);
                vwAntiBudget.resetUI();
                VWJPopupEditor pop = new VWJPopupEditor(getParentWindow(), "Anticipated Budget",
                    vwAntiBudget, vwAntiBudget);
                pop.show();
            }
        });

        budgetSum.getTreeTable().addRowSelectedListener(new RowSelectedListener(){
            public void processRowSelected() {
                int selectedRow = budgetSum.getTreeTable().getSelectedRow();
                int passiveRow = budgetMonth.getTable().getSelectedRow();
                if(passiveRow != selectedRow){
                    budgetMonth.getTable().setSelectRow(selectedRow);
                    budgetMonth.getTable().fireSelected();
                }
            }
        });

        budgetSum.getTreeTable().getTree().addTreeExpansionListener(new TreeExpansionListener (){
            public void treeCollapsed(TreeExpansionEvent event) {
//                if(budgetSum.isTreeInited()){
//                    DtoCbsBudgetItem budgetItem = (DtoCbsBudgetItem) budgetSum.
//                                                  getSelectedData();
//                    if (budgetItem != null) {
//                        budgetItem.setShow(false);
//                        budget.setOp(IDto.OP_MODIFY);
//                    }
//                    budgetMonth.getModel().fireTableDataChanged();
//                }
            }
            public void treeExpanded(TreeExpansionEvent event) {
//                if(budgetSum.isTreeInited()){
//                    DtoCbsBudgetItem budgetItem = (DtoCbsBudgetItem) budgetSum.
//                                                  getSelectedData();
//                    if (budgetItem != null) {
//                        budgetItem.setShow(true);
//                        budget.setOp(IDto.OP_MODIFY);
//                    }
//                    budgetMonth.getModel().fireTableDataChanged();
//                }
            }
        });
        budgetMonth.getTable().addRowSelectedListener(new RowSelectedListener(){
            public void processRowSelected() {
                int selectedRow = budgetMonth.getTable().getSelectedRow();
                int passiveRow = budgetSum.getTreeTable().getSelectedRow();
                if(passiveRow != selectedRow){
                    budgetSum.getTreeTable().setSelectedRow(selectedRow);
                    budgetSum.getTreeTable().fireSelected();
                }
            }
        });

        //expand
        JButton btnExpand = this.getButtonPanel().addButton("expand.png");
        btnExpand.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedExpand();
            }
        });
        btnExpand.setToolTipText("expand the tree");

    }
    private void actionPerformedExpand(){
        budgetSum.getTreeTable().expandsRow();
    }
    private void actionPerformedChoosePeriod(){
        ChoosePeriod vwPeriod = new ChoosePeriod();
        Parameter param = new Parameter();
        param.put("endDate",budget.getToMonth());
        param.put("beginDate",budget.getFromMonth());
        vwPeriod.setParameter(param);
        VWJPopupEditor pop = new VWJPopupEditor(this.getParentWindow(), "Choose Period",
            vwPeriod, vwPeriod);
        pop.show();
        if(vwPeriod.isOK()){
            budget.setFromMonth(vwPeriod.getBeginDate());
            budget.setToMonth(vwPeriod.getEndDate());
            budget.setOp(IDto.OP_MODIFY);
            budgetMonth.resetUI();
        }
    }

    private void actionPerformedSave(){
        if(checkModified())
            saveData();
    }

    private void actionPerformedLog(){
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(this.actionIdInitLog);
        inputInfo.setInputObj("acntRid",budgetParam.getAcntRid());
        inputInfo.setInputObj("budgetRid",budgetParam.getBudgetRid());
        ReturnInfo returnInfo = accessData(inputInfo);
        if(!returnInfo.isError()){
            DtoBudgetLog log = (DtoBudgetLog) returnInfo.getReturnObj("log");
            if(log != null){
                if(budgetLog == null)
                    budgetLog = new VwBudgetLog();
                budgetLog.setBudgetLog(log);
                budgetLog.setBudgetType(budgetParam.getBudgetType());
                budgetLog.resetUI();
                VWJPopupEditor pop = new VWJPopupEditor(this.getParentWindow(), "Budget Log",
                    budgetLog,budgetLog);
                pop.show();
            }
        }


    }
    private void actionPerformedLaborBgt(){
        if(laborBgtPop == null)
            laborBgtPop = new VwLaborBgtPopWin();
        laborBgtPop.laborBudget.setBudget(budget);
        laborBgtPop.resetUI();
        VWJPopupEditor pop = new VWJPopupEditor(this.getParentWindow(), "Labor Budget",
                                                       laborBgtPop, laborBgtPop.laborBudget);
        pop.show();
        if(laborBgtPop.laborBudget.needRefreashParent())
            resetUI();
    }

    private boolean checkModified(){
        if(budget == null)
            return false;
        return budget.isChanged();
    }

    private boolean saveData(){
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(this.actionIdSaveBudget);
        inputInfo.setInputObj("budget",budget);
        ReturnInfo returnInfo = accessData(inputInfo);
        if(!returnInfo.isError()){
            resetUI();
            return true;
        }
        return false;
    }

    public void saveWorkArea() {
        if (checkModified()){
            isSaveOk = false;
            if(confirmSaveWorkArea()){
                   isSaveOk = saveData();
            }else{
                isSaveOk = true;
            }
        }else{
            isSaveOk = true;
        }
    }
    public boolean isSaveOk(){
        return this.isSaveOk;
    }
}
