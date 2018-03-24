package client.framework.view.event;

public interface RowSelectedLostListener {

    /**
     * �������false,��ô��row������lost select
     * @param oldSelectedRow int ��lost select���е�index
     * @param oldSelectedNode ITreeNode  ��lost select���е�����
     *        ��VWJTreeTable��һ��ITreeNode��������
     *        ��VWJTable��һ��IDto��������
     * @return boolean
     */
    public boolean processRowSelectedLost(int oldSelectedRow, Object oldSelectedData);
}
