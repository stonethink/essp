package net.sourceforge.ganttproject.action.task;

import java.util.List;

import net.sourceforge.ganttproject.GanttTask;
import net.sourceforge.ganttproject.IGanttProject;
import net.sourceforge.ganttproject.gui.GanttDialogProperties;
import net.sourceforge.ganttproject.gui.UIFacade;
import net.sourceforge.ganttproject.resource.HumanResourceManager;
import net.sourceforge.ganttproject.roles.RoleManager;
import net.sourceforge.ganttproject.task.TaskSelectionManager;

public class TaskPropertiesAction extends TaskActionBase {

	private RoleManager myRoleManager;
	private HumanResourceManager myHumanManager;
	private IGanttProject myProject;

	public TaskPropertiesAction(IGanttProject project, TaskSelectionManager selectionManager, UIFacade uiFacade) {
		super(project.getTaskManager(), selectionManager, uiFacade);
		myProject = project;
		myHumanManager = (HumanResourceManager) project.getHumanResourceManager();
		myRoleManager = project.getRoleManager();
	}

	protected boolean isEnabled(List selection) {
		return selection.size()==1;
	}

	protected void run(List/*<Task>*/ selection) throws Exception {
        //È¥µôµ¯³ö´°¿Ú
//        if (selection.size()!=1) {
//            return;
//        }
//		GanttTask[] tasks = new GanttTask[] {(GanttTask)selection.get(0)};
//        GanttDialogProperties pd = new GanttDialogProperties(tasks);

//        pd.show(getTaskManager(), myHumanManager, myRoleManager, getUIFacade());
//        if (pd.change) {
//            myProject.setModified(true);
////            setRowHeight(rowHeight);
////            getResourcePanel().getResourceTreeTableModel()
////                    .updateResources();
//        }

	}

	protected String getLocalizedName() {
		return getI18n("propertiesTask");
	}

	protected String getIconFilePrefix() {
		return "properties_";
	}

}
