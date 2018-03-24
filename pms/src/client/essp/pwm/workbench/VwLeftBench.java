package client.essp.pwm.workbench;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JToolBar;

import client.essp.common.calendar.PanelCalendar;
import client.essp.common.view.VWWorkArea;
import client.essp.pwm.workbench.workscope.VwWorkScope;
import client.framework.common.Global;
import com.wits.util.Parameter;
import client.essp.common.calendar.EditableCalendarPanel;

public class VwLeftBench extends VWWorkArea {

    private boolean refreshFlag = false;

    EditableCalendarPanel panelCalendar;
    public VwWorkScope vwWorkScope;

     public VwLeftBench(){
        try {
            jbInit();
        } catch (Exception ex) {
            log.error(ex);
        }

        initUI();
     }

     private void jbInit() throws Exception {

         panelCalendar = new EditableCalendarPanel(true);
         panelCalendar.setMinimumSize(new Dimension(250, 148));
         panelCalendar.setMaximumSize(new Dimension(250, 148));
         panelCalendar.setPreferredSize(new Dimension(250, 148));

         vwWorkScope = new VwWorkScope();

         JToolBar toolbar = new JToolBar();
         toolbar.setOrientation(JToolBar.VERTICAL);
         toolbar.setLayout(new BorderLayout());
         toolbar.add(panelCalendar, BorderLayout.NORTH);
         toolbar.add(vwWorkScope, BorderLayout.CENTER);
         toolbar.setPreferredSize(new Dimension(250, 600));

         this.add(toolbar);
     }

     private void initUI(){
//         Date today = comDate.toDate(Global.todayDate, Global.todayDatePattern);
//         Calendar canlendar = Calendar.getInstance();
//         canlendar.setTime(today);
//         panelCalendar.setToDay(canlendar);
     }

     public EditableCalendarPanel getPanelCalendar(){
         return this.panelCalendar;
     }

     public void setParameter(Parameter p){
         this.refreshFlag = true;
         this.vwWorkScope.setParameter(p);
     }

     public void refreshWorkArea(){
         if( refreshFlag == true ){
             //开始时选中今天的日期
             Date today = Global.todayDate;
             Calendar c = Calendar.getInstance();
             c.setTime(today);
             panelCalendar.setToDay(c);
             panelCalendar.setSelectToday();

             this.vwWorkScope.refreshWorkArea();
             refreshFlag = false;
         }
     }
 }
