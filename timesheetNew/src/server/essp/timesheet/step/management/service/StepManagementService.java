package server.essp.timesheet.step.management.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;

import server.essp.timesheet.account.dao.IAccountDao;
import server.essp.timesheet.step.management.dao.IStepManagementDao;
import server.essp.timesheet.step.management.dao.IStepManagementP3ApiDao;
import server.framework.common.BusinessException;
import c2s.dto.DtoBase;
import c2s.dto.DtoTreeNode;
import c2s.dto.DtoUtil;
import c2s.dto.ITreeNode;
import c2s.essp.timesheet.step.management.DtoActivityAndStep;
import c2s.essp.timesheet.step.management.DtoActivityForStep;
import c2s.essp.timesheet.step.management.DtoStep;
import c2s.essp.timesheet.step.management.DtoStepBase;

import com.wits.excel.ICellStyleSwitch;

import db.essp.timesheet.TsStep;

public class StepManagementService implements IStepManagementService {
	private IStepManagementP3ApiDao stepManagementApiDao;
	private IStepManagementDao stepDao;
	private IAccountDao accountDao;
	private boolean excelDto = false;

	public IStepManagementP3ApiDao getStepManagementApiDao() {
		return stepManagementApiDao;
	}

	public void setStepManagementApiDao(
			IStepManagementP3ApiDao stepManagementApiDao) {
		this.stepManagementApiDao = stepManagementApiDao;
	}

	public Vector listAcnt(String loginId) {
		try {
			return stepManagementApiDao.getAcntList(loginId, accountDao.getSelectAccount(loginId));
		} catch (Exception e) {
			throw new BusinessException("error.StepManagementService.listProject",
					"List project error", e);
		}
	}

	public List listActivity(String loginId, String projectObjectId) {
		try {
			List actList = stepManagementApiDao.getActivityList(projectObjectId);
			setSelectAccount(loginId, actList);
			return actList;
		} catch (Exception e) {
			throw new BusinessException("error.StepManagementService.listActivity",
					"List activity error", e);
		}
	}
	
	private void setSelectAccount(String loginId, List<DtoActivityForStep> list) {
		if(loginId != null && list != null && list.size() > 0) {
		this.accountDao.setSelectAccount(loginId, list.get(0).getProjectCode());	
		}
	}

	public List listStepByActivityId(Long activityId) {
		List result = new ArrayList();
		List stepList = stepDao.listStepByActivityId(activityId);
		for (int i = 0; i < stepList.size(); i++) {
			DtoStep step = new DtoStep();
			DtoUtil.copyBeanToBean(step, stepList.get(i));
			step.setActualCostTime(stepDao.getStepCumulativeHours(step.getRid()));
			result.add(step);
		}
		return result;
	}

	public void saveStepList(List<DtoStep> stepList) {
		for (DtoStep orig : stepList) {
			if (orig.getOp().equals(DtoBase.OP_INSERT)) {
				TsStep dest = new TsStep();
				DtoUtil.copyBeanToBean(dest, orig);
				if (orig.getCompleteFlag() == null) {
					dest.setCompleteFlag(false);
				}
				dest.setIsSync("Y");
				stepDao.addStep(dest);
			} else if (orig.getOp().equals(DtoBase.OP_MODIFY)) {
				TsStep dest = new TsStep();
				DtoUtil.copyBeanToBean(dest, orig);
				if (orig.getCompleteFlag() == null) {
					dest.setCompleteFlag(false);
				}
				dest.setIsSync("Y");
				stepDao.updateStep(dest);
			} else if (orig.getOp().equals(DtoBase.OP_DELETE)) {
				TsStep dest = new TsStep();
				DtoUtil.copyBeanToBean(dest, orig);
				if (orig.getCompleteFlag() == null) {
					dest.setCompleteFlag(false);
				}
				dest.setIsSync("Y");
				dest.setRst("X");
				stepDao.updateStep(dest);
			}
		}
	}

	public void deleteStepList(List<DtoStep> stepList) {
		for (DtoStep orig : stepList) {
			TsStep dest = new TsStep();
			DtoUtil.copyBeanToBean(dest, orig);
			dest.setIsSync("Y");
			dest.setRst("X");
			stepDao.updateStep(dest);
		}

	}
	
