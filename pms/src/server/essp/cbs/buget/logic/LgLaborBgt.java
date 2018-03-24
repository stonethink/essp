package server.essp.cbs.buget.logic;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.RowSet;

import c2s.dto.DtoTreeNode;
import c2s.dto.DtoUtil;
import c2s.dto.IDto;
import c2s.dto.ITreeNode;
import c2s.essp.cbs.CbsConstant;
import c2s.essp.cbs.DtoPrice;
import c2s.essp.cbs.DtoSubject;
import c2s.essp.cbs.budget.DtoCbsBudget;
import c2s.essp.cbs.budget.DtoCbsBudgetItem;
import c2s.essp.cbs.budget.DtoResBudgetItem;
import c2s.essp.cbs.budget.DtoResBudgtSum;
import c2s.essp.cbs.cost.DtoResCostSum;
import c2s.essp.common.account.IDtoAccount;
import c2s.essp.common.calendar.WorkCalendar;
import c2s.essp.common.calendar.WorkCalendarConstant;
import c2s.essp.common.calendar.WrokCalendarFactory;
import com.wits.util.comDate;
import db.essp.cbs.CbsBgtLabor;
import db.essp.cbs.CbsBgtLaborMon;
import db.essp.cbs.CbsBudget;
import db.essp.cbs.PmsAcntCost;
import db.essp.cbs.SysPrice;
import db.essp.pms.LaborResource;
import db.essp.pms.LaborResourceDetail;
import itf.account.AccountFactory;
import itf.hr.HrFactory;
import itf.hr.IHrUtil;
import net.sf.hibernate.LockMode;
import net.sf.hibernate.Session;
import oracle.sql.BLOB;
import server.essp.cbs.config.logic.LgCbs;
import server.essp.cbs.config.logic.LgPrice;
import server.essp.framework.logic.AbstractESSPLogic;
import server.essp.pms.account.labor.logic.LgAccountLaborRes;
import server.framework.common.BusinessException;
import java.math.BigDecimal;

