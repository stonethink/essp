package server.essp.pms.account.labor.viewbean;

/**
 * ��ʾ��Աÿ�ܵİ���
 * @author not attributable
 * @version 1.0
 */
public class VbResourceWeekPlan {
        /**
         * �����ǵ�ǰʱ����
         */
        private boolean isFuture = true;
        /**
         * �����Ƿ��Ѱ���
         */
        private boolean isPlaned = false;
        /**
         * ��Ŀ�ڱ��ܸ���ʱ����ռ����
         */
        private String usagePercent = "0";
        /**
         * ��ʾ���ܷ���ı�����ɫ
         */
        private String bgColor;
    public String getUsagePercent() {
        return usagePercent;
    }

    public boolean isPlaned() {
        return isPlaned;
    }

    public String getBgColor() {
        //������ܲ��ڵ�ǰ�����ڣ���ʹ�ñ�����ɫ
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
