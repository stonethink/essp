package client.essp.pwm.workbench.period;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.NoSuchElementException;

import client.essp.common.view.VWWorkArea;
import client.framework.view.vwcomp.VWJCheckBox;
import client.framework.view.vwcomp.VWJLabel;
import client.framework.view.vwcomp.VWJNumber;
import com.wits.util.TestPanel;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Enovation</p>
 * @author Xuxi.Chen
 * @version 1.0
 */

public class VwPeriodWeekDef extends VWWorkArea implements IPeriodDef{
    public static final String[] WEEK_DAY_NAMES = new String[]{
       "Sunday", "Monday", "Thuesday", "Wednesday", "Thursday", "Friday", "Saturday"
    };

    VWJNumber numWeekIntv = new VWJNumber();
    VWJCheckBox[] cmbDates = new VWJCheckBox[]{
        new VWJCheckBox(),
        new VWJCheckBox(),
        new VWJCheckBox(),
        new VWJCheckBox(),
        new VWJCheckBox(),
        new VWJCheckBox(),
        new VWJCheckBox()
    };

    public VwPeriodWeekDef() {
        try {
            jbInit();
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    void jbInit() throws Exception {
        this.setLayout( new GridBagLayout() );

        VWJLabel jLabel1 = new VWJLabel();
        VWJLabel jLabel2 = new VWJLabel();
        jLabel1.setText("Every");
        jLabel2.setText("week(s)");
        numWeekIntv.setPreferredSize(new Dimension(35, 20));
        numWeekIntv.setMaxInputIntegerDigit(4);
        numWeekIntv.setValue(1);

        this.add(jLabel1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 8, 0, 0), 0, 0));
        this.add(numWeekIntv, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        this.add(jLabel2,  new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));

        for (int i = 0; i < WEEK_DAY_NAMES.length; i++) {
            cmbDates[i].setText(WEEK_DAY_NAMES[i]);
        }
        this.add(cmbDates[0],     new GridBagConstraints(0, 1, 2, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 0, 0), 0, 0));
        this.add(cmbDates[1],     new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 0, 0), 0, 0));
        this.add(cmbDates[2],     new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 0, 0), 0, 0));
        this.add(cmbDates[3],     new GridBagConstraints(4, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 0, 0), 0, 0));
        this.add(cmbDates[4],     new GridBagConstraints(0, 2, 2, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 0, 0), 0, 0));
        this.add(cmbDates[5],     new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 0, 0), 0, 0));
        this.add(cmbDates[6],     new GridBagConstraints(3, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 0, 0), 0, 0));

    }

    /**
     * 取得勾选的日期,星期天为1, 星期一为2,依此类推
     * @return int[]
     */
    private
    int[] getSelectDays(){
        int days[] = new int[]{0,0,0,0,0,0,0};
        int num = 0;
        for( int i = 0 ; i < cmbDates.length; i++ ){
            if( cmbDates[i].isSelected() == true ){
                days[i] = 1;
                num++;
            }
        }

        int selectDays[] = new int[num];
        int k = 0;
        for( int j = 0; j < days.length; j++ ){
            if( days[j] == 1 ){
                selectDays[k++] = j+1;
            }
        }

        return selectDays;
    }

    private int getWeekStep(){
        return (int)numWeekIntv.getValue();
    }

    public Iterator getPeriodList(Date beginDate, long repTimes){
        return new WeekRepTimesIterator(beginDate, repTimes,getWeekStep(), getSelectDays());
    }

    public Iterator getPeriodList(Date beginDate, Date endDate) {
        return new WeekRangeIterator(beginDate, endDate,getWeekStep(), getSelectDays());
    }

    public class WeekRepTimesIterator implements Iterator{
        Calendar beginDate;
        long repTimes;
        int daysOfWeek[];
        int weekStep;

        /**
         * 一周的第一天
         * 初值为beginDate所在周的第一天
         */
        Calendar firstDateOfWeek;

        /**
         * 下一天为firstDateOfWeek + daysOfWeek[nextDay]天
         * 下一天不含beginDate，且大于beginDate
         */
        int nextDay=0;

        /**
         * 周的已循环次数
         */
        int curWeek=0;

        boolean hasMore = true;

        public WeekRepTimesIterator(Date beginDate, long repTimes,int weekStep, int daysOfWeek[]){
            this.beginDate = Calendar.getInstance();
            this.beginDate.setTime(beginDate);
            this.repTimes = repTimes;
            this.daysOfWeek = daysOfWeek;
            this.weekStep = weekStep;

            this.firstDateOfWeek = Calendar.getInstance();
            firstDateOfWeek.setTime(beginDate);
            firstDateOfWeek.set(Calendar.DAY_OF_WEEK, 0);
        }

        public boolean hasNext(){
            if( hasMore == false ){
                return false;
            }

            if( beginDate == null || daysOfWeek == null || daysOfWeek.length == 0 ){
                return false;
            }

            if( curWeek < repTimes ){
                if( nextDay < daysOfWeek.length ){
                    return true;
                }
            }

            return false;
        }

        /**
         * note: return value may be null
         * @return Object -- java.util.Calendar
         */
        public Object next(){
            if( hasNext() ){
                Calendar ret = (Calendar)this.firstDateOfWeek.clone();
                int d = this.daysOfWeek[nextDay];
                ret.set(Calendar.DAY_OF_WEEK, d);

                nextDay = nextDay + 1;
                if( nextDay >= daysOfWeek.length ){

                    nextDay = 0;
                    curWeek = curWeek + 1;
                    int w = firstDateOfWeek.get(Calendar.WEEK_OF_YEAR);
                    firstDateOfWeek.set(Calendar.WEEK_OF_YEAR, w+weekStep);
                }

                //返回结果要大于beginDate
                if( isDateBiggerOrEqual(beginDate, ret) == true ){
                    if( hasNext() ){
                        return next();
                    }else{

                        hasMore = false;
                        return null;
                    }
                }
                return ret;
            }else{
                throw new NoSuchElementException("Has no date any more.");
            }
        }

        public void remove(){}
    }


    public class WeekRangeIterator implements Iterator{
        Calendar beginDate;
        Calendar endDate;
        int daysOfWeek[];
        int weekStep;

        /**
         * 一周的第一天
         * 初值为beginDate所在周的第一天
         */
        Calendar firstDateOfWeek;

        /**
         * 下一天为firstDateOfWeek + daysOfWeek[nextDay]天
         * 下一天大于beginDate,不含beginDate，且小于endDate,含endDate
         */
        int nextDay=0;

        boolean hasMore = true;

        public WeekRangeIterator(Date beginDate, Date endDate, int weekStep,int daysOfWeek[]){
            this.beginDate = Calendar.getInstance();
            this.beginDate.setTime(beginDate);
            this.endDate = Calendar.getInstance();
            this.endDate.setTime(endDate);
            this.daysOfWeek=daysOfWeek;
            this.weekStep = weekStep;

            this.firstDateOfWeek = Calendar.getInstance();
            firstDateOfWeek.setTime(beginDate);
            firstDateOfWeek.set(Calendar.DAY_OF_WEEK, 0);
        }

        public boolean hasNext(){
            if( beginDate == null || endDate == null
                || daysOfWeek == null || nextDay >= daysOfWeek.length ){
                return false;
            }

            if( hasMore == false ){
                return false;
            }

            return true;
        }

        /**
         * note: return value may be null
         * @return Object -- java.util.Calendar
         */
        public Object next(){
            if( hasNext() ){
                Calendar ret = (Calendar)this.firstDateOfWeek.clone();
                int d = this.daysOfWeek[nextDay];
                ret.set(Calendar.DAY_OF_WEEK, d);

                nextDay = nextDay + 1;
                if( nextDay >= daysOfWeek.length ){

                    nextDay = 0;
                    int w = firstDateOfWeek.get(Calendar.WEEK_OF_YEAR);
                    firstDateOfWeek.set(Calendar.WEEK_OF_YEAR, w+weekStep);
                }

                //返回结果要大于beginDate
                if( isDateBiggerOrEqual(beginDate, ret) == true ){
                    if( hasNext() ){
                        return next();
                    }else{

                        hasMore = false;
                        return null;
                    }
                }

                if( isDateBigger(ret, endDate) == true ){
                    hasMore = false;
                    return null;
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

        long n1 = y1 * 1024 + m1 * 512 + d1;
        long n2 = y2 * 1024 + m2 * 512 + d2;
        if( ( n1 ) > ( n2 ) ){
            return true;
        }else{
            return false;
        }
    }
    private boolean isDateBiggerOrEqual(Calendar left, Calendar right){
        int y1= left.get(Calendar.YEAR);
        int m1= left.get(Calendar.MONTH);
        int d1 = left.get(Calendar.DAY_OF_YEAR);

        int y2= right.get(Calendar.YEAR);
        int m2= right.get(Calendar.MONTH);
        int d2 = right.get(Calendar.DAY_OF_YEAR);

        long n1 = y1 * 1024 + m1 * 512 + d1;
        long n2 = y2 * 1024 + m2 * 512 + d2;
        if( ( n1 ) >= (  n2  ) ){
            return true;
        }else{
            return false;
        }
    }


    public static void main(String args[]){
        TestPanel.show(new VwPeriodWeekDef() );
    }
}
