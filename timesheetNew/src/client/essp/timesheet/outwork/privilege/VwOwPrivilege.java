/*
 * Created on 2008-5-19
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package client.essp.timesheet.outwork.privilege;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import com.wits.util.Parameter;
import c2s.essp.timesheet.outwork.DtoUser;
import client.essp.common.view.VWTDWorkArea;
/**
 * �����ˆT���޾S�o
 * @author baohuitu
 *
 */
public class VwOwPrivilege extends VWTDWorkArea {

        private VwUserList vwUserList;
        private VwPrivilegeList vwPrivilegeList;
    
        public VwOwPrivilege() {
            super(300);
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
            vwUserList = new VwUserList();
            vwPrivilegeList = new VwPrivilegeList();
            
            this.getTopArea().addTab("rsid.common.user", vwUserList);
            this.getDownArea().addTab("rsid.common.privilege", vwPrivilegeList);
        }
    
        /**
         * ע��UI�¼�
         */
        private void addUICEvent() {
            vwUserList.getTable().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                public void valueChanged(ListSelectionEvent e) {
                    processSelectedUser();
                }
            });
            
        }
    
        /**
         * ѡ��һ���û���ˢ���������Ȩ�б�
         */
        private void processSelectedUser() {
            DtoUser user = (DtoUser) vwUserList.getTable().getSelectedData();
            String loginId = null;
            if(user != null) {
                loginId = user.getLoginId();
                vwUserList.btnDel.setEnabled(true);
            }else{
                vwUserList.btnDel.setEnabled(false);
            }
            Parameter param = new Parameter();
            param.put(DtoUser.DTO_LOGIN_ID, loginId);
            vwPrivilegeList.setParameter(param);
            vwPrivilegeList.refreshWorkArea();
        }
    
        /**
         * ���ݲ�����vwUserList
         * @param parameter Parameter
         */
        public void setParameter(Parameter parameter) {
            vwUserList.setParameter(parameter);
        }
    
        /**
         * ����ˢ���¼���vwUserList
         */
        public void refreshWorkArea() {
            vwUserList.refreshWorkArea();
        }


}
