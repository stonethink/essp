package c2s.essp.pms.activity;

import java.util.Date;

import c2s.dto.DtoBase;

import c2s.essp.common.calendar.WorkCalendar;

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
public class DtoMilestone extends DtoBase {

    private String name;
    private String type;
    private Date planStart;
    private Date planFinish;
    private Date antiStart;
    private Date antiFinish;
    private Date compeleteDate;
    private String status;
    private String remark;
    public DtoMilestone() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPlanStart(Date planStart) {
        this.planStart = planStart;
    }

    public void setPlanFinish(Date planFinish) {
        this.planFinish = planFinish;
    }

    public void setAntiStart(Date antiStart) {
        this.antiStart = antiStart;
    }

    public void setAntiFinish(Date antiFinish) {
        this.antiFinish = antiFinish;
    }

    public void setCompeleteDate(Date compeleteDate) {

        this.compeleteDate = compeleteDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setReachedCondition(String reachedCondition) {
        this.reachedCondition = reachedCondition;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public Date getPlanStart() {
        return planStart;
    }

    public Date getPlanFinish() {
        return planFinish;
    }

    public Date getAntiStart() {
        return antiStart;
    }

    public Date getAntiFinish() {
        return antiFinish;
    }

    public Date getCompeleteDate() {

        return compeleteDate;
    }

    /**
     * 期望完成日期-实际完成日期
     */
    public String getVariance() {
        if (compeleteDate == null || antiFinish == null) {
            return "";
        }
        return WorkCalendar.calculateTimeLimit(antiFinish,
            compeleteDate) + "";
    }

    /**
     * 计算Milestone的状态:
     * 如果完成日期不为空，状态为Reached,否则
     * 若期望结束日期早与今天，则状态为Delay
     * 否则为Normal
     */
    public String getStatus() {
        if (status != null) {
            return status;
        }
        if (compeleteDate != null) {
            return MILESTONE_REACHED;
        }
        Date today = new Date();
        if (antiFinish != null && today.getTime() > antiFinish.getTime()) {
            return MILESTONE_DELAY;
        } else {
            return MILESTONE_NORMAL;
        }
    }

    public String getRemark() {
        return remark;
    }

    public String getCode() {
        return code;
    }

    public String getReachedCondition() {
        return reachedCondition;
    }

    public String getTypeName(){
        return parseType(type);
    }
    private String parseType(String type) {
        if (type == null) {
            return "";
        } else if (type.equals("P")) {
            return "Payment";
        } else if (type.equals("D")) {
            return "Delivery";
        } else if (type.equals("O")) {
            return "Other";
        }
        return "";
    }
    public static final String MILESTONE_DELETED = "Deleted";
    public static final String MILESTONE_DELAY = "Delay";
    public static final String MILESTONE_REACHED = "Reached";
    public static final String MILESTONE_NORMAL = "Normal";

    public static final String[] TYPE_NAMES = {"Delivery milestone",
                                              "Payment milestone",
                                              "Other milestone"};
    public static final String[] TYPE_VALUES = {"D", "P", "O"};
    /**
     * 是否是MileStone
     */
    public static final String IS_MILESTONE = "1";
    public static final String NOT_MILESTONE = "0";
    private String code;
    private String reachedCondition;

}
