package client.essp.timesheet.report.humanreport;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.SwingConstants;

import c2s.essp.timesheet.report.DtoHumanReport;
import c2s.essp.timesheet.report.DtoHumanTimes;
import client.essp.common.TableUitl.TableColumnChooseDisplay;
import client.essp.common.excelUtil.VWJSExporterUtil;
import client.essp.common.view.VWGeneralWorkArea;
import client.essp.common.view.VWTDWorkArea;
import client.framework.view.common.comMSG;
import client.framework.view.vwcomp.VWJPopupEditor;

import com.wits.util.Parameter;
import com.wits.util.comDate;

public class VwHumanReport extends VWTDWorkArea {
	private final static String acntionId_Export="FTSExportHumanReport";
    JButton queryBtn = new JButton();
    JButton expBtn = new JButton();
    JButton remindBtn = new JButton();
    private VwHumanReportTop top = new VwHumanReportTop();
    private VwHumanReportDown down = new VwHumanReportDown();
    private VwHumanTimesDown timesDown = new VwHumanTimesDown();
    private List<DtoHumanTimes> timesList;
	
	public VwHumanReport() {
		super(150);
        jbInit();
        addUICEvent();
	}
	
	private void jbInit() {
		this.getTopArea().addTab("rsid.timesheet.humanReportQuery", top);
		this.getDownArea().addTab("rsid.timesheet.humanTimes", timesDown);
	    this.getDownArea().addTab("rsid.timesheet.humanReport", down);
	}
	private void addUICEvent() {
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
        
        remindBtn = timesDown.getButtonPanel().addButton("email.jpg");
		remindBtn.setToolTipText("rsid.timesheet.callingUp");
		remindBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				processRemind();
			}
		});
		remindBtn.setEnabled(false);
		
		//Display
        TableColumnChooseDisplay chooseDisplayTimes = 
        		new TableColumnChooseDisplay(timesDown.getTable(), this);
		JButton buttonTimes = chooseDisplayTimes.getDisplayButton();
		timesDown.getButtonPanel().addButton(buttonTimes);
		
		TableColumnChooseDisplay chooseDisplay = new TableColumnChooseDisplay(
														down.getTable(), this);
		JButton button = chooseDisplay.getDisplayButton();
		down.getButtonPanel().addButton(button);
        
	}
	private void processRemind() {
		if(timesList == null || timesList.size() ==0) {
			return;
		}
		List dtoList = new ArrayList();
		for(DtoHumanTimes dto : timesList) {
			if(dto.getSumLevel() != 0) {
				continue;
			}
			if(dto.isBalanced()) {
				dto.setChecked(true);
			}
			dtoList.add(dto);
		}
		Parameter param = new Parameter();
		param.put(DtoHumanTimes.DTO_QUERY_LIST, dtoList);
		VwRemindList vwRemindList = new VwRemindList();
		vwRemindList.setParameter(param);
		VWGeneralWorkArea gwa = new VWGeneralWorkArea();
		gwa.addTab("rsid.timesheet.personList", vwRemindList);
		gwa.setPreferredSize(new Dimension(800, 400));
		//show
        VWJPopupEditor popup = new VWJPopupEditor(this.getParentWindow(),"",
                                                  gwa, vwRemindList);
        int result = popup.showConfirm();
	}
	private void processQuery() {
		Date begin = top.getBegin(); 
		Date end = top.getEnd();
		String site = top.getSite();
		if (checkError(begin, end)) {
			return;
		}
		Map resultMap = down.processQuery(begin, end, site);
		Parameter param = new Parameter();
		param.put(DtoHumanReport.DTO_QUERY_RESULT, (List)resultMap.get(DtoHumanReport.DTO_QUERY_RESULT));
		down.setParameter(param);
		param = new Parameter();
		timesList = (List)resultMap.get(DtoHumanTimes.DTO_QUERY_LIST);
		param.put(DtoHumanTimes.DTO_QUERY_LIST, timesList);
		timesDown.setParameter(param);
		if(timesList == null || timesList.size() == 0){
			remindBtn.setEnabled(false);
		} else if(timesList != null && timesList.size() > 0) {
			remindBtn.setEnabled(true);
		}
		this.getDownArea().getSelectedWorkArea().refreshWorkArea();
	}
	private void processExport() {
		Date begin = top.getBegin(); 
		Date end = top.getEnd();
		String site = top.getSite();
		if (checkError(begin, end)) {
			return;
		}
		Map<String, String> param = new HashMap<String, String>();
        param.put(DtoHumanReport.DTO_BEGIN, comDate.dateToString(begin, "yyyy-MM-dd"));
        param.put(DtoHumanReport.DTO_END, comDate.dateToString(end, "yyyy-MM-dd"));
        param.put(DtoHumanReport.DTO_SITE, site);
        VWJSExporterUtil.excuteJSExporter(acntionId_Export, param);
	}
	private boolean checkError(Date begin, Date end) {
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
		   return false;
	   }
	 /**
	   * ¼¤»îË¢ÐÂ
	   */
	  public void setParameter(Parameter param) {
	      super.setParameter(param);
	      top.setParameter(param);
	  }
}
