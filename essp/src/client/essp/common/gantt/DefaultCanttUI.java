package client.essp.common.gantt;



public class DefaultCanttUI implements GanttUI {
    long minTime;
    long maxTime;

    //缺省的缩放因子:三个小时一个点
    static final double DEFALT_ZOOM_FACTOR = 1000.0 * 3600.0 * 1;

    //缩放因子
    private double zoomFactor = DEFALT_ZOOM_FACTOR;

    public DefaultCanttUI(long minTime, long maxTime) {
        this.minTime = minTime;
        this.maxTime = maxTime;
    }

    /**
     * 按毫秒数取时间的下限
     * @return 时间下限所对应的GMT时间
     */
    public long getMinTime() {
        return this.minTime;
    }

    /**
     * 按毫秒数取时间的上限
     * @return 时间上限所对应的GMT时间
     */
    public long getMaxTime() {
        return this.maxTime;
    }

    /**
     * 取某一点表示的时间(ms)
     * @param xPos某点的横坐标
     * @return xPos所对应的时间
     */
    public long getTimeAtPoint(int xPos) {
        return getMinTime() + xPos * (long) zoomFactor;
    }

    /**
     * 取某一时刻所在的X坐标
     * @param time某一时刻
     * @return time所对应的坐标
     */
    public int getPointOfTime(long time) {
        return (int) ((time - getMinTime()) / zoomFactor);
    }

    public void setZoomFactor(double zoomFactor){
        this.zoomFactor = zoomFactor;
    }

    public int getOneDayDots(){
        double oneDayDots = ADAY_MSEL / zoomFactor;
        return (int)oneDayDots;
    }
}
