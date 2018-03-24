package c2s.essp.tc.weeklyreport;


public class DtoWeeklyReportSumOnMonthByDm extends DtoAllocateHour implements IDtoAllocateHour{
    public static final String SUMMARY_TITLE = "Actual Hours";

    private String jobCode;

    public String getJobCode() {
        return jobCode;
    }

    public void setJobCode(String jobCode) {
        this.jobCode = jobCode;
    }


}
