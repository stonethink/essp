package client.framework.view.vwcomp;

import java.awt.dnd.*;
import java.awt.Component;

/**
 * <p>Title: VWJTableDragSource</p>
 *
 * <p>Description: ΪComponent�д���DragSourceʹ��������¼�</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public class VWDragSource implements DragGestureListener {

    //ע����ϵ�Table
    protected Component comp;

    protected VWDragSource(Component c) {
        this.comp = c;
    }

    /**
     * ע�����¼���Ӧ
     * @return DragSource ������ DragSourceListener
     */
    protected DragSource create() {
        DragSource dragSource = DragSource.getDefaultDragSource();

        dragSource.createDefaultDragGestureRecognizer(
                comp, DnDConstants.ACTION_COPY_OR_MOVE, this);
        return dragSource;
    }

    /**
     * ��һ��VWJTableע��ΪDragSource
     * @param table VWJTable
     * @return VWJTableDragSource
     */
    public static DragSource RegisterDragSource(Component c) {
        return new VWDragSource(c).create();
    }

    /**
     * ��ȡҪ�Ϸŵ����ݣ��ɸ���������أ�������Ӧ����
     * @return Object
     */
    protected Object getDragData() {
        if(comp instanceof VWJTable) {
            return ((VWJTable)comp).getSelectedData();
        } else if(comp instanceof VWJTreeTable){
            return ((VWJTreeTable)comp).getSelectedNode();
        } else if(comp instanceof IVWComponent) {
            return ((IVWComponent)comp).getUICValue();
        } else {
            return null;
        }
    }

    /**
     * ʵ��DragGestureListener�ķ���
     *    ��ʼ���϶���
     * @param event DragGestureEvent
     */
    public void dragGestureRecognized(DragGestureEvent event) {
        //dragCursor ���� null��ʹ��Ĭ�϶�̬Cursor
        event.startDrag(null, new VWDataTransferable(getDragData()));
    }
}



