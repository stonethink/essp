package server.essp.timesheet.account.service;

import java.util.List;

import c2s.essp.timesheet.account.DtoAccount;
import server.essp.timesheet.account.dao.IAccountDao;
import server.essp.timesheet.account.dao.LgAccountPrivilege;
import server.essp.timesheet.code.codetype.dao.ICodeTypeDao;
import db.essp.timesheet.TsAccount;
import db.essp.timesheet.TsCodeType;
import c2s.dto.DtoUtil;
import java.util.ArrayList;

/**
 * <p>Title: Account service</p>
 *
 * <p>Description: 与项目相关的业务逻辑服务</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public class AccountServiceImp implements IAccountService {

    private IAccountDao accountDao;
    private ICodeTypeDao codeTypeDao;
    private String isPrivilege = "true";
    private List privilegeAllUserList = null;

    /**
     * 列出当前用户为项目经理的状态正常的项目
     * @param loginId String 
     * @param isPmo boolean
     * @return List
     * @todo Implement this
     *   server.essp.timesheet.Account.service.IAccountService method
     */
    public List listAccounts(String loginId, boolean isPmo) {
        List resultList = new ArrayList();
        Double actAggregateHours = Double.valueOf(0);
        List<TsAccount> list = null;
        if(isPmo) {
        	String ou = getPrivilegeOu(loginId);
        	list = accountDao.listAccountByOu(ou);
        } else {
        	list = accountDao.listAccounts(loginId);
        }
        if(list!=null && list.size()>0){
            DtoAccount dtoAccount = null;
            TsCodeType tsCodeType = null;
            for(TsAccount tsAccount: list){
                dtoAccount = new DtoAccount();
                DtoUtil.copyBeanToBean(dtoAccount, tsAccount);
                actAggregateHours=accountDao.sumWorkHoursByAccount(tsAccount.getRid(),loginId);
                dtoAccount.setActAggregateHours(actAggregateHours);
                if (tsAccount.getCodeTypeRid() != null) {
					tsCodeType = codeTypeDao.getCodeType(tsAccount
							.getCodeTypeRid());
					if (tsCodeType != null) {
						dtoAccount.setCodeType(tsCodeType.getName());
					}
				}
                resultList.add(dtoAccount);
            }
        }
        return resultList;
    }
    
    /**
     * 获取用户对那些OU下专案的查询权限
     * 		isPrivilege注入"false"表示所有人对所有OU都有权限
     * 		privilegeAllUserList注入对所有OU都有权限的loginId List
     * @param loginId String
     * @return ou String
     */
    private String getPrivilegeOu(String loginId) {
    	if("false".equals(isPrivilege.toLowerCase())) {
    		return null;
    	}
    	if(privilegeAllUserList != null && privilegeAllUserList.contains(loginId)) {
    		return null;
    	}
    	LgAccountPrivilege lg = new LgAccountPrivilege();
    	return lg.getPrivilegeUnit(loginId);
    }

    /**
     * 获取项目信息
     *
     * @param AccountRid Long
     * @return DtoAccount
     * @todo Implement this
     *   server.essp.timesheet.Account.service.IAccountService method
     */
    public DtoAccount loadAccount(Long accountRid) {
        TsAccount tsAccount = accountDao.loadAccount(accountRid);
        DtoAccount dtoAccount = new DtoAccount();
        DtoUtil.copyBeanToBean(dtoAccount, tsAccount);
        return dtoAccount;
    }

    /**
     * 保存项目信息
     *
     * @param Account DtoAccount
     * @todo Implement this
     *   server.essp.timesheet.Account.service.IAccountService method
     */
    public void saveAccount(DtoAccount dtoAccount) {
         TsAccount tsAccount = accountDao.loadAccount(dtoAccount.getRid());
         tsAccount.setCodeTypeRid(dtoAccount.getCodeTypeRid());
         tsAccount.setLeaveCodeTypeRid(dtoAccount.getLeaveCodeTypeRid());
         tsAccount.setMethodRid(dtoAccount.getMethodRid());
         tsAccount.setIsMail(dtoAccount.getIsMail());
         tsAccount.setP6Id(dtoAccount.getP6Id());
         accountDao.saveAccount(tsAccount);
    }

    public void setAccountDao(IAccountDao accountDao) {
        this.accountDao = accountDao;
    }

	public void setCodeTypeDao(ICodeTypeDao codeTypeDao) {
		this.codeTypeDao = codeTypeDao;
	}

	public List queryAccounts(DtoAccount dto, String loginId) {
		List resultList = new ArrayList();
        Double actAggregateHours = Double.valueOf(0);
        List<TsAccount> list = null;
        list = accountDao.listByCondtion(dto, getPrivilegeOu(loginId));
     
        if(list!=null && list.size()>0){
            DtoAccount dtoAccount = null;
            TsCodeType tsCodeType = null;
            for(TsAccount tsAccount: list){
                dtoAccount = new DtoAccount();
                DtoUtil.copyBeanToBean(dtoAccount, tsAccount);
                actAggregateHours=accountDao.sumWorkHoursByAccount(tsAccount.getRid());
                dtoAccount.setActAggregateHours(actAggregateHours);
                if (tsAccount.getCodeTypeRid() != null) {
					tsCodeType = codeTypeDao.getCodeType(tsAccount
							.getCodeTypeRid());
					if (tsCodeType != null) {
						dtoAccount.setCodeType(tsCodeType.getName());
					}
				}
                resultList.add(dtoAccount);
            }
        }
        return resultList;
	}

	public void setIsPrivilege(String isPrivilege) {
		this.isPrivilege = isPrivilege;
	}
}
