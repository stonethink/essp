/*
 * Created on 30.04.2005
 */
package net.sourceforge.ganttproject.importer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.sourceforge.ganttproject.GanttProject;
import net.sourceforge.ganttproject.Mediator;
import net.sourceforge.ganttproject.gui.UIFacade;
import net.sourceforge.ganttproject.gui.projectwizard.WizardImpl;
import net.sourceforge.ganttproject.language.GanttLanguage;
import net.sourceforge.ganttproject.plugins.PluginManager;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;

/**
 * @author bard
 */
public class ImportFileWizardImpl extends WizardImpl {
    private State myState;

    private GanttProject myProject;

    public ImportFileWizardImpl(UIFacade uiFacade, GanttProject project) {
        super(uiFacade, GanttLanguage.getInstance().getText("importWizard.dialog.title"));
        myProject = project;
        myState = new State();
        Importer[] importers = getImporters();
        addPage(new ImporterChooserPage(importers, myState));
        addPage(new FileChooserPage(myState));
    }

    private Importer[] getImporters() {
        List result = new ArrayList();
        Importer[] importers = (Importer[]) Mediator.getPluginManager().getExtensions(Importer.EXTENSION_POINT_ID, Importer.class);
//        String extensionPointID = 
//        IConfigurationElement[] configElements = Platform
//                .getExtensionRegistry().getConfigurationElementsFor(
//                        extensionPointID);
        return importers;
    }

    protected void onOkPressed() {
        super.onOkPressed();
        //myProject.getTree().addBlankLine(null, myProject.getTree().getRoot().getChildCount());
        myState.myImporter.run(myProject, myProject.getUIFacade(), myState.getFile());
    }

    protected boolean canFinish() {
        return myState.myImporter != null && myState.getFile() != null;
    }

    class State {
        Importer myImporter;

        private File myFile;

        void setFile(File myFile) {
            this.myFile = myFile;
            ImportFileWizardImpl.this.adjustButtonState();
        }

        File getFile() {
            return myFile;
        }
    }
}
