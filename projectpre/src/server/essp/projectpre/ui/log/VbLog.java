package server.essp.projectpre.ui.log;

public class VbLog {
	    private Long rid;
	    private String acntId; //ר����������뵥��
	    private String dataType;//��������
	    private String applicationType;//��������
	    private String operation;//����
	    private String operator;//������
	    private String mailSender;//�ʼ�������
	    private String operationDate;//��������
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
