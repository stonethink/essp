package client.essp.timesheet.admin.code.relation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;

import c2s.dto.ITreeNode;
import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.timesheet.code.DtoCodeType;
import c2s.essp.timesheet.code.DtoCodeValue;
import client.essp.common.view.VWTreeTableWorkArea;
import client.framework.model.VMColumnConfig;
import client.framework.view.common.comMSG;
import client.framework.view.vwcomp.VWJCheckBox;
import com.wits.util.Parameter;
import client.framework.view.vwcomp.VWJText;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public class VwCodeRelationList extends VWTreeTableWorkArea {

    private static final String actionId_List = "FTSListCodeRelation";
    private static final String actionId_Save = "FTSSaveCodeRelation";
   
    private static final String treeColumnName = "name";
    JButton saveBtn;
    JButton refreshBth;
    private VWJCheckBox vwEnableCheckBox = new VWJCheckBox();
    private Long codeTypeRid;
    private boolean isLeaveType = false;
    public VwCodeRelationList() {
        Object[][] configs = { {"rsid.common.name", "name", VMColumnConfig.EDITABLE, null}
                             , {"rsid.common.description", "description",
                             VMColumnConfig.UNEDITABLE, new VWJText()}
                             , {"rsid.common.enable", "isEnable", VMColumnConfig.EDITABLE,
                             vwEnableCheckBox}
        };

        try {
            super.jbInit(configs, treeColumnName, DtoCodeValue.class);

        } catch (Exception e) {
            log.error(e);
        }
        addUICEvent();
    }

    /**
     * �����¼�
     */
    private void addUICEvent() {

        //����
       saveBtn = this.getButtonPanel().addSaveButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedSave();
            }
        });
       saveBtn.setToolTipText("rsid.common.save");

        //ˢ��
        refreshBth = this.getButtonPanel().addLoadButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedLoad();
            }
        });
        refreshBth.setToolTipText("rsid.common.refresh");
        //���Enable Check Box
        vwEnableCheckBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                processdEnableCheck(((VWJCheckBox)e.getSource()).isSelected());
            }
        });
    }

    /**
     * ��������
     */
    private void actionPerformedSave() {
        InputInfo inputInfo = new InputInfo();
        inputInfo.setInputObj(DtoCodeType.DTO_RID, codeTypeRid);
        inputInfo.setInputObj(DtoCodeType.DTO_IS_LEAVE_TYPE, isLeaveType);
        inputInfo.setInputObj(DtoCodeValue.DTO_TREE, this.getModel().getRoot());
        inputInfo.setActionId(this.actionId_Save);
        ReturnInfo returnInfo = this.accessData(inputInfo);
        if(returnInfo.isError() == false) {
        	comMSG.dispMessageDialog("rsid.common.saveComplete");
        }
    }

    /**
     * ִ��ˢ��
     * @param e ActionEvent
     */
    private void actionPerformedLoad() {
        resetUI();
    }

    /**
     * ����Enaboleѡ���¼�
     */
    private void processdEnableCheck(boolean isCheck) {
        if(isCheck) {
            checkOnChildren(this.getSelectedNode());
            checkOnParent(this.getSelectedNode());
        } else {
            unCheckOnChildren(this.getSelectedNode());
        }
        this.getTreeTable().refreshTree();
    }

    /**
     * ѡ����������ڵ�
     * @param node ITreeNode
     */
    private void checkOnChildren(ITreeNode node) {
        DtoCodeValue dto = (DtoCodeValue) node.getDataBean();
        dto.setIsEnable(true);
        List<ITreeNode> children = node.children();
        for(ITreeNode child : children) {
            checkOnChildren(child);
        }
    }

    /**
     * ȡ��ѡ����������ڵ�
     * @param node ITreeNode
     */
    private void unCheckOnChildren(ITreeNode node) {
        DtoCodeValue dto = (DtoCodeValue) node.getDataBean();
        dto.setIsEnable(false);
        List<ITreeNode> children = node.children();
        for(ITreeNode child : children) {
            unCheckOnChildren(child);
        }
    }


    /**
     * ѡ���������Ƚڵ�
     * @param node ITreeNode
     */
    private void checkOnParent(ITreeNode node) {
        DtoCodeValue dto = (DtoCodeValue) node.getDataBean();
        dto.setIsEnable(true);
        ITreeNode parent = node.getParent();
        if(parent != null) {
            checkOnParent(parent);
        }
    }

    /**
     * ��������������ˢ��
     * @param param Parameter
     */
    public void setParameter(Parameter param) {
        super.setParameter(param);
        DtoCodeType dto = (DtoCodeType) param.get(DtoCodeType.DTO);
        if(dto != null) {
            codeTypeRid = dto.getRid();
        } else {
            codeTypeRid = null;
        }
        isLeaveType = (Boolean) param.get(DtoCodeType.DTO_IS_LEAVE_TYPE);
    }

    /**
     * ҳ��ˢ��
     */
    protected void resetUI() {
        if(codeTypeRid == null) {
            this.getModel().setRoot(null);
            return;
        }
        InputInfo inputInfo = new InputInfo();
        inputInfo.setInputObj(DtoCodeType.DTO_RID, codeTypeRid);
        inputInfo.setActionId(this.actionId_List);
        inputInfo.setInputObj(DtoCodeType.DTO_IS_LEAVE_TYPE, isLeaveType);
        ReturnInfo returnInfo = accessData(inputInfo);
        ITreeNode root = (ITreeNode) returnInfo.getReturnObj(DtoCodeValue.
                DTO_TREE);
        this.getModel().setRoot(root);
        this.getTreeTable().setSelectedRow(root);
        this.getTreeTable().expandRow(0);
    }
}