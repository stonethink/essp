package com.wits.db;

import java.sql.*;
import java.util.List;
import javax.sql.RowSet;
import org.apache.log4j.Category;
import oracle.jdbc.rowset.OracleCachedRowSet;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: Wistron ITS Wuhan SDC</p>
 *
 * @author Stone
 * @version 1.0
 */
public class DbAccess {
    Category log = Category.getInstance( "server" );

    String DB_NAME = null;
    Connection conn = null;
    OracleCachedRowSet crs = null;

    public DbAccess() {
        this.DB_NAME = "";
    }

    public DbAccess(String sDBName) {
        this.DB_NAME = sDBName;
    }

    public void beginTx() throws Exception {
        beginTx(0);
    }

//iType = 0, if there is not connection, it will create a new connection;
//           if there is a connection, it will close the connection existed and create a new connection.
//iType = 1, if there is not connection, it will create a new connection;
//           if there is a connection, it will commit the connection existed and create a new connection.
//iType = 2, if there is not connection, it will create a new connection;
//           if there is a connection, it will use the connection existed
    public void beginTx(int iType) throws Exception {
        log.debug("beginTx, DB_NAME =" + DB_NAME + ";iType =" + iType);
        if (conn == null) {
            conn = DbConnection.getConnect(DB_NAME);
            log.debug("create new connection in beginTx, " + DB_NAME + ";iType =" + iType);
            conn.setAutoCommit(false);
        } else {
            log.debug("beginTx, conn isn't null! DB_NAME=" + DB_NAME + ";iType =" + iType);
            switch (iType) {
                case 0:
                    conn.close();
                    conn = DbConnection.getConnect(DB_NAME);
                    conn.setAutoCommit(false);
                case 1:
                    conn.commit();
                case 2:
                    //conn.commit();
                default:
                    conn.close();
                    conn = DbConnection.getConnect(DB_NAME);
                    conn.setAutoCommit(false);
            }
        }
    }

    public void endTx() throws Exception {
        endTx(1);
    }

    public void endTxRollback() throws Exception {
        endTx(0);
    }

//iType = 0, Rollback transaction
//iType = 1, Commint transaction
    void endTx(int iType) throws Exception {
        if (conn == null) {
            log.debug("endTx, conn is null! DB_NAME =" + DB_NAME + ";iType =" + iType);
            return;
        }
        if (iType == 0) {
            conn.rollback();
            log.debug("endTx, conn is rollback! DB_NAME =" + DB_NAME + ";iType =" + iType);
        } else {
            conn.commit();
            log.debug("endTx, conn is commit! DB_NAME =" + DB_NAME + ";iType =" + iType);
        }
        conn.close();
        conn = null;
    }

    public Connection getConnect() {
        if (conn == null) {
            conn = DbConnection.getConnect(DB_NAME);
        }
        return conn;
    }

    public void setConnect(Connection conn) {
        this.conn = conn;
    }

