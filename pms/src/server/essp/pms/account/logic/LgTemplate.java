package server.essp.pms.account.logic;

import c2s.essp.pms.account.DtoPmsAcnt;
import server.essp.framework.logic.AbstractESSPLogic;
import server.essp.pms.activity.logic.ActivityNodeCopy;
import server.essp.pms.wbs.logic.WbsNodeCopy;
import server.framework.common.BusinessException;
import net.sf.hibernate.Query;
import java.util.List;
import c2s.dto.DtoComboItem;
import db.essp.pms.Account;
import java.util.ArrayList;
import net.sf.hibernate.Session;
import java.util.Vector;
import client.framework.model.VMComboBox;

public class LgTemplate extends AbstractESSPLogic {
    public static final String INSERT_INTO = "insert into ";
    public static final String UPDATE = "update ";

    public static final String PMS_ACNT_SEQ_TABLE = "pms_acnt_seq";
    public static final String PMS_PBS_MAX_CODE = "pms_pbs_max_code";
    //account pcb
    public static final String PMS_PCB_ITEM_TABLE = "pms_pcb_item";
    public static final String PMS_PCB_PARAMETER_TABLE = "pms_pcb_parameter";

    //account pbs
    public static final String PMS_PBS_TABLE = "pms_pbs";

    //wbs table
    public static final String PMS_WBS_TABLE = "pms_wbs";
    public static final String PMS_WBS_CODE_TABLE = "pms_wbs_code";
    public static final String PMS_WBS_CHECKPOINT_TABLE = "pms_wbs_checkpoint";
    public static final String PMS_PBS_ASSIGNMENT_TABLE = "pms_pbs_assignment";
    public static final String PMS_QA_CHECKPOINT_TABLE = "pms_qa_check_point";
    public static final String PMS_QA_CHECKACTION_TABLE = "pms_qa_check_action";
    public static final String PMS_WBS_GUIDELINE_TABLE = "pms_wbs_guideline";

    //activity table
    public static final String PMS_ACTIVITY_TABLE = "pms_activity";
    public static final String PMS_ACTIVITY_CODE_TABLE = "pms_activity_code";
    public static final String PMS_ACTIVITY_QUALITY_TABLE = "pms_activity_quality";
    public static final String PMS_ACTIVITY_RELATION_TABLE = "pms_activity_relation";
    public static final String PMS_ACTIVITY_GUIDELINE_TABLE = "pms_activity_guideline";

    public ActivityNodeCopy activtyCopy;
    public WbsNodeCopy wbsCopy;

    public LgTemplate(){
    }
    public LgTemplate(ActivityNodeCopy activtyCopy, WbsNodeCopy wbsCopy) {
        this.activtyCopy = activtyCopy;
        this.wbsCopy = wbsCopy;
    }
    /**
     * 根据模板或项目来创建模板
     * 1.新建模板,生成模板Rid
     * 2.复制PCB
     * 3.复制PBS树
     * 4.创建模板对应计划的根节点,将来源的根节点内容复制到目标根节点
     * 5.将源根节点下第一层子结点连接到创建的模板根节点下
     */
    public void createTemplate(Long srcAcntRid, DtoPmsAcnt dto) {
        if(dto == null){
            throw new BusinessException("dto of template is null!");
        }
        Long templateRid = createTemplate(dto);
        if(srcAcntRid == null) return;
        if(templateRid == null){
            throw new BusinessException("template rid is null!");
        }
        copyPCB(srcAcntRid,templateRid);
        copyPBSTree(srcAcntRid, templateRid);
        copyWbsActivityTree(srcAcntRid,templateRid);
    }

