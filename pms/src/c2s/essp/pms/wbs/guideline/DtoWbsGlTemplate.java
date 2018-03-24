package c2s.essp.pms.wbs.guideline;

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
public class DtoWbsGlTemplate {
    private Long rid;
    private String acntId;
    private String isTemplate;
    public DtoWbsGlTemplate() {
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public void setAcntId(String acntId) {
        this.acntId = acntId;
    }

    public void setIsTemplate(String isTemplate) {
        this.isTemplate = isTemplate;
    }

    public Long getRid() {
        return rid;
    }

    public String getAcntId() {
        return acntId;
    }

    public String getIsTemplate() {
        return isTemplate;
    }
}
