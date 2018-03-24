package server.essp.pwm.workbench.logic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import c2s.essp.common.account.IDtoAccount;
import c2s.essp.common.user.DtoUser;
import c2s.essp.pwm.wp.*;
import com.wits.util.ThreadVariant;
import db.essp.pms.Activity;
import db.essp.pms.ActivityWorker;
import essp.tables.PwWp;
import itf.account.AccountFactory;
import net.sf.hibernate.Query;
import server.essp.framework.logic.AbstractESSPLogic;
import server.framework.common.BusinessException;

public class LgWorkScope extends AbstractESSPLogic {
    String loginId = null;

    public LgWorkScope() {
        DtoUser user = this.getUser();
        if (user != null) {
            loginId = user.getUserLoginId();
        } else {
            //loginId = "stone.shi";
            //throw new BusinessException("E001", "Can't get user information from session.Please login first!");
        }
    }

    public List getScope() {
        List scopeList = new ArrayList();

        try {
            //get account list
            List accountList = AccountFactory.create().listAccountsByLoginID(loginId);
            for (int i = 0; i < accountList.size(); i++) {
                IDtoAccount account = (IDtoAccount) accountList.get(i);
                DtoWSAccount dtoWSAccount = createWSAccount(account);

                Long acntRid = dtoWSAccount.getAcntRid();
                if (acntRid == null) {
                    continue;
                }

                //get activity list
                String selSql = " from ActivityWorker t "
                                + " where (t.loginId=:loginId or t.activity.manager = :loginId)"
                                + " and t.activity.pk.acntRid=:acntRid "
                                + " and t.activity.start=:start "
                                + " and (t.activity.finish !=:finish or t.activity.finish is null)"
                                ;
                List workers = getDbAccessor().getSession().createQuery(selSql)
                               .setString("loginId", loginId)
                               .setLong("acntRid", acntRid.longValue())
                               .setBoolean("start", true)
                               .setBoolean("finish", true)
                               .list();
                if (workers != null) {
                    for (int j = 0; j < workers.size(); j++) {
                        ActivityWorker worker = (ActivityWorker) workers.get(j);
                        Activity activity = worker.getActivity();

                        DtoWSActivity dtoWSActivity = createWSActivity(dtoWSAccount, activity);
                        dtoWSAccount.getActivityList().add(dtoWSActivity);
                    }
                }

                //get wp list
                String sqlSelWp = " from PwWp t where ( t.wpWorker LIKE '%" + loginId + "%' "
                                  + " and t.projectId=" + acntRid.longValue()
                                  + " AND t.wpStatus!='" + DtoPwWpItem.CLOSED_STATUS
                                  + "'  AND t.wpStatus!='" + DtoPwWpItem.CANCEL_STATUS
                                  + "'  AND t.wpStatus!='" + DtoPwWpItem.REJECT_STATUS + "'  )";
                Query qWp = getDbAccessor().getSession().createQuery(sqlSelWp);
                List wpList = qWp.list();
                for (Iterator iter = wpList.iterator(); iter.hasNext(); ) {
                    PwWp pwWp = (PwWp) iter.next();

                    DtoWSWp dtoWSWp = createWSWp(dtoWSAccount, pwWp);
                    dtoWSAccount.getWpList().add(dtoWSWp);
                }

                scopeList.add(dtoWSAccount);
            }
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new BusinessException("E00001", "Error when get account list error.", ex);
        }

        return scopeList;
    }

    private DtoWSAccount createWSAccount(IDtoAccount account) {
        DtoWSAccount dtoWSAccount = new DtoWSAccount();
        dtoWSAccount.setAcntRid(account.getRid());
        dtoWSAccount.setAcntCode(account.getId());
        dtoWSAccount.setAcntName(account.getName());
        return dtoWSAccount;
    }

    private DtoWSActivity createWSActivity(DtoWSAccount dtoWSAccount, Activity activity) {
        DtoWSActivity dtoWSActivity = new DtoWSActivity();
        dtoWSActivity.setAcntRid(dtoWSAccount.getAcntRid());
        dtoWSActivity.setAcntCode(dtoWSAccount.getAcntCode());
        dtoWSActivity.setAcntName(dtoWSAccount.getAcntName());

        dtoWSActivity.setActivityRid(activity.getPk().getActivityId());
        dtoWSActivity.setActivityCode(activity.getCode());
        dtoWSActivity.setActivityName(activity.getName());
        return dtoWSActivity;
    }

    private DtoWSWp createWSWp(DtoWSAccount dtoWSAccount, PwWp PwWp) {

            DtoWSWp dtoWSWp = new DtoWSWp();
            dtoWSWp.setAcntRid(dtoWSAccount.getAcntRid());
            dtoWSWp.setAcntCode(dtoWSAccount.getAcntCode());
            dtoWSWp.setAcntName(dtoWSAccount.getAcntName());
            try{
                Activity activity = PwWp.getActivity();
                if (activity != null) {
                    dtoWSWp.setActivityRid(activity.getPk().getActivityId());
                    dtoWSWp.setActivityCode(activity.getCode());
                    dtoWSWp.setActivityName(activity.getName());
                }
            }catch(Exception e){
                log.warn("",e);
            }
            dtoWSWp.setWpRid(PwWp.getRid());
            dtoWSWp.setWpCode(PwWp.getWpCode());
            dtoWSWp.setWpName(PwWp.getWpName());
            return dtoWSWp;
    }

    public static void main(String args[]){
        ThreadVariant thread=ThreadVariant.getInstance();
        DtoUser user = new DtoUser();
        user.setUserLoginId("WH0507014");
        thread.set(DtoUser.SESSION_USER,user);

        List scopeList = (new LgWorkScope()).getScope();
        System.out.println(scopeList.size());
        for (int i = 0; i < scopeList.size(); i++) {
            DtoWSAccount scope = (DtoWSAccount)scopeList.get(i);
            System.out.println(scope.getActivityList().size());
            System.out.println(scope.getWpList().size());
        }
    }
}
