package c2s.essp.tc.weeklyreport;

import java.util.Date;

import c2s.dto.DtoBase;
import com.wits.util.comDate;

public class DtoTcSearchResult extends DtoBase{
    private Long acntRid;
    private String acntName;
    private String manager;
    private String user;

    private Date beginPeriod;
    private Date endPeriod;

    private String status;
    private String selected;

    public void setUser(String user) {
        this.user = user;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setAcntRid(Long acntRid) {
        this.acntRid = acntRid;
    }

    public void setAcntName(String acntName) {
        this.acntName = acntName;
    }

    public void setSelected(String selected) {
        this.selected = selected;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public void setBeginPeriod(Date beginPeriod) {
        this.beginPeriod = beginPeriod;
        if( beginPeriod.getMinutes() != 0 ){
            System.out.println( beginPeriod.getMinutes() );
        }
    }

    public void setEndPeriod(Date endPeriod) {
        this.endPeriod = endPeriod;
        if( endPeriod.getMinutes() != 0 ){
            System.out.println( endPeriod.getMinutes() );
        }
    }

    public String getUser() {
        return user;
    }


    public String getStatus() {
        return status;
    }

    public String getAcntName() {
        return acntName;
    }

    public Long getAcntRid() {
        return acntRid;
    }


    public String getPeriod(){
        String str = null;
        if( beginPeriod != null && endPeriod != null ){
            str = comDate.dateToString(beginPeriod) + " ~ "
                         + comDate.dateToString(endPeriod);
        }

        return str;
    }

    public String getSelected() {
        return selected;
    }

    public String getManager() {
        return manager;
    }

    public Date getBeginPeriod() {
        return beginPeriod;
    }

    public Date getEndPeriod() {
        return endPeriod;
    }

}
