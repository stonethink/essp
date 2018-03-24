package server.essp.issue.issue.sendmail.action;

import server.framework.common.BusinessException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import server.essp.issue.common.form.MailInputDataBean;
import db.essp.issue.IssueMailDefaultTocc;
import server.essp.issue.issue.sendmail.logic.lgDefaultToCc;
import server.essp.issue.issue.sendmail.util.HanderUtil;
import server.essp.common.mail.GenMailContent;
import server.essp.common.mail.ContentBean;
import java.util.ArrayList;
import server.essp.issue.issue.sendmail.logic.LgMailTemplate;
import java.io.*;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;

public class AcChangeTemplate extends Action {
    public AcChangeTemplate() {
    }

    //ִ�иı�ģ����Ӧ�Ķ���
    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                    HttpServletRequest request,
                                    HttpServletResponse response){
        System.out.println("in AcChangeTemplate Action!");
        //�ı�ģ����Ҫ�ı�TO��CC��EMAIL��������
        String returnResult = "";
        String tempId = "0";
        MailInputDataBean midb = new MailInputDataBean();
        if (request.getParameter("TemplateId") != null) {
            tempId = (String) request.getParameter("TemplateId");
        }
        if (request.getSession().getAttribute("inputData") != null) {
            midb = (MailInputDataBean) request.getSession().getAttribute(
                "inputData");
        }

        IssueMailDefaultTocc defTocc;
        lgDefaultToCc lgToCc = new lgDefaultToCc();
        defTocc = lgToCc.getByCondition(midb.getAcntRid(), midb.getIssueType(),
                                        new Long(tempId));
        String mailTo = "";
        String cc = "";
        if (defTocc != null) {
            mailTo = HanderUtil.contractUser(midb.getMailTo(),
                                             defTocc.getMailto());
            cc = HanderUtil.contractUser(midb.getCc(), defTocc.getCc());
        } else {
            mailTo = midb.getMailTo() == null ? "" : midb.getMailTo();
            cc = midb.getCc() == null ? "" : midb.getCc();
        }

        GenMailContent genMail = new GenMailContent();
        ContentBean contBean = new ContentBean();
        contBean.setUser(mailTo); //��������ȥ
        contBean.setEmail(""); //�����������ɵ�EMAIL
        contBean.setCcAddress(""); //�����˵ĵ�ַ
        ArrayList al = new ArrayList();
        al.add(midb.getDataBean());
        contBean.setMailcontent(al);

        //�ҳ�ģ�沢��������
        LgMailTemplate lgmail = new LgMailTemplate();
        String tempPath = lgmail.getTemplatePath(new Long(tempId));
        String mailCont = "�޴�ģ��";
        if (tempPath != null && !tempPath.equals("")) {
            mailCont = genMail.generateMailContent(tempPath, contBean);
        }
        returnResult = mailTo + ",,,,," + cc + ",,,,," + mailCont;

        try {
            response.setContentType("text/html;charset=utf-8");
            PrintWriter out = response.getWriter();
//            out.print("<content><mailTo>" + mailTo + "</mailTo><cc>" + cc +
//                      "</cc><mailCont>" + mailCont + "</mailCont></content>");
            out.print(returnResult);
            //out.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
           // throw new BusinessException("", "д��ģ��ı�ʱ�ı任���ʱ�쳣");
        }

        return null;
    }
}
