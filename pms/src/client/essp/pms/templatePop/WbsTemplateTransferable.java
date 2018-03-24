package client.essp.pms.templatePop;

import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.awt.datatransfer.DataFlavor;
import client.essp.pms.templatePop.WbsTemplateTransferable;
import java.awt.datatransfer.Transferable;

/**
 * <p>Title: </p>
 *
 * <p>Description: 用来传输template数据 </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author wuyi
 * @version 1.0
 */
public class WbsTemplateTransferable implements Transferable{
    private Object tranObj;

    public WbsTemplateTransferable(Object selObj) {
       this. tranObj=selObj;
    }

    public DataFlavor[] getTransferDataFlavors() {
        try {
           return new DataFlavor[] {
               new DataFlavor(DataFlavor.javaSerializedObjectMimeType+"; class=c2s.essp.pms.wbs.DtoWbsTreeNode")
           };
       } catch (ClassNotFoundException ex) {
           ex.printStackTrace();
           return null;
       }

    }

    public boolean isDataFlavorSupported(DataFlavor flavor) {
        return  flavor.equals(DataFlavor.stringFlavor);
    }

    public Object getTransferData(DataFlavor flavor) throws
        UnsupportedFlavorException, IOException {
        if (flavor.isMimeTypeEqual(DataFlavor.javaSerializedObjectMimeType))
           return tranObj;
       else
           throw new UnsupportedFlavorException(flavor);

    }
}

