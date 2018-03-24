package client.framework.view.jmscomp;

import java.awt.*;
import javax.swing.*;
import javax.swing.JTextField;
import javax.swing.border.*;
import javax.swing.BorderFactory;
import client.framework.view.common.*;

/**
 * <p>�^�C�g��: JMsComp </p>
 * <p>����: Javax.Swing�p���̃I���W�i���R���|�[�l���g�Q</p>
 * <p>���쌠: milestone Copyright (c) 2002</p>
 * <p>��Ж�: �}�C���X�g�[���������</p>
 * @author ������
 * @version 1.0
 */

public class JMsLabelTitle extends JTextField {
	BorderLayout borderLayout1 = new BorderLayout();
	TitledBorder titledBorder1;
	TitledBorder titledBorder2;
	Border border1;
	TitledBorder titledBorder3;

	public JMsLabelTitle() {
		try {
			jbInit();
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}

		try {
			initBeanUser();
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}

	}

	/**
	 *<BR>
	 *�@�^�C�v�@�F�@������<BR>
	 *�@�������@�F�@�����l�ݒ菈��<BR>
	 *�@���@�l�@�F�@<BR>
	 *<BR>
	 *�@�ύX����<BR>
	 *<BR>
	 *�@�@Version�@�@���@�t�@�@�@�X�V�ҁ@�@�@�@�@�R�����g<BR>
	 *�@�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|<BR>
	 *�@�@00.00�@�@2002/05/30�@�󗈁@�K�O�@�@�V�K�쐬<BR>
	 *<BR>
	 */
	private void jbInit() throws Exception {
		titledBorder1 = new TitledBorder("");
		titledBorder2 = new TitledBorder("");
		border1 = BorderFactory.createLineBorder(new Color(255, 151, 255),2);
		titledBorder3 = new TitledBorder("");
		this.setBackground(Color.orange);
		this.setBorder(null);
		this.setHorizontalAlignment(SwingConstants.CENTER);
		this.setLayout(borderLayout1);
	}

	/**
	 *<BR>
	 *�@�^�C�v�@�F�@������<BR>
	 *�@�������@�F�@���[�U�����l�ݒ菈��<BR>
	 *�@���@�l�@�F�@<BR>
	 *<BR>
	 *�@�ύX����<BR>
	 *<BR>
	 *�@�@Version�@�@���@�t�@�@�@�X�V�ҁ@�@�@�@�@�R�����g<BR>
	 *�@�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|<BR>
	 *�@�@00.00�@�@2002/05/30�@�󗈁@�K�O�@�@�V�K�쐬<BR>
	 *<BR>
	 */
	private void initBeanUser() throws Exception {

		//++****************************
		//	��Ԑݒ�
		//--****************************
		this.setEnabled( false );

		//++****************************
		//	�������ݒ�
		//--****************************
		setFont ( DefaultComp.LABEL_TITLE_FONT );
        this.setFont(DefaultComp.LABEL_TITLE_FONT);

		//++****************************
		//	�F�ݒ�
		//--****************************
		setDisabledTextColor ( DefaultComp.LABEL_TITLE_FOREGROUND_COLOR );
		setBackground ( DefaultComp.LABEL_TITLE_BACKGROUND_COLOR );

	}




}