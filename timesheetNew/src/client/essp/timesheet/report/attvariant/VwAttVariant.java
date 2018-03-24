/*
 * Created on 2008-1-29
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package client.essp.timesheet.report.attvariant;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JButton;
import c2s.essp.timesheet.report.DtoAttVariant;
import c2s.essp.timesheet.report.DtoAttVariantQuery;
import client.essp.common.TableUitl.TableColumnChooseDisplay;
import client.essp.common.excelUtil.VWJSExporterUtil;
import client.essp.common.view.VWGeneralWorkArea;
import client.essp.common.view.VWTDWorkArea;
import client.framework.view.common.comMSG;
import client.framework.view.vwcomp.VWJPopupEditor;
import com.wits.util.Parameter;
import com.wits.util.comDate;

/**
 * VwAttVariant
 * @author TBH
 */
public class VwAttVariant extends VWTDWorkArea{
	
    	private static final String actionIdExporter = "FTAttVariantExport";
    
        private VwAttVariantUp vwUp = new VwAttVariantUp();
        private VwAttVariantDownBase vwDown = new VwAttVariantDownBase();
        private VwAttVariantLeaveDown leaveDown = new VwAttVariantLeaveDown();
        private VwAttVariantOvertimeDown otDown = new VwAttVariantOvertimeDown();
        private JButton remindBtn = new JButton();
        private JButton queryBtn = new JButton();
        private JButton expBtn = new JButton();
        private List<DtoAttVariant> leaveList;
        private List<DtoAttVariant> otList;
        
        public VwAttVariant() {
            super(160);
            try {
                jbInit();
            } catch (Exception ex) {
                log.error(ex);
            }
            addUICEvent();
        }
    
        //初始化
        private void jbInit() throws Exception {
            this.setVerticalSplit();
            this.setPreferredSize(new Dimension(650, 650));
            vwUp = new VwAttVariantUp();
            vwUp.setPreferredSize(new Dimension(650, 200));
            this.getTopArea().addTab("rsid.timesheet.attvariantquery",vwUp);
            
            this.getDownArea().addTab("rsid.timesheet.attVariantLeaveHours", leaveDown);
            this.getDownArea().addTab("rsid.timesheet.attVariantOvertimeHours", otDown);
        }
    
