package server.essp.pms.modifyplan.logic;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import c2s.dto.DtoUtil;
import c2s.essp.common.code.DtoKey;
import c2s.essp.pms.pbs.DtoPmsPbsFiles;
import c2s.essp.pms.pbs.pbsAndFiles.DtoPbsAssign;
import c2s.essp.pms.wbs.DtoWbsActivity;
import db.essp.pms.Activity;
import db.essp.pms.ActivityPK;
import db.essp.pms.ActivityQuality;
import db.essp.pms.Pbs;
import db.essp.pms.PbsAssignment;
import db.essp.pms.PbsAssignmentPK;
import db.essp.pms.PbsFiles;
import db.essp.pms.PbsFilesPK;
import db.essp.pms.PbsPK;
import db.essp.pms.PmsActivityGuideline;
import db.essp.pms.PmsActivityGuidelineId;
import db.essp.pms.PmsQaCheckAction;
import db.essp.pms.PmsQaCheckActionPK;
import db.essp.pms.PmsQaCheckPoint;
import db.essp.pms.PmsQaCheckPointPK;
import db.essp.pms.Wbs;
import db.essp.pms.WbsPK;
import server.essp.framework.logic.AbstractESSPLogic;
import server.essp.pms.activity.logic.ActivityNodeCopy;
import server.essp.pms.pbs.logic.LgPmsPbsFilesList;
import server.essp.pms.pbs.pbsAndFiles.logic.LgPbsList;
import server.essp.pms.wbs.logic.LgAcntSeq;
import server.essp.pms.wbs.process.checklist.logic.LgQaCheckAction;
import server.essp.pms.wbs.process.checklist.logic.LgQaCheckPoint;
import server.framework.common.BusinessException;
import server.framework.hibernate.HBSeq;

