package server.essp.attendance.overtime.logic;

import java.util.*;

import javax.sql.RowSet;

import c2s.dto.DtoUtil;
import c2s.essp.attendance.Constant;
import com.wits.util.comDate;
import server.essp.attendance.overtime.viewbean.VbOverTimeReport;
import server.essp.framework.logic.AbstractESSPLogic;
import server.framework.common.BusinessException;

public class LgOverTimeReport extends AbstractESSPLogic {
    private static final String SQL_HEAD =
            "select l.login_id,hr.name,hr.chinese_name,hr.indate as in_date,u.unit_code,u.name as unit_name,a.acnt_id,a.acnt_name, sum(d.hours) as total_hours, sum(d.shift_hours) as shift_hours, sum(d.payed_hours) as payed_hours "
            + "from tc_overtime o left  join tc_overtime_detail d on o.rid=d.overtime_id "
            + "left join pms_acnt a on o.acnt_rid=a.rid "
            + "left join (upms_loginuser l left join essp_hr_employee_main_t hr on l.user_id=hr.code) on o.login_id=l.login_id  left join essp_upms_unit u on hr.dept=u.unit_id where o.status='" + Constant.STATUS_FINISHED + "' ";

    private static final String SQL_GROUP_BY =
            " group by l.login_id,acnt_id,o.login_id,hr.name,hr.chinese_name,hr.indate,u.unit_code,u.name,a.acnt_id,a.acnt_name";

    private static final String SQL_ORDER_BY =
            " order by u.unit_code desc, lower(l.login_id) desc, a.acnt_id";

    /**
     * ��ȡָ��HR Account������Ա������ĳʱ�������ڣ���Ա�����ܣ�����Ŀ��ϸ�ļӰ��¼
     * @param acntRid Long
     * @param beginDate Date
     * @param endDate Date
     * @return List
     */
    public List getData(Long acntRid, Date beginDate, Date endDate) {
        List userList = getUserListInHrAcnt(acntRid);
        String users = exchangUserList2Str(userList);
        List tcList = getDataBeanList(users, beginDate, endDate);

        List result = new ArrayList();
        //��loginId����
        for(int i = 0; i < userList.size(); i++) {
            String loginId = (String) userList.get(i);

            //��ӻ��ܼ�¼
            VbOverTimeReport sumVb = new VbOverTimeReport();
            result.add(sumVb);

            //������ѯ��������ܵ�ǰloginId��¼
            for(int j = tcList.size() - 1; j >= 0; j--) {
                VbOverTimeReport vb = (VbOverTimeReport) tcList.get(j);
                if(loginId.equals(vb.getLoginId())) {
                    if(sumVb.getLoginId() == null) {
                        sumVb.setLoginId(loginId);
                        DtoUtil.copyBeanToBean(sumVb, vb);
                        sumVb.setSumHours(sumVb.getTotalHours());
                        sumVb.setTotalHours(null);
                        sumVb.setAcntId(null);
                        sumVb.setAcntName(null);
                    } else {
                        sumVb.setSumHours(sumVb.getSumHours() + vb.getTotalHours());
                        sumVb.setShiftHours(sumVb.getShiftHours() + vb.getShiftHours());
                        sumVb.setPayedHours(sumVb.getPayedHours() + vb.getPayedHours());
                    }

                    //��Ŀ��ϸ��¼����ո�����Ϣ
                    vb.setChineseName(null);
                    vb.setLoginId(null);
                    vb.setName(null);
                    vb.setUnitCode(null);
                    vb.setUnitName(null);
                    vb.setInDate(null);

                    tcList.remove(vb);
                    result.add(vb);
                }
            }
            //����޼Ӱ��¼��
            if(sumVb.getLoginId() == null) {
                result.remove(sumVb);
            }

        }
        return result;
    }

    /**
     * ��ȡָ��Ա�����ϣ���ĳʱ�������ڣ�����Ŀ���ܵļӰ��¼
     * @param users String
     * @param beginDate Date
     * @param endDate Date
     * @return List
     */
    private List getDataBeanList(String users, Date beginDate, Date endDate) {
        StringBuffer buf = new StringBuffer();
        //����SQL���
        buf.append(SQL_HEAD);

        //�����󽻼�
       if(beginDate != null){
           buf.append(" and to_char(o.actual_datetime_finish,'"+comDate.pattenDate+"')>='"
                      +comDate.dateToString(beginDate,comDate.pattenDate)+ "'");
       }
       if(endDate != null){
           buf.append(" and to_char(o.actual_datetime_start,'"+comDate.pattenDate+"')<='"
                      +comDate.dateToString(endDate,comDate.pattenDate)+ "'");
       }

       buf.append(" and o.login_id in (" + users + ")");

       //�ۺϺ�������
       buf.append(SQL_GROUP_BY);
       //����
       buf.append(SQL_ORDER_BY);

       List lst= this.getDbAccessor().executeQueryToList(buf.toString(),
               VbOverTimeReport.class);
       return lst;
    }

    /**
     * ��user loginId ��Listת�����ʺ�SQL��String
     * @param userList List
     * @return String
     */
    private String exchangUserList2Str(List userList) {
        StringBuffer userSb = new StringBuffer("''");
        Iterator rset = userList.iterator();
        while (rset.hasNext()) {
            String loginId = (String) rset.next();
            userSb.append(",");
            userSb.append("'");
            userSb.append(loginId);
            userSb.append("'");
        }
        return userSb.toString();
    }

    /**
     * ��ȡHR Account����������ΪFull TimeԱ����LoginId
     * @param acntRid Long
     * @return List
     * @throws BusinessException
     */
    public List getUserListInHrAcnt(Long acntRid) throws BusinessException {
        List userList = new ArrayList();
        String sql = "select login.login_id as loginid " +
                     "from upms_loginuser login " +
                     "left join essp_hr_employee_main_t hr on hr.code = login.user_id " +
                     "left join essp_upms_unit u on hr.dept = u.unit_id " +
                     "left join essp_hr_account_scope_t acntScope on hr.code = acntScope.SCOPE_CODE " +
                     "where acntScope.ACCOUNT_ID = " + acntRid.longValue() +
                     "and hr.types = '0' order by u.unit_code, lower(loginid)";

        try {
            RowSet rset = getDbAccessor().executeQuery(sql);
            while (rset.next()) {
                String loginId = rset.getString("loginid");
                userList.add(loginId);
            }
        } catch (Exception ex) {
            throw new BusinessException("E000", "Error when get user in account - " + acntRid, ex);
        }
        return userList;
    }
}
