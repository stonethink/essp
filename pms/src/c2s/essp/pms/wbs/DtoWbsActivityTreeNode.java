package c2s.essp.pms.wbs;

import c2s.dto.DtoTreeNode;
import java.util.Date;

public class DtoWbsActivityTreeNode extends DtoTreeNode  {
    public DtoWbsActivityTreeNode() {
        super(null);
    }
    public DtoWbsActivityTreeNode(IDtoWbsActivity dto) {
        super(dto);
    }

    public String getWbsPathName(){
        return null;
    }

    public boolean checkAnticipatedStart(Date date){
        return true;
    }

    public boolean checkAnticipatedFinish(Date date){
        return true;
    }

    public boolean checkPlannedStart(Date date){
        return true;
    }

    public boolean checkPlannedFinish(Date date){
        return true;
    }

    public void setAnticipatedStart(Date date){
        //return true;
    }

    public void setAnticipatedFinish(Date date){
        //return true;
    }

    public void setPlannedStart(Date date){
        //return true;
    }

    public void setPlannedFinish(Date date){
        //return true;
    }

    public void setActualStart(Date date){
        //return true;
    }

    public void setActualFinish(Date date){
        //return true;
    }

//只需要读取就可以了，真正的计算在生成Tree时完成，Client更改后并不实时对整个Tree的完工比例进行更新
//    public void calculateCompleteRate(){
//        //return true;
//    }
}
