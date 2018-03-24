package server.essp.timesheet.period.dao;

import java.sql.Types;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import c2s.essp.timesheet.weeklyreport.DtoTimeSheetPeriod;

public class PeriodP3DbDaoImp extends JdbcDaoSupport implements IPeriodP3DbDao {

	private final static String TABLE_NAME_NEXTKEY = "NEXTKEY";
	private final static String TABLE_NAME_TSDATES = "TSDATES";
	private final static String NEXTKEY_NAME = "tsdates_ts_id";
	
	private final static String SQL_INCREASE_NEXTKEY =
        "UPDATE " + TABLE_NAME_NEXTKEY
        + " SET key_seq_num = key_seq_num + 1 "
        + " WHERE key_name = '" + NEXTKEY_NAME + "'";
	
	private final static String SQL_GET_NEXTKEY =
        "SELECT key_seq_num FROM " + TABLE_NAME_NEXTKEY
        + " WHERE key_name = '" + NEXTKEY_NAME + "'";
	
	private final static String SQL_INSERT_TSDATES =
        "INSERT INTO " + TABLE_NAME_TSDATES
        + "(ts_id, start_date, end_date)"
        + " VALUES(?, ?, ?)";
	
	private final static String SQL_DELETE_RSRCHOUR =
        "DELETE FROM " + TABLE_NAME_TSDATES +
        " WHERE ts_id = ? ";
	
	/**
	 * 新增TimeSheet period
	 */
	public void insertPeriod(DtoTimeSheetPeriod dtoPeriod) {
		Object[] args = new Object[] {dtoPeriod.getTsId(), 
									  dtoPeriod.getBeginDate(),
									  dtoPeriod.getEndDate()};
		int[] types = {Types.NUMERIC, Types.DATE, Types.DATE};
		getJdbcTemplate().update(SQL_INSERT_TSDATES, args, types);
	}
	/**
     * 获取新的TsDates主键值
     * @return Long
     */
    public Long getNewTsDatesId() {
        Long nextTsHoursKey = getJdbcTemplate().queryForLong(SQL_GET_NEXTKEY);
        getJdbcTemplate().update(SQL_INCREASE_NEXTKEY);
        return nextTsHoursKey;
    }
	public void deletePeriod(Long tsId) {
		Object[] args = new Object[]{tsId};
        getJdbcTemplate().update(SQL_DELETE_RSRCHOUR, args);		
	}
}
