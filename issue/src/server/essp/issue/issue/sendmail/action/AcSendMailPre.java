package server.essp.issue.issue.sendmail.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import server.essp.issue.common.action.AbstractISSUEAction;
import server.framework.common.BusinessException;
import server.essp.issue.common.form.MailInputDataBean;
import server.essp.issue.issue.sendmail.logic.LgDefaultTemplate;
import db.essp.issue.IssueMailDefaultTemp;
import server.essp.issue.issue.sendmail.viewbean.VbSendMailInfo;
import server.essp.common.mail.GenMailContent;
import server.essp.common.mail.ContentBean;
import java.util.ArrayList;
import server.essp.issue.issue.sendmail.logic.LgMailTemplate;
import java.util.List;
import server.framework.taglib.util.SelectOptionImpl;
import db.essp.issue.IssueMailTemplate;
import db.essp.issue.IssueMailDefaultTocc;
import server.essp.issue.issue.sendmail.logic.lgDefaultToCc;
import server.essp.issue.issue.sendmail.util.HanderUtil;

public class AcSendMailPre extends AbstractISSUEAction {
    public AcSendMailPre() {
    }

    /**
     * ���뷢���ʼ��Ľ���ʱ���е�Ԥ����
     *
     * @param httpServletRequest HttpServletRequest
     * @param httpServletResponse HttpServletResponse
     * @param transactionData TransactionData
     * @throws BusinessException
     * @todo Implement this server.framework.action.AbstractBusinessAction
     *   method
     */
    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response,
                           TransactionData data) throws
        BusinessException {
        MailInputDataBean midb = null;
        if (request.getSession().getAttribute("inputData") != null) {
            midb = (MailInputDataBean) request.getSession().getAttribute("inputData");
        }
        VbSendMailInfo sendMailInfo = new VbSendMailInfo();
        //���ݴ�������ֵҪ�������Ӧ��ֵ��Ȼ���ٽ���ҳ����
        //1����ģ�漰Ĭ��ģ��
        LgMailTemplate lgMailTemp = new LgMailTemplate();
        List tempList = lgMailTemp.getTempList(midb.getIssueType(),
                                               midb.getCardType());
        List templateList = new ArrayList(); //ҳ������ʾ�õ�(Select List)
        for (int i = 0; i < tempList.size(); i++) {
            IssueMailTemplate mailTemp = (IssueMailTemplate) tempList.get(i);
            templateList.add(new SelectOptionImpl(mailTemp.getTemplatename(),
                                                  mailTemp.getRid().toString()));
        }
        sendMailInfo.setTemplateList(templateList);

        LgDefaultTemplate lgDefaultTemp = new LgDefaultTemplate();
        IssueMailDefaultTemp defaultTemp = lgDefaultTemp.getByCondition(midb.
            getAcntRid(), midb.getIssueType(), midb.getCardType());
        Long defTempId = null;
        if (defaultTemp != null) {
            defTempId = defaultTemp.getDefaulttemplateid();
        }
        if (defTempId != null) {
            sendMailInfo.setDefaultTemp(defTempId.toString());
        } else { //���ν����Ҳ���Ĭ��ģ�棬���Զ�ѡ���һ��
            if (tempList.size() > 0) {
                IssueMailTemplate temp = (IssueMailTemplate) tempList.get(0);
                sendMailInfo.setDefaultTemp(temp.getRid().toString());
                defTempId = temp.getRid();
            } else {
                throw new BusinessException("", "û�ж����κ�ģ�棬���ȶ��巢���ʼ���ģ��");
            }
        }

        //2����MAILTO��CC,Title
        IssueMailDefaultTocc defTocc;
        lgDefaultToCc lgToCc = new lgDefaultToCc();
        defTocc = lgToCc.getByCondition(midb.getAcntRid(), midb.getIssueType(),
                                        defTempId);
        if (defTocc != null) {
            sendMailInfo.setMailTo(HanderUtil.contractUser(midb.getMailTo(),
                defTocc.getMailto()));
            sendMailInfo.setCc(HanderUtil.contractUser(midb.getCc(),
                defTocc.getCc()));
        } else {
            sendMailInfo.setMailTo(midb.getMailTo());
            sendMailInfo.setCc(midb.getCc());
        }

        sendMailInfo.setTitle(midb.getTitle());

        //3�������������ֵ,���ݴ���������Ϣ��DataBean
        GenMailContent genMail = new GenMailContent();
        ContentBean contBean = new ContentBean();
        contBean.setUser(sendMailInfo.getMailTo()); //��������ȥ
        contBean.setEmail(""); //�����������ɵ�EMAIL
        contBean.setCcAddress(""); //�����˵ĵ�ַ
        ArrayList al = new ArrayList();
        al.add(midb.getDataBean());
        contBean.setMailcontent(al);

        //�ҳ�ģ�沢��������
        sendMailInfo.setContent(genMail.generateMailContent(lgMailTemp.
            getTemplatePath(defTempId), contBean));

        //4���ø���
        sendMailInfo.setAttachments(midb.getAttachments());

        //5�����´��õ������ͱ���Ҫ��ʾ��ֵ
        request.setAttribute("inputData", midb);
        request.setAttribute("webVo", sendMailInfo);
    }

    //�õ�һ���ö��ŷָ��ַ����������˵�MAIL��ַ
    public String getEmailByUserList(String user) {
        String mailAdd = "";

        return mailAdd;
    }


}
