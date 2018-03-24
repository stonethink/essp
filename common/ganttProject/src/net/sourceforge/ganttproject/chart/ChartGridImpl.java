package net.sourceforge.ganttproject.chart;

import java.awt.Color;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import sun.security.action.GetBooleanAction;

import net.sourceforge.ganttproject.GanttCalendar;
import net.sourceforge.ganttproject.calendar.GPCalendar;
import net.sourceforge.ganttproject.chart.ChartModelBase.Offset;
import net.sourceforge.ganttproject.chart.GraphicPrimitiveContainer.Line;
import net.sourceforge.ganttproject.gui.UIConfiguration;
import net.sourceforge.ganttproject.gui.options.model.BooleanOption;
import net.sourceforge.ganttproject.gui.options.model.DefaultBooleanOption;
import net.sourceforge.ganttproject.gui.options.model.GP1XOptionConverter;
import net.sourceforge.ganttproject.gui.options.model.GPOption;
import net.sourceforge.ganttproject.gui.options.model.GPOptionGroup;
import net.sourceforge.ganttproject.time.TimeFrame;
import net.sourceforge.ganttproject.time.TimeUnit;
import net.sourceforge.ganttproject.time.gregorian.FramerImpl;
import c2s.essp.common.calendar.WrokCalendarFactory;
import c2s.essp.common.calendar.WorkCalendar;
import java.util.List;
import javax.swing.tree.DefaultMutableTreeNode;
import net.sourceforge.ganttproject.task.Task;
import net.sourceforge.ganttproject.GanttProject;

/**
 * Created by IntelliJ IDEA.
 */
public class ChartGridImpl extends ChartRendererBase implements TimeUnitVisitor {
    private int myPosX;

    private TimeFrame myCurrentFrame;

    private boolean areUnitsAccepted;

    private TimeUnit myCurrentUnit;

    private Date myToday;

    private final BooleanOption myRedlineOption;
    private final BooleanOption myProjectDatesOption;

    private GPOptionGroup myOptions;

    private Date projectEnd = null;

    private Date projectStart = null;

    private FramerImpl myDayFramer = new FramerImpl(Calendar.DAY_OF_MONTH);

    private GanttProject project;

    public ChartGridImpl(ChartModelBase chartModel,
                         final UIConfiguration projectConfig) {
        super(chartModel);
        myRedlineOption = projectConfig.getRedlineOption();
        myProjectDatesOption = projectConfig.getProjectBoundariesOption();
        myOptions = new ChartOptionGroup("ganttChartGridDetails",
                                         new GPOption[] {myRedlineOption,
                                         myProjectDatesOption},
                                         chartModel.getOptionEventDispatcher());

    }

    GPOptionGroup getOptions() {
        return myOptions;
    }

    public void beforeProcessingTimeFrames() {
        getPrimitiveContainer().clear();
        myPosX = 0;
        myToday = myDayFramer.adjustLeft(GregorianCalendar.getInstance().
                                         getTime());

        projectStart = getChartModel().getTaskManager().getProjectStart();
        projectEnd = getChartModel().getTaskManager().getProjectEnd();

    }

    public void startTimeFrame(TimeFrame timeFrame) {
        myCurrentFrame = timeFrame;
    }

    public void endTimeFrame(TimeFrame timeFrame) {
        myCurrentFrame = null;
    }

    public void startUnitLine(TimeUnit timeUnit) {
        if (timeUnit == myCurrentFrame.getBottomUnit()) {
            areUnitsAccepted = true;
            myCurrentUnit = timeUnit;
        }
    }

    public void endUnitLine(TimeUnit timeUnit) {
        areUnitsAccepted = false;
        myCurrentUnit = null;
    }

