package server.essp.issue.typedefine.form;

import org.apache.struts.action.ActionForm;

public class AfType extends ActionForm {
   private String description;
   private String sequence;
   private String rst;
   private String typeName;
    private String saveStatusHistory;
    private String saveInfluenceHistory;
    public String getTypeName() {
        if ((typeName==null)||(typeName.equalsIgnoreCase("null"))){
          typeName="";
        }
        return this.typeName;
    }

    public void setDescription(String description) {
        this.description=description;
    }

    public String getSequence() {
        if ((sequence==null)||(sequence.equalsIgnoreCase("null"))){
          sequence="";
        }
        return this.sequence.trim();
    }

    public String getDescription() {
        if ((description==null)||(description.equalsIgnoreCase("null"))){
          description="";
        }
        return this.description.trim();
    }

    public String getRst() {
        if ((rst==null)||(rst.equalsIgnoreCase("null"))){
          rst="";
        }

        return rst;
    }

    public String getSaveStatusHistory() {
        return saveStatusHistory;
    }

    public String getSaveInfluenceHistory() {
        return saveInfluenceHistory;
    }

    public void setSequence(String sequence) {
      this.sequence=sequence;
    }

    public void setRst(String rst) {
      this.rst=rst;
    }

    public void setTypeName(String typename) {
      this.typeName=typename;
    }

    public void setSaveStatusHistory(String saveStatusHistory) {
        this.saveStatusHistory = saveStatusHistory;
    }

    public void setSaveInfluenceHistory(String saveInfluenceHistory) {
        this.saveInfluenceHistory = saveInfluenceHistory;
    }

}
