package client.framework.view.jmscomp;

import java.awt.*;
import javax.swing.JPasswordField;
import java.awt.event.*;
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

public class JMsPassText extends JPasswordField {

	/**		�t�B�[���h�G���[����p	*/
	private String		_sField_Error   = null;

	/**		setEnabled�ł̍ŏI�v���l	*/
	private boolean		_bEnabled_Save;

	/**		�v���e�N�g�E�N���A�E�t���O	*/
	private boolean		_bProtectClearFlag;


	BorderLayout borderLayout1 = new BorderLayout();
	private int maxByteLength;




	public JMsPassText() {
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
		this.setBorder(BorderFactory.createLoweredBevelBorder());
		this.setHorizontalAlignment(SwingConstants.LEFT);
		this.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				this_keyPressed(e);
			}
		});
		this.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusGained(FocusEvent e) {
				this_focusGained(e);
			}
			public void focusLost(FocusEvent e) {
				this_focusLost(e);
			}
		});
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
		//	�����l�ݒ�
		//--****************************
		this._sField_Error	= "";
		_sField_Error	= "";
//		setKey( false );
//		setProtectClear( false );


		//++****************************
		//	�������ݒ�
		//--****************************
//		setAutoIME( true );
//		setInputStyle ( defComponent.INPUT_STYLE );
		setFont( DefaultComp.TEXT_FONT );
		this.setSelectedTextColor( DefaultComp.FOREGROUND_COLOR_SELECT );
		this.setSelectionColor( DefaultComp.BACKGROUND_COLOR_SELECT );
		this.setDisabledTextColor( DefaultComp.DISABLED_FONT_COLOR );

		//++****************************
		//	�v���p�e�B�����l
		//--****************************
		this.setMaxByteLength( DefaultComp.PASS_MAX_BYTE_LENGTH );
		this.setText( "" );

		//++****************************
		//	�w�i�F�ݒ�
		//--****************************
		_setBackgroundColor();

		//++****************************
		//	���̑��ݒ�
		//--****************************
//		setSelectedInFocus( true );
		setEnabled( true );
