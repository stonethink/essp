package server.essp.pms.psr.logic;

import server.essp.framework.logic.AbstractESSPLogic;
import java.util.List;
import net.sf.hibernate.Query;
import net.sf.hibernate.*;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class LgListAllPSRData extends AbstractESSPLogic {
    public LgListAllPSRData() {
    }

    public List getAllPSRDate(Long acntRid) {
        List result = null;
        String sql = "from PmsStatusReportHistory h where h.acntRid=:account"
                     +" order by h.startdate";
        try {
            Query query = this.getDbAccessor().getSession().createQuery(sql);
            query.setLong("account", acntRid.longValue());
            result = query.list();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return result;
    }

    public static void main(String[] args) {
        LgListAllPSRData lglistallpsrdata = new LgListAllPSRData();
        List l = lglistallpsrdata.getAllPSRDate(new Long(6022));
        System.out.println(l.size());
    }
}
