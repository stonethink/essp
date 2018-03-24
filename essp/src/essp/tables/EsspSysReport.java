package essp.tables;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import server.framework.hibernate.HBComAccess;

/** @author Hibernate CodeGenerator */
public class EsspSysReport implements Serializable {

    /** identifier field */
    private String id;

    /** nullable persistent field */
    private String upId;

    /** nullable persistent field */
    private String link;

    /** nullable persistent field */
    private String name;

    /** nullable persistent field */
    private String code;

    /** nullable persistent field */
    private String description;

    /** nullable persistent field */
    private Double positionx;

    /** nullable persistent field */
    private Double positiony;

    /** nullable persistent field */
    private Double length;

    /** nullable persistent field */
    private Double hight;

    /** full constructor */
    public EsspSysReport(String id, String upId, String link, String name, String code, String description, Double positionx, Double positiony, Double length, Double hight) {
        this.id = id;
        this.upId = upId;
        this.link = link;
        this.name = name;
        this.code = code;
        this.description = description;
        this.positionx = positionx;
        this.positiony = positiony;
        this.length = length;
        this.hight = hight;
    }

    /** default constructor */
    public EsspSysReport() {
    }

    /** minimal constructor */
    public EsspSysReport(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUpId() {
        return this.upId;
    }

    public void setUpId(String upId) {
        this.upId = upId;
    }

    public String getLink() {
        return this.link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPositionx() {
        return this.positionx;
    }

    public void setPositionx(Double positionx) {
        this.positionx = positionx;
    }

    public Double getPositiony() {
        return this.positiony;
    }

    public void setPositiony(Double positiony) {
        this.positiony = positiony;
    }

    public Double getLength() {
        return this.length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public Double getHight() {
        return this.hight;
    }

    public void setHight(Double hight) {
        this.hight = hight;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof EsspSysReport) ) return false;
        EsspSysReport castOther = (EsspSysReport) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

    public static void main(String[] args){
        HBComAccess db = new HBComAccess();
        EsspSysReport  reprt = new EsspSysReport("test");
        reprt.setCode("test");
        reprt.setName("test");
        reprt.setDescription("ddd");
        try {
            db.save(reprt);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
