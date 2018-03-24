package server.essp.pms.qa.monitoring.logic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import c2s.dto.DtoUtil;
import c2s.dto.IDto;
import c2s.dto.ITreeNode;
import c2s.essp.common.code.DtoKey;
import c2s.essp.pms.qa.DtoMonitoringTreeNode;
import c2s.essp.pms.qa.DtoQaCheckAction;
import c2s.essp.pms.qa.DtoQaCheckPoint;
import c2s.essp.pms.wbs.DtoQaMonitoring;
import c2s.essp.pms.wbs.DtoWbsActivity;
import client.essp.pms.qa.monitoring.VwMonitoringList;
import db.essp.pms.Wbs;
import db.essp.pms.WbsPK;
import server.essp.framework.logic.AbstractESSPLogic;
import server.essp.pms.wbs.logic.LgAcntSeq;
import server.essp.pms.wbs.logic.LgWbs;
import server.essp.pms.wbs.process.checklist.logic.LgQaCheckAction;
import server.essp.pms.wbs.process.checklist.logic.LgQaCheckPoint;
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
public class LgMonitoring extends AbstractESSPLogic {
    public final static String[] chkActionOccasion = DtoQaCheckAction.chkActionOccasion;
    private String filterType;
    private String filterStatus;
    private String planPerformer;
    private Date planStartDate;
    private Date planFinishDate;
    private String actualPerformer;
    private Date actualStartDate;
    private Date actualFinishDate;
    public LgMonitoring() {
    }

    public LgMonitoring(String filterType, String filterStatus,
                        String planPerformer, Date planStartDate,
                        Date planFinishDate, String actualPerformer,
                        Date actualStartDate, Date actualFinishDate) {
        this.filterType = filterType;
        this.filterStatus = filterStatus;
        this.planPerformer = planPerformer;
        this.planStartDate = planStartDate;
        this.planFinishDate = planFinishDate;
        this.actualPerformer = actualPerformer;
        this.actualStartDate = actualStartDate;
        this.actualFinishDate = actualFinishDate;
    }

    public ITreeNode getCheckTree(Long accontRid, boolean showPoint) {
        LgWbs lgWbs = new LgWbs();
        ITreeNode root = lgWbs.getWbsTree(accontRid);
        ITreeNode newNode = getChildren(root, showPoint);
        List cList = newNode.listAllChildrenByPostOrder();
        for (int i = 0; i < cList.size(); i++) {
            ITreeNode cNode = (ITreeNode) cList.get(i);
            DtoQaMonitoring cDto = (DtoQaMonitoring) cNode.getDataBean();
            cDto.setOp(IDto.OP_NOCHANGE);
        }
        return newNode;
    }

    public ITreeNode getChildren(ITreeNode node, boolean showPoint) {
        DtoWbsActivity dto = (DtoWbsActivity) node.getDataBean();
        ITreeNode newNode = new DtoMonitoringTreeNode(null);
        List childs = node.children();
        for (int i = childs.size(); i > 0; i--) {
            ITreeNode childNode = (ITreeNode) childs.get(i - 1);
            ITreeNode newChildNode = getChildren(childNode, showPoint);
            newNode.addChild(0, newChildNode);
        }
        DtoQaMonitoring newDto = new DtoQaMonitoring();
        newDto.setName(dto.getName());
        newDto.setAcntRid(dto.getAcntRid());
        newDto.setCode(dto.getCode());
        newDto.setDtoWbsActivity(dto);
        if (dto.isActivity()) {
            newDto.setRid(dto.getActivityRid());
            newDto.setType(DtoKey.TYPE_ACTIVITY);
            newDto.setBelongRid(dto.getActivityRid());
        } else {
            newDto.setRid(dto.getWbsRid());
            newDto.setType(DtoKey.TYPE_WBS);
            newDto.setBelongRid(dto.getWbsRid());
        }
        List pointList = new ArrayList();
        LgQaCheckPoint lg = new LgQaCheckPoint();

        pointList = lg.listCheckPoint(newDto.getAcntRid(),
                                          newDto.getBelongRid(),
                                          newDto.getType());
        newDto.setPointList(pointList);
        node.setDataBean(newDto);
        newNode.setDataBean(newDto);
        node = newNode;

        for (int j = pointList.size() -1; j >= 0; j--) {
            DtoQaCheckPoint point = (DtoQaCheckPoint) pointList.get(j);
            if(showPoint) {//ÏÔÊ¾check point
                DtoQaMonitoring MonPoint = new DtoQaMonitoring();
                DtoUtil.copyBeanToBean(MonPoint, point);
                MonPoint.setType(DtoQaCheckPoint.DTO_PMS_CHECK_POINT_TYPE);
                ITreeNode pointNode = new DtoMonitoringTreeNode(MonPoint);
                node.addChild(pointNode);
            }
            LgQaCheckAction lgAct = new LgQaCheckAction();
            List actionList = new ArrayList();
            if (filterType != null &&
                VwMonitoringList.KEY_PLAN_PERFORMER.equals(filterType)) {
                actionList = lgAct.listCheckActionByPlanPerformer(point.getRid(),
                    point.getAcntRid(), planPerformer);
            } else if (filterType != null &&
                       VwMonitoringList.KEY_PLAN_DATE.equals(filterType)) {
                actionList = lgAct.listCheckActionByPlanDate(point.getRid(),
                    point.getAcntRid(), planStartDate, planFinishDate);
            } else if (filterType != null &&
                       VwMonitoringList.KEY_ACTUAL_PERFORMER.equals(filterType)) {
                actionList = lgAct.listCheckActionByActPerformer(point.getRid(),
                    point.getAcntRid(), actualPerformer);
            } else if (filterType != null &&
                       VwMonitoringList.KEY_ACTUAL_DATE.equals(filterType)) {
                actionList = lgAct.listCheckActionByActDate(point.getRid(),
                    point.getAcntRid(), actualStartDate, actualFinishDate);
            }  else if (filterType != null &&
                        VwMonitoringList.KEY_FILTER_STATUS.equals(filterType)) {
                actionList = lgAct.listCheckActionByStatus(point.getRid(),
                    point.getAcntRid(),filterStatus);
            }else {
                actionList = lgAct.listCheckAction(point.getRid(),
                    point.getAcntRid());
            }

            for (int k = actionList.size() -1; k >=0; k--) {
                DtoQaCheckAction action = (DtoQaCheckAction) actionList.
                                          get(k);
                DtoQaMonitoring MonAction = new DtoQaMonitoring();
                DtoUtil.copyBeanToBean(MonAction, action);

                //reference parent date
                Date planDate = getQaCheckAtionPlanDate(action.getOccasion(),dto);
                if(planDate != null) {
                    MonAction.setPlanDate(planDate);
                }

                if(showPoint == false) {
                    MonAction.setName(point.getName());
                    MonAction.setMethod(point.getMethod());
                    MonAction.setRemark(point.getRemark());
                }
                MonAction.setType(DtoQaCheckAction.DTO_PMS_CHECK_ACTION_TYPE);
                ITreeNode actionNode = new DtoMonitoringTreeNode(MonAction);
                node.addChild(0, actionNode);
            }
        }
        return newNode;
    }

