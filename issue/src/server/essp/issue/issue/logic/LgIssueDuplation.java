package server.essp.issue.issue.logic;

import server.essp.issue.common.logic.AbstractISSUELogic;
import server.essp.issue.issue.viewbean.VbIssueDuplation;
import server.framework.common.BusinessException;
import net.sf.hibernate.Session;

public class LgIssueDuplation extends AbstractISSUELogic {
    public VbIssueDuplation issueDuplation(server.essp.issue.issue.form.AfIssueDuplation AfIssueDuplation) throws
        BusinessException{
        try {
            Session session = this.getDbAccessor().getSession();
            VbIssueDuplation webVo = new VbIssueDuplation();


            return webVo;
        } catch (Exception e) {
            throw new BusinessException(e);
        }


}
}
