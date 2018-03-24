package server.essp.timesheet.workscope.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import server.essp.timesheet.account.dao.IAccountDao;
import server.essp.timesheet.code.coderelation.dao.ICodeRelationDao;
import server.essp.timesheet.code.coderelation.service.ICodeRelationService;
import server.essp.timesheet.preference.dao.IPreferenceDao;
import server.essp.timesheet.workscope.dao.IWorkScopeP3ApiDao;
import server.framework.common.BusinessException;
import c2s.dto.DtoUtil;
import c2s.dto.ITreeNode;
import c2s.essp.common.calendar.WorkCalendar;
import c2s.essp.timesheet.code.DtoCodeType;
import c2s.essp.timesheet.code.DtoCodeValue;
import c2s.essp.timesheet.workscope.DtoAccount;
import c2s.essp.timesheet.workscope.DtoActivity;
import db.essp.timesheet.TsAccount;
import db.essp.timesheet.TsCodeRelation;
import db.essp.timesheet.TsParameter;

/**
 * <p>Title: WorkScopeServiceImp类</p>
 *
 * <p>Description:从P3和ESSP中获得符合条件的数据 </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author tbh
 * @version 1.0
 */
public class WorkScopeServiceImp implements IWorkScopeService {
    private IWorkScopeP3ApiDao workScopeP3ApiDao;
    private ICodeRelationDao codeRelationDao;
    private IAccountDao accountDao;
    private IPreferenceDao preferenceDao;
    private ICodeRelationService codeRelationService;

    /**
     * 得到ESSP和P3中的项目集合的差,如果得到的P3中的项目在TS_ACCOUNT中存在取出来,否则跳过
     * @param loginId Long
     * @return List
     */
    public List getAccountList(String loginId) {
        List listAccount = getAccountListFromEssp(loginId);
        List listP3 = this.getAccountListFromP3();
        if (listP3 != null && listAccount != null) {
            for (int i = 0; i < listP3.size(); i++) {
                DtoAccount dtoAccount = (DtoAccount) listP3.get(i);
                for (int j = 0; j < listAccount.size(); j++) {
                    DtoAccount dtoAcnt = (DtoAccount) listAccount.get(j);
                    if (dtoAccount.getCode().equals(dtoAcnt.getP6Id())) {
                        dtoAcnt.setIsP3(true);
                        dtoAcnt.setActivities(dtoAccount.getActivities());
                        break;
                    }
                }
            }
        }
        return listAccount;
    }

    /**
     * 得到的每个Object对象中包含TsAccount和TsLaborResource,取出TsAccount放入指定LIST中
     * @param qryList
     * @return List
     */
    private List getAccountList(List qryList){
        List accountList = new ArrayList();
        if(qryList != null){
            for(int i=0;i<qryList.size();i++){
                Object[] obj = (Object[])qryList.get(i);
                accountList.add(obj[0]);
            }
        }
        return accountList;
    }
    
    /**
     * 获取当前用户所在的所有项目（被分配了Activity的项目）
     * 1.根据当前用户获得ACTIVITY
     * 2.根据每条ACTIVITY中的AccountId找到对应的ACCOUNT记录
     * 3.将ACTIVITY存放到对应的DTOACCOUNT中
     * @return List
     */
    private List getAccountListFromP3() {
        Map<Integer, DtoAccount> AccountMap = new HashMap();
        try {
            List activityList = workScopeP3ApiDao.getActivityList();
            if (activityList != null) {
                Iterator<DtoActivity> activityIter = activityList.iterator();
                while (activityIter.hasNext()) {
                    DtoActivity dtoActivity = activityIter.next();
                    Integer AccountId = dtoActivity.getProjectId();
                    DtoAccount dtoAccount = AccountMap.get(AccountId);
                    if (dtoAccount == null) {
                        dtoAccount = workScopeP3ApiDao.getAccount(AccountId);
                        if(dtoAccount == null) {
                        	continue;
                        }
                        AccountMap.put(AccountId, dtoAccount);
                    }
                    dtoAccount.getActivities().add(dtoActivity);
                }
            }
        } catch (Exception ex) {
            throw new BusinessException("error.logic.WorkScopeServiceImp.loadAccountErr",
                                        "load AccountsFromP3 error", ex);
        }
        return Map2SortedList(AccountMap);
    }

    /**
     * 从ESSP中得到ACCOUNTLIST
     * 1.根据loginId从LaborResource中得到对应的AccountRid集合
     * 2. 根据AccountRid得到对应的ACCOUNT记录
     * @param loginId Long
     * @return List
     */
    private List getAccountListFromEssp(String loginId) {
        List queryList = accountDao.listAccountByLabResLoginId(loginId);
        List listAccount = getAccountList(queryList);
        List accountList = new ArrayList();
        for (int i = 0; i < listAccount.size(); i++) {
            TsAccount tsAcnt = (TsAccount) listAccount.get(i);
            //根据AccountRid得到对应的ACCOUNT记录
            if (tsAcnt == null || tsAcnt.getAccountStatus().equals("Y")) {
                continue;
            }
            DtoAccount dtoAcnt = copyTsAcntToDtoAcnt(tsAcnt);
            accountList.add(dtoAcnt);
        }
        
        return accountList;
    }

