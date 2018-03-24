package server.essp.projectpre.service.sendMail;




import java.util.ArrayList;
import java.util.HashMap;

import c2s.essp.common.user.DtoUserInfo;
import server.essp.common.ldap.LDAPUtil;
import server.essp.common.mail.ContentBean;
import server.essp.common.mail.SendHastenMail;
import server.framework.logic.AbstractBusinessLogic;

public class SendMailServiceImpl extends AbstractBusinessLogic implements
		ISendMailService {
	/**
	 * 发送E-MAIL
	 * @param toUser 收件人（loginId）
	 * @param title  e-mail的标题
	 * @param obj    e-mail的内容
	 */
    public void sendMail(String loginId, String doMain, String vmFile, String title, Object obj) {
        sendMail( loginId,  doMain,  vmFile,  title,  obj, null);
    }
 
    /**
     * 发送带附件E-MAIL
     * @param loginId 收件人（loginId）
     * @param doMain
     * @param vmFile
     * @param title   e-mail的标题
     * @param obj     e-mail的内容
     * @param attachMap
     */
	public void sendMail(final String loginId,  String doMain,final  String vmFile,final  String title, final Object obj,final  HashMap attachMap) {
        final HashMap hm = new HashMap();
		
        
        try {

            ArrayList al = new ArrayList();
            al.add(obj);
            
            LDAPUtil  ldapUtil = new LDAPUtil();
            if(doMain == null || "".equals(doMain)) {
            	doMain = LDAPUtil.DOMAIN_ALL;
            }
            DtoUserInfo dtoUser = ldapUtil.findUser(doMain,loginId);
            if(dtoUser!=null){
                String userMail = dtoUser.getEmail();
                String userName = dtoUser.getUserName();
                ContentBean cb = new ContentBean();

                cb.setUser(userName);

                cb.setEmail(userMail);
                cb.setMailcontent(al);
                if(attachMap!=null&&attachMap.size()>0){
                    cb.setAttachments(attachMap);
                }
            
                hm.put(userName, cb);
                final SendHastenMail shMail = new SendHastenMail();
                Thread t = new Thread(new Runnable(){
                    public void run() {
                        shMail.sendHastenMail( vmFile,  title,  hm); 
                    }
                });
                try{
                    
                    t.start();
                }catch(Throwable tx){
                    tx.printStackTrace();
                    t.stop();
                }
               
            }
            
        } catch (Throwable tx) {
            String msg = "error get all Email data!";
            log.error(msg, tx);
            // throw new BusinessException("", msg, tx);
        }


	}

}
