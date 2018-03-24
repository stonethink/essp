package server.essp.timesheet.synchronization.syncexception.viewbean;

import java.util.Date;

public class VbException {
	public static final String STATUS_PENDING = "Pending";
	public static final String STATUS_COMPLETED = "Completed";
	public static final String STATUS_CANCELED = "Canceled";
	public static final String OPERATION_INSERT ="Insert";
	public static final String OPERATION_UPDATE ="Update";
	public static final String OPERATION_DELETE ="Delete";
	
	private String accountId;

	private String accountName;

	private String employeeId;

	private String employeeName;

	private String model;

	private String status;

	private Date occurTime;

	private String errorMessage;

	private String operation;

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
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

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getOccurTime() {
		return occurTime;
	}

	public void setOccurTime(Date occurTime) {
		this.occurTime = occurTime;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
