package net.sourceforge.ganttproject.importer;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import net.sourceforge.ganttproject.GanttProject;
import net.sourceforge.ganttproject.document.Document;
import net.sourceforge.ganttproject.document.FileDocument;
import net.sourceforge.ganttproject.gui.GanttDialogInfo;
import net.sourceforge.ganttproject.gui.UIFacade;
import net.sourceforge.ganttproject.io.GanttXMLOpen;
import net.sourceforge.ganttproject.language.GanttLanguage;
import net.sourceforge.ganttproject.parser.AllocationTagHandler;
import net.sourceforge.ganttproject.parser.BlankLineTagHandler;
import net.sourceforge.ganttproject.parser.DependencyTagHandler;
import net.sourceforge.ganttproject.parser.HolidayTagHandler;
import net.sourceforge.ganttproject.parser.ResourceTagHandler;
import net.sourceforge.ganttproject.parser.RoleTagHandler;
import net.sourceforge.ganttproject.parser.TaskTagHandler;
import net.sourceforge.ganttproject.parser.VacationTagHandler;
import net.sourceforge.ganttproject.resource.HumanResourceManager;
import net.sourceforge.ganttproject.resource.ResourceManager;
import net.sourceforge.ganttproject.roles.RoleManager;
import net.sourceforge.ganttproject.roles.RoleManagerImpl;
import net.sourceforge.ganttproject.task.TaskManager;

public class ImporterFromGanttFile extends ImporterBase implements Importer {

    private UIFacade myUIFacade;

	public String getFileNamePattern() {
        return "xml|gan";
    }

    public String getFileTypeDescription() {
        return GanttLanguage.getInstance().getText("ganttprojectFiles");
    }

    public void run(GanttProject project, final UIFacade uiFacade, File selectedFile) {
        final boolean bMerge = acceptImport(project);
        myUIFacade = project.getUIFacade();
		final GanttProject fproject = project;
		final Document document = getDocument(selectedFile);
		project.getUndoManager().undoableEdit("Import",
                new Runnable() {
					public void run() {
						openDocument(fproject, uiFacade, document, bMerge);
					}
				});
    }

    protected Document getDocument(File selectedFile) {
        return new FileDocument(selectedFile);
    }

    protected void openDocument(GanttProject project, UIFacade uiFacade, Document document,
            boolean bMerge) {
        try {
            TaskManager taskManager = project.getTaskManager().emptyClone();
            // ResourceManager resourceManager =
            // project.getHumanResourceManager();
            ResourceManager resourceManager = new HumanResourceManager(project
                    .getRoleManager().getDefaultRole());
            RoleManager roleManager = new RoleManagerImpl();
            GanttXMLOpen opener = new GanttXMLOpen(project.prjInfos, project
                    .getArea(), taskManager, project.getUIFacade());
            opener.addTagHandler(opener.getDefaultTagHandler());
            TaskTagHandler taskHandler = new TaskTagHandler(taskManager, opener
                    .getContext());
            opener.addTagHandler(taskHandler);
            BlankLineTagHandler blankLineHandler = new BlankLineTagHandler(project.getUIFacade().getGanttChart());
            opener.addTagHandler(blankLineHandler);
            ResourceTagHandler resourceHandler = new ResourceTagHandler(
                    resourceManager, roleManager, project.getResourceCustomPropertyManager());
            opener.addTagHandler(resourceHandler);
            DependencyTagHandler dependencyHandler = new DependencyTagHandler(
                    opener.getContext(), taskManager, myUIFacade);
            opener.addTagHandler(dependencyHandler);
            RoleTagHandler roleHandler = new RoleTagHandler(roleManager);
            opener.addTagHandler(roleHandler);
            opener.addParsingListener(dependencyHandler);
            opener.addParsingListener(resourceHandler);
            AllocationTagHandler allocationHandler = new AllocationTagHandler(
                    resourceManager, taskManager, roleManager);
            opener.addTagHandler(allocationHandler);
            opener.addParsingListener(allocationHandler);
            VacationTagHandler vacationHandler = new VacationTagHandler(
                    resourceManager);
            opener.addTagHandler(vacationHandler);

            //
            if (bMerge == false) {
                HolidayTagHandler holidayHandler = new HolidayTagHandler(
                        project);

                opener.addTagHandler(holidayHandler);
                opener.addParsingListener(holidayHandler);
            } else {
                opener.isMerging(true);
            }
            opener.load(document.getInputStream());

            project.getRoleManager().importData(roleManager);
            Map original2ImportedResource = project.getHumanResourceManager()
                    .importData(resourceManager);
            Map original2ImportedTask = project.getTaskManager().importData(
                    taskManager);
            project.getTaskManager().importAssignments(taskManager,
                    project.getHumanResourceManager(), original2ImportedTask,
                    original2ImportedResource);
            project.getResourcePanel().getResourceTreeTableModel()
                    .updateResources();
        } catch (IOException e) {
            uiFacade.showErrorDialog(e);
        }

    }
}
