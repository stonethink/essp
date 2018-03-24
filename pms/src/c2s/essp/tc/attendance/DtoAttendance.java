package c2s.essp.tc.attendance;

import java.util.Date;

import c2s.dto.DtoBase;

public class DtoAttendance extends DtoBase{
   private Long rid;
   private String loginId;
   private String attendanceType;
//   private String typeName;
   private Date  attendanceDate;
   private String remark;
    public Date getAttendanceDate() {
        return attendanceDate;
    }

    public String getAttendanceType() {
        return attendanceType;
    }

    public String getLoginId() {
        return loginId;
    }

    public String getRemark() {
        return remark;
    }

    public Long getRid() {
        return rid;
    }

//    public String getTypeName() {
//        return typeName;
//    }

    public void setAttendanceDate(Date attendanceDate) {
        this.attendanceDate = attendanceDate;
    }

    public void setAttendanceType(String attendanceType) {
        this.attendanceType = attendanceType;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

//    public void setTypeName(String typeName) {
//        this.typeName = typeName;
//    }
}
