package server.essp.cbs.cost.logic;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import c2s.dto.DtoComboItem;
import c2s.dto.DtoTreeNode;
import c2s.dto.DtoUtil;
import c2s.dto.IDto;
import c2s.dto.ITreeNode;
import c2s.essp.cbs.CbsConstant;
import c2s.essp.cbs.DtoCbs;
import c2s.essp.cbs.DtoPrice;
import c2s.essp.cbs.DtoSubject;
import c2s.essp.cbs.budget.DtoCbsBudget;
import c2s.essp.cbs.budget.DtoCbsBudgetItem;
import c2s.essp.cbs.cost.DtoCbsCost;
import c2s.essp.cbs.cost.DtoCbsCostItem;
import c2s.essp.cbs.cost.DtoCostItem;
import c2s.essp.common.account.IDtoAccount;
import db.essp.cbs.CbsCostLabor;
import db.essp.cbs.PmsAcntCost;
import db.essp.cbs.PmsCostItem;
import itf.account.AccountFactory;
import net.sf.hibernate.Session;
import server.essp.cbs.buget.logic.LgBuget;
import server.essp.cbs.config.logic.LgCbs;
import server.essp.cbs.config.logic.LgPrice;
import server.essp.common.syscode.LgSysCurrency;
import server.essp.framework.logic.AbstractESSPLogic;
import server.framework.common.BusinessException;
import c2s.essp.cbs.cost.DtoCostItemSum;

public class LgCost extends AbstractESSPLogic {
    /**
     * ����Account��Ӧ��Cost
     * 1.����Account��PmsAcntCost,����PmsAcntCost���Cost
     * 2.1 ��PmsAcntCost�����ڣ�ͬ2.5
     * 2.2 �ж�PmsAcntCost��ǰ��BaseLine Budget�Ƿ���ڣ������ڲ��Ҹ�Budget��Cost�Ŀ�Ŀ���ṹ��Ԥ��ֵ��Copy��Budget�Ŀ�Ŀ
     * 2.3 ��BaseLine Budget�����ڣ����ҵ�ǰ�Ľ���Ԥ�㣬CostֻCopy��Budget�Ŀ�Ŀ���ṹ�� ��CopyԤ����ֵ
     * 2.4 ������Ԥ�㲻���ڣ���ʼ������Ԥ�㣬��Copy��ṹ
     * 3.����Cost��Ŀ���ṹ�и���Ŀ�µ�ֵ�����ø�ֵ
     *   3.1 ��CbsCostLabor��ͳ�������ɱ����ֵ�ֵ���ۼ�JobCode��Ӧ�Ŀ�Ŀ��
     *   3.2 ��PmsCostItem��ͳNonLabor��Expense���ֵ�ֵ���ۼӵ���Ӧ�Ŀ�Ŀ��
     * @param acntRid Long
     * @return DtoCbsCost
     */
    public DtoCbsCost getCostByAcnt(Long acntRid){
        if(acntRid == null)
            throw new BusinessException("COST_0001","Can not get the cost of account ["+acntRid+"]");
        DtoCbsCost result = null;

        LgBuget lgBudget = new LgBuget();
        PmsAcntCost acntCost = lgBudget.getAcntCost(acntRid);
        if(acntCost == null){
            DtoCbsBudget propBudget = lgBudget.initPropBudget(acntRid);
            acntCost=new PmsAcntCost();
            acntCost.setPropRid(propBudget.getRid());
            result = initFromBudget(propBudget,false);
        }else if(acntCost.getBaseRid() != null){
            Long baseRid = acntCost.getBaseRid();
            DtoCbsBudget baseBudget = lgBudget.getBudget(baseRid) ;
            if(baseBudget!=null){
                result = initFromBudget(baseBudget, true);
            }
        }else if(acntCost.getPropRid() != null){
            Long propRid = acntCost.getPropRid();
            DtoCbsBudget propBudget = lgBudget.getBudget(propRid) ;
            result = initFromBudget(propBudget,false);
        }else{
            DtoCbsBudget propBudget = lgBudget.initPropBudget(acntRid);
            acntCost.setPropRid(propBudget.getRid());
            result = initFromBudget(propBudget,false);
        }

        String currency = getAccountCurrency(acntRid);
        if(result!=null){
            result.setCurrency(currency);
            result.setAcntRid(acntRid);
            setCostData(result);
        }
        return result;
    }

