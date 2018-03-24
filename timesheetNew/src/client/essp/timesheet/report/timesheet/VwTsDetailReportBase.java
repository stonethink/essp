package client.essp.timesheet.report.timesheet;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.SwingConstants;

import c2s.essp.timesheet.report.DtoQueryCondition;
import c2s.essp.timesheet.report.DtoTsDetailReport;
import client.essp.common.TableUitl.TableColumnChooseDisplay;
import client.essp.common.excelUtil.VWJSExporterUtil;
import client.essp.common.view.VWTDWorkArea;
import client.framework.view.common.comMSG;

import com.wits.util.Parameter;
import com.wits.util.comDate;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author wenhaizheng
 * @version 1.0
 */
public abstract class VwTsDetailReportBase extends VWTDWorkArea{
    protected VwTsDetailReportTopBase top = getTop();
    protected VwTsDetailReportDown down = new VwTsDetailReportDown();
    private final static String acntionId_Export="FTSExportTsDetailReport";
    JButton queryBtn = new JButton();
    JButton expBtn = new JButton();
    public VwTsDetailReportBase() {
        super(150);
        jbInit();
        addUICEvent();
    }
    protected void jbInit() {
       this.getTopArea().addTab("rsid.timesheet.tsDetailReportQuery", top);
       this.getDownArea().addTab("rsid.timesheet.tsDetailReport", down);
   }
   /**
     * 事件处理
     */
    protected void addUICEvent() {
        top.getButtonPanel().addButton(queryBtn);
        queryBtn.setText("rsid.timesheet.query");
        queryBtn.setHorizontalAlignment(SwingConstants.CENTER);
        queryBtn.setToolTipText("rsid.timesheet.query");
        queryBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                processQuery();
            }
        });
        expBtn = top.getButtonPanel().addButton("export.png");
        expBtn.setToolTipText("rsid.common.export");
        expBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				processExport();
			}
        });

        //Display
        TableColumnChooseDisplay chooseDisplay =
                new TableColumnChooseDisplay(down.getTable(), this);
        JButton button = chooseDisplay.getDisplayButton();
        down.getButtonPanel().addButton(button);

    }
    /**
     * 导出报表
     *
     */
    protected void processExport() {
    	DtoQueryCondition dtoQueryCondition = top.getDtoQueryCondition();
        if(checkError(dtoQueryCondition)){
     	   return;
        }
        String begin = comDate.dateToString(dtoQueryCondition.getBegin());
        String end = comDate.dateToString(dtoQueryCondition.getEnd());
        String deptId = "null";
        String projectId = "null";
        String loginId = "null";
        if(dtoQueryCondition.getDeptId() != null) {
            deptId = dtoQueryCondition.getDeptId();
        }
        if(dtoQueryCondition.getProjectId() != null) {
            projectId = dtoQueryCondition.getProjectId();
        }
        if(dtoQueryCondition.getLoginId() != null) {
        	loginId = dtoQueryCondition.getLoginId();
        }
        Map<String, String> param = new HashMap<String, String>();
        param.put("deptId", deptId);
        param.put("projectId", projectId);
        param.put("loginId", loginId);
        param.put("begin", begin);
        param.put("end", end);
        param.put("queryWay", dtoQueryCondition.getQueryWay());
        param.put("includeSub", ""+dtoQueryCondition.isIncludeSub());
        VWJSExporterUtil.excuteJSExporter(acntionId_Export, param);
    }
   protected void processQuery() {
       DtoQueryCondition dtoQueryCondition = top.getDtoQueryCondition();
       if(checkError(dtoQueryCondition)){
    	   return;
       }
       Parameter param = new Parameter();
       param.put(DtoTsDetailReport.DTO_CONDITION, dtoQueryCondition);
       down.setParameter(param);
       down.refreshWorkArea();

   }
   private boolean checkError(DtoQueryCondition dtoQueryCondition) {
	   Date begin = dtoQueryCondition.getBegin();
	   Date end = dtoQueryCondition.getEnd();
	   if(begin == null){ 
		   comMSG.dispErrorDialog("rsid.common.fill.begin");
		   top.foucsOnDate("begin");
		   return true;
	   }
	   if(end == null){
		   comMSG.dispErrorDialog("rsid.common.fill.end");
		   top.foucsOnDate("end");
		   return true;
	   }
	   if(begin.after(end)){
		   comMSG.dispErrorDialog("rsid.common.beginlessEnd");
		   top.foucsOnDate("begin");
		   return true;
	   }
	   if(dtoQueryCondition.isIncludeSub() &&
	      (dtoQueryCondition.getDeptId() == null 
	       || "".equals(dtoQueryCondition.getDeptId()))) {
		   comMSG.dispMessageDialog("rsid.common.selectDeptWhenIsIncludeSub");
		   top.foucsOnDate("dept");
		   return true;
	   }
	   return false;
   }
    protected abstract VwTsDetailReportTopBase getTop();
  /**
   * 激活刷新
   */
  public void setParameter(Parameter param) {
      super.setParameter(param);
      top.setParameter(param);
  }

}
