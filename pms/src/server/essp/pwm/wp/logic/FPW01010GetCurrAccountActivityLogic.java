package server.essp.pwm.wp.logic;

import c2s.essp.pwm.wp.DtoAccountAcitivity;
import com.wits.util.Parameter;
import server.framework.common.BusinessException;
import server.framework.logic.AbstractBusinessLogic;

public class FPW01010GetCurrAccountActivityLogic extends AbstractBusinessLogic {

    public void executeLogic(Parameter param) throws BusinessException {
        //get parameter
        Long lProjectId = (Long) param.get("inProjectId");
        Long lActivityId = (Long) param.get("inActivityId");

        DtoAccountAcitivity dtoAccountAcitivity = new DtoAccountAcitivity();
        try {
            dtoAccountAcitivity = FPW01000CommonLogic.getCurrAccountActivity(getDbAccessor(), lProjectId, lActivityId);
        } catch (Exception ex) {
            if (ex instanceof BusinessException) {
                throw (BusinessException) ex;
            } else {
                log.error("", ex);
                throw new BusinessException("E000000", "Get CurrAccountActivity error.");
            }
        }

        //set parameter
        param.put("dtoAccountAcitivity", dtoAccountAcitivity);
    }

}
