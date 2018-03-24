package client.essp.common.mailUtil;

import c2s.essp.common.mail.DtoMail;

public interface IMailProvider {
    boolean beforeSendMail();
    DtoMail getMail();
    //void afterSendMail();
}
