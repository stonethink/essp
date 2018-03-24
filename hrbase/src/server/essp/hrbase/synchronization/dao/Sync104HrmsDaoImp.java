package server.essp.hrbase.synchronization.dao;

import java.util.Date;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import db.essp.hrbase.HrbUnitBase;

public class Sync104HrmsDaoImp extends JdbcDaoSupport implements ISync104HrmsDao {
	
	private static final String TABLE_NAME_104PROJECT = "ZZ_WISTRON_MID_SALARY_PROJECT";
	
	private static final String SQL_INSERT_104PROJECT = 
		"INSERT INTO " + TABLE_NAME_104PROJECT + "(COMPANY_CODE, PROJECT_CODE, " 
	  + " PROJECT_CNAME, PROJECT_ENAME, PROJECT_SDATE, PROJECT_EDATE, PROJECT_MGR_NO, STATUS, TCS_FLAG, CREATE_EMPLOYEE_ID, CREATE_DATE) " 
	  + " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	
	private static final String SQL_UPDATE_104PROJECT = 
		"INSERT INTO " + TABLE_NAME_104PROJECT + "(COMPANY_CODE, PROJECT_CODE, " 
	  + " PROJECT_CNAME, PROJECT_ENAME, PROJECT_SDATE, PROJECT_EDATE, PROJECT_MGR_NO, STATUS, TCS_FLAG, CREATE_EMPLOYEE_ID, CREATE_DATE) " 
	  + " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	
	private static final String SQL_DELETE_104PROJECT = 
		"INSERT INTO " + TABLE_NAME_104PROJECT + "(COMPANY_CODE, PROJECT_CODE, " 
	  + " PROJECT_CNAME, PROJECT_ENAME, PROJECT_SDATE, PROJECT_EDATE, PROJECT_MGR_NO, STATUS, TCS_FLAG, CREATE_EMPLOYEE_ID, CREATE_DATE) " 
	  + " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	public void add(HrbUnitBase unitBase) {
		Object[] args =  new Object[] {unitBase.getBelongSite(), unitBase.getUnitCode(),
				nvlString(unitBase.getUnitFullName()), unitBase.getUnitName(), 
				   unitBase.getEffectiveBegin(), unitBase.getEffectiveEnd(),
				   unitBase.getDmId(), 0, "I", 0, new Date()};
		this.getJdbcTemplate().update(SQL_INSERT_104PROJECT, args);
	}
	private String nvlString(String str) {
		if(str == null) {
			return "";
		}
		return str;
	}
	public void update(HrbUnitBase unitBase) {
		Object[] args =  new Object[] {unitBase.getBelongSite(), unitBase.getUnitCode(),
				nvlString(unitBase.getUnitFullName()), unitBase.getUnitName(), 
				   unitBase.getEffectiveBegin(), unitBase.getEffectiveEnd(),
				   unitBase.getDmId(), 0, "U", 0, new Date()};
		this.getJdbcTemplate().update(SQL_UPDATE_104PROJECT, args);
	}

	public void delete(HrbUnitBase unitBase) {
		Object[] args =  new Object[] {unitBase.getBelongSite(), unitBase.getUnitCode(),
				   unitBase.getUnitFullName(), unitBase.getUnitName(), 
				   unitBase.getEffectiveBegin(), unitBase.getEffectiveEnd(),
				   unitBase.getDmId(), 0, "D", 0, new Date()};
		this.getJdbcTemplate().update(SQL_DELETE_104PROJECT, args);
	}

}
