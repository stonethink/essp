package server.essp.timesheet.synchronization.dao;

import java.util.Date;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

public class SyncHrmsDaoImp extends JdbcDaoSupport implements ISyncHrmsDao {
	
	private static final String TABLE_NAME_TSEXCEPTION = "ZZ_WISTRON_MID_EMP_PROJECT";
	
	private static final String SQL_INSERT = 
		"INSERT INTO " + TABLE_NAME_TSEXCEPTION
	  + " (COMPANY_CODE, EMPLOYEE_NO, PROJECT_CODE, STATUS, TCS_FLAG,"
	  + " CREATE_EMPLOYEE_ID, CREATE_DATE)"
	  + " VALUES(?, ?, ?, ?, ?, ?, ?)";

	public void add(String companyCode, String projectId, String employeeId, String op) {
		Object[] args = new Object[]{companyCode, employeeId, projectId,
				                  0, op, 0, new Date()};
		this.getJdbcTemplate().update(SQL_INSERT, args);
	}

}
