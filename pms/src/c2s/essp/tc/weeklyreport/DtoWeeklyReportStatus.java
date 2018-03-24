package c2s.essp.tc.weeklyreport;

import java.util.Date;

import c2s.dto.DtoBase;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import com.wits.excel.ICellStyleAware;

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
public class DtoWeeklyReportStatus extends DtoBase implements ICellStyleAware {
    private String loginId;
    private String chineseName;
    private String dept;
    private Date beginPeriod;
    private Date endPeriod;
    private String acntName;
    private String acntManager;
    private String acntManagerName;
    private String remark;
    public DtoWeeklyReportStatus() {
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public void setChineseName(String chineseName) {
        this.chineseName = chineseName;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public void setBeginPeriod(Date beginPeriod) {

        this.beginPeriod = beginPeriod;
    }

    public void setEndPeriod(Date endPeriod) {

        this.endPeriod = endPeriod;
    }

    public void setAcntName(String acntName) {

        this.acntName = acntName;
    }

    public void setAcntManager(String acntManager) {

        this.acntManager = acntManager;
    }

    public void setAcntManagerName(String acntManagerName) {

        this.acntManagerName = acntManagerName;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getLoginId() {
        return loginId;
    }

    public String getChineseName() {
        return chineseName;
    }

    public String getDept() {
        return dept;
    }

    public Date getBeginPeriod() {

        return beginPeriod;
    }

    public Date getEndPeriod() {

        return endPeriod;
    }

    public String getAcntName() {

        return acntName;
    }

    public String getAcntManager() {

        return acntManager;
    }

    public String getAcntManagerName() {

        return acntManagerName;
    }

    public String getRemark() {
        return remark;
    }
    public void setCellStyle(String propertyName, Object bean,
                                 HSSFWorkbook wb, HSSFCellStyle cellStyle) {
        if (getRemark() != null && "".equals(getRemark()) == false) {
            cellStyle.setFillPattern((short) HSSFCellStyle.SOLID_FOREGROUND);
            cellStyle.setFillForegroundColor(HSSFColor.GREEN.index);
        }
    }
}
