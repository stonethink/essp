package client.essp.tc.hrmanager;

import java.util.Date;

public interface ITimecardExcelParameter {
    public Long getAcntRid();
    public Date getBeginPeriod();
    public Date getEndPeriod();
    public String getType();
}
