package itf.treeTable;

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
public class TreeTableFactory {

    static final String implClassName =
            "server.essp.common.jspTreeTable.JspTreeTable";
    static Class implClass = null;

    public TreeTableFactory() {
    }

    public static ITreeTable create() {
        if (implClass == null) {
            try {
                implClass = Class.forName(implClassName);
            } catch (ClassNotFoundException ex) {
                return null;
            }
        }
        ITreeTable iObj = null;
        try {
            iObj = (ITreeTable) implClass.newInstance();
        } catch (IllegalAccessException ex1) {
        } catch (InstantiationException ex1) {
        }
        return iObj;
    }

}
