package client.essp.common.security.queryPrivilege;

import c2s.dto.ITreeNode;
import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.common.security.DtoQueryPrivilege;
import client.essp.common.view.VWTreeTableWorkArea;
import client.framework.model.VMColumnConfig;
import client.framework.view.common.comMSG;
import client.framework.view.vwcomp.VWJCheckBox;
import com.wits.util.Parameter;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.JButton;

/**
 * <p>Title: VwPrivilegeList</p>
 *
 * <p>Description: 项目查询授权列表</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public class VwPrivilegeList extends VWTreeTableWorkArea {

    private final static String actionIdLoadPrivilege = "F00LoadQueryPrivilege";
    private final static String actionIdSavePrivilege = "F00SaveQueryPrivilege";
    private final static String PROPERTY_EXECUTE_UNIT = "executeUnitName";
    private final static String PROPERTY_ORG_PRIVILEGE = "orgQueryFlag";
    private final static String PROPERTY_V_CODE_PRIVILEGE = "vCodeQueryFlag";
    public JButton btnSave = null;
    public JButton btnRefresh = null;
    private String loginId;
    private ITreeNode root;
    private VWJCheckBox chkOrgPrivilege;
    private VWJCheckBox chkVCodePrivilege;

    public VwPrivilegeList() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        addUICEvent();
    }

    public void jbInit() throws Exception {
        chkOrgPrivilege = new VWJCheckBox();
        chkVCodePrivilege = new VWJCheckBox();
        Object[][] configs = new Object[][] { {
                                     "rsid.common.executeUnit", PROPERTY_EXECUTE_UNIT,
                                     VMColumnConfig.EDITABLE, null},
                                     {"rsid.common.unitQueryPrivilege", PROPERTY_ORG_PRIVILEGE,
                                     VMColumnConfig.EDITABLE, chkOrgPrivilege},
                                     {"rsid.common.vCodeQueryPrivilege", PROPERTY_V_CODE_PRIVILEGE,
                                     VMColumnConfig.EDITABLE, chkVCodePrivilege},
                };
        super.jbInit(configs, PROPERTY_EXECUTE_UNIT, DtoQueryPrivilege.class);
    }

    private void addUICEvent() {

        btnSave = this.getButtonPanel().addSaveButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedSave();
            }
        });
        btnSave.setToolTipText("rsid.common.save");
        
        btnRefresh =this.getButtonPanel().addLoadButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resetUI();
            }
        });
        btnRefresh.setToolTipText("rsid.common.refresh");

        chkOrgPrivilege.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                VWJCheckBox chk = (VWJCheckBox)e.getSource();
                checkOnChildren(getSelectedNode(),
                                chk.getName(), chk.isSelected());
                getTreeTable().refreshTree();
            }
        });

        chkVCodePrivilege.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                VWJCheckBox chk = (VWJCheckBox)e.getSource();
                checkOnChildren(getSelectedNode(),
                                chk.getName(), chk.isSelected());
                getTreeTable().refreshTree();
            }
        });

    }

    /**
     * 保存授权结果
     */
    private void actionPerformedSave() {
    	Object root = this.getModel().getRoot();
    	if(loginId == null || "".equals(loginId) || root == null) {
    		return;
    	}
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(this.actionIdSavePrivilege);
        inputInfo.setInputObj("loginId", loginId);
        inputInfo.setInputObj("root", root);
        ReturnInfo returnInfo = accessData(inputInfo);
        if (returnInfo.isError() == false) {
        	comMSG.dispMessageDialog("rsid.common.saveComplete");
        }
    }

    /**
     * 连动所有子孙节点
     * @param node ITreeNode
     */
    private void checkOnChildren(ITreeNode node, String property, boolean flag) {
        DtoQueryPrivilege dto =  (DtoQueryPrivilege)node.getDataBean();
        if(PROPERTY_ORG_PRIVILEGE.equals(property)) {
            dto.setOrgQueryFlag(flag);
        } else if(PROPERTY_V_CODE_PRIVILEGE.equals(property)) {
            dto.setVCodeQueryFlag(flag);
        }

        List<ITreeNode> children = node.children();
        for(ITreeNode child : children) {
            checkOnChildren(child, property, flag);
        }
    }


    /**
     * 传递参数
     * @param param Parameter
     */
    public void setParameter(Parameter param) {
        loginId = (String) param.get("loginId");
        super.setParameter(param);
    }

    /**
     * 刷新界面
     */
    protected void resetUI() {
    	if(loginId == null || "".equals(loginId)) {
    		this.getTreeTable().setRoot(null);
            btnSave.setEnabled(false);
            btnRefresh.setEnabled(false);
    		return;
    	}
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(this.actionIdLoadPrivilege);
        inputInfo.setInputObj("loginId", loginId);
        ReturnInfo returnInfo = accessData(inputInfo);
        if(returnInfo.isError() == false) {
            root = (ITreeNode) returnInfo.getReturnObj("root");
        }
        this.getTreeTable().setRoot(root);
        this.getTreeTable().expandRow(0);
        if(root == null){
            btnSave.setEnabled(false);
            btnRefresh.setEnabled(false);
        }else{
            btnSave.setEnabled(true);
            btnRefresh.setEnabled(true);
        }
    }


}
