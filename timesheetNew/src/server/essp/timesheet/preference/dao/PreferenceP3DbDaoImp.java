package server.essp.timesheet.preference.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
/**
 *
 * <p>Title: PreferenceP3DbDaoImp</p>
 *
 * <p>Description: 访问P3数据库查询preference设置</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public class PreferenceP3DbDaoImp extends JdbcDaoSupport
        implements IPreferenceP3DbDao {

    private final static String SQL_GET_WEEK_START_DAY_NUM =
            "SELECT week_start_day_num "
            + "FROM PREFER WHERE prefer_id = 1";

    /**
     * 获取P3管理员设置中周开始日
     * @return DtoPreference
     */
    public int getWeekStartDayNum() {
        return (Integer) getJdbcTemplate().
                query(SQL_GET_WEEK_START_DAY_NUM,
                      new WeekStartDayNumResultSetExtractor());
    }

    private class WeekStartDayNumResultSetExtractor
            implements ResultSetExtractor {

        public Object extractData(ResultSet rs) throws SQLException,
                DataAccessException {
            if(rs == null || !rs.next())
                return null;
            return rs.getInt("week_start_day_num");
        }
    }

}
