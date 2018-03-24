package server.essp.timecard.worktime.data;


/**
 *
 * <p>Title: 一笔工作时间设置的内容</p>
 * <p>Description: 采用字符串表时起止工作时间，同时提供数字数组分解时间</p>
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

    //分解14:20这种时间
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
     * 记算此区间相差工时
     * @return float
     */
    public float getTimeHours() {
        return getTimeHours(timeStartNumber, timeEndNumber);
    }

    /**
     * 记算任意两个时间的相差时间
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
     * 记算任一时间到区时结束时的时间
     * @param input int[]
     * @return float
     */
    public float getTimeHours(int[] input) {
        return getTimeHours(input, timeEndNumber);
    }

    /**
     * 判断是否在区间内
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
     * 判断是否在输入时间之后
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
     * 获取区间中离当前输入时间最近的一个时间<br>
     * 1.如果区间在此时间之后，返回区间开始时间<br>
     * 2.如果时间落在区间内，返回此时间
     * 3.如果区间在此时间之前，返回一个长度的数组，数组返回值为-1
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
