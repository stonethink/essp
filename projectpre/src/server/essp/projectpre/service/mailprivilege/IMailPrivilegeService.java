package server.essp.projectpre.service.mailprivilege;

import java.util.List;

import server.essp.projectpre.db.MailPrivilege;
import server.framework.common.BusinessException;

public interface IMailPrivilegeService {
	/**
	 * �г�������Ȩ�޵��û���Ϣ
	 */
	public List listAllUser() throws BusinessException;

	/**
	 * ���б���ɾ����ǰ�û�
	 * @throws Exception
	 */
	public void deleteByLoginid(String loginId) throws BusinessException;
	
	/**
	 * ����µ��û�Ȩ����Ϣ��¼
	 * @param mailP
	 */
	public void add(MailPrivilege mailP);

	/**
	 * �����û�Ȩ������
	 * @throws Exception
	 */
	public void update(MailPrivilege mailP);

	/**
	 * ����loginId��ʾ�û�Ȩ��
	 * @return
	 * @throws Exception
	 */
	public MailPrivilege loadByLoginid(String loginId) throws BusinessException;
	
	/**
	 * �����������г��н���֪ͨȨ�޵���Ա
	 * @param type
	 * @param site
	 * @return
	 */
	public List listToNotice(String type, String site);

}
