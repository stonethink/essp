package client.framework.view.vwcomp;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: Wistron ITS Wuhan SDC</p>
 *
 * @author not attributable
 * @version 1.0
 */
public interface IVWJavaScriptCaller {
    public void performJS(IVWJavaScriptListener listener, String functionName, Object[] args);
}
