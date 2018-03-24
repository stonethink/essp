/*
 * Created on 2008-1-29
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.timesheet.report.attvariant.dao;

import java.sql.Types;
import java.util.Date;
import java.util.List;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import c2s.essp.timesheet.report.DtoAttVariant;
import c2s.essp.timesheet.report.DtoAttVariantQuery;
import server.essp.timesheet.common.dao.BeanRowMapper;

public class AttVariantDaoImp extends JdbcDaoSupport implements IAttVariantDao {

	private final static int[] TYPES = { Types.VARCHAR, Types.DATE, Types.DATE,
			Types.VARCHAR, Types.VARCHAR, Types.DATE, Types.DATE, Types.VARCHAR };

	private final static BeanRowMapper MAPPER = new BeanRowMapper(
			DtoAttVariant.class);

	public List queryLeaveVariant(DtoAttVariantQuery dtoQuery) {
        String loginId = dtoQuery.getEmpId();
        String site = dtoQuery.getSite();
        Date begin = dtoQuery.getBeginDate();
        Date end = dtoQuery.getEndDate();
        Boolean selectAll = dtoQuery.getSelectAll();
		String id = "%";
		if (loginId != null && "".equals(loginId.trim()) == false) {
			id += loginId + "%";
		}
		Object[] args;
		args = new Object[] { site, begin, end, id, site, begin, end, id };
        if(selectAll){
		  return getJdbcTemplate().query(LEAVE_ALL_SQL, args, TYPES, MAPPER);
        }else{
          return getJdbcTemplate().query(LEAVE_SQL, args, TYPES, MAPPER);
        }
	}

	public List queryOvertimeVariant(DtoAttVariantQuery dtoQuery) {
        String loginId = dtoQuery.getEmpId();
        String site = dtoQuery.getSite();
        Date begin = dtoQuery.getBeginDate();
        Date end = dtoQuery.getEndDate();
        Boolean selectAll = dtoQuery.getSelectAll();
		String id = "%";
		if (loginId != null && "".equals(loginId.trim()) == false) {
			id += loginId + "%";
		}
		Object[] args = new Object[] { site, begin, end, id, site, begin, end,
				id };
        if(selectAll){
		   return getJdbcTemplate().query(OVERTIME_ALL_SQL, args, TYPES, MAPPER);
        }else{
            return getJdbcTemplate().query(OVERTIME_SQL, args, TYPES, MAPPER); 
        }
	}

	private final static String LEAVE_ALL_SQL = "select case when m.login_id is not null then m.login_id else l.employee_id end as login_id, "
			+ "case when m.login_id is not null then h1.english_name else h2.english_name end as english_name, "
			+ "case when m.login_id is not null then h1.chinese_name else h2.chinese_name end as chinese_name, "
			+ "case when d.work_date is not null then d.work_date else l.leave_date end as att_date, "
			+ "d.rid as ts_rid,a.account_id as ts_unit_code,c.name as ts_code,d.work_hours as ts_hours, "
			+ "l.rid as hr_rid,l.unit_code as hr_unit_code,l.phase_code as hr_code,l.actual_hours as hr_hours "
			+ "from (ts_timesheet_master m "
			+ "join ts_timesheet_detail t on t.ts_rid = m.rid "
			+ "join ts_timesheet_day d on d.ts_detail_rid = t.rid "
			+ "left join ts_account a on a.rid = t.account_rid "
			+ "join ts_code_value c on c.rid = t.code_value_rid "
			+ "join ts_human_base h1 on upper(h1.employee_id) = upper(m.login_id) "
			+ ") "
			+ "full outer join  "
			+ "(att_leave l  "
			+ "join ts_human_base h2 on upper(h2.employee_id) = upper(l.employee_id)) "
			+ "on upper(l.employee_id) = upper(m.login_id) and l.leave_date = d.work_date "
			+ "where (c.is_leave_type = '1' or c.is_leave_type is null) and (h1.site = ? or h1.site is null) and" 
            +" ((d.work_date >= ? and d.work_date <= ?) or d.work_date is null) and (upper(h1.employee_id) like upper(?) or h1.employee_id is null) "
			+ "and (h2.site = ? or h2.site is null) and ((l.leave_date >= ? and l.leave_date <= ?) or l.leave_date is null)"
            +" and (upper(h2.employee_id) like upper(?) or h2.employee_id is null) "
			+ "order by case when d.work_date is not null then d.work_date else l.leave_date end ";

    private final static String LEAVE_SQL = "select case when m.login_id is not null then m.login_id else l.employee_id end as login_id, "
        + "case when m.login_id is not null then h1.english_name else h2.english_name end as english_name, "
        + "case when m.login_id is not null then h1.chinese_name else h2.chinese_name end as chinese_name, "
        + "case when d.work_date is not null then d.work_date else l.leave_date end as att_date, "
        + "d.rid as ts_rid,a.account_id as ts_unit_code,c.name as ts_code,d.work_hours as ts_hours, "
        + "l.rid as hr_rid,l.unit_code as hr_unit_code,l.phase_code as hr_code,l.actual_hours as hr_hours "
        + "from (ts_timesheet_master m "
        + "join ts_timesheet_detail t on t.ts_rid = m.rid "
        + "join ts_timesheet_day d on d.ts_detail_rid = t.rid "
        + "left join ts_account a on a.rid = t.account_rid "
        + "join ts_code_value c on c.rid = t.code_value_rid "
        + "join ts_human_base h1 on upper(h1.employee_id) = upper(m.login_id) "
        + ") "
        + "full outer join  "
        + "(att_leave l  "
        + "join ts_human_base h2 on upper(h2.employee_id) = upper(l.employee_id)) "
        + "on upper(l.employee_id) = upper(m.login_id) and l.leave_date = d.work_date "
        + "where (c.is_leave_type = '1' or c.is_leave_type is null) and (h1.site = ? or h1.site is null) and" 
        +" ((d.work_date >= ? and d.work_date <= ?) or d.work_date is null) and (upper(h1.employee_id) like upper(?) or h1.employee_id is null) "
        + "and (h2.site = ? or h2.site is null) and ((l.leave_date >= ? and l.leave_date <= ?) or l.leave_date is null)"
        +" and (upper(h2.employee_id) like upper(?) or h2.employee_id is null) " 
        +" and (d.work_hours is null or l.actual_hours is null or d.work_hours <> l.actual_hours) "
        + "order by case when d.work_date is not null then d.work_date else l.leave_date end ";
    
    
	private final static String OVERTIME_ALL_SQL = "select case when m.login_id is not null then m.login_id else o.employee_id end as login_id, "
			+ "case when m.login_id is not null then h1.english_name else h2.english_name end as english_name, "
			+ "case when m.login_id is not null then h1.chinese_name else h2.chinese_name end as chinese_name, "
			+ "case when d.work_date is not null then d.work_date else o.overtime_date end as att_date, "
			+ "d.rid as ts_rid,h1.unit_code as ts_unit_code,a.account_id as ts_code,d.overtime_hours as ts_hours, "
			+ "o.rid as hr_rid,o.unit_code as hr_unit_code,o.project_code as hr_code,o.actual_hours as hr_hours "
			+ "from (ts_timesheet_master m "
			+ "join ts_timesheet_detail t on t.ts_rid = m.rid "
			+ "join ts_timesheet_day d on d.ts_detail_rid = t.rid "
			+ "left join ts_account a on a.rid = t.account_rid "
			+ "join ts_human_base h1 on upper(h1.employee_id) = upper(m.login_id) "
			+ ") "
			+ "full outer join  "
			+ "(att_overtime o  "
			+ "join ts_human_base h2 on upper(h2.employee_id) = upper(o.employee_id)) "
			+ "on upper(o.employee_id) = upper(m.login_id) and o.overtime_date= d.work_date "
			+ "where d.overtime_hours > 0 and (h1.site = ? or h1.site is null) and ((d.work_date >= ? and"
            + " d.work_date <= ?) or d.work_date is null) and (upper(h1.employee_id) like upper(?) or h1.employee_id is null) "
			+ " and (h2.site = ? or h2.site is null) and ((o.overtime_date >= ? and o.overtime_date <= ?) "
            + " or o.overtime_date is null) and (upper(h2.employee_id) like upper(?) or h2.employee_id is null)"
			+ "order by case when d.work_date is not null then d.work_date else o.overtime_date end ";
    
    private final static String OVERTIME_SQL = "select case when m.login_id is not null then m.login_id else o.employee_id end as login_id, "
        + "case when m.login_id is not null then h1.english_name else h2.english_name end as english_name, "
        + "case when m.login_id is not null then h1.chinese_name else h2.chinese_name end as chinese_name, "
        + "case when d.work_date is not null then d.work_date else o.overtime_date end as att_date, "
        + "d.rid as ts_rid,h1.unit_code as ts_unit_code,a.account_id as ts_code,d.overtime_hours as ts_hours, "
        + "o.rid as hr_rid,o.unit_code as hr_unit_code,o.project_code as hr_code,o.actual_hours as hr_hours "
        + "from (ts_timesheet_master m "
        + "join ts_timesheet_detail t on t.ts_rid = m.rid "
        + "join ts_timesheet_day d on d.ts_detail_rid = t.rid "
        + "left join ts_account a on a.rid = t.account_rid "
        + "join ts_human_base h1 on upper(h1.employee_id) = upper(m.login_id) "
        + ") "
        + "full outer join  "
        + "(att_overtime o  "
        + "join ts_human_base h2 on upper(h2.employee_id) = upper(o.employee_id)) "
        + "on upper(o.employee_id) = upper(m.login_id) and o.overtime_date= d.work_date "
        + "where d.overtime_hours > 0 and (h1.site = ? or h1.site is null) and ((d.work_date >= ? and"
        + " d.work_date <= ?) or d.work_date is null) and (upper(h1.employee_id) like upper(?) or h1.employee_id is null) "
        + " and (h2.site = ? or h2.site is null) and ((o.overtime_date >= ? and o.overtime_date <= ?) "
        + " or o.overtime_date is null) and (upper(h2.employee_id) like upper(?) or h2.employee_id is null)"
        + " and (d.overtime_hours is null or o.actual_hours is null or d.overtime_hours <> o.actual_hours)"
        + " order by case when d.work_date is not null then d.work_date else o.overtime_date end ";
}
