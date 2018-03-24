package server.essp.common.humanAllocate.form;

import javax.servlet.http.*;

import org.apache.struts.action.*;
import java.util.List;

public class AfUserAllocInAD extends ActionForm {
    private String loginId;
    private String empName;
    private String empMail;
    private String selectDomain;
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

    public void setEmpMail(String empMail) {
        this.empMail = empMail;
    }

    public void setSelectDomain(String selectDomain) {
        this.selectDomain = selectDomain;
    }

    public String getLoginId() {
        return loginId;
    }

    public String getEmpName() {
        return empName;
    }

    public String getEmpMail() {
        return empMail;
    }

    public String getSelectDomain() {
        return selectDomain;
    }
}
