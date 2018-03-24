package client.essp.common.gantt;

public interface GanttModel {
    int getRowCount();

    int getRowStyle(int row);

    long getRowBeginTime(int row);

    long getRowEndTime(int row);

    double getProcessRate(int row);

    int getLinkCount(int srcRow);

    int getLinkRow(int srcRow, int i);

    int getLinkType(int srcRow, int i);

    /**
     * Adds a listener to the list that is notified each time a change
     * to the data model occurs.
     *
     * @param	l		the GanttModelListener
     */
    public void addGanttModelListener(GanttListener l);

    /**
     * Removes a listener from the list that is notified each time a
     * change to the data model occurs.
     *
     * @param	l		the GanttModelListener
     */
    public void removeGanttModelListener(GanttListener l);

}
