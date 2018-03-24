package client.essp.common.gantt;



public class DefaultCanttUI implements GanttUI {
    long minTime;
    long maxTime;

    //ȱʡ����������:����Сʱһ����
    static final double DEFALT_ZOOM_FACTOR = 1000.0 * 3600.0 * 1;

    //��������
    private double zoomFactor = DEFALT_ZOOM_FACTOR;

    public DefaultCanttUI(long minTime, long maxTime) {
        this.minTime = minTime;
        this.maxTime = maxTime;
    }

    /**
     * ��������ȡʱ�������
     * @return ʱ����������Ӧ��GMTʱ��
     */
    public long getMinTime() {
        return this.minTime;
    }

    /**
     * ��������ȡʱ�������
     * @return ʱ����������Ӧ��GMTʱ��
     */
    public long getMaxTime() {
        return this.maxTime;
    }

    /**
     * ȡĳһ���ʾ��ʱ��(ms)
     * @param xPosĳ��ĺ�����
     * @return xPos����Ӧ��ʱ��
     */
    public long getTimeAtPoint(int xPos) {
        return getMinTime() + xPos * (long) zoomFactor;
    }

    /**
     * ȡĳһʱ�����ڵ�X����
     * @param timeĳһʱ��
     * @return time����Ӧ������
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
