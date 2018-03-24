package server.essp.timesheet.synchronization.service;

import java.util.HashMap;
import java.util.Map;

import server.essp.timesheet.synchronization.dao.ISyncHrmsDao;
import server.essp.timesheet.synchronization.syncexception.viewbean.VbException;

import c2s.essp.timesheet.synchronization.DtoSync;
import db.essp.timesheet.TsException;

public class SyncToHrmsImp implements ISyncData {
	
	private static Map siteMap;
	private ISyncHrmsDao syncHrmsDao;

	public void setSyncHrmsDao(ISyncHrmsDao syncHrmsDao) {
		this.syncHrmsDao = syncHrmsDao;
	}

	public void add(DtoSync dto) {
		String projectId = dto.getProjectId();
		int length = projectId.length();
		String code = projectId.substring(length-1);
		if(siteMap.containsKey(code) || dto.isDept()) {
			syncHrmsDao.add("TP", projectId, dto.getEmployeeId(), "I");
		}
		
	}

	public void delete(DtoSync dto) {
		String projectId = dto.getProjectId();
		int length = projectId.length();
		String code = projectId.substring(length-1);
		if(siteMap.containsKey(code) || dto.isDept()) {
			syncHrmsDao.add("TP", projectId, dto.getEmployeeId(), "D");
		}

	}

	public void update(DtoSync dto) {
		// TODO Auto-generated method stub

	}
	static{
		siteMap = new HashMap();
		siteMap.put("T", "TP");
	}
	public String getFunctionName() {
		return this.getClass().getName();
	}

	public void carryForward(TsException te) {
		DtoSync dto = new DtoSync();
		dto.setEmployeeId(te.getEmployeeId());
		dto.setProjectId(te.getProjectId());
		if(VbException.OPERATION_INSERT.equals(te.getOperation())) {
			add(dto);
		} else if(VbException.OPERATION_DELETE.equals(te.getOperation())){
			delete(dto);
		}
		
	}
	
}
