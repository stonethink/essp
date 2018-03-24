package server.essp.issue.typedefine.viewbean;

public class VbType {
    private String typeName;
    private String sequence;
    private String description;
    private String rid;
    private String status;
    private String saveStatusHistory;
    private String saveInfluenceHistory;

    public String getDescription() {
        if ((description==null)||(description.equalsIgnoreCase("null"))){
         description="";
       }
        return this.description;
    }

    public void setRid(String rid) {
        this.rid=rid;
    }

    public void setDescription(String description) {
        this.description=description;
    }

    public String getSequence() {
        if ((sequence==null)||(sequence.equalsIgnoreCase("null"))){
         sequence="";
       }
        return this.sequence;
    }

    public void setTypeName(String typeName) {
        this.typeName=typeName;
    }

    public void setSequence(String sequence) {
        this.sequence=sequence;
    }

    public String getRid() {
        return this.rid;
    }

    public String getTypeName() {
        if ((typeName==null)||(typeName.equalsIgnoreCase("null"))){
          typeName="";
        }
        return this.typeName;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setSaveInfluenceHistory(String saveInfluenceHistory) {
        this.saveInfluenceHistory = saveInfluenceHistory;
    }

    public void setSaveStatusHistory(String saveStatusHistory) {
        this.saveStatusHistory = saveStatusHistory;
    }

    public String getStatus() {
        return status;
    }

    public String getSaveInfluenceHistory() {
        return saveInfluenceHistory;
    }

    public String getSaveStatusHistory() {
        return saveStatusHistory;
    }

    public VbType() {
    }
}