        public void addUICEvent() {
            //查询
            queryBtn.setText("rsid.timesheet.query");
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
            
            //Display
            TableColumnChooseDisplay chooseDisplayTimes = 
                    new TableColumnChooseDisplay(leaveDown.getTable(), this);
            JButton buttonTimes = chooseDisplayTimes.getDisplayButton();
            leaveDown.getButtonPanel().addButton(buttonTimes);
            
            TableColumnChooseDisplay chooseDisplayOt = 
                new TableColumnChooseDisplay(otDown.getTable(), this);
            JButton buttonOt = chooseDisplayOt.getDisplayButton();
            otDown.getButtonPanel().addButton(buttonOt);
            
            //發郵件
            remindBtn = leaveDown.getButtonPanel().addButton("email.jpg");
            remindBtn.setToolTipText("rsid.timesheet.callingUp");
            remindBtn.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    processRemind();
                }
            });
            remindBtn.setEnabled(false);
            
            
            //全選
            vwUp.radioBtnAll.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    selectRadioAll();
                }
            });
            
            //有差異
            vwUp.radioBtnVariant.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    selectRadioVariant();
                }
            });
        }
        
       //初始化郵件對話框
        private void processRemind() {
            if((leaveList == null || leaveList.size() ==0) 
                    && (otList == null || otList.size() ==0)) {
                return;
            }
            for(DtoAttVariant dto : leaveList) {
                if(dto.isBalanced()){
                    dto.setChecked(true);
                } else {
                    dto.setChecked(false);
                }
            }
            
            for(DtoAttVariant dto : otList) {
                if(dto.isBalanced()){
                    dto.setChecked(true);
                } else {
                    dto.setChecked(false);
                }
            }
            Parameter param = new Parameter();
            param.put(DtoAttVariantQuery.DTO_LEAVE_LIST, leaveList);
            param.put(DtoAttVariantQuery.DTO_OVERTIME_LIST, otList);
            VwSendMail vwSendMail = new VwSendMail();
            vwSendMail.setParameter(param);
            VWGeneralWorkArea gwa = new VWGeneralWorkArea();
            gwa.addTab("rsid.timesheet.mailList", vwSendMail);
            gwa.setPreferredSize(new Dimension(800, 400));
            //show
            VWJPopupEditor popup = new VWJPopupEditor(this.getParentWindow(),"",
                                                      gwa, vwSendMail);
            int result = popup.showConfirm();
        }
    
        //全选
        private void selectRadioAll(){
            if(vwUp.radioBtnAll.isSelected()){
                vwUp.radioBtnVariant.setSelected(false);
            }else{
                vwUp.radioBtnVariant.setSelected(true);
            }
        }
        
        //选择有差异
        private void selectRadioVariant(){
            if(vwUp.radioBtnVariant.isSelected()){
                vwUp.radioBtnAll.setSelected(false);
            }else{
                vwUp.radioBtnAll.setSelected(true);
            }
        }
        
        /**
         * 导出报表
         *
         */
        protected void processExport() {
            DtoAttVariantQuery dtoQuery = vwUp.getDtoAttVariant();
            if(checkError(dtoQuery)){
               return;
            }
            String begin = comDate.dateToString(dtoQuery.getBeginDate());
            String end = comDate.dateToString(dtoQuery.getEndDate());
            String site = dtoQuery.getSite();
            String empId = dtoQuery.getEmpId();
            String selectAll = dtoQuery.getSelectAll().toString();
            if(empId == null) {
             empId = "";
            }
            Map<String, String> param = new HashMap<String, String>();
            param.put(DtoAttVariantQuery.DTO_BEGIN_DATE, begin);
            param.put(DtoAttVariantQuery.DTO_END_DATE, end);
            param.put(DtoAttVariantQuery.DTO_SITE, site);
            param.put(DtoAttVariantQuery.DTO_EMP_ID, empId);
            param.put(DtoAttVariantQuery.DTO_SELECT_ALL,selectAll);
            VWJSExporterUtil.excuteJSExporter(actionIdExporter, param);
        }
        
        /**
         * 校验开始和结束时间
         * @param dtoAtt
         * @return boolean
         */
        private boolean checkError(DtoAttVariantQuery dtoAtt) {
           Date begin = dtoAtt.getBeginDate();
           Date end = dtoAtt.getEndDate();
           if(begin == null){ 
               comMSG.dispErrorDialog("rsid.common.fill.begin");
               vwUp.foucsOnData(DtoAttVariantQuery.DTO_BEGIN_DATE);
               return true;
           }
           if(end == null){
               comMSG.dispErrorDialog("rsid.common.fill.end");
               vwUp.foucsOnData(DtoAttVariantQuery.DTO_END_DATE);
               return true;
           }
           if(begin.after(end)){
               comMSG.dispErrorDialog("rsid.common.beginlessEnd");
               vwUp.foucsOnData(DtoAttVariantQuery.DTO_BEGIN_DATE);
               return true;
           }
           return false;
        }
    
        //查詢
        private void actionPerformedQuery(ActionEvent e) {
            DtoAttVariantQuery dtoQuery = vwUp.getDtoAttVariant();
            Parameter param = new Parameter();
            param.put(DtoAttVariantQuery.DTO_ATTVARIANT_QUERY, dtoQuery);
            param.put(DtoAttVariantQuery.DTO_RESULT_LIST,null);
            vwDown.setParameter(param);
            boolean flag = checkError(dtoQuery);
            if(!flag){
             vwDown.query();
             Map resultMap = vwDown.resultMap;
             leaveList = (List)resultMap.get(DtoAttVariantQuery.DTO_LEAVE_HOURS);
             param.put(DtoAttVariantQuery.DTO_RESULT_LIST, leaveList);
             leaveDown.setParameter(param);
             leaveDown.refreshWorkArea();
            
             otList = (List)resultMap.get(DtoAttVariantQuery.DTO_OVERTIME_HOURS);
             param.put(DtoAttVariantQuery.DTO_RESULT_LIST, otList);
             otDown.setParameter(param);
             otDown.refreshWorkArea();
             
             if((leaveList == null || leaveList.size() == 0) 
                     && (otList == null || otList.size() ==0)){
                remindBtn.setEnabled(false);
            } else {
                remindBtn.setEnabled(true);
            }
            }
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
