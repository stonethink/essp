package server.essp.timesheet.synchronization.syncexception.dao;

import java.util.List;

import db.essp.timesheet.TsException;

public interface ISyncExceptionDao {
	public void insert(TsException te);

	public void update(TsException te);

	public List listException();

	public TsException loadExceptionByRid(Long rid);
}
