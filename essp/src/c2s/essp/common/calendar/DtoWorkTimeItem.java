package c2s.essp.common.calendar;

import java.io.*;

import c2s.dto.*;
/**
 *
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: WistonITS</p>
 *
 * @author Rong.Xiao
 * @version 1.0
 */
public class DtoWorkTimeItem extends DtoBase implements Serializable {
    private String timeStart;
    private String timeEnd;
    private int[] timeStartArray;
    private int[] timeEndArray;
    public DtoWorkTimeItem(String timeStart, String timeEnd) {
        if(timeStart == null || timeEnd == null)
            throw new IllegalArgumentException("The time period can not be null");
        if(!timeStart.matches(WorkCalendarConstant.TIME_HHMM_REG))
            throw new IllegalArgumentException("The time ["+timeStart+"] can not match the format HH:MM");
        if(!timeEnd.matches(WorkCalendarConstant.TIME_HHMM_REG))
            throw new IllegalArgumentException("The time ["+timeEnd+"] can not match the format HH:MM");
        timeStartArray = parseTime(timeStart);
        timeEndArray = parseTime(timeEnd);
        if(getStartHour() > getEndHour() ||
           (getStartHour() == getEndHour() && getStartMinute() > getEndHour()))
            throw new IllegalArgumentException("The start time can not be after end time!");
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public int getStartHour(){
        return timeStartArray[0];
    }
    public int getStartMinute(){
        return timeStartArray[1];
    }
    public int getEndHour(){
        return timeEndArray[0];
    }
    public int getEndMinute(){
        return timeEndArray[1];
    }

    public float getHours() {
        float result = (float)(timeEndArray[0] - timeStartArray[0]) + //小时之差
                       (float)(timeEndArray[1] - timeStartArray[1]) / 60 ; //分之差
        return result;
    }
    /**
     * 判断该时间段是否在另一时间之前,即结束时间在小于Other的开始时间
     * @param other DtoWorkTimeItem
     * @return boolean
     */
    public boolean before(DtoWorkTimeItem other){
        if(other == null)
            throw new IllegalArgumentException();
        if(getEndHour() < other.getStartHour() ||
           (getEndHour() == other.getStartHour() && getEndMinute() < other.getStartMinute())){
            return true;
        }
        return false;
    }
    /**
     * 判断该时间段是否在另一段之后,即开始时间在大于Other的结束时间
     * @param other DtoWorkTimeItem
     * @return boolean
     */
    public boolean after(DtoWorkTimeItem other){
        if(other == null)
            throw new IllegalArgumentException();
        if(getStartHour() > other.getEndHour() ||
           (getStartHour() == other.getEndHour() && getStartMinute() > other.getEndMinute())){
            return true;
        }
        return false;
    }
    /**
     * 判断该时间段是否完整包含另一段,即开始时间在小于或等于Other的结束时间,且,
     * 结束时间在大于或等于Other的结束时间,
     * @param other DtoWorkTimeItem
     * @return boolean
     */
    public boolean contain(DtoWorkTimeItem other){
        if(other == null)
            throw new IllegalArgumentException();
        if(   (getStartHour() < other.getStartHour() ||
              (getStartHour() == other.getStartHour() && getStartMinute() <= other.getStartMinute()))
            &&(getEndHour() > other.getEndHour() ||
              (getEndHour() == other.getEndHour() && getEndMinute() >= other.getEndMinute()))
          ){
            return true;
        }
        return false;
    }
    /**
     * 计算该区间与另一区间重复部分的时间小时数
     * 1.该区间在另一区间前或后,返回0
     * 2.该区间包含另一区间,返回另一区间的小时数,
     * 3.另一区间包含该区间,返回该区间的小时数
     * 4.该区间后部分与另一区间前部分重复,返回该区间小时数
     * 5.该区间前部分与另一区间后部分重复,返回该区间小时数
     * @param other DtoWorkTimeItem
     * @return float
     */
    public float getCrossHours(DtoWorkTimeItem other){
        if(before(other) || after(other))
            return 0;
        if(contain(other))
            return other.getHours();
        else if(other.contain(this))
            return this.getHours();
        else if(getStartHour() > other.getStartHour() ||
                getStartHour() == other.getStartHour() && getStartMinute() > other.getStartMinute()){
            DtoWorkTimeItem temp = new DtoWorkTimeItem(getTimeStart(),other.getTimeEnd());
            return temp.getHours();
        }else {
            DtoWorkTimeItem temp = new DtoWorkTimeItem(other.getTimeStart(),getTimeEnd());
            return temp.getHours();
        }
    }
    //解析HH:MM格式的实现,放入int数组中;
    private int[] parseTime(String timeStr) {
        String[] temp = timeStr.split(WorkCalendarConstant.DEFAULT_TIME_DELIM);
        if (temp == null || temp.length != 2) {
            throw new IllegalArgumentException("The time ["+timeStr+"] does not match format HH:MM");
        }
        int[] time = new int[2];
        for (int i = 0; i < temp.length; i++) {
            time[i] = Integer.parseInt(temp[i]);
        }
        return time;
    }

}
