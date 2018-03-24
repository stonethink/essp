package server.essp.pwm.wp.logic;

import java.util.ArrayList;
import java.util.List;

import c2s.dto.DtoUtil;
import com.wits.util.Parameter;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import server.essp.framework.logic.AbstractESSPLogic;
import server.framework.common.BusinessException;


public class FPW01022CheckpointLogSelectLogic extends AbstractESSPLogic {
    public void executeLogic(Parameter param) throws BusinessException {
        //get parameter
        String inCheckpointId = (String) param.get("inCheckpointId");

        List chkLogList = new ArrayList();

        try {
            checkParameter(inCheckpointId);

            Session hbSession = getDbAccessor().getSession();
            Query q = hbSession.createQuery("from essp.tables.PwWpchklog t " +
                    "where t.wpchkId = :wpchkId  " +
                    "order by t.wpchklogsDate asc");
            q.setString("wpchkId", inCheckpointId);

            List ql = q.list();
            chkLogList = DtoUtil.list2List(ql,
                    c2s.essp.pwm.wp.DtoPwWpchklog.class);
        } catch (Exception ex) {
            if (ex instanceof BusinessException) {
                throw (BusinessException) ex;
            } else {
                log.error("", ex);
                throw new BusinessException("E000000",
                    "Select logs of checkpoint - " + inCheckpointId +
                    " error.");
            }
        }

        //set parameter
        param.put("chkLogList", chkLogList);
    }

    private void checkParameter(String lWpId) throws BusinessException {
        if (lWpId == null) {
            throw new BusinessException("E000000", "The checkpoint id is null.");
        }
    }
}
