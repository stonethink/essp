package server.essp.projectpre.service.accountapplication;

import java.util.List;
import server.essp.projectpre.db.AcntApp;
import server.essp.projectpre.db.AcntCustContactorApp;
import server.essp.projectpre.db.AcntPersonApp;
import server.essp.projectpre.db.AcntTechinfoApp;
import server.framework.common.BusinessException;

public interface IAccountApplicationService {
	
   /**
    * ��������ѯ������ȡ���������¼��
    * 1��loginId ��¼��ID
    * 2: applicationType ��������(project code,deparment code)
    * 3��applicationStatus �����״̬�������ˣ����ܽ^���Ѵ_�J��
    * @return List
    * @throws BusinessException
    */
   public List listAll(String loginId,String applicationType,String applicationStatus) throws BusinessException;
   
   /**
    * ����ר����¼
    * @param accountApplication
    */
   public void save(AcntApp accountApplication);
   
   /**
    * ����ר����¼
    * @param accountApplication
    */
   public void update(AcntApp accountApplication);
   
   
   /**
    * ɾ��ר����¼
    * @param Rid
    * @throws BusinessException
    */
   public void delete(Long Rid)throws BusinessException ;
   
   /**
    * ����������ȡר����¼
    * @param Rid
    * @return
    * @throws BusinessException
    */
   public AcntApp loadByRid(Long Rid) throws BusinessException;
   
   /**
    * �������뵥��
    * @return
    * @throws BusinessException
    */
   public String createApplyNo()throws BusinessException;
   
   /**
    * ����ר������RID����Ա���Ͳ�ѯ�����Ա����
    * @param Rid
    * @param personType
    * @return
    * @throws BusinessException
    */
   public AcntPersonApp loadByRidFromPersonApp(Long Rid, String personType) throws BusinessException;
   
   /**
    * ����ר�������Rid�ͼ����������Ͳ�ѯ��ؼ�������
    * @param Rid
    * @param Kind
    * @return
    * @throws BusinessException
    */
   public List listByRidKindFromTechInfoApp(Long Rid, String Kind) throws BusinessException;
   
   /**
    * ����ר������Rid�������������ͺͼ������ϵĴ����ȡ��������
    * @param Rid
    * @param Kind
    * @param Code
    * @return
    * @throws BusinessException
    */
   public AcntTechinfoApp loadByRidKindCodeFromTechInfoApp(Long Rid, String Kind, String Code) throws BusinessException;
   
   /**
    * ����ר������Rid�������������ͻ�ȡ��������
    * @param Rid
    * @param Kind
    * @return
    * @throws BusinessException
    */
   public AcntTechinfoApp loadByRidKindFromTechInfoApp(Long Rid, String Kind) throws BusinessException;
   
   /**
    * ����ר������Rid�������������ͺͼ������ϵĴ���ɾ����������
    * @param Rid
    * @param Kind
    * @param Code
    * @throws BusinessException
    */
   public void deleteFromTechInfoApp(Long Rid, String Kind, String Code) throws BusinessException;
   
   /**
    * ����ר�������Ridɾ����ؼ�������
    * @param Rid
    * @throws BusinessException
    */
   public void deleteAllRelatedFromTechInfoApp(Long Rid) throws BusinessException;
   
   /**
    * ���漼������
    * @param techInfoApp
    * @throws BusinessException
    */
   public void saveInTechInfoApp(AcntTechinfoApp techInfoApp) throws BusinessException;
   
   /**
    * ���¼�������
    * @param techInfoApp
    * @throws BusinessException
    */
   public void updateTechInfoAppText(AcntTechinfoApp techInfoApp) throws BusinessException;
   
   /**
    * ר�������Rid����ϵ�˵����Ͳ�ѯ�ͻ�����
    * @param Rid
    * @param Type
    * @return
    * @throws BusinessException
    */
   public AcntCustContactorApp loadByRidTypeFromAcntCustContactorApp(Long Rid, String Type) throws BusinessException;
   
   /**
    * ���¿ͻ�����
    * @param acntCustContactorApp
    * @throws BusinessException
    */
   public void updateCustomerInfoApp(AcntCustContactorApp acntCustContactorApp) throws BusinessException;
   
   /**
    * ����ͻ�����
    * @param acntCustContactorApp
    * @throws BusinessException
    */
   public void saveCustomerInfoApp(AcntCustContactorApp acntCustContactorApp) throws BusinessException;
   
   /**
    * ����ר�������Ridɾ����ؿͻ�����
    * @param Rid
    * @throws BusinessException
    */
   public void deleteAllRelatedFromCustomerInfoApp(Long Rid) throws BusinessException;
   
   /**
    * �����������ͺ�ר����������ѯר������
    * @param applicationType
    * @param acntId
    * @return
    * @throws BusinessException
    */
   public AcntApp loadByTypeAcntId(String applicationType, String acntId) throws BusinessException;
   
   public List loadByType(String applicationType) throws BusinessException;
   
   /**
    * �����������ͺ�ר����������ѯ���ύ��ר������
    * @param applicationType
    * @param acntId
    * @return
    * @throws BusinessException
    */
   public AcntApp loadCloseByTypeAcntId(String applicationType, String acntId) throws BusinessException;
   
   /**
    * �г��������ύ��ר������
    * @param isAcnt
    * @return
    * @throws BusinessException
    */
   public List listAllProjectApp(String isAcnt) throws BusinessException;
   
   /**
    * ����ר������Rid��ѯ��ؼ�������
    * @param Rid
    * @return
    * @throws BusinessException
    */
   public List listByRidFromTechInfoApp(Long Rid) throws BusinessException;
   
   /**
    * ����ר������Rid��ѯ��ؿͻ�����
    * @param Rid
    * @return
    * @throws BusinessException
    */
   public List listByRidFromCustomerInfoApp(Long Rid) throws BusinessException;
   
   /**
    * ����ר������Rid��ѯ�����Ա����
    * @param Rid
    * @return
    * @throws BusinessException
    */
   public List listByRidFromPersonApp(Long Rid) throws BusinessException;
   
   /**
    * ����ר�����������Ա����
    * @param acntPersonApp
    * @throws BusinessException
    */
   public void saveAcntPersonApp(AcntPersonApp acntPersonApp) throws BusinessException;
   
   /**
    * ����ר�����������Ա����
    * @param acntPersonApp
    * @throws BusinessException
    */
   public void updateAcntPersonApp(AcntPersonApp acntPersonApp) throws BusinessException;
   
   /**
    * ����ר�������Ridɾ��ר�����������Ա����
    * @param Rid
    * @throws BusinessException
    */
   public void deleteAcntPersonApp(Long Rid) throws BusinessException;

   /**
    * ����ר�����루Rid��ɾ��ĳר�����������������Ա����
    * @param Rid
    * @throws BusinessException
    */
   public void deleteAllRelatedFromPersonApp(Long Rid) throws BusinessException;
   
  
   /**
    * ���ݴ�������ͻ�ȡ���ύ��ר������������
    * @param acntId
    * @param isAcnt
    * @return
    * @throws BusinessException
    */
   public AcntApp loadByAcntIdAndStatus(String acntId, String isAcnt) throws BusinessException;
}
