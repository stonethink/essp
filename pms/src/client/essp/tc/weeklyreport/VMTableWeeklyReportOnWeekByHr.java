package client.essp.tc.weeklyreport;

import client.essp.tc.common.TableListener;
import c2s.essp.tc.weeklyreport.DtoWeeklyReport;
import java.math.BigDecimal;
import c2s.essp.tc.weeklyreport.DtoHoursOnWeek;
import c2s.essp.tc.weeklyreport.DtoAllocateHour;

public class VMTableWeeklyReportOnWeekByHr extends VMTableWeeklyReport{
    DtoAllocateHour allocateHourOnWeek = new DtoAllocateHour();

    public VMTableWeeklyReportOnWeekByHr(Object[][] configs) {
        super(configs);

        SATURDAY_COLUMN_INDEX = 1;
        FRIDAY_COLUMN_INDEX = 7;
        SUMMARY_COLUMN_INDEX = 8;
        COMMENTS_COLUMN_INDEX = 9;
    }

    protected void reCaculateSumAll() {
        super.reCaculateSumAll();

        BigDecimal actualHourSum = new BigDecimal(0);
        BigDecimal actualHourSumConfirmed = new BigDecimal(0);
        for (int i = 0; i < getRowCount(); i++) {
            DtoWeeklyReport dto = (DtoWeeklyReport) getRow(i);
            if (dto != null) {
                BigDecimal sum = dto.getSumHour();

                actualHourSum = actualHourSum.add(sum);
                if(dto.isConfirmed() ){
                    actualHourSumConfirmed = actualHourSumConfirmed.add(sum);
                }
            }
        }

        allocateHourOnWeek.setActualHour(actualHourSum);
        allocateHourOnWeek.setActualHourConfirmed(actualHourSumConfirmed);
        notifyTableChanged(TableListener.EVENT_SUM_DATA_CHANGED);
    }

    public DtoAllocateHour getAllcoateHourOnWeek(){
        return this.allocateHourOnWeek;
    }
}
