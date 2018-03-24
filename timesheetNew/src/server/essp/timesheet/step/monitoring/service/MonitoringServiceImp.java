/*
 * Created on 2008-5-26
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.timesheet.step.monitoring.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import server.essp.timesheet.account.dao.IAccountDao;
import server.essp.timesheet.step.management.dao.IStepManagementDao;
import server.essp.timesheet.step.management.dao.IStepManagementP3ApiDao;
import server.essp.timesheet.step.monitoring.dao.IMonitoringP3ApiDao;
import server.framework.common.BusinessException;
import c2s.dto.DtoTreeNode;
import c2s.dto.ITreeNode;
import c2s.essp.timesheet.step.management.DtoStepBase;
import c2s.essp.timesheet.step.monitoring.DtoMonitoring;
import c2s.essp.timesheet.step.monitoring.DtoMonitoringQuery;
import com.primavera.common.value.ObjectId;
import com.primavera.common.value.PrmNumber;
import com.primavera.integration.client.bo.BusinessObjectException;
import com.primavera.integration.client.bo.enm.ActivityStatus;
import com.primavera.integration.client.bo.object.Activity;
import com.primavera.integration.client.bo.object.Project;
import com.primavera.integration.client.bo.object.Resource;
import com.primavera.integration.client.bo.object.ResourceAssignment;
import com.primavera.integration.client.bo.object.WBS;
import com.wits.excel.ICellStyleSwitch;

import db.essp.timesheet.TsStep;
import db.essp.timesheet.TsStepTDetail;

/**
 * MonitoringServiceImp
 * @author TBH
 */
public class MonitoringServiceImp implements IMonitoringService{
    
        private IMonitoringP3ApiDao monitoringP3ApiDao;
        private IStepManagementP3ApiDao stepManagementApiDao;
        private IStepManagementDao stepDao;
        private IAccountDao accountDao;
        private boolean excelDto = false;
        private Map wbsMap = new HashMap();
        private Map actMap = new HashMap();
        private Map stepMap = new HashMap();
        private Map resAssginMap = new HashMap();
        private Map resMap = new HashMap();
        private Map stepDetailMap = new HashMap();
        
        public Vector getProjectList(String loginId) {
               Vector projList = null;
               try{
               projList = stepManagementApiDao.getAcntList(loginId, accountDao.getSelectAccount(loginId));
               }catch(Exception e){
            	   throw new BusinessException("error.MonitoringServiceImp.listProject",
       					"List project error", e);
               }
               return projList;
        }
        
        /**
         * 根據查詢條件的得到WBS,Activity,step对应的子孙结点
         * @param dtoQry
         * @param dtoFilter
         * @return Map
         */
        public Map loadMonitoringInfo(String loginId, String projectId) {
                wbsMap.clear();
                actMap.clear();
                stepMap.clear();
                resAssginMap.clear();
                resMap.clear();
                stepDetailMap.clear();
                Map map = new HashMap();
                ITreeNode treeNode = null;
                String projName = "";
                try{
                    Project project = monitoringP3ApiDao.loadProject(projectId);
                    if(project != null){
                        List wbsList = monitoringP3ApiDao.getAllWbs(project);
                        setWBSMap(wbsList);
                        List actList = monitoringP3ApiDao.loadAllActivity(project);
                        setActivityMap(actList);
                        List stepList = stepDao.getAllStepByPrjId(new Long(project.getObjectId().toInteger()));
                        setStepMap(stepList);
                        List stepDetailList = stepDao.getStepDetailInfo();
                        setStepDetailInfoMap(stepDetailList);
                        List resAssginList = monitoringP3ApiDao.loadResourceAssignment(project);
                        setResourceAssignmentMap(resAssginList);
                        List resList = monitoringP3ApiDao.loadResources(project);
                        setResourceMap(resList);
                        DtoMonitoring dto = getDtoMonitoringInstance();
                        dto.setBeanType(DtoMonitoring.BEAN_TYPE_WBS);
                        dto.setName(project.getName());
                        if(project.getAnticipatedStartDate() != null){
                         dto.setPlanStart(new Date(project.getAnticipatedStartDate().getTime()));
                        }
                        if(project.getAnticipatedFinishDate() != null){
                         dto.setPlanFinish(new Date(project.getAnticipatedFinishDate().getTime()));
                        }
                        dto.setWbsId(Long.valueOf(project.getWBSObjectId().toString()));
                        treeNode = new DtoTreeNode(dto);
                        dto.setColorIndex(HSSFColor.LIGHT_CORNFLOWER_BLUE.index);
                        getChildren(treeNode,project,0);
                        String projId = project.getId();
                        projName = projId + " － " + project.getName();
                        int childrenSize = treeNode.getChildCount();
                        if(childrenSize == 0){
                           getActivity(treeNode,project.getWBSObjectId());
                        }
                        this.accountDao.setSelectAccount(loginId, projId);
                    }
                    map.put(DtoMonitoringQuery.DTO_TREE_NODE,treeNode);
                    map.put(DtoMonitoringQuery.DTO_PROJECT_NAME,projName);
                }catch(Exception e){
                    e.printStackTrace();
                  throw new BusinessException(e.getLocalizedMessage(),e.getLocalizedMessage());
                }
                return map;
        }
        