    public void nextTimeUnit(int unitIndex) {
        if (areUnitsAccepted) {
//            if (myRedlineOption.isChecked()
//                    && myCurrentFrame.getUnitStart(myCurrentUnit, unitIndex)
//                            .compareTo(myToday) <= 0
//                    && myCurrentFrame.getUnitFinish(myCurrentUnit, unitIndex)
//                            .compareTo(myToday) > 0) {
//
//            }
            Date unitStart = myCurrentFrame.getUnitStart(myCurrentUnit,
                unitIndex);
            DayTypeAlternance[] alternance = getChartModel()
                                             .getDayTypeAlternance(
                                                 myCurrentFrame, myCurrentUnit,
                                                 unitIndex);
            Offset[] offsets = getChartModel().calculateOffsets(myCurrentFrame,
                myCurrentUnit, unitStart,
                getChartModel().getTimeUnitStack().getDefaultTimeUnit(),
                getChartModel().getBottomUnitWidth());
            if (myRedlineOption.isChecked()) {
                //改为自己的方法画线
                drawProcessLine(unitStart, unitIndex, myToday, offsets,
                                Color.RED, 2);
            }
            if (isProjectBoundariesOptionOn()) {
                drawDateLine(unitStart, unitIndex, projectStart, offsets,
                             Color.BLUE, 0);
                drawDateLine(unitStart, unitIndex, projectEnd, offsets,
                             Color.BLUE, 0);
            }
            //int posX = myPosX;

//            float delta = (float) getChartModel().getBottomUnitWidth()
//                    / (float) alternance.length;
//            int width=0;
            int prevOffset = 0;
            //DayTypeAlternance next=null;
            for (int i = 0; i < alternance.length; i++) {
                DayTypeAlternance next = alternance[i];
                int alternanceEndOffset = getChartModel().getBottomUnitWidth();
                for (int j = 0; j < offsets.length; j++) {
                    if (offsets[j].getOffsetEnd().equals(next.getEnd())) {
                        alternanceEndOffset = offsets[j].getOffsetPixels();
                        break;
                    }
                }
                //System.err.println("[ChartGridImpl] nextTimeUnit(): lternance="+next);
                //int width = (int)(next.getDuration().getLength(myCurrentUnit)*getChartModel().getBottomUnitWidth());

//                posX = (int) (myPosX + delta * i);
//                nextPosX = i < alternance.length - 1 ? (int) (myPosX + delta
//                        * (i + 1))
//                        : myPosX + getChartModel().getBottomUnitWidth();
                //width = nextPosX - posX;
                Calendar cal = Calendar.getInstance();
                cal.setTime(next.getEnd());
                GanttCalendar ganttCal = new GanttCalendar(next.getEnd());
                ganttCal.add( -1);
                cal.setTime(ganttCal.getTime());
                WorkCalendar wc = WrokCalendarFactory.clientCreate();
                if (!wc.isWorkDay(cal)) {
                    //原来的判断条件为此
//                if (GPCalendar.DayType.WEEKEND == next.getDayType()) {
                    //System.err.println("[ChartGridImpl] nextTimeUnit(): prevOffset="+prevOffset+" endOffset="+alternanceEndOffset);
                    //System.err.println("[ChartGridImpl] nextTimeUnit(): end="+next.getEnd()+" offset="+alternanceEndOffset+" bottom width="+getChartModel().getBottomUnitWidth());
                    GraphicPrimitiveContainer.Rectangle r =
                        getPrimitiveContainer()
                        .createRectangle(myPosX + prevOffset, 0,
                                         alternanceEndOffset - prevOffset,
                                         getHeight());
                    r.setBackgroundColor(getConfig()
                                         .getHolidayTimeBackgroundColor());
                    r.setStyle("calendar.holiday");
                    getPrimitiveContainer().bind(r, next.getDayType());
                }

                if (GPCalendar.DayType.HOLIDAY == next.getDayType()) {
                    GraphicPrimitiveContainer.Rectangle r =
                        getPrimitiveContainer()
                        .createRectangle(myPosX + prevOffset, 0,
                                         alternanceEndOffset, getHeight());
                    r.setBackgroundColor(getConfig()
                                         .getPublicHolidayTimeBackgroundColor());
                    r.setStyle("calendar.holiday");
                    getPrimitiveContainer().bind(r, next.getDayType());
                }
                prevOffset = alternanceEndOffset;
                //posX = myPosX+width;
            }
            //
            myPosX += getChartModel().getBottomUnitWidth();
        }
    }

    public void afterProcessingTimeFrames() {
    }

    private void drawDateLine(Date unitStart, int unitIndex, Date lineDate,
                              Offset[] offsets, Color color,
                              int horizontalOffset) {
        int redLineOffset = -1;
        if (unitStart.before(lineDate) &&
            myCurrentFrame.getUnitFinish(myCurrentUnit,
                                         unitIndex).after(lineDate)) {
            for (int i = 0; i < offsets.length; i++) {
                if (offsets[i].getOffsetEnd().equals(lineDate)) {
                    redLineOffset = offsets[i].getOffsetPixels();
                    break;
                }
            }
        } else if (unitStart.equals(lineDate)) {
            redLineOffset = 0;
        }
        if (redLineOffset >= 0) {
            Line redLine = getPrimitiveContainer().createLine(myPosX +
                redLineOffset + horizontalOffset,
                0, myPosX + redLineOffset + horizontalOffset, getHeight());
            redLine.setForegroundColor(color);
        }

    }

    private boolean isProjectBoundariesOptionOn() {
        return myProjectDatesOption.isChecked();
    }

    public void setProject(GanttProject project) {
        this.project = project;
    }

