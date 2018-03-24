package c2s.essp.pms.gantt;

import c2s.essp.pms.wbs.DtoWbs;
import c2s.dto.ITreeNode;
import c2s.essp.common.gantt.ITreeGanttDto;
import c2s.essp.common.gantt.IGanttDto;

public class DtoWbsGantt extends DtoWbs implements ITreeGanttDto {

    public int getStyle() {
        return IGanttDto.TASK;
    }

    public long getBeginTime() {
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

    public double getProcessRate() {
        return 0;
    }

    public int getLinkCount() {
        return 0;
    }

    public ITreeNode getLinkNode(int i) {
        return null;
    }

    public int getLinkType(int i) {
        return IGanttDto.NONE_STYLE;
    }

}
