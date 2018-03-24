package client.framework.view.jmscomp;

import java.awt.*;
import javax.swing.JComboBox;
import javax.swing.BorderFactory;
import java.awt.event.*;
import javax.swing.border.*;
import javax.swing.*;
import client.framework.view.common.*;

/**
 * <p>�^�C�g��: JMsComp </p>
 * <p>����: Javax.Swing�p���̃I���W�i���R���|�[�l���g�Q</p>
 * <p>���쌠: milestone Copyright (c) 2002</p>
 * <p>��Ж�: �}�C���X�g�[���������</p>
 * @author ������
 * @version 1.0
 */

public class JMsComboBox extends JComboBox {
	BorderLayout borderLayout1 = new BorderLayout();


	private String		_sField_Error	= null;
	/**		setEnabled�ł̍ŏI�v���l	*/
	private boolean		_bEnabled_Save;
	/**		�O����͒l	*/
	private String		_sText_Save;
	/**		�L�[�t���O	*/
	private boolean		_bKeyFlag;
	/**		�v���e�N�g�E�N���A�E�t���O	*/
	private boolean		_bProtectClearFlag;
	private boolean		_bSelectByKey;
	private boolean		_bNoSelect;
	private boolean		_bFocus;
	Border border1;


	public JMsComboBox() {
		try {
			jbInit();
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}

		try {
			InitUser();
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}

	}

