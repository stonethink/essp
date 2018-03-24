package server.essp.attendance.overtime.form;

import org.apache.struts.action.*;

public class AfOverTimeSearch extends ActionForm {
    private String depart_code;
    private String account_id;
    private String beginDate;
    private String endDate;
    private String empName;
    public AfOverTimeSearch() {
    }

    public void setDepart_code(String depart_code) {

        this.depart_code = depart_code;
    }

    public void setAccount_id(String account_id) {

        this.account_id = account_id;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setEmpName(String empName) {

        this.empName = empName;
    }

    public String getDepart_code() {

        return depart_code;
    }

    public String getAccount_id() {

        return account_id;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getEmpName() {

        return empName;
    }
}
