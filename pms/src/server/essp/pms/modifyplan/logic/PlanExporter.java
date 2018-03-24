package server.essp.pms.modifyplan.logic;

import com.wits.excel.ExcelExporter;
import com.wits.excel.SheetExporter;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import com.wits.util.Parameter;
import java.util.Calendar;
import c2s.essp.pms.wbs.DtoWbsTreeNode;
import db.essp.pms.Account;
import server.essp.pms.account.logic.LgAccountBase;
import java.util.List;
import c2s.essp.pms.wbs.DtoWbsActivity;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import server.essp.pwm.wpList.logic.LgWpList;
import c2s.essp.pwm.wp.DtoPwWp;
import server.essp.pms.modifyplan.WbsActivityWp;
import c2s.dto.DtoUtil;
import com.wits.util.comDate;
import c2s.essp.common.calendar.WrokCalendarFactory;
import c2s.essp.common.calendar.WorkCalendar;
import com.wits.util.StringUtil;
import c2s.dto.ITreeNode;
import server.essp.pms.wbs.logic.LgWbs;
import c2s.essp.pms.account.DtoPmsAcnt;
import c2s.essp.common.user.DtoUser;
import com.wits.util.ThreadVariant;
import c2s.essp.common.account.IDtoAccount;

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
public class PlanExporter extends ExcelExporter {
    public static final String TEMPLATE_FILE = "Template_ProjectPlan.xls"; //模板文件名
    public static final String OUT_FILE = "ProjectPlan.xls"; //导出文件名
    private Long acntRid;
    private List wpList;
    private String filter;
    public PlanExporter() {
        super(TEMPLATE_FILE, OUT_FILE);
    }

    public PlanExporter(String outFile) {
        super(TEMPLATE_FILE, outFile);
    }

    //Sheet名称
    public static final String SHEEET_ACCOUNT = "Account";
    public static final String SHEEET_GANTT = "Gantt";
    public static final String SHEEET_TIMING = "Timing";
    //Sheet配置文件名前缀
    public static final String CONFIG_FILE_HEAD = "Template_ProjectPlan";

    public void doExport(Parameter inputData) throws Exception {
        //获得数据所需参数
        DtoWbsTreeNode root = (DtoWbsTreeNode) inputData.get("rootNode");
        acntRid = (Long) inputData.get("acntRid");
        Byte[] imageTmp = (Byte[]) inputData.get("image");
        if(imageTmp != null){
            int height = Integer.parseInt(inputData.get("imageHeight").toString());
            int width = Integer.parseInt(inputData.get("imageWidth").toString());
            filter = inputData.get("filter").toString();

            byte[] image = new byte[imageTmp.length];
            for (int i = 0; i < imageTmp.length; i++) {
                image[i] = imageTmp[i].byteValue();
            }
            //gantt sheet
            this.generateGanttSheet(image, width, height);
        }
        DtoWbsActivity account = (DtoWbsActivity) root.getDataBean();
        //account sheet
        this.generateAccoutSheet(acntRid, account.getCompleteRate());
        //timing sheet
        this.generateTimingSheet(root);
    }

    /**
     * prepare account sheet data
     * @param acntRid Long
     * @param completeRate Double
     */
    private void generateAccoutSheet(Long acntRid, Double completeRate) {
        LgAccountBase lg = new LgAccountBase();
        Account acnt = lg.load(acntRid);
        Parameter param = new Parameter();

        String mLoginId = acnt.getManager();
        if(mLoginId != null && "".equals(mLoginId) == false) {
            String mName = itf.hr.HrFactory.create().getChineseName(mLoginId);
            acnt.setManager(mName + "(" + mLoginId + ")");
        }
        String orgId = acnt.getOrganization();
        if(orgId != null && "".equals(orgId) == false) {
            String orgName = itf.orgnization.OrgnizationFactory.create().
                             getOrgName(orgId);
            acnt.setOrganization(orgName);
        }
        param.put("acnt", acnt);
        param.put("laborList", acnt.getLaborResources());
        param.put("completeRate", completeRate);
        HSSFSheet accountSheet = targetWorkbook.getSheet(SHEEET_ACCOUNT);
        String accountSheetConfigFile = getSheetCfgFileName(SHEEET_ACCOUNT);
        SheetExporter accountSheetEx = new SheetExporter(targetWorkbook,
            accountSheet, accountSheetConfigFile);
        accountSheetEx.export(param);
    }

