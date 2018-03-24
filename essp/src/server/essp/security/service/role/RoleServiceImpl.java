package server.essp.security.service.role;

import java.util.ArrayList;
import java.util.List;
import javax.sql.RowSet;


import server.framework.logic.AbstractBusinessLogic;
import server.framework.common.BusinessException;
import c2s.essp.common.user.DtoRole;
import c2s.essp.common.user.DtoUserBase;

import java.sql.*;


import c2s.dto.ITreeNode;
import c2s.dto.DtoTreeNode;
public class RoleServiceImpl extends AbstractBusinessLogic implements IRoleService{
        /**
         * 列出系统中所有可见的角色
         * @return List
         */
        public List listAllVisibleRole() {
            String sql = "select * from UPMS_ROLE t WHERE VISIBLE='1' order by t.SEQUENCE";
            return sql2Role(sql);
        }

        /**
         * 列出所有状态可用和可见的角色
         * @return List
         */
        public List listEnabled2VisibleRole(){
            String sql = "select * from UPMS_ROLE t WHERE VISIBLE='1' and t.STATUS='1' order by t.SEQUENCE";
            return sql2Role(sql);
        }

        /**
         * 列出所有的Role
         * @return
         */
        public List listAllRole(){
            String sql = "select * from UPMS_ROLE t order by t.SEQUENCE";
            return sql2Role(sql);
        }

