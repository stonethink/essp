package server.essp.pms.account.bean;

import java.util.Date;

public class Timing {
    private String webID;
    private String webName;
    private String activity;
    private Date start;
    private Date finish;
    private String predecessors;
    private String successors;
    private String milestoneMark;
    private String principal;
    private String remarks;
    public Timing() {
    }

    public void setWebID(String webID) {
        this.webID = webID;
    }

    public void setWebName(String webName) {
        this.webName = webName;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public void setFinish(Date finish) {
        this.finish = finish;
    }

    public void setPredecessors(String predecessors) {
        this.predecessors = predecessors;
    }

    public void setSuccessors(String successors) {
        this.successors = successors;
    }

    public void setMilestoneMark(String milestoneMark) {
        this.milestoneMark = milestoneMark;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getWebID() {
        return webID;
    }

    public String getWebName() {
        return webName;
    }

    public String getActivity() {
        return activity;
    }

    public Date getStart() {
        return start;
    }

    public Date getFinish() {
        return finish;
    }

    public String getPredecessors() {
        return predecessors;
    }

    public String getSuccessors() {
        return successors;
    }

    public String getMilestoneMark() {
        return milestoneMark;
    }

    public String getPrincipal() {
        return principal;
    }

    public String getRemarks() {
        return remarks;
    }
}
