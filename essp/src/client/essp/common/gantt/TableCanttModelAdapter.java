package client.essp.common.gantt;

import java.util.ArrayList;
import java.util.List;

import client.framework.model.VMTable2;
import c2s.essp.common.gantt.IGanttDto;


public class TableCanttModelAdapter implements GanttModel {
    VMTable2 tableModel;

    List ganttModelListenerList = new ArrayList();

    public TableCanttModelAdapter(VMTable2 tableModel) {
        this.tableModel = tableModel;
    }

    public int getRowCount() {
        return tableModel.getRowCount();
    }

    public int getRowStyle(int row) {
        IGanttDto dto = getDto(row);
        if (dto != null) {
            return dto.getStyle();
        } else {
            return IGanttDto.NONE_STYLE;
        }
    }

    public long getRowBeginTime(int row) {
        IGanttDto dto = getDto(row);
        if (dto != null) {
            return dto.getBeginTime();
        } else {
            return 0;
        }
    }

    public long getRowEndTime(int row) {
        IGanttDto dto = getDto(row);
        if (dto != null) {
            return dto.getEndTime();
        } else {
            return 0;
        }
    }

    public double getProcessRate(int row) {
        IGanttDto dto = getDto(row);
        if (dto != null) {
            return dto.getProcessRate();
        } else {
            return 0;
        }
    }

    public int getLinkCount(int srcRow) {
        IGanttDto dto = getDto(srcRow);
        if (dto != null) {
            return 0; //dto.getLinkCount();
        } else {
            return 0;
        }
    }

    public int getLinkRow(int srcRow, int i) {
        IGanttDto dto = getDto(srcRow);
        if (dto != null) {
            return 0; //dto.getLinkRow(i);
        } else {
            return 0;
        }
    }

    public int getLinkType(int srcRow, int i) {
        IGanttDto dto = getDto(srcRow);
        if (dto != null) {
            return 0 ;//dto.getLinkType(i);
        } else {
            return 0;
        }
    }

    private IGanttDto getDto(int row) {
        Object obj = tableModel.getRow(row);
        return (IGanttDto) obj;
    }

    public void addGanttModelListener(GanttListener l){
        this.ganttModelListenerList.add(l);
    }

    public void removeGanttModelListener(GanttListener l){
        this.ganttModelListenerList.remove(l);
    }

}