        /**
         * 依次得到WBS,Activity,step对应的子孙结点
         * @param treeNode
         * @param project
         * @param dtoQry
         * @throws Exception
         */
        private void getChildren(ITreeNode treeNode,Project project,int k) throws Exception{
                DtoMonitoring dto = (DtoMonitoring)treeNode.getDataBean();
                ObjectId wbsObjId = ObjectId.fromString(dto.getWbsId().toString());
                List children = (List)wbsMap.get(wbsObjId);
                if(k == 0){
                  getActivity(treeNode,wbsObjId);
                }
                if(children != null){
                 for(int i = 0;i<children.size();i++){
                    WBS wbsC = (WBS)children.get(i);
                    DtoMonitoring dtoChildren = copyProperties(wbsC);
                    DtoTreeNode cNode = new DtoTreeNode(dtoChildren);
                    treeNode.addChild(cNode);
                    k++;
                    getChildren(cNode,project,k);
                    getActivity(cNode,wbsC.getObjectId());
                 }
                }
        }
        
        /**
         * 根據WbsObjectId得到WBS对应的Activity集合
         * @param treeNode
         * @param wbsObjId
         */
        private void getActivity(ITreeNode treeNode,ObjectId wbsObjId){
                 List actList = (List)actMap.get(wbsObjId);
                 try{
                     Date pStart = null;
                     Date pFinish = null;
                     Date aStart = null;
                     Date aFinish = null;
                    if(actList != null){
                     for(int i = 0;i<actList.size();i++){
                      Activity act = (Activity)actList.get(i);
                      pStart = null;
                      pFinish = null;
                      aStart = null;
                      aFinish = null;
                      DtoMonitoring dtoM = getDtoMonitoringInstance();
                      dtoM.setColorIndex(HSSFColor.LIGHT_GREEN.index);
                      if(act.getPlannedStartDate() != null){
                          pStart = new Date(act.getPlannedStartDate().getTime());
                          dtoM.setPlanStart(pStart);
                      }
                      if(act.getPlannedFinishDate() != null){
                         pFinish = new Date(act.getPlannedFinishDate().getTime());
                         dtoM.setPlanFinish(pFinish);
                      }
                      if(act.getActualStartDate() != null){
                          aStart = new Date(act.getActualStartDate().getTime());
                          dtoM.setActualStart(aStart);
                      }
                      if(act.getActualFinishDate() != null){
                          aFinish = new Date(act.getActualFinishDate().getTime());
                          dtoM.setActualFinish(aFinish);
                      }
                      dtoM.setBeanType(DtoMonitoring.BEAN_TYPE_ACTIVITY);
                      dtoM.setName(act.getName());
                      dtoM.setPlanHours(toDouble(act.getPlannedLaborUnits()));
                      dtoM.setActualHours(toDouble(act.getActualLaborUnits()));
                      dtoM.setManagerName(act.getPrimaryResourceName());
                      
                      BigDecimal percent =  setScale(act.getPercentComplete().doubleValue()*100);
                      dtoM.setComplete(percent+"%");
                      dtoM.setStatus(act.getStatus().getDescription());
                      Map resourceMap = getResources(act.getObjectId());
                      String resouceId = (String)resourceMap.get("resourceId");
                      String resouceName = (String)resourceMap.get("resourceName");
                      dtoM.setManagerId(act.getPrimaryResourceId());
                      dtoM.setResourceNames(resouceName);
                      dtoM.setResourceIds(resouceId);
                     
                      DtoTreeNode cNode = new DtoTreeNode(dtoM);
                      treeNode.addChild(cNode);
                      //根据Activity得到对应的STEP
                      getStep(cNode,act.getObjectId());
                   }
                  }
                 } catch (Exception e) {
                     e.printStackTrace();
                     throw new BusinessException(e.getMessage(),e.getMessage());
                 }
          }
        
