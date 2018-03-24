package itf.account;

import java.util.List;
import server.framework.common.BusinessException;
import server.framework.logic.IBusinessLogic;
import java.util.Vector;

/**
 * <p>Title: </p>
 *
 * <p>Description: ��ȡAccount List</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public interface IAccountListUtil extends IBusinessLogic {

    /**
     * ��Ŀ״̬��Display Name��ʾ���
     * δ�趨��DefaultֵΪSHOWSTYLE_DASHED
     * @param style String
     */
    public void setShowStyle(String style);

    //// I����ȡ��Ŀ�嵥,������Login ID
    /**
     * ��ȡȫ����Ŀ�嵥��������Normal & Closed
     * @return List
     * @throws BusinessException
     */
    public List listAllAccounts() throws BusinessException;

    /**
     * ��ȡ��Ŀ�嵥����Ҫ��ѯ���������£�
     * A, ��Ŀ״̬��STATUS( Normal/Closed)
     * B, ��Ŀ����  ATTRIBUTE(1���ͻ���2���з���3-����)
     * C, ��Ŀ����  TYPE(Project/Management/Sales/Administration/HR/Finance/SSE/Others)
     */

    /**
     * ��Ŀ״̬�Ĳ�ѯ����
     * ��Ŀ״̬��STATUS( Normal/Closed)
     * δ����ʱDefault����ΪNormal
     * @param status String[]
     */
    public void statusCondition(String[] status);


    /**
     * ��Ŀ���͵Ĳ�ѯ����
     * ��Ŀ����  TYPE(Project/Management/Sales/Administration/HR/Finance/SSE/Others)
     * δ����ʱDefault����Ϊȫ����Ŀ����
     * @param types String[]
     */
    public void typeCondition(String[] types);

    /**
     * ��Ŀ��Χ�Ĳ�ѯ����
     * ��Ŀ��Χ  INNER(O/I/)
     * δ����ʱDefault����Ϊȫ����Ŀ����
     * @param types String[]
     */
    public void innerCondition(String[] inner);
    /**
     * �����趨�Ĳ�ѯ������ȡ��Ŀ�嵥�����Ҳ�ѯ����֮���ǲ���ȡ�������磺
     * �趨��״̬���������������������ߵĽ�������ͬʱ��Ҫ�������ߵ������Ż�ɸѡ����
     * ����κ�������δ���ã�����ѯ������״̬��������Ŀ��
     * @return List
     * @throws BusinessException
     */
    public List listAccounts() throws BusinessException;

    /**
     * ��ѯ��ʽͬ�ϣ�����ѯ�Ľ����Combo��ʽ����,����
     * List {DtoComboItem(dispName, dto.getRid(), dto)},����dispName��ʽ����show style���趨
     * @return Vector
     * @throws BusinessException
     */
    public Vector listComboAccounts() throws BusinessException;

    /**
     * ��ѯ��ʽͬ�ϣ�����ѯ�Ľ����SelectOption��ʽ����,����
     * List {SelectOptionImpl(dispName, sRid)},����dispName��ʽ����show style���趨
     * @return List
     * @throws BusinessException
     */
    public List listOptAccounts() throws BusinessException;

    ///////////////////////////////////////////////////////////////////////////
    //// II������Login ID��ȡ��Ӧ����Ŀ�嵥
    /**
     * ����Login ID��ȡ��Ŀ�嵥����Ҫ��ѯ�����������ϻ����ϲ����������ͣ�
     * D, ��Ŀ��������ͼ�Login ID����PERSON_TYPE,����������Ŀ����,�I�ս���,BD����,���r������,�I�մ����
     * E, ��Ϊ��Ŀ������Ա
     * F, ��Ϊ��Ŀ��Key Person
     */

    /**
     * �û����͵Ĳ�ѯ������Ŀǰ֧���������͵��û����в�ѯ�������������ǽ��л����㣺
     * ���δ���κ��趨������ѯ����Ϊ��Ŀ������Ŀ��������ص���Ŀ��
     * @param userTypes String[]
     */
    public void userTypeCondition(String[] userTypes);

    /**
     * �����趨�Ĳ�ѯ������loginID����Ӧ���û����ͻ�ȡ��Ŀ�嵥
     * @param loginID String
     * @return List
     * @throws BusinessException
     */
    public List listAccounts(String loginID) throws BusinessException;

    /**
     * ��ѯ��ʽͬ�ϣ�����ѯ�Ľ����Combo��ʽ����,����
     * List {DtoComboItem(dispName, dto.getRid(), dto)},����dispName��ʽ����show style���趨
     * @param loginID String
     * @return Vector
     * @throws BusinessException
     */
    public Vector listComboAccounts(String loginID) throws BusinessException;

    /**
     * ��ѯ��ʽͬ�ϣ�����ѯ�Ľ����SelectOption��ʽ����,����
     * List {SelectOptionImpl(dispName, sRid)},����dispName��ʽ����show style���趨
     * @param loginID String
     * @return List
     * @throws BusinessException
     */
    public List listOptAccounts(String loginID) throws BusinessException;
}
