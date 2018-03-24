package server.essp.pms.psr.bean;

import com.wits.excel.ICellStyleAware;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class CommuAndProcessSheetBean {
    private String begin;
    private String end;
    private String type;
    private Double addCount;
    private Double closeCount;
    private Double handleCount;
    private Double abnormalCount;
    private Double totalCount;
    public String getBegin() {
        return begin;
    }

    public String getEnd() {
        return end;
    }

    public String getType() {
        return type;
    }

    public Double getAddCount() {

        return addCount;
    }

    public Double getCloseCount() {
        return closeCount;
    }

    public Double getHandleCount() {
        return handleCount;
    }

    public Double getAbnormalCount() {
        return abnormalCount;
    }

    public Double getTotalCount() {
        return totalCount;
    }

    public void setBegin(String begin) {
        this.begin = begin;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setAddCount(Double addCount) {

        this.addCount = addCount;
    }

    public void setCloseCount(Double closeCount) {
        this.closeCount = closeCount;
    }

    public void setHandleCount(Double handleCount) {
        this.handleCount = handleCount;
    }

    public void setAbnormalCount(Double abnormalCount) {
        this.abnormalCount = abnormalCount;
    }

    public void setTotalCount(Double totalCount) {
        this.totalCount = totalCount;
    }
}
