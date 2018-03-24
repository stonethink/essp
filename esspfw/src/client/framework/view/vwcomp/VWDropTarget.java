package client.framework.view.vwcomp;


import java.awt.Component;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.*;

/**
 * <p>Title: VWDropTarget</p>
 *
 * <p>Description: ΪComponent�д���DropTargetʹ����ܷ��¼�</p>
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
     * �Ϸ��¼�����
     * @param event DropTargetDragEvent
     */
    public void dragEnter(DropTargetDragEvent event) {
        if (!isDragAcceptable(event)) {
            event.rejectDrag();
            return;
        }
    }

    /**
     * �Ϸ�ʱ���˳�
     * @param event DropTargetEvent
     */
    public void dragExit(DropTargetEvent event) {
    }

    /**
     * �����ڴ�ȷ���ؼ�����Щ�ط��ܷţ���Щ���ܷš�
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
     * ִ�С��š��Ķ���
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
     * ��ȡ�Ϸŵ�����
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
     * ��������
     * @param event DropTargetDropEvent
     * @param data Object
     */
    protected abstract void acceptData(DropTargetDropEvent event, Object data);

    /**
     * ���������¼��Ƿ���ȷ
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
        //û������
        if (flavors == null) {
            return false;
        }
        //�������Ҫ�����ݣ�����true
        for (DataFlavor flavor : flavors) {
            if (flavor.isMimeTypeEqual(getMimeType())) {
                return true;
            }
        }
        return false;
    }

    /**
     * ��ȡ��ǰ����� MIME type
     * @return String
     */
    private String getMimeType() {
        return VWDataTransferable.getMimeType(getAcceptClass());
    }

    /**
     * ��ȡ�ɽ��ܵ���������
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
