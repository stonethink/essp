package c2s.essp.pwm.wp;

/**
 * <p>Title: essp system</p>
 * <p>Description: essp</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company:  Wistron ITS Wuhan SDC</p>
 * @author wenhuike@wistronits.com
 * @version 1.0
 */

import java.io.*;
import java.util.*;

public class DtoNoDailyReport implements Serializable {

    private java.util.Date reportDate;
    private String site;
    private String empId;
    private String empName;
    private String reasonFlag;
    private String remark;
    private String empCode; //员工系统内部ID

    //以下定义日报状态之常量
    public static final String REPORT_FLAG_UNCONFIRM = "0";
    public static final String REPORT_FLAG_LEVE = "1";
    public static final String REPORT_FLAG_FILL = "2";
    public static final String REPORT_FLAG_NONE = "3";

    //定义显示之内容
    public static final String DISP_REPORT_FLAG_UNCONFIRM = "No report";
    public static final String DISP_REPORT_FLAG_LEVE = "On leave, Evection...";
    public static final String DISP_REPORT_FLAG_FILL = "Late report";
    public static final String DISP_REPORT_FLAG_NONE = "Deregulation";


    public static Hashtable hStatus = new Hashtable() ;

    static{
        hStatus.put(REPORT_FLAG_UNCONFIRM,DISP_REPORT_FLAG_UNCONFIRM);
        hStatus.put(REPORT_FLAG_LEVE,DISP_REPORT_FLAG_LEVE);
        hStatus.put(REPORT_FLAG_FILL,DISP_REPORT_FLAG_FILL);
        hStatus.put(REPORT_FLAG_NONE,DISP_REPORT_FLAG_NONE);
    }

    private String reason;

    public DtoNoDailyReport() {
    }

    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject();
    }

    private void readObject(ObjectInputStream ois) throws
        ClassNotFoundException, IOException {
        ois.defaultReadObject();
    }

    public java.util.Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(java.util.Date reportDate) {
        this.reportDate = reportDate;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getReasonFlag() {
        return reasonFlag;
    }

    public void setReasonFlag(String reasonFlag) {
        this.reasonFlag = reasonFlag;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getEmpCode() {
        return empCode;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }

    public String getReason() {
        String flag = getReasonFlag();
        if (flag.equals(REPORT_FLAG_UNCONFIRM)||flag.equals(REPORT_FLAG_LEVE)||
            flag.equals(REPORT_FLAG_FILL) ||flag.equals(REPORT_FLAG_NONE) ) {
            return hStatus.get(flag).toString();
        }
        else {
            return ("Unknow");
        }
    }

    public static  String getReasonFlagByReason(String reason) {
         if (reason.equals(DISP_REPORT_FLAG_UNCONFIRM)){
            return REPORT_FLAG_UNCONFIRM;
         }

         if (reason.equals(DISP_REPORT_FLAG_UNCONFIRM)){
             return REPORT_FLAG_UNCONFIRM;
         }

         if (reason.equals(DISP_REPORT_FLAG_FILL)){
             return REPORT_FLAG_FILL;
         }

         if (reason.equals(DISP_REPORT_FLAG_NONE)){
             return REPORT_FLAG_NONE;
         }

        return "0";
    }

}
