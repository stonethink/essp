package server.essp.timesheet.template.step.dao;

import java.util.List;

import db.essp.timesheet.TsStepTDetail;

public interface IDetailStepDao {

	public void delete(TsStepTDetail stepDetail);
	
	public void save(TsStepTDetail stepDetail);
	
	public void update(TsStepTDetail stepDetail);
	
	public TsStepTDetail load(Long rid);
	
	public List getSteptByTid(Long rid);
}
