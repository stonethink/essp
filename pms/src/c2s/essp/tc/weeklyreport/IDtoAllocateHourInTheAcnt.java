package c2s.essp.tc.weeklyreport;

import java.math.BigDecimal;

public interface IDtoAllocateHourInTheAcnt extends IDtoAllocateHour{
    public Long getAcntRid();


    public BigDecimal getActualHourConfirmedInTheAcnt() ;

    public BigDecimal getActualHourInTheAcnt() ;

    public BigDecimal getOvertimeSumConfirmedInTheAcnt();

    public BigDecimal getOvertimeSumInTheAcnt() ;

    public BigDecimal getSumHour();

    public void setOvertimeSumConfirmedInTheAcnt(BigDecimal hour);

    public void setOvertimeSumInTheAcnt(BigDecimal hour) ;

    public void setSumHour(BigDecimal sumHour);

    public void setActualHourInTheAcnt(BigDecimal actualHourInTheAcnt);

    public void setActualHourConfirmedInTheAcnt(BigDecimal actualHourConfirmedInTheAcnt) ;

}
