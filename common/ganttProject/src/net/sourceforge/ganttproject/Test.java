package net.sourceforge.ganttproject;

import net.sourceforge.ganttproject.GanttProject;
import javax.swing.JFrame;
import c2s.dto.ITreeNode;
import com.wits.util.comDate;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.event.ChangeEvent;
import net.sourceforge.ganttproject.language.GanttLanguage;
import net.sourceforge.ganttproject.task.TaskNode;
import net.sourceforge.ganttproject.task.algorithm.AdjustTaskBoundsAlgorithm;
import net.sourceforge.ganttproject.task.algorithm.
        RecalculateTaskCompletionPercentageAlgorithm;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class Test {
    private JFrame f;
    GanttProject project;
    public Test() {
        f = new JFrame("Test");
        project = new GanttProject(false, false);
//        WitsGanttChartModel model=(WitsGanttChartModel)project.getArea().getMyChartModel();
//        model.setRoot(Test.getStaticRoot());
        f.getContentPane().add(project.getArea());
        f.setSize(800, 600);
        f.setVisible(true);
        refreshAreaByModel(project);

    }

    public static void main(String[] args) {
        Test t = new Test();
//        t.refreshAreaByModel();
    }

//    public static ITreeNode getStaticRoot() {
//        DtoWbsActivity dto1 = new DtoWbsActivity();
//        dto1.setName("Test1");
//        dto1.setPlannedStart(comDate.toDate("20060901", "yyyyMMdd"));
//        dto1.setPlannedFinish(comDate.toDate("20060930", "yyyyMMdd"));
//        DtoWbsActivity dto2 = new DtoWbsActivity();
//        dto2.setName("Test2");
//        dto2.setPlannedStart(comDate.toDate("20060906", "yyyyMMdd"));
//        dto2.setPlannedFinish(comDate.toDate("20060920", "yyyyMMdd"));
//
//        ITreeNode root = new DtoWbsActivityTreeNode(dto1);
//        root.addChild(new DtoWbsActivityTreeNode(dto2));
//        return root;
//    }

    public void refreshAreaByModel(GanttProject project) {

        project.getTabs().setSelectedIndex(0);
        int index = -1;
        MutableTreeNode selectedNode = (MutableTreeNode) Mediator
                                       .getGanttProjectSingleton().getTree().
                                       getSelectedNode();
        if (selectedNode != null) {
            DefaultMutableTreeNode parent1 = (DefaultMutableTreeNode)
                                             selectedNode.getParent();
            index = parent1.getIndex(selectedNode) + 1;
            project.getTree().getTreeTable().getTree().setSelectionPath(
                    new TreePath(parent1.getPath()));
            project.getTree().getTreeTable().getTreeTable().editingStopped(
                    new ChangeEvent(project.getTree().getTreeTable().
                                    getTreeTable()));
        }

        GanttCalendar cal = new GanttCalendar(project.getArea().getViewState()
                                              .getStartDate());

        DefaultMutableTreeNode node = project.getTree().getSelectedNode();
        GanttLanguage lang = GanttLanguage.getInstance();
        String nameOfTask = project.getOptions().getTaskNamePrefix(); // language.getText("newTask");

        //开始添加任务
        GanttTask task = project.getTaskManager().createTask();
        task.setStart(cal);
        task.setLength(8);
        project.getTaskManager().registerTask(task); // create a new task in the tab
        // paneneed to register it
        task.setName("Test1");
//        task.setCompletionPercentage(60);
        task.setColor(project.getArea().getTaskColor());
        TaskNode taskNode = project.getTree().addObject(task, node, index);

//        GanttTask task2 = project.getTaskManager().createTask();
//        task2.setStart(cal);
//        task2.setLength(10);
//        project.getTaskManager().registerTask(task2); // create a new task in the tab
//        // paneneed to register it
//        task2.setName("Test2");
////        task2.setCompletionPercentage(70);
//        task2.setColor(project.getArea().getTaskColor());
//        TaskNode taskNode2 = project.getTree().addObject(task2, taskNode, -1);

        /*
         * this will add new custom columns to the newly created task.
         */
        Mediator.getCustomColumnsStorage().processNewTask(task);

        AdjustTaskBoundsAlgorithm alg = project.getTaskManager()
                                        .getAlgorithmCollection().
                                        getAdjustTaskBoundsAlgorithm();
        alg.run(task);
        RecalculateTaskCompletionPercentageAlgorithm alg2 = project.
                getTaskManager()
                .getAlgorithmCollection()
                .getRecalculateTaskCompletionPercentageAlgorithm();
        alg2.run(task);
        project.getArea().repaint();
        project.setAskForSave(true);
        project.getUIFacade().setStatusText(project.getLanguage().getText(
                "createNewTask"));
        if (project.getOptions().getAutomatic()) {
            project.propertiesTask();
        }
        // setQuickSave(true);
//        project.getTree().setEditingTask(task);
        project.repaint2();

    }


}
