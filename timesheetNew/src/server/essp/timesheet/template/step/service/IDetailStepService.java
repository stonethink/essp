package server.essp.timesheet.template.step.service;

import java.util.List;

import server.essp.timesheet.template.step.form.Afstep;
import server.essp.timesheet.template.step.form.VbStep;

public interface IDetailStepService {
	
	public List listStep(Long templateRid);
	
	public Long saveStep(Afstep form);
	
	public Long deleteStep(Long rid);
	
	public Afstep getStep(Long rid);

}
