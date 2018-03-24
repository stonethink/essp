package client.framework.view.vwcomp;

import java.awt.dnd.*;
import java.awt.Component;

/**
 * <p>Title: VWJTableDragSource</p>
 *
 * <p>Description: 为Component中创建DragSource使其接受拖事件</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public class VWDragSource implements DragGestureListener {

    //注册可拖的Table
    protected Component comp;

    protected VWDragSource(Component c) {
        this.comp = c;
    }

    /**
     * 注册拖事件响应
     * @return DragSource 可增加 DragSourceListener
     */
    protected DragSource create() {
        DragSource dragSource = DragSource.getDefaultDragSource();

        dragSource.createDefaultDragGestureRecognizer(
                comp, DnDConstants.ACTION_COPY_OR_MOVE, this);
        return dragSource;
    }

    /**
     * 给一个VWJTable注册为DragSource
     * @param table VWJTable
     * @return VWJTableDragSource
     */
    public static DragSource RegisterDragSource(Component c) {
        return new VWDragSource(c).create();
    }

    /**
     * 获取要拖放的数据，可根据情况重载，返回响应数据
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
     * 实现DragGestureListener的方法
     *    初始化拖动作
     * @param event DragGestureEvent
     */
    public void dragGestureRecognized(DragGestureEvent event) {
        //dragCursor 传入 null，使用默认动态Cursor
        event.startDrag(null, new VWDataTransferable(getDragData()));
    }
}



