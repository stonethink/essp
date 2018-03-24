package c2s.essp.pms.gantt;

import java.util.ArrayList;
import java.util.List;

import c2s.dto.ITreeNode;
import c2s.essp.pms.wbs.DtoActivity;
import c2s.essp.common.gantt.ITreeGanttDto;
import c2s.essp.common.gantt.IGanttDto;

public class DtoActivityGantt extends DtoActivity  implements ITreeGanttDto{
    List relations = new ArrayList();
    List relationTypes = new ArrayList();

    //树中本dto所在的node(记住这个是为了找到该dto在树中的行号)
    ITreeNode thisNode;

    public void addRelation(DtoActivityGantt relationDto, int type ){
        relations.add( relationDto );
        relationTypes.add( new Integer(type) );
    }

    public int getStyle() {
        int style = IGanttDto.TASK | IGanttDto.LEAF;
        boolean isStart = false;
        if( isStart() != null ){
            isStart = isStart().booleanValue();
        }

        boolean isFinish = false;
        if( isFinish() != null ){
            isFinish = isFinish().booleanValue();
        }

        if( isStart && isFinish ){
            style = style | IGanttDto.ACTUAL_TASK;
        }else if( isStart && isFinish == false ){
            style = style | IGanttDto.PROCESS;
        }

        return style;
    }

    public long getBeginTime(){
        if( getPlannedStart() != null ){
            return getPlannedStart().getTime();
        }else{
            return 0;
        }
    }

    public long getEndTime() {
        if( getPlannedFinish() != null ){
            return getPlannedFinish().getTime();
        }else{
            return 0;
        }
    }

    public double getProcessRate(){
        if( getCompleteRate() != null ){
            return getCompleteRate().doubleValue();
        }else{
            return 0;
        }
    }

    public int getLinkCount(){
        return this.relations.size();
    }

    public ITreeNode getLinkNode(int i){
        if( i >= 0 && i < getLinkCount() ){
            DtoActivityGantt relation = (DtoActivityGantt)relations.get(i);
            return relation.getThisNode();
        }

        return null;
    }

    public int getLinkType(int i){
        if( i >= 0 && i < this.relationTypes.size() ){
            return ( (Integer)this.relationTypes.get(i) ).intValue();
        }else{
            return IGanttDto.NONE_STYLE;
        }
    }

    public ITreeNode getThisNode(){
        return this.thisNode;
    }

    public void setThisNode(ITreeNode thisNode){
        this.thisNode = thisNode;
    }


}
