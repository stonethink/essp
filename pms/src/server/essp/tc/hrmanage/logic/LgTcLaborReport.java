package server.essp.tc.hrmanage.logic;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.sql.RowSet;

import c2s.essp.common.account.IDtoAccount;
import c2s.essp.common.calendar.WorkCalendarConstant;
import c2s.essp.common.user.DtoUser;
import c2s.essp.tc.weeklyreport.DtoWeeklyReport;
import com.wits.util.StringUtil;
import db.essp.cbs.PmsAcntCost;
import db.essp.pms.LaborResource;
import db.essp.tc.TcByWorkerAccount;
import db.essp.tc.TcStandardTimecard;
import itf.account.AccountFactory;
import itf.account.IAccountUtil;
import itf.hr.HrFactory;
import net.sf.hibernate.Session;
import server.essp.cbs.buget.logic.LgBuget;
import server.essp.framework.logic.AbstractESSPLogic;
import server.essp.pms.account.labor.logic.LgAccountLaborRes;
import server.essp.tc.hrmanage.viewbean.VbTcAccount;
import server.essp.tc.hrmanage.viewbean.VbTcLabor;
import server.essp.tc.hrmanage.viewbean.VbTcLaborReport;
import server.framework.common.BusinessException;
import server.essp.pms.wbs.logic.LgAcntSeq;
import java.sql.ResultSet;
import java.sql.Timestamp;
import server.essp.tc.common.LgTcCommon;
import c2s.dto.DtoTreeNode;
import java.util.TreeMap;
import com.wits.util.comDate;
import c2s.essp.attendance.Constant;
import java.util.Comparator;
import java.util.Collections;
import c2s.dto.ITreeNode;
import itf.hr.LgHrUtilImpl;
import c2s.essp.common.calendar.WrokCalendarFactory;
import itf.orgnization.OrgnizationFactory;
import itf.orgnization.IOrgnizationUtil;

/**
 * 查询人力报表显示所需内容
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Rong.Xiao
 * @version 1.0
 */
public class LgTcLaborReport extends AbstractESSPLogic {
    public static final String STAT_ACCOUNT_TYPE = "Project";

