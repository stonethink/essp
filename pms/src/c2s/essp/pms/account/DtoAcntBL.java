package c2s.essp.pms.account;

import java.util.Date;

import c2s.dto.DtoBase;


public class DtoAcntBL extends DtoBase {

    /** identifier field */
    private Long rid;

    private String loginUserId;


    /** nullable persistent field */
    private String baselineId;


    /** nullable persistent field */
    private Date appDate;


    /** nullable persistent field */
    private String appReason;


    /** nullable persistent field */
    private Date approveDate;


    /** nullable persistent field */
    private String approveUser;


    /** nullable persistent field */
    private String appStatus;


    /** nullable persistent field */
    private String remark;


    /** nullable persistent field */
    private String rst;


    /** nullable persistent field */
    private Date rct;


    /** nullable persistent field */
    private Date rut;

//    private String pmId;

    private boolean add = true;

    public final static String STATUS_MODIFY = "Modified";
    public final static String STATUS_APPLICATION = "Application";
    public final static String STATUS_AGREED = "Agreed";
    public final static String STATUS_APPROVED = "Approved";
    public final static String STATUS_NONAPPROVED = "Not Approved";


    /** full constructor */
    public DtoAcntBL(Long rid, String baselineId, Date appDate,
                     String appReason, Date approveDate, String approveUser,
                     String appStatus, String remark, String rst, Date rct,
                     Date rut) {
        this.rid = rid;
        this.baselineId = baselineId;
        this.appDate = appDate;
        this.appReason = appReason;
        this.approveDate = approveDate;
        this.approveUser = approveUser;
        this.appStatus = appStatus;
        this.remark = remark;
        this.rst = rst;
        this.rct = rct;
        this.rut = rut;
    }


    /** default constructor */
    public DtoAcntBL() {
    }


    /** minimal constructor */
    public DtoAcntBL(Long rid) {
        this.rid = rid;
    }

    public Long getRid() {
        return this.rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public String getBaselineId() {
        return this.baselineId;
    }

    public void setBaselineId(String baselineId) {
        this.baselineId = baselineId;
    }

    public Date getAppDate() {
        return this.appDate;
    }

    public void setAppDate(Date appDate) {
        this.appDate = appDate;
    }

    public String getAppReason() {
        return this.appReason;
    }

    public void setAppReason(String appReason) {
        this.appReason = appReason;
    }

    public Date getApproveDate() {
        return this.approveDate;
    }

    public void setApproveDate(Date approveDate) {
        this.approveDate = approveDate;
    }

    public String getApproveUser() {
        return this.approveUser;
    }

    public void setApproveUser(String approveUser) {
        this.approveUser = approveUser;
    }


    public String getLoginUserId() {
        return this.loginUserId;
    }

    public void setLoginUserId(String applicationUser) {
        this.loginUserId = applicationUser;
    }

    public String getAppStatus() {
        return this.appStatus;
    }

    public void setAppStatus(String appStatus) {
        this.appStatus = appStatus;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRst() {
        return this.rst;
    }

    public void setRst(String rst) {
        this.rst = rst;
    }

    public Date getRct() {
        return this.rct;
    }

    public void setRct(Date rct) {
        this.rct = rct;
    }

    public Date getRut() {
        return this.rut;
    }

//    public String getPmId() {
//        return pmId;
//    }
//
    public boolean isAdd() {
        return add;
    }

    public void setRut(Date rut) {
        this.rut = rut;
    }

//    public void setPmId(String pmId) {
//        this.pmId = pmId;
//    }

    public void setAdd(boolean add) {
        this.add = add;
    }

    public static Object[] getPMStatus() {
        return new Object[] {
            STATUS_APPLICATION
        };
    }

    public static Object[] getManagerStatus() {
        return new Object[] {
            STATUS_AGREED,
            STATUS_APPROVED,
            STATUS_NONAPPROVED
        };
    }

    public static Object[] getAllStatus() {
        return new Object[] {
            STATUS_APPLICATION,
            STATUS_AGREED,
            STATUS_APPROVED,
            STATUS_NONAPPROVED
        };
    }

    public boolean canApplication() {
        if (this.appStatus.equals(STATUS_MODIFY) ||
            this.appStatus.equals(STATUS_APPLICATION) ||
            this.appStatus.equals(STATUS_NONAPPROVED) ||
            this.appStatus.equals(STATUS_APPROVED)) {
            return true;
        } else {
            return false;
        }
    }

    public static String getDefalutStatus() {
        return STATUS_APPLICATION;
    }

    public static String getNextBaselineId(String baselineId) {
        if (baselineId == null || baselineId.equals("")) {
            return "V1.0";
        } else {
            int iPosP = baselineId.indexOf(".");
            String sHeader = "";
            String sNewTail = ".0";

            if (iPosP != -1) {
                sHeader = baselineId.substring(0, iPosP);
//                sTail = baselineId.substring(iPosP);
            } else {
                sHeader = baselineId;
            }

            String sNewHeader = getNextBaselineHead(sHeader);
            return (sNewHeader + sNewTail);
        }
    }

    private static String getNextBaselineHead(String sHeader) {
        String sNewHeader = "";
        if (sHeader == null || sHeader.equals("")) {
            return "V1";
        }

        char[] cA = sHeader.toCharArray();
        int iEndPos = 0;
        for (iEndPos = cA.length-1; iEndPos >= 0;iEndPos-- ) {
            if (cA[iEndPos] < '0' || cA[iEndPos] > '9') {
                break;
            }
        }
        iEndPos = iEndPos + 1;
        String sNums = "";
        String sHeaderTmp = "";
        int iNum = 1;
        if (iEndPos == cA.length ) {
            sNums = "0";
        } else if (iEndPos == 0) {
            sNums = sHeader;
        } else {
            sHeaderTmp = sHeader.substring(0, iEndPos);
            sNums = sHeader.substring(iEndPos);
        }
        try {
            iNum = Integer.parseInt(sNums);
        } catch (Exception ex) {
//            return this.baselineId + "1";
        }
        sNewHeader = sHeaderTmp + (iNum + 1);
        return sNewHeader;
    }

    public static void main(String[] args) {
        DtoAcntBL dto = new DtoAcntBL();
        String baselineId = "Vaa2a11";
        String newBaseline = dto.getNextBaselineId(baselineId);
        System.out.println("NextBaselineId=[" + newBaseline + "]");
    }



}
