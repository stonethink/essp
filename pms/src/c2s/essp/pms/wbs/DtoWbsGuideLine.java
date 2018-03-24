package c2s.essp.pms.wbs;

import c2s.dto.DtoBase;

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
public class DtoWbsGuideLine extends DtoBase{
    private String template;
    private String wbs;
    private String description;
    private Long acntRid;
    private Long refAcntRid;
    private Long refWbsRid;
    private String refWbsId;
    private String refAcntId;
    private Long refActivityRid;
    private String refActivityId;
    private String isTemplate;
    private String isQuality;
    private Long wbsRid;
    private Long activityRid;
    private Long otherRid;
    private String beLongTo;
    public DtoWbsGuideLine() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public void setWbs(String wbs) {
        this.wbs = wbs;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAcntRid(Long acntRid) {
        this.acntRid = acntRid;
    }

    public void setRefAcntRid(Long refAcntRid) {
        this.refAcntRid = refAcntRid;
    }

    public void setRefWbsRid(Long refWbsRid) {
        this.refWbsRid = refWbsRid;
    }

    public void setRefWbsId(String refWbsId) {
        this.refWbsId = refWbsId;
    }

    public void setRefAcntId(String refAcntId) {
        this.refAcntId = refAcntId;
    }

    public void setRefActivityRid(Long refActivityRid) {
        this.refActivityRid = refActivityRid;
    }

    public void setRefActivityId(String refActivityId) {
        this.refActivityId = refActivityId;
    }

    public void setIsTemplate(String isTemplate) {
        this.isTemplate = isTemplate;
    }

    public void setIsQuality(String isQuality) {
        this.isQuality = isQuality;
    }

    public void setWbsRid(Long wbsRid) {
        this.wbsRid = wbsRid;
    }

    public void setActivityRid(Long activityRid) {
        this.activityRid = activityRid;
    }

    public void setOtherRid(Long otherRid) {
        this.otherRid = otherRid;
    }

    public void setBeLongTo(String beLongTo) {
        this.beLongTo = beLongTo;
    }

    public String getTemplate() {
        return template;
    }

    public String getWbs() {
        return wbs;
    }

    public String getDescription() {
        return description;
    }

    public Long getAcntRid() {
        return acntRid;
    }

    public Long getRefAcntRid() {
        return refAcntRid;
    }

    public Long getRefWbsRid() {
        return refWbsRid;
    }

    public String getRefWbsId() {
        return refWbsId;
    }

    public String getRefAcntId() {
        return refAcntId;
    }

    public Long getRefActivityRid() {
        return refActivityRid;
    }

    public String getRefActivityId() {
        return refActivityId;
    }

    public String getIsTemplate() {
        return isTemplate;
    }

    public String getIsQuality() {
        return isQuality;
    }

    public Long getWbsRid() {
        return wbsRid;
    }

    public Long getActivityRid() {
        return activityRid;
    }

    public Long getOtherRid() {
        return otherRid;
    }

    public String getBeLongTo() {
        return beLongTo;
    }

    private void jbInit() throws Exception {
    }
}