public class LgLaborBgt extends AbstractESSPLogic {
    /**
     * ����ͬ��JobCodeͳ��ĳʱ�������Ŀ��ÿ��ʹ�õ�������
     * 1.������Ŀʹ�õ�LaborResource����Planning,����ͳ��ʱ��������·�
     * 2.����budgetRid�¶�Ӧ�ľɵ�ÿ��JobCode��Ԥ�㣬��ͳ��ʱ���������·ݵ�ֵ��0
     * 3.ͳ��ÿ����Ա��ӦJobCode��ÿ�µ���������
     * 3.1 ����ÿ����Ա��������JobCode��Ӧ��Ԥ�㣬�������ڣ��½�����
     * 3.2 ������Ա��ÿ��Planning���䣬����Planning������ͳ��������ÿ�µ������������ۼӵ���JobCodeԤ�㵱�µ�������
     * 4.����ͳ�ƽ������Budget�ж�Ӧ�Ŀ�Ŀ
     * @param acntRid Long
     * @return List
     */
    public List caculateLaborBudget(Long acntRid,Long budgetRid,Date begin,Date end){
        if(acntRid == null)
            throw new BusinessException("CBS_LABBGT_0001","The account Rid can not be null!");
        if(begin == null || end == null)
            throw new BusinessException("CBS_LABBGT_0001","The caculate period can not be null!");
        if(begin.getTime() > end.getTime())
            throw new BusinessException("CBS_LABBGT_0001","The begin date can not be larger than end!!");
        List result = new ArrayList();
        //�г���Ŀʹ�õ���������
        LgAccountLaborRes lgLabor = new LgAccountLaborRes();
        List laborRes = lgLabor.listLaborRes(acntRid);
        if(laborRes == null)
            return result;

        //��������ڰ�������
        Calendar calBegin = Calendar.getInstance();
        Calendar calEnd = Calendar.getInstance();
        calBegin.setTime(begin);
        calEnd.setTime(end);
        List months = WorkCalendar.getBEMonthList(calBegin,calEnd);

        //Ϊ���ڼ��㽫JobCode�����Ӧ��Budget����Map�У�Key����jobCodeId,Value����DtoResBudgetItem
        Map map = new HashMap();
        List oldLaborBugetList = listLaborBudget(budgetRid);//���ҵ�ǰ������Labor Budget
        for(int i = 0;i < oldLaborBugetList.size();i ++){
            DtoResBudgetItem budgetItem = (DtoResBudgetItem) oldLaborBugetList.get(i);
            budgetItem.setAcntRid(acntRid);
            map.put(budgetItem.getResId(),budgetItem);
            for(int j = 0;j < months.size() ;j ++){//��ָ���·ݵľɵ����������
                Calendar[] monthBE = (Calendar[])months.get(j);
                Calendar monthEnd = monthBE[1];
                String month = comDate.dateToString(monthEnd.getTime(),
                    DtoCbsBudget.MONTH_STYLE);
                budgetItem.setMonthUnitNum(month,new Double(0));
                updateLaborBgt(budgetItem);
            }
        }

        //������Ա������ÿ�˵��������ۼӵ�JobCode��Budge��
        for(int i = 0;i < laborRes.size();i ++){
            LaborResource resource = (LaborResource) laborRes.get(i);
            String jobCodeId = resource.getJobcodeId();
            if(jobCodeId == null || "".equals(jobCodeId)){ //��Աû��ְ��ʱ�Ĳ�����ͳ��
                log.warn("Resource:[" +resource.getLoginId()+ "] doesn't assgin jobCode!");
                continue;
            }
            //ȡ��JobCode��Map�ж�Ӧ��Budget����,���ö��󲻴������½�
            DtoResBudgetItem budgetItem =  getBudgetItem(acntRid, map, jobCodeId,budgetRid);
            Set resourcePlan = resource.getLaborResourceDetails();
            if(resourcePlan == null || resourcePlan.size() == 0)
                continue;
            setResourcePlanMap(budgetItem,resourcePlan,months);
            updateLaborBgt(budgetItem);//��LaborBudget���ݸ��µ����ݿ�
        }
        result.addAll(map.values());

        //��Labor Budgetֵ���µ���Ӧ��Budget��
        updateLaborBgt2Bgt(result);
        return merge2PriceList(result,acntRid,budgetRid);

    }
    /**
     * ��Labor Budget�ı��ֵ���µ�Budget��������
     * 1.����Labor Budget������ÿ��Labor Budget��Ӧ��Budget�µĿ�Ŀ
     * 2.��ÿ��Labor Budgetÿ�½���������ۼӵ�Budget��Ŀ�¶�Ӧ���·�
     * 3.�ж��丸��Ŀ�ļ������ͣ������Ҫ�����ӿ�Ŀ�ϼƵģ�������䵱�µ�ֵ
     * 4.����PmsAcountCost��Ӧ��Budget��ֵ
     */
    private void updateLaborBgt2Bgt(List laborBudgets){
        if(laborBudgets == null || laborBudgets.size() <= 0){
            return;
        }

        DtoResBudgetItem any = (DtoResBudgetItem) laborBudgets.get(0);
        Long acntRid = any.getAcntRid();
        Long budgetRid = any.getBudgetRid();
        if(acntRid == null || budgetRid == null)
            throw new BusinessException("","The acntRid and bugetRid can not be null!");
        try {
            Session s = this.getDbAccessor().getSession();
            CbsBudget budget = (CbsBudget)s.load(CbsBudget.class,
                                                 budgetRid,LockMode.UPGRADE);
            BLOB blob = (BLOB) budget.getBudget();

            DtoTreeNode cbsRoot = (DtoTreeNode) LgCbs.blobToObj(blob);
            DtoCbsBudgetItem rootData = (DtoCbsBudgetItem) cbsRoot.getDataBean();
            List allSubjects = cbsRoot.listAllChildrenByPreOrder();
            //ȡ�����е�Subject��������Map�У�Key��subjectCode,Value:ITreeNode
            Map subjectMaps = new HashMap(allSubjects.size());
            for(int i = 0;i < allSubjects.size();i ++){
                ITreeNode node = (ITreeNode) allSubjects.get(i);
                DtoCbsBudgetItem subject = (DtoCbsBudgetItem) node.getDataBean();
                subjectMaps.put(subject.getSubjectCode(),node);
            }
            subjectMaps.put(rootData.getSubjectCode(),cbsRoot);
            List records = new ArrayList();//������¼��һ���Ѿ��������Budget��ĿList�е�ֵΪ��subjectCode+"--"+month
            double pm = 0;//��¼��������
            for(int i = 0;i < laborBudgets.size();i ++){
                DtoResBudgetItem resBudget = (DtoResBudgetItem) laborBudgets.get(i);
                pm = pm + resBudget.getUnitNum().doubleValue();
                Map unitNums = resBudget.getHmMonthUnitNum();
                if(unitNums == null || unitNums.size() <=0)
                    continue;
                LgPrice lgPrice = new LgPrice();
                //����Labor Budget��Ӧ��Subject
                DtoPrice price = lgPrice.getPrice(acntRid,resBudget.getResId(),CbsConstant.LABOR_RESOURCE);
                if(price == null){
                    log.warn("JobCode:[" +resBudget.getResId()+ "] doesn't assgin price!");
                    continue;
                }
                String subjectCode = price.getSubjectCode();
                if(subjectCode == null || "".equals(subjectCode)){//Labor Budgetλ��Ӧͳ�ƿ�Ŀ��ô����������
                    throw new BusinessException("CBS_LABBGT_0012","The price Subject code can not be null!");
                }else{
                    ITreeNode node = (ITreeNode) subjectMaps.get(subjectCode);
                    if(node != null && node.getDataBean() != null){
                        DtoCbsBudgetItem subject = (DtoCbsBudgetItem) node.getDataBean();
                        //����Subject��Ӧ��ÿ��ֵ
                        for(Iterator it = unitNums.keySet().iterator();it.hasNext();){
                            String month = (String) it.next();
                            Double unitNum = (Double) unitNums.get(month);
                            //monthAmt���ǵ�ǰ��Ŀ���µ�ֵ�����ǵ�ǰJobCode�ĵ���ֵ
                            double monthAmt = unitNum.doubleValue() * resBudget.getPrice().doubleValue();
                            String record = subjectCode + "--" + month;
                            if(records.indexOf(record) == -1){ //
                                subject.setMonthAmt(month,new Double(monthAmt));
                                records.add(record);
                            }else{
                                subject.addMonthAmt(month,new Double(monthAmt));//�ۼ�Budget��Ŀ���µ�ֵ
                            }
                            updateParent(node,month);
                        }
                    }
                }
            }
            records.clear();
            //mod by XR,Ԥ��ֻͳ��������Ԥ��
            Object[] obj = getLaborBudgetSum(budgetRid);
            budget.setCurrentPm((Double)(obj[0]));
            budget.setCurrentAmt((Double)(obj[1]));
//            DtoCbsBudgetItem rootSubject = (DtoCbsBudgetItem) cbsRoot.getDataBean();
//            budget.setCurrentAmt(rootSubject.getAmt());
//            budget.setCurrentPm(new Double(pm));
            LgCbs.ObjToBlob(cbsRoot,blob);
            s.flush();

            LgBuget lgBudget = new LgBuget();
            PmsAcntCost acntCost = lgBudget.getAcntCost(acntRid);
            if(budget.getBudgetType().equals(CbsConstant.ANTICIPATED_BUDGET)){
                acntCost.setAntiRid(budget.getRid());
                acntCost.setAntiAmt(budget.getCurrentAmt());
                acntCost.setAntiPm(budget.getCurrentPm());
            }else if(budget.getBudgetType().equals(CbsConstant.PROPOSED_BUDGET)){
                acntCost.setPropRid(budget.getRid());
                acntCost.setPropAmt(budget.getCurrentAmt());
                acntCost.setPropPm(budget.getCurrentPm());
            }else if(budget.getBudgetType().equals(CbsConstant.BASELINE_BUDGET)){

            }else{
                throw new BusinessException("CBS_CFG_0016","illegal budget type:["+budget.getBudgetType()+"]");
            }
            this.getDbAccessor().update(acntCost);
        }catch(BusinessException ex) {
            throw ex;
        }
         catch (Exception ex) {
            throw new BusinessException("CBS_LABBGT_0012","error while update budget",ex);
        }

    }

