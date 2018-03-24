package server.essp.pms.account.labor.viewbean;
/**
 * 区间划分的每周
 * @author not attributable
 * @version 1.0
 */
public class VbPeriodPerWeek {
        /**
         * 显示该星期的第一天（星期一）的日期
         */
        private String showDay;
        /**
         * 判断该月的最后一个星期是否跨到下一个月
         */
        private boolean isBrige = false;
        /**
         * 背景图片路径
         */
        private String background;
    public String getShowDay() {
        return showDay;
    }

    public boolean isBrige() {
        return isBrige;
    }

    public String getBackground() {
        return isBrige? "../photo/alloc_tool/=alloc_tool_back1.jpg" :
                         "../photo/alloc_tool/alloc_tool_back3.jpg";
    }

    public void setShowDay(String showDay) {
        this.showDay = showDay;
    }

    public void setIsBrige(boolean isBrige) {
        this.isBrige = isBrige;
    }
}
