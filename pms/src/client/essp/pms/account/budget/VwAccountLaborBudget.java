package client.essp.pms.account.budget;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.cbs.budget.DtoBudgetLog;
import c2s.essp.cbs.budget.DtoCbsBudget;
import client.essp.cbs.budget.ChoosePeriod;
import client.essp.cbs.budget.VwBudgetLog;
import client.essp.cbs.budget.labor.VwLaborBudget;
import client.framework.view.vwcomp.VWJPopupEditor;
import com.wits.util.Parameter;
import com.wits.util.comDate;

public class VwAccountLaborBudget extends VwLaborBudget {
    static final String actionIdInitLog = "FCbsBudgetInitLogAction";
    VwBudgetLog budgetLog;
    public VwAccountLaborBudget(){
        super();
        JButton displayBt = this.getButtonPanel().addButton("column.png");
        displayBt.setToolTipText("display");
        displayBt.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                actionPerformedChoosePeriod();
            }
        });

        JButton logBt = this.getButtonPanel().addButton("pmLog.png");
        logBt.setToolTipText("log budget");
        logBt.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                actionPerformedLog();
            }
        });

    }

    private void actionPerformedChoosePeriod(){
        ChoosePeriod vwPeriod = new ChoosePeriod();
        Parameter param = new Parameter();
        List monthList = laborBudgetMonth.getMonthList();
        if(monthList != null && monthList.size() > 1){
            String firstMonth = (String) monthList.get(0);
            String lastMonth = (String) monthList.get(monthList.size() - 1);
            param.put("beginDate", comDate.toDate(firstMonth,DtoCbsBudget.MONTH_STYLE));
            param.put("endDate",comDate.toDate(lastMonth,DtoCbsBudget.MONTH_STYLE));
        }
        vwPeriod.setParameter(param);
        VWJPopupEditor pop = new VWJPopupEditor(this.getParentWindow(), "Choose Period",
            vwPeriod, vwPeriod);
        pop.show();
        if(vwPeriod.isOK()){
            DtoCbsBudget budget = this.getBudget();
            budget.setFromMonth(vwPeriod.getBeginDate());
            budget.setToMonth(vwPeriod.getEndDate());
            this.setBudget(budget);
            laborBudgetMonth.resetUI();
        }
    }

    private void actionPerformedLog(){
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(this.actionIdInitLog);
        inputInfo.setInputObj("acntRid",this.getBudget().getAcntRid());
        inputInfo.setInputObj("budgetRid",this.getBudget().getRid());
        ReturnInfo returnInfo = accessData(inputInfo);
        if(!returnInfo.isError()){
            DtoBudgetLog log = (DtoBudgetLog) returnInfo.getReturnObj("log");
//            DtoCbsBudget budget = (DtoCbsBudget) returnInfo.getReturnObj("budget");
            if(log != null){
                if(budgetLog == null)
                    budgetLog = new VwBudgetLog();
                budgetLog.setBudgetLog(log);
//                budgetLog.setBudgetType(budgetParam.getBudgetType());
                budgetLog.resetUI();
                VWJPopupEditor pop = new VWJPopupEditor(this.getParentWindow(), "Budget Log",
                    budgetLog,budgetLog);
                pop.show();
//                if(budgetLog.needRefreshParent()){
//                    this.getBudget().setLastAmt(this.getBudget().getCurrentAmt());
//                    this.getBudget().setLastPm(this.getBudget().getCurrentPm());
//                }
            }
        }

    }
}
