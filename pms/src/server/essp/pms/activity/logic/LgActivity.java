package server.essp.pms.activity.logic;

import c2s.essp.pms.wbs.DtoWbsActivity;
import db.essp.pms.WbsPK;
import server.framework.common.BusinessException;
import db.essp.pms.Wbs;
import server.essp.pms.wbs.logic.LgAcntSeq;
import c2s.dto.DtoUtil;
import db.essp.pms.ActivityPK;
import db.essp.pms.Activity;
import c2s.essp.common.user.DtoUser;
import java.util.List;
import net.sf.hibernate.Session;
import java.util.Iterator;
import java.util.ArrayList;
import server.essp.pms.wbs.logic.LgWbsComplete;
import server.essp.pms.wbs.logic.LgWbs;
import c2s.dto.ITreeNode;
import c2s.dto.DtoTreeNode;
import com.wits.util.StringUtil;
import net.sf.hibernate.*;

public class LgActivity extends LgWbs {
    private String userId;

    public LgActivity() {
        super();
        DtoUser user = getUser();
        if (user == null) {
            throw new BusinessException("E_WBS001", "User object is null!!!");
        }
        userId = user.getUserLoginId();
    }

    public DtoWbsActivity getCode(DtoWbsActivity dataBean) {
        String code = LgAcntSeq.getActivityCode(dataBean.getAcntRid());
        Long rid = LgAcntSeq.getActivitySeq(dataBean.getAcntRid());
        dataBean.setActivityRid(rid);
        dataBean.setCode(code);
        return dataBean;
    }

    public void add(DtoWbsActivity dataBean) {
        if (dataBean == null) {
            throw new BusinessException("E_WBS005", "dataBean is null!!!");
        }
//        if (dataBean.getManager() == null) {
//            dataBean.setManager(userId);
//        }
        Long acntRid = dataBean.getAcntRid();
        Long wbsRid = dataBean.getWbsRid();
        Activity activity = new Activity();
        try {
            /*
             * 如果没有设定code，则自动产生
             */
            //如果是插入的也自动产生CODE，主要是为了在复制的过程中使用
            //modified by:Robin.zhang
//            if (dataBean.getCode() == null ||
//                dataBean.getCode().trim().equals("") || dataBean.isInsert()) {
//                getCode(dataBean);
//            }

            //get code only for it has none.
            //modified by lipengxu
            if("".equals(StringUtil.nvl(dataBean.getCode()))) {
                getCode(dataBean);
            }
            DtoUtil.copyProperties(activity, dataBean);
            Long rid = dataBean.getActivityRid();
            if(rid == null) {
                rid = LgAcntSeq.getActivitySeq(acntRid);
                dataBean.setActivityRid(rid);
            }

            /**
             * 工期计算
             */
            double durationPlan = LgWbsComplete.caculateDurationPlan(dataBean);
            double durationActual = LgWbsComplete.caculateDurationActual(
                dataBean);
            if (durationPlan < 0) {
                durationPlan = 0;
            }
            if (durationActual < 0) {
                durationActual = 0;
            }
            dataBean.setDurationPlan(new Long((long) durationPlan));
            dataBean.setDurationActual(new Long((long) durationActual));
            dataBean.setDurationRemain(new Long((long) (durationPlan -
                durationActual)));
            double completeRate = durationActual * 100 / durationPlan;
            if (durationPlan != 0) {
                dataBean.setDurationComplete(new Double(completeRate));
            } else {
                dataBean.setDurationComplete(new Double(0));
            }

            //activity.setCode(dtoSeq.getCode());
            ActivityPK pk = new ActivityPK(acntRid, rid);
            activity.setPk(pk);

            WbsPK parentPk = new WbsPK(acntRid, wbsRid);
            Wbs parent = (Wbs)this.getDbAccessor().load(Wbs.class, parentPk);
            this.getDbAccessor().save(activity);
            if (parent.getActivities() != null) {
                parent.getActivities().add(activity);
            } else {
                List list = new ArrayList();
                list.add(activity);
                parent.setActivities(list);
            }
            this.getDbAccessor().update(parent);
//            modifyActivityDate(activity);
        } catch (Exception ex) {
            if (ex instanceof BusinessException) {
                throw (BusinessException) ex;
            } else {
                throw new BusinessException(ex);
            }
        }

    }