    /**
     * 生成人力报表所需数据
     * 1.查找区间所有人员的TimeCard按项目分组,放入Map中,Key:VbTcAccount(项目数据)
     * Value:List{VbTcLabor(项目内每人的数据)}
     * 2.遍历Map的Key,将Key对应的List放入报表并加上Key,汇总"Project"的正常工时和分配工时
     * 及所有项目的实际工时和认列工时,
     * 3.计算Utilization Rate=“Project”类型项目汇总正常工时/区间内所有工程人员的标准工时之和
     *   Productivity Rate = 项目总认列工时/项目总实际工时;
     * @param beginDate Date
     * @param endDate Date
     * @return VbTcLaborReport
     */
    public VbTcLaborReport getLaborReport(Long acntRid,Date beginDate,Date endDate){
        if(beginDate == null || endDate == null)
            throw new BusinessException("","date period can not be null!");
        if(beginDate.getTime() > endDate.getTime())
            throw new BusinessException("","end date must be larger than begin date!");
        List productivityUserList = getHrAcntUserList(acntRid, true);
        VbTcLaborReport report = new VbTcLaborReport();
        //将开始时间的时分秒都设为0
        Date resetBeginPeriod = LgTcCommon.resetBeginDate(beginDate);
        //将结束时间的设置为23:59:59 999
        Date resetEndPeriod = LgTcCommon.resetEndDate(endDate);
        report.setBeginPeriod(resetBeginPeriod);
        report.setEndPeriod(resetEndPeriod);

        BigDecimal totalProjectNormalTime = new BigDecimal(0);//所有Type="Project"项目正常工时汇总
        BigDecimal totalStandardTime = getTotalStandardTime(productivityUserList,resetBeginPeriod,resetEndPeriod);//区间内所有工程人员的标准工时之和
        BigDecimal totalAccountActualTime = new BigDecimal(0);//所有项目实际工时汇总
        BigDecimal totalAccountEarnedTime = new BigDecimal(0);//所有项目认列工时汇总
        BigDecimal totalActualTime = new BigDecimal(0);//所有实际工时
        BigDecimal totalNormalTime = new BigDecimal(0);//所有正常工时
        BigDecimal totalOverTime = new BigDecimal(0);//所有加班工时
        BigDecimal totalAssignTime = new BigDecimal(0);//所有分配工时

        //根节点
        VbTcAccount sum = new VbTcAccount();
        sum.setAccountId("SUM");
        DtoTreeNode root = new DtoTreeNode(sum);

        List userList = getHrAcntUserList(acntRid, false);
        String users = exchangUserList2Str(userList);
        Map accountLaborMap = listLaborByAccount(users, resetBeginPeriod,resetEndPeriod);
        for(Iterator it =  accountLaborMap.keySet().iterator();it.hasNext();){
            //每个项目对应的节点
            VbTcAccount account = (VbTcAccount) it.next();
            DtoTreeNode accountNode = new DtoTreeNode(account);
            root.addChild(accountNode);
            BigDecimal sumAcntWorkTime = new BigDecimal(0);
            BigDecimal sumAcntOverTime = new BigDecimal(0);
            BigDecimal sumAcntAssignTime = new BigDecimal(0);

            List labors = (List) accountLaborMap.get(account);
            for(int i = 0;i < labors.size() ;i ++){
                //每个人员对应的节点
                VbTcLabor labor = (VbTcLabor) labors.get(i);
                DtoTreeNode laborNode = new DtoTreeNode(labor);
                accountNode.addChild(laborNode);

                BigDecimal actualWorkTime = labor.getActualWorkTime();
                BigDecimal overTime = labor.getOverTime();
                BigDecimal assignedTime = labor.getAssignedTime();
                BigDecimal normalTime = labor.getNormalWorkTime();
                if(actualWorkTime != null){
                    totalActualTime = totalActualTime.add(actualWorkTime);
                    sumAcntWorkTime = sumAcntWorkTime.add(actualWorkTime);
                }
                if(overTime != null){
                    totalOverTime = totalOverTime.add(overTime);
                    sumAcntOverTime = sumAcntOverTime.add(overTime);
                }
                if(assignedTime != null){
                    sumAcntAssignTime = sumAcntAssignTime.add(assignedTime);
                    totalAssignTime = totalAssignTime.add(assignedTime);
                }
                if(normalTime != null){
                    totalNormalTime = totalNormalTime.add(normalTime);
                }

            }
            account.setActualWorkTime(sumAcntWorkTime);
            account.setOverTime(sumAcntOverTime);
            account.setAssignedTime(sumAcntAssignTime);

            if(STAT_ACCOUNT_TYPE.equals(account.getType())){
                System.out.print(account.getAccountName());
                totalProjectNormalTime = totalProjectNormalTime.add(account.getNormalWorkTime());
            }
            totalAccountActualTime = totalAccountActualTime.add(account.getAccountActualTime());
            totalAccountEarnedTime = totalAccountEarnedTime.add(account.getAccountEarnedTime());
        }
        Collections.sort(root.children(),new AccountComparator());

        sum.setAccountActualTime(totalAccountActualTime);
        sum.setAccountEarnedTime(totalAccountEarnedTime);
        sum.setActualWorkTime(totalActualTime);
        sum.setOverTime(totalOverTime);
        sum.setAssignedTime(totalAssignTime);
        report.setRoot(root);
        report.setTotalProjectNormalTime(totalProjectNormalTime);
        report.setTotalEngineerStandardTime(totalStandardTime);
        //计算Productivity Rate
        if(totalAccountActualTime.doubleValue() != 0D)
            report.setProductivityRate(totalAccountEarnedTime.multiply(new BigDecimal(100)).divide(totalAccountActualTime,BigDecimal.ROUND_HALF_UP).setScale(2,BigDecimal.ROUND_HALF_UP));
        else
            report.setProductivityRate(new BigDecimal(0));
        accountLaborMap.clear();
        return report;
    }
    /**
     * 计算某个人力项目下区间内所有的工程人员的标准工时之和
     * @param beginDate Date
     * @param endDate Date
     * @return BigDecimal
     */
    private BigDecimal getTotalStandardTime(List userList,Date beginDate,Date endDate){
        BigDecimal TotalST = new BigDecimal(0);
        if(userList != null && userList.size() != 0){
            for(int i = 0;i < userList.size() ;i ++){
                String user = (String) userList.get(i);
                TotalST = TotalST.add(getStandardWorkTime(user, beginDate, endDate));
            }
        }
        return TotalST;
    }

