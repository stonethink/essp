package server.essp.timesheet.account.labor.dao;

import java.util.List;

import db.essp.timesheet.TsLaborResource;

/**
 * <p>Title: labor resource data access interface</p>
 *
 * <p>Description: ����Ŀ������ص����ݷ���</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public interface ILaborDao {

    /**
     * �г�ָ����Ŀ�µ�������Դ
     * @param AccountRid Long
     * @return List<TsLaborResource>
     */
    public List listLabor(Long accountRid);

    /**
     * ��ָ��������Դ����Ŀ��ɾ��
     * @param labor TsLaborResource
     */
    public void deleteLabor(TsLaborResource labor);

    /**
     * Ϊĳ��Ŀ����������Դ
     * @param labor TsLaborResource
     */
    public void addLabor(TsLaborResource labor);

    /**
     * ����������Դ��Ϣ
     * @param labor TsLaborResource
     */
    public void saveLabor(TsLaborResource labor);
    /**
     * ͨ��rid��ѯlabor����
     * @param rid Long
     * @return TsLaborResource
     */
    public TsLaborResource loadLabor(Long rid);
    /**
     * ����ר�������login id��ѯLabor����
     * @param accountId String
     * @param loginId String
     * @return TsLaborResource
     */
    public TsLaborResource loadByAccountIdLoginId(Long accountRid,
                                                  String loginId);

    /**
    * ���ݵ�¼��ID��LaborResource����ȡ�ö�Ӧ��AccountRid����
    * @param loginId String
    * @return Long
    */
   public List getAccountRidByLabor(String loginId);
   /**
    * ��ѯ��Ӧ��Դ�����µ�������Ա����
    * @param managerId String ���Ͼ���login id
    * @return List
    */
   public List<String> listLoginIdByResourceManager(String managerId);
   
   /**
    * �г�����״̬ΪIN��Ա����LOGINID
    * @return List
    */
   public List listAllEmpId(final String deptStr);
   
   /**
    * ͨ��ר�������ѯר���е���Ա
    * @param accountId
    * @return
    */
   public List listPersonInProject(String accountId);
}