    /**
     * 复制srcAcntRid项目树到acntRid指定的项目下
     * 复制的内容包括：
     *   wbs/activity
     *   wbs关联的checkpoint,pbs,code，Process(checkpoint,guideLine)
     *   activity关联的status,Predecessors,Successors,pbs,code,Quaility,Process(checkpoint,guideLine),milestone
     * 操作的方式：
     *   把找到符合条件的记录复制一份，修改相关的XXXRID，然后把此记录存入数据库
     *   比如：复制wbs，
     *   1：先根据srcAcntRid找到所有的wbs记录
     *   2：再把每条wbs记录的acntRid更该为acntRid
     *   3：最后把更该后的记录存入pms_wbs(有关时间和人等信息将不会保存)
     *   就完成了srcAcntRid指定项目的wbs到acntRid指定项目的复制
     * @param root ITreeNode
     * @param acntRid Long
     */

    public void copyWbsActivityTree(Long srcAcntRid,Long descAcntRid){
        try{
            //保存其对应的AccountSeq
            String accountSeqSql = INSERT_INTO + " " + PMS_ACNT_SEQ_TABLE +
                                   " (RID,SEQ_TYPE,LAST_RID,ROOT_RID,SEQ,STEP,RST,RCT,RUT)"+
                                   " (select "+descAcntRid+" as RID,"+
                                       " SEQ_TYPE,LAST_RID,ROOT_RID,SEQ,STEP,RST,SYSDATE,SYSDATE"+
                                       " from "+PMS_ACNT_SEQ_TABLE+" t"+
                                       " where  t.RID="+srcAcntRid+")";
            log.info("execute:"+accountSeqSql);
            this.getDbAccessor().executeUpdate(accountSeqSql);

            //保存其对应的pmsPbsMaxCode
            String pmsPbsMaxCodeSql = INSERT_INTO + " " + PMS_PBS_MAX_CODE +
                                      " (ACNT_RID,MAX_CODE)"+
                                      " (select "+descAcntRid+" as RID,MAX_CODE"+
                                      " from "+PMS_PBS_MAX_CODE+" t"+
                                      " where  t.ACNT_RID="+srcAcntRid+")";
            log.info("execute:"+pmsPbsMaxCodeSql);
            this.getDbAccessor().executeUpdate(pmsPbsMaxCodeSql);

            //保存wbs
            String wbsSql = INSERT_INTO + " " + PMS_WBS_TABLE +
                            " (ACNT_RID,WBS_RID,PARENT_RID,CODE,NAME,MANAGER,WEIGHT,BRIEF,"+
                               " IS_MILESTONE,ANTICIPATED_START,ANTICIPATED_FINISH,PLANNED_START,PLANNED_FINISH,"+
                               " ACTUAL_START,ACTUAL_FINISH,COMPLETE_METHOD,ETC_METHOD,"+
                               " RST,RCT,RUT,SEQ,PARENT_ACNT_RID)"+
                            " (select "+descAcntRid+" as ACNT_RID,WBS_RID,"+
                                 "  PARENT_RID,CODE,NAME,"+
                                 null +" as MANAGER,WEIGHT,BRIEF,IS_MILESTONE,"+
                                 null +" as ANTICIPATED_START," +null+" as ANTICIPATED_FINISH," +
                                 null +" as PLANNED_START,"      +null +" as PLANNED_FINISH,"
                                 +null +" as ACTUAL_START,"     +null +" as ACTUAL_FINISH,"+
                                 "COMPLETE_METHOD,ETC_METHOD,"+
                                " RST,SYSDATE as RCT,SYSDATE as RUT,SEQ,"+
                                descAcntRid+" as PARENT_ACNT_RID"+
                                " from " + PMS_WBS_TABLE + " t"+
                                " where t.ACNT_RID=" + srcAcntRid + ")";
            log.info("execute:"+wbsSql);
            this.getDbAccessor().executeUpdate(wbsSql);

            //保存wbs的code
            String wbsCodeSql = INSERT_INTO + " " + PMS_WBS_CODE_TABLE +
                                " (ACNT_RID,WBS_RID,TYPE_RID,VALUE_RID,TYPE,VALUE_PATH)"+
                                " (select "+descAcntRid+" as ACNT_RID,"+
                                    " WBS_RID,TYPE_RID,VALUE_RID,TYPE,VALUE_PATH"+
                                    " from " + PMS_WBS_CODE_TABLE + " t" +
                                    " where t.ACNT_RID=" + srcAcntRid + ")";
            log.info("execute:"+wbsCodeSql);
            this.getDbAccessor().executeUpdate(wbsCodeSql);

            //保存wbs的checkpoint
            String wbsCheckPointSql = INSERT_INTO + " " + PMS_WBS_CHECKPOINT_TABLE +
                                   " (ACNT_RID,WBS_RID,CHK_RID,CHK_NAME,CHK_WEIGHT,DUE_DATE,"+
                                       " FINISH_DATE,IS_COMPLETED,REMARK,RST,RCT,RUT)"+
                                   " (select "+descAcntRid+" as ACNT_RID,"+
                                      " WBS_RID,CHK_RID,CHK_NAME,CHK_WEIGHT,"+
                                        null+" as DUE_DATE,"+
                                        null + " as FINISH_DATE,IS_COMPLETED,REMARK,RST,"+
                                      " SYSDATE as RCT,SYSDATE as RUT"+
                                      " from " + PMS_WBS_CHECKPOINT_TABLE + " t"+
                                      " where t.ACNT_RID=" + srcAcntRid + ")";
            log.info("execute:" +wbsCheckPointSql);
            this.getDbAccessor().executeUpdate(wbsCheckPointSql);

            //保存wbs的pbs和activity的pbs
            String wbsPbsSql = INSERT_INTO + " " + PMS_PBS_ASSIGNMENT_TABLE +
                               " (ACNT_RID,PBS_RID,JOIN_TYPE,JOIN_RID,IS_WORKPRODUCT)"+
                               " (select "+descAcntRid+" as ACNT_RID,"+
                                  " PBS_RID,JOIN_TYPE,JOIN_RID,IS_WORKPRODUCT"+
                                  " from " + PMS_PBS_ASSIGNMENT_TABLE + " t"+
                                  " where t.ACNT_RID=" + srcAcntRid + ")";
            log.info("execute:"+wbsPbsSql);
            this.getDbAccessor().executeUpdate(wbsPbsSql);

            //保存wbs的process:checkpoint和Activity的process:checkpoint
            String processCheckPointSql = INSERT_INTO + " " + PMS_QA_CHECKPOINT_TABLE +
                                          "(RID,ACNT_RID,BELONG_RID,BELONG_TO,NAME,METHOD,"+
                                            " RST,RCT,RUT)"+
                                          " (select RID, "+descAcntRid+" as ACNT_RID,"+
                                             " BELONG_RID,BELONG_TO,NAME,METHOD,"+
                                             " RST,SYSDATE as RCT,SYSDATE as RUT"+
                                             " from " + PMS_QA_CHECKPOINT_TABLE + " t"+
                                             " where t.ACNT_RID=" + srcAcntRid + ")";
            log.info("execute:"+processCheckPointSql);
            this.getDbAccessor().executeUpdate(processCheckPointSql);

            //保存wbs的process:guideLine
            String wbsGuideLineSql = INSERT_INTO + " " + PMS_WBS_GUIDELINE_TABLE +
                                  " (ACNT_RID,WBS_RID,REF_ACNT_RID,REF_WBS_RID,DESCRIPTION,"+
                                      " RST,RCT,RUT)"+
                                  " (select " + descAcntRid+" as ACNT_RID,t.WBS_RID,"+
                                      srcAcntRid+" as REF_ACNT_RID,t.WBS_RID as REF_WBS_RID,g.DESCRIPTION,"+
                                      " t.RST,SYSDATE as RCT,SYSDATE as RUT"+
                                      " from " + PMS_WBS_TABLE + " t "+
                                      " left join "+PMS_WBS_GUIDELINE_TABLE+" g on t.acnt_rid = g.acnt_rid and t.wbs_rid = g.wbs_rid " +
                                      " where t.ACNT_RID=" + srcAcntRid + ")";
            log.info("execute:"+wbsGuideLineSql);
            this.getDbAccessor().executeUpdate(wbsGuideLineSql);

            //保存activity
            String activitySql = INSERT_INTO + " " + PMS_ACTIVITY_TABLE +
                                 " (ACNT_RID,WBS_RID,ACTIVITY_RID,CODE,NAME,MANAGER,WEIGHT,BRIEF,"+
                                    " IS_KEY_PATH,ANTICIPATED_START,ANTICIPATED_FINISH,PLANNED_START,"+
                                    " PLANNED_FINISH,ACTUAL_START,ACTUAL_FINISH,DURATION_PLAN,DURATION_ACTUAL,"+
                                    " DURATION_REMAIN,DURATION_COMPLETE,"+
                                    " COMPLETE_METHOD,ETC_METHOD,PRE_RID,WBS_ACNT_RID,TIMELIMIT,TIMELIMIT_TYPE,"+
                                    " MILESTONE,MILESTONE_TYPE,REACH_CONDITION,IS_QUALITY_ACT,SEQ,RST,RCT,RUT)"+
                                 " (select " + descAcntRid+" as ACNT_RID,WBS_RID,ACTIVITY_RID,CODE,NAME,"+
                                      null+" as  MANAGER,WEIGHT,BRIEF,IS_KEY_PATH,"+
                                      null+" as ANTICIPATED_START," +null+" as ANTICIPATED_FINISH,"+
                                      null+" as PLANNED_START,"     +null+" as PLANNED_FINISH,"+
                                      null+" as ACTUAL_START,"      +null+" as ACTUAL_FINISH,"+
                                      " DURATION_PLAN,DURATION_ACTUAL,DURATION_REMAIN,DURATION_COMPLETE,"+
                                      " COMPLETE_METHOD,ETC_METHOD,PRE_RID,"+
                                      descAcntRid+" as WBS_ACNT_RID,"+
                                      " TIMELIMIT,TIMELIMIT_TYPE,MILESTONE,MILESTONE_TYPE,REACH_CONDITION,IS_QUALITY_ACT,SEQ,"+
                                      "  RST,SYSDATE as RCT,SYSDATE as RUT"+
                                      " from " + PMS_ACTIVITY_TABLE + " t"+
                                      " where t.ACNT_RID=" + srcAcntRid + ")";
            log.info("execute:"+activitySql);
            this.getDbAccessor().executeUpdate(activitySql);

            //保存activity的code
            String activityCodeSql = INSERT_INTO + " " + PMS_ACTIVITY_CODE_TABLE +
                                     " (ACNT_RID,ACTIVITY_RID,TYPE_RID,VALUE_RID,TYPE,VALUE_PATH)"+
                                     " (select " + descAcntRid+" as ACNT_RID,"+
                                        " ACTIVITY_RID,TYPE_RID,VALUE_RID,TYPE,VALUE_PATH"+
                                        " from " + PMS_ACTIVITY_CODE_TABLE + " t"+
                                        " where t.ACNT_RID=" + srcAcntRid + ")";
            log.info("execute:"+activityCodeSql);
            this.getDbAccessor().executeUpdate(activityCodeSql);

            //保存activity的predecessor和successor
            String activityRelationSql = INSERT_INTO + " " + PMS_ACTIVITY_RELATION_TABLE +
                                         " (ACNT_RID,ACTIVITY_ID,POST_ACTIVITY_ID,START_FINISH_TYPE,"+
                                             " DELAY_DAYS,RST,RCT,RUT)"+
                                          " (select " + descAcntRid+" as ACNT_RID,"+
                                             " ACTIVITY_ID,POST_ACTIVITY_ID,START_FINISH_TYPE,DELAY_DAYS,"+
                                             " RST,SYSDATE as RCT,SYSDATE as RUT"+
                                             " from " + PMS_ACTIVITY_RELATION_TABLE + " t"+
                                             " where t.ACNT_RID=" + srcAcntRid + ")";
            log.info("execute:"+activityRelationSql);
            this.getDbAccessor().executeUpdate(activityRelationSql);

            //保存activity的guideline
            String activityGuideLineSql = INSERT_INTO + " " + PMS_ACTIVITY_GUIDELINE_TABLE +
                                          " (ACNT_RID,ACT_RID,REF_ACNT_RID,REF_ACT_RID,DESCRIPTION,"+
                                             " RST,RCT,RUT)"+
                                          " (select " + descAcntRid+" as ACNT_RID,t.ACTIVITY_RID,"+
                                             srcAcntRid+" as REF_ACNT_RID,t.ACTIVITY_RID,g.DESCRIPTION, "+
                                             " t.RST,SYSDATE as RCT,SYSDATE as RUT"+
                                             " from " + PMS_ACTIVITY_TABLE + " t "+
                                             "left join "+PMS_ACTIVITY_GUIDELINE_TABLE+" g on t.acnt_rid = g.acnt_rid and t.activity_rid = g.act_rid " +
                                             " where t.ACNT_RID=" + srcAcntRid + ")";
            log.info("execute:"+activityGuideLineSql);
            this.getDbAccessor().executeUpdate(activityGuideLineSql);

            //保存activity的quality
            String activityQualitySql = INSERT_INTO + " " + PMS_ACTIVITY_QUALITY_TABLE
                                            + " (ACNT_RID,ACTIVITY_RID,TYPE,PRODUCTION,CRITERION,METHOD,REMARK,"
                                            + " PLAN_SIZE,ACTUAL_SIZE,ACTUAL_CASE_NUM,ACTUAL_DENSITY,CONCLUSION_BY,"
                                            + " CONCLUSION,REASON,RST,RCT,RUT,ACTUAL_DEFECT_NUM,ACTUAL_DEFECT_RATE)"
                                            + " (select " + descAcntRid + " as ACNT_RID,ACTIVITY_RID,TYPE,PRODUCTION,CRITERION,METHOD,REMARK,"
                                            + " PLAN_SIZE,ACTUAL_SIZE,ACTUAL_CASE_NUM,ACTUAL_DENSITY,CONCLUSION_BY,"
                                            + " CONCLUSION,REASON,RST, SYSDATE as RCT, SYSDATE as RUT,ACTUAL_DEFECT_NUM,ACTUAL_DEFECT_RATE"
                                            + " from "+PMS_ACTIVITY_QUALITY_TABLE+" t "
                                            + " where t.ACNT_RID=" + srcAcntRid + ")";
            log.info("execute:"+activityQualitySql);
            this.getDbAccessor().executeUpdate(activityQualitySql);

        }catch(Exception e){
            log.error(e);
            throw new BusinessException("WbsActivityTree.copy.error",e);
        }
    }

