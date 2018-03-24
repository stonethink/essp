package server.essp.timesheet.weeklyreport.dao;


import java.sql.*;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.support.AbstractLobCreatingPreparedStatementCallback;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.lob.*;
import org.springframework.jdbc.support.nativejdbc.SimpleNativeJdbcExtractor;

import com.wits.util.comDate;

import c2s.essp.timesheet.weeklyreport.*;

/**
 *
 * <p>Title: TimeSheet DB Access Object</p>
 *
 * <p>Description: 与TimeSheet相关的数据库操作</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: Wistron ITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public class TimeSheetP3DbDaoImp extends JdbcDaoSupport implements ITimeSheetP3DbDao {

    private final static String TABLE_NAME_TIMESHEET = "TIMESHT";
    private final static String TABLE_NAME_RESOURCEHOUR = "RSRCHOUR";
    private final static String TABLE_NAME_NEXTKEY = "NEXTKEY";
    private final static String TABLE_NAME_TASKRSRC = "TASKRSRC";
    private final static String TABLE_NAME_USER = "USERS";
    private final static String TABLE_NAME_RSRC = "RSRC";
    private final static String TABLE_NAME_TASK = "TASK";

    private final static String NEXTKEY_NAME = "rsrchour_rsrc_hr_id";

    private final static String SQL_INSERT_TIMESHEET =
            "INSERT INTO " + TABLE_NAME_TIMESHEET
            + "(ts_id, rsrc_id, daily_flag, status_code, user_id, last_recv_date, status_date, ts_notes) "
            + "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
    
    private final static String SQL_UPDATE_TIMESHEET_STATUS = 
    	    "UPDATE " + TABLE_NAME_TIMESHEET 
    	    + " set status_code = ? where ts_id = ? and rsrc_id = ?";

    private final static String SQL_INSERT_RESOURCEHOUR =
            "INSERT INTO " + TABLE_NAME_RESOURCEHOUR
            + "(rsrc_hr_id, rsrc_id, ts_id, task_ts_flag, taskrsrc_id, proj_id,"
            + " pend_hr_cnt, hr_cnt, pend_ot_hr_cnt, ot_hr_cnt, work_date, status_code)"
            + " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private final static String SQL_GET_NEXTKEY =
            "SELECT key_seq_num FROM " + TABLE_NAME_NEXTKEY
            + " WHERE key_name = '" + NEXTKEY_NAME + "'";

    private final static String SQL_INCREASE_NEXTKEY =
            "UPDATE " + TABLE_NAME_NEXTKEY
            + " SET key_seq_num = key_seq_num + 1 "
            + " WHERE key_name = '" + NEXTKEY_NAME + "'";

    private final static String SQL_GET_USERID =
            "SELECT user_id FROM " + TABLE_NAME_USER +
            " WHERE lower(user_name) = lower(?) and delete_date is null";

    private final static String SQL_GET_RSRCID =
            "SELECT rsrc_id FROM " + TABLE_NAME_RSRC +
            " WHERE user_id = ? and delete_date is null";

    private final static String SQL_DELETE_RSRCHOUR =
            "DELETE FROM " + TABLE_NAME_RESOURCEHOUR +
            " WHERE ts_id = ? AND rsrc_id = ?";

    private final static String SQL_DELETE_TIMESHT =
            "DELETE FROM " + TABLE_NAME_TIMESHEET +
            " WHERE ts_id = ? AND rsrc_id = ?";

    private final static String SQL_GET_TASKRSRCID =
            "SELECT taskrsrc_id FROM " + TABLE_NAME_TASKRSRC +
            " WHERE task_id = ? AND rsrc_id = ? and delete_date is null";
    private final static String SQL_GET_PROJ_ID =
            "SELECT proj_id FROM " + TABLE_NAME_TASK +
            " WHERE task_id = ? and delete_date is null";

    private String insertStatusCode = DtoTimeSheet.STATUS_APPROVED;

    /**
     * 插入ResourceHour
     * @param dto DtoRsrcHour
     */
    public void insertResourceHours(DtoRsrcHour dto) {
        Object[] args = new Object[] {dto.getRsrcHrId(), dto.getRsrcId(),
                        dto.getTsId(), dto.getTaskTsFlag(), dto.getTaskrsrcId(),
                        dto.getProjId(),
                        dto.getPendHrCnt(),dto.getHrCnt(),dto.getPendOtHrCnt(),
                        dto.getOtHrCnt(), dto.getWorkDate(), insertStatusCode};
        getJdbcTemplate().update(SQL_INSERT_RESOURCEHOUR, args);
    }

    /**
     * 新增TimeSheet
     * @param dto DtoTimeSheet
     */
    public void insertTimeSheet(final DtoTimeSht dto) {
    	final String lastRecvDate = comDate.dateToString(dto.getLastRecvDate());
    	final String statusDate = comDate.dateToString(dto.getStatusDate());
    	OracleLobHandler handler = new OracleLobHandler();
    	handler.setNativeJdbcExtractor(new SimpleNativeJdbcExtractor());
    	getJdbcTemplate().execute(SQL_INSERT_TIMESHEET, new AbstractLobCreatingPreparedStatementCallback((LobHandler)handler){
			protected void setValues(PreparedStatement ps, LobCreator lobCreator) throws SQLException, DataAccessException {
				ps.setLong(1, dto.getTsId());
				ps.setLong(2, dto.getRsrcId());
				ps.setString(3, dto.getDailyFlag());
				ps.setString(4, insertStatusCode);
				ps.setLong(5, dto.getUserId());
				ps.setDate(6, java.sql.Date.valueOf(lastRecvDate));
				ps.setDate(7, java.sql.Date.valueOf(statusDate));
				lobCreator.setBlobAsBytes(ps, 8, dto.getTsNotes().getBytes());
			}
    	});
    }
    
    /**
     * 更新TimeSheet的状态
     */
    public void updateTimeSheetStatus(DtoTimeSht dto, String status) {
    	Object[] args = new Object[]{status, dto.getTsId(), dto.getRsrcId()};
    	getJdbcTemplate().update(SQL_UPDATE_TIMESHEET_STATUS, args);
	}

    /**
     * 获取新的RsrcHour主键值
     * @return Long
     */
    public Long getNewResourceHursId() {
        Long nextTsHoursKey = getJdbcTemplate().queryForLong(SQL_GET_NEXTKEY);
        getJdbcTemplate().update(SQL_INCREASE_NEXTKEY);
        return nextTsHoursKey;
    }
    /**
     * 根据userName(loginId)查询userId
     * @param userName String
     * @return Long
     */
    public Long getUserId(String userName) {
        Object[] args = new Object[]{userName};
        return (Long) getJdbcTemplate()
                .query(SQL_GET_USERID, args, new UserResultSetExtractor());
    }
    /**
     * 根据userId查询rsrcId
     * @param userId Long
     * @return Long
     */
    public Long getRsrcId(Long userId) {
        Object[] args = new Object[]{userId};
        return (Long) getJdbcTemplate()
                .query(SQL_GET_RSRCID, args, new RsrcResultSetExtractor());
    }
    /**
     * 根据rsId和rsrcId删除RSRCHOUR资料
     * @param rsId Long
     * @param rsrcId Long
     */
    public void deleteRsrchour(Long rsId, Long rsrcId) {
        Object[] args = new Object[]{rsId, rsrcId};
        getJdbcTemplate().update(SQL_DELETE_RSRCHOUR, args);
    }
    /**
     * 根据rsId和rsrcId删除TIMESHT资料
     * @param rsId Long
     * @param rsrcId Long
     */
    public void deleteTimesht(Long rsId, Long rsrcId) {
        Object[] args = new Object[]{rsId, rsrcId};
        getJdbcTemplate().update(SQL_DELETE_TIMESHT, args);
    }
    /**
     * 根据taskId和rsrcId获取taskRsrcId
     * @param taskId Long
     * @param rsrcId Long
     * @return Long
     */
    public Long getTaskRsrcId(Long taskId, Long rsrcId) {
        Object[] args = new Object[]{taskId, rsrcId};
        return (Long) getJdbcTemplate().query(SQL_GET_TASKRSRCID,
                                              args, new TaskRsrcResultSetExtractor());
    }
    /**
     * 根据task_id从TASK表中获取proj_id
     * @param taskId Long
     * @return Long
     */
    public Long getProjIdFromTask(Long taskId) {
        Object[] args = new Object[]{taskId};
        return (Long) getJdbcTemplate().query(SQL_GET_PROJ_ID,
                                              args, new TaskResultSetExtractor());
    }
    private class TaskResultSetExtractor implements ResultSetExtractor {
        public Object extractData(ResultSet rs) throws SQLException,
                DataAccessException {
            if (rs == null || !rs.next()) {
              return null;
           }
            return rs.getLong("proj_id");
        }
    }
    private class TaskRsrcResultSetExtractor implements ResultSetExtractor {
        public Object extractData(ResultSet rs) throws SQLException,
                DataAccessException {
            if (rs == null || !rs.next()) {
               return null;
            }
            return rs.getLong("taskrsrc_id");
        }
    }

    private class RsrcResultSetExtractor implements ResultSetExtractor{
        public Object extractData(ResultSet rs) throws SQLException,
                DataAccessException {
            if (rs == null || !rs.next()) {
               return null;
           }
            return rs.getLong("rsrc_id");
        }
    }
    private class UserResultSetExtractor implements ResultSetExtractor {
        public Object extractData(ResultSet rs) throws SQLException,
                DataAccessException {
            if (rs == null || !rs.next()) {
                return null;
            }
            return rs.getLong("user_id");
        }
    }


    public void setInsertStatusCode(String insertStatusCode) {
        this.insertStatusCode = insertStatusCode;
    }

	public Long getResourceHursId() {
		return getJdbcTemplate().queryForLong(SQL_GET_NEXTKEY);
	}

	
}
