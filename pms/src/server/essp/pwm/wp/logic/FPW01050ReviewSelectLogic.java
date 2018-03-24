package server.essp.pwm.wp.logic;

import java.util.Iterator;

import c2s.essp.pwm.wp.DtoPwWprev;
import com.wits.util.Parameter;
import essp.tables.PwWp;
import essp.tables.PwWprev;
import essp.tables.PwWpsum;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import server.essp.framework.logic.AbstractESSPLogic;
import server.framework.common.BusinessException;
import c2s.essp.common.user.DtoUser;
import c2s.essp.common.account.IDtoAccount;

public class FPW01050ReviewSelectLogic extends AbstractESSPLogic {
    String loginId = null;
    String acntManager = null;

    public FPW01050ReviewSelectLogic(){
        DtoUser user = this.getUser();
        if( user != null ){
            loginId = user.getUserLoginId();
        }else{
            loginId = null;
        }

        IDtoAccount account = this.getAccount();
        if( account != null ){
            acntManager = account.getManager();
        }else{
            acntManager = null;
        }
    }


    public void executeLogic(Parameter param) throws BusinessException {
        //get parameter
        Long lWpId = (Long) param.get("inWpId");

        DtoPwWprev dtoPwWprev = new DtoPwWprev();
        try {
            Session hbSession = getDbAccessor().getSession();
            PwWp pwWp = null;
            PwWprev pwWprev = null;
            PwWpsum pwWpsum = null;

            pwWp = (PwWp) FPW01000CommonLogic.load(hbSession, PwWp.class, lWpId);
            pwWpsum = pwWp.getWpSum();

            Query q = hbSession.createQuery(
                "from essp.tables.PwWprev t "
                + "where t.wpId = :wpid "
                );
            q.setString("wpid", lWpId.toString());
            for (Iterator it = q.iterate(); it.hasNext(); ) {
                pwWprev = (PwWprev) it.next();
                break;
            }
            if (pwWprev == null) {

                //如果不存在就新增
                pwWprev = new PwWprev();
                pwWprev.setWpId(lWpId);
            }

//            Query q2 = hbSession.createQuery(
//                "from essp.tables.PwWpsum t "
//                + "where t.rid = :wpid and t.rst = 'N'  "
//                );
//            q2.setString("wpid", lWpId.toString());
//            for (Iterator it = q2.iterate(); it.hasNext(); ) {
//                pwWpsum = (PwWpsum) it.next();
//                break;
//            }

            //log.debug(DtoUtil.dumpBean(pwWp));
            //log.debug(DtoUtil.dumpBean(pwWpsum));
            //log.debug(DtoUtil.dumpBean(pwWprev));
            FPW01000CommonLogic.copyProperties(dtoPwWprev, pwWp);
            FPW01000CommonLogic.copyProperties(dtoPwWprev, pwWpsum);
            FPW01000CommonLogic.copyProperties(dtoPwWprev, pwWprev);
            //log.debug(DtoUtil.dumpBean(dtoPwWprev));

            if (loginId != null) {
                if (loginId.equals(acntManager) == true
                    || loginId.equals(pwWp.getWpAssignby()) == true) {
                    dtoPwWprev.setIsAssignBy(true);
                } else {
                    dtoPwWprev.setIsAssignBy(false);
                }
            } else {
                dtoPwWprev.setIsAssignBy(false);
            }

        } catch (Exception ex) {
            throw new BusinessException("E000000", "Select wp review error.", ex);
        }

        //set parameter
        param.put("dtoPwWprev", dtoPwWprev);
    }

}
