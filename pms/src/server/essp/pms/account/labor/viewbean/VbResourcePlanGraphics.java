package server.essp.pms.account.labor.viewbean;

import java.util.*;

/**
 * ͼ����ʾ������Աʱ�䰲�����
 * @author not attributable
 * @version 1.0
 */
public class VbResourcePlanGraphics {
        /**
         * ������Ա��VbAllocatedResource
         */
        private List resourcePlan = new ArrayList();
        /**
         * ��ʾ�������Ŀ�ȣ�������ÿ�ܷ���Ŀ��
         */
        private int tabWidth;
        /**
         * ���ж�����(С����)
         */
        private int allWeeks;
    public int getAllWeeks() {
        return allWeeks;
    }

    public List getResourcePlan() {
        return resourcePlan;
    }

    public int getTabWidth() {
        tabWidth = allWeeks * VbPeriodPerMonth.PER_WEEK_WIDTH;
        return tabWidth;
    }

    public void setAllWeeks(int allWeeks) {
        this.allWeeks = allWeeks;
    }

    public void setResourcePlan(List resourcePlan) {
        this.resourcePlan = resourcePlan;
    }
}
