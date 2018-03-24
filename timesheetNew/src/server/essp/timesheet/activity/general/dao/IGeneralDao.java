package server.essp.timesheet.activity.general.dao;

import com.primavera.ServerException;
import com.primavera.integration.client.bo.BusinessObjectException;
import com.primavera.integration.client.bo.object.Activity;
import com.primavera.integration.network.NetworkException;

import c2s.essp.timesheet.activity.DtoActivityGeneral;

/**
 * <p>Description:GeneralDao的接口 </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author not attributable
 * @version 1.0
 */
public interface IGeneralDao {
    /**
     * 通过rid查询符合条件的General
     * @param rid Long
     * @return DtoActivityGeneral
     * @throws Exception
     */
    public DtoActivityGeneral queryGeneral(Long rid) throws Exception;
    /**
     * 修改General
     * @param dataBean DtoActivityGeneral
     * @throws Exception
     */
    public void update(DtoActivityGeneral dataBean) throws Exception;
    /**
     * 通过RID从General中得到相应的NOTE字段
     * @param rid Long
     * @return DtoActivityGeneral
     * @throws Exception
     */
    public DtoActivityGeneral getNoteFromGneral(Long rid) throws Exception;

    /**
     * 保存Feedback
     * @param rid Long
     * @param feedback String
     * @throws Exception
     */
     public void saveFeeedback(Long rid, String feedback) throws Exception;
     
     /**
      * 获取指定用户是否是指定作业的主资源
      * @param activityId
      * @param loginId
      * @return boolean
      */
     public boolean isPrimaryResource(Long activityId, String loginId);
     
     /**
      * 根据RID获得对应的Activity
      * @param rid Long
      * @return Activity
      * @throws BusinessObjectException
      * @throws NetworkException
      * @throws ServerException
      */
     public Activity getActivity(Long rid) throws BusinessObjectException,
             NetworkException, ServerException;
}
