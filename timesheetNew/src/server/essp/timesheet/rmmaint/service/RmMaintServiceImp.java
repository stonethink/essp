package server.essp.timesheet.rmmaint.service;

import java.util.*;

import server.essp.common.ldap.LDAPUtil;
import server.essp.timesheet.rmmaint.dao.IRmMaintDao;
import server.framework.common.BusinessException;
import c2s.essp.common.user.DtoUser;
import c2s.essp.common.user.DtoUserBase;
import c2s.essp.timesheet.rmmaint.DtoRmMaint;
import db.essp.timesheet.TsHumanBase;
import db.essp.timesheet.TsRmMaint;

public class RmMaintServiceImp implements IRmMaintService {
	
	private IRmMaintDao rmMaintDao;
	
	private static final String FIND_WAY_AD = "AD";
	private static final String FIND_WAY_DB = "DB";
	/**
	 * 查询方式（可以注入）AD或DB
	 */
	private String findWay = FIND_WAY_AD;
	
	
	
	public void setRmMaintDao(IRmMaintDao rmMaintDao) {
		this.rmMaintDao = rmMaintDao;
	}
	/**
	 *  获取选择的人员的信息列表
	 *  @param loginIds
	 *  @return List
	 */
	public List getUserInfo(String loginIds) {
		List userList = new ArrayList();
		if(loginIds == null || "".equals(loginIds)) {
			return userList;
		}
		String[] loginIdArray = loginIds.split(","); 
		DtoRmMaint dtoRmMaint = null;
		for(String loginId : loginIdArray) {
			dtoRmMaint = new DtoRmMaint();
			dtoRmMaint.setLoginId(loginId);
			dtoRmMaint.setName(getUserName(loginId));
			dtoRmMaint.setDept(getDept(loginId));
			userList.add(dtoRmMaint);
		}
		return userList;
	}
	/**
     * 获取用户名
     * @param loginId String
     * @return String
     */
    public String getUserName(String loginId) {
        String name = "";
        if(FIND_WAY_AD.equals(findWay)){
        	LDAPUtil ldap = new LDAPUtil();
            DtoUser user = ldap.findUser(LDAPUtil.DOMAIN_ALL, loginId);
            if(user != null) {
                name = user.getUserName();
            }
        } else if(FIND_WAY_DB.equals(findWay)) {
        	TsHumanBase tsHumanBase = rmMaintDao.loadHumanById(loginId);
        	if(tsHumanBase != null) {
        		name = tsHumanBase.getFullName();
        	}
        }
        return name;
    }
    /**
     * 根据loginId获取email
     */
    public String getUserEmail(String loginId) {
    	String email = "";
    	if(FIND_WAY_AD.equals(findWay)){
        	LDAPUtil ldap = new LDAPUtil();
            DtoUser user = ldap.findUser(LDAPUtil.DOMAIN_ALL, loginId);
            if(user != null) {
            	email = user.getEmail();
            }
        } else if(FIND_WAY_DB.equals(findWay)) {
        	TsHumanBase tsHumanBase = rmMaintDao.loadHumanById(loginId);
        	if(tsHumanBase != null) {
        		email = tsHumanBase.getEmail();
        	}
        }
    	return email;
    }
    /**
     * 获取部门名称
     * @param loginId
     * @return
     */
    public String getDept(String loginId) {
    	String dept = "";
    	if(FIND_WAY_AD.equals(findWay)){
    		LDAPUtil ldap = new LDAPUtil();
        	dept = ldap.findDirectOU(LDAPUtil.DOMAIN_ALL, loginId);
        	if(dept != null) {
        		dept = "";
        	}
    	} else if(FIND_WAY_DB.equals(findWay)) {
    		TsHumanBase tsHumanBase = rmMaintDao.loadHumanById(loginId);
			if(tsHumanBase != null) {
				dept = tsHumanBase.getUnitCode();
			}
    	}
    	
    	return dept;
    }
    /**
     * 根据loginId获取RM的loginId
     * 如果在关系表中维护了对应关系则从数据库中取出数据
     * 否则从AD中获取RM
     */
	public String getRmByLoginId(String loginId) {
		String rmId = "";
		TsRmMaint tsRmMaint = rmMaintDao.getRmByLoginId(loginId);
		if(tsRmMaint == null || "".equals(tsRmMaint.getRmId())) {
			if(FIND_WAY_AD.equals(findWay)){
				LDAPUtil ldap = new LDAPUtil();
				DtoUser user = ldap.findManager(LDAPUtil.DOMAIN_ALL, loginId);
				if(user != null) {
					rmId = user.getUserLoginId();
				}
			} else if(FIND_WAY_DB.equals(findWay)) {
				TsHumanBase tsHumanBase = rmMaintDao.loadHumanById(loginId);
				if(tsHumanBase != null) {
					rmId = tsHumanBase.getResManagerId();
				}
			}
			
		} else {
			rmId = tsRmMaint.getRmId();
		}
		return rmId;
	}
	/**
	 * 删除RM对应关系
	 */
	public void delRm(String loginId) {
		TsRmMaint tsRmMaint = rmMaintDao.getRmByLoginId(loginId);
		if(tsRmMaint != null){
			rmMaintDao.delRmMaint(tsRmMaint);
		}
	}
	/**
	 * 新增或更新RM对应关系
	 */
	public void saveOrUpdateRm(String loginId, String rmId) {
		String oldRmId = null;
		if(FIND_WAY_AD.equals(findWay)){
			LDAPUtil ldap = new LDAPUtil();
			DtoUser user = ldap.findManager(LDAPUtil.DOMAIN_ALL, loginId);
			oldRmId = user.getUserLoginId();
		} else if(FIND_WAY_DB.equals(findWay)) {
			TsHumanBase tsHumanBase = rmMaintDao.loadHumanById(loginId);
			if(tsHumanBase != null) {
				oldRmId = tsHumanBase.getResManagerId();
			}
		}
		if(rmId.equals(oldRmId)) {
			throw new BusinessException("error.logic.RmMaintServiceImp.sameWithOld", 
					                    "RM same with the one in AD");
		}
		TsRmMaint tsRmMaint = rmMaintDao.getRmByLoginId(loginId);
		if(tsRmMaint != null){
			tsRmMaint.setRmId(rmId);
			rmMaintDao.updateRmMaint(tsRmMaint);
		} else {
			tsRmMaint = new TsRmMaint();
			tsRmMaint.setLoginId(loginId);
			tsRmMaint.setRmId(rmId);
			rmMaintDao.addRmMaint(tsRmMaint);
		}
	}
	/**
	 * 获取已经维护了RM的人员
	 */
	public List getUserList() {
		List userList = new ArrayList();
		List<TsRmMaint> list = rmMaintDao.getAllUserMainted();
		String loginId = "";
		for(TsRmMaint rmMaint : list) {
			DtoRmMaint dtoRmMaint = new DtoRmMaint();
			loginId = rmMaint.getLoginId();
			dtoRmMaint.setLoginId(loginId);
			dtoRmMaint.setName(getUserName(loginId));
			dtoRmMaint.setDept(getDept(loginId));
			userList.add(dtoRmMaint);
		}
		return userList;
	}
	public void setFindWay(String findWay) {
		this.findWay = findWay;
	}
	/**
	 * 获取rm下的相关人员
	 */
	public List<String> getHumanUnderRM(String rmId) {
		//列出当前资源经理管理下的所有人员
		Map<String, String> personMap = new HashMap<String, String>();
		if (FIND_WAY_AD.equals(findWay)) {
			List<DtoUserBase> laborList = rmMaintDao.listPersonByManagerFromAD(rmId);
			if (laborList != null && laborList.size() > 0) {
				// 查询特殊RM维护关系表过滤人员
				TsRmMaint tsRmMaint = null;
				for (DtoUserBase dtoUserBase : laborList) {
					tsRmMaint = rmMaintDao.getRmByLoginId(dtoUserBase
							.getUserLoginId());
					if (tsRmMaint == null || tsRmMaint.getRmId().equals(rmId)) {
						personMap.put(dtoUserBase.getUserLoginId(), dtoUserBase
								.getUserLoginId());
					}
				}
			}
		} else if(FIND_WAY_DB.equals(findWay)) {
			List<TsHumanBase> list = rmMaintDao.listHumanByRmFromDB(rmId);
			if(list != null && list.size() > 0) {
				//查询特殊RM维护关系表过滤人员
				TsRmMaint tsRmMaint = null;
				for (TsHumanBase tsHumanBase : list) {
					tsRmMaint = rmMaintDao.getRmByLoginId(tsHumanBase
							.getEmployeeId());
					if (tsRmMaint == null || tsRmMaint.getRmId().equals(rmId)) {
						personMap.put(tsHumanBase.getEmployeeId(), 
								      tsHumanBase.getEmployeeId());
					}
				}
			}
		}
            //查询特殊RM维护关系下的所有人员加入到人员表中
            List<TsRmMaint> rmMaintList = rmMaintDao.getPersonUnderRm(rmId);
            if(rmMaintList != null && rmMaintList.size() > 0) {
            	for(TsRmMaint rmMaint : rmMaintList) {
            		if(rmMaint.getLoginId() != null && !rmMaint.getLoginId().equals("")){
            			personMap.put(rmMaint.getLoginId(), rmMaint.getLoginId());
            		}
            		
                }
            }
        
        List<String> personList = new ArrayList<String>(personMap.keySet());
		return personList;
	}
	/**
	 * 根据loginId获取site
	 */
	public String getSite(String employeeId) {
		if(employeeId == null) {
    		return null;
    	}
    	TsHumanBase human = rmMaintDao.loadHumanById(employeeId);
    	if(human != null) {
    		return human.getSite();
    	} else {
    		return null;
    	}
	}
	/**
	 * 根据site获取rm下的相关人员
	 */
	public List<String> getHumanUnderRMBySite(String rmId, String site) {
		//列出当前资源经理管理下的所有人员
		Map<String, String> personMap = new HashMap<String, String>();
			List<TsHumanBase> list = rmMaintDao.listHumanByRmFromDBBySite(rmId, site);
			if(list != null && list.size() > 0) {
				//查询特殊RM维护关系表过滤人员
				TsRmMaint tsRmMaint = null;
				for (TsHumanBase tsHumanBase : list) {
					tsRmMaint = rmMaintDao.getRmByLoginId(tsHumanBase
							.getEmployeeId());
					if (tsRmMaint == null || tsRmMaint.getRmId().equals(rmId)) {
						personMap.put(tsHumanBase.getEmployeeId(), 
								      tsHumanBase.getEmployeeId());
					}
				}
			}
            //查询特殊RM维护关系下的所有人员加入到人员表中
            List<TsRmMaint> rmMaintList = rmMaintDao.getPersonUnderRm(rmId);
            if(rmMaintList != null && rmMaintList.size() > 0) {
            	for(TsRmMaint rmMaint : rmMaintList) {
            		if(rmMaint.getLoginId() != null && !rmMaint.getLoginId().equals("")){
            			personMap.put(rmMaint.getLoginId(), rmMaint.getLoginId());
            		}
            		
                }
            }
        List<String> personList = new ArrayList<String>(personMap.keySet());
		return personList;
	}

}
