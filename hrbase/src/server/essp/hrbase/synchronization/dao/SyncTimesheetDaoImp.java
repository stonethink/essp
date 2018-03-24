package server.essp.hrbase.synchronization.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import server.essp.hrbase.dept.viewbean.VbSyncUnitBase;
import server.essp.hrbase.humanbase.viewbean.VbHumanBase;
import server.essp.hrbase.humanbase.viewbean.VbHumanBaseLog;

public class SyncTimesheetDaoImp extends JdbcDaoSupport implements ISyncTimesheetDao {
	
	private static final String TABLE_NAME_TIMESHEET_HUMANBASE = "TS_HUMAN_BASE";
	private static final String TABLE_NAME_TS_ACCOUNT = "TS_ACCOUNT";
	private static final String TABLE_NAME_TS_LABORRESOURCE = "TS_LABOR_RESOURCE";
	
	private static final String SQL_INSERT_TIMESHEET_HUMANBASE = 
		"INSERT INTO " + TABLE_NAME_TIMESHEET_HUMANBASE
      + " (rid, employee_id, unit_code, english_name, chinese_name, title, rank," +
      	" res_manager_id, res_manager_name, email, in_date, out_date, effective_date," +
      	" site, attribute, rct, rut, rcu, ruu, is_direct) "
      + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	
	private static final String SQL_UPDATE_TIMESHEET_HUMANBASE = 
		"UPDATE " + TABLE_NAME_TIMESHEET_HUMANBASE
	  +" SET unit_code=?, english_name=?, chinese_name=?," 
	  +" title=?, rank=?, res_manager_id=?, res_manager_name=?," 
	  +" email=?, in_date=?, out_date=?, effective_date=?, site=?,"
	  +" attribute=?, rct=?, rut=?,  rcu=?, ruu=?, is_direct=? where upper(employee_id)=upper(?)";
	
	private static final String SQL_DELETE_TIMESHEET_HUMANBASE = 
		"DELETE FROM " + TABLE_NAME_TIMESHEET_HUMANBASE
	   +" WHERE upper(employee_id)=upper(?)";
	
	private static final String SQL_INSERT_TIMESHEET_DEPT = 
		"INSERT INTO " + TABLE_NAME_TS_ACCOUNT
	   +" (rid, account_id, account_name, manager, account_brief," 
	   +" account_status, dept_flag, parent_id, org_code)"
	   +" VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
	
