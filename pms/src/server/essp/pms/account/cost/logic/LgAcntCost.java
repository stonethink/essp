package server.essp.pms.account.cost.logic;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.sql.RowSet;

import c2s.essp.cbs.CbsConstant;
import c2s.essp.cbs.budget.DtoResBudgetItem;
import c2s.essp.common.calendar.WorkCalendarConstant;
import c2s.essp.pms.account.cost.DtoAcntCost;
import c2s.essp.pms.account.cost.DtoSum;
import db.essp.cbs.PmsAcntCost;
import db.essp.cbs.SysPrice;
import net.sf.hibernate.Session;
import server.essp.cbs.buget.logic.LgBuget;
import server.essp.cbs.buget.logic.LgLaborBgt;
import server.essp.framework.logic.AbstractESSPLogic;
import server.framework.common.BusinessException;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company:wistron </p>
 *
 * @author wuyi
 * @version 1.0
 */
public class LgAcntCost extends AbstractESSPLogic {
    public SysPrice getPrice(Long acntRid, String id, String catalog) {
        try {
            Session s = this.getDbAccessor().getSession();
            Object obj = s.createQuery("from SysPrice price " +
                                       "where price.acntRid=:acntRid " +
                                       "and price.catalog=:catalog " +
                                       "and id=:id "
                         )
                         .setParameter("acntRid", acntRid)
                         .setParameter("catalog", catalog)
                         .setParameter("id", id)
                         .uniqueResult();
            if (obj == null) {
                return null;
            }
            SysPrice price = (SysPrice) obj;
            return price;
        } catch (Exception ex) {
            throw new BusinessException("CBS_PRICE_0014",
                                        "error while getting price!", ex);
        }
    }
    /**
     *查找项目人力预算(已被审核的)同实际成本的比较
     *1.查找项目基线预算对应的人力预算
     *2.查找项目每个JobCode下的实际工时
     *3.合并两个结果集合
     **/
    public List load(String acntRid) throws BusinessException {
        List result = new ArrayList();

        Map laborBudgetMap = new HashMap();///Key:jobcodeId,Value:DtoResBudgetItem,该职等下的预算

        LgBuget budgetLg = new LgBuget();
        LgLaborBgt laborLg = new LgLaborBgt();
        //从项目的基线预算获得预算的人月及金额
        PmsAcntCost acntCost = budgetLg.getAcntCost(new Long(acntRid));
        if(acntCost != null){
            Long baseBudgetRid = acntCost.getBaseRid();
            if(baseBudgetRid != null){
                List budgetList = laborLg.listLaborBudget(baseBudgetRid);
                for(int i = 0 ;i < budgetList.size() ;i ++){
                    DtoResBudgetItem item = (DtoResBudgetItem) budgetList.get(i);
                    laborBudgetMap.put(item.getResId(),item);
                }
            }
        }

        DtoSum dtoSum = new DtoSum();
        dtoSum.setJobCode("SUM");
        double sumBdgPM = 0;
        double sumBdgAmt = 0;
        double sumActPM = 0;
        double sumActAmt = 0;

        try {
            //查找项目每个职等下的实际工时
            RowSet rs = this.getDbAccessor().executeQuery(
                "select t.position_level as jobcodeId,t.actual_hours as act_hours " +
                "from pms_acnt_actual_workhours t where t.acnt_rid='" + acntRid + "'");

             while (rs.next()) {
                DtoAcntCost dto = new DtoAcntCost();
                String jobcodeId = rs.getString("jobcodeId");
                double actHours = rs.getDouble("act_hours");
                double pm = actHours / WorkCalendarConstant.MONTHLY_WORK_HOUR;
                dto.setJobCodeId(jobcodeId);
                dto.setActualPm(new Double(pm));

                DtoResBudgetItem budgetItem = (DtoResBudgetItem) laborBudgetMap.get(
                    jobcodeId);
                //存在该职等的预算
                if (budgetItem != null) {
                    dto.setJobCode(budgetItem.getResName());
                    dto.setCurrency(budgetItem.getCurrency());
                    Double price = budgetItem.getPrice();
                    dto.setPrice(price);
                    dto.setBudgetPm(budgetItem.getUnitNum());
                    dto.setBudgetAmt(budgetItem.getAmt());
                    if (price != null)
                        dto.setActualAmt(new Double(price.doubleValue() * pm));
                    laborBudgetMap.remove(jobcodeId);

                    sumBdgPM += (budgetItem.getUnitNum() == null) ?
                        0D : budgetItem.getUnitNum().doubleValue();
                    sumBdgAmt += (budgetItem.getAmt() == null) ?
                        0D : budgetItem.getAmt().doubleValue();

                }
                //不存在该职等的预算
                else {
                    dto.setJobCode(getJobCode(jobcodeId));
                    dto.setBudgetPm(new Double(0));
                    dto.setBudgetAmt(new Double(0));
                    SysPrice sysPrice = getPrice(new Long(acntRid), jobcodeId,
                                                 CbsConstant.LABOR_RESOURCE);
                    if (sysPrice != null) {
                        dto.setCurrency(sysPrice.getCurrency());
                        dto.setPrice(sysPrice.getPrice());
                        dto.setActualAmt(new Double(sysPrice.getPrice().doubleValue() *
                                                    pm));
                    }
                }
                sumActPM += dto.getActualPm() == null ? 0D :
                    dto.getActualPm().doubleValue();
                sumActAmt += dto.getActualAmt() == null ? 0D :
                    dto.getActualAmt().doubleValue();
                result.add(dto);
            }

        } catch (Exception ex) {
            throw new BusinessException("LgAcntCost",
                                        "error while load account cost: code="
                                        , ex);
        }
        //处理有预算但还没有实际产生Cost的职等
        for(Iterator it = laborBudgetMap.keySet().iterator();it.hasNext();){
            String jobcodeId = (String) it.next();
            DtoResBudgetItem budgetItem = (DtoResBudgetItem) laborBudgetMap.get(jobcodeId);
            DtoAcntCost dto = new DtoAcntCost();
            dto.setJobCodeId(budgetItem.getResId());
            dto.setJobCode(budgetItem.getResName());
            dto.setCurrency(budgetItem.getCurrency());
            dto.setPrice( budgetItem.getPrice());
            dto.setBudgetPm(budgetItem.getUnitNum());
            dto.setBudgetAmt(budgetItem.getAmt());
            result.add(dto);

            sumBdgPM += (budgetItem.getUnitNum() == null) ?
                0D : budgetItem.getUnitNum().doubleValue();
            sumBdgAmt += (budgetItem.getAmt() == null) ?
                0D : budgetItem.getAmt().doubleValue();

        }
        //放置sum数据到列表的第一行
        Collections.sort(result,new SortByJobCode());

        dtoSum.setBudgetPm(new Double(sumBdgPM));
        dtoSum.setBudgetAmt(new Double(sumBdgAmt));
        dtoSum.setActualPm(new Double(sumActPM));
        dtoSum.setActualAmt(new Double(sumActAmt));
        result.add(0,dtoSum);
        return result;

    }
    private String getJobCode(String id){
        RowSet rs = this.getDbAccessor().executeQuery("select name_english from essp_hr_code_t t where t.kind_code='RANK' and trim(t.code)='"+id+"'");
            try {
                while(rs.next())
                    return rs.getString(1);
            } catch (SQLException ex) {
                throw new BusinessException(ex);
            }
            return "No JobCode";
    }

    private static class SortByJobCode implements Comparator{
        public int compare(Object o1, Object o2) {
            if(o1 instanceof DtoAcntCost
                && o2 instanceof DtoAcntCost ){
                DtoAcntCost d1 = (DtoAcntCost) o1;
                DtoAcntCost d2 = (DtoAcntCost) o2;
                return d1.getJobCodeId().compareTo(d2.getJobCodeId());
             }
             return 0;
        }
        public boolean equals(Object obj) {
            return false;
        }

    }
}
