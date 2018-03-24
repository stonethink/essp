package c2s.essp.report;

import c2s.dto.*;

/** @author Hibernate CodeGenerator */
public class DtoSysReport  extends DtoBase {

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

    private String url;

    /** full constructor */
    public DtoSysReport(String id, String upId, String link, String name, String code, String description, Double positionx, Double positiony, Double length, Double hight) {
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
    public DtoSysReport() {
    }

    /** minimal constructor */
    public DtoSysReport(String id) {
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

    public String getUrl() {

        if ( this.link == null ||this.link.equals("") ){
            return this.name;
        }

        this.url = "<a style=cursor:hand onclick= "
                  + " window.open(\"" + this.link + "\",\"" + "\","
                  + "\"width=" + this.length  + ",height=" + this.hight + ","
                  + "top=" + this.positionx + ",left=" + this.positiony + ","
                  + "resizable=yes,scrollbars=yes\")>";
        this.url = this.url + this.name + "</a>";
        return url;
    }

    public void setHight(Double hight) {
        this.hight = hight;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String toString(){
        return this.id;
    }


}
