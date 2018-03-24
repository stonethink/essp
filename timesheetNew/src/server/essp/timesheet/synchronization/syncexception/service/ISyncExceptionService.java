package server.essp.timesheet.synchronization.syncexception.service;

import java.util.List;

import server.essp.timesheet.synchronization.syncexception.viewbean.VbException;
import db.essp.timesheet.TsException;

public interface ISyncExceptionService {
	public void insert(TsException te);

	public void update(Long rid, String status);

	public List listException();

	public VbException loadExceptionByRid(Long rid);
	
	public TsException getExceptionByRid(Long rid);

}
