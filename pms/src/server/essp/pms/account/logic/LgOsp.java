package server.essp.pms.account.logic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import c2s.dto.DtoUtil;
import c2s.essp.common.account.IDtoAccount;
import c2s.essp.pms.account.DtoPmsAcnt;
import com.wits.util.comDate;
import db.essp.pms.Account;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import server.essp.framework.logic.AbstractESSPLogic;
import server.framework.common.BusinessException;
import c2s.essp.pms.wbs.DtoWbsGuideLine;
import c2s.dto.DtoComboItem;

public class LgOsp extends AbstractESSPLogic {
    public LgOsp() {
    }

    public void ospUpdate(DtoPmsAcnt dataBean) {
        try {
            Account dbBean = (Account)this.getDbAccessor().load(Account.class,
                dataBean.getRid());

            dbBean.setActualFinish(dataBean.getActualFinish());
            dbBean.setActualStart(dataBean.getActualStart());
            dbBean.setAnticipatedFinish(dataBean.getAnticipatedFinish());
            dbBean.setAnticipatedStart((dataBean.getAnticipatedStart()));
            dbBean.setBrief(dataBean.getBrief());
            dbBean.setCurrency(dataBean.getCurrency());
            dbBean.setId(dataBean.getId());
            dbBean.setInner(dataBean.getInner());
            dbBean.setManager(dataBean.getManager());
            dbBean.setName(dataBean.getName());
            dbBean.setOrganization(dataBean.getOrganization());
            dbBean.setPlannedFinish(dataBean.getPlannedFinish());
            dbBean.setPlannedStart(dataBean.getPlannedStart());
            dbBean.setRid(dataBean.getRid());
            dbBean.setStatus(dataBean.getStatus());
            dbBean.setType(dataBean.getType());

            getDbAccessor().update(dbBean);
            this.getDbAccessor().commit();
            this.getDbAccessor().executeUpdate(
                "update pms_acnt t set t.is_template='2' where t.rid=" +
                dataBean.getRid().longValue());

        } catch (Exception ex) {
            try {
                throw ex;
            } catch (Exception ex1) {
                try {
                    this.getDbAccessor().executeUpdate(
                        "delete from pms_acnt t where t.rid=" +
                        dataBean.getRid().longValue());
                } catch (Exception e) {
                    throw new BusinessException("E000000", "OSP.saveAs.error",
                                                e);
                }
                log.error("", ex);
                throw new BusinessException("E000000", "OSP.saveAs.error.");
            }
        }
    }

    public void ospSaveAs(DtoPmsAcnt dataBean) {
        Long acntBakRollBackRid = null;
        try {
            //先备份原来的osp
            Long acntRid = dataBean.getRid();
            Account accountBak = new Account();
            //获取当前的osp
            Account dbBean = (Account)this.getDbAccessor().load(Account.class,
                acntRid);
            DtoUtil.copyBeanToBean(accountBak, dbBean,
                                   new String[] {"rid", "allwbs",
                                   "ebss", "laborResources", "keyPersons",
                                   "noneLaborResources", "acntCodes"});
            Date date = new Date();
            String strDate = comDate.dateToString(date, "yyyy-MM-dd hh:mm");
            accountBak.setId(accountBak.getId() + "-" + strDate);
            //String accountId = accountBak.getId()+"-"+strDate;
            Long accountBakRid = this.getDbAccessor().assignedRid(accountBak);
            acntBakRollBackRid = accountBakRid;
            //设置备份的osp的status为closed
            accountBak.setStatus(IDtoAccount.ACCOUNT_STATUS_CLOSED);

            this.getDbAccessor().save(accountBak);
            this.getDbAccessor().flush();
            this.getDbAccessor().executeUpdate(
                "update pms_acnt t set t.is_template='2' where t.rid=" +
                accountBakRid.longValue());
            Long bakAcntRid = accountBak.getRid();

            LgTemplate lgTemplate = new LgTemplate();
            //备份osp的pcb
            lgTemplate.copyPCB(acntRid, bakAcntRid);
            //备份osp的pbs
            lgTemplate.copyPBSTree(acntRid, bakAcntRid);
            //备份osp的Wbs/Activity及其关联信息
            lgTemplate.copyWbsActivityTree(acntRid, bakAcntRid);

            //更新当前的osp
            dbBean.setActualFinish(dataBean.getActualFinish());
            dbBean.setActualStart(dataBean.getActualStart());
            dbBean.setAnticipatedFinish(dataBean.getAnticipatedFinish());
            dbBean.setAnticipatedStart((dataBean.getAnticipatedStart()));
            dbBean.setBrief(dataBean.getBrief());
            dbBean.setCurrency(dataBean.getCurrency());
            dbBean.setId(dataBean.getId());
            dbBean.setInner(dataBean.getInner());
            dbBean.setManager(dataBean.getManager());
            dbBean.setName(dataBean.getName());
            dbBean.setOrganization(dataBean.getOrganization());
            dbBean.setPlannedFinish(dataBean.getPlannedFinish());
            dbBean.setPlannedStart(dataBean.getPlannedStart());
            dbBean.setRid(dataBean.getRid());
            dbBean.setStatus(dataBean.getStatus());
            dbBean.setType(dataBean.getType());

            getDbAccessor().update(dbBean);
            this.getDbAccessor().flush();
            this.getDbAccessor().executeUpdate(
                "update pms_acnt t set t.is_template='2' where t.rid=" +
                dbBean.getRid().longValue());
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            try {
                this.getDbAccessor().executeUpdate(
                    "delete from pms_acnt t where t.rid=" +
                    acntBakRollBackRid.longValue());
            } catch (Exception e) {
                throw new BusinessException("E000000", "OSP.saveAs.error", e);
            }
            log.error("", ex);
            throw new BusinessException("E000000", "OSP.saveAs.error.");
        }
    }
    /**
     * 查找当前最新一个被Approved的OSP
     * @return Long
     */
    public Long getOsp() {
        List ospList = new ArrayList();
        Query q;
        try {
            Session s = this.getDbAccessor().getSession();
            q = s.createQuery(
                "from Account where is_template=2 and acnt_status='Approved'  order by rid");
            ospList = q.list();
            for (int i = 0; i < ospList.size(); i++) {
                Account accountTemp = (Account) ospList.get(i);
                return accountTemp.getRid();
            }
        } catch (Exception ex) {
            throw new BusinessException("OSP_0001", "find osp error!", ex);
        }
        throw new BusinessException("OSP_0002",
                                    "Has not Approved OSP in system!");
    }

}
