package server.essp.timesheet.syncaccount;

import itf.webservices.IAccountWService;
import itf.webservices.ProjectBase;

import java.util.Map;

import server.essp.timesheet.account.dao.IAccountDao;
import server.framework.common.BusinessException;
import c2s.dto.DtoUtil;

import com.wits.util.comDate;

import db.essp.timesheet.TsAccount;
import c2s.essp.timesheet.account.DtoAccount;

public class SyncAccountImp implements IAccountWService {
    private IAccountDao accountDao;
    private Double standarHours;
    /**
     * 新增专案
     * @param accountFields Map
     */
    public void addAccount(Map accountFields) {
        TsAccount account = new TsAccount();
        ProjectBase projectBase = new ProjectBase();
        try {
            DtoUtil.copyMapToBean(projectBase, accountFields);
            if(!accountFields.containsKey("deptFlag")){
                account.setAccountType((String)accountFields.get("AccountTypeName"));
                account.setPlannedStart(comDate.toDate(projectBase.getPlanFrom()));
                account.setPlannedFinish(comDate.toDate(projectBase.getPlanTo()));
                account.setActualStart(comDate.toDate(projectBase.getActuFrom()));
                account.setActualFinish(comDate.toDate(projectBase.getActuTo()));
                account.setOrgCode(projectBase.getProjectExecUnit());
                account.setDeptFlag("0");
                account.setAccountBrief(projectBase.getProjectDesc());
                account.setEstWorkHours(projectBase.getEstManmonth()*standarHours);
                account.setPrimaveraAdapted((String)accountFields.get("UsePrimavera"));
                account.setBillable((String)accountFields.get("Billable"));
            } else {
                String deptFlag = (String)accountFields.get("deptFlag");
                if("1".equals(deptFlag)){
                    account.setDeptFlag("1");
                }
                account.setAccountBrief(projectBase.getProjName());
                account.setParentId(projectBase.getMasterProjects());
            }
            account.setAchieveBelong((String)accountFields.get("AchieveBelong"));
            account.setAccountStatus(DtoAccount.STATUS_NORMAL);
            account.setAccountId(projectBase.getProjId());
            account.setAccountName(projectBase.getNickName());
            //专案前置结转操作修改，对应改变获取PM工号的方式
            account.setManager((String)accountFields.get("PM"));
            
            accountDao.addPorject(account);
        } catch (Exception ex) {
            throw new BusinessException("Synchronize Insert TimeSheet Error", ex);
        }
    }
    
    /**
     * 更新专案
     * @param accountFields Map
     */
    public void updateAccount(Map accountFields) {
        TsAccount account = new TsAccount();
        ProjectBase projectBase = new ProjectBase();
        try {
            DtoUtil.copyMapToBean(projectBase, accountFields);
            account = accountDao.loadByAccountId(projectBase.getProjId());
            if(!accountFields.containsKey("deptFlag")){
                account.setAccountType((String)accountFields.get("AccountTypeName"));
                account.setPlannedStart(comDate.toDate(projectBase.getPlanFrom()));
                account.setPlannedFinish(comDate.toDate(projectBase.getPlanTo()));
                account.setActualStart(comDate.toDate(projectBase.getActuFrom()));
                account.setActualFinish(comDate.toDate(projectBase.getActuTo()));
                account.setOrgCode(projectBase.getProjectExecUnit());
                account.setAccountBrief(projectBase.getProjectDesc());
                account.setEstWorkHours(projectBase.getEstManmonth()*standarHours);
                account.setPrimaveraAdapted((String)accountFields.get("UsePrimavera"));
                account.setBillable((String)accountFields.get("Billable"));
            } else {
                String deptFlag = (String)accountFields.get("deptFlag");
                if("1".equals(deptFlag)){
                    account.setAccountStatus((String)accountFields.get("status"));
                }
                account.setAccountBrief(projectBase.getProjName());
                account.setParentId(projectBase.getMasterProjects());
            }
            account.setAchieveBelong((String)accountFields.get("AchieveBelong"));
            //专案前置结转操作修改，对应改变获取PM工号的方式
            account.setManager((String)accountFields.get("PM"));
            account.setAccountName(projectBase.getNickName());
            accountDao.saveAccount(account);
        } catch (Exception ex) {
             throw new BusinessException("Synchronize Update TimeSheet Error", ex);
            
        }
    }
    /**
     * 专案结案
     * @param acntId String
     */
    public void closeAccount(String acntId, Map accountFields) {
    	try {
    		TsAccount account = accountDao.loadByAccountId(acntId);
    		account.setAccountStatus("Y");
    		accountDao.saveAccount(account);
    	} catch (Exception ex) {
             throw new BusinessException("Synchronize Close TimeSheet Error", ex);
         }
    }
    
    /**
     * 外部系统对本系统专案再开启或结案
     * @param acntId
     */
    public void reopenOrCloseAccount (String acntId, String status) {
    	try {
    		TsAccount Account = accountDao.loadByAccountId(acntId);
    		Account.setAccountStatus(status);
    		accountDao.saveAccount(Account);
    	} catch (Exception ex) {
             throw new BusinessException("Synchronize Reopen TimeSheet Error", ex);
         }
    }

    public void setAccountDao(IAccountDao AccountDao) {
        this.accountDao = AccountDao;
    }
	public void setStandarHours(Double standarHours) {
		this.standarHours = standarHours;
	}

	public void closeAccount(String arg0) {
		// TODO Auto-generated method stub
		
	}

    @Override
    public void reopenAccount(String arg0) {
        // TODO Auto-generated method stub
        
    }

}
