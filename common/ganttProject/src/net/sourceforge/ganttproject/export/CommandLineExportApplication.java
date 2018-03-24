package net.sourceforge.ganttproject.export;

import java.io.File;
import java.util.Arrays;

import org.eclipse.core.runtime.Platform;

import net.sourceforge.ganttproject.GanttProject;
import net.sourceforge.ganttproject.Mediator;
import net.sourceforge.ganttproject.gui.UIFacade;

public class CommandLineExportApplication {
    public boolean export(String[] cmdline) {
        if (cmdline.length<2) {
            return false;
        }
        if (cmdline[0].startsWith("-")) {
            Exporter exporter = findExporter(cmdline[0].toLowerCase().substring(1));
            if (exporter!=null) {
                GanttProject project = new GanttProject(false);
                project.openStartupDocument(cmdline[1]);
                ConsoleUIFacade consoleUI = new ConsoleUIFacade(project.getUIFacade());
                Platform.getJobManager().setProgressProvider(null);
                File inputFile = new File(cmdline[1]);
                if (false==inputFile.exists()) {
                    consoleUI.showErrorDialog("File "+cmdline[1]+" does not exist.");
                    return true;
                }
                if (false==inputFile.canRead()) {
                    consoleUI.showErrorDialog("File "+cmdline[1]+" is not readable.");
                    return true;                    
                }
                File outputFile;
                if (cmdline.length>2) {
                    outputFile = new File(cmdline[2]);
                }
                else {
                    outputFile = FileChooserPage.proposeOutputFile(project, exporter);
                }
                System.err
                .println("[CommandLineExportApplication] export(): exporting with "+exporter);
                exporter.setContext(project, consoleUI);
                if (exporter instanceof ExportFileWizardImpl.LegacyOptionsClient) {
                    ((ExportFileWizardImpl.LegacyOptionsClient)exporter).setOptions(project.getOptions());
                }
                try {
                	ExportFinalizationJob finalizationJob = new ExportFinalizationJob() {
						public void run(File[] exportedFiles) {
							System.exit(0);
						}
                	};
                    exporter.run(outputFile, finalizationJob);
                } catch (Exception e) {
                    consoleUI.showErrorDialog(e);
                }
                return true;
            }
        }
        return false;
    }
    
    private Exporter findExporter(String fileExtension) {
        Exporter[] exporters = Mediator.getPluginManager().getExporters();
        for (int i=0; i<exporters.length; i++) {
            Exporter next = exporters[i];
            if (Arrays.asList(next.getFileExtensions()).contains(fileExtension)) {
                return next;
            }
        }
        return null;
    }
}
