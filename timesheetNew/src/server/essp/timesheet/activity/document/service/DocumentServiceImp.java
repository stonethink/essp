package server.essp.timesheet.activity.document.service;

import java.util.List;

import c2s.essp.timesheet.activity.DtoDocumentDetail;
import server.essp.timesheet.activity.document.dao.IDocumentDao;
import server.framework.common.BusinessException;

/**
 * <p>Description: DocumentService��ʵ��</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author not attributable
 * @version 1.0
 */
public class DocumentServiceImp implements IDocumentService {
    private IDocumentDao idocumentDao;
    /**
    * ����activityRid�õ�Document��¼
    * @param activityRid Long
    * @return List
    * @throws Exception
    */
    public List getDocumentList(Long activityRid) {
    List docList = null;
    try {
        docList = idocumentDao.getDocumentList(activityRid);
    } catch (Exception ex) {
        throw new BusinessException("error.logic.DocumentServiceImp.loadDocListErr",
                                        "load document list error", ex);
    }
       return docList;
    }

    /**
     * ����docsRid�õ�document��ϸ��Ϣ
     * @param docsRid Long
     * @return DtoDocumentDetail
     * @throws Exception
     */
    public DtoDocumentDetail getDocDetail(Long docRid) {
        DtoDocumentDetail docDetail = new DtoDocumentDetail();
        try{
            docDetail = idocumentDao.getDocsDetail(docRid);
        }catch (Exception ex){
              throw new BusinessException("error.logic.DocumentServiceImp.loadDocDetailErr",
                                              "get document detail error", ex);
        }
     return docDetail;
  }


    public void setIdocumentDao(IDocumentDao idocumentDao) {
        this.idocumentDao = idocumentDao;
    }


}
