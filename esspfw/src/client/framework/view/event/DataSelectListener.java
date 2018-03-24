package client.framework.view.event;

public interface DataSelectListener extends EventListener {

    /**
     * Invoked when an action occurs.
     */
    public void  processDataSelect(Object oldData, Object newData);

}
