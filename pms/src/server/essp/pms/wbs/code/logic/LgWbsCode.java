package server.essp.pms.wbs.code.logic;

import java.util.List;
import java.util.Set;

import db.essp.pms.Wbs;
import db.essp.pms.WbsPK;
import server.essp.common.code.choose.logic.LgCodeChoose;
import server.framework.common.BusinessException;
import server.framework.hibernate.HBComAccess;

public class LgWbsCode extends LgCodeChoose {
    Wbs wbs = null;

    public LgWbsCode(Long acntRid, Long wbsRid) {
        WbsPK pk = new WbsPK(acntRid, wbsRid);
        try {
            wbs = (Wbs) getDbAccessor().load(Wbs.class, pk);
        } catch (Exception ex) {
            throw new BusinessException("E0000",
                                        "Error when select wbs.",
                                        ex);
        }
    }

    public Object getCodeOwner() {
        return wbs;
    }

    public Set getCodeSet() {
        return wbs.getWbsCodes();
    }

    public static void main(String args[]) {
        try {
            HBComAccess hb = HBComAccess.newInstance();
            hb.newTx();
            LgCodeChoose logic = new LgWbsCode(new Long(1), new Long(12));
            List list = logic.list();
            if (list != null) {
                System.out.println(list.size());
            }
            hb.endTxCommit();
        } catch (Exception ex) {
        }
    }

}
