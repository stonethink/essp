package client.essp.timesheet;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.*;
import javax.swing.plaf.basic.BasicToolBarUI;

import client.essp.common.view.VWGeneralWorkArea;
import client.essp.timesheet.calendar.CalendarSelectDateListener;
import client.essp.timesheet.calendar.TimesheetCalendarPanel;
import client.essp.timesheet.workscope.VwWorkScope;

import com.wits.util.Parameter;

public class VwLeftBench extends VWGeneralWorkArea {
    TimesheetCalendarPanel panelCalendar = new TimesheetCalendarPanel();
    public VwWorkScope vwWorkScope = new VwWorkScope();;

     public VwLeftBench(){
        try {
            jbInit();
        } catch (Exception ex) {
            log.error(ex);
        }
     }

     private void jbInit() throws Exception {
         panelCalendar.setMinimumSize(new Dimension(250, 148));
         panelCalendar.setMaximumSize(new Dimension(250, 148));
         panelCalendar.setPreferredSize(new Dimension(250, 148));

         JToolBar toolbar = new JToolBar();
         toolbar.setOrientation(JToolBar.VERTICAL);
         toolbar.setLayout(new BorderLayout());
         toolbar.add(panelCalendar, BorderLayout.NORTH);
         toolbar.add(vwWorkScope, BorderLayout.CENTER);
         toolbar.setPreferredSize(new Dimension(250, 600));
         toolbar.setUI(new ResizableToolBarUI());

         this.add(toolbar);
     }

     public void setParameter(Parameter p){
         vwWorkScope.setParameter(p);
         super.setParameter(p);
     }

     public void addCalendarSelectDateListener(CalendarSelectDateListener l) {
         panelCalendar.addCalendarSelectDateListener(l);
     }

     public void addActivityChangedListener(ActivityChangedListener l) {
        vwWorkScope.addActivityChangedListener(l);
    }


     public void resetUI() {
         vwWorkScope.refreshWorkArea();
     }
     
     public class ResizableToolBarUI extends BasicToolBarUI {
  		protected RootPaneContainer createFloatingWindow(JToolBar toolbar) {
  			RootPaneContainer c = super.createFloatingWindow(toolbar);
  			if(c instanceof JDialog) {
  				((JDialog)c).setResizable(true);
  			}
  			return c;
  		}
  	}
 }
