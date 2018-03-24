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
     * acntRid为hr性质的项目，这里列出其下所属的人
     * List {loginID} 建议改为 {DtoUser}
     * @param acntRid Long
     * @return List
     * @throws BusinessException
     */
    public List getUserListInHrAcnt(Long acntRid) throws BusinessException;

}
