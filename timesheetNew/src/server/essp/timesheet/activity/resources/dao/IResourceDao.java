package server.essp.timesheet.activity.resources.dao;

import java.util.List;
import java.util.Map;

import c2s.essp.timesheet.activity.DtoResourceUnits;
import c2s.essp.timesheet.activity.DtoResourceDetail;

/**
 * <p>Description: ResourceDao�Ľӿ�</p>
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
     * ����RID��ѯ��Resource�ļ�¼
     * @param rid Long
     * @return List
     * @throws Exception
     */
    public List queryResourceList(Long rid) throws Exception;
    /**
     * ����resourceRid��ԃResource�ļ�¼
     * @param assignmentObjectId Long
     * @return DtoResourceUnits
     * @throws Exception
     */
    public DtoResourceUnits queryResourceUnits(Long assignmentObjectId) throws Exception;
    /**
     * ����ResourceRid��ѯRESOURCE����ϸ��Ϣ
     * @param assignmentObjectId Long
     * @return DtoResourceDetail
     * @throws Exception
     */
    public DtoResourceDetail queryResourceDetail(Long assignmentObjectId) throws Exception;
    /**
     * �޸�Resource
     * @param dto DtoResourceUnits
     * @throws Exception
     */
    public void updateResource(DtoResourceUnits dto) throws Exception;
    
    /**
     * ����loginId��ѯԱ���Ƿ���Ҫ��д��ʱ��
     * @param loginId
     * @return boolean
     * @throws Exception
     */
    public boolean isFillTimesheet(String loginId);
    
    /**
     * �������������ˆT��Ϣ����MAP�У����õ�ÿ���T���������՚v����MAP��
     * @return Map
     * @throws Exception
     */
    public Map getEmployInfoMap() throws Exception;
}
