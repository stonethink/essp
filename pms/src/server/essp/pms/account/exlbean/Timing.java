package server.essp.pms.account.exlbean;

import java.util.Date;

import com.wits.excel.ICellStyleAware;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

public class Timing implements ICellStyleAware{
    private String code;
    private String wbsName;
    private String activityName;
    private Date plannedStart;
    private Date plannedFinish;
    private String milestoneImage;//milestone对应的小图片名
    private String manager;
    private Double weight;
    private String brief;
    private Double completeRate;
    private String completeMethod;
    private boolean wbs = false;

    public Timing() {
    }


    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public void setWbsName(String wbsName) {
        this.wbsName = wbsName;
    }

    public void setPlannedStart(Date plannedStart) {
        this.plannedStart = plannedStart;
    }

    public void setPlannedFinish(Date plannedFinish) {
        this.plannedFinish = plannedFinish;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public void setCompleteRate(Double completeRate) {
        this.completeRate = completeRate;
    }

    public void setCompleteMethod(String completeMethod) {
        this.completeMethod = completeMethod;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }


    public String getActivityName() {
        return activityName;
    }

    public String getBrief() {
        return brief;
    }

    public String getCode() {
        return code;
    }

    public String getCompleteMethod() {
        return completeMethod;
    }

    public Double getCompleteRate() {
        return completeRate;
    }

    public String getManager() {
        return manager;
    }

    public Date getPlannedFinish() {
        return plannedFinish;
    }

    public Date getPlannedStart() {
        return plannedStart;
    }

    public String getWbsName() {
        return wbsName;
    }

    public Double getWeight() {
        return weight;
    }

    public boolean isWbs(){
        return wbs;
    }

    public String getMilestoneImage() {
        return milestoneImage;
    }

    public boolean isActivity(){
        return !isWbs();
    }
    public void setWbs(boolean iswbs){
        this.wbs = iswbs;
    }

    public void setMilestoneImage(String milestoneImage) {
        this.milestoneImage = milestoneImage;
    }

    //设置单元格样式
    //1.所有WBS都用黄底色
    //2.milestone,用红色标识
    public void setCellStyle(String propertyName, Object bean, HSSFWorkbook wb,
                             HSSFCellStyle cellStyle) {
        if(isWbs()){
            cellStyle.setFillPattern((short) HSSFCellStyle.SOLID_FOREGROUND);
            cellStyle.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
            return;
        }

        if(milestoneImage != null){
            cellStyle.setFillPattern((short) HSSFCellStyle.SOLID_FOREGROUND);
            cellStyle.setFillForegroundColor(HSSFColor.ROSE.index);

            return;
        }
    }

}
