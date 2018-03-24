package server.essp.tc.mail.logic;

import server.framework.common.BusinessException;
import java.util.List;
import java.util.Calendar;
import server.essp.tc.hrmanage.logic.LgTcExcel;
import java.util.HashMap;
import server.framework.logic.AbstractBusinessLogic;
import server.essp.tc.hrmanage.viewbean.VbTcExcel;
import itf.hr.HrFactory;
import itf.hr.LgHrUtilImpl;
import server.essp.tc.mail.genmail.contbean.PersonalStaticsBean;
import java.util.ArrayList;
import java.sql.ResultSet;
import c2s.essp.common.calendar.WorkCalendar;
import c2s.essp.common.calendar.WrokCalendarFactory;
import java.util.Iterator;
import server.essp.tc.mail.genmail.contbean.DateDul;
import java.util.Map.Entry;
import java.util.Map;
import server.essp.tc.mail.genmail.contbean.WorkFlowBean;
import server.essp.pms.account.logic.LgAccountUtilImpl;
import c2s.essp.common.account.IDtoAccount;
import com.wits.util.comDate;
import server.essp.tc.mail.genmail.contbean.VbTcExcelCopy;
import server.essp.common.mail.ContentBean;
import c2s.essp.tc.weeklyreport.DtoWeeklyReportSumByHr;
import server.essp.common.mail.SendHastenMail;
import itf.hr.IHrUtil;


/**
 *　此类用于取得个人的所有相关数据，并由HR手动发送的内容
 *　@author:Robin.Zhang
 */
public class LgGetAllPersonalStaticsDate extends AbstractBusinessLogic {
    HashMap hm = new HashMap();
    ResultSet rs1 = null;
    ResultSet rsFilled = null;
    ResultSet rsAtt = null;

    static Calendar calendar = Calendar.getInstance();
    WorkCalendar wc = WrokCalendarFactory.serverCreate();
    String sqla = null;
    String sqlb = null;

    String begin = null;
    String end = null;
    String beginDate = null;
    String endDate = null;
    String type = null;
    int iYearBegin;
    int iMonthBegin;
    int iDayBegin;
    int iYearEnd;
    int iMonthEnd;
    int iDayEnd;
    IHrUtil hrUtil = HrFactory.create();

    public LgGetAllPersonalStaticsDate() {
    }

    /**初始化内容HashMap,并把基本内容（指核对数据）读入
     * @param accountId Long
     * @param accountId Calendar
     * @param endPeriod Calendar
     * @param wmflag Calendar
     * @param tcList Calendar //只有在此List中的人才发邮件
     */
    public void initContent(Long accountId, Calendar beginPeriod,
                            Calendar endPeriod, String wmflag, List tcList) {
        LgTcExcel lgData = new LgTcExcel();
        List data = lgData.AllDetailList(accountId, beginPeriod.getTime(),
                                         endPeriod.getTime(), wmflag);
        List labors = null;

        //所有员工的差勤信息List放在data[1]中
        try {
            labors = (List) data.get(1);
        } catch (Throwable tx) {
            String msg = "error get all attendance data!";
            log.error(msg);
            throw new BusinessException("", msg, tx);
        }
        //inital hashMap
        for (int i = 0; i < labors.size(); i++) {
            VbTcExcel vbte = new VbTcExcel();
            vbte = (VbTcExcel) labors.get(i);
            if (this.isExistList(vbte.getName(), tcList)) {
                VbTcExcelCopy vbtec = new VbTcExcelCopy();

                //----------------------------------
                vbtec.setPeriod(vbte.getPeriod());
                vbtec.setBeginPeriod(vbte.getBeginPeriod());
                vbtec.setEndPeriod(vbte.getEndPeriod());
                vbtec.setName(vbte.getName());
                vbtec.setBeginDateOfWork(comDate.dateToString(vbte.
                    getBeginDateOfWork()));
                vbtec.setEndDateOfWork(comDate.dateToString(vbte.
                    getEndDateOfWork()));
                vbtec.setStandardWorkTime(vbte.getStandardWorkTime());
                vbtec.setActualWorkTime(vbte.getActualWorkTime());
                vbtec.setNormalWorkTime(vbte.getNormalWorkTime());
                vbtec.setSubWorkTime(vbte.getSubWorkTime());
                vbtec.setSalaryWorkTime(vbte.getSalaryWorkTime());
                vbtec.setOverTime(vbte.getOverTime());
                vbtec.setFullSalaryLeave(vbte.getFullSalaryLeave());
                vbtec.setHalfSalaryLeave(vbte.getHalfSalaryLeave());
                vbtec.setLeave(vbte.getLeave());
                vbtec.setAbsentFromWork(vbte.getAbsentFromWork());
                vbtec.setViolat(vbte.getViolat());
                vbtec.setRemark(vbte.getRemark());
                //---------------------------------------

                PersonalStaticsBean psb = new PersonalStaticsBean();
                psb.setStartDate(comDate.dateToString(beginPeriod.getTime()));
                psb.setFinishDate(comDate.dateToString(endPeriod.getTime()));
                psb.setBaseInfo(vbtec);

                String user = vbtec.getName();
                ContentBean cb = new ContentBean();
                cb.setUser(user);
                LgHrUtilImpl ihui = (LgHrUtilImpl) HrFactory.create();
                String email = ihui.getUserEmail(user);
                cb.setEmail(email);

                ArrayList al = new ArrayList();
                al.add(psb);
                cb.setMailcontent(al);
                hm.put(user, cb);
            }
        }
        // System.out.println(hm);
    }

