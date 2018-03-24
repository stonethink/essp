/*
 * Created on 27.04.2005
 */
package net.sourceforge.ganttproject.resource;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.swing.DefaultListModel;

import net.sourceforge.ganttproject.calendar.GanttDaysOff;
import net.sourceforge.ganttproject.task.ResourceAssignment;
import net.sourceforge.ganttproject.task.Task;
import net.sourceforge.ganttproject.task.TaskActivity;
import net.sourceforge.ganttproject.task.TaskLength;
import net.sourceforge.ganttproject.task.TaskManager;
import net.sourceforge.ganttproject.time.TimeUnitStack;

public class LoadDistribution {
    public static class Load {
        public final TaskLength startDelta;

        public float load;

        public final Task refTask;

        public TaskLength endDelta = null;

        Load(TaskLength startDelta, float load, Task ref) {
            this.startDelta = startDelta;
            this.load = load;
            this.refTask = ref;
        }

        public String toString() {
            return "delta=" + this.startDelta + " load=" + this.load
                    + " refTask = " + this.refTask;
        }
    }

    private final List myDaysOff = new LinkedList();

    private final List myLoads = new ArrayList();

    private final List myTasksLoads = new ArrayList();

    private final ProjectResource myResource;

    private final Date myStartDate;

    private final TimeUnitStack myTimeUnitStack;

    private final TaskManager myTaskManager;

    // private final int myPosY;
    public LoadDistribution(ProjectResource resource, Date startDate,
            TimeUnitStack timeUnitStack, TaskManager taskManager) {
        myStartDate = startDate;
        myTimeUnitStack = timeUnitStack;
        myTaskManager = taskManager;
        myLoads.add(new Load(null, 0, null));
        myDaysOff.add(new Load(null, 0, null));
        myResource = resource;
        ResourceAssignment[] assignments = myResource.getAssignments();
        for (int j = 0; j < assignments.length; j++) {
            processAssignment(assignments[j]);
        }
        processDaysOff(myResource);
        /*
         * This code was replaced by time-unit independent ArrayList daysOffList =
         * new ArrayList (); for (int i=0; i<resources.length; i++) {
         * HumanResource hr = (HumanResource)resources[i]; int startX = -1; if
         * (hr.getDaysOff() != null) { for (int l = 0 ; l <
         * hr.getDaysOff().size() ; l++) { if
         * (myCurrentFrame.getBottomUnit().getName().equals("week")) { for (int
         * w = 0 ; w < 7 ; w ++) { Date d = (Date) unitStart.clone();
         * d.setDate(d.getDate() + w); if
         * (((GanttDaysOff)hr.getDaysOff().getElementAt(l)).isADayOff (d)) {
         * GraphicPrimitiveContainer.Rectangle r =
         * getPrimitiveContainer().createRectangle(myPosX +
         * w*getChartModel().getBottomUnitWidth()/7 , i * 20,
         * getChartModel().getBottomUnitWidth()/7, 20); r.setBackgroundColor(new
         * Color(240, 240, 160)); } } } else if
         * (((GanttDaysOff)hr.getDaysOff().getElementAt(l)).isADayOff
         * (unitStart)) { daysOffList.add (unitStart); for (int v = 0; v <
         * ((GanttDaysOff)hr.getDaysOff().getElementAt(l)).getDuration() ; v++)
         * startX = myPosX; GraphicPrimitiveContainer.Rectangle r =
         * getPrimitiveContainer().createRectangle(startX, i * 20,
         * getChartModel().getBottomUnitWidth(), 20); r.setBackgroundColor(new
         * Color(240, 240, 160)); } } } }
         */

    }

    private void processDaysOff(ProjectResource resource) {
        // TODO Auto-generated method stub
        HumanResource hr = (HumanResource) resource;
        DefaultListModel daysOff = hr.getDaysOff();
        if (daysOff != null) {
            for (int l = 0; l < daysOff.size(); l++) {
                processDayOff((GanttDaysOff) daysOff.get(l));
            }
        }

    }

    private void processDayOff(GanttDaysOff dayOff) {
        Date viewStart = myStartDate;
        Date dayOffEnd = dayOff.getFinish().getTime();
        if (dayOffEnd.before(viewStart)) {
            return;
        }
        TaskManager taskManager = myTaskManager;
        TaskLength startDelta = taskManager.createLength(myTimeUnitStack
                .getDefaultTimeUnit(), viewStart, dayOff.getStart().getTime());
        TaskLength endDelta = taskManager.createLength(myTimeUnitStack
                .getDefaultTimeUnit(), viewStart, dayOff.getFinish().getTime());
        addDayOff(startDelta, endDelta);
    }

