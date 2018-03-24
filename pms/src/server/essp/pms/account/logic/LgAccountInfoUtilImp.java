package server.essp.pms.account.logic;

import server.framework.logic.AbstractBusinessLogic;
import itf.account.IAccountInfoUtil;
import c2s.essp.common.account.IDtoAccount;
import server.framework.common.BusinessException;
import java.util.List;
import server.essp.pms.account.logic.LgAccountListUtilImp.AccountMapper;
import c2s.essp.common.user.DtoUserBase;

public class LgAccountInfoUtilImp extends AbstractBusinessLogic implements IAccountInfoUtil {
    private AccountMapper mapper = null;
    public LgAccountInfoUtilImp(){
        LgAccountListUtilImp listUtil = new LgAccountListUtilImp();
        mapper =  listUtil.new AccountMapper(getDbAccessor());
    }
    /**
     * 通过主键查询Account
     * @param acntRid Long
     * @return IDtoAccount
     * @throws BusinessException
     */
    public IDtoAccount getAccountByRID(Long acntRid) throws BusinessException {
        String sql = LgAccountListUtilImp.QUERY_ALL_ACCOUNT
                     + " AND rid='"+acntRid+"'";
        List list = mapper.sql2Account(sql);
        if(list == null || list.size() == 0)
            return null;
        return (IDtoAccount) list.get(0);
    }
    /**
     * 通过Id查询Account
     * @param acntCode String
     * @return IDtoAccount
     * @throws BusinessException
     */
    public IDtoAccount getAccountByCode(String acntCode) throws
        BusinessException {
        String sql = LgAccountListUtilImp.QUERY_ALL_ACCOUNT
                     + " AND acnt_id='"+acntCode+"'";
        List list = mapper.sql2Account(sql);
        if(list == null || list.size() == 0)
            return null;
        return (IDtoAccount) list.get(0);
    }
    /**
     * 列出Account中的所有人员,人员以DtoUserBase表示
     * @param acntRid Long
     * @return List
     * @throws BusinessException
     */
    public List listLaborResourceByAcntRid(Long acntRid) throws BusinessException {
        String sql = "SELECT login_id as userLoginId,emp_name as userName FROM pms_labor_resources WHERE acnt_rid='"+acntRid+"'";
        return this.getDbAccessor().executeQueryToList(sql,DtoUserBase.class);
    }

}
