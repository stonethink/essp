/*
 * Created on 2008-2-1
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package c2s.essp.timesheet.report;

import java.util.Date;

import c2s.dto.DtoBase;

public class DtoAttVariant extends DtoBase {

	private String loginId;
	private String englishName;
	private String chineseName;
	private Date attDate;
	private Long tsRid;
	private String tsUnitCode;
	private String tsCode;
	private Double tsHours;
	private Long hrRid;
	private String hrUnitCode;
	private String hrCode;
	private Double hrHours;
    private boolean checked;
    private boolean isBalanced;
    private boolean isLeave;

	public boolean isLeave() {
        return isLeave;
    }

    public void setLeave(boolean isLeave) {
        this.isLeave = isLeave;
    }

    public boolean isBalanced() {
        return isBalanced;
    }

    public void setBalanced(boolean isBalanced) {
        this.isBalanced = isBalanced;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public Date getAttDate() {
		return attDate;
	}

	public void setAttDate(Date attDate) {
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

	public String getHrUnitCode() {
		return hrUnitCode;
	}

	public void setHrUnitCode(String hrUnitCode) {
		this.hrUnitCode = hrUnitCode;
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

	public Long getHrRid() {
		return hrRid;
	}

	public void setHrRid(Long hrRid) {
		this.hrRid = hrRid;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getTsUnitCode() {
		return tsUnitCode;
	}

	public void setTsUnitCode(String tsUnitCode) {
		this.tsUnitCode = tsUnitCode;
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

	public Long getTsRid() {
		return tsRid;
	}

	public void setTsRid(Long tsRid) {
		this.tsRid = tsRid;
	}
	
	/**
	 * 判断差勤记录是否存在差异
	 *    双方的unitCode, code, hours都不为空，且对应相等则无差异，否则有差异
	 * @return boolean
	 */
	public Boolean isVariant() {
		if (this.getTsUnitCode() == null || this.getTsCode() == null
				|| this.getTsHours() == null || this.getHrUnitCode() == null
				|| this.getHrCode() == null || this.getHrHours() == null) {
			return true;
		} else if(this.getTsUnitCode().equals(this.getHrUnitCode())
				&& this.getTsCode().equals(this.getHrCode())
				&& this.getTsHours().equals(this.getHrHours())) {
			return false;
		}
		return true;
	}
	
	public String getVariantFlag() {
		return isVariant().toString();
	}

	/**
	 * englishName(chineseName)
	 * 
	 * @return
	 */
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
