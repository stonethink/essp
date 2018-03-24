package c2s.essp.common.gantt;

import c2s.dto.ITreeNode;

public interface ITreeGanttDto extends IGanttDto {
    int getLinkCount();

    ITreeNode getLinkNode(int i);

    int getLinkType(int i);
}
