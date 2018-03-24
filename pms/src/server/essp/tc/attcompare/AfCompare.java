package server.essp.tc.attcompare;

import org.apache.struts.action.ActionForm;

public class AfCompare extends ActionForm{
    private String beginDate;
    private String endDate;
    public AfCompare() {
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

}