    /**
     * prepare gantt sheet image
     * @param byteArrayOut byte[]
     * @param w int
     * @param h int
     */
    private void generateGanttSheet(byte[] byteArrayOut, int w, int h) {
        try {
            HSSFSheet ganttSheet = targetWorkbook.getSheet(SHEEET_GANTT);
            HSSFPatriarch patriarch = ganttSheet.createDrawingPatriarch();
            HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 1023,
                255, (short) 0, 2, (short) (w / 72), (h / 19));
            patriarch.createPicture(anchor,
                                    targetWorkbook.addPicture(byteArrayOut,
                HSSFWorkbook.PICTURE_TYPE_PNG
                                    ));
            String ganttSheetConfigFile = getSheetCfgFileName(SHEEET_GANTT);
            SheetExporter timingSheetEx = new SheetExporter(targetWorkbook,
                ganttSheet, ganttSheetConfigFile);
            Parameter param = new Parameter();
            param.put("filter", filter);
            timingSheetEx.export(param);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * prepare timing tree
     * @param root DtoWbsTreeNode
     */
    private void generateTimingSheet(DtoWbsTreeNode root) {
        Parameter param = new Parameter();
        LgWpList lgWpList = new LgWpList();
        wpList = lgWpList.list(acntRid, null);
        try {
            exchangeTree(root);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        param.put("dataTree", root);
        param.put("filter", filter);
        HSSFSheet timingSheet = targetWorkbook.getSheet(SHEEET_TIMING);
        String timingSheetConfigFile = getSheetCfgFileName(SHEEET_TIMING);
        SheetExporter timingSheetEx = new SheetExporter(targetWorkbook,
            timingSheet, timingSheetConfigFile);
        timingSheetEx.export(param);
    }

    /**
     * exchange wbs/activity data bean
     * @param node DtoWbsTreeNode
     * @throws Exception
     */
    private void exchangeTree(DtoWbsTreeNode node) throws Exception {
        DtoWbsActivity dto = (DtoWbsActivity) node.getDataBean();
        WbsActivityWp dataBean = new WbsActivityWp();
        //set type
        if (dto.isActivity()) {
            dataBean.setDataType("activity");
        } else {
            dataBean.setDataType("wbs");
        }
        DtoUtil.copyProperties(dataBean, dto);
        int duration = 0;
        if (dto.getPlannedStart() != null && dto.getPlannedFinish() != null) {
            WorkCalendar wc = WrokCalendarFactory.serverCreate();
            Calendar cStart = Calendar.getInstance();
            Calendar cFinish = Calendar.getInstance();
            cStart.setTime(dto.getPlannedStart());
            cFinish.setTime(dto.getPlannedFinish());
            duration = wc.getWorkDayNum(cStart, cFinish);
        }
        dataBean.setDuration(Integer.toString(duration));
        dataBean.setManager(getCnName(dataBean.getManager()));
        node.setDataBean(dataBean);
        //get activity's wp
        if (dto.isActivity()) {
            for (int i = 0; i < wpList.size(); i++) {
                DtoPwWp wp = (DtoPwWp) wpList.get(i);
                if (wp.getActivityId().equals(dto.getActivityRid())) {
                    dataBean = this.wpDataExchange(wp);
                    dataBean.setDuration(Integer.toString(duration));
                    DtoWbsTreeNode wpNode = new DtoWbsTreeNode(dataBean);
                    node.addChild(wpNode);
                }
            }
        }
        List cList = node.children();
        int count = cList.size();
        for (int j = 0; j < count; j++) {
            exchangeTree((DtoWbsTreeNode) cList.get(j));
        }
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

    /**
     * format wp workerr loginIds to "chinese name (loginId), ......"
     * @param loginIds String
     * @return String
     */
    private String getMutilCnName(String loginIds) {
        if("".equals(StringUtil.nvl(loginIds))) {
            return "";
        }
        String[] idArray = loginIds.split(",");
        String cnNames = "";
        for(int i = 0; i < idArray.length; i++) {
            if("".equals(cnNames)) {
                cnNames = getCnName(idArray[i]);
            } else {
                cnNames += "," + getCnName(idArray[i]);
            }
        }
        return cnNames;
    }

    /**
     * exchange wp data bean
     * @param wp DtoPwWp
     * @return WbsActivityWp
     */
    private WbsActivityWp wpDataExchange(DtoPwWp wp) {
        WbsActivityWp dataBean = new WbsActivityWp();
        Double complRate = new Double(0);
        if (wp.getWpCmpltrate() != null) {
            complRate = new Double(wp.getWpCmpltrate().doubleValue());
        }
        dataBean.setCompleteRate(complRate);
        dataBean.setName(wp.getWpName());
        dataBean.setActualStart(wp.getWpActStart());
        dataBean.setActualFinish(wp.getWpActFinish());
        dataBean.setPlannedStart(wp.getWpPlanStart());
        dataBean.setPlannedFinish(wp.getWpPlanFihish());
        dataBean.setCode(wp.getWpCode());
        dataBean.setWpType(wp.getWpType());
        dataBean.setWpWorker(getMutilCnName(wp.getWpWorker()));
        dataBean.setWpStatus(wp.getWpStatus());
        dataBean.setWpRemark(wp.getWpRemark());
        dataBean.setWpActWkhr(wp.getWpActWkhr() != null ?
                              wp.getWpActWkhr().toString() : "0");
        dataBean.setWpReqWkhr(wp.getWpReqWkhr() != null ?
                              wp.getWpReqWkhr().toString() : "0");
        dataBean.setWpPlanWkhr(wp.getWpPlanWkhr() != null ?
                               wp.getWpPlanWkhr().toString() : "0");
        if (wp.getWpSizePlan() != null && wp.getWpSizeUnit() != null) {
            dataBean.setWpSizePlan(wp.getWpSizePlan() + " " +
                                   wp.getWpSizeUnit());
        }
        if (wp.getWpSizeAct() != null && wp.getWpSizeUnit() != null) {
            dataBean.setWpSizeAct(wp.getWpSizeAct() + " " +
                                  wp.getWpSizeUnit());
        }
        if (wp.getWpDensityPlan() != null && wp.getWpDensityrateUnit() != null) {
            dataBean.setWpDensityPlan(wp.getWpDensityPlan() + " " +
                                      wp.getWpDensityrateUnit());
        }
        if (wp.getWpDensityAct() != null && wp.getWpDensityrateUnit() != null) {
            dataBean.setWpDensityrateAct(wp.getWpDensityAct() + " " +
                                         wp.getWpDensityrateUnit());
        }
        if (wp.getWpDensityratePlan() != null && wp.getWpDensityrateUnit() != null) {
            dataBean.setWpDensityratePlan(wp.getWpDensityratePlan() +
                                          " " + wp.getWpDensityrateUnit());
        }
        if (wp.getWpDensityrateAct() != null && wp.getWpDensityrateUnit() != null) {
            dataBean.setWpDensityrateAct(wp.getWpDensityrateAct() +
                                         " " + wp.getWpDensityrateUnit());
        }
        if (wp.getWpDefectPlan() != null && wp.getWpDefectrateUnit() != null) {
            dataBean.setWpDefectPlan(wp.getWpDefectPlan() + " " +
                                     wp.getWpDefectrateUnit());
        }
        if (wp.getWpDefectAct() != null && wp.getWpDefectrateUnit() != null) {
            dataBean.setWpDefectAct(wp.getWpDefectAct() + " " +
                                    wp.getWpDefectrateUnit());
        }
        if (wp.getWpDefectratePlan() != null && wp.getWpDefectrateUnit() != null) {
            dataBean.setWpDefectratePlan(wp.getWpDefectratePlan() + " " +
                                         wp.getWpDefectrateUnit());
        }
        if (wp.getWpDefectrateAct() != null && wp.getWpDefectrateUnit() != null) {
            dataBean.setWpDefectrateAct(wp.getWpDefectrateAct() + " " +
                                        wp.getWpDefectrateUnit());
        }
        if (wp.getWpProductivityPlan() != null && wp.getWpProductivityUnit() != null) {
            dataBean.setWpProductivityPlan(wp.getWpProductivityPlan() +
                                           " " + wp.getWpProductivityUnit());
        }
        if (wp.getWpProductivityPlan() != null && wp.getWpProductivityUnit() != null) {
            dataBean.setWpProductivityAct(wp.getWpProductivityPlan() +
                                          " " + wp.getWpProductivityUnit());
        }
        dataBean.setWpAssignby(getCnName(wp.getWpAssignby()));
        dataBean.setManager(dataBean.getWpAssignby());
        dataBean.setWpAssigndate(comDate.dateToString(wp.
            getWpAssigndate()));
        dataBean.setWpTechtype(wp.getWpTechtype());
        dataBean.setDataType("wp");
        return dataBean;
    }

    public static void main(String[] args) {
        ThreadVariant thread = ThreadVariant.getInstance();
DtoUser user = new DtoUser();
user.setUserLoginId("stone.shi");
thread.put(DtoUser.SESSION_USER, user);

IDtoAccount account = new DtoPmsAcnt();
account.setRid(new Long(6022));
thread.put(IDtoAccount.SESSION_KEY, account);

        Parameter inputData = new Parameter();
        Long esspRid = new Long(6022);
        LgWbs wbsLogic = new LgWbs();
        ITreeNode treeNode = wbsLogic.getWbsTree(esspRid);
        inputData.put("acntRid", esspRid);
        inputData.put("rootNode", treeNode);
        PlanExporter exp = new PlanExporter();
        try {
            exp.export(inputData);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