    public RowSet executeQuery(String sSql) throws Exception {
        log.debug(sSql);
        boolean bTmpConn = false;
        Statement stamt = null;
        ResultSet rs = null;

        crs = null;
        int rowcount = 0;
        try {
            if (conn == null) {
                conn = DbConnection.getConnect(DB_NAME);
                conn.setAutoCommit(true);
                bTmpConn = true;
            }

            stamt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                                         ResultSet.CONCUR_READ_ONLY);

            //String sSql = this.generateSQL(sqlid, parm);
            rs = stamt.executeQuery(sSql);
            if (rs != null) {
                rs.beforeFirst();
                try {
                    crs = new OracleCachedRowSet();
                } catch (Exception e) {
                    try {
                        crs = new OracleCachedRowSet();
                    } catch (Exception e1) {
                        e.printStackTrace();
                        log.debug(e1.getMessage());
                        crs = new OracleCachedRowSet();
                    }
                }
                crs.populate(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error( "Exception occur", e );
            //throw e;
        } finally {
            try {
                rs.close();
                stamt.close();
                if (bTmpConn) {
                    conn.close();
                    conn = null;
                }
            } catch (Exception e) {
                e.printStackTrace();
                log.error( "Exception occur", e );
                throw e;
            }
        }
//        log.debug("Exce_Select, crs =" + crs);
        return crs;
    }

    public int executeUpdate(String sSql) throws Exception {
        log.debug(sSql);
        boolean bTmpConn = false;
        Statement stamt = null;

        int rtn = 0;
        try {
            if (conn == null) {
                conn = DbConnection.getConnect(DB_NAME);
                conn.setAutoCommit(true);
                bTmpConn = true;
            }
            stamt = conn.createStatement();
            rtn = stamt.executeUpdate(sSql);
        } catch (Exception e) {
            rtn = -1;
            e.printStackTrace();
            log.error( "Exception occur", e );
            //throw e;
        } finally {
            try {
                stamt.close();
                if (bTmpConn) {
                    conn.close();
                    conn = null;
                }
            } catch (Exception e) {
                rtn = -1;
                e.printStackTrace();
                log.error( "Exception occur", e );
                throw e;
            }
        }
//        log.debug("Exce_Update_Del_ins, rtn =" + rtn);
        return rtn;
    }

    public RowSet executeQuery(String sql_stmt, List oParm) throws
        Exception {
        log.debug( dumpSqlStmt(sql_stmt, oParm) );
        boolean bTmpConn = false;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        crs = null;
        int rowcount = 0;
        try {
            if (conn == null) {
                conn = DbConnection.getConnect(DB_NAME);
                conn.setAutoCommit(true);
                bTmpConn = true;
            }

            pstmt =
                conn.prepareStatement(
                sql_stmt,
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY);

            if (oParm != null) {
                for (int i = 0; i < oParm.size(); i++) {
                    pstmt.setObject(i + 1, oParm.get(i) );
//                    pstmt.setObject(i + 1, "AccType" );
                }
            }

            rs = pstmt.executeQuery();
            if (rs != null) {
                rs.beforeFirst();
                try {
                    crs = new OracleCachedRowSet();
                } catch (Exception e) {
                    try {
                        crs = new OracleCachedRowSet();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                        log.debug(e1);
                        crs = new OracleCachedRowSet();
                    }
                }
                crs.populate(rs);
            }
        } catch (Exception e) {
            log.error( "Exception occur", e );
            throw e;
        } finally {
            try {
                rs.close();
                pstmt.close();
                if (bTmpConn) {
                    conn.close();
                    conn = null;
                }
            } catch (Exception e) {
                log.error( "Exception occur", e );
                throw e;
            }
        }
  //        log.debug("Exce_Select, crs =" + crs);
        return crs;
    }


    public int executeUpdate(String sql_stmt, List oParm) throws
        Exception {
        log.debug( dumpSqlStmt(sql_stmt, oParm) );
        boolean bTmpConn = false;
        PreparedStatement pstmt = null;

        int rtn = 0;
        try {
            if (conn == null) {
                conn = DbConnection.getConnect(DB_NAME);
                conn.setAutoCommit(true);
                bTmpConn = true;
            }

            pstmt = conn.prepareStatement(sql_stmt);

            if (oParm != null) {
                for (int i = 0; i < oParm.size(); i++) {
                    pstmt.setObject(i + 1, oParm.get(i));
                }
            }

            rtn = pstmt.executeUpdate();
        } catch (Exception e) {
            rtn = -1;
            log.error( "Exception occur", e );
            throw e;
        } finally {
            try {
                pstmt.close();
                if (bTmpConn) {
                    conn.close();
                    conn = null;
                }
            } catch (Exception e) {
                rtn = -1;
                log.error( "Exception occur", e );
                throw e;
            }
        }
  //        log.debug("Exce_Update_Del_ins, rtn =" + rtn);
        return rtn;
    }

    public int executeUpdate(String sql_stmt, List oParm, List oParmType) throws
        Exception {
        log.debug( dumpSqlStmt(sql_stmt, oParm) );
        boolean bTmpConn = false;
        PreparedStatement pstmt = null;

        int rtn = 0;
        try {
            if (conn == null) {
                conn = DbConnection.getConnect(DB_NAME);
                conn.setAutoCommit(true);
                bTmpConn = true;
            }

            pstmt = conn.prepareStatement(sql_stmt);

            if (oParm != null) {
                for (int i = 0; i < oParm.size(); i++) {
                    //+++
                    String parmType = (String)oParmType.get(i) ;
                    if( parmType.equalsIgnoreCase( "date" ) == true ||
                        parmType.equalsIgnoreCase( "timestamp" ) == true
                        ){
                        Object parm = oParm.get(i);
                        if (parm == null) {
                            pstmt.setDate(i + 1, null);
                        } else {
                            pstmt.setTimestamp(i + 1, (java.sql.Timestamp) oParm.get(i));
                        }
                    }else if(parmType.equalsIgnoreCase( "long" ) == true){
                        pstmt.setLong(i + 1, ((Long)oParm.get(i)).longValue());
                    }else if(parmType.equalsIgnoreCase( "String" ) == true||
                             parmType.equalsIgnoreCase( "java.lang.String" ) == true||
                             parmType.equalsIgnoreCase( "" ) == true
                             ){
                        //varchar2不让插入null值
                        if( oParm.get(i) == null ){
                            pstmt.setObject(i + 1, "", java.sql.Types.VARCHAR);
                        }else{
                            pstmt.setObject(i + 1, oParm.get(i), java.sql.Types.VARCHAR);
                        }
                    } else {
                        pstmt.setObject(i + 1, oParm.get(i));
                    }

                }
            }

            rtn = pstmt.executeUpdate();
        } catch (Exception e) {
            rtn = -1;
            log.error( "Exception occur", e );
            throw e;
        } finally {
            try {
                pstmt.close();
                if (bTmpConn) {
                    conn.close();
                    conn = null;
                }
            } catch (Exception e) {
                rtn = -1;
                log.error( "Exception occur", e );
                throw e;
            }
        }
  //        log.debug("Exce_Update_Del_ins, rtn =" + rtn);
        return rtn;
    }


    String dumpSqlStmt( String sql_stmt, List oParam ){
        String outStr = sql_stmt + "\t参数( ";
        if( oParam != null ){
            for (int i = 0; i < oParam.size(); i++) {
                if( oParam.get(i) == null ){
                    outStr += "null, ";
                }else{
                    outStr += "'" + oParam.get(i).toString() + "', ";
                }
            }

            if( outStr.lastIndexOf(',') > 0 ){
                outStr = outStr.substring(0, outStr.lastIndexOf(','));
            }
        }

        outStr += " )";
        return outStr;
    }

}
