package server.essp.timesheet.account.dao;

import java.util.List;
import db.essp.timesheet.TsAccount;
import c2s.essp.timesheet.account.DtoAccount;

/**
 * <p>Title: Account data access interface</p>
 *
 * <p>Description: ����Ŀ��ص����ݷ���</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public interface IAccountDao {

    /**
     * ����ר�������loginId�г���ĳ�����µ�ר��
     * @param deptId String
     * @param loginId String
     * @return List
     */
    public List listProjectsByLoginIdInDept(String deptId, String loginId);
    /**
     * ����deptId�г������µ�����ר��
     * deptIds��ʽΪ('deptId1','deptId2','deptId3')
     * @param deptIds String
     * @return List
     */
    public List listProjectsByDept(String deptIds);

    /**
     * ���ݸ����ŵ�id�г��Ӳ���
     * @param parentId String
     * @return List
     */
    public List listChildrenDeptByParentId(String parentId);

    /**
     * ���ݲ������ܵ�loginId��ѯ����
     * @param loginId String
     * @return List
     */
    public List listDeptByManager(String loginId);

    /**
     * �г�ָ���û�Ϊ��Ŀ�����״̬��������Ŀ
     * @param loginId String
     * @return List
     */
    public List listAccounts(String loginId);
    
    /**
     * �г�ָ��OU��״̬��������Ŀ
     * 	ou��Ϊ������Ŵ��룬��","�ָ���s
     * @param ou String
     * @return List<TsAccount>
     */
    public List listAccountByOu(String ou);
    
    /**
     * �г�״̬����������Account���������ţ�
     * @return
     */
    public List listNormalAccounts();
    /**
     * ��ȡָ��rid����Ŀ
     * @param rid Long
     * @return TsAccount
     */
    public TsAccount loadAccount(Long rid);


    /**
     * ������Ŀ
     * @param Account TsAccount
     */
    public void saveAccount(TsAccount account);

    /**
     * ������Ŀ
     * @param Account TsAccount
     */
    public void addPorject(TsAccount account);

    /**
     * ����ר�������ȡר����Ϣ
     *
     * @param AccountId String
     * @return TsAccount
     */
    public TsAccount loadByAccountId(String accountId);
    /**
     * �г�account�е���Ŀ
     * @param loginId String
     * @return List
     */
    public List listProjects(String loginId);
    /**
     * ����accountRid�õ���ǰ��Ŀ��ʵ���ۼ�ʱ��
     * @param accountRid Long
     * @return BigDecimal
     */
    public Double sumWorkHoursByAccount(final Long accountRid,String loginId);
    
    /**
     * ����accountRid�õ���ǰ��Ŀ��ʵ���ۼ�ʱ��(PMOʹ�ò���ר����������)
     * @param accountRid Long
     * @return BigDecimal
     */
    public Double sumWorkHoursByAccount(final Long accountRid);

    /**
     * ���ݲ���Id�г������µ�״̬Ϊ����������ר��
     * deptIds��ʽΪ('deptId1','deptId2','deptId3')
     * @param deptId String
     * @return List
     */
    public List listAccountByDept(String deptIds);
    
    /**
     * ����������ѯAccounts
     * @param dto
     * @return
     */
    public List listByCondtion(DtoAccount dto, String privilegeOu);
    
    /**
     * �г����Р�B��N�Ĳ��T
     * @return List
     */
    public List listAllDept();
    
    /**
     * ����loginId��ѯ����Ŀ
     * @param loginId
     * @return List
     */ 
    public List listAccountByLabResLoginId(final String loginId);
    
    /**
     * ��ȡ�û�ѡ����Ŀ����
     * @param loginId String
     * @return String
     */
    public String getSelectAccount(String loginId);
    
    /**
     * �����û�ѡ����Ŀ
     * @param loginId String
     * @param accountId String
     */
    public void setSelectAccount(String loginId, String accountId);
    
    /**
     * ��ȡ��Ҫ�����ձ���ר��
     * @return
     */
    public List listMailProject();
}
