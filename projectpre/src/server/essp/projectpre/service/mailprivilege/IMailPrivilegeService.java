package server.essp.projectpre.service.mailprivilege;

import java.util.List;

import server.essp.projectpre.db.MailPrivilege;
import server.framework.common.BusinessException;

public interface IMailPrivilegeService {
	/**
	 * 列出所有有权限的用户信息
	 */
	public List listAllUser() throws BusinessException;

	/**
	 * 从列表中删除当前用户
	 * @throws Exception
	 */
	public void deleteByLoginid(String loginId) throws BusinessException;
	
	/**
	 * 添加新的用户权限信息记录
	 * @param mailP
	 */
	public void add(MailPrivilege mailP);

	/**
	 * 更新用户权限数据
	 * @throws Exception
	 */
	public void update(MailPrivilege mailP);

	/**
	 * 根据loginId显示用户权限
	 * @return
	 * @throws Exception
	 */
	public MailPrivilege loadByLoginid(String loginId) throws BusinessException;
	
	/**
	 * 按申请类型列出有接受通知权限的人员
	 * @param type
	 * @param site
	 * @return
	 */
	public List listToNotice(String type, String site);

}
