package server.essp.hrbase.humanbase.service;

import java.util.ArrayList;
import java.util.List;

import c2s.dto.DtoUtil;

import db.essp.hrbase.HrbHumanBase;
import db.essp.hrbase.HrbHumanBaseLog;

import server.essp.hrbase.humanbase.dao.IHumanBaseDao;
import server.essp.hrbase.humanbase.form.AfHumanBase;
import server.essp.hrbase.humanbase.form.AfHumanBaseLog;
import server.essp.hrbase.humanbase.form.AfHumanBaseQuery;
import server.essp.hrbase.humanbase.viewbean.VbHumanBase;
import server.essp.hrbase.humanbase.viewbean.VbHumanBaseLog;
import server.framework.common.BusinessException;

public class HumanBaseSeviceImp implements IHumanBaseSevice {

	private IHumanBaseDao humanBaseDao;

	public List getHrBaseByCondition(AfHumanBaseQuery af) {
		List resultList = humanBaseDao.queryByCondition(af);
		return resultList;
	}
	
	public List listAddingLog(String sites) {
		List list = humanBaseDao.listAddLog(sites);
		return list;
	}

	public List listEditingLog(Long rid) {
		List list = humanBaseDao.listUpdateLog(rid);
		return list;
	}

	public VbHumanBase loadHumanBase(Long rid) {
		return bean2Vb(humanBaseDao.loadHumanBase(rid));
	}
	
	public VbHumanBaseLog loadHumanBaseLog(Long rid) {
		return logBean2LogVb(humanBaseDao.loadHumanBaseLog(rid));
	}

	public void addHumanBase(AfHumanBase af) {
		checkEmployeeIdExist(af.getEmployeeId());
		HrbHumanBaseLog logBean = af2Bean(af);
		logBean.setOperation(HrbHumanBaseLog.OPERATION_INSERT);
		logBean.setStatus(HrbHumanBaseLog.STATUS_PENDING);
		humanBaseDao.saveHumanBaseLog(logBean);
	}
	
	/**
	 * 判断工号是否已经存在，或正在新增
	 * @param employeeId
	 */
	private void checkEmployeeIdExist(String employeeId) {
		//已经存在
		HrbHumanBase bean = humanBaseDao.findHumanBase(employeeId);
		if(bean != null) {
			throw new BusinessException("error.hrbase.logic.HumanBaseSeviceImp.employeeIdExist",
					employeeId + " has exist, name:" + bean.getEnglishName() + "("
					+ bean.getChineseName() + ") rct:" + bean.getRct());
		}
		//正在新增
		HrbHumanBaseLog logBean = humanBaseDao.findInsertingHumanBaseLog(employeeId);
		if(logBean != null) {
			throw new BusinessException("error.hrbase.logic.HumanBaseSeviceImp.employeeIdExist",
					employeeId + " is inserting, name:" + logBean.getEnglishName() + "("
					+ logBean.getChineseName() + ") rct:" + logBean.getRct());
		}
	}

	public void updateHumanBase(AfHumanBase af) {
		HrbHumanBaseLog logBean = af2Bean(af);
		Long baseRid = Long.valueOf(af.getRid());
		logBean.setRid(null);
		logBean.setBaseRid(baseRid);
		logBean.setOperation(HrbHumanBaseLog.OPERATION_UPDATE);
		logBean.setStatus(HrbHumanBaseLog.STATUS_PENDING);
		humanBaseDao.saveHumanBaseLog(logBean);
		
		//设定主档Operation
		HrbHumanBase bean = humanBaseDao.loadHumanBase(baseRid);
		bean.setOperation(HrbHumanBaseLog.OPERATION_UPDATE);
		humanBaseDao.updateHumanBase(bean);
	}
	
	public void updateHumanBaseLog(AfHumanBaseLog af) {
		HrbHumanBaseLog logBean = af2LogBean(af);
		humanBaseDao.updateHumanBaseLog(logBean);
	}

	public void deleteHumanBase(Long rid) {
		HrbHumanBase bean = humanBaseDao.loadHumanBase(rid);
		HrbHumanBaseLog logBean = bean2LogBean(bean);
		logBean.setRid(null);
		logBean.setBaseRid(bean.getRid());
		logBean.setOperation(HrbHumanBaseLog.OPERATION_DELETE);
		logBean.setStatus(HrbHumanBaseLog.STATUS_PENDING);
		humanBaseDao.saveHumanBaseLog(logBean);
//		设定主档Operation
		bean.setOperation(HrbHumanBaseLog.OPERATION_DELETE);
		humanBaseDao.updateHumanBase(bean);
	}
	
	public void cancelHumanBaseLog(Long rid) {
		HrbHumanBaseLog log = humanBaseDao.loadHumanBaseLog(rid);
		log.setStatus(HrbHumanBaseLog.STATUS_CANCELED);
		humanBaseDao.updateHumanBaseLog(log);
		if(log.getBaseRid() == null) {
			return;
		}
		//取消主档Operation
		HrbHumanBase bean = humanBaseDao.loadHumanBase(log.getBaseRid());
		if(bean == null) {
			return;
		}
		bean.setOperation(HrbHumanBaseLog.OPERATION_NONE);
		humanBaseDao.updateHumanBase(bean);
	}

	public void setHumanBaseDao(IHumanBaseDao humanBaseDao) {
		this.humanBaseDao = humanBaseDao;
	}
	
	private static HrbHumanBaseLog af2Bean(AfHumanBase af) {
		HrbHumanBaseLog bean  = new HrbHumanBaseLog();
		DtoUtil.copyBeanToBean(bean, af);
		return bean;
	}
	
	private static HrbHumanBaseLog af2LogBean(AfHumanBaseLog af) {
		HrbHumanBaseLog logBean  = new HrbHumanBaseLog();
		DtoUtil.copyBeanToBean(logBean, af);
		return logBean;
	}
	
	private static HrbHumanBaseLog bean2LogBean(HrbHumanBase bean) {
		HrbHumanBaseLog logBean  = new HrbHumanBaseLog();
		DtoUtil.copyBeanToBean(logBean, bean);
		return logBean;
	}
	public List beanList2VbList(List<HrbHumanBase> beanList) {
		if(beanList == null || beanList.size() == 0){
			return new ArrayList();
		} 
		List resultList = new ArrayList();
		for(HrbHumanBase bean : beanList) {
			resultList.add(bean2Vb(bean));
		}
		return resultList;
		
	}
	private VbHumanBase bean2Vb(HrbHumanBase bean) {
		VbHumanBase vb = new VbHumanBase();
		DtoUtil.copyBeanToBean(vb, bean);
		Object[] atts = humanBaseDao.getAttributeInfoByGroupRid(bean.getAttributeGroupRid());
		if(atts != null && atts.length >= 3) {
			vb.setAttributeGroupRid((Long)atts[0]);
			vb.setHrAttribute((String)atts[1]);
			vb.setAttribute((String)atts[2]);
			vb.setIsFormal((String)atts[3]);
		}
		return vb;
	}
	
	private VbHumanBaseLog logBean2LogVb(HrbHumanBaseLog logBean) {
		VbHumanBaseLog vb = new VbHumanBaseLog();
		DtoUtil.copyBeanToBean(vb, logBean);
		Object[] atts = humanBaseDao.getAttributeInfoByGroupRid(logBean.getAttributeGroupRid());
		if(atts != null && atts.length >= 3) {
			vb.setAttributeGroupRid((Long)atts[0]);
			vb.setHrAttribute((String)atts[1]);
			vb.setAttribute((String)atts[2]);
			vb.setIsFormal((String)atts[3]);
		}
		return vb;
	}
}
