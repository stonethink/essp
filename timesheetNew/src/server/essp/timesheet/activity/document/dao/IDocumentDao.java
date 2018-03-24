package server.essp.timesheet.activity.document.dao;

import java.util.List;
import c2s.essp.timesheet.activity.DtoDocumentDetail;

/**
 * <p>Description: DocumentDao�Ľӿ�</p>
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
     * ����activityRid�õ�Document��¼
     * @param activityRid Long
     * @return List
     * @throws Exception
     */
    public List getDocumentList(Long activityRid) throws Exception;
    /**
     * ����docsRid�õ�document��ϸ��Ϣ
     * @param docsRid Long
     * @return DtoDocumentDetail
     * @throws Exception
     */
    public DtoDocumentDetail getDocsDetail(Long docsRid) throws Exception;
}
