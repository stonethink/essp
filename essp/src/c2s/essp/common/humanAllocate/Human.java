package c2s.essp.common.humanAllocate;

import java.io.Serializable;

public class Human implements Serializable{

    //人的id，当有多个时，用逗号","分隔
    String ids;

    //人的name，当有多个时，用逗号","分隔
    String names;

    public Human(){
        this("","");
    }

    public Human(String ids) {
        this(ids, "" );
    }

    public Human(String ids, String names){
        this.ids = ids;
        this.names = names;
    }

    public String getIds() {
        return ids;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }
}
