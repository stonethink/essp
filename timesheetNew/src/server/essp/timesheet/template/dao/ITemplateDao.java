package server.essp.timesheet.template.dao;

import java.util.List;

import db.essp.timesheet.TsMethodTMap;
import db.essp.timesheet.TsStepT;

public interface ITemplateDao {
	
	 public void deleteTemplate(TsStepT template);
	 
	 public TsStepT getTemplate(Long rid);
	 
	 public void updateTemplate(TsStepT template);
	 
	 public void addTemplate(TsStepT template);
	 
	 public List listAllTemplate();
	 
	 public List getAllMethodByTid(Long rid);
	 
	 public void deleteAllMapByTid(Long rid);
	 
	 public TsStepT getTemplateByName(String name);
	 
	 public void saveMapId(TsMethodTMap map);
}
