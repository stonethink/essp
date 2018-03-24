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
     * 进入发送邮件的界面时进行的预处理
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
        //根据传进来的值要计算出相应的值，然后再进入页面中
        //1计算模版及默认模版
        LgMailTemplate lgMailTemp = new LgMailTemplate();
        List tempList = lgMailTemp.getTempList(midb.getIssueType(),
                                               midb.getCardType());
        List templateList = new ArrayList(); //页面上显示用的(Select List)
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
        } else { //初次进入找不到默认模版，则自动选择第一个
            if (tempList.size() > 0) {
                IssueMailTemplate temp = (IssueMailTemplate) tempList.get(0);
                sendMailInfo.setDefaultTemp(temp.getRid().toString());
                defTempId = temp.getRid();
            } else {
                throw new BusinessException("", "没有定义任何模版，请先定义发送邮件的模版");
            }
        }

        //2计算MAILTO，CC,Title
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

        //3计算内容里面的值,根据传进来的信息和DataBean
        GenMailContent genMail = new GenMailContent();
        ContentBean contBean = new ContentBean();
        contBean.setUser(sendMailInfo.getMailTo()); //放人名进去
        contBean.setEmail(""); //根据人名生成的EMAIL
        contBean.setCcAddress(""); //抄送人的地址
        ArrayList al = new ArrayList();
        al.add(midb.getDataBean());
        contBean.setMailcontent(al);

        //找出模版并生成内容
        sendMailInfo.setContent(genMail.generateMailContent(lgMailTemp.
            getTemplatePath(defTempId), contBean));

        //4设置附件
        sendMailInfo.setAttachments(midb.getAttachments());

        //5设置下次用的条件和本次要显示的值
        request.setAttribute("inputData", midb);
        request.setAttribute("webVo", sendMailInfo);
    }

    //得到一个用逗号分隔字符串的所有人的MAIL地址
    public String getEmailByUserList(String user) {
        String mailAdd = "";

        return mailAdd;
    }


}
