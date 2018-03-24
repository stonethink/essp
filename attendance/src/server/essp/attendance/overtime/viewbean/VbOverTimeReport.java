package server.essp.attendance.overtime.viewbean;

import java.util.*;
import com.wits.excel.ICellStyleAware;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class VbOverTimeReport  implements ICellStyleAware  {
    private String loginId;
    private String name;
    private String chineseName;
    private String unitCode;
    private String unitName;
    private String acntId;
    private String acntName;
    private Double totalHours;
    private Double sumHours;
    private Double shiftHours;
    private Double payedHours;
    private Date inDate;

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setChineseName(String chineseName) {
        this.chineseName = chineseName;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public void setAcntId(String acntId) {
        this.acntId = acntId;
    }

    public void setAcntName(String acntName) {
        this.acntName = acntName;
    }

    public void setTotalHours(Double totalHours) {
        this.totalHours = totalHours;
    }

    public void setSumHours(Double sumHours) {

        this.sumHours = sumHours;
    }

    public void setShiftHours(Double shiftHours) {
        this.shiftHours = shiftHours;
    }

    public void setPayedHours(Double payedHours) {
        this.payedHours = payedHours;
    }

    public void setInDate(Date inDate) {
        this.inDate = inDate;
    }

    public String getLoginId() {
        return loginId;
    }

    public String getName() {
        return name;
    }

    public String getChineseName() {
        return chineseName;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public String getUnitName() {
        return unitName;
    }

    public String getAcntId() {
        return acntId;
    }

    public String getAcntName() {
        return acntName;
    }

    public Double getTotalHours() {
        return totalHours;
    }

    public Double getSumHours() {

        return sumHours;
    }

    public Double getShiftHours() {
        return shiftHours;
    }

    public Double getPayedHours() {
        return payedHours;
    }

    public Date getInDate() {
        return inDate;
    }

    public Double getUsableHours() {
        Double usableHours = 0.0;
        if(totalHours == null) {
            usableHours = sumHours - shiftHours - payedHours;
        } else {
            usableHours = totalHours - shiftHours - payedHours;
        }
        BigDecimal big = new BigDecimal(usableHours);

        return big.setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    public void setCellStyle(String string, Object object,
                             HSSFWorkbook hSSFWorkbook,
                             HSSFCellStyle cellStyle) {
        if(sumHours != null) {
            cellStyle.setFillPattern((short) HSSFCellStyle.SOLID_FOREGROUND);
            cellStyle.setFillForegroundColor(HSSFColor.YELLOW.index);
        }
    }
}
