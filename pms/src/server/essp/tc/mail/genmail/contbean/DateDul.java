package server.essp.tc.mail.genmail.contbean;

/**
 *此类用于周报催促EMAIL的内容Bean
 *author:Robin.Zhang
 */
public class DateDul {
    private String beginDate;
    private String endDate;
    private String type;
    public DateDul() {
    }

    public void setBeginDate(String beginDate) {

        this.beginDate = beginDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBeginDate() {

        return beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getType() {
        return type;
    }
}
