package server.essp.tc.hrmanage.viewbean;

import java.math.BigDecimal;
import java.util.Date;
import com.wits.excel.ICellStyleAware;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * ���ڼ�¼��
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Rong.Xiao
 * @version 1.0
 */
public class VbTcExcel implements ICellStyleAware{
   private String period;//������
   private Date beginPeriod;//��������
   private Date endPeriod;//����ֹ��
   private String name;//Ա����¼ID
   private String chineseName;//Ա����������
   private Date beginDateOfWork;//��������
   private Date endDateOfWork;//����ֹ��
   private BigDecimal standardWorkTime;//��׼��ʱ
   private BigDecimal actualWorkTime;//ʵ�ʹ�ʱ
   private BigDecimal normalWorkTime;//������ʱ
   private BigDecimal subWorkTime;//��ʱ��
   private BigDecimal salaryWorkTime;//��н��ʱ
   private BigDecimal overTime;//�Ӱ�Сʱ��
   private BigDecimal fullSalaryLeave;//�ݼ٣�ȫн��Сʱ��
   private BigDecimal halfSalaryLeave;//�ݼ٣���н��Сʱ��
   private BigDecimal    leave;//�ݼ�Сʱ��
   private BigDecimal absentFromWork;//��Сʱ��
   private Long violat;//Υ���ܴ���
   private String remark;//��ע
   private String wmReportStatus;//���±�״̬(��¼δ��д��δ������δ��ȷ�ϵ��ܱ���Ϣ)
   private String attendanceReportStatus;//�����״̬(��¼δ��˵ļӰ������Ϣ)

    public BigDecimal getAbsentFromWork() {
        return absentFromWork;
    }

    public BigDecimal getActualWorkTime() {
        return actualWorkTime;
    }

    public Date getBeginDateOfWork() {
        return beginDateOfWork;
    }

    public Date getBeginPeriod() {
        return beginPeriod;
    }

    public Date getEndDateOfWork() {
        return endDateOfWork;
    }

    public Date getEndPeriod() {
        return endPeriod;
    }

    public BigDecimal getFullSalaryLeave() {
        return fullSalaryLeave;
    }

    public BigDecimal getHalfSalaryLeave() {
        return halfSalaryLeave;
    }

    public BigDecimal getLeave() {
        return leave;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getNormalWorkTime() {
        return normalWorkTime;
    }

    public BigDecimal getOverTime() {
        return overTime;
    }

    public String getPeriod() {
        return period;
    }

    public String getRemark() {
        return remark;
    }

    public BigDecimal getSalaryWorkTime() {
        return salaryWorkTime;
    }

    public BigDecimal getStandardWorkTime() {
        return standardWorkTime;
    }

    public BigDecimal getSubWorkTime() {
        return subWorkTime;
    }

    public Long getViolat() {
        return violat;
    }

    public String getChineseName() {
        return chineseName;
    }

    public String getWmReportStatus() {
        return wmReportStatus;
    }

    public String getAttendanceReportStatus() {
        return attendanceReportStatus;
    }

    public void setViolat(Long violat) {
        this.violat = violat;
    }

    public void setSubWorkTime(BigDecimal subWorkTime) {
        this.subWorkTime = subWorkTime;
    }

    public void setStandardWorkTime(BigDecimal standardWorkTime) {
        this.standardWorkTime = standardWorkTime;
    }

    public void setSalaryWorkTime(BigDecimal salaryWorkTime) {
        this.salaryWorkTime = salaryWorkTime;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public void setOverTime(BigDecimal overTime) {
        this.overTime = overTime;
    }

    public void setNormalWorkTime(BigDecimal normalWorkTime) {
        this.normalWorkTime = normalWorkTime;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLeave(BigDecimal leave) {
        this.leave = leave;
    }

    public void setHalfSalaryLeave(BigDecimal halfSalaryLeave) {
        this.halfSalaryLeave = halfSalaryLeave;
    }

    public void setFullSalaryLeave(BigDecimal fullSalaryLeave) {
        this.fullSalaryLeave = fullSalaryLeave;
    }

    public void setEndPeriod(Date endPeriod) {
        this.endPeriod = endPeriod;
    }

    public void setEndDateOfWork(Date endDateOfWork) {
        this.endDateOfWork = endDateOfWork;
    }

    public void setBeginPeriod(Date beginPeriod) {
        this.beginPeriod = beginPeriod;
    }

    public void setBeginDateOfWork(Date beginDateOfWork) {
        this.beginDateOfWork = beginDateOfWork;
    }

    public void setActualWorkTime(BigDecimal actualWorkTime) {
        this.actualWorkTime = actualWorkTime;
    }

    public void setAbsentFromWork(BigDecimal absentFromWork) {
        this.absentFromWork = absentFromWork;
    }

    public void setChineseName(String chineseName) {
        this.chineseName = chineseName;
    }

    public void setWmReportStatus(String wmReportStatus) {
        this.wmReportStatus = wmReportStatus;
    }

    public void setAttendanceReportStatus(String attendanceReportStatus) {
        this.attendanceReportStatus = attendanceReportStatus;
    }

    //���õ���ʱ,��Ԫ�����ʽ
    //��ʱ��С��0ʱ,���óɺ�ɫ����
    public void setCellStyle(String propertyName,Object bean,HSSFWorkbook wb, HSSFCellStyle hSSFCellStyle) {
        System.out.println(subWorkTime.doubleValue() + " " + (subWorkTime.doubleValue() < 0D));
        if(subWorkTime != null && subWorkTime.doubleValue() < 0D){
            hSSFCellStyle.setFillPattern((short) HSSFCellStyle.SOLID_FOREGROUND);
            hSSFCellStyle.setFillForegroundColor(HSSFColor.RED.index);
        }
    }

}
