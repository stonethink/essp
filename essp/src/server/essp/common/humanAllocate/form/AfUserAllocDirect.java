package server.essp.common.humanAllocate.form;

import javax.servlet.http.*;

import org.apache.struts.action.*;

public class AfUserAllocDirect extends ActionForm {
    private String loginId;
    private String empName;
    private String empCode;
    public ActionErrors validate(ActionMapping actionMapping,
                                 HttpServletRequest httpServletRequest) {
            /** @todo: finish this method, this is just the skeleton.*/
        return null;
    }

    public void reset(ActionMapping actionMapping,
                      HttpServletRequest servletRequest) {
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }

    public String getLoginId() {
        return loginId;
    }

    public String getEmpName() {
        return empName;
    }

    public String getEmpCode() {
        return empCode;
    }
}
