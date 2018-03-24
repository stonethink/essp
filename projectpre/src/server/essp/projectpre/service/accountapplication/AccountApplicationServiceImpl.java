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
     * 按给定查询条件从AcntApp表中获取所有申请记录
     * 
     * @param loginId:
     *            登录者ID
     * @param applicationType:申请类型(project
     *            code,deparment code)
     * @param applicationStatus:申请的状态（未提交，待審核，被拒絕，已確認）
     * @return List
     */
    public List listAll(String loginId, String applicationType,
            String applicationStatus) throws BusinessException {
        List accountApplicationList = new ArrayList();
        try {
            // 先获得Session
            Session session = this.getDbAccessor().getSession();

            // 创建HQL查询对象
            Query query = session
                    .createQuery("from AcntApp as t where t.applicationType=:applicationType and t.applicationStatus<>:applicationStatus and t.applicantId=:loginId order by t.rid")
                    .setString("applicationType", applicationType)
                    .setString("applicationStatus", applicationStatus)
                    .setString("loginId", loginId);
            // 查询
            accountApplicationList = query.list();
        } catch (Exception e) {
            // 将错误抛至上层即可，此处产生的错误均为报出数据库操作错误
            throw new BusinessException("error.system.db", e);
        }
        return accountApplicationList;
    }

    /**
     * 保存专案申请
     */
    public void save(AcntApp accountApplication) {
        this.getDbAccessor().save(accountApplication);

    }

    /**
     * 更新专案申请
     */
    public void update(AcntApp accountApplication) {
       
       this.getDbAccessor().update(accountApplication);
       
    }

    /**
     * 根据RID删除专案申请
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
     * 根据RID获取专案申请
     */
    public AcntApp loadByRid(Long Rid) throws BusinessException {
        AcntApp acntApp = null;
        try {
            // 先获得Session
            Session session = this.getDbAccessor().getSession();

            // 查询对象
            acntApp = (AcntApp) session.createQuery(
                    "from AcntApp as t where t.rid=:Rid").setParameter("Rid",
                    Rid).setMaxResults(1).uniqueResult();

        } catch (Exception e) {
            // 将错误抛至上层即可，此处产生的错误均为报出数据库操作错误
            throw new BusinessException("error.system.db", e);
        }
        return acntApp;
    }

    /**
     * 从AcntApp表中获取最大的Rid，并将此值加1，格式化为8位字符串作为申请单号返回
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
     * 根据专案申请RID和人员类型查询相关人员资料
     */
    public AcntPersonApp loadByRidFromPersonApp(Long Rid, String personType)
            throws BusinessException {
        AcntPersonApp acntPersonApp = new AcntPersonApp();
        try {
            // 先获得Session
            Session session = this.getDbAccessor().getSession();

            // 查询对象
            acntPersonApp = (AcntPersonApp) session
                    .createQuery(
                            "from AcntPersonApp as t where t.acntAppRid=:Rid and t.personType=:personType")
                    .setParameter("Rid", Rid).setString("personType",
                            personType).setMaxResults(1).uniqueResult();

        } catch (Exception e) {
            // 将错误抛至上层即可，此处产生的错误均为报出数据库操作错误
            throw new BusinessException("error.system.db", e);
        }
        return acntPersonApp;
    }

    /**
     * 根据专案申请的Rid和技术资料类型查询相关技术资料
     */
    public List listByRidKindFromTechInfoApp(Long Rid, String Kind) throws BusinessException {
        List techInfoAppList = new ArrayList();
        try {
            // 先获得Session
            Session session = this.getDbAccessor().getSession();

            // 创建HQL查询对象
            Query query = session
                    .createQuery(
                            "from AcntTechinfoApp as t where t.id.acntAppRid=:Rid and t.id.kind=:Kind")
                    .setParameter("Rid", Rid)
                    .setString(
                            "Kind", Kind);
            // 查询
            techInfoAppList = query.list();
        } catch (Exception e) {
            // 将错误抛至上层即可，此处产生的错误均为报出数据库操作错误
            throw new BusinessException("error.system.db", e);
        }
        return techInfoAppList;
        
    }

    /**
     * 根据专案申请Rid、技术资料类型和技术资料的代码获取技术资料
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
     * 根据专案申请Rid、技术资料类型和技术资料的代码删除技术资料
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
     * 保存技术资料
     */
    public void saveInTechInfoApp(AcntTechinfoApp techInfoApp) throws BusinessException {
        this.getDbAccessor().save(techInfoApp);
        
    }

    /**
     * 更新技术资料
     */
    public void updateTechInfoAppText(AcntTechinfoApp techInfoApp) throws BusinessException {
        this.getDbAccessor().update(techInfoApp);
        
    }

    /**
     * 专案申请的Rid和联系人的类型查询客户资料
     */
    public AcntCustContactorApp loadByRidTypeFromAcntCustContactorApp(Long Rid, String Type) throws BusinessException {
        AcntCustContactorApp acntCustContactorApp = new AcntCustContactorApp();
        try {
            // 先获得Session
            Session session = this.getDbAccessor().getSession();

            // 查询对象
            acntCustContactorApp = (AcntCustContactorApp) session
                    .createQuery("from AcntCustContactorApp as t where t.acntAppRid=:Rid and t.contactorType=:Type")
                    .setParameter("Rid", Rid) 
                    .setString("Type", Type)
                    .setMaxResults(1)
                    .uniqueResult();
       
        } catch (Exception e) {
            // 将错误抛至上层即可，此处产生的错误均为报出数据库操作错误
            throw new BusinessException("error.system.db", e);
        }
        return acntCustContactorApp;
    }

    /**
     * 更新客户资料
     */
    public void updateCustomerInfoApp(AcntCustContactorApp acntCustContactorApp) throws BusinessException {
        this.getDbAccessor().update(acntCustContactorApp);
        
    }

    /**
     * 保存客户资料
     */
    public void saveCustomerInfoApp(AcntCustContactorApp acntCustContactorApp) throws BusinessException {
        this.getDbAccessor().save(acntCustContactorApp);
        
    }

    /**
     * 根据专案申请的Rid删除相关技术资料
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
     * 根据专案申请的Rid删除相关客户资料
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
     * 根据申请类型和专案申请代码查询专案申请
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
     * 根据申请类型和专案申请代码查询专案申请
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
     * 列出所有已提交的专案申请
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
     * 根据专案申请Rid查询相关技术资料
     */
    public List listByRidFromTechInfoApp(Long Rid) throws BusinessException {
        List techInfoAppList = new ArrayList();
        try {
            // 先获得Session
            Session session = this.getDbAccessor().getSession();

            // 创建HQL查询对象
            Query query = session
                    .createQuery(
                            "from AcntTechinfoApp as t where t.id.acntAppRid=:Rid")
                    .setParameter("Rid", Rid);
            // 查询
            techInfoAppList = query.list();
        } catch (Exception e) {
            // 将错误抛至上层即可，此处产生的错误均为报出数据库操作错误
            throw new BusinessException("error.system.db", e);
        }
        return techInfoAppList;
    }

    /**
     * 根据专案申请Rid查询相关客户资料
     */
    public List listByRidFromCustomerInfoApp(Long Rid) throws BusinessException {
        List customerInfoApp = new ArrayList();
        try {
            Session session = this.getDbAccessor().getSession();

            // 创建HQL查询对象
            Query query = session
                    .createQuery(
                            "from AcntCustContactorApp as t where t.acntAppRid=:Rid")
                    .setParameter("Rid", Rid);
            // 查询
            customerInfoApp = query.list();
            
            
            
        } catch (Exception e) {
            throw new BusinessException("error.system.db", e);
        }
        
        
        
        return customerInfoApp;
    }

    /**
     * 根据专案申请Rid查询相关人员资料
     */
    public List listByRidFromPersonApp(Long Rid) throws BusinessException {
        List personAppList = new ArrayList();
        try {
            Session session = this.getDbAccessor().getSession();

            // 创建HQL查询对象
            Query query = session
                    .createQuery(
                            "from AcntPersonApp as t where t.acntAppRid=:Rid")
                    .setParameter("Rid", Rid);
            // 查询
            personAppList = query.list();
            
            
        } catch (Exception e) {
            throw new BusinessException("error.system.db", e);
        }
        return personAppList;
    }

    /**
     * 保存专案申请相关人员资料
     */
    public void saveAcntPersonApp(AcntPersonApp acntPersonApp) throws BusinessException {
        this.getDbAccessor().save(acntPersonApp);
        
    }

    /**
     * 更新专案申请相关人员资料
     */
    public void updateAcntPersonApp(AcntPersonApp acntPersonApp) throws BusinessException {
        this.getDbAccessor().update(acntPersonApp);
        
    }


    /**
     * 根据专案申请的Rid删除专案申请相关人员资料
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
     * 根据专案申请（Rid）删除某专案申请下所有相关人员资料
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
     * 根据申请类型和专案申请代码查询已提交的专案申请
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
     * 根据代码和类型获取已提交的专案或部门申请资料
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
     * 根据专案申请Rid、技术资料类型获取技术资料
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
