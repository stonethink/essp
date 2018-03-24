package server.essp.security.service.queryPrivilege;

import java.util.ArrayList;
import java.util.List;
import c2s.dto.DtoTreeNode;
import c2s.dto.ITreeNode;
import c2s.essp.common.security.DtoQueryPrivilege;
import c2s.essp.common.user.DtoRole;
import c2s.essp.common.user.DtoUser;
import c2s.essp.common.user.DtoUserBase;
import server.essp.common.ldap.LDAPUtil;
import server.essp.framework.logic.AbstractESSPLogic;
import server.essp.security.service.role.IRoleService;
import javax.sql.RowSet;
import java.sql.*;

/**
 * <p>Title: QueryPrivilegeImp</p>
 *
 * <p>Description: 专案查询授权服务实现</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public class QueryPrivilegeServiceImp extends AbstractESSPLogic
        implements IQueryPrivilegeService {
 
        private final static String SQL_INSERT =
                "insert into pp_acnt_query_privilege values(?, ?, ?, ?)";
    
        private final static String SQL_DELETE =
                "delete pp_acnt_query_privilege where upper(login_id) = upper(?)";
    
        private final static String SQL_HEAD
                = "select a.rid as execute_unit_rid, a.acnt_id as execute_unit_id, "
                  + "a.acnt_name as execute_unit_name, "
                  + " p.org_privilege as org_query_flag, "
                  + " p.v_code_privilege as v_code_query_flag"
                  + " from pp_acnt a left join pp_acnt_query_privilege p"
                  + " on a.rid = p.acnt_rid and upper(p.login_id) = upper(?)"
                  + " where a.is_acnt = '0' ";
        
        private final static String SQL_ORDER_BY = " order by a.acnt_id";
    
        private IRoleService roleService;
        private String rootOuId = "W0000";
    
        /**
         * 根据用户loginId(用“，”隔开的多用户)获取用户名称，权限角色等信息
         *
         * @param loginIds String
         * @return List
         */
         public List getUserInfo(String loginIds) {
              List uList = new ArrayList();
              List userList = getUserList();
              for(int i=0;i<userList.size();i++){
                  String loginId = (String)userList.get(i);
                  DtoUserBase user = new DtoUserBase();
                  user.setUserLoginId(loginId);
                  user.setUserName(getUserName(loginId));
                  user.setUserType(getRoles(loginId));
                  uList.add(user);
              }
              if(loginIds != null && !"".equals(loginIds)){
               String[] loginIdArray = loginIds.split(",");
               for(String loginId : loginIdArray) {
                 if(userList != null && userList.contains(loginId)){
                     continue;
                 }
                 DtoUserBase user = new DtoUserBase();
                 user.setUserLoginId(loginId);
                 user.setUserName(getUserName(loginId));
                 user.setUserType(getRoles(loginId));
                 uList.add(user);
               }
              }
             return uList;
            }
        
        private List getUserList(){
            String sql ="select distinct login_id from pp_acnt_query_privilege ";
            List<DtoQueryPrivilege> list =
                    rowSet2LoginIdList(getDbAccessor().executeQuery(sql));
            return list;
        }
        
        private static List rowSet2LoginIdList(RowSet rs) {
            List list = new ArrayList();
            try {
                while (rs.next()) {
                    String loginId = (rs.getString("login_id"));
                    list.add(loginId);
                }
            }catch(Exception e){
                log.debug(e);
            }
            return list;
         }
            /**
             * 获取用户名
             * @param loginId String
             * @return String
             */
           private String getUserName(String loginId) {
                String name = "";
                LDAPUtil ldap = new LDAPUtil();
                DtoUser user = ldap.findUser("all", loginId);
                if(user != null) {
                   name = user.getUserName();
                 }
                 return name;
          }
    
        /**
         * 获取用户角色
         * @param loginId String
         * @return String
         */
        private String getRoles(String loginId) {
            String roleStr = null;
            List<DtoRole> roles = roleService.listUserEnabledRole(loginId);
            for (DtoRole role : roles) {
                if(roleStr == null) {
                    roleStr = role.getRoleName();
                } else {
                    roleStr += "," + role.getRoleName();
                }
            }
            return roleStr;
        }
    
        /**
         * 根据用户loginId获取用户查询权限
         *
         * @param loginId String
         * @return ITreeNode
         */
        public ITreeNode loadQueryPrivilege(String loginId) {
            ITreeNode root = getRoot(loginId);
            appendChildren(root, rootOuId, loginId);
            return root;
        }
    
        /**
         * 获取根授权节点
         * @return ITreeNode
         */
        private ITreeNode getRoot(String loginId) {
            ITreeNode root = null;
            String sql = SQL_HEAD + " and a.acnt_id = '" + rootOuId  + "'";
            List param = new ArrayList();
            param.add(loginId);
            List<DtoQueryPrivilege> list =
                    rowSet2Dto(getDbAccessor().executeQuery(sql, param));
            if (list != null && list.size() > 0) {
                root = new DtoTreeNode(list.get(0));
            }
            return root;
        }
    
        /**
         * 添加子孙节点
         * @param node ITreeNode
         * @param ouId String
         */
        private void appendChildren(ITreeNode node, String ouId, String loginId) {
            String sql = SQL_HEAD
                         + "and a.rel_parent_id = '" + ouId + "'"
                         + SQL_ORDER_BY;
            List param = new ArrayList();
            param.add(loginId);
            List<DtoQueryPrivilege> list =
                    rowSet2Dto(getDbAccessor().executeQuery(sql, param));
            if (list == null) {
                return;
            }
            for (DtoQueryPrivilege dto : list) {
                DtoTreeNode child = new DtoTreeNode(dto);
                node.addChild(child);
                appendChildren(child, dto.getExecuteUnitId(), loginId);
            }
        }
    
        /**
         * 将RowSet转换成List<DtoQueryPrivilege>
         * @param rs RowSet
         * @return List<DtoQueryPrivilege>
         */
        private static List rowSet2Dto(RowSet rs) {
            List list = new ArrayList();
            try {
                while (rs.next()) {
                    DtoQueryPrivilege dto = new DtoQueryPrivilege();
                    dto.setExecuteUnitRid(rs.getLong("execute_unit_rid"));
                    dto.setExecuteUnitId(rs.getString("execute_unit_id"));
                    dto.setExecuteUnitName(rs.getString("execute_unit_name"));
                    String orgFlag = rs.getString("org_query_flag");
                    if(orgFlag != null && orgFlag.equals("1")) {
                        dto.setOrgQueryFlag(true);
                    } else {
                        dto.setOrgQueryFlag(false);
                    }
                    String vCodeFlag = rs.getString("v_code_query_flag");
                    if(vCodeFlag != null && vCodeFlag.equals("1")) {
                        dto.setVCodeQueryFlag(true);
                    } else {
                        dto.setVCodeQueryFlag(false);
                    }
                    list.add(dto);
                }
            } catch (SQLException ex) {
                log.debug(ex);
            }
            return list;
        }
    
        /**
         * 保存用户专案查询权限
         * @param root ITreeNode
         * @param loginId String
         */
        public void saveQueryPrivilege(String loginId, ITreeNode root) {
            List<ITreeNode> list = root.listAllChildrenByPostOrder();
            list.add(root);
            clearQueryPrivilege(loginId);
            for (ITreeNode node : list) {
                insertQueryPrivilege((DtoQueryPrivilege) node.getDataBean(),
                                     loginId);
            }
        }
    
        /**
         * 清除某用户的所有专案查询授权
         * @param loginId String
         */
        public void clearQueryPrivilege(String loginId) {
            List param = new ArrayList();
            param.add(loginId);
            this.getDbAccessor().executeUpdate(SQL_DELETE, param);
        }
    
        /**
         * 新增专案查询授权
         * @param dto DtoQueryPrivilege
         * @param loginId String
         */
        public void insertQueryPrivilege(DtoQueryPrivilege dto, String loginId) {
            boolean orgFlag = isOrgQueryPrivilege(dto);
            boolean vCodeFlag = isVCodePrivilege(dto);
            if (orgFlag == false && vCodeFlag == false) {
                return;
            }
            List param = new ArrayList();
            param.add(loginId);
            param.add(dto.getExecuteUnitRid());
            param.add(orgFlag);
            param.add(vCodeFlag);
            getDbAccessor().executeUpdate(SQL_INSERT, param);
        }
    
        private static boolean isOrgQueryPrivilege(DtoQueryPrivilege dto) {
            if(dto == null) {
                 return false;
             }
             if(dto.getOrgQueryFlag() == null || dto.getOrgQueryFlag() == false) {
                 return false;
             } else {
                 return true;
             }
        }
    
        private static boolean isVCodePrivilege(DtoQueryPrivilege dto) {
            if(dto == null) {
                 return false;
             }
             if(dto.getVCodeQueryFlag() == null || dto.getVCodeQueryFlag() == false) {
                 return false;
             } else {
                 return true;
             }
        }
    
        public void setRoleService(IRoleService roleService) {
            this.roleService = roleService;
        }
    
        public void setRootOuId(String rootOuId) {
            this.rootOuId = rootOuId;
        }
}
