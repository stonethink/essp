package server.essp.cbs.cost.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import c2s.dto.DtoUtil;
import c2s.essp.cbs.CbsConstant;
import c2s.essp.cbs.DtoPrice;
import c2s.essp.cbs.cost.DtoAcitvityLaborCost;
import c2s.essp.cbs.cost.DtoActivityCost;
import c2s.essp.cbs.cost.DtoActivityCostSum;
import c2s.essp.cbs.cost.DtoCostItem;
import c2s.essp.pms.wbs.DtoActivityWorker;
import db.essp.cbs.PmsActivityCost;
import db.essp.cbs.PmsCostItem;
import db.essp.pms.Activity;
import db.essp.pms.ActivityPK;
import net.sf.hibernate.Session;
import server.essp.cbs.config.logic.LgPrice;
import server.essp.framework.logic.AbstractESSPLogic;
import server.essp.pms.activity.logic.LgActivityWorker;
import server.framework.common.BusinessException;

public class LgActivityCost extends AbstractESSPLogic {
    /**
     * 列出Account下每个Activity对应的Cost
     * @param acntRid Long
     * @return List
     */
    public List listActivityCostByAcnt(Long acntRid){
        if(acntRid == null)
            throw new BusinessException("ACT_COST_00001","Can not list null account activity cost!");
        List result = new ArrayList();
        try {
            Session s = this.getDbAccessor().getSession();
            List l = s.createQuery("from PmsActivityCost as actcost " +
                                   "where actcost.pk.acntRid=:acntRid " +
                                   "order by actcost.activity.plannedStart " )
                     .setParameter("acntRid",acntRid)
                     .list();
            for(int i = 0;i < l.size();i ++){
                PmsActivityCost acitvityCost = (PmsActivityCost) l.get(i);
                Activity activity = acitvityCost.getActivity();
                DtoActivityCost dto = new DtoActivityCost();
                DtoUtil.copyBeanToBean(dto,acitvityCost);
                dto.setAcntRid(acntRid);
                dto.setActivityId(activity.getPk().getActivityId());
                dto.setActivityCode(activity.getCode());
                dto.setActivityName(activity.getName());
                result.add(dto);
            }
        } catch (Exception ex) {
            throw new BusinessException("ACT_COST_00002","list account ["+acntRid+"] acitivty cost error!",ex);
        }
        Collections.sort(result,new ActivityCostComparator());
        addSum(result);
        return result;
    }
    /**
     * 查找Activity下的所有Worker，按JobCod分类
     * @param acntRid Long
     * @param activityId Long
     * @return List
     */
    public List listActLaborCostItem(Long acntRid,Long activityId){
        LgActivityWorker lg = new LgActivityWorker();
        List workers = lg.listActivityWorkerDto(acntRid,activityId);
        Map jobCodeMap = new HashMap();
        for(int i = 0;i < workers.size() ; i ++){
            DtoActivityWorker worker = (DtoActivityWorker) workers.get(i);
            String jobCodeId = worker.getJobcodeId();
            DtoAcitvityLaborCost dto = (DtoAcitvityLaborCost) jobCodeMap.get(jobCodeId);
            DtoPrice priceDto = getPrice(acntRid,jobCodeId);
            if(dto == null){
                dto = new DtoAcitvityLaborCost();
                dto.setJobCode(worker.getJobCode());
                if(priceDto!=null){
                    dto.setPrice(priceDto.getPrice());
                }// ????定价需根据单位（PM，PH，PD）转换
                jobCodeMap.put(jobCodeId,dto);
            }
            dto.addBudgetPh(worker.getPlanWorkTime());
            dto.addActualPh(worker.getActualWorkTime());
        }
        List result = new ArrayList(jobCodeMap.values());
        jobCodeMap.clear();
        return result;
    }
    /**
     * 查找Activity中属于Labor或NonLabor的CostItem
     * @return List
     */
    public List listActCostItemByAttr(Long acntRid,Long activityId,String attribute){
        if(acntRid == null || activityId == null)
            throw new BusinessException("ACT_COST_00007","can not list null activity cost item!");
        List result = new ArrayList();
        try {
            Session s = this.getDbAccessor().getSession();
            List l = s.createQuery("from PmsCostItem costItem " +
                                   "where costItem.acntRid=:acntRid " +
                                   "and costItem.activityId=:activityId " +
                                   "and costItem.scope=:scope " +
                                   "and costItem.attribute=:attribute " +
                                   "order by costItem.costDate"
                     )
                     .setParameter("acntRid",acntRid)
                     .setParameter("activityId",activityId)
                     .setParameter("scope",DtoCostItem.ACTIVITY_SCOPE)
                     .setParameter("attribute",attribute)
                     .list();
            for(int i = 0;i < l.size();i ++){
                PmsCostItem costItem = (PmsCostItem) l.get(i);
                DtoCostItem dto = new DtoCostItem();
                DtoUtil.copyBeanToBean(dto,costItem);
                result.add(dto);
            }
        } catch (Exception ex) {
            throw new BusinessException("ACT_COST_00008","error list activity cost item!",ex);
        }
        return result;
    }
    public DtoActivityCost getActivityCost(Long acntRid,Long activityId){
        if(acntRid == null || activityId == null)
            throw new BusinessException("ACT_COST_00010","can not get null acitivyt cost!");
        ActivityPK pk = new ActivityPK(acntRid,activityId);
        try {
            PmsActivityCost cost = (PmsActivityCost) this.getDbAccessor().load(PmsActivityCost.class, pk);
            DtoActivityCost dto = new DtoActivityCost();
            DtoUtil.copyBeanToBean(dto,cost);
            dto.setAcntRid(acntRid);
            dto.setActivityId(activityId);
            return dto;
        } catch (Exception ex) {
            throw new BusinessException("ACT_COST_00011","error get null acitivyt cost!",ex);
        }
    }
    //列表中新增合计数值
    private void addSum(List activityCostList){
        DtoActivityCostSum sum = new DtoActivityCostSum();
        long budgetPhSum = 0L,actualPhSum = 0L;
        double budgetLaborAmtSum = 0.0d,budgetNonlaborAmtSum = 0.0d,budgetExpAmtSum = 0.0d;
        double actualLaborAmtSum = 0.0d,actualNonlaborAmtSum = 0.0d,actualExpAmtSum = 0.0;;;
        for(int i = 0;i < activityCostList.size();i ++){
            DtoActivityCost dto = (DtoActivityCost) activityCostList.get(i);
            Long budgetPh = dto.getBudgetPh();
            budgetPhSum +=(budgetPh == null ? 0L : budgetPh.longValue());

            Long actualPh = dto.getActualPh();
            actualPhSum +=(actualPh == null ? 0L : actualPh.longValue());

            Double budgetLaborAmt = dto.getBudgetLaborAmt();
            budgetLaborAmtSum += (budgetLaborAmt == null ? 0.0d : budgetLaborAmt.doubleValue());

            Double budgetNonLabor = dto.getBudgetNonlaborAmt();
            budgetNonlaborAmtSum +=(budgetNonLabor == null ? 0.0d : budgetNonLabor.doubleValue());

            Double budgetExpens = dto.getBudgetExpAmt();
            budgetExpAmtSum +=(budgetExpens == null ? 0.0d : budgetExpens.doubleValue());

            Double actualLaborAmt = dto.getActualLaborAmt();
            actualLaborAmtSum += (actualLaborAmt == null ? 0.0d : actualLaborAmt.doubleValue());

            Double actualNonLabor = dto.getActualNonlaborAmt();
            actualNonlaborAmtSum +=(actualNonLabor == null ? 0.0d : actualNonLabor.doubleValue());

            Double actualExpens = dto.getActualExpAmt();
            actualExpAmtSum +=(actualExpens == null ? 0.0d : actualExpens.doubleValue());
        }
        sum.setBudgetPh(new Long(budgetPhSum));
        sum.setBudgetLaborAmt(new Double(budgetLaborAmtSum));
        sum.setBudgetNonlaborAmt(new Double(budgetNonlaborAmtSum));
        sum.setBudgetExpAmt(new Double(budgetExpAmtSum));

        sum.setActualPh(new Long(actualPhSum));
        sum.setActualExpAmt(new Double(actualExpAmtSum));
        sum.setActualNonlaborAmt(new Double(actualNonlaborAmtSum));
        sum.setActualLaborAmt(new Double(actualLaborAmtSum));
        activityCostList.add(0,sum);
    }
    public void updateActivityCost(DtoActivityCost dto){
        if(dto == null || dto.getAcntRid() == null || dto.getActivityId() == null)
            throw new BusinessException("ACT_COST_00005","Can not update null account activity cost!");
        ActivityPK pk = new ActivityPK(dto.getAcntRid(),dto.getActivityId());
        try {
            PmsActivityCost acitvityCost = (PmsActivityCost)this.getDbAccessor().
                                           load(PmsActivityCost.class, pk);
            DtoUtil.copyBeanToBean(acitvityCost,dto);
            this.getDbAccessor().update(acitvityCost);
        } catch (Exception ex) {
            throw new BusinessException("ACT_COST_00006","error update account activity cost!",ex);
        }

    }

