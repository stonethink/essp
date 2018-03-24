package server.essp.timesheet.activity.resources.service;

import java.util.List;
import c2s.essp.timesheet.activity.DtoResourceUnits;
import server.essp.timesheet.activity.resources.dao.IResourceDao;
import server.framework.common.BusinessException;
import c2s.essp.timesheet.activity.DtoResourceDetail;

/**
 * <p>Description:ResourceService��ʵ�� </p>
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
    * ����RID��ѯ��Resource�ļ�¼
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
     * ����resourceRid��ԃResource�ļ�¼
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
    * ����ResourceRid��ѯRESOURCE����ϸ��Ϣ
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
   * �޸�Resource
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
