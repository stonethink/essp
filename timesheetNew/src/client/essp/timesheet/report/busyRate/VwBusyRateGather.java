/*
 * Created on 2008-6-27
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package client.essp.timesheet.report.busyRate;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;
import javax.swing.JButton;
import c2s.dto.ITreeNode;
import c2s.essp.timesheet.report.DtoBusyRateQuery;
import client.essp.common.excelUtil.VWJSExporterUtil;
import client.essp.common.view.VWGeneralWorkArea;
import client.essp.common.view.VWTDWorkArea;
import client.framework.view.common.comMSG;
import com.wits.util.Parameter;
import com.wits.util.comDate;

public class VwBusyRateGather extends VWTDWorkArea{
       private static final String actionIdExporter = "FTSBusyRateGatherExporter";;
       private VwBusyRateGatherUp vwUp = new VwBusyRateGatherUp();
       private VwBusyRateGatherDown vwDown = new VwBusyRateGatherDown();
       private Vector deptItem = new Vector();
       private JButton queryBtn = new JButton();
       private JButton expBtn = new JButton();
    
       public VwBusyRateGather() {
            super(150);
             try {
                jbInit();
             } catch (Exception ex) {
             }
              addUICEvent();
          }
    
        //初始化
        private void jbInit() throws Exception {
                this.setVerticalSplit();
                this.setPreferredSize(new Dimension(650, 650));
                vwUp = new VwBusyRateGatherUp();
                vwUp.setPreferredSize(new Dimension(650, 300));
                this.getTopArea().addTab("rsid.timesheet.busyRateGatherQuery",vwUp);
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
        }
        
        /**
         * 导出报表
         *
         */
        protected void processExport() {
                DtoBusyRateQuery dtoQuery = vwUp.getDtoBusyRateQuery();
                Map<String, String> param = new HashMap<String, String>();
                if(checkError(dtoQuery)){
                   return;
                }
                String beginDate = comDate.dateToString(dtoQuery.getBegin(),comDate.pattenDate);
                String endDate = comDate.dateToString(dtoQuery.getEnd(),comDate.pattenDate);
                String  acntId = dtoQuery.getAcntId();
                String loginId = dtoQuery.getLoginId();
                param.put(DtoBusyRateQuery.DTO_LOGINID, loginId);
                param.put(DtoBusyRateQuery.DTO_BEGIN_DATE, beginDate);
                param.put(DtoBusyRateQuery.DTO_END_DATE, endDate);
                param.put(DtoBusyRateQuery.DTO_ACCOUNT_ID, acntId);
                VWJSExporterUtil.excuteJSExporter(actionIdExporter, param);
        }
        
        /**
         * 校验开始和结束时间
         * @param dtoAtt
         * @return boolean
         */
        private boolean checkError(DtoBusyRateQuery dtoQuery) {
               Date begin = dtoQuery.getBegin();
               Date end = dtoQuery.getEnd();
               if(dtoQuery.getAcntId()== null){
                   comMSG.dispErrorDialog("rsid.common.select.dept");
                   vwUp.foucsOnData(DtoBusyRateQuery.DTO_ACCOUNT_ID);
                   return true;
               }
               if(begin == null){ 
                   comMSG.dispErrorDialog("rsid.common.fill.begin");
                   vwUp.foucsOnData(DtoBusyRateQuery.DTO_BEGIN_DATE);
                   return true;
               }
               if(end == null){
                   comMSG.dispErrorDialog("rsid.common.fill.end");
                   vwUp.foucsOnData(DtoBusyRateQuery.DTO_END_DATE);
                   return true;
               }
               if(begin.after(end)){
                   comMSG.dispErrorDialog("rsid.common.beginlessEnd");
                   vwUp.foucsOnData(DtoBusyRateQuery.DTO_BEGIN_DATE);
                   return true;
               }
               return false;
        }
        
    
       //查\uFFFD\uFFFD
        private void actionPerformedQuery(ActionEvent e) {
                DtoBusyRateQuery dtoQuery = vwUp.getDtoBusyRateQuery();
                Parameter param = new Parameter();
                param.put(DtoBusyRateQuery.DTO_UNTIL_RATE_QUERY, dtoQuery);
                deptItem = vwUp.DeptItem;
                param.put(DtoBusyRateQuery.DTO_DEPT_VECTOR, deptItem);
                param.put(DtoBusyRateQuery.DTO_DEPT_LIST,null);
                this.add(vwDown);
                vwDown.setParameter(param);
                Boolean flag = checkError(dtoQuery);
                if(!flag){
                 vwDown.queryUtilRateGather();
                 Map deptList = vwDown.deptList;
                 VWGeneralWorkArea downArea = new VWGeneralWorkArea();
                 if(deptList != null){
                    Iterator iter = deptList.keySet().iterator();
                    while(iter.hasNext()){
                      vwDown = new VwBusyRateGatherDown();
                      String year = (String) iter.next();
                      ITreeNode root = (ITreeNode) deptList.get(year);
                      param.put(DtoBusyRateQuery.DTO_DEPT_LIST, root);
                      param.put(DtoBusyRateQuery.DTO_UNTIL_RATE_QUERY, dtoQuery);
                      param.put(DtoBusyRateQuery.DTO_DEPT_VECTOR, deptItem);
                      vwDown.setParameter(param);
                      downArea.addTab(year, vwDown);
                      vwDown.refreshWorkArea();
                    }
                    this.setDownArea(downArea);
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
