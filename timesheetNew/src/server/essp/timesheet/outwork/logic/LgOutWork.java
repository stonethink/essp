package server.essp.timesheet.outwork.logic;

import itf.account.AccountFactory;
import itf.account.IAccountUtil;
import itf.hr.HrFactory;
import itf.hr.LgHrUtilImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import server.essp.common.ldap.LDAPUtil;
import server.essp.common.syscode.LgSysParameter;
import server.essp.timesheet.outwork.form.AfSearchForm;
import server.essp.timesheet.outwork.viewbean.vbOutWorker;
import server.framework.common.BusinessException;
import server.framework.common.Constant;
import server.framework.logic.AbstractBusinessLogic;
import c2s.dto.DtoTreeNode;
import c2s.essp.common.calendar.WorkCalendarBase;

import com.wits.util.IgnoreCaseStringComparator;
import com.wits.util.comDate;

import db.essp.code.SysParameter;
import db.essp.timesheet.TsAccount;
import db.essp.timesheet.TsOutWorker;

public class LgOutWork extends AbstractBusinessLogic {

    /**
     * 新增一条outWorker记录
     * @param outWorker TcOutWorker
     * @throws BusinessException
     */
    public void add(TsOutWorker outWorker) throws BusinessException {
    	if(existsIntersection(outWorker)) {
    		throw new BusinessException("Intersection", "exists Intersection outwork record!");
    	}
        try {
            this.getDbAccessor().save(outWorker);
            this.getDbAccessor().commit();
        } catch (Exception ex) {
            log.error(ex);
            throw new BusinessException("logic.LgOutWork.add", "add outwork record error!", ex);
        }
    }

    /**
     * 根据主键更新outWorker
     * @param outWorker TcOutWorker
     * @throws BusinessException
     */
    public void update(TsOutWorker outWorker) {
    	if(existsIntersection(outWorker)) {
    		throw new BusinessException("Intersection", "exists Intersection outwork record!");
    	}
        try {
            Session session = this.getDbAccessor().getSession();
            TsOutWorker tow = (TsOutWorker) session.load(TsOutWorker.class, outWorker.getRid());
            tow.setLoginId(outWorker.getLoginId());
            tow.setBeginDate(outWorker.getBeginDate());
            tow.setEndDate(outWorker.getEndDate());
            tow.setDestAddress(outWorker.getDestAddress());
            tow.setDays(outWorker.getDays());
            tow.setIsAutoWeeklyReport(outWorker.getIsAutoWeeklyReport());
            tow.setCodeRid(outWorker.getCodeRid());
            if(outWorker.getIsTravellingAllowance()==null || outWorker.getIsTravellingAllowance().length()==0){
                tow.setIsTravellingAllowance("");
            }else{
                tow.setIsTravellingAllowance(outWorker.getIsTravellingAllowance());
            }
            tow.setActivityRid(outWorker.getActivityRid());
            tow.setAcntRid(outWorker.getAcntRid());
            tow.setEvectionType(outWorker.getEvectionType());
            this.getDbAccessor().commit();
        } catch (Exception ex) {
            log.error(ex);
            throw new BusinessException("Error", "update outwork record error!");
        }
    }
    
    private boolean existsIntersection(TsOutWorker outWorker) {
    	String hql = "from TsOutWorker t where  t.rst='" + Constant.RST_NORMAL 
    				+ "' and t.loginId = '" + outWorker.getLoginId() + "' "
    				+ " and t.beginDate <= :end and t.endDate >= :begin";
    	if(outWorker.getRid() != null) {
    		hql += " and t.rid <> " + outWorker.getRid();
    	}
    	try {
			List l = this.getDbAccessor().getSession().createQuery(hql)
						.setDate("end", outWorker.getEndDate())
						.setDate("begin", outWorker.getBeginDate())
						.list();
			if(l != null && l.size() > 0) {
				return true;
			}
		} catch (Exception e) {
			log.error(e);
			throw new BusinessException("Error", "check Intersection outwork record error!");
		}
    	return false;
    }

    /**
     * 删除相应的outWorker记录
     * @param outWorker TsOutWorker
     * @throws BusinessException
     */
    public void delete(TsOutWorker outWorker) throws BusinessException {
        try {

            this.getDbAccessor().delete(outWorker);
            this.getDbAccessor().commit();

        } catch (Exception ex) {
            log.error(ex);
            throw new BusinessException("Error", "delete outwork record error!");
        }
    }

    /**
     * 根据主键删除相应的outWorker记录
     * @param Rid Long
     * @throws BusinessException
     */
    public void delete(Long Rid) throws BusinessException {
        try {

            this.getDbAccessor().executeUpdate(
                    "delete from TS_OUTWORKER where RID=" +
                    Rid);

        } catch (Exception ex) {
            log.error(ex);
            throw new BusinessException("Error", "delete outwork record error by Rid!");
        }
    }


