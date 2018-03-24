package server.essp.tc.mail.logic;

import server.framework.logic.AbstractBusinessLogic;
import java.sql.ResultSet;
import java.util.HashMap;
import itf.hr.LgHrUtilImpl;
import server.framework.common.BusinessException;
import itf.hr.HrFactory;

/**
 *�������ڸ�����ĿID���Ա�����û�����EMAIL��ַ
 *author:Robin.Zhang
 */
public class GetEmployeeList extends AbstractBusinessLogic {
    public GetEmployeeList() {
    }
    /**
     *�˷���������ĿIDȡ��Ա�����û�����EMAIL��ַ
     * ������accountId����ĿID
     * ���أ���HashMap����ʽ����
     */
    public HashMap getAllEmployee(String accountId) {
        ResultSet rs;
        String user;
        String email;
        LgHrUtilImpl ihui = (LgHrUtilImpl)HrFactory.create();
        HashMap allEmployee=new HashMap();
        try {
            String sql = "select t.login_id from "+LgHrUtilImpl.LOGIN_TABLE+" t,essp_hr_account_scope_t hr "
                         + "where t.user_id=hr.scope_code and hr.account_id=" + accountId;
            System.out.println(sql);

            rs = this.getDbAccessor().executeQuery(sql);

            while (rs.next()) {
                user = rs.getString("login_id");
                email = ihui.getUserEmail(user);
                allEmployee.put(user, email);
            }

        }catch(Throwable tx){
                   String msg = "error get all employee data!";
                   throw new BusinessException("",msg,tx);
       }
        return allEmployee;
    }

    public static void main(String[] args) {
        GetEmployeeList getemployeelist = new GetEmployeeList();
        System.out.println(getemployeelist.getAllEmployee("883"));

    }
}