    /**得到周报统计信息及其相关内容的信息
     * @param accountId Long
     * @param accountId Calendar
     * @param endPeriod Calendar
     * @param wmflag Calendar
     * @param tcList List
     */
    public HashMap getWeeklyReportData(String accountId, Calendar beginPeriod,
                                       Calendar endPeriod, String wmflag,
                                       List tcList) {
        if (accountId == null || wmflag == null || beginPeriod == null ||
            endPeriod == null) {
            throw new BusinessException("", "illegal arguments!");
        }
        this.initContent(new Long(accountId), beginPeriod, endPeriod, wmflag,
                         tcList);
        if (!hm.isEmpty()) {

            if (wmflag.equals("week")) {
                this.getAllPersonalStaticsDataForWeeklyReport(beginPeriod, 0);
            } else {
                ArrayList weekList = (ArrayList) WorkCalendar.getBEWeekListMax(
                    beginPeriod, endPeriod);
                for (int i = 0; i < weekList.size(); i++) {
                    Calendar aweekBegin = ((Calendar[]) weekList.get(i))[0];
                    Calendar aweekEnd = ((Calendar[]) weekList.get(i))[1];
                    System.out.println(comDate.dateToString(aweekBegin.getTime()));
                    if (aweekEnd.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY &&
                        aweekEnd.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
                        this.getAllPersonalStaticsDataForWeeklyReport(
                            aweekBegin, 0);
                    }
                }
            }
        }
        this.handlerAttConfirm();
        this.exchangeId2Name();
        return hm;
    }

    public void exchangeId2Name() {
        Iterator iter = hm.entrySet().iterator();
        for (; iter.hasNext(); ) {
            Map.Entry item = (Map.Entry) iter.next();
            String t = item.getKey().toString();
            ContentBean cb = new ContentBean();
            cb = (ContentBean) hm.get(t);
            cb.setUser(hrUtil.getChineseName(cb.getUser()));
            List contentList = cb.getMailcontent();
            for (int i = 0; i < contentList.size(); i++) {
                PersonalStaticsBean bean = (PersonalStaticsBean) contentList.
                                           get(i);
                VbTcExcelCopy vb = bean.getBaseInfo();
                vb.setName(hrUtil.getChineseName(vb.getName()));
                if(bean.isHasATT()) {
                    List attList = bean.getAttContent();
                    for(int j = 0;j < attList.size();j++) {
                        WorkFlowBean wkBean = (WorkFlowBean)attList.get(j);
                        wkBean.setSubmiter(hrUtil.getChineseName(wkBean.getSubmiter()));
                    }
                }
            }
        }
    }

