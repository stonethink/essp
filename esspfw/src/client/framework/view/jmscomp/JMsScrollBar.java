package client.framework.view.jmscomp;

import java.awt.*;
import javax.swing.JScrollBar;

/**
 * <p>�^�C�g��: JMsComp </p>
 * <p>����: Javax.Swing�p���̃I���W�i���R���|�[�l���g�Q</p>
 * <p>���쌠: milestone Copyright (c) 2002</p>
 * <p>��Ж�: �}�C���X�g�[���������</p>
 * @author ������
 * @version 1.0
 */

public class JMsScrollBar extends JScrollBar {
	BorderLayout borderLayout1 = new BorderLayout();

	public JMsScrollBar() {
		try {
			jbInit();
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	private void jbInit() throws Exception {
		this.setLayout(null);
	}
}