	private void jbInit() throws Exception {
//		border1 = BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(),border1);
		this.setBorder(BorderFactory.createLoweredBevelBorder());
		this.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				this_mouseClicked(e);
			}
		});

		this.registerKeyboardAction (new keyAction(), KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

		this.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusGained(FocusEvent e) {
				this_focusGained(e);
			}
			public void focusLost(FocusEvent e) {
				this_focusLost(e);
			}
		});
	}

	private void InitUser() throws Exception {
		//++****************************
		//	�����l�ݒ�
		//--****************************
		_sField_Error	    = "";
		_bSelectByKey		= false;
		_bNoSelect			= false;
//		_bFocus				= false;
//		setKey( false );
//		setProtectClear( false );

		//++****************************
		//	�������ݒ�
		//--****************************
		setFont ( DefaultComp.COMBO_BOX_FONT );
//        this.setModel( DefaultComp.DISABLED_FONT_COLOR );

		//++****************************
		//	�w�i�F�ݒ�
		//--****************************
		_setBackgroundColor();

		//++****************************
		//	���̑��ݒ�
		//--****************************
		setEnabled( true );
	}

	/**
	 *<BR>
	 *�@�^�C�v�@�F�@���[�U�[�֐�<BR>
	 *�@�������@�F�@�w�i�F�ݒ菈��<BR>
	 *�@���@�l�@�F�@<BR>
	 *<BR>
	 *�@�ύX����<BR>
	 *<BR>
	 *�@�@Version�@�@���@�t�@�@�@�X�V�ҁ@�@�@�@�@�R�����g<BR>
	 *�@�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|<BR>
	 *�@�@00.00�@�@2002/05/30�@�󗈁@�K�O�@�@�V�K�쐬<BR>
	 *<BR>
	 */
	public void _setBackgroundColor(
	) {
		if ( isEnabled() == true ) {
			setBackground( DefaultComp.BACKGROUND_COLOR_ENABLED );
		} else {
			setBackground( DefaultComp.BACKGROUND_COLOR_DISABLED );
			this.setForeground(DefaultComp.FOREGROUND_COLOR_NORMAL);
		}
	}

	/**
	 *<BR>
	 *�@�^�C�v�@�F�@�C�x���g<BR>
	 *�@�������@�F�@�t�H�[�J�X���̏���<BR>
	 *�@���@�l�@�F�@<BR>
	 *<BR>
	 *�@�ύX����<BR>
	 *<BR>
	 *�@�@Version�@�@���@�t�@�@�@�X�V�ҁ@�@�@�@�@�R�����g<BR>
	 *�@�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|<BR>
	 *�@�@00.00�@�@2002/06/24    �󗈁@�K�O�@�@�V�K�쐬<BR>
	 *<BR>
	 */
	void this_focusGained(FocusEvent e) {

	   String  sStr;

		//++****************************
		//	���͕s���A�t�B�[���h�ړ�
		//--****************************
		if ( isEnabled() == false ) {
			return;
		}

		//++****************************
		//	��Ԑݒ�
		//--****************************
		_bFocus		= true;
		_bSelectByKey		= false;

		//++****************************
		//	�w�i�F�ݒ�
		//--****************************
//		setBackground( DefaultComp.BACKGROUND_COLOR_INPUT_ACTIVE );

	}


	/**
	 *<BR>
	 *�@�^�C�v�@�F�@�C�x���g<BR>
	 *�@�������@�F�@���X�ƃt�H�[�J�X���̏���<BR>
	 *�@���@�l�@�F�@<BR>
	 *<BR>
	 *�@�ύX����<BR>
	 *<BR>
	 *�@�@Version�@�@���@�t�@�@�@�X�V�ҁ@�@�@�@�@�R�����g<BR>
	 *�@�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|<BR>
	 *�@�@00.00�@�@2002/05/30�@�󗈁@�K�O�@�@�V�K�쐬<BR>
	 *<BR>
	 */
	public void this_focusLost(FocusEvent e) {
		String  sStr;

		//++****************************
		//	�w�i�F�ݒ�
		//--****************************
//		_setBackgroundColor(); //modify by stone

		//++****************************
		//	��Ԑݒ�
		//--****************************
		_bFocus		= false;

	}
	/**
	 *<BR>
	 *�@�^�C�v�@�F�@�v���p�e�B[ Field_ERROR ]: get<BR>
	 *�@�������@�F�@�v���p�e�B[ Field_ERROR ]�̎擾���\�b�h<BR>
	 *�@���@�l�@�F�@<BR>
	 *<BR>
	 *�@�ύX����<BR>
	 *<BR>
	 *�@�@Version�@�@���@�t�@�@�@�X�V�ҁ@�@�@�@�@�R�����g<BR>
	 *�@�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|<BR>
	 *�@�@00.00�@�@2000/09/05�@�T��@��q�@�@�V�K�쐬<BR>
	 *<BR>
	 */
	public String getField_ERROR () {

		//++******************************
		//	�߂�l�ݒ�
		//--******************************
		return _sField_Error;

	}

	/**
	 *<BR>
	 *�@�^�C�v�@�F�@�v���p�e�B[ Field_ERROR ]: set<BR>
	 *�@�������@�F�@�v���p�e�B[ Field_ERROR ]�̐ݒ胁�\�b�h<BR>
	 *�@���@�l�@�F�@<BR>
	 *<BR>
	 *�@�ύX����<BR>
	 *<BR>
	 *�@�@Version�@�@���@�t�@�@�@�X�V�ҁ@�@�@�@�@�R�����g<BR>
	 *�@�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|<BR>
	 *�@�@00.00�@�@2000/09/05�@�T��@��q�@�@�V�K�쐬<BR>
	 *<BR>
	 */
	public void setField_ERROR (
		String		prm_sValue
	) {
		//++******************************
		//	���͒l��ۑ�
		//--******************************
		_sField_Error	= prm_sValue;

		//++************************
		//	�����F�ݒ�
		//--************************
		if ( _sField_Error.equals( DefaultComp.FIELD_ERROR ) == true ) {
			setForeground( DefaultComp.FOREGROUND_COLOR_ERROR );
		} else {
			setForeground( DefaultComp.FOREGROUND_COLOR_NORMAL );
		}

		//++************************
		//	���͉ېݒ�
		//--************************
		if ( _sField_Error.equals( DefaultComp.FIELD_PROTECT ) == true ) {
			//++********************************
			//	�v���e�N�g���̃N���A����
			//--********************************
			if ( _bProtectClearFlag == true ) {
				this.setSelectedIndex( -1 );
//				setText( "" );
			}

			//++********************************
			//	���͕s�ɐݒ�
			//--********************************
			super.setEnabled( false );
			_setBackgroundColor();
		} else {
			//++********************************
			//	�ŏIsetEnabled�ݒ�v���l�ɐݒ�
			//--********************************
			super.setEnabled( _bEnabled_Save );
			_setBackgroundColor();
		}

	}


	public boolean checkSelectByKey(
	) {
		return _bSelectByKey;
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
	 *�@�@00.00�@�@2000/09/05�@�T��@��q�@�@�V�K�쐬<BR>
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
		if ( _sField_Error.equals( DefaultComp.FIELD_PROTECT ) == true ) {
			return;
		}

		//++****************************
		//	���͕s�ݒ�
		//--****************************
		super.setEnabled( prm_bValue );
		_setBackgroundColor();

	}

	/**
	 *<BR>
	 *�@�^�C�v�@�F�@�C�x���g<BR>
	 *�@�������@�F�@�L�[�������̏���<BR>
	 *�@���@�l�@�F�@Enter�L�[�����Ńt�B�[���h�ړ�<BR>
	 *<BR>
	 *�@�ύX����<BR>
	 *<BR>
	 *�@�@Version�@�@���@�t�@�@�@�X�V�ҁ@�@�@�@�@�R�����g<BR>
	 *�@�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|<BR>
	 *�@�@00.00�@�@2002/06/24    �󗈁@�K�O�@�@�V�K�쐬<BR>
	 *<BR>
	 * @param   �Ȃ�
	 * @return  �Ȃ�
	 */
	protected void this_keyPressed(KeyEvent e) {
		switch ( e.getKeyCode() ) {
			case KeyEvent.VK_ENTER:
				//	���t�B�[���h�ֈړ�
				transferFocus();
				break;

			case KeyEvent.VK_UP:
			case KeyEvent.VK_DOWN:
				_bSelectByKey		= true;
				break;
/*
			case KeyEvent.VK_DELETE:
			case KeyEvent.VK_BACK_SPACE:
				//++******************************
				//	���I���\�ȏꍇ�A���I���ɐݒ�
				//--******************************
				if ( _bNoSelect == true ) {
					select( -1 );
				}
			break;

*/
		}
	}

	void this_mouseClicked(MouseEvent e) {
		_bSelectByKey		= false;
	}



	/**
	 *<BR>
	 *�@�^�C�v�@�F�@�v���p�e�B[ Field_IF_xxx_E ]: get<BR>
	 *�@�������@�F�@�v���p�e�B[ Field_IF_xxx_E ]�̎擾���\�b�h<BR>
	 *�@���@�l�@�F�@<BR>
	 *<BR>
	 *�@�ύX����<BR>
	 *<BR>
	 *�@�@Version�@�@���@�t�@�@�@�X�V�ҁ@�@�@�@�@�R�����g<BR>
	 *�@�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|<BR>
	 *�@�@00.00�@�@2000/09/05�@�T��@��q�@�@�V�K�쐬<BR>
	 *<BR>
	 */
	public String getField_Error () {

		//++******************************
		//	�߂�l�ݒ�
		//--******************************
		return _sField_Error;

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
	 *�@�@00.00�@�@2000/09/05�@�T��@��q�@�@�V�K�쐬<BR>
	 *<BR>
	 */
	public void setField_Error (
		String		prm_sValue
	) {
		//++******************************
		//	���͒l��ۑ�
		//--******************************
		_sField_Error	= prm_sValue;

		//++************************
		//	�����F�ݒ�
		//--************************
		if ( _sField_Error.equals( DefaultComp.FIELD_ERROR ) == true ) {
			setForeground( DefaultComp.FOREGROUND_COLOR_ERROR );
		} else {
			setForeground( DefaultComp.FOREGROUND_COLOR_NORMAL );
		}

		//++************************
		//	���͉ېݒ�
		//--************************
		if ( _sField_Error.equals( DefaultComp.FIELD_PROTECT ) == true ) {
			//++********************************
			//	�v���e�N�g���̃N���A����
			//--********************************
			if ( _bProtectClearFlag == true ) {
//				select( -1 );
//				setText( "" );
			}

			//++********************************
			//	���͕s�ɐݒ�
			//--********************************
			super.setEnabled( false );
			_setBackgroundColor();
		} else {
			//++********************************
			//	�ŏIsetEnabled�ݒ�v���l�ɐݒ�
			//--********************************
			super.setEnabled( _bEnabled_Save );
			_setBackgroundColor();
		}

	}
}

class keyAction	extends	AbstractAction
{
	public	keyAction (){
	}

	public	void	actionPerformed (ActionEvent	e)
	{
		JComponent combo = (JComponent) e.getSource();
		combo.transferFocus();
	}
}
