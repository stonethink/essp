package c2s.essp.common.workflow;

import java.text.DecimalFormat;
import java.util.Date;
import com.wits.util.comDate;
import c2s.dto.DtoBase;
import java.util.ArrayList;
import java.util.List;

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
public class DtoActivityDef extends DtoBase implements IActivityDefine {
    private static int SEED = 0;

    private String activityID;
    private String activityName;
    private IParticipant participant;
    private String processClassName;
    private List children;
    private List Parents;
    private boolean start;
    private boolean end;
    private String startMode;
    private String endMode;

    public DtoActivityDef() {
        this.setStartMode(this.MODE_AUTO);
        this.setEndMode(this.MODE_MENU);
        this.setStart(false);
        this.setEnd(false);
        this.getActivityDefaultID();
        this.setActivityName("");
    }

    private static synchronized String getSeed(){
        SEED ++ ;
        SEED=SEED%10000;
        DecimalFormat format=new DecimalFormat("00000");
        return format.format(SEED);
     }

     public String getActivityDefaultID(){
         return comDate.formatSqlTimestamp(new Date(),"yyyyMMddhhmmssSSS") + "-" + this.getSeed();
     }

     public void addChildren(IActivityDefine iActivityDefine){
         if ( this.getChildren() == null ) {
             this.setChildren(new ArrayList());
         }

         if ( iActivityDefine.getParents() == null ) {
             iActivityDefine.setParents(new ArrayList());
         }

         iActivityDefine.getParents().add(this);
         this.getChildren().add(iActivityDefine);
     }

    public String getActivityID() {
        return "";
    }

    public String getActivityName() {
        return "";
    }

    public IParticipant getParticipant() {
        return null;
    }

    public String getProcessClassName() {
        return "";
    }

    public List getChildren() {
        return null;
    }

    public List getParents() {
        return null;
    }

    public boolean isStart() {
        return false;
    }

    public boolean isEnd() {
        return false;
    }

    public String getStartMode() {
        return "";
    }

    public String getEndMode() {
        return "";
    }

    public void setStart(boolean start) {
        this.start = start;
    }

    public void setEnd(boolean end) {
        this.end = end;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public void setChildren(List children) {
        this.children = children;
    }

    public void setParents(List parents) {
        this.Parents = parents;
    }

    public void setParticipant(IParticipant participant) {
        this.participant = participant;
    }

    public void setProcessClassName(String processClassName) {
        this.processClassName = processClassName;
    }

    public void setStartMode(String startMode) {
        this.startMode = startMode;
    }

    public void setEndMode(String endMode) {
        this.endMode = endMode;
    }

    public void setActivityID(String activityID) {
        this.activityID = activityID;
    }

}
