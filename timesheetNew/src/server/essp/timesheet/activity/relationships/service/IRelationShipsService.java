package server.essp.timesheet.activity.relationships.service;

import java.util.List;
import c2s.essp.timesheet.activity.DtoRelationShipsDetail;

/**
 * <p>Description:RelationShipsService�Ľӿ� </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author not attributable
 * @version 1.0
 */
public interface IRelationShipsService {
    /**
     * ���ݲ�ѯ������ѯRelation
     * @param activityObjId Long
     * @param isPre boolean
     * @return List
     * @throws Exception
     * @throws BusinessObjectException
     * @throws NetworkException
     * @throws ServerException
     */
  public List getRelationShipsList(Long activityObjId,boolean isPre);

  /**
   * ����activityRid��ѯRelationDetail
   * @param activityObjId Long
   * @return DtoRelationShipsDetail
   * @throws Exception
   */
  public DtoRelationShipsDetail getRelationDetail(Long activityObjId);

}
