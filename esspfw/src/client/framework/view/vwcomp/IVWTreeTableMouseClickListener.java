package client.framework.view.vwcomp;

import java.awt.event.MouseEvent;
import c2s.dto.ITreeNode;

public interface IVWTreeTableMouseClickListener {
    public final static int POSITION_TREE=1;
    public final static int POSITION_TABLE=2;
    /**
     * @param e MouseEvent ����¼�����
     * @param node ITreeNode �����Ӱ������ڵ�
     * @param positionType int ���λ�á���ʾ��ǰ�ǵ�������ϣ����ǵ���ڱ����
     */
    void onMouseClick(MouseEvent e,ITreeNode node,int positionType);
}
