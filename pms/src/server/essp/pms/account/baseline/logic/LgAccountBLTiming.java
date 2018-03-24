package server.essp.pms.account.baseline.logic;

import server.framework.logic.AbstractBusinessLogic;
import java.util.List;
import java.util.ArrayList;
import net.sf.hibernate.Query;
import db.essp.pms.MilestoneHistory;
import c2s.essp.pms.account.DtoAcntTiming;
import c2s.dto.DtoUtil;
import db.essp.pms.AccountBaseline;
import db.essp.pms.AccountBaselineLog;
import com.wits.util.comDate;
import java.util.Date;
import db.essp.pms.Activity;
import server.framework.common.BusinessException;

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
public class LgAccountBLTiming extends AbstractBusinessLogic {
    public static final String  IsMilestone="1";
    private Long acntRid;
    public String lastBaselineId="";
    public LgAccountBLTiming(Long acntRid){
       this.acntRid=acntRid;
    }
    public LgAccountBLTiming(String acntRid){
        this.acntRid=new Long(acntRid);
    }
    public List listBaseLineTiming(){
        List timingList=new ArrayList();
        timingList.add(0,listLastBaseLine());
        timingList.add(1,listCurrentMilestone());
        return handerReultList(timingList);
       // return timingList;
    }
    /**
     * 根据acntRid从PMS_MILESTONE_APPROVED_H表中获取最近一个被Approved的记录
     * @return List
     */
    public List listLastBaseLine(){
        if(acntRid==null)return null;
        try{
            String baselineId=getLastBaselineId(acntRid).trim();
             //如果上一个Baseline的baselineId不存在，说明当前的Baseline是第一个Baseline，则返回null
             if(baselineId.equals(""))return null;

            Query q = getDbAccessor().getSession().createQuery(
                "from MilestoneHistory m where m.acntRid=:acntRid and m.baseLineId=:baselineId order by m.wbsRid");
            q.setString("baselineId",baselineId);
            q.setLong("acntRid",acntRid.longValue());
            List resultList=q.list();
            List returnList=new ArrayList();
            for(int i=0;i<resultList.size();i++){
               MilestoneHistory milestoneHistory=
                    (MilestoneHistory)resultList.get(i);

               DtoAcntTiming dtoAcntTiming=new DtoAcntTiming();

               DtoUtil.copyProperties(dtoAcntTiming,milestoneHistory);
               if(i==0) {
                    lastBaselineId= dtoAcntTiming.getBaseLineId();
                }
                returnList.add(dtoAcntTiming);
            }

            return returnList;

        }catch(Exception ex){
            log.error(ex);
            throw new BusinessException(ex);
        }
    }

    /**
     * 根据acntRid从PMS_Activity表中获取记录
     * @return List
     */
    public List listCurrentMilestone(){
        List returnList=new ArrayList();
        if(acntRid==null)return returnList;
        try{
            String querySql = "from Activity t where t.pk.acntRid=:acntRid and t.milestone=:isMs";
            List l = this.getDbAccessor().getSession()
                     .createQuery(querySql)
                     .setParameter("isMs", Boolean.TRUE)
                     .setLong("acntRid", acntRid.longValue())
                     .list();

             if( l == null || l.size() == 0)
                 return returnList;

              AccountBaseline accountBaseLine=LoadAccountBaseLine(acntRid);
              String baselineId=accountBaseLine.getBaselineId();
              for(int i=0;i<l.size();i++){
                  Activity milestone=(Activity)l.get(i);
                  DtoAcntTiming dtoAcntTiming=new DtoAcntTiming();
                  DtoUtil.copyProperties(dtoAcntTiming,milestone,new String[]{"pk"});
                  dtoAcntTiming.setAcntRid(milestone.getPk().getAcntRid());
                  dtoAcntTiming.setWbsRid(milestone.getPk().getActivityId());

                  dtoAcntTiming.setBaseLineId(baselineId);
                  returnList.add(dtoAcntTiming);
              }
              return returnList;
           }catch(Exception ex){
            log.error(ex);
            throw new BusinessException();
        }
    }
    /**
     * 依据acntRid获取上一个Baseline（被approved）的BaselineId
     * @param anctRid Long
     * @return String
     */
    public String getLastBaselineId(Long anctRid){
        try{
            if(anctRid==null)return "";
            Query q=this.getDbAccessor().getSession().createQuery(
                    " from AccountBaselineLog a where a.acntRid=:acntRid order by rid desc");
            q.setLong("acntRid",acntRid.longValue());
            List resultList=q.list();
            if(resultList!=null&&resultList.size()>0){
                AccountBaselineLog accountBaselineLog=(AccountBaselineLog)resultList.get(0);
                return accountBaselineLog.getBaselineId();
            }
            return "";
        }catch(Exception ex){
            log.error(ex);
            return "";
        }

    }
    /**
     * 依据acntRid获取当前BaselineId
     * @param acntRid Long
     * @return AccountBaseline
     */
    public AccountBaseline LoadAccountBaseLine(Long acntRid){
        try{
            if (acntRid==null)return null;
            Query q=this.getDbAccessor().getSession().createQuery(
                " from AccountBaseline a where a.rid=:acntRid");
            q.setLong("acntRid",acntRid.longValue());
            List resultList=q.list();
            return (AccountBaseline)resultList.get(0);

        }catch(Exception ex){
            log.error(ex);
            return null;
        }
    }

