package server.essp.pms.account.baseline.logic;

import server.essp.framework.logic.AbstractESSPLogic;
import db.essp.pms.AccountBaseline;
import net.sf.hibernate.Query;
import db.essp.pms.MilestoneHistory;
import java.util.List;
import server.framework.common.BusinessException;
import db.essp.pms.Wbs;
import net.sf.hibernate.Session;
import db.essp.pms.Activity;
import c2s.dto.DtoUtil;
import net.sf.hibernate.*;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class LgSaveMilestoneBak extends AbstractESSPLogic implements
    BaseLineApprovedListener {
    public LgSaveMilestoneBak() {
    }

    /**
     * 根据acntRid从PMS_ACTIVTY表中获取该项目所有milestone记录
     * 备份到pms_milestone_approved_H
     * @param accountBaseline AccountBaseline
     */
    public void approveBaseLine(AccountBaseline accountBaseline) {
        Long acntRid = accountBaseline.getRid();
        String querySql = "from Activity t where t.pk.acntRid=:acntRid and t.milestone=:isMs";
        try {
            List l = this.getDbAccessor().getSession()
                     .createQuery(querySql)
                     .setParameter("isMs", Boolean.TRUE)
                     .setLong("acntRid", acntRid.longValue())
                     .list();
            if(l == null || l.size() == 0)
                return;
            for(int i = 0;i < l.size() ;i ++){
                 Activity activity = (Activity) l.get(i);
                 MilestoneHistory msHistory = new MilestoneHistory();

                DtoUtil.copyBeanToBean(msHistory,activity);
                msHistory.setAcntRid(activity.getPk().getAcntRid());
                msHistory.setActivityRid(activity.getPk().getActivityId());
                msHistory.setWbsAcntRid(activity.getWbs().getPk().getAcntRid());
                msHistory.setWbsRid(activity.getWbs().getPk().getWbsRid());
                msHistory.setBaseLineId(accountBaseline.getBaselineId());
                this.getDbAccessor().save(msHistory);
            }
        } catch (Exception ex) {
            throw new BusinessException(ex);
        }

    }

}
