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
import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.common.user.DtoUserBase;
import c2s.essp.timesheet.outwork.DtoUser;
import client.essp.common.humanAllocate.HrAllocate;
import client.essp.common.view.VWTableWorkArea;
import client.framework.common.Constant;
import client.framework.model.VMColumnConfig;
import client.framework.view.common.comMSG;
import client.framework.view.vwcomp.VWJText;

/**
 * �����ˆT���޾S�o
 * @author baohuitu
 *
 */
public class VwUserList extends VWTableWorkArea {

        private final static String actionIdGetUserInfo = "FTSLoadUserInfo";
        private final static String actionIdAddUserInfo = "FTSAddUserInfo";
        private final static String actionIdDelUserPri = "FTSDelPrivilege";
        
        private JButton btnUser = null;
        public JButton btnDel = null;
        private String userLoginIds = "";
        private List userList;
    
        public VwUserList() {
            try {
                jbInit();
            } catch (Exception e) {
                e.printStackTrace();
            }
            addUICEvent();
        }
    
        /**
         * ��ʼ��UI
         * @throws Exception
         */
        public void jbInit() throws Exception {
            Object[][] configs = new Object[][] { {
                                 "rsid.common.loginId", "loginId",
                                 VMColumnConfig.UNEDITABLE, new VWJText()},
                                 {"rsid.common.name", "loginName",
                                 VMColumnConfig.UNEDITABLE, new VWJText()},
                                 {"rsid.common.role", "role",
                                 VMColumnConfig.UNEDITABLE, new VWJText()},
            };
            super.jbInit(configs, DtoUserBase.class);
    //      ������
            this.getTable().setSortable(true);
        }
    
        /**
         * ע��UI�¼�
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
           btnDel.setToolTipText("rsid.common.delete");
        }
        
        /**
         * �h��
         */
        public void actionPerformedDelete(){
                int op = comMSG.dispConfirmDialog("error.client.VwRmMaint.confirmDel");
                if(op != Constant.OK) {
                    return;
                }
                DtoUser dtoUser = (DtoUser) this.getTable().getSelectedData();
                InputInfo inputInfo = new InputInfo();
                inputInfo.setActionId(actionIdDelUserPri);
                inputInfo.setInputObj(DtoUser.DTO_LOGIN_ID, 
                        dtoUser.getLoginId());
                ReturnInfo returnInfo = accessData(inputInfo);
                if(!returnInfo.isError()) {
                    this.getTable().deleteRow();
                    userList.remove(dtoUser);
                }
        }
    
        /**
         * ��ȡҪ����Ȩ�޵��û�
         */
        private void actionPerformedUser() {
            HrAllocate hrAllocate = new HrAllocate();
            hrAllocate.setTitle("Get user for project query privilege");
            hrAllocate.allocMultiple();
            userLoginIds = hrAllocate.getNewUserIds();
            addUser();
        }
        
        /**
         * ��ѡ�е�Ա����ӵ��б���
         *
         */
        private void addUser(){
            InputInfo inputInfo = new InputInfo();
            inputInfo.setActionId(this.actionIdAddUserInfo);
            inputInfo.setInputObj(DtoUser.DTO_USER_LOGINS, userLoginIds);
            inputInfo.setInputObj(DtoUser.DTO_USER_LIST, userList);
            ReturnInfo returnInfo = accessData(inputInfo);
            if (returnInfo.isError() == false) {
                userList = (List) returnInfo.getReturnObj(DtoUser.DTO_USER_LIST);
            }
            if(userList != null && userList.size() > 0){
              this.getTable().setRows(userList);
              this.getTable().setSelectRow(this.getTable().getRowCount() - 1);
              btnDel.setEnabled(true);
            }
        }
    
        /**
         * ���ݲ���
         * @param param Parameter
         */
        public void setParameter(Parameter param) {
            super.setParameter(param);
        }
    
        /**
         * ˢ�½���
         */
        protected void resetUI() {
            InputInfo inputInfo = new InputInfo();
            inputInfo.setActionId(this.actionIdGetUserInfo);
            ReturnInfo returnInfo = accessData(inputInfo);
            if(returnInfo.isError() == false) {
                userList = (List) returnInfo.getReturnObj(DtoUser.DTO_USER_LIST);
            }
            this.getTable().setRows(userList);
        }
}
