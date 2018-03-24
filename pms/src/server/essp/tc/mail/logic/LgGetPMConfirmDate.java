package server.essp.tc.mail.logic;

import java.util.HashMap;
import java.sql.ResultSet;
import server.framework.logic.AbstractBusinessLogic;
import itf.hr.LgHrUtilImpl;
import java.util.ArrayList;
import server.essp.pms.account.logic.LgAccountUtilImpl;
import c2s.essp.common.account.IDtoAccount;
import server.essp.tc.mail.genmail.contbean.PMConfirmBean;
import itf.hr.HrFactory;
import server.framework.common.BusinessException;
import server.essp.common.mail.ContentBean;
import itf.hr.IHrUtil;
import server.essp.common.mail.SendHastenMail;
import com.wits.util.comDate;

/**
 *此类用于周报催促LOGIC
 *author:Robin.Zhang
 */
public class LgGetPMConfirmDate extends AbstractBusinessLogic {
    public LgGetPMConfirmDate() {
    }
    /**
     *　取得所有项目经理没有CONFIRM的周报数据
     *　参数：无
     *　返回：所有数据用一个HashMap装载
     */
    public HashMap getPMConfirmDate() {
        HashMap hm = new HashMap();
        ResultSet rs;

        String sql = "select tcsum.acnt_rid,tcsum.user_id,tcsum.begin_period,tcsum.end_period "
                     + "from tc_weekly_report_sum tcsum "
                     + "where tcsum.confirm_status='Locked'";

        LgHrUtilImpl ihui = (LgHrUtilImpl)HrFactory.create();
        System.out.println(sql);
        String user, submiter, begindate, enddate, email,acntId,accountName;
        try {
            rs = this.getDbAccessor().executeQuery(sql);

            while (rs.next()) {
                acntId=rs.getString("acnt_rid");
                IDtoAccount idtoa=new LgAccountUtilImpl().getAccountByRID(new Long(acntId));
                user = idtoa.getManager();
                submiter = rs.getString("user_id");
                begindate = comDate.dateToString(rs.getDate("begin_period"));
                enddate = comDate.dateToString(rs.getDate("end_period"));
                accountName=idtoa.getDisplayName();
                if (hm.isEmpty() || hm.get(user) == null) {
                    ArrayList al = new ArrayList();
                    ContentBean cb = new ContentBean();

                    PMConfirmBean pmcb = new PMConfirmBean();
                    String name = ihui.getChineseName(submiter);
                    if(name != null) {
                        pmcb.setSubmiter(name);
                    } else {
                        pmcb.setSubmiter(submiter);
                    }
                    pmcb.setBeginDate(begindate);
                    pmcb.setEndDate(enddate);
                    pmcb.setAccountInfo(accountName);
                    al.add(pmcb);

                    String userName = ihui.getChineseName(user);
                    if(userName != null) {
                        cb.setUser(userName);
                    } else {
                        cb.setUser(user);
                    }
                    email = ihui.getUserEmail(user);
                    cb.setEmail(email);
                    cb.setMailcontent(al);
                    hm.put(user, cb);
                } else {
                    ArrayList al = new ArrayList();
                    ContentBean cb = (ContentBean) hm.get(user);
                    al = cb.getMailcontent();

                    PMConfirmBean pmcb = new PMConfirmBean();
                    String name = ihui.getChineseName(submiter);
                    if(name != null) {
                        pmcb.setSubmiter(name);
                    } else {
                        pmcb.setSubmiter(submiter);
                    }
                    pmcb.setBeginDate(begindate);
                    pmcb.setEndDate(enddate);
                    pmcb.setAccountInfo(accountName);
                    al.add(pmcb);

                    String userName = ihui.getChineseName(user);
                    if(userName != null) {
                        cb.setUser(userName);
                    } else {
                        cb.setUser(user);
                    }

                    cb.setMailcontent(al);
                    hm.remove(user);
                    hm.put(user, cb);
                }

            } //whild end
        }catch(Throwable tx){
                    String msg = "error get all PM unconfirm hasten data!";
                    throw new BusinessException("",msg,tx);
        }


        return hm;
    }


    public static void main(String[] args) {
        LgGetPMConfirmDate lgpmcd = new LgGetPMConfirmDate();
//        System.out.println(lgpmcd.getPMConfirmDate());
        SendHastenMail shm = new SendHastenMail();
        //使用前请先设置Debug Mail !!!
        shm.sendHastenMail("mail/template/tc/PMConfirmMailTemplate.htm",
                           "Test Mail", lgpmcd.getPMConfirmDate());

    }
}
