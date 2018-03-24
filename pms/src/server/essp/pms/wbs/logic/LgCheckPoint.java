package server.essp.pms.wbs.logic;

import server.framework.logic.AbstractBusinessLogic;
import db.essp.pms.CheckPoint;
import java.util.ArrayList;
import c2s.dto.DtoUtil;
import c2s.essp.pms.wbs.DtoCheckPoint;
import java.util.List;
import java.util.Iterator;
import net.sf.hibernate.Session;
import server.framework.common.BusinessException;
import c2s.dto.IDto;
import db.essp.pms.CheckPointPK;
import net.sf.hibernate.*;
import server.essp.framework.logic.AbstractESSPLogic;
import server.essp.pms.wbs.viewbean.VbCheckPoint;
import com.wits.util.comDate;

public class LgCheckPoint  extends AbstractESSPLogic {
    /**
     * 列出WBS所有的CheckList
     * @param acntRid Long
     * @param wbsRid Long
     * @return List
     */
    public List listCheckPointDto(Long acntRid, Long wbsRid) {
        List result = new ArrayList();
        try {
            List l = listCheckPoint(acntRid, wbsRid);
            Iterator i = l.iterator();
            while (i.hasNext()) {
                CheckPoint checkPoint = (CheckPoint) i.next();
                DtoCheckPoint dto = new DtoCheckPoint();
                DtoUtil.copyBeanToBean(dto, checkPoint);
                dto.setAcntRid(checkPoint.getPk().getAcntRid());
                dto.setWbsRid(checkPoint.getPk().getWbsRid());
                dto.setRid(checkPoint.getPk().getRid());
                result.add(dto);
            }
        } catch (Exception ex) {
            log.error(ex);
            throw new BusinessException("WBS_CH_001","error while listing check point!",ex);
        }
        return result;
    }

    public List listCheckPoint(Long acntRid, Long wbsRid) throws Exception {
        Session s = this.getDbAccessor().getSession();
        List l = s.createQuery("from CheckPoint check " +
                               "where check.pk.acntRid=:acntRid " +
                               "and  check.pk.wbsRid=:wbsRid " +
                               "order by check.dueDate")
                 .setParameter("acntRid", acntRid)
                 .setParameter("wbsRid", wbsRid)
                 .list();
        return l;
    }
    public static final String DATE_STYLE = "yyyy/MM/dd";
    public List listCheckPointReport(Long acntRid, Long wbsRid){
        List result = new ArrayList();
        try {
            List l = listCheckPoint(acntRid,wbsRid);
           Iterator i = l.iterator();
           while(i.hasNext()){
               CheckPoint checkPoint = (CheckPoint) i.next();
               VbCheckPoint vb = new VbCheckPoint();
               DtoUtil.copyBeanToBean(vb, checkPoint);
               vb.setDueDate(comDate.dateToString(checkPoint.getDueDate(),DATE_STYLE));
               vb.setFinishDate(comDate.dateToString(checkPoint.getFinishDate(),DATE_STYLE));
               if(checkPoint.getWeight()!=null)
                   vb.setWeight(checkPoint.getWeight().toString());
               else
                   vb.setWeight("");
               result.add(vb);
           }
        } catch (Exception ex) {
           throw new BusinessException("WBS_CH_002","error while reporting milestone checkpoints",ex);
       }
       return result;
   }
    public void updateDtoList(List checkPointList){
        if(checkPointList == null)
            return;
        Iterator i = checkPointList.iterator();
        while(i.hasNext()){
            DtoCheckPoint dto = (DtoCheckPoint) i.next();
            if(dto.getOp().equals(IDto.OP_INSERT)){
                add(dto);
                dto.setOp(IDto.OP_NOCHANGE);
            }else if(dto.getOp().equals(IDto.OP_DELETE)){
                delete(dto);
                i.remove();
            }else if(dto.getOp().equals(IDto.OP_MODIFY)){
                update(dto);
                dto.setOp(IDto.OP_NOCHANGE);
            }
        }
    }
    public void add(CheckPoint checkPoint){
        try{
            Long rid = this.generateRid(checkPoint.getPk().getAcntRid(),checkPoint.getPk().getWbsRid());
            checkPoint.getPk().setRid(rid);
            this.getDbAccessor().save(checkPoint);


        }catch(Exception ex){
            log.error(ex);
            throw new BusinessException("WBS_CH_003","error while adding checkpoint",ex);
        }
    }
    public void add(DtoCheckPoint dto){
        try {
            CheckPoint checkPoint = getDBModel(dto);
            Double weight = checkPoint.getWeight();
            if(weight == null || weight.doubleValue() == 0){
                weight = new Double(1);
                checkPoint.setWeight(weight);
            }
            Long rid = this.generateRid(dto.getAcntRid(),dto.getWbsRid());
            checkPoint.getPk().setRid(rid);
            this.getDbAccessor().save(checkPoint);
        } catch (Exception ex) {
             log.error(ex);
            throw new BusinessException("WBS_CH_003","error while adding checkpoint",ex);
        }
    }

    private Long generateRid(Long acntRid, Long wbsRid){
        try {
            Session s = this.getDbAccessor().getSession();
            List l =s.createQuery("select max(check.pk.rid) from  CheckPoint check " +
                                  "where check.pk.acntRid=:acntRid " +
                                  "and  check.pk.wbsRid=:wbsRid ")
                    .setParameter("acntRid", acntRid)
                    .setParameter("wbsRid", wbsRid)
                    .list();
            if(l == null || l.size() <=0 )
                return new Long(0);
            else{
                Long maxRid = (Long) l.get(0);
                if(maxRid == null)
                    return new Long(0);
                return new Long( maxRid.longValue() + 1);
            }
        } catch (Exception ex) {
             log.error(ex);
            throw new BusinessException("WBS_CH_004","error while generating rid of checkpoint!",ex);
        }
    }
    private CheckPoint getDBModel(DtoCheckPoint dto) throws Exception {
        CheckPoint result = new CheckPoint();
        DtoUtil.copyBeanToBean(result,dto);
        CheckPointPK pk = new CheckPointPK(dto.getAcntRid(),dto.getWbsRid(),dto.getRid());
        result.setPk(pk);
        return result;
    }
    public void update (DtoCheckPoint dto){
        try {
            CheckPoint checkPoint = getDBModel(dto);
            Session s = this.getDbAccessor().getSession();
            CheckPoint old = (CheckPoint) s.load(CheckPoint.class,checkPoint.getPk());
            DtoUtil.copyBeanToBean(old,checkPoint);
            s.flush();
        } catch (Exception ex) {
            log.error(ex);
            throw new BusinessException("WBS_CH_005","error while updating checkpoint!",ex);
        }
    }

    public void delete (DtoCheckPoint dto){
        try {
            CheckPoint checkPoint = getDBModel(dto);
            this.getDbAccessor().delete(checkPoint);
        } catch (Exception ex) {
             log.error(ex);
            throw new BusinessException("WBS_CH_006","error while deleting checkpoint!",ex);
        }
    }
}
