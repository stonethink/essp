package server.essp.pwm.wp.logic;

import java.util.List;
import c2s.essp.pwm.wp.DtoPwWpchk;
import com.wits.util.Parameter;
import essp.tables.PwWpchk;
import server.framework.common.BusinessException;
import server.framework.logic.AbstractBusinessLogic;
import c2s.dto.IDto;

public class FPW01022CheckpointUpdateLogic extends AbstractBusinessLogic {

    public void executeLogic(Parameter param) throws BusinessException {
        //get parameter
        Long inWpId = (Long) param.get("inWpId");
        List checkpointList = (List) param.get("checkpointList");

        try {
            checkParameter(inWpId, checkpointList);
            if (checkpointList == null) {
                return;
            }

            for (int i = 0; i < checkpointList.size(); i++) {
                DtoPwWpchk dtoPwWpchk = (DtoPwWpchk) checkpointList.get(i);

                PwWpchk pwWpchk = new PwWpchk();
                if (dtoPwWpchk.isInsert()) {
                    dtoPwWpchk.setWpId(inWpId);

                    FPW01000CommonLogic.copyProperties(pwWpchk, dtoPwWpchk);

                    getDbAccessor().save(pwWpchk);

                    dtoPwWpchk.setOp( IDto.OP_NOCHANGE );
                } else if (dtoPwWpchk.isDelete()) {
                    FPW01000CommonLogic.copyProperties(pwWpchk, dtoPwWpchk);

                    //delete check point log
                    String sSelLog = " from PwWpchklog t where t.wpchkId =:wpchkId ";
                    List logList = getDbAccessor().getSession().createQuery(sSelLog)
                               .setLong("wpchkId", pwWpchk.getRid().longValue())
                               .list();
                    getDbAccessor().delete(logList);

                    getDbAccessor().delete(pwWpchk);

                    checkpointList.remove(i);
                    i--;
                } else if (dtoPwWpchk.isChanged()) {
                    FPW01000CommonLogic.copyProperties(pwWpchk, dtoPwWpchk);

                    getDbAccessor().update(pwWpchk);

                    dtoPwWpchk.setOp( IDto.OP_NOCHANGE );
                }
            }

            //重新获取checkpoint
            Parameter param1 = new Parameter();
            param1.put("inWpId", inWpId);
            FPW01022CheckpointSelectLogic lgPwWpCheckpointSelect = new FPW01022CheckpointSelectLogic();
            lgPwWpCheckpointSelect.setDbAccessor( this.getDbAccessor() );
            lgPwWpCheckpointSelect.executeLogic(param1);
            checkpointList = (List) param1.get("checkpointList");
        } catch (Exception ex) {
            if (ex instanceof BusinessException) {
                throw (BusinessException) ex;
            } else {
                log.error("", ex);
                throw new BusinessException("E000000", "Update wp review error.");
            }
        }

        //set parameter
        param.put("checkpointList", checkpointList);
    }

    private void checkParameter(Long lWpId, List checkpointList) throws BusinessException {
        if (lWpId == null) {
            throw new BusinessException("E000000", "The wp id is null.");
        }
    }

}
