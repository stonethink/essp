package server.essp.timesheet.activity.relationships.service;

import java.util.List;
import server.framework.common.BusinessException;
import c2s.essp.timesheet.activity.DtoRelationShipsDetail;
import server.essp.timesheet.activity.relationships.dao.IRelationShipsDao;

/**
 * <p>Description:RelationShipsService的实现</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author TBH
 * @version 1.0
 */
public class RelationShipsServiceImp implements IRelationShipsService {
   private IRelationShipsDao irelationDao;
   /**
    * 根据查询条件查询Relation
    * @param activityObjId Long
    * @param isPre boolean
    * @return List
    * @throws Exception
    * @throws BusinessObjectException
    * @throws NetworkException
    * @throws ServerException
    */
   public List getRelationShipsList(Long activityObjId, boolean isPre) {
       List list = null;
       try {
           if(isPre) {
               list = irelationDao.queryPredecessor(activityObjId);
           } else {
               list = irelationDao.querySuccessor(activityObjId);
           }
       } catch (Exception ex) {
           throw new BusinessException("error.logic.RelationShipsServiceImp.loadRelErr",
                                       "load RelationShips error", ex);
       }
       return list;
   }

   /**
    * 根据activityRid查询RelationDetail
    * @param activityObjId Long
    * @return DtoRelationShipsDetail
    * @throws Exception
    */
   public DtoRelationShipsDetail getRelationDetail(Long activityObjId) {
       DtoRelationShipsDetail dtoDetail = null;
       try {
          dtoDetail = irelationDao.queryRelationDetail(activityObjId);
      } catch (Exception ex) {
          throw new BusinessException("error.logic.RelationShipsServiceImp.getRelDetailErr=",
                                      "get RelationDetail error", ex);
      }

     return dtoDetail;
 }

    public void setIrelationDao(IRelationShipsDao irelationDao) {
        this.irelationDao = irelationDao;
    }



}
