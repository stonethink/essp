package server.essp.pms.psr.logic;

import java.sql.SQLException;
import java.util.Date;
import javax.sql.RowSet;
import com.wits.util.comDate;
import server.essp.framework.logic.AbstractESSPLogic;
import java.util.List;
import java.util.ArrayList;
import java.util.Calendar;
import c2s.essp.common.calendar.WorkCalendar;
import java.util.GregorianCalendar;
import c2s.essp.common.calendar.WrokCalendarFactory;
import c2s.essp.common.calendar.WorkCalendarConstant;

/**
 * <p>Title:统计PV和Budget </p>
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
public class LgStaticsPV extends AbstractESSPLogic {
    public List getAllBudget(Long acntRid) {
        List result = new ArrayList();
        String sql =
            "select t.month,sum(t.bgt_unit_num)*"
            + WorkCalendarConstant.MONTHLY_WORK_DAY
            + "*" + WorkCalendarConstant.DAILY_WORK_HOUR + " as budget"
            + " from cbs_bgt_labor_mon t,cbs_bgt_labor c"
            + " where t.bgt_labor_rid=c.rid and c.bgt_rid in ("
            + " select p.base_rid from pms_acnt_cost p where p.rid=" +
            acntRid.toString()
            + ") group by t.month order by t.month";
        RowSet set = this.getDbAccessor().executeQuery(sql);
        try {
            while (set.next()) {
                Double budget = new Double(set.getDouble("budget"));
                if (budget.compareTo(new Double(0)) == 0) {
                    continue;
                }
                Date date = set.getDate("month");
                LaborCostPerMonthBean bean = new LaborCostPerMonthBean();
                bean.setMonth(date);
                bean.setBudgetPerMonth(budget);
                result.add(bean);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    /**
     * PV = 当月的预算分摊到每个自然周的预算值
     */
    public Double getPv(List allBudget, Date start, Date finish) {
        Double pv = new Double(0);
        //判断开始日期那一月的25号是不是在此区间内，如果在的话，就要作特别处理
        String t = comDate.dateToString(start, "yyyy/MM/dd");
        Date monthEnd = comDate.toDate(t.substring(0, 7) + "/25");
        if (monthEnd.after(start) && monthEnd.before(finish)) {
            //如果25号在这段区间内,就要把这段时间分成两段
            //此时PV＝25号所在月的所有前面的月＋后半段几天所在月的预算
            for (int i = 0; i < allBudget.size(); i++) {
                LaborCostPerMonthBean bean = (LaborCostPerMonthBean) allBudget.
                                             get(i);
                if (bean.getMonth().before(start)) {
                    pv = sumBothDouble(pv, bean.getBudgetPerMonth());
                } else {
                    break;
                }
            }
            pv = sumBothDouble(getBudgetAtMonthByPeriod(allBudget, finish), pv);
        } else {
            //否则求出以前所有月的预算，再加上当月的预算
            Date m = calculateMonth(finish);
            for (int i = 0; i < allBudget.size(); i++) {
                LaborCostPerMonthBean bean = (LaborCostPerMonthBean) allBudget.
                                             get(i);
                if (bean.getMonth().before(m)) {
                    pv = sumBothDouble(pv, bean.getBudgetPerMonth());
                } else {
                    break;
                }
            }
            pv = sumBothDouble(getBudgetAtMonthByPeriod(allBudget, finish), pv);
        }

        return pv;
    }

    //计算此项目总的Budget
    public Double getTotalBudget(List allBudget) {
        Double result = new Double(0);
        for (int i = 0; i < allBudget.size(); i++) {
            LaborCostPerMonthBean bean = (LaborCostPerMonthBean) allBudget.
                                         get(i);
            result = this.sumBothDouble(bean.getBudgetPerMonth(), result);
        }
        return result;
    }

    //传入一个日期，得到从传入日期当月的26号开始到传入日期的Budget
    private Double getBudgetAtMonthByPeriod(List allBudget, Date finish) {

        Calendar cal = GregorianCalendar.getInstance();
        cal.setTime(finish);
        Calendar[] period = WorkCalendar.getBEMonthDay(cal);
        Date start = period[0].getTime();
        int days = calculateDays(start, finish);
        int monthDays = calculateDays(start, period[1].getTime());
        Date month = calculateMonth(finish);
        Double cuurentMonthBudget = new Double(0);
        for (int i = 0; i < allBudget.size(); i++) {
            LaborCostPerMonthBean bean = (LaborCostPerMonthBean) allBudget.
                                         get(i);
            if (bean.getMonth().compareTo(month) == 0) {
                cuurentMonthBudget = bean.getBudgetPerMonth();
                break;
            }
        }
        double result = (new Double(days)).doubleValue() /
                        (new Double(monthDays)).doubleValue() *
                        cuurentMonthBudget.doubleValue();
        return new Double(result);
    }

    //传入一个日期，计算它是否大于25号，如果大于，就把月份加1，
    //并且把日期置为1
    private Date calculateMonth(Date date) {
        Calendar cal = GregorianCalendar.getInstance();
        cal.setTime(date);
        if (cal.getTime().getDate() > 25) {
            cal.add(Calendar.MONTH, 1);
        }
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }

    //计算两个日期之间的工作日
    private int calculateDays(Date start, Date finish) {
        WorkCalendar wc = WrokCalendarFactory.serverCreate();
        Calendar cStart = Calendar.getInstance();
        Calendar cFinish = Calendar.getInstance();
        cStart.setTime(start);
        cFinish.setTime(finish);
        int days = wc.getWorkDayNum(cStart, cFinish);
        return days;
    }

    //对两个Double类型数相加
    private Double sumBothDouble(Double a, Double b) {
        double c = a.doubleValue() + b.doubleValue();
        return new Double(c);
    }

    public static void main(String[] args) {
        LgStaticsPV lgstaticspv = new LgStaticsPV();
        List allBudget = lgstaticspv.getAllBudget(new Long(6022));
        Calendar cal = Calendar.getInstance();
        for (int i = 1; i < 2; i++) {
            Date[] d = SplitWeek.getWeekPeriod(cal, -i);
            System.out.println(comDate.dateToString(d[0]) + "~~~~~~~~" +
                               comDate.dateToString(d[1]));
            Double t = lgstaticspv.getPv(allBudget, d[0], d[1]);
            System.out.println(t.toString());
        }
        System.out.println(lgstaticspv.getTotalBudget(allBudget));
    }
}


class LaborCostPerMonthBean {
    private Date month;
    private Double budgetPerMonth;

    public Double getBudgetPerMonth() {
        return budgetPerMonth;
    }

    public Date getMonth() {
        return month;
    }

    public void setBudgetPerMonth(Double budgetPerMonth) {
        this.budgetPerMonth = budgetPerMonth;
    }

    public void setMonth(Date month) {
        this.month = month;
    }
}