    //加入周相应的Weekly Report的提示数据
    public void getAllPersonalStaticsDataForWeeklyReport(Calendar cal,
        int offset) {

        Iterator iter;

        Calendar[] beDay = wc.getNextBEWeekDay(cal, offset);
        //周开始日期
        iYearBegin = beDay[0].get(Calendar.YEAR);
        iMonthBegin = beDay[0].get(Calendar.MONTH) + 1;
        iDayBegin = beDay[0].get(Calendar.DATE);
        //周结束日期
        iYearEnd = beDay[1].get(Calendar.YEAR);
        iMonthEnd = beDay[1].get(Calendar.MONTH) + 1;
        iDayEnd = beDay[1].get(Calendar.DATE);

        begin = iYearBegin +
                (iMonthBegin < 10 ? ("0" + iMonthBegin) :
                 Integer.toString(iMonthBegin))
                +
                (iDayBegin < 10 ? ("0" + iDayBegin) : Integer.toString(iDayBegin));
        end = iYearEnd +
              (iMonthEnd < 10 ? ("0" + iMonthEnd) : Integer.toString(iMonthEnd))
              + (iDayEnd < 10 ? ("0" + iDayEnd) : Integer.toString(iDayEnd));
        System.out.println(begin + "~~~~" + end);

        //此句查出在时间段内填写过但并未被CONFIRM的人
        sqla = "select tcsum.user_id,tcsum.begin_period,tcsum.end_period,tcsum.confirm_status,tcsum.acnt_rid "
               + "from tc_weekly_report_sum tcsum where "
               + "tcsum.confirm_status<>'Confirmed' "
               + "and to_char(tcsum.begin_period ,'yyyymmdd')='" + begin + "'";
        System.out.println(sqla);

        //此句查出在时间段内填写过周报的员工
        String sqlforFilled = "select tcsum.user_id "
                              + "from tc_weekly_report_sum tcsum where "
                              + " to_char(tcsum.begin_period ,'yyyymmdd')='" +
                              begin + "'";

        try {
            rs1 = this.getDbAccessor().executeQuery(sqla);
            rsFilled = this.getDbAccessor().executeQuery(sqlforFilled);

            //处理未Confirm的员工
            while (rs1.next()) {
                String user = rs1.getString("USER_ID");
                if (hm != null && !hm.isEmpty() && hm.get(user) != null) {
                    //如果存在此人，先把这个人取出，然后进行相应的处理，处理完成之后再放回来
                    ContentBean cb = (ContentBean) hm.remove(user);
                    PersonalStaticsBean psb = (PersonalStaticsBean) cb.
                                              getMailcontent().get(0);

                    beginDate = comDate.dateToString(rs1.getDate("begin_period"));
                    endDate = comDate.dateToString(rs1.getDate("end_period"));
                    type = rs1.getString("acnt_rid");
                    IDtoAccount idtoa = new LgAccountUtilImpl().getAccountByRID(new
                        Long(type));
                    type = hrUtil.getChineseName(idtoa.getManager()) + "</td><td>" + idtoa.getId() +
                           "--" + idtoa.getName();
                    DateDul dd = new DateDul();
                    dd.setBeginDate(beginDate);
                    dd.setEndDate(endDate);
                    dd.setType(type);
                    ArrayList alforWf = new ArrayList();
                    ArrayList altempforCB = new ArrayList();
                    if (psb.isHasWF()) { //判断是否有过填写但未CONFRIM的记录
                        alforWf = psb.getWfContent();
                        alforWf.add(dd);
                        psb.setWfContent(alforWf);
                        altempforCB.add(psb);
                        cb.setMailcontent(altempforCB);
                    } else {
                        psb.setHasWF(true);
                        alforWf.add(dd);
                        psb.setWfContent(alforWf);
                        altempforCB.add(psb);
                        cb.setMailcontent(altempforCB);
                    }
                    hm.put(user, cb);

                } //没在初始化HashMap列表中的员工不管

            } //while end

            //记录已经填过的人
            HashMap filledEm = new HashMap(); //用作存所有填过记录的临时LIST
            while (rsFilled.next()) {
                String user = rsFilled.getString("USER_ID");
                filledEm.put(user, user);
            } //没在初始化HashMap列表中的员工不管

            //把未填写周报的员工进行处理
            iter = hm.entrySet().iterator();
            ArrayList allUser = new ArrayList(); //存所有员工的临时LIST
            for (; iter.hasNext(); ) {
                Map.Entry item = (Map.Entry) iter.next();
                String name = item.getKey().toString();
                allUser.add(name);
            }
            for (int i = 0; i < allUser.size(); i++) {
                String t_user = allUser.get(i).toString();
                if (filledEm.get(t_user) == null) { //未填过的人便加入未填信息
                    ContentBean cb = (ContentBean) hm.remove(t_user);
                    PersonalStaticsBean psb = (PersonalStaticsBean) cb.
                                              getMailcontent().get(0);

                    beginDate = iYearBegin + "-" +
                                (iMonthBegin < 10 ? ("0" + iMonthBegin) :
                                 Integer.toString(iMonthBegin))
                                + "-" +
                                (iDayBegin < 10 ? ("0" + iDayBegin) :
                                 Integer.toString(iDayBegin));
                    endDate = iYearEnd + "-" +
                              (iMonthEnd < 10 ? ("0" + iMonthEnd) :
                               Integer.toString(iMonthEnd))
                              + "-" +
                              (iDayEnd < 10 ? ("0" + iDayEnd) :
                               Integer.toString(iDayEnd));

                    DateDul dd = new DateDul();
                    dd.setBeginDate(beginDate);
                    dd.setEndDate(endDate);
                    dd.setType("Not Fill");

                    ArrayList altempforCB = new ArrayList();
                    ArrayList alForWr = new ArrayList();
                    if (psb.isHasWR()) { //判断是否有过未填写的记录，如果有就追加，没有就设为有然后直接放进去
                        alForWr = psb.getWrContent();
                        alForWr.add(dd);
                        psb.setWrContent(alForWr);
                        altempforCB.add(psb);
                        cb.setMailcontent(altempforCB);
                    } else {
                        psb.setHasWR(true);
                        alForWr.add(dd);
                        psb.setWrContent(alForWr);
                        altempforCB.add(psb);
                        cb.setMailcontent(altempforCB);
                    }
                    hm.put(t_user, cb);
                } //填过的人在此处不作处理

            } //for end

        } catch (Throwable tx) {
            String msg =
                "error get all personal statics about the Weekly Report hasten data!";
            log.error(msg);
            throw new BusinessException("", msg, tx);
        }

    }