        /**
         * 得到RESOURCES的NAME和ID
         * @param actObjectId
         * @return Map
         */
        private Map getResources(ObjectId actObjectId){
               Map rMap = new HashMap();
               String id = "";
               String name = "";
               List resAssginList = (List)resAssginMap.get(actObjectId);
               List resIdList = new ArrayList();
               if(resAssginList != null){
                  try{
                   for(int i=0;i<resAssginList.size();i++){
                       ObjectId resObjectId = (ObjectId)resAssginList.get(i);
                       Resource res = (Resource)resMap.get(resObjectId);
                       if(res == null){
                           continue;
                       }
                       String resId = res.getId();
                       String resName = res.getName();
                       if(resIdList.contains(resId)){
                           continue;
                       }else{
                           resIdList.add(resId);
                       }
                       if(id.equals("") && resId != null && 
                               !"".equals(resId.trim())){
                           id = resId;
                       }else if(!id.equals("") && resId != null && 
                               !"".equals(resId.trim())){
                           id += "," + resId;
                       }
                       if(name.equals("") && resName != null && 
                               !"".equals(resName)){
                           name = res.getName();
                       }else if(!name.equals("") &&  resName != null && 
                               !"".equals(resName)){
                           name += "," + resName;
                       }
                   }
                   rMap.put("resourceId",id);
                   rMap.put("resourceName",name);
                  } catch (Exception e) {
                      e.printStackTrace();
                      throw new BusinessException(e.getMessage(),e.getMessage());
                  }
               }
               return rMap;
        }
        
        /**
         * 将STEP加入node中
         * @param node
         * @param actObjId
         */
        private void getStep(ITreeNode node,ObjectId actObjId){
                List sList = (List)stepMap.get(actObjId.toString());
                Map sMap = new HashMap();
                List catagoryList = new ArrayList();
                if(sList != null){
                for(int i=0;i<sList.size();i++){
                    TsStep step = (TsStep)sList.get(i);
                    String category = step.getCategory();
                    if(!catagoryList.contains(category)){
                        catagoryList.add(category);
                    }
                    List stepList = (List)sMap.get(category);
                    if(stepList != null){
                        stepList.add(step);
                    }else{
                        stepList = new ArrayList();
                        stepList.add(step);
                        sMap.put(category,stepList);
                    }
                 }
                for(int i=0;i<catagoryList.size();i++){
                    String key = (String)catagoryList.get(i);
                    List list = (List)sMap.get(key);
                    DtoMonitoring dtoM = getStepDto(list,key);
                    DtoTreeNode dtoC = new DtoTreeNode(dtoM);
                    node.addChild(dtoC);
                }
              }
        }
        
