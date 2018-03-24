package server.essp.pms.modifyplan.logic;

import java.util.List;

import c2s.dto.ITreeNode;
import c2s.essp.pms.wbs.DtoWbsActivity;
import c2s.essp.pms.wbs.DtoWbsTreeNode;
import server.essp.framework.logic.AbstractESSPLogic;
import server.essp.pms.activity.logic.ActivityNodeCopy;
import server.essp.pms.wbs.logic.WbsNodeCopy;
import server.framework.common.BusinessException;

public class LgAppendNode extends AbstractESSPLogic {
    private ActivityNodeCopy activityCopy;
    private WbsNodeCopy wbsCopy;

    public LgAppendNode() {
        activityCopy = new LgActivityNodeCopy();
        wbsCopy = new LgWbsNodeCopy();
    }

    public LgAppendNode(ActivityNodeCopy activityCopy, WbsNodeCopy wbsCopy) {
        this.activityCopy = activityCopy;
        this.wbsCopy = wbsCopy;
    }

    /**
     * ��srcNode���ӵ�descNode��
     * ���Ƶ����ݰ���srcNode������srcNode���ӣ��������й�������Ϣ
     * @param srcNode ITreeNode
     * @param descNode ITreeNode
     */
    public ITreeNode appendNode(ITreeNode srcNode, ITreeNode descNode) {
        DtoWbsTreeNode addedFirstNode = null; //��¼���ӵĵ�һ���ڵ�
        ITreeNode addedLastNode = null; //ʼ��ָ�����һ���ڵ�
        if (descNode == null) {
            throw new BusinessException("copy.ITreeNode.error ",
                                        "descNode is null!");
        }
        if (srcNode == null) {
            return null;
        }

        addedLastNode = new DtoWbsTreeNode(null);

        DtoWbsActivity srcData = (DtoWbsActivity) srcNode.getDataBean();
        DtoWbsActivity parentData = (DtoWbsActivity) descNode.getDataBean();

        if (srcData.isActivity()) {
            DtoWbsActivity desDate = activityCopy.copyActivity(srcData,
                parentData);
            addedLastNode.setDataBean(desDate);
        } else {
            DtoWbsActivity desDate = wbsCopy.copyWBS(srcData,
                parentData);
            addedLastNode.setDataBean(desDate);
        }

        addedLastNode.setParent(descNode);
        addedFirstNode = (DtoWbsTreeNode) addedLastNode; //�õ����Ƶĵ�һ���ڵ�

        //���ƺ���Ľڵ�
        List child = srcNode.children();
        for (int i = 0; i < child.size(); i++) {
            ITreeNode node = (ITreeNode) child.get(i);
            appendChildrenNode(node, addedLastNode, addedFirstNode);
        }
        return addedFirstNode;
    }

    private void appendChildrenNode(ITreeNode srcNode, ITreeNode descNode,
                                    DtoWbsTreeNode parentNode) {
        if (descNode == null) {
            throw new BusinessException("copy.ITreeNode.error ",
                                        "descNode is null!");
        }
        if (srcNode == null) {
            return;
        }
        ITreeNode copyNode = null;
        if (copyNode == null) {
            copyNode = new DtoWbsTreeNode(null);
        }
        DtoWbsActivity srcData = (DtoWbsActivity) srcNode.getDataBean();
        DtoWbsActivity parentData = (DtoWbsActivity) descNode.getDataBean();
        DtoWbsActivity desDate;
        if (srcData.isActivity()) {
            desDate = activityCopy.copyActivity(srcData,
                                                parentData);
            copyNode.setDataBean(desDate);
        } else {
            desDate = wbsCopy.copyWBS(srcData,
                                      parentData);
            copyNode.setDataBean(desDate);

        }

        copyNode.setParent(descNode);
        DtoWbsTreeNode parent = new DtoWbsTreeNode(desDate);
        parentNode.addChild(parent);
        List child = srcNode.children();
        for (int i = 0; i < child.size(); i++) {
            ITreeNode node = (ITreeNode) child.get(i);
            appendChildrenNode(node, copyNode, parent);
        }
    }

}
