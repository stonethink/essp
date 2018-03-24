package server.essp.pms.account.baseline.action;

import server.framework.action.AbstractBusinessAction;
import server.framework.common.BusinessException;
import javax.servlet.http.HttpServletRequest;
import c2s.dto.TransactionData;
import javax.servlet.http.HttpServletResponse;
import server.essp.cbs.buget.logic.LgBuget;
import c2s.essp.cbs.budget.DtoBudgetLog;
import db.essp.cbs.PmsAcntCost;
import java.util.List;
import java.util.ArrayList;
import java.text.DecimalFormat;

public class AcntBLBud extends AbstractBusinessAction {

    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData data) throws
        BusinessException {
        Long acntRid = (Long) data.getInputInfo().getInputObj("acntRid");
            int flag1=-1;
            int flag2=-1;
                LgBuget lg = new LgBuget();
                PmsAcntCost acntCost = lg.getAcntCost(acntRid);
                if(acntCost == null)
                    throw new BusinessException("","Before logging a budget ,you must create the budget!");
                DtoBudgetLog log = new DtoBudgetLog();
                if(acntCost.getBaseAmt()!=null){
               log.setBaseAmt(new Double(new DecimalFormat( ".00" ).format(acntCost.getBaseAmt().doubleValue())) );}
                 else log.setBaseAmt(new Double(0.00));
                 if(acntCost.getBasePm()!=null){
                log.setBasePm(new Double(new DecimalFormat( ".00" ).format(acntCost.getBasePm().doubleValue())) );}
                else log.setBasePm(new Double(0.00));
                log.setBaseId(acntCost.getBaseId());
                log.setChangeBugetAmt(new Double(new DecimalFormat( ".00" ).format(acntCost.getPropAmt().doubleValue())) );
                log.setChangeBugetPm(new Double(new DecimalFormat( ".00" ).format(acntCost.getPropPm().doubleValue())) );


                System.out.println("BugetAmt:"+log.getBaseAmt());
                System.out.println("BugetPm:"+log.getBasePm());
                List loglist=new ArrayList();
                loglist.add(0,log);
                if(!acntCost.getBaseAmt().equals(acntCost.getPropAmt()))
                    flag2=1;
             if(!acntCost.getBasePm().equals(acntCost.getPropPm()))
                    flag1=0;
         data.getReturnInfo().setReturnObj("flag1",new Integer(flag1));
         data.getReturnInfo().setReturnObj("flag2",new Integer(flag2));
        data.getReturnInfo().setReturnObj("BaseId",log.getBaseId());
        data.getReturnInfo().setReturnObj("BLBud",loglist);
    }

}
