package server.essp.timesheet.account.labor.service;

import java.util.*;

import server.essp.timesheet.account.labor.dao.ILaborDao;
import server.essp.timesheet.account.labor.level.dao.ILaborLevelDao;
import server.essp.timesheet.account.labor.role.dao.ILaborRoleDao;
import server.essp.timesheet.rmmaint.service.IRmMaintService;
import server.framework.common.BusinessException;
import c2s.dto.DtoUtil;
import c2s.essp.timesheet.account.DtoAccount;
import c2s.essp.timesheet.labor.DtoLaborResource;
import db.essp.timesheet.*;

/**
 * <p>Title: labor resource service</p>
 *
 * <p>Description: 与项目人力资源相关的业务逻辑服务</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public class LaborServiceImp implements ILaborService {

    private ILaborDao laborDao;
    private ILaborLevelDao laborLevelDao;
    private ILaborRoleDao  laborRoleDao ;
    private IRmMaintService rmMaintService;

    public void setRmMaintService(IRmMaintService rmMaintService) {
		this.rmMaintService = rmMaintService;
	}

	/**
     * 为某项目增加人力资源
     * 如果新增人员在该项目中将会抛出异常信息
     *
     * @param labor DtoLaborResource
     * @todo Implement this
     *   server.essp.timesheet.Account.labor.service.ILaborService method
     */
    public void addLabor(DtoLaborResource labor) {
        TsLaborResource tsLabor = null;
        tsLabor = laborDao.loadByAccountIdLoginId(labor.getAccountRid(),
                                                  labor.getLoginId());
        if(tsLabor != null){
           throw new BusinessException("error.logic.LaborServiceImp.sameProject",
                                       "This person is already in this project");
        }
        tsLabor = new TsLaborResource();
        DtoUtil.copyBeanToBean(tsLabor, labor);
        laborDao.addLabor(tsLabor);
    }

    /**
     * 把指定人力资源从项目中删除
     *
     * @param labor DtoLaborResource
     * @todo Implement this
     *   server.essp.timesheet.Account.labor.service.ILaborService method
     */
    public void deleteLabor(DtoLaborResource labor) {
        TsLaborResource tsLabor = new TsLaborResource();
        DtoUtil.copyBeanToBean(tsLabor, labor);
        laborDao.deleteLabor(tsLabor);
    }

    /**
     * 列出指定项目下的人力资源
     *
     * @param AccountRid Long
     * @return List
     * @todo Implement this
     *   server.essp.timesheet.Account.labor.service.ILaborService method
     */
    public List listLabor(Long accountRid) {
        List resultList = new ArrayList();
        List<TsLaborResource> list = laborDao.listLabor(accountRid);
        if(list != null && list.size() > 0) {
            DtoLaborResource dtoLabor = null;
            TsLaborLevel tsLaborLevel = null;
            TsLaborRole tsLaborRole = null;
            for(TsLaborResource tsLabor : list){
                dtoLabor = new DtoLaborResource();
                DtoUtil.copyBeanToBean(dtoLabor, tsLabor);
                if (tsLabor.getLevelRid() != null) {
					try {
						tsLaborLevel = laborLevelDao.getLevelByRid(tsLabor
															.getLevelRid());
					} catch (Exception e) {}
					if (tsLaborLevel != null) {
						dtoLabor.setLevel(tsLaborLevel.getName());
					}
				}
                if(tsLabor.getRoleRid() != null) {
                	try {
						tsLaborRole = laborRoleDao.getRoleByRid(tsLabor
														.getRoleRid());
					} catch (Exception e) {}
                	if(tsLaborRole != null) {
                		dtoLabor.setRole(tsLaborRole.getName());
                	}
                }
                resultList.add(dtoLabor);
            }
        }
        return resultList;
    }

    /**
     * 保存人力资源信息
     *
     * @param labor DtoLaborResource
     * @todo Implement this
     *   server.essp.timesheet.Account.labor.service.ILaborService method
     */
    public void saveLabor(DtoLaborResource labor) {
        TsLaborResource tsLabor = laborDao.loadLabor(labor.getRid());
        tsLabor.setLoginId(labor.getLoginId());
        tsLabor.setName(labor.getName());
        tsLabor.setRm(labor.getRm());
        tsLabor.setStatus(labor.getStatus());
        tsLabor.setRemark(labor.getRemark());
        tsLabor.setLevelRid(labor.getLevelRid());
        tsLabor.setRoleRid(labor.getRoleRid());
        laborDao.saveLabor(tsLabor);
    }

    public void setLaborDao(ILaborDao laborDao) {
        this.laborDao = laborDao;
    }

	public void setLaborLevelDao(ILaborLevelDao laborLevelDao) {
		this.laborLevelDao = laborLevelDao;
	}

	public void setLaborRoleDao(ILaborRoleDao laborRoleDao) {
		this.laborRoleDao = laborRoleDao;
	}
	/**
	 * 增加多个人员
	 */
	public List addLabors(String loginIds, Long rid) {
		if(loginIds == null || "".equals(loginIds)) {
			return new ArrayList();
		}
		String[] loginIdArray = loginIds.split(","); 
		List<TsLaborResource> list = laborDao.listLabor(rid);
		Map laborMap = new HashMap();
		if(list != null && list.size() > 0) {
			for(TsLaborResource labor : list) {
				laborMap.put(labor.getLoginId(), labor.getLoginId());
			}
		}
		TsLaborResource tsLabor = null;
		List loginIdList = new ArrayList();
		for(String loginId : loginIdArray) {
			if(!laborMap.containsKey(loginId)) {
				tsLabor = new TsLaborResource();
				tsLabor.setLoginId(loginId);
				tsLabor.setAccountRid(rid);
				tsLabor.setRm(rmMaintService.getRmByLoginId(loginId));
				tsLabor.setName(rmMaintService.getUserName(loginId));
				tsLabor.setStatus(DtoAccount.STATUS_IN);
				laborDao.addLabor(tsLabor);
				loginIdList.add(loginId);
			}
		}
		return loginIdList;
	}

	public List getPersonInProject(String accountId) {
		return laborDao.listPersonInProject(accountId);
	}
}
