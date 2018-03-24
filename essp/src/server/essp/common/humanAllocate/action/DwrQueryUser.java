package server.essp.common.humanAllocate.action;

import itf.user.IHrQueryUser;
import itf.user.UserFactory;
import java.util.List;
import server.framework.hibernate.HBComAccess;

/**
 * <p>Title: </p>
 *
 * <p>Description: query user base info by name left match</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author lipengxu
 * @version 1.0
 */
public class DwrQueryUser {
    public DwrQueryUser() {
    }

    /**
     * query by name
     * @param searchName String
     * @return Object[]
     */
    public Object[] query(String searchName) {
        HBComAccess hbAccess= HBComAccess.newInstance();
        IHrQueryUser q = UserFactory.createQueryUser();
        List l = q.queryByName(searchName);
        if(l == null || l.size() == 0) {
            return null;
        }
        try {
            hbAccess.close();
        } catch (Exception ex) {
        }
        return l.toArray();
    }
}
