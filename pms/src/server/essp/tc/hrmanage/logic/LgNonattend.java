package server.essp.tc.hrmanage.logic;

import server.framework.logic.AbstractBusinessLogic;
import c2s.essp.tc.nonattend.DtoNonattend;
import db.essp.tc.TcNonAtten;
import server.framework.common.BusinessException;
import java.math.BigDecimal;
import net.sf.hibernate.Session;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import net.sf.hibernate.Query;
import java.util.Iterator;
import java.util.Calendar;
import server.essp.tc.common.LgTcCommon;

public class LgNonattend extends AbstractBusinessLogic {

    /**
     * 新增一条nonattend记录
     * @param dto DtoNonattend
     */
    public void add(DtoNonattend dto) {
        TcNonAtten tcNonAtten = new TcNonAtten();
        try {
            tcNonAtten.setUserId(dto.getLoginId());
            tcNonAtten.setTimeFrom(dto.getDateFrom());
            tcNonAtten.setTimeTo(dto.getDateTo());
            tcNonAtten.setTotalHours(new BigDecimal(dto.getTotalHours().doubleValue()));
            tcNonAtten.setRemark(dto.getRemark());
            this.getDbAccessor().save(tcNonAtten);

        } catch (Exception ex) {
            log.error(ex);
            throw new BusinessException("Error", "add nonattend record error!");
        }
    }
    /**
     * 根据主键删除相应的nonattend记录
     * @param dto DtoNonattend
     */
    public void delete(DtoNonattend dto){
        TcNonAtten tcNonAtten;
        try {
            Long nonattendRid=dto.getRid();
            Session session = this.getDbAccessor().getSession();
            tcNonAtten = (TcNonAtten) session.load(TcNonAtten.class, nonattendRid);
            if (tcNonAtten != null) {
                session.delete(tcNonAtten);
            }
        } catch (Exception ex) {
            log.error(ex);
            throw new BusinessException("Error", "delete nonattend record error!");
        }
    }
    /**
     * 更新nonattend
     * @param dto DtoNonattend
     */
    public void update(DtoNonattend dto){
       TcNonAtten tcNonAtten;
       try {
           Long nonattendRid=dto.getRid();
           Session session = this.getDbAccessor().getSession();
           tcNonAtten=(TcNonAtten)session.load(TcNonAtten.class,nonattendRid);
           tcNonAtten.setTimeFrom(dto.getDateFrom());
           tcNonAtten.setTimeTo(dto.getDateTo());
           tcNonAtten.setTotalHours(new BigDecimal(dto.getTotalHours().doubleValue()));
           tcNonAtten.setRemark(dto.getRemark());
           session.update(tcNonAtten);

       } catch (Exception ex) {
           log.error(ex);
           throw new BusinessException("Error", "update attendance record error!");
       }

   }
   /**
    * 根据userId在给定period内的所有nonattend记录
    * @param userId String
    * @param beginPeriod Date
    * @param endPeriod Date
    * @return List
    */
   public List nonattendList(String userId,Date beginPeriod,Date endPeriod){
       List resultList=new ArrayList();
       //将开始时间的时分秒都设为0
       Date resetBeginPeriod = LgTcCommon.resetBeginDate(beginPeriod);
       //将结束时间的设置为23:59:59 999
       Calendar cal = Calendar.getInstance();
       cal.setTime(endPeriod);
       cal.set(Calendar.HOUR_OF_DAY,23);
       cal.set(Calendar.HOUR_OF_DAY, 23);
       cal.set(Calendar.MINUTE, 59);
       cal.set(Calendar.SECOND, 59);
       cal.set(Calendar.MILLISECOND, 1000);
       Date resetEndPeriod = cal.getTime();
//       Date resetEndPeriod = LgTcCommon.resetEndDate(endPeriod);
       String querySql="from TcNonAtten tc where tc.userId=:userId";
       String condition1 = "(tc.timeFrom>=:beginPeriod and tc.timeFrom<=:endPeriod)";
       String condition2 = "(tc.timeFrom<=:beginPeriod and tc.timeTo>=:beginPeriod)";
       String condition = " and ( " + condition1 + " or " + condition2 + " ) ";
       querySql = querySql + condition+" order by tc.timeFrom";

       try{
           Query q = this.getDbAccessor().getSession().createQuery(querySql);
           q.setString("userId",userId);
           q.setDate("beginPeriod",resetBeginPeriod);
           q.setDate("endPeriod",resetEndPeriod);
           List dbResult = q.list();
           Iterator it=dbResult.iterator();
           while(it.hasNext()){
               TcNonAtten tcNonatten=(TcNonAtten)it.next();
               DtoNonattend dto=new DtoNonattend();
               dto.setRid(tcNonatten.getRid());
               dto.setLoginId(tcNonatten.getUserId());
               dto.setTimeFrom(tcNonatten.getTimeFrom());
               dto.setDateFrom(tcNonatten.getTimeFrom());

               dto.setTimeTo(tcNonatten.getTimeTo());
               dto.setDateTo(tcNonatten.getTimeTo());

               dto.setDate(tcNonatten.getTimeFrom());
               dto.setTotalHours(new Double(tcNonatten.getTotalHours().doubleValue()));
               dto.setRemark(tcNonatten.getRemark());
               resultList.add(dto);
           }
           return resultList;

       }  catch (Exception ex) {
           log.error(ex);
           throw new BusinessException("Error", "list nonattend record error!");
       }

   }


}
