package client.essp.pwm.workbench.period;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.NoSuchElementException;

import javax.swing.ButtonGroup;

import client.essp.common.view.VWWorkArea;
import client.framework.view.vwcomp.VWJRadioButton;
import com.wits.util.TestPanel;
import c2s.essp.common.calendar.WorkCalendar;
import c2s.essp.common.calendar.WrokCalendarFactory;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Enovation</p>
 * @author Xuxi.Chen
 * @version 1.0
 */

public class VwPeriodDateDef extends VWWorkArea implements IPeriodDef{
    VWJRadioButton jRadioButtonDay = new VWJRadioButton();
    VWJRadioButton jRadioButtonWorkDay = new VWJRadioButton();
    WorkCalendar workCalendar;

    public VwPeriodDateDef() {
        try {
            jbInit();

            workCalendar = WrokCalendarFactory.clientCreate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    void jbInit() throws Exception {
        this.setLayout(new GridBagLayout());

        jRadioButtonDay.    setText("Every Day     ");
        jRadioButtonWorkDay.setText("Every Work Day");

        this.add(jRadioButtonWorkDay, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        this.add(jRadioButtonDay, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));

        ButtonGroup buttonGroup1 = new ButtonGroup();
        buttonGroup1.add(this.jRadioButtonWorkDay);
        buttonGroup1.add(this.jRadioButtonDay);

        //初始默认的选择
        jRadioButtonWorkDay.setSelected(true);
    }

	public Iterator getPeriodList(Date beginDate, long repTimes){

        if( jRadioButtonDay.isSelected() == true ){
            return new DayRepTimeIterator(beginDate, repTimes);
        }else{
            return new DayRepTimeIterator(beginDate, repTimes, true);
        }
	}

    public Iterator getPeriodList(Date beginDate, Date endDate) {

        if (jRadioButtonDay.isSelected() == true) {
            return new DayRangeIterator(beginDate, endDate);
        } else {
            return new DayRangeIterator(beginDate, endDate, true);
        }
    }

    public static void main(String args[]){
        TestPanel.show(new VwPeriodDateDef() );
    }

    //按自然日循环repTimes次
    public class DayRepTimeIterator implements Iterator{
        Date beginDate;
        long repTimes;

        /**
         * 已循环的次数
         * 下一天为beginDate + nextDay天
         * */
        int nextDay=1;

        //是否只计算工作日,非工作日将排除
        boolean onlyWorkDay;
        boolean hasMore = true;

        public DayRepTimeIterator(Date beginDate, long repTimes){
            this(beginDate, repTimes, false );
        }

        public DayRepTimeIterator(Date beginDate, long repTimes, boolean onlyWorkDay){
            this.beginDate = new Date(beginDate.getTime());
            this.repTimes = repTimes;
            this.onlyWorkDay = onlyWorkDay;
        }

        public boolean hasNext(){
            if( hasMore == false ){
                return false;
            }

            if( beginDate == null ){
                return false;
            }

            if( nextDay > repTimes ){
                return false;
            }else{
                return true;
            }
        }

        /**
         * @return Object -- java.util.Date
         */
        public Object next()throws NoSuchElementException{
            if( hasNext() ){
                Calendar ret = Calendar.getInstance();
                ret.setTime(beginDate);
                int d = ret.get(Calendar.DAY_OF_YEAR);
                ret.set(Calendar.DAY_OF_YEAR, d+nextDay);

                nextDay++;

                if( onlyWorkDay == true ){
                    if( workCalendar.isWorkDay(ret) == false ){

                        repTimes++;//非工作日不计在repTimes中

                        //如果是非工作日,则继续寻找下一个日期
                        if( hasNext() == true ){
                            return next();
                        }else{

                            hasMore = false;
                            return null;
                        }
                    }
                }

                return ret;
            }else{
                throw new NoSuchElementException("Has no date any more.");
            }
        }

        public void remove(){}
    }

    //按自然日循环从beginDate到endDate
    public class DayRangeIterator implements Iterator{
        Calendar beginDate;
        Calendar endDate;

        /**
         * 将返回的日期
         * 下一天为nextDay天
         * */
        Calendar nextDay;

        //是否只计算工作日,非工作日将排除
        boolean onlyWorkDay;
        boolean hasMore = true;

        public DayRangeIterator(Date beginDate, Date endDate){
            this( beginDate, endDate, false );
        }

        public DayRangeIterator(Date beginDate, Date endDate, boolean onlyWorkDay){
            this.beginDate = Calendar.getInstance();
            this.beginDate.setTime(beginDate);

            this.endDate = Calendar.getInstance();
            this.endDate.setTime(endDate);

            this.onlyWorkDay = onlyWorkDay;

            int d = this.beginDate.get(Calendar.DAY_OF_YEAR);
            this.nextDay = (Calendar)this.beginDate.clone();
            nextDay.set(Calendar.DAY_OF_YEAR, d+1);
        }

        public boolean hasNext(){
            if( hasMore == false ){
                return false;
            }

            if( beginDate == null || endDate == null ){
                return false;
            }

            if( isDateBigger(nextDay, endDate) == true ){
                return false;
            }else{
                return true;
            }
        }

        /**
         * @return Object -- java.util.Calendar
         */
        public Object next()throws NoSuchElementException{
            if( hasNext() ){
                Calendar ret = (Calendar) nextDay.clone();

                int d = this.nextDay.get(Calendar.DAY_OF_YEAR);
                nextDay.set(Calendar.DAY_OF_YEAR, d + 1);

                if( onlyWorkDay == true ){
                    if( workCalendar.isWorkDay(ret) == false ){

                        //如果是非工作日,则继续寻找下一个日期
                        if( hasNext() == true ){
                            return next();
                        }else{

                            hasMore = false;
                            return null;
                        }
                    }
                }

                return ret;
            }else{
                throw new NoSuchElementException("Has no date any more.");
            }
        }

        public void remove(){}
    }

    private boolean isDateBigger(Calendar left, Calendar right){
        int y1= left.get(Calendar.YEAR);
        int m1= left.get(Calendar.MONTH);
        int d1 = left.get(Calendar.DAY_OF_YEAR);

        int y2= right.get(Calendar.YEAR);
        int m2= right.get(Calendar.MONTH);
        int d2 = right.get(Calendar.DAY_OF_YEAR);

        long n1 =y1 * 1024 + m1 * 512 + d1;
        long n2 =y2 * 1024 + m2 * 512 + d2;
        if( ( n1 ) > (  n2  ) ){
            return true;
        }else{
            return false;
        }
    }
}