    /**
     * 获取在HRAccount中，在职，有产能员工的LoginId
     * @param acntRid Long
     * @return List
     * @throws BusinessException
     */
    private List getHrAcntUserList(Long acntRid, boolean isProductivity) throws BusinessException {
        List userList = new ArrayList();
        String sql = "select login.login_id as loginid " +
                     "from " + LgHrUtilImpl.LOGIN_TABLE + " login " +
                     "left join essp_hr_account_scope_t acntScope on login.user_id = acntScope.SCOPE_CODE " +
                     "left join essp_hr_employee_main_t hr on login.user_id = hr.code " +
                     "where acntScope.ACCOUNT_ID = " + acntRid.longValue() +
                     (isProductivity ? " and hr.productivity = '1' " : " ") +
                     "and hr.dimission_flag = '0' " +
                     " order by lower(loginid)";

        try {
            RowSet rset = getDbAccessor().executeQuery(sql);
            while (rset.next()) {
                String loginId = rset.getString("loginid");
                userList.add(loginId);
            }
        } catch (Exception ex) {
            throw new BusinessException("E000",
                                        "Error when get productivity user in account - " +
                                        acntRid, ex);
        }
        return userList;
    }

    /**
     * 将user loginId 的List转换成适合SQL的String
     * @param userList List
     * @return String
     */
    private String exchangUserList2Str(List userList) {
        StringBuffer userSb = new StringBuffer("''");
        Iterator rset = userList.iterator();
        while (rset.hasNext()) {
            String loginId = (String) rset.next();
            userSb.append(",");
            userSb.append("'");
            userSb.append(loginId);
            userSb.append("'");
        }
        return userSb.toString();
    }


