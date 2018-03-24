package server.essp.pms.account.labor.viewbean;

import java.util.*;

/**
 * 图形显示所有人员时间安排情况
 * @author not attributable
 * @version 1.0
 */
public class VbResourcePlanGraphics {
        /**
         * 所有人员的VbAllocatedResource
         */
        private List resourcePlan = new ArrayList();
        /**
         * 显示整个表格的宽度：周数×每周方格的宽度
         */
        private int tabWidth;
        /**
         * 共有多少周(小方格)
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
