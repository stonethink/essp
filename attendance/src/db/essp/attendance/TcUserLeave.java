package db.essp.attendance;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import java.util.Iterator;
import server.framework.common.BusinessException;
import java.util.TreeSet;
import java.text.DecimalFormat;
import java.math.BigDecimal;

/** @author Hibernate CodeGenerator */
public class TcUserLeave implements Serializable {

    /** identifier field */
    private db.essp.attendance.TcUserLeavePK comp_id;

    /** nullable persistent field */
    private Long rid;

    /** nullable persistent field */
    private Double canLeaveHours;

    /** nullable persistent field */
    private Double useLeaveHours;

    /** nullable persistent field */
    private Double payedHours;

    /** nullable persistent field */
    private String rst;

    /** nullable persistent field */
    private Date rct;

    /** nullable persistent field */
    private Date rut;

    /** persistent field */
    private Set tcUserLeaveDetails;

    /** default constructor */
    public TcUserLeave() {
    }

    public db.essp.attendance.TcUserLeavePK getComp_id() {
        return this.comp_id;
    }

    public void setComp_id(db.essp.attendance.TcUserLeavePK comp_id) {
        this.comp_id = comp_id;
    }

    public Long getRid() {
        return this.rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public void setPayedHours(Double payedHours) {
        this.payedHours = payedHours;
    }

    public String getRst() {
        return this.rst;
    }

    public void setRst(String rst) {
        this.rst = rst;
    }

    public Date getRct() {
        return this.rct;
    }

    public void setRct(Date rct) {
        this.rct = rct;
    }

    public Date getRut() {
        return this.rut;
    }

    public void setRut(Date rut) {
        this.rut = rut;
    }

    public Set getTcUserLeaveDetails() {
        return this.tcUserLeaveDetails;
    }
    public void setTcUserLeaveDetails(Set tcUserLeaveDetails) {
        this.tcUserLeaveDetails = tcUserLeaveDetails;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("comp_id", getComp_id())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof TcUserLeave) ) return false;
        TcUserLeave castOther = (TcUserLeave) other;
        return new EqualsBuilder()
            .append(this.getComp_id(), castOther.getComp_id())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getComp_id())
            .toHashCode();
    }
    public void setCanLeaveHours(Double canLeaveHours) {
        this.canLeaveHours = canLeaveHours;
    }
    public void setUseLeaveHours(Double useLeaveHours) {
        this.useLeaveHours = useLeaveHours;
    }
    //若存在每年明细,则汇总明细的时间,否则自己的值
    public Double getCanLeaveHours() {
        if(tcUserLeaveDetails != null && tcUserLeaveDetails.size() != 0){
            double sum = 0D;
            Iterator it = tcUserLeaveDetails.iterator();
            while(it.hasNext()){
                TcUserLeaveDetail detail = (TcUserLeaveDetail) it.next();
                sum += detail.getCanLeaveHours().doubleValue();
            }
            return new Double(sum);
        }else{
            return (canLeaveHours == null ? new Double(0) : canLeaveHours);
        }
    }
    //同CanLeaveday
    public Double getUseLeaveHours() {
        if(tcUserLeaveDetails != null && tcUserLeaveDetails.size() != 0){
            double sum = 0D;
            Iterator it = tcUserLeaveDetails.iterator();
            while(it.hasNext()){
                TcUserLeaveDetail detail = (TcUserLeaveDetail) it.next();
                sum += detail.getUseLeaveHours().doubleValue();
            }
            return new Double(sum);
        }else{
            return (useLeaveHours == null ? new Double(0) : useLeaveHours);
        }
    }
    //同CanLeaveday
    public Double getPayedHours() {
        if(tcUserLeaveDetails != null && tcUserLeaveDetails.size() != 0){
            double sum = 0D;
            Iterator it = tcUserLeaveDetails.iterator();
            while(it.hasNext()){
                TcUserLeaveDetail detail = (TcUserLeaveDetail) it.next();
                sum += detail.getPayedHours().doubleValue();
            }
            return new Double(sum);
        }else{
            return (payedHours == null ? new Double(0) : payedHours);
        }
    }
    //查找人员某年该假别休假状况的明细
    public TcUserLeaveDetail getUserYearLeave(int year){
        if(this.tcUserLeaveDetails == null || this.tcUserLeaveDetails.size() ==0)
            return null;
        Iterator it = tcUserLeaveDetails.iterator();
        while(it.hasNext()){
            TcUserLeaveDetail detail = (TcUserLeaveDetail) it.next();
            if(detail.getComp_id()!= null){
                Long detailYear = detail.getComp_id().getYearth();
                if(detailYear != null && detailYear.intValue() == year){
                    return detail;
                }
            }
        }
        return null;
    }
    //总的可用时间=总可休时间+总的已用时间
    public Double getUsableHours(){
        double usable = getCanLeaveHours().doubleValue() - getUsedHours().doubleValue();
        BigDecimal big = new BigDecimal(usable).setScale(2,BigDecimal.ROUND_HALF_UP);
        return new Double(big.doubleValue());
    }
    //总的已用时间=总已休时间+总已支付时间
    public Double getUsedHours(){
        double used = getUseLeaveHours().doubleValue() + getPayedHours().doubleValue();
        BigDecimal big = new BigDecimal(used).setScale(2,BigDecimal.ROUND_HALF_UP);
        return new Double(big.doubleValue());
    }
    /**
     * 增加休假的小时数,提交请假申请时调用
     * 1.不存在明细的则从累加到汇总值的已休时间.
     * 2.将每年休假明细按可用时间从小到大遍历
     * 3.若该年可休时间不为0且大于等于要休假的小时数,则累加小时数到该年的已休时间,退出
     * 4.若该年可休时间不为0但小于于等于要增加的小时数,累加该年的可休时间到已休时间,继续下一次循环
     * @param hours double
     */
    public double addUseLeaveHours(double hours){
//        if(hours > getUsableHours().doubleValue())
//            throw new BusinessException("TC_LV_0009","There is not enough hours!");
        double allUsable = getUsableHours().doubleValue();
        double addHours = hours < allUsable ? hours : allUsable;
        double residualHours = hours - addHours;

        if(tcUserLeaveDetails == null || tcUserLeaveDetails.size() == 0){
            double useLeave = getUseLeaveHours().doubleValue() + addHours;
            setUseLeaveHours(new Double(useLeave));
            return  residualHours;
        }
        TreeSet sorted = new TreeSet(EnableSorter.getInstance());
        sorted.addAll(tcUserLeaveDetails);
        Iterator it = sorted.iterator();
        while(it.hasNext()){
            TcUserLeaveDetail detail = (TcUserLeaveDetail) it.next();
            double usable = detail.getUsableHours().doubleValue();
            if(usable > 0){
                if(addHours <= usable){
                    detail.addUseLeaveHours(addHours);
                    break;
                }else{
                    addHours -= usable;
                    detail.addUseLeaveHours(usable);
                    continue;
                }
            }
        }
        return residualHours;
    }
    /**
     *返回休假的小时数,拒绝请假申请时调用
     * @param hours double
     */
    public void returnUseLeaveHours(double hours){
        if(hours > getUseLeaveHours().doubleValue())
            throw new BusinessException("TC_LV_0009","There is not enough hours to return!");
        TreeSet sorted = new TreeSet(UseLeaveSorter.getInstance());
        sorted.addAll(tcUserLeaveDetails);
        Iterator it = sorted.iterator();
        while(it.hasNext()){
            TcUserLeaveDetail detail = (TcUserLeaveDetail) it.next();
            double useLeave = detail.getUseLeaveHours().doubleValue();
            if(useLeave > 0){
                if (useLeave >= hours) {
                    detail.returnUseLeaveHours(hours);
                    return;
                }else{
                    hours -= useLeave;
                    detail.returnUseLeaveHours(useLeave);
                }
            }
        }
    }

    /**
     * 增加付钱的小时数,提交请假申请时调用
     * 同addUseLeaveHours
     * @param hours double
     */
    public double addPayedHours(double hours){
//        if(hours > getUsableHours().doubleValue())
//            throw new BusinessException("TC_LV_0009","There is not enough hours!");
        if(tcUserLeaveDetails == null || tcUserLeaveDetails.size() == 0){
            double addHours = hours;
            if(hours < getUsableHours().doubleValue()) {
                addHours = getUsableHours().doubleValue();
            }
            double payed = getPayedHours().doubleValue() + addHours;
            setPayedHours(new Double(payed));
            hours -= addHours;
            return hours;
        }
        TreeSet sorted = new TreeSet(EnableSorter.getInstance());
        sorted.addAll(tcUserLeaveDetails);
        Iterator it = sorted.iterator();
        while(it.hasNext()){
            TcUserLeaveDetail detail = (TcUserLeaveDetail) it.next();
            double usable = detail.getUsableHours().doubleValue();
            if(usable > 0){
                if(hours <= usable){
                    detail.addPayedeHours(hours);
                    hours = 0;
                    return hours;
                }else{
                    hours -= usable;
                    detail.addPayedeHours(usable);
                    continue;
                }
            }
        }
         return hours;
    }
    /**
     *返回付钱的小时数,拒绝请假申请时调用
     * @param hours double
     */
    public void returnPayedHours(double hours){
        if(hours > getPayedHours().doubleValue())
            throw new BusinessException("TC_LV_0009","There is not enough hours to return!");
        TreeSet sorted = new TreeSet(UseLeaveSorter.getInstance());
        sorted.addAll(tcUserLeaveDetails);
        Iterator it = sorted.iterator();
        while(it.hasNext()){
            TcUserLeaveDetail detail = (TcUserLeaveDetail) it.next();
            double useLeave = detail.getPayedHours().doubleValue();
            if(useLeave > 0){
                if (useLeave >= hours) {
                    detail.returnPayedHours(hours);
                    return;
                }else{
                    hours -= useLeave;
                    detail.returnPayedHours(useLeave);
                }
            }
        }
    }

    //对休假每年明细按可用天数排序
    static class EnableSorter implements java.util.Comparator{
        private static EnableSorter instance;
        public static EnableSorter getInstance(){
            if(instance == null)
                instance = new EnableSorter();
            return instance;
        }
        public boolean equals(Object obj) {
            return false;
        }
        public int compare(Object o1, Object o2) {
            if(o1 instanceof TcUserLeaveDetail && o2 instanceof TcUserLeaveDetail){
                TcUserLeaveDetail leave1 = (TcUserLeaveDetail) o1;
                TcUserLeaveDetail leave2 = (TcUserLeaveDetail) o2;
                return (int)(leave1.getUsableHours().doubleValue() -
                             leave2.getUsableHours().doubleValue() );
            }
            return 0;
        }
    }
    //对休假每年明细按已休天数排序
    static class UseLeaveSorter implements java.util.Comparator{
        private static UseLeaveSorter instance;
        public static UseLeaveSorter getInstance(){
            if(instance == null)
                instance = new UseLeaveSorter();
            return instance;
        }
        public boolean equals(Object obj) {
            return false;
        }
        public int compare(Object o1, Object o2) {
            if(o1 instanceof TcUserLeaveDetail && o2 instanceof TcUserLeaveDetail){
                TcUserLeaveDetail leave1 = (TcUserLeaveDetail) o1;
                TcUserLeaveDetail leave2 = (TcUserLeaveDetail) o2;
                return (int)(leave1.getUseLeaveHours().doubleValue() -
                             leave2.getUseLeaveHours().doubleValue() );
            }
            return 0;
        }
    }
}
