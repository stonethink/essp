package client.essp.common.gantt;

public interface GanttListener {
    static int ROW_COUNT_CHANGED = 1;
    static int DATA_CHANGED = 2;
    static int STATE_CHANGED = 4;
    static int ROW_SELECTED = 8;

    void ganttChanged(int eventType);
}
