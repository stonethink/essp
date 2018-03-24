package client.essp.pwm.workbench.workscope;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;

/**
 *
 * <p>Title: </p>
 * <p>Description: 实现传送工作包信息的接口</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Enovation</p>
 * @author Xuxi.Chen
 * @version 1.0
 */
class ScopeTransferable implements Transferable {
    public ScopeTransferable(Object selObj) {
        this.tranObj = selObj;
    }

    public DataFlavor[] getTransferDataFlavors() {
        try {
            return new DataFlavor[] {
                new DataFlavor(DataFlavor.javaSerializedObjectMimeType+"; class=c2s.essp.pwm.wp.IDtoScope")
            };
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public boolean isDataFlavorSupported(DataFlavor flavor) {
        return flavor.equals(DataFlavor.stringFlavor);
    }

    public Object getTransferData(DataFlavor flavor) throws
        UnsupportedFlavorException {
        if (flavor.isMimeTypeEqual(DataFlavor.javaSerializedObjectMimeType))
            return tranObj;
        else
            throw new UnsupportedFlavorException(flavor);
    }

    private Object tranObj;

}
