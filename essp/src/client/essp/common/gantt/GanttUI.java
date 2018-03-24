package client.essp.common.gantt;

public interface GanttUI {
    public static final long ADAY_MSEL = 3600 * 24 * 1000; //һ��ĺ�����

    /**
     * ȡĳһʱ�����ڵ�X����
     * @param timeĳһʱ��
     * @return time����Ӧ������
     */
    int getPointOfTime(long time);


    /**
     * ȡĳһ���ʾ��ʱ��(ms)
     * @param xPosĳ��ĺ�����
     * @return xPos����Ӧ��ʱ��
     */
    long getTimeAtPoint(int xPos);

    long getMinTime();
    long getMaxTime();

    //һ��ĳ��ȣ�������
    int getOneDayDots();
}