public class LgActivityNodeCopy extends AbstractESSPLogic implements
    ActivityNodeCopy {
    public LgActivityNodeCopy() {
    }

    /**
     * 把原srcActivity复制到目标desWbs下
     * 复制的内容包括code,guideline,quality,relation,pms,qa_check_point，qa_check_action
     * @param srcActivity DtoWbsActivity
     * @param desWbs DtoWbsActivity
     * @return DtoWbsActivity
     */
    public DtoWbsActivity copyActivity(DtoWbsActivity srcActivity,
                                       DtoWbsActivity desWbs) {
        try {
            DtoWbsActivity destDtoActivity = new DtoWbsActivity();
            //原srcActivity所属项目的rid
            Long srcAcntRid = srcActivity.getAcntRid();
            //原srcActivity的rid
            Long srcActivityRid = srcActivity.getActivityRid();
            //目标项目的rid
            Long destAcntRid = desWbs.getAcntRid();
            //目标wbs的rid
            Long destWbsRid = desWbs.getWbsRid();

            DtoUtil.copyBeanToBean(destDtoActivity, srcActivity);
            //give a new code
            destDtoActivity.setCode(LgAcntSeq.getActivityCode(destAcntRid));
            destDtoActivity.setCompleteRate(null);
            destDtoActivity.setStart(Boolean.FALSE);
            destDtoActivity.setFinish(Boolean.FALSE);
            destDtoActivity.setAcntRid(destAcntRid);
            destDtoActivity.setWbsRid(destWbsRid);

            //---------复制activity----------
            Activity activity = new Activity();
            DtoUtil.copyBeanToBean(activity, destDtoActivity);
            //modified by lipengxu, inaccurate get rid method
            //Long activityRid = this.getDbAccessor().assignedRid(activity);
            Long activityRid = LgAcntSeq.getActivitySeq(destAcntRid);
            ActivityPK activityPK = new ActivityPK(destAcntRid, activityRid);
            activity.setPk(activityPK);
            destDtoActivity.setActivityRid(activityRid);

            ActivityPK tempActivityPK = new ActivityPK(srcAcntRid,
                srcActivityRid);
            ActivityQuality tmpActivityQality =
                (ActivityQuality)this.getDbAccessor().get(ActivityQuality.class,
                tempActivityPK);

            //复制activityQuality
            ActivityQuality activityQuality = new ActivityQuality();
            DtoUtil.copyBeanToBean(activityQuality, tmpActivityQality);
            activityQuality.setPk(activityPK);
            activity.setActivityQuality(activityQuality);

            WbsPK wbsPK = new WbsPK(destAcntRid, destWbsRid);
            Wbs wbs = (Wbs)this.getDbAccessor().get(Wbs.class, wbsPK);
            this.getDbAccessor().save(activity);
            this.getDbAccessor().flush();
            if (wbs.getActivities() != null) {
                wbs.getActivities().add(activity);
            } else {
                List list = new ArrayList();
                list.add(activity);
                wbs.setActivities(list);
            }
            this.getDbAccessor().update(wbs);

            //复制activity Code
            ResultSet activityCodeRs = this.getDbAccessor().executeQuery(
                "select ACNT_RID,ACTIVITY_RID,TYPE_RID,VALUE_RID,TYPE,VALUE_PATH" +
                " from PMS_ACTIVITY_CODE t where t.ACNT_RID='" +
                srcAcntRid.longValue() + "'" +
                " and t.ACTIVITY_RID='" + srcActivityRid.longValue() + "'");
            if (activityCodeRs != null) {
                while (activityCodeRs.next()) {
                    long type_rid = activityCodeRs.getLong("TYPE_RID");
                    long value_rid = activityCodeRs.getLong("VALUE_RID");
                    String type = activityCodeRs.getString("TYPE");
                    String value_path = activityCodeRs.getString("VALUE_PATH");
                    this.getDbAccessor().executeUpdate(
                        "insert into PMS_ACTIVITY_CODE" +
                        " (ACNT_RID,ACTIVITY_RID,TYPE_RID,VALUE_RID,TYPE,VALUE_PATH)" +
                        "  values(" + destAcntRid.longValue() + "," +
                        activityRid.longValue() + "," +
                        type_rid + "," + value_rid + ",'" + type + "','" +
                        value_path + "')");
                }
            }

            //-------------复制guideline---------
            /**
             * 1.不同项目的节点复制，新增Guideline, decription 来自源节点（如果存在）
             * 引用acntRid和ActivityRid=源节点的acntRid和ActivityRid
             * 2.同项目节点复制，原抄。
             */
            PmsActivityGuidelineId srcGuideLineId = new PmsActivityGuidelineId();
            srcGuideLineId.setAcntRid(srcAcntRid);
            srcGuideLineId.setActRid(srcActivityRid);
            PmsActivityGuideline srcPmsActivityGuideLine =
                (PmsActivityGuideline)this.getDbAccessor().get(
                    PmsActivityGuideline.class, srcGuideLineId);

            PmsActivityGuidelineId guideLineId = new PmsActivityGuidelineId();
            guideLineId.setAcntRid(destAcntRid);
            guideLineId.setActRid(activityRid);
            PmsActivityGuideline pmsActivityGuideLine = new
                PmsActivityGuideline();
            pmsActivityGuideLine.setId(guideLineId);

            if (!srcAcntRid.equals(destAcntRid)) {
                String description = srcPmsActivityGuideLine == null ? null :
                                     srcPmsActivityGuideLine.getDescription();
                pmsActivityGuideLine.setRefAcntRid(srcAcntRid);
                pmsActivityGuideLine.setRefActRid(srcActivityRid);
                pmsActivityGuideLine.setDescription(description);
                this.getDbAccessor().save(pmsActivityGuideLine);
            } else if (srcPmsActivityGuideLine != null) {

                pmsActivityGuideLine.setRefAcntRid(srcPmsActivityGuideLine.
                    getRefAcntRid());
                pmsActivityGuideLine.setRefActRid(srcPmsActivityGuideLine.
                                                  getRefActRid());
                pmsActivityGuideLine.setDescription(srcPmsActivityGuideLine.
                    getDescription());
                this.getDbAccessor().save(pmsActivityGuideLine);

            }

            //-------------复制pbs---------------
            /**
             * 分两种情况：（第二中情况暂不实现）
             *  1.在同一棵wbs/activity树上进行复制（srcActivity和destWbs属于同一个项目）
             *  2.把一棵树的wbs/activity点复制到另一棵数（srcActivity和destWbs属于不同项目）
             */

            //如果属于第一中情况，则进行pbs复制
            if (srcAcntRid.longValue() == destAcntRid.longValue()) {
                LgPbsList lgPbsList = new LgPbsList();
                List pbsList = lgPbsList.list(srcAcntRid,
                                              DtoPbsAssign.JOINTYPEACT,
                                              srcActivityRid);
                if (pbsList != null) {
                    for (int pi = 0; pi < pbsList.size(); pi++) {
                        DtoPbsAssign dtoPbsAssign = (DtoPbsAssign) pbsList.get(
                            pi);
                        PbsAssignmentPK pbsAssignmentpk = new PbsAssignmentPK(
                            destAcntRid,
                            dtoPbsAssign.getPbsRid(),
                            dtoPbsAssign.getJoinType(),
                            activityRid);
                        PbsPK pbsPK = new PbsPK(srcAcntRid,
                                                dtoPbsAssign.getPbsRid());
                        Pbs pbs = (Pbs) getDbAccessor().getSession().get(Pbs.class,
                            pbsPK);

                        //复制pbsAssignment
                        PbsAssignment pbsAssignment = new PbsAssignment();
                        pbsAssignment.setPk(pbsAssignmentpk);
                        pbsAssignment.setPbs(pbs);
                        this.getDbAccessor().save(pbsAssignment);

                        LgPmsPbsFilesList lgPmsPbsFilesList = new
                            LgPmsPbsFilesList();
                        List pbsFilesList = lgPmsPbsFilesList.list(srcAcntRid,
                            dtoPbsAssign.getPbsRid());
                        if (pbsFilesList != null) {
                            for (int fi = 0; fi < pbsFilesList.size(); fi++) {
                                DtoPmsPbsFiles pbsFilesDto = (DtoPmsPbsFiles)
                                    pbsFilesList.get(fi);
                                //复制PbsFiles
                                PbsFiles pbsFiles = new PbsFiles();
                                //得到新的filesRid
                                Long filesRid = new Long(HBSeq.getSEQ(
                                    "pms_pbs_files"));
                                PbsFilesPK pbsFilesPK = new PbsFilesPK(
                                    destAcntRid,
                                    pbsFilesDto.getPbsRid(),
                                    filesRid);
                                DtoUtil.copyBeanToBean(pbsFiles, pbsFilesDto);
                                pbsFiles.setPk(pbsFilesPK);
                                pbsFiles.setPbs(pbs);
                                this.getDbAccessor().save(pbsFiles);

                            } //end for (pbsFilesList 循环)
                        } //end if (pbsFilesList != null)
                    } //end for (pbsList循环)
                } //end if (pbsList != null)
            } //end if (属于第一中情况，则进行pbs复制)

            //-------------复制qa_check_point和qa_check_action----
            LgQaCheckPoint lgQaCheckPointList = new LgQaCheckPoint();
            List qaCheckPointList =
                lgQaCheckPointList.listCheckPointBean(srcAcntRid,
                srcActivityRid,
                DtoKey.TYPE_ACTIVITY);
            if (qaCheckPointList != null) {
                for (int pi = 0; pi < qaCheckPointList.size(); pi++) {
                    PmsQaCheckPoint srcPmsQaCheckPoint =
                        (PmsQaCheckPoint) qaCheckPointList.get(pi);
                    //复制QaCheckPoint
                    PmsQaCheckPoint pmsQaCheckPoint = new PmsQaCheckPoint();
                    DtoUtil.copyBeanToBean(pmsQaCheckPoint, srcPmsQaCheckPoint);
                    PmsQaCheckPointPK pmsQaCheckPointPK = new PmsQaCheckPointPK();
                    Long checkPointRid = LgAcntSeq.getSeq(destAcntRid,
                        PmsQaCheckPoint.class);
                    pmsQaCheckPointPK.setAcntRid(destAcntRid);
                    pmsQaCheckPointPK.setRid(checkPointRid);
                    pmsQaCheckPoint.setPK(pmsQaCheckPointPK);
                    pmsQaCheckPoint.setBelongRid(activityRid);
//                    this.getDbAccessor().assignedRid(pmsQaCheckPoint);
                    this.getDbAccessor().save(pmsQaCheckPoint);

                    Long srcQaCheckPointRid = srcPmsQaCheckPoint.getPK().getRid();
                    Long destQaCheckPointRid = pmsQaCheckPoint.getPK().getRid();
                    LgQaCheckAction lgQaCheckAction = new LgQaCheckAction();
                    List qaCheckActionList =
                        lgQaCheckAction.listCheckActionOfBean(
                            srcQaCheckPointRid, srcAcntRid);
                    if (qaCheckActionList != null) {
                        for (int ai = 0; ai < qaCheckActionList.size(); ai++) {
                            PmsQaCheckAction srcQaCheckAction =
                                (PmsQaCheckAction) qaCheckActionList.get(ai);
                            //复制QaCheckAction
                            PmsQaCheckAction qaCheckAction = new
                                PmsQaCheckAction();
                            DtoUtil.copyBeanToBean(qaCheckAction,
                                srcQaCheckAction);
                            PmsQaCheckActionPK pmsQaCheckActionPK = new
                                PmsQaCheckActionPK();
                            Long checkPointActionRid = LgAcntSeq.getSeq(
                                destAcntRid, PmsQaCheckAction.class);
                            pmsQaCheckActionPK.setAcntRid(destAcntRid);
                            pmsQaCheckActionPK.setRid(checkPointActionRid);
                            qaCheckAction.setPK(pmsQaCheckActionPK);
                            qaCheckAction.setCpRid(destQaCheckPointRid);
//                            this.getDbAccessor().assignedRid(qaCheckAction);
                            this.getDbAccessor().save(qaCheckAction);
                        } //end for ((对qaCheckActionList遍历)
                    } //end if (存在qaCheckActionList)
                } //end for (对qaCheckPointList遍历)
            } //end if (存在qaCheckPointList)

            return destDtoActivity;
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error(ex);
            throw new BusinessException("activity.copy.error", ex);
        }
    }
//    public void saveCode(Long srcAcntRid,Long srcActivityRid){
//        try{
//            ResultSet activityCodeRs = this.getDbAccessor().executeQuery(
//                "select ACNT_RID,ACTIVITY_RID,TYPE_RID,VALUE_RID,TYPE,VALUE_PATH" +
//                " from PMS_ACTIVITY_CODE t where t.ACNT_RID='" +
//                srcAcntRid.longValue() + "'" +
//                " and t.ACTIVITY_RID='" + srcActivityRid.longValue() + "'");
//            if (activityCodeRs != null) {
//                while (activityCodeRs.next()) {
//                    long type_rid = activityCodeRs.getLong("TYPE_RID");
//                    long value_rid = activityCodeRs.getLong("VALUE_RID");
//                    String value_path = activityCodeRs.getString("VALUE_PATH");
//                    String type = activityCodeRs.getString("TYPE");
//                    System.out.println("type_rid:"+type_rid);
//                    System.out.println("value_rid:"+value_rid);
//                    System.out.println("value_path:"+value_path);
//                    String sql = "insert into PMS_ACTIVITY_CODE"+
//                     " (ACNT_RID,ACTIVITY_RID,TYPE_RID,VALUE_RID,TYPE,VALUE_PATH)"+
//                     " values("+2+","+3+","+
//                     type_rid+","+value_rid+",'"+type+"','"+value_path+"')";
//
//                    System.out.println("sql:"+sql);
//                    this.getDbAccessor().executeUpdate(sql);
//                }
//            }
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//    }
//    public static void main(String[] args){
//        LgActivityNodeCopy lg = new LgActivityNodeCopy();
//        lg.saveCode(new Long(1),new Long(2));
//    }
}
