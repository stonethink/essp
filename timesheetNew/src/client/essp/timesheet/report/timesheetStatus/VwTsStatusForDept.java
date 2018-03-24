/*
 * Created on 2008-3-17
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package client.essp.timesheet.report.timesheetStatus;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JButton;
import c2s.dto.ITreeNode;
import c2s.essp.timesheet.report.DtoTsStatusQuery;
import client.essp.common.excelUtil.VWJSExporterUtil;
import client.essp.common.view.VWGeneralWorkArea;
import client.essp.common.view.VWTDWorkArea;
import client.framework.view.common.comMSG;
import com.wits.util.Parameter;
import com.wits.util.comDate;

/**
 * <p>Title: </p>
 *
 * <p>Description:VwTsStatusForDept </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author tubaohui
 * @version 1.0
 */
public class VwTsStatusForDept extends VWTDWorkArea{
        private static final String actionIdExporter = "FTSStatusExporter";
        private VwTsStatusUpForDept vwUp = new VwTsStatusUpForDept();
        private VwTsStatusDown vwDown = new VwTsStatusDown();
        private VwTsActiveDown vwActDown = new VwTsActiveDown();
        private VwTsUnfillDown vwTsDown = new VwTsUnfillDown();
        private VwStatusHumanDown vwStatusDown = new VwStatusHumanDown();
        private VwFilledRateDown vwFillRate = new VwFilledRateDown();
        private JButton queryBtn = new JButton();
        private JButton expBtn = new JButton();
        
