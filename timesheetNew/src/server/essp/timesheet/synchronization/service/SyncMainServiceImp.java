package server.essp.timesheet.synchronization.service;

import java.util.*;

import com.wits.util.Config;

import server.essp.common.mail.ContentBean;
import server.essp.common.mail.SendHastenMail;
import server.essp.timesheet.account.dao.IAccountDao;
import server.essp.timesheet.synchronization.syncexception.dao.ISyncExceptionDao;

import c2s.essp.timesheet.synchronization.DtoSync;
import db.essp.timesheet.TsAccount;
import db.essp.timesheet.TsException;

public class SyncMainServiceImp implements ISyncMainService {
	
	private List syncList;
	private ISyncData syncData;
	private IAccountDao accountDao;
	private ISyncExceptionDao syncExceptionDao;
	public static final String vmFile1 = "mail/template/ts/SyncErrorMail.htm";
	public void setSyncList(List syncList) {
		this.syncList = syncList;
	}

	public void delete(String employeeId, Long accountRid) {
		int size = syncList.size();
		boolean isError = false;
		TsAccount account = accountDao.loadAccount(accountRid);
		if(account == null) {
			return;
		}
		DtoSync dto = new DtoSync();
		dto.setEmployeeId(employeeId);
		dto.setProjectId(account.getAccountId());
		for(int i = 0; i < size; i++){
			syncData = (ISyncData) syncList.get(i);
			try{
				syncData.delete(dto);
			} catch(Exception e) {
				addException(dto, syncData.getFunctionName(), e.getMessage(), "Delete");
				isError = true;
			}
		}
		if(isError) {//出错发送邮件给管理员
			HashMap hm = getMailMap();
			sendMail(vmFile1, "Synchronise error", hm);
		}
	}

	public void insert(List<DtoSync> dtoList) {
		int size = syncList.size();
		boolean isError = false;
		for(int i = 0; i < size; i++){
			syncData = (ISyncData) syncList.get(i);
			for(DtoSync dto : dtoList) {
				try{
					syncData.add(dto);
				}catch(Exception e) {
					addException(dto, syncData.getFunctionName(), e.getMessage(), "Insert");
					isError = true;
				}
			}
		}
		if(isError) {//出错发送邮件给管理员
			HashMap hm = getMailMap();
			sendMail(vmFile1, "Synchronise error", hm);
		}
	}
	/**
	 * 新增同步异常记录
	 * @param dto
	 * @param functionName
	 * @param errorMessage
	 */
	private void addException(DtoSync dto, String functionName, String errorMessage, String operation) {
		TsException exception = new TsException();
		if(SyncToHrmsImp.class.getName().equals(functionName)) {
			exception.setModel("Hrms");
		}
		exception.setProjectId(dto.getProjectId());
		exception.setEmployeeId(dto.getEmployeeId());
		exception.setErrorMessage(errorMessage);
		exception.setStatus("Pending");
		exception.setOperation(operation);
		syncExceptionDao.insert(exception);
	}

	public void update(DtoSync dto) {
		// TODO Auto-generated method stub

	}
	public static HashMap getMailMap() {
		Config cf = new Config("SyncMail");
		String userName = cf.getValue("MasterName");
		String userMail = cf.getValue("MasterMail");
		HashMap hm = new HashMap();
		ArrayList al = new ArrayList();
		ContentBean cb = new ContentBean();
		cb.setUser(userName);
		cb.setEmail(userMail);
		cb.setMailcontent(al);
		hm.put(userName, cb);
		return hm;
	}
	public List dataToDtoList(List<String> loginIds, Long accountRid) {
		if(loginIds == null || "".equals(loginIds)) {
			return new ArrayList();
		}
		TsAccount account = accountDao.loadAccount(accountRid);
		if(account == null) {
			return new ArrayList();
		}
		String deptFlag = account.getDeptFlag();
		String projectId = account.getAccountId();
		List resultList = new ArrayList();
		DtoSync dto = null;
		String tempStr = "";
		for(String loginId : loginIds) {
			tempStr = loginId.substring(0, 2);
			if("1".equals(deptFlag) && !tempStr.equalsIgnoreCase("TP")) {
				continue;
			}
			dto = new DtoSync();
			dto.setEmployeeId(loginId);
			dto.setProjectId(projectId);
			dto.setDeptFlag(deptFlag);
			resultList.add(dto);
		}
		return resultList;
	}
	/**
	 * 发送邮件
	 * 
	 * @param vmFile
	 * @param title
	 * @param hm
	 */
	public static void sendMail(final String vmFile, final String title,
			final HashMap hm) {
		final SendHastenMail shMail = new SendHastenMail();
		shMail.sendHastenMail(vmFile, title, hm);
	}
	public void setAccountDao(IAccountDao accountDao) {
		this.accountDao = accountDao;
	}

	public void setSyncExceptionDao(ISyncExceptionDao syncExceptionDao) {
		this.syncExceptionDao = syncExceptionDao;
	}
	

}
