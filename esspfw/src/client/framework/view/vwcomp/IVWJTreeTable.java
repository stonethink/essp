package client.framework.view.vwcomp;

import c2s.dto.ITreeNode;
import client.framework.common.treeTable.TreeTableModel;

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
public interface IVWJTreeTable {
    TreeTableModel getTreeTableModel();

    /**
     * �¼�����
     */
    void addListener(IVWJTreeTableListener listener);

    void removeListener(IVWJTreeTableListener listener);

    void removeAllListener();

    //ȡ�õ�row�еĽڵ�
    ITreeNode getNodeForRow(int row);

    //ȡ�õ�ǰ��ѡ�еĽڵ㣬���û�б�ѡ�еģ�����null��
    ITreeNode getSelectedNode();

    //ȡ�õ�ǰ��ѡ�е��е��к�
    int getSelectedRow();

    /**
     *-------------�ƶ�����------------------------------------
     *
     *�Խڵ���ƽ������(upMove)��ƽ������(downMove)��Խ������(leftMove)��Խ������(rightMove)��
     *�ڽ���ĳһ�ƶ�����ǰһ��Ҫ���ж��Ƿ������ƶ�������
     * �жϺ���isUpMovable, isDownMovable, isLeftMovable, isRightMovable��
     * ȱʡʵ�֡�
     */

    /**
     *�����<code>rowid</code>���ڵ���Ҷ�ӽڵ㣬��ô������ƽ�����ƣ�����true;
     * ���򷵻�false;
     */
    boolean isUpMovable(int rowid);

    /**
     *����<code>rowid</code>���ڵ�������һ��λ��, ����������������һ���ֵܽڵ����λ��;
     *�ƶ����������ֵܵ��ӽڵ㶼���䡣
     * ������������ƶ���
     * 1.����root�ڵ㣻
     * 2.���������׵ĵ�һ���ӽڵ�
     */
    void upMove(int rowid);

    /**
     *�����<code>rowid</code>���ڵ���Ҷ�ӽڵ㣬��ô������ƽ�����ƣ�����true;
     * ���򷵻�false;
     */
    boolean isDownMovable(int rowid);

    /**
     *����<code>rowid</code>���ڵ�������һ��λ��, ����������������һ���ֵܽڵ����λ��;
     *�ƶ����������ֵܵ��ӽڵ㶼���䡣
     * ������������ƶ���
     * 1.����root�ڵ㣻
     * 2.���������׵����һ���ӽڵ�
     */
    void downMove(int rowid);

    /**
     *�����<code>rowid</code>���ڵ���Ҷ�ӽڵ㣬��ô������ƽ�����ƣ�����true;
     * ���򷵻�false;
     */
    boolean isLeftMovable(int rowid);

    /**
     *����<code>rowid</code>���ڵ�������һ��λ�ã��Ƶ��������ڵ�ƽ����λ���������ڵ������
     *�ƶ��������ӽڵ㶼���䡣
     * ������������ƶ���
     * 1.����root�ڵ㣻
     * 2.���ĸ��ڵ���root�ڵ�
     */
    void leftMove(int rowid);

    /**
     *�����<code>rowid</code>���ڵ���Ҷ�ӽڵ㣬��ô������ƽ�����ƣ�����true;
     * ���򷵻�false;
     */
    boolean isRightMovable(int rowid);

    /**
     *����<code>rowid</code>���ڵ��Ƶ������ֵܽڵ�����棬��Ϊ���ֵܽڵ�����һ���ӽڵ㡣
     *�ƶ��������ӽڵ㶼���䡣
     * ������������ƶ���
     * 1.����root�ڵ㣻
     * 2.���ĸ��ڵ���root�ڵ�
     */
    void rightMove(int rowid);

    /**
     * �� ��ǰѡ���� ִ�� �ƶ�����
     */
    boolean isUpMovable();

    void upMove();

    boolean isDownMovable();

    void downMove();

    boolean isLeftMovable();

    void leftMove();

    boolean isRightMovable();

    void rightMove();

    /*
     * ��<code>objNewNode</code>�ӵ���<code>row</code>�С�
     * �������֮ǰ��<code>row</code>���ǽڵ�nodeOldRow����ô�����<code>objNewNode</code>
     * ��ΪnodeOldRow���ֵܽڵ㣬λ����nodeOldRow����һ��λ�ӡ�
     * �����󣬽ڵ�<code>objNewNode</code>���ӽڵ㲻�䡣
     * ��������������ӣ�
     * 1.<code>row</code>�����㣬   ������addRow��������һ�����ڵ㣻
     * 2.<code>row</code>���ڵ�������
     */
    Object addRow(int row, Object objNewNode);

    /**
     * ��<code>rowIndex</code>����һĬ���С�
     * �������� �����ͼ�@see ITreeTableModel, IColumnConfig��getDtoType()
     */
    Object addRow(int rowIndex);

    /**
     * ����<code>rowIndex</code>�нڵ�ɾ����
     * ɾ�������������ӽڵ㶼��ɾ����
     */
    Object deleteRow(int rowIndex);

    Object deleteRows(int[] rowIndexs);
}
