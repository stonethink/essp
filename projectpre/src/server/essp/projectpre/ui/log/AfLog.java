package server.essp.projectpre.ui.log;

import java.util.Date;

import org.apache.struts.action.ActionForm;

public class AfLog extends ActionForm{
	    private Long rid;
	    private String acntId; //ר����������뵥��
	    private String dataType;//��������
	    private String applicationType;//��������
	    private String operation;//����
	    private String operator;//������
	    private String mailSender;//�ʼ�������
	    private String startDate;//��ʼ����
	    private String endDate;//��������
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
		public String getEndDate() {
			return endDate;
		}
		public void setEndDate(String endDate) {
			this.endDate = endDate;
		}
		public String getStartDate() {
			return startDate;
		}
		public void setStartDate(String startDate) {
			this.startDate = startDate;
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
