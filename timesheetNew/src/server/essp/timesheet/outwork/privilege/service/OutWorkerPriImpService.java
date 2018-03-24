package server.essp.timesheet.outwork.privilege.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import server.essp.timesheet.outwork.privilege.dao.IOutWorkerPrivilegeDao;
import server.essp.timesheet.outwork.privilege.dao.IOutWorkerPrivilegeDbDao;
import server.essp.timesheet.rmmaint.dao.IRmMaintDao;
import c2s.dto.DtoTreeNode;
import c2s.dto.ITreeNode;
import c2s.essp.common.user.DtoRole;
import c2s.essp.timesheet.outwork.DtoPrivilege;
import c2s.essp.timesheet.outwork.DtoUser;
import db.essp.timesheet.TsHumanBase;
import db.essp.timesheet.TsOutworkerPrivilege;

public class OutWorkerPriImpService implements IOutWorkerPriService {
		private IOutWorkerPrivilegeDbDao outworkPrivilegeDbDao;
        private IOutWorkerPrivilegeDao outworkPrivilegeDao;
        private IRmMaintDao rmMaintDao;
		private String rootOuId = "W0000";

        /**
          * 列出所有有权限维护差人员的信息
          * @return List
          */
		public List listUserInfo() {
				List userList=new ArrayList();
				List list=outworkPrivilegeDbDao.listUser();
				for(int i=0;i<list.size();i++){
					DtoUser dtoUser=(DtoUser)list.get(i);
					String loginId=dtoUser.getLoginId();
					List<DtoRole> roleList=outworkPrivilegeDbDao.getRoleList(loginId);
					String roleString = getRoles(roleList);
					dtoUser.setRole(roleString);
					userList.add(dtoUser);
				}
				return userList;
		}
	    
        /**
         * 將角色用'，'分隔拼接成字符串
         * @param roleList
         * @return
         */
		private String getRoles(List<DtoRole> roleList){
			    String roleStr = null;
		        for (DtoRole role : roleList) {
		            if(roleStr == null) {
		                roleStr = role.getRoleName();
		            } else {
		                roleStr += "," + role.getRoleName();
		            }
		        }
		        return roleStr;
		 }
	
        /**
         * 列出所有部门和当前员工所可以维护的部门（被勾选）
         * @param loginId
         * @return ITreeNode
         */
		public ITreeNode loadQueryPrivilege(String loginId) {
                  ITreeNode root = null;
                  if(loginId != null){
    			    root = getRoot(loginId);
                    if(root != null){
    	             appendChildren(root, rootOuId, loginId);
                    }
                  }
                  return root;
		}
	
		 /**
	     * 获取根授权节点
	     * @return ITreeNode
	     */
	    private ITreeNode getRoot(String loginId) {
    	        ITreeNode root = null;
    	        List<DtoPrivilege> list =  outworkPrivilegeDbDao.loadPrivilege(loginId,rootOuId,true);
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
    	    	List<DtoPrivilege> list = outworkPrivilegeDbDao.
                                               loadPrivilege(loginId,ouId,false);
    	        for (DtoPrivilege dto : list) {
    	            DtoTreeNode child = new DtoTreeNode(dto);
    	            if(dto.getLoginId() != null && !"".equals(dto.getLoginId().trim())){
    	            	dto.setIsPrivilege(true);
    	            }else{
    	            	dto.setIsPrivilege(false);
    	            }
    	            node.addChild(child);
    	            appendChildren(child, dto.getAccountId(), loginId);
    	        }
	    }
		
        /**
         * 保存
         * @param loginId
         * @param root
         */
		public void saveOutWorkerPri(String loginId, ITreeNode root) {
			    List<ITreeNode> list = root.listAllChildrenByPostOrder();
		        list.add(root);
                outworkPrivilegeDbDao.delete(loginId);
		        for (ITreeNode node : list) {
		            insertQueryPrivilege((DtoPrivilege) node.getDataBean(),
		                                 loginId);
		        }
		}
        
        /**
         * 新增人员时将若干个员工加入人员列表中
         * @param loginIds
         * @param userList
         * @return List
         */
         public List addUserInfo(String loginIds,List userList) {
              List uList = new ArrayList();
              if(userList == null){
                  userList = new ArrayList();
              }else{
                  for(int i=0;i<userList.size();i++){
                      DtoUser user = (DtoUser)userList.get(i);
                      uList.add(user.getLoginId());
                  }
              }
              if(loginIds != null && !"".equals(loginIds)){
               String[] loginIdArray = loginIds.split(",");
               for(String loginId : loginIdArray) {
                 if(uList.contains(loginId)){
                     continue;
                 }
                 DtoUser user = new DtoUser();
                 user.setLoginId(loginId);
                 user.setLoginName(getLoginName(loginId));
                 List<DtoRole> roleList=outworkPrivilegeDbDao.getRoleList(loginId);
                 String roleString = getRoles(roleList);
                 user.setRole(roleString);
                 userList.add(user);
               }
              }
             return userList;
            }
	   
         /**
          * 根據loginId得到名稱
          * @param loginId
          * @return String
          */
         private String getLoginName(String loginId){
                 TsHumanBase tsHumanbase =  rmMaintDao.loadHumanById(loginId);
                 String loginName = "";
                 if(tsHumanbase != null){
                     loginName = tsHumanbase.getFullName();
                 }
                 return loginName;
         }
         
	    /**
	     * 新增专案查询授权
	     * @param dto DtoQueryPrivilege
	     * @param loginId String
	     */
	    public void insertQueryPrivilege(DtoPrivilege dto, String loginId) {
	          if (dto.getIsPrivilege() == null || !dto.getIsPrivilege()) {
	            return;
	           }
              String loginName = getLoginName(loginId);
              TsOutworkerPrivilege tsPrivilege = new TsOutworkerPrivilege();
              tsPrivilege.setLoginId(loginId);
              tsPrivilege.setLoginName(loginName);
              tsPrivilege.setAcntRid(dto.getAcntRid());
              tsPrivilege.setAcntId(dto.getAccountId());
              tsPrivilege.setAcntName(dto.getAccountName());
              tsPrivilege.setRct(new Date());
              tsPrivilege.setRut(new Date());
              tsPrivilege.setRcu(loginId);
              tsPrivilege.setRuu(loginId);
              tsPrivilege.setRst("N");
              outworkPrivilegeDao.insert(tsPrivilege);
	    }

        /**
         * 删除
         * @param LoginId
         */
		public void delOutworkerPrivilege(String LoginId) {
            outworkPrivilegeDbDao.delete(LoginId);
		}

		
        public void setRmMaintDao(IRmMaintDao rmMaintDao) {
            this.rmMaintDao = rmMaintDao;
        }

        public void setOutworkPrivilegeDao(IOutWorkerPrivilegeDao outworkPrivilegeDao) {
            this.outworkPrivilegeDao = outworkPrivilegeDao;
        }

        public void setOutworkPrivilegeDbDao(
                IOutWorkerPrivilegeDbDao outworkPrivilegeDbDao) {
            this.outworkPrivilegeDbDao = outworkPrivilegeDbDao;
        }

}
