package client.framework.view.vwcomp;

import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;

/**
 * <p>Title: VWJTableDataTransferable</p>
 *
 * <p>Description: 为VWJTable拖放动作传输数据的类</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public class VWDataTransferable implements Transferable {
    //要传输的对象
    private Object tranObj;

    public VWDataTransferable(Object selObj) {
        this.tranObj = selObj;
    }

    /**
     * Returns an array of DataFlavor objects indicating the flavors the data
     * can be provided in.  The array should be ordered according to preference
     * for providing the data (from most richly descriptive to least descriptive).
     * @return DataFlavor[] data flavors in which this data can be transferred
     */
    public DataFlavor[] getTransferDataFlavors() {
        try {
            return new DataFlavor[] {
                new DataFlavor(getMimeType())
            };
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * /**
     * Returns whether or not the specified data flavor is supported for
     * this object.
     * @param flavor DataFlavor the requested flavor for the data
     * @return booleanindicating whether or not the data flavor is supported
     */
    public boolean isDataFlavorSupported(DataFlavor flavor) {
        return flavor.isMimeTypeEqual(getMimeType());
    }

    /**
     * Returns an object which represents the data to be transferred.  The class
     * of the object returned is defined by the representation class of the flavor.
     * @param flavor DataFlavor
     * @return Object
     * @throws UnsupportedFlavorException
     */
    public Object getTransferData(DataFlavor flavor) throws
        UnsupportedFlavorException {
        if (isDataFlavorSupported(flavor)) {
            return tranObj;
        } else {
            throw new UnsupportedFlavorException(flavor);
        }
    }

    /**
     * 获取当前对象的 MIME type
     * @return String the string used to identify the MIME type for this flavor
     */
    private String getMimeType() {
        if (tranObj == null) {
            return getMimeType(null);
        } else {
            return getMimeType(tranObj.getClass());
        }
    }

    /**
     * 根据传入的类，生成相应的 MIME type
     * @param cls Class
     * @return String
     */
    public static String getMimeType(Class cls) {
        if(cls == null) {
            return DataFlavor.javaSerializedObjectMimeType;
        } else {
            return DataFlavor.javaSerializedObjectMimeType
                    + "; class=" + cls.getName();
        }
    }

}
