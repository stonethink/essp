package client.essp.pms.activity.wp;

import java.awt.dnd.DragSourceAdapter;
import client.framework.view.vwcomp.VWJTable;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DnDConstants;
import java.awt.datatransfer.Transferable;
import c2s.essp.pwm.wp.IDtoScope;
import com.wits.util.StringUtil;
import java.awt.dnd.DragSourceDropEvent;
import c2s.essp.pms.wbs.DtoActivityWorker;

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
                DtoActivityWorker wkritem = (DtoActivityWorker) table.getSelectedData();
                if( wkritem == null ){
                    return;
                }

                String draggedValue = StringUtil.nvl(wkritem.getAcntRid()) + ";" +
                                      StringUtil.nvl(wkritem.getActivityRid())
                                      + ";" + StringUtil.nvl(wkritem.getLoginId()) +
                                      ";" + StringUtil.nvl(wkritem.getEmpName())
                                      + ";" + StringUtil.nvl(wkritem.getRid()) +
                                      ";" + StringUtil.nvl(wkritem.getRoleName())
                                      ;

                System.out.println("draggedValue[" + draggedValue + "]");

                Transferable transferable = new WorkersTransferable(wkritem);
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
                System.out.println(draggedValue +
                                   "will Change status to Accept");
            }
        }
    }
}
