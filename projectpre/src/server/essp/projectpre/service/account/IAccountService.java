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
	    * 获取所有已确认的申请记录
	    * @return List
	    * @throws BusinessException
	    */
	   public List listAll(String loginId) throws BusinessException;
	  
	   /**
	    * 获取某人（loginId）申请的所有Dept
	    * @param loginId
	    * @return
	    * @throws BusinessException
	    */
       public List listAllDept(String loginId) throws BusinessException;
	   
       /**
        * 列出所有部门
        * @return
        * @throws BusinessException
        */
       public List listDept() throws BusinessException;
       
       /**
        * 通过业绩归属查询部门
        * @param achieveBelong
        * @return
        * @throws BusinessException
        */
       public List listDeptByAchieveBelong(String achieveBelong) throws BusinessException;
       /**
        * 通过成本归属查询部门
        * @param costBelong
        * @return
        * @throws BusinessException
        */
       public List listDeptByCostBelong(String costBelong) throws BusinessException;
	   /**
	    * 保存专案记录
	    * @param account
	    */
	   public void save(Acnt account);
	   
	   /**
	    * 更新专案记录
	    * @param account
	    */
	   public void update(Acnt account);
	   
	   /**
	    * 根据主键获取专案记录
	    * @param Rid
	    * @return
	    * @throws BusinessException
	    */
	   public Acnt loadByRid(Long Rid) throws BusinessException;
	   
	   /**
	    * 创建专案代码
	    * @return
	    * @throws BusinessException
	    */
	   public String createProjectCode(Long length, Long currentSeq) throws BusinessException;
	   
	   /**
	    * 查询专案信息
	    * @return ArrayList
	    * @throws BusinessException
	    */
	   public List queryAccount(String accountId, String accountName, String paramKesys, String personKeys) throws BusinessException;
       
       public Acnt loadByAcntId(String acntId, String isAcnt) throws BusinessException;
       
       /**
        * 通过acnt rid和kind查询技术资料
        * @param Rid
        * @param Kind
        * @return
        * @throws BusinessException
        */
       public List listByRidKindFromTechInfo(Long Rid, String Kind) throws BusinessException;
       
       /**
        * 根据专案RID查询专案相关的技术资料
        * @param Rid
        * @return
        * @throws BusinessException
        */
       public List listByRidFromTechInfo(Long Rid) throws BusinessException;

       /**
        * 通过acnt rid，kind和Code获取技术资料
        * @param Rid
        * @param Kind
        * @param Code
        * @return
        * @throws BusinessException
        */
       public AcntTechinfo loadByRidKindCodeFromTechInfo(Long Rid, String Kind, String Code) throws BusinessException;
       
      
       /**
        * 通过acnt rid和Type获取客户资料
        * @param Rid
        * @param Type
        * @return
        * @throws BusinessException
        */
       public AcntCustContactor loadByRidTypeFromAcntCustContactor(Long Rid, String Type) throws BusinessException;
       
       /**
        * 保存技术资料
        * @param techInfo
        * @throws BusinessException
        */
       public void saveAcntTechinfo(AcntTechinfo techInfo) throws BusinessException;
       
       /**
        * 更新专案技术资料
        * @param techInfo
        * @throws BusinessException
        */
       public void updateAcntTechinfo(AcntTechinfo techInfo) throws BusinessException;
     
       /**
        * 删除某一条专案技术资料
        * @param techInfo
        * @throws BusinessException
        */
       public void deleteAcntTechinfo(AcntTechinfo techInfo) throws BusinessException;
       
       /**
        * 保存客户资料
        * @param acntCustContactor
        * @throws BusinessException
        */
       public void saveAcntCustContactor(AcntCustContactor acntCustContactor) throws BusinessException;
       
       /**
        * 更新专案客户资料
        * @param acntCustContactor
        * @throws BusinessException
        */
       public void updateAcntCustContactor(AcntCustContactor acntCustContactor) throws BusinessException;
       
     
       /**
        * 保存专案相关人员资料
        * @param acntPerson
        * @throws BusinessException
        */
       public void saveAcntPerson(AcntPerson acntPerson) throws BusinessException;
       
       /**
        * 更新专案相关人员资料
        * @param acntPerson
        * @throws BusinessException
        */
       public void updateAcntPerson(AcntPerson acntPerson) throws BusinessException;
       
       /**
        * 通过专案RID和人员类型查询相关人员资料
        * @param Rid
        * @param personType
        * @return
        * @throws BusinessException
        */
       public AcntPerson loadByRidFromPerson(Long Rid, String personType) throws BusinessException;
   
       /**
        * 根据专案代码获取主属专案
        * @param relParentId
        * @return
        * @throws BusinessException
        */
       public Acnt loadMasterProjectFromAcnt(String relParentId) throws BusinessException;
       
     
     
       /**
        * 列出所有符合传入条件的专案
        * @param af
        * @param loginId
        * @param roleList
        * @return
        * @throws BusinessException
        */
       public Map listAllMacthedConditionAcnt(AfProjectQuery af, String loginId, List roleList) throws BusinessException;
       
       /**
        * 生成年月流水号专案代码
        * @param length
        * @param currentSeq
        * @return
        * @throws BusinessException
        */
       public String createYearAcntId(Long length, Long currentSeq) throws BusinessException;

       /**
        * 根据传入条件模糊查询部门
        * 1.部门代码
        * 2.部门简称
     	* 3.业绩归属
        * @param acnt
        * @return List
        * @throws BusinessException
        */
       public List listByKey (Acnt acnt,String applicantName,String BDMName,String
               TCSName,String deptManager) throws BusinessException;
       
       /**
        * 根据传入条件模糊查询符合結案條件的數據
        * @param acnt
        * @return List
        * @throws BusinessException
        */
       public List listCloseData (AfProjectConfirm af,String userLoginId) throws BusinessException;
       
       /**
        * 根据RID查询部门资料
        * @param Rid
        * @return
        * @throws BusinessException
        */
       public Acnt loadByRidInDept(Long Rid) throws BusinessException;
       /**
        * 列出可以变更的专案资料
        * @param loginId
        * @return
        * @throws BusinessException
        */
       public List listAcntToChange(String loginId) throws BusinessException;
       /**
        * 根据查询语句查询要导出的数据
        * @param hqlMap
        * @return
        * @throws BusinessException
        */
       public List queryDataToExport(Map hqlMap) throws BusinessException;
       /**
        * 查询快结束的专案的专案经理的ID
        * @return
        * @throws BusinessException
        */
       public List queryPMIdNearToClose() throws Exception;
       
       /**
        * 查询业务线部门
        * @return
        * @throws BusinessException
        */
       public List queryBlDept() throws BusinessException;
}