    /**
     *
     * @param afSearchForm AfSearchForm
     * @throws BusinessException
     */
//    public List listAll(AfSearchForm afSearchForm, boolean chinese) throws BusinessException {
//
//        List result = new ArrayList();
//        List list = new ArrayList();
//        String str = str = "from TsOutWorker as t where t.rst='" + Constant.RST_NORMAL +
//                         "' ";
//        try {
//            Session session = this.getDbAccessor().getSession();
//
//            if (afSearchForm.getAcntRid() != null && !afSearchForm.getAcntRid().equals("")) {
//                Long acntRid = new Long(afSearchForm.getAcntRid());
//                str = str + " and t.acntRid='" + acntRid + "'";
//            }
//            if (afSearchForm.getBeginDate() != null && !afSearchForm.getBeginDate().equals("")) {
//                String begindatge = afSearchForm.getBeginDate();
//                str = str + " and to_char(t.endDate,'yyyyMMdd')>='" + begindatge + "'";
//            }
//            if (afSearchForm.getEndDate() != null && !afSearchForm.getEndDate().equals("")) {
//                String enddatge = afSearchForm.getEndDate();
//                str = str + " and to_char(t.beginDate,'yyyyMMdd')<= '" + enddatge + "'";
//            }
//            if (afSearchForm.getLoginId() != null && !afSearchForm.getLoginId().equals("")) {
//                str = str + " and UPPER(t.loginId) like UPPER('%" + afSearchForm.getLoginId() + "%')";
//            }
//            if ((afSearchForm.getAcntRid() == null || afSearchForm.getAcntRid().equals("")) && afSearchForm.getDepartRid() != null &&
//                !afSearchForm.getDepartRid().equals("")) {
//            	LgAccount lgAccount = new LgAccount();
//                List acntList = (List) lgAccount.listAccountByDeptId(afSearchForm.getDepartRid());
//                if (acntList.size() > 1) {
//                    str = str + " and ( ";
//                    for (int i = 0; i < acntList.size(); i++) {
//                    	TsAccount acnt = (TsAccount) acntList.get(i);
//                        if (i == 0) {
//                            str = str + " t.acntRid='" + acnt.getRid() + "'";
//                        } else {
//                            str = str + " or t.acntRid='" + acnt.getRid() + "'";
//                        }
//                    }
//                    str = str + " )";
//                } else {
//                	TsAccount acnt = (TsAccount) acntList.get(0);
//                    str = str + " and  t.acntRid='" + acnt.getRid() + "'";
//                }
//
//            }
//            str = str + " order by t.rid desc";
//            Query query = session.createQuery(str);
//            list = query.list();
//            LDAPUtil ldap = new LDAPUtil();
//            String chinesename = "";
//            LgSysParameter lg = new LgSysParameter();
//            List evectionTypeList = lg.listSysParas("EVECTION_TYPE");
//            for (int i = 0; i < list.size(); i++) {
//                vbOutWorker vbow = new vbOutWorker();
//                TsOutWorker tow = (TsOutWorker) list.get(i);
//                if (tow.getLoginId() != null && !tow.getLoginId().equals("")) {
////                	DtoUser user = ldap.findUser(tow.getLoginId());
////                	if(user != null) {
////                		chinesename = user.getUserName();
////                		vbow.setChineseName(chinesename == null ? "" : chinesename);
////                	}
//                }
//                vbow.setRid(tow.getRid());
//                LgAccount lgAcnt = new LgAccount();
//                TsAccount acnt = lgAcnt.getTsAccount(tow.getAcntRid());
//                if(acnt != null) {
//                	vbow.setAccount(acnt.getAccountId() + " -- " + acnt.getAccountName());
//                	vbow.setOrganization(acnt.getOrgCode());
//                }
//                if (tow.getActivityRid() != null && !tow.getActivityRid().equals("")) {
////                    vbow.setActivity(lgGetActivity.getActivityName(tow.getActivityRid(), tow.getAcntRid()));
//                }
//                vbow.setBeginDate(comDate.dateToString(tow.getBeginDate()));
//                vbow.setEndDate(comDate.dateToString(tow.getEndDate()));
//                vbow.setDays(tow.getDays().toString());
//                if (tow.getDestAddress() != null && !tow.getDestAddress().equals("")) {
//                    vbow.setDestAddress(tow.getDestAddress());
//                }
//                if (tow.getIsAutoWeeklyReport() != null && !tow.getIsAutoWeeklyReport().equals("")) {
//                    vbow.setIsAutoFillWR(tow.getIsAutoWeeklyReport().toString());
//                }
//                if(tow.getIsTravellingAllowance()!=null && !tow.getIsTravellingAllowance().equals("")){
//                    vbow.setIsTravellingAllowance(tow.getIsTravellingAllowance());
//                }else{
//                    vbow.setIsTravellingAllowance("false");
//                }
//                vbow.setLoginId(tow.getLoginId());
//                vbow.setEvectionType("");
//                String evectionType = tow.getEvectionType();
//                if(evectionType != null && evectionTypeList != null) {
//	                for(int j = 0; j < evectionTypeList.size(); j ++){
//	                    SysParameter param = (SysParameter) evectionTypeList.get(j);
//	                    if(param.getComp_id().getCode().equals(evectionType)){
//	                        vbow.setEvectionType(chinese?param.getDescription():param.getName());
//	                    }
//	                }
//                }
//                result.add(vbow);
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
////            log.error(ex);
////            throw new BusinessException("Error", "search outwork record error!");
//        }
//        return result;
//    }
    
