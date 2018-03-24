package client.framework.view.jmscomp;

import java.awt.*;
import javax.swing.JButton;
import java.awt.event.*;
import javax.swing.BorderFactory;
import javax.swing.border.*;
import client.framework.view.common.*;

/**
 * <p>�^�C�g��: JMsComp </p>
 * <p>����: Javax.Swing�p���̃I���W�i���R���|�[�l���g�Q</p>
 * <p>���쌠: milestone Copyright (c) 2002</p>
 * <p>��Ж�: �}�C���X�g�[���������</p>
 * @author ������
 * @version 1.0
 */


public class JMsListButton extends JButton {
	BorderLayout borderLayout1 = new BorderLayout();
	TitledBorder titledBorder1;

	/**		CORBA�ʐM�f�[�^ IF_xxx_E �t�B�[���h�l	*/
	private String		_sField_IF_xxx_E	= null;

	/**		setEnabled�ł̍ŏI�v���l	*/
	private boolean		_bEnabled_Save = true;

	public JMsListButton() {
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
		this.setBorder(BorderFactory.createRaisedBevelBorder());
		this.setText("�~");
		this.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyPressed(KeyEvent e) {
					this_keyPressed(e);
			}
		});
		this.setLayout(borderLayout1);

		//++****************************
		//	�����l�ݒ�
		//--****************************
		_sField_IF_xxx_E	= "";

	}

	/**
	 *<BR>
	 *�@�^�C�v�@�F�@�v���p�e�B[ Field_IF_xxx_E ]: set<BR>
	 *�@�������@�F�@�v���p�e�B[ Field_IF_xxx_E ]�̐ݒ胁�\�b�h<BR>
	 *�@���@�l�@�F�@<BR>
	 *<BR>
	 *�@�ύX����<BR>
	 *<BR>
	 *�@�@Version�@�@���@�t�@�@�@�X�V�ҁ@�@�@�@�@�R�����g<BR>
	 *�@�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|<BR>
	 *�@�@00.00�@�@2000/09/04�@�����@�i��@�@�V�K�쐬<BR>
	 *<BR>
	 */
	public void setField_Error (
		String		prm_sValue
	) {
		//++******************************
		//	���͒l��ۑ�
		//--******************************
		_sField_IF_xxx_E	= prm_sValue;

		//++************************
		//	���͉ېݒ�
		//--************************
		if ( _sField_IF_xxx_E.equals( DefaultComp.FIELD_PROTECT ) == true ) {
			//++********************************
			//	���͕s�ɐݒ�
			//--********************************
			super.setEnabled( false );
		} else {
			//++********************************
			//	�ŏIsetEnabled�ݒ�v���l�ɐݒ�
			//--********************************
			super.setEnabled( _bEnabled_Save );
		}

	}

	/**
	 *<BR>
	 *�@�^�C�v�@�F�@�v���p�e�B[ Enabled ]: set<BR>
	 *�@�������@�F�@�v���p�e�B[ Enabled ]�̐ݒ胁�\�b�h<BR>
	 *�@���@�l�@�F�@setEnabled�̃I�[�o�[���C�h�֐�<BR>
	 *<BR>
	 *�@�ύX����<BR>
	 *<BR>
	 *�@�@Version�@�@���@�t�@�@�@�X�V�ҁ@�@�@�@�@�R�����g<BR>
	 *�@�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|<BR>
	 *�@�@00.00�@�@2000/09/04�@�����@�i��@�@�V�K�쐬<BR>
	 *<BR>
	 */
	public void setEnabled(
		boolean		prm_bValue
	) {

		//++****************************
		//	�v���l�ۑ�
		//--****************************
		_bEnabled_Save	= prm_bValue;

		//++**************************************
		//	���͕s�̏ꍇ�A�����L�����Z��
		//--**************************************
		if ( _sField_IF_xxx_E.equals( DefaultComp.FIELD_PROTECT ) == true ) {
			return;
		}

		//++****************************
		//	���͕s�ݒ�
		//--****************************
		super.setEnabled( prm_bValue );

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
		//	�������ݒ�
		//--****************************
//        this.setRequestFocusEnabled(false);
//        this.setFocusPainted( false );
		this.setDefaultCapable( false );

		setFont( DefaultComp.BUTTON_FONT );

	}

	void this_keyPressed(KeyEvent e) {
		if( e.getKeyCode() == KeyEvent.VK_ENTER ){
			this.doClick();
		}
	}

}