        private List sql2Role(String sql)  throws RuntimeException {
            List roles = new ArrayList();
            RowSet rs = this.getDbAccessor().executeQuery(sql);
            try {
                while (rs.next()) {
                    DtoRole role = new DtoRole();
                    role.setRoleId(rs.getString("ROLE_ID"));
                    role.setRoleName(rs.getString("NAME"));
                    role.setDescription(rs.getString("DESCRIPTION"));
                    String parent = rs.getString("PARENT_ID");
                    if(parent!=null && parent.length()>0){
                        role.setParentId(parent);
                    }
                    boolean status = false;
                    String STATUS =rs.getString("STATUS");
                    if(STATUS.equals("1")){
                        status = true;
                    }
                    role.setStatus(status);
                    boolean visible = false;
                    String VISIBLE = rs.getString("VISIBLE");
                    if (VISIBLE.equals("1")) {
                        visible = true;
                    }
                    role.setVisible(visible);
                    int seq = rs.getInt("SEQUENCE");
                    role.setSeq(seq);
                    roles.add(role);
                }
            } catch (SQLException ex) {
                throw new BusinessException("error.system.db", ex);
            }
            return roles;
        }
        /**
         * 列出所有状态可用的角色
         * @return
         */
        public List listEnabledRole(){
            String sql = "select * from UPMS_ROLE t where t.STATUS='1' order by t.SEQUENCE";
            return sql2Role(sql);
        }
        /**
         * 列出所有状态不可用的角色
         * @return
         */
        public List listDisabledRole(){
            String sql =
                    "select * from UPMS_ROLE t where t.STATUS='0' order by t.SEQUENCE";
            return sql2Role(sql);
        }
        /**
         * 列出用户所有可用的角色
         * @return
         */
        public List listUserEnabledRole(String loginId){
            String sql = "select * from UPMS_ROLE t where t.STATUS='1' and t.ROLE_ID in ("+
                         " select u.ROLE_ID from UPMS_ROLE_USER u where Upper(u.LOGIN_ID)=Upper('"+loginId+"')) order by t.SEQUENCE";
            return sql2Role(sql);
        }
        /**
         * 列出用户所有的角色,包含可用和不可用
         * @param loginId
         * @return
         */
        public List listUserAllRole(String loginId){
            String sql = "select * from UPMS_ROLE t where t.ROLE_ID in (" +
                         " select u.ROLE_ID from UPMS_ROLE_USER u where Upper(u.LOGIN_ID)=Upper('" +
                         loginId + "')) order by t.SEQUENCE";
            return sql2Role(sql);
        }
        /**
         * 新增一个角色,角色ID不能重复
         * @param role
         */
        public void addRole(DtoRole role){
            String id = "";
            String name ="";
            String description = "";
            String parentId = "";
            String status = "";
            String visible="";
            int seq =-1;
            try {
                id = role.getRoleId();
                if(id !=null && id.length()>0){
                    String str = "select * from UPMS_ROLE where ROLE_ID='"+id+"'";
                    RowSet rs = this.getDbAccessor().executeQuery(str);
                    while (rs.next()) {
                        throw new BusinessException("error.logic.RoleServiceImpl.Roleduplicate",
                                 "Role duplicate");
                    }
                    rs.close();
                }
                if(role.getRoleName()!=null || role.getRoleName().length()>0){
                    name = role.getRoleName();
                }

                if (role.getDescription() == null ||
                    role.getDescription().length() == 0) {
                    description = role.getRoleName();
                } else {
                    description = role.getDescription();
                }
                if (role.getParentId() != null &&
                    role.getParentId().length() > 0) {
                    parentId = role.getParentId();
                }
                if(role.getSeq()>-1){
                    seq = role.getSeq();
                }
                if (role.isStatus()) {
                    status = "1";
                } else {
                    status = "0";
                }
                if(role.isVisible()){
                    visible = "1";
                }else{
                    visible = "0";
                }
                String sql =
                        "insert into UPMS_ROLE(ROLE_ID,NAME,PARENT_ID,DESCRIPTION,STATUS,VISIBLE,SEQUENCE) values ('" +
                        id+
                        "','" + name + "','" + parentId + "','" +
                        description + "','" +
                        status +"','" + visible +
                        "'," + seq + ")";
                this.getDbAccessor().executeUpdate(sql);
            }catch(BusinessException ex){
                throw new BusinessException("error.logic.RoleServiceImpl.Roleduplicate",ex);
            }catch (Exception ex) {
                throw new BusinessException("error.logic.RoleServiceImpl.Roleadd",
                                            ex);
            }

        }
        /**
         * 查找角色名对应的Role,不存在时返回空
         * @param roleName
         */
        public DtoRole getRole(String roleId){
            String sql = "select * from UPMS_ROLE t where t.ROLE_ID='" + roleId + "' order by t.ROLE_ID";
            DtoRole role = null;
            try {
                RowSet rs;
                rs = this.getDbAccessor().executeQuery(sql);
                while (rs.next()) {
                    role = new DtoRole();
                    role.setRoleId(rs.getString("ROLE_ID"));
                    role.setRoleName(rs.getString("NAME"));
                    String description = rs.getString("DESCRIPTION");
                    if(description!=null && description.length()>0){
                        role.setDescription(description);
                    }
                    String parentid = rs.getString("PARENT_ID");
                    if(parentid!=null && parentid.length()>0){
                       role.setParentId(rs.getString("PARENT_ID"));
                    }
                    int seq = rs.getInt("SEQUENCE");
                    if(seq >-1){
                        role.setSeq(seq);
                    }
                    boolean status = false;
                    String STATUS = rs.getString("STATUS");
                    if(STATUS.equals("1")){
                        status = true;
                    }
                    role.setStatus(status);

                    boolean visible = false;
                    String VISIBLE = rs.getString("VISIBLE");
                    if(VISIBLE.equals("1")){
                        visible = true;
                    }
                    role.setVisible(visible);
                }
                rs.close();
                return role;
            }catch (Exception ex) {
                throw new BusinessException("error.logic.RoleServiceImpl.getRole",
                                            ex);
            }
        }
        /**
         * 更新一个角色信息,角色ID不能重复
         * @param role
         */
        public void updateRole(DtoRole role,String roleId){
            String sql = "update UPMS_ROLE set";
            if(role.getDescription()!=null && role.getDescription().length()>0){
                sql = sql + " DESCRIPTION='"+role.getDescription()+"',";
            }else{
                sql = sql + " DESCRIPTION='',";
            }
            if(role.isStatus()){
                sql = sql + " STATUS='1',";
            }else{
                sql = sql + " STATUS='0',";
            }
            if(role.isVisible()){
                sql = sql + " VISIBLE='1',";
            }else{
                sql = sql + " VISIBLE='0',";
            }
            if(role.getSeq()>-1){
                sql = sql +" SEQUENCE="+role.getSeq()+",";
            }
            if(role.getParentId()==null || role.getParentId().length()==0){
                sql = sql + " PARENT_ID='',";
            }else{
                sql = sql + " PARENT_ID='"+ role.getParentId()+"',";
            }

            if (role.getRoleName()!= null && role.getRoleName().length()>0) {
                 sql = sql + " Name='"+ role.getRoleName()+"',";
            }

            try {
                if (role.getRoleId() == null || role.getRoleId().length() == 0) {
                    throw new BusinessException("error.logic.RoleServiceImpl.roleId",
                                                "this roleId is not null");
                }
                String str = "select * from UPMS_ROLE where ROLE_ID = '"+role.getRoleId()+"' and  ROLE_ID <>'"+roleId+"'";
                sql = sql + " ROLE_ID='" + role.getRoleId() + "'";
                RowSet rs = this.getDbAccessor().executeQuery(str);
                while (rs.next()) {
                    throw new BusinessException("error.logic.RoleServiceImpl.Roleduplicate",
                                                "Role duplicate");
                }
                rs.close();
                sql = sql + " where ROLE_ID='" + roleId + "'";
                this.getDbAccessor().executeUpdate(sql);
            }catch (Exception ex) {
                throw new BusinessException("error.logic.RoleServiceImpl.updateRole",
                                            ex);
            }
        }
        /**
         * 保存或更新人员对应的角色
         */
        public void saveOrUpdateUserRole(String loginId,String[] roleIds,String domain){

//          String str = "delete from UPMS_ROLE_USER t where t.LOGIN_ID='" + loginId + "'";
//        	@ORA2SQL delete sentence shoudn't include alias 't'
//        	modify by LipengXu at 2009-01-14
        	String str = "delete from UPMS_ROLE_USER where LOGIN_ID='" + loginId + "'";
            try{
                this.getDbAccessor().executeUpdate(str);
                if(roleIds!=null && roleIds.length>0){
                    for (int i = 0; i < roleIds.length; i++) {
                        String sql1 =
                                "insert into UPMS_ROLE_USER (ROLE_ID,LOGIN_ID,DOMAIN)" +
                                " values ('" + roleIds[i] + "','" + loginId +"','"+ domain +
                                "')";
                        this.getDbAccessor().executeUpdate(sql1);
                    }
                }
            }catch(Exception ex){
                throw new BusinessException("error.logic.RoleServiceImpl.saveOrUpdateUserRole",
                                            ex);
            }
        }

