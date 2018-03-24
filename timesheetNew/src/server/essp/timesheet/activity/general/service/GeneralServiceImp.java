package server.essp.timesheet.activity.general.service;

import c2s.essp.timesheet.activity.DtoActivityGeneral;
import server.framework.common.BusinessException;
import server.essp.timesheet.activity.general.dao.IGeneralDao;


/**
 * <p>Description:GeneralService的实现 </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author not attributable
 * @version 1.0
 */
public class GeneralServiceImp implements IGeneralService {
     private IGeneralDao igeneralDao;
     /**
      * 通过rid查询符合条件的General
      * @param rid Long
      * @return DtoActivityGeneral
      * @throws Exception
      */
    public DtoActivityGeneral getActivityGeneral(Long activityRid) {
        if(activityRid == null) return null;
          DtoActivityGeneral dtoGeneral = null;
          try {
            dtoGeneral = igeneralDao.queryGeneral(activityRid);
           }catch (Exception ex) {
            throw new BusinessException("error.logic.GeneralServiceImp.loadActivityGeneralErr",
                                        "load activity general error", ex);
        }
          return dtoGeneral;
    }

    /**
     * 修改General
     * @param dataBean DtoActivityGeneral
     * @throws Exception
     */
    public void updateGeneral(DtoActivityGeneral dtoGeneral) {
         try {
             igeneralDao.update(dtoGeneral);
          }catch (Exception ex) {
           throw new BusinessException("error.logic.GeneralServiceImp.updateActivityGeneralErr",
                                       "update general error", ex);
       }
   }

   /**
    * 通过RID从General中得到相应的NOTE字段
    * @param rid Long
    * @return DtoActivityGeneral
    * @throws Exception
    */
   public DtoActivityGeneral getGeneralNote(Long activityRid) {
       DtoActivityGeneral dtoGeneral;
       try {
           dtoGeneral = igeneralDao.getNoteFromGneral(activityRid);
       } catch (Exception ex) {
           throw new BusinessException("error.logic.GeneralServiceImp.loadNoteErr",
                                       "get note error", ex);
       }
       return dtoGeneral;
   }

   /**
     * 保存Feedback
     * @param rid Long
     * @param feedback String
     * @throws Exception
     */
    public void saveFeeedback(Long rid, String feedback) {
        try {
            igeneralDao.saveFeeedback(rid, feedback);
        } catch (Exception ex) {
            throw new BusinessException("error.logic.GeneralServiceImp.saveFeedback",
                                       "save feedback error", ex);
        }
    }
    
    /**
     * 获取指定用户是否是指定作业的主资源
     * @param activityId
     * @param loginId
     * @return boolean
     */
    public boolean isPrimaryResource(Long activityId, String loginId) {
    	return igeneralDao.isPrimaryResource(activityId, loginId);
    }



    public void setIgeneralDao(IGeneralDao iGeneralDao) {
        this.igeneralDao = iGeneralDao;
    }

}
