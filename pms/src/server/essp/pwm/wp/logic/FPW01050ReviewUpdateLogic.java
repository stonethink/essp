package server.essp.pwm.wp.logic;

import java.util.Iterator;
import c2s.dto.DtoUtil;
import c2s.dto.IDto;
import c2s.essp.pwm.wp.DtoPwWprev;
import com.wits.util.Parameter;
import essp.tables.PwWprev;
import essp.tables.PwWpsum;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import server.framework.common.BusinessException;
import server.framework.hibernate.HBComAccess;
import server.framework.logic.AbstractBusinessLogic;
import java.util.List;

public class FPW01050ReviewUpdateLogic extends AbstractBusinessLogic {

    public void executeLogic(Parameter param) throws BusinessException {
        //get parameter
        DtoPwWprev dtoPwWprev = (DtoPwWprev) param.get("dtoPwWprev");

        boolean isUpdateOrInsert = true;

        try {
            Session hbSession = getDbAccessor().getSession();

            PwWprev pwWprev = new PwWprev();
            FPW01000CommonLogic.copyProperties(pwWprev, dtoPwWprev);
            log.debug(DtoUtil.dumpBean(dtoPwWprev));
            log.debug(DtoUtil.dumpBean(pwWprev));
            if (dtoPwWprev.getRid() == null) {
                HBComAccess.assignedRid(pwWprev);
                getDbAccessor().save(pwWprev);

                isUpdateOrInsert = false;
            } else {
                getDbAccessor().update(pwWprev);

                isUpdateOrInsert = true;
            }

            //update table=PwWpsum: wpDefectRmn
            PwWpsum pwWpsum;
            Query q = getDbAccessor().getSession().createQuery("from essp.tables.PwWpsum t " +
                                            "where t.rid = :wpid");
            q.setLong("wpid", dtoPwWprev.getWpId().longValue());
            List list=q.list();
            if(list!=null){
                for (Iterator it = list.iterator(); it.hasNext(); ) {
                    pwWpsum = (PwWpsum) it.next();
                    pwWpsum.setWpDefectRmn(dtoPwWprev.getWpDefectRmn());
                    getDbAccessor().update(pwWpsum);
                    break;
                }
            }
        } catch (Exception ex) {
            if (isUpdateOrInsert == true) {
                throw new BusinessException("E000000", "Update wp review error.",ex);
            } else {
                throw new BusinessException("E000000", "Insert wp review error.",ex);
            }

        }

        //set parameter
        param.put("dtoPwWprev", dtoPwWprev);
        dtoPwWprev.setOp(IDto.OP_NOCHANGE);
    }

}
