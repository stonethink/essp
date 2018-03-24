package server.essp.projectpre.ui.parameter;

public class VbParameter {
    
    private String kind;
    private String code;
    private String name;
    private Long sequence;
    private String description;
    private Boolean status;
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Long getSequence() {
        return sequence;
    }
    public void setSequence(Long sequence) {
        this.sequence = sequence;
    }
    public Boolean getStatus() {
        return status;
    }
    public void setStatus(Boolean status) {
        this.status = status;
    }
    public String getKind() {
        return kind;
    }
    public void setKind(String kind) {
        this.kind = kind;
    }
 
 

}