        /**
         * 根据角色名删除相应的Role
         * @param rolename String
         */
        public void deleteRole(String roleId){
//          String sql = "delete from UPMS_ROLE t where t.ROLE_ID='"+roleId+"'";
//        	@ORA2SQL delete sentence shoudn't include alias 't'
//        	modify by LipengXu at 2009-01-14
            String sql = "delete from UPMS_ROLE  where ROLE_ID='"+roleId+"'";
            try{
               this.getDbAccessor().executeUpdate(sql);
            }catch(Exception ex){
                throw new BusinessException("error.logic.RoleServiceImpl.deleteRole",
                                            ex);
            }
        }

        /**
         * 获取角色树
         * @return TreeNode
         */
        public ITreeNode getRoleTree(){
            DtoRole dtoRole = new DtoRole();
            ITreeNode root = new DtoTreeNode(dtoRole);
            List list = (List) listAllRole();
            for (int i = 0; i < list.size(); i++) {
                DtoRole dto = (DtoRole) list.get(i);
                if (dto.getParentId() == null ||
                    dto.getParentId().length() == 0) {
                    ITreeNode childTree = new DtoTreeNode(dto);
                    getChildTree(childTree,list);
                    root.addChild(childTree);
                }
            }
            return root;
        }

        public void getChildTree(ITreeNode root,List list){
            DtoRole dto = (DtoRole)root.getDataBean();
            for(int i=0;i<list.size();i++){
                DtoRole dtoRole =(DtoRole)list.get(i);
                if((dto.getRoleId()).equals(dtoRole.getParentId())){
                    ITreeNode childTree = new DtoTreeNode(dtoRole);
                    getChildTree(childTree,list);
                    root.addChild(childTree);
                }
            }
        }

        public static void main(String [] args)throws Exception{
            RoleServiceImpl rsi = new RoleServiceImpl();
//            DtoRole role = new DtoRole();
//            role.setRoleId("aaa");
//            role.setParentId("bbb");
//            role.setRoleName("bbb");
//            role.setDescription("bbb");
//            role.setStatus(true);
//            String[] name = {"Director","Chairman","COO","PM"};
//            rsi.saveOrUpdateUserRole("WenJunYang",name);
//              rsi.deleteRole("bbb");
//            DtoRole role = rsi.getRole("Role_Admin");
//            System.out.println(role.getRoleId()+" "+role.getRoleName()+" "+role.getDescription()+" "+
//                    role.getParentId()+ " "+role.isStatus());
//            rsi.updateRole(role);

        }
        /**
         * 列出某角色下的所有人的Login Id
         */
        public List listLoginIdUnderRole(String[] roleIds) {
            List dtoList = new ArrayList();
            String sql = "select distinct t.LOGIN_ID,t.DOMAIN from UPMS_ROLE_USER t where";
            try {
              for(int i=0;i<roleIds.length;i++){
                String roleId = roleIds[i];
                if(i==0){
                    sql = sql + " t.ROLE_ID='"+roleId+"'";
                } else {
                    sql = sql + " or t.ROLE_ID='"+roleId+"'";
                }
              }
              sql += " order by t.LOGIN_ID";
              RowSet rs =  this.getDbAccessor()
                                   .executeQuery(sql);
                while (rs!=null&&rs.next()){
                    String loginId = rs.getString("LOGIN_ID");
                    String domain =rs.getString("DOMAIN");
                    DtoUserBase dtoUser = new DtoUserBase();
                    dtoUser.setUserLoginId(loginId);
                    dtoUser.setDomain(domain);
                    dtoList.add(dtoUser);
                }

            } catch (Exception e) {
               throw new BusinessException(e);
            }
            return dtoList;
        }



}