    //专门画进度线的方法
    public void drawProcessLine(Date unitStart, int unitIndex, Date lineDate,
                                Offset[] offsets, Color color,
                                int horizontalOffset) {
        int redLineOffset = -1;
        if (unitStart.before(lineDate)
            && myCurrentFrame.getUnitFinish(myCurrentUnit, unitIndex)
            .after(lineDate)) {
            for (int i = 0; i < offsets.length; i++) {
                if (offsets[i].getOffsetEnd().equals(lineDate)) {
                    redLineOffset = offsets[i].getOffsetPixels();
                    break;
                }
            }
        } else if (unitStart.equals(lineDate)) {
            redLineOffset = 0;
        }

        if (redLineOffset >= 0) {
            List nodes = project.getTree().getAllVisibleNodes();
            String unitType = this.myCurrentUnit.getName();
            // System.out.println("unitType:"+unitType);
            int columnWidth = this.getChartModel().getBottomUnitWidth();
            if (unitType.equals("week")) {
                columnWidth /= 7;
            }
            for (int i = 1; i < nodes.size(); i++) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) nodes
                                              .get(i);
                Task task = (Task) node.getUserObject();
                Date taskStart = task.getStart().getTime();
                Date taskEnd = task.getEnd().getTime();
                if (taskStart.before(taskEnd)) {
                    // 正常情况，有时间，且时间正常
                    int compRate = task.getCompletionPercentage();
                    if (taskEnd.before(lineDate)) {
                        if (compRate == 100) {
                            Line redLine = getPrimitiveContainer().createLine(
                                myPosX + redLineOffset + horizontalOffset,
                                (i - 1) * 18,
                                myPosX + redLineOffset + horizontalOffset,
                                i * 18);
                            redLine.setForegroundColor(color);

                        } else {
                            Long temp = (lineDate.getTime() - taskEnd.getTime()) /
                                        24 / 3600 / 1000;
                            Long daysTemp = taskEnd.getTime()
                                            - taskStart.getTime();
                            daysTemp = daysTemp / 24 / 3600 / 1000;
                            temp = temp + daysTemp * (100 - compRate) / 100;
                            Long offset = temp * columnWidth;

                            Line redLineUp = getPrimitiveContainer()
                                             .createLine(
                                                 myPosX + redLineOffset
                                                 + horizontalOffset,
                                                 (i - 1) * 18,
                                                 myPosX + redLineOffset
                                                 + horizontalOffset
                                                 - offset.intValue(),
                                                 (i - 1) * 18 + 9);
                            Line redLineDown = getPrimitiveContainer()
                                               .createLine(
                                myPosX + redLineOffset
                                + horizontalOffset
                                - offset.intValue(),
                                i * 18 - 9,
                                myPosX + redLineOffset
                                + horizontalOffset, i * 18);
                            redLineUp.setForegroundColor(color);
                            redLineDown.setForegroundColor(color);
                        }
                    } else if (taskEnd.after(lineDate)
                               && taskStart.before(lineDate)) {
                        // 任务结束时间大于当前时间，任务开始时间小于当前时间
                        Long temp = (lineDate.getTime() - taskStart.getTime()) /
                                    24 / 3600 / 1000;
                        Long daysTemp = taskEnd.getTime() - taskStart.getTime();
                        daysTemp = daysTemp / 24 / 3600 / 1000;
                        temp -= daysTemp * compRate / 100;
                        Long offset = temp * columnWidth;

                        Line redLineUp = getPrimitiveContainer().createLine(
                            myPosX + redLineOffset + horizontalOffset,
                            (i - 1) * 18,
                            myPosX + redLineOffset + horizontalOffset
                            - offset.intValue(), (i - 1) * 18 + 9);
                        Line redLineDown = getPrimitiveContainer().createLine(
                            myPosX + redLineOffset + horizontalOffset
                            - offset.intValue(), i * 18 - 9,
                            myPosX + redLineOffset + horizontalOffset,
                            i * 18);
                        redLineUp.setForegroundColor(color);
                        redLineDown.setForegroundColor(color);
                    } else if (lineDate.before(taskStart)) {
                        //第三类，任务在当前时间后
                        if (compRate == 0) {
                            Line redLine = getPrimitiveContainer().createLine(
                                myPosX + redLineOffset + horizontalOffset,
                                (i - 1) * 18,
                                myPosX + redLineOffset + horizontalOffset,
                                i * 18);
                            redLine.setForegroundColor(color);

                        } else {
                            //如果开始做了，表示任务已经提前进行了
                            Long temp = (taskStart.getTime() - lineDate
                                         .getTime()) / 24 / 3600 / 1000;
                            Long daysTemp = taskEnd.getTime()
                                            - taskStart.getTime();
                            daysTemp = daysTemp / 24 / 3600 / 1000;
                            temp = temp + daysTemp * compRate / 100;
                            Long offset = temp * columnWidth;

                            Line redLineUp = getPrimitiveContainer()
                                             .createLine(
                                                 myPosX + redLineOffset
                                                 + horizontalOffset,
                                                 (i - 1) * 18,
                                                 myPosX + redLineOffset
                                                 + horizontalOffset
                                                 + offset.intValue(),
                                                 (i - 1) * 18 + 9);
                            Line redLineDown = getPrimitiveContainer()
                                               .createLine(
                                myPosX + redLineOffset
                                + horizontalOffset
                                + offset.intValue(),
                                i * 18 - 9,
                                myPosX + redLineOffset
                                + horizontalOffset, i * 18);
                            redLineUp.setForegroundColor(color);
                            redLineDown.setForegroundColor(color);
                        }

                    }

                } else {
                    // 开始时间大于结束时间，不正常，不画线
                    Line redLine = getPrimitiveContainer().createLine(
                        myPosX + redLineOffset + horizontalOffset,
                        (i - 1) * 18,
                        myPosX + redLineOffset + horizontalOffset,
                        i * 18);
                    redLine.setForegroundColor(color);

                }
            }

        }

    }

}
