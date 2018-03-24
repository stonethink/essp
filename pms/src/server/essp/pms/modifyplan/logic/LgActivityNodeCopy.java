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
     * ��ԭsrcActivity���Ƶ�Ŀ��desWbs��
     * ���Ƶ����ݰ���code,guideline,quality,relation,pms,qa_check_point��qa_check_action
     * @param srcActivity DtoWbsActivity
     * @param desWbs DtoWbsActivity
     * @return DtoWbsActivity
     */
    public DtoWbsActivity copyActivity(DtoWbsActivity srcActivity,
                                       DtoWbsActivity desWbs) {
        try {
            DtoWbsActivity destDtoActivity = new DtoWbsActivity();
            //ԭsrcActivity������Ŀ��rid
            Long srcAcntRid = srcActivity.getAcntRid();
            //ԭsrcActivity��rid
            Long srcActivityRid = srcActivity.getActivityRid();
            //Ŀ����Ŀ��rid
            Long destAcntRid = desWbs.getAcntRid();
            //Ŀ��wbs��rid
            Long destWbsRid = desWbs.getWbsRid();

            DtoUtil.copyBeanToBean(destDtoActivity, srcActivity);
            //give a new code
            destDtoActivity.setCode(LgAcntSeq.getActivityCode(destAcntRid));
            destDtoActivity.setCompleteRate(null);
            destDtoActivity.setStart(Boolean.FALSE);
            destDtoActivity.setFinish(Boolean.FALSE);
            destDtoActivity.setAcntRid(destAcntRid);
            destDtoActivity.setWbsRid(destWbsRid);

            //---------����activity----------
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

            //����activityQuality
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

            //����activity Code
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

            //-------------����guideline---------
            /**
             * 1.��ͬ��Ŀ�Ľڵ㸴�ƣ�����Guideline, decription ����Դ�ڵ㣨������ڣ�
             * ����acntRid��ActivityRid=Դ�ڵ��acntRid��ActivityRid
             * 2.ͬ��Ŀ�ڵ㸴�ƣ�ԭ����
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

            //-------------����pbs---------------
            /**
             * ��������������ڶ�������ݲ�ʵ�֣�
             *  1.��ͬһ��wbs/activity���Ͻ��и��ƣ�srcActivity��destWbs����ͬһ����Ŀ��
             *  2.��һ������wbs/activity�㸴�Ƶ���һ������srcActivity��destWbs���ڲ�ͬ��Ŀ��
             */

            //������ڵ�һ������������pbs����
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

                        //����pbsAssignment
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
                                //����PbsFiles
                                PbsFiles pbsFiles = new PbsFiles();
                                //�õ��µ�filesRid
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

                            } //end for (pbsFilesList ѭ��)
                        } //end if (pbsFilesList != null)
                    } //end for (pbsListѭ��)
                } //end if (pbsList != null)
            } //end if (���ڵ�һ������������pbs����)

            //-------------����qa_check_point��qa_check_action----
            LgQaCheckPoint lgQaCheckPointList = new LgQaCheckPoint();
            List qaCheckPointList =
                lgQaCheckPointList.listCheckPointBean(srcAcntRid,
                srcActivityRid,
                DtoKey.TYPE_ACTIVITY);
            if (qaCheckPointList != null) {
                for (int pi = 0; pi < qaCheckPointList.size(); pi++) {
                    PmsQaCheckPoint srcPmsQaCheckPoint =
                        (PmsQaCheckPoint) qaCheckPointList.get(pi);
                    //����QaCheckPoint
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
                            //����QaCheckAction
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
                        } //end for ((��qaCheckActionList����)
                    } //end if (����qaCheckActionList)
                } //end for (��qaCheckPointList����)
            } //end if (����qaCheckPointList)

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
