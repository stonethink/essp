package client.framework.view.jmscomp;

import java.awt.*;
import javax.swing.JLabel;
import client.framework.view.common.*;

/**
 * <p>�^�C�g��: JMsComp </p>
 * <p>����: Javax.Swing�p���̃I���W�i���R���|�[�l���g�Q</p>
 * <p>���쌠: milestone Copyright (c) 2002</p>
 * <p>��Ж�: �}�C���X�g�[���������</p>
 * @author ������
 * @version 1.0
 */

public class JMsLabel extends JLabel {
	BorderLayout borderLayout1 = new BorderLayout();

	public JMsLabel() {
		try {
			jbInit();
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	private void jbInit() throws Exception {
		this.setLayout(borderLayout1);
		this.setFont( DefaultComp.DEFUALT_BOLD_FONT );
		this.setForeground( Color.black );
		//this.setDisplayedMnemonic('0');
	}
}
