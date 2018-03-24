package client.essp.timesheet.approval;

import c2s.essp.timesheet.weeklyreport.DtoTimeSheetPeriod;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author wenhaizheng
 * @version 1.0
 */
public interface TsPeriodChangeListener {

    public void performePeriodChanged(DtoTimeSheetPeriod period);

}
