package server.essp.timesheet.tsmodify.service;

import java.util.List;

import c2s.essp.timesheet.tsmodify.DtoTsModify;

public interface ITsModifyService {
	
	public List queryByCondition(DtoTsModify dto);

}
