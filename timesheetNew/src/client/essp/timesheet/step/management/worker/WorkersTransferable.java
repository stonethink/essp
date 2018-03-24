package client.essp.timesheet.step.management.worker;

import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;

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
public class WorkersTransferable  implements Transferable {
    public static final String MIME_TYPE =
        DataFlavor.javaSerializedObjectMimeType+"; class=c2s.essp.common.user.DtoUserBase";
    public WorkersTransferable(Object selObj) {
        this.tranObj = selObj;
    }

    public DataFlavor[] getTransferDataFlavors() {
        try {
            return new DataFlavor[] {
                new DataFlavor(MIME_TYPE)
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
