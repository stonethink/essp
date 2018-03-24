package server.essp.cbs.cost.logic;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import c2s.dto.DtoUtil;
import c2s.dto.ITreeNode;
import c2s.essp.cbs.CbsConstant;
import c2s.essp.cbs.DtoPrice;
import c2s.essp.cbs.budget.DtoResBudgetItem;
import c2s.essp.cbs.cost.DtoCbsCostItem;
import c2s.essp.cbs.cost.DtoResCostItem;
import c2s.essp.cbs.cost.DtoResCostSum;
import c2s.essp.common.calendar.WorkCalendarConstant;
import db.essp.cbs.CbsCostLabor;
import db.essp.cbs.PmsAcntCost;
import db.essp.pms.LaborResource;
import itf.hr.HrFactory;
import itf.hr.IHrUtil;
import net.sf.hibernate.Session;
import server.essp.cbs.buget.logic.LgBuget;
import server.essp.cbs.buget.logic.LgLaborBgt;
import server.essp.cbs.config.logic.LgPrice;
import server.essp.framework.logic.AbstractESSPLogic;
import server.essp.pms.account.labor.logic.LgAccountLaborRes;
import server.framework.common.BusinessException;

public class LgLaborCost extends AbstractESSPLogic {

    /**
     * 列出Account对应的Labor Cost
     * @param acntRid Long
     * @return List
     */
    public List listLaborCost(Long acntRid){
        if(acntRid == null)
            throw new BusinessException("COST_0002","Can not get the cost data of null account");
        try {
            Session s = this.getDbAccessor().getSession();
            List l = s.createQuery("from CbsCostLabor laborCost " +
                                   "where laborCost.acntRid=:acntRid "
                     )
                     .setParameter("acntRid",acntRid)
                     .list();
            return l;
        } catch (Exception ex) {
            throw new BusinessException("COST_0003","error listing account labor cost!",ex);
        }
    }
    /**
     * 列出Account对应的Labor Cost和Budget
     * 1.查找acntRid对应的PmsAcntCost,若BaseLine Budget不为空列出BudgetLine Budget对应的Labor Budget
     *   否则列出Proposed Budget对应的Labor Budget；
     * 3.查找Account对应的Labor Cost，和Labor Budget合并组成DtoResCostItem
     * @param acntRid Long
     * @return List
     */
    public List listLaborCostDto(Long acntRid){
        if(acntRid == null)
            throw new BusinessException("COST_0010","Can not list null account labor cost!");
        Map result = new HashMap();
        List laborBudgets = null;
        LgBuget lgBudget = new LgBuget();
        PmsAcntCost acntCost = lgBudget.getAcntCost(acntRid);
        if(acntCost == null )
            throw new BusinessException("COST_0011","The cost of account ["+acntRid+"] can not be null!");

        LgLaborBgt lgLaborBgt = new LgLaborBgt();
        if(acntCost.getBaseId() != null && acntCost.getBaseRid() != null){
            laborBudgets = lgLaborBgt.listLaborBudget(acntCost.getBaseRid());
        }else{
            laborBudgets = lgLaborBgt.listLaborBudget(acntCost.getPropRid());
        }
        DtoResCostSum sum = new DtoResCostSum();
        if(laborBudgets != null)
                for(int i =0;i < laborBudgets.size() ;i ++){
                    DtoResCostItem costLaborDto = new DtoResCostItem();
                    DtoResBudgetItem laborBudget = (DtoResBudgetItem) laborBudgets.get(i);
                    DtoUtil.copyBeanToBean(costLaborDto,laborBudget);
                    costLaborDto.setBudgetAmt(laborBudget.getAmt());
                    costLaborDto.setBudgetUnitNum(laborBudget.getUnitNum());
                    costLaborDto.setRid(null);
                    costLaborDto.setDescription(null);
                    costLaborDto.setRut(null);
                    costLaborDto.setAcntRid(acntRid);

                    sum.addBudgetAmt(laborBudget.getAmt());
                    sum.addBudgetUnitNum(laborBudget.getUnitNum());
                    result.put(costLaborDto.getResId(),costLaborDto);
                }

        List laborCosts = listLaborCost(acntRid);

        for(Iterator it = laborCosts.iterator();it.hasNext();){
            CbsCostLabor costLabor = (CbsCostLabor) it.next();
            String jobCodeId = costLabor.getJobCodeId();
            DtoResCostItem costLaborDto = (DtoResCostItem) result.get(jobCodeId);
            if(costLaborDto == null){
                costLaborDto = new DtoResCostItem();
                DtoUtil.copyBeanToBean(costLaborDto, costLabor);
                costLaborDto.setResId(jobCodeId);
                costLaborDto.setResName(getJobCode(jobCodeId));
                costLaborDto.setAcntRid(acntRid);
            }else{
                costLaborDto.setRid(costLabor.getRid());
                costLaborDto.setCostAmt(costLabor.getCostAmt());
                costLaborDto.setCostUnitNum(costLabor.getCostUnitNum());
                costLaborDto.setRut(costLabor.getRut());
            }
            sum.addCostAmt(costLabor.getCostAmt());
            sum.addCostUnitNum(costLabor.getCostUnitNum());
            result.put(jobCodeId,costLaborDto);
        }
        List temp = new ArrayList(result.values());
        temp.add(0,sum);
        result.clear();
        return temp;
    }

