package server.essp.projectpre.service.sendMail;

import java.util.HashMap;



public interface ISendMailService {
	/**
	 * ����E-MAIL
	 * @param toUser �ռ��ˣ�loginId��
	 * @param title  e-mail�ı���
	 * @param obj    e-mail������
	 */
    public void sendMail(String loginId, String doMain,String vmFile,String title,Object obj);
    
    /**
     * ���ʹ�����E-MAIL
     * @param loginId �ռ��ˣ�loginId��
     * @param doMain
     * @param vmFile
     * @param title   e-mail�ı���
     * @param obj     e-mail������
     * @param attachMap
     */
    public void sendMail(String loginId, String doMain,String vmFile,String title,Object obj, HashMap attachMap);
    
}
