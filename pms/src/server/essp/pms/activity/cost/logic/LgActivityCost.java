package server.essp.pms.activity.cost.logic;


import java.util.ArrayList;
import java.util.List;

import javax.sql.RowSet;

import c2s.dto.DtoUtil;
import c2s.essp.cbs.DtoPrice;

import db.essp.cbs.SysPrice;
import net.sf.hibernate.Session;
import server.essp.framework.logic.AbstractESSPLogic;
import server.framework.common.BusinessException;
import c2s.essp.common.calendar.WorkCalendarConstant;
import c2s.essp.pms.activity.DtoActivityCost;

/**
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
public class LgActivityCost extends AbstractESSPLogic {
    public LgActivityCost() {}


    public DtoPrice getPrice(Long acntRid, String id, String catalog) {
        try {
            Session s = this.getDbAccessor().getSession();
            Object obj = s.createQuery("from SysPrice price " +
                                       "where price.acntRid=:acntRid " +
                                       "and price.catalog=:catalog " +
                                       "and id=:id "
                         )
                         .setParameter("acntRid", acntRid)
                         .setParameter("catalog", catalog)
                         .setParameter("id", id)
                         .uniqueResult();
            if (obj == null) {
                return null;
            }
            SysPrice price = (SysPrice) obj;
            DtoPrice dto = new DtoPrice();
            DtoUtil.copyBeanToBean(dto, price);
            return dto;
        } catch (Exception ex) {
            throw new BusinessException("CBS_PRICE_0014",
                                        "error while getting price!", ex);
        }
    }

    public List load(String acntRid, String activityId) throws
        BusinessException {

//得到一个account的一个活动的相关信息
        try {

            List result = new ArrayList();
            DtoPrice dtoP = new DtoPrice();
            double price = 0;
            RowSet rs = null;

//                if (dto1.getIsActivity().equals("Null")) {
//                    continue;
//                } else

            rs = this.getDbAccessor().executeQuery(
                "select * from pms_activity_workhours t where acnt_rid='" +
                acntRid + "'and t.activity_rid='" + activityId +
                "' and t.is_activity=1");

            double activityAmtA = 0;
            double activityAmtB = 0;
            double activityAmtR = 0;
            double aph = 0;
            double bph = 0;

//得到一个account的某个活动的不同职业等级相关信息，遍历结果，计算ph，amt，price，放入一个dto里
            while (rs.next()) {
                DtoActivityCost dto = new DtoActivityCost();
                String level = rs.getString("position_level");

                dtoP = getPrice(Long.valueOf(acntRid), level, "Labor");

                if (dtoP == null) {
                    price = 0;
                    dto.setPrice(null);
                    dto.setJobCode(level);

                } else {
                    price = dtoP.getPrice().doubleValue();
                    dto.setPrice(new Double(price));
                    dto.setJobCode(dtoP.getName());
                }

                if (rs.getString("plan_hours") != null) {
                    bph = Double.valueOf(rs.getString("plan_hours")).
                          doubleValue();
                    activityAmtB = price * bph /
                                   WorkCalendarConstant.MONTHLY_WORK_HOUR;
                }
                if (rs.getString("actual_hours") != null) {
                    aph = Double.valueOf(rs.getString("actual_hours")).
                          doubleValue();
                    activityAmtA = price * aph /
                                   WorkCalendarConstant.MONTHLY_WORK_HOUR;
                }
                activityAmtR = activityAmtB - activityAmtA;

                dto.setActualPh(new Double(aph));
                dto.setBudgetPh(new Double(bph));
                dto.setBudgetAmt(new Double(activityAmtB));
                dto.setActualAmt(new Double(activityAmtA));
                dto.setRemain(new Double(activityAmtR));
                result.add(dto);

            }

            rs.close();

            //合并No JobCode项
            DtoActivityCost dtoNo = new DtoActivityCost();
            dtoNo.setActualAmt(Double.valueOf("0"));
            dtoNo.setBudgetAmt(Double.valueOf("0"));
            dtoNo.setRemain(Double.valueOf("0"));
            double bphN = 0;
            double aphN = 0;
            double rphN = 0;

            for (int i = 0; i < result.size(); i++) {
                DtoActivityCost dtoCost = (DtoActivityCost) result.get(i);

                if (dtoCost.getJobCode().equals("No JobCode")) {
                    bphN += dtoCost.getBudgetPh().doubleValue();
                    aphN += dtoCost.getActualPh().doubleValue();
                    dtoNo.setJobCode("No JobCode");
                    result.remove(i);
                    i -= 1;
                } else {
                    continue;
                }
            }

            dtoNo.setActualPh(new Double(aphN));
            dtoNo.setBudgetPh(new Double(bphN));
            dtoNo.setRemain(new Double(rphN));

            if (dtoNo.getJobCode() != null) {
                result.add(dtoNo);
            }

            return result;

        } catch (Exception ex) {
            throw new BusinessException("LgActivityCost",
                                        "error while load account cost: code="
                                        , ex);
        }
    }

    //测试函数
//    public static void main(String[] args) {
//        try {
//            LgActivityCost lg = new LgActivityCost();
//            DtoActivityCost dto = new DtoActivityCost();
//            List all = new ArrayList();
//            all = lg.load("908", "2", "1");
//            dto = (DtoActivityCost) all.get(1);
//            //System.out.println(dto.getCode());
//            System.out.println(dto.getActualPh());
//
//            //lg.getDbAccessor().followTx();
//            //lg.getDbAccessor().commit();
//        } catch (Exception ex) {
//            //log.error(ex);
//        }
//    }
}
