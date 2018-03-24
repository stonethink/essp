package server.essp.cbs.buget.logic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import c2s.dto.DtoTreeNode;
import c2s.dto.DtoUtil;
import c2s.dto.ITreeNode;
import c2s.essp.cbs.CbsConstant;
import c2s.essp.cbs.DtoCbs;
import c2s.essp.cbs.DtoSubject;
import c2s.essp.cbs.budget.DtoBudgetLog;
import c2s.essp.cbs.budget.DtoCbsBudget;
import c2s.essp.cbs.budget.DtoCbsBudgetItem;
import c2s.essp.cbs.budget.DtoResBudgetItem;
import c2s.essp.common.account.IDtoAccount;
import db.essp.cbs.CbsBudget;
import db.essp.cbs.CbsBudgetLog;
import db.essp.cbs.PmsAcntCost;
import itf.account.AccountFactory;
import itf.account.IAccountUtil;
import net.sf.hibernate.Hibernate;
import net.sf.hibernate.LockMode;
import net.sf.hibernate.Session;
import oracle.sql.BLOB;
import server.essp.cbs.config.logic.LgCbs;
import server.essp.framework.logic.AbstractESSPLogic;
import server.framework.common.BusinessException;
import server.essp.pms.account.baseline.logic.BaseLineApprovedListener;
import db.essp.pms.AccountBaseline;
import java.sql.ResultSet;

public class LgBuget extends AbstractESSPLogic implements BaseLineApprovedListener {
    /**
     * 列出Buget的Log
     * @param bugetRid Long
     * @return List
     */
    public List listBudgetLog(Long budgetRid){
        if(budgetRid == null)
            throw new BusinessException("CBS_BGT_0001","The Buget Rid can not be null!");
        List result = new ArrayList();
        try {
            Session s = this.getDbAccessor().getSession();
            List l = s.createQuery("from CbsBudgetLog log " +
                                   "where log.budgetRid=:budgetRid " +
                                   "order by log.logDate"
                     )
                     .setParameter("budgetRid",budgetRid)
                     .list();
            for(int i = 0;i < l.size();i ++){
                CbsBudgetLog log = (CbsBudgetLog) l.get(i);
                DtoBudgetLog dto = new DtoBudgetLog();
                DtoUtil.copyBeanToBean(dto,log);
                result.add(dto);
            }
        } catch (Exception ex) {
            throw new BusinessException("CBS_BGT_0002","error while listing buget log!",ex);
        }
        return result;
    }

