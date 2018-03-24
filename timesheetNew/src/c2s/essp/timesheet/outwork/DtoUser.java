package c2s.essp.timesheet.outwork;

import c2s.dto.DtoBase;

public class DtoUser extends DtoBase{
       public final static String DTO_LOGIN_ID="loginId";
       public final static String DTO_USER_LIST="userList";
       public final static String DTO_TREE_NODE="treeNode";
       public final static String DTO_USER_LOGINS="userLoginIds";
		private String loginId;
		private String loginName;
		private String role;
		
		public String getRole() {
			return role;
		}
		public void setRole(String role) {
			this.role = role;
		}
		public String getLoginId() {
			return loginId;
		}
		public void setLoginId(String loginId) {
			this.loginId = loginId;
		}
		public String getLoginName() {
			return loginName;
		}
		public void setLoginName(String loginName) {
			this.loginName = loginName;
		}
		
}
