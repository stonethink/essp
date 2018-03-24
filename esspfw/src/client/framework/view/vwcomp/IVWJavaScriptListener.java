package client.framework.view.vwcomp;

import com.wits.util.Parameter;

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
public interface IVWJavaScriptListener {
    /**
     * invoked when JavaScript callback
     * @param param Parameter
     */
    public void actionPerformed(Parameter param);
}
