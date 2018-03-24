package itf.account;

import java.util.*;
import server.framework.common.BusinessException;
import server.framework.logic.IBusinessLogic;
import c2s.essp.common.account.IDtoAccount;


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
public interface IAccountUtil extends IBusinessLogic {
    public static final String SHOWSTYLE_DASHED = "code -- name";
    public static final String SHOWSTYLE_BRACKET = "(code)name";
    public static final String SHOWSTYLE_ONLYCODE = "code";
    public static final String SHOWSTYLE_ONLYNAME = "name";
    /**
     * 依据项目记录的RID，获取项目信息
     * @param accID Long
     * @throws BusinessException
     * @return c2s.essp.common.account.IDtoAccount
     */
    public IDtoAccount getAccountByRID(Long acntRID) throws BusinessException;


    /**
     * 依据项目Code，获取项目信息
     * @param accID Long
     * @throws BusinessException
     * @return c2s.essp.common.account.IDtoAccount
     */
    public IDtoAccount getAccountByCode(String acntCode) throws
            BusinessException;


    /**
     * 获取所有Normal的项目清单(即项目状态为：Initial/Approved)
     * @throws BusinessException
     * @return List{DtoAccount}
     */
    public List listAccounts() throws BusinessException;


    /**
     * 依据所传入项目状态的列表，获取所有的项目清单
     * @param sAcntStatus List
     * @throws BusinessException
     * @return List{DtoAccount}
     */
    public List listAccounts(List sAcntStatus) throws BusinessException;


    /**
     * 获取所有的项目清单,包括各种项目状态
     * @throws BusinessException
     * @return List{DtoAccount}
     */
    public List listAllAccounts() throws BusinessException;


    /**
     * 依据用户ID获取其所有能查看和修改的项目状态为Normal(即项目状态为：Initial/Approved)的项目清单，
     * 1.获取作为项目经理的项目
     * 2.获取作为EBS所管理的项目
     * 3.获取作为参与者的项目
     *
     * @param loginID String
     * @throws BusinessException
     * @return List{DtoUserAccount}
     */
    public List listAccountsByLoginID(String loginID) throws BusinessException;


    /**
     * 依据用户ID获取其所有能查看和修改的项目状态为所选条件的项目清单，
     * 1.获取作为项目经理的项目
     * 2.获取作为EBS所管理的项目
     * 3.获取作为参与者的项目{DtoUserAccount}
     *
     * @param loginID String
     * @param sAcntStatus List
     * @throws BusinessException
     * @return List{DtoUserAccount}
     */
    public List listAccountsByLoginID(String loginID, List sAcntStatus) throws
            BusinessException;


    /**
     * 依据用户ID获取其所有能查看和修改的全部项目清单，
     * 1.获取作为项目经理的项目
     * 2.获取作为EBS所管理的项目
     * 3.获取作为参与者的项目{DtoUserAccount}
     *
     * @param loginID String
     * @param sAcntStatus List
     * @throws BusinessException
     * @return List{DtoUserAccount}
     */
    public List listAllAccountsByLoginID(String loginID) throws
            BusinessException;


    /**
     * 依据用户ID获取其作为Key Personal的项目清单，且项目状态为Normal(即项目状态为：Initial/Approved)
     *
     * @param loginID String
     * @throws BusinessException
     * @return List{DtoUserAccount}
     */
    public List listKeyPersonalAccountsByLoginID(String loginID) throws
            BusinessException;

    public String listLaborResourceLoginIdStr(Long acntRid) throws
            BusinessException;

    public List listLaborResourceByAcntRid(Long acntRid) throws
            BusinessException;

    public List listManagerAccounts(List userAccountList) throws
            BusinessException;

    public List listPMAccounts(List userAccountList) throws
            BusinessException;

    public List listParticipantAccounts(List userAccountList) throws
            BusinessException;

    public Vector listComboAccounts() throws BusinessException;

    public List listOptAccounts() throws BusinessException;

    public Vector listComboAccountsDefaultStyle() throws BusinessException;

    public List listOptAccountsDefaultStyle() throws BusinessException;

    public Vector listComboAccounts(String loginID) throws BusinessException;

    public List listOptAccounts(String loginID) throws BusinessException;

    public Vector listComboAccountsDefaultStyle(String loginID) throws
            BusinessException;

    public List listOptAccountsDefaultStyle(String loginID) throws
            BusinessException;

    public Vector listComboKeyPersonalAccounts(String loginID) throws
            BusinessException;

    public List listOptKeyPersonalAccounts(String loginID) throws
            BusinessException;

    public Vector listComboKeyPersonalAccountsDefaultStyle(String loginID) throws
            BusinessException;

    public List listOptKeyPersonalAccountsDefaultStyle(String loginID) throws
            BusinessException;

    public Vector listComboList(List accountList, String style) throws
            BusinessException;

    public List listOptList(List accountList, String style) throws
            BusinessException;

    /**acntRid为hr性质的项目，这里列出其下所属的人*/
    public List getUserListInHrAcnt(Long acntRid) throws BusinessException;

    public String listLaborResourceStr(Long acntRid) throws
            BusinessException;

    /**
     * list labor's loginId, name, type and domain split by "&".
     * @param acntRid Long
     * @return String
     * @throws BusinessException
     */
    public String listKeyPersonByAccountStr(Long accountRid) throws
            BusinessException;

    /**
     * list keyperson's loginId, name, type and domain split by "&".
     * @param accountRid Long
     * @return String
     * @throws BusinessException
     */
    public String listKeyPersonInfoStr(Long accountRid) throws
            BusinessException;

    public List getHrAcntRidList() throws BusinessException;

    public List getHrAcntNameList() throws BusinessException;

    public List getHrAcntSelectOptions() throws BusinessException;
}
