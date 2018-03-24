/*
 * Created on 2008-3-17
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package client.essp.timesheet.report.timesheetStatus;

import java.awt.Rectangle;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;
import javax.swing.JButton;
import c2s.essp.timesheet.report.DtoTsStatusQuery;
import client.essp.common.view.VWGeneralWorkArea;
import client.framework.view.common.comFORM;
import client.framework.view.vwcomp.VWJCheckBox;
import client.framework.view.vwcomp.VWJComboBox;
import client.framework.view.vwcomp.VWJDatePanel;
import client.framework.view.vwcomp.VWJLabel;
import com.wits.util.Parameter;

public abstract class VwTsStatusUpBase extends VWGeneralWorkArea{
        protected DtoTsStatusQuery dtoQuery = new DtoTsStatusQuery();
        private VWJLabel begLab = new VWJLabel();
        private VWJLabel endLab = new VWJLabel();
        protected VWJDatePanel inputBegin = new VWJDatePanel();
        protected VWJDatePanel inputEnd = new VWJDatePanel();
        protected VWJComboBox deptComBox = new VWJComboBox();
        protected VWJLabel deptLab = new VWJLabel();
        protected VWJCheckBox subCheckBox = new VWJCheckBox();
        protected VWJLabel subLab = new VWJLabel();
        protected JButton queryButton = new JButton();
        protected Vector deptItem = new Vector(); 
        protected VWJComboBox siteCombox = new VWJComboBox();
        protected VWJLabel siteLab = new VWJLabel();
        protected Vector siteItem = new Vector();
        
        public VwTsStatusUpBase()  {
            try {
                jbInit();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        //��ʼ��
        public void jbInit() throws Exception {
            this.setLayout(null);
            //��һ��1��
            begLab.setBounds(new Rectangle(20,25,80,20));
            begLab.setText("rsid.timesheet.begin");
            inputBegin.setBounds(new Rectangle(110,25,100,20));
            inputBegin.setCanSelect(true);
            
            endLab.setBounds(new Rectangle(20,60,80,20));
            endLab.setText("rsid.timesheet.end");
            inputEnd.setBounds(new Rectangle(110,60,100,20));
            inputEnd.setCanSelect(true);
          
            add(inputBegin);
            add(begLab);
            add(endLab);
            add(inputEnd);
         }
    
        /**
         * ���ù��
         * @param foucsOn
         */
        protected void foucsOnData(String foucsOn) {
          if(foucsOn.equals(DtoTsStatusQuery.DTO_BEGIN_DATE)) {
              comFORM.setFocus(inputBegin);
          } else if(foucsOn.equals(DtoTsStatusQuery.DTO_END_DATE)){
              comFORM.setFocus(inputEnd);
          }
        }
        
        /**
        * ˢ�½���
        */
       protected void resetUI() {
           initBeginAndEndDate();
       }
    
       /**
        * ��ʼ����ʼ���ںͽ������ڣ���ǰʱ����ϸ��µ��³�����ĩ
        *
        */
       private void initBeginAndEndDate(){
           Calendar cal = Calendar.getInstance();
           int month = cal.get(Calendar.MONTH);
           //�����ǰ�·�Ϊ1�£�������һ���12�µ��³�����ĩ
           if(month==0){
               int year = cal.get(Calendar.YEAR)-1;
               month=11;
               cal.set(year,month,1);
               Date beg =cal.getTime();
               inputBegin.setUICValue(beg);
               int date = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
               cal.set(year,month,date);
               Date end = cal.getTime();
               inputEnd.setUICValue(end);
           }else{
              int year = cal.get(Calendar.YEAR);
              cal.set(year,month-1,1);
              Date beg =cal.getTime();
              inputBegin.setUICValue(beg);
              int date = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
              cal.set(year,month-1,date);
              Date end = cal.getTime();
              inputEnd.setUICValue(end);
           }
       }
    
    
       //�����O��
        public void setParameter(Parameter param) {
            super.setParameter(param);
        }
    
        protected abstract DtoTsStatusQuery getDtoTsStatusQuery();
 }
