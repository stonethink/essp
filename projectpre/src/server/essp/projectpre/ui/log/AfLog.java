package server.essp.projectpre.ui.log;

import java.util.Date;

import org.apache.struts.action.ActionForm;

public class AfLog extends ActionForm{
	    private Long rid;
	    private String acntId; //专案代码或申请单号
	    private String dataType;//资料类型
	    private String applicationType;//申请类型
	    private String operation;//操作
	    private String operator;//操作者
	    private String mailSender;//邮件发送者
	    private String startDate;//起始日期
	    private String endDate;//结束日期
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
