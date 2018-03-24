package server.framework.taglib.util;

import server.framework.taglib.util.SelectContentInterface;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author Robin.Zhang
 * @version 1.0
 */
public class SelectList implements SelectContentInterface {
    private int id;
    private String optionValue;
    private String optionShow;

    public SelectList() {
    }

    public SelectList(int id, String optionValue, String optionShow) {
        this.setId(id);
        this.setOptionValue(optionValue);
        this.setOptionShow(optionShow);
    }

    public String getValue() {
        return this.getOptionValue();
    }


    public String getUp() {
        return "";
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

    public int getId() {
        return id;
    }

    public String getOptionValue() {

        return optionValue;
    }

    public String getOptionShow() {

        return optionShow;
    }


}
