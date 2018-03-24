package net.sourceforge.ganttproject.chart;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sourceforge.ganttproject.chart.GraphicPrimitiveContainer.Rectangle;
import net.sourceforge.ganttproject.resource.LoadDistribution;
import net.sourceforge.ganttproject.resource.ProjectResource;
import net.sourceforge.ganttproject.resource.LoadDistribution.Load;
import net.sourceforge.ganttproject.task.ResourceAssignment;
import net.sourceforge.ganttproject.task.Task;
import net.sourceforge.ganttproject.task.TaskLength;
import net.sourceforge.ganttproject.task.algorithm.SortTasksAlgorithm;
import net.sourceforge.ganttproject.time.TimeFrame;
import net.sourceforge.ganttproject.time.TimeUnit;

public class ResourceLoadRenderer extends ChartRendererBase implements
        TimeUnitVisitor {

    private static final SortTasksAlgorithm ourAlgorithm = new SortTasksAlgorithm();

    private TimeFrame myCurrentFrame;

    private TimeUnit myCurrentUnit;

    private int myPosX;

    private Map myResource2rect = new HashMap();

    private List myActivitiesQueue = new ArrayList();

    private List myDistributions;

    private ResourceChart myResourcechart;

    public ResourceLoadRenderer(ChartModelResource model,
            ResourceChart resourceChart) {
        super(model);
        myResourcechart = resourceChart;
    }

    public void beforeProcessingTimeFrames() {
        myDistributions = new ArrayList();
        getPrimitiveContainer().clear();
        myResource2rect.clear();
        myActivitiesQueue.clear();
        myPosX = 0;
        ProjectResource[] resources = ((ChartModelResource) getChartModel())
                .getVisibleResources();
        // Set assignedTasks = new HashSet();
        for (int i = 0; i < resources.length; i++) {
            ProjectResource nextResource = resources[i];
            LoadDistribution nextDistribution = new LoadDistribution(
                    nextResource, getChartModel().getStartDate(),
                    getChartModel().getTimeUnitStack(), getChartModel()
                            .getTaskManager());
            myDistributions.add(nextDistribution);
        }

        ourAlgorithm.sortByStartDate(myActivitiesQueue);
    }

    public void afterProcessingTimeFrames() {

    }

    public void startTimeFrame(TimeFrame timeFrame) {
        myCurrentFrame = timeFrame;
    }

    public void endTimeFrame(TimeFrame timeFrame) {
        myCurrentFrame = null;
    }

    public void startUnitLine(TimeUnit timeUnit) {
        if (timeUnit == myCurrentFrame.getBottomUnit()) {
            myCurrentUnit = timeUnit;
        }
    }

    public void endUnitLine(TimeUnit timeUnit) {
        myCurrentUnit = null;
    }

    /**
     * Class to use as Model object to display the load percentage in the
     * rectangle.
     * 
     * @author bbaranne
     */
    static class ResourceLoad {
        private float load;

        ResourceLoad(float load) {
            this.load = load;
        }

        public float getLoad() {
            return load;
        }

        public String toString() {
            return Float.toString(load);
        }
    }

    public void nextTimeUnit(int unitIndex) {
        if (myCurrentUnit != null && myDistributions != null) {
            Date unitStart = myCurrentFrame.getUnitStart(myCurrentUnit,
                    unitIndex);
            int yPos = 0;
            for (int i = 0; i < myDistributions.size(); i++) {
                LoadDistribution next = (LoadDistribution) myDistributions
                        .get(i);
                getDayOffRectangles(next.getDaysOff(), unitStart, yPos
                        - getConfig().getYOffSet());
                getLoadRectangles(next.getLoads(), unitStart, yPos
                        - getConfig().getYOffSet());

                if (myResourcechart.isExpanded(next.getResource())) {
                    ResourceAssignment assignments[] = next.getResource()
                            .getAssignments();

                    int yPos2 = yPos;
                    List taskLoadsLists = getTaskLoadsLists(next
                            .getTasksLoads());

                    for (int m = 0; m < assignments.length; m++) {
                        yPos2 += getConfig().getRowHeight();
                        Task task = assignments[m].getTask();
                        List taskLoadList = getListForTask(taskLoadsLists, task);
                        if (taskLoadList != null)
                            buildTasksLoadsRectangles(taskLoadList, yPos2
                                    - getConfig().getYOffSet());
                    }
                    yPos += calculateGap(next.getResource());
                }
                yPos += getConfig().getRowHeight();
                GraphicPrimitiveContainer.Line nextLine = getPrimitiveContainer().createLine(0, yPos,(int) getChartModel().getBounds().getWidth(), yPos);
                nextLine.setForegroundColor(Color.GRAY);
            }
            myDistributions = null;
        }
    }

    /**
     * Returns the appropriate list of load per task according to
     * <code>task</code> and searching into <code>tasksLoadsList</code>.
     * 
     * @param tasksLoadsList
     *            List of lists of Load
     * @param task
     *            Reference task.
     * @return The appropriate list of load per task according to
     *         <code>task</code> and searching into
     *         <code>tasksLoadsList</code>.
     */
    private static List getListForTask(List tasksLoadsList, Task task) {
        List res = null;

        for (Iterator it = tasksLoadsList.iterator(); it.hasNext();) {
            List currentList = (List) it.next();
            if (!currentList.isEmpty()
                    && ((Load) currentList.get(0)).refTask == task) {
                res = currentList;
                break;
            }
        }

        return res;
    }

    /**
     * Returns a list of list where the second lists contain Loads that refer to
     * the same task.
     * 
     * @param tasksLoadsList
     *            Load list.
     * @return a list of list where the second lists contain Loads that refer to
     *         the same task.
     */
    private static List getTaskLoadsLists(List tasksLoadsList) {
        List res = new ArrayList();
        boolean createNewList = false;
        List l = null;
        Task prevTask = null;
        for (int i = 0; i < tasksLoadsList.size(); i++) {
            Load curLoad = (Load) tasksLoadsList.get(i);
            Task curTask = curLoad.refTask;

            createNewList = prevTask == null || prevTask != curTask;
            if (createNewList) {
                if (l != null)
                    res.add(l);
                l = new ArrayList();
            }

            l.add(curLoad);
            prevTask = curTask;

        }
        if (l != null && !l.isEmpty())
            res.add(l);

        return res;
    }

    private int calculateGap(ProjectResource resource) {
        return resource.getAssignments().length * getConfig().getRowHeight();
    }

    /**
     * Builds loads rectangles for each assignment in the resources table.
     * <code>tasksLoads</code> contains <code>Load</code> instances that
     * refer all to the same task (and so will be deisplayed on the same line).
     * Each <code>Load</code> instance has a reference task, startDelta,
     * endDelta and load value. start delta is to be consider from the time
     * frame start date, idem for end delta. The difference between the endDelta
     * and startDelta is the duration of this piece of load refering to the
     * represented task load.
     * 
     * @param tasksLoads
     *            List of loads (<code>Load</code> instances) that all refer
     *            to the same task
     * @param posY
     *            y position of the rectangles
     */
    private void buildTasksLoadsRectangles(List tasksLoads, int posY) {
        TimeUnit currentUnit = getChartModel().getBottomUnit();

        for (int i = 0; i < tasksLoads.size(); i++) {
            Load curLoad = (Load) tasksLoads.get(i);
            int loadX = (int) (curLoad.startDelta.getLength(currentUnit) * getChartModel()
                    .getBottomUnitWidth());
            float width = curLoad.endDelta.getLength(currentUnit)
                    * getChartModel().getBottomUnitWidth()
                    - curLoad.startDelta.getLength(currentUnit)
                    * getChartModel().getBottomUnitWidth();

            Rectangle nextRect = getPrimitiveContainer().createRectangle(loadX,
                    posY, (int)width, getConfig().getRowHeight());

            String style;
            if (curLoad.load < 100f)
                style = "load.underload";
            else if (curLoad.load > 100f)
                style = "load.overload";
            else
                style = "load.normal";
            style += ".first.last";
            nextRect.setStyle(style);
            nextRect.setModelObject(new ResourceLoad(curLoad.load));
        }
    }

    private List getLoadRectangles(List myLoads, Date realStart, int myPosY) {
        TimeUnit currentUnit = getChartModel().getBottomUnit();
        List result = new ArrayList(myLoads.size());
        // int startX = myPosX;
        String suffix = "";
        for (int i = 1; i < myLoads.size(); i++) {
            Load curLoad = (Load) myLoads.get(i);
            Load prevLoad = (Load) myLoads.get(i - 1);

            int prevEndX = (int) (curLoad.startDelta.getLength(currentUnit) * getChartModel()
                    .getBottomUnitWidth());
            // System.err.println("[LoadDistribution] getRectangles:
            // curLoad="+curLoad+" prevLoad="+prevLoad+" deltaX="+deltaX);
            // Rectangle prevRect = (Rectangle)
            // myResource2rect.remove(myResource);
            if (prevLoad.load > 0) {
                int prevStartX = (int) (prevLoad.startDelta
                        .getLength(currentUnit) * getChartModel()
                        .getBottomUnitWidth());
                int width = prevEndX - prevStartX;
                TaskLength visibleOffset = getChartModel().getTaskManager()
                        .createLength(
                                getChartModel().getTimeUnitStack()
                                        .getDefaultTimeUnit(), realStart,
                                getChartModel().getStartDate());
                prevStartX += (int) (visibleOffset.getLength(currentUnit) * getChartModel()
                        .getBottomUnitWidth());
                // System.err.println("[LoadDistribution] getRectangles():
                // realStart="+realStart+"
                // chartmodelstart="+getChartModel().getStartDate()+"
                // offset="+visibleOffset+" startx="+prevStartX);
                if (width > 0) {
                    Rectangle nextRect = getPrimitiveContainer()
                            .createRectangle(prevStartX, myPosY, width,
                                    getConfig().getRowHeight());
                    suffix += curLoad.load == 0 ? ".last" : "";
                    // if (prevRect==null) {
                    // suffix = ".first";
                    // }
                    // myResource2rect.put(myResource, nextRect);
                    // String style = (prevLoad.load<=100f ? "load.normal" :
                    // "load.overload")+suffix;

                    String style;
                    if (prevLoad.load < 100f)
                        style = "load.underload";
                    else if (prevLoad.load > 100f)
                        style = "load.overload";
                    else
                        style = "load.normal";
                    style += suffix;
                    nextRect.setStyle(style);
                    nextRect.setModelObject(new ResourceLoad(prevLoad.load));
                    result.add(nextRect);
                }
                suffix = "";

            } else if (curLoad.load > 0) {
                suffix = ".first";
                // prevRect.setStyle(prevRect.getStyle()+".last");
            }
            // startX = myPosX + deltaX;
        }
        return result;
    }

    private List getDayOffRectangles(List myDaysOff, Date realStart, int myPosY) {
        TimeUnit currentUnit = getChartModel().getBottomUnit();
        List result = new ArrayList(myDaysOff.size());
        for (int i = 1; i < myDaysOff.size(); i++) {
            Load curLoad = (Load) myDaysOff.get(i);
            Load prevLoad = (Load) myDaysOff.get(i - 1);
            int prevEndX = (int) (curLoad.startDelta.getLength(currentUnit) * getChartModel()
                    .getBottomUnitWidth());
            if (prevLoad.load > 0) {
                int prevStartX = (int) (prevLoad.startDelta
                        .getLength(currentUnit) * getChartModel()
                        .getBottomUnitWidth());
                int width = prevEndX - prevStartX;
                TaskLength visibleOffset = getChartModel().getTaskManager()
                        .createLength(
                                getChartModel().getTimeUnitStack()
                                        .getDefaultTimeUnit(), realStart,
                                getChartModel().getStartDate());
                prevStartX += (int) (visibleOffset.getLength(currentUnit) * getChartModel()
                        .getBottomUnitWidth());
                // System.err.println("[LoadDistribution] getRectangles():
                // realStart="+realStart+"
                // chartmodelstart="+getChartModel().getStartDate()+"
                // offset="+visibleOffset+" startx="+prevStartX);
                Rectangle nextRect = getPrimitiveContainer().createRectangle(
                        prevStartX, myPosY, width, getConfig().getRowHeight());
                nextRect.setStyle("dayoff");
                result.add(nextRect);
            }
        }
        return result;
    }
}
