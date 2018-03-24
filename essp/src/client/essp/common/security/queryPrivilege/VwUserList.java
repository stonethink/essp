package client.essp.common.security.queryPrivilege;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.common.user.DtoUserBase;
import client.essp.common.humanAllocate.HrAllocate;
import client.essp.common.view.VWTableWorkArea;
import client.framework.common.Constant;
import client.framework.model.VMColumnConfig;
import client.framework.view.common.comMSG;
import client.framework.view.vwcomp.VWJText;
import com.wits.util.Parameter;

/**
 * <p>Title: VwUserList</p>
 *
 * <p>Description: 项目查询授权，用户列表</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public class VwUserList extends VWTableWorkArea {

        private final static String actionIdGetUserInfo = "F00GetUserInfo";
        private final static String actionIdDelUserPri = "F00DelPrivilege";
        private JButton btnUser = null;
        private String userLoginIds = "";
        private List userList;
        private JButton btnDel = null;
    
        public VwUserList() {
            try {
                jbInit();
            } catch (Exception e) {
                e.printStackTrace();
            }
            addUICEvent();
        }
    
        /**
         * 初始化UI
         * @throws Exception
         */
        public void jbInit() throws Exception {
            Object[][] configs = new Object[][] { {
                                 "rsid.common.loginId", "userLoginId",
                                 VMColumnConfig.UNEDITABLE, new VWJText()},
                                 {"rsid.common.name", "userName",
                                 VMColumnConfig.UNEDITABLE, new VWJText()},
                                 {"rsid.common.role", "userType",
                                 VMColumnConfig.UNEDITABLE, new VWJText()},
            };
            super.jbInit(configs, DtoUserBase.class);
        }
    
        /**
         * 注册UI事件
         */
        private void addUICEvent() {
            btnUser = this.getButtonPanel().addButton("humanAllocate.gif");
            btnUser.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    actionPerformedUser();
                }
            });
            btnUser.setToolTipText("rsid.common.allocate");
            
            btnDel = getButtonPanel().addButton("del.png");
            btnDel.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    actionPerformedDelete();
                }
            });
            btnDel.setEnabled(false);
            btnDel.setToolTipText("rsid.common.delete");
        }
    
        /**
         * 刪除
         */
        public void actionPerformedDelete(){
                int op = comMSG.dispConfirmDialog("error.client.VwRmMaint.confirmDel");
                if(op != Constant.OK) {
                    return;
                }
                DtoUserBase dtoUser = (DtoUserBase) this.getTable().getSelectedData();
                InputInfo inputInfo = new InputInfo();
                inputInfo.setActionId(actionIdDelUserPri);
                inputInfo.setInputObj(DtoUserBase.USER_LOGIN_ID, 
                        dtoUser.getUserLoginId());
                ReturnInfo returnInfo = accessData(inputInfo);
                if(!returnInfo.isError()) {
                    this.getTable().deleteRow();
                    userList.remove(dtoUser);
                }
                if(userList != null && userList.size() > 0){
                    btnDel.setEnabled(true);
                }else{
                    btnDel.setEnabled(false);
                }
        }
        
        /**
         * 获取要分配权限的用户
         */
        public void actionPerformedUser() {
            HrAllocate hrAllocate = new HrAllocate();
            hrAllocate.setTitle("Get user for project query privilege");
            hrAllocate.allocMultiple();
            userLoginIds = hrAllocate.getNewUserIds();
            resetUI();
        }
    
        /**
         * 传递参数
         * @param param Parameter
         */
        public void setParameter(Parameter param) {
            super.setParameter(param);
        }
    
        /**
         * 刷新界面
         */
        protected void resetUI() {
            InputInfo inputInfo = new InputInfo();
            inputInfo.setActionId(this.actionIdGetUserInfo);
            inputInfo.setInputObj("loginIds", userLoginIds);
            ReturnInfo returnInfo = accessData(inputInfo);
            if(returnInfo.isError() == false) {
                userList = (List) returnInfo.getReturnObj("userList");
            }
            if(userList == null || userList.size() > 0){
                this.getTable().setRows(userList);
                if(userLoginIds != null && !"".equals(userLoginIds.trim())){
                  this.getTable().setSelectRow(userList.size()-1);
                }
                btnDel.setEnabled(true);
            }else{
                btnDel.setEnabled(false);
            }
        }
}