    /**
    * 
    * @param afSearchForm AfSearchForm
    * @throws BusinessException
    */
   public List listAll(AfSearchForm afSearchForm, boolean chinese) throws BusinessException {

       List list = new ArrayList();
       String evecType = "p.name";
       if(chinese) {
    	   evecType = "p.description";
       }
       String str = str = "select t.rid,a.account_id || ' -- ' || a.account_name as account,a.org_code as Organization, t.login_id, h.english_name || '(' ||h.chinese_name||')' as chinese_name,t.begin_date,t.end_date,t.days,t.dest_address,t.is_auto_weeklyreport as is_Auto_Fill_W_R,t.is_travelling_allowance as is_Travelling_Allowance," + evecType + " as evection_type,t.dest_address " 
    	   			+ "from ts_outworker t "
    	   			+ "left join ts_account a on a.rid = t.acnt_rid "
    	   			+ "left join ts_human_base h on upper(h.employee_id) = upper(t.login_id) "
    	   			+ "left join sys_parameter p on p.code = t.evection_type "
    	   			+ "where t.rst='" + Constant.RST_NORMAL
    	   			+ "' and p.kind = 'EVECTION_TYPE' ";
       try {

           if (afSearchForm.getAcntRid() != null && !afSearchForm.getAcntRid().equals("")) {
               Long acntRid = new Long(afSearchForm.getAcntRid());
               str = str + " and t.acnt_Rid='" + acntRid + "'";
           } else if(afSearchForm.getDepartRid() != null &&
                   !"".equals(afSearchForm.getDepartRid())) {
        	   str = str + " and a.org_code='" + afSearchForm.getDepartRid() + "'";
           } else {
        	   return list;
           }
           
           if (afSearchForm.getBeginDate() != null && !afSearchForm.getBeginDate().equals("")) {
               String begindatge = afSearchForm.getBeginDate();
               str = str + " and to_char(t.end_date,'yyyyMMdd')>='" + begindatge + "'";
           }
           if (afSearchForm.getEndDate() != null && !afSearchForm.getEndDate().equals("")) {
               String enddatge = afSearchForm.getEndDate();
               str = str + " and to_char(t.begin_date,'yyyyMMdd')<= '" + enddatge + "'";
           }
           if (afSearchForm.getLoginId() != null && !afSearchForm.getLoginId().equals("")) {
               str = str + " and (UPPER(t.login_id) like UPPER('%" + afSearchForm.getLoginId() 
               + "%') or UPPER(h.english_name) like UPPER('%" + afSearchForm.getLoginId()
               +  "%') or UPPER(h.chinese_name) like UPPER('%"+ afSearchForm.getLoginId() + "%'))";
           }
           str = str + " order by t.rid desc";
           list = this.getDbAccessor().executeQueryToList(str, vbOutWorker.class);
       } catch (Exception ex) {
           log.error(ex);
           throw new BusinessException("Error", "search outwork record error!");
       }
       return list;
   }

    public TsOutWorker loadByRid(Long rid) throws BusinessException {
        TsOutWorker tow = null;
        List result = new ArrayList();
        try {
            Session session = this.getDbAccessor().getSession();
            Query query = session.createQuery("from TsOutWorker as t where t.rid=" +
                                              rid);
            result = query.list();
            tow = (TsOutWorker) result.get(0);

        } catch (Exception ex) {
            log.error(ex);
            throw new BusinessException("Error", "search outwork record error!");
        }
        return tow;
    }

    public static void main(String[] args) throws Exception {
//        TsOutWorker tow = new TsOutWorker();
//        Long acntrid = new Long("6022");
//        tow.setAcntRid(acntrid);
//        tow.setLoginId("mingxing.zhang");
//        tow.setBeginDate(new java.util.Date());
//        tow.setEndDate(new java.util.Date());
//        tow.setDestAddress("BJ");
//        tow.setDays(new Long("7"));

        LgOutWork low = new LgOutWork();
//        low.add(tow);
//        tow = low.loadByRid(new Long("19"));
//        System.out.println(tow.getDays());

        AfSearchForm af=new AfSearchForm();
        af.setLoginId("WH0707");
        List l = low.listAll(af, false);
        for(int i = 0; i < l.size(); i ++) {
        	vbOutWorker vb = (vbOutWorker)l.get(i);
        	System.out.println(vb.getLoginId());
        	System.out.println(vb.getChineseName());
        	System.out.println(vb.getAccount());
        	System.out.println(vb.getBeginDate());
        	System.out.println(vb.getEndDate());
        	System.out.println(vb.getDays());
        	System.out.println(vb.getChineseName());
        	System.out.println(vb.getIsAutoFillWR());
        	System.out.println(vb.getIsTravellingAllowance());
        	System.out.println(vb.getEvectionType());
        	System.out.println();
        }
        //TsOutWorker tow =low.loadByRid(new Long("11"));
//        low.delete(tow);

    }

}
