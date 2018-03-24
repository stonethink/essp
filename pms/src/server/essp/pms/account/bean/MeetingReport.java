package server.essp.pms.account.bean;



public class MeetingReport {
    private String meetingTopic;
    private String meetingSponsor;
    private String meetingPrincipal;
    private String meetingParticipator;
    private String meetingDescription;
    private String attachmentName;
    private String attachmentURL;
    private String meetingDate;
    private String meetingTime;
    public MeetingReport() {
    }

    public void setMeetingTopic(String meetingTopic) {
        this.meetingTopic = meetingTopic;
    }

    public void setMeetingSponsor(String meetingSponsor) {
        this.meetingSponsor = meetingSponsor;
    }

    public void setMeetingPrincipal(String meetingPrincipal) {
        this.meetingPrincipal = meetingPrincipal;
    }

    public void setMeetingParticipator(String meetingParticipator) {
        this.meetingParticipator = meetingParticipator;
    }

    public void setMeetingDescription(String meetingDescription) {
        this.meetingDescription = meetingDescription;
    }

    public void setAttachmentName(String attachmentName) {
        this.attachmentName = attachmentName;
    }

    public void setAttachmentURL(String attachmentURL) {
        this.attachmentURL = attachmentURL;
    }

    public void setMeetingDate(String meetingDate) {
        this.meetingDate = meetingDate;
    }

    public void setMeetingTime(String meetingTime) {
        this.meetingTime = meetingTime;
    }

    public String getMeetingTopic() {
        return meetingTopic;
    }

    public String getMeetingSponsor() {
        return meetingSponsor;
    }

    public String getMeetingPrincipal() {
        return meetingPrincipal;
    }

    public String getMeetingParticipator() {
        return meetingParticipator;
    }

    public String getMeetingDescription() {
        return meetingDescription;
    }

    public String getAttachmentName() {
        return attachmentName;
    }

    public String getAttachmentURL() {
        return attachmentURL;
    }

    public String getMeetingDate() {
        return meetingDate;
    }

    public String getMeetingTime() {
        return meetingTime;
    }
}
