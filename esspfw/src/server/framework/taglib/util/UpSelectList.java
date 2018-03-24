package server.framework.taglib.util;

import server.framework.taglib.util.SelectContentInterface;

public class UpSelectList implements SelectContentInterface{
    public UpSelectList() {
    }

    private int id;
    private String optionValue;
    private String optionShow;
    private String upId;

    public UpSelectList(int id, String optionValue, String optionShow,String upId) {
        this.setId(id);
        this.setOptionValue(optionValue);
        this.setOptionShow(optionShow);
        setUpId(upId);
    }

    public String getValue() {
        return this.getOptionValue();
    }


    public String getUp() {
        return getUpId();
    }

    public String getShow(){
        return optionShow==null?"":optionShow;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setOptionValue(String optionValue) {

        this.optionValue = optionValue;
    }

    public void setOptionShow(String optionShow) {

        this.optionShow = optionShow;
    }

    public void setUpId(String upId) {
        this.upId = upId;
    }

    public int getId() {
        return id;
    }

    public String getOptionValue() {

        return optionValue;
    }

    public String getOptionShow() {

        return optionShow;
    }

    public String getUpId() {
        return upId;
    }

}
