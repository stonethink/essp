package server.essp.issue.issue.sendmail.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import server.essp.issue.common.action.AbstractISSUEAction;
import server.framework.common.BusinessException;
import server.essp.issue.issue.sendmail.form.AfSendContent;
import server.essp.issue.common.form.MailInputDataBean;
import server.essp.issue.issue.sendmail.util.HanderUtil;
import server.essp.issue.issue.sendmail.logic.LgDefaultTemplate;
import db.essp.issue.IssueMailDefaultTemp;
import server.essp.issue.issue.sendmail.logic.lgDefaultToCc;
import db.essp.issue.IssueMailDefaultTocc;
import java.util.Iterator;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Map;

public class AcSendMail extends AbstractISSUEAction {
    public AcSendMail() {
    }

    /**
     * executeAct
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
        AfSendContent sendForm = (AfSendContent)this.getForm();

        String tempId = "0";
        if (request.getParameter("TemplateId") != null) {
            tempId = (String) request.getParameter("TemplateId");
        }
        //得到输入的条件
        MailInputDataBean midb = new MailInputDataBean();
        if (request.getSession().getAttribute("inputData") != null) {
            midb = (MailInputDataBean) request.getSession().getAttribute(
                "inputData");
        }
        //需要做：1发送邮件，2存默认模版，3更新默认TO&CC
        //先转换TO和CC为真正的EMAIL地址并发送邮件
        HanderUtil sendUtil = new HanderUtil();
        String address = sendUtil.convertLoginNameToEmailAdd(sendForm.getMailTo());
        String ccAddress = sendUtil.convertLoginNameToEmailAdd(sendForm.getCc());
        //处理附件
        String[] att = request.getParameterValues("attachment");
        HashMap attachments = null;
        if (att != null && att.length > 0 && midb.getAttachments() != null) {
            attachments = new HashMap();
            HashMap hm = midb.getAttachments();
            String server_root = "";
            server_root = "http://" + request.getServerName();
            if (request.getServerPort() != 80) {
                server_root = server_root + ":" + request.getServerPort();
            }
            for (int i = 0; i < att.length; i++) {
                String fileUri = (String) hm.get(att[i]);
                fileUri = server_root + fileUri;
                attachments.put(att[i], fileUri);
            }
        }

        sendUtil.sendMail(address, ccAddress,
                          sendForm.getTitle(), midb.getFrom(),
                          sendForm.getContent(), attachments);

        //存默认模版(如果现在没有的话)
        LgDefaultTemplate lgDefaultTemp = new LgDefaultTemplate();
        IssueMailDefaultTemp defaultTemp = lgDefaultTemp.getByCondition(midb.
            getAcntRid(), midb.getIssueType(), midb.getCardType());
        if (defaultTemp == null) { //说明没有默认的模版
            //把当前使用的模版作为默认模版加入
            defaultTemp = new IssueMailDefaultTemp();
            defaultTemp.setAcntRid(midb.getAcntRid());
            defaultTemp.setIssuetype(midb.getIssueType());
            defaultTemp.setCardtype(midb.getCardType());
            defaultTemp.setDefaulttemplateid(new Long(tempId));
            lgDefaultTemp.add(defaultTemp);
        }

        //更新默认TO和CC
        IssueMailDefaultTocc defTocc; //=new IssueMailDefaultTocc();
        lgDefaultToCc lgToCc = new lgDefaultToCc();
        defTocc = lgToCc.getByCondition(midb.getAcntRid(), midb.getIssueType(),
                                        new Long(tempId));
        String defTo = HanderUtil.subtractString(sendForm.getMailTo(),
                                                 midb.getMailTo());
        String defCc = HanderUtil.subtractString(sendForm.getCc(), midb.getCc());
        if (defTocc == null) {
            defTocc = new IssueMailDefaultTocc();
            defTocc.setAcntRid(midb.getAcntRid());
            defTocc.setIssuetype(midb.getIssueType());
            defTocc.setTemplaterid(new Long(tempId));
            defTocc.setMailto(defTo);
            defTocc.setCc(defCc);
            lgToCc.save(defTocc);
        } else {
            defTocc.setMailto(defTo);
            defTocc.setCc(defCc);
            lgToCc.update(defTocc);

        }
    }
}