    private String getAccountCurrency(Long acntRid) throws BusinessException {
        IDtoAccount account = AccountFactory.create().getAccountByRID(acntRid);
        String currency = account.getCurrency();
//        if(currency == null)
//            throw new BusinessException("COST_0003","The account currency can not be null while caculating cost");
        return currency;
    }
    /**
     * �г���Ŀ��������������ɱ���ϸ
     * @param acntRid Long
     * @return List
     */
    public List listOtherCostItem(Long acntRid){
        if(acntRid == null)
            throw new BusinessException("COST_0002","Can not get the cost data of null account");
        try {
            Session s = this.getDbAccessor().getSession();
            List l = s.createQuery("from PmsCostItem costItem " +
                                   "where costItem.acntRid=:acntRid "
                     )
                     .setParameter("acntRid",acntRid)
                     .list();
            return l;
        } catch (Exception ex) {
            throw new BusinessException("COST_0003","error listing account labor cost!",ex);
        }
    }
    /**
     * �г���Ŀ��������������ɱ���ϸ��ָ����ѯ�ɱ���Ŀ�ķ�Χ��Activity�ڻ�Account��
     * @param acntRid Long
     * @return List
     */
    public List listOtherCostItem(Long acntRid,String scope){
        if(acntRid == null)
            throw new BusinessException("COST_0002","Can not get the cost data of null account");
        try {
            Session s = this.getDbAccessor().getSession();
            List l = s.createQuery("from PmsCostItem costItem " +
                                   "where costItem.acntRid=:acntRid " +
                                   "and costItem.scope=:scope " +
                                   "order by costItem.costDate "
                     )
                     .setParameter("scope",scope)
                     .setParameter("acntRid",acntRid)
                     .list();
            return l;
        } catch (Exception ex) {
            throw new BusinessException("COST_0003","error listing account labor cost!",ex);
        }
    }
    public List listCostItemDto(Long acntRid,String scope){
        List result = new ArrayList();
        List l = listOtherCostItem(acntRid,scope);
//        DtoCostItemSum sum = new DtoCostItemSum();
        double amtSum = 0.0d;
        for(int i = 0;i < l.size();i ++){
            PmsCostItem costItem = (PmsCostItem) l.get(i);
            DtoCostItem dto = new DtoCostItem();
            DtoUtil.copyBeanToBean(dto,costItem);
            result.add(dto);
//            Double amt = dto.getAmt();
//            amtSum +=( amt == null ? 0.0d : amt.doubleValue());
        }
//        sum.setAmt(new Double(amtSum));
//        result.add(0,sum);
        return result;
    }
    /**
     * ɾ���ɱ���Ŀ
     * @param dto DtoCostItem
     */
    public void deleteCostItem(DtoCostItem dto){
        if(dto == null || dto.getRid() == null)
            throw new BusinessException("COST_0003","Can not delete  null cost item!");
        PmsCostItem costItem = new PmsCostItem(dto.getRid());
        try {
            this.getDbAccessor().delete(costItem);
//            if(DtoCostItem.ACTIVITY_SCOPE.equals(dto.getScope())){
//                Double oldAmt = dto.getAmt();
//                double increAmt = ( oldAmt == null ? 0.0d : -oldAmt.doubleValue());
//                ActivityPK pk = new ActivityPK(dto.getAcntRid(),dto.getActivityId());
//                PmsActivityCost activityCost = (PmsActivityCost) this.getDbAccessor().load(PmsActivityCost.class,pk);
//                if(DtoSubject.ATTRI_EXPENSE_SUM.equals(dto.getAttribute())){
//                    Double oldExpAmt = activityCost.getActualExpAmt();
//                    if(oldExpAmt != null){
//                        double amt =  oldExpAmt.doubleValue() + increAmt;
//                        if(amt < 0)
//                            amt = 0;
//                        activityCost.setActualExpAmt(new Double(amt));
//                    }
//                }else if(DtoSubject.ATTRI_NONLABOR_SUM.equals(dto.getAttribute())){
//                    Double oldNonLaborAmt = activityCost.getActualNonlaborAmt();
//                    if(oldNonLaborAmt != null){
//                        double amt = oldNonLaborAmt.doubleValue() + increAmt;
//                        if(amt < 0)
//                            amt = 0;
//                        activityCost.setActualNonlaborAmt(new Double(amt));
//                    }
//                }
//            }
        } catch (Exception ex) {
            throw new BusinessException("COST_0004"," error while delete cost item!",ex);
        }
    }
    public void addCostItem(DtoCostItem dto){
        if(dto == null ){
            throw new BusinessException("COST_0005","Can not add  null cost item!");
        }
        PmsCostItem costItem = new PmsCostItem();
        DtoUtil.copyBeanToBean(costItem,dto);
        try {
            this.getDbAccessor().save(costItem);
//            if(DtoCostItem.ACTIVITY_SCOPE.equals(dto.getScope())){
//                Double amt = dto.getAmt();
//                double increAmt = ( amt == null ? 0.0d : amt.doubleValue());
//                updateActivityCost(dto.getAcntRid(),dto.getActivityId(),dto.getAttribute(), increAmt);
//            }
        } catch (Exception ex) {
            throw new BusinessException("COST_0006","error add  cost item!",ex);
        }
    }
    public void updateCostItem(DtoCostItem dto){
        if(dto == null || dto.getRid() == null){
            throw new BusinessException("COST_0007","Can not update  null cost item!");
        }
        try {
            PmsCostItem costItem = (PmsCostItem)this.getDbAccessor().
                                   load(PmsCostItem.class, dto.getRid());
//            Double oldAmt = costItem.getAmt();
//            Double newAmt = dto.getAmt();
            DtoUtil.copyBeanToBean(costItem,dto);
            this.getDbAccessor().update(costItem);
            //����Activity�ڵ�Cost�����PmsAcitvityCost
//            if(DtoCostItem.ACTIVITY_SCOPE.equals(dto.getScope())){
//                double increAmt = 0.0;
//                if(oldAmt == null && newAmt != null)
//                    increAmt =  newAmt.doubleValue();
//                else if(oldAmt != null && newAmt != null){
//                    increAmt = newAmt.doubleValue() - oldAmt.doubleValue();
//                }else if(oldAmt != null && newAmt == null){
//                    increAmt = - oldAmt.doubleValue();
//                }
//                ActivityPK pk = new ActivityPK(dto.getAcntRid(),dto.getActivityId());
//                PmsActivityCost activityCost = (PmsActivityCost) this.getDbAccessor().load(PmsActivityCost.class,pk);
//                if(DtoSubject.ATTRI_EXPENSE_SUM.equals(dto.getAttribute())){
//                    Double oldExpAmt = activityCost.getActualExpAmt();
//                    if(oldExpAmt == null)
//                        activityCost.setActualExpAmt(newAmt);
//                    else{
//                        double amt =  oldExpAmt.doubleValue() + increAmt;
//                        activityCost.setActualExpAmt(new Double(amt));
//                    }
//                }else if(DtoSubject.ATTRI_NONLABOR_SUM.equals(dto.getAttribute())){
//                    Double oldNonLaborAmt = activityCost.getActualNonlaborAmt();
//                    if(oldNonLaborAmt == null){
//                        activityCost.setActualNonlaborAmt(newAmt);
//                    }else{
//                        double amt = oldNonLaborAmt.doubleValue() + increAmt;
//                        activityCost.setActualNonlaborAmt(new Double(amt));
//                    }
//                }
//            }
        } catch (Exception ex) {
            throw new BusinessException("COST_0008","error update cost item!",ex);
        }
    }

//    private void updateActivityCost(Long acntRid,Long activityId,String attribute, double increAmt) throws
//        Exception {
//        ActivityPK pk = new ActivityPK(acntRid,activityId);
//        PmsActivityCost activityCost = (PmsActivityCost) this.getDbAccessor().load(PmsActivityCost.class,pk);
//        if(DtoSubject.ATTRI_EXPENSE_SUM.equals(attribute)){
//            Double oldExpAmt = activityCost.getActualExpAmt();
//            double amt = (oldExpAmt == null ?  increAmt : oldExpAmt.doubleValue() + increAmt);
//            activityCost.setActualExpAmt(new Double(amt));
//        }else if(DtoSubject.ATTRI_NONLABOR_SUM.equals(attribute)){
//            Double oldNonLaborAmt = activityCost.getActualNonlaborAmt();
//            double amt = (oldNonLaborAmt == null ?  increAmt : oldNonLaborAmt.doubleValue() + increAmt);
//            activityCost.setActualNonlaborAmt(new Double(amt));
//        }
//    }
    public void updateCostItemList(List l){
        if(l == null || l.size() <= 0)
            return;
        for(int i = 0;i < l.size();i ++){
            DtoCostItem dto = (DtoCostItem) l.get(i);
            if(IDto.OP_INSERT.equals(dto.getOp())){
                addCostItem(dto);
                dto.setOp(IDto.OP_NOCHANGE);
            }else if(IDto.OP_DELETE.equals(dto.getOp())){
                deleteCostItem(dto);
                l.remove(dto);
            }else if(IDto.OP_MODIFY.equals(dto.getOp())){
                updateCostItem(dto);
                dto.setOp(IDto.OP_NOCHANGE);
            }
        }
    }
    /**
     * �г�Cost��Ŀ���ṹ�г��������ֵ�����������Ŀ
     * 1.����Cost��Ŀ�ṹ������Account��BaseLine Budget��Ϊ�����Ǹ�Budget�Ľṹ
     * �����ǽ���Ԥ��Ŀ�Ŀ�ṹ
     * 2.�������ṹ�е�AttributeΪNonLabor��Expense�Ŀ�Ŀ�����ӿ�Ŀ
     * @param acntRid Long
     * @return Vector
     */
    public Vector listCostItemSubjectCom(Long acntRid){
        Vector result = new Vector();
        LgBuget lgBudget = new LgBuget();
        PmsAcntCost acntCost = lgBudget.getAcntCost(acntRid);
        DtoCbsBudget budget = null ;
        if(acntCost == null){
            budget = lgBudget.initPropBudget(acntRid);
        }else if(acntCost.getBaseRid() != null){
            Long baseRid = acntCost.getBaseRid();
            budget = lgBudget.getBudget(baseRid);
        } else if(acntCost.getPropRid() != null){
            Long propRid = acntCost.getPropRid();
            budget = lgBudget.getBudget(propRid);
        }else{
            budget = lgBudget.initPropBudget(acntRid);
        }
        if(budget == null)
            return null;
        DtoTreeNode root = budget.getCbsRoot();
        DtoCbsBudgetItem dataBean = (DtoCbsBudgetItem) root.getDataBean();
        if(DtoSubject.ATTRI_EXPENSE_SUM.equals(dataBean.getSubjectAttribute()) ||
           DtoSubject.ATTRI_NONLABOR_SUM.equals(dataBean.getSubjectAttribute()) ){
            LgCbs lg = new LgCbs();
            return lg.listComboSubject(DtoCbs.DEFAULT_TYPE);
        }
        List allChildren = root.listAllChildrenByPreOrder();
        for(int i = 0;i < allChildren.size();i ++){
            DtoTreeNode node = (DtoTreeNode) allChildren.get(i);
            dataBean = (DtoCbsBudgetItem) node.getDataBean();
            String attribute = dataBean.getSubjectAttribute();
            if(DtoSubject.ATTRI_EXPENSE_SUM.equals(attribute) ||
               DtoSubject.ATTRI_NONLABOR_SUM.equals(attribute)){
                result.addAll(getSubjectCom(node,attribute));
            }
        }
        return result;
    }
    /**
     * ����Budget��ʼCost�Ŀ�Ŀ���ṹ
     * @param propBudget DtoCbsBudget
     * @param copyData boolean
     * @param acntRid Long
     * @return DtoCbsCost
     */
    private DtoCbsCost initFromBudget(DtoCbsBudget propBudget,boolean copyBudgetData){
        DtoCbsCost costDto = new DtoCbsCost();

        DtoTreeNode root = propBudget.getCbsRoot();
        DtoCbsBudgetItem dataBean = (DtoCbsBudgetItem) root.getDataBean();
        DtoCbsCostItem costRoot = new DtoCbsCostItem(dataBean,copyBudgetData);
        root.setDataBean(costRoot);
        List allChild = root.listAllChildrenByPreOrder();
        for(int i = 0 ;i < allChild.size() ;i ++){
            ITreeNode node = (ITreeNode) allChild.get(i);
            dataBean = (DtoCbsBudgetItem) node.getDataBean();
            DtoCbsCostItem costItem = new DtoCbsCostItem(dataBean,copyBudgetData);
            node.setDataBean(costItem);
        }
        costDto.setCostRoot(root);

        costDto.setCostDate(new Date());
        return costDto;
    }
    /**
     * ��Account��ÿ����Ŀ�ĳɱ�ֵ���õ���Ŀ���ṹ
     * 1.�����еĿ�Ŀ����Map�У�Key:subjectCode,Value:DtoCbsCostItem
     * 2.���������ɱ��������ɱ����ۼƵ����Ӧ�Ŀ�Ŀ��
     * @param costDto DtoCbsCost
     */
    private void setCostData(DtoCbsCost costDto){
        if(costDto == null || costDto.getAcntRid() == null)
            throw new BusinessException("COST_0002","Can not get the cost data of null account");
        Map subjectMap = new HashMap();
        DtoTreeNode root = costDto.getCostRoot();
        DtoCbsCostItem dataBean = (DtoCbsCostItem) root.getDataBean();
        subjectMap.put(dataBean.getSubjectCode(),root);
        List allChild = root.listAllChildrenByPreOrder();
        for(int i = 0 ;i < allChild.size() ;i ++){
            ITreeNode node = (ITreeNode) allChild.get(i);
            dataBean = (DtoCbsCostItem) node.getDataBean();
            subjectMap.put(dataBean.getSubjectCode(),node);
        }
        //�ۼ������ɱ�
        Long acntRid = costDto.getAcntRid();
        LgLaborCost lg = new LgLaborCost();
        List laborList = lg.listLaborCost(acntRid);
        for(int i = 0;i < laborList.size();i ++){
            CbsCostLabor labor = (CbsCostLabor) laborList.get(i);
            String jobCodeId = labor.getJobCodeId();
            String subjectCode = getSubjectCode(acntRid,jobCodeId);
            ITreeNode node = (ITreeNode) subjectMap.get(subjectCode);
            DtoCbsCostItem costItem = (DtoCbsCostItem) node.getDataBean();
            costItem.addCostAmt(labor.getCostAmt());
            updateParent(node,labor.getCostAmt());
        }
        //�ۼ��������͵ĳɱ�
        List costList = listOtherCostItem(acntRid);
        for(int i = 0;i < costList.size();i ++){
            PmsCostItem item = (PmsCostItem) costList.get(i);
            String subjectCode = item.getSubjectCode();
            Double amt = item.getAmt();
            String currency = item.getCurrency();
            String accountCurrency = getAccountCurrency(acntRid);
            if(!accountCurrency.equals(currency)){ //�жϳɱ���Ŀ�ıұ����Ŀ�ұ��Ƿ���ͬ
                LgSysCurrency lgCurrency = new LgSysCurrency();
                Double rate = lgCurrency.getExchRate(currency,accountCurrency);
                if(amt != null && rate != null){
                    amt = new Double(amt.doubleValue() * rate.doubleValue());
                }
            }
            ITreeNode node = (ITreeNode) subjectMap.get(subjectCode);
            DtoCbsCostItem costItem = (DtoCbsCostItem) node.getDataBean();
            costItem.addCostAmt(amt);
            updateParent(node,amt);
        }
    }
    private Vector getSubjectCom(DtoTreeNode node,String attribute){
        Vector result = new Vector();
        DtoCbsBudgetItem dataBean = (DtoCbsBudgetItem) node.getDataBean();
        DtoComboItem item = new DtoComboItem(dataBean.getSubjectCode(),
                                             dataBean.getSubjectCode());
        item.setItemRelation(attribute);
        result.add(item);
        List allChildren = node.listAllChildrenByPreOrder();
        for(int i = 0;i < allChildren.size();i ++){
            DtoTreeNode child = (DtoTreeNode) allChildren.get(i);
            dataBean = (DtoCbsBudgetItem) child.getDataBean();
            DtoComboItem comItem = new DtoComboItem(dataBean.getSubjectCode(),
                dataBean.getSubjectCode());
            comItem.setItemRelation(attribute);
            result.add(comItem);
        }
        return result;
    }
    //�ݹ���¸��ڵ㣬���ڵ�ľ�ֵ�ۼ�������
    private  void updateParent(ITreeNode node,Double increAmt){
        if(node == null)
            return;
        ITreeNode parent = node.getParent();
        if(parent == null || parent.getDataBean() == null)
            return;
        DtoCbsCostItem parentCostItem = (DtoCbsCostItem) parent.getDataBean();
        if( !DtoCbsCostItem.TYPE_AUTO_CALCULATE.equals(parentCostItem.getCostCalType())
            || parent.children() == null || parent.children().size() <= 0)
            return;
        parentCostItem.addCostAmt(increAmt);
        updateParent(parent,increAmt);
    }
    //����Price
    private String getSubjectCode(Long acntRid,String jobCodeId){
        DtoPrice priceDto = getPrice(acntRid,jobCodeId,CbsConstant.LABOR_RESOURCE);
        String subjectCode = priceDto.getSubjectCode();
        return subjectCode;
    }
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
    private Map priceMap = new HashMap();
    LgPrice lgPrice;

    public static void main(String[] args){
        LgCost lg = new LgCost();
        DtoCbsCost dto = lg.getCostByAcnt(new Long(1));
        System.out.println("end");
    }
}
