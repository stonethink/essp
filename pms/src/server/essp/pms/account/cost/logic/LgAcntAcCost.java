package server.essp.pms.account.cost.logic;


import c2s.essp.cbs.DtoPrice;
import server.essp.framework.logic.AbstractESSPLogic;
import java.util.ArrayList;
import c2s.dto.DtoUtil;
import javax.sql.RowSet;
import server.framework.common.BusinessException;
import java.util.List;
import db.essp.cbs.SysPrice;
import net.sf.hibernate.Session;
import c2s.essp.pms.account.cost.DtoAcntActivityCost;
import c2s.essp.pms.account.cost.DtoAcntActivityCostSum;
import c2s.essp.pms.account.cost.DtoSum;

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
public class LgAcntAcCost extends AbstractESSPLogic {
    public LgAcntAcCost() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

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

    public List load(String acntRid) throws BusinessException {

        //通过以上得到acnt_rid position_level actual_workhours budget_workhours

        try {
            List result = new ArrayList();

            //得到要查询的项目组的cost


            RowSet rs = this.getDbAccessor().executeQuery("select activity_rid,code_value_rid,code,name,sum(plan_hours) as plan_hours, sum(actual_hours) as actual_hours from pms_activity_workhours t where t.acnt_rid='"+acntRid + "' group by t.activity_rid,t.code_value_rid,t.code,t.name");
          /*  RowSet rs = this.getDbAccessor().executeQuery("select  activity_rid ,code_value_rid,name,sum(plan_hours)as budget_hours, sum(actual_hours)as actual_hours from pms_activity_workhours t where acnt_rid='" +
                acntRid +
                "' group by activity_rid,code_value_rid,name order by activity_rid");*/
            DtoSum dtoSum = new DtoSum();

            String rid = null;
            String level = null;
            String job = null;

            double aph;
            double bph;
            double rph;
            double aphSum = 0;
            double bphSum = 0;
            double rphSum = 0;
            double aAmtSum = 0;
            double bAmtSum = 0;
            double rAmtSum = 0;
            Double b = null;
            while (rs.next()) {

                DtoAcntActivityCost dto = new DtoAcntActivityCost();

                dto.setCode(rs.getString("code"));

                if (rs.getString("activity_rid") == null &&
                    rs.getString("code_value_rid") != null) {
                    dto.setIsActivity("0");
                    dto.setActivityId(Long.valueOf(rs.getString(
                        "code_value_rid")));
                } else if (rs.getString("activity_rid") != null) {
                    dto.setIsActivity("1");
                    dto.setActivityId(Long.valueOf(rs.getString("activity_rid")));
                } else {
                    dto.setIsActivity("2");
                }

                dto.setActivityName(rs.getString("name"));

                //注意区分值和对象*/

               if (rs.getObject("plan_hours") == null) {
                   bph = 0;
               } else {
                   bph = Double.valueOf(rs.getObject("plan_hours").toString()).
                         doubleValue();
               }

                if (rs.getObject("actual_hours") == null) {
                    aph = 0;
                } else {
                    aph = Double.valueOf(rs.getObject("actual_hours").toString()).
                          doubleValue();
                }
                rph = bph - aph; //计算多余的工作量
                b = new Double(rph); //用构造函数来使一个值变成对象
                dto.setActualPh(new Double(aph));
                dto.setBudgetPh(new Double(bph));
                dto.setRemainPh(b);
                //合算ph值
                bphSum += dto.getBudgetPh().doubleValue();
                aphSum += dto.getActualPh().doubleValue();
                rphSum += dto.getRemainPh().doubleValue();

                //得到相应的价格信息

                result.add(dto);
            }

            rs.close();

            //得到各个活动中职业等级相关信息,选择的方法实际上复杂了，因为该视图已经能够使activity唯一了
            List result1 = new ArrayList();
            DtoPrice dtoP = new DtoPrice();
            double price = 0;
            for (int ii = 0; ii < result.size(); ii++) {
                String activityId = null;
                String code = null;
                RowSet rs1 = null;
                DtoAcntActivityCost dto1 = (DtoAcntActivityCost) result.get(ii);
                if (dto1.getIsActivity().equals("2")) {
                    continue;
                } else
                if (dto1.getIsActivity().equals("1")) {
                    activityId = dto1.getActivityId().toString();
                    rs1 = this.getDbAccessor().executeQuery("select position_level,sum(plan_hours) as level_bud_hours,sum(actual_hours) as level_act_hours from pms_activity_workhours t where acnt_rid='" +
                        acntRid + "'and t.activity_rid='" + activityId +
                        "' and t.is_activity=1 group by position_level");
                } else {
                    code = dto1.getActivityId().toString();
                    rs1 = this.getDbAccessor().executeQuery("select position_level,sum(plan_hours) as level_bud_hours,sum(actual_hours) as level_act_hours from pms_activity_workhours t where acnt_rid='" +
                        acntRid + "'and t.code_value_rid='" + code +
                        "' and t.is_activity=0 group by position_level");
                }

                //计算每一个活动的amt值
                double activityAmtA = 0;
                double activityAmtB = 0;
                double activityAmtR = 0;
                while (rs1.next()) {
                    level = rs1.getString("position_level");
                    dtoP = getPrice(Long.valueOf(acntRid), level, "Labor");

                    if (dtoP == null) {
                        price = 0;
                    } else {
                        price = dtoP.getPrice().doubleValue();
                    }
                    if (rs1.getString("level_bud_hours") != null) {
                        activityAmtB += price *
                            Double.valueOf(rs1.getString("level_bud_hours")).
                            doubleValue()/168.0F;
                    }
                    if (rs1.getString("level_act_hours") != null) {
                        activityAmtA += price *
                            Double.valueOf(rs1.getString("level_act_hours")).
                            doubleValue()/168.0F;
                    }
                    activityAmtR = activityAmtB - activityAmtA;
                }
                dto1.setBudgetAmt(new Double(activityAmtB));
                dto1.setActualAmt(new Double(activityAmtA));
                dto1.setRemainAmt(new Double(activityAmtR));

                aAmtSum += dto1.getActualAmt().doubleValue();
                bAmtSum += dto1.getBudgetAmt().doubleValue();
                rAmtSum += dto1.getRemainAmt().doubleValue();

                result1.add(dto1);

            }

            dtoSum.setCode("Sum");
            dtoSum.setBudgetPh(new Double(bphSum));
            dtoSum.setActualPh(new Double(aphSum));
//            dtoSum.setRemainPh(new Double(rphSum));
            dtoSum.setBudgetAmt(new Double(bAmtSum));
            dtoSum.setActualAmt(new Double(aAmtSum));
//            dtoSum.setRemainAmt(new Double(rAmtSum));
            result1.add(0, dtoSum);
            return result1;
        }

        catch (Exception ex) {
            throw new BusinessException("LgAcntAcCost",
                                        "error while load account cost: code="
                                        , ex);
        }
    }

//测试函数
 /*   public static void main(String[] args) {
        try {
            LgAcntAcCost lg = new LgAcntAcCost();
            DtoAcntActivityCost dto = new DtoAcntActivityCost();
            List all = new ArrayList();
            all = lg.load("908");
            dto = (DtoAcntActivityCost) all.get(2);
            System.out.print(dto.getActivityName());

            //lg.getDbAccessor().followTx();
            //lg.getDbAccessor().commit();
        } catch (Exception ex) {
            //log.error(ex);
        }
    }*/

    private void jbInit() throws Exception {
    }
}
