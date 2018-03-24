package server.framework.logic;

import c2s.dto.FileInfo;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.*;
import server.framework.common.BusinessException;
import java.text.DecimalFormat;
import java.text.FieldPosition;
import server.framework.hibernate.HBComAccess;

/**
 *
 * <p>Title: </p>
 *
 * <p>请确保数据库中已经执行下面的DDL: </p>
 * create table ESSP_FILE_SEQ_TBL <br>
 * ( <br>
 *   module VARCHAR2(100),<br>
 *   account VARCHAR2(200),<br>
 *   maxid NUMBER(8,0),<br>
 *   primary key(module,account)<br>
 * );<br>
 *
 * create table ESSP_FILE_TBL <br>
 * ( <br>
 *   module  VARCHAR2(100),<br>
 *   account VARCHAR2(200),<br>
 *   id      VARCHAR2(20),<br>
 *   filename VARCHAR2(255),<br>
 *   primary key(module,account,id)<br>
 * );<br>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */

public class LgUpload extends AbstractBusinessLogic {
    private final static String IDFORMATER = "F00000000";

    public LgUpload() {
    }

    private boolean isInsert(Connection conn, FileInfo info) throws Exception {
        boolean isInsert = true;
        Statement stmt = conn.createStatement();
        String sql =
            "select count(*) as FILESEQCOUNT from ESSP_FILE_SEQ_TBL where module='" +
            info.getModulename() + "' and account='" + info.getAccountcode() +
            "'";
        ResultSet rs = stmt.executeQuery(sql);
        rs.next();
        if (rs.getLong("FILESEQCOUNT") > 0) {
            isInsert = false;
        }
        rs.close();
        stmt.close();
        return isInsert;
    }

    public FileInfo getFileId(FileInfo info) {
        /**
         * 检查是否要覆盖文件（如果客户端设置了fileid，则是覆盖文件）
         */
        if(info.getId()!=null && !info.getId().trim().equals("")) {
            return info;
        }

        HBComAccess dbAccessor = null;
        try {
            dbAccessor = new HBComAccess();
            Connection conn = dbAccessor.getConnect();
            dbAccessor.newTx();
            boolean isInsert = isInsert(conn, info);
            long id = 0;
            Statement stmt = conn.createStatement();
            String sql = "";

            /**
             * 如果不存在此笔序号，则新增一笔记录
             */
            if (isInsert) {
                id = 1;
                sql =
                    "insert into ESSP_FILE_SEQ_TBL(module,account,maxid) values ('" +
                    info.getModulename() + "','" + info.getAccountcode() +
                    "'," + id + ")";
                stmt.executeUpdate(sql);
                stmt.close();
            } else {

                /**
                 * 首先找到此笔记录
                 */
                sql = "select MAXID from ESSP_FILE_SEQ_TBL where module='" +
                      info.getModulename() + "' and account='" +
                      info.getAccountcode() +
                      "'";
                ResultSet rs = stmt.executeQuery(sql);
                rs.next();
                /**
                 * 将序号加1
                 */
                id = rs.getLong("MAXID") + 1;
                rs.close();

                /**
                 * 将加1后的序号写回数据库
                 */
                sql = "update ESSP_FILE_SEQ_TBL set MAXID=" + id +
                      " where module='" +
                      info.getModulename() + "' and account='" +
                      info.getAccountcode() +
                      "'";
                stmt.executeUpdate(sql);
                stmt.close();
            }

            DecimalFormat df = new DecimalFormat(IDFORMATER);
            String fileID = df.format(id, new StringBuffer(),
                                      new FieldPosition(0)).toString();
            info.setId(fileID);

            dbAccessor.commit();
        } catch (BusinessException e) {
            try {
                dbAccessor.rollback();
            } catch (Exception ex) {
            }
            throw e;
        } catch (Exception ex) {
            try {
                dbAccessor.rollback();
            } catch (Exception e) {

            }
            throw new BusinessException(ex);
        } finally {
            try {
                dbAccessor.close();
            } catch (Exception e) {
            }
        }

        return info;
    }

    private boolean hasUpload(Connection conn, FileInfo info) throws Exception {
        if (info.getId() == null) {
            return false;
        }
        boolean hasUploadFlag = false;
        String sql =
            "select count(*) as FILESEQCOUNT from ESSP_FILE_TBL where module='" +
            info.getModulename() + "' and account='" + info.getAccountcode() +
            "' and id='" + info.getId() + "'";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        rs.next();
        if (rs.getLong("FILESEQCOUNT") > 0) {
            hasUploadFlag = true;
        }
        rs.close();
        stmt.close();
        return hasUploadFlag;
    }

    public void upload(FileInfo info) {
        HBComAccess dbAccessor = null;
        try {
            dbAccessor = new HBComAccess();
            Connection conn = dbAccessor.getConnect();
            dbAccessor.newTx();
            boolean hasUpload = hasUpload(conn, info);
            Statement stmt = conn.createStatement();
            if (hasUpload) {
                String sql = "update ESSP_FILE_TBL set filename='" +
                             info.getFilename() + "' where module='" +
                             info.getModulename() + "' and account='" +
                             info.getAccountcode() +
                             "' and id='" + info.getId() + "'";
                stmt.executeUpdate(sql);
            } else {
                String sql =
                    "insert into ESSP_FILE_TBL(module,account,id,filename) values ('" +
                    info.getModulename() + "','" +
                    info.getAccountcode() +
                    "','" + info.getId() + "','" + info.getFilename() + "')";
                stmt.executeUpdate(sql);
            }

            stmt.close();
            dbAccessor.commit();
        } catch (BusinessException e) {
            try {
                dbAccessor.rollback();
            } catch (Exception ex) {
            }
            throw e;
        } catch (Exception ex) {
            try {
                dbAccessor.rollback();
            } catch (Exception e) {

            }
            throw new BusinessException(ex);
        } finally {
            try {
                dbAccessor.close();
            } catch (Exception e) {
            }
        }

    }
}
