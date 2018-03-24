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
public interface IVWLoadingStatus {
    void setMaximum(int max);

    void setMinimum(int min);

    void setStatus(int status);

    int getStatus();
}
