package server.essp.timesheet.template.step.service;

import java.util.ArrayList;
import java.util.List;

import server.essp.timesheet.template.step.dao.IDetailStepDao;
import server.essp.timesheet.template.step.form.Afstep;
import server.essp.timesheet.template.step.form.VbStep;
import c2s.dto.DtoUtil;
import db.essp.timesheet.TsStepTDetail;

public class DetailStepServiceImp implements IDetailStepService{
	IDetailStepDao stepDao;

	public void setStepDao(IDetailStepDao stepDao) {
		this.stepDao = stepDao;
	}

	public List listStep(Long templateRid) {
		List list =stepDao.getSteptByTid(templateRid);
		List stepList = new ArrayList();
		for(int i=0;i<list.size();i++)
		{
			TsStepTDetail tsDetail =(TsStepTDetail)list.get(i);
			VbStep viewBean = new VbStep();
			DtoUtil. copyBeanToBean(viewBean,tsDetail);	
			if("N".equals(tsDetail.getIsCorp()))
				viewBean.setIsCorp("No");
			else
				viewBean.setIsCorp("Yes");					
			stepList.add(viewBean);
		}
		return stepList;
	}

	public Long saveStep(Afstep form) {
		TsStepTDetail tsDetail=new TsStepTDetail();		
		DtoUtil. copyBeanToBean(tsDetail,form);	
		if(null==form.getIsCorp()){
			tsDetail.setIsCorp("N");
		}
		if("".equals(form.getRid()))
			stepDao.save(tsDetail);				
		else
			stepDao.update(tsDetail);
		return tsDetail.getRid();
	}

	public Long deleteStep(Long rid) {
		TsStepTDetail tsDetail = stepDao.load(rid);
		stepDao.delete(tsDetail);		
		return tsDetail.getTempRid();
	}

	public Afstep getStep(Long rid) {
		Afstep step =new Afstep();	
		if(rid!=0L){
		TsStepTDetail tsDetail = stepDao.load(rid);
		DtoUtil. copyBeanToBean(step,tsDetail);	
		}			
		return step;
	}

}
