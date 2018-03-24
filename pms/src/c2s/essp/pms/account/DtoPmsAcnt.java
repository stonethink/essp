package c2s.essp.pms.account;

import java.util.Date;

import c2s.dto.DtoBase;
import c2s.essp.common.account.IDtoAccount;


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

public class DtoPmsAcnt extends DtoBase implements IDtoAccount{

   private Long rid;
   private String id;
   private String name;
   private String currency;
   private String type;
   private String organization;
   private Date anticipatedStart;
   private Date anticipatedFinish;
   private Date plannedStart;
   private Date plannedFinish;
   private Date actualStart;
   private Date actualFinish;
   private String status;
   private String brief;
   private String inner;
   private String showStyle;
   private String acntDisplayName;
   private String manager;
   private boolean isOSP = false;
   private boolean isTemplate;



//  DtoAccount dtoAccount;
    String loginId;
    boolean management = false;
    boolean pm = false;
    boolean participant = false;
    boolean keyPersonal = false;

    boolean selected;
    private String acntTailor;


    public Date getActualFinish() {
        return actualFinish;
    }

    public Date getActualStart() {
        return actualStart;
    }

    public Date getAnticipatedFinish() {
        return anticipatedFinish;
    }

    public Date getAnticipatedStart() {
        return anticipatedStart;
    }

    public String getBrief() {
        return brief;
    }

    public String getCurrency() {
        return currency;
    }

    public String getId() {
        return id;
    }

    public String getInner() {
        return inner;
    }

    public boolean isKeyPersonal() {
        return keyPersonal;
    }

    public String getLoginId() {
        return loginId;
    }

    public boolean isManagement() {
        return management;
    }

    public String getManager() {
         return manager;
    }

    public String getName() {
        return name;
    }

    public String getOrganization() {
        return organization;
    }

    public boolean isParticipant() {
        return participant;
    }

    public Date getPlannedFinish() {
        return plannedFinish;
    }

    public Date getPlannedStart() {
        return plannedStart;
    }
    public void setManager(String manager) {
        this.manager = manager;
    }
    public boolean isPm() {
        return pm;
    }


    public Long getRid() {
        return rid;
    }

    public String getStatus() {
        return status;
    }

    public String getType() {
        return type;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setActualFinish(Date actualFinish) {
        this.actualFinish = actualFinish;
    }

    public void setActualStart(Date actualStart) {
        this.actualStart = actualStart;
    }

    public void setAnticipatedFinish(Date anticipatedFinish) {
        this.anticipatedFinish = anticipatedFinish;
    }

    public void setAnticipatedStart(Date anticipatedStart) {
        this.anticipatedStart = anticipatedStart;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setInner(String inner) {
        this.inner = inner;
    }

    public void setKeyPersonal(boolean keyPersonal) {
        this.keyPersonal = keyPersonal;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public void setManagement(boolean management) {
        this.management = management;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public void setParticipant(boolean participant) {
        this.participant = participant;
    }

    public void setPlannedFinish(Date plannedFinish) {
        this.plannedFinish = plannedFinish;
    }

    public void setPlannedStart(Date plannedStart) {
        this.plannedStart = plannedStart;
    }

    public void setPm(boolean pm) {
        this.pm = pm;
    }


    public void setRid(Long rid) {
        this.rid = rid;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setType(String type) {
        this.type = type;
    }

   public void setSelected(boolean selected) {
        this.selected = selected;
    }
    /**
     * 根据showStyle生成Account的显示名称
     * @return String
     */
    public String getDisplayName(){
        if(showStyle == null)
            showStyle = IDtoAccount.SHOWSTYLE_DASHED;
        if(showStyle.indexOf("id") == -1 && showStyle.indexOf("name") == -1){
            acntDisplayName = name;
        }else{
            acntDisplayName = showStyle.replaceAll("id", id);
            acntDisplayName = acntDisplayName.replaceAll("name", name);
        }
        return acntDisplayName;
    };

    public String toString(){
            return getDisplayName();
    }

    public void setIsTemplate(boolean flag){
        this.isTemplate=flag;
    }
    public void setIsOSP(boolean flag){
        this.isOSP = flag;
    }
    public void setShowStyle(String showStyle) {
        this.showStyle = showStyle;
    }

    public void setAcntTailor(String acntTailor) {
        this.acntTailor = acntTailor;
    }

    public boolean isTemplate(){
        return this.isTemplate;
    }
    public boolean isOSP(){
        return this.isOSP;
    }
    public String getShowStyle() {
        return showStyle;
    }

    public String getAcntTailor() {
        return acntTailor;
    }
}
