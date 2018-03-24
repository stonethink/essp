package server.essp.common.mail;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.dto.TransactionData;
import c2s.essp.common.mail.DtoMail;
import c2s.essp.common.user.DtoUser;
import itf.hr.HrFactory;
import itf.hr.IHrUtil;
import server.framework.action.AbstractBusinessAction;
import server.framework.common.BusinessException;

public class AcMailSendByUserId extends AbstractBusinessAction{

    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData transData) throws BusinessException {

        InputInfo inputInfo = transData.getInputInfo();
        ReturnInfo returnInfo = transData.getReturnInfo();

        DtoMail dto = (DtoMail)inputInfo.getInputObj(DtoMail.MAIL_KEY);

        String configFile = dto.getConfigFile();
        String content = dto.getContent();
        String title = dto.getTitle();
        String userIds = dto.getToUserId();
        String ccUserIds = dto.getCcUserId();

        IHrUtil hrUtil = HrFactory.create();
        String toAddress = hrUtil.getUserEmail(userIds);
        String ccAddress = null;
        if( ccUserIds != null ){
            ccAddress = hrUtil.getUserEmail(ccUserIds);
        }

        LgMailSendByConfig logic = new LgMailSendByConfig(configFile);
        logic.send(null, null,
                   toAddress, ccAddress,
                   title, content);


    }
}
