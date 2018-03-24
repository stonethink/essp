package server.essp.timesheet.template.service;

import java.util.ArrayList;
import java.util.List;

import server.essp.timesheet.methodology.dao.IMethodDao;
import server.essp.timesheet.template.dao.ITemplateDao;
import server.essp.timesheet.template.form.AfTemplate;
import server.essp.timesheet.template.form.VbMethodMap;
import server.essp.timesheet.template.form.Vbtemplate;
import server.essp.timesheet.template.step.dao.IDetailStepDao;
import server.framework.common.BusinessException;
import c2s.dto.DtoUtil;
import db.essp.timesheet.TsMethod;
import db.essp.timesheet.TsMethodTMap;
import db.essp.timesheet.TsMethodTMapId;
import db.essp.timesheet.TsStepT;

public class TemplateServiceImp implements ITemplateService {
	
	private ITemplateDao templateDao;
	private IMethodDao methodDao;
	private IDetailStepDao stepDao;

	public Long saveTemplate(AfTemplate form,String [] methods) {		
		TsStepT template=new TsStepT ();
		template.setTemplateName(form.getTemplateName());
		template.setTemplateCode(form.getTemplateCode());	
		template.setDescription(form.getDescription());
		Long templateId = null;
		if(null==form.getRst()){
			template.setRst("D");
		}
		if("0".equals(form.getRid())){
			templateDao.addTemplate(template);		
			templateId = template.getRid();
		}else{
			templateId=Long.valueOf(form.getRid());	
			template.setRid(templateId);
			templateDao.updateTemplate(template);
			templateDao.deleteAllMapByTid(templateId);
		}
		if(null!=methods){
			for(int i=0;i<methods.length; i++){			
				Long methodId = Long.valueOf(methods[i]);
				TsMethodTMap  tsMap = new TsMethodTMap();
				TsMethodTMapId id = new TsMethodTMapId();
				id.setMethodRid(methodId);
				id.setTemplateRid(templateId);			
				tsMap.setId(id);
				templateDao.saveMapId(tsMap);
			}
		}	
		return templateId;
	}	

	/**
	 * 当rid为零的时候初始化map的数据
	 */
	public AfTemplate getTemplate(Long rid) {	
		AfTemplate vb=new AfTemplate();
		TsStepT template =new TsStepT();
		List allMethods = methodDao.listAllMethodType();
		List methodMap = new ArrayList();
		if(rid==0L){			
			methodMap=getMethodMap(allMethods,null);
		}else{
			template = templateDao.getTemplate(rid);		
			List ableMethod = templateDao.getAllMethodByTid(rid);	
			methodMap=getMethodMap(allMethods,ableMethod);
			vb.setTemplateCode(template.getTemplateCode());
			vb.setTemplateName(template.getTemplateName());
			vb.setDescription(template.getDescription());
			vb.setRst(template.getRst());
		}		
		vb.setRid(rid+"");		
		vb.setMethodMap(methodMap);		
		return vb;
	}
	
	/**
	 * 当methodology 可以使用当前的模版的时候，设置check status为checked状态
	 * @param allMethod List
	 * @param ableMethod List
	 * @return
	 */
	private List getMethodMap(List allMethod,List ableMethod){
		List<VbMethodMap> methodMap = new ArrayList<VbMethodMap>();			
		for (int i=0;i<allMethod.size();i++) {
			TsMethod method = (TsMethod) allMethod.get(i);
			Long rid = method.getRid();
			VbMethodMap vbMap = new VbMethodMap();
			if(ableMethod!=null){
				for(int j=0; j< ableMethod.size();j++){
					TsMethodTMap map = (TsMethodTMap)ableMethod.get(j);
					if(map.getId().getMethodRid().equals(rid)){
						vbMap.setStatus("checked");
					}
				}
			}
			vbMap.setMethodName(method.getName());
			vbMap.setMethodId(method.getRid());
			methodMap.add(vbMap);
		}		
		return methodMap;		
	}
	
	public void deleteTemplate(Long rid) {			
		TsStepT tempalte  = templateDao.getTemplate(rid);
		List stepList=stepDao.getSteptByTid(rid);
		if(null!=stepList&&stepList.size()!=0){
			throw new BusinessException("BZ302","Can't delete the template before you delete all steps!");
		}else{
		templateDao.deleteTemplate(tempalte); 		
		templateDao.deleteAllMapByTid(rid);
		}
	}

	public List getTemplateList() {		
		List list=templateDao.listAllTemplate();		
		List templateList=new ArrayList();
		for(int i=0;i<list.size();i++){
			TsStepT template =(TsStepT)list.get(i);
			Long rid=template.getRid();		
			Vbtemplate vb=new Vbtemplate();
			DtoUtil. copyBeanToBean(vb,template);
			vb.setMethodType(getMethodType(rid));
			if("N".equals(template.getRst()))
				vb.setRst("Yes");
			else
				vb.setRst("No");
					
			templateList.add(vb);		
		}
		return templateList;
	}
	
	private String getMethodType(Long rid){
		List list = templateDao.getAllMethodByTid(rid);
		String methodType = "";
		for(int i=0; i<list.size();i++){
			TsMethodTMap map = (TsMethodTMap)list.get(i);			
			Long methodRid=map.getId().getMethodRid();
			String name=methodDao.getMethodType(methodRid).getName();
			if("".equals(methodType))
				methodType += name;
			else
				methodType += ","+name;
		}
		return methodType;
	}	

	public void setTemplateDao(ITemplateDao templateDao) {
		this.templateDao = templateDao;
	}
	
	public void setMethodDao(IMethodDao methodDao) {
		this.methodDao = methodDao;
	}

	public void setStepDao(IDetailStepDao stepDao) {
		this.stepDao = stepDao;
	}
	


}
