package c2s.essp.timesheet.outwork;

import c2s.dto.DtoBase;

public class DtoPrivilege extends DtoBase{
        private Long rid;
	    private Long acntRid;
		private String accountName;
		private String accountId;
		private String loginId;
		private String loginName;
		private Boolean isPrivilege;
	
        public Boolean getIsPrivilege() {
			return isPrivilege;
		}
		public void setIsPrivilege(Boolean isPrivilege) {
			this.isPrivilege = isPrivilege;
		}
		public String getLoginId() {
			return loginId;
		}
		public void setLoginId(String loginId) {
			this.loginId = loginId;
		}
		public Long getAcntRid() {
			return acntRid;
		}
		public void setAcntRid(Long acntRid) {
			this.acntRid = acntRid;
		}
		public String getLoginName() {
			return loginName;
		}
		public void setLoginName(String loginName) {
			this.loginName = loginName;
		}
        public Long getRid() {
            return rid;
        }
        public void setRid(Long rid) {
            this.rid = rid;
        }
        public String getAccountId() {
            return accountId;
        }
        public void setAccountId(String accountId) {
            this.accountId = accountId;
        }
        public String getAccountName() {
            return accountName;
        }
        public void setAccountName(String accountName) {
            this.accountName = accountName;
        }

	
}