        /**
         * 根据CATAGORY对STEP进行分组
         * @param sList
         * @param name
         * @return DtoMonitoring
         */
        private DtoMonitoring getStepDto(List sList,String name){
                DtoMonitoring dto = new DtoMonitoring();
                Date pStart = null;
                Date pFinish = null;
                Date aStart = null;
                Date aFinish = null;
                int completedSize = 0;
                int size = sList.size();
                String emp = "";
                String empId = "";
                List empList = new ArrayList();
                List empIdList = new ArrayList();
                BigDecimal comPercent = BigDecimal.valueOf(0);
                Double actualTime = Double.valueOf(0);
                String procName = "";
                for(int i = 0;i<size;i++){
                    TsStep step = (TsStep)sList.get(i);
                    Long stepTRid =  step.getStepTRid();
                    if(procName == null || procName.trim().equals("")){
                       procName = (String)stepDetailMap.get(stepTRid);
                    }
                    String resName = step.getResourceName();
                    String resId = step.getResourceId();
                    if(pStart == null || (step.getPlanStart() != null &&
                            pStart.after(step.getPlanStart()))){
                        pStart = step.getPlanStart();
                    }
                    if(pFinish == null || (step.getPlanFinish() != null && 
                         pFinish.before(step.getPlanFinish()))){
                        pFinish = step.getPlanFinish();
                    }
                    if(aStart == null || (step.getActualStart() != null && 
                            aStart.after(step.getActualStart()))){
                        aStart = step.getActualStart();
                       }
                    if(aFinish == null || (step.getActualFinish() != null && 
                            aFinish.before(step.getActualFinish()))){
                        aFinish = step.getActualFinish();
                     }
                    if(step.getCompleteFlag()){
                        completedSize++;
                    }
                    if(empId.equals("") && resId != null 
                            && !empIdList.contains(resId)){
                        empId = resId;
                        empIdList.add(resId);
                    }else if(resId != null &&
                            !empIdList.contains(resId)){
                        empId += ","+ resId;
                        empIdList.add(resId);
                    }
                    if(emp.equals("") && resName != null 
                            && !empList.contains(resName)){
                        emp = resName;
                        empList.add(resName);
                    }else if(resName != null &&
                           resId!=null && !empList.contains(resName)){
                        emp += ","+ resName;
                        empList.add(resName);
                    }
                    if(step.getActualCostTime() != null && step.getActualCostTime() > 0){
                     actualTime += step.getActualCostTime();
                    }
                }
                if(aStart == null) {
              	    dto.setStatus(ActivityStatus.NOT_STARTED.getDescription());
                } else if(aFinish != null){
                    dto.setStatus(ActivityStatus.COMPLETED.getDescription());
                }else{
                    dto.setStatus(ActivityStatus.IN_PROGRESS.getDescription());
                }
                Double cSize = Double.valueOf(Integer.valueOf(completedSize).toString());
                Double totalSize = Double.valueOf(Integer.valueOf(size).toString());
                if(totalSize > 0){
                    comPercent =  setScale(cSize/totalSize*100);
                }
                dto.setBeanType(DtoMonitoring.BEAN_TYPE_STEP_GGROUP);
                dto.setComplete(comPercent+"%");
                dto.setPlanStart(pStart);
                dto.setPlanFinish(pFinish);
                dto.setActualStart(aStart);
                dto.setActualFinish(aFinish);
                dto.setResourceNames(emp);
                dto.setResourceIds(empId);
                dto.setStepName(name);
                dto.setActualHours(actualTime);
                dto.setCategoryDes(procName);
                return dto;
        }
        
        /**
         * 将同一WBS下的Activity分别放入同一个List中
         * @param actList
         */
        private void setActivityMap(List actList){
               if(actList != null){
                   try {
                   for(int i=0;i<actList.size();i++){
                        Activity act = (Activity)actList.get(i);
                        List activityList = (List)actMap.get(act.getWBSObjectId());
                        if(activityList != null){
                            activityList.add(act);
                        }else{
                           List  aList = new ArrayList();
                           aList.add(act);
                           actMap.put(act.getWBSObjectId(),aList);
                        }
                   }
               } catch (Exception e) {
                   throw new BusinessException(e.getMessage(),e.getMessage());
               }
              }      
        }
        
        /**
         * 將相同作業(Activity)的資源放在同一數組中
         * @param resAssginList
         */
        private void setResourceAssignmentMap(List resAssginList){
                if(resAssginList != null){
                    try {
                    for(int i= 0;i<resAssginList.size();i++){
                       ResourceAssignment resA = (ResourceAssignment)resAssginList.get(i);
                       List resList = (List)resAssginMap.get(resA.getActivityObjectId());
                       if(resList != null && resList.size() > 0){
                            resList.add(resA.getResourceObjectId());
                       }else{
                           resList = new ArrayList();
                           resList.add(resA.getResourceObjectId());
                           resAssginMap.put(resA.getActivityObjectId(),resList);
                       }
                  }
                } catch (Exception e) {
                    throw new BusinessException(e.getMessage(),e.getMessage());
                }
          }
        }
        
        /**
         * 將資源放入緩存
         * @param resList
         */
        private void setResourceMap(List resList){
                if(resList != null){
                 try {
                  for(int i= 0;i<resList.size();i++){
                   Resource res = (Resource)resList.get(i);
                   resMap.put(res.getObjectId(),res);
                 }
               } catch (Exception e) {
                throw new BusinessException(e.getMessage(),e.getMessage());
              }
          }
       }
        
