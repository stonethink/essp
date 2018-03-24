package server.essp.projectpre.service.accountapplication;

import java.util.List;
import server.essp.projectpre.db.AcntApp;
import server.essp.projectpre.db.AcntCustContactorApp;
import server.essp.projectpre.db.AcntPersonApp;
import server.essp.projectpre.db.AcntTechinfoApp;
import server.framework.common.BusinessException;

public interface IAccountApplicationService {
	
   /**
    * 按给定查询条件获取所有申请记录：
    * 1：loginId 登录者ID
    * 2: applicationType 申请类型(project code,deparment code)
    * 3：applicationStatus 申请的状态（待審核，被拒絕，已確認）
    * @return List
    * @throws BusinessException
    */
   public List listAll(String loginId,String applicationType,String applicationStatus) throws BusinessException;
   
   /**
    * 保存专案记录
    * @param accountApplication
    */
   public void save(AcntApp accountApplication);
   
   /**
    * 更新专案记录
    * @param accountApplication
    */
   public void update(AcntApp accountApplication);
   
   
   /**
    * 删除专案记录
    * @param Rid
    * @throws BusinessException
    */
   public void delete(Long Rid)throws BusinessException ;
   
   /**
    * 根据主键获取专案记录
    * @param Rid
    * @return
    * @throws BusinessException
    */
   public AcntApp loadByRid(Long Rid) throws BusinessException;
   
   /**
    * 创建申请单号
    * @return
    * @throws BusinessException
    */
   public String createApplyNo()throws BusinessException;
   
   /**
    * 根据专案申请RID和人员类型查询相关人员资料
    * @param Rid
    * @param personType
    * @return
    * @throws BusinessException
    */
   public AcntPersonApp loadByRidFromPersonApp(Long Rid, String personType) throws BusinessException;
   
   /**
    * 根据专案申请的Rid和技术资料类型查询相关技术资料
    * @param Rid
    * @param Kind
    * @return
    * @throws BusinessException
    */
   public List listByRidKindFromTechInfoApp(Long Rid, String Kind) throws BusinessException;
   
   /**
    * 根据专案申请Rid、技术资料类型和技术资料的代码获取技术资料
    * @param Rid
    * @param Kind
    * @param Code
    * @return
    * @throws BusinessException
    */
   public AcntTechinfoApp loadByRidKindCodeFromTechInfoApp(Long Rid, String Kind, String Code) throws BusinessException;
   
   /**
    * 根据专案申请Rid、技术资料类型获取技术资料
    * @param Rid
    * @param Kind
    * @return
    * @throws BusinessException
    */
   public AcntTechinfoApp loadByRidKindFromTechInfoApp(Long Rid, String Kind) throws BusinessException;
   
   /**
    * 根据专案申请Rid、技术资料类型和技术资料的代码删除技术资料
    * @param Rid
    * @param Kind
    * @param Code
    * @throws BusinessException
    */
   public void deleteFromTechInfoApp(Long Rid, String Kind, String Code) throws BusinessException;
   
   /**
    * 根据专案申请的Rid删除相关技术资料
    * @param Rid
    * @throws BusinessException
    */
   public void deleteAllRelatedFromTechInfoApp(Long Rid) throws BusinessException;
   
   /**
    * 保存技术资料
    * @param techInfoApp
    * @throws BusinessException
    */
   public void saveInTechInfoApp(AcntTechinfoApp techInfoApp) throws BusinessException;
   
   /**
    * 更新技术资料
    * @param techInfoApp
    * @throws BusinessException
    */
   public void updateTechInfoAppText(AcntTechinfoApp techInfoApp) throws BusinessException;
   
   /**
    * 专案申请的Rid和联系人的类型查询客户资料
    * @param Rid
    * @param Type
    * @return
    * @throws BusinessException
    */
   public AcntCustContactorApp loadByRidTypeFromAcntCustContactorApp(Long Rid, String Type) throws BusinessException;
   
   /**
    * 更新客户资料
    * @param acntCustContactorApp
    * @throws BusinessException
    */
   public void updateCustomerInfoApp(AcntCustContactorApp acntCustContactorApp) throws BusinessException;
   
   /**
    * 保存客户资料
    * @param acntCustContactorApp
    * @throws BusinessException
    */
   public void saveCustomerInfoApp(AcntCustContactorApp acntCustContactorApp) throws BusinessException;
   
   /**
    * 根据专案申请的Rid删除相关客户资料
    * @param Rid
    * @throws BusinessException
    */
   public void deleteAllRelatedFromCustomerInfoApp(Long Rid) throws BusinessException;
   
   /**
    * 根据申请类型和专案申请代码查询专案申请
    * @param applicationType
    * @param acntId
    * @return
    * @throws BusinessException
    */
   public AcntApp loadByTypeAcntId(String applicationType, String acntId) throws BusinessException;
   
   public List loadByType(String applicationType) throws BusinessException;
   
   /**
    * 根据申请类型和专案申请代码查询已提交的专案申请
    * @param applicationType
    * @param acntId
    * @return
    * @throws BusinessException
    */
   public AcntApp loadCloseByTypeAcntId(String applicationType, String acntId) throws BusinessException;
   
   /**
    * 列出所有已提交的专案申请
    * @param isAcnt
    * @return
    * @throws BusinessException
    */
   public List listAllProjectApp(String isAcnt) throws BusinessException;
   
   /**
    * 根据专案申请Rid查询相关技术资料
    * @param Rid
    * @return
    * @throws BusinessException
    */
   public List listByRidFromTechInfoApp(Long Rid) throws BusinessException;
   
   /**
    * 根据专案申请Rid查询相关客户资料
    * @param Rid
    * @return
    * @throws BusinessException
    */
   public List listByRidFromCustomerInfoApp(Long Rid) throws BusinessException;
   
   /**
    * 根据专案申请Rid查询相关人员资料
    * @param Rid
    * @return
    * @throws BusinessException
    */
   public List listByRidFromPersonApp(Long Rid) throws BusinessException;
   
   /**
    * 保存专案申请相关人员资料
    * @param acntPersonApp
    * @throws BusinessException
    */
   public void saveAcntPersonApp(AcntPersonApp acntPersonApp) throws BusinessException;
   
   /**
    * 更新专案申请相关人员资料
    * @param acntPersonApp
    * @throws BusinessException
    */
   public void updateAcntPersonApp(AcntPersonApp acntPersonApp) throws BusinessException;
   
   /**
    * 根据专案申请的Rid删除专案申请相关人员资料
    * @param Rid
    * @throws BusinessException
    */
   public void deleteAcntPersonApp(Long Rid) throws BusinessException;

   /**
    * 根据专案申请（Rid）删除某专案申请下所有相关人员资料
    * @param Rid
    * @throws BusinessException
    */
   public void deleteAllRelatedFromPersonApp(Long Rid) throws BusinessException;
   
  
   /**
    * 根据代码和类型获取已提交的专案或部门申请资
    * @param acntId
    * @param isAcnt
    * @return
    * @throws BusinessException
    */
   public AcntApp loadByAcntIdAndStatus(String acntId, String isAcnt) throws BusinessException;
}