    public void update(DtoWbsActivity dataBean) {
        if (dataBean == null) {
            throw new BusinessException("E_WBS005", "dataBean is null!!!");
        }

        Long acntRid = dataBean.getAcntRid();
        Long wbsRid = dataBean.getWbsRid();
        Long activityRid = dataBean.getActivityRid();
        if (acntRid == null) {
            throw new BusinessException("E_WBS006", "account rid is null!!!");
        }
        if (wbsRid == null) {
            throw new BusinessException("E_WBS007", "wbs rid is null!!!");
        }
        if (activityRid == null) {
            throw new BusinessException("E_WBS008", "activity rid is null!!!");
        }

        /**
         * 工期计算
         */
        double durationPlan = LgWbsComplete.caculateDurationPlan(dataBean);
        double durationActual = LgWbsComplete.caculateDurationActual(dataBean);
        if (durationPlan < 0) {
            durationPlan = 0;
        }
        if (durationActual < 0) {
            durationActual = 0;
        }
        dataBean.setDurationPlan(new Long((long) durationPlan));
        dataBean.setDurationActual(new Long((long) durationActual));
        dataBean.setDurationRemain(new Long((long) (durationPlan -
            durationActual)));
        double completeRate = durationActual * 100 / durationPlan;
        if (durationPlan != 0) {
            dataBean.setDurationComplete(new Double(completeRate));
        } else {
            dataBean.setDurationComplete(new Double(0));
        }

//        WbsPK pk = new WbsPK();
//        pk.setAcntRid(acntRid);
//        pk.setWbsRid(wbsRid);

        ActivityPK actPk = new ActivityPK();
        actPk.setAcntRid(acntRid);
        actPk.setActivityId(activityRid);

        try {
            //Wbs wbs = (Wbs)this.getDbAccessor().load(Wbs.class, pk);
            Activity activity = (Activity)this.getDbAccessor().load(Activity.class,
                actPk);
            DtoUtil.copyProperties(activity, dataBean);
            this.getDbAccessor().update(activity);
//            modifyActivityDate(activity);

            DtoUtil.copyProperties(dataBean, activity);
            dataBean.setAutoCode(dataBean.getCode());
        } catch (Exception ex) {
            if (ex instanceof BusinessException) {
                throw (BusinessException) ex;
            } else {
                throw new BusinessException(ex);
            }
        }
    }

    /**
     * 查找WBS对应的所有Activity
     * @param acntRid Long
     * @param wbsRid Long
     * @return List
     */
    public List listWbsActivity(Long acntRid, Long wbsRid) {
        if (acntRid == null) {
            throw new BusinessException("E_WBS006", "account rid is null!!!");
        }
        if (wbsRid == null) {
            throw new BusinessException("E_WBS007", "wbs rid is null!!!");
        }
        List result = new ArrayList();
        WbsPK wbsPK = new WbsPK(acntRid, wbsRid);
        try {
            Session s = this.getDbAccessor().getSession();
            Wbs wbs = (Wbs) s.load(Wbs.class, wbsPK);
            List l = wbs.getActivities();
            Iterator i = l.iterator();
            while (i.hasNext()) {
                Activity activity = (Activity) i.next();
                DtoWbsActivity dto = new DtoWbsActivity();
                DtoUtil.copyProperties(dto, activity);
                //原来的DTO中没有AcntRid信息，加入
                //Modify:Robin 20060915
                dto.setAcntRid(acntRid);
                dto.setWbsRid(wbsRid);
                dto.setActivityRid(activity.getPk().getActivityId());
                if (this.userId != null && this.getAccount() != null && (
                    this.userId.equals(this.getAccount().getManager()) ||
                    this.userId.equals(activity.getManager()))) {
                    dto.setReadonly(false);
                } else {
                    dto.setReadonly(true);
                }

                result.add(dto);
            }
        } catch (Exception ex) {
            log.error(ex);
            throw new BusinessException(
                "error while listing activities of wbs!");
        }
        return result;
    }

    public ITreeNode getActivity(Long acntrid, Long activityRid) {
        ActivityPK actPk = new ActivityPK();
        actPk.setAcntRid(acntrid);
        actPk.setActivityId(activityRid);
        try {
            Activity activity = (Activity)this.getDbAccessor().load(Activity.class,
                actPk);
            DtoWbsActivity dto = new DtoWbsActivity();
            DtoUtil.copyProperties(dto, activity);
            dto.setAcntRid(acntrid);
            dto.setActivityRid(activityRid);
            Long wbsRid = activity.getWbs().getPk().getWbsRid();
            dto.setWbsRid(wbsRid);
//          modified by  lipengxu for no manager NullPointerException
//            if ((dto.getManager()).equals(userId)) {
            if (userId.equals(dto.getManager())) {
                dto.setReadonly(false);
            } else {
                dto.setReadonly(true);
            }
            ITreeNode node = new DtoTreeNode(dto);
            //获得Activity父节点对应的WBS
            WbsPK wbsPK = new WbsPK(acntrid, wbsRid);
            Wbs wbs = (Wbs)this.getDbAccessor().load(Wbs.class,
                wbsPK);
            DtoWbsActivity wbsDto = new DtoWbsActivity();
            DtoUtil.copyProperties(wbsDto, wbs);
            wbsDto.setAcntRid(acntrid);
            wbsDto.setWbsRid(wbsRid);
            ITreeNode parentNode = new DtoTreeNode(wbsDto);
            node.setParent(parentNode);

            return node;
        } catch (Exception ex) {
            throw new BusinessException("get Activity error!", ex);
        }
    }

    /**
     * 列出指定项目下所有manager为当前用户的Activity
     * @param acntRid Long
     * @return List
     */
    public List listActivityByManager(Long acntRid) {
        return listActivityByManager(acntRid, userId);
    }

    /**
     * 列出指定项目下所有manager为loginId的Activity
     * @param acntRid Long
     * @param loginId String
     * @return List
     */
    public List listActivityByManager(Long acntRid, String loginId) {
        String hql = "from Activity t where t.pk.acntRid=:acntRid and t.manager=:manager";
        List lst = null;
        try {
            lst = this.getDbAccessor().getSession().createQuery(hql)
                  .setLong("acntRid", acntRid)
                  .setString("manager", loginId)
                  .list();
        } catch (Exception ex) {
            throw new BusinessException("list activity by manager error!", ex);
        }
      return lst;
    }

    public static void main(String[] args) {
        LgActivity lgactivity = new LgActivity();

        lgactivity.listWbsActivity(new Long(1), new Long(34));
    }
}
