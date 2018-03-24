package server.essp.pms.qa.monitoring.logic;

import server.essp.framework.logic.AbstractESSPLogic;
import java.util.List;
import c2s.essp.pms.qa.DtoNcrIssue;

public class LgNcrIssue extends AbstractESSPLogic {
    private final static String SQL_HEAD =
        "select t.issue_id, t.issue_name, h.chinese_name || '(' || t.principal || ')' as principal, "
         + " t.priority, t.filledate as fill_date, t.duedate as due_date, c.finished_date as finish_date, t.issue_status "
         + "from issue t left join issue_conclusion c on t.rid = c.rid "
         + "left join (essp_hr_employee_main_t h left join upms_loginuser l on h.code = l.user_id) on t.principal = l.login_id "
         + "where t.type_name = 'NCR' ";

     private final static String SQL_ORDER_BY = " order by t.filledate";

     public List list(Long acntRid) {
         String sql = SQL_HEAD
                      + " and t.account_id = " + acntRid
                      + SQL_ORDER_BY;
         List list =  this.getDbAccessor().executeQueryToList(sql, DtoNcrIssue.class);
         return list;
     }
}
