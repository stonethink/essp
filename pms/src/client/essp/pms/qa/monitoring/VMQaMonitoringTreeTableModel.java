package client.essp.pms.qa.monitoring;

import java.util.List;

import c2s.dto.ITreeNode;
import c2s.essp.common.code.DtoKey;
import c2s.essp.pms.qa.DtoQaCheckAction;
import c2s.essp.pms.qa.DtoQaCheckPoint;
import c2s.essp.pms.wbs.DtoQaMonitoring;
import c2s.essp.pms.wbs.DtoWbsActivity;
import client.essp.pms.wbs.process.checklist.VwCheckActionList;
import client.framework.model.VMColumnConfig;
import client.framework.model.VMTreeTableModel;
import java.util.Date;

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
public class VMQaMonitoringTreeTableModel extends VMTreeTableModel {
    public VMQaMonitoringTreeTableModel(ITreeNode root) {
        super(root);
    }

    public VMQaMonitoringTreeTableModel(ITreeNode root, Object[][] configs) {
        super(root, configs);
    }

    public VMQaMonitoringTreeTableModel(ITreeNode root, Object[][] configs,
                                        String treeColumnName) {
        super(root, configs, treeColumnName);
    }

    public boolean isCellEditable(Object node, int columnIndex) {
        setTreeColumnEditable(false);
        VMColumnConfig columnConfig = (VMColumnConfig)this.getColumnConfigs().
                                      get(columnIndex);
        ITreeNode tNode = (ITreeNode) node;
        DtoQaMonitoring dto = (DtoQaMonitoring) tNode.getDataBean();
        boolean bColumnEditable = columnConfig.getEditable();
        boolean bRowEditable = true;
        if (DtoKey.TYPE_WBS.endsWith(dto.getType()) ||
            DtoKey.TYPE_ACTIVITY.equals(dto.getType())) {
            if ("Name".equals(columnConfig.getName()) == false) {
                bRowEditable = false;
            }
        } else if (DtoQaCheckPoint.DTO_PMS_CHECK_POINT_TYPE.equals(dto.getType())) {
//            ITreeNode pNode = tNode.getParent();
//            DtoQaMonitoring pDto = (DtoQaMonitoring) pNode.getDataBean();
//            if(pDto.getDtoWbsActivity().isReadonly()) {
//                return false;
//            }
//            if ("Name".equals(columnConfig.getName())) {
//                setTreeColumnEditable(true);
//                return true;
//            }
//            if ("Date".equals(columnConfig.getName())
//                || "Performer".equals(columnConfig.getName())
//                || "Actual Date".equals(columnConfig.getName())
//                || "Result".equals(columnConfig.getName())
//                || "NCR NO".equals(columnConfig.getName())) {
//                bRowEditable = false;
//            }
            return false;
        } else if (DtoQaCheckAction.DTO_PMS_CHECK_ACTION_TYPE.equals(dto.
            getType())) {
            ITreeNode gpNode = tNode.getParent();
            DtoQaMonitoring gpDto = (DtoQaMonitoring) gpNode.getDataBean();
            if (gpDto.getDtoWbsActivity().isReadonly()) {
                return false;
            }
//            if ("Name".equals(columnConfig.getName())) {
//                setTreeColumnEditable(true);
//                return true;
//            }
            if ("Plan Date".equals(columnConfig.getName())) {
                if (VwCheckActionList.chkActionOccasion[4].equals(dto.getOccasion())) {
                    bRowEditable = true;
                } else {
                    bRowEditable = false;
                }
            } else if ("Name".equals(columnConfig.getName())
                       || "Code".equals(columnConfig.getName())
                       || "Method".equals(columnConfig.getName())) {
                bRowEditable = false;
            } else {
                bRowEditable = true;
            }
        }
        return (bColumnEditable && bRowEditable);
    }
     public void setValueAt(Object aValue, Object node, int column) {
         super.setValueAt(aValue, node, column);
         List columnConfigs = getColumnConfigs();
         VMColumnConfig columnConfig = (VMColumnConfig) columnConfigs.get(
                    column);
        String setName = columnConfig.getDataName(); //columnConfig.getName();//ÁÐµÄÃû×Ö

        //if occasion is plan/actual start/finish, get parent plan/actual start/finish date
        if("occasion".equals(setName)) {
            ITreeNode nodeData = (ITreeNode) node;
            DtoQaMonitoring pDto = (DtoQaMonitoring) nodeData.getParent().getDataBean();
            DtoWbsActivity pData = pDto.getDtoWbsActivity();
            int dataColumn = columnConfigs.indexOf(this.getColumnConfig("Plan Date"));
            Date planDate = VwCheckActionList.getQaCheckAtionPlanDate(aValue.toString(),pData);
            if(planDate != null) {
                super.setValueAt(planDate, node, dataColumn);
            }
        }

     }
}
