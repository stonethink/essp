package server.essp.common.mail;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.dto.TransactionData;
import c2s.essp.common.mail.DtoMail;
import server.framework.action.AbstractBusinessAction;
import server.framework.common.BusinessException;

public class AcMailSendByEmailAddress extends AbstractBusinessAction{

    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData transData) throws BusinessException {

        InputInfo inputInfo = transData.getInputInfo();
        ReturnInfo returnInfo = transData.getReturnInfo();

        DtoMail dto = (DtoMail)inputInfo.getInputObj(DtoMail.MAIL_KEY);

        String configFile = dto.getConfigFile();
        String content = dto.getContent();
        String title = dto.getTitle();
        String address = dto.getToAddress();
        String ccAddress = dto.getCcAddress();

        LgMailSendByConfig logic = new LgMailSendByConfig(configFile);

        logic.send(null, null, address, ccAddress, title, content);

    }
}