    private Object[] getLaborBudgetSum(Long budgetRid)
         {
        Object[] obj = null;
        try {
            obj = (Object[])this.getDbAccessor()
                  .getSession()
                  .createQuery(
                "select sum(t.unitNum),sum(t.amt) from CbsBgtLabor t where t.cbsBudget.rid='" +
                budgetRid + "'")
                  .uniqueResult();
        }  catch (Exception ex) {
            throw new BusinessException(ex);
        }
        return obj;
    }
    /**
     * �ݹ���¸��ڵ㵱�µ�����
     * 1.�жϸø��ڵ��Ƿ���Ҫ����,���ӽڵ�ϼƵĲ������
     * 2.�ۼӸ��ڵ������ӽڵ㵱��ֵ
     * @param node ITreeNode
     * @param month String
     * @param oldMonthAmt Double
     */
    private void updateParent(ITreeNode node,String month) {
        if(node == null || node.getParent() == null)
            return;
        ITreeNode parentNode = node.getParent();
        DtoCbsBudgetItem parentSubject = (DtoCbsBudgetItem) parentNode.getDataBean();
        if(parentSubject != null && parentSubject.getBudgetCalType().equals(DtoSubject.TYPE_AUTO_CALCULATE)){
            List children = parentNode.children();
            double amt = 0 ;
            for(int i = 0;i < children.size();i ++){
                ITreeNode child = (ITreeNode) children.get(i);
                DtoCbsBudgetItem childData = (DtoCbsBudgetItem) child.getDataBean();
                amt = amt + childData.getMonthAmt(month).doubleValue();
            }
            parentSubject.setMonthAmt(month,new Double(amt));
            updateParent(parentNode,month);
        }else{
            return ;
        }
    }
    /**
     * ������Ա���еļƻ������������������ۼӵ�Map�ж�Ӧ���·���
     * @param resourcePlan Set
     * @return Map
     */
    private void setResourcePlanMap(DtoResBudgetItem budgetItem,Set resourcePlan,List months){
        if(resourcePlan == null)
            return ;
        Iterator ite = resourcePlan.iterator();
        while(ite.hasNext()){
            LaborResourceDetail planDetail = (LaborResourceDetail) ite.next();
            Long percent = planDetail.getPercent();
            Date beginDate = planDetail.getBeginDate() ;
            Date endDate = planDetail.getEndDate();
            if(beginDate == null || endDate == null)
                throw new BusinessException("CBS_LABBGT_0002","The resource plan period begin date and end date can not be null!");
            Calendar calBegin = Calendar.getInstance();
            Calendar calEnd = Calendar.getInstance();
            calBegin.setTime(beginDate);
            calEnd.setTime(endDate);
            for(int i = 0;i < months.size() ;i ++){
                double pmAmt = 1;
                Calendar[] monthBE = (Calendar[])months.get(i);
                Calendar monthBegin = monthBE[0];
                Calendar monthEnd = monthBE[1];
                String month = comDate.dateToString(monthEnd.getTime(),
                    DtoCbsBudget.MONTH_STYLE);
                if(beginDate.getTime() <= monthBegin.getTimeInMillis()
                   && endDate.getTime() >= monthEnd.getTimeInMillis()){//�м��������
                    pmAmt = calculatePM(monthBegin,monthEnd,percent);
                    budgetItem.addMonthUnitNum(month,new Double(pmAmt));
                }else if(WorkCalendar.isBetween(calBegin,monthBegin,monthEnd)
                         && endDate.getTime() >= monthEnd.getTimeInMillis()){//��һ����
                    pmAmt = calculatePM(calBegin,monthEnd,percent);
                    budgetItem.addMonthUnitNum(month,new Double(pmAmt));
                } else if( beginDate.getTime() <= monthBegin.getTimeInMillis()
                         && WorkCalendar.isBetween(calEnd,monthBegin,monthEnd)){//���һ����
                    pmAmt = calculatePM(monthBegin,calEnd,percent);
                    budgetItem.addMonthUnitNum(month,new Double(pmAmt));
                }else if(WorkCalendar.isBetween(calBegin,monthBegin,monthEnd)
                         && WorkCalendar.isBetween(calEnd,monthBegin,monthEnd)){//���������м�
                    pmAmt = calculatePM(calBegin,calEnd,percent);
                    budgetItem.addMonthUnitNum(month,new Double(pmAmt));
                }
            }
        }
    }
    /*
     * �����ڼƻ�������ÿ�µ�������
     * ���㷽���������ڼƻ���ʱ  * ʹ���� ��/ ��ÿ�µĹ������� * ÿ�칤ʱ��
     */
    private double calculatePM(Calendar bg,Calendar ed,Long percent){
        if(percent == null)
            return 0;
        WorkCalendar workCal = WrokCalendarFactory.serverCreate();
        double fullWorkHours = workCal.getWorkHours(bg,ed);
        double workHours =  fullWorkHours * percent.doubleValue() / 100;
        double pm = workHours / (WorkCalendarConstant.MONTHLY_WORK_HOUR);
        return pm;
    }

