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
        //�ж��޸ĵ��У��������Ԥ�ƿ�ʼʱ���Ԥ�ƽ���ʱ������ø�����ֵ����
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
            //�ж������ʱ���Ƿ�ǰ����
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
            //�ж������ʱ���Ƿ�ǰ����
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
                    //�����WBS���Ϳ�����û����,�������ܱ༭
                    List l = ((ITreeNode) node).children();
                    if (l != null && l.size() > 0) {
                        bRowEditable = false;
                    } else {
                        bRowEditable = true;
                    }
                }
            }

            if (columnConfig.getDataName().equals("completeRate")) {
                //����깤�����ļ��㷽�����ֶ��Ĳ������
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

    //����һ���ڵ���ӽڵ����Ƿ���WBS
    //���ж������һ�㼴��
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

    //����һ���ڵ����Ƿ����Activity
    //���ж��������еĽڵ�
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
