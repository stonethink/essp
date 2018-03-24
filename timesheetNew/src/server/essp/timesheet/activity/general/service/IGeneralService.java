package server.essp.timesheet.activity.general.service;

import c2s.essp.timesheet.activity.DtoActivityGeneral;

/**
 * <p>Description:GeneralService的接口 </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author not attributable
 * @version 1.0
 */
public interface IGeneralService {
    /**
    * 通过rid查询符合条件的General
    * @param rid Long
    * @return DtoActivityGeneral
    * @throws Exception
    */
     public DtoActivityGeneral getActivityGeneral(Long activityRid);

     /**
      * 修改General
      * @param dataBean DtoActivityGeneral
      * @throws Exception
      */
     public void updateGeneral(DtoActivityGeneral dtoGeneral);

     /**
      * 通过RID从General中得到相应的NOTE字段
      * @param rid Long
      * @return DtoActivityGeneral
      * @throws Exception
      */
     public DtoActivityGeneral getGeneralNote(Long activityRid);

     /**
     * 保存Feedback
     * @param rid Long
     * @param feedback String
     * @throws Exception
     */
     public void saveFeeedback(Long rid, String feedback);
     
     /**
      * 获取指定用户是否是指定作业的主资源
      * @param activityId
      * @param loginId
      * @return boolean
      */
     public boolean isPrimaryResource(Long activityId, String loginId);

}
