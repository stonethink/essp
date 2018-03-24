package client.essp.timesheet.step.management.worker;

import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.awt.dnd.DragSourceAdapter;
import java.awt.dnd.DragSourceDropEvent;

import c2s.essp.common.user.DtoUserBase;
import client.framework.view.vwcomp.VWJTable;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class WorkersDragSource {
    VWJTable table;

    public WorkersDragSource(VWJTable table) {
        this.table = table;
    }

    public DragSource create() {
        DragSource dragSource = DragSource.getDefaultDragSource();

        dragSource.createDefaultDragGestureRecognizer(
            table,
            DnDConstants.ACTION_COPY_OR_MOVE,
            new DragGestureListener() {
            public void dragGestureRecognized(DragGestureEvent event) {
                DtoUserBase item = (DtoUserBase) table.getSelectedData();
//                if( item == null ){
//                    return;
//                }
                String draggedValue = item.getUserLoginId()+";"+item.getUserName();
//                System.out.println("draggedValue[" + draggedValue + "]");
                Transferable transferable = new WorkersTransferable(item);
                event.startDrag(null, transferable, new WorkersDragSourceListener(draggedValue));
            }
        });

        return dragSource;
    }
}


class WorkersDragSourceListener extends DragSourceAdapter {
    String draggedValue;
    public WorkersDragSourceListener(String draggedValue){
        this.draggedValue = draggedValue;
    }

    public void dragDropEnd(DragSourceDropEvent event) {
        if (event.getDropSuccess()) {
            int action = event.getDropAction();
            if (action == DnDConstants.ACTION_MOVE ||
                action == DnDConstants.ACTION_COPY) {
//                System.out.println(draggedValue +
//                                   "will Change status to Accept");
            }
        }
    }
}