	public DtoActivityForStep getActivity(Long activityId) {
		return stepManagementApiDao.getActivity(activityId);
	}

//	public void updateStepList(List<DtoStep> stepList) {
//		for (DtoStep orig : stepList) {
//			TsStep dest = new TsStep();
//			DtoUtil.copyBeanToBean(dest, orig);
//			dest.setIsSync("Y");
//			stepDao.updateStep(dest);
//		}
//	}
	public ITreeNode getDateByActObjId(String actObjIds) {
		String actObjs []=actObjIds.split(",");
		ITreeNode root=new DtoTreeNode(null);
		for(int i=0;i<actObjs.length;i++){
			String actObjId=actObjs[i];
			DtoActivityForStep orig=stepManagementApiDao.getActivityByObjectId(actObjId);
			DtoActivityAndStep dest = getDtoActivityInstance();
			DtoUtil.copyBeanToBean(dest, orig);
			//¼ì²é×´Ì¬Ö¸Ê¾Æ÷
			dest.setColorIndex(HSSFColor.LIGHT_GREEN.index);
			dest.setCategory(orig.getCode());			
			dest.setResourceIds(orig.getResourceIds());			
			List stepList=listStepByActivityId(Long.valueOf(actObjId));
			ITreeNode actNode = new DtoTreeNode(dest);
			getChildren(actNode,stepList);
			root.addChild(actNode);
		}	
		return root;
	}
	
	 private void getChildren(ITreeNode node,List<DtoStep> stepList) {
         for ( DtoStep orig: stepList ) {              	 
        	 DtoActivityAndStep dest = getDtoActivityInstance();
        	 DtoUtil.copyBeanToBean(dest, orig);        
        	 dest.setManager(orig.getResourceName());
        	 dest.setManagerId(orig.getResourceId());
        	 dest.setStatus(orig.getCompleteFlag()?"Complete":"Not Complete");
        	 dest.setName(orig.getProcName());
 			 dest.setColorIndex(HSSFColor.WHITE.index);
        	 ITreeNode child = new DtoTreeNode(dest);
        	 node.addChild(child);
         }
	 }
	 
	 private static short checkStatusIndicatorColor(DtoActivityAndStep dto) {
		 if(DtoStepBase.NORMAL.equals(dto.getStatusIndicator())) {
			 return HSSFColor.BRIGHT_GREEN.index;
		 } else if(DtoStepBase.DELAY.equals(dto.getStatusIndicator())) {
			 return HSSFColor.RED.index;
		 } else if(DtoStepBase.AHEAD.equals(dto.getStatusIndicator())) {
			 return HSSFColor.BLUE.index;
		 } else {
			 return dto.getColorIndex();
		 }
	 }
	 
	 private DtoActivityAndStep getDtoActivityInstance() {
		 if(excelDto) {
			 return new StyledDtoActivity();
		 } else {
			 return new DtoActivityAndStep();
		 }
    }
	 
     public class StyledDtoActivity extends DtoActivityAndStep
	     implements ICellStyleSwitch {
	  public HSSFCellStyle getStyle(String styleName, HSSFCellStyle cellStyle) {
	     if (styleName != null && !"".equals(styleName)) {
	         cellStyle.setFillPattern((short) HSSFCellStyle.SOLID_FOREGROUND);
	         cellStyle.setFillForegroundColor(Short.valueOf(styleName));
	     }
	      return cellStyle;
	  }
	
	  public String getStyleName(String propertyName) {
		  if("statusIndicator".equals(propertyName)) {
			  return checkStatusIndicatorColor(this) + "";
		  }
		  if(this.getColorIndex() > -1) {
			  return this.getColorIndex() + "";
		  } else {
			  return null;
		  }
	 }
	}
	public List<Date> getAllWorkDayByProjectCalendar(String activityObjId,
			Date start, Date finish) {
		return stepManagementApiDao.getAllWorkDayByProjectCalendar(
				activityObjId, start, finish);
	}

	public IStepManagementDao getStepDao() {
		return stepDao;
	}

	public void setStepDao(IStepManagementDao stepDao) {
		this.stepDao = stepDao;
	}

	public void setExcelDto(boolean excelDto) {
		this.excelDto = excelDto;		
	}

	/**
	 * @param accountDao The accountDao to set.
	 */
	public void setAccountDao(IAccountDao accountDao) {
		this.accountDao = accountDao;
	}
}
