/*
 * Created on 2008-4-2
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package c2s.essp.timesheet.report;

import c2s.dto.DtoBase;

public class DtoAttVariantMail extends DtoBase {
    
    private String loginId;
    private String englishName;
    private String chineseName;
    private String attDate;
    private String tsUnitCode;
    private String tsCode;
    private Double tsHours;
    private String hrUnitCode;
    private String hrCode;
    private Double hrHours;
    
    public String getAttDate() {
        return attDate;
    }
    public void setAttDate(String attDate) {
        this.attDate = attDate;
    }
    
    public String getChineseName() {
        return chineseName;
    }
    public void setChineseName(String chineseName) {
        this.chineseName = chineseName;
    }
    public String getEnglishName() {
        return englishName;
    }
    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }
    public String getHrCode() {
        return hrCode;
    }
    public void setHrCode(String hrCode) {
        this.hrCode = hrCode;
    }
    public Double getHrHours() {
        return hrHours;
    }
    public void setHrHours(Double hrHours) {
        this.hrHours = hrHours;
    }
    public String getHrUnitCode() {
        return hrUnitCode;
    }
    public void setHrUnitCode(String hrUnitCode) {
        this.hrUnitCode = hrUnitCode;
    }
    public String getLoginId() {
        return loginId;
    }
    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }
    public String getTsCode() {
        return tsCode;
    }
    public void setTsCode(String tsCode) {
        this.tsCode = tsCode;
    }
    public Double getTsHours() {
        return tsHours;
    }
    public void setTsHours(Double tsHours) {
        this.tsHours = tsHours;
    }
    public String getTsUnitCode() {
        return tsUnitCode;
    }
    public void setTsUnitCode(String tsUnitCode) {
        this.tsUnitCode = tsUnitCode;
    }
    
    public String getFullName() {
        return ecName2FullName(this.getEnglishName(), this.getChineseName());
    }

    public static String ecName2FullName(final String eName, final String cName) {
        String fullName = eName;
        if (fullName == null) {
            return cName;
        }
        if (cName != null && "".equals(cName) == false) {
            fullName += "(" + cName + ")";
        }
        return fullName;
    }
}
