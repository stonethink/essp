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
 * <p>Title: WorkScopeServiceImp��</p>
 *
 * <p>Description:��P3��ESSP�л�÷������������� </p>
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
     * �õ�ESSP��P3�е���Ŀ���ϵĲ�,����õ���P3�е���Ŀ��TS_ACCOUNT�д���ȡ����,��������
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
     * �õ���ÿ��Object�����а���TsAccount��TsLaborResource,ȡ��TsAccount����ָ��LIST��
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
     * ��ȡ��ǰ�û����ڵ�������Ŀ����������Activity����Ŀ��
     * 1.���ݵ�ǰ�û����ACTIVITY
     * 2.����ÿ��ACTIVITY�е�AccountId�ҵ���Ӧ��ACCOUNT��¼
     * 3.��ACTIVITY��ŵ���Ӧ��DTOACCOUNT��
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
     * ��ESSP�еõ�ACCOUNTLIST
     * 1.����loginId��LaborResource�еõ���Ӧ��AccountRid����
     * 2. ����AccountRid�õ���Ӧ��ACCOUNT��¼
     * @param loginId Long
     * @return List
     */
    private List getAccountListFromEssp(String loginId) {
        List queryList = accountDao.listAccountByLabResLoginId(loginId);
        List listAccount = getAccountList(queryList);
        List accountList = new ArrayList();
        for (int i = 0; i < listAccount.size(); i++) {
            TsAccount tsAcnt = (TsAccount) listAccount.get(i);
            //����AccountRid�õ���Ӧ��ACCOUNT��¼
            if (tsAcnt == null || tsAcnt.getAccountStatus().equals("Y")) {
                continue;
            }
            DtoAccount dtoAcnt = copyTsAcntToDtoAcnt(tsAcnt);
            accountList.add(dtoAcnt);
        }
        
        return accountList;
    }

    /**
     * ��TsAccount�е����Ը��Ƶ�DtoAccount��
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
     * �г��ٱ�ļ�¼
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
     * �õ�����������CODEVALUE��¼
     * 1.������Ŀ���ŵ�TSACCOUNT�еõ���Ӧ��CodeTypeRid,����CodeTypeRid��CodeRelation�в�ѯ���������ļ�¼
     * 2.��������ҵõ���CODEVALUE�����Ҷ�������codeTypeRid��CODEVALUE�еõ���Ӧ�ļ�¼��
     * 3.�����������ٵ�CODETYPE�в�ѯ
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
     * ����codeTypeRid��CodeRelation���еõ�CodeValueRid�ļ���
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
     * ��Map�е�DtoAccount��Code�������У�����List
     * @return List<DtoAccount>
     */
    private List Map2SortedList(Map<Integer, DtoAccount> AccountMap) {
        List lst = new ArrayList(AccountMap.values());
        Collections.sort(lst, new AccountCodeComparator());
        return lst;
    }
    
    /**
     * ����ActivityId��ȡActivityShowName,���û�ҵ��򷵻ؿ��ַ���
     * @param activityId Long
     * @return String
     */
    public String getActivityShowName(Long activityId) {
    	return workScopeP3ApiDao.getActivityShowName(activityId);
    }
    
    /**
     * ����acntRid��ȡAccountShowName,���û�ҵ��򷵻ؿ��ַ���
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
     * Spring Dao ע��
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
