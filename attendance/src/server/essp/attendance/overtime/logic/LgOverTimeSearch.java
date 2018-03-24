package server.essp.attendance.overtime.logic;

import server.essp.attendance.overtime.form.AfOverTimeSearch;
import java.util.Date;
import com.wits.util.comDate;
import server.essp.framework.logic.AbstractESSPLogic;
import java.util.List;
import server.essp.attendance.overtime.viewbean.VbOverTimeSearch;
import c2s.essp.attendance.Constant;

public class LgOverTimeSearch extends AbstractESSPLogic {

    private static final String SQL_HEAD =
            "select o.rid,o.login_id,hr.name as emp_name,hr.chinese_name,u.name as org_name,a.acnt_name,o.actual_datetime_start as begin_date,o.actual_datetime_finish as end_date, sum(d.hours) as total_hours, sum(d.shift_hours) as shift_hours, sum(d.payed_hours) as payed_hours "
            +"from tc_overtime o left  join tc_overtime_detail d on o.rid=d.overtime_id "
            + "left join (pms_acnt a left join essp_upms_unit u on a.acnt_organization=u.unit_id) on o.acnt_rid=a.rid "
            + "left join (upms_loginuser l left join essp_hr_employee_main_t hr on l.user_id=hr.code) on o.login_id=l.login_id where o.status='" + Constant.STATUS_FINISHED + "' ";
    private static final String SQL_GROUP_BY =
            " group by o.rid,o.login_id,hr.name,hr.chinese_name,u.name,unit_code,a.acnt_name,o.actual_datetime_start,o.actual_datetime_finish";

    private static final String SQL_ORDER_BY =
            " order by u.unit_code,a.acnt_name,hr.chinese_name,o.actual_datetime_start";

    public List queryByCondition(AfOverTimeSearch af){
        String acntRid = af.getAccount_id();
        String orgId = af.getDepart_code();
        String beginStr = af.getBeginDate();
        String endStr = af.getEndDate();
        Date beginDate = null;
        Date endDate = null;
        if (beginStr != null && !"".equals(beginStr)) {
            beginDate = comDate.toDate(af.getBeginDate());
        }
        if (endStr != null && !"".equals(endStr)) {
            endDate = comDate.toDate(af.getEndDate());
        }
        String employee = af.getEmpName();

        StringBuffer buf = new StringBuffer();
        //构造SQL语句
        buf.append(SQL_HEAD);

        //生成查询条件，
        if (acntRid != null && !"".equals(acntRid) && !"-1".equals(acntRid)) {
            buf.append(" and a.rid=" + acntRid);
        } else if (orgId != null && !"".equals(orgId) && !"-1".equals(orgId)) {
            buf.append(" and a.acnt_organization='" + orgId + "'");
        }

       //日期求交集
       if(beginDate != null){
           buf.append(" and to_char(o.actual_datetime_finish,'"+comDate.pattenDate+"')>='"
                      +comDate.dateToString(beginDate,comDate.pattenDate)+ "'");
       }
       if(endDate != null){
           buf.append(" and to_char(o.actual_datetime_start,'"+comDate.pattenDate+"')<='"
                      +comDate.dateToString(endDate,comDate.pattenDate)+ "'");
       }

       //员工姓名模糊匹配
       if (employee != null && !"".equals(employee)) {
           buf.append(" and lower(hr.name) like lower('%" + employee + "%')");
       }

       //聚合函数分组
       buf.append(SQL_GROUP_BY);
       //排序
       buf.append(SQL_ORDER_BY);

       List lst= this.getDbAccessor().executeQueryToList(buf.toString(),
               VbOverTimeSearch.class);
       return lst;
    }

}
