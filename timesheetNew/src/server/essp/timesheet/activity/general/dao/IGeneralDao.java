package server.essp.timesheet.activity.general.dao;

import com.primavera.ServerException;
import com.primavera.integration.client.bo.BusinessObjectException;
import com.primavera.integration.client.bo.object.Activity;
import com.primavera.integration.network.NetworkException;

import c2s.essp.timesheet.activity.DtoActivityGeneral;

/**
 * <p>Description:GeneralDao�Ľӿ� </p>
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
     * ͨ��rid��ѯ����������General
     * @param rid Long
     * @return DtoActivityGeneral
     * @throws Exception
     */
    public DtoActivityGeneral queryGeneral(Long rid) throws Exception;
    /**
     * �޸�General
     * @param dataBean DtoActivityGeneral
     * @throws Exception
     */
    public void update(DtoActivityGeneral dataBean) throws Exception;
    /**
     * ͨ��RID��General�еõ���Ӧ��NOTE�ֶ�
     * @param rid Long
     * @return DtoActivityGeneral
     * @throws Exception
     */
    public DtoActivityGeneral getNoteFromGneral(Long rid) throws Exception;

    /**
     * ����Feedback
     * @param rid Long
     * @param feedback String
     * @throws Exception
     */
     public void saveFeeedback(Long rid, String feedback) throws Exception;
     
     /**
      * ��ȡָ���û��Ƿ���ָ����ҵ������Դ
      * @param activityId
      * @param loginId
      * @return boolean
      */
     public boolean isPrimaryResource(Long activityId, String loginId);
     
     /**
      * ����RID��ö�Ӧ��Activity
      * @param rid Long
      * @return Activity
      * @throws BusinessObjectException
      * @throws NetworkException
      * @throws ServerException
      */
     public Activity getActivity(Long rid) throws BusinessObjectException,
             NetworkException, ServerException;
}
