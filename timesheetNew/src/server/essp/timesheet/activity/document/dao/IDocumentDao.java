package server.essp.timesheet.activity.document.dao;

import java.util.List;
import c2s.essp.timesheet.activity.DtoDocumentDetail;

/**
 * <p>Description: DocumentDao的接口</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author not attributable
 * @version 1.0
 */
public interface IDocumentDao {
    /**
     * 根据activityRid得到Document记录
     * @param activityRid Long
     * @return List
     * @throws Exception
     */
    public List getDocumentList(Long activityRid) throws Exception;
    /**
     * 根据docsRid得到document详细信息
     * @param docsRid Long
     * @return DtoDocumentDetail
     * @throws Exception
     */
    public DtoDocumentDetail getDocsDetail(Long docsRid) throws Exception;
}