        /**
         * 查询人力报表显示所需内容,每行内容为VbTcLabor
         * 1.查找区间内所有人员的经PM确认的周报(db.essp.tc.TcByWorkerAccount),按项目排序
         * 2.遍历所有人员,Account与前一个Account不同时,若AccountType='Project'设置前一个Account的汇总数据
         *   将Account放入List
         * @return List
         */
        private Map listLaborByAccount(String users, Date beginDate,Date endDate){
            if(beginDate == null || endDate == null || beginDate.getTime() > endDate.getTime())
                throw new BusinessException("TC_REPORT_0000","illegal date!");
            Map acountLaborMap = new HashMap();
            List timeCardReport = listTimeCardReport(users, beginDate,endDate);

            Long curAcntRid = new Long(0);
            VbTcAccount curAccount = null;
            for(int i = 0;i < timeCardReport.size() ;i ++){
                Object[] workerReport = (Object[]) timeCardReport.get(i);
                VbTcLabor vb = new VbTcLabor();
                Long acntRid = (Long)workerReport[1];
                //设置项目
                if(curAcntRid.longValue() != acntRid.longValue()){
                      curAccount = getAccount(acntRid);
                      curAcntRid = acntRid;
                }
                vb.setAccountId(curAccount.getAccountId());
                vb.setAccountName(curAccount.getAccountName());
                vb.setOrgName(curAccount.getOrgName());
                //设置用户姓名和JobCode
                String userId = StringUtil.nvl(workerReport[0]);
                DtoUser user = HrFactory.create().findResouce(userId);
                vb.setLoginId(userId);
                if(user != null){
                    if(user.getChineseName() != null){
                         vb.setUserName(user.getChineseName());
                     } else{
                         vb.setUserName(user.getUserName());
                     }
                     vb.setJobCode(HrFactory.create().getJobCodeById(user.getJobCode()));
                     vb.setType(user.getUserType());
                     vb.setPositionType(user.getPtype());
                }else{
                    vb.setUserName(userId);
                }
                //设置实际工时和加班工时
                BigDecimal workTime = (BigDecimal) workerReport[2];
                BigDecimal overTime = getWorkerOverTimeInAccount(userId,acntRid,beginDate,endDate);
                vb.setActualWorkTime(workTime);
                vb.setOverTime(overTime);
                //设置实际工作起始时间
                Date[] actualPeriod = getActualWorkPeriod(userId,acntRid,beginDate,endDate);
                vb.setActualBeginDate(actualPeriod[0]);
                vb.setActualEndDate(actualPeriod[1]);
                BigDecimal userTotalTime = getUserTotalActualWorkTime(userId,beginDate,endDate);
                ///计算所有人某区间内在某项目分配工时
                ///若不是该人员所在的最后一个项目,则用公式:区间内人员标准工时*区间内人员在项目实际工时/区间内员工的实际工时
                ///否则用公式:标准工时-在其他项目的分配工时
                if(userTotalTime.doubleValue() != 0D){
                    BigDecimal standardTime = getStandardWorkTime(userId,beginDate,endDate);
                    vb.setStandardTime(standardTime);
                    if(isLastOne(timeCardReport,i+1,userId)){
                        BigDecimal assignedTime = standardTime;
                        //若是该人的最后一个项目,遍历当前所有项目,及项目中的人
                        //用标准工时减之前分配的工时
                        Iterator it = acountLaborMap.values().iterator();
                        while(it.hasNext()){
                            List accountLabors = (List) it.next();
                            if(accountLabors != null && accountLabors.size() > 0){
                                for(int j = 0; j< accountLabors.size() ;j ++){
                                    VbTcLabor other = (VbTcLabor) accountLabors.get(j);
                                    if(userId.equals(other.getLoginId()) &&
                                        other.getAssignedTime() != null){
                                        assignedTime = assignedTime.subtract(other.getAssignedTime());
                                    }
                                }
                            }
                        }
                        vb.setAssignedTime(assignedTime);
                    }else{
                        BigDecimal assignedTime = standardTime.multiply(vb.getActualWorkTime()).divide(userTotalTime,0,BigDecimal.ROUND_HALF_UP);
                        vb.setAssignedTime(assignedTime);
                    }
                }
                List accountLabors = (List) acountLaborMap.get(curAccount);
                if( accountLabors == null){
                    accountLabors = new ArrayList();
                    acountLaborMap.put(curAccount,accountLabors);
                }
                //项目经理放在最后一项
                if(userId.equals(curAccount.getManager())){
                    accountLabors.add(vb);
                }else{
                    accountLabors.add(0, vb);
                }
            }
            return acountLaborMap;
        }
     private boolean isLastOne(List timeCardReport,int beginIndex,String userId){
         for(int i = beginIndex;i < timeCardReport.size() ;i ++){
             Object[] workerReport = (Object[]) timeCardReport.get(i);
             if(userId.equals(StringUtil.nvl(workerReport[0])))
                 return false;
         }
         return true;
     }
    /**
     * 计算用户在某区间内在某项目的加班时间
     * @param userId String
     * @param acntRid Long
     * @param beginDate Date
     * @param endDate Date
     * @return BigDecimal
     */
    private BigDecimal getWorkerOverTimeInAccount(String userId, Long acntRid, Date beginDate, Date endDate) {
        String sql = "select sum(t2.hours) as hours from tc_overtime t1 left join tc_overtime_detail t2 on t1.rid = t2.overtime_id " +
                     "where t1.login_id='"+userId+"' " +
                     "and t1.acnt_rid='"+acntRid+"' " +
                     "and t1.status='"+Constant.STATUS_FINISHED+"' " +
                     "and to_char(t2.overtime_day,'yyyy/MM/dd') >= '"+comDate.dateToString(beginDate,"yyyy/MM/dd")+"' " +
                     "and to_char(t2.overtime_day,'yyyy/MM/dd') <= '"+comDate.dateToString(endDate,"yyyy/MM/dd")+"' " ;
        log.info("sum overtime hours:" + sql);
        try {
            ResultSet rt = this.getDbAccessor().executeQuery(sql);
            while(rt.next()){
                double hours = rt.getDouble("hours");
                return new BigDecimal(hours);
            }
        } catch (Exception ex) {
            throw new BusinessException(ex);
        }
        return DtoWeeklyReport.BIG_DECIMAL_0;
    }