	private static final String SQL_UPDATE_TIMESHEET_DEPT = 
		"UPDATE " + TABLE_NAME_TS_ACCOUNT
	   +" SET account_name=?, manager=?, " 
	   +" account_brief=?, parent_id=?"
	   +" WHERE account_id=?";
	private static final String SQL_SET_TIMESHEET_DEPT = 
		"UPDATE " + TABLE_NAME_TS_ACCOUNT
	   +" SET account_status='Y'" 
	   +" WHERE account_id=?";
	private static final String SQL_INSERT_TIMESHEET_LABOR = 
		"INSERT INTO " + TABLE_NAME_TS_LABORRESOURCE
	   +" (rid, login_id, account_rid, name, status)"
	   +" VALUES(?, ?, ?, ?, ?)";
	private static final String SQL_ADD_HUMAN_TO_LABOR = 
		"INSERT INTO " + TABLE_NAME_TS_LABORRESOURCE
	   +" (rid, login_id, account_rid, name, rm, status, rct, rut, rcu, ruu)"
	   +" VALUES(?, ?, (SELECT t.rid FROM ts_account t WHERE t.account_id=?), ?, ?, ?, ?, ?, ?, ?)";
	private static final String SQL_GET_UNITCODE = 
		"SELECT a.rid FROM (" + TABLE_NAME_TS_ACCOUNT + " a join " + TABLE_NAME_TS_LABORRESOURCE + " r"
	   +" ON a.rid=r.account_rid) join " + TABLE_NAME_TIMESHEET_HUMANBASE + " h"
	   +" ON a.account_id = h.unit_code AND lower(r.login_id) = lower(h.employee_id) WHERE lower(r.login_id)=lower(?) AND a.dept_flag='1' ";
	private static final String SQL_UPDATE_HUMAN_IN_LABOR = 
		"UPDATE " + TABLE_NAME_TS_LABORRESOURCE
	   +" SET account_rid=(SELECT rid FROM " + TABLE_NAME_TS_ACCOUNT + " WHERE account_id=? and dept_flag='1')"
	   +" WHERE account_rid=?"
	   +" AND upper(login_id)=upper(?)";
	private static final String SQL_DELETE_HUMAN_IN_LABOR =
		"DELETE FROM " + TABLE_NAME_TS_LABORRESOURCE
	   +" WHERE upper(login_id)=upper(?) "
	   +" AND account_rid=(SELECT rid FROM " + TABLE_NAME_TS_ACCOUNT + " WHERE account_id=? and dept_flag='1')";
	private static final String SQL_QUERY_LABOR =
		"SELECT l.rid FROM " + TABLE_NAME_TS_LABORRESOURCE
	  + " l," + TABLE_NAME_TS_ACCOUNT +" a"
	  + " WHERE a.rid=l.account_rid AND upper(l.login_id)=upper(?) AND a.account_id=?";
	/**
	 * 向timesheet系统中新增人员基本信息
	 */
	public void add(VbHumanBaseLog hrbHumanLog) {
		Object[] args = new Object[]{hrbHumanLog.getRid(), hrbHumanLog.getEmployeeId(),hrbHumanLog.getUnitCode(),
				hrbHumanLog.getEnglishName(), hrbHumanLog.getChineseName(), hrbHumanLog.getTitle(), hrbHumanLog.getRank(),
				hrbHumanLog.getResManagerId(), hrbHumanLog.getResManagerName(), hrbHumanLog.getEmail(), hrbHumanLog.getInDate(),
				hrbHumanLog.getOutDate(), hrbHumanLog.getEffectiveDate(), 
				hrbHumanLog.getSite(), hrbHumanLog.getAttribute(), hrbHumanLog.getRct(), hrbHumanLog.getRut(),
				hrbHumanLog.getRcu(), hrbHumanLog.getRuu(), hrbHumanLog.getIsDirect()};
		this.getJdbcTemplate().update(SQL_INSERT_TIMESHEET_HUMANBASE, args);
	}
	/**
	 * 删除timesheet系统中的人员信息记录
	 */
	public void delete(String employeeId) {
		Object[] args = new Object[]{employeeId};
		this.getJdbcTemplate().update(SQL_DELETE_TIMESHEET_HUMANBASE, args);
	}
	/**
	 * 更新timesheet系统中人员基本信息
	 */
	public void update(VbHumanBaseLog hrbHumanLog) {
		Object[] args = new Object[]{hrbHumanLog.getUnitCode(),hrbHumanLog.getEnglishName(), 
				hrbHumanLog.getChineseName(), hrbHumanLog.getTitle(), hrbHumanLog.getRank(),
				hrbHumanLog.getResManagerId(), hrbHumanLog.getResManagerName(), 
				hrbHumanLog.getEmail(), hrbHumanLog.getInDate(),
				hrbHumanLog.getOutDate(), hrbHumanLog.getEffectiveDate(), 
				hrbHumanLog.getSite(), hrbHumanLog.getAttribute(), hrbHumanLog.getRct(), hrbHumanLog.getRut(),
				hrbHumanLog.getRcu(), hrbHumanLog.getRuu(), hrbHumanLog.getIsDirect(), hrbHumanLog.getEmployeeId(), };
		this.getJdbcTemplate().update(SQL_UPDATE_TIMESHEET_HUMANBASE, args);

	}
	/**
	 * 新增timesheet系统的部门信息
	 */
	public void addUnit(VbSyncUnitBase unitBase) {
		Object[] args = new Object[] {unitBase.getRid(), unitBase.getUnitCode(), 
				                 unitBase.getUnitName(), unitBase.getDmId(), 
				                 unitBase.getUnitFullName(), "N", "1",
				                 unitBase.getParentUnitCode(), unitBase.getUnitCode()};
		this.getJdbcTemplate().update(SQL_INSERT_TIMESHEET_DEPT, args);
	}
	/**
	 * 更新timesheet系统的部门信息
	 */
	public void updateUnit(VbSyncUnitBase unitBase) {
		Object[] args = new Object[] {unitBase.getUnitName(), unitBase.getDmId(), 
                		unitBase.getUnitFullName(), unitBase.getParentUnitCode(), 
                		unitBase.getUnitCode()};
		this.getJdbcTemplate().update(SQL_UPDATE_TIMESHEET_DEPT, args);	
		
	}
	/**
	 * 向timesheet系统插入与部门相关的人员信息
	 */
	public void insertLabor(VbHumanBase hrbHumanBase, Long accountRid) {
		Object[] args = new Object[] {hrbHumanBase.getRid(), hrbHumanBase.getEmployeeId(),
									accountRid, hrbHumanBase.getEnglishName(), "In"};
		this.getJdbcTemplate().update(SQL_INSERT_TIMESHEET_LABOR, args);	
	}
	/**
	 * 删除部门信息（修改状态）
	 */
	public void deleteUnit(String unitCode) {
		this.getJdbcTemplate().update(SQL_SET_TIMESHEET_DEPT, new Object[]{unitCode});
	}
	/**
	 * 将人员加入部门下
	 */
	public void addHuman2Labor(VbHumanBaseLog hrbHumanLog) {
		Object[] args = new Object[] {hrbHumanLog.getEmployeeId(), hrbHumanLog.getUnitCode()};
		Long laborRid = (Long) this.getJdbcTemplate().query(SQL_QUERY_LABOR, 
				                         args, new RidResultSetExtractor());
		if(laborRid != null) { 
			return;
		}
		args = new Object[] {hrbHumanLog.getRid(),
				hrbHumanLog.getEmployeeId(), hrbHumanLog.getUnitCode(),
				hrbHumanLog.getFullName(), hrbHumanLog.getResManagerId(), "In", 
				hrbHumanLog.getRct(), hrbHumanLog.getRut(),
				hrbHumanLog.getRcu(), hrbHumanLog.getRuu()};
		this.getJdbcTemplate().update(SQL_ADD_HUMAN_TO_LABOR, args);
 	}
	/**
	 * 删除人员时从部门中删除该人员信息
	 */
	public void deleteHumanInLabor(String employeeId, String unitCode) {
		Object[] args = new Object[] {employeeId, unitCode};
		this.getJdbcTemplate().update(SQL_DELETE_HUMAN_IN_LABOR, args);
	}
	/**
	 * 变更人员部门时更新Labor Resources中的资料
	 */
	public boolean updateHumanInLabor(VbHumanBaseLog hrbHumanLog) {
		String employeeId = hrbHumanLog.getEmployeeId();
		String unitCode = hrbHumanLog.getUnitCode();
		Object[] args = new Object[] {employeeId};
		Long acntRid = (Long) this.getJdbcTemplate().query(SQL_GET_UNITCODE, 
				                         args, new RidResultSetExtractor());
		if(acntRid == null) {
			return false;
		}
		args = new Object[] {unitCode, acntRid, employeeId};
		this.getJdbcTemplate().update(SQL_UPDATE_HUMAN_IN_LABOR, args);
		return true;
	}
	/**
	 * 构造查询记录RID表的结集类
	 * @author wenhaizheng
	 *
	 */
	private class RidResultSetExtractor implements ResultSetExtractor {
		public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
			if (rs == null || !rs.next()) {
	               return null;
	            }
	        return rs.getLong("rid");
		}
		
	}
}
