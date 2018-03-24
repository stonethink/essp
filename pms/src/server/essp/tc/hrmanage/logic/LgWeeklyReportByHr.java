package server.essp.tc.hrmanage.logic;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import c2s.essp.tc.weeklyreport.DtoWeeklyReport;
import server.essp.tc.common.LgWeeklyReport;

public class LgWeeklyReportByHr extends LgWeeklyReport{

    /**
     * �г�userId�İ�����Ŀ�����ܱ���Ϣ��
     * Hr�����˵��ܱ�ʱ������Ҫactivity��Ϣ���������ｫͬһ��account�µ��ܱ�������
     * ��ʱ������Ϊ���ܡ�ʱ����userId�μӵ�ÿ����Ŀ����������һ����¼
     * ��ʱ������Ϊ���¡�ʱ���ԡ��¡��ڵ�ÿ���ܡ�����userId�μӵ�ÿ����Ŀ����������һ����¼
     */
    public List listByUserId(String userId, Date beginPeriod, Date endPeriod) {
        List result = new ArrayList();
        Map weeklyReportMap = new HashMap();

        List weeklyReportList = super.listByUserId(userId, beginPeriod, endPeriod);
        for (Iterator itWeeklyReport = weeklyReportList.iterator(); itWeeklyReport.hasNext(); ) {
            DtoWeeklyReport dto = (DtoWeeklyReport) itWeeklyReport.next();

            AcntAndPeriod key = new AcntAndPeriod(dto.getAcntRid(), dto.getBeginPeriod(), dto.getEndPeriod() );
            DtoWeeklyReport dtoByHr = (DtoWeeklyReport)weeklyReportMap.get(key);
            if( dtoByHr == null ){
                weeklyReportMap.put(key, dto);
                result.add(dto);
            } else{
                for (int i = DtoWeeklyReport.SATURDAY; i <= DtoWeeklyReport.FRIDAY; i++) {
                    if( dtoByHr.getHour(i) == null ){
                        dtoByHr.setHour(i, dto.getHour(i));
                    }else{
                        if( dto.getHour(i) != null ){
                            BigDecimal sum = dtoByHr.getHour(i).add( dto.getHour(i));
                            dtoByHr.setHour(i, sum);
                        }
                    }
                }
            }
        }

        return result;
    }

    private class AcntAndPeriod{
        Long acntRid;
        Date beginPeriod;
        Date endPeriod;
        public AcntAndPeriod(Long acntRid, Date beginPeriod, Date endPeriod){
            this.acntRid = acntRid;
            this.beginPeriod = beginPeriod;
            this.endPeriod = endPeriod;
        }

        public int hashCode(){
            return this.acntRid.hashCode() +
                    this.beginPeriod.hashCode()
                    + this.endPeriod.hashCode();
        }

        public boolean equals(Object obj){

            if( obj == this ){
                return true;
            }

            if (obj != null && obj instanceof AcntAndPeriod) {
                AcntAndPeriod other = (AcntAndPeriod) obj;
                if (other.acntRid.equals(this.acntRid) &&
                    other.beginPeriod.equals(this.beginPeriod) &&
                    other.endPeriod.equals(this.endPeriod)) {
                    return true;
                }
            }

            return false;
        }
    }
}
