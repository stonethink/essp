package server.essp.attendance.overtime.viewbean;

import org.apache.struts.action.*;

public class VbOverTimeImportInfo extends ActionForm {
    private Long totalRows;
    private Long importedRows;
    private Double totalHours;
    private String remark;
    public VbOverTimeImportInfo() {
    }

    public void setTotalRows(Long totalRows) {
        this.totalRows = totalRows;
    }

    public void setImportedRows(Long importedRows) {
        this.importedRows = importedRows;
    }

    public void setTotalHours(Double totalHours) {
        this.totalHours = totalHours;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getTotalRows() {
        return totalRows;
    }

    public Long getImportedRows() {
        return importedRows;
    }

    public Double getTotalHours() {
        return totalHours;
    }

    public String getRemark() {
        return remark;
    }
}
