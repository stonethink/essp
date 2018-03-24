package server.essp.timesheet.activity.relationships.dao;

import java.util.List;
import com.primavera.integration.client.bo.BusinessObjectException;
import com.primavera.ServerException;
import com.primavera.integration.network.NetworkException;
import c2s.essp.timesheet.activity.DtoRelationShipsDetail;
import c2s.essp.timesheet.activity.DtoRelationShips;

/**
 * <p>Description:RelationShipsDao的接口 </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author not attributable
 * @version 1.0
 */
public interface IRelationShipsDao {

    /**
     * 查询指定Activity的前紧作业
     * @param activityObjId Long
     * @return List
     * @throws Exception
     */
    public List queryPredecessor(Long activityObjId) throws Exception;

    /**
     * 查询指定Activity的后续作业
     * @param activityObjId Long
     * @return List
     * @throws Exception
     */
    public List querySuccessor(Long activityObjId) throws Exception;

    /**
     * 根据activityRid查询RelationDetail
     * @param activityObjId Long
     * @return DtoRelationShipsDetail
     * @throws Exception
     */
    public DtoRelationShipsDetail queryRelationDetail(Long activityRid) throws Exception;

}
