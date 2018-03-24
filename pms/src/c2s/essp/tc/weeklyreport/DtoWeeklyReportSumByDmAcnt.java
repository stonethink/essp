package c2s.essp.tc.weeklyreport;

import java.math.BigDecimal;

public class DtoWeeklyReportSumByDmAcnt extends DtoAllocateHour implements IDtoAllocateHourInTheAcnt{

    private Long acntRid;
    private String acntName;

    private String acntType;
    private BigDecimal overtimeSumConfirmedInTheAcnt; //�ڱ���Ŀ�ģ����б���׼�˵ļӰ�ʱ��
    private BigDecimal overtimeSumInTheAcnt; //�ڱ���Ŀ�ģ�����û�б�reject�ļӰ�ʱ��
    //private BigDecimal sumHour;

    public String getAcntName() {
        return acntName;
    }

    public Long getAcntRid() {
        return acntRid;
    }

    public String getAcntType() {
        return acntType;
    }

    public BigDecimal getOvertimeSumConfirmedInTheAcnt() {
        return overtimeSumConfirmedInTheAcnt;
    }

    public BigDecimal getOvertimeSumInTheAcnt() {
        return overtimeSumInTheAcnt;
    }

    public BigDecimal getSumHour() {
        return null;
    }

    public void setAcntType(String acntType) {
        this.acntType = acntType;
    }

    public void setAcntRid(Long acntRid) {
        this.acntRid = acntRid;
    }

    public void setAcntName(String acntName) {
        this.acntName = acntName;
    }

    public void setOvertimeSumConfirmedInTheAcnt(BigDecimal overtimeSumConfirmedInTheAcnt) {
        this.overtimeSumConfirmedInTheAcnt = overtimeSumConfirmedInTheAcnt;
    }

    public void setOvertimeSumInTheAcnt(BigDecimal overtimeSumInTheAcnt) {
        this.overtimeSumInTheAcnt = overtimeSumInTheAcnt;
    }

    public void setSumHour(BigDecimal sumHour) {
//        this.sumHour = sumHour;
    }


    public BigDecimal getActualHourConfirmedInTheAcnt() {
        return null;
    }

    public BigDecimal getActualHourInTheAcnt(){
        return null;
    }

    public void setActualHourInTheAcnt(BigDecimal actualHourInTheAcnt){}

    public void setActualHourConfirmedInTheAcnt(BigDecimal actualHourConfirmedInTheAcnt){}

}
