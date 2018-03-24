package net.sourceforge.ganttproject.importer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.sourceforge.ganttproject.GanttProject;
import net.sourceforge.ganttproject.gui.GanttDialogInfo;
import net.sourceforge.ganttproject.gui.options.model.GPOptionGroup;

public class ImporterBase {
    
    private List myPreImporterProcessors = new ArrayList();
    
    protected boolean acceptImport(GanttProject ganttProject) {
        boolean bMerge = false;
        boolean accept = false;

        if (ganttProject.projectDocument != null
                || ganttProject.askForSave == true) {
            GanttDialogInfo gdi = new GanttDialogInfo(ganttProject,
                    GanttDialogInfo.QUESTION, GanttDialogInfo.YES_NO_OPTION,
                    ganttProject.getLanguage().getText("msg17"), ganttProject
                            .getLanguage().getText("question"));
            gdi.show();
            bMerge = (gdi.res == GanttDialogInfo.YES);
            accept = true;
        }
        else if(ganttProject.projectDocument == null)
            accept=true;

        if (!bMerge) {
            if (ganttProject.checkCurrentProject()) {
                ganttProject.close();
            }
        }

        return accept;
    }

    public String getFileTypeDescription() {
        return null;
    }

    public String getFileNamePattern() {
        return null;
    }
    
    public GPOptionGroup[] getSecondaryOptions() {
    	return new GPOptionGroup[0];
    }
    

}
