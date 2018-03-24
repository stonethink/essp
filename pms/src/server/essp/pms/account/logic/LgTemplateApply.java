package server.essp.pms.account.logic;

import server.essp.framework.logic.AbstractESSPLogic;
import server.framework.common.BusinessException;
import javax.sql.RowSet;
import java.util.*;

public class LgTemplateApply extends AbstractESSPLogic {


    public List getWpRid(Long acntRid) {
        String Sql = "select RID from PW_WP t where t.PROJECT_ID='";
        List list = new ArrayList();
        try {
            RowSet rs;
            rs = this.getDbAccessor().executeQuery(Sql + acntRid + "'");
            while (rs.next()) {
                long wpRid = rs.getLong("RID");
                list.add(new Long(wpRid));
            }
            rs.close();
            return list;
        } catch (Exception ex) {
            throw new BusinessException("PMS_ACC_003", "get wpRid error.", ex);
        }
    }

    public List getWpchkId(Long wpRid) {
        String Sql = "select RID from PW_WPCHK t where t.WP_ID='";
        List list = new ArrayList();
        try {
            RowSet rs;
            rs = this.getDbAccessor().executeQuery(Sql + wpRid.longValue() +
                "'");
            while (rs.next()) {
                long wpchkRid = rs.getLong("RID");
                list.add(new Long(wpchkRid));
            }
            rs.close();
            return list;
        } catch (Exception ex) {
            throw new BusinessException("PMS_ACC_004", "get wpchkRid error.",
                                        ex);
        }
    }

    public void deletePmsAcntSeq(Long acntRid){
        try{
            this.getDbAccessor().executeUpdate(
                "delete from pms_acnt_seq t where t.rid='" +
                acntRid.longValue() + "'");
        }catch(Exception ex){
            throw new BusinessException("delete msAcntSeq error",ex);
        }

    }
    public void deleteWpchklogs(Long wpchkRid) {
        try {
            this.getDbAccessor().executeUpdate(
                "delete from PW_WPCHKLOGS t where t.WPCHK_ID='" +
                wpchkRid.longValue() + "'");
        } catch (Exception ex) {
            throw new BusinessException("PMS_ACC_005",
                                        "delete wpchklogs error.", ex);
        }
    }

    public void deleteWpchk(Long wpRid) {
        try {
            this.getDbAccessor().executeUpdate(
                "delete from PW_WPCHK t where t.WP_ID='" +
                wpRid.longValue() + "'");
            this.getDbAccessor().executeUpdate(
                "delete from PW_WPREV m where m.WP_ID='" +
                wpRid.longValue() + "'");
            this.getDbAccessor().executeUpdate(
                "delete from PW_WPSUM n where n.WP_ID='" +
                wpRid.longValue() + "'");
        } catch (Exception ex) {
            throw new BusinessException("PMS_ACC_005", "delete wpchk error.",
                                        ex);
        }
    }

    public void deleteWp(Long acntRid) {
        try {
            this.getDbAccessor().executeUpdate(
                "delete from PW_WP_MAX_CODE t where t.ACNT_RID='" +
                acntRid.longValue() + "'");
            this.getDbAccessor().executeUpdate(
                "delete from PW_WP m where m.PROJECT_ID='" +
                acntRid.longValue() + "'");
        } catch (Exception ex) {
            throw new BusinessException("PMS_ACC_006", "delete wp error.", ex);
        }
    }

