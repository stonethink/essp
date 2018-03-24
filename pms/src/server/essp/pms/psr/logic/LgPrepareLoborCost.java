package server.essp.pms.psr.logic;

import server.essp.framework.logic.AbstractESSPLogic;
import java.util.List;
import java.util.ArrayList;
import server.essp.pms.psr.bean.LaborCostSheetBean;
import javax.sql.RowSet;
import java.sql.*;
import java.lang.reflect.Method;
import java.lang.reflect.*;
import com.wits.util.StringUtil;

/**
 * <p>Title:此类主要用于准备LaborCost页的数据 </p>
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
public class LgPrepareLoborCost extends AbstractESSPLogic {
    List allLaborCost = new ArrayList();


    private void staticsLaborCostData(Long acntRid) {
        String sql = "select t.type,t.user_id,sum(t.total_hours) as hours from pms_status_report_laborcost t"
                     + " where t.acnt_rid=" + acntRid.toString()
                     + " group by t.type,t.user_id order by t.user_id";
        RowSet rs = this.getDbAccessor().executeQuery(sql);
        try {
            while (rs.next()) {
                String user = rs.getString(2);
                LaborCostSheetBean bean = getBeanByUser(user);
                if (bean == null) {
                    bean = new LaborCostSheetBean();
                    bean.setUser(user);
                    writePropertyToBean(bean, rs.getString(1),
                                        new Double(rs.getDouble(3)));
                    allLaborCost.add(bean);
                } else {
                    writePropertyToBean(bean, rs.getString(1),
                                        new Double(rs.getDouble(3)));
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
//        System.out.println(allLaborCost.size());
    }

    //用反射把值写到相应的Bean中去
    private void writePropertyToBean(LaborCostSheetBean bean, String property,
                                     Double value) {
        Method[] methods = bean.getClass().getMethods();
        String methodName = "set" + property.substring(0, 1).
                            toUpperCase() + property.substring(1,
            property.length()).toLowerCase();
        for (int i = 0; i < methods.length; i++) {
            if (methods[i].getName().equals(methodName)) {
                try {
                    methods[i].invoke(bean, new Object[] {value});
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    //通过user来查找List中对应的Bean
    private LaborCostSheetBean getBeanByUser(String user) {
        for (int i = 0; i < allLaborCost.size(); i++) {
            LaborCostSheetBean bean = (LaborCostSheetBean) allLaborCost.get(i);
            if (bean.getUser().equals(user)) {
                return bean;
            }
        }
        return null;
    }

    public List getAllLaborCost(Long acntRid) {
        staticsLaborCostData(acntRid);
        for (int i = 0; i < allLaborCost.size(); i++) {
            LaborCostSheetBean bean = (LaborCostSheetBean) allLaborCost.get(i);
            bean.setTotal(calculateTotalValue(bean));
        }
        return allLaborCost;
    }

    public LaborCostSheetBean sumAllUser(List cost) {
        LaborCostSheetBean result = new LaborCostSheetBean();
        result.setRd(sumByKind(cost, "Rd"));
        result.setDe(sumByKind(cost, "De"));
        result.setBd(sumByKind(cost, "Bd"));
        result.setTt(sumByKind(cost, "Tt"));
        result.setAw(sumByKind(cost, "Aw"));
        result.setPm(sumByKind(cost, "Pm"));
        result.setCm(sumByKind(cost, "Cm"));
        result.setCs(sumByKind(cost, "Cs"));
        result.setQa(sumByKind(cost, "Qa"));
        result.setOt(sumByKind(cost, "Ot"));
        result.setOa(sumByKind(cost, "Oa"));
        result.setTotal(sumByKind(cost, "Total"));
        return result;
    }

    private Double sumByKind(List cost, String kind) {
        Double result = new Double(0);
        for (int i = 0; i < cost.size(); i++) {
            LaborCostSheetBean bean = (LaborCostSheetBean) cost.get(i);
            bean.setUser(getCnName(bean.getUser()));
            Method[] methods = bean.getClass().getMethods();
            String methodName = "get" + kind;
            for (int j = 0; j < methods.length; j++) {
                if (methods[j].getName().equals(methodName)) {
                    Double value = new Double(0);
                    try {
                        value = (Double) methods[j].invoke(bean);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    if (value == null) {
                        value = new Double(0);
                    }
                    result = sumBothDouble(result, value);
                }
            }
        }
        return result;
    }

    private Double calculateTotalValue(LaborCostSheetBean bean) {
        Double result = new Double(0);
        result = sumBothDouble(result, bean.getRd());
        result = sumBothDouble(result, bean.getDe());
        result = sumBothDouble(result, bean.getBd());
        result = sumBothDouble(result, bean.getTt());
        result = sumBothDouble(result, bean.getAw());
        result = sumBothDouble(result, bean.getPm());
        result = sumBothDouble(result, bean.getCm());
        result = sumBothDouble(result, bean.getCs());
        result = sumBothDouble(result, bean.getQa());
        result = sumBothDouble(result, bean.getOt());
        result = sumBothDouble(result, bean.getOa());
        return result;
    }

    private Double sumBothDouble(Double a, Double b) {
        Double result = new Double(0);
        if (a == null && b == null) {
            return result;
        }
        if (a == null && b != null) {
            return b;
        }
        if (a != null && b == null) {
            return a;
        }
        result = new Double(a.doubleValue() + b.doubleValue());
        return result;
    }

    //format loginId to "chinese name (loginId)"
    private String getCnName(String loginId) {
        if("".equals(StringUtil.nvl(loginId))) {
            return "";
        }
        //get chinese name
        String cnName = itf.hr.HrFactory.create().getChineseName(loginId);
        if("".equals(StringUtil.nvl(cnName))) {
            return loginId;
        }
        return cnName + "(" +loginId +")";
    }


    public static void main(String[] args) {
        LgPrepareLoborCost lgprepareloborcost = new LgPrepareLoborCost();
        lgprepareloborcost.staticsLaborCostData(new Long(6022));
        lgprepareloborcost.sumAllUser(lgprepareloborcost.getAllLaborCost(new
            Long(6022)));
    }
}
