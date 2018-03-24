package server.essp.timesheet.activity.resources.dao;

import server.essp.common.primavera.PrimaveraApiBase;
import java.util.List;
import com.primavera.integration.client.bo.BOIterator;
import com.primavera.integration.client.bo.BusinessObjectException;
import c2s.dto.DtoUtil;
import c2s.essp.timesheet.activity.DtoResourceAssignment;
import com.primavera.integration.client.bo.enm.ResourceType;
import com.primavera.integration.client.bo.object.Calendar;
import com.primavera.integration.client.bo.object.ResourceAssignment;
import java.util.Map;
import java.util.HashMap;
import c2s.essp.timesheet.activity.DtoResourceUnits;
import c2s.essp.timesheet.activity.DtoResourceDetail;
import com.primavera.integration.client.bo.object.Resource;
import server.framework.common.BusinessException;
import com.primavera.common.value.Unit;
import java.util.ArrayList;
import java.util.Date;
import com.primavera.integration.client.bo.object.Activity;
import com.primavera.common.value.ObjectId;

/**
 * <p>Description: ResourceDao的实现</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author not attributable
 * @version 1.0
 */
public class ResourceDaoImp extends PrimaveraApiBase implements
        IResourceDao {
    private final static Map<String, String> resourceAssignPropertiesMap;
    private final static Map<String, String> resourcePropertiesMap;

    /**
     * 根据RID查询出Resource的记录
     * @param rid Long
     * @return List
     * @throws Exception
     */
    public List queryResourceList(Long rid) throws Exception {
        Activity activity = Activity.load(getSession(),
                                          new String[]{"ObjectId"},
                                          new ObjectId(rid));
        BOIterator boiResourceAssign = activity.loadResourceAssignments(
            new String[] {"ObjectId", "ActivityObjectId", "ResourceName",
            "ResourceId", "RemainingUnits", "RoleId", "ResourceType",
            "StartDate", "IsPrimaryResource", "FinishDate", "PendingRemainingUnits"}, "", "");
       List list = DtoUtil.array2List(boiResourceAssign.getAll(),
               DtoResourceAssignment.class, resourceAssignPropertiesMap);
      List resList = new ArrayList();
      for(int i=0;i<list.size();i++){
          DtoResourceAssignment dtoRes = (DtoResourceAssignment)list.get(i);
          if(dtoRes.isIsPrimaryResource()){
               dtoRes.setPrimary("Yes");
          }
          if(dtoRes.getStartDate() != null){
              dtoRes.setStartDate(new Date(dtoRes.getStartDate().getTime()));
          }
          if(dtoRes.getFinishDate() != null){
              dtoRes.setFinishDate(new Date(dtoRes.getFinishDate().getTime()));
          }
          resList.add(dtoRes);
      }
        return resList;
    }

    /**
     * 根据resourceRid查詢Resource的记录
     * @param resourceObjId Long
     * @return DtoResourceUnits
     * @throws Exception
     */
     public DtoResourceUnits queryResourceUnits(Long assignmentObjectId)
             throws Exception {
         DtoResourceUnits dtoResUnits = new DtoResourceUnits();
         ResourceAssignment resourceAssign = ResourceAssignment.load(getSession(),
                 new String[] {"ObjectId", "ResourceObjectId", "ResourceName",
                 "ResourceObjectId", "PlannedUnits", "IsPrimaryResource",
                 "RemainingUnits", "ActualUnits", "PendingRemainingUnits"},
                 new ObjectId(assignmentObjectId));
         DtoUtil.copyBeanToBeanWithPropertyMap(dtoResUnits,
                                               resourceAssign,
                                               resourceAssignPropertiesMap);
       return  dtoResUnits;
     }

     /**
      * 根据ResourceObjectId得到相应的信息
      * @param ResourceObjectId Long
      * @return ResourceAssignment
      * @throws Exception
      */
     private ResourceAssignment getUnits(Long assignmentObjectId) throws Exception {
         ResourceAssignment resourceAssign = ResourceAssignment.load(getSession(),
                 new String[] {"ObjectId", "ResourceObjectId", "ResourceName",
                 "ResourceObjectId", "PlannedUnits", "IsPrimaryResource",
                 "RemainingUnits", "ActualUnits"},
                 new ObjectId(assignmentObjectId));

      return  resourceAssign;
    }


    /**
     * 根据ResourceRid查询RESOURCE的详细信息
     * @param ResourceObjectId Long
     * @return DtoResourceDetail
     * @throws Exception
     */
    public DtoResourceDetail queryResourceDetail(Long assignmentObjectId) throws
            Exception {
        DtoResourceDetail dtoResDetail = new DtoResourceDetail();
        DtoResourceDetail dtoResTemp = new DtoResourceDetail();
        ResourceAssignment assign = ResourceAssignment.load(getSession(),
                new String[] {"ResourceName", "ResourceObjectId", "RoleName",
                "IsPrimaryResource", "PendingRemainingUnits","ResourceType"}, new ObjectId(assignmentObjectId));

        Resource resource = assign.loadResource(new String[] {"ObjectId","ResourceType",
                                          "OfficePhone", "EmailAddress"});

        DtoUtil.copyBeanToBeanWithPropertyMap(dtoResDetail,
                                              assign,
                                              resourceAssignPropertiesMap);
        DtoUtil.copyBeanToBeanWithPropertyMap(dtoResTemp,
                                              resource,
                                              resourcePropertiesMap);
        if(ResourceType.LABOR.getValue().equals(resource.
                getResourceType().getValue())){
            dtoResDetail.setIsResourceType(true);
        }else{
            dtoResDetail.setIsResourceType(false);
        }
        dtoResDetail.setEmail(dtoResTemp.getEmail());
        dtoResDetail.setOfficePhone(dtoResTemp.getOfficePhone());
        return dtoResDetail;
    }

    /**
     * 修改Resource
     * @param dto DtoResourceUnits
     * @throws Exception
     */
    public void updateResource(DtoResourceUnits dtoUnits) throws Exception {
        try {
            ResourceAssignment dbBean = getUnits(dtoUnits.getObjectId());
            if (dtoUnits.getPriorActualUnits()!=null) {
               Unit unit = new Unit(dtoUnits.getPriorActualUnits());
               dbBean.setActualUnits(unit);
            }
            if (dtoUnits.getNewRemainingUnits()!= null) {
                dbBean.setPendingRemainingUnits(dtoUnits.getNewRemainingUnits());
             }
            dbBean.update();
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new BusinessException("E000000", "update resource error.", ex);
        }
    }
    
    /**
     * 根据loginId查询员工是否需要填写工时单
     * @param loginId
     * @return boolean
     * @throws Exception
     */
    public boolean isFillTimesheet(String loginId) {
           Resource res = this.getResource(loginId);
           if(res != null){
              try {
                return res.getUseTimesheets();
            } catch (BusinessObjectException e) {
                log.warn(e);
                return false;
            }
           } else {
               return false;
           }
    }
    
    /**
     * 將需填寫，不需填寫人員信息放入MAP中，并得到每個員工對應的日歷放入MAP中
     * @return Map
     * @throws Exception
     */
    public Map getEmployInfoMap() throws Exception {
           Map map = new HashMap();
           Map mapEmpUnfill = new HashMap();
           Map cMap = new HashMap();
           Map cNameMap = new HashMap();
           String empId = "";
           String cName;
           BOIterator boi = getGOM().loadResources( new String[] {"Id", 
                           "CalendarName", "UseTimesheets", "UserName"},null,null);
           while(boi.hasNext()){
                Resource resource = (Resource)boi.next();
                String userName = resource.getUserName();
                if(userName == null) {
                	continue;
                }
                empId = userName.toUpperCase();
                cName = resource.getCalendarName();
                Object c = cMap.get(cName);
                if(c == null) {
                    cMap.put(cName, resource.loadCalendar(Calendar.getAllFields()));
                }
                cNameMap.put(empId,cName);
                if(!resource.getUseTimesheets()){
                    mapEmpUnfill.put(empId,false);
                }else{
                    mapEmpUnfill.put(empId,true);
                }
           }
           map.put("isFill",mapEmpUnfill);
           map.put("Canlendar",cMap);
           map.put("CanlendarName",cNameMap);
           return map;
    }
    
    static {
           resourceAssignPropertiesMap = new HashMap();
           resourceAssignPropertiesMap.put("objectId", "objectId");
           resourceAssignPropertiesMap.put("activityObjectId", "rid");
           resourceAssignPropertiesMap.put("resourceId", "resourceId");
           resourceAssignPropertiesMap.put("resourceName", "resourceName");
           resourceAssignPropertiesMap.put("remainingUnits", "remainingUnits");
           resourceAssignPropertiesMap.put("roleId", "roleId");
           resourceAssignPropertiesMap.put("roleName", "roleName");
           resourceAssignPropertiesMap.put("isPrimaryResource", "isPrimaryResource");
           resourceAssignPropertiesMap.put("resourceType", "resourceType");
           resourceAssignPropertiesMap.put("startDate", "startDate");
           resourceAssignPropertiesMap.put("finishDate", "finishDate");

           resourceAssignPropertiesMap.put("resourceObjectId", "resourceObjectId");
           resourceAssignPropertiesMap.put("plannedUnits", "plannedUnits");
           resourceAssignPropertiesMap.put("remainingUnits", "remainingUnits");
           resourceAssignPropertiesMap.put("actualUnits", "priorActualUnits");
           resourceAssignPropertiesMap.put("ResourceObjectId", "resourceRid");
           resourceAssignPropertiesMap.put("PendingRemainingUnits", "newRemainingUnits");

          resourcePropertiesMap = new HashMap();
          resourcePropertiesMap.put("officePhone", "officePhone");
          resourcePropertiesMap.put("emailAddress", "email");
     }
    }

