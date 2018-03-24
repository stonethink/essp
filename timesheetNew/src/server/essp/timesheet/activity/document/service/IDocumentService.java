package server.essp.timesheet.activity.document.service;

import java.util.List;
import c2s.essp.timesheet.activity.DtoDocumentDetail;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author not attributable
 * @version 1.0
 */
public interface IDocumentService {
    /**
     * ����activityRid�õ�Document��¼
     * @param activityRid Long
     * @return List
     * @throws Exception
     */
   public List getDocumentList(Long activityRid);
     /**
      * ����docsRid�õ�document��ϸ��Ϣ
      * @param docsRid Long
      * @return DtoDocumentDetail
      * @throws Exception
      */
   public DtoDocumentDetail getDocDetail(Long docRid);
}
