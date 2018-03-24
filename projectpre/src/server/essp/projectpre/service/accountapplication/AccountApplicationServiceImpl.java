package server.essp.projectpre.service.accountapplication;

import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.util.ArrayList;
import java.util.List;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import server.essp.projectpre.db.AcntApp;
import server.essp.projectpre.db.AcntCustContactorApp;
import server.essp.projectpre.db.AcntPersonApp;
import server.essp.projectpre.db.AcntTechinfoApp;
import server.essp.projectpre.service.constant.Constant;
import server.framework.common.BusinessException;
import server.framework.hibernate.HBComAccess;
import server.framework.logic.AbstractBusinessLogic;

public class AccountApplicationServiceImpl extends AbstractBusinessLogic
        implements IAccountApplicationService {

    /**
     * ��������ѯ������AcntApp���л�ȡ���������¼
     * 
     * @param loginId:
     *            ��¼��ID
     * @param applicationType:��������(project
     *            code,deparment code)
     * @param applicationStatus:�����״̬��δ�ύ�������ˣ����ܽ^���Ѵ_�J��
     * @return List
     */
    public List listAll(String loginId, String applicationType,
            String applicationStatus) throws BusinessException {
        List accountApplicationList = new ArrayList();
        try {
            // �Ȼ��Session
            Session session = this.getDbAccessor().getSession();

            // ����HQL��ѯ����
            Query query = session
                    .createQuery("from AcntApp as t where t.applicationType=:applicationType and t.applicationStatus<>:applicationStatus and t.applicantId=:loginId order by t.rid")
                    .setString("applicationType", applicationType)
                    .setString("applicationStatus", applicationStatus)
                    .setString("loginId", loginId);
            // ��ѯ
            accountApplicationList = query.list();
        } catch (Exception e) {
            // �����������ϲ㼴�ɣ��˴������Ĵ����Ϊ�������ݿ��������
            throw new BusinessException("error.system.db", e);
        }
        return accountApplicationList;
    }

    /**
     * ����ר������
     */
    public void save(AcntApp accountApplication) {
        this.getDbAccessor().save(accountApplication);

    }

    /**
     * ����ר������
     */
    public void update(AcntApp accountApplication) {
       
       this.getDbAccessor().update(accountApplication);
       
    }

    /**
     * ����RIDɾ��ר������
     */
    public void delete(Long Rid) throws BusinessException {
        try {
            if(Rid==null||"".equals(Rid)) return;
            AcntApp acntApp=this.loadByRid(Rid);
            if(acntApp!=null){
                this.getDbAccessor().delete(acntApp);
            }
        }catch(Exception e) {
            throw new BusinessException("error.system.db", e);
        }


    }

    /**
     * ����RID��ȡר������
     */
    public AcntApp loadByRid(Long Rid) throws BusinessException {
        AcntApp acntApp = null;
        try {
            // �Ȼ��Session
            Session session = this.getDbAccessor().getSession();

            // ��ѯ����
            acntApp = (AcntApp) session.createQuery(
                    "from AcntApp as t where t.rid=:Rid").setParameter("Rid",
                    Rid).setMaxResults(1).uniqueResult();

        } catch (Exception e) {
            // �����������ϲ㼴�ɣ��˴������Ĵ����Ϊ�������ݿ��������
            throw new BusinessException("error.system.db", e);
        }
        return acntApp;
    }

    /**
     * ��AcntApp���л�ȡ����Rid��������ֵ��1����ʽ��Ϊ8λ�ַ�����Ϊ���뵥�ŷ���
     */
    public String createApplyNo() throws BusinessException {
    
        String IDFORMATER = "00000000";
        AcntApp acntApp = new AcntApp();
        // maxRid = this.getDbAccessor().assignedRid(acntApp);
        HBComAccess.assignedRid(acntApp);
        DecimalFormat df = new DecimalFormat(IDFORMATER);
        String maxRid = df.format(acntApp.getRid(), new StringBuffer(),
                    new FieldPosition(0)).toString();

        return maxRid;
    }

    /**
     * ����ר������RID����Ա���Ͳ�ѯ�����Ա����
     */
    public AcntPersonApp loadByRidFromPersonApp(Long Rid, String personType)
            throws BusinessException {
        AcntPersonApp acntPersonApp = new AcntPersonApp();
        try {
            // �Ȼ��Session
            Session session = this.getDbAccessor().getSession();

            // ��ѯ����
            acntPersonApp = (AcntPersonApp) session
                    .createQuery(
                            "from AcntPersonApp as t where t.acntAppRid=:Rid and t.personType=:personType")
                    .setParameter("Rid", Rid).setString("personType",
                            personType).setMaxResults(1).uniqueResult();

        } catch (Exception e) {
            // �����������ϲ㼴�ɣ��˴������Ĵ����Ϊ�������ݿ��������
            throw new BusinessException("error.system.db", e);
        }
        return acntPersonApp;
    }

    /**
     * ����ר�������Rid�ͼ����������Ͳ�ѯ��ؼ�������
     */
    public List listByRidKindFromTechInfoApp(Long Rid, String Kind) throws BusinessException {
        List techInfoAppList = new ArrayList();
        try {
            // �Ȼ��Session
            Session session = this.getDbAccessor().getSession();

            // ����HQL��ѯ����
            Query query = session
                    .createQuery(
                            "from AcntTechinfoApp as t where t.id.acntAppRid=:Rid and t.id.kind=:Kind")
                    .setParameter("Rid", Rid)
                    .setString(
                            "Kind", Kind);
            // ��ѯ
            techInfoAppList = query.list();
        } catch (Exception e) {
            // �����������ϲ㼴�ɣ��˴������Ĵ����Ϊ�������ݿ��������
            throw new BusinessException("error.system.db", e);
        }
        return techInfoAppList;
        
    }

    /**
     * ����ר������Rid�������������ͺͼ������ϵĴ����ȡ��������
     */
    public AcntTechinfoApp loadByRidKindCodeFromTechInfoApp(Long Rid, String Kind, String Code) throws BusinessException{
        AcntTechinfoApp techInfoApp = new AcntTechinfoApp();
        try {
            Session session = this.getDbAccessor().getSession();

            techInfoApp = (AcntTechinfoApp) session
                    .createQuery("from AcntTechinfoApp as t where t.id.acntAppRid=:Rid and t.id.kind=:Kind and t.id.code=:Code")
                    .setParameter("Rid", Rid)
                    .setString("Kind", Kind)
                    .setString("Code", Code)
                    .setMaxResults(1)
                    .uniqueResult();
        } catch (Exception e) {
            throw new BusinessException("error.system.db", e);
        }
        return techInfoApp;
    }

    /**
     * ����ר������Rid�������������ͺͼ������ϵĴ���ɾ����������
     */
    public void deleteFromTechInfoApp(Long Rid, String Kind, String Code) throws BusinessException {
        try {
            if(Rid==null||"".equals(Rid)||Kind==null||"".equals(Kind)||Code==null||"".equals(Code)) return;
            AcntTechinfoApp techInfoApp =this.loadByRidKindCodeFromTechInfoApp(Rid, Kind, Code);
            if(techInfoApp!=null){
                this.getDbAccessor().delete(techInfoApp);
            }
        }catch(Exception e) {
            throw new BusinessException("error.system.db", e);
        }
        
    }

    /**
     * ���漼������
     */
    public void saveInTechInfoApp(AcntTechinfoApp techInfoApp) throws BusinessException {
        this.getDbAccessor().save(techInfoApp);
        
    }

    /**
     * ���¼�������
     */
    public void updateTechInfoAppText(AcntTechinfoApp techInfoApp) throws BusinessException {
        this.getDbAccessor().update(techInfoApp);
        
    }

    /**
     * ר�������Rid����ϵ�˵����Ͳ�ѯ�ͻ�����
     */
    public AcntCustContactorApp loadByRidTypeFromAcntCustContactorApp(Long Rid, String Type) throws BusinessException {
        AcntCustContactorApp acntCustContactorApp = new AcntCustContactorApp();
        try {
            // �Ȼ��Session
            Session session = this.getDbAccessor().getSession();

            // ��ѯ����
            acntCustContactorApp = (AcntCustContactorApp) session
                    .createQuery("from AcntCustContactorApp as t where t.acntAppRid=:Rid and t.contactorType=:Type")
                    .setParameter("Rid", Rid) 
                    .setString("Type", Type)
                    .setMaxResults(1)
                    .uniqueResult();
       
        } catch (Exception e) {
            // �����������ϲ㼴�ɣ��˴������Ĵ����Ϊ�������ݿ��������
            throw new BusinessException("error.system.db", e);
        }
        return acntCustContactorApp;
    }

    /**
     * ���¿ͻ�����
     */
    public void updateCustomerInfoApp(AcntCustContactorApp acntCustContactorApp) throws BusinessException {
        this.getDbAccessor().update(acntCustContactorApp);
        
    }

    /**
     * ����ͻ�����
     */
    public void saveCustomerInfoApp(AcntCustContactorApp acntCustContactorApp) throws BusinessException {
        this.getDbAccessor().save(acntCustContactorApp);
        
    }

    /**
     * ����ר�������Ridɾ����ؼ�������
     */
    public void deleteAllRelatedFromTechInfoApp(Long Rid) throws BusinessException {
        try{
          if(Rid!=null){
              this.getDbAccessor().getSession().delete("from AcntTechinfoApp as t where t.id.acntAppRid='"+Rid+"'");
                                  
          }
        } catch (Exception e) {
            throw new BusinessException("error.system.db", e);
        }
        
    }

    /**
     * ����ר�������Ridɾ����ؿͻ�����
     */
    public void deleteAllRelatedFromCustomerInfoApp(Long Rid) throws BusinessException {
        try{
            if(Rid!=null){
                this.getDbAccessor().getSession().delete("from AcntCustContactorApp as t where t.acntAppRid='"+Rid+"'");
                                    
            }
          } catch (Exception e) {
              throw new BusinessException("error.system.db", e);
          }
        
    }

    /**
     * �����������ͺ�ר����������ѯר������
     */
    public AcntApp loadByTypeAcntId(String applicationType, String acntId) throws BusinessException {
          AcntApp acntApp = new AcntApp();
          String applicationStatus = Constant.CONFIRMED;
          try {
              Session session = this.getDbAccessor().getSession();
              
              acntApp = (AcntApp) session.createQuery("from AcntApp as t where t.applicationType=:applicationType and t.acntId=:acntId and t.applicationStatus<>:applicationStatus")
                                         .setString("applicationType", applicationType)
                                         .setString("acntId", acntId)
                                         .setString("applicationStatus", applicationStatus)
                                         .setMaxResults(1)
                                         .uniqueResult();
       
          } catch (Exception e) {
              throw new BusinessException("error.system.db", e);
          }
        return acntApp;
    }
    /**
     * �����������ͺ�ר����������ѯר������
     */
    public List loadByType(String applicationType) throws BusinessException {
          List list;
          String applicationStatus = Constant.CONFIRMED;
          try {
              Session session = this.getDbAccessor().getSession();
              
               list = session.createQuery("from AcntApp as t where t.applicationType=:applicationType" +
              		" and t.applicationStatus<>:applicationStatus")
                                         .setString("applicationType",applicationType)
                                         .setString("applicationStatus", applicationStatus)
                                         .list();
       
          } catch (Exception e) {
              throw new BusinessException("error.system.db", e);
          }
        return list;
    }
    
    /**
     * �г��������ύ��ר������
     */
    public List listAllProjectApp(String isAcnt) throws BusinessException {
       List acntAppList = new ArrayList();
       String applicationStatus = Constant.SUBMIT;
       try {
           Session session =this.getDbAccessor().getSession();
           
           Query query = session.createQuery("from AcntApp as t where t.isAcnt=:isAcnt and t.applicationStatus=:applicationStatus order by t.rid")
                                .setString("isAcnt", isAcnt)
                                .setString("applicationStatus", applicationStatus);
                                
           acntAppList = query.list();
           
       } catch (Exception e) {
           throw new BusinessException("error.system.db", e);
       }
        return acntAppList;
    }

    /**
     * ����ר������Rid��ѯ��ؼ�������
     */
    public List listByRidFromTechInfoApp(Long Rid) throws BusinessException {
        List techInfoAppList = new ArrayList();
        try {
            // �Ȼ��Session
            Session session = this.getDbAccessor().getSession();

            // ����HQL��ѯ����
            Query query = session
                    .createQuery(
                            "from AcntTechinfoApp as t where t.id.acntAppRid=:Rid")
                    .setParameter("Rid", Rid);
            // ��ѯ
            techInfoAppList = query.list();
        } catch (Exception e) {
            // �����������ϲ㼴�ɣ��˴������Ĵ����Ϊ�������ݿ��������
            throw new BusinessException("error.system.db", e);
        }
        return techInfoAppList;
    }

    /**
     * ����ר������Rid��ѯ��ؿͻ�����
     */
    public List listByRidFromCustomerInfoApp(Long Rid) throws BusinessException {
        List customerInfoApp = new ArrayList();
        try {
            Session session = this.getDbAccessor().getSession();

            // ����HQL��ѯ����
            Query query = session
                    .createQuery(
                            "from AcntCustContactorApp as t where t.acntAppRid=:Rid")
                    .setParameter("Rid", Rid);
            // ��ѯ
            customerInfoApp = query.list();
            
            
            
        } catch (Exception e) {
            throw new BusinessException("error.system.db", e);
        }
        
        
        
        return customerInfoApp;
    }

    /**
     * ����ר������Rid��ѯ�����Ա����
     */
    public List listByRidFromPersonApp(Long Rid) throws BusinessException {
        List personAppList = new ArrayList();
        try {
            Session session = this.getDbAccessor().getSession();

            // ����HQL��ѯ����
            Query query = session
                    .createQuery(
                            "from AcntPersonApp as t where t.acntAppRid=:Rid")
                    .setParameter("Rid", Rid);
            // ��ѯ
            personAppList = query.list();
            
            
        } catch (Exception e) {
            throw new BusinessException("error.system.db", e);
        }
        return personAppList;
    }

    /**
     * ����ר�����������Ա����
     */
    public void saveAcntPersonApp(AcntPersonApp acntPersonApp) throws BusinessException {
        this.getDbAccessor().save(acntPersonApp);
        
    }

    /**
     * ����ר�����������Ա����
     */
    public void updateAcntPersonApp(AcntPersonApp acntPersonApp) throws BusinessException {
        this.getDbAccessor().update(acntPersonApp);
        
    }


    /**
     * ����ר�������Ridɾ��ר�����������Ա����
     */
    public void deleteAcntPersonApp(Long Rid) throws BusinessException {
        try{
            if(Rid!=null){                                     
                this.getDbAccessor().getSession().delete("from AcntPersonApp as t where t.acntAppRid='"+Rid+"'");
                                    
            }
          } catch (Exception e) {
              throw new BusinessException("error.system.db", e);
          }
        
    }

    /**
     * ����ר�����루Rid��ɾ��ĳר�����������������Ա����
     */
    public void deleteAllRelatedFromPersonApp(Long Rid) throws BusinessException {
        try{
            if(Rid!=null){
                this.getDbAccessor().getSession().delete("from AcntPersonApp as t where t.acntAppRid='"+Rid+"'");
                                    
            }
          } catch (Exception e) {
              throw new BusinessException("error.system.db", e);
          }
        
    }



    /**
     * �����������ͺ�ר����������ѯ���ύ��ר������
     */
    public AcntApp loadCloseByTypeAcntId(String applicationType, String acntId) throws BusinessException {
        AcntApp acntApp = new AcntApp();
        try {
            Session session = this.getDbAccessor().getSession();
            
            acntApp = (AcntApp) session.createQuery("from AcntApp as t where t.applicationType=:applicationType and t.acntId=:acntId and t.applicationStatus='Submitted'")
                                       .setString("applicationType", applicationType)
                                       .setString("acntId", acntId)
                                       .setMaxResults(1)
                                       .uniqueResult();
     
        } catch (Exception e) {
            throw new BusinessException("error.system.db", e);
        }
        
        
        return acntApp;
    }


    /**
     * ���ݴ�������ͻ�ȡ���ύ��ר��������������
     */
    public AcntApp loadByAcntIdAndStatus(String acntId, String isAcnt) throws BusinessException {
        AcntApp acntApp = new AcntApp();
        String status="Submitted";
        try{
            Session session = this.getDbAccessor().getSession();
            
            acntApp = (AcntApp) session.createQuery("from AcntApp as t where t.acntId=:acntId and  t.isAcnt=:isAcnt and t.applicationStatus=:status")
                          .setString("acntId", acntId)
                          .setString("isAcnt", isAcnt)
                          .setString("status",status)
                          .setMaxResults(1)
                          .uniqueResult();
                           
            
        } catch (Exception e) {
            throw new BusinessException("error.system.db", e);
        }
        return acntApp;
    }
    /**
     * ����ר������Rid�������������ͻ�ȡ��������
     */
    public AcntTechinfoApp loadByRidKindFromTechInfoApp(Long Rid, String Kind) throws BusinessException {
        AcntTechinfoApp techInfoApp = new AcntTechinfoApp();
        try {
            Session session = this.getDbAccessor().getSession();

            techInfoApp = (AcntTechinfoApp) session
                    .createQuery("from AcntTechinfoApp as t where t.id.acntAppRid=:Rid and t.id.kind=:Kind")
                    .setParameter("Rid", Rid)
                    .setString("Kind", Kind)
                    .setMaxResults(1)
                    .uniqueResult();
        } catch (Exception e) {
            throw new BusinessException("error.system.db", e);
        }
        return techInfoApp;
    }
   

}
