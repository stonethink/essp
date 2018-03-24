/*
 * Created on 06.01.2005
 */
package net.sourceforge.ganttproject.gui.projectwizard;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.ButtonModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.sourceforge.ganttproject.GanttProject;
import net.sourceforge.ganttproject.IGanttProject;
import net.sourceforge.ganttproject.calendar.GPCalendar;
import net.sourceforge.ganttproject.calendar.XMLCalendarOpen;
import net.sourceforge.ganttproject.language.GanttLanguage;

/**
 * @author bard
 */
public class WeekendConfigurationPage implements WizardPage, ActionListener {
    private JLabel myLabel = new JLabel(
            "<html><b>Weekend configuration is not implemented yet,<br> please wait until 1.11-pre2 release!</b></html>");

    private final Box myBox = Box.createVerticalBox();

    private final GPCalendar myCalendar;

    private final JPanel myPanel;

    private final JLabel choosePublicHoliday;

    private final JLabel chooseWeekend;

    private final JComboBox JCCalendar;

    private final I18N myI18N;

    private final ArrayList publicHolidayCalendar = new ArrayList();

    //private final File[] files;

    private final URL[] calendars;
    private final GanttProject myProject;

    WeekendConfigurationPage(GPCalendar calendar, I18N i18n,
            IGanttProject project) throws Exception {
        myCalendar = calendar;
        myCalendar.getPublicHolidays().clear();
        myProject = (GanttProject) project;
        myI18N = i18n;
        String[] dayNames = myI18N.getDayNames();
        myPanel = new JPanel(new BorderLayout());
        choosePublicHoliday = new JLabel(GanttLanguage.getInstance().getText(
                "choosePublicHoliday"));
        chooseWeekend = new JLabel(GanttLanguage.getInstance().getText(
                "chooseWeekend"));
        JCCalendar = new JComboBox();
        JCCalendar.addItem(GanttLanguage.getInstance().getText("none"));
        XMLCalendarOpen open = new XMLCalendarOpen();
        open.setCalendars();
        String[] labels = open.getLabels();
        calendars = open.getCalendarResources();
        for (int i = 0; i < labels.length; i++)
            JCCalendar.addItem(labels[i]);
        JCCalendar.addActionListener(this);

        JPanel publicHolidayPanel = new JPanel(new BorderLayout());
        publicHolidayPanel.add(choosePublicHoliday, BorderLayout.WEST);
        publicHolidayPanel.add(JCCalendar);
        myBox.add(publicHolidayPanel);
        myBox.add(new JPanel());

        Box cb = Box.createVerticalBox();
        cb.add(chooseWeekend);
        int nextDay = Calendar.MONDAY;
        for (int i = 0; i < 7; i++) {
            JCheckBox nextCheckBox = new JCheckBox();
            nextCheckBox
                    .setSelected(calendar.getWeekDayType(nextDay) == GPCalendar.DayType.WEEKEND);
            nextCheckBox.setAction(new CheckBoxAction(nextDay,
                    dayNames[nextDay - 1], nextCheckBox.getModel()));
            cb.add(nextCheckBox);
            if (++nextDay >= 8) {
                nextDay = 1;
            }
        }

        JPanel weekendPanel = new JPanel(new BorderLayout());
        weekendPanel.add(cb, BorderLayout.WEST);
        myBox.add(weekendPanel);
        myBox.add(new JPanel());

        JPanel projectPanel = new JPanel(new BorderLayout());
        projectPanel.add(myBox, BorderLayout.NORTH);
        myPanel.add(projectPanel);
        // addUsingGBL(myPanel, myBox, gbc, 0, 1, 4, 4);
    }

    public String getTitle() {
        return myI18N.getProjectWeekendPageTitle();
    }

    public Component getComponent() {
        return myPanel;
    }

    public void setActive(boolean b) {
    }

    private class CheckBoxAction extends AbstractAction {
        private int myDay;

        private ButtonModel myModel;

        CheckBoxAction(int day, String dayName, ButtonModel model) {
            super(dayName);
            myDay = day;
            myModel = model;
        }

        public void actionPerformed(ActionEvent e) {
            WeekendConfigurationPage.this.myCalendar.setWeekDayType(myDay,
                    myModel.isSelected() ? GPCalendar.DayType.WEEKEND
                            : GPCalendar.DayType.WORKING);
        }

    }

    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource() instanceof JComboBox) {
            if (evt.getSource() == JCCalendar)
                myCalendar.setPublicHolidays(getProjectCalendar(), myProject);
        }
    }

    public URL getProjectCalendar() {
        int index = JCCalendar.getSelectedIndex();
        if (index == 0)
            return null;
        else
            return (calendars[index - 1]);
    }

}
