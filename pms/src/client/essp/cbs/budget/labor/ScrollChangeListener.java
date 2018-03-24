package client.essp.cbs.budget.labor;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JScrollPane;
import java.awt.Point;

public class ScrollChangeListener implements ChangeListener {
    /**
     * Invoked when the target of the listener has changed its state.
     *
     * @param e a ChangeEvent object
     * @todo Implement this javax.swing.event.ChangeListener method
     */
    JScrollPane scrollPane;
    JScrollPane listenerScrollPane;

    public ScrollChangeListener(JScrollPane scrollPane,
                                JScrollPane listenerScrollPane
        ) {
        this.scrollPane = scrollPane;
        this.listenerScrollPane = listenerScrollPane;
    }

    //��listnerScrollPane�� scrollPanel�Ĵ�ֱ���� ����ֱ����
    public void stateChanged(ChangeEvent e) {
        if (e.getSource() == this) { //����ѭ��
            return;
        }

        Point p = listenerScrollPane.getViewport().getViewPosition();
        p.y = scrollPane.getViewport().getViewPosition().y;
        listenerScrollPane.getViewport().setViewPosition(p);
    }

}
