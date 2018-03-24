package server.essp.timesheet.activity.resources.dao;

import java.util.List;
import java.util.Map;

import c2s.essp.timesheet.activity.DtoResourceUnits;
import c2s.essp.timesheet.activity.DtoResourceDetail;

/**
 * <p>Description: ResourceDao的接口</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author not attributable
 * @version 1.0
 */
public interface IResourceDao {
    /**
     * 根据RID查询出Resource的记录
     * @param rid Long
     * @return List
     * @throws Exception
     */
    public List queryResourceList(Long rid) throws Exception;
    /**
     * 根据resourceRid查詢Resource的记录
     * @param assignmentObjectId Long
     * @return DtoResourceUnits
     * @throws Exception
     */
    public DtoResourceUnits queryResourceUnits(Long assignmentObjectId) throws Exception;
    /**
     * 根据ResourceRid查询RESOURCE的详细信息
     * @param assignmentObjectId Long
     * @return DtoResourceDetail
     * @throws Exception
     */
    public DtoResourceDetail queryResourceDetail(Long assignmentObjectId) throws Exception;
    /**
     * 修改Resource
     * @param dto DtoResourceUnits
     * @throws Exception
     */
    public void updateResource(DtoResourceUnits dto) throws Exception;
    
    /**
     * 根据loginId查询员工是否需要填写工时单
     * @param loginId
     * @return boolean
     * @throws Exception
     */
    public boolean isFillTimesheet(String loginId);
    
    /**
     * 將需填寫，不需填寫人員信息放入MAP中，并得到每個員工對應的日歷放入MAP中
     * @return Map
     * @throws Exception
     */
    public Map getEmployInfoMap() throws Exception;
}