    /**
         * 查找时间段内所有人员在项目中(可能多个)的实际工时和加班时间
         * @param beginPeriod Date
         * @param endPeriod Date
         * @return List{Object[3]:Object[0]--userId,
         *                        Object[1]--acntRid,
         *                        Object[2]--用户在当前项目的实际工时,
         *                        }
         */
        private List listTimeCardReport(String users, Date beginPeriod,Date endPeriod){
            try {
                Session s = this.getDbAccessor().getSession();
                String query = "select tc.userId,tc.account.rid,sum(tc.satHour+tc.sunHour+tc.monHour+tc.tueHour+tc.wedHour+tc.thuHour+tc.friHour) " +
                               "from TcByWorkerAccount tc " +
                               "where tc.beginPeriod>=:beginPeriod " +
                               "and tc.endPeriod<=:endPeriod " +
                               "and tc.confirmStatus=:status " +
                               "and tc.userId in (" + users + ") " +
                               "group by tc.userId,acnt_rid " +
                               "order by acnt_rid,tc.userId";
                return s.createQuery(query)
                        .setParameter("beginPeriod",beginPeriod)
                        .setParameter("endPeriod",endPeriod)
                        .setParameter("status",DtoWeeklyReport.STATUS_CONFIRMED)
                        .list();
            } catch (Exception ex) {
                throw new BusinessException("TC_REPORT_0001","error list weekly report!",ex);
            }

        }
        /**
         * 查找用户在某项目内在某区间内的实际工作起止时间
         * 将周六至周五工作时间放入数组,
         * 从前往后遍历,第一个不为0的为开始时间
         * 从后往前遍历,第一个不为0的为结束时间
         * @return Date[]
         */
        private Date[] getActualWorkPeriod(String userId,Long acntRid,Date beginPeriod,Date endPeriod){
            Date[] result = new Date[2];
            result[0] = null;
            result[1] = null;
            try {
                Session s = this.getDbAccessor().getSession();
                String query = "from TcByWorkerAccount tc " +
                               "where tc.beginPeriod>=:beginPeriod " +
                               "and tc.endPeriod<=:endPeriod " +
                               "and tc.confirmStatus=:status " +
                               "and tc.userId=:userId " +
                               "and tc.account.rid=:acntRid order by tc.beginPeriod asc";
                List l = s.createQuery(query)
                         .setParameter("beginPeriod",beginPeriod)
                         .setParameter("endPeriod",endPeriod)
                         .setParameter("status",DtoWeeklyReport.STATUS_CONFIRMED)
                         .setParameter("userId",userId)
                         .setParameter("acntRid",acntRid)
                         .list();
                if(l == null || l.size() == 0){
                    return result;
                }
                TcByWorkerAccount first = (TcByWorkerAccount) l.get(0);


                TcByWorkerAccount last = (TcByWorkerAccount) l.get(l.size() - 1);

                result[0] = first.getBeginPeriod();
                result[1] = last.getEndPeriod();
            } catch (Exception ex) {
                throw new BusinessException("TC_REPORT_0002","error list weekly report!",ex);
            }
            return result;
        }
        /**
         * 查找某人在区间内的标准工时
         * @return BigDecimal
         */
        private BigDecimal getStandardWorkTime(String userId, Date beginPeriod, Date endPeriod){
      		BigDecimal result = new BigDecimal(0);

            Calendar bc = Calendar.getInstance();
         	Calendar ec = Calendar.getInstance();
         	bc.setTime(beginPeriod);
         	ec.setTime(endPeriod);
         	List weekList =  WrokCalendarFactory.serverCreate().getBEWeekListMin(bc, ec);
            LgStandardTimecard lg = new LgStandardTimecard();
            for (Iterator iter = weekList.iterator(); iter.hasNext(); ) {
             	Calendar[] weekPeriod = (Calendar[]) iter.next();
                TcStandardTimecard standar = lg.getByUserIdOnWeek(userId,
                    weekPeriod[0].getTime(),
                    weekPeriod[1].getTime());
                if(standar != null && standar.getTimecardNum() != null)
                    result = result.add(standar.getTimecardNum());
            }
            return result;
        }
        /**
         * 计算某人某区间内实际工时
         */
        private BigDecimal getUserTotalActualWorkTime(String userId,Date beginDate,Date endDate){

            try {
                Session s = this.getDbAccessor().getSession();
                String query = "select sum(tc.satHour+tc.sunHour+tc.monHour+tc.tueHour+tc.wedHour+tc.thuHour+tc.friHour) " +
                               "from TcByWorkerAccount tc " +
                               "where tc.beginPeriod>=:beginPeriod " +
                               "and tc.endPeriod<=:endPeriod " +
                               "and tc.confirmStatus=:status " +
                               "and tc.userId=:userId ";
                Object result = s.createQuery(query)
                                .setParameter("beginPeriod",beginDate)
                                .setParameter("endPeriod",endDate)
                                .setParameter("status",DtoWeeklyReport.STATUS_CONFIRMED)
                                .setParameter("userId",userId)
                                .uniqueResult();
                if(result == null)
                    return new BigDecimal(0);
                else
                    return (BigDecimal)result;
            } catch (Exception ex) {
                throw new BusinessException(ex);
            }
        }
        /**
         * 计算项目的实际累计工时,遍历项目中所有的人员,累加所有人员的实际工时
         */
        private BigDecimal getAccountActualTime(Long acntRid){
            LgAccountLaborRes lg = new LgAccountLaborRes();
            List list = lg.listLaborRes(acntRid);
            double result = 0D;
            for(int i = 0;i < list.size() ;i ++){
                LaborResource resouce = (LaborResource) list.get(i);
                Double hours = resouce.getActualWorkTime();
                if(hours == null)
                    continue;
                result += hours.doubleValue();
            }
            return new BigDecimal(result);
        }
        /**
         * 计算项目的总认列工时=完工比例*基线预算的人时数
         */
        private BigDecimal getAccountEarnedTime(Long acntRid){
            BigDecimal result = new BigDecimal(0);
            double ph = getAccountBasePh(acntRid);
            //System.out.println("********baseline ph:"+ph+"**********");
            if(ph != 0D){
                result = result.add(new BigDecimal(ph));
                double comRate = getAccountCompleteRate(acntRid);
                //System.out.println("********complete rate:"+comRate+"**********");
                result = result.multiply(new BigDecimal(comRate)).setScale(2,BigDecimal.ROUND_HALF_UP);
            }
            return result;
        }
        //查找项目的基线预算人时数
        private double getAccountBasePh(Long acntRid) {
            LgBuget lg = new LgBuget();
            PmsAcntCost acntCost = lg.getAcntCost(acntRid);
            if(acntCost == null || acntCost.getBasePm() == null)
                return 0D;
            Double pm = acntCost.getBasePm();
            //人月数转换成人时数=人月数 * 每月工时
            double ph = pm.doubleValue() * WorkCalendarConstant.MONTHLY_WORK_HOUR;
            return ph;
        }
        //查找项目完工比例:根WBS的完工比例
        private double getAccountCompleteRate(Long acntRid){
            LgAcntSeq lg = new LgAcntSeq();
            Long rootWbsRid = lg.getWbsRootRid(acntRid);
            String sql = "select complete_rate " +
                         "from pms_wbs " +
                         "where acnt_rid='"+acntRid+"' and wbs_rid='"+rootWbsRid+"'";
            try {
                RowSet rt = this.getDbAccessor().executeQuery(sql);
                if(rt.next())
                    return rt.getDouble("complete_rate")/100;
            } catch (Exception ex) {
                throw new BusinessException("TC_REPORT_0003","error get complete rate!",ex);
            }
            return 0;
        }
        private VbTcAccount getAccount(Long rid){
                IDtoAccount dto = acntUtil.getAccountByRID(rid);
                VbTcAccount account = new VbTcAccount();
                account.setAccountId(dto.getId());
                account.setAccountName(dto.getName());
                account.setType(dto.getType());
                String orgId = dto.getOrganization();
                account.setOrgId(orgId);
                String orgName = OrgnizationFactory.create().getOrgName(orgId);
                account.setOrgName(orgName);
                account.setAccountActualTime(getAccountActualTime(rid));
                account.setManager(dto.getManager());
                DtoUser user = HrFactory.create().findResouce(dto.getManager());
                if(user != null)
                    account.setUserName(user.getChineseName());
                //"Project"类型项目统计其实际工时和总认列工时
//                if(STAT_ACCOUNT_TYPE.equals(account.getType())){
                    account.setAccountEarnedTime(getAccountEarnedTime(rid));
//                }
                return account;
        }
        private IAccountUtil acntUtil = AccountFactory.create();

