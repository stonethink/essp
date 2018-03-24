package server.essp.pwm.wp.logic;

import c2s.dto.DtoUtil;
import c2s.dto.IDto;
import c2s.essp.pwm.wp.DtoPwWpchklog;
import com.wits.util.Parameter;
import essp.tables.PwWpchklog;
import server.essp.framework.logic.AbstractESSPLogic;
import server.framework.common.BusinessException;

public class FPW01022CheckpointLogInsertLogic extends AbstractESSPLogic {

    public void executeLogic(Parameter param) throws BusinessException {
        //get parameter
        DtoPwWpchklog dtoPwWpchklog = (DtoPwWpchklog) param.get("dtoPwWpchklog");

        try {
            if (dtoPwWpchklog == null) {
                return;
            }

            PwWpchklog pwWpchklog = new PwWpchklog();
            FPW01000CommonLogic.copyProperties(pwWpchklog, dtoPwWpchklog);
            log.debug(DtoUtil.dumpBean(pwWpchklog));
            getDbAccessor().save(pwWpchklog);
        } catch (Exception ex) {
            if (ex instanceof BusinessException) {
                throw (BusinessException) ex;
            } else {
                log.error("", ex);
                throw new BusinessException("E000000", "Save wp checkpoint log error.");
            }
        }

        //set parameter
        param.put("dtoPwWpchklog", dtoPwWpchklog);
        dtoPwWpchklog.setOp(IDto.OP_NOCHANGE);
    }
}
