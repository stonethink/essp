package itf.user;

import java.util.List;
import server.framework.common.BusinessException;

/**
 * <p>Title: </p>
 *
 * <p>Description: Interface of Hr for dwr query user</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author lipengxu
 * @version 1.0
 */
public interface IHrQueryUser {
    /**
     * query user by name left match
     * @param searchName String
     * @return List
     * @throws BusinessException
     */
    public List queryByName(String searchName) throws BusinessException;
}
