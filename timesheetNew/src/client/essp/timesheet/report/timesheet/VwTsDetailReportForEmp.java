package client.essp.timesheet.report.timesheet;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import c2s.essp.timesheet.report.DtoQueryCondition;
import client.essp.common.excelUtil.VWJSExporterUtil;
import client.framework.view.common.comMSG;
import com.wits.util.comDate;
/**
 * <p>Title: VwTsDetailReport</p>
 *
 * <p>Description: 工时明细报表查询界面的最外层框架(一般员工适用)</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author wenhaizheng
 * @version 1.0
 */
public class VwTsDetailReportForEmp extends VwTsDetailReportBase {
    private final static String acntionId_Export_Period="FTSExportTsPeriodReport";
    JButton expDetailBtn = new JButton();
    protected VwTsDetailReportTopBase getTop() {
        return new VwTsDetailReportTopForEmp();
    }
    protected void jbInit() {
        this.getTopArea().addTab("rsid.timesheet.tsReportMyQuery", top);
        this.getDownArea().addTab("rsid.timesheet.tsReportMy", down);
    }
    /**
     * 事件处理
     */
    protected void addUICEvent() {
        super.addUICEvent();
        //按周期導出
        expDetailBtn = top.getButtonPanel().addButton("export.png");
        expDetailBtn.setToolTipText("rsid.common.exportdetail");
        expDetailBtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                processExportByPeriod();
            }
        });
    }

    //導出
    protected void processExportByPeriod() {
        DtoQueryCondition dtoQueryCondition = top.getDtoQueryCondition();
        if(checkError(dtoQueryCondition)){
           return;
        }
        String begin = comDate.dateToString(dtoQueryCondition.getBegin());
        String end = comDate.dateToString(dtoQueryCondition.getEnd());
        String loginId = "null";
        if(dtoQueryCondition.getLoginId() != null) {
            loginId = dtoQueryCondition.getLoginId();
        }
        Map<String, String> param = new HashMap<String, String>();
        param.put("loginId", loginId);
        param.put("begin", begin);
        param.put("end", end);
        VWJSExporterUtil.excuteJSExporter(acntionId_Export_Period, param);
    }
    
    //校驗開始和結束時間
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
           return false;
       }
}

