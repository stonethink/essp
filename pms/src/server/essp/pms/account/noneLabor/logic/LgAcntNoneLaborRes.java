package server.essp.pms.account.noneLabor.logic;

import java.util.Iterator;

import c2s.essp.pms.account.DtoNoneLaborRes;
import db.essp.pms.NonlaborResItem;
import db.essp.pms.NonlaborResource;
import server.essp.framework.logic.AbstractESSPLogic;
import server.framework.common.BusinessException;

public class LgAcntNoneLaborRes extends AbstractESSPLogic {

    public void delete(DtoNoneLaborRes dtoNoneLaborRes) throws
        BusinessException {
        try {
            NonlaborResource nonlaborResource = (NonlaborResource)this.
                                                getDbAccessor().load(
                NonlaborResource.class, dtoNoneLaborRes.getRid());

            //delete the item belong to it
            for (Iterator iter = nonlaborResource.getNonlaborResItems().iterator(); iter.hasNext(); ) {
                NonlaborResItem item = (NonlaborResItem) iter.next();

                getDbAccessor().delete(item);
            }

            getDbAccessor().delete(nonlaborResource);
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new BusinessException("E000000",
                                        "Delete NonlaborRes error.", ex);
        }
    }
}
