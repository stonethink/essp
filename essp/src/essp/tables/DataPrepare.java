package essp.tables;

import java.lang.reflect.Field;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.HashMap;


class SQLUtil {
    private HashMap    map  = new HashMap();
    private String     sql  = "";
    private Connection conn;

    public SQLUtil(Connection conn,
                   String     sql) {
        this.conn = conn;
        this.sql  = sql;
    }

    public void setString(int    index,
                          String value) {
        map.put(String.valueOf(index), value);

        //System.out.println("put("+index+","+value+")");
    }

    public void setNumber(int    index,
                          Number value) {
        map.put(String.valueOf(index), value);

        //System.out.println("put("+index+","+value.toString()+")");
    }

    public void setDate(int            index,
                        java.util.Date date) {
        java.util.Date newDate = null;

        if (date instanceof java.sql.Date) {
            newDate = date;
        } else {
            newDate = new java.sql.Date(date.getTime());
        }

        map.put(String.valueOf(index), newDate);

        // System.out.println("put("+index+","+newDate.toString()+")");
    }

    public void executeUpdate() throws Exception {
        PreparedStatement stmt = conn.prepareStatement(sql);

        for (int i = 1; i <= map.size(); i++) {
            Object value = map.get(String.valueOf(i));

            if (value instanceof String) {
                stmt.setString(i, (String) value);
            } else if (value instanceof Long) {
                stmt.setLong(i, ((Long) value).longValue());
            } else if (value instanceof Integer) {
                stmt.setInt(i, ((Integer) value).intValue());
            } else if (value instanceof Float) {
                stmt.setFloat(i, ((Float) value).floatValue());
            } else if (value instanceof Double) {
                stmt.setDouble(i, ((Double) value).doubleValue());
            } else if (value instanceof Boolean) {
                stmt.setBoolean(i, ((Boolean) value).booleanValue());
            } else if (value instanceof java.math.BigDecimal) {
                stmt.setBigDecimal(i, (java.math.BigDecimal) value);
            } else if (value instanceof java.math.BigInteger) {
                stmt.setBigDecimal(i, (java.math.BigDecimal) value);
            } else if (value instanceof java.sql.Date) {
                java.sql.Date utilDate = (java.sql.Date) value;
                stmt.setDate(i, utilDate);
            } else {
                System.out.println("UNKNOWN TYPE:" + value.getClass().getName());
                throw new Exception("UNKNOWN TYPE:"
                                    + value.getClass().getName());
            }
        }

        stmt.execute();
        stmt.close();
    }
}


class ESSP_PWP_PWPITEM_T {
    public String         PWPID                     = "";
    public String         PWPNAME                   = "";
    public String         PWPTYPE                   = "";
    public java.util.Date REQUIREDSTART             = null;
    public java.util.Date REQUIREDFINISH            = null;
    public java.util.Date ACTUALSTART               = null;
    public java.util.Date ACTUALFINISH              = null;
    public String         REQIREMENT                = "";
    public String         WORKERID                  = "";
    public String         COMPLETERATE              = "";
    public String         PWPSTATUS                 = "";
    public String         ACTIVITYID                = "";
    public String         ACCOUNTID                 = "";
    public String         REQUIREDWORKHOURS         = "";
    public String         PLANNEDWORKHOURS          = "";
    public String         ACTUALWORKHOURS           = "";
    public String         REQUIREDSIZE              = "";
    public String         PLANNEDSIZE               = "";
    public String         ACTUALSIZE                = "";
    public String         REQUIREDDEFECTS           = "";
    public String         PLANNEDDEFECTS            = "";
    public String         ACTUALDEFECTS             = "";
    public String         CLOSEDDEFECTS             = "";
    public String         ORGANIZATIONALDEFECTS     = "";
    public String         ELIGIBLEDEFECTS           = "";
    public String         DELIVERYSCORE             = "";
    public String         TIMESCORE                 = "";
    public String         DEFECTSCORE               = "";
    public String         COSTSCORE                 = "";
    public String         PERFORMANCESCORE          = "";
    public String         PWPCOMMENT                = "";
    public String         DELIVERYPOINT             = "";
    public String         TIMEPOINT                 = "";
    public String         RESERVEDFIELD             = "";
    public String         MYSELFBROKENHOURS         = "";
    public String         ORGANIZATIONALBROKENHOURS = "";
    public String         OTHERBROKENHOURS          = "";
    public String         WEIGHT                    = "";
    public String         DEFECTDENSITY             = "";
    public String         MUNITID                   = "";
}


