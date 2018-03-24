package server.essp.pms.account.logic;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.sql.RowSet;

import c2s.dto.DtoTreeNode;
import c2s.dto.DtoUtil;
import c2s.dto.ITreeNode;
import c2s.essp.common.account.IDtoAccount;
import c2s.essp.common.user.DtoUser;
import c2s.essp.pms.account.DtoAcntBL;
import c2s.essp.pms.account.DtoAcntLaborResDetail;
import c2s.essp.pms.account.DtoAcntLoaborRes;
import c2s.essp.pms.account.DtoNoneLaborRes;
import c2s.essp.pms.account.DtoNoneLaborResItem;
import c2s.essp.pms.account.DtoPmsAcnt;
import c2s.essp.pms.pbs.DtoPmsPbs;
import c2s.essp.pms.wbs.DtoWbsActivity;
import c2s.essp.tc.weeklyreport.DtoTcKey;
import com.wits.excel.ExcelExporter;
import com.wits.excel.SheetExporter;
import com.wits.util.Parameter;
import com.wits.util.ThreadVariant;
import db.essp.pms.Account;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import server.essp.cbs.config.logic.LgPrice;
import server.essp.pms.account.baseline.logic.LgAccountBaseline;
import server.essp.pms.account.cost.logic.LgAcntCost;
import server.essp.pms.account.exlbean.AccountNoneLaborRes;
import server.essp.pms.account.exlbean.MeetingReport;
import server.essp.pms.account.exlbean.PbsBean;
import server.essp.pms.account.exlbean.Timing;
import server.essp.pms.account.exlbean.Visitor;
import server.essp.pms.account.keyperson.logic.LgAccountKeyPersonnel;
import server.essp.pms.account.labor.logic.LgAccountLaborRes;
import server.essp.pms.account.noneLabor.logic.LgAcntNoneLaborResItemList;
import server.essp.pms.account.noneLabor.logic.LgAcntNoneLaborResList;
import server.essp.pms.pbs.logic.LgPmsPbsList;
import server.essp.pms.wbs.logic.LgWbs;
import server.framework.common.BusinessException;
import server.framework.hibernate.HBJdbcAccess;
import c2s.essp.pms.account.cost.*;
import com.wits.excel.ICellStyleAware;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import com.wits.util.StringUtil;
import c2s.essp.pms.account.DtoAcntBLApp;
import c2s.essp.pms.account.DtoAcntBLLog;
import java.util.Date;
import com.wits.util.comDate;
import server.essp.pms.quality.activity.logic.LgQualityActivity;
import server.essp.pms.quality.goal.logic.LgQualityGoal;
import server.essp.cbs.buget.logic.LgBuget;
import db.essp.cbs.PmsAcntCost;
import server.essp.cbs.buget.logic.LgLaborBgt;
import server.essp.pms.account.exlbean.DtoMonthBudget;

public class AccountBaseLineExport extends ExcelExporter {
    public static final String TEMPLATE_FILE = "Template_Baseline.xls"; //Excel模板文件名
    public static final String OUT_FILE = "Baseline Report.xls"; //Excel导出文件名


    //Sheet名称
    public static final String SHEET_CATALOGUE = "Catalogue";
    public static final String SHEEET_VERSION = "Version";
    public static final String SHEEET_SUMMARY = "Summary";
    public static final String SHEET_SCOPE = "Scope";
    public static final String SHEET_HUMAN_RESOURCE = "Human_Resource";
    public static final String SHEET_NON_LABORR_ESOURCE = "Non_Labor_Resource";
    public static final String SHEET_COMMUNICATIONS = "Communications";
    public static final String SHEET_TIMING = "Timing";
    public static final String SHEET_COST = "Cost";
    public static final String SHEET_QUALITY = "Quality";

    public AccountBaseLineExport() {
        super(TEMPLATE_FILE, OUT_FILE);
    }

    public AccountBaseLineExport(String out_file) {
        super(TEMPLATE_FILE, out_file);
    }

    public AccountBaseLineExport(String out_dir, String out_file) {
        super(TEMPLATE_FILE, out_dir, out_file);
    }

