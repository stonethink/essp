package client.essp.pms.templatePop;


import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.awt.dnd.DragSourceAdapter;
import java.awt.dnd.DragSourceDropEvent;

import c2s.essp.pms.wbs.DtoWbsTreeNode;
import client.framework.view.vwcomp.VWJTreeTable;

/**
 * <p>Title: </p>
 *
 * <p>Description: ππ‘ÏÕœ‘¥</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author wuyi
 * @version 1.0
 */
public class WbsTemplateDragSource {
    VWJTreeTable table;

     public WbsTemplateDragSource(VWJTreeTable table) {
         this.table = table;
     }

     public DragSource create() {
         DragSource dragSource = DragSource.getDefaultDragSource();

         dragSource.createDefaultDragGestureRecognizer(
             table,
             DnDConstants.ACTION_COPY_OR_MOVE,
             new DragGestureListener() {
             public void dragGestureRecognized(DragGestureEvent event) {
                 DtoWbsTreeNode template = (DtoWbsTreeNode) table.getSelectedNode();
                 if( template == null ){
                     return;
                 }

                 Transferable transferable = new WbsTemplateTransferable(template);
                 String drogValue = "null";
                 if(template.getDataBean() != null) {
                     drogValue = template.getDataBean().toString();
                 }
                 event.startDrag(null, transferable, new AcTemplateDragSourceListener(drogValue));
             }
         });

         return dragSource;
     }
 }


 class AcTemplateDragSourceListener extends DragSourceAdapter {
     String draggedValue;
     public AcTemplateDragSourceListener(String draggedValue){
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
