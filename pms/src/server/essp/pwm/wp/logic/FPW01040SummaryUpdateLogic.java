package server.essp.pwm.wp.logic;

import c2s.dto.DtoUtil;
import c2s.dto.IDto;
import c2s.essp.pwm.wp.DtoPwWpsum;
import com.wits.util.Parameter;
import essp.tables.PwWpsum;
import server.framework.common.BusinessException;
import server.framework.hibernate.HBComAccess;
import server.framework.logic.AbstractBusinessLogic;

public class FPW01040SummaryUpdateLogic extends AbstractBusinessLogic {

    public void executeLogic(Parameter param) throws BusinessException {
        //get parameter
        DtoPwWpsum dtoPwWpsum = (DtoPwWpsum) param.get("dtoPwWpsum");
        Long lWpId = (Long) param.get("inWpId");

        boolean isUpdateOrInsert = true;
        try {

            //log.debug(DtoUtil.dumpBean(dtoPwWpsum));
            //log.debug(DtoUtil.dumpBean(pwWpsum));
            //if (dtoPwWpsum.getOp().equals(IDto.OP_INSERT) == true) {
            if(dtoPwWpsum.getRid() == null){
                PwWpsum pwWpsum = new PwWpsum();
                //HBComAccess.assignedRid(pwWpsum);
                pwWpsum.setRid(lWpId); //wpsum和wp有相同的rid
                pwWpsum.setWpId(lWpId);
                getDbAccessor().save(pwWpsum);

                dtoPwWpsum.setRid(lWpId);
                isUpdateOrInsert = false;
            } else {
                PwWpsum pwWpsum = (PwWpsum)FPW01000CommonLogic.load(getDbAccessor().getSession(),PwWpsum.class, dtoPwWpsum.getRid());
                FPW01000CommonLogic.copyProperties(pwWpsum, dtoPwWpsum);

                getDbAccessor().update(pwWpsum);

                isUpdateOrInsert = true;
            }

        } catch (Exception ex) {
            log.error("", ex);
            if (isUpdateOrInsert == true) {
                throw new BusinessException("E000000", "Update wp summary error.");
            } else {
                throw new BusinessException("E000000", "Insert wp summary error.");
            }
        }

        //set parameter
        param.put("dtoPwWpsum", dtoPwWpsum);
        dtoPwWpsum.setOp(IDto.OP_NOCHANGE);
    }

}
