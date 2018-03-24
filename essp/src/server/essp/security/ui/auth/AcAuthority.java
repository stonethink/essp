package server.essp.security.ui.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import server.framework.common.BusinessException;
import c2s.dto.TransactionData;
import java.util.List;
import c2s.essp.common.user.DtoRole;
import java.util.ArrayList;
import server.essp.security.service.role.IRoleService;
import server.essp.framework.action.AbstractESSPAction;

public class AcAuthority extends AbstractESSPAction {

        public static final String Selected="selectd";
        /**
         * 根据select,unselect,selectdRole的值来给对应的loginId分配角色。
         * @param request HttpServletRequest
         * @param response HttpServletResponse
         * @param data TransactionData
         * @throws BusinessException
         */
        public void executeAct(HttpServletRequest request,
                        HttpServletResponse response, TransactionData data)
                        throws BusinessException {
             String select = (String)request.getParameter("select");
             String unselect = (String)request.getParameter("unselect");
             List list = (List)request.getSession().getAttribute(Selected);
             String loginId = (String)request.getSession().getAttribute("loginId");
             String domain = (String)request.getSession().getAttribute("domain");
             String[] Select = null;
             String[] UnSelect = null;
             String[] SELECT = null;
             List  sList = new ArrayList();
             List  uList = new ArrayList();
             if(select.length()>0 && unselect.length()>0){
                 Select = select.split(",");
                 UnSelect = unselect.split(",");
                 for (int i = 0; i < Select.length; i++) {
                     if(i>0){
                         sList.add(Select[i]);
                     }
                 }
                 for (int i = 0; i < UnSelect.length; i++) {
                     if(i>0){
                         uList.add(UnSelect[i]);
                     }
                 }
                 for (int i = 0; i < sList.size(); i++) {
                     String s = (String)sList.get(i);
                     for (int j = 0; j < uList.size(); j++) {
                         String u = (String)uList.get(j);
                         if (s.equals(u)) {
                             sList.remove(i);
                             uList.remove(j);
                             i--;
                             break;
                         }
                     }
                 }
                 if (uList.size() > 0) {
                     for (int i = 0; i < list.size(); i++) {
                         DtoRole dr = (DtoRole) list.get(i);
                         for (int j = 0; j < uList.size(); j++) {
                             String u = (String)uList.get(j);
                             if ((dr.getRoleId()).equals(u)) {
                                 list.remove(i);
                                 i--;
                                 break;
                             }
                         }
                     }
                 }
                 if(list.size()>0){
                     for (int i = 0; i < list.size(); i++) {
                         DtoRole dr = (DtoRole) list.get(i);
                         sList.add(dr.getRoleId());
                     }
                 }
                 if(sList.size()>0){
                     SELECT = new String[sList.size()];
                     for (int i = 0; i < sList.size(); i++) {
                         String dr = (String) sList.get(i);
                         SELECT[i] = dr;
                     }
                 }
             }else if(select.length()>0){
                 Select = select.split(",");
                 for (int i = 0; i < Select.length; i++) {
                     if(i>0){
                         sList.add(Select[i]);
                     }
                 }
                 int len = Select.length;
                 if(list.size()>0){
                     for (int i = 0; i < list.size(); i++) {
                         DtoRole dr = (DtoRole) list.get(i);
                         sList.add(dr.getRoleId());
                     }
                 }
                 if(sList.size()>0){
                     SELECT = new String[sList.size()];
                     for (int i = 0; i < sList.size(); i++) {
                         String s = (String)sList.get(i);
                         SELECT[i] = s;
                     }
                 }
             }else if(unselect.length()>0){
                 UnSelect = unselect.split(",");
                 for (int i = 0; i < UnSelect.length; i++) {
                     if(i>0){
                         uList.add(UnSelect[i]);
                     }
                 }
                 for(int i=0;i<uList.size();i++){
                     String un = (String)uList.get(i);
                     for(int j=0;j<list.size();j++){
                         DtoRole dr = (DtoRole)list.get(j);
                         if((dr.getRoleId()).equals(un)){
                             list.remove(j);
                         }
                     }
                 }
                 if(list.size()>0){
                     SELECT = new String[list.size()];
                     for (int i = 0; i < list.size(); i++) {
                         DtoRole dr = (DtoRole) list.get(i);
                         SELECT[i] = dr.getRoleId();
                     }
                 }
             }

             IRoleService logic = (IRoleService)this.getBean("RoleService");
             logic.saveOrUpdateUserRole(loginId,SELECT,domain);
        }
}
