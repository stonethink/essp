package server.essp.timesheet.activity.general.service;

import c2s.essp.timesheet.activity.DtoActivityGeneral;

/**
 * <p>Description:GeneralService�Ľӿ� </p>
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
    * ͨ��rid��ѯ����������General
    * @param rid Long
    * @return DtoActivityGeneral
    * @throws Exception
    */
     public DtoActivityGeneral getActivityGeneral(Long activityRid);

     /**
      * �޸�General
      * @param dataBean DtoActivityGeneral
      * @throws Exception
      */
     public void updateGeneral(DtoActivityGeneral dtoGeneral);

     /**
      * ͨ��RID��General�еõ���Ӧ��NOTE�ֶ�
      * @param rid Long
      * @return DtoActivityGeneral
      * @throws Exception
      */
     public DtoActivityGeneral getGeneralNote(Long activityRid);

     /**
     * ����Feedback
     * @param rid Long
     * @param feedback String
     * @throws Exception
     */
     public void saveFeeedback(Long rid, String feedback);
     
     /**
      * ��ȡָ���û��Ƿ���ָ����ҵ������Դ
      * @param activityId
      * @param loginId
      * @return boolean
      */
     public boolean isPrimaryResource(Long activityId, String loginId);

}
