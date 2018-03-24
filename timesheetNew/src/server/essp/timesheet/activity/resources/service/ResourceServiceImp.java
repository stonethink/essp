package server.essp.timesheet.activity.resources.service;

import java.util.List;
import c2s.essp.timesheet.activity.DtoResourceUnits;
import server.essp.timesheet.activity.resources.dao.IResourceDao;
import server.framework.common.BusinessException;
import c2s.essp.timesheet.activity.DtoResourceDetail;

/**
 * <p>Description:ResourceService的实现 </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author not attributable
 * @version 1.0
 */
public class ResourceServiceImp implements IResourceService {
   private IResourceDao iresDao;

   /**
    * 根据RID查询出Resource的记录
    * @param rid Long
    * @return List
    * @throws Exception
    */
    public List getAssignedResourceList(Long activityRid){
        List list = null;
         try {
           list = iresDao.queryResourceList(activityRid);
          }catch (Exception ex) {
           throw new BusinessException("error.logic.ResourceServiceImp.loadResListErr",
                                       "load ResourceAssigned error", ex);
       }
         return list;
    }

    /**
     * 根据resourceRid查詢Resource的记录
     * @param resourceObjId Long
     * @return DtoResourceUnits
     * @throws Exception
     */
    public DtoResourceUnits getResUnits(Long assignmentObjectId){
       DtoResourceUnits dtoResUnits = null;
        try {
          dtoResUnits = iresDao.queryResourceUnits(assignmentObjectId);
         }catch (Exception ex) {
          throw new BusinessException("error.logic.ResourceServiceImp.getResUnitsErr",
                                      "get ResourceUnits error", ex);
      }
        return dtoResUnits;
   }

   /**
    * 根据ResourceRid查询RESOURCE的详细信息
    * @param ResourceObjectId Long
    * @return DtoResourceDetail
    * @throws Exception
    */
   public DtoResourceDetail getResDetail(Long assignmentObjectId){
      DtoResourceDetail dtoResDetail = null;
       try {
         dtoResDetail = iresDao.queryResourceDetail(assignmentObjectId);
        }catch (Exception ex) {
         throw new BusinessException("error.logic.ResourceServiceImp.GetResDetailErr",
                                     "get ResourceDetail error", ex);
     }
       return dtoResDetail;
  }

  /**
   * 修改Resource
   * @param dto DtoResourceUnits
   * @throws Exception
   */
  public void updateReource(DtoResourceUnits dtoUnits) {
      try {
             iresDao.updateResource(dtoUnits);
          }catch (Exception ex) {
           throw new BusinessException("error.logic.ResourceServiceImp.updateResErr",
                                       "update Resource error", ex);
       }

   }
    public void setIresDao(IResourceDao iresDao) {
        this.iresDao = iresDao;
    }

}
