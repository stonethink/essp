package server.essp.pms.pbs.logic;

import java.sql.ResultSet;

import server.framework.logic.AbstractBusinessLogic;
import c2s.essp.pms.pbs.DtoPmsPbs;

public class LgPmsPbsInit extends AbstractBusinessLogic{
    public Long generatorCode(Long acntRid){
        if( acntRid == null ){
            return null;
        }

        long maxCode = 1;

        String sqlSel = "select MAX_CODE from PMS_PBS_MAX_CODE";
        String whereStr = " where 1=1 ";
        if( acntRid != null ){
            whereStr += " and ACNT_RID="+acntRid.longValue() ;
        }else{
            whereStr += " and ACNT_RID is null";
        }

        try {
            ResultSet rs = getDbAccessor().executeQuery(sqlSel + whereStr);

            if (rs.next()) {
                maxCode = rs.getLong("MAX_CODE");
                maxCode++;

                while( checkExist(acntRid,maxCode) == true ){
                    maxCode++;
                }

                String sqlUpdate = "update PMS_PBS_MAX_CODE set MAX_CODE=" + maxCode + whereStr;
                getDbAccessor().executeUpdate(sqlUpdate);
            }else{
                maxCode = 1;

                while( checkExist(acntRid,maxCode) == true ){
                    maxCode++;
                }

                String sqlIns = "insert into PMS_PBS_MAX_CODE (ACNT_RID, MAX_CODE)values(" +
                                acntRid + ", "+ maxCode + " )";
                getDbAccessor().executeUpdate(sqlIns);
            }

        } catch (Exception ex) {
            return null;
        }

        return new Long(maxCode);
    }

    private boolean checkExist(Long acntRid, long code){
        String pbsCode = code + "";
        int len = pbsCode.length();
        for (int i = len; i < DtoPmsPbs.CODE_NUMBER_LENGTH; i++) {
            pbsCode = "0" + pbsCode;
        }
        pbsCode = "P" + pbsCode;

        try {
            String sqlSel = "select count(*) as NUM from PMS_PBS where ACNT_RID=" + acntRid.longValue()
                            + " and PRODUCT_CODE='" + pbsCode + "'";
            ResultSet rs = getDbAccessor().executeQuery(sqlSel);
            if (rs.next()) {
                int i = rs.getInt("NUM");
                if (i > 0) {
                    return true;
                }
            }
        } catch (Exception ex) {
        }

        return false;
    }
}
