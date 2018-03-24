package server.essp.pwm.wp.logic;

import c2s.essp.pwm.wp.DtoPwWpchk;
import com.wits.util.Parameter;
import essp.tables.PwWpchk;
import server.essp.framework.logic.AbstractESSPLogic;
import server.framework.common.BusinessException;

public class FPW01022CheckpointDeleteLogic extends AbstractESSPLogic {

    public void executeLogic(Parameter param) throws BusinessException {
        //get parameter
        DtoPwWpchk dtoPwWpchk = (DtoPwWpchk) param.get("dtoPwWpchk");

        try {
            PwWpchk pwWpchk = new PwWpchk();
            FPW01000CommonLogic.copyProperties(pwWpchk, dtoPwWpchk);

            getDbAccessor().delete(pwWpchk);
        } catch (BusinessException ex) {
            throw (BusinessException) ex;
        } catch (Exception ex) {
            throw new BusinessException("E000000", "Delete check point error.", ex);
        }

    }


}