    /**
     * 查找Rid对应的Budget，若不存在返回Null
     * @param budgetRid Long
     * @return DtoCbsBudget
     */
    public DtoCbsBudget getBudget(Long budgetRid){
        if(budgetRid == null)
            throw new BusinessException("CBS_BGT_0001","The Buget Rid can not be null!");
        try {
            Session s = this.getDbAccessor().getSession();
            CbsBudget budget = (CbsBudget)s.get(CbsBudget.class,budgetRid);
            if(budget == null)
                return null;
            DtoCbsBudget budgetDto = new DtoCbsBudget();
            DtoUtil.copyBeanToBean(budgetDto,budget);
            BLOB blob = (BLOB) budget.getBudget();
            Object cbsRoot = LgCbs.blobToObj(blob);
            budgetDto.setCbsRoot((DtoTreeNode)cbsRoot);
            return budgetDto;
        } catch (Exception ex) {
            throw new BusinessException("CBS_BGT_0013","load buget error!",ex);
        }
    }
    /**
     * 新增Budget
     * 1.新增Budget记录
     * 2.新增或更新PmsAcntCost对应的BudgetType
     * @param budgetDto DtoCbsBudget
     */
    public Long addBudget(DtoCbsBudget budgetDto){
        if(budgetDto == null || budgetDto.getBudgetType() == null
           || budgetDto.getCbsRoot() ==null || budgetDto.getAcntRid() == null){
            throw new BusinessException("CBS_BGT_0014","can not add illegal budget!");
        }
        try {
            Session s = this.getDbAccessor().getSession();
            CbsBudget budget = new CbsBudget();
            DtoUtil.copyBeanToBean(budget,budgetDto);
            this.getDbAccessor().assignedRid(budget);
            budgetDto.setRid(budget.getRid());
            byte[] buffer = new byte[1024];
            budget.setBudget(Hibernate.createBlob(buffer));
            s.save(budget);
            s.flush();
            s.refresh(budget, LockMode.UPGRADE);

            BLOB blob = (BLOB) budget.getBudget();
            LgCbs.ObjToBlob(budgetDto.getCbsRoot(),blob);

            updateAcntCost(budgetDto);
            s.flush();
            return budget.getRid();
        } catch (Exception ex) {
            throw new BusinessException("CBS_CFG_0015","error while adding  budget!",ex);
        }
    }
    /**
     * 更新Account对应的不同Budget的值
     * @param budgetDto DtoCbsBudget
     * @throws BusinessException
     */
    public void updateAcntCost(DtoCbsBudget budgetDto) throws BusinessException {
        try {
            PmsAcntCost acntCost = getAcntCost(budgetDto.getAcntRid());
            if(acntCost == null){
                acntCost = new PmsAcntCost();
            }
            if(budgetDto.getBudgetType().equals(CbsConstant.ANTICIPATED_BUDGET)){
                acntCost.setAntiRid(budgetDto.getRid());
                acntCost.setAntiAmt(budgetDto.getCurrentAmt());
                acntCost.setAntiPm(budgetDto.getCurrentPm());
            }else if(budgetDto.getBudgetType().equals(CbsConstant.PROPOSED_BUDGET)){
                acntCost.setPropRid(budgetDto.getRid());
                acntCost.setPropAmt(budgetDto.getCurrentAmt());
                acntCost.setPropPm(budgetDto.getCurrentPm());
            }else if(budgetDto.getBudgetType().equals(CbsConstant.BASELINE_BUDGET)){
            }else{
                throw new BusinessException("CBS_CFG_0016","illegal budget type:["+budgetDto.getBudgetType()+"]");
            }
            if(acntCost.getRid() == null){
                acntCost.setRid(budgetDto.getAcntRid());
                this.getDbAccessor().save(acntCost);
            }else{
                this.getDbAccessor().update(acntCost);
            }
        } catch (Exception ex) {
            throw new BusinessException("CBS_CFG_0017","error while updating  budget!",ex);
        }
    }