//		setModified( false );

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
	private void _setBackgroundColor(
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
         *�@�������@�F�@�L�[�������̏���<BR>
         *�@���@�l�@�F�@Enter�L�[�����Ńt�B�[���h�ړ�<BR>
         *<BR>
         *�@�ύX����<BR>
         *<BR>
         *�@�@Version�@�@���@�t�@�@�@�X�V�ҁ@�@�@�@�@�R�����g<BR>
         *�@�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|<BR>
         *�@�@00.00�@�@2002/05/30�@�󗈁@�K�O�@�@�V�K�쐬<BR>
         *   00.01   2004/11/30  Yery        Enter�C�x���g�̏������폜
         *<BR>
         */
	void this_keyPressed(KeyEvent e) {
                //������ENTER�C�x���g�Ŗ��𔭐�����̂ŁA�폜����͂��ł��BcomFORM.setEnterOrder()���Q�Ƃ�������
		//Enter�L�[�����̏ꍇTAB�L�[�����ɃC�x���g��ύX
//		if ( e.getKeyCode() == KeyEvent.VK_ENTER ) {
//			this.transferFocus();
//		}
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
	 *�@�@00.00�@�@2002/05/30�@�󗈁@�K�O�@�@�V�K�쐬<BR>
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
		//	���͕ϊ��i�����ړ��́j
		//--****************************
		getInputContext().setCharacterSubsets( null );

		//�\�����e�̈ꎞ�Αޔ�
		sStr    = this.getText();

		//++****************************
		//	�����}���`���̐���
		//--****************************
		setDocument(new InputDocument( getMaxByteLength(),   //�ő���͌���
									   3,                    //�ʏ핶���Ɛ����̂�
									   false                 //�Qbyte������true/false
									   ));


		 //�\�����e��ޔ����畜��
		 this.setText( sStr );


		//++****************************
		//	�S�I�����
		//--****************************
		this.selectAll();

		//++****************************
		//	�w�i�F�ݒ�
		//--****************************
		setBackground( DefaultComp.BACKGROUND_COLOR_INPUT_ACTIVE );
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
	void this_focusLost(FocusEvent e) {
		String  sStr;
		//++****************************
		//	�w�i�F�ݒ�
		//--****************************
		_setBackgroundColor();

		//++****************************
		//	�啶���ϊ�����
		//--****************************
//		if ( this.isConvUpperCase() == true ) {
//			sStr	= getText().toUpperCase();
//			setText( sStr );
//		}

		//++****************************
		//	�I����Ԃ�����
		//--****************************
		this.setSelectionStart(0);
		setSelectionEnd(0);
	}


	/**
	 *<BR>
	 *�@�^�C�v�@�F�@�v���p�e�B[ Field_Error ]: get<BR>
	 *�@�������@�F�@�v���p�e�B[ Field_Error ]�̎擾���\�b�h<BR>
	 *�@���@�l�@�F�@<BR>
	 *<BR>
	 *�@�ύX����<BR>
	 *<BR>
	 *�@�@Version�@�@���@�t�@�@�@�X�V�ҁ@�@�@�@�@�R�����g<BR>
	 *�@�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|<BR>
	 *�@�@00.00�@�@2002/05/30�@�󗈁@�K�O�@�@�V�K�쐬<BR>
	 *<BR>
	 */
	public String getField_Error () {
		//++******************************
		//	�߂�l�ݒ�
		//--******************************
		return this._sField_Error;
	}

	/**
	 *<BR>
	 *�@�^�C�v�@�F�@�v���p�e�B[ Field_Error ]: set<BR>
	 *�@�������@�F�@�v���p�e�B[ Field_Error ]�̐ݒ胁�\�b�h<BR>
	 *�@���@�l�@�F�@<BR>
	 *<BR>
	 *�@�ύX����<BR>
	 *<BR>
	 *�@�@Version�@�@���@�t�@�@�@�X�V�ҁ@�@�@�@�@�R�����g<BR>
	 *�@�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|<BR>
	 *�@�@00.00�@�@2002/05/30�@�󗈁@�K�O�@�@�V�K�쐬<BR>
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
				setText( "" );
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
	 *�@�@00.00�@�@2002/05/30�@�󗈁@�K�O�@�@�V�K�쐬<BR>
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
	 *�@�^�C�v�@�F�@���[�U�[�֐�<BR>
	 *�@�������@�F�@�G���[��Ԑݒ菈��<BR>
	 *�@���@�l�@�F�@<BR>
	 *<BR>
	 *�@�ύX����<BR>
	 *<BR>
	 *�@�@Version�@�@���@�t�@�@�@�X�V�ҁ@�@�@�@�@�R�����g<BR>
	 *�@�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|<BR>
	 *�@�@00.00�@�@2002/05/30�@�󗈁@�K�O�@�@�V�K�쐬<BR>
	 *<BR>
	 */
	public void setErrorField(
		boolean		prm_bError
	) {
		//++********************************
		//	�v���e�N�g��Ԃ̏ꍇ�A�����Ȃ�
		//--********************************
		if ( _sField_Error.equals( DefaultComp.FIELD_PROTECT ) == true ) {
			return;
		}

		//++********************************
		//	�G���[��Ԑݒ�
		//--********************************
		if ( prm_bError == true ) {
			setField_Error( DefaultComp.FIELD_ERROR );
		} else {
			setField_Error( "" );
		}
	}






	/**
	 *<BR>
	 *�@�^�C�v�@�F�@setText�̃I�[�o�[���C�h�֐�<BR>
	 *�@���@�l�@�F�@�啶���ϊ��v���p�e�B�𒲂ׁA�ϊ���setText<BR>
	 *<BR>
	 *�@�ύX����<BR>
	 *<BR>
	 *�@�@Version�@�@���@�t�@�@�@�X�V�ҁ@�@�@�@�@�R�����g<BR>
	 *�@�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|<BR>
	 *�@�@00.00�@�@2002/05/30�@�󗈁@�K�O�@�@�V�K�쐬<BR>
	 *<BR>
	 */
/*	public void setText(
		String		prm_sStr
	) {
		if ( this.isConvUpperCase() == true ) {
			super.setText( prm_sStr.toUpperCase() );
		} else {
			super.setText( prm_sStr );
		}
//		_checkModified();
	}
*/
	public void setMaxByteLength(int maxByteLength) {
		this.maxByteLength = maxByteLength;
	}
	public int getMaxByteLength() {
		return maxByteLength;
	}

	public String getText() {
		int i;
		char[] chr;
		String sStr = "";
		chr = this.getPassword();
		for ( i=0 ; i<chr.length; i++ ) {
			sStr = sStr + chr[i];
		}

		sStr = sStr.trim();
		return sStr;
	}

	public void setText( String prm_sStr2) {
		String prm_sStr=prm_sStr2;
		if ( prm_sStr == null ) {
			prm_sStr = "";
		}

		super.setText(prm_sStr);
	}


}
