package client.essp.tc.common;

import java.util.Date;

public interface IPeriodModel {
    public void setPeriod(Date beginPeriod, Date endPeriod);

    public void refreshSumHours(int row);
}
