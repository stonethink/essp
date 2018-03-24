package client.essp.common.security.queryPrivilege;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import client.essp.common.view.VWTDWorkArea;
import c2s.essp.common.user.DtoUserBase;
import com.wits.util.Parameter;

/**
 * <p>Title: VwQueryPrivilege</p>
 *
 * <p>Description: 项目查询授权功能主界面</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public class VwQueryPrivilege extends VWTDWorkArea {

        private VwUserList vwUserList;
        private VwPrivilegeList vwPrivilegeList;
    
        public VwQueryPrivilege() {
            super(300);
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
            vwUserList = new VwUserList();
            vwPrivilegeList = new VwPrivilegeList();
            this.getTopArea().addTab("rsid.common.user", vwUserList);
            this.getDownArea().addTab("rsid.common.privilege", vwPrivilegeList);
        }
    
        /**
         * 注册UI事件
         */
        private void addUICEvent() {
            vwUserList.getTable().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
    			public void valueChanged(ListSelectionEvent e) {
    				processSelectedUser();
    			}
            });
            
        }
    
        /**
         * 选中一个用户，刷新下面的授权列表
         */
        private void processSelectedUser() {
            DtoUserBase user = (DtoUserBase) vwUserList.getTable().getSelectedData();
            String loginId = null;
            if(user != null) {
            	loginId = user.getUserLoginId();
            }
            Parameter param = new Parameter();
            param.put("loginId", loginId);
            vwPrivilegeList.setParameter(param);
            vwPrivilegeList.refreshWorkArea();
        }
    
        /**
         * 传递参数给vwUserList
         * @param parameter Parameter
         */
        public void setParameter(Parameter parameter) {
            vwUserList.setParameter(parameter);
        }
    
        /**
         * 传递刷新事件给vwUserList
         */
        public void refreshWorkArea() {
            vwUserList.refreshWorkArea();
        }


}
