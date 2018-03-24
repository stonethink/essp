package net.sourceforge.ganttproject;

import net.sourceforge.ganttproject.*;
import net.sourceforge.ganttproject.gui.zoom.ZoomManager;
import net.sourceforge.ganttproject.task.TaskManager;
import net.sourceforge.ganttproject.undo.GPUndoManager;
import client.framework.model.VMColumnConfig;
import client.framework.view.vwcomp.VWJText;

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
public class WitsGanttGraphicArea extends GanttGraphicArea {
    Object[][] configs = null;

    public WitsGanttGraphicArea(GanttProject app, GanttTree2 ttree,
                                TaskManager taskManager,
                                ZoomManager zoomManager,
                                GPUndoManager undoManager) {
        super(app, ttree, taskManager, zoomManager, undoManager);

        configs = new Object[][] { {"Name", "name",
                  VMColumnConfig.EDITABLE, null}, {"Code", "code",
                  VMColumnConfig.EDITABLE, new VWJText(), Boolean.FALSE},
        };

        WitsGanttChartModel model = new WitsGanttChartModel(app.getTaskManager(),
                app.getTimeUnitStack(), app.getUIConfiguration(), null, configs,
                "name");
        this.setMyChartModel(model);

    }
}
