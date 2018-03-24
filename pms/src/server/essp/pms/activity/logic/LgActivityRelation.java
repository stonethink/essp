package server.essp.pms.activity.logic;

import java.util.ArrayList;
import java.util.List;

import net.sf.hibernate.Session;
import server.essp.framework.logic.AbstractESSPLogic;
import server.framework.common.BusinessException;
import java.util.Iterator;
import db.essp.pms.ActivityRelation;
import db.essp.pms.Activity;
import db.essp.pms.ActivityPK;
import c2s.dto.DtoUtil;
import c2s.essp.pms.wbs.DtoActivityRelation;
import net.sf.hibernate.*;
import db.essp.pms.ActivityRelationPK;
import c2s.dto.IDto;

public class LgActivityRelation extends AbstractESSPLogic {
    /**
     * 找到一个Activity（后置节点）的所有前置节点
     * @param acntRid Long
     * @param postRid Long
     * @return List
     */
    public List getActivityPredecessorsDto(Long acntRid, Long postRid) {
        if (acntRid == null || postRid == null) {
            return null;
        }
        List result = new ArrayList();
        try {
            Session s = this.getDbAccessor().getSession();
            List l = s.createQuery("from ActivityRelation relation " +
                                   "where relation.pk.acntRid=:acntRid " +
                                   "and relation.pk.postActivityId=:postRid")
                     .setParameter("acntRid", acntRid)
                     .setParameter("postRid", postRid)
                     .list();
            Iterator i = l.iterator();
            while (i.hasNext()) {
                ActivityRelation relation = (ActivityRelation) i.next();
                ActivityPK pk = new ActivityPK(relation.getPk().getAcntRid(),
                                               relation.getPk().getActivityId());
                Activity activity = (Activity) s.load(Activity.class, pk);
                DtoActivityRelation dto = new DtoActivityRelation();
                DtoUtil.copyBeanToBean(dto, activity);
                dto.setAcntRid(pk.getAcntRid());
                dto.setActivityId(pk.getActivityId());
                dto.setPostActivityId(postRid);
                dto.setStartFinishType(relation.getStartFinishType());
                String wbsName = activity.getWbs().getName();
                dto.setWbsName(wbsName);
                result.add(dto);
            }
        } catch (Exception ex) {
            log.error(ex);
            throw new BusinessException("ACT_REL_001",
                                        "error while loading Predecessors of activity:[" +
                                        postRid +
                                        "]", ex);
        }
        return result;
    }

    /**
     * 获得一个Activity（前置节点）的所有后置节点
     * @param acntRid Long
     * @param postRid Long
     * @return List
     */
    public List getActivitySuccessorsDto(Long acntRid, Long preRid) {
        List result = new ArrayList();
        try {
            Session s = this.getDbAccessor().getSession();
            List l = s.createQuery("from ActivityRelation relation " +
                                   "where relation.pk.acntRid=:acntRid " +
                                   "and relation.pk.activityId=:preRid")
                     .setParameter("acntRid", acntRid)
                     .setParameter("preRid", preRid)
                     .list();
            Iterator i = l.iterator();
            while (i.hasNext()) {
                ActivityRelation relation = (ActivityRelation) i.next();
                ActivityPK pk = new ActivityPK(relation.getPk().getAcntRid(),
                                               relation.getPk().
                                               getPostActivityId());
                Activity activity = (Activity) s.load(Activity.class, pk);
                DtoActivityRelation dto = new DtoActivityRelation();
                DtoUtil.copyBeanToBean(dto, activity);
                dto.setAcntRid(pk.getAcntRid());
                dto.setActivityId(preRid);
                dto.setPostActivityId(relation.getPk().getPostActivityId());
                dto.setStartFinishType(relation.getStartFinishType());
                String wbsName = activity.getWbs().getName();
                dto.setWbsName(wbsName);
                result.add(dto);
            }
        } catch (Exception ex) {
            log.error(ex);
            throw new BusinessException("ACT_REL_002",
                                        "error while loading Successors of activity:[" +
                                        preRid + "]", ex);
        }
        return result;
    }


    /**
     * 获得一个Account的（前置节点）的所有后置节点
     * @param acntRid Long
     * @return List
     */
    public List getActivityAllSuccessorsDtoByAcnt(Long acntRid) {
        List result = new ArrayList();
        try {
            Session s = this.getDbAccessor().getSession();
            List l = s.createQuery("from ActivityRelation relation " +
                                   "where relation.pk.acntRid=:acntRid")
                     .setParameter("acntRid", acntRid)
                     .list();
            for (int i = 0; i < l.size(); i++) {
                ActivityRelation relation = (ActivityRelation) l.get(i);
                DtoActivityRelation dto = new DtoActivityRelation();
                dto.setAcntRid(acntRid);
                dto.setPostActivityId(relation.getPk().getPostActivityId());
                dto.setActivityId(relation.getPk().getActivityId());
                dto.setStartFinishType(relation.getStartFinishType());
                result.add(dto);
            }
        } catch (Exception ex) {
            log.error(ex);
            throw new BusinessException("ACT_REL_002",
                                        "error while loading Successors of account:[" +
                                        acntRid + "]", ex);
        }
        return result;
    }


