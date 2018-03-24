package server.essp.pms.psr.logic;

import server.essp.framework.logic.AbstractESSPLogic;
import java.util.List;
import java.util.ArrayList;
import server.essp.pms.account.logic.LgAccountListUtilImp;
import c2s.essp.common.account.IDtoAccount;
import server.essp.pms.account.baseline.logic.LgAccountBaseline;
import c2s.essp.pms.account.DtoAcntBLLog;
import server.essp.ebs.logic.LgAcnt;
import java.util.Date;
import db.essp.pms.PmsStatusReportHistory;
import server.framework.hibernate.HBComAccess;
import itf.hr.HrFactory;
import itf.hr.IHrUtil;
import server.essp.pms.wbs.logic.LgWbsComplete;
import java.util.Calendar;
import net.sf.hibernate.*;

/**
 * <p>Title:自动生成项目状态报告 </p>
 *
 * <p>Description: 每周一晚上根据ESSP系统中的各项数据自动生成项目状态报告</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class LgAutoGenerateReportInfo extends AbstractESSPLogic {
    private List reqAccount = new ArrayList();
    private Date startDate, finishDate;

    public LgAutoGenerateReportInfo(String[] types, int i) {
        List accountList = findReqAccout(types);
        //filter project baseline no approved
        checkAndFilterApproved(accountList);
        getDatePeriod(i);
    }

    public LgAutoGenerateReportInfo(Long acntRid, int i) {
        LgAcnt lg = new LgAcnt();
        List accountList = new ArrayList();
        accountList.add(lg.select(acntRid));
        //filter project baseline no approved
        checkAndFilterApproved(accountList);
        getDatePeriod(i);
    }

    private void insertGenerateData() {
        HBComAccess comAcc = this.getDbAccessor();
        LgStaticsWp wpLogic = new LgStaticsWp();
        LgStaticsLaborCost costLogic = new LgStaticsLaborCost();
        try {
            try {
                comAcc.followTx();
                for (int i = 0; i < reqAccount.size(); i++) {
                    IDtoAccount dto = (IDtoAccount) reqAccount.get(i);
                    Long acntRid = dto.getRid();
                    PmsStatusReportHistory history = getHistory(acntRid, startDate, finishDate);
                    IHrUtil hr = HrFactory.create();
                    history.setManager(hr.getChineseName(dto.getManager())
                                       + "(" + dto.getManager() + ")");
                    //先计算完工比例然后得出此比例并保存
                    LgWbsComplete lgComplete = new LgWbsComplete(comAcc);
                    Double rate = lgComplete.getCompleteRate(acntRid);
                    history.setCompleterate(rate);

                    //得到WP的相关数据
                    history.setWpcount(wpLogic.getTotalWpCount(acntRid));
                    history.setCompleteplancount(
                        wpLogic.getCurrentPlanCount(acntRid, startDate,
                        finishDate));
                    history.setCompleteactualcount(
                        wpLogic.getCurrentActualCount(acntRid, startDate,
                        finishDate));
                    history.setTotalplancount(
                        wpLogic.getTotalPlanCount(acntRid, finishDate));
                    history.setTotalactualcount(
                        wpLogic.getTotalActualCount(acntRid, finishDate));
                    //得到Budget和PV
                    LgStaticsPV lgPv = new LgStaticsPV();
                    List allBudget = lgPv.getAllBudget(acntRid);
                    Double pv = lgPv.getPv(allBudget, startDate, finishDate);
                    Double budget = lgPv.getTotalBudget(allBudget);
                    history.setPv(pv);
                    history.setBudget(budget);

                    //计算EV，EV＝完工比例＊Budget
                    double ev = rate.doubleValue() * budget.doubleValue() /
                                (new Double(100)).doubleValue();
                    history.setEv(new Double(ev));
                    //得到Cost的数据，AC(Actual Cost)
                    history.setAc(costLogic.getTotalCost(acntRid));
//                    comAcc.save(history);
                    Double costPerWeek = costLogic.getCostPerWeek(acntRid,
                        startDate, finishDate);
                    history.setLaborcostperweek(costPerWeek);

                    LgStaticsIssue t = new LgStaticsIssue();
                    t.staticIssueData(dto.getRid(), startDate, finishDate);
                }
                comAcc.endTxCommit();
            } catch (Exception ex1) {
                comAcc.endTxRollback();
                ex1.printStackTrace();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * get history when exist
     * @param acntRid Long
     * @param startDate Date
     * @param finishDate Date
     * @return PmsStatusReportHistory
     * @throws HibernateException
     * @throws Exception
     */
    private PmsStatusReportHistory getHistory(Long acntRid, Date startDate, Date finishDate) throws
        HibernateException, Exception {
        HBComAccess comAcc = this.getDbAccessor();
        String hql = "from PmsStatusReportHistory h" +
                     " where h.acntRid=:acntRid" +
                     " and h.startdate=:startdate" +
                     " and h.finishdate=:finishdate";
        PmsStatusReportHistory h = null;
        h = (PmsStatusReportHistory) comAcc.getSession().createQuery(hql)
            .setLong("acntRid", acntRid)
            .setDate("startdate", startDate)
            .setDate("finishdate", finishDate).uniqueResult();
        if(h == null) {
            h = new PmsStatusReportHistory();
            h.setAcntRid(acntRid);
            h.setStartdate(startDate);
            h.setFinishdate(finishDate);
            comAcc.save(h);
        }
        return h;
    }

    //取得当前周的起止时间，后面所有用到的时间均为此两项
    private void getDatePeriod(int i) {
        Calendar cal = Calendar.getInstance();
        Date[] period = SplitWeek.getWeekPeriod(cal, i);
        startDate = period[0];
        finishDate = period[1];
    }

    //过滤没有被批准的项目
    private void checkAndFilterApproved(List accountList) {
        for (int i = 0; i < accountList.size(); i++) {
            IDtoAccount dto = (IDtoAccount) accountList.get(i);
//            System.out.println(dto.getDisplayName() + " == " + dto.getRid());
            LgAccountBaseline acntBL = new LgAccountBaseline();
            List logList = acntBL.listBaseLineLog(dto.getRid());
            for (int j = 0; j < logList.size(); j++) {
                DtoAcntBLLog blLog = (DtoAcntBLLog) logList.get(j);
                if (blLog.getAppStatus().equals("Approved")) {
                    reqAccount.add(dto);
                    break;
                }
            }
        }

        System.out.println(reqAccount.size());
        for (int i = 0; i < reqAccount.size(); i++) {
            System.out.println(((IDtoAccount) reqAccount.get(i)).getDisplayName());
        }
    }


    private List findReqAccout(String[] types) {
        List result = new ArrayList();
        LgAccountListUtilImp lgAccountListUtilImp = new LgAccountListUtilImp();
        String[] values = {IDtoAccount.ACCOUNT_STATUS_APPROVED};
        lgAccountListUtilImp.statusCondition(values);
        lgAccountListUtilImp.typeCondition(types);
        result = lgAccountListUtilImp.listAccounts();
        return result;
    }

    /**
     * 主要有以下两个参数:
     * 第一个：ProjectType=project,默认为此参数
     * 第二个：项目RID，可用于指定对特定的项目生成报告。
     * @param arg String[]
     */
    public static void main(String[] arg) {
        LgAutoGenerateReportInfo autoGen = null;
        if (arg == null || arg.length == 0) {
            //没有传任何参数进来，就用默认的参数,直接去找所有类型为Project的项目
            String[] types = {IDtoAccount.ACCOUNT_TYPE_PROJECT};
            autoGen = new LgAutoGenerateReportInfo(types, -1);
        } else {
            if (arg[0].matches("[0-9]*")) {
                autoGen = new LgAutoGenerateReportInfo(new Long(arg[0]), -1);

            } else {
                if (arg[0].startsWith("ProjectType")) {
                    String temp = arg[0].replaceAll("ProjectType=", "");
                    String[] types = temp.split(",");
                    autoGen = new LgAutoGenerateReportInfo(types, -1);
                } else if(arg[0].startsWith("offset=")) {
                    String temp = arg[0].replaceAll("offset=", "");
                    int offset = Integer.parseInt(temp);
                    String[] types = {IDtoAccount.ACCOUNT_TYPE_PROJECT};
                    autoGen = new LgAutoGenerateReportInfo(types, offset);
                }
            }
        } //对需要自动生成的项目和当前周起止时间初始化完成
        if (autoGen != null) {
            autoGen.insertGenerateData();
        }
        System.out.println("generate report info over!");
    }
}
