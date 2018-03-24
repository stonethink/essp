/*
 * Created on 2008-5-19
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package client.essp.timesheet.outwork.privilege;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import com.wits.util.Parameter;
import client.essp.common.view.VWTreeTableWorkArea;
import client.framework.model.VMColumnConfig;
import client.framework.view.common.comMSG;
import client.framework.view.vwcomp.VWJCheckBox;
import c2s.dto.ITreeNode;
import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.timesheet.outwork.DtoPrivilege;
import c2s.essp.timesheet.outwork.DtoUser;

/**
 * 外派人員權限維護
 * @author baohuitu
 *
 */
public class VwPrivilegeList extends VWTreeTableWorkArea {

        private final static String actionIdLoadPrivilege = "FTSLoadPrivilegeList";
        private final static String actionIdSavePrivilege = "FTSSaveQueryPrivilege";
        
        public JButton btnSave = null;
        public JButton btnRefresh = null;
        private String loginId;
        private ITreeNode root;
        private VWJCheckBox chkOrgPrivilege;
    
        public VwPrivilegeList() {
            try {
                jbInit();
            } catch (Exception e) {
                e.printStackTrace();
            }
            addUICEvent();
        }
        
        /**
         * 初始化
         * @throws Exception
         */
        public void jbInit() throws Exception {
            chkOrgPrivilege = new VWJCheckBox();
            Object[][] configs = new Object[][] { {
                                         "rsid.timesheet.unit", "accountName",
                                         VMColumnConfig.EDITABLE, null},
                                         {"rsid.timesheet.privilege", "isPrivilege",
                                         VMColumnConfig.EDITABLE, chkOrgPrivilege},
                    };
            super.jbInit(configs, "accountName", DtoPrivilege.class);
        }
        
        /**
         * 注册UI事件
         */
        private void addUICEvent() {
            //保存
            btnSave = this.getButtonPanel().addSaveButton(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    actionPerformedSave();
                }
            });
            btnSave.setToolTipText("rsid.common.save");
            
            //刷新
            btnRefresh =this.getButtonPanel().addLoadButton(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    resetUI();
                }
            });
            btnRefresh.setToolTipText("rsid.common.refresh");
    
            //選擇組織
            chkOrgPrivilege.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    VWJCheckBox chk = (VWJCheckBox)e.getSource();
                    checkOnChildren(getSelectedNode(),chk.isSelected());
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
            inputInfo.setInputObj(DtoUser.DTO_LOGIN_ID, loginId);
            inputInfo.setInputObj(DtoUser.DTO_TREE_NODE, root);
            ReturnInfo returnInfo = accessData(inputInfo);
            if (returnInfo.isError() == false) {
                comMSG.dispMessageDialog("rsid.common.saveComplete");
            }
        }
    
        /**
         * 连动所有子孙节点
         * @param node ITreeNode
         */
        private void checkOnChildren(ITreeNode node,  boolean flag) {
                DtoPrivilege dto =  (DtoPrivilege)node.getDataBean();
                    dto.setIsPrivilege(flag);
                List<ITreeNode> children = node.children();
                for(ITreeNode child : children) {
                    checkOnChildren(child, flag);
                }
        }
    
    
        /**
         * 传递参数
         * @param param Parameter
         */
        public void setParameter(Parameter param) {
            loginId = (String) param.get(DtoUser.DTO_LOGIN_ID);
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
                inputInfo.setInputObj(DtoUser.DTO_LOGIN_ID, loginId);
                ReturnInfo returnInfo = accessData(inputInfo);
                if(returnInfo.isError() == false) {
                    root = (ITreeNode) returnInfo.getReturnObj(DtoUser.DTO_TREE_NODE);
                }
                if(root != null){
                  this.getTreeTable().setRoot(root);
                  this.getTreeTable().expandRow(0);
                  btnSave.setEnabled(true);
                  btnRefresh.setEnabled(true);
                }else {
                  btnSave.setEnabled(false);
                  btnRefresh.setEnabled(false);
                }
            }
}