    /**
     * 计算Account的人力成本
     * 1.列出Account对应的LaborCost，清除所有的Cost值
     * 2.列出Account所有的人力，获得每人的实际工作时间，计算其人月数和花费(判断计价单位和币别)，累加到该人对应的JobCode的成本上
     * @param acntRid Long
     */
    public void caculateLaborCost(Long acntRid){
        if(acntRid == null)
            throw new BusinessException("COST_0014","Can not caculate null account labor cost!");
        try {
            List laborCosts = listLaborCost(acntRid);
            Map laborCostMap = new HashMap();
            for(Iterator it = laborCosts.iterator();it.hasNext();){
                CbsCostLabor costLabor = (CbsCostLabor) it.next();
                costLabor.setCostAmt(null);
                costLabor.setCostUnitNum(null);
//                this.getDbAccessor().update(costLabor);
                laborCostMap.put(costLabor.getJobCodeId(),costLabor);
            }

            LgAccountLaborRes lgLaobrRes = new LgAccountLaborRes();
            List laborReses =  lgLaobrRes.listLaborRes(acntRid);
            for(int i = 0;i < laborReses.size();i ++){
                LaborResource resource = (LaborResource) laborReses.get(i);
                String jobCodeId = resource.getJobcodeId();
                Double actualWorkHour = resource.getActualWorkTime();
                double pm = actualWorkHour == null ? 0d :
                            actualWorkHour.doubleValue() / WorkCalendarConstant.MONTHLY_WORK_HOUR;
                DtoPrice priceDto = getPrice(acntRid,jobCodeId,CbsConstant.LABOR_RESOURCE);
                Double price = priceDto.getPrice();
                String priceUnit = priceDto.getUnit();
                //计价单位转换
                if(CbsConstant.LABOR_UNIT_PD.equals(priceUnit)){//PD单位转为PM
                    price = new Double(price.doubleValue() * WorkCalendarConstant.MONTHLY_WORK_DAY);
                }else if(CbsConstant.LABOR_UNIT_PH.equals(priceUnit)){//PH单位转为PM
                    price = new Double(price.doubleValue() * WorkCalendarConstant.MONTHLY_WORK_HOUR);
                }else if(!CbsConstant.LABOR_UNIT_PM.equals(priceUnit)){
                    throw new BusinessException("CBS_LABBGT_0003","Illegal labor unit : ["+priceUnit+"]");
                }
                double amt =  price.doubleValue() * pm;
                CbsCostLabor costLabor = (CbsCostLabor) laborCostMap.get(jobCodeId);
                if(costLabor == null){
                    costLabor = new CbsCostLabor();
                    costLabor.setAcntRid(acntRid);
                    costLabor.setJobCodeId(jobCodeId);
                    costLabor.setCostUnit(CbsConstant.LABOR_UNIT_PM);
                    costLabor.setPrice(priceDto.getPrice());
                    costLabor.setCurrency(priceDto.getCurrency());
                    costLabor.setCostAmt(new Double(amt));
                    costLabor.setCostUnitNum(new Double(pm));
                    this.getDbAccessor().save(costLabor);
                    laborCostMap.put(costLabor.getJobCodeId(),costLabor);
                }else{
                    costLabor.setPrice(priceDto.getPrice());
                    Double oldAmt = costLabor.getCostAmt();
                    Double newAmt = oldAmt == null ? new Double(amt) :
                                    new Double(oldAmt.doubleValue() + amt);
                    costLabor.setCostAmt(newAmt);
                    Double oldUnitNum = costLabor.getCostUnitNum();
                    Double newUnitNum = oldUnitNum == null ? new Double(pm) :
                                        new Double(oldUnitNum.doubleValue() + pm);
                    costLabor.setCostUnitNum(newUnitNum);
                }
                costLabor.setRut(new Date());
            }

            laborCostMap.clear();
        } catch (Exception ex) {
            throw new BusinessException("COST_0015","error while caculating labor cost of account ["+acntRid+"]!",ex);
        }
    }

//    public void updateDtoList(List laborCostList){
//        if(laborCostList == null)
//            return;
//        if(laborCostList == null || laborCostList.size() <= 1)
//            return;
//        boolean isCostChanged = false;
//        Long acntRid = null;
//        for(int i = 0;i < laborCostList.size();i ++){
//            DtoResCostItem costItem = (DtoResCostItem) laborCostList.get(i);
//            if(DtoResCostSum.SUM.equals(costItem.getResName())){
//                continue;
//            }
//            if(costItem.getRid() == null && costItem.getOp().equals(IDto.OP_MODIFY)){
//                save(costItem);
//            }else if(costItem.getRid() != null && costItem.getOp().equals(IDto.OP_MODIFY)){
//                update(costItem);
//            }
//            costItem.setOp(IDto.OP_NOCHANGE);
//            acntRid = costItem.getAcntRid();
//            isCostChanged = true;
//        }
//        if(isCostChanged && acntRid != null)
//            mergeLaborCost2Cost(acntRid);
//    }
//    private void save(DtoResCostItem costItem){
//        CbsCostLabor laborCost = new CbsCostLabor();
//        DtoUtil.copyBeanToBean(laborCost,costItem);
//        laborCost.setJobCodeId(costItem.getResId());
//
//        CbsCost cost = new CbsCost();
//        cost.setRid(costItem.getRid());
//        laborCost.setCbsCost(cost);
//        try {
//            this.getDbAccessor().save(cost);
//        } catch (Exception ex) {
//            throw new BusinessException("COST_0017","error while adding labor cost !",ex);
//        }
//    }
//    private void update(DtoResCostItem costItem){
//        try {
//            CbsCostLabor laborCost = (CbsCostLabor)this.getDbAccessor().load(
//                CbsCostLabor.class, costItem.getRid());
//            DtoUtil.copyBeanToBean(laborCost,costItem);
//            this.getDbAccessor().update(laborCost);
//        } catch (Exception ex) {
//            throw new BusinessException("COST_0018","error while updating labor cost !",ex);
//        }
//    }

