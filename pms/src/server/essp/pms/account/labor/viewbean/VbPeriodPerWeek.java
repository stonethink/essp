package server.essp.pms.account.labor.viewbean;
/**
 * ���仮�ֵ�ÿ��
 * @author not attributable
 * @version 1.0
 */
public class VbPeriodPerWeek {
        /**
         * ��ʾ�����ڵĵ�һ�죨����һ��������
         */
        private String showDay;
        /**
         * �жϸ��µ����һ�������Ƿ�絽��һ����
         */
        private boolean isBrige = false;
        /**
         * ����ͼƬ·��
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