    public void doExport(Parameter inputData) throws Exception {
        //获得数据所需参数
        Long accountId = (Long) inputData.get(DtoTcKey.ACNT_RID);

        //准备Catalogue Sheet所需数据并导出
        LgAccountBase lb = new LgAccountBase();
        Account a = lb.load(accountId);

        LgAccountBaseline lgbl = new LgAccountBaseline();
        DtoAcntBL dto = lgbl.queryBaselineInfo(accountId);

        Parameter catalogueParam = new Parameter();
        catalogueParam.put("period",
                           a.getId() + "-" + a.getName() + " Plan(Base Line "
                           +dto.getBaselineId()+ ")");
        catalogueParam.put("exportDate",comDate.dateToString(new Date()));
        HSSFSheet catalogueSheet = targetWorkbook.getSheet(SHEET_CATALOGUE);
        String catalogueSheetConfigFile = getSheetCfgFileName(SHEET_CATALOGUE);
        SheetExporter catalogueExporter = new SheetExporter(targetWorkbook,
            catalogueSheet, catalogueSheetConfigFile);
        catalogueExporter.export(catalogueParam);

        //准备Version Sheet所需数据并导出
        Parameter versionParam = new Parameter();
        if (dto != null) {
            versionParam.put("cellData1", dto);
            List apL = lgbl.listApprovalLog(accountId, dto.getBaselineId());
            if(apL != null) {
                for(int i = 0; i < apL.size(); i++) {
                    DtoAcntBLApp aplog = (DtoAcntBLApp) apL.get(i);
                    aplog.setFilledBy(getCnName(aplog.getFilledBy()));
                }
            }
            versionParam.put("tblData1", apL);
        }
        List baseL = lgbl.listBaseLineLog(accountId);
        for(int i = 0; i < baseL.size(); i++) {
            DtoAcntBLLog baseLine = (DtoAcntBLLog) baseL.get(i);
            baseLine.setApproveUser(getCnName(baseLine.getApproveUser()));
        }
        versionParam.put("tblData2", baseL);
        HSSFSheet versionSheet = targetWorkbook.getSheet(SHEEET_VERSION);
        String versionSheetConfigFile = getSheetCfgFileName(SHEEET_VERSION);
        SheetExporter versionExporter = new SheetExporter(targetWorkbook,
            versionSheet, versionSheetConfigFile);
        versionExporter.export(versionParam);

        //准备Summary Sheet所需数据并导出
        LgAccountBase lg = new LgAccountBase();
        Account account = lg.load(accountId);
        LgAccountKeyPersonnel logic = new LgAccountKeyPersonnel();
        Parameter summaryParam = new Parameter();
        String mLoginId = account.getManager();
        account.setManager(getCnName(mLoginId));
        summaryParam.put("cellData1", account);
        summaryParam.put("tblData1", logic.listKeyPersonnelDto(accountId));
        HSSFSheet summarySheet = targetWorkbook.getSheet(SHEEET_SUMMARY);
        String summarySheetConfigFile = getSheetCfgFileName(SHEEET_SUMMARY);
        SheetExporter summaryExporter = new SheetExporter(targetWorkbook,
            summarySheet, summarySheetConfigFile);
        summaryExporter.export(summaryParam);

        //准备Scope Sheet所需数据并导出

        Parameter scopeParam = new Parameter();
        scopeParam.put("tblData1", getPBSTree(accountId));
        HSSFSheet scopeSheet = targetWorkbook.getSheet(SHEET_SCOPE);
        String scopeSheetConfigFile = getSheetCfgFileName(SHEET_SCOPE);
        SheetExporter scopeExporter = new SheetExporter(targetWorkbook,
            scopeSheet, scopeSheetConfigFile);
        scopeExporter.export(scopeParam);

        //准备Human Resource Sheet所需数据并导出
        Parameter humanParam = new Parameter();
        humanParam.put("tblData1", getLaborResource(accountId));
        HSSFSheet humanSheet = targetWorkbook.getSheet(SHEET_HUMAN_RESOURCE);
        String humanSheetConfigFile = getSheetCfgFileName(SHEET_HUMAN_RESOURCE);
        SheetExporter humanExporter = new SheetExporter(targetWorkbook,
            humanSheet, humanSheetConfigFile);
        humanExporter.export(humanParam);

        //准备Non_Labor_Resource Sheet所需数据并导出
        Parameter nonehumanParam = new Parameter();
        nonehumanParam.put("tblData1", getNoneLabor(accountId));
        HSSFSheet nonehumanSheet = targetWorkbook.getSheet(
            SHEET_NON_LABORR_ESOURCE);
        String nonehumanSheetConfigFile = getSheetCfgFileName(
            SHEET_NON_LABORR_ESOURCE);
        SheetExporter nonehumanExporter = new SheetExporter(targetWorkbook,
            nonehumanSheet, nonehumanSheetConfigFile);
        nonehumanExporter.export(nonehumanParam);

        //准备Communications Sheet所需数据并导出
        Parameter communicationsParam = new Parameter();
        communicationsParam.put("tblData1", getMeetingReportlist(accountId));
        communicationsParam.put("tblData2", getVisitorlist(accountId));
        HSSFSheet communicationsSheet = targetWorkbook.getSheet(
            SHEET_COMMUNICATIONS);
        String communicationsSheetConfigFile = getSheetCfgFileName(
            SHEET_COMMUNICATIONS);
        SheetExporter communicationsExporter = new SheetExporter(targetWorkbook,
            communicationsSheet, communicationsSheetConfigFile);
        communicationsExporter.export(communicationsParam);

        //准备Timing Sheet所需数据并导出
        Parameter timingParam = new Parameter();
        timingParam.put("tblData1", getWbsActivityTree(accountId));
        HSSFSheet timingSheet = targetWorkbook.getSheet(SHEET_TIMING);
        String timingSheetConfigFile = getSheetCfgFileName(SHEET_TIMING);
        SheetExporter timingExporter = new SheetExporter(targetWorkbook,
            timingSheet, timingSheetConfigFile);
        timingExporter.export(timingParam);

        //准备Cost Sheet所需数据并导出
        LgAcntCost lgcost = new LgAcntCost();
        Parameter costParam = new Parameter();
        List tblDatal = lgcost.load(accountId.toString());
        //modify by lipengxu. change dto implements ICellStyleAware
        DtoSum dtoSum = (DtoSum)tblDatal.get(0);
        DtoCostSum dtoCostSum = new DtoCostSum();
        DtoUtil.copyBeanToBean(dtoCostSum,dtoSum);
        tblDatal.remove(0);
        tblDatal.add(0,dtoCostSum);
        costParam.put("tblData1", tblDatal);

        LgBuget lgBuget = new LgBuget();
        PmsAcntCost acntCost = lgBuget.getAcntCost(accountId);
        Long baseRid = acntCost.getBaseRid();
        List tblData2=null;
        if(baseRid!=null){
            tblData2 =getLaborBgtList(baseRid);
        }
        costParam.put("tblData2", tblData2);

        HSSFSheet costSheet = targetWorkbook.getSheet(SHEET_COST);
        String costSheetConfigFile = getSheetCfgFileName(SHEET_COST);
        log.info("costSheetConfigFile:" + costSheetConfigFile);
        SheetExporter costExporter = new SheetExporter(targetWorkbook,
            costSheet, costSheetConfigFile);
        costExporter.export(costParam);

        //准备Quality Sheet所需数据并导出
              Parameter qualityParam = new Parameter();
              LgQualityActivity qa = new LgQualityActivity();
              IDtoAccount accountDto = new DtoPmsAcnt();
              accountDto.setRid(accountId);
              List activityList = qa.listQualityActivity(accountDto);
              qualityParam.put("tblData1",activityList);

              LgQualityGoal lgQualityGoal = new LgQualityGoal();
              List goalList = lgQualityGoal.listQualityGoal(accountId);
              qualityParam.put("tblData2",goalList);

              HSSFSheet qualitySheet = targetWorkbook.getSheet(SHEET_QUALITY);
              String qualitySheetConfigFile = getSheetCfgFileName(SHEET_QUALITY);
              SheetExporter qualityExporter = new SheetExporter(targetWorkbook,
                  qualitySheet, qualitySheetConfigFile);
              qualityExporter.export(qualityParam);

        //导出完成后，默认选择第1个Sheet
        catalogueSheet.setSelected(true);
    }

