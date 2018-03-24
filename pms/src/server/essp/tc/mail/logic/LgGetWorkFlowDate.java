package server.essp.tc.mail.logic;

import java.util.HashMap;
import java.sql.ResultSet;
import server.framework.logic.AbstractBusinessLogic;
import itf.hr.LgHrUtilImpl;
import server.essp.tc.mail.genmail.contbean.WorkFlowBean;
import java.util.ArrayList;
import itf.hr.HrFactory;
import server.framework.common.BusinessException;
import org.apache.log4j.Category;
import server.essp.common.mail.ContentBean;
import server.essp.common.mail.SendHastenMail;

/**
 *此类用于工作流催促的LOGIC，取得数据
 *author:Robin.Zhang
 */
public class LgGetWorkFlowDate extends AbstractBusinessLogic {
//    protected static Category log = Category.getInstance("server");
    HashMap hm = new HashMap();
    public LgGetWorkFlowDate() {
    }

    /**
     *　取得所有没有被CONFIRM的工作流信息
     *　参数：无
     *　返回：所有数据用一个HashMap装载
     */
    public HashMap getWorkFlowDate() {
        ResultSet rs;

        String sql = "select wka.performer_loginid,wki.sub_emp_loginid,wki.process_name,wki.description "
                     + "from wk_activity wka,wk_instance wki "
                     + "where wka.instance_id=wki.rid and wka.status='start'";
        LgHrUtilImpl ihui = (LgHrUtilImpl) HrFactory.create();
        System.out.println(sql);
        String user, submiter, title, desc, email;
        try {
            rs = this.getDbAccessor().executeQuery(sql);

            while (rs.next()) {
                user = rs.getString("performer_loginid");
                submiter = rs.getString("sub_emp_loginid");
                title = rs.getString("process_name");
                desc = rs.getString("description");

                if (hm.isEmpty() || hm.get(user) == null) {
                    ArrayList al = new ArrayList();
                    ContentBean cb = new ContentBean();

                    WorkFlowBean wfb = new WorkFlowBean();
                    String name = ihui.getChineseName(submiter);
                    if(name != null) {
                        wfb.setSubmiter(name);
                    } else {
                        wfb.setSubmiter(submiter);
                    }
                    wfb.setTitle(title);
                    wfb.setDesc(desc);

                    al.add(wfb);

                    String userName = ihui.getChineseName(user);
                    if (userName != null) {
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

                    WorkFlowBean wfb = new WorkFlowBean();
                    String name = ihui.getChineseName(submiter);
                    if(name != null) {
                        wfb.setSubmiter(name);
                    } else {
                        wfb.setSubmiter(submiter);
                    }
                    wfb.setTitle(title);
                    wfb.setDesc(desc);
                    al.add(wfb);

                    cb.setMailcontent(al);
                    hm.remove(user);
                    hm.put(user, cb);
                }

            } //whild end
//        }catch(Exception ex){
//            ex.printStackTrace();
//        }
        } catch (Throwable tx) {
            String msg = "error get all WorkFlow unconfirm data!";
            log.error(msg, tx);
            throw new BusinessException("", msg, tx);
        }

        return hm;
    }

    //For Test
    public static void main(String[] args) {
        LgGetWorkFlowDate lgwfd = new LgGetWorkFlowDate();
        SendHastenMail shm = new SendHastenMail();
        //使用前请先设置Debug Mail !!!
        shm.sendHastenMail("mail/template/tc/workFlowMailTemplate.htm", "Test",
                           lgwfd.getWorkFlowDate());
    }
}
