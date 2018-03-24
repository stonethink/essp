package itf.account;

import server.framework.common.BusinessException;
import c2s.essp.common.account.IDtoAccount;
import java.util.List;


/**
 * <p>Title: </p>
 *
 * <p>Description:获取Account相关的信息 </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public interface IAccountInfoUtil {

    /**
     * 依据项目记录的RID，获取项目信息
     * @param accID Long
     * @throws BusinessException
     * @return c2s.essp.common.account.IDtoAccount
     */
    public IDtoAccount getAccountByRID(Long acntRid) throws BusinessException;

    /**
     * 依据项目Code，获取项目信息
     * @param acntCode String
     * @throws BusinessException
     * @return c2s.essp.common.account.IDtoAccount
     */
    public IDtoAccount getAccountByCode(String acntCode) throws
            BusinessException;

    /**
     * 依据ACNT_RID获取其所有的参与人员（Labor Resource）
     * List {DtoUser}
     * @param acntRid Long
     * @return List
     * @throws BusinessException
     */
    public List listLaborResourceByAcntRid(Long acntRid) throws
           BusinessException;

}