    public void add(DtoQaMonitoring dataBean) {
        if (dataBean == null) {
            throw new BusinessException("E_WBS005", "dataBean is null!!!");
        }

        Long acntRid = dataBean.getAcntRid();
        Long parentRid = dataBean.getBelongRid();
        Wbs wbs = new Wbs();
        try {
            DtoUtil.copyProperties(wbs, dataBean);
            Long rid = dataBean.getRid();
            if (rid == null || dataBean.isInsert()) {
                rid = LgAcntSeq.getWbsSeq(acntRid);
                dataBean.setRid(rid);
            }
            WbsPK pk = new WbsPK(acntRid, parentRid);
            wbs.setPk(pk);
            if (parentRid != null) {
                WbsPK parentPk = new WbsPK(acntRid, parentRid);
                Wbs parent = (Wbs)this.getDbAccessor().load(Wbs.class, parentPk);
                this.getDbAccessor().save(wbs);
                parent.getChilds().add(wbs);
                this.getDbAccessor().update(parent);

//                modifyWbsDate(wbs);
            } else {
                /*
                 * set this Wbs as root
                 */
                setWbsRootRid(acntRid, rid);
                this.getDbAccessor().save(wbs);
            }

        } catch (Exception ex) {
            if (ex instanceof BusinessException) {
                throw (BusinessException) ex;
            } else {
                throw new BusinessException(ex);
            }
        }

    }

    public void saveDate(List list) {
        for (int i = 0; i < list.size(); i++) {
            DtoQaMonitoring qaMonitoring = (DtoQaMonitoring) ((ITreeNode) list.
                get(i)).getDataBean();
            if(qaMonitoring.isChanged() == false) continue;
            if (qaMonitoring.isCheckAction()) {
                DtoQaCheckAction dtoCa = new DtoQaCheckAction();
                LgQaCheckAction logicAct = new LgQaCheckAction();
                DtoUtil.copyBeanToBean(dtoCa, qaMonitoring);
                if (qaMonitoring.isInsert()) {
                    logicAct.insertCheckAction(dtoCa);
                } else if(qaMonitoring.isModify()) {
                    logicAct.updateCheckAction(dtoCa);
                }
            } else if(qaMonitoring.isCheckPiont()) {
                DtoQaCheckPoint dtoCp = new DtoQaCheckPoint();
                LgQaCheckPoint logic = new LgQaCheckPoint();
                DtoUtil.copyBeanToBean(dtoCp, qaMonitoring);
                if (qaMonitoring.isInsert()) {
                    logic.insertCheckPoint(dtoCp);
                } else if (qaMonitoring.isModify()) {
                    logic.updateCheckPoint(dtoCp);
                }
            }
        }
    }


    public void setWbsRootRid(Long accountRid, Long rootRid) {
        LgAcntSeq.setWbsRootRid(accountRid, rootRid);
    }

//    private class QaActionComparator implements Comparator {
//        public int compare(Object o1, Object o2) {
//            if(o1 instanceof DtoQaCheckAction
//                && o2 instanceof DtoQaCheckAction ){
//                DtoQaCheckAction d1 = (DtoQaCheckAction) o1;
//                DtoQaCheckAction d2 = (DtoQaCheckAction) o2;
//                return d1.getPlanDate().compareTo(d2.getPlanDate());
//             }
//             return 0;
//        }
//        public boolean equals(Object obj) {
//            return false;
//        }
//    }

    public static Date getQaCheckAtionPlanDate(String occasion,
                                               DtoWbsActivity pDto) {
        if(pDto == null) {
            return null;
        }

        if (chkActionOccasion[0].equals(occasion)) {
            return pDto.getPlannedStart();
        } else if (chkActionOccasion[1].equals(occasion)) {
            return pDto.getPlannedFinish();
        } else if (chkActionOccasion[2].equals(occasion)) {
            return pDto.getActualStart();
        } else if (chkActionOccasion[3].equals(occasion)) {
            return pDto.getActualFinish();
        } else {
            return null;
        }
    }


}