        public VwTsStatusForDept() {
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
            this.getTopArea().addTab("rsid.timesheet.tsReportStatusDeptQuery",vwUp);
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
         * 校验开始和结束时间
         * @param dtoAtt
         * @return boolean
         */
        protected boolean checkError(DtoTsStatusQuery dtoQuery) {
           Date begin = dtoQuery.getBeginDate();
           Date end = dtoQuery.getEndDate();
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
            DtoTsStatusQuery dtoQuery = vwUp.getDtoTsStatusQuery();
            Parameter param = new Parameter();
            param.put(DtoTsStatusQuery.DTO_TS_QUERY, dtoQuery);
            this.add(vwDown);
            vwDown.setParameter(param);
            vwDown.refreshWorkArea();
            boolean flag = checkError(dtoQuery);
            if(!flag){
             vwDown.query();
             Map resultMap = vwDown.resultMap;
             VWGeneralWorkArea downArea = new VWGeneralWorkArea();
             if(resultMap != null){
                 //填寫率
                String newTitle = getLocalTitle(DtoTsStatusQuery.DTO_FILLED_RATE);
                ITreeNode rootFillRate = (ITreeNode) resultMap.get(DtoTsStatusQuery.DTO_FILLED_RATE);
                if(rootFillRate != null){
                    param.put(DtoTsStatusQuery.DTO_RESULT_LIST, rootFillRate);
                    vwFillRate = new VwFilledRateDown();
                    vwFillRate.setParameter(param);
                    downArea.addTab(newTitle,vwFillRate);
                    vwFillRate.refreshWorkArea();  
                }
                
                //填寫率匯總
                newTitle = getLocalTitle(DtoTsStatusQuery.DTO_FILLED_RATE_GATHER);
                ITreeNode root = (ITreeNode) resultMap.get(DtoTsStatusQuery.DTO_FILLED_RATE_GATHER);
                if(root != null ){
                    param.put(DtoTsStatusQuery.DTO_RESULT_LIST, root);
                    vwFillRate = new VwFilledRateDown();
                    vwFillRate.setParameter(param);
                    downArea.addTab(newTitle,vwFillRate);
                    vwFillRate.refreshWorkArea();  
                }
                
                newTitle = getLocalTitle(DtoTsStatusQuery.DTO_STATUS_HUMAN);
                List lst = (List) resultMap.get(DtoTsStatusQuery.DTO_STATUS_HUMAN);
                //状态人员统计
                if(lst != null && lst.size() > 0){
                param.put(DtoTsStatusQuery.DTO_RESULT_LIST, lst);
                vwStatusDown = new VwStatusHumanDown();
                vwStatusDown.setParameter(param);
                downArea.addTab(newTitle,vwStatusDown);
                vwStatusDown.refreshWorkArea();  
                }
                //未填写工时单
                newTitle = getLocalTitle(DtoTsStatusQuery.DTO_UNFILLED);
                lst = (List) resultMap.get(DtoTsStatusQuery.DTO_UNFILLED);
                if(lst != null && lst.size() > 0){
                    param.put(DtoTsStatusQuery.DTO_RESULT_LIST, lst);
                    vwTsDown = new VwTsUnfillDown();
                    vwTsDown.setParameter(param);
                    downArea.addTab(newTitle, vwTsDown);
                    vwTsDown.refreshWorkArea();
                }
                //未提交工时单
                newTitle = getLocalTitle(DtoTsStatusQuery.DTO_ACTIVE);
                lst = (List) resultMap.get(DtoTsStatusQuery.DTO_ACTIVE);
                if(lst != null && lst.size() > 0){
                    param.put(DtoTsStatusQuery.DTO_RESULT_LIST, lst);
                    vwActDown = new VwTsActiveDown();
                    vwActDown.setParameter(param);
                    downArea.addTab(newTitle, vwActDown);
                    vwActDown.refreshWorkArea();
                }
                //已提交工时单
                newTitle = getLocalTitle(DtoTsStatusQuery.DTO_SUBMITTED);
                lst = (List) resultMap.get(DtoTsStatusQuery.DTO_SUBMITTED);
                if(lst != null && lst.size() > 0){
                    param.put(DtoTsStatusQuery.DTO_RESULT_LIST, lst);
                    vwDown = new VwTsStatusDown();
                    vwDown.setParameter(param);
                    downArea.addTab(newTitle, vwDown);
                    vwDown.refreshWorkArea();
                }
                //被拒绝工时单
                newTitle = getLocalTitle(DtoTsStatusQuery.DTO_REJECTED);
                lst = (List) resultMap.get(DtoTsStatusQuery.DTO_REJECTED);
                if(lst != null && lst.size() > 0){
                    param.put(DtoTsStatusQuery.DTO_RESULT_LIST, lst);
                    vwDown = new VwTsStatusDown();
                    vwDown.setParameter(param);
                    downArea.addTab(newTitle, vwDown);
                    vwDown.refreshWorkArea();
                }
                //PM已审批工时单
                newTitle = getLocalTitle(DtoTsStatusQuery.DTO_PM_APPROVED);
                lst = (List) resultMap.get(DtoTsStatusQuery.DTO_PM_APPROVED);
                if(lst != null && lst.size() > 0){
                    param.put(DtoTsStatusQuery.DTO_RESULT_LIST, lst);
                    vwDown = new VwTsStatusDown();
                    vwDown.setParameter(param);
                    downArea.addTab(newTitle, vwDown);
                    vwDown.refreshWorkArea();
                }
                //RM已审批工时单
                newTitle = getLocalTitle(DtoTsStatusQuery.DTO_RM_APPROVED);
                lst = (List) resultMap.get(DtoTsStatusQuery.DTO_RM_APPROVED);
                if(lst != null && lst.size() > 0){
                    param.put(DtoTsStatusQuery.DTO_RESULT_LIST, lst);
                    vwDown = new VwTsStatusDown();
                    vwDown.setParameter(param);
                    downArea.addTab(newTitle, vwDown);
                    vwDown.refreshWorkArea();
                }
                //已审批工时单
                newTitle = getLocalTitle(DtoTsStatusQuery.DTO_APPROVED);
                lst = (List) resultMap.get(DtoTsStatusQuery.DTO_APPROVED);
                if(lst != null && lst.size() > 0){
                    param.put(DtoTsStatusQuery.DTO_RESULT_LIST, lst);
                    vwDown = new VwTsStatusDown();
                    vwDown.setParameter(param);
                    downArea.addTab(newTitle, vwDown);
                    vwDown.refreshWorkArea();
                }
                this.setDownArea(downArea);
             }
            }
        }
        
        /**
         * 國際化卡片標題
         * @param title
         * @return String
         */
        protected String getLocalTitle(String title){
            String newTitle = "";
            if(title.equals(DtoTsStatusQuery.DTO_FILLED_RATE)){
                newTitle="rsid.timesheet.fillRate";
            }
            if(title.equals(DtoTsStatusQuery.DTO_FILLED_RATE_GATHER)){
                newTitle="rsid.timesheet.fillRateGather";
            }
            if(title.equals(DtoTsStatusQuery.DTO_UNFILLED)){
                newTitle="rsid.timesheet.unfilled";
            }
            if(title.equals(DtoTsStatusQuery.DTO_APPROVED)){
                newTitle= "rsid.timesheet.confirmed";
            }
            if(title.equals(DtoTsStatusQuery.DTO_REJECTED)){
                newTitle = "rsid.timesheet.rejected";
            }
            if(title.equals(DtoTsStatusQuery.DTO_SUBMITTED)){
                newTitle = "rsid.timesheet.unconfirmed";
            }
            if(title.equals(DtoTsStatusQuery.DTO_ACTIVE)){
                newTitle = "rsid.timesheet.unlock";
            }
            if(title.equals(DtoTsStatusQuery.DTO_PM_APPROVED)){
                newTitle = "rsid.timesheet.pmapproved";
            }
            if(title.equals(DtoTsStatusQuery.DTO_STATUS_HUMAN)){
                newTitle = "rsid.timesheet.statusHumanStat";
            }
            if(title.equals(DtoTsStatusQuery.DTO_RM_APPROVED)){
                newTitle="rsid.timesheet.rmapproved";
            }
            return newTitle;
        }
        
        //设置参数
        public void setParameter(Parameter parameter) {
            vwUp.setParameter(parameter);
        }
    
        //刷新
        public void refreshWorkArea() {
            vwUp.refreshWorkArea();
        }
    
        /**
         * 导出报表
         *
         */
        protected void processExport() {
            DtoTsStatusQuery dtoQuery = vwUp.getDtoTsStatusQuery();
            if(checkError(dtoQuery)){
               return;
            }
            String beginDate = comDate.dateToString(dtoQuery.getBeginDate(),comDate.pattenDate);
            String endDate = comDate.dateToString(dtoQuery.getEndDate(),comDate.pattenDate);
            Map<String, String> param = new HashMap<String, String>();
            param.put(DtoTsStatusQuery.DTO_BEGIN_DATE, beginDate);
            param.put(DtoTsStatusQuery.DTO_END_DATE, endDate);
            param.put(DtoTsStatusQuery.DTO_DEPT_ID,dtoQuery.getDeptId());
            param.put(DtoTsStatusQuery.DTO_IS_DEPT_QUERY,dtoQuery.getIsDeptQuery().toString());
            param.put(DtoTsStatusQuery.DTO_INCLUDE_SUB,dtoQuery.getIsSub().toString());
            VWJSExporterUtil.excuteJSExporter(actionIdExporter, param);
        }

}