    //准备PSB Sheet显示的数据
    private ITreeNode getPBSTree(Long acntRid) {
        LgPmsPbsList pbs = new LgPmsPbsList();
        ITreeNode root = pbs.listPBSByAcntRid(acntRid);
        if(root == null)
            return null;
        List children = root.listAllChildrenByPostOrder();
        for (int i = 0; i < children.size(); i++) {
            ITreeNode node = (ITreeNode) children.get(i);
            int level = node.getLevel();
            DtoPmsPbs pbsDto = (DtoPmsPbs) node.getDataBean();
            PbsBean pbsBean = new PbsBean();
            DtoUtil.copyBeanToBean(pbsBean, pbsDto);
            String name = pbsDto.getProductName();
            pbsBean.setProductName(getFillBlank(level) + name);

            node.setDataBean(pbsBean);
        }
        return root;
    }

    private String getFillBlank(int level) {
        StringBuffer result = new StringBuffer();
        while (level > 2) {
            result.append("  ");
            level--;
        }
        return result.toString();
    }

    //准备Labor Resource显示所需数据
    private ITreeNode getLaborResource(Long acntRid) {
        LgAccountLaborRes lg = new LgAccountLaborRes();
        List list = lg.listLaborResWithPlan(acntRid);
        double hour = 0;
        DtoAcntLoaborRes sum = new DtoAcntLoaborRes();
        sum.setEmpName("SUM");
         ITreeNode root = new DtoTreeNode(null);
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                DtoAcntLoaborRes labor = (DtoAcntLoaborRes) list.get(i);
                labor.setEmpName(getCnName(labor.getLoginId()));
                ITreeNode node = new DtoTreeNode(labor);
                root.addChild(node);

                List list1 = labor.getPlanDetail();
                for (int j = 0; j < list1.size(); j++) {
                    DtoAcntLaborResDetail detailDto = (DtoAcntLaborResDetail)
                        list1.get(j);
                    ITreeNode laborNode = new DtoTreeNode(detailDto);
                     node.addChild(laborNode);
                    hour+= detailDto.getHour();
                }
            }
            DtoAcntLaborResDetail sumDetail = new DtoAcntLaborResDetail();
            sumDetail.setHour(hour);
            sum.addDetail(sumDetail);
            ITreeNode node = new DtoTreeNode(sum);
            root.addChild(0,node);
        }
        return root;
    }

    //准备non labor resource所需数据
    private DtoTreeNode getNoneLabor(Long acnt_rid) {
        LgAcntNoneLaborResList lgNonLaborList = new LgAcntNoneLaborResList();
        List pbsList = lgNonLaborList.list(acnt_rid);
        AccountNoneLaborRes nonelabor = new AccountNoneLaborRes();
        DtoTreeNode root = new DtoTreeNode(nonelabor);
        root.setDataBean(null);
        for (int i = 0; i < pbsList.size(); i++) {
            DtoNoneLaborRes dtoNoneLaborRes = (DtoNoneLaborRes) pbsList.get(i);
            AccountNoneLaborRes none = new AccountNoneLaborRes();
            none.setEnvName(dtoNoneLaborRes.getEnvName());
            DtoTreeNode dto = new DtoTreeNode(none);
            root.addChild(dto);
            LgAcntNoneLaborResItemList logic = new LgAcntNoneLaborResItemList();
            List list = logic.list(acnt_rid, dtoNoneLaborRes.getRid());
            for (int j = 0; j < list.size(); j++) {
                DtoNoneLaborResItem dtoNoneLaborResItem = (DtoNoneLaborResItem)
                    list.get(j);
                AccountNoneLaborRes laborRes = new AccountNoneLaborRes();
                laborRes.setResourceName(dtoNoneLaborResItem.getResourceName());
                laborRes.setResourceId(dtoNoneLaborResItem.getResourceId());
                laborRes.setRequirement(dtoNoneLaborResItem.getRequirement());
                laborRes.setStartDate(dtoNoneLaborResItem.getStartDate());
                laborRes.setFinishDate(dtoNoneLaborResItem.getFinishDate());
                laborRes.setResourceNumber(dtoNoneLaborResItem.
                                           getResourceNumber());
                laborRes.setRemark(dtoNoneLaborResItem.getRemark());
                DtoTreeNode tree = new DtoTreeNode(laborRes);
                dto.addChild(tree);
            }
        }
        return root;
    }

    //查找Communication中的Meeting Report
    private List getMeetingReportlist(Long accountId) throws BusinessException {
        List meetingReportList = new ArrayList();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        DateFormat timeFormat = new SimpleDateFormat("HH:mm");
        String sqlString = "select * from CM_MEETING_REPORT where PROJECTID="+accountId;
        try {
            HBJdbcAccess hb = new HBJdbcAccess();
            RowSet rs = hb.executeQuery(sqlString);
            while (rs.next()) {
                MeetingReport meeting = new MeetingReport();
                meeting.setMeetingDate(dateFormat.format(rs.getDate(
                    "MEETING_DATE")));
                meeting.setMeetingTime(timeFormat.format(rs.getDate(
                    "MEETING_TIME")));
                meeting.setMeetingTopic(rs.getString("MEETING_TOPIC"));
                meeting.setMeetingSponsor(rs.getString("MEETING_SPONSOR"));
                meeting.setMeetingPrincipal(rs.getString("MEETING_PRINCIPAL"));
                meeting.setMeetingParticipator(rs.getString(
                    "MEETING_PARTICIPATOR"));
                meeting.setMeetingDescription(rs.getString(
                    "MEETING_DESCRIPTION"));
                meeting.setAttachmentName(rs.getString(
                    "MEETING_ATTACHMENT_NAME"));
                meeting.setAttachmentURL(rs.getString("MEETING_ATTACHMENT_URL"));
                meetingReportList.add(meeting);
            }
        } catch (Exception ex) {
            log.error("Get Meeting Report Error!!");
            throw new BusinessException(ex);
        }
        return meetingReportList;
    }

    //查找Communication中的Visiting and Interviewing
    private List getVisitorlist(Long accountId) throws BusinessException {

        List visitList = new ArrayList();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        String sqlString = "select * from CM_VISIT_INTERVIEW where PROJECTID="+accountId;
        try {
            HBJdbcAccess hb = new HBJdbcAccess();
            RowSet rs = hb.executeQuery(sqlString);
            while (rs.next()) {
                Visitor visit = new Visitor();
                if (rs.getDate("VISIT_DATE") != null &&
                    !rs.getDate("VISIT_DATE").equals("")) {
                    visit.setVisitingDate(dateFormat.format(rs.getDate(
                        "VISIT_DATE")));
                }
                visit.setVisitor(rs.getString("VISITOR"));
                visit.setInterviewee(rs.getString("INTERVIEWEE"));
                if (rs.getDate("FILLED_DATE") != null &&
                    !rs.getDate("FILLED_DATE").equals("")) {
                    visit.setFilledDate(dateFormat.format(rs.getDate(
                        "FILLED_DATE")));
                }
                Integer degree = null;
                visit.setSatisfactionDegree(degree.toString(rs.getInt(
                    "SATISFACTION_DEGREE")));
                visit.setFeedbackBy(rs.getString("FEEDBACK_BY"));
                visitList.add(visit);
            }
        } catch (Exception ex) {
            log.error("Get Visitor Report Error!!");
            throw new BusinessException(ex);
        }
        return visitList;
    }

    //查找项目计划的树
    private ITreeNode getWbsActivityTree(Long accountRid) {
        ITreeNode root = null;
        LgWbs lgwbs = new LgWbs();
        root = lgwbs.getWbsTree(accountRid);
        List allChildren = root.listAllChildrenByPostOrder();
        for (int i = 0; i < allChildren.size(); i++) {
            ITreeNode node = (ITreeNode) allChildren.get(i);
            DtoWbsActivity dto = (DtoWbsActivity) node.getDataBean();
            int level = node.getLevel();
            Timing data = new Timing();
            DtoUtil.copyBeanToBean(data, dto);
            if (dto.isWbs()) {
                data.setWbs(true);
                data.setWbsName(getFillBlank(level) + dto.getName());
                data.setCode(getFillBlank(level) + dto.getCode());
                data.setActivityName(null);
            } else {
                data.setWbs(false);
                data.setWbsName(null);
                data.setActivityName(dto.getName());
                if (dto.isMilestone() != null && dto.isMilestone().booleanValue()) {
                    data.setMilestoneImage("flag.png");
                }
            }
            data.setManager(getCnName(data.getManager()));
            node.setDataBean(data);
        }
        DtoWbsActivity dtoRoot = (DtoWbsActivity) root.getDataBean();
        dtoRoot.setManager(getCnName(dtoRoot.getManager()));
        return root;
    }

    //format loginId to "chinese name (loginId)"
    private String getCnName(String loginId) {
        if("".equals(StringUtil.nvl(loginId))) {
            return "";
        }
        //get chinese name
        String cnName = itf.hr.HrFactory.create().getChineseName(loginId);
        if("".equals(StringUtil.nvl(cnName))) {
            return loginId;
        }
        return cnName + "(" +loginId +")";
    }

    //获得Cost Sheet中项目的Labor Resource价格配置
    private List getPrice(Long acntRid) {
        LgPrice lgprice = new LgPrice();
        return lgprice.listAcntPrice(acntRid);
    }

    public static void main(String[] args) {
        ThreadVariant thread = ThreadVariant.getInstance();
        DtoUser user = new DtoUser();
        user.setUserLoginId("stone.shi");
        thread.put(DtoUser.SESSION_USER, user);

        IDtoAccount account = new DtoPmsAcnt();
        account.setRid(new Long(6022));
        thread.put(IDtoAccount.SESSION_KEY, account);
        AccountBaseLineExport baseline = new AccountBaseLineExport();
        Parameter inputData = new Parameter();
        inputData.put(DtoTcKey.ACNT_RID, new Long(6022));
        try {
            baseline.export(inputData);
//            LgWbs lgwbs = new LgWbs();
//            ITreeNode root = lgwbs.getWbsTree(new Long(6022));
//            root.getDataBean();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    class DtoCostSum extends DtoSum implements ICellStyleAware {
        //Excel导出时，汇总数据设置成粗斜体
        public void setCellStyle(String propertyName, Object bean,
                                 HSSFWorkbook wb,
                                 HSSFCellStyle cellStyle) {
            HSSFFont font = wb.createFont();
            font.setBoldweight(font.BOLDWEIGHT_BOLD);
            font.setItalic(true);
            cellStyle.setFont(font);
        }

    }


    public List getLaborBgtList(Long baseRid) throws BusinessException {
      try {
          LgLaborBgt lgLaborBgt=new LgLaborBgt();
          RowSet rs = lgLaborBgt.getLaborBgt(baseRid);
          DtoMonthBudget dtoSum = new DtoMonthBudget();
          dtoSum.setMonth("SUM");
          List resultList=new ArrayList();
          double budgetAmt=0;
          double budgetPm=0;
          while(rs.next()){
                DtoMonthBudget dtoMonthBudget = new DtoMonthBudget();
                Date month = (Date)rs.getDate("month");
                Double bgtUnitNum=(Double)rs.getDouble("unit_num_sum");
                Double bgtAmt=(Double)rs.getDouble("amt_sum");
                dtoMonthBudget.setBudgetPm(bgtUnitNum);
                dtoMonthBudget.setBudgetAmt(bgtAmt);
                dtoMonthBudget.setMonth(comDate.dateToString(month,"yyyy-MM"));
                resultList.add(dtoMonthBudget);
                budgetAmt+=bgtAmt;
                budgetPm+=bgtUnitNum;
          }
          dtoSum.setBudgetAmt(budgetAmt);
          dtoSum.setBudgetPm(budgetPm);
          resultList.add(0,dtoSum);
          return resultList;
       } catch (Exception ex) {
          log.error(ex);
          throw new BusinessException("ACNT_LABOR_001",
                                      "error while list all resource of account [" +
                                      baseRid +
                                      "]", ex);
      }
  }

}
