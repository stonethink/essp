package server.essp.timesheet.synchronization.syncexception.service;

import java.util.List;

import server.essp.timesheet.account.dao.IAccountDao;
import server.essp.timesheet.rmmaint.service.IRmMaintService;
import server.essp.timesheet.synchronization.syncexception.dao.ISyncExceptionDao;
import server.essp.timesheet.synchronization.syncexception.viewbean.VbException;
import db.essp.timesheet.TsAccount;
import db.essp.timesheet.TsException;

public class SyncExceptionServiceImp implements ISyncExceptionService {
	private ISyncExceptionDao syncExceptionDao;

	private IAccountDao accountDao;

	private IRmMaintService rmMaintService;

	public void setAccountDao(IAccountDao accountDao) {
		this.accountDao = accountDao;
	}

	public void insert(TsException te) {
		syncExceptionDao.insert(te);
	}

	public List listException() {
		return syncExceptionDao.listException();
	}

	public VbException loadExceptionByRid(Long rid) {
		TsException te = new TsException();
		te = syncExceptionDao.loadExceptionByRid(rid);
		VbException ve = new VbException();
		ve.setAccountId(te.getProjectId());
		TsAccount account = accountDao.loadByAccountId(te.getProjectId());
		String accountName = "";
		if (account != null) {
			accountName = account.getAccountName();
		}
		ve.setAccountName(accountName);
		ve.setEmployeeId(te.getEmployeeId());
		ve.setEmployeeName(rmMaintService.getUserName(te.getEmployeeId()));
		ve.setErrorMessage(te.getErrorMessage());
		ve.setModel(te.getModel());
		ve.setOccurTime(te.getRct());
		ve.setOperation(te.getOperation());
		ve.setStatus(te.getStatus());
		return ve;
	}

	public void update(Long rid, String status) {
		TsException te = new TsException();
		te = syncExceptionDao.loadExceptionByRid(rid);
		te.setStatus(status);
		syncExceptionDao.update(te);

	}

	public void setSyncExceptionDao(ISyncExceptionDao syncExceptionDao) {
		this.syncExceptionDao = syncExceptionDao;
	}

	public void setRmMaintService(IRmMaintService rmMaintService) {
		this.rmMaintService = rmMaintService;
	}

	public TsException getExceptionByRid(Long rid) {
		TsException te = new TsException();
		te = syncExceptionDao.loadExceptionByRid(rid);
		return te;
	}

}
