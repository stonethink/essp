package server.framework.logic;

import java.sql.ResultSet;
import java.sql.Statement;
import c2s.dto.FileInfo;
import java.sql.Connection;
import server.framework.hibernate.HBComAccess;
import java.sql.*;
import server.framework.common.BusinessException;

public class LgDownload extends AbstractBusinessLogic {
    private final static String IDFORMATER = "F00000000";


    public FileInfo getFileInfo(FileInfo info) {

        if (info.getId() == null || info.getId().equals("")) {
            return info;
        }
        HBComAccess dbAccessor = null;
        try {
            dbAccessor = HBComAccess.newInstance();
            Connection conn = dbAccessor.getConnect();
            dbAccessor.newTx();
            String sql =
                "select filename from ESSP_FILE_TBL where module='" +
                info.getModulename() + "' and account='" + info.getAccountcode() +
                "' and id='" + info.getId() + "'";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                info.setFilename(rs.getString("filename"));
                rs.close();
                stmt.close();
                dbAccessor.commit();
            } else {
                rs.close();
                stmt.close();
                dbAccessor.commit();
                throw new BusinessException("UPLOAD",
                                            "File ID [" + info.getId() + "] not exist!");
            }

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
}
