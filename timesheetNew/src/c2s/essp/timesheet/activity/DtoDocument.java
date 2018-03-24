package c2s.essp.timesheet.activity;

import c2s.dto.DtoBase;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author not attributable
 * @version 1.0
 */
public class DtoDocument extends DtoBase{
    private String title;
    private String statusName;
    private String isWorkProduct;
    private Boolean isWProduct;
    private Long projectRid;
    private Long documentRid;

    public String getIsWorkProduct() {
        return isWorkProduct;
    }

    public String getStatusName() {
        return statusName;
    }

    public String getTitle() {
        return title;
    }

    public Long getProjectRid() {
        return projectRid;
    }

    public Long getDocumentRid() {
        return documentRid;
    }

    public Boolean getIsWProduct() {
        return isWProduct;
    }

    public void setIsWorkProduct(String isWorkProduct) {
        this.isWorkProduct = isWorkProduct;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setProjectRid(Long projectRid) {
        this.projectRid = projectRid;
    }

    public void setDocumentRid(Long documentRid) {
        this.documentRid = documentRid;
    }

    public void setIsWProduct(Boolean isWProduct) {
        this.isWProduct = isWProduct;
    }


}
