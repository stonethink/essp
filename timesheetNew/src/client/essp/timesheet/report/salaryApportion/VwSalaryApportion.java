/*
 * Created on 2008-1-2
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package client.essp.timesheet.report.salaryApportion;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import c2s.essp.timesheet.report.DtoSalaryWkHrQuery;
import client.essp.common.excelUtil.VWJSExporterUtil;
import client.essp.common.view.VWTDWorkArea;
import client.framework.view.common.comMSG;
import com.wits.util.Parameter;
import com.wits.util.comDate;
/**
 * <p>Title: </p>
 *
 * <p>Description:VwSalaryApportionDown </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author tubaohui
 * @version 1.0
 */
public class VwSalaryApportion extends VWTDWorkArea{
        private static final String actionIdExporter = "FTSSalaryWkHrExport";
        VwSalaryApportionUp vwUp = new VwSalaryApportionUp();
        VwSalaryApportionDown vwDown = new VwSalaryApportionDown();
        private JButton queryBtn = new JButton();
        private JButton expBtn = new JButton();
        
        public VwSalaryApportion() {
            super(150);
            try {
                jbInit();
            } catch (Exception ex) {
                log.error(ex);
            }
            addUICEvent();
        }
    
        //��ʼ��
        private void jbInit() throws Exception {
            this.setVerticalSplit();
            this.setPreferredSize(new Dimension(650, 650));
            vwUp = new VwSalaryApportionUp();
            vwUp.setPreferredSize(new Dimension(650, 300));
            this.getTopArea().addTab("rsid.timesheet.salaryquery",vwUp);
            
            vwDown = new VwSalaryApportionDown();
            vwDown.setPreferredSize(new Dimension(650,350));
            this.getDownArea().addTab("rsid.timesheet.tsgather",vwDown);
        }
    
        public void addUICEvent() {
            //��ѯ
            queryBtn.setText("rsid.timesheet.query");
            vwUp.queryButton = vwUp.getButtonPanel().addButton(queryBtn);
            vwUp.queryButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    actionPerformedQuery(e);
                }
            });
            vwUp.queryButton.setToolTipText("rsid.timesheet.query");
            
            //����
            expBtn = vwUp.getButtonPanel().addButton("export.png");
            expBtn.setToolTipText("rsid.common.export");
            expBtn.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    processExport();
                }
             });
            
            //�Y���՞�����26������25
            vwUp.isBalanceOneInput.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    selectBalanceOne();
                }
            });
            
            //�Y���՞鮔��1̖���µ�
            vwUp.isBalanceTwoInput.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    selectBalanceTwo();
                }
            });
        }
        
//      �Y���՞�����26������25
        private void selectBalanceOne(){
              vwUp.isBalanceOneInput.setSelected(true);
              vwUp.isBalanceTwoInput.setSelected(false);
        }
        
        //�Y���՞鮔��1̖���µ�
        private void selectBalanceTwo(){
            vwUp.isBalanceOneInput.setSelected(false);
            vwUp.isBalanceTwoInput.setSelected(true);
       }
        
        /**
         * ��������
         *
         */
        protected void processExport() {
            DtoSalaryWkHrQuery dtoQuery = vwUp.getDtoSalaryQuery();
            if(checkError(dtoQuery)){
               return;
            }
            String beginDate = comDate.dateToString(dtoQuery.getBeginDate(),comDate.pattenDate);
            String balanceOne = dtoQuery.getIsBalanceOne().toString();
            String balanceTwo =dtoQuery.getIsBalanceTwo().toString();
            String site = dtoQuery.getSite().toString();
            Map<String, String> param = new HashMap<String, String>();
            param.put(DtoSalaryWkHrQuery.DTO_BEGIN_DATE, beginDate);
            param.put(DtoSalaryWkHrQuery.DTO_BALANCE_ONT,balanceOne);
            param.put(DtoSalaryWkHrQuery.DTO_BALANCE_TWO,balanceTwo);
            param.put(DtoSalaryWkHrQuery.DTO_SITE,site);
            VWJSExporterUtil.excuteJSExporter(actionIdExporter, param);
        }
        
        /**
         * У�鿪ʼ�ͽ���ʱ��
         * @param dtoAtt
         * @return boolean
         */
        private boolean checkError(DtoSalaryWkHrQuery dtoQuery) {
            Date begin = dtoQuery.getBeginDate();
            if(begin == null){ 
                comMSG.dispErrorDialog("rsid.common.fill.begin");
                vwUp.foucsOnData(DtoSalaryWkHrQuery.DTO_BEGIN_DATE);
                return true;                            
            }
           if(!dtoQuery.getIsBalanceOne() && !dtoQuery.getIsBalanceTwo()){
               comMSG.dispErrorDialog("rsid.timesheet.selectBalanceDate");
               vwUp.foucsOnData(DtoSalaryWkHrQuery.DTO_BALANCE_ONT);
               return true;
           }
           
           return false;
        }
    
        //��ԃ
        private void actionPerformedQuery(ActionEvent e) {
            DtoSalaryWkHrQuery dtoQuery = vwUp.getDtoSalaryQuery();
            Parameter param = new Parameter();
            param.put(DtoSalaryWkHrQuery.DTO_SALARY_QUERY, dtoQuery);
            vwDown.setParameter(param);
            boolean flag = checkError(dtoQuery);
            if(!flag){
             vwDown.refreshWorkArea();
            }
        }
        
        //���ò���
        public void setParameter(Parameter parameter) {
            vwUp.setParameter(parameter);
        }
    
        //ˢ��
        public void refreshWorkArea() {
            vwUp.refreshWorkArea();
        }


}