class PW_WP {
    public String         RID               = "";
    public String         PROJECT_ID        = "";
    public String         ACTIVITY_ID       = "";
    public String         WP_SEQUENCE       = "";
    public String         WP_PWPORWP        = "";
    public String         WP_CODE           = "";
    public String         WP_NAME           = "";
    public String         WP_TYPE           = "";
    public String         WP_ASSIGNBY       = "";
    public java.util.Date  WP_ASSIGNDATE     = null;
    public String         WP_WORKER         = "";
    public String         WP_REQ_WKHR       = "";
    public String         WP_PLAN_WKHR      = "";
    public String         WP_ACT_WKHR       = "";
    public java.util.Date WP_PLAN_START     = null;
    public java.util.Date WP_PLAN_FIHISH    = null;
    public java.util.Date WP_ACT_START      = null;
    public java.util.Date WP_ACT_FINISH     = null;
    public String         WP_STATUS         = "";
    public String         WP_CMPLTRATE_TYPE = "";
    public String         WP_CMPLTRATE      = "";
    public String         WP_REQUIREMENT    = "";
    public String         RST               = "";
    public String         RCT               = "";
    public String         RUT               = "";
}


public class DataPrepare {
    //    private static String driver   = "org.gjt.mm.mysql.Driver";
    //    private static String url      = "jdbc:mysql://localhost/xplanner";
    //    private static String user     = "xplanner";
    //    private static String password = "";  jdbc:oracle:thin:@10.5.2.251:1521:essp
    private static String driverFrom   = "oracle.jdbc.driver.OracleDriver";
    private static String urlFrom      = "jdbc:oracle:thin:@10.5.2.251:1521:essp";
    private static String userFrom     = "essp";
    private static String passwordFrom = "essp";
    private static String driverTo     = "oracle.jdbc.driver.OracleDriver";
    private static String urlTo        = "jdbc:oracle:thin:@10.5.2.56:1521:esspfw";
    private static String userTo       = "esspfw";
    private static String passwordTo   = "esspfw";

    public static void insertBean(Connection conn,
                                  String     tableName,
                                  Object     bean) throws Exception {
        String  sSQL = "insert into " + tableName + " (";

        Field[] allFields = bean.getClass().getFields();

        for (int i = 0; i < allFields.length; i++) {
            sSQL = sSQL + allFields[i].getName();

            if (i < (allFields.length - 1)) {
                sSQL = sSQL + ",";
            }
        }

        sSQL = sSQL + ") values (";

        for (int i = 0; i < allFields.length; i++) {
            sSQL = sSQL + "?";

            if (i < (allFields.length - 1)) {
                sSQL = sSQL + ",";
            } else {
                sSQL = sSQL + ")";
            }
        }

        SQLUtil util = new SQLUtil(conn, sSQL);

        for (int i = 0; i < allFields.length; i++) {
            Field  field      = allFields[i];
            Object fieldValue = allFields[i].get(bean);

            //System.out.println("set "+field.getName()+"("+(i+1)+","+fieldValue+")");
            if (fieldValue instanceof String) {
                util.setString(i + 1, (String) fieldValue);
            } else if (fieldValue instanceof Long
                           || fieldValue instanceof Integer
                           || fieldValue instanceof Float
                           || fieldValue instanceof Double
                           || fieldValue instanceof Boolean
                           || fieldValue instanceof java.math.BigDecimal
                           || fieldValue instanceof java.math.BigInteger) {
                util.setNumber(i + 1, (Number) fieldValue);
            } else if (fieldValue instanceof java.util.Date) {
                util.setDate(i + 1, (java.util.Date) fieldValue);
            }
        }

        util.executeUpdate();
    }

