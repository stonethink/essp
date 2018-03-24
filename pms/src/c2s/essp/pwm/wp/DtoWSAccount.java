package c2s.essp.pwm.wp;

import c2s.dto.DtoBase;
import java.util.List;
import java.util.ArrayList;
import com.wits.util.StringUtil;

public class DtoWSAccount extends DtoBase implements IDtoScope{
    Long acntRid;
    String acntCode;
    String acntName;

    List activityList = null;
    List wpList = null;

    public DtoWSAccount() {
        activityList = new ArrayList();
        wpList = new ArrayList();
    }

    public String getAcntCode() {
        return acntCode;
    }

    public String getAcntName() {
        return acntName;
    }

    public Long getAcntRid() {
        return acntRid;
    }


    public Long getActivityRid(){return null;}
    public String getActivityCode(){return null;}
    public String getActivityName(){return null;}

    public Long getWpRid(){return null;}
    public String getWpCode(){return null;}
    public String getWpName(){return null;}

    public List getActivityList() {
        return activityList;
    }

    public List getWpList() {
        return wpList;
    }

    public void setWpList(List wpList) {
        this.wpList = wpList;
    }

    public void setActivityList(List activityList) {
        this.activityList = activityList;
    }

    public void setAcntRid(Long acntRid) {
        this.acntRid = acntRid;
    }

    public void setAcntName(String acntName) {
        this.acntName = acntName;
    }

    public void setAcntCode(String acntCode) {
        this.acntCode = acntCode;
    }

    public String getScopeInfo(){
        if( this.acntCode != null ){
            return this.acntCode + " -- " + StringUtil.nvl(this.acntName);
        }else{
            return "";
        }
    }

    public String getDescription() {
        return this.acntName;
    }
}
