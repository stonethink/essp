package server.essp.projectpre.service.account;

import java.util.List;
import java.util.Map;

import server.essp.projectpre.db.Acnt;
import server.essp.projectpre.db.AcntCustContactor;
import server.essp.projectpre.db.AcntPerson;
import server.essp.projectpre.db.AcntTechinfo;
import server.essp.projectpre.ui.project.confirm.AfProjectConfirm;
import server.essp.projectpre.ui.project.query.AfProjectQuery;
import server.framework.common.BusinessException;

public interface IAccountService {

	   /**
	    * ��ȡ������ȷ�ϵ������¼
	    * @return List
	    * @throws BusinessException
	    */
	   public List listAll(String loginId) throws BusinessException;
	  
	   /**
	    * ��ȡĳ�ˣ�loginId�����������Dept
	    * @param loginId
	    * @return
	    * @throws BusinessException
	    */
       public List listAllDept(String loginId) throws BusinessException;
	   
       /**
        * �г����в���
        * @return
        * @throws BusinessException
        */
       public List listDept() throws BusinessException;
       
       /**
        * ͨ��ҵ��������ѯ����
        * @param achieveBelong
        * @return
        * @throws BusinessException
        */
       public List listDeptByAchieveBelong(String achieveBelong) throws BusinessException;
       /**
        * ͨ���ɱ�������ѯ����
        * @param costBelong
        * @return
        * @throws BusinessException
        */
       public List listDeptByCostBelong(String costBelong) throws BusinessException;
	   /**
	    * ����ר����¼
	    * @param account
	    */
	   public void save(Acnt account);
	   
	   /**
	    * ����ר����¼
	    * @param account
	    */
	   public void update(Acnt account);
	   
	   /**
	    * ����������ȡר����¼
	    * @param Rid
	    * @return
	    * @throws BusinessException
	    */
	   public Acnt loadByRid(Long Rid) throws BusinessException;
	   
	   /**
	    * ����ר������
	    * @return
	    * @throws BusinessException
	    */
	   public String createProjectCode(Long length, Long currentSeq) throws BusinessException;
	   
	   /**
	    * ��ѯר����Ϣ
	    * @return ArrayList
	    * @throws BusinessException
	    */
	   public List queryAccount(String accountId, String accountName, String paramKesys, String personKeys) throws BusinessException;
       
       public Acnt loadByAcntId(String acntId, String isAcnt) throws BusinessException;
       
       /**
        * ͨ��acnt rid��kind��ѯ��������
        * @param Rid
        * @param Kind
        * @return
        * @throws BusinessException
        */
       public List listByRidKindFromTechInfo(Long Rid, String Kind) throws BusinessException;
       
       /**
        * ����ר��RID��ѯר����صļ�������
        * @param Rid
        * @return
        * @throws BusinessException
        */
       public List listByRidFromTechInfo(Long Rid) throws BusinessException;

       /**
        * ͨ��acnt rid��kind��Code��ȡ��������
        * @param Rid
        * @param Kind
        * @param Code
        * @return
        * @throws BusinessException
        */
       public AcntTechinfo loadByRidKindCodeFromTechInfo(Long Rid, String Kind, String Code) throws BusinessException;
       
      
       /**
        * ͨ��acnt rid��Type��ȡ�ͻ�����
        * @param Rid
        * @param Type
        * @return
        * @throws BusinessException
        */
       public AcntCustContactor loadByRidTypeFromAcntCustContactor(Long Rid, String Type) throws BusinessException;
       
       /**
        * ���漼������
        * @param techInfo
        * @throws BusinessException
        */
       public void saveAcntTechinfo(AcntTechinfo techInfo) throws BusinessException;
       
       /**
        * ����ר����������
        * @param techInfo
        * @throws BusinessException
        */
       public void updateAcntTechinfo(AcntTechinfo techInfo) throws BusinessException;
     
       /**
        * ɾ��ĳһ��ר����������
        * @param techInfo
        * @throws BusinessException
        */
       public void deleteAcntTechinfo(AcntTechinfo techInfo) throws BusinessException;
       
       /**
        * ����ͻ�����
        * @param acntCustContactor
        * @throws BusinessException
        */
       public void saveAcntCustContactor(AcntCustContactor acntCustContactor) throws BusinessException;
       
       /**
        * ����ר���ͻ�����
        * @param acntCustContactor
        * @throws BusinessException
        */
       public void updateAcntCustContactor(AcntCustContactor acntCustContactor) throws BusinessException;
       
     
       /**
        * ����ר�������Ա����
        * @param acntPerson
        * @throws BusinessException
        */
       public void saveAcntPerson(AcntPerson acntPerson) throws BusinessException;
       
       /**
        * ����ר�������Ա����
        * @param acntPerson
        * @throws BusinessException
        */
       public void updateAcntPerson(AcntPerson acntPerson) throws BusinessException;
       
       /**
        * ͨ��ר��RID����Ա���Ͳ�ѯ�����Ա����
        * @param Rid
        * @param personType
        * @return
        * @throws BusinessException
        */
       public AcntPerson loadByRidFromPerson(Long Rid, String personType) throws BusinessException;
   
       /**
        * ����ר�������ȡ����ר��
        * @param relParentId
        * @return
        * @throws BusinessException
        */
       public Acnt loadMasterProjectFromAcnt(String relParentId) throws BusinessException;
       
     
     
       /**
        * �г����з��ϴ���������ר��
        * @param af
        * @param loginId
        * @param roleList
        * @return
        * @throws BusinessException
        */
       public Map listAllMacthedConditionAcnt(AfProjectQuery af, String loginId, List roleList) throws BusinessException;
       
       /**
        * ����������ˮ��ר������
        * @param length
        * @param currentSeq
        * @return
        * @throws BusinessException
        */
       public String createYearAcntId(Long length, Long currentSeq) throws BusinessException;

       /**
        * ���ݴ�������ģ����ѯ����
        * 1.���Ŵ���
        * 2.���ż��
     	* 3.ҵ������
        * @param acnt
        * @return List
        * @throws BusinessException
        */
       public List listByKey (Acnt acnt,String applicantName,String BDMName,String
               TCSName,String deptManager) throws BusinessException;
       
       /**
        * ���ݴ�������ģ����ѯ���ϽY���l���Ĕ���
        * @param acnt
        * @return List
        * @throws BusinessException
        */
       public List listCloseData (AfProjectConfirm af,String userLoginId) throws BusinessException;
       
       /**
        * ����RID��ѯ��������
        * @param Rid
        * @return
        * @throws BusinessException
        */
       public Acnt loadByRidInDept(Long Rid) throws BusinessException;
       /**
        * �г����Ա����ר������
        * @param loginId
        * @return
        * @throws BusinessException
        */
       public List listAcntToChange(String loginId) throws BusinessException;
       /**
        * ���ݲ�ѯ����ѯҪ����������
        * @param hqlMap
        * @return
        * @throws BusinessException
        */
       public List queryDataToExport(Map hqlMap) throws BusinessException;
       /**
        * ��ѯ�������ר����ר�������ID
        * @return
        * @throws BusinessException
        */
       public List queryPMIdNearToClose() throws Exception;
       
       /**
        * ��ѯҵ���߲���
        * @return
        * @throws BusinessException
        */
       public List queryBlDept() throws BusinessException;
}
