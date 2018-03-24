package c2s.essp.timesheet.report;

import java.util.Date;
import c2s.dto.DtoBase;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public class DtoUtilRateQuery extends DtoBase{
   private String loginId;
   private String acntId;
   private Long acntRid;
   private Date begin;
   private Date end;
   private String deptIds;
   private String loginIds;
   private Boolean isSub = false;

   public Boolean getIsSub() {
    return isSub;
}

public void setIsSub(Boolean isSub) {
    this.isSub = isSub;
}

public String getAcntId() {
        return acntId;
    }

    public Long getAcntRid() {
        return acntRid;
    }

    public Date getBegin() {
        return begin;
    }

    public Date getEnd() {
        return end;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setAcntId(String acntId) {
        this.acntId = acntId;
    }

    public void setAcntRid(Long acntRid) {
        this.acntRid = acntRid;
    }

    public void setBegin(Date begin) {
        this.begin = begin;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getDeptIds() {
        return deptIds;
    }

    public void setDeptIds(String deptIds) {
        this.deptIds = deptIds;
    }

    public String getLoginIds() {
        return loginIds;
    }

    public void setLoginIds(String loginIds) {
        this.loginIds = loginIds;
    }
}