    private Long createTemplate(DtoPmsAcnt dto) {
        //先保存template，并把生成的template RID存入dto
        LgAccount logic = new LgAccount();
        logic.add(dto);
        return dto.getRid();
    }
    /**
     * 复制srcAcntRid指定项目的pcb item到desctemplateRid指定项目下
     * @param srcAcntRid Long
     * @param desctemplateRid Long
     */
    public void copyPCB(Long srcAcntRid,Long descTemplateRid) {
        long acntRid = srcAcntRid.longValue();
        long templateRid = descTemplateRid.longValue();
        try{
            String accountPcbSql = INSERT_INTO + " " + PMS_PCB_ITEM_TABLE +
                                   " (RID,ACNT_RID,NAME,TYPE,REMARK,SEQ,RST,RCT,RUT)"+
                                   " (select RID," + templateRid+" as ACNT_RID,"+
                                      " NAME,TYPE,REMARK,SEQ,RST,SYSDATE as RCT,SYSDATE as RUT"+
                                      " from " + PMS_PCB_ITEM_TABLE + " t"+
                                      " where t.ACNT_RID=" + acntRid + ")";
            log.info("execute:"+accountPcbSql);
            this.getDbAccessor().executeUpdate(accountPcbSql);

        }catch(Exception e){
            log.error(e);
            throw new BusinessException("template.pcb.save.error",e);
        }
        //copy parameter
        //add by lipengxu
        try{
            String parameterSql = INSERT_INTO + " " + PMS_PCB_PARAMETER_TABLE +
                                   " (RID,ACNT_RID,ITEM_RID,NAME,UNIT,UCL,MEAN,LCL,PLAN,REMARK,ID,SEQ,RST,RCT,RUT)"+
                                   " (select RID," + templateRid+" as ACNT_RID,"+
                                      " ITEM_RID,NAME,UNIT,UCL,MEAN,LCL,PLAN,REMARK,ID,SEQ,RST,SYSDATE as RCT,SYSDATE as RUT"+
                                      " from " + PMS_PCB_PARAMETER_TABLE + " t"+
                                      " where t.ACNT_RID=" + acntRid + ")";
            log.info("execute:"+parameterSql);
            this.getDbAccessor().executeUpdate(parameterSql);

        }catch(Exception e){
            log.error(e);
            throw new BusinessException("template.pcb_parameter.save.error",e);
        }
    }
    /**
     * 复制PBS树
     */
    public void copyPBSTree(Long srcAcntRid,Long descTemplateRid){
        long acntRid = srcAcntRid.longValue();
        long templateRid = descTemplateRid.longValue();
        try{
            String accountPbsTreeSql = INSERT_INTO + " " + PMS_PBS_TABLE +
                                       " (PBS_RID,PRODUCT_CODE,PRODUCT_NAME,PBS_REFERRENCE,"+
                                          " PBS_MANAGER,PBS_BRIEF,PLANNED_FINISH,ACTUAL_FINISH,"+
                                          " PBS_STATUS,PBS_PARENT_RID,ACNT_RID,SEQ,PARENT_ACNT_RID,"+
                                          " RST,RCT,RUT)"+
                                       " (select PBS_RID,PRODUCT_CODE,PRODUCT_NAME,PBS_REFERRENCE,"+
                                          null+" as PBS_MANAGER,PBS_BRIEF,"+
                                          null+" as PLANNED_FINISH,"+null+" as ACTUAL_FINISH,"+
                                          " PBS_STATUS,PBS_PARENT_RID,"+
                                          templateRid+" as ACNT_RID,SEQ,"+
                                          templateRid+" as PARENT_ACNT_RID,"+
                                          " RST,SYSDATE as RCT,SYSDATE as RUT"+
                                          " from " + PMS_PBS_TABLE + " t"+
                                          " where t.ACNT_RID=" + acntRid + ")";
            log.info("execute:"+accountPbsTreeSql);
            this.getDbAccessor().executeUpdate(accountPbsTreeSql);

        }catch(Exception e){
            log.info(e);
            throw new BusinessException("template.pbstree.copy.error",e);
        }
    }

    /**
     * 列出系统中所有Template和OSP的下拉选项
     * @param includeOSP boolean
     * @return List
     */
    public Vector getOspTemplateCombox(boolean includeOSP) {

        List templateList = new ArrayList();
        Vector dtoList = new Vector();
        Query q;
        String getType = "is_template=1";
        if (includeOSP) {
            getType = "(is_template=1 or is_template=2)";
        }
        try {
            Session s = this.getDbAccessor().getSession();
            q = s.createQuery( //modify by lipengxu add Approved
                "from Account where acnt_status = 'Approved' and " + getType +
                " order by acnt_name");
            templateList = q.list();

            for (int i = 0; i < templateList.size(); i++) {
                Account accountTemp = new Account();

                accountTemp = (Account) templateList.get(i);
                DtoComboItem item = new DtoComboItem(accountTemp.getName(),
                                    accountTemp.getRid());
                dtoList.add(item);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new BusinessException("", ex);
        }
        return dtoList;
    }
    public  static void  main(String[] args){
        LgTemplate lg = new LgTemplate();
        lg.copyWbsActivityTree(new Long(6002),new Long(83545));
    }

}
