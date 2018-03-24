package server.essp.timesheet.activity.resources.service;

import java.util.List;
import c2s.essp.timesheet.activity.DtoResourceUnits;
import c2s.essp.timesheet.activity.DtoResourceDetail;

/**
 * <p>Description:ResourceService的接口</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author not attributable
 * @version 1.0
 */
public interface IResourceService {
    /**
     * 根据RID查询出Resource的记录
     * @param rid Long
     * @return List
     * @throws Exception
     */
    public List getAssignedResourceList(Long activityRid);

    /**
     * 根据resourceRid查詢Resource的记录
     * @param resourceObjId Long
     * @return DtoResourceUnits
     * @throws Exception
     */
    public DtoResourceUnits getResUnits(Long assignmentObjectId);

    /**
     * 根据ResourceRid查询RESOURCE的详细信息
     * @param ResourceObjectId Long
     * @return DtoResourceDetail
     * @throws Exception
     */
    public DtoResourceDetail getResDetail(Long assignmentObjectId);

    /**
     * 修改Resource
     * @param dto DtoResourceUnits
     * @throws Exception
     */
    public void updateReource(DtoResourceUnits dtoUnits);
}
