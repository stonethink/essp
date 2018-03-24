package client.essp.tc.weeklyreport;

public class VMTableWeeklyReportOnMonth extends VMTableWeeklyReport{

    public VMTableWeeklyReportOnMonth(Object[][] configs) {
        super(configs);

        SATURDAY_COLUMN_INDEX = 4;
        FRIDAY_COLUMN_INDEX = 10;
        SUMMARY_COLUMN_INDEX = 11;
        COMMENTS_COLUMN_INDEX = 12;
    }
}
