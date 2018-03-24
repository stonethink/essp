package server.essp.common.jspTreeTable;

import itf.treeTable.ITreeTable;
import c2s.essp.common.treeTable.TreeTableConfig;
import itf.treeTable.INode;
import c2s.dto.ITreeNode;
import c2s.dto.TransactionData;
import c2s.dto.DtoUtil;

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
public class JspTreeTable implements ITreeTable {
    public JspTreeTable() {
    }

    private int iRowCount = 0;

    public String createTreeTable(TreeTableConfig[] treeTableConfig,
                                  Class className , TransactionData data) {

        String retStr = "root  = addRoot(companyGif, '','');";

        try {
            INode iNode = (INode) className.newInstance();

            ITreeNode iTreeNode = iNode.createNodes(data);

            retStr = retStr + createRows(treeTableConfig,iTreeNode);

        } catch (IllegalAccessException ex) {
            return "";
        } catch (InstantiationException ex) {
            return "";
        }
        return retStr;
    }

    public String createRows( TreeTableConfig[] treeTableConfig,ITreeNode iTreeNode){
        String retStr = "";
        String tmpContent = "contents" + iRowCount;
        String tmpContentDef = "var " + tmpContent + " = new Array();\r\n";
        retStr = retStr + tmpContentDef;

        Object tmpData = iTreeNode.getDataBean();

        //增加单元格
        for (int i = 0; i < treeTableConfig.length; i++) {

            String tmpRange = " range" + iRowCount + i;
            String tmpRangeDef = "var " + tmpRange;
            String tmpRangeValue = createColumn(treeTableConfig[i],tmpData);

            String tmpRangeDisp = tmpRangeDef + " = " + tmpRangeValue;
            String tmpContentValue = tmpContent + "[" + i + "] =" + tmpRange + ";\r\n";

            retStr = retStr + tmpRangeDisp;
            retStr = retStr + tmpContentValue;
        }

        //将行加入
        String tmpRow = "";
        if ( iTreeNode.getParent() == null){
            tmpRow = tmpData.toString() + " = addItem( root , rootGif , " +
                     tmpContent + " ,  '' ); \r\n";
        } else {
            String tmpParentRow = iTreeNode.getParent().toString();
            tmpRow = tmpData.toString() + " = addItem( " + tmpParentRow + " , "
                     + " funcGif , " + tmpContent + " ,  '' ); \r\n";
        }

        retStr = retStr + tmpRow;
        iRowCount ++;
        //以上增加完成一行数据，后面将增加下面行

        for ( int i = 0 ; i < iTreeNode.getChildCount() ; i++ ){
            retStr = retStr + createRows(treeTableConfig,iTreeNode.getChildAt(i));
        }

        return retStr;
    }

    public String createColumn( TreeTableConfig treeTableConfig , Object obj) {

        String retStr = "new Range(";

        try {
            String dispName = (String) DtoUtil.getProperty(obj, treeTableConfig.getName());

            retStr = retStr + "'" + dispName + "' , ";

            if ( treeTableConfig.isHerf() ) {
                retStr = retStr + " true , ''  ," ;
            } else {
                retStr = retStr + " false , '' ,";
            }

            retStr = retStr + " '" + treeTableConfig.getStyle() + "' , ";

            if ( treeTableConfig.isTreeColumn() ) {
                retStr = retStr + " 1 );";
            } else {
                retStr = retStr + " 0 );";
            }

            retStr = retStr + "\r\n";

        } catch (Exception ex) {
            return "";
        }


        return retStr;

    }



}
