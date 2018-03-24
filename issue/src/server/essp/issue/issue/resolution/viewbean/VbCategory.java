package server.essp.issue.issue.resolution.viewbean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import server.framework.taglib.util.SelectOptionImpl;

public class VbCategory {
    private String category;
    private String value;
    private String description;
    private List valueOptions = new ArrayList();
    private HashMap descriptionOfvalueOptions=new HashMap();

    public VbCategory() {
        valueOptions.add(new SelectOptionImpl(
            "", ""));
    }

    public String getCategory() {
        return category;
    }

    public String getValue() {
        return value;
    }

    public List getValueOptions() {
        return valueOptions;
    }

    public String getDescription() {
        return description;
    }

    public HashMap getDescriptionOfvalueOptions() {
        return descriptionOfvalueOptions;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setValueOptions(List valueOptions) {
        this.valueOptions = valueOptions;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDescriptionOfvalueOptions(HashMap descriptionOfvalueOptions) {
        this.descriptionOfvalueOptions = descriptionOfvalueOptions;
    }
}
