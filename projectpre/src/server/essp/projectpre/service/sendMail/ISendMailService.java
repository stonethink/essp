package server.essp.projectpre.service.sendMail;

import java.util.HashMap;



public interface ISendMailService {
	/**
	 * 发送E-MAIL
	 * @param toUser 收件人（loginId）
	 * @param title  e-mail的标题
	 * @param obj    e-mail的内容
	 */
    public void sendMail(String loginId, String doMain,String vmFile,String title,Object obj);
    
    /**
     * 发送带附件E-MAIL
     * @param loginId 收件人（loginId）
     * @param doMain
     * @param vmFile
     * @param title   e-mail的标题
     * @param obj     e-mail的内容
     * @param attachMap
     */
    public void sendMail(String loginId, String doMain,String vmFile,String title,Object obj, HashMap attachMap);
    
}
