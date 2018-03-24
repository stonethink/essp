package server.essp.timesheet.methodology.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import server.essp.timesheet.account.dao.IAccountDao;
import server.essp.timesheet.methodology.dao.IMethodDao;
import server.essp.timesheet.methodology.form.AfMethod;
import server.essp.timesheet.methodology.form.VbMethod;
import server.framework.common.BusinessException;
import c2s.dto.DtoComboItem;
import c2s.dto.DtoUtil;
import db.essp.timesheet.TsAccount;
import db.essp.timesheet.TsMethod;

public class MethodServiceImp implements IMethodService {

	private IMethodDao methodDao;
	private IAccountDao accountDao;

	public Long saveMethod(AfMethod form) {
		TsMethod method = new TsMethod();
		Long rid=null;
		if(null!=form.getRid()&&!"".endsWith(form.getRid()))	
			rid=Long.valueOf(form.getRid());		
		DtoUtil.copyBeanToBean(method, form);
		if(null == form.getRst()){
			method.setRst("D");
		}
		if (null == form.getRid()||"".equals(form.getRid()) )
			rid=methodDao.addMethodType(method);
		else
			methodDao.updateMethodType(method);
		return rid;

	}

	public VbMethod getMethod(Long rid) {
		TsMethod method = methodDao.getMethodType(rid);
		VbMethod vb = new VbMethod();
		DtoUtil.copyBeanToBean(vb, method);
		return vb;
	}

	public void deleteMethod(Long rid) {
		List list= methodDao.getTemplateByMethod(rid);
		TsMethod method = methodDao.getMethodType(rid);
		if(null!=list&&list.size()!=0){
			throw new BusinessException("BZ301","Can't delete methodology before delete all template children!");
		}else{
		methodDao.deleteMethodType(method);
		}
	}

	public List getMethodList() {
		List list = methodDao.listAllMethodType();
		List methodList = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			TsMethod method = (TsMethod) list.get(i);
			VbMethod vb = new VbMethod();
			DtoUtil.copyBeanToBean(vb, method);
			if("N".equals(method.getRst()))
				vb.setRst("Yes");
			else
				vb.setRst("No");			
			methodList.add(vb);
		}
		return methodList;
	}

	public void setMethodDao(IMethodDao methodDao) {
		this.methodDao = methodDao;
	}

	public List getTemplateListByMethod(String methodRid) {
		List tempList=methodDao.getTemplateByMethod(Long.valueOf(methodRid));
		return tempList;
	}
	
	public List getTemplateListByAcntId(String acntId) {
		TsAccount account = accountDao.loadByAccountId(acntId);
		if(account != null && account.getMethodRid() != null) {
			return getTemplateListByMethod(account.getMethodRid().toString());
		}
		return new ArrayList();
	}

	/**
	 * @param accountDao The accountDao to set.
	 */
	public void setAccountDao(IAccountDao accountDao) {
		this.accountDao = accountDao;
	}

	public Vector getMethodCmb() {
		List<TsMethod> list = methodDao.listNormalMethod();
		if(list == null || list.size() == 0) {
			return new Vector();
		}
		Vector dtoList = new Vector(list.size());
        for(TsMethod bean : list) {
                DtoComboItem dto = new DtoComboItem(bean.getName()+" -- "+bean.getDescription(), 
                		                            bean.getRid());
                dtoList.add(dto);
            
        }
        return dtoList;
	}
}
