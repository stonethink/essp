package server.essp.pms.account.labor.viewbean;

import java.util.*;

import com.wits.util.*;


/**
 * 人员分配的每个时间区间
 * @author not attributable
 * @version 1.0
 */
public class VbResourcePlanDateItem {

    private Calendar beginDate;
    private Calendar endDate;
    private String rid;
    private String percent;
    private String seq;
    private String hour;
    private Calendar date2Calendar(Date d){
        Calendar cl = new GregorianCalendar();
        cl.setTime(d);
        return cl;
    }
    public Calendar getBeginDate() {
        return beginDate;
    }

    public Calendar getEndDate() {
        return endDate;
    }

    public String getPercent() {
        return percent;
    }

    public String getSeq() {
        return seq;
    }

    public String getHour() {
        return hour;
    }

    public String getRid() {
        return rid;
    }

    public void setBeginDate(Date begindate) {

        this.beginDate = date2Calendar(begindate);
    }

    public void setEndDate(Date enddate) {
        this.endDate = date2Calendar(enddate);
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public String getBeginDateStr(){
        return  comDate.dateToString(beginDate.getTime(),"yyyy-MM-dd");
    }
    public String getEndDateStr(){
        return comDate.dateToString(endDate.getTime(),"yyyy-MM-dd");
    }

}
