package server.essp.pms.account.labor.form;

import org.apache.struts.action.*;

public class AfPersonnelPlanManager extends ActionForm {

    private String ifAdd;//是否有新增操作
    private String tridAdd;//新增记录的在页面中的行号
    private String ifUpdate;//是否有更新操作
    private String ifDelete;
    private String tridUpdate;
    private String tridDelete;
    private String acntRid;
    private String loginId;
    private String resRid;
    public String getIfAdd() {

        return ifAdd;
    }

    public String getTridAdd() {

        return tridAdd;
    }

    public String getIfUpdate() {

        return ifUpdate;
    }

    public String getIfDelete() {

        return ifDelete;
    }

    public String getTridUpdate() {

        return tridUpdate;
    }

    public String getTridDelete() {

        return tridDelete;
    }

    public String getAcntRid() {

        return acntRid;
    }

    public String getLoginId() {
        return loginId;
    }

    public String getResRid() {

        return resRid;
    }

    public void setIfAdd(String ifAdd) {

        this.ifAdd = ifAdd;
    }

    public void setTridAdd(String tridAdd) {

        this.tridAdd = tridAdd;
    }

    public void setIfUpdate(String ifUpdate) {

        this.ifUpdate = ifUpdate;
    }

    public void setIfDelete(String ifDelete) {

        this.ifDelete = ifDelete;
    }

    public void setTridUpdate(String tridUpdate) {

        this.tridUpdate = tridUpdate;
    }

    public void setTridDelete(String tridDelete) {

        this.tridDelete = tridDelete;
    }

    public void setAcntRid(String acntRid) {

        this.acntRid = acntRid;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public void setResRid(String resRid) {

        this.resRid = resRid;
    }
}
