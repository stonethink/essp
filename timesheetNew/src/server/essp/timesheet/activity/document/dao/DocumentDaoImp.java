package server.essp.timesheet.activity.document.dao;

import server.essp.common.primavera.PrimaveraApiBase;
import java.util.Map;
import java.util.List;
import java.util.HashMap;
import com.primavera.integration.client.bo.BOIterator;
import com.primavera.integration.client.bo.object.Activity;
import com.primavera.common.value.ObjectId;
import c2s.dto.DtoUtil;
import c2s.essp.timesheet.activity.DtoDocument;
import c2s.essp.timesheet.activity.DtoDocumentDetail;
import com.primavera.integration.client.bo.object.Document;
import com.primavera.integration.client.bo.object.ProjectDocument;
import java.util.ArrayList;
import java.util.Date;

/**
 * <p>Description: DocumentDao的实现</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author not attributable
 * @version 1.0
 */
public class DocumentDaoImp  extends PrimaveraApiBase implements
        IDocumentDao {
      private final static Map<String, String> documentPropertiesMap;
      private final static Map<String, String> docsDetailPropertiesMap;
      /**
       * 根据activityRid得到Document记录
       * @param activityRid Long
       * @return List
       * @throws Exception
       */
    public List getDocumentList(Long activityRid) throws Exception {
          Activity activity = Activity.load(getSession(), Activity.getAllFields(),
                                       new ObjectId(activityRid));
          List list = new ArrayList();
          if(activity!=null){
             BOIterator proDocBoi = activity.loadProjectDocuments(new String[]{"DocumentObjectId",
                                           "DocumentTitle","IsWorkProduct"},null,null);
             while(proDocBoi.hasNext()){
                 DtoDocument dtoDoc = new DtoDocument();
                 ProjectDocument proDocument = (ProjectDocument)proDocBoi.next();
                 String strWhere = "ObjectId = " + proDocument.getDocumentObjectId();
                 if(proDocument.getIsWorkProduct()){
                     dtoDoc.setIsWorkProduct("Yes");
                 }
                 dtoDoc.setDocumentRid(proDocument.getDocumentObjectId().hashCodeLong());
                 dtoDoc.setTitle(proDocument.getDocumentTitle());
                 BOIterator boiDocs = getGOM().loadDocuments(new String[] {"ObjectId",
                         "DocumentStatusCodeName"},strWhere,null);
                 if(boiDocs.hasNext()){
                     Document document = (Document)boiDocs.next();
                     dtoDoc.setStatusName(document.getDocumentStatusCodeName());
                 }
                 list.add(dtoDoc);
             }
         }
        return list;
    }

    /**
     * 根据docsRid得到document详细信息
     * @param docsRid Long
     * @return DtoDocumentDetail
     * @throws Exception
     */
    public DtoDocumentDetail getDocsDetail(Long docsRid) throws Exception {
        DtoDocumentDetail dtoDetail = new DtoDocumentDetail();
        Document document = null;
        String strWhere = "ObjectId = " + docsRid;
        BOIterator boiDocs = getGOM().loadDocuments(
                new String[] {"ObjectId", "Author", "RevisionDate","Deliverable",
                "Description", "DocumentStatusCodeName", "PublicLocation",
                "Title", "Version", "ReferenceNumber"}, strWhere, "");
        if (boiDocs.hasNext()) {
            document = (Document) boiDocs.next();
            DtoUtil.copyBeanToBeanWithPropertyMap(dtoDetail,
                                                  document,
                                                  docsDetailPropertiesMap);
            Date revisionDate = null;
            if(document != null && document.getRevisionDate() != null) {
                revisionDate = new Date(document.getRevisionDate().getTime());
            }
            dtoDetail.setRevisionDate(revisionDate);
        }
        return dtoDetail;

    }

    static{
        documentPropertiesMap = new HashMap();
        documentPropertiesMap.put("documentTitle","title");
        documentPropertiesMap.put("documentStatusCodeName","statusName");
        documentPropertiesMap.put("isWorkProduct","isWProduct");
        documentPropertiesMap.put("documentObjectId","documentRid");
        
        docsDetailPropertiesMap = new HashMap();
        docsDetailPropertiesMap.put("author", "author");
        docsDetailPropertiesMap.put("revisionDate", "revisionDate");
        docsDetailPropertiesMap.put("deliverable", "deliverable");
        docsDetailPropertiesMap.put("description", "description");
        docsDetailPropertiesMap.put("documentStatusCodeName", "status");
        docsDetailPropertiesMap.put("publicLocation", "location");
        docsDetailPropertiesMap.put("title", "title");
        docsDetailPropertiesMap.put("version", "version");
        docsDetailPropertiesMap.put("referenceNumber", "refNo");

    }


}
