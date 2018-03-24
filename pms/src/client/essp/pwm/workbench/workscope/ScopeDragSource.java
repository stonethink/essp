package client.essp.pwm.workbench.workscope;

import client.framework.view.vwcomp.VWJTable;
import java.awt.dnd.DragSource;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragGestureEvent;
import java.awt.datatransfer.Transferable;
import c2s.essp.pwm.wp.IDtoScope;
import com.wits.util.StringUtil;
import java.awt.dnd.DragSourceAdapter;
import java.awt.dnd.DragSourceDropEvent;

public class ScopeDragSource {
    VWJTable table;

    public ScopeDragSource(VWJTable table) {
        this.table = table;
    }

    public DragSource create() {
        DragSource dragSource = DragSource.getDefaultDragSource();

        dragSource.createDefaultDragGestureRecognizer(
            table,
            DnDConstants.ACTION_COPY_OR_MOVE,
            new DragGestureListener() {
            public void dragGestureRecognized(DragGestureEvent event) {
                IDtoScope wkitem = (IDtoScope) table.getSelectedData();
                if( wkitem == null ){
                    return;
                }

                //wpid : wpname : projectid : projectname : activityid : activityname : pwpflag
                String draggedValue = StringUtil.nvl(wkitem.getWpRid()) + ";" +
                                      StringUtil.nvl(wkitem.getWpName())
                                      + ";" + StringUtil.nvl(wkitem.getAcntRid()) +
                                      ";" + StringUtil.nvl(wkitem.getAcntName())
                                      + ";" + StringUtil.nvl(wkitem.getAcntRid()) +
                                      ";" + StringUtil.nvl(wkitem.getActivityName())
                                      + ";" + wkitem.getScopeInfo()
                                      ;

                System.out.println("draggedValue[" + draggedValue + "]");

                Transferable transferable = new ScopeTransferable(wkitem);
                event.startDrag(null, transferable, new ScopeDragSourceListener(draggedValue));
            }
        });

        return dragSource;
    }
}


class ScopeDragSourceListener extends DragSourceAdapter {
    String draggedValue;
    public ScopeDragSourceListener(String draggedValue){
        this.draggedValue = draggedValue;
    }

    public void dragDropEnd(DragSourceDropEvent event) {
        if (event.getDropSuccess()) {
            int action = event.getDropAction();
            if (action == DnDConstants.ACTION_MOVE ||
                action == DnDConstants.ACTION_COPY) {
                System.out.println(draggedValue +
                                   "will Change status to Accept");
            }
        }
    }
}
