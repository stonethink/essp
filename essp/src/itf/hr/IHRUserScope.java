package itf.hr;

import server.framework.common.BusinessException;
import java.util.List;

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
public interface IHRUserScope {
    /**
     * acntRidΪhr���ʵ���Ŀ�������г�������������
     * List {loginID} �����Ϊ {DtoUser}
     * @param acntRid Long
     * @return List
     * @throws BusinessException
     */
    public List getUserListInHrAcnt(Long acntRid) throws BusinessException;

}