    //将Labor Cost累加到Cost树上对应的Subject
//    private void mergeLaborCost2Cost(Long acntRid){
//        LgBuget lgBudget = new LgBuget();
//        PmsAcntCost acntCost = lgBudget.getAcntCost(acntRid);
//        if(acntCost == null || acntCost.getCostRid() == null)
//            throw new BusinessException("COST_0011","The cost of account ["+acntRid+"] can not be null!");
//        try {
//            Session s = this.getDbAccessor().getSession();
//            CbsCost cost = (CbsCost)s.load(CbsCost.class, acntCost.getCostRid(),LockMode.UPGRADE);
//            BLOB blob = (BLOB) cost.getCost();
//
//            Map subjectMap = new HashMap();
//            DtoTreeNode root = (DtoTreeNode) LgCbs.blobToObj(blob);
//            DtoCbsCostItem costItem = (DtoCbsCostItem) root.getDataBean();
//            subjectMap.put(costItem.getSubjectCode(),root);
//            List allChild = root.listAllChildren();
//            for(int i =0;i < allChild.size(); i ++){
//                DtoTreeNode node = (DtoTreeNode) allChild.get(i);
//                costItem = (DtoCbsCostItem) node.getDataBean();
//                subjectMap.put(costItem.getSubjectCode(),node);
//            }
//
//            Set laborCosts = cost.getCbsCostLabors();
//            List records = new ArrayList();
//            double pm = 0d;
//            for(Iterator it = laborCosts.iterator();it.hasNext();){
//                CbsCostLabor costLaobor = (CbsCostLabor) it.next();
//                String jobCodeId = costLaobor.getJobCodeId();
//                DtoPrice priceDto = getPrice(acntRid,jobCodeId,CbsConstant.LABOR_RESOURCE);
//                String subjectCode = priceDto.getSubjectCode();
//                DtoTreeNode subject = (DtoTreeNode) subjectMap.get(subjectCode);
//                if(subject != null && subject.getDataBean() != null){
//                    costItem = (DtoCbsCostItem) subject.getDataBean();
//                    if(records.contains(subjectCode)){
//                        costItem.addCostAmt(costLaobor.getCostAmt());
//                    }else{
//                        costItem.setCostAmt(costLaobor.getCostAmt());
//                        records.add(subjectCode);
//                    }
//                }
//                updateParent(subject);
//                Double unitNum = costLaobor.getCostUnitNum();
//                if(unitNum != null)
//                    pm = pm + unitNum.doubleValue();
//            }
//            records.clear();
//            LgCbs.ObjToBlob(root,blob);
//
//            costItem = (DtoCbsCostItem) root.getDataBean();
//            acntCost.setCostDate(new Date());
//            acntCost.setCostAmt(costItem.getCostAmt());
//            acntCost.setCostPm(new Double(pm));
//            s.update(acntCost);
//            s.flush();
//            subjectMap.clear();
//        } catch (Exception ex) {
//            throw new BusinessException("COST_0016","error while merging labor cost to account cost!",ex);
//        }
//
//    }

//    private void updateParent(ITreeNode node){
//        if(node == null)
//            return;
//        ITreeNode parent = node.getParent();
//        if(parent == null || parent.getDataBean() == null)
//            return;
//        DtoCbsCostItem parentCostItem = (DtoCbsCostItem) parent.getDataBean();
//        if( !DtoCbsCostItem.TYPE_AUTO_CALCULATE.equals(parentCostItem.getCostCalType())
//            || parent.children() == null || parent.children().size() <= 0)
//            return;
//        List children = parent.children();
//        double costAmt = 0d;
//        for(int i =0;i < children.size();i ++){
//            ITreeNode child = (ITreeNode) children.get(i);
//            DtoCbsCostItem costItem = (DtoCbsCostItem) child.getDataBean();
//            if(costItem.getCostAmt() != null)
//                costAmt = costAmt + costItem.getCostAmt().doubleValue();
//        }
//        parentCostItem.setCostAmt(new Double(costAmt));
//        updateParent(parent);
//    }

    private DtoPrice getPrice(Long acntRid,String id,String catalog){
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
    private String getJobCode(String jobCodeId){
        String jobCode = (String) jobCodeMap.get(jobCodeId);
        if(jobCode == null){
            if(hrUtil == null)
                hrUtil = HrFactory.create();
            jobCode = hrUtil.getJobCodeById(jobCodeId);
            if(jobCodeId != null)
                jobCodeMap.put(jobCodeId,jobCode);
        }
        return jobCode;
    }
    private Map jobCodeMap = new HashMap(); //缓存JobCode和Price，减少数据库查询次数
    private Map priceMap = new HashMap();
    LgPrice lgPrice;
    IHrUtil hrUtil;
}
