package server.essp.pms.psr.logic;

import com.wits.excel.ExcelExporter;
import com.wits.util.Parameter;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import com.wits.excel.SheetExporter;
import java.awt.image.BufferedImage;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.data.general.DefaultPieDataset;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.*;
import server.essp.pms.psr.bean.LaborCostSheetBean;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import db.essp.pms.PmsStatusReportHistory;
import com.wits.util.comDate;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import server.essp.pms.account.logic.LgAccountUtilImpl;
import itf.orgnization.IOrgnizationUtil;
import itf.orgnization.OrgnizationFactory;
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
public class PSRExporter extends ExcelExporter {
    public static final String TEMPLATE_FILE =
        "Template_ProjectStatusReport.xls"; //模板文件名
    public static final String OUT_FILE = "WITS-WH-PM-R0D0-PSR.xls"; //导出文件名

    public PSRExporter() {
        super(TEMPLATE_FILE, OUT_FILE);
    }

    public PSRExporter(String outFile) {
        super(TEMPLATE_FILE, outFile);
    }

    //Sheet名称
    public static final String SHEEET_SUMMARY = "Summary";
    public static final String SHEEET_MILESTONE = "Time(Milestone)";
    public static final String SHEEET_DETAIL = "Time(Detail)";
    public static final String SHEEET_DELIVERY = "Quality(Delivery)";
    public static final String SHEEET_ACATIVITY = "Quality(Activity)";
    public static final String SHEEET_LABORCOST = "LaborCost";
    public static final String SHEEET_EVA = "EVA";
    public static final String SHEEET_COMMU = "Communication";
    public static final String SHEEET_PROCESS = "Process";

    //Sheet配置文件名前缀
    public static final String CONFIG_FILE_HEAD =
        "Template_ProjectStatusReport";

    public void doExport(Parameter inputData) throws Exception {
        //获得数据所需参数
        Long acntRid = (Long) inputData.get("acntRid");

        LgListAllPSRData lglistallpsrdata = new LgListAllPSRData();
        List allPSRData = lglistallpsrdata.getAllPSRDate(acntRid);
        if (allPSRData.size() > 0) {
            generateSummarySheet(allPSRData);
            generateMilestoneSheet(acntRid);
            generateTimeDetailSheet(allPSRData);
            generateCommuAndProcessSheet(acntRid);
            generateLaborCostSheet(acntRid, allPSRData);
            generateEVASheet(allPSRData);
        }
    }

    //生成和导出Summary Sheet
    private void generateSummarySheet(List allPSRData) {
        LgAccountUtilImpl logic = new LgAccountUtilImpl();
        PmsStatusReportHistory h = (PmsStatusReportHistory) allPSRData.get(
            allPSRData.size() - 1);
        IDtoAccount account = logic.getAccountByRID(h.getAcntRid());
        IOrgnizationUtil orgUtil = OrgnizationFactory.createOrgnizationUtil();
        String orgId = account.getOrganization();
        String orgName = orgUtil.getOrgName(orgId);
        String dept = orgId + "--" + orgName;
        HSSFSheet summarySheet = targetWorkbook.getSheet(SHEEET_SUMMARY);
        String summarySheetConfig = getSheetCfgFileName(SHEEET_SUMMARY);
        SheetExporter commuSheetEx = new SheetExporter(targetWorkbook,
            summarySheet, summarySheetConfig);
        Parameter para1 = new Parameter();
        para1.put("Dept", dept);
        para1.put("Account", account);
        para1.put("History", h);
        commuSheetEx.export(para1);
    }

    //生成和导出Time Milestone Sheet
    private void generateMilestoneSheet(Long acntRid) {
        LgPrepareMilestoneSheet logic = new LgPrepareMilestoneSheet();
        List list = logic.getMilestoneSheetData(acntRid);
        HSSFSheet Sheet = targetWorkbook.getSheet(SHEEET_MILESTONE);
        String SheetConfig = getSheetCfgFileName(SHEEET_MILESTONE);
        SheetExporter commuSheetEx = new SheetExporter(targetWorkbook,
            Sheet, SheetConfig);
        Parameter para1 = new Parameter();
        para1.put("Milestone", list);
        commuSheetEx.export(para1);
    }