    /**
     * ���JobCode��Ӧ��BudgetItem
     * @param acntRid Long
     * @param map Map
     * @param jobCodeId String
     * @throws BusinessException
     */
    private DtoResBudgetItem getBudgetItem(Long acntRid, Map map, String jobCodeId,Long budgetRid) throws
        BusinessException {
        DtoResBudgetItem budgetItem = (DtoResBudgetItem) map.get(jobCodeId);
        if(budgetItem == null){
            budgetItem = addResBgtItem(acntRid,budgetRid,jobCodeId);
            map.put(jobCodeId,budgetItem);
        }
        return budgetItem;
    }
    /**
     * ����JobCode��Ӧ��budget����
     * 1.����JobCode�Ƿ��Ѷ��ۣ���δ������۸�����δ0
     * 2.��JobCode�Ѷ��ۣ��жϼ۸�ѡ������Ƿ��Account����ͬ������λ�Ƿ�Ϊ����
     * @param acntRid Long
     * @param jobCodeId String
     * @return DtoResBudgetItem
     * @throws BusinessException
     */
    public DtoResBudgetItem addResBgtItem(Long acntRid,Long budgetRid, String jobCodeId) throws
        BusinessException {
        if(acntRid == null || budgetRid == null || jobCodeId == null)
            throw new BusinessException("CBS_LABBGT_0015","Can not add illegal Labor Budget");
        DtoResBudgetItem budgetItem = new DtoResBudgetItem();
        budgetItem.setAcntRid(acntRid);
        budgetItem.setBudgetRid(budgetRid);
        budgetItem.setUnit(CbsConstant.LABOR_UNIT_PM);
        LgPrice lgPrice = new LgPrice();
        DtoPrice priceDto = lgPrice.getPrice(acntRid,jobCodeId,CbsConstant.LABOR_RESOURCE);
        IDtoAccount account = AccountFactory.create().getAccountByRID(acntRid);
        if(priceDto == null){
            budgetItem.setPrice(new Double(0));
            budgetItem.setDescription("The JobCode has not been designate the price!");
            budgetItem.setResId(jobCodeId);
            IHrUtil hrUtil =  HrFactory.create();
            budgetItem.setResName(hrUtil.getJobCodeById(jobCodeId));
        }else{
            String acntCurrency = account.getCurrency();
            String priceUnit = priceDto.getUnit();
            Double price = priceDto.getPrice();
            if(price == null)
                price = new Double(0);
            //�Ƽ۵�λת��
            if(CbsConstant.LABOR_UNIT_PD.equals(priceUnit)){//PD��λתΪPM
                price = new Double(price.doubleValue() * WorkCalendarConstant.MONTHLY_WORK_DAY);
            }else if(CbsConstant.LABOR_UNIT_PH.equals(priceUnit)){//PH��λתΪPM
                price = new Double(price.doubleValue() * WorkCalendarConstant.MONTHLY_WORK_HOUR);
            }else if(!CbsConstant.LABOR_UNIT_PM.equals(priceUnit)){
                throw new BusinessException("CBS_LABBGT_0003","Illegal labor unit : ["+priceUnit+"]");
            }
//            if(!priceCurrency.equals(acntCurrency)){//���ʻ���
//                LgSysCurrency lgCurr = new LgSysCurrency();
//                Double exRate = lgCurr.getExchRate(priceCurrency,acntCurrency);
//                price = new Double( price.doubleValue() * exRate.doubleValue() );
//            }
            budgetItem.setPrice(price);
            budgetItem.setCurrency(acntCurrency);
            budgetItem.setResId(priceDto.getId());
            budgetItem.setResName(priceDto.getName());
        }
        Long rid = saveLaborBgt(budgetItem);
        budgetItem.setRid(rid);
        return budgetItem;
    }
    /**
     * �������ݿ���Budget��Ӧ��Labor Budget
     * @param budgetRid Long
     * @return List
     */
    public List listLaborBudget(Long budgetRid){
        if(budgetRid == null)
            throw new BusinessException("CBS_LABBGT_0004","The budgetRid can not be null!");
        List result = new ArrayList();
        try {
            Session s = this.getDbAccessor().getSession();
            CbsBudget budget = (CbsBudget) s.load(CbsBudget.class,budgetRid);
            Set laborBgts = budget.getCbsBgtLabors();
            if(laborBgts == null)
                return result;
            for(Iterator it = laborBgts.iterator(); it.hasNext();){
                CbsBgtLabor bgtLabor = (CbsBgtLabor) it.next();
                DtoResBudgetItem dto = new DtoResBudgetItem();
                DtoUtil.copyBeanToBean(dto,bgtLabor);
                dto.setBudgetRid(budgetRid);
                String jobCodeId = bgtLabor.getJobCodeId();
                dto.setResId(jobCodeId);
                String jobCode = HrFactory.create().getJobCodeById(jobCodeId);
                dto.setResName(jobCode);
                Set bgtLaborMonth = bgtLabor.getCbsBgtLaborMons();
                if(bgtLaborMonth == null)
                    continue;
                for(Iterator it2 = bgtLaborMonth.iterator(); it2.hasNext();){
                    CbsBgtLaborMon monBgt = (CbsBgtLaborMon) it2.next();
                    String month = comDate.dateToString(monBgt.getMonth(),DtoCbsBudget.MONTH_STYLE);
                    dto.setMonthUnitNum(month,monBgt.getBgtUnitNum());
                }
                result.add(dto);
            }
        } catch (Exception ex) {
            throw new BusinessException("CBS_LABBGT_0005","error while list labor budget!",ex);
        }
        return result;
    }
    /**
     * ������Ŀ��Ӧ������������Դ�Ķ���
     * 1.����Budget��Ӧ��Labor Budget
     * 2.�����е�������Դ�Ķ���ת��ΪDtoResBudgetItem����SysPrice��Id����Budgt���ж�Ӧ��ResId��
     * 3.�������ÿ������JobCode���������ĺͣ���������ڵ�һ��
     * ����Labor Budget�е�DtoResBudgetItem
     * @param budgetRid Long
     * @param acntRid Long
     * @return List
     */
    public List listLaborBudget(Long budgetRid,Long acntRid){
        List bugetList = listLaborBudget(budgetRid);
        return merge2PriceList(bugetList,acntRid,budgetRid);
    }
    //�ϲ�LaborBudget�б�Account Price�б�
    private List merge2PriceList(List bugetList,Long acntRid,Long budgetRid) {
        List result = new ArrayList();
        LgPrice lgPrice = new LgPrice();
        List acntPrices = lgPrice.listAcntPrice(acntRid);
        DtoResBudgtSum sum = new DtoResBudgtSum();//��һ�еĺϼ�����
        Object[] obj = getLaborBudgetSum(budgetRid);
        sum.setUnitNum((Double)(obj[0]));
        sum.setAmt((Double)(obj[1]));
        for(int i = 0;i < acntPrices.size();i ++){
            SysPrice price = (SysPrice) acntPrices.get(i);
            DtoResBudgetItem budgtItem = new DtoResBudgetItem();//SysPrice��Id��DtoResBudgetItem��resId��ʱJobCodeId
            DtoUtil.copyBeanToBean(budgtItem,price);
            budgtItem.setRid(null);//copy��Rid��SysPrice��Rid
            budgtItem.setResId(price.getId());
            budgtItem.setResName(price.getName());
            budgtItem.setBudgetRid(budgetRid);
            Double priceNum = budgtItem.getPrice();
            Double nowPrice = new Double(0);
            String priceUnit = budgtItem.getUnit();
            //�Ƽ۵�λת��
            if(CbsConstant.LABOR_UNIT_PD.equals(priceUnit)){//PD��λתΪPM
                nowPrice = new Double(priceNum.doubleValue() * WorkCalendarConstant.MONTHLY_WORK_DAY);
                budgtItem.setPrice(nowPrice);
                budgtItem.setUnit(CbsConstant.LABOR_UNIT_PM);
            }else if(CbsConstant.LABOR_UNIT_PH.equals(priceUnit)){//PH��λתΪPM
                nowPrice = new Double(priceNum.doubleValue() * WorkCalendarConstant.MONTHLY_WORK_HOUR);
                budgtItem.setPrice(nowPrice);
                budgtItem.setUnit(CbsConstant.LABOR_UNIT_PM);
            }else if(!CbsConstant.LABOR_UNIT_PM.equals(priceUnit)){
                throw new BusinessException("CBS_LABBGT_0003","Illegal labor unit : ["+priceUnit+"]");
            }

            Iterator it = bugetList.iterator();
            boolean isEqual = false;//��־λ���ж�bugetList�Ƿ��к�SysPrice��Id��ȵ�Ԫ��
            while(it.hasNext()){
                DtoResBudgetItem labordget = (DtoResBudgetItem) it.next();
                labordget.setResName(budgtItem.getResName());
                labordget.setUnit(budgtItem.getUnit());
                labordget.setPrice(budgtItem.getPrice());
                labordget.setAcntRid(acntRid);
                if(labordget.getResId().equals(price.getId())){
                    Map monUnitNums = labordget.getHmMonthUnitNum(); //����Labor Budgetÿ���ۼƵ�������
                    if(monUnitNums == null)
                        continue;
                    Iterator it2 = monUnitNums.keySet().iterator();
                    while(it2.hasNext()){
                        String month = (String) it2.next();
                        Double unitNum = (Double) monUnitNums.get(month);
                        sum.addMonthUnitNum(month,unitNum);
                    }
//                    amt = amt + labordget.getAmt().doubleValue();
                    result.add(labordget);
                    it.remove();
                    isEqual = true;
                    break;
                }
            }
            if(!isEqual)
                result.add(budgtItem);
        }
        result.add(0,sum);
        return result;
    }
    /**
     * ����Client��������Labor Budgt�������ݿ�
     * 1.List�е�һ���Ǻϼ�ֵ��������
     * 2.List��Dtoһ������Budget��Ӧ��Labor Budget���ݣ�һ�����Ǵ�JobCode����Price�л��
     * ǰһ�����޸Ĺ�����£���һ�����޸Ĺ��豣��
     * 3.����LaborBudget�ж�Ӧ��Budget��Ŀ
     * @param laborBgtList List
     */
    public void updateLaborBgtList(List laborBgtList){
        if(laborBgtList == null)
            throw new BusinessException("CBS_LABBGT_0006","The Labor budget list can not be null!");
        List updateedLaborBgt = new ArrayList();
        for(int i = 0;i < laborBgtList.size();i ++){
            DtoResBudgetItem item = (DtoResBudgetItem) laborBgtList.get(i);
            if(item.getResName().equals(DtoResCostSum.SUM)) //��һ�кϼƼ�¼
                continue;
            try{
                if(item.getOp().equals(IDto.OP_MODIFY) && item.getRid() == null){
                     //RID == null��DtoResBudgetItem�Ǵ�SysPrice Copy�ģ����޸Ĺ�������Ҫ���������ݿ�
                     saveLaborBgt(item);
                     item.setOp(IDto.OP_NOCHANGE);
                     updateedLaborBgt.add(item);
                }else if(item.getOp().equals(IDto.OP_MODIFY)){
                    updateLaborBgt(item);
                    item.setOp(IDto.OP_NOCHANGE);
                    updateedLaborBgt.add(item);
                }
                else if(item.getRid() != null){
                    updateedLaborBgt.add(item);
                }
            }catch(Exception ex){
                throw new BusinessException("CBS_LABBGT_0007","error while updating labor budgt list!",ex);
            }
        }
        updateLaborBgt2Bgt(updateedLaborBgt);
    }
    /**
     * ����Labor Budget��Ӧ������
     * 1.����LaborBudget�Լ�����
     * 2.����LaborBudget��Ӧ��ÿ������
     * @param item DtoResBudgetItem
     */
    private void updateLaborBgt(DtoResBudgetItem item){
        if(item == null || item.getRid() == null)
            throw new BusinessException("CBS_LABBGT_0009","can not update illegal data!");
        try{
            CbsBgtLabor laborBdg = (CbsBgtLabor) this.getDbAccessor().load(CbsBgtLabor.class,item.getRid());
            DtoUtil.copyBeanToBean(laborBdg, item);
            laborBdg.setJobCodeId(item.getResId());

            Map monBgtMap = item.getHmMonthUnitNum();
            Set set = laborBdg.getCbsBgtLaborMons();
            if(monBgtMap != null){
                for (Iterator it = monBgtMap.keySet().iterator(); it.hasNext(); ) {
                    String month = (String) it.next();
                    Double unitNum = (Double) monBgtMap.get(month);
                    double amt = 0;
                    if (unitNum != null && item.getPrice() != null)
                        amt = unitNum.doubleValue() *
                              item.getPrice().doubleValue();
                    boolean isEqual = false;//��־λ���жϸ��·ݵ�ֵ���ݿ����Ƿ��Ѵ���
                    for(Iterator it2 = set.iterator();it2.hasNext();){
                        CbsBgtLaborMon laborBdgMon = (CbsBgtLaborMon) it2.next();
                        //�����Ѵ��ڵ�Month
                        if(month.equals(comDate.dateToString(laborBdgMon.getMonth(),
                            DtoCbsBudget.MONTH_STYLE))){
                            laborBdgMon.setBgtUnitNum(unitNum);
                            laborBdgMon.setBgtAmt(new Double(amt));
                            isEqual = true;
                            break;
                        }
                    }
                    if(!isEqual){
                        //����Month
                        CbsBgtLaborMon laborBdgMon = new CbsBgtLaborMon();
                        laborBdgMon.setMonth(comDate.toDate(month,
                            DtoCbsBudget.MONTH_STYLE));
                        laborBdgMon.setBgtUnitNum(unitNum);
                        laborBdgMon.setCurrency(item.getCurrency());
                        laborBdgMon.setCbsBgtLabor(laborBdg);
                        laborBdgMon.setBgtAmt(new Double(amt));
                        this.getDbAccessor().save(laborBdgMon);
                        set.add(laborBdgMon);
                    }
                }
            }
            this.getDbAccessor().update(laborBdg);
        } catch (Exception ex) {
            throw new BusinessException("CBS_LABBGT_0008","error while updating labor budgt !",ex);
        }
    }


