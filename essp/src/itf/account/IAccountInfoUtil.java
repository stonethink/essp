package itf.account;

import server.framework.common.BusinessException;
import c2s.essp.common.account.IDtoAccount;
import java.util.List;


/**
 * <p>Title: </p>
 *
 * <p>Description:��ȡAccount��ص���Ϣ </p>
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
     * ������Ŀ��¼��RID����ȡ��Ŀ��Ϣ
     * @param accID Long
     * @throws BusinessException
     * @return c2s.essp.common.account.IDtoAccount
     */
    public IDtoAccount getAccountByRID(Long acntRid) throws BusinessException;

    /**
     * ������ĿCode����ȡ��Ŀ��Ϣ
     * @param acntCode String
     * @throws BusinessException
     * @return c2s.essp.common.account.IDtoAccount
     */
    public IDtoAccount getAccountByCode(String acntCode) throws
            BusinessException;

    /**
     * ����ACNT_RID��ȡ�����еĲ�����Ա��Labor Resource��
     * List {DtoUser}
     * @param acntRid Long
     * @return List
     * @throws BusinessException
     */
    public List listLaborResourceByAcntRid(Long acntRid) throws
           BusinessException;

}