    private void processAssignment(ResourceAssignment assignment) {
        Task task = assignment.getTask();
        if (task.getEnd().getTime().before(myStartDate)) {
            return;
        }
        TaskActivity[] activities = task.getActivities();
        for (int i = 0; i < activities.length; i++) {
            processActivity(activities[i], assignment.getLoad());
        }
    }

    private void processActivity(TaskActivity activity, float load) {
//        System.out.println("processActivity : " + activity + ", load : " + load
//                + "(" + activity.getIntensity() + ")");
        Date startDate = myStartDate;
        if (activity.getEnd().before(startDate)) {
            return;
        }
        if (activity.getIntensity() == 0) {
            return;
        }
        TaskManager taskManager = activity.getTask().getManager();
        TaskLength startDelta = taskManager.createLength(myTimeUnitStack
                .getDefaultTimeUnit(), startDate, activity.getStart());
        TaskLength endDelta = taskManager.createLength(myTimeUnitStack
                .getDefaultTimeUnit(), startDate, activity.getEnd());
        addLoad(startDelta, endDelta, load, activity.getTask());
    }

    void addDayOff(TaskLength startDelta, TaskLength endDelta) {
        addLoad(startDelta, endDelta, 100, myDaysOff, null);
    }

    void addLoad(TaskLength startDelta, TaskLength endDelta, float load, Task t) {
        addLoad(startDelta, endDelta, load, myLoads, t);
    }

    private void addLoad(TaskLength startDelta, TaskLength endDelta,
            float load, List loads, Task t) {
        // System.err.println("[LoadDistribution] addLoad:
        // startDelta="+startDelta+" endDelta="+endDelta+" load="+load);

//        System.out.println("addLoad Task : " + t + ", load : " + load);

        Load taskLoad = new Load(startDelta, load, t);
        taskLoad.endDelta = endDelta;

        myTasksLoads.add(taskLoad);

        int idxStart = -1;
        float currentLoad = 0;
        if (startDelta == null) {
            idxStart = 0;
        } else {
            for (int i = 1; i < loads.size(); i++) {
                Load nextLoad = (Load) loads.get(i);
                if (startDelta.getValue() >= nextLoad.startDelta.getValue()) {
                    currentLoad = ((Load) loads.get(i)).load;
                }
                if (startDelta.getValue() > nextLoad.startDelta.getValue()) {
                    continue;
                }
                idxStart = i;
                if (startDelta.getValue() < nextLoad.startDelta.getValue()) {
                    loads.add(i, new Load(startDelta, currentLoad, null));
                }
                break;
            }
        }
        if (idxStart == -1) {
            idxStart = loads.size();
            loads.add(new Load(startDelta, 0, t));
        }
        int idxEnd = -1;
        if (endDelta == null) {
            idxEnd = loads.size() - 1;
        } else {
            for (int i = idxStart; i < loads.size(); i++) {
                Load nextLoad = (Load) loads.get(i);
                if (endDelta.getValue() > nextLoad.startDelta.getValue()) {
                    nextLoad.load += load;
                    continue;
                }
                idxEnd = i;
                if (endDelta.getValue() < nextLoad.startDelta.getValue()) {
                    Load prevLoad = (Load) loads.get(i - 1);
                    loads
                            .add(i, new Load(endDelta, prevLoad.load - load,
                                    null));
                }
                break;
            }
        }
        if (idxEnd == -1) {
            idxEnd = loads.size();
            loads.add(new Load(endDelta, 0, t));
        }
        // System.err.println("[LoadDistribution] addLoad: \nloads="+myLoads);

    }

    public ProjectResource getResource() {
        return myResource;
    }

    public List getLoads() {
        return myLoads;
    }

    public List getDaysOff() {
        return myDaysOff;
    }

    /**
     * Returns a list of lists of <code>Load</code> instances. Each list
     * contains a set of <code>Load</code>
     * 
     * @return a list of lists of <code>Load</code> instances.
     */
    public List getTasksLoads() {
        return myTasksLoads;
    }
}