package server.essp.issue.typedefine.category.viewbean;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class VbCategory {
    private String categoryName;
    private String description;
    private String sequence;
    private String typeName;
    public String getCategoryName() {
        return categoryName;
    }

    public String getDescription() {
        return description;
    }

    public String getSequence() {
        return sequence;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
