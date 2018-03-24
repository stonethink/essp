package server.essp.pms.account.labor.viewbean;

/**
 * 显示人员每周的安排
 * @author not attributable
 * @version 1.0
 */
public class VbResourceWeekPlan {
        /**
         * 该周是当前时间内
         */
        private boolean isFuture = true;
        /**
         * 该周是否已安排
         */
        private boolean isPlaned = false;
        /**
         * 项目在本周个人时间所占比例
         */
        private String usagePercent = "0";
        /**
         * 显示该周方格的背景颜色
         */
        private String bgColor;
    public String getUsagePercent() {
        return usagePercent;
    }

    public boolean isPlaned() {
        return isPlaned;
    }

    public String getBgColor() {
        //如果本周不在当前日期内，不使用背景颜色
        return isFuture ? "" : "bgColor=\"#dedede\"";
    }

    public boolean isFuture() {
        return isFuture;
    }


    public void setUsagePercent(String usagePercent) {
        this.usagePercent = usagePercent;
    }

    public void setIsPlaned(boolean isPlaned) {
        this.isPlaned = isPlaned;
    }

    public void setIsFuture(boolean isFuture) {
        this.isFuture = isFuture;
    }


}
