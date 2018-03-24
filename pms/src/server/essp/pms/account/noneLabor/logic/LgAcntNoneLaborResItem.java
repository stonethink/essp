package server.essp.pms.account.noneLabor.logic;

import c2s.essp.pms.account.DtoNoneLaborResItem;
import db.essp.pms.NonlaborResItem;
import server.essp.framework.logic.AbstractESSPLogic;
import server.framework.common.BusinessException;

public class LgAcntNoneLaborResItem extends AbstractESSPLogic {

    public void delete(DtoNoneLaborResItem dtoNoneLaborResItem) throws
        BusinessException {
        try {
            NonlaborResItem nonlaborResItem = (NonlaborResItem)this.getDbAccessor().
                                              load(NonlaborResItem.class,
                                                   dtoNoneLaborResItem.getRid());

            getDbAccessor().delete(nonlaborResItem);
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new BusinessException("E000000",
                                        "Delete NonlaborResItem error.", ex);
        }
    }

}