    //����������Labor Budget
    public Long saveLaborBgt(DtoResBudgetItem item) {
        if(item == null || item.getResId() == null || item.getBudgetRid() == null)
            throw new BusinessException("CBS_LABBGT_0010","can not save illegal data!");
        CbsBgtLabor laborBdg = new CbsBgtLabor();
        DtoUtil.copyBeanToBean(laborBdg, item);
        laborBdg.setJobCodeId(item.getResId());
        try {
            CbsBudget budget = (CbsBudget) this.getDbAccessor().load(CbsBudget.class, item.getBudgetRid());
            laborBdg.setCbsBudget(budget);
            this.getDbAccessor().save(laborBdg);
            Map monBgtMap = item.getHmMonthUnitNum();
            Set set = new HashSet();
            if(monBgtMap != null)
                for(Iterator it = monBgtMap.keySet().iterator();it.hasNext();){
                    String month = (String) it.next();
                    Double unitNum = (Double) monBgtMap.get(month);
                    CbsBgtLaborMon laborBdgMon = new CbsBgtLaborMon();
                    laborBdgMon.setMonth(comDate.toDate(month,DtoCbsBudget.MONTH_STYLE));
                    laborBdgMon.setBgtUnitNum(unitNum);
                    laborBdgMon.setCurrency(item.getCurrency());
                    laborBdgMon.setCbsBgtLabor(laborBdg);
                    double amt = 0;
                    if(unitNum != null && item.getPrice() != null)
                        amt = unitNum.doubleValue() * item.getPrice().doubleValue();
                    laborBdgMon.setBgtAmt(new Double(amt));
                    this.getDbAccessor().save(laborBdgMon);
                    set.add(laborBdgMon);
                }
            laborBdg.setCbsBgtLaborMons(set);
            this.getDbAccessor().update(laborBdg);
            return laborBdg.getRid();
        } catch (Exception ex) {
           throw new BusinessException("CBS_LABBGT_0008","error while saving labor budgt list!",ex);
        }
    }