    public void deleteActivity(Long acntRid) {
        try {
            this.getDbAccessor().executeUpdate(
                "delete from PMS_ACTIVITY_WORKERS t where t.ACNT_RID='" +
                acntRid.longValue() + "'");
            this.getDbAccessor().executeUpdate(
                "delete from PMS_ACTIVITY_RELATION m where m.ACNT_RID='" +
                acntRid.longValue() + "'");
            this.getDbAccessor().executeUpdate(
                "delete from PMS_ACTIVITY_COST n where n.ACNT_RID='" +
                acntRid.longValue() + "'");
            this.getDbAccessor().executeUpdate(
                "delete from PMS_ACTIVITY_CODE p where p.ACNT_RID='" +
                acntRid.longValue() + "'");
            this.getDbAccessor().executeUpdate(
                "delete from PMS_ACTIVITY_QUALITY q where q.ACNT_RID='" +
                acntRid.longValue() + "'");
            this.getDbAccessor().executeUpdate(
                "delete from PMS_ACTIVITY_GUIDELINE g  where g.ACNT_RID='" +
                acntRid.longValue() + "'");
            this.getDbAccessor().executeUpdate(
                "delete from PMS_ACTIVITY_TEST_STAT s  where s.ACNT_RID='" +
                acntRid.longValue() + "'");
            this.getDbAccessor().executeUpdate(
                "delete from PMS_ACTIVITY_TEST t  where t.ACNT_RID='" +
                acntRid.longValue() + "'");
            this.getDbAccessor().executeUpdate(
                "delete from PMS_ACTIVITY q where q.ACNT_RID='" +
                acntRid.longValue() + "'");
        } catch (Exception ex) {
            throw new BusinessException("PMS_ACC_007", "delete activity error.",
                                        ex);
        }
    }
    public void deleteAccountPcb(Long acntRid){
        try{
            this.getDbAccessor().executeUpdate(
               "delete from PMS_PCB_PARAMETER p where p.ACNT_RID='" +
               acntRid.longValue() + "'");
           this.getDbAccessor().executeUpdate(
               "delete from PMS_PCB_ITEM i where i.ACNT_RID='" +
               acntRid.longValue() + "'");

        }catch(Exception ex){
            throw new BusinessException("PMS_ACC_008", "delete pcb error.",
                                        ex);
        }
    }
    public void deletePbs(Long acntRid) {
        try {
            this.getDbAccessor().executeUpdate(
                "delete from PMS_PBS_MAX_CODE t where t.ACNT_RID='" +
                acntRid.longValue() + "'");
            this.getDbAccessor().executeUpdate(
                "delete from PMS_PBS_FILES m where m.ACNT_RID='" +
                acntRid.longValue() + "'");
            this.getDbAccessor().executeUpdate(
                "delete from PMS_PBS_ASSIGNMENT n where n.ACNT_RID='" +
                acntRid.longValue() + "'");

            this.getDbAccessor().executeUpdate(
                "delete from PMS_QA_CHECK_ACTION a where a.ACNT_RID='" +
                acntRid.longValue() + "'");
            this.getDbAccessor().executeUpdate(
                "delete from PMS_PCB_ITEM i where i.ACNT_RID='" +
                acntRid.longValue() + "'");
            this.getDbAccessor().executeUpdate(
                "delete from PMS_PCB_PARAMETER p  where p.ACNT_RID='" +
                acntRid.longValue() + "'");
            this.getDbAccessor().executeUpdate(
                "delete from PMS_QA_CHECK_POINT p where p.ACNT_RID='" +
                acntRid.longValue() + "'");
            this.getDbAccessor().executeUpdate(
                "delete from PMS_PBS p where p.ACNT_RID='" +
                acntRid.longValue() + "'");

        } catch (Exception ex) {
            throw new BusinessException("PMS_ACC_008", "delete pbs error.",
                                        ex);
        }
    }

    public void deleteWbs(Long acntRid) {
        try {
            this.getDbAccessor().executeUpdate(
                "delete from PMS_WBS_CODE n where n.ACNT_RID='" +
                acntRid.longValue() + "'");
            this.getDbAccessor().executeUpdate(
                "delete from PMS_WBS_CHECKPOINT p where p.ACNT_RID='" +
                acntRid.longValue() + "'");
            this.getDbAccessor().executeUpdate(
                "delete from PMS_WBS_GUIDELINE g where g.ACNT_RID='" +
                acntRid.longValue() + "'");
            this.getDbAccessor().executeUpdate(
                "delete from PMS_WBS q where q.ACNT_RID='" +
                acntRid.longValue() + "'");
        } catch (Exception ex) {
            throw new BusinessException("PMS_ACC_009", "delete wbs error.",
                                        ex);
        }
    }

    public void updTemplateRidAndTailor(Long apprAcntRid,String tailorDescription,
                                        Long acntRid) {
        try {
            this.getDbAccessor().executeUpdate(
                "update PMS_ACNT t set t.IMPORT_TEMPLATE_RID='" + apprAcntRid +
                "' , t.ACNT_TAILOR='"+tailorDescription+"' where t.rid='"
                + acntRid + "'");
        } catch (Exception ex) {
            throw new BusinessException("PMS_ACC_010", "set import_template_rid error.",
                                        ex);
        }

    }

    public static void main(String[] args) throws Exception {
        LgTemplateApply lg = new LgTemplateApply();
        List wpIdList = lg.getWpRid(new Long(6001));
        for (int i = 0; i < wpIdList.size(); i++) {
            Long wpId = (Long) wpIdList.get(i);
            List wpchkIdList = lg.getWpchkId(wpId);
            for (int j = 0; j < wpchkIdList.size(); j++) {
                Long wpchkId = (Long) wpchkIdList.get(j);
                lg.deleteWpchklogs(wpchkId);
            }
            lg.deleteWpchk(wpId);
        }
        lg.deleteWp(new Long(6001));
        lg.deleteActivity(new Long(6001));
        lg.deleteWbs(new Long(6001));
        lg.deletePbs(new Long(6001));
        lg.updTemplateRidAndTailor(new Long(6023),"tetse",new Long(6001));

    }
}
