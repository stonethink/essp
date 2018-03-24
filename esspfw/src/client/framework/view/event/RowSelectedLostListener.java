package client.framework.view.event;

public interface RowSelectedLostListener {

    /**
     * 如果返回false,那么该row将不会lost select
     * @param oldSelectedRow int 将lost select的行的index
     * @param oldSelectedNode ITreeNode  将lost select的行的数据
     *        对VWJTreeTable是一个ITreeNode类型数据
     *        对VWJTable是一个IDto类型数据
     * @return boolean
     */
    public boolean processRowSelectedLost(int oldSelectedRow, Object oldSelectedData);
}
