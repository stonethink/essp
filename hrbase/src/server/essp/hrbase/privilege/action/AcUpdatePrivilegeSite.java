/*
 * Created on 2007-12-17
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.hrbase.privilege.action;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import c2s.dto.TransactionData;
import c2s.essp.common.user.DtoUser;
import c2s.essp.hrbase.maintenance.DtoHrbSitePrivilege;
import server.essp.common.ldap.LDAPUtil;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.hrbase.privilege.service.IPrivilegeSiteService;
import server.framework.common.BusinessException;

/**
 * 修改權限SITE
 * @author TBH
 */
public class AcUpdatePrivilegeSite extends AbstractESSPAction {

    public static final String Selected="selectd";
    /**
     * 根据select,unselect,selectdSite的值保存选中用户和SITE之间的关系
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
//         String loginName = (String)request.getSession().getAttribute("loginName");
         String domain = (String)request.getSession().getAttribute("domain");
         LDAPUtil ldapUtil = new LDAPUtil();
         DtoUser dtoUser = ldapUtil.findUser(loginId);
         String userName=dtoUser.getUserName();
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
                     DtoHrbSitePrivilege dr = (DtoHrbSitePrivilege) list.get(i);
                     for (int j = 0; j < uList.size(); j++) {
                         String u = (String)uList.get(j);
                         if ((dr.getBelongSite()).equals(u)) {
                             list.remove(i);
                             i--;
                             break;
                         }
                     }
                 }
             }
             if(list.size()>0){
                 for (int i = 0; i < list.size(); i++) {
                     DtoHrbSitePrivilege dr = (DtoHrbSitePrivilege) list.get(i);
                     sList.add(dr.getBelongSite());
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
                     DtoHrbSitePrivilege dr = (DtoHrbSitePrivilege) list.get(i);
                     sList.add(dr.getBelongSite());
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
                     DtoHrbSitePrivilege dr = (DtoHrbSitePrivilege)list.get(j);
                     if((dr.getBelongSite()).equals(un)){
                         list.remove(j);
                     }
                 }
             }
             if(list.size()>0){
                 SELECT = new String[list.size()];
                 for (int i = 0; i < list.size(); i++) {
                     DtoHrbSitePrivilege dr = (DtoHrbSitePrivilege) list.get(i);
                     SELECT[i] = dr.getBelongSite();
                 }
             }
         }

         IPrivilegeSiteService logic = (IPrivilegeSiteService)this.getBean("PrivilegeService");
         logic.saveOrUpdateBlongSite(loginId,userName,domain,SELECT);
    }
}
