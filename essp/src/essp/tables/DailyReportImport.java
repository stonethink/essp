package essp.tables;

import java.lang.reflect.Field;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.HashMap;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: Wistron ITS Wuhan SDC</p>
 *
 * @author not attributable
 * @version 1.0
 */
public class DailyReportImport {
  private static String driverFrom = "oracle.jdbc.driver.OracleDriver";
  private static String urlFrom = "jdbc:oracle:thin:@10.5.2.251:1521:essp";
  private static String userFrom = "essp";
  private static String passwordFrom = "essp";
  private static String driverTo = "oracle.jdbc.driver.OracleDriver";
  private static String urlTo = "jdbc:oracle:thin:@10.5.2.56:1521:esspfw";
  private static String userTo = "esspfw";
  private static String passwordTo = "esspfw";

  public static void main(String[] args) throws Exception {
    Connection connFrom = null;
    Connection connTo = null;

    try {
      Class.forName(driverFrom);
      connFrom = DriverManager.getConnection(urlFrom, userFrom,
                                             passwordFrom);
      connTo = DriverManager.getConnection(urlTo, userTo, passwordTo);
      connTo.setAutoCommit(false);

      long maxRid = getMaxRID(connTo);

      String sql = "select * from ESSP_PSP_DAILYREPORT_T order by WPID,OWNERID,PLANITEMID";
      Statement stmt = connFrom.createStatement();
      ResultSet rs = stmt.executeQuery(sql);

      while (rs.next()) {
        maxRid++;
        ESSP_PSP_DAILYREPORT_T oldDailyReport = new ESSP_PSP_DAILYREPORT_T();
        DataPrepare.selectData(rs, oldDailyReport);
        PW_WKITEM newDailyReport=new PW_WKITEM();
        newDailyReport.RID=maxRid+"";
        newDailyReport.PROJECT_ID=getProjectId(connFrom,oldDailyReport.WPID);
        newDailyReport.WP_ID=oldDailyReport.WPID;
        newDailyReport.WKITEM_OWNER=oldDailyReport.OWNERID;
        newDailyReport.WKITEM_PLACE=oldDailyReport.PLACE;
        newDailyReport.WKITEM_BELONGT0="";
        newDailyReport.WKITEM_NAME=oldDailyReport.TITLE;
        newDailyReport.WKITEM_DATE=oldDailyReport.PLANDATE;
        newDailyReport.WKITEM_STARTTIME=oldDailyReport.BEGINTIME;
        newDailyReport.WKITEM_FINISHTIME=oldDailyReport.ENDTIME;
        newDailyReport.WKITEM_WKHOURS=String.valueOf((oldDailyReport.ENDTIME.getTime()-
            oldDailyReport.BEGINTIME.getTime())/(1000*60*60));
        newDailyReport.WKITEM_ISDLRPT="1";

      }
      rs.close();
      stmt.close();
      connTo.commit();

    } catch (Exception ex) {
      ex.printStackTrace();

      try {
        if (connFrom != null) {
          connFrom.rollback();
        }

        if (connTo != null) {
          connTo.rollback();
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
    } finally {
      try {
        if (connFrom != null) {
          connFrom.close();
        }

        if (connTo != null) {
          connTo.close();
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }

  }

  private static long getMaxRID(Connection conn) throws SQLException {
    long maxRid = 1;

    try {
      String sql = "select max(RID) as MAX_RID from PW_WKITEM";
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery(sql);

      if (rs.next()) {
        maxRid = rs.getLong("MAX_RID");
      }

      rs.close();
      stmt.close();
    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    }

    return maxRid;
  }

  private static String getProjectId(Connection conn, String wpId) throws Exception {
    String projectId = "";
    try {
      String sql = "select ACCOUNTID from ESSP_PWP_PWPITEM_T where PWPID='" + wpId + "'";
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery(sql);

      if (rs.next()) {
        projectId = rs.getString("ACCOUNTID");
      }

      rs.close();
      stmt.close();
    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    }
    return projectId;
  }
}

class PW_WKITEM {
  public String RID = "";
  public String PROJECT_ID = "";
  public String WP_ID = "";
  public String WKITEM_OWNER = "";
  public String WKITEM_PLACE = "";
  public String WKITEM_BELONGT0 = "";
  public String WKITEM_NAME = "";
  public java.util.Date WKITEM_DATE = null;
  public java.sql.Timestamp WKITEM_STARTTIME = null;
  public java.sql.Timestamp WKITEM_FINISHTIME = null;
  public String WKITEM_WKHOURS = "";
  public String WKITEM_ISDLRPT = "";
  public String WKITEM_COPYFROM = "";
  public String RST = "";
  public String RCT = "";
  public String RUT = "";
}

class ESSP_PSP_DAILYREPORT_T {
  public java.util.Date PLANDATE = null;
  public java.sql.Timestamp BEGINTIME = null;
  public java.sql.Timestamp ENDTIME = null;
  public String PLACE = "";
  public String TITLE = "";
  public String WPID = "";
  public String OWNERID = "";
}
