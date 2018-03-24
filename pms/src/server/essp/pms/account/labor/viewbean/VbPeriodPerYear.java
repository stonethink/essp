package server.essp.pms.account.labor.viewbean;

/**
 * 表示区间划分的每年
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class VbPeriodPerYear {
        /**
         * 显示的年数
         */
        private int year;
        /**
         * 时间段在该年有几月，显示用于合并单元格
         */
        private int months;
    public int getYear() {
        return year;
    }

    public int getMonths() {

        return months;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setMonths(int months) {

        this.months = months;
    }

}
