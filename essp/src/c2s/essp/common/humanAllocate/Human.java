package c2s.essp.common.humanAllocate;

import java.io.Serializable;

public class Human implements Serializable{

    //�˵�id�����ж��ʱ���ö���","�ָ�
    String ids;

    //�˵�name�����ж��ʱ���ö���","�ָ�
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
