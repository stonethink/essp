package server.essp.timesheet.activity.resources.service;

import java.util.List;
import c2s.essp.timesheet.activity.DtoResourceUnits;
import c2s.essp.timesheet.activity.DtoResourceDetail;

/**
 * <p>Description:ResourceService�Ľӿ�</p>
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
     * ����RID��ѯ��Resource�ļ�¼
     * @param rid Long
     * @return List
     * @throws Exception
     */
    public List getAssignedResourceList(Long activityRid);

    /**
     * ����resourceRid��ԃResource�ļ�¼
     * @param resourceObjId Long
     * @return DtoResourceUnits
     * @throws Exception
     */
    public DtoResourceUnits getResUnits(Long assignmentObjectId);

    /**
     * ����ResourceRid��ѯRESOURCE����ϸ��Ϣ
     * @param ResourceObjectId Long
     * @return DtoResourceDetail
     * @throws Exception
     */
    public DtoResourceDetail getResDetail(Long assignmentObjectId);

    /**
     * �޸�Resource
     * @param dto DtoResourceUnits
     * @throws Exception
     */
    public void updateReource(DtoResourceUnits dtoUnits);
}