    //对差勤信息要求确认的数据进行处理，加入到HashMap中。
    public void handlerAttConfirm() {
        //此句查出差勤未审核的记录
        String att = "select wka.performer_loginid,wki.sub_emp_loginid,wki.process_name,wki.description "
                     + "from wk_activity wka,wk_instance wki "
                     + "where wka.instance_id=wki.rid and wka.status='start'";

        try {
            rsAtt = this.getDbAccessor().executeQuery(att);
            while (rsAtt.next()) {
                String user, pManager, title, desc;
                user = rsAtt.getString("sub_emp_loginid");

                if (!hm.isEmpty() && hm.get(user) != null) {
                    //如果存在此人，先把这个人取出，然后进行相应的处理，处理完成之后再放回来
                    ContentBean cb = (ContentBean) hm.remove(user);
                    PersonalStaticsBean psb = (PersonalStaticsBean) cb.
                                              getMailcontent().get(0);

                    pManager = rsAtt.getString("performer_loginid");
                    title = rsAtt.getString("process_name");
                    desc = rsAtt.getString("description");
                    WorkFlowBean wfb = new WorkFlowBean();
                    wfb.setSubmiter(pManager);
                    wfb.setTitle(title);
                    wfb.setDesc(desc);

                    ArrayList alforAtt = new ArrayList();
                    ArrayList altempforCB = new ArrayList();
                    if (psb.isHasATT()) {
                        alforAtt = psb.getAttContent();
                        alforAtt.add(wfb);
                        psb.setAttContent(alforAtt);
                        altempforCB.add(psb);
                        cb.setMailcontent(altempforCB);
                    } else {
                        psb.setHasATT(true);
                        alforAtt.add(wfb);
                        psb.setAttContent(alforAtt);
                        altempforCB.add(psb);
                        cb.setMailcontent(altempforCB);
                    }
                    hm.put(user, cb);

                } //不存在的人不管(可能是不在此HR项目中)

            } //whild end
        } catch (Throwable tx) {
            String msg =
                "error get all personal statics about the attendce infomation!";
            log.error(msg);
            throw new BusinessException("", msg, tx);
        }
    }

    //判断一个字符串是否在一个List中
    public boolean isExistList(String destString, List l) {
        for (int i = 0; i < l.size(); i++) {
            DtoWeeklyReportSumByHr dtowrs = null;
            dtowrs = (DtoWeeklyReportSumByHr) l.get(i);
            if (dtowrs.getUserId().equals(destString)) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {

        Calendar begin = Calendar.getInstance();
        begin.set(2006, 1, 18, 0, 0, 0);
        begin.set(Calendar.MILLISECOND, 0);
        Calendar end = Calendar.getInstance();
        end.set(2006, 10, 24, 0, 0, 0);
        end.set(Calendar.MILLISECOND, 0);
        LgGetAllPersonalStaticsDate gapsd = new LgGetAllPersonalStaticsDate();
        List testList = new ArrayList();
        DtoWeeklyReportSumByHr dto1 = new DtoWeeklyReportSumByHr();
        dto1.setUserId("mingxing.zhang");
        testList.add(dto1);
        DtoWeeklyReportSumByHr dto2 = new DtoWeeklyReportSumByHr();
        dto2.setUserId("stone.shi");
        testList.add(dto2);
        HashMap tempHm = gapsd.getWeeklyReportData("6024", begin, end, "week",
            testList);
        SendHastenMail shm = new SendHastenMail();
        //使用前请先设置Debug Mail !!!
        shm.sendHastenMail("mail/template/tc/personalAllInfoMailTemplate.htm",
                           "Test Mail", tempHm);
        System.out.println(tempHm);
        System.out.println(tempHm.size());

    }


}