    public static void main(String[] args) throws Exception{
//        String month="2005/12";
//        Date d = comDate.toDate(month,DtoCbsBudget.MONTH_STYLE);
//        System.out.println(comDate.dateToString(d,DtoCbsBudget.MONTH_STYLE));
//        LgLaborBgt lg = new LgLaborBgt();
//        lg.getDbAccessor().followTx();
//        List l =lg.caculateLaborBudget(new Long(1));
//        DtoResBudgetItem item = new DtoResBudgetItem();
//        item.setRid(new Long(47));
//        item.setAcntRid(new Long(111));
//        item.setAmt(new Double(20000));
//        item.setCurrency("USD");
//        item.setMonthUnitNum("2006/9",new Double(3));
//        item.setMonthUnitNum("2006/10",new Double(3));
//        item.setMonthUnitNum("2006/11",new Double(3));
//        item.setMonthUnitNum("2006/12",new Double(3));
//        item.setPrice(new Double(200));
//        item.setResId("JC2");
//        item.setResName("Job Code 2");
//        item.setUnit("PM");
//        item.setUnitNum(new Double(12));
//        lg.deleteLaborBgt(item);
//        lg.getDbAccessor().endTxCommit();
        double d1 = 0.95d;
        double d2 = 875d;
        System.out.println(d1 * d2);
    }




     /**
       * ��ȡȫ����Labor Resources
       * @return List {DtoLaborRes}
     * */
    public RowSet getLaborBgt(Long baseRid) throws BusinessException {
        try{
         RowSet rs =getDbAccessor().executeQuery(
                "select t1.month,sum(t1.bgt_unit_num) as unit_num_sum,sum(t1.bgt_amt) as amt_sum " +
                "from cbs_bgt_labor_mon t1 left join cbs_bgt_labor t2 " +
                "on t1.bgt_labor_rid=t2.rid where t2.bgt_rid='"
                + baseRid + "' group by t1.month order by t1.month asc");
          return rs;
         } catch (Exception ex) {
            log.error(ex);
            throw new BusinessException("ACNT_LABOR_001",
                                        "error while list all resource of account [" +
                                        baseRid +
                                        "]", ex);
        }

    }

}
