package server.essp.hrbase.synchronization.syncexception.viewbean;

import java.util.*;

public class VbSyncException {
	public static final String RES_TYPE_HUMAN = "H";
	public static final String RES_TYPE_UNIT = "U";
	public static final String MODEL_PP = "ProjectPre";
	public static final String MODEL_TS = "Timesheet";
	public static final String MODEL_HR = "HrBase";
	public static final String MODEL_FI = "Finance";
	public static final String MODEL_P6 = "Primavera";
	public static final String MODEL_104HRMS = "104Hrms";
	public static final String STATUS_PENDING = "Pending";
	public static final String STATUS_COMPLETED = "Completed";
	public static final String STATUS_CANCELED = "Canceled";
	public static final String OPERATION_INSERT ="Insert";
	public static final String OPERATION_UPDATE ="Update";
	public static final String OPERATION_DELETE ="Delete";
	
	private Long rid;
    private Long resDataRid;
    private String model;
    private Date effectiveDate;
    private String operation;
    private String status;
    private String errorMessage;
    private String resType;
    private Date rct;
    private static Map<String, String> dataNameMap;
    
    static {
        dataNameMap = new HashMap();
        dataNameMap.put(RES_TYPE_HUMAN, "Human Base Data");
        dataNameMap.put(RES_TYPE_UNIT, "Unit Base Data");
    }

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public Date getRct() {
		return rct;
	}

	public void setRct(Date rct) {
		this.rct = rct;
	}

	public Long getResDataRid() {
		return resDataRid;
	}

	public void setResDataRid(Long resDataRid) {
		this.resDataRid = resDataRid;
	}

	public String getResType() {
		return resType;
	}
	public String getResTypeName() {
		return dataNameMap.get(getResType());
	}
	public void setResType(String resType) {
		this.resType = resType;
	}

	public Long getRid() {
		return rid;
	}

	public void setRid(Long rid) {
		this.rid = rid;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
