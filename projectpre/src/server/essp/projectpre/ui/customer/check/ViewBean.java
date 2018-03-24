package server.essp.projectpre.ui.customer.check;

public class ViewBean {
    private String option;
    private String beforeChange;
    private String afterChange;
    public String getAfterChange() {
        return afterChange;
    }
    public void setAfterChange(String afterChange) {
        this.afterChange = afterChange;
    }
    public String getBeforeChange() {
        return beforeChange;
    }
    public void setBeforeChange(String beforeChange) {
        this.beforeChange = beforeChange;
    }
    public String getOption() {
        return option;
    }
    public void setOption(String option) {
        this.option = option;
    }
}
