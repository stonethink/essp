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
 *����������ȡ�ø��˵�����������ݣ�����HR�ֶ����͵�����
 *��@author:Robin.Zhang
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

    /**��ʼ������HashMap,���ѻ������ݣ�ָ�˶����ݣ�����
     * @param accountId Long
     * @param accountId Calendar
     * @param endPeriod Calendar
     * @param wmflag Calendar
     * @param tcList Calendar //ֻ���ڴ�List�е��˲ŷ��ʼ�
     */
    public void initContent(Long accountId, Calendar beginPeriod,
                            Calendar endPeriod, String wmflag, List tcList) {
        LgTcExcel lgData = new LgTcExcel();
        List data = lgData.AllDetailList(accountId, beginPeriod.getTime(),
                                         endPeriod.getTime(), wmflag);
        List labors = null;

        //����Ա���Ĳ�����ϢList����data[1]��
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

    /**�õ��ܱ�ͳ����Ϣ����������ݵ���Ϣ
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

    //��������Ӧ��Weekly Report����ʾ����
    public void getAllPersonalStaticsDataForWeeklyReport(Calendar cal,
        int offset) {

        Iterator iter;

        Calendar[] beDay = wc.getNextBEWeekDay(cal, offset);
        //�ܿ�ʼ����
        iYearBegin = beDay[0].get(Calendar.YEAR);
        iMonthBegin = beDay[0].get(Calendar.MONTH) + 1;
        iDayBegin = beDay[0].get(Calendar.DATE);
        //�ܽ�������
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

        //�˾�����ʱ�������д������δ��CONFIRM����
        sqla = "select tcsum.user_id,tcsum.begin_period,tcsum.end_period,tcsum.confirm_status,tcsum.acnt_rid "
               + "from tc_weekly_report_sum tcsum where "
               + "tcsum.confirm_status<>'Confirmed' "
               + "and to_char(tcsum.begin_period ,'yyyymmdd')='" + begin + "'";
        System.out.println(sqla);

        //�˾�����ʱ�������д���ܱ���Ա��
        String sqlforFilled = "select tcsum.user_id "
                              + "from tc_weekly_report_sum tcsum where "
                              + " to_char(tcsum.begin_period ,'yyyymmdd')='" +
                              begin + "'";

        try {
            rs1 = this.getDbAccessor().executeQuery(sqla);
            rsFilled = this.getDbAccessor().executeQuery(sqlforFilled);

            //����δConfirm��Ա��
            while (rs1.next()) {
                String user = rs1.getString("USER_ID");
                if (hm != null && !hm.isEmpty() && hm.get(user) != null) {
                    //������ڴ��ˣ��Ȱ������ȡ����Ȼ�������Ӧ�Ĵ����������֮���ٷŻ���
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
                    if (psb.isHasWF()) { //�ж��Ƿ��й���д��δCONFRIM�ļ�¼
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

                } //û�ڳ�ʼ��HashMap�б��е�Ա������

            } //while end

            //��¼�Ѿ��������
            HashMap filledEm = new HashMap(); //���������������¼����ʱLIST
            while (rsFilled.next()) {
                String user = rsFilled.getString("USER_ID");
                filledEm.put(user, user);
            } //û�ڳ�ʼ��HashMap�б��е�Ա������

            //��δ��д�ܱ���Ա�����д���
            iter = hm.entrySet().iterator();
            ArrayList allUser = new ArrayList(); //������Ա������ʱLIST
            for (; iter.hasNext(); ) {
                Map.Entry item = (Map.Entry) iter.next();
                String name = item.getKey().toString();
                allUser.add(name);
            }
            for (int i = 0; i < allUser.size(); i++) {
                String t_user = allUser.get(i).toString();
                if (filledEm.get(t_user) == null) { //δ������˱����δ����Ϣ
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
                    if (psb.isHasWR()) { //�ж��Ƿ��й�δ��д�ļ�¼������о�׷�ӣ�û�о���Ϊ��Ȼ��ֱ�ӷŽ�ȥ
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
                } //��������ڴ˴���������

            } //for end

        } catch (Throwable tx) {
            String msg =
                "error get all personal statics about the Weekly Report hasten data!";
            log.error(msg);
            throw new BusinessException("", msg, tx);
        }

    }

    //�Բ�����ϢҪ��ȷ�ϵ����ݽ��д������뵽HashMap�С�
    public void handlerAttConfirm() {
        //�˾�������δ��˵ļ�¼
        String att = "select wka.performer_loginid,wki.sub_emp_loginid,wki.process_name,wki.description "
                     + "from wk_activity wka,wk_instance wki "
                     + "where wka.instance_id=wki.rid and wka.status='start'";

        try {
            rsAtt = this.getDbAccessor().executeQuery(att);
            while (rsAtt.next()) {
                String user, pManager, title, desc;
                user = rsAtt.getString("sub_emp_loginid");

                if (!hm.isEmpty() && hm.get(user) != null) {
                    //������ڴ��ˣ��Ȱ������ȡ����Ȼ�������Ӧ�Ĵ����������֮���ٷŻ���
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

                } //�����ڵ��˲���(�����ǲ��ڴ�HR��Ŀ��)

            } //whild end
        } catch (Throwable tx) {
            String msg =
                "error get all personal statics about the attendce infomation!";
            log.error(msg);
            throw new BusinessException("", msg, tx);
        }
    }

    //�ж�һ���ַ����Ƿ���һ��List��
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
        //ʹ��ǰ��������Debug Mail !!!
        shm.sendHastenMail("mail/template/tc/personalAllInfoMailTemplate.htm",
                           "Test Mail", tempHm);
        System.out.println(tempHm);
        System.out.println(tempHm.size());

    }


}
