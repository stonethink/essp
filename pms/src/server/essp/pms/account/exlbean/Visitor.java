package server.essp.pms.account.exlbean;



public class Visitor {
    private String visitor;
    private String interviewee;
    private String satisfactionDegree;
    private String feedbackBy;
    private String visitingDate;
    private String filledDate;
    public Visitor() {
    }

    public void setVisitor(String visitor) {
        this.visitor = visitor;
    }

    public void setInterviewee(String interviewee) {
        this.interviewee = interviewee;
    }

    public void setSatisfactionDegree(String satisfactionDegree) {
        this.satisfactionDegree = satisfactionDegree;
    }

    public void setFeedbackBy(String feedbackBy) {
        this.feedbackBy = feedbackBy;
    }

    public void setVisitingDate(String visitingDate) {
        this.visitingDate = visitingDate;
    }

    public void setFilledDate(String filledDate) {
        this.filledDate = filledDate;
    }

    public String getVisitor() {
        return visitor;
    }

    public String getInterviewee() {
        return interviewee;
    }

    public String getSatisfactionDegree() {
        return satisfactionDegree;
    }

    public String getFeedbackBy() {
        return feedbackBy;
    }

    public String getVisitingDate() {
        return visitingDate;
    }

    public String getFilledDate() {
        return filledDate;
    }
}
