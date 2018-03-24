package server.essp.timecard.worktime.data;


/**
 *
 * <p>Title: һ�ʹ���ʱ�����õ�����</p>
 * <p>Description: �����ַ�����ʱ��ֹ����ʱ�䣬ͬʱ�ṩ��������ֽ�ʱ��</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company:  Wistron ITS Wuhan SDC</p>
 * @author not attributable
 * @version 1.0
 */
public class WorkTimeItem {
    String timeStart;
    String timeEnd;
    int[]  timeStartNumber = new int[2];
    int[]  timeEndNumber   = new int[2];

    public WorkTimeItem() {
    }

    public WorkTimeItem(String start,
                        String end) {
        timeStart = start;
        timeEnd   = end;

        if (spliteTime(start) != null) {
            timeStartNumber = spliteTime(start);
        }

        if (spliteTime(end) != null) {
            timeEndNumber = spliteTime(end);
        }
    }

    //�ֽ�14:20����ʱ��
    public static int[] spliteTime(String inTime) {
        String[] times = inTime.split("[:]");

        if (times.length != 2) {
            return null;
        }

        int[] iRet = new int[2];
        iRet[0] = Integer.parseInt(times[0]);
        iRet[1] = Integer.parseInt(times[1]);

        return iRet;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public int[] getTimeEndNumber() {
        return timeEndNumber;
    }

    public void setTimeEndNumber(int[] timeEndNumber) {
        this.timeEndNumber = timeEndNumber;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public int[] getTimeStartNumber() {
        return timeStartNumber;
    }

    public void setTimeStartNumber(int[] timeStartNumber) {
        this.timeStartNumber = timeStartNumber;
    }

    /**
     * �����������ʱ
     * @return float
     */
    public float getTimeHours() {
        return getTimeHours(timeStartNumber, timeEndNumber);
    }

    /**
     * ������������ʱ������ʱ��
     * @param Start int[]
     * @param End int[]
     * @return float
     */
    public static float getTimeHours(int[] start,
                                     int[] end) {
        float backTime = (float) (end[1] - start[1]) / 60;
        float fRet = end[0] - start[0] + backTime;

        return fRet;
    }

    /**
     * ������һʱ�䵽��ʱ����ʱ��ʱ��
     * @param input int[]
     * @return float
     */
    public float getTimeHours(int[] input) {
        return getTimeHours(input, timeEndNumber);
    }

    /**
     * �ж��Ƿ���������
     * @param input int[]
     * @return boolean
     */
    public boolean isAt(int[] input) {
        if ((getTimeHours(timeStartNumber, input) >= 0)
                && (getTimeHours(input, timeEndNumber) >= 0)) {
            return true;
        }

        return false;
    }

    /**
     * �ж��Ƿ�������ʱ��֮��
     * @param input int[]
     * @return boolean
     */
    public boolean isAfter(int[] input) {
        if (getTimeHours(timeStartNumber, input) < 0) {
            return true;
        }

        return false;
    }

    /**
     * ��ȡ�������뵱ǰ����ʱ�������һ��ʱ��<br>
     * 1.��������ڴ�ʱ��֮�󣬷������俪ʼʱ��<br>
     * 2.���ʱ�����������ڣ����ش�ʱ��
     * 3.��������ڴ�ʱ��֮ǰ������һ�����ȵ����飬���鷵��ֵΪ-1
     * @param input int[]
     * @return int[]
     */
    public int[] getNextTimeHours(int[] input) {
        if (isAfter(input)) {
            return this.timeStartNumber;
        } else if (isAt(input)) {
            return input;
        } else {
            int[] iRet = new int[1];
            iRet[0] = -1;

            return iRet;
        }
    }
}
