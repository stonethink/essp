package server.essp.pms.psr.logic;

import db.essp.pms.PmsStatusReportHistory;
import java.util.List;
import java.util.ArrayList;
import server.essp.pms.psr.bean.TimeDetailSheetBean;

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
public class LgPrepareTimeDetailSheet {
    public List getEVADataList(List allPSRData) {
        List result = new ArrayList();
        for (int i = 0; i < allPSRData.size(); i++) {
            PmsStatusReportHistory h = (PmsStatusReportHistory) allPSRData.get(
                i);
            TimeDetailSheetBean bean = new TimeDetailSheetBean();
            bean.setBegin(h.getStartdate());
            bean.setEnd(h.getFinishdate());
            bean.setWpTotal(h.getWpcount());
            bean.setCurrentPlan(h.getCompleteplancount());
            bean.setCurrentActual(h.getCompleteactualcount());
            long delay = bean.getCurrentActual().longValue() -
                         bean.getCurrentPlan().longValue();
            bean.setCurrentDelay(new Long(delay));

            bean.setTotalPlan(h.getTotalplancount());
            bean.setTotalActual(h.getTotalactualcount());

            double planRate = bean.getTotalPlan().doubleValue() /
                              bean.getWpTotal().doubleValue();
            bean.setRatePlan(new Double(planRate));
            double actualRate = bean.getTotalActual().doubleValue() /
                                bean.getWpTotal().doubleValue();
            bean.setRateActual(new Double(actualRate));
            double var = bean.getRateActual().doubleValue() -
                         bean.getRatePlan().doubleValue();
            bean.setVariance(new Double(var * 100));
            bean.setScore(calculateScore(new Double(var * 100)));
            bean.setRemark("");
            result.add(bean);
        }
        return result;
    }

    //���0%<=����ƫ���Ϊ5��
    //���-5%<=����ƫ��<0%����Ϊ4��
    //���-10%<=����ƫ��<-5%����Ϊ3��
    //���-15%<=����ƫ��<-10%����Ϊ2��
    //�������ƫ��<-15%����Ϊ1��
    private Long calculateScore(Double var) {
        var = new Double(Math.abs(var.doubleValue()));
        int result = 5;
        if (var.compareTo(new Double(15)) > 0) { //����15
            result = 1;
            return new Long(result);
        } else if (var.compareTo(new Double(10)) > 0) { //����10
            result = 2;
            return new Long(result);
        } else if (var.compareTo(new Double(5)) > 0) { //����5
            result = 3;
            return new Long(result);
        } else if (var.compareTo(new Double(0)) > 0) { //����0
            result = 4;
            return new Long(result);
        }
        return new Long(result);
    }

}