    public PmsAcntCost getAcntCost(Long acntRid) {
        Session s = null;
        try {
            s = this.getDbAccessor().getSession();
            PmsAcntCost acntCost  = (PmsAcntCost) s.get(PmsAcntCost.class, acntRid);
            return acntCost;
        } catch (Exception ex) {
            throw new BusinessException("CBS_CFG_0020","error while get account cost!",ex);
        }
    }
    /**
     * 更新Budget
     * 1.更新Budget记录
     * 2.新增或更新PmsAcntCost对应的BudgetType
     * @param budgetDto DtoCbsBudget
     */
    public void updateBudget(DtoCbsBudget budgetDto){
        if(budgetDto == null || budgetDto.getBudgetType() == null || budgetDto.getCbsRoot() ==null){
            throw new BusinessException("CBS_BGT_0014","can not update illegal budget!");
        }
        try{
            Session s = this.getDbAccessor().getSession();
            CbsBudget budget = (CbsBudget) s.load(CbsBudget.class,
                                                  budgetDto.getRid(),
                                                  LockMode.UPGRADE);
            DtoUtil.copyBeanToBean(budget,budgetDto);
            BLOB blob = (BLOB) budget.getBudget();
            DtoTreeNode root = budgetDto.getCbsRoot();
            DtoCbsBudgetItem rootData = (DtoCbsBudgetItem) root.getDataBean();
            budget.setCurrentAmt(rootData.getAmt());
            LgCbs.ObjToBlob(root,blob);

            budgetDto.setCurrentAmt(budget.getCurrentAmt());
            budgetDto.setCurrentPm(budget.getCurrentPm());
            updateAcntCost(budgetDto);
            s.flush();
        }catch(Exception ex){
            throw new BusinessException("CBS_CFG_0019","error while updating  account cost!",ex);
        }
    }
    /**
     * 初始化期望预算
     * 1.读取CBS结构，将节点转换成 DtoCbsBudgetItem,保存在Budget表中
     * 2.更新或保存PmsAcntCost，记录期望预算的BudgetRid和预算值
     * @return Long
     */
    public DtoCbsBudget initAntiBudget(Long acntRid){
        if(acntRid == null){
            throw new BusinessException("CBS_BGT_0014","can not add illegal budget!");
        }
        PmsAcntCost acntCost = this.getAcntCost(acntRid);
        if(acntCost != null && acntCost.getAntiRid() != null && getBudget(acntCost.getAntiRid()) != null){
            throw new BusinessException("CBS_BGT_0021","The anticipated budget of account["+acntRid+"] has been inited!");
        }
        LgCbs lgCbs = new LgCbs();
        DtoCbs cbs = lgCbs.loadCbsDefine(DtoCbs.DEFAULT_TYPE);
        if(cbs == null)
            throw new BusinessException("CBS_BGT_0025","Before init anticpated budget ,you must create CBS subject tree!");
        //设置Budget初始值
        DtoCbsBudget budgetDto = new DtoCbsBudget();
        budgetDto.setAcntRid(acntRid);
        budgetDto.setBudgetType(CbsConstant.ANTICIPATED_BUDGET);
        IAccountUtil acntUtil = AccountFactory.create();
        IDtoAccount account = acntUtil.getAccountByRID(acntRid);
        budgetDto.setCurrency(account.getCurrency());//预算货币用项目的定义货币
        budgetDto.setCurrentAmt(new Double(0));
        budgetDto.setCurrentPm(new Double(0));
        budgetDto.setLastAmt(new Double(0));
        budgetDto.setLastPm(new Double(0));
        Date planStart = account.getPlannedStart();
        Date planFinish = account.getPlannedFinish();
        if(planStart == null && planFinish == null){
            planStart = new Date();
            planFinish = new Date(planStart.getYear() + 1,planStart.getMonth(),planStart.getDay());
        }else if(planStart != null && planFinish == null){
            planFinish = new Date(planStart.getYear() + 1,planStart.getMonth(),planStart.getDay());
        }else if(planStart == null && planFinish != null){
            planStart = new Date(planFinish.getYear() - 1,planFinish.getMonth(),planFinish.getDay());
        }
        budgetDto.setFromMonth(planStart);
        budgetDto.setToMonth(planFinish);
        //读取CBS结构，转换节点
        DtoTreeNode root = cbs.getCbsRoot();
        DtoSubject rootSubject = (DtoSubject) root.getDataBean();
        root.setDataBean(new DtoCbsBudgetItem(rootSubject));
        List allChild = root.listAllChildrenByPreOrder();
        for(int i = 0 ;i < allChild.size() ;i ++){
            ITreeNode node = (ITreeNode) allChild.get(i);
            DtoSubject subject = (DtoSubject) node.getDataBean();
            node.setDataBean(new DtoCbsBudgetItem(subject));
        }
        budgetDto.setCbsRoot(root);
        this.addBudget(budgetDto);
        return budgetDto;
    }
    /**
     * 初始建议预算
     * 1.获得该项目对应的期望预算，若期望预算不存在，初始期望预算
     * 2.以期望预算的值生成建议预算,包括建议预算对应的Labor Budget和（NonLabor Budget）
     * 3.更新或保存PmsAcntCost，记录建议预算的BudgetRid和预算值
     * @param acntRid Long
     * @return DtoCbsBudget
     */
    public DtoCbsBudget initPropBudget(Long acntRid){
        if(acntRid == null){
            throw new BusinessException("CBS_BGT_0014","can not add illegal budget!");
        }
        PmsAcntCost acntCost = this.getAcntCost(acntRid);
        //判断项目对应的建议预算是否已存在
        if(acntCost != null && acntCost.getPropRid() != null && getBudget(acntCost.getPropRid()) != null){
            throw new BusinessException("CBS_BGT_0022","The proposed budget of account["+acntRid+"] has been inited!");
        }
        try {
            DtoCbsBudget antiBudget = null;
            if(acntCost == null || acntCost.getAntiRid() == null){
                antiBudget = initAntiBudget(acntRid);
            }else{
                Long acntiBudgetRid = acntCost.getAntiRid();
                antiBudget = getBudget(acntiBudgetRid);
            }
            DtoCbsBudget propBudget = new DtoCbsBudget();
            DtoUtil.copyBeanToBean(propBudget,antiBudget);
            propBudget.setAcntRid(acntRid);
            propBudget.setBudgetType(CbsConstant.PROPOSED_BUDGET);
            Long propBudgetRid = this.addBudget(propBudget);


            //Copy 建议预算对应的人力成本的预算
            copyLaborBudget(antiBudget.getRid(), propBudgetRid);
            return propBudget;
        } catch (Exception ex) {
            throw new BusinessException("CBS_BGT_0018","can not init Prop budget!",ex);
        }
    }
    /**
     * BaseLine审核时,将当前的建议预算做为BaseLine预算保存
     * Copy当前的建议预算,保存为Base Line预算
     * @param acntRid Long
     */
    public void approveBaseLine(AccountBaseline accountBaseline){
        if(accountBaseline == null){
            throw new BusinessException("CBS_BGT_0019","can not add approve null base budget!");
        }
        Long acntRid = accountBaseline.getRid();
        String baseId = accountBaseline.getBaselineId();
        if(acntRid == null || baseId == null){
            throw new BusinessException("CBS_BGT_0019","can not add approve null base budget!");
        }
        PmsAcntCost acntCost = this.getAcntCost(acntRid);
        //判断项目对应的建议预算是否已存在
        if(acntCost != null && acntCost.getPropRid() == null ){
            throw new BusinessException("CBS_BGT_0020","The proposed budget of account["+acntRid+"] is null!");
        }
        DtoCbsBudget propBudget = getBudget(acntCost.getPropRid());
        if(propBudget == null)
            throw new BusinessException("CBS_BGT_0020","The proposed budget of account["+acntRid+"] is null!");
        DtoCbsBudget baseBudget = new DtoCbsBudget();
        DtoUtil.copyBeanToBean(baseBudget,propBudget);
        baseBudget.setBudgetType(CbsConstant.BASELINE_BUDGET);
        baseBudget.setAcntRid(acntRid);
        Long baseBudgetRid = this.addBudget(baseBudget);
        acntCost.setBaseRid(baseBudgetRid);
        acntCost.setBaseId(baseId);
        acntCost.setBasePm(baseBudget.getCurrentPm());
        acntCost.setBaseAmt(baseBudget.getCurrentAmt());
        try {
            copyLaborBudget(acntCost.getPropRid(), baseBudgetRid);
        } catch (Exception ex) {
            throw new BusinessException("CBS_BGT_0021","error copy proposed budget to base budget!",ex);
        }
    }
    private void copyLaborBudget(Long antiBudgetRid,
                                     Long propBudgetRid)  {
        LgLaborBgt lgLaborBgt = new LgLaborBgt();
        List antiLaborBgts = lgLaborBgt.listLaborBudget(antiBudgetRid);
        for(int i =0;i < antiLaborBgts.size();i ++){
            DtoResBudgetItem antiLaborBgt = (DtoResBudgetItem) antiLaborBgts.get(i);
            DtoResBudgetItem propLaborBgt = new DtoResBudgetItem();
            DtoUtil.copyBeanToBean(propLaborBgt,antiLaborBgt);
            propLaborBgt.setBudgetRid(propBudgetRid);
            propLaborBgt.setRid(null);
            lgLaborBgt.saveLaborBgt(propLaborBgt);
        }
    }

    public void addBudgetLog(DtoBudgetLog dto){
        if(dto == null || dto.getBudgetRid() == null)
            throw new BusinessException("CBS_BGT_0022","can not add null log!");
        CbsBudgetLog log = new CbsBudgetLog();
        DtoUtil.copyBeanToBean(log,dto);
        try {
            this.getDbAccessor().save(log);
            CbsBudget cbsBudget = (CbsBudget) this.getDbAccessor().load(CbsBudget.class,dto.getBudgetRid());
            cbsBudget.setLastAmt(cbsBudget.getCurrentAmt());
            cbsBudget.setLastPm(cbsBudget.getCurrentPm());
            this.getDbAccessor().update(cbsBudget);
        } catch (Exception ex) {
            throw new BusinessException("CBS_BGT_0023","can not add log!",ex);
        }
    }
    public static void main(String[] args) throws Exception {
        LgBuget lg = new LgBuget();
        lg.getDbAccessor().followTx();
        lg.initAntiBudget(new Long(1));
        lg.getDbAccessor().endTxCommit();
    }
}
