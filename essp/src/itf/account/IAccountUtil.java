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
     * ������Ŀ��¼��RID����ȡ��Ŀ��Ϣ
     * @param accID Long
     * @throws BusinessException
     * @return c2s.essp.common.account.IDtoAccount
     */
    public IDtoAccount getAccountByRID(Long acntRID) throws BusinessException;


    /**
     * ������ĿCode����ȡ��Ŀ��Ϣ
     * @param accID Long
     * @throws BusinessException
     * @return c2s.essp.common.account.IDtoAccount
     */
    public IDtoAccount getAccountByCode(String acntCode) throws
            BusinessException;


    /**
     * ��ȡ����Normal����Ŀ�嵥(����Ŀ״̬Ϊ��Initial/Approved)
     * @throws BusinessException
     * @return List{DtoAccount}
     */
    public List listAccounts() throws BusinessException;


    /**
     * ������������Ŀ״̬���б���ȡ���е���Ŀ�嵥
     * @param sAcntStatus List
     * @throws BusinessException
     * @return List{DtoAccount}
     */
    public List listAccounts(List sAcntStatus) throws BusinessException;


    /**
     * ��ȡ���е���Ŀ�嵥,����������Ŀ״̬
     * @throws BusinessException
     * @return List{DtoAccount}
     */
    public List listAllAccounts() throws BusinessException;


    /**
     * �����û�ID��ȡ�������ܲ鿴���޸ĵ���Ŀ״̬ΪNormal(����Ŀ״̬Ϊ��Initial/Approved)����Ŀ�嵥��
     * 1.��ȡ��Ϊ��Ŀ�������Ŀ
     * 2.��ȡ��ΪEBS���������Ŀ
     * 3.��ȡ��Ϊ�����ߵ���Ŀ
     *
     * @param loginID String
     * @throws BusinessException
     * @return List{DtoUserAccount}
     */
    public List listAccountsByLoginID(String loginID) throws BusinessException;


    /**
     * �����û�ID��ȡ�������ܲ鿴���޸ĵ���Ŀ״̬Ϊ��ѡ��������Ŀ�嵥��
     * 1.��ȡ��Ϊ��Ŀ�������Ŀ
     * 2.��ȡ��ΪEBS���������Ŀ
     * 3.��ȡ��Ϊ�����ߵ���Ŀ{DtoUserAccount}
     *
     * @param loginID String
     * @param sAcntStatus List
     * @throws BusinessException
     * @return List{DtoUserAccount}
     */
    public List listAccountsByLoginID(String loginID, List sAcntStatus) throws
            BusinessException;


    /**
     * �����û�ID��ȡ�������ܲ鿴���޸ĵ�ȫ����Ŀ�嵥��
     * 1.��ȡ��Ϊ��Ŀ�������Ŀ
     * 2.��ȡ��ΪEBS���������Ŀ
     * 3.��ȡ��Ϊ�����ߵ���Ŀ{DtoUserAccount}
     *
     * @param loginID String
     * @param sAcntStatus List
     * @throws BusinessException
     * @return List{DtoUserAccount}
     */
    public List listAllAccountsByLoginID(String loginID) throws
            BusinessException;


    /**
     * �����û�ID��ȡ����ΪKey Personal����Ŀ�嵥������Ŀ״̬ΪNormal(����Ŀ״̬Ϊ��Initial/Approved)
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

    /**acntRidΪhr���ʵ���Ŀ�������г�������������*/
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