    /**
     * 将TsAccount中的属性复制到DtoAccount中
     * @param tsAcnt TsAccount
     * @return DtoAccount
     */
    private DtoAccount copyTsAcntToDtoAcnt(TsAccount tsAcnt) {
    	if(tsAcnt == null) {
    		return null;
    	}
        DtoAccount dtoAcnt = new DtoAccount();
        DtoUtil.copyBeanToBean(dtoAcnt, tsAcnt);
        dtoAcnt.setId(tsAcnt.getRid().intValue());
        dtoAcnt.setCode(tsAcnt.getAccountId());
        dtoAcnt.setName(tsAcnt.getAccountName());
        dtoAcnt.setIsP3(false);
        dtoAcnt.setCodeTypeRid(tsAcnt.getCodeTypeRid());
        if (tsAcnt.getDeptFlag() != null && tsAcnt.getDeptFlag().equals("1")) {
            dtoAcnt.setIsDept(true);
        } else {
            dtoAcnt.setIsDept(false);
        }
        return dtoAcnt;
    }

    /**
     * 列出假别的记录
     * @param leaveCodeTypeRid Long
     * @return List
     */
    public List getCodeLeave(Long leaveCodeTypeRid) {
    	List<DtoCodeValue> codeValueList = new ArrayList();
        ITreeNode root = codeRelationService.getRelationTree(leaveCodeTypeRid,true);
        List<ITreeNode> valueList = root.listAllChildrenByPreOrder();
        valueList.add(root);
        for (ITreeNode node : valueList) {
            DtoCodeValue value = (DtoCodeValue) node.getDataBean();
            if (node.isLeaf() && value.getIsEnable()) {
                codeValueList.add(value);
            }
        }
        return codeValueList;
    }

    /**
     * 得到符合条件的CODEVALUE记录
     * 1.根据项目代号到TSACCOUNT中得到相应的CodeTypeRid,根据CodeTypeRid到CodeRelation中查询符合条件的记录
     * 2.如果存在且得到的CODEVALUE结点是叶子则根据codeTypeRid到CODEVALUE中得到相应的记录，
     * 3.否则跳过不再到CODETYPE中查询
     * @param accountId String
     * @return List
     */
    public List getJobCode(Long codeTypeRid) {
        List<DtoCodeValue> codeValueList = new ArrayList();
        ITreeNode root = codeRelationService.getRelationTree(codeTypeRid,false);
        List<ITreeNode> valueList = root.listAllChildrenByPreOrder();
        valueList.add(root);
        for (ITreeNode node : valueList) {
            DtoCodeValue value = (DtoCodeValue) node.getDataBean();
            if (node.isLeaf() && value.getIsEnable()) {
                codeValueList.add(value);
            }
        }
        return codeValueList;
    }

    /**
     * 根据codeTypeRid从CodeRelation表中得到CodeValueRid的集合
     * @param codeTypeRid long
     * @return List
     */
    private List getCodeValueRid(Long codeTypeRid) {
        List list = codeRelationDao.listRelation(codeTypeRid,DtoCodeType.DTO_NON_LEAVE_TYPE);
        List relRidList = new ArrayList();
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                TsCodeRelation tsRel = (TsCodeRelation) list.get(i);
                Long valueRid = tsRel.getValueRid();
                relRidList.add(valueRid);
            }
            return relRidList;
        } else {
            return null;
        }
    }

    /**
     * 将Map中的DtoAccount按Code降序排列，返回List
     * @return List<DtoAccount>
     */
    private List Map2SortedList(Map<Integer, DtoAccount> AccountMap) {
        List lst = new ArrayList(AccountMap.values());
        Collections.sort(lst, new AccountCodeComparator());
        return lst;
    }
    
    /**
     * 根据ActivityId获取ActivityShowName,如果没找到则返回空字符串
     * @param activityId Long
     * @return String
     */
    public String getActivityShowName(Long activityId) {
    	return workScopeP3ApiDao.getActivityShowName(activityId);
    }
    
    /**
     * 根据acntRid获取AccountShowName,如果没找到则返回空字符串
     * @param acntRid Long
     * @return String
     */
    public String getAccountShowName(Long acntRid) {
    	TsAccount tsAcnt = accountDao.loadAccount(acntRid);
    	if(tsAcnt == null) {
    		return "";
    	}
    	DtoAccount dtoAcnt = copyTsAcntToDtoAcnt(tsAcnt);
        return dtoAcnt.getShowName();
    }

    /**
     * Spring Dao 注入
     * @param iworkScopeDao IWorkScopeDao
     */
    public void setWorkScopeP3ApiDao(IWorkScopeP3ApiDao workScopeP3ApiDao) {
        this.workScopeP3ApiDao = workScopeP3ApiDao;
    }

    public void setCodeRelationDao(ICodeRelationDao codeRelationDao) {
        this.codeRelationDao = codeRelationDao;
    }

    public void setAccountDao(IAccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public void setPreferenceDao(IPreferenceDao preferenceDao) {
        this.preferenceDao = preferenceDao;
    }

    public void setCodeRelationService(ICodeRelationService codeRelationService) {
        this.codeRelationService = codeRelationService;
    }

    private class AccountCodeComparator implements Comparator<DtoAccount> {
        public int compare(DtoAccount p1, DtoAccount p2) {
            return p2.getCode().compareToIgnoreCase(p1.getCode());
        }

        public boolean equals(Object obj) {
            return false;
        }
    }
}
