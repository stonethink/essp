package server.essp.tc.mail.genmail.contbean;

/**
 *此类用于工作流未处理催促EMAIL的内容Bean
 *author:Robin.Zhang
 */
public class WorkFlowBean {
    private String submiter;
    private String title;
    private String desc;
    public WorkFlowBean() {
    }

    public void setSubmiter(String submiter) {
        this.submiter = submiter;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getSubmiter() {
        return submiter;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }
}
