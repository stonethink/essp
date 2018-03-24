package c2s.essp.tc.weeklyreport;

import java.math.BigDecimal;
import java.util.Date;

public interface IDtoAllocateHour {

    public String getUserId();

    public Date getBeginPeriod() ;

    public Date getEndPeriod() ;


    public BigDecimal getActualHour();

    public BigDecimal getActualHourConfirmed() ;

    public BigDecimal getLeaveSum() ;

    public BigDecimal getLeaveSumConfirmed() ;

    public BigDecimal getOvertimeSum() ;

    public BigDecimal getOvertimeSumConfirmed();

    public void setOvertimeSumConfirmed(BigDecimal overtimeSumConfirmed);

    public void setOvertimeSum(BigDecimal overtimeSum) ;

    public void setLeaveSumConfirmed(BigDecimal leaveSumConfirmed);

    public void setLeaveSum(BigDecimal leaveSum);

    public void setActualHourConfirmed(BigDecimal actualHourConfirmed);

    public void setActualHour(BigDecimal actualHour);

    public BigDecimal getAllocateHour();

    public BigDecimal getAllocateHourConfirmed();

}
