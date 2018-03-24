package c2s.essp.ebs;

import c2s.dto.DtoBase;

public class DtoAssignAcnt extends DtoBase{
    private Long rid;
    private String name;

    public Long getRid(){
        return this.rid;
    }
    public void setRid(Long rid ){
        this.rid = rid;
    }
    public String getName(){
        return this.name;
    }
    public void setName(String name ){
        this.name = name;
    }

    public String toString(){
        return this.getName();
    }

}
