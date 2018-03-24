package client.framework.view.event;

public interface StateChangedListener extends EventListener {

    /**
     * Invoked when an action occurs.
     * see  example in client.essp.common.view.VWWorkArea
     */
    public void  processStateChanged();

}
