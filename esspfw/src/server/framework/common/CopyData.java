/* CopyData.java
 * Copyright (c) 2005 PSA, all rights reserved.
 * Provider : Wistron ITS
 * History
 *   Date(DD/MM/YYYY)    Author          Description
 * ----------------------------------------------------------------------------
 *   11/04/2005           Yery             Created
 */
package server.framework.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;


/**
 *
 */
public class CopyData {
    private static String nvl(String value) {
        if (value == null) {
            return "";
        }

        return value;
    }

    private static void copyTable(Connection connFrom, Connection connTo,
        String table_from, String table_to) throws Exception {
        Statement smtDataFrom = connFrom.createStatement();
        String sSQL = "select * from " + table_from;
        ResultSet rsDataFroms = smtDataFrom.executeQuery(sSQL);
        Statement smtDataTo = connTo.createStatement();
        smtDataTo.executeUpdate("delete from " + table_to);

        while (rsDataFroms.next()) {
            ResultSetMetaData metaData = rsDataFroms.getMetaData();
            int iCount = metaData.getColumnCount();
            String sInsertSQL = "insert into " + table_to + "(";

            for (int i = 0; i < iCount; i++) {
                if (i < (iCount - 1)) {
                    sInsertSQL = sInsertSQL + metaData.getColumnName(i + 1) +
                        ",";
                } else {
                    sInsertSQL = sInsertSQL + metaData.getColumnName(i + 1) +
                        ")";
                }
            }

            sInsertSQL = sInsertSQL + " values (";

            for (int i = 0; i < iCount; i++) {
                String sValue = nvl(rsDataFroms.getString(
                            metaData.getColumnName(i + 1)));

                if (i < (iCount - 1)) {
                    sInsertSQL = sInsertSQL + " '" + sValue + "',";
                } else {
                    sInsertSQL = sInsertSQL + " '" + sValue + "')";
                }
            }

            //log.debug(sInsertSQL);
            try {
                smtDataTo.executeUpdate(sInsertSQL);
            } catch (SQLException e) {
                System.out.println("InsertSQL=" + sInsertSQL);
                //throw e;
            }
        }
    }

    public static boolean checkTable(Connection conn,String table) {
      boolean isExist=false;
      try {
        Statement stmt=conn.createStatement();
        ResultSet rs=stmt.executeQuery("select * from TAB where TNAME='"+table+"'");
        if(rs.next()) {
          isExist=true;
        }
        rs.close();
        stmt.close();

      } catch(Exception e) {
        e.printStackTrace();
      }
      return isExist;
    }

    public static void main(String[] args) {
        Connection connFrom = null;
        Connection connTo = null;


        try {
            //DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            Class.forName("oracle.jdbc.driver.OracleDriver");
            //connTo = DriverManager.getConnection("jdbc:oracle:thin:@10.5.2.63:1521:psaora","balibali", "balibali");
            connTo = DriverManager.getConnection("jdbc:oracle:thin:@10.5.2.56:1521:esspfw","esspfw", "esspfw");
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            //DriverManager.registerDriver(new sun.jdbc.odbc.JdbcOdbcDriver());
            connFrom = DriverManager.getConnection("jdbc:oracle:thin:@10.5.2.251:1521:essp","essp", "essp");
            //connFrom = DriverManager.getConnection("jdbc:oracle:thin:@10.5.2.63:1521:psaora", "balibali","balibali");
            /*
            Statement stmt=connFrom.createStatement();
            ResultSet rs=stmt.executeQuery("select TNAME from tab where TABTYPE='TABLE' ");

            while(rs.next()) {
              String tableName=rs.getString(1);
              boolean checkResult=checkTable(connTo,tableName);
              if(!checkResult) {
                System.out.println(tableName+" ²»´æÔÚ£¡£¡£¡");
              } else {
                if(tableName.trim().toUpperCase().startsWith("ESSP_SYS")) {
                  copyTable(connFrom, connTo, tableName, tableName);
                  System.out.println("complete copy table : " + tableName);
                }
              }
              //copyTable(connFrom, connTo, tableName, tableName);
              //System.out.println("complete copy table : " + tableName);
            }
            rs.close();
            stmt.close();
*/
            copyTable(connFrom, connTo, "essp_sys_account_T", "essp_sys_account_T");
            connFrom.close();
            connTo.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("batgetConn : " + e);
        }
    }
}
