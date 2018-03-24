package c2s.essp.pwm.wp;

import java.util.List;
import java.util.Collections;
import com.wits.util.StringUtil;

public class DtoWSActivity extends DtoWSAccount {
    Long activityRid;
    String activityCode;
    String activityName;

    public List getActivityList() {
        return null;
    }

    public List getWpList() {
        return Collections.EMPTY_LIST;
    }

    public String getActivityCode() {
        return activityCode;
    }

    public String getActivityName() {
        return activityName;
    }

    public Long getActivityRid() {
        return activityRid;
    }

    public void setActivityCode(String activityCode) {
        this.activityCode = activityCode;
    }

    public void setActivityRid(Long activityRid) {
        this.activityRid = activityRid;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getScopeInfo(){
        if( this.activityCode != null ){
            return this.activityCode + " -- " + StringUtil.nvl(this.activityName);
        }else{
            return "";
        }
    }

    public String getDescription() {
        return this.activityName;
    }

}