    int lastBLnum=0;
    int currentMSnum=0;
    public List handerReultList(List timingList){

        if(timingList.size()<2) return timingList;

        List rsList1=(List)timingList.get(0);
        List rsList2=(List)timingList.get(1);
        if(rsList1.size()<0||rsList2.size()<0)return timingList;
        List rsList1HR=new ArrayList();//rsList1被处理之后的结果
        List rsList2HR=new ArrayList();//rsList2被处理之后的结果
        List returnList=new ArrayList(2);//本方法返回的结果

        int i=-1;

        int maxSize=rsList1.size()>rsList2.size()?rsList1.size():rsList2.size();
        while(lastBLnum<rsList1.size()&&currentMSnum<rsList2.size()){
              ++i;
              List handerList=getHanderList(rsList1,rsList2);
              DtoAcntTiming lastBaselineDto=(DtoAcntTiming)handerList.get(0);
              DtoAcntTiming currentMilestoneDto=(DtoAcntTiming)handerList.get(1);
              rsList1HR.add(i,lastBaselineDto);
              rsList2HR.add(i,currentMilestoneDto);

        }
        returnList.add(0,rsList1HR);
        returnList.add(1,rsList2HR);
        return returnList;
    }
    public List getHanderList(List blList,List msList){
        List returnList=new ArrayList(2);
        DtoAcntTiming lastBaselineDto=(DtoAcntTiming)blList.get(lastBLnum);
        DtoAcntTiming currentMilestoneDto=(DtoAcntTiming)msList.get(currentMSnum);
        int result=lastBaselineDto.getCode().compareTo(currentMilestoneDto.getCode());
        if(result<0){
            lastBLnum++;
            DtoAcntTiming newCurrentMilestoneDto=new DtoAcntTiming();
            lastBaselineDto.setFlag(1);
            newCurrentMilestoneDto.setFlag(-1);
            returnList.add(0,lastBaselineDto);
            returnList.add(1,newCurrentMilestoneDto);
        }else if(result==0){
            Date lAnticipatedFinish=lastBaselineDto.getAnticipatedFinish();
            Date cgetAnticipatedFinish=currentMilestoneDto.getAnticipatedFinish();
            String lstr=comDate.dateToString(lAnticipatedFinish,"yyyy-MM-dd");
            String cstr=comDate.dateToString(cgetAnticipatedFinish,"yyyy-MM-dd");
            if(!lstr.equalsIgnoreCase(cstr))
            {
                lastBaselineDto.setFlag(0);
                currentMilestoneDto.setFlag(0);
            }else{
                lastBaselineDto.setFlag(-1);
                currentMilestoneDto.setFlag(-1);
            }
            returnList.add(0,lastBaselineDto);
            returnList.add(1,currentMilestoneDto);
            lastBLnum++;
            currentMSnum++;
        }else{
            currentMSnum++;
            DtoAcntTiming newLaseBaselineDto=new DtoAcntTiming();
            newLaseBaselineDto.setFlag(-1);
            currentMilestoneDto.setFlag(1);
           returnList.add(0,newLaseBaselineDto);
           returnList.add(1,currentMilestoneDto);
        }
        return returnList;
    }
    public String getLastBaseLineId(){
       return this.lastBaselineId;
    }
}