    public static String nvl(String value) {
        if (value == null) {
            return "";
        } else {
            return value;
        }
    }

    public static void selectData(ResultSet rs,
                                  Object    bean) throws Exception {
        Field[] allFields = bean.getClass().getFields();

        for (int i = 0; i < allFields.length; i++) {
            if (allFields[i].getType() == String.class) {
                allFields[i].set(bean, nvl(rs.getString(allFields[i].getName())));
            }

            if ((allFields[i].getType() == java.util.Date.class)
                    || (allFields[i].getType() == java.sql.Date.class)) {
                allFields[i].set(bean, rs.getDate(allFields[i].getName()));
            }
        }
    }

    public static void main(String[] args) {
        //        try {
        //            Class.forName(driver);
        //
        //            Connection conn = DriverManager.getConnection(url, user, password);
        //            conn.createStatement();
        //
        //            Wkitem item = new Wkitem();
        //            item.PROJECT_ID        = "1";
        //            item.RID               = 4;
        //            item.RST               = "N";
        //            item.WP_ID             = "11";
        //            item.WKITEM_DATE       = new java.util.Date();
        //            item.WKITEM_STARTTIME  = new java.util.Date();
        //            item.RCT               = new java.util.Date();
        //            item.RUT               = new java.util.Date();
        //            item.WKITEM_FINISHTIME = new java.util.Date();
        //            item.WKITEM_NAME       = "test item";
        //            item.WKITEM_OWNER      = "tom";
        //            item.WKITEM_PLACE      = "Wuhan";
        //            item.WKITEM_COPYFROM   = 0;
        //            item.WKITEM_BELONGT0   = "wuhan";
        //            item.WKITEM_WKHOURS    = new java.math.BigDecimal(8).setScale(1);
        //            item.WKITEM_ISDLRPT    = "0";
        //            insertBean(conn, "pw_wkitem", item);
        //        } catch (Exception ex) {
        //            ex.printStackTrace();
        //        }
        Connection connFrom = null;
        Connection connTo = null;

        try {
            Class.forName(driverFrom);
            connFrom = DriverManager.getConnection(urlFrom, userFrom,
                                                   passwordFrom);
            connTo   = DriverManager.getConnection(urlTo, userTo, passwordTo);
            connTo.setAutoCommit(false);

            long      maxRid = getMaxRID(connFrom);

            String    sql  = "select * from ESSP_PWP_PWPITEM_T order by ACCOUNTID,ACTIVITYID,PWPID";
            Statement stmt = connFrom.createStatement();
            ResultSet rs   = stmt.executeQuery(sql);

            while (rs.next()) {
                ESSP_PWP_PWPITEM_T beanFrom = new ESSP_PWP_PWPITEM_T();
                selectData(rs, beanFrom);

                PW_WP beanTo = new PW_WP();
                beanTo.RID         = String.valueOf(maxRid + 1);
                beanTo.PROJECT_ID  = beanFrom.ACCOUNTID;
                beanTo.ACTIVITY_ID = beanFrom.ACTIVITYID;
                beanTo.WP_SEQUENCE = "0";

                if (nvl(beanFrom.WORKERID).equals("")) {
                    beanTo.WP_WORKER  = getWorkers(connFrom, beanTo.PROJECT_ID);
                    beanTo.WP_PWPORWP = "0";
                } else {
                    beanTo.WP_WORKER  = beanFrom.WORKERID;
                    beanTo.WP_PWPORWP = "1";
                }
                
                beanTo.WP_ASSIGNBY    = getAssignby(connFrom, beanTo.PROJECT_ID);
                if(nvl(beanTo.WP_ASSIGNBY).equals("")) {
                	System.out.println("beanTo.WP_ASSIGNBY is null [ACCOUNTID='"+beanFrom.ACCOUNTID+"',PWPID='"+beanFrom.PWPID+"']. Ignore it");
                	beanTo.WP_ASSIGNBY=" ";
                	continue;
                }
                
                if(nvl(beanTo.WP_WORKER).equals("")) {
                	System.out.println("beanTo.WP_WORKER is null [ACCOUNTID='"+beanFrom.ACCOUNTID+"',PWPID='"+beanFrom.PWPID+"']. set beanTo.WP_WORKER=beanTo.WP_ASSIGNBY");
                	beanTo.WP_WORKER=beanTo.WP_ASSIGNBY;
                }

                beanTo.WP_CODE = beanFrom.PWPID;
                beanTo.WP_NAME = beanFrom.PWPNAME;
                beanTo.WP_TYPE = beanFrom.PWPTYPE;

                
                beanTo.WP_ASSIGNDATE  = beanFrom.REQUIREDSTART;
                beanTo.WP_REQ_WKHR    = beanFrom.REQUIREDWORKHOURS;
                beanTo.WP_PLAN_WKHR   = beanFrom.PLANNEDWORKHOURS;
                beanTo.WP_ACT_WKHR    = beanFrom.ACTUALWORKHOURS;
                beanTo.WP_PLAN_START  = beanFrom.REQUIREDSTART;
                beanTo.WP_PLAN_FIHISH = beanFrom.REQUIREDFINISH;
                beanTo.WP_ACT_START   = beanFrom.ACTUALSTART;
                beanTo.WP_ACT_FINISH  = beanFrom.ACTUALFINISH;
                beanTo.WP_STATUS      = beanFrom.PWPSTATUS;
                beanTo.WP_CMPLTRATE   = beanFrom.COMPLETERATE;
                beanTo.WP_REQUIREMENT = beanFrom.REQIREMENT;
                beanTo.RST            = "N";
                beanTo.RCT            = "";
                beanTo.RUT            = "";
                try {
					insertBean(connTo, "PW_WP", beanTo);
				} catch (Exception e) {
					e.printStackTrace();
					throw e;
				}

                maxRid++;
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
        //        long maxRid = 1;
        //
        //        try {
        //            String    sql  = "select max(RID) as MAX_RID from PW_WP";
        //            Statement stmt = conn.createStatement();
        //            ResultSet rs   = stmt.executeQuery(sql);
        //
        //            if (rs.next()) {
        //                maxRid = rs.getLong("MAX_RID");
        //            }
        //
        //            rs.close();
        //            stmt.close();
        //        } catch (SQLException e) {
        //            e.printStackTrace();
        //            throw e;
        //        }
        //
        //        return maxRid;
        return 10000;
    }

    private static String getWorkers(Connection conn,
                                     String     accoundId)
                              throws SQLException {
        String workers = "";

        try {
            String sql =
                "select HR_CODE from ESSP_SYS_ACCOUNT_PERSONAL_T where ACCOUNT_ID='"
                + accoundId + "' order by HR_CODE";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String workerId = rs.getString("HR_CODE");

                if (workers.equals("")) {
                    workers = workerId;
                } else {
                    workers = workers + "," + workerId;
                }
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

        return workers;
    }

    private static String getAssignby(Connection conn,
                                      String     accoundId)
                               throws SQLException {
        String assignBy = "";

        try {
            String    sql = "select MANAGER from ESSP_SYS_ACCOUNT_T where ID='"
                            + accoundId + "'";
            Statement stmt = conn.createStatement();
            ResultSet rs   = stmt.executeQuery(sql);

            if (rs.next()) {
                assignBy = rs.getString("MANAGER");
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

        return assignBy;
    }
}
