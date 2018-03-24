package c2s.essp.ebs;

import c2s.dto.DtoBase;
import java.util.Date;

public class DtoEbsEbs extends DtoBase implements IDtoEbsAcnt{
    Long rid				;
    String ebsId            ;
    String ebsName          ;
    String ebsManager       ;
    String ebsStatus       ;
    String ebsDescription   ;
    Long ebsParentRid      ;

    Long theLevel;

    public Long getTheLevel() {
        return theLevel;
    }

    public void setTheLevel(Long theLevel) {
        this.theLevel = theLevel;
    }

    public Long getRid() {
        return rid;
    }

    public String getEbsId() {
        return ebsId;
    }

    public String getEbsManager() {
        return ebsManager;
    }

    public Long getEbsParentRid() {
        return ebsParentRid;
    }

    public String getEbsName() {
        return ebsName;
    }

    public String getEbsStatus() {
        return ebsStatus;
    }

    public void setEbsStatus(String ebsStatus) {
        this.ebsStatus = ebsStatus;
    }

    public void setEbsDescription(String ebsDescription) {
        this.ebsDescription = ebsDescription;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public void setEbsId(String ebsId) {
        this.ebsId = ebsId;
    }

    public void setEbsManager(String ebsManager) {
        this.ebsManager = ebsManager;
    }

    public void setEbsParentRid(Long ebsParentRid) {
        this.ebsParentRid = ebsParentRid;
    }

    public void setEbsName(String ebsName) {
        this.ebsName = ebsName;
    }

    public String getEbsDescription() {
        return ebsDescription;
    }


    public String getStatus(){
        return this.ebsStatus;
    }

    public void setStatus( String status ){
        this.ebsStatus = status;
    }

    public String getManager(){
        return this.ebsManager;
    }

    public void setManager( String manager ){
        this.ebsManager = manager;
    }

    public String getCode(){
        return this.ebsId;
    }

    public void setCode( String code ){
        this.ebsId = code;
    }

    public String getName(){
        return this.ebsName;
    }

    public void setName( String name ){
        this.ebsName = name;
    }

    public String toString(){
        if(this.getCode() == null ){
            return "";
        }else{
            return this.getCode();
        }
    }

    public boolean isEbs(){
        return true;
    }

    public boolean isAcnt(){
        return false;
    }
}
