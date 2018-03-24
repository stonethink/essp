package c2s.essp.timecard.timecard;

import c2s.dto.DtoBase;

import java.io.Serializable;

import java.math.BigDecimal;

import java.util.Date;


public class DtoQueryCondition extends DtoBase implements Serializable {
    private boolean    init; //�Ƿ��ǳ�ʼ������
    private boolean    pm; //��½���Ƿ�Ϊ��Ŀ����
    private Date       weekStart; //������
    private Date       weekEnd; //��ֹ��
    private String     submitStatus; //�ύ״̬
    private int        projectID; //��Ŀ����
    private BigDecimal recWorkHours; //��Ŀ���й�ʱ

    public DtoQueryCondition() {
    }

    public boolean isInit() {
        return init;
    }

    public void setInit(boolean init) {
        this.init = init;
    }

    public boolean isPM() {
        return pm;
    }

    public void setPM(boolean pm) {
        this.pm = pm;
    }

    public Date getWeekEnd() {
        return weekEnd;
    }

    public void setWeekEnd(Date weekEnd) {
        this.weekEnd = weekEnd;
    }

    public Date getWeekStart() {
        return weekStart;
    }

    public void setWeekStart(Date weekStart) {
        this.weekStart = weekStart;
    }

    public int getProjectID() {
        return projectID;
    }

    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }

    public String getSubmitStatus() {
        return submitStatus;
    }

    public void setSubmitStatus(String submitStatus) {
        this.submitStatus = submitStatus;
    }

    public BigDecimal getRecWorkHours() {
        return recWorkHours;
    }

    public void setRecWorkHours(BigDecimal recWorkHours) {
        this.recWorkHours = recWorkHours;
    }
}
