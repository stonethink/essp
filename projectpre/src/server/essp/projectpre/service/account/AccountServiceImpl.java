package server.essp.projectpre.service.account;


import itf.user.IHrQueryUser;

import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import server.essp.projectpre.db.Acnt;
import server.essp.projectpre.db.AcntCustContactor;
import server.essp.projectpre.db.AcntPerson;
import server.essp.projectpre.db.AcntTechinfo;
import server.essp.projectpre.service.constant.Constant;
import server.essp.projectpre.ui.project.confirm.AfProjectConfirm;
import server.essp.projectpre.ui.project.query.AfProjectQuery;
import server.framework.common.BusinessException;
import server.framework.logic.AbstractBusinessLogic;
import c2s.dto.DtoUtil;
import c2s.essp.common.account.IDtoAccount;
import c2s.essp.common.user.DtoRole;
import c2s.essp.common.user.DtoUserBase;

import com.wits.util.StringUtil;
import com.wits.util.comDate;


public class AccountServiceImpl extends AbstractBusinessLogic implements
		IAccountService, IHrQueryUser {

	/**
	 * 从Acnt表中获取当前用户有权限查阅的专案记录
	 */
	public List listAll(String loginId) throws BusinessException {    
        String acntStatus = Constant.NORMAL;
		List acntList = new ArrayList();
        try{
            Session session = this.getDbAccessor().getSession();
            
            Query query = session
             .createQuery("from Acnt as t "+
                          "where t.isAcnt='1' " +
                          "and t.acntStatus=:acntStatus " +
                          "and t.rid in (select c.acntRid " +
                                            "from AcntPerson as c " +
                                            "where (c.personType='Applicant' and c.loginId=:loginId) " +
                                             "or (c.personType='PM' and c.loginId=:loginId)" +
                                             ") " +
                          "order by t.acntId")
            .setString("acntStatus", acntStatus)
            .setString("loginId", loginId);
            acntList = query.list();
            
        } catch ( Exception e) {
            throw new BusinessException("error.system.db", e);
        }
        return acntList;
	}

	/**
	 * 保存专案
	 */
	public void save(Acnt account) {
		this.getDbAccessor().save(account);

	}

	/**
	 * 更新专案
	 */
	public void update(Acnt account) {
		this.getDbAccessor().update(account);

	}


	/**
	 * 根据RID获取专案
	 */
	public Acnt loadByRid(Long Rid) throws BusinessException {
        Acnt acnt = null;
        try {
            // 先获得Session
            Session session = this.getDbAccessor().getSession();

            // 查询对象
            acnt = (Acnt) session.createQuery(
                    "from Acnt as t where t.rid=:Rid").setParameter("Rid",
                    Rid).setMaxResults(1).uniqueResult();

        } catch (Exception e) {
            // 将错误抛至上层即可，此处产生的错误均为报出数据库操作错误
            throw new BusinessException("error.system.db", e);
        }
        return acnt;
    }

	/**
	 * 根据currentSeq（当前流水号）和length（专案代码码数）
	 */
	public String createProjectCode(Long length, Long currentSeq) throws BusinessException {
        String IDFORMATER = "0";
        int i = length.intValue();
        for(int k = 1;k < i;k++) {
            IDFORMATER = IDFORMATER + "0";
        }
        DecimalFormat df = new DecimalFormat(IDFORMATER);
        String tem = String.valueOf((currentSeq.intValue()+1));
        currentSeq = Long.valueOf(tem);
        String newCurrentSeq = df.format(currentSeq, new StringBuffer(),
                                  new FieldPosition(0)).toString();
		return newCurrentSeq;
	}

	/**
	 * 获取某人（loginId）申请的所有Dept
	 */
    public List listAllDept(String loginId) throws BusinessException {
        List accountApplicationList = new ArrayList();
       
        try{
            //先获得Session
           Session session = this.getDbAccessor().getSession();

           // 创建HQL查询对象
           Query query = session
                   .createQuery("from Acnt as t " +
                        "where t.isAcnt='0' " +
                        "and t.rid in " +
                                        "(select c.acntRid " +
                                        "from AcntPerson as c " +
                                        "where (c.personType='Applicant' and c.loginId=:loginId) " +
                                        "or (c.personType='DeptManager' and c.loginId=:loginId)) " +
                        "order by t.acntId")
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
	 * 根据主键获取专案记录
	 * @param Rid
	 * @return
	 * @throws BusinessException
	 */
    public Acnt loadByAcntId(String acntId, String isAcnt) throws BusinessException {
        Acnt acnt = new Acnt();
        try{
            Session session = this.getDbAccessor().getSession();
            
            acnt = (Acnt) session.createQuery("from Acnt as t where t.acntId=:acntId and  t.isAcnt=:isAcnt")
                          .setString("acntId", acntId)
                          .setString("isAcnt", isAcnt)
                          .setMaxResults(1)
                          .uniqueResult();
                           
            
        } catch (Exception e) {
            throw new BusinessException("error.system.db", e);
        }
        return acnt;
    }

    /**
     * 通过acnt rid和kind查询技术资料
     * @param Rid
     * @param Kind
     * @return
     * @throws BusinessException
     */
    public List listByRidKindFromTechInfo(Long Rid, String Kind) throws BusinessException {
        List techInfoList = new ArrayList();
        try {
            // 先获得Session
            Session session = this.getDbAccessor().getSession();

            // 创建HQL查询对象
            Query query = session
                    .createQuery(
                            "from AcntTechinfo as t where t.id.acntRid=:Rid and t.id.kind=:Kind")
                    .setParameter("Rid", Rid)
                    .setString(
                            "Kind", Kind);
            // 查询
            techInfoList = query.list();
        } catch (Exception e) {
            // 将错误抛至上层即可，此处产生的错误均为报出数据库操作错误
            throw new BusinessException("error.system.db", e);
        }
        return techInfoList;
    }

    /**
     * 通过acnt rid，kind和Code获取技术资料
     */
    public AcntTechinfo loadByRidKindCodeFromTechInfo(Long Rid, String Kind, String Code) throws BusinessException {
        AcntTechinfo techInfo = new AcntTechinfo();
        try {
            Session session = this.getDbAccessor().getSession();

            techInfo = (AcntTechinfo) session
                    .createQuery("from AcntTechinfo as t where t.id.acntRid=:Rid and t.id.kind=:Kind and t.id.code=:Code")
                    .setParameter("Rid", Rid)
                    .setString("Kind", Kind)
                    .setString("Code", Code)
                    .setMaxResults(1)
                    .uniqueResult();
        } catch (Exception e) {
            throw new BusinessException("error.system.db", e);
        }
        return techInfo;
    }

    /**
     *  通过acnt rid和Type获取客户资料
     */
    public AcntCustContactor loadByRidTypeFromAcntCustContactor(Long Rid, String Type) throws BusinessException {
        AcntCustContactor acntCustContactor = new AcntCustContactor();
        try {
            // 先获得Session
            Session session = this.getDbAccessor().getSession();

            // 查询对象
            acntCustContactor = (AcntCustContactor) session
                    .createQuery("from AcntCustContactor as t where t.acntRid=:Rid and t.contactorType=:Type")
                    .setParameter("Rid", Rid) 
                    .setString("Type", Type)
                    .setMaxResults(1)
                    .uniqueResult();
       
        } catch (Exception e) {
            // 将错误抛至上层即可，此处产生的错误均为报出数据库操作错误
            throw new BusinessException("error.system.db", e);
        }
        return acntCustContactor;
    }
    
    /**
     * 根据不同条件查询专案
     * 1.专案代码
     * 2.专案简称
     * 3.传入约束条件
     * 4.根据权限约束
     */
    public List queryAccount(String accountId, String accountName, String paramKesys, String personKeys) throws BusinessException {
    	List resultList = new ArrayList();
		String subSqlId = "";
		String subSqlName = "";
		String subSqlParamKeys = "";
		boolean exits = false;
		if (accountId != null && !accountId.equals("")) {
			subSqlId = "lower(Acnt_id) like lower('%" + accountId + "%')";
			exits = true;
		}
		if (accountName != null && !accountName.equals("")) {
			subSqlName = "lower(Acnt_name) like lower('%" + accountName + "%')";
			if (exits) {
				subSqlName = " or " + subSqlName + ")";
				subSqlId = "(" + subSqlId;
			}
			exits = true;
		}
		if (paramKesys != null && !paramKesys.equals("")) {
			subSqlParamKeys = paramKesys;
			if (exits) {
				subSqlParamKeys = " and (" + subSqlParamKeys + ")";
			}
			exits = true;
		}
		String hqlStr = "from Acnt as t";
		if (exits) {
			hqlStr += " where " + subSqlId + subSqlName + subSqlParamKeys;
		}
        if(personKeys!=null&&!personKeys.equals("")){
            if(exits){
                hqlStr += " and t.rid in (select c.acntRid " +
                                            "from AcntPerson as c " +
                                            "where "+personKeys+
                                             ") ";
            } else {
                hqlStr += " where t.rid in (select c.acntRid " +
                                            "from AcntPerson as c " +
                                            "where "+personKeys+
                                             ") ";
            }
        }
		try {
			Session session = this.getDbAccessor().getSession();
			resultList = session.createQuery(hqlStr).list();

		} catch (Exception e) {
			throw new BusinessException("error.system.db", e);
		}
        List newResultList = new ArrayList();
        for(int i = 0;i<resultList.size();i++) {
            Acnt acnt = (Acnt)resultList.get(i);
            VbAcnt vb = new VbAcnt();
            try {
                DtoUtil.copyProperties(vb, acnt);
            } catch (Exception e) {            
                e.printStackTrace();
            }
            String str = acnt.getAcntBrief();
            if(str!=null){
            str = str.replaceAll("\r\n","<br>");
            }
            vb.setAcntBrief(str);
            newResultList.add(vb);
            
        }
		return newResultList;
    }

    /**
     * 保存技术资料
     */
    public void saveAcntTechinfo(AcntTechinfo techInfo) throws BusinessException {
       this.getDbAccessor().save(techInfo);
        
    }

    /**
     * 保存客户资料
     */
    public void saveAcntCustContactor(AcntCustContactor acntCustContactor) throws BusinessException {
        this.getDbAccessor().save(acntCustContactor);
        
    }

    /**
     * 保存专案相关人员资料
     */
    public void saveAcntPerson(AcntPerson acntPerson) throws BusinessException {
        this.getDbAccessor().save(acntPerson);
    }

  

    /**
     * 通过专案RID和人员类型查询相关人员资料
     */
    public AcntPerson loadByRidFromPerson(Long Rid, String personType)
    throws BusinessException {
       AcntPerson acntPerson = new AcntPerson();
    try {
    // 先获得Session
        Session session = this.getDbAccessor().getSession();

    // 查询对象
        acntPerson = (AcntPerson) session
            .createQuery(
                    "from AcntPerson as t where t.acntRid=:Rid and t.personType=:personType")
            .setParameter("Rid", Rid).setString("personType",
                    personType).setMaxResults(1).uniqueResult();

    } catch (Exception e) {
    // 将错误抛至上层即可，此处产生的错误均为报出数据库操作错误
    throw new BusinessException("error.system.db", e);
    }
       return acntPerson;
    }
    
    /**
     * 更新专案相关人员资料
     */
    public void updateAcntPerson(AcntPerson acntPerson) {
        this.getDbAccessor().update(acntPerson);

    }
    
    /**
     * 根据专案代码获取主属专案
     */
    public Acnt loadMasterProjectFromAcnt(String relPrjId) throws BusinessException {
                Acnt acnt = new Acnt();
                
       try {
           Session session = this.getDbAccessor().getSession();

           acnt = (Acnt) session.createQuery(
             "from Acnt as t where t.acntId=:relPrjId")
             .setString("relPrjId", relPrjId)
             .setMaxResults(1).uniqueResult();
          } catch (Exception e) {
              throw new BusinessException("error.system.db", e);
          }
                  return acnt;
   }


    /**
     * 列出所有符合传入条件的专案
     */
    public Map listAllMacthedConditionAcnt(AfProjectQuery af, String loginId, List roleList) throws BusinessException {
       Map resultMap = new HashMap();
       List acntList = new ArrayList();
       StringBuffer hqlBuf = new StringBuffer();
       hqlBuf.append("from QueryAcntView as t where 1=1 and t.isAcnt='1'");
       String acntId = af.getAcntId();
       boolean includeClosedProject = af.getIncludeClosedProject();
       String beginDate = af.getApplicationDateBegin();
       String endDate = af.getApplicationDateEnd();
       String planedStartDate = af.getAcntPlannedStart();
       String planedFinishDate = af.getAcntPlannedFinish();
       Date _beginDate = null;
       Date _endDate = null;
       Date _startDate = null;
       Date _finishDate = null;
       String relParentId = af.getRelParentId();
       String achieveBelong = af.getAchieveBelong();
       String execUnitId = af.getExecUnitId();
       String execSite = af.getExecSite();
       String costAttachBd = af.getCostAttachBd();
       String bizSource = af.getBizSource();
       String customerId = af.getCustomerId();
       String acntAttribute = af.getAcntAttribute();
       String applicant = af.getApplicant();
       String bdLoginId = af.getBDMId();
       String pmLoginId = af.getPMId();
       String projectType = af.getAcntType();
       String productType = af.getProductType();
       String productAttribute = af.getProductProperty();
       String workItem = af.getWorkItem();
       String technicalDomain = af.getTechnicalArea();
       String primaveraAdapted = af.getPrimaveraAdapted();
       String billingBasis = af.getBillingBasis();
       String bizProperty = af.getBizProperty();
       String billType = af.getBillType();
       String relPrjType = af.getRelPrjType();
       String languageKind = "";
       String languageCode = "";
       boolean existBeginDate = false;
       boolean existEndDate = false;
       boolean existPlanedStart = false;
       boolean existPlanedFinish = false;
       if(af.getSupportLanguge()!=null&&!af.getSupportLanguge().equals("")){
         languageKind = af.getSupportLanguge().substring(0, af.getSupportLanguge().indexOf("---"));
         languageCode = af.getSupportLanguge().substring(af.getSupportLanguge().indexOf("---")+3);
        
       }
       if(acntId!=null&&!acntId.equals("")){
           hqlBuf.append(" and lower(t.acntId) like lower('%"+acntId+"%')");
       }
       if(!includeClosedProject){
           hqlBuf.append(" and t.acntStatus='"+Constant.NORMAL+"'");
       }
       if(beginDate!=null&&!beginDate.equals("")){
           hqlBuf.append(" and t.applicationDate>=:beginDate");
           existBeginDate = true;
           Date tmpBDate = comDate.toDate(beginDate);
           Calendar cBDate = Calendar.getInstance();
           cBDate.setTime(tmpBDate);
           cBDate.set(Calendar.HOUR,0);
           cBDate.set(Calendar.MINUTE,0);
           cBDate.set(Calendar.SECOND,0);
           cBDate.set(Calendar.MILLISECOND,0);
           _beginDate = cBDate.getTime();
       } 
       if(endDate!=null&&!endDate.equals("")){
           hqlBuf.append(" and t.applicationDate<=:endDate");
           existEndDate = true;
           Date tmpBDate = comDate.toDate(endDate);
           Calendar cBDate = Calendar.getInstance();
           cBDate.setTime(tmpBDate);
           cBDate.set(Calendar.HOUR,23);
           cBDate.set(Calendar.MINUTE,59);
           cBDate.set(Calendar.SECOND,59);
           cBDate.set(Calendar.MILLISECOND,1000);
           _endDate = cBDate.getTime();
       }
       if(planedStartDate!=null&&!"".equals(planedStartDate)){
           _startDate = comDate.toDate(planedStartDate);
           hqlBuf.append(" and t.acntPlannedStart>=:planedStartDate");
           existPlanedStart = true;
       }
       if(planedFinishDate!=null&&!"".equals(planedFinishDate)){
           _finishDate = comDate.toDate(planedFinishDate);
           hqlBuf.append(" and t.acntPlannedFinish<=:planedFinishDate");
           existPlanedFinish = true;
       }
       if(relParentId!=null&&!relParentId.equals("")){
           hqlBuf.append(" and lower(t.relParentId) like lower('%"+relParentId+"%')");
       }
       if(achieveBelong!=null&&!achieveBelong.equals("")){
           hqlBuf.append(" and lower(t.achieveBelong) like lower('%"+achieveBelong+"%')");
       }
       if(execUnitId!=null&&!execUnitId.equals("")){
           hqlBuf.append(" and t.execUnitId='"+execUnitId+"'");
       }
       if(execSite!=null&&!execSite.equals("")){
           hqlBuf.append(" and t.execSite='"+execSite+"'");
       }
       if(costAttachBd!=null&&!costAttachBd.equals("")){
           hqlBuf.append(" and t.costAttachBd='"+costAttachBd+"'");
       }
       if(bizSource!=null&&!bizSource.equals("")){
           hqlBuf.append(" and t.bizSource='"+bizSource+"'");
       }
       if(primaveraAdapted != null && !"".equals(primaveraAdapted)) {
    	   hqlBuf.append(" and t.primaveraAdapted='"+primaveraAdapted+"'");
       }
       if(billingBasis != null && !"".equals(billingBasis)) {
    	   hqlBuf.append(" and t.billingBasis='"+billingBasis+"'");
       }
       if(bizProperty != null && !"".equals(bizProperty)) {
    	   hqlBuf.append(" and t.bizProperty='"+bizProperty+"'");
       }
       if(billType != null && !"".equals(billType)) {
    	   hqlBuf.append(" and t.billType='"+billType+"'");
       }
       if(relPrjType != null && !"".equals(relPrjType)) {
    	   hqlBuf.append(" and t.relPrjType='"+relPrjType+"'");
       }
       if(customerId!=null&&!customerId.equals("")){
           hqlBuf.append(" and lower(t.cusShort) like lower('%"+customerId+"%')");
       }
       if(acntAttribute!=null&&!acntAttribute.equals("")){
           hqlBuf.append(" and t.acntAttribute='"+acntAttribute+"'");
       }
       if(applicant!=null&&!applicant.equals("")){
           hqlBuf.append(" and lower(t.appName) like lower('%"+applicant+"%')");
       }
       if(bdLoginId!=null&&!bdLoginId.equals("")){
           hqlBuf.append(" and lower(t.bdLoginid) like lower('%"+bdLoginId+"%')");
       }
       if(pmLoginId != null && !pmLoginId.equals("")) {
    	   hqlBuf.append(" and lower(t.pmLoginid) like lower('%"+pmLoginId+"%')");
       }
       if(projectType!=null&&!projectType.equals("")){
           hqlBuf.append(" and t.projectType like '%"+projectType+"%'");
       }
       if(productType!=null&&!productType.equals("")){
           hqlBuf.append(" and t.productType like '%["+productType+"]%'");
       }
       if(productAttribute!=null&&!productAttribute.equals("")){
           hqlBuf.append(" and t.productAttribute like '%["+productAttribute+"]%'");
       }
       if(workItem!=null&&!workItem.equals("")){
           hqlBuf.append(" and t.workItem like '%["+workItem+"]%'");
       }
       if(technicalDomain!=null&&!technicalDomain.equals("")){
           hqlBuf.append(" and t.technicalDomain like '%["+technicalDomain+"]%'");
       }
       if(!languageKind.equals("")){
           if(languageKind.equals(Constant.ORIGINAL_LANGUAGE)){
               hqlBuf.append(" and t.originalLanguage like '%["+languageCode+"]%'");
           } else if(languageKind.equals(Constant.TRANSLATION_LANGUANGE)){
               hqlBuf.append(" and t.translationLanguage like '%["+languageCode+"]%'");
           }
       }
       if(!isProjectOfficeOrAdmin(roleList)){
           hqlBuf.append(" and (t.appLoginid='"+loginId+"' " +
                            "or t.pmLoginid='"+loginId+"' " +
                            "or t.bdLoginid='"+loginId+"' " +
                            "or t.deptLoginid='"+loginId+"')");
           String queryPrivilege = this.getQueryPrivilege(loginId);
           if(!"".equals(queryPrivilege)) {
        	   hqlBuf.append(" and ("+queryPrivilege+")");
           }
       }
       hqlBuf.append(" order by t.acntId");
       try {
           Session session = this.getDbAccessor().getSession();
           
           Query query = session.createQuery(hqlBuf.toString());
           if(existBeginDate){
               query.setDate("beginDate",_beginDate);
               resultMap.put("beginDate",_beginDate);//将日期作为条件返回，以备导出专案资料使用
           }
           if(existEndDate){
               query.setDate("endDate",_endDate);
               resultMap.put("endDate",_endDate);
           }
           if(existPlanedStart){
               query.setDate("planedStartDate", _startDate);
               resultMap.put("planedStartDate",_startDate);
           }
           if(existPlanedFinish){
               query.setDate("planedFinishDate", _finishDate);
               resultMap.put("planedFinishDate",_finishDate);
           }
           resultMap.put("HQL", query.getQueryString());
           acntList = query.list();
       } catch (Exception e) {
           throw new BusinessException("error.system.db", e);
       }
        resultMap.put("acntList", acntList);
        return resultMap;
    }
    /**
     * 根据查询权限设定获取相应的查询权限
     * @param loginId
     * @return
     */
    private String getQueryPrivilege(String loginId){
    	String hql = new String();
    	hql = "select c.acntId,t.vcodePrivilege from AcntQueryPrivilege as t,Acnt as c " +
    			   "where t.acntRid=c.rid and t.orgPrivilege='1' and t.loginId='"+loginId+"'";
    	String queryPrivilege = "";
    	try {
			Session session = this.getDbAccessor().getSession();
			Query query = session.createQuery(hql);
			List list = query.list();
			if(list != null && list.size() > 0) {
				int size = list.size();
				Object[] obj = null;
				String temp = "";
				for(int i = 0;i<size;i++) {
					obj = (Object[])list.get(i);
					if("1".equals((String)obj[1])) {
						temp = "(t.execUnitId='"+(String)obj[0]+"')";
					} else if("0".equals((String)obj[1])) {
						temp = "(t.execUnitId='"+(String)obj[0]+"' and t.execSite<>'V')";
					}
					if(i == 0) {
						queryPrivilege = temp;
					} else {
						queryPrivilege += " or " + temp; 
					}
				} 
				
			}
		} catch (Exception e) {
			throw new BusinessException("error.system.db", e);
		}
    	return queryPrivilege;
    }
    /**
     * 判断是否是ProjectOffice人员或系统管理员
     * @param roleList
     * @return
     */
    private boolean isProjectOfficeOrAdmin(List roleList){
        boolean result = false;
        for(int i=0;i<roleList.size();i++){
            DtoRole role = (DtoRole)roleList.get(i);
            if(role!=null){
                String roleId = role.getRoleId();
                if(roleId.equals("R0000SU00")||roleId.equals("R000APO00")
                        ||roleId.equals("RH00APO00")||roleId.equals("RU00APO00")){
                    result = true;
                }
            }
        }
        return result;
    }
 

   

    /**
     * 生成年月流水号专案代码
     * @param length
     * @param currentSeq
     * @return
     * @throws BusinessException
     */
    public String createYearAcntId(Long length, Long currentSeq) throws BusinessException {
        String newAcntId = null;
        String IDFORMATER = "0";
        int i = length.intValue();
        i = i-2;
        for(int k = 1;k < i;k++) {
            IDFORMATER = IDFORMATER + "0";
        }
        DecimalFormat df = new DecimalFormat(IDFORMATER);
        String tem = String.valueOf((currentSeq.intValue()+1));
        currentSeq = Long.valueOf(tem);
        String newCurrentSeq = df.format(currentSeq, new StringBuffer(),
                                  new FieldPosition(0)).toString();
        
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int currentMonth = Calendar.getInstance().get(Calendar.MONTH);
        currentMonth = currentMonth + 1;
        String newYear = String.valueOf(currentYear);
        newYear = newYear.substring(3);
        String newMonth = String.valueOf(currentMonth);
        switch (currentMonth) {
        case  10:
            newMonth = "A";
            break;
        case  11:
            newMonth = "B";
            break;
        case  12:
            newMonth = "C";
            break;
        }
        newAcntId = newYear+newMonth+newCurrentSeq;
    
        return newAcntId;
    }

    /**
     * 列出所有部门
     */
    public List listDept() throws BusinessException {
        List acntList = new ArrayList();
        try{
        Session session = this.getDbAccessor().getSession();
        
        Query query = session
        .createQuery("from Acnt as t where t.isAcnt='0' and t.isEnable='1' order by t.acntId");
        
        acntList = query.list();
        
        } catch(Exception e) {
            throw new BusinessException("error.system.db", e);
        }
        return acntList;
    }

   

    /**
     * 更新专案技术资料
     */
    public void updateAcntTechinfo(AcntTechinfo techInfo) throws BusinessException {
        this.getDbAccessor().update(techInfo);
        
    }

    /**
     * 更新专案客户资料
     */
    public void updateAcntCustContactor(AcntCustContactor acntCustContactor) throws BusinessException {
        this.getDbAccessor().update(acntCustContactor);
        
    }
    
    /**
     * 根据传入条件模糊查询部门
     * 1.部门代码
     * 2.部门简称
  	 * 3.业绩归属
     * @param acnt
     * @return
     * @throws BusinessException
     */
    public List listByKey (Acnt acnt,String applicantName,String BDMName,String
            TCSName,String deptManager) throws BusinessException {   
        List resultList=new ArrayList();
        String acntId=StringUtil.nvl(acnt.getAcntId());
        String acntName=StringUtil.nvl(acnt.getAcntName());
        String belongBd=StringUtil.nvl(acnt.getAchieveBelong());    
        String appPersonType=IDtoAccount.USER_TYPE_APPLICANT;
        String BDType=IDtoAccount.USER_TYPE_BD_MANAGER;
        String TCSType=IDtoAccount.USER_TYPE_TC_SIGNER;
        String deptManagerType="DeptManager";
            try{
                Session session = this.getDbAccessor().getSession();  
                StringBuffer hqlBuff = new StringBuffer();
                hqlBuff.append("from Acnt as a where 1=1");
                if(acntId!=null&&!acntId.equals("")){
                  hqlBuff.append(" and lower(a.acntId) like lower('%" + acntId+ "%')");
                }
                if(acntName!=null&&!acntName.equals("")){
                  hqlBuff.append(" and lower(a.acntName) like lower('%" + acntName + "%')"); 
                }
                if(belongBd!=null&&!belongBd.equals("")){
                  hqlBuff.append(" and lower(a.achieveBelong) like lower('%"+belongBd+"%')");
                }
                if(applicantName!=null&&!applicantName.equals("")){
                  hqlBuff.append(" and a.rid in(select p.acntRid from AcntPerson" +
                        " as p where p.personType='"+appPersonType+"' " +
                                "and lower(p.name) like lower('%" +applicantName+ "%'))"); 
                }
                if(BDMName!=null&&!BDMName.equals("")){
                  hqlBuff.append(" and a.rid in(select p.acntRid from AcntPerson" +
                            " as p where p.personType='"+BDType+"' " +
                                    "and lower(p.name) like lower('%" +BDMName+ "%'))"); 
                }
                if(TCSName!=null&&!TCSName.equals("")){
                   hqlBuff.append(" and a.rid in(select p.acntRid from AcntPerson" +
                            " as p where p.personType='"+TCSType+"' " +
                                    "and lower(p.name) like lower('%" +TCSName+ "%'))");    
                }
                if(deptManager!=null&&!deptManager.equals("")){
                    hqlBuff.append(" and a.rid in(select p.acntRid from AcntPerson" +
                            " as p where p.personType='"+deptManagerType+"' " +
                                    "and lower(p.name) like lower('%" +deptManager+ "%'))");   
                }
                hqlBuff.append(" and a.acntAttribute ='" + "Dept" +"'" );
                hqlBuff.append(" and a.isAcnt = '"+ "0" +"'");
                resultList = (session.createQuery(hqlBuff.toString())).list();           
        } catch(Exception e) {
            throw new BusinessException("error.system.db", e);
        }
        return resultList;
    }
/**
 * 查詢出符合結案條件的數據
 */
    public List listCloseData(AfProjectConfirm af,String userLoginId) 
    throws BusinessException {   
    	String acntStatus = Constant.NORMAL; 	
        List resultList=new ArrayList();
        String acntId=StringUtil.nvl(af.getAcntId());
        String acntName=StringUtil.nvl(af.getAcntName());
        String acntType=StringUtil.nvl(af.getAcntType());
        String customerName=StringUtil.nvl(af.getCustomerId());
        String acntPlanStart=af.getAcntPlannedStart();   
        String acntPlanFinish=af.getAcntPlannedFinish();                
        String appPersonType=IDtoAccount.USER_TYPE_APPLICANT;
        String PMType=IDtoAccount.USER_TYPE_PM;
        Date _beginDate=null;
        Date _endDate=null;
            try{
                Session session = this.getDbAccessor().getSession();  
                StringBuffer hqlBuff = new StringBuffer();
                hqlBuff.append("from Acnt as a where 1=1");
                boolean existAcntPlannedStart=false;
                boolean existAcntPlannedFinish=false;
                if(acntId!=null&&!acntId.equals("")){
                  hqlBuff.append(" and lower(a.acntId) like lower('%" + acntId+ "%')");
                }
                if(acntName!=null&&!acntName.equals("")){
                  hqlBuff.append(" and lower(a.acntName) like lower('%" + acntName + "%')"); 
                }
                if(acntType!=null&&!acntType.equals("")){
                  hqlBuff.append(" and lower(a.relPrjType) like lower('%"+acntType+"%')");
                }
                
                if(acntPlanStart!=null&&!acntPlanStart.equals("")&&
                		acntPlanFinish!=null&&!acntPlanFinish.equals("")){
                	 Date tmpBDate = comDate.toDate(acntPlanStart);
                     Calendar cBDate = Calendar.getInstance();
                     cBDate.setTime(tmpBDate);
                     cBDate.set(Calendar.HOUR,0);
                     cBDate.set(Calendar.MINUTE,0);
                     cBDate.set(Calendar.SECOND,0);
                     cBDate.set(Calendar.MILLISECOND,0);
                    _beginDate = cBDate.getTime();
                     
                     Date tempBDate = comDate.toDate(acntPlanFinish);
                     Calendar cbDate = Calendar.getInstance();
                     cbDate.setTime(tempBDate);
                     cbDate.set(Calendar.HOUR,0);
                     cbDate.set(Calendar.MINUTE,0);
                     cbDate.set(Calendar.SECOND,0);
                     cbDate.set(Calendar.MILLISECOND,0);
                     _endDate = cbDate.getTime();
                     
                	 hqlBuff.append(" and a.acntPlannedStart>=:acntPlanStart and a.acntPlannedFinish<=:acntPlanFinish");
                	 existAcntPlannedStart=true;
                	 existAcntPlannedFinish=true;
                }else if((acntPlanStart!=null&&!acntPlanStart.equals(""))&&(acntPlanFinish==null
                		||acntPlanFinish.equals(""))){
                	 Date tmpBDate = comDate.toDate(acntPlanStart);
                     Calendar cBDate = Calendar.getInstance();
                     cBDate.setTime(tmpBDate);
                     cBDate.set(Calendar.HOUR,0);
                     cBDate.set(Calendar.MINUTE,0);
                     cBDate.set(Calendar.SECOND,0);
                     cBDate.set(Calendar.MILLISECOND,0);
                     _beginDate = cBDate.getTime();
               	     hqlBuff.append(" and a.acntPlannedStart>=:acntPlanStart");
               	     existAcntPlannedStart=true;
               }else if((acntPlanFinish!=null&&!acntPlanFinish.equals(""))
            		   &&(acntPlanStart==null||acntPlanStart.equals(""))){
            	   Date tempBDate = comDate.toDate(acntPlanFinish);
                   Calendar cbDate = Calendar.getInstance();
                   cbDate.setTime(tempBDate);
                   cbDate.set(Calendar.HOUR,0);
                   cbDate.set(Calendar.MINUTE,0);
                   cbDate.set(Calendar.SECOND,0);
                   cbDate.set(Calendar.MILLISECOND,0);
                    _endDate = cbDate.getTime();
            	   hqlBuff.append(" and a.acntPlannedFinish<=:acntPlanFinish");
            	   existAcntPlannedFinish=true;
               }
                if(customerName!=null&&!customerName.equals("")){
                    hqlBuff.append(" and a.customerId in(select c.customerId from Customer c where lower" +
                    		"(c.short_) like lower('%"+customerName+"%'))");
                  }
                if(af.getApplicant()!=null&&!af.getApplicant().equals("")){
                	 hqlBuff.append(" and a.rid in(select ap.acntRid from AcntPerson ap " +
                	 		"where ap.personType='"+appPersonType+"' and lower(ap.name)" +
                	 				" like lower('%"+af.getApplicant()+"%'))");	
                }
                hqlBuff.append(" and a.isAcnt = '1'");
                hqlBuff.append(" and a.acntStatus='"+acntStatus+"'");
                hqlBuff.append(" and a.rid in(select ap.acntRid from AcntPerson " +
                		                     "as ap where (ap.personType='"+appPersonType+"' and ap.loginId='"+userLoginId+"') " +
                		                     		  "or (ap.personType='"+PMType+"'and ap.loginId='"+userLoginId+"'))");
                hqlBuff.append(" and a.acntId not in(select app.acntId from AcntApp as app where app.applicationType='"+Constant.PROJECTCHANGEAPP+"' and app.applicationStatus<>'"+Constant.CONFIRMED+"')");
                Query query = session.createQuery(hqlBuff.toString());
               
                if(existAcntPlannedStart){
                	 query.setDate("acntPlanStart", _beginDate);
                }
                if(existAcntPlannedFinish){
                	query.setDate("acntPlanFinish",_endDate);
                }
                resultList = query.list();           
        } catch(Exception e) {
            throw new BusinessException("error.system.db", e);
        }
   
        return resultList;
    }
    /** 
     * 根据RID查询部门资料 
     * @param Rid 
     * @return
     * @throws BusinessException
     */
    public Acnt loadByRidInDept(Long Rid) throws BusinessException{
    Acnt acnt=new Acnt();
    try {
        Session session = this.getDbAccessor().getSession();

        // 查询对象
        acnt = (Acnt) session.createQuery(
                "from Acnt as t where t.rid=:Rid and t.isAcnt='0'").setParameter("Rid",
                Rid).setMaxResults(1).uniqueResult();
    } catch (Exception e) {
        // 将错误抛至上层即可，此处产生的错误均为报出数据库操作错误
        throw new BusinessException("error.system.db", e);
    }
    return acnt;
}

    /**
     * 根据专案RID查询专案相关的技术资料
     */
    public List listByRidFromTechInfo(Long Rid) throws BusinessException {
        List techInfoList = new ArrayList();
        try {
            // 先获得Session
            Session session = this.getDbAccessor().getSession();

            // 创建HQL查询对象
            Query query = session
                    .createQuery(
                            "from AcntTechinfo as t where t.id.acntRid=:Rid")
                    .setParameter("Rid", Rid);
            // 查询
            techInfoList = query.list();
        } catch (Exception e) {
            // 将错误抛至上层即可，此处产生的错误均为报出数据库操作错误
            throw new BusinessException("error.system.db", e);
        }
        return techInfoList;
    }

    /**
     * 删除某一条专案技术资料
     */
    public void deleteAcntTechinfo(AcntTechinfo techInfo) throws BusinessException {
      try {
          if(techInfo!=null){
              this.getDbAccessor().delete(techInfo);
            }
    } catch (Exception e) {
        throw new BusinessException("error.system.db", e);
    }
        
    }

    /**
     * 通过业绩归属查询部门
     */
    public List listDeptByAchieveBelong(String achieveBelong) throws BusinessException {
       
        List acntList = new ArrayList();
        try{
            Session session = this.getDbAccessor().getSession();
        
            Query query = session
                    .createQuery("from Acnt as t where t.achieveBelong=:achieveBelong and t.isAcnt='0' and t.isEnable='1' order by t.acntId")
                        .setString("achieveBelong", achieveBelong);
        
            acntList = query.list();
        
            } catch(Exception e) {
                throw new BusinessException("error.system.db", e);
            }
        return acntList;
    }
    /**
     * 通过成本归属查询部门
     */
    public List listDeptByCostBelong(String costBelong) throws BusinessException {
       
        List acntList = new ArrayList();
        try{
            Session session = this.getDbAccessor().getSession();
        
            Query query = session
                    .createQuery("from Acnt as t where t.costAttachBd=:costBelong and t.isAcnt='0' and t.isEnable='1' order by t.acntId")
                        .setString("costBelong", costBelong);
        
            acntList = query.list();
        
            } catch(Exception e) {
                throw new BusinessException("error.system.db", e);
            }
        return acntList;
    }

    /**
     * 根据姓名模糊查询人员
     * @param name
     * @return
     */
    public List queryByName(String searchName) {
    	String sql = "select distinct t.login_id as user_login_id,t.name as user_name,t.domain, 'EMP' as user_type "
    		+"from pp_acnt_person_app t "
    		+"where lower(t.name) like lower('"+searchName+"%') "
    		+"and t.login_id is not null and t.domain is not null";
    	List list = this.getDbAccessor().executeQueryToList(sql, DtoUserBase.class);
    	return list;
    }
    /**
     * 查询出当前登陆者可以变更的专案
     */
    public List listAcntToChange(String loginId) throws BusinessException {
        List acntList = new ArrayList();
        try{
            Session session = this.getDbAccessor().getSession();
        
            Query query = session
                    .createQuery("from Acnt as t " +
                            "where t.isAcnt='1' and t.acntStatus='"+Constant.NORMAL+"' " +
                            "and t.rid in (select c.acntRid " +
                                           "from AcntPerson as c " +
                                           "where (c.personType='Applicant' and c.loginId=:loginId) " +
                                           "or (c.personType='PM' and c.loginId=:loginId)) " +
                            "and t.acntId not in (select d.acntId " +
                                              "from AcntApp as d " +
                                              "where (d.applicationType='ProjectChangeApp' " +
                                              "or d.applicationType='ProjectConfirmApp') " +
                                              "and (d.applicationStatus='Submitted' " +
                                              "or d.applicationStatus='UnSubmit')) " +
                            "order by t.acntId").setString("loginId", loginId);
                        
        
            acntList = query.list();
        
            } catch(Exception e) {
                throw new BusinessException("error.system.db", e);
            }
        return acntList;
    }
    /**
     * 根据查询语句查询要导出的数据
     */
    public List queryDataToExport(Map hqlMap) throws BusinessException {
        List list = new ArrayList();
        try {
            String hql = (String)hqlMap.get("HQL");
            Date beginDate = (Date)hqlMap.get("beginDate");
            Date endDate = (Date)hqlMap.get("endDate");
            Date planedStartDate = (Date)hqlMap.get("planedStartDate");
            Date planedFinishDate = (Date)hqlMap.get("planedFinishDate");
            Session session = this.getDbAccessor().getSession();
            Query query = session.createQuery(hql);
            if(beginDate!=null){
                query.setDate("beginDate", beginDate);
            }
            if(endDate!=null){
                query.setDate("endDate", endDate);
            }
            if(planedStartDate!=null){
                query.setDate("planedStartDate", planedStartDate);
            }
            if(planedFinishDate!=null){
                query.setDate("planedFinishDate", planedFinishDate);
            }
            list = query.list();
        } catch (Exception e) {
            throw new BusinessException("error.system.db", e);
        }
        
        return list;
    }
    /**
     * 查询快结束的专案的专案经理的ID
     */
    public List queryPMIdNearToClose() throws Exception {
        List list = new ArrayList();
        Date tmpBDate = new Date();
        Calendar cBDate = Calendar.getInstance();
        cBDate.setTime(tmpBDate);
        cBDate.set(Calendar.HOUR,0);
        cBDate.set(Calendar.MINUTE,0);
        cBDate.set(Calendar.SECOND,0);
        cBDate.set(Calendar.MILLISECOND,0);
        cBDate.add(Calendar.DAY_OF_MONTH, 7);
        Date endDate = cBDate.getTime();
        String hql = "from QueryAcntView as t " +
                     "where t.acntPlannedFinish<=:endDate " +
                     "and t.acntStatus='"+Constant.NORMAL+"' " +
                     "and not exists (select d.acntId from AcntApp as d " +
                                           "where d.applicationType='"+Constant.PROJECTCONFIRMAPP+"' " +
                                           "and d.applicationStatus='"+Constant.SUBMIT+"') " +
                     "order by t.acntPlannedFinish";
        try {
            Session session = this.getDbAccessor().getSession();
            Query q = session.createQuery(hql);
            q.setDate("endDate", endDate);
            list = q.list();
        } catch (Exception e) {
            throw e;
        }
        return list;
    }
    /**
     * 查询业务线部门
     */
	public List queryBlDept() throws BusinessException {
		List list = new ArrayList();
		String hql = "from Acnt as t where t.isAcnt='0' and t.isEnable='1' and t.isBl='1' order by t.acntId";
		try {
            Session session = this.getDbAccessor().getSession();
            Query q = session.createQuery(hql);
            list = q.list();
        } catch (Exception e) {
            throw new BusinessException("error.system.db", e);
        }
		return list;
	}
}
