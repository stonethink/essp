package client.essp.pms.modifyplan.popselect;

import java.awt.Point;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;

import c2s.essp.pms.wbs.DtoActivityWorker;
import c2s.essp.pms.wbs.DtoWbsActivity;
import c2s.essp.pms.wbs.DtoWbsTreeNode;
import client.essp.pms.modifyplan.VwBLPlanList;
import client.essp.pms.modifyplan.VwBaseLinePlanModify;
import client.framework.common.Constant;
import client.framework.view.common.comMSG;
import client.framework.view.vwcomp.VWJTreeTable;
import c2s.dto.IDto;

/**
 * <p>Title: </p>
 *
 * <p>Description:构造拖放的目标 </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class WbsActivityDrogTarget implements DropTargetListener {
    static final String actionCopyNode ="FBLPlanModifyCopyNodeAction";
    VwBLPlanList targetArea;
    VWJTreeTable treeTable;
    VwBaseLinePlanModify vwBaseLinePlanModify;
    private int lastRow = -1;
    //if drop worker
    private boolean workerFlag = false;

    public WbsActivityDrogTarget(VwBLPlanList targetArea,VwBaseLinePlanModify vwBaseLinePlanModify) {
        this.targetArea = targetArea;
        this.treeTable = targetArea.getTreeTable();
        this.vwBaseLinePlanModify = vwBaseLinePlanModify;
    }

    public DropTarget createDrogTarget() {
        return new DropTarget(treeTable, this);
    }

    /**
     * Called while a drag operation is ongoing, when the mouse pointer
     * enters the operable part of the drop site for the
     * <code>DropTarget</code> registered with this listener.
     *
     * @param dtde the <code>DropTargetDragEvent</code>
     * @todo Implement this java.awt.dnd.DropTargetListener method
     */
    public void dragEnter(DropTargetDragEvent event) {
        if (!isDragAcceptable(event)) {
            event.rejectDrag();
            return;
        }
    }

    /**
     * Called while a drag operation is ongoing, when the mouse pointer has
     * exited the operable part of the drop site for the
     * <code>DropTarget</code> registered with this listener.
     *
     * @param dte the <code>DropTargetEvent</code>
     * @todo Implement this java.awt.dnd.DropTargetListener method
     */
    public void dragExit(DropTargetEvent event) {
    }

    /**
     * Called when a drag operation is ongoing, while the mouse pointer is
     * still over the operable part of the drop site for the
     * <code>DropTarget</code> registered with this listener.
     *
     * @param dtde the <code>DropTargetDragEvent</code>
     * @todo Implement this java.awt.dnd.DropTargetListener method
     */
    public void dragOver(DropTargetDragEvent event) {
        Point p = event.getLocation();
        int dropRow = treeTable.rowAtPoint(p);
        if(lastRow == dropRow) {
            return;
        }
        lastRow = dropRow;
        if(dropRow < 0) {
            event.rejectDrag();
            return;
        }
        //获取目标节点
        DtoWbsTreeNode destWbsTreeNode =
            (DtoWbsTreeNode) treeTable.getTree()
            .getPathForRow(dropRow)
            .getLastPathComponent();
        //for test
        DtoWbsActivity destWbs =
            (DtoWbsActivity) destWbsTreeNode.getDataBean();
        if((destWbs.isReadonly() || (destWbs.isActivity() && !workerFlag))) {
            event.rejectDrag();
            return;
        }
        event.acceptDrag(event.getDropAction());
        if (!isDragAcceptable(event)) {
            event.rejectDrag();
        }
    }

    /**
     * Called when the drag operation has terminated with a drop on the
     * operable part of the drop site for the <code>DropTarget</code>
     * registered with this listener.
     *
     * @param dtde the <code>DropTargetDropEvent</code>
     * @todo Implement this java.awt.dnd.DropTargetListener method
     */
    public void drop(DropTargetDropEvent event) {

            if (!isDropAcceptable(event)) {
                event.rejectDrop();
                return;
            }

            int eventType = event.getDropAction();
            if ((eventType & DnDConstants.ACTION_MOVE) == 0) {
                return;
            }

            event.acceptDrop(event.getDropAction());
            Transferable transferable = event.getTransferable();
            Point p = event.getLocation();
            event.getSource();

            int dropRow = treeTable.rowAtPoint(p);

//        VMTreeTableModel model = (VMTreeTableModel) targetArea.getModel();
            DataFlavor[] flavors = transferable.getTransferDataFlavors();
            for (int i = 0; i < flavors.length; i++) {
                DataFlavor d = flavors[i];
                try {
                    if (d.isMimeTypeEqual(DataFlavor.
                                          javaSerializedObjectMimeType)) {
                        //获取拖动的源节点
                       Object srcObj = transferable.getTransferData(d);

                        //获取目标节点
                        DtoWbsTreeNode destWbsTreeNode =
                            (DtoWbsTreeNode) treeTable.getTree()
                            .getPathForRow(dropRow)
                            .getLastPathComponent();
                        //处理拖放的是TreeNode或是Worker
                        if(srcObj instanceof DtoWbsTreeNode) {
                           dropTreeNode((DtoWbsTreeNode) srcObj, destWbsTreeNode);
                       } else if(srcObj instanceof DtoActivityWorker) {
                           dropWorker((DtoActivityWorker) srcObj, destWbsTreeNode);
                       }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            event.dropComplete(true);
        }
        private void dropTreeNode(DtoWbsTreeNode srcTn, DtoWbsTreeNode destTn) {
            //提示是否保存数据，如果保存，则进入下步操作
            if (Constant.OK ==
                comMSG.dispDialogProcessYN("do you want to copy node?")) {

                //复制节点srcWbsTreeNode到目标节点destWbsTreeNode

                targetArea.copyNode(srcTn, destTn);
                vwBaseLinePlanModify.getPasteBtn().setEnabled(false);
                vwBaseLinePlanModify.getRefreshBtn().doClick();
                vwBaseLinePlanModify.refreshGraphic();
            } //end if(复制)
        }

        private void dropWorker(DtoActivityWorker srcWorker, DtoWbsTreeNode destTn) {
            DtoWbsActivity dto = (DtoWbsActivity) destTn.getDataBean();
            dto.setManager(srcWorker.getLoginId());
            dto.setOp(IDto.OP_MODIFY);
            this.treeTable.updateUI();
            vwBaseLinePlanModify.setSaveBtnsetEnabled(true);
        }

    /**
     * Called if the user has modified the current drop gesture.
     *
     * @param dtde the <code>DropTargetDragEvent</code>
     * @todo Implement this java.awt.dnd.DropTargetListener method
     */
    public void dropActionChanged(DropTargetDragEvent event) {
        if (!isDragAcceptable(event)) {
            event.rejectDrag();
            return;
        }

    }

    public boolean isDragAcceptable(DropTargetDragEvent event) {
        boolean acceptable = false;
        Transferable transferable = event.getTransferable();
        DataFlavor[] flavors = transferable.getTransferDataFlavors();
        if (flavors != null && flavors.length > 0
            && flavors[0].isMimeTypeEqual(DataFlavor.javaSerializedObjectMimeType)) {
            try {
                Object obj = transferable.getTransferData(flavors[0]);
                if (obj instanceof DtoWbsTreeNode) {
                    acceptable = true;
                    workerFlag = false;
                } else if(obj instanceof DtoActivityWorker) {
                    acceptable = true;
                    workerFlag = true;
                }

            } catch (Exception ex) {
            }
        }
        return ((event.getDropAction()
                 & DnDConstants.ACTION_COPY_OR_MOVE) != 0) && acceptable;

    }

    public boolean isDropAcceptable(DropTargetDropEvent event) {
        return (event.getDropAction()
                & DnDConstants.ACTION_COPY_OR_MOVE) != 0;
    }

}
