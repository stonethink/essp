package client.essp.common.gantt;

public interface GanttUI {
    public static final long ADAY_MSEL = 3600 * 24 * 1000; //一天的毫秒数

    /**
     * 取某一时刻所在的X坐标
     * @param time某一时刻
     * @return time所对应的坐标
     */
    int getPointOfTime(long time);


    /**
     * 取某一点表示的时间(ms)
     * @param xPos某点的横坐标
     * @return xPos所对应的时间
     */
    long getTimeAtPoint(int xPos);

    long getMinTime();
    long getMaxTime();

    //一天的长度（点数）
    int getOneDayDots();
}
