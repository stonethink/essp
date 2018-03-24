/*
 * Created on 13.10.2005
 */
package net.sourceforge.ganttproject.gui;

import net.sourceforge.ganttproject.IGanttProject;
import net.sourceforge.ganttproject.document.Document;
import net.sourceforge.ganttproject.gui.options.model.GPOptionGroup;

public interface ProjectUIFacade {
    void saveProject(IGanttProject project);
    void saveProjectAs(IGanttProject project);
    public void saveProjectRemotely(IGanttProject project);    
    void openProject(IGanttProject project);
    void openRemoteProject(IGanttProject project);
    void openProject(Document document, IGanttProject project);
    
    void createProject(IGanttProject project);
    GPOptionGroup getOptionGroup();
}
