package c2s.essp.tc.weeklyreport;

import java.math.BigDecimal;

public interface IDtoAllocateHourIncLeaveDetail extends IDtoAllocateHour{

    public void setLeaveOfPayAll(BigDecimal leaveOfPayAll);

    public void setLeaveOfPayHalf(BigDecimal leaveOfPayHalf) ;

    public void setLeaveOfPayNone(BigDecimal leaveOfPayNone);

    public void setLeaveOfPayNoneConfirmed(BigDecimal leaveOfPayNoneConfirmed) ;

    public void setLeaveOfPayHalfConfirmed(BigDecimal leaveOfPayHalfConfirmed) ;

    public void setLeaveOfPayAllConfirmed(BigDecimal leaveOfPayAllConfirmed);


    public BigDecimal getLeaveOfPayAll();

    public BigDecimal getLeaveOfPayHalf();

    public BigDecimal getLeaveOfPayNone();

    public BigDecimal getLeaveOfPayAllConfirmed();

    public BigDecimal getLeaveOfPayHalfConfirmed();

    public BigDecimal getLeaveOfPayNoneConfirmed();
}

