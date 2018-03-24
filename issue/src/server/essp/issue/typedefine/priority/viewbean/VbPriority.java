package server.essp.issue.typedefine.priority.viewbean;

public class VbPriority {

    private String description;
    private String status;
    private String sequence;
    private String duration;
    private String priority;
    private String typeName;


    public VbPriority() {
    }

    public void setTypeName(String typeName){
      this.typeName=typeName;
    }
    public String getTypeName(){
        if ((typeName==null)||(typeName.equalsIgnoreCase("null"))){
         typeName="";
       }
      return typeName;
    }

    public void setPriority(String priority){
             this.priority= priority;
    }

    public void setDescription(String description){
            this.description= description;
    }

    public void setRst(String status){
           this.status= status;
    }

    public void setSequence(String sequence){
           this.sequence= sequence;
    }

    public void setDuration(String duration){
           this.duration= duration;
    }


    public String getDescription(){
        if ((description==null)||(description.equalsIgnoreCase("null"))){
         description="";
       }
       return description;
    }


    public String getRst(){
        if ((status==null)||(status.equalsIgnoreCase("null"))){
         status="";
       }
        return status;
    }

    public String getSequence(){
        if ((sequence==null)||(sequence.equalsIgnoreCase("null"))){
         sequence="";
       }
        return sequence;
    }

    public String getDuration(){
        if ((duration==null)||(duration.equalsIgnoreCase("null"))){
         duration="";
       }
        return duration;
    }

    public String getPriority(){
        if ((priority==null)||(priority.equalsIgnoreCase("null"))){
         priority="";
        }
        return priority;
    }
}