    /**
     * 新增Activity前后置关系
     * @param dto DtoActivityRelation
     */
    public void addActivityRelation(DtoActivityRelation dto) {
        if (dto == null || dto.getAcntRid() == null
            || dto.getActivityId() == null || dto.getPostActivityId() == null) {
            return;
        }
        ActivityRelationPK pk = new ActivityRelationPK(dto.getAcntRid(),
            dto.getActivityId(), dto.getPostActivityId());
        try {
            Session s = this.getDbAccessor().getSession();
            Object obj = s.get(ActivityRelation.class, pk);
            if (obj != null) {
                throw new BusinessException(
                    "activity relation has been created!");
            }
            ActivityRelation relation = new ActivityRelation();
            DtoUtil.copyBeanToBean(relation, dto);
            relation.setPk(pk);
            s.save(relation);
            s.flush();
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error(ex);
            throw new BusinessException("ACT_REL_003",
                                        "error while creating activity relation !",
                                        ex);
        }
    }

    /**
     * 根据acntRid和activityId获取所有的activity relations
     * @param acntRid Long
     * @param activityId Long
     * @return List：ActivityRelation List
     */
    public List listRelationsBeansByAccountAndActivity(Long acntRid,Long activityId){
        List result = null;
        try {
            Session s = this.getDbAccessor().getSession();
            result = s.createQuery("from ActivityRelation relation " +
                                   "where relation.pk.acntRid=:acntRid "+
                                   " and relation.pk.activityId=:activityId "
                     )
                     .setParameter("acntRid", acntRid)
                     .setParameter("activityId",activityId)
                     .list();
            return result;
        } catch (Exception ex) {
            log.error(ex);
            throw new BusinessException("ACT_REL_004",
                                        "error while load all activity relations!",
                                        ex);
        }

    }
    /**
     * 根据acntRid获取所有的activity relations
     * @param acntRid Long
     * @return List:ActivityRelation List
     */
    public List listRelationsBeansByAccount(Long acntRid) {
        List result = null;
        try {
            Session s = this.getDbAccessor().getSession();
            result = s.createQuery("from ActivityRelation relation " +
                                   "where relation.pk.acntRid=:acntRid "
                     )
                     .setParameter("acntRid", acntRid)
                     .list();
            return result;
        } catch (Exception ex) {
            log.error(ex);
            throw new BusinessException("ACT_REL_004",
                                        "error while load all activity relations!",
                                        ex);
        }
    }

    /**
     * 根据acntRid获取所有的activity relations
     * @param acntRid Long
     * @return List:DtoActivityRelation List
     */
    public List listRelationsByAccount(Long acntRid) {
        List result = new ArrayList();
        try {
            Session s = this.getDbAccessor().getSession();
            List l = listRelationsBeansByAccount(acntRid);
            Iterator i = l.iterator();
            while (i.hasNext()) {
                ActivityRelation relation = (ActivityRelation) i.next();
                DtoActivityRelation dto = new DtoActivityRelation();
                DtoUtil.copyBeanToBean(dto, relation);
                dto.setAcntRid(relation.getPk().getAcntRid());
                dto.setActivityId(relation.getPk().getActivityId());
                dto.setPostActivityId(relation.getPk().getPostActivityId());
                result.add(dto);
            }
        } catch (Exception ex) {
            log.error(ex);
            throw new BusinessException("ACT_REL_004",
                                        "error while load all activity relations!",
                                        ex);
        }
        return result;
    }

    public void deleteActivityRelation(DtoActivityRelation dto) {
        if (dto == null) {
            return;
        }
        ActivityRelationPK pk = new ActivityRelationPK();
        pk.setAcntRid(dto.getAcntRid());
        pk.setActivityId(dto.getActivityId());
        pk.setPostActivityId(dto.getPostActivityId());
        ActivityRelation relation = new ActivityRelation(pk);
        try {
            this.getDbAccessor().delete(relation);
        } catch (Exception ex) {
            log.error(ex);
            throw new BusinessException("ACT_REL_005",
                                        "error while deleting activity relation!",
                                        ex);
        }
    }

    public void updateDto(DtoActivityRelation dto) {
        ActivityRelationPK pk = new ActivityRelationPK(dto.getAcntRid(),
            dto.getActivityId(), dto.getPostActivityId());
        try {
            Session s = this.getDbAccessor().getSession();
            ActivityRelation relation = (ActivityRelation) s.load(
                ActivityRelation.class, pk);
            relation.setStartFinishType(dto.getStartFinishType());
            relation.setDelayDays(dto.getDelayDays());
        } catch (Exception ex) {
            log.error(ex);
            throw new BusinessException("ACT_REL_006",
                                        "error while updating activity relation!",
                                        ex);
        }
    }

    public void updateDtoList(List relations) {
        if (relations == null || relations.size() == 0) {
            return;
        }
        for (int i = 0; i < relations.size(); i++) {
            DtoActivityRelation dto = (DtoActivityRelation) relations.get(i);
            if (dto.getOp().equals(IDto.OP_INSERT)) {
                this.addActivityRelation(dto);
                dto.setOp(IDto.OP_NOCHANGE);
            } else if (dto.getOp().equals(IDto.OP_DELETE)) {
                this.deleteActivityRelation(dto);
                relations.remove(i);
            } else if (dto.getOp().equals(IDto.OP_MODIFY)) {
                this.updateDto(dto);
                dto.setOp(IDto.OP_NOCHANGE);
            }
        }
    }

    /**
     * 删除该Activity所有的前置和后置节点
     * @param acntRid Long
     * @param activityRid Long
     */
    public void deleteActivityAllRelation(Long acntRid, Long activityRid) {
        //this.getDbAccessor().executeUpdate("");
    }

    public static void main(String[] arg) {
        LgActivityRelation logic = new LgActivityRelation();
        Long acntRid = new Long(1);
        Long activityRid = new Long(10330);
        logic.getActivitySuccessorsDto(acntRid, activityRid);
    }
}