    //生成和导出Time Detail Sheet
    private void generateTimeDetailSheet(List allPSRData) {
        LgPrepareTimeDetailSheet detailLg = new LgPrepareTimeDetailSheet();
        List list = detailLg.getEVADataList(allPSRData);
        HSSFSheet detailSheet = targetWorkbook.getSheet(SHEEET_DETAIL);
        String SheetConfig = getSheetCfgFileName(SHEEET_DETAIL);
        SheetExporter commuSheetEx = new SheetExporter(targetWorkbook,
            detailSheet, SheetConfig);
        Parameter para1 = new Parameter();
        para1.put("TimeDetail", list);
        commuSheetEx.export(para1);
    }

    //生成和导出EVA Sheet
    private void generateEVASheet(List allPSRData) {
        LgPrepareEVA evaLogic = new LgPrepareEVA();
        List evaList = evaLogic.getEVADataList(allPSRData);
        HSSFSheet evaSheet = targetWorkbook.getSheet(SHEEET_EVA);

        HSSFPatriarch patriarch = evaSheet.createDrawingPatriarch();
        HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 1023,
            255, (short) 0, 4, (short) (700 / 72), 21);
        patriarch.createPicture(anchor, targetWorkbook.addPicture(
            evaLogic.getLineChartForEVA(evaList).toByteArray(),
            HSSFWorkbook.PICTURE_TYPE_PNG));

