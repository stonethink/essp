package server.essp.pms.modifyplan.logic;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import c2s.dto.DtoUtil;
import c2s.essp.common.code.DtoKey;
import c2s.essp.pms.pbs.DtoPmsPbsFiles;
import c2s.essp.pms.pbs.pbsAndFiles.DtoPbsAssign;
import c2s.essp.pms.wbs.DtoWbsActivity;
import db.essp.pms.CheckPoint;
import db.essp.pms.CheckPointPK;
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
import server.essp.pms.pbs.logic.LgPmsPbsFilesList;
import server.essp.pms.pbs.pbsAndFiles.logic.LgPbsList;
import server.essp.pms.wbs.logic.LgAcntSeq;
import server.essp.pms.wbs.logic.LgCheckPoint;
import server.essp.pms.wbs.logic.WbsNodeCopy;
import server.essp.pms.wbs.process.checklist.logic.LgQaCheckAction;
import server.essp.pms.wbs.process.checklist.logic.LgQaCheckPoint;
import server.framework.common.BusinessException;
import server.framework.hibernate.HBSeq;
import db.essp.pms.PmsWbsGuidelineId;
import db.essp.pms.PmsWbsGuideline;

public class LgWbsNodeCopy extends AbstractESSPLogic implements WbsNodeCopy{
    public LgWbsNodeCopy() {
    }
    /**
     * 把srcWbs复制到desWbs下
     * 复制的内容包括checkpoint，code，guideline，qa_check_point，qa_check_action
     * @param srcWbs DtoWbsActivity
     * @param desWbs DtoWbsActivity
     * @return DtoWbsActivity
     */
    public DtoWbsActivity copyWBS(DtoWbsActivity srcWbs, DtoWbsActivity desWbs) {
        try {
            DtoWbsActivity desDate = new DtoWbsActivity();
            DtoUtil.copyBeanToBean(desDate, srcWbs, new String[] {"wbsRid"});
            //原项目的acntRid
            Long srcAcntRid = srcWbs.getAcntRid();
            //原项目的wbsRid
            Long srcWbsRid = srcWbs.getWbsRid();
            //目标项目的acntRid
            Long destAcntRid = desWbs.getAcntRid();
            //目标项目的wbsRid
            Long destWbsRid = desWbs.getWbsRid();
            LgAcntSeq lgAcntSeq = new LgAcntSeq();
            //生成新的wbsRid
            Long wbsRid = lgAcntSeq.getWbsSeq(destAcntRid);
            //new code
            java.text.DecimalFormat df = new java.text.DecimalFormat("W0000");
            desDate.setCode(df.format(wbsRid));
            desDate.setCompleteRate(null);
            desDate.setStart(Boolean.FALSE);
            desDate.setFinish(Boolean.FALSE);

            desDate.setAcntRid(destAcntRid);
            desDate.setParentRid(destWbsRid);
            desDate.setWbsRid(wbsRid);

            Wbs wbs = new Wbs();
             DtoUtil.copyProperties(wbs, desDate);
             WbsPK wbsPK = new WbsPK(destAcntRid,wbsRid);
             wbs.setPk(wbsPK);
            //-----------保存wbs code-------
//            LgWbsCode lgWbsCode = new LgWbsCode(srcAcntRid,srcWbsRid);
//            Set set = lgWbsCode.getCodeSet();
//            wbs.setWbsCodes(set);
            //-----------保存wbs------------
            this.getDbAccessor().save(wbs);
            //-----------保存wbs code-------
            ResultSet wbsCodeRs = this.getDbAccessor().executeQuery(
                "select ACNT_RID,WBS_RID,TYPE_RID,VALUE_RID,TYPE,VALUE_PATH"+
                " from PMS_WBS_CODE t where t.ACNT_RID='"+srcAcntRid.longValue()+"'"+
                " and t.WBS_RID='"+srcWbsRid.longValue()+"'");
            if(wbsCodeRs != null){
                while (wbsCodeRs.next()) {
                  long type_rid = wbsCodeRs.getLong("TYPE_RID");
                  long value_rid = wbsCodeRs.getLong("VALUE_RID");
                  String type = wbsCodeRs.getString("TYPE");
                  String value_path = wbsCodeRs.getString("VALUE_PATH");
                  this.getDbAccessor().executeUpdate("insert into PMS_WBS_CODE"+
                      " (ACNT_RID,WBS_RID,TYPE_RID,VALUE_RID,TYPE,VALUE_PATH)"+
                      "  values("+destAcntRid.longValue()+","+wbsRid.longValue()+","+
                      type_rid+","+value_rid+",'"+type+"','"+value_path+"')");
                }
            }
            WbsPK parentWbsPK = new WbsPK(destAcntRid,destWbsRid);
            Wbs parentWbs = (Wbs)this.getDbAccessor().get(Wbs.class, parentWbsPK);
            if(parentWbs != null){
                if (parentWbs.getChilds() == null) {
                    List list = new ArrayList();
                    list.add(wbs);
                    parentWbs.setChilds(list);
                } else {
                    parentWbs.getChilds().add(wbs);
                }
                this.getDbAccessor().update(parentWbs);
            }

            //------------保存wbs checkpoint------
            LgCheckPoint lgCheckPoint = new LgCheckPoint();
            List checkPointList = lgCheckPoint.listCheckPoint(srcAcntRid, srcWbsRid);
            if(checkPointList != null){
                for(int ci = 0; ci < checkPointList.size(); ci++){
                    CheckPoint srcCheckPoint =(CheckPoint) checkPointList.get(ci);
                    CheckPointPK pk = new CheckPointPK();
                    pk.setAcntRid(destAcntRid);
                    pk.setWbsRid(wbsRid);
                    Long chkRid = null;
                    pk.setRid(chkRid);
                    CheckPoint desCheckPoint = new CheckPoint();
                    DtoUtil.copyBeanToBean(desCheckPoint,srcCheckPoint);
                    desCheckPoint.setPk(pk);
                    lgCheckPoint.add(desCheckPoint);
                }
            }

            //-------------复制guideline---------
            /**
             * 1.不同项目的节点复制，新增Guideline, decription 来自源节点（如果存在）
             * 引用acntRid和WbsRid=源节点的acntRid和WbsRid
             * 2.同项目节点复制，原抄。
             */
            PmsWbsGuidelineId srcGuideLineId = new PmsWbsGuidelineId();
            srcGuideLineId.setAcntRid(srcAcntRid);
            srcGuideLineId.setWbsRid(srcWbsRid);
            PmsWbsGuideline srcPmsWbsGuideLine =
               (PmsWbsGuideline)this.getDbAccessor().get(PmsWbsGuideline.class,srcGuideLineId);
           PmsWbsGuidelineId guideLineId = new PmsWbsGuidelineId();
               guideLineId.setAcntRid(destAcntRid);
               guideLineId.setWbsRid(wbsRid);
               PmsWbsGuideline pmsWbsGuideLine = new
                   PmsWbsGuideline();
               pmsWbsGuideLine.setId(guideLineId);

           if (!srcAcntRid.equals(destAcntRid)) {
               String description = srcPmsWbsGuideLine == null ? null :
                                     srcPmsWbsGuideLine.getDescription();
                pmsWbsGuideLine.setRefAcntRid(srcAcntRid);
                pmsWbsGuideLine.setRefWbsRid(srcWbsRid);
                pmsWbsGuideLine.setDescription(description);
                this.getDbAccessor().save(pmsWbsGuideLine);

           }else if( srcPmsWbsGuideLine != null){

               pmsWbsGuideLine.setRefAcntRid(srcPmsWbsGuideLine.getRefAcntRid());
               pmsWbsGuideLine.setRefWbsRid(srcPmsWbsGuideLine.getRefWbsRid());
               pmsWbsGuideLine.setDescription(srcPmsWbsGuideLine.
                   getDescription());
               this.getDbAccessor().save(pmsWbsGuideLine);
           }
            //----------保存wbs qa_check_point和qa_check_action--------
            LgQaCheckPoint lgQaCheckPointList = new LgQaCheckPoint();
            List qaCheckPointList =
                lgQaCheckPointList.listCheckPointBean(srcAcntRid,srcWbsRid,DtoKey.TYPE_WBS);
            if(qaCheckPointList != null){
                for(int pi = 0; pi < qaCheckPointList.size(); pi++){
                    PmsQaCheckPoint srcPmsQaCheckPoint =
                        (PmsQaCheckPoint) qaCheckPointList.get(pi);
                    //复制QaCheckPoint
                    PmsQaCheckPoint pmsQaCheckPoint = new PmsQaCheckPoint();
                    DtoUtil.copyBeanToBean(pmsQaCheckPoint,srcPmsQaCheckPoint);
                    PmsQaCheckPointPK pmsQaCheckPointPK = new PmsQaCheckPointPK();
                    Long checkPointRid = LgAcntSeq.getSeq(destAcntRid, PmsQaCheckPoint.class);
                    pmsQaCheckPointPK.setAcntRid(destAcntRid);
                    pmsQaCheckPointPK.setRid(checkPointRid);
                    pmsQaCheckPoint.setPK(pmsQaCheckPointPK);

                    pmsQaCheckPoint.setBelongRid(wbsRid);
                    this.getDbAccessor().assignedRid(pmsQaCheckPoint);
                    this.getDbAccessor().save(pmsQaCheckPoint);

                    Long srcQaCheckPointRid = srcPmsQaCheckPoint.getPK().getRid();
                    Long destQaCheckPointRid = pmsQaCheckPoint.getPK().getRid();
                    LgQaCheckAction lgQaCheckAction = new LgQaCheckAction();
                    List qaCheckActionList =
                        lgQaCheckAction.listCheckActionOfBean(srcQaCheckPointRid,srcAcntRid);
                    if(qaCheckActionList != null){
                        for(int ai = 0; ai < qaCheckActionList.size(); ai++){
                            PmsQaCheckAction srcQaCheckAction =
                                (PmsQaCheckAction) qaCheckActionList.get(ai);
                            //复制QaCheckAction
                            PmsQaCheckAction qaCheckAction = new PmsQaCheckAction();
                            DtoUtil.copyBeanToBean(qaCheckAction,srcQaCheckAction);
                            PmsQaCheckActionPK pmsQaCheckActionPK = new PmsQaCheckActionPK();
                            Long checkPointActionRid = LgAcntSeq.getSeq(destAcntRid, PmsQaCheckAction.class);
                            pmsQaCheckActionPK.setAcntRid(destAcntRid);
                            pmsQaCheckActionPK.setRid(checkPointActionRid);
                            qaCheckAction.setPK(pmsQaCheckActionPK);

                            qaCheckAction.setCpRid(destQaCheckPointRid);
                            this.getDbAccessor().assignedRid(qaCheckAction);
                            this.getDbAccessor().save(qaCheckAction);
                        }
                    }
                }
            }
            //---------复制pbs--------
            /**
             * 分两种情况：（第二中情况暂不实现）
             *  1.在同一棵wbs/activity树上进行复制（srcWbs和destWbs属于同一个项目）
             *  2.把一棵树的wbs/activity点复制到另一棵数（srcWbs和destWbs属于不同项目）
             */

            //如果属于第一中情况，则进行pbs复制
            if(srcAcntRid.longValue()==destAcntRid.longValue()){
                LgPbsList lgPbsList = new LgPbsList();
                List pbsList = lgPbsList.list(srcAcntRid,
                                              DtoPbsAssign.JOINTYPEWBS,
                                              srcWbsRid);
                if (pbsList != null) {
                    for (int pi = 0; pi < pbsList.size(); pi++) {
                        DtoPbsAssign dtoPbsAssign = (DtoPbsAssign) pbsList.get(
                            pi);
                        PbsAssignmentPK pbsAssignmentpk = new PbsAssignmentPK(
                            destAcntRid,
                            dtoPbsAssign.getPbsRid(),
                            dtoPbsAssign.getJoinType(),
                            wbsRid);
                        PbsPK pbsPK = new PbsPK(srcAcntRid,dtoPbsAssign.getPbsRid());
                        Pbs pbs = (Pbs) getDbAccessor().getSession().get(Pbs.class,pbsPK);

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
                                Long filesRid =  new Long( HBSeq.getSEQ("pms_pbs_files") );
                                PbsFilesPK pbsFilesPK = new PbsFilesPK(
                                    destAcntRid,
                                    pbsFilesDto.getPbsRid(),
                                    filesRid);
                                DtoUtil.copyBeanToBean(pbsFiles, pbsFilesDto);
                                pbsFiles.setPk(pbsFilesPK);
                                pbsFiles.setPbs(pbs);
                                this.getDbAccessor().save(pbsFiles);

                            }//end for (pbsFilesList 循环)
                        }//end if (pbsFilesList != null)
                    }//end for (pbsList循环)
                }//end if (pbsList != null)
            }//end if (属于第一中情况，则进行pbs复制)
            return desDate;
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error(ex);
            throw new BusinessException("wbs.copy.error", ex);
        }
    }
}