        private static class AccountComparator implements Comparator{
            public boolean equals(Object obj) {
                return false;
            }

            public int compare(Object o1, Object o2) {
                if(o1 instanceof ITreeNode && o2 instanceof ITreeNode){
                    ITreeNode node1 = (ITreeNode)o1;
                    ITreeNode node2 = (ITreeNode)o2;
                    VbTcAccount account1 = (VbTcAccount)node1.getDataBean();
                    VbTcAccount account2 = (VbTcAccount)node2.getDataBean();
                    IOrgnizationUtil orgUtil = OrgnizationFactory.create();
                    String code1 = orgUtil.getOrgCode(account1.getOrgId());
                    String code2 = orgUtil.getOrgCode(account2.getOrgId());
                    int result = code2.compareTo(code1) * 1000 + account2.getManager().compareTo(account1.getManager());
                    return result;
                }else{
                    return 0;
                }
            }

        }
//        private LgOverTime lgOverTime = new LgOverTime();
        public static void main(String[] args){
            LgTcLaborReport lg = new LgTcLaborReport();
            Calendar begin = Calendar.getInstance();
            begin.set(2007,0,26,0,0,0);
            begin.set(Calendar.MILLISECOND,0);
            Calendar end = Calendar.getInstance();
            end.set(2007,1,25,0,0,0);
            end.set(Calendar.MILLISECOND,0);
            List userList = lg.getHrAcntUserList(new Long(883),false);
            String users = "'WH0607014','WH0607016'";
            List lst = lg.listTimeCardReport(users, comDate.toDate("2007-01-26"),comDate.toDate("2007-02-25"));
            System.out.println(lg.getTotalStandardTime(userList,begin.getTime(),end.getTime()));
//            VbTcLaborReport l = lg.getLaborReport(begin.getTime(),end.getTime());
        }
}
