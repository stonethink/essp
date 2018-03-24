/*
 * Created on 2008-6-18
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package client.essp.timesheet.report.attendance;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import c2s.essp.timesheet.report.DtoAttendanceQuery;
import c2s.essp.timesheet.report.DtoTsStatusQuery;
import client.essp.common.excelUtil.VWJSExporterUtil;
import client.essp.common.view.VWTDWorkArea;
import client.framework.view.common.comMSG;
import com.wits.util.Parameter;
import com.wits.util.comDate;

public class VwAttendance extends VWTDWorkArea{
    private static final String actionIdExporter = "FTSAttendanceExporter";
    private VwAttendanceUp vwUp = new VwAttendanceUp();
    private VwAttendanceDown vwDown = new VwAttendanceDown();
    private JButton queryBtn = new JButton();
    private JButton expBtn = new JButton();
    
    public VwAttendance() {
        super(150);
        try {
            jbInit();
            addUICEvent();
        } catch (Exception ex) {
        }
    }

    //初始化
    protected void jbInit() throws Exception {
        this.setVerticalSplit();
        this.setPreferredSize(new Dimension(650, 650));
        vwUp.setPreferredSize(new Dimension(650, 310));
        this.getTopArea().addTab("rsid.timesheet.attendanceQuery",vwUp);

        vwDown.setPreferredSize(new Dimension(650, 340));
        this.getDownArea().addTab("rsid.timesheet.attendanceReport",vwDown);
    }

    protected void addUICEvent() {
        //查询
        queryBtn.setText("rsid.timesheet.query");
        queryBtn.setSize(40,20);
        vwUp.queryButton = vwUp.getButtonPanel().addButton(queryBtn);
        vwUp.queryButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedQuery(e);
            }
        });
        vwUp.queryButton.setToolTipText("rsid.timesheet.query");
        
        //導出
        expBtn = vwUp.getButtonPanel().addButton("export.png");
        expBtn.setToolTipText("rsid.common.export");
        expBtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                processExport();
            }
         });
    }
    
    /**
     * 导出报表
     *
     */
    protected void processExport() {
        DtoAttendanceQuery dtoQuery = vwUp.getDtoAttendanceQuery();
        if(checkError(dtoQuery)){
           return;
        }
        String beginDate = comDate.dateToString(dtoQuery.getBegin(),comDate.pattenDate);
        String endDate = comDate.dateToString(dtoQuery.getEnd(),comDate.pattenDate);
        String site = dtoQuery.getSite();
        Map<String, String> param = new HashMap<String, String>();
        param.put(DtoAttendanceQuery.DTO_BEGIN_DATE, beginDate);
        param.put(DtoAttendanceQuery.DTO_END_DATE, endDate);
        param.put(DtoAttendanceQuery.DTO_SITE,site);
        VWJSExporterUtil.excuteJSExporter(actionIdExporter, param);
    }
    
    /**
     * 校验开始和结束时间
     * @param dtoAtt
     * @return boolean
     */
    protected boolean checkError(DtoAttendanceQuery dtoQuery) {
       Date begin = dtoQuery.getBegin();
       Date end = dtoQuery.getEnd();
       if(begin == null){ 
           comMSG.dispErrorDialog("rsid.common.fill.begin");
           vwUp.foucsOnData(DtoTsStatusQuery.DTO_BEGIN_DATE);
           return true;
       }
       if(end == null){
           comMSG.dispErrorDialog("rsid.common.fill.end");
           vwUp.foucsOnData(DtoTsStatusQuery.DTO_END_DATE);
           return true;
       }
       if(begin.after(end)){
           comMSG.dispErrorDialog("rsid.common.beginlessEnd");
           vwUp.foucsOnData(DtoTsStatusQuery.DTO_BEGIN_DATE);
           return true;
       }
       return false;
    }
    
      //查詢
      protected void actionPerformedQuery(ActionEvent e) {
            DtoAttendanceQuery dtoQuery = vwUp.getDtoAttendanceQuery();
            Parameter param = new Parameter();
            param.put(DtoAttendanceQuery.DTO_QUERY, dtoQuery);
            vwDown.setParameter(param);
            vwDown.refreshWorkArea();
        }

    //设置参数
    public void setParameter(Parameter parameter) {
        vwUp.setParameter(parameter);
    }

    //刷新
    public void refreshWorkArea() {
        vwUp.refreshWorkArea();
    }


}
