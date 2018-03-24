package client.framework.view.vwcomp;


import java.awt.Component;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.*;

/**
 * <p>Title: VWDropTarget</p>
 *
 * <p>Description: 为Component中创建DropTarget使其接受放事件</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public abstract class VWDropTarget implements DropTargetListener {
    private Component comp;

    protected VWDropTarget(Component c) {
        comp = c;
    }

    protected DropTarget create() {
        return new DropTarget(comp, this);
    }

    /**
     * 拖放事件进入
     * @param event DropTargetDragEvent
     */
    public void dragEnter(DropTargetDragEvent event) {
        if (!isDragAcceptable(event)) {
            event.rejectDrag();
            return;
        }
    }

    /**
     * 拖放时间退出
     * @param event DropTargetEvent
     */
    public void dragExit(DropTargetEvent event) {
    }

    /**
     * 可以在此确定控件的那些地方能放，那些不能放。
     * @param event DropTargetDragEvent
     */
    public void dragOver(DropTargetDragEvent event) {
        // you can provide visual feedback here
    }

    public void dropActionChanged(DropTargetDragEvent event) {
        if (!isDragAcceptable(event)) {
            event.rejectDrag();
            return;
        }
    }

    /**
     * 执行“放”的动作
     * @param event DropTargetDropEvent
     */
    public void drop(DropTargetDropEvent event) {
        if (!isDropAcceptable(event)) {
            event.rejectDrop();
            return;
        }

        if ((event.getDropAction() & DnDConstants.ACTION_COPY_OR_MOVE) == 0) {
            return;
        }

        Object data = getDropData(event.getTransferable());
        if(data == null) {
            event.rejectDrop();
            return;
        }
        acceptData(event, data);

        event.dropComplete(true);
    }

    /**
     * 获取拖放的数据
     * @param event DropTargetDropEvent
     * @return Object
     */
    protected Object getDropData(Transferable transferable) {

        DataFlavor[] flavors = transferable.getTransferDataFlavors();
        for (int i = 0; i < flavors.length; i++) {
            DataFlavor d = flavors[i];
            try {
                if (d.isMimeTypeEqual(getMimeType())) {
                    Object data = transferable.getTransferData(d);
                    return data;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 接受数据
     * @param event DropTargetDropEvent
     * @param data Object
     */
    protected abstract void acceptData(DropTargetDropEvent event, Object data);

    /**
     * 检查拖入的事件是否正确
     * @param event DropTargetDragEvent
     * @return boolean
     */
    public boolean isDragAcceptable(DropTargetDragEvent event) {
        if ((event.getDropAction()
             & DnDConstants.ACTION_COPY_OR_MOVE) == 0) {
            return false;
        }
        Transferable transferable = event.getTransferable();
        DataFlavor[] flavors = transferable.getTransferDataFlavors();
        //没有数据
        if (flavors == null) {
            return false;
        }
        //如果有需要的数据，返回true
        for (DataFlavor flavor : flavors) {
            if (flavor.isMimeTypeEqual(getMimeType())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取当前对象的 MIME type
     * @return String
     */
    private String getMimeType() {
        return VWDataTransferable.getMimeType(getAcceptClass());
    }

    /**
     * 获取可接受的数据类型
     * @return Class
     */
    protected abstract Class getAcceptClass();

    private boolean isDropAcceptable(DropTargetDropEvent event) {
        // usually, you check the available data flavors here
        // in this program, we accept all flavors
        return (event.getDropAction()
                & DnDConstants.ACTION_COPY_OR_MOVE) != 0;
    }
}