        HSSFClientAnchor anchor2 = new HSSFClientAnchor(0, 0, 1023,
            255, (short) 0, 23, (short) (700 / 72), 40);
        patriarch.createPicture(anchor2, targetWorkbook.addPicture(
            evaLogic.getLineChartForPerformance(evaList).toByteArray(),
            HSSFWorkbook.PICTURE_TYPE_PNG));
        String evaSheetConfig = getSheetCfgFileName(SHEEET_EVA);
        SheetExporter commuSheetEx = new SheetExporter(targetWorkbook,
            evaSheet, evaSheetConfig);
        Parameter para1 = new Parameter();
        para1.put("evaList", evaList);
        commuSheetEx.export(para1);
    }

    //生成和导出Labor Cost sheet
    private void generateLaborCostSheet(Long acntRid, List allPSRData) {
        PmsStatusReportHistory h = (PmsStatusReportHistory) allPSRData.get(
            allPSRData.size() - 1);
        LgPrepareLoborCost logic = new LgPrepareLoborCost();
        List cost = logic.getAllLaborCost(acntRid);
        LaborCostSheetBean sum = logic.sumAllUser(cost);
        HSSFSheet laborSheet = targetWorkbook.getSheet(SHEEET_LABORCOST);
        HSSFPatriarch patriarch = laborSheet.createDrawingPatriarch();
        HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 1023,
            255, (short) 0, 4, (short) (350 / 72), (500 / 19));
        patriarch.createPicture(anchor, targetWorkbook.addPicture(
            getPieChartForLaborCost(sum).toByteArray(),
            HSSFWorkbook.PICTURE_TYPE_PNG));
        HSSFClientAnchor anchor2 = new HSSFClientAnchor(0, 0, 1023,
            255, (short) 5, 4, (short) (900 / 72), (500 / 19));
        patriarch.createPicture(anchor2, targetWorkbook.addPicture(
            getBarChartForLaborCost(allPSRData).toByteArray(),
            HSSFWorkbook.PICTURE_TYPE_PNG));

        String laborSheetConfig = getSheetCfgFileName(SHEEET_LABORCOST);
        SheetExporter commuSheetEx = new SheetExporter(targetWorkbook,
            laborSheet, laborSheetConfig);
        Parameter para1 = new Parameter();
        sum.setUser("SUM");
        cost.add(0, sum); //加入合计行
        para1.put("LaborCost", cost);
        para1.put("budget", h.getBudget());
        para1.put("actualUsed", sum.getTotal());
        double spend = sum.getTotal().doubleValue() / h.getBudget().doubleValue();
        para1.put("spending", new Double(spend));
        double left = h.getBudget().doubleValue() - sum.getTotal().doubleValue();
        para1.put("left", new Double(left));
        commuSheetEx.export(para1);
    }

    //生成活动成本图
    private ByteArrayOutputStream getPieChartForLaborCost(LaborCostSheetBean
        sum) {
        DefaultPieDataset defaultpiedataset = new DefaultPieDataset();
        defaultpiedataset.setValue("RD", sum.getRd());
        defaultpiedataset.setValue("DE", sum.getDe());
        defaultpiedataset.setValue("BD", sum.getBd());
        defaultpiedataset.setValue("TT", sum.getTt());
        defaultpiedataset.setValue("AW", sum.getAw());
        defaultpiedataset.setValue("PM", sum.getPm());
        defaultpiedataset.setValue("CM", sum.getCm());
        defaultpiedataset.setValue("CS", sum.getCs());
        defaultpiedataset.setValue("QA", sum.getQa());
        defaultpiedataset.setValue("OT", sum.getOt());
        defaultpiedataset.setValue("OA", sum.getOa());

        JFreeChart jfreechart = ChartFactory.createPieChart("活动成本分布图",
            defaultpiedataset, true, true, false);
        PiePlot pieplot = (PiePlot) jfreechart.getPlot();
//        pieplot.setExplodePercent(1, 0.1D);
        pieplot.setLabelGenerator(new StandardPieSectionLabelGenerator(
            "{0} ({2})"));
        BufferedImage image = jfreechart.createBufferedImage(400, 400);
        ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "png", byteArrayOut);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return byteArrayOut;
    }

    //生成每周成本投入趋势图
    private ByteArrayOutputStream getBarChartForLaborCost(List data) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int i = 0; i < data.size(); i++) {
            PmsStatusReportHistory h = (PmsStatusReportHistory) data.get(i);
            String label = comDate.dateToString(h.getStartdate()) + "~~" +
                           comDate.dateToString(h.getFinishdate());
            dataset.addValue(h.getLaborcostperweek(), "a", label);
        }

        JFreeChart chart = ChartFactory.createBarChart("每周成本投入趋势图",
            "Period", "ph", dataset, PlotOrientation.VERTICAL, false, true, true);
        Plot p = chart.getPlot();
        CategoryPlot plot = (CategoryPlot) p;
        CategoryAxis categoryaxis = plot.getDomainAxis();
        categoryaxis.setCategoryLabelPositions(CategoryLabelPositions.
                                               createUpRotationLabelPositions(
            0.52359877559829882D));
        BarRenderer barrenderer = (BarRenderer) plot.getRenderer();
        barrenderer.setBaseItemLabelsVisible(true);
        barrenderer.setBaseItemLabelGenerator(new
                                              StandardCategoryItemLabelGenerator());

        BufferedImage image = chart.createBufferedImage(600, 400);
        ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "png", byteArrayOut);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return byteArrayOut;

    }

    //生成和导出Communication sheet和Process sheet
    private void generateCommuAndProcessSheet(Long acntRid) {
        LgPrepareCommuAndProcess commuAndProcess = new LgPrepareCommuAndProcess();
        List commu = commuAndProcess.getCommuSheetDate(acntRid);
        List process = commuAndProcess.getProcessSheetDate(acntRid);

        HSSFSheet commuSheet = targetWorkbook.getSheet(SHEEET_COMMU);
        String commuSheetConfig = getSheetCfgFileName(SHEEET_COMMU);
        SheetExporter commuSheetEx = new SheetExporter(targetWorkbook,
            commuSheet, commuSheetConfig);
        Parameter para1 = new Parameter();
        para1.put("Communication", commu);
        commuSheetEx.export(para1);

        HSSFSheet processSheet = targetWorkbook.getSheet(SHEEET_COMMU);
        String processSheetConfig = getSheetCfgFileName(SHEEET_COMMU);
        SheetExporter processSheetEx = new SheetExporter(targetWorkbook,
            processSheet, processSheetConfig);
        Parameter para2 = new Parameter();
        para2.put("Process", process);
        processSheetEx.export(para2);
    }

    public static void main(String[] args) {
        Long acntRid = new Long(6022);
        Parameter p = new Parameter();
        p.put("acntRid", acntRid);
        PSRExporter e = new PSRExporter();
        try {
            e.export(p);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
