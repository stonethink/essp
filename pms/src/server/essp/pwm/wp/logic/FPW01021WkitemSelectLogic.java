package server.essp.pwm.wp.logic;

import java.util.ArrayList;
import java.util.List;

import c2s.dto.DtoUtil;
import c2s.essp.pwm.wp.DtoPwWkitem;
import com.wits.util.Parameter;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import server.framework.common.BusinessException;
import server.framework.logic.AbstractBusinessLogic;
import java.util.*;


public class FPW01021WkitemSelectLogic extends AbstractBusinessLogic {
    public void executeLogic(Parameter param) throws BusinessException {
        //get parameter
        Long lWpId = (Long) param.get("inWpId");

        List workItems = new ArrayList();

        try {
            checkParameter(lWpId);

            Session hbSession = getDbAccessor().getSession();
            Query q = hbSession.createQuery("from essp.tables.PwWkitem t " +
                    "where t.wpId = :wpid  " +
                    "order by t.wkitemDate asc");
            q.setString("wpid", lWpId.toString());

            List orgWkitems = q.list();
            workItems = DtoUtil.list2List(orgWkitems, DtoPwWkitem.class);

            for (Iterator iter = workItems.iterator(); iter.hasNext(); ) {
                DtoPwWkitem item = (DtoPwWkitem) iter.next();
                item.setWkitemWkhoursOld(item.getWkitemWkhours());
            }

        } catch (Exception ex) {
            if (ex instanceof BusinessException) {
                throw (BusinessException) ex;
            } else {
                log.error("", ex);
                throw new BusinessException("E000000",
                    "Select work items of wp - " + lWpId + " error.");
            }
        }

        //set parameter
        param.put("workItems", workItems);
    }

    private void checkParameter(Long inWpId) throws BusinessException {
        if (inWpId == null) {
            throw new BusinessException("E000000", "The wp id is null.");
        }
    }
}
