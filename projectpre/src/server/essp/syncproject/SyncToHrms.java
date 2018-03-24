package server.essp.syncproject;

import java.util.Date;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import server.essp.projectpre.db.Acnt;
import server.framework.common.BusinessException;

public class SyncToHrms extends JdbcDaoSupport implements ISyncToHrms{
	
	private static final String TABLE_NAME_PROJECT = "ZZ_WISTRON_MID_SALARY_PROJECT";
	
	private static final String SQL_INSERT_PROJECT = 
		"INSERT INTO " + TABLE_NAME_PROJECT
      + " (COMPANY_CODE, PROJECT_CODE, PROJECT_CNAME, PROJECT_ENAME," 
      + " PROJECT_SDATE, PROJECT_MGR_NO, STATUS, TCS_FLAG, " 
      + " CREATE_EMPLOYEE_ID, CREATE_DATE) "
      + " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	
	private static final String SQL_CLOSE_PROJECT = 
		"INSERT INTO " + TABLE_NAME_PROJECT
	      + " (COMPANY_CODE, PROJECT_CODE, PROJECT_CNAME, PROJECT_ENAME," 
	      + " PROJECT_SDATE, PROJECT_EDATE, PROJECT_MGR_NO, STATUS, TCS_FLAG, " 
	      + " CREATE_EMPLOYEE_ID, CREATE_DATE) "
	      + " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	public void addProjcet(Acnt acnt, String site, String pmId) throws BusinessException {
		try{
			Object[] args = null;
			Date start = null;
			if(acnt.getAcntActualStart() != null) {
				start = acnt.getAcntActualStart();
			} else {
				start = acnt.getAcntPlannedStart();
			}
			args = new Object[]{site, acnt.getAcntId(), 
						acnt.getAcntFullName(),
						acnt.getAcntName(), start, pmId, 0, 
						"I", 0, new Date()};
			
		    this.getJdbcTemplate().update(SQL_INSERT_PROJECT, args);
		} catch (Exception e) {
			throw new BusinessException("Synchronize Hrms Insert Error", e.getMessage());
		}
	}

	public void updateProject(Acnt acnt, String site, String pmId) throws BusinessException {
		try{
			Object[] args = null;
			Date start = null;
			if(acnt.getAcntActualStart() != null) {
				start = acnt.getAcntActualStart();
			} else {
				start = acnt.getAcntPlannedStart();
			}
			args = new Object[]{site, acnt.getAcntId(), acnt.getAcntFullName(),
					   acnt.getAcntName(), start, 
					   pmId, 0, "U", 0, new Date()};
			
			this.getJdbcTemplate().update(SQL_INSERT_PROJECT, args);
		} catch (Exception e) {
			throw new BusinessException("Synchronize Hrms Update Error", e.getMessage());
		}
	}

	public void closeProject(Acnt acnt, String site, String pmId)
			throws BusinessException {
		try{
			Object[] args = null;
			Date start = null;
			Date finish = null;
			if(acnt.getAcntActualStart() != null  ) {
				start = acnt.getAcntActualStart();
			} else {
				start = acnt.getAcntPlannedStart();
			}
			finish = acnt.getAcntActualFinish();
			
		    args = new Object[]{site, acnt.getAcntId(), acnt.getAcntFullName(),
					   acnt.getAcntName(), start,  finish, 
					   pmId, 0, "U", 0, new Date()};
			
		    this.getJdbcTemplate().update(SQL_CLOSE_PROJECT, args);
		} catch (Exception e) {
			throw new BusinessException("Synchronize Hrms Close Error", e.getMessage());
		}
	}

}
