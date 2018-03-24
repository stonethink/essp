package server.essp.tc.auto;

import c2s.essp.common.calendar.WrokCalendarFactory;
import java.util.Calendar;
import c2s.essp.common.calendar.WorkCalendar;
import com.wits.util.comDate;
import server.essp.tc.weeklyreport.logic.WeeklyReportStatusExporter;
import com.wits.util.Parameter;
import com.wits.util.Config;
import com.wits.util.PathGetter;
import server.essp.common.mail.LgMailSend;
import java.util.HashMap;
import java.io.File;
import org.apache.log4j.Category;
import c2s.essp.tc.weeklyreport.DtoTcKey;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class AutoSendWeeklyReportStatus {
    private static Category log            = Category.getInstance("server");
    public static String    PERIOD_TYPE_WEEK  = "week";
    public static String    PERIOD_TYPE_MONTH = "month";
    public static String    DATE_STYLE        = comDate.pattenDate;
    private static String   WRS_TOMAIL        = "WRS_ToMail";
    private static String   WRS_CCMAIL        = "WRS_CcMail";
    private static String   OUT_DIR           = "essp/excelReport/";
    private static String   MAIL_CONTENT      = "Dear,All:\r\n\tPleace check and accept the attachment!";

    private Long            hrAcntRid         = null;
    private Calendar        today             = Calendar.getInstance();//current date
    private WorkCalendar    wc                = WrokCalendarFactory.serverCreate();
    private Calendar[]      period            = null;//work week period
    private String          fileName          = WeeklyReportStatusExporter.DEFAULT_FILE_NAME;
    private String          absFilePath       = null;
    private Config          attendManagerCfg  = new Config("manager");

    /**
     * constructor
     * @param hrAcntRid Long
     */
    public AutoSendWeeklyReportStatus(Long hrAcntRid) {
        this(hrAcntRid, PERIOD_TYPE_MONTH, 0);
    }

    /**
     * constructor
     * @param hrAcntRid Long
     * @param type String
     * @param pOffset int
     */
    public AutoSendWeeklyReportStatus(Long hrAcntRid, String type, int pOffset) {
        this.hrAcntRid = hrAcntRid;
        Calendar[] ca;
        //get work week period
        if(PERIOD_TYPE_MONTH.equals(type)) {
            ca = wc.getNextBEMonthDay(today, pOffset);
        } else {
            ca = wc.getNextBEWeekDay(today, pOffset);
        }
        period = new Calendar[2];
        formatPeriod(ca[0], ca[1]);
    }

    /**
     * constructor
     * @param hrAcntRid Long
     * @param weekBegin Calendar
     * @param weekEnd Calendar
     */
    public AutoSendWeeklyReportStatus(Long hrAcntRid,
                                   Calendar weekBegin, Calendar weekEnd) {
        this.hrAcntRid = hrAcntRid;
        period = new Calendar[2];
        formatPeriod(weekBegin, weekEnd);
     }

     /**
      * generate and send weekly report status report
      */
     public void execute() {
         if(generateStatusReport()) {
             sendIt();
         }
     }

     /**
      * generate report
      * @return boolean
      */
     private boolean generateStatusReport() {
        WeeklyReportStatusExporter exporter = new WeeklyReportStatusExporter(OUT_DIR, fileName);
        Parameter param = new Parameter();
        param.put(DtoTcKey.ACNT_RID, hrAcntRid);
        param.put(DtoTcKey.BEGIN_PERIOD, period[0].getTime());
        param.put(DtoTcKey.END_PERIOD, period[1].getTime());

        try {
            absFilePath = exporter.scheduleExport(param);
            return true;
        } catch (Exception ex) {
            log.error("Excle Export Error", ex);
            return false;
        }
     }

     /**
      * send report
      */
     private void sendIt() {
        LgMailSend lgSend = new LgMailSend();
        lgSend.setToAddress(this.getTorMail());
        lgSend.setCcAddress(this.getCcMails());
        lgSend.setFrom("essp");
        lgSend.setTitle(fileName.replaceAll(".xls", ""));
        HashMap hs = new HashMap();
        File f = new File(absFilePath);
        hs.put(fileName, f);
        lgSend.setAttachmentFiles(hs);
        lgSend.setContent(MAIL_CONTENT);
        try {
            lgSend.send();
        } catch (Exception e) {
            log.error("Send Mail Error", e);
        }
     }

     /**
      * get send to mail address
      * @return String
      */
     protected String getTorMail() {
        return attendManagerCfg.getValue(WRS_TOMAIL);
     }

     /**
      * get cc mail address
      * @return String
      */
     protected String getCcMails() {
         return attendManagerCfg.getValue(WRS_CCMAIL);
     }

     /**
      * Format period begin and end date, for the largest period.
      * @param weekBegin Calendar
      * @param weekEnd Calendar
      */
     private void formatPeriod(Calendar weekBegin, Calendar weekEnd) {
        period[0] = weekBegin;
        period[1] = weekEnd;

        period[0].set(Calendar.HOUR_OF_DAY, 0);
        period[0].set(Calendar.MINUTE, 0);
        period[0].set(Calendar.SECOND, 0);
        period[0].set(Calendar.MILLISECOND, 0);
        period[1].set(Calendar.HOUR_OF_DAY, 23);
        period[1].set(Calendar.MINUTE, 59);
        period[1].set(Calendar.SECOND, 59);
        period[1].set(Calendar.MILLISECOND, 999);
        String beginStr = comDate.dateToString(period[0].getTime());
        String endStr = comDate.dateToString(period[1].getTime());
        fileName = fileName.replaceAll(".xls",beginStr + "~" + endStr + ".xls");
    }

    public static void main(String[] args) {
        String helpStr = "1 parameter is required:\r\n\t" +
                         "HR account rid (Long)\r\n" +
                         "2 parameters are optional:\r\n\t" +
                         "1. process period offset (Integer, default:0)\r\n\t" +
                         "2. period type (week/month, default:week)\r\n";

        Long hrAcntRid = new Long(883);
        String periodType = PERIOD_TYPE_WEEK;
        int offset = 0;

        if(args.length < 1) {
            helpStr += "\r\n Please input HR account rid (Long)!";
            System.out.println(helpStr);
            return;
        } else {
            try {
                hrAcntRid = Long.valueOf(args[0]);
            } catch(NumberFormatException ex) {
                helpStr += "\r\n Please input HR account rid (Long) correctly!";
                System.out.println(helpStr);
            }
        }
        for(int i = 1; i < args.length; i++) {
            try {
                int off = Integer.parseInt(args[i]);
                offset = off;
            } catch(NumberFormatException ex) {
            }
            if(PERIOD_TYPE_MONTH.equalsIgnoreCase(args[i])) {
                periodType = PERIOD_TYPE_MONTH;
            }

        }
        AutoSendWeeklyReportStatus ap = new AutoSendWeeklyReportStatus(hrAcntRid, periodType, offset);
        try {
            ap.execute();
            System.out.println("Execute over!");
        } catch (Exception e) {
            log.error("Main Error", e);
        }

    }
}
