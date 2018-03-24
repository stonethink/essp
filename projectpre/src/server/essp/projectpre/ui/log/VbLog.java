package server.essp.projectpre.ui.log;

public class VbLog {
	    private Long rid;
	    private String acntId; //专案代码或申请单号
	    private String dataType;//资料类型
	    private String applicationType;//申请类型
	    private String operation;//操作
	    private String operator;//操作者
	    private String mailSender;//邮件发送者
	    private String operationDate;//操作日期
		public String getAcntId() {
			return acntId;
		}
		public void setAcntId(String acntId) {
			this.acntId = acntId;
		}
		public String getApplicationType() {
			return applicationType;
		}
		public void setApplicationType(String applicationType) {
			this.applicationType = applicationType;
		}
		public String getDataType() {
			return dataType;
		}
		public void setDataType(String dataType) {
			this.dataType = dataType;
		}
		public String getMailSender() {
			return mailSender;
		}
		public void setMailSender(String mailSender) {
			this.mailSender = mailSender;
		}
		public String getOperation() {
			return operation;
		}
		public void setOperation(String operation) {
			this.operation = operation;
		}
		public String getOperationDate() {
			return operationDate;
		}
		public void setOperationDate(String operationDate) {
			this.operationDate = operationDate;
		}
		public String getOperator() {
			return operator;
		}
		public void setOperator(String operator) {
			this.operator = operator;
		}
		public Long getRid() {
			return rid;
		}
		public void setRid(Long rid) {
			this.rid = rid;
		}
}