        /**
         * 将STEP按Activity分类放入缓存中
         * @param stepList
         */
        private void setStepMap(List stepList){
            if(stepList != null){
                try {
                for(int i=0;i<stepList.size();i++){
                     TsStep step = (TsStep)stepList.get(i);
                     step.setActualCostTime(stepDao.getStepCumulativeHours(step.getRid()));
                     List sList = (List)stepMap.get(step.getTaskId().toString());
                     if(sList != null){
                         sList.add(step);
                     }else{
                        List  stList = new ArrayList();
                        stList.add(step);
                        stepMap.put(step.getTaskId().toString(),stList);
                     }
                }
            } catch (Exception e) {
                throw new BusinessException(e.getMessage(),e.getMessage());
            }
           }      
        }
        
         /**
          * setStepDetailInfoMap
          * @param sDetailList
          */
         private void setStepDetailInfoMap(List sDetailList){
                if(sDetailList != null){
                    for(int i=0;i<sDetailList.size();i++){
                        TsStepTDetail sDetail = (TsStepTDetail)sDetailList.get(i);
                        Long rid = sDetail.getRid();
                        String procName = sDetail.getProcName();
                        stepDetailMap.put(rid,procName);
                    }
                }
        }
        
        private BigDecimal setScale(Double wPercent){
               BigDecimal percent =  BigDecimal.valueOf(wPercent);
               percent = percent.setScale(0,BigDecimal.ROUND_HALF_UP);
               return percent;
        }
        
        /**
         * 分别将父结点相同的WBS放入同一个LIST中
         * @param wbsList
         */
        private void setWBSMap(List wbsList){
                for(int i = 0;i<wbsList.size();i++){
                    WBS wbs = (WBS)wbsList.get(i);
                    try {
                        ObjectId objId = wbs.getParentObjectId();
                        List wList = (List)wbsMap.get(objId);
                        if(wList != null && wList.size() > 0){
                            wList.add(wbs);
                        }else{
                            List list = new ArrayList();
                            list.add(wbs);
                            wbsMap.put(objId,list);
                        }
                    } catch (BusinessObjectException e) {
                        throw new BusinessException(e.getMessage(),e.getMessage());
                    }
                }
        }
        
        private DtoMonitoring copyProperties(WBS wbs){
            DtoMonitoring dto = getDtoMonitoringInstance();
            try {
                dto.setColorIndex(HSSFColor.LIGHT_CORNFLOWER_BLUE.index);
                dto.setBeanType(DtoMonitoring.BEAN_TYPE_WBS);
                dto.setWbsId(Long.valueOf(wbs.getObjectId().toString()));
                dto.setName(wbs.getName());
                dto.setWbsCode(wbs.getCode());
                if(wbs.getSummaryActualLaborUnits() != null) {
                	dto.setActualHours(wbs.getSummaryActualLaborUnits().doubleValue());
                }
                if(wbs.getStartDate() != null){
                    dto.setPlanStart(new Date(wbs.getStartDate().getTime()));
                }
                if(wbs.getFinishDate() != null){
                    dto.setPlanFinish(new Date(wbs.getFinishDate().getTime()));
                }
                if(wbs.getSummaryActualStartDate() != null){
                    dto.setActualStart(new Date(wbs.getSummaryActualStartDate().
                            getTime()));
                }
                if(wbs.getSummaryActualFinishDate() != null){
                    dto.setActualFinish(new Date(wbs.getSummaryActualFinishDate().
                            getTime()));
                }
            } catch (Exception e) {
                throw new BusinessException(e.getMessage(),e.getMessage());
            }
            return dto;
        }

        private DtoMonitoring getDtoMonitoringInstance() {
            if(excelDto){
                return new StyledDtoMonitoring();
            }else{
                return new DtoMonitoring();
            }
       }
        
        private static short checkStatusIndicatorColor(StyledDtoMonitoring dto) {
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

       public class StyledDtoMonitoring extends DtoMonitoring
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
   		  if(this.getColorIndex() > 0) {
   			  return this.getColorIndex() + "";
   		  } else {
   			  return null;
   		  }
        }
    }
        
        /**
         * to double if null:0
         * @param n PrmNumber
         * @return double
         */
        private static double toDouble(PrmNumber n) {
           if(n == null) {
               return 0;
           } else {
               return n.doubleValue();
           }
        }
        
        public void setMonitoringP3ApiDao(IMonitoringP3ApiDao monitoringP3ApiDao) {
            this.monitoringP3ApiDao = monitoringP3ApiDao;
        }
    
        public void setStepManagementApiDao(IStepManagementP3ApiDao stepManagementApiDao) {
            this.stepManagementApiDao = stepManagementApiDao;
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