    public void saveActivityCost(DtoActivityCost dto){
        if(dto == null || dto.getAcntRid() == null || dto.getActivityId() == null)
            throw new BusinessException("ACT_COST_00007","Can not add null account activity cost!");
        ActivityPK pk = new ActivityPK(dto.getAcntRid(),dto.getActivityId());
        PmsActivityCost acitvityCost = new PmsActivityCost();
        DtoUtil.copyBeanToBean(acitvityCost,dto);
        acitvityCost.setPk(pk);
        try {
            this.getDbAccessor().save(acitvityCost);
        } catch (Exception ex) {
            throw new BusinessException("ACT_COST_00008","error add account activity cost!",ex);
        }
    }
    /**
     * 对Activity Cost按ActivityId排序
     */
    class ActivityCostComparator implements Comparator{
        public boolean equals(Object obj) {
                return false;
        }

        public int compare(Object o1, Object o2) {
            if(o1 instanceof DtoActivityCost && o2 instanceof DtoActivityCost ){
                DtoActivityCost cost1 = (DtoActivityCost) o1;
                DtoActivityCost cost2 = (DtoActivityCost) o2;
                return cost1.getActivityId().intValue() - cost2.getActivityId().intValue();
            }else{
                return o1.hashCode() - o2.hashCode();
            }
        }
    }
    //缓存Price
    private DtoPrice getPrice(Long acntRid,String id){
        String catalog = CbsConstant.LABOR_RESOURCE;
        DtoPrice dtoPrice = (DtoPrice) priceMap.get(wapperPriceKey(acntRid,id,catalog));
        if(dtoPrice == null){
            if(lgPrice == null)
                lgPrice = new LgPrice();
            dtoPrice = lgPrice.getPrice(acntRid,id,catalog);
            if(dtoPrice != null)
                priceMap.put(wapperPriceKey(acntRid,id,catalog),dtoPrice);
        }
        return dtoPrice;
    }
    private String wapperPriceKey(Long acntRid,String id,String catalog){
        return acntRid + "--" + id + "--" + catalog;
    }
    private Map priceMap = new HashMap();
    LgPrice lgPrice;
}
