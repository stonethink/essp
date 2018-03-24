package server.framework.hibernate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.RowSet;

import c2s.dto.DtoUtil;
import net.sf.hibernate.HibernateException;
import org.apache.log4j.Category;
import server.framework.common.BusinessException;
import oracle.jdbc.rowset.OracleCachedRowSet;

public class HBJdbcAccess extends HBAccess {
    static Category log = Category.getInstance("server");
    OracleCachedRowSet crs = null;
    Connection conn = null;

    public HBJdbcAccess() {
        super();
        isNewSession = true;
        conn = null;
    }

    public Connection getConnect() {
        try {
            if (conn == null) {
                conn = super.getSession().connection();
            }
        } catch (HibernateException ex) {
            log.error(ex);
        } catch (Exception ex) {
            log.error(ex);
        }
        return conn;
    }

    public RowSet executeQuery(String sql_stmt, List oParm, List oParmType) throws
        RuntimeException {
        log.debug(dumpSqlStmt(sql_stmt, oParm));
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            beginTx();
//        boolean bTmpConn = false;
            crs = null;
            int rowcount = 0;
            if (conn == null) {
                conn = this.getConnect();
//                conn.setAutoCommit(true);
//                bTmpConn = true;
            }

            pstmt =
                conn.prepareStatement(
                    sql_stmt,
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            if (oParm != null) {
                for (int i = 0; i < oParm.size(); i++) {

                    //+++
                    String parmType = (String) oParmType.get(i);
                    if (parmType.equalsIgnoreCase("date") == true ||
                        parmType.equalsIgnoreCase("timestamp") == true
                        ) {
                        Object parm = oParm.get(i);
                        if (parm == null) {
                            pstmt.setDate(i + 1, null);
                        } else {
                            pstmt.setTimestamp(i + 1,
                                               (java.sql.Timestamp) oParm.get(i));
                        }
                    } else if (parmType.equalsIgnoreCase("long") == true) {
                        pstmt.setLong(i + 1, ((Long) oParm.get(i)).longValue());
                    } else if (parmType.equalsIgnoreCase("String") == true ||
                               parmType.equalsIgnoreCase("java.lang.String") == true ||
                               parmType.equalsIgnoreCase("") == true
                        ) {
                        //varchar2不让插入null值
                        if (oParm.get(i) == null) {
                            pstmt.setObject(i + 1, "", java.sql.Types.VARCHAR);
                        } else {
                            pstmt.setObject(i + 1, oParm.get(i),
                                            java.sql.Types.VARCHAR);
                        }
                    } else {
                        pstmt.setObject(i + 1, oParm.get(i));
                    }

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
            log.error("Exception occur", e);
            throw new BusinessException("error.system.db", e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                endTx();
//                if (bTmpConn) {
//                    conn.close();
//                    conn = null;
//                }
            } catch (Exception e) {
                log.error("Exception occur", e);
                throw new BusinessException("error.system.db", e);
            }
        }
//        log.debug("Exce_Select, crs =" + crs);
        return crs;
    }

    public List executeQueryToList(String sSql, Class beanClass) throws
        RuntimeException {
        List list = new ArrayList();
        log.debug(sSql);
        Statement stamt = null;
        ResultSet rs = null;
        crs = null;
        try {
            beginTx();
            if (conn == null) {
                conn = this.getConnect();
            }

            stamt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                                         ResultSet.CONCUR_READ_ONLY);

            rs = stamt.executeQuery(sSql);

            if (rs != null) {
                try {
                    ResultSetMetaData rsmd = rs.getMetaData();
                    int iCC = rsmd.getColumnCount();
                    while (rs.next()) {
                        boolean bCopy = false;
                        Object bean = null;
                        try {
                            bean = beanClass.newInstance();
                        } catch (Exception ex) {
                            log.error(ex);
                            throw ex;
                        }
                        for (int i = 0; i < iCC; i++) {
                            String fieldName = rsmd.getColumnName(i + 1);
                            Object value = rs.getObject(i + 1);
                            try {
                                DtoUtil.setProperty(bean,
                                    fieldName2PropertyName(fieldName),
                                    value);
                                bCopy = true;
                            } catch (Exception ex1) {
                            }
                        }
                        if (bCopy) {
                            list.add(bean);
                        }
                    }
                } catch (Exception e) {
                    log.error("Exception occur", e);
                    throw e;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Exception occur", e);
            throw new BusinessException("error.system.db", e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stamt != null) {
                    stamt.close();
                }
                endTx();
            } catch (Exception e) {
                e.printStackTrace();
                log.error("Exception occur", e);
                throw new BusinessException("error.system.db", e);
            }
        }
        return list;
    }

    public RowSet executeQuery(String sSql) throws RuntimeException {
        log.debug(sSql);
//        boolean bTmpConn = false;
        Statement stamt = null;
        ResultSet rs = null;
        crs = null;
        int rowcount = 0;
        try {
            beginTx();
            if (conn == null) {
                conn = this.getConnect();
//                conn.setAutoCommit(true);
//                bTmpConn = true;
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
            log.error("Exception occur", e);
            throw new BusinessException("error.system.db", e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stamt != null) {
                    stamt.close();
                }
                endTx();
//                if (bTmpConn) {
//                    conn.close();
//                    conn = null;
//                }
            } catch (Exception e) {
                e.printStackTrace();
                log.error("Exception occur", e);
                throw new BusinessException("error.system.db", e);
            }
        }
//        log.debug("Exce_Select, crs =" + crs);
        return crs;
    }

    public int executeUpdate(String sSql) throws RuntimeException {
        log.debug(sSql);
//        boolean bTmpConn = false;
        Statement stamt = null;
        int rtn = 0;
        try {
            beginTx();
            if (conn == null) {
                conn = this.getConnect();
//                conn.setAutoCommit(true);
//                bTmpConn = true;
            }
            stamt = conn.createStatement();
            rtn = stamt.executeUpdate(sSql);
        } catch (Exception e) {
            rtn = -1;
            e.printStackTrace();
            log.error("Exception occur", e);
            throw new BusinessException("error.system.db", e);
        } finally {
            try {
                if (stamt != null) {
                    stamt.close();
                }
                endTx();
//                if (bTmpConn) {
//                    conn.close();
//                    conn = null;
//                }
            } catch (Exception e) {
                rtn = -1;
                e.printStackTrace();
                log.error("Exception occur", e);
                throw new BusinessException("error.system.db", e);
            }
        }
//        log.debug("Exce_Update_Del_ins, rtn =" + rtn);
        return rtn;
    }

    public List executeQueryToList(String sSql, List oParm, Class beanClass) throws
        RuntimeException {
        List list = new ArrayList();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        crs = null;

        try {
            beginTx();
            if (conn == null) {
                conn = this.getConnect();
            }

            pstmt =
                conn.prepareStatement(
                    sSql,
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            if (oParm != null) {
                for (int i = 0; i < oParm.size(); i++) {
                    pstmt.setObject(i + 1, oParm.get(i));
                }
            }

            rs = pstmt.executeQuery();
            if (rs != null) {
                try {
                    ResultSetMetaData rsmd = rs.getMetaData();
                    int iCC = rsmd.getColumnCount();
                    while (rs.next()) {
                        boolean bCopy = false;
                        Object bean = null;
                        try {
                            bean = beanClass.newInstance();
                        } catch (Exception ex) {
                            log.error(ex);
                            throw ex;
                        }
                        for (int i = 0; i < iCC; i++) {
                            String fieldName = rsmd.getColumnName(i + 1);
                            Object value = rs.getObject(i + 1);
                            try {
                                DtoUtil.setProperty(bean,
                                    fieldName2PropertyName(fieldName),
                                    value);
                                bCopy = true;
                            } catch (Exception ex1) {
                            }
                        }
                        if (bCopy) {
                            list.add(bean);
                        }
                    }
                } catch (Exception e) {
                    log.error("Exception occur", e);
                    throw e;
                }
            }

        } catch (Exception e) {
            log.error("Exception occur", e);
            throw new BusinessException("error.system.db", e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                endTx();
            } catch (Exception e) {
                log.error("Exception occur", e);
                throw new BusinessException("error.system.db", e);
            }
        }
        return list;
    }

    public RowSet executeQuery(String sql_stmt, List oParm) throws
        RuntimeException {
        log.debug(dumpSqlStmt(sql_stmt, oParm));
//        boolean bTmpConn = false;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        crs = null;
        int rowcount = 0;
        try {
            beginTx();
            if (conn == null) {
                conn = this.getConnect();
//                conn.setAutoCommit(true);
//                bTmpConn = true;
            }

            pstmt =
                conn.prepareStatement(
                    sql_stmt,
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            if (oParm != null) {
                for (int i = 0; i < oParm.size(); i++) {
                    pstmt.setObject(i + 1, oParm.get(i));
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
            log.error("Exception occur", e);
            throw new BusinessException("error.system.db", e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                endTx();
//                if (bTmpConn) {
//                    conn.close();
//                    conn = null;
//                }
            } catch (Exception e) {
                log.error("Exception occur", e);
                throw new BusinessException("error.system.db", e);
            }
        }
//        log.debug("Exce_Select, crs =" + crs);
        return crs;
    }

    public int executeUpdate(String sql_stmt, List oParm) throws
        RuntimeException {
        log.debug(dumpSqlStmt(sql_stmt, oParm));
//        boolean bTmpConn = false;
        PreparedStatement pstmt = null;

        int rtn = 0;
        try {
            beginTx();
            if (conn == null) {
                conn = this.getConnect();
//                conn.setAutoCommit(true);
//                bTmpConn = true;
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
            log.error("Exception occur", e);
            throw new BusinessException("error.system.db", e);
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                endTx();
//                if (bTmpConn) {
//                    conn.close();
//                    conn = null;
//                }
            } catch (Exception e) {
                rtn = -1;
                log.error("Exception occur", e);
                throw new BusinessException("error.system.db", e);
            }
        }
//        log.debug("Exce_Update_Del_ins, rtn =" + rtn);
        return rtn;
    }

    public int executeUpdate(String sql_stmt, List oParm, List oParmType) throws
        RuntimeException {
        log.debug(dumpSqlStmt(sql_stmt, oParm));
//        boolean bTmpConn = false;
        PreparedStatement pstmt = null;

        int rtn = 0;
        try {
            beginTx();
            if (conn == null) {
                conn = this.getConnect();
//                conn.setAutoCommit(true);
//                bTmpConn = true;
            }

            pstmt = conn.prepareStatement(sql_stmt);

            if (oParm != null) {
                for (int i = 0; i < oParm.size(); i++) {
                    //+++
                    String parmType = (String) oParmType.get(i);
                    if (parmType.equalsIgnoreCase("date") == true ||
                        parmType.equalsIgnoreCase("timestamp") == true
                        ) {
                        Object parm = oParm.get(i);
                        if (parm == null) {
                            pstmt.setDate(i + 1, null);
                        } else {
                            pstmt.setTimestamp(i + 1,
                                               (java.sql.Timestamp) oParm.get(i));
                        }
                    } else if (parmType.equalsIgnoreCase("long") == true) {
                        pstmt.setLong(i + 1, ((Long) oParm.get(i)).longValue());
                    } else if (parmType.equalsIgnoreCase("String") == true ||
                               parmType.equalsIgnoreCase("java.lang.String") == true ||
                               parmType.equalsIgnoreCase("") == true
                        ) {
                        //varchar2不让插入null值
                        if (oParm.get(i) == null) {
                            pstmt.setObject(i + 1, "", java.sql.Types.VARCHAR);
                        } else {
                            pstmt.setObject(i + 1, oParm.get(i),
                                            java.sql.Types.VARCHAR);
                        }
                    } else {
                        pstmt.setObject(i + 1, oParm.get(i));
                    }

                }
            }

            rtn = pstmt.executeUpdate();
        } catch (Exception e) {
            rtn = -1;
            log.error("Exception occur", e);
            throw new BusinessException("error.system.db", e);
        } finally {
            try {
                pstmt.close();
                endTx();
//                if (bTmpConn) {
//                    conn.close();
//                    conn = null;
//                }
            } catch (Exception e) {
                rtn = -1;
                log.error("Exception occur", e);
                throw new BusinessException("error.system.db", e);
            }
        }
//        log.debug("Exce_Update_Del_ins, rtn =" + rtn);
        return rtn;
    }

    public List result2List(ResultSet rs, Class beanClass) throws
        RuntimeException {
        int iRowNum = 0;
        List list = new ArrayList();

        try {
            ResultSetMetaData rsmd = rs.getMetaData();
            int iCC = rsmd.getColumnCount();
            while (rs.next()) {
                boolean bCopy = false;
                Object bean = null;
                try {
                    bean = beanClass.newInstance();
                } catch (IllegalAccessException ex) {
                    log.error(ex);
                    return null;
                }
                for (int i = 0; i < iCC; i++) {
                    String fieldName = rsmd.getColumnName(i + 1);
                    Object value = rs.getObject(i + 1);
                    try {
                        DtoUtil.setProperty(bean,
                                            fieldName2PropertyName(fieldName),
                                            value);
                        bCopy = true;
                    } catch (Exception ex1) {
                    }
                }
                if (bCopy) {
                    list.add(bean);
                }
                iRowNum++;
            }
        } catch (Exception e) {
            log.error("Exception occur", e);
            throw new BusinessException("error.system.db", e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception e) {
                log.error("Exception occur", e);
                throw new BusinessException("error.system.db", e);
            }
        }
        return list;
    }

    public String fieldName2PropertyName(String fieldName) {
        String type = "";
        if (fieldName == null || fieldName.equals("")) {
            return type;
        }

        int iPos = fieldName.indexOf("_");
        if (iPos < 0) {
            return new String(fieldName).toLowerCase();
            //return fieldName;
        }

        char[] caClassName = fieldName.toCharArray();
        for (int i = 0; i < caClassName.length; i++) {
            if (caClassName[i] == '_') {
                i++;
                if (i < caClassName.length) {
                    char[] ca = new char[1];
                    ca[0] = caClassName[i];
                    type += new String(ca).toUpperCase();
                }
            } else {
                char[] ca = new char[1];
                ca[0] = caClassName[i];
                type += new String(ca).toLowerCase();
            }
        }

        return type;
    }

    String dumpSqlStmt(String sql_stmt, List oParam) {
        String outStr = sql_stmt + "\t参数( ";
        if (oParam != null) {
            for (int i = 0; i < oParam.size(); i++) {
                if (oParam.get(i) == null) {
                    outStr += "null, ";
                } else {
                    outStr += "'" + oParam.get(i).toString() + "', ";
                }
            }

            if (outStr.lastIndexOf(',') > 0) {
                outStr = outStr.substring(0, outStr.lastIndexOf(','));
            }
        }

        outStr += " )";
        return outStr;
    }

    public static void main(String args[]) {
        HBJdbcAccess tst = new HBJdbcAccess();
        String sPropertyName = tst.fieldName2PropertyName("pwWPP");
        System.out.println(sPropertyName);
    }

}
