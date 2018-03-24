package c2s.essp.pwm.wp;

/**
 * <p>Title: essp system</p>
 * <p>Description: essp</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company:  Wistron ITS Wuhan SDC</p>
 * @author wenhuike@wistronits.com
 * @version 1.0
 */

import java.io.*;
import java.math.BigDecimal;

public class DtoDailyReport implements Serializable {

  private String planItemID;
  private java.util.Date planDate;
  public java.sql.Timestamp beginTime = null;
  public java.sql.Timestamp endTime = null;
  private String place;
  private String title;
  private String type;
  private String recorderID;
  private String ownerID;
  private String ownerName;
  private String wpID;
  private BigDecimal wkitemWkhours = new BigDecimal( Double.parseDouble("0"));

  private String pwpName;
  private String activityID;
  private String activityName;

  private int from = 1;

  public DtoDailyReport() {
  }
  private void writeObject(ObjectOutputStream oos) throws IOException {
    oos.defaultWriteObject();
  }
  private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
    ois.defaultReadObject();
  }
  public String getPlanItemID() {
    return planItemID;
  }
  public void setPlanItemID(String planItemID) {
    this.planItemID = planItemID;
  }
  public java.util.Date getPlanDate() {
    return planDate;
  }
  public void setPlanDate(java.util.Date planDate) {
    this.planDate = planDate;
  }
  public java.util.Date getBeginTime() {
    return beginTime;
  }
  public void setBeginTime(java.util.Date beginTime) {
    this.beginTime = new java.sql.Timestamp(beginTime.getTime());
  }
  public java.util.Date getEndTime() {
    return endTime;
  }
  public void setEndTime(java.util.Date endTime) {
    this.endTime = new java.sql.Timestamp(endTime.getTime());
  }
  public String getPlace() {
    return place;
  }
  public void setPlace(String place) {
    this.place = place;
  }
  public String getTitle() {
    return title;
  }
  public void setTitle(String title) {
    this.title = title;
  }
  public String getType() {
    return type;
  }
  public void setType(String type) {
    this.type = type;
  }
  public String getRecorderID() {
    return recorderID;
  }
  public void setRecorderID(String recorderID) {
    this.recorderID = recorderID;
  }
  public String getOwnerID() {
    return ownerID;
  }
  public void setOwnerID(String ownerID) {
    this.ownerID = ownerID;
  }
  public String getOwnerId() {
    return ownerID;
  }
  public void setOwnerId(String ownerId) {
    this.ownerID = ownerId;
  }

  public String getWpID() {
    return wpID;
  }
  public void setWpID(String wpID) {
    this.wpID = wpID;
  }
  public int getFrom() {
    return from;
  }
  public void setFrom(int from) {
    this.from = from;
  }
  public String getPwpName() {
    return pwpName;
  }
  public void setPwpName(String pwpName) {
    this.pwpName = pwpName;
  }
  public String getActivityID() {
    return activityID;
  }
  public void setActivityID(String activityID) {
    this.activityID = activityID;
  }
  public String getActivityName() {
    return activityName;
  }
  public void setActivityName(String activityName) {
    this.activityName = activityName;
  }
    public BigDecimal getWkitemWkhours() {
        return wkitemWkhours;
    }
    public void setWkitemWkhours(BigDecimal wkitemWkhours) {
        this.wkitemWkhours = wkitemWkhours;
    }
    public String getOwnerName() {
        return ownerName;
    }
    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

}
