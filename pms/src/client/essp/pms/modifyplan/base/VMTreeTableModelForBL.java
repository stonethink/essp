package client.essp.pms.modifyplan.base;

import client.framework.model.VMTreeTableModel;
import c2s.dto.ITreeNode;
import client.framework.model.VMColumnConfig;
import c2s.essp.pms.wbs.DtoWbsActivity;
import java.util.List;
import java.util.Date;
import client.framework.view.common.comMSG;

public class VMTreeTableModelForBL extends VMTreeTableModel {
    private boolean locked = false;
    public VMTreeTableModelForBL(ITreeNode root,
                                 Object[][] configs,
                                 String treeColumnName) {
        super(root, configs);
        this.setTreeColumnName(treeColumnName);
    }


    public void setValueAt(Object aValue, Object node, int column) {
        if (locked) {
            return;
        }
        String columnName = this.getColumnName(column);
        //判断修改的列，如果不是预计开始时间和预计结束时间则调用父中设值方法
        if (!columnName.equals("Planned Start") &&
            !columnName.equals("Planned Finish")) {
            super.setValueAt(aValue, node, column);
            return;
        }
        ITreeNode currentNode = (ITreeNode) node;
        DtoWbsActivity currentWbs = (DtoWbsActivity) currentNode.getDataBean();
        Date sDate = null;
        Date fDate = null;
        if (columnName.equals("Planned Start")) {
            sDate = (Date) aValue;
            //判断输入的时间是否前后倒置
            if (sDate != null && currentWbs.getPlannedFinish() != null &&
                sDate.after(currentWbs.getPlannedFinish())) {
                locked = true;
                comMSG.dispErrorDialog(
                    "error!!! finish date must be after start date.");
                locked = false;
                return;
            }
        }
        if (columnName.equals("Planned Finish")) {
            fDate = (Date) aValue;
            //判断输入的时间是否前后倒置
            if (fDate != null && currentWbs.getPlannedStart() != null &&
                fDate.before(currentWbs.getPlannedStart())) {
                locked = true;
                comMSG.dispErrorDialog(
                    "error!!! finish date must be after start date.");
                locked = false;
                return;
            }
        }

        super.setValueAt(aValue, node, column);

    }


    public boolean isCellEditable(Object node, int columnIndex) {
        VMColumnConfig columnConfig = (VMColumnConfig)this.getColumnConfigs().
                                      get(columnIndex);
        boolean bColumnEditable = columnConfig.getEditable();
        boolean bRowEditable = true;

        DtoWbsActivity dto = (DtoWbsActivity) ((ITreeNode) node).getDataBean();
        if (columnConfig.getDataName().equals("name")) {
            return true;
        }
        if (dto.isReadonly()) {
            return false;
        } else {
            if (columnConfig.getDataName().equals("plannedStart") ||
                columnConfig.getDataName().equals("plannedFinish")) {
                if (dto.isActivity()) {
                    bRowEditable = true;
                } else {
                    //如果是WBS，就看其有没有子,有子则不能编辑
                    List l = ((ITreeNode) node).children();
                    if (l != null && l.size() > 0) {
                        bRowEditable = false;
                    } else {
                        bRowEditable = true;
                    }
                }
            }

            if (columnConfig.getDataName().equals("completeRate")) {
                //如果完工比例的计算方法是手动的才允许改
                if (dto.getCompleteMethod() != null &&
                    dto.getCompleteMethod().
                    equals(DtoWbsActivity.WBS_COMPLETE_BY_MANUAL)) {
                    bRowEditable = true;
                } else {
                    bRowEditable = false;
                }
            }
        }

        return (bColumnEditable && bRowEditable);
    }

    //返回一个节点的子节点中是否有WBS
    //仅判断下面的一层即可
    public boolean hasWbsChildNode(ITreeNode node) {
        if (!node.isLeaf()) {
            List list = node.listAllChildrenByPostOrder();
            for (int i = 0; i < list.size(); i++) {
                ITreeNode tempNode = (ITreeNode) list.get(i);
                DtoWbsActivity dataBean = (DtoWbsActivity) tempNode.getDataBean();
                if (dataBean.isWbs()) {
                    return true;
                }

            }
        }
        return false;
    }

    //返回一个节点中是否存在Activity
    //需判断下面所有的节点
    public boolean hasActivityChildNode(ITreeNode node) {
        if (node.isLeaf()) {
            DtoWbsActivity dataBean = (DtoWbsActivity) node.getDataBean();
            if (dataBean.isActivity()) {
                return true;
            }
        } else {
            List list = node.listAllChildrenByPostOrder();
            for (int i = 0; i < list.size(); i++) {
                this.hasActivityChildNode((ITreeNode) list.get(i));
            }
        }
        return false;
    }

}
