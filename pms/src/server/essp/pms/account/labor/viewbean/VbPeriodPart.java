package server.essp.pms.account.labor.viewbean;

import java.util.*;

public class VbPeriodPart {
        /**
         * �鿴��Ŀʱ�����ͣ�0���������ڵ�ʱ�䰲�ţ�1������Ŀ�����ڵ�ʱ�䰲��
         */
        public static final String VIEW_BASE_TWO_YEARS = "0";
    public static final String VIEW_BASE_WHOLE_LIFE_CYCLE = "1";

    private List years = new ArrayList();
    private List months = new ArrayList();
    public List getMonths() {
        return months;
    }

    public List getYears() {
        return years;
    }

    public void setMonths(List months) {
        this.months = months;
    }

    public void setYears(List years) {
        this.years = years;
    }
}
