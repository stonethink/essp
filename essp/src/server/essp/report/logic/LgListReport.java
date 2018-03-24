package server.essp.report.logic;

import c2s.dto.ITreeNode;
import java.util.List;
import server.essp.framework.logic.AbstractESSPLogic;
import essp.tables.EsspSysReport;
import c2s.dto.DtoUtil;
import c2s.essp.report.DtoSysReport;
import c2s.dto.DtoTreeNode;
import itf.treeTable.INode;
import c2s.dto.TransactionData;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class LgListReport  extends AbstractESSPLogic implements INode {
    public LgListReport() {
    }

    public ITreeNode getReportByID(String id) throws Exception {
        List lSysReports = getReportListByID(id);
        DtoTreeNode dtTree = null;
        DtoSysReport rootReport = null;
        for ( int i = 0 ; i < lSysReports.size() ; i ++ ){
           if ( i == 0) {
               rootReport = (DtoSysReport) lSysReports.get(i);
               dtTree = new DtoTreeNode(rootReport);
           } else {
               if ( ((DtoSysReport) lSysReports.get(i)).getUpId().equals(rootReport.getId())){
                   DtoTreeNode tmpDtoTree = new DtoTreeNode((DtoSysReport) lSysReports.get(i) );
                   dtTree.addChild(tmpDtoTree);
               }else{
                   insertReport((DtoSysReport) lSysReports.get(i), dtTree);
               }
           }
        }
        return dtTree;
    }

    public List getReportListByID(String id) throws Exception {
        String sql = "SELECT * FROM ESSP_SYS_REPORT_T t  "
                   + " START WITH T.ID = '" + id + "' CONNECT BY PRIOR T.ID = T.UP_ID "
                   + " ORDER BY CODE";

        List lReportSys = this.getDbAccessor().executeQueryToList(sql,EsspSysReport.class);

        List lDtoReportSys = DtoUtil.list2List(lReportSys,DtoSysReport.class);

        return lDtoReportSys;
    }

    private boolean insertReport(DtoSysReport dtoSysReport , ITreeNode iTreeNode){
          for ( int i = 0 ; i < iTreeNode.getChildCount() ; i ++ ){
              DtoSysReport dtotemp = (DtoSysReport)iTreeNode.getChildAt(i).getDataBean();
              if ( dtoSysReport.getUpId().equals(dtotemp.getId())){
                  DtoTreeNode dtoTree = new DtoTreeNode(dtoSysReport);
                  iTreeNode.getChildAt(i).addChild(dtoTree);
                  return true;
              } else {
                 if ( insertReport(dtoSysReport,iTreeNode.getChildAt(i)) ){
                     return true;
                 }
              }
          }

          return false;
    }

    public ITreeNode createNodes(TransactionData data) {
        String reportID = data.getInputInfo().getInputObj("ReportID").toString();
        try {
            return getReportByID(reportID);
        } catch (Exception ex) {
            return null;
        }
    }

}
