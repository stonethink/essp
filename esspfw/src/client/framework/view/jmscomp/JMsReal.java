package client.framework.view.jmscomp;

import java.awt.*;
import javax.swing.JTextField;
import java.awt.event.*;
import java.text.*;
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

public class JMsReal extends JTextField {

	/**		���͕�������p	*/
	public int         _iInputCharType;

	/**		�t�B�[���h�G���[����p	*/
	private String		_sField_Error   = null;

	/**		setEnabled�ł̍ŏI�v���l	*/
	private boolean		_bEnabled_Save;

	/**		�v���e�N�g�E�N���A�E�t���O	*/
        private boolean		_bProtectClearFlag;
        private boolean canNegative = false; //add by xh


	BorderLayout borderLayout1 = new BorderLayout();
	private int maxInputIntegerDigit=8;
	private int MaxInputDecimalDigit=0;

	public JMsReal() {
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
	 *�@�@00.00�@�@2002/05/31�@�󗈁@�K�O�@�@�V�K�쐬<BR>
	 *<BR>
	 */
	private void jbInit() throws Exception {
		this.setBorder(BorderFactory.createLoweredBevelBorder());
		this.setDisabledTextColor(Color.black);
		this.setHorizontalAlignment(SwingConstants.RIGHT);
		this.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				this_actionPerformed(e);
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
		this.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				this_keyPressed(e);
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
	 *�@�@00.00�@�@2002/05/31�@�󗈁@�K�O�@�@�V�K�쐬<BR>
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

        this.setFont(DefaultComp.REAL_FONT);

        //++****************************
		//	�������ݒ�
		//--****************************
//		setAutoIME( true );
//		setInputStyle ( defComponent.INPUT_STYLE );
		setFont( DefaultComp.NUMBER_FONT );
		this.setSelectedTextColor( DefaultComp.FOREGROUND_COLOR_SELECT );
		this.setSelectionColor( DefaultComp.BACKGROUND_COLOR_SELECT );
		this.setDisabledTextColor( DefaultComp.DISABLED_FONT_COLOR );

		//++****************************
		//	�v���p�e�B�����l
		//--****************************
		this.setMaxInputIntegerDigit( DefaultComp.REAL_MAX_INTEGER_DIGIT );
		this.setMaxInputDecimalDigit( DefaultComp.REAL_MAX_DECIMAL_DIGIT );
		this.setValue( 0 );

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
	protected void this_keyPressed(KeyEvent e) {
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
	 *�@�@00.00�@�@2002/05/31�@�󗈁@�K�O�@�@�V�K�쐬<BR>
	 *<BR>
	 */
	void this_focusGained(FocusEvent e) {
		String    sOldStr;

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

		//++****************************
		//	�w�i�F�ݒ�
		//--****************************
		_setBackgroundColor();

		//++****************************
		//	�\�����e�ۑ�
		//--****************************
		sOldStr    = this.getText();
		/*setDocument(new InputDocument( 100,//','�������ꍇ�ɁA�ǂ̂��炢�����邩�킩��Ȃ��̂�100�ɃZ�b�g
									   5,
									   getMaxInputIntegerDigit(),
									   getMaxInputDecimalDigit() ));

	*/
       InputDocument document = new InputDocument(100, //','�������ꍇ�ɁA�ǂ̂��炢�����邩�킩��Ȃ��̂�100�ɃZ�b�g
                                                  5,
                                                  getMaxInputIntegerDigit(),
                                                  getMaxInputDecimalDigit());

       document.setCanNegative(this.canNegative());
       setDocument(document);


     //++****************************
		//	�\�����e����
		//--****************************
//System.out.println( " removeNonNumeric( sOldStr ) : " +  removeNonNumeric( sOldStr ) );
		this.setText( removeNonNumeric( sOldStr ) );

		//++****************************
		//	�w�i�F�ݒ�
		//--****************************
		setBackground( DefaultComp.BACKGROUND_COLOR_INPUT_ACTIVE );

		//++****************************
		//	�S�I�����
		//--****************************
		this.selectAll();

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
		String  sStr1;
		String  sStr2;
		double  dvalue;

		//++****************************
		//	�w�i�F�ݒ�
		//--****************************
		_setBackgroundColor();

		if ( getText().trim().equals( "-" ) == false ) {
		   if ( getText().trim().equals( "." ) == false ) {


				StringBuffer sbuff = new StringBuffer();
				FieldPosition fpos = new FieldPosition(DecimalFormat.INTEGER_FIELD);

				this.setText2( getText() );
		   }
		}
		//++****************************
		//	�I����Ԃ�����
		//--****************************
		this.setSelectionStart(0);
		setSelectionEnd(0);

	}

	/**
	 *<BR>
	 *�@�^�C�v�@�F�@�v���p�e�B[ setText ]: set<BR>
	 *�@�������@�F�@�v���p�e�B[ setText ]�̐ݒ胁�\�b�h<BR>
	 *�@���@�l�@�F�@setText�̃I�[�o�[���C�h�֐�<BR>
	 *<BR>
	 *�@�ύX����<BR>
	 *<BR>
	 *�@�@Version�@�@���@�t�@�@�@�X�V�ҁ@�@�@�@�@�R�����g<BR>
	 *�@�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|<BR>
	 *�@�@00.00�@�@2002/05/30�@�󗈁@�K�O�@�@�V�K�쐬<BR>
	 *<BR>
	 */
	public void setText (String sStr ) {

		/*setDocument(new InputDocument( 100,//','�������ꍇ�ɁA�ǂ̂��炢�����邩�킩��Ȃ��̂�100�ɃZ�b�g
									   5,
									   getMaxInputIntegerDigit(),
									   getMaxInputDecimalDigit() ));
	*/
       InputDocument document = new InputDocument(100, //','�������ꍇ�ɁA�ǂ̂��炢�����邩�킩��Ȃ��̂�100�ɃZ�b�g
                                                  5,
                                                  getMaxInputIntegerDigit(),
                                                  getMaxInputDecimalDigit());

       document.setCanNegative(this.canNegative());
       setDocument(document);

       super.setText(sStr);
  }



	/**
	 *<BR>
	 *�@�^�C�v�@�F�@�v���p�e�B[ setText ]: set<BR>
	 *�@�������@�F�@�v���p�e�B[ setText ]�̐ݒ胁�\�b�h<BR>
	 *�@���@�l�@�F�@setText�̃I�[�o�[���C�h�֐�<BR>
	 *<BR>
	 *�@�ύX����<BR>
	 *<BR>
	 *�@�@Version�@�@���@�t�@�@�@�X�V�ҁ@�@�@�@�@�R�����g<BR>
	 *�@�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|<BR>
	 *�@�@00.00�@�@2002/05/30�@�󗈁@�K�O�@�@�V�K�쐬<BR>
	 *<BR>
	 */
	public void setText2 (String sStr ) {

		/*setDocument(new InputDocument( 100,//','�������ꍇ�ɁA�ǂ̂��炢�����邩�킩��Ȃ��̂�100�ɃZ�b�g
									   0,
									   getMaxInputIntegerDigit(),
									   getMaxInputDecimalDigit() ));
                */
               InputDocument document = new InputDocument( 100,//','�������ꍇ�ɁA�ǂ̂��炢�����邩�킩��Ȃ��̂�100�ɃZ�b�g
                                                                           0,
                                                                           getMaxInputIntegerDigit(),
                                                                           getMaxInputDecimalDigit() );
              document.setCanNegative(this.canNegative());
              setDocument(document);
		super.setText( fromatFractionDigits( sStr ) );
  }

	/**
	 *<BR>
	 *�@�^�C�v�@�F�@���[�U�[�֐�<BR>
	 *�@�������@�F�@�����񑀍쏈��<BR>
	 *�@���@�l�@�F�@�����y�сu.�v�ȊO�𕶎��񂩂�O��<BR>
	 *<BR>
	 *�@�ύX����<BR>
	 *<BR>
	 *�@�@Version�@�@���@�t�@�@�@�X�V�ҁ@�@�@�@�@�R�����g<BR>
	 *�@�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|<BR>
	 *�@�@00.00�@�@2002/05/30�@�󗈁@�K�O�@�@�V�K�쐬<BR>
	 *<BR>
	 */
	private String removeNonNumeric( String oldStr ){
		StringBuffer newStr = new StringBuffer();

		boolean bFrg  = false;
		char char2;

		for( int i=0 ; i<oldStr.length() ; i++ ){
			char chr = oldStr.charAt(i);
			//if( Character.isDigit( chr ) ){ ����ł͑S�p�̐������ʂ��Ă��܂�
			if( ( '0' <= chr && chr <= '9' ) || chr == '.' || chr == '-'  ){
				try {
					newStr.append( chr );
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

        //add: ��ֹ�����������"-"�����
        if( newStr.equals( "-" ) == true ){
            newStr = new StringBuffer("");
        }

		return( newStr.toString() );
	}

	/**
	 *<BR>
	 *�@�^�C�v�@�F�@���[�U�[�֐�<BR>
	 *�@�������@�F�@�����񐔒l��ύX����<BR>
	 *�@���@�l�@�F�@<BR>
	 *<BR>
	 *�@�ύX����<BR>
	 *<BR>
	 *�@�@Version�@�@���@�t�@�@�@�X�V�ҁ@�@�@�@�@�R�����g<BR>
	 *�@�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|<BR>
	 *�@�@00.00�@�@2002/05/30�@�󗈁@�K�O�@�@�V�K�쐬<BR>
	 *<BR>
	 */
	public String fromatFractionDigits (String prm_sStr ) {
		String  sStr1;
		String  sStr2;
		double  dvalue;
		StringBuffer sbuff = new StringBuffer();
		FieldPosition fpos = new FieldPosition(DecimalFormat.INTEGER_FIELD);

		if ( prm_sStr.equals( "" ) == false ) {

			//dvalue  = Double.parseDouble( prm_sStr ); replaced by yery on 2004/12/22
                        dvalue  = Double.parseDouble( prm_sStr.replaceAll(",","") );
			DecimalFormat df = new DecimalFormat();

			df.setMinimumFractionDigits( getMaxInputDecimalDigit() );
			df.format( dvalue, sbuff, fpos );

			return sbuff.toString();
		} else {
			return "";
		}

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
			setForeground( DefaultComp.FOREGROUND_COLOR_NORMAL );
		}
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
	public void clearText(
	) {
		//++********************************
		//	�\���l���N���A����
		//--********************************
		this.setText( "" );
	}


	/**
	 *<BR>
	 *�@�^�C�v�@�F�@���[�U�[�֐�<BR>
	 *�@�������@�F�@�\���f�[�^�擾����<BR>
	 *�@���@�l�@�F�@<BR>
	 *<BR>
	 *�@�ύX����<BR>
	 *<BR>
	 *�@�@Version�@�@���@�t�@�@�@�X�V�ҁ@�@�@�@�@�R�����g<BR>
	 *�@�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|<BR>
	 *�@�@00.00�@�@2002/05/30�@�󗈁@�K�O�@�@�V�K�쐬<BR>
	 *<BR>
	 */
	public double getValue(
	) {
		double	dDoub;
		Double	dTemp;
		String  sStr;

		sStr	= getText();

		//���͂����������ꍇ�A0��Ԃ�
		if ( sStr.equals( "" ) == true ) {
			dTemp	= Double.valueOf( "0" );
		} else {
			sStr    = removeNonNumeric( sStr );
			dTemp	= Double.valueOf( sStr );
		}
		dDoub = dTemp.doubleValue();
		return dDoub;
	}


	/**
	 *<BR>
	 *�@�^�C�v�@�F�@���[�U�[�֐�<BR>
	 *�@�������@�F�@�\���f�[�^�擾����<BR>
	 *�@���@�l�@�F�@<BR>
	 *<BR>
	 *�@�ύX����<BR>
	 *<BR>
	 *�@�@Version�@�@���@�t�@�@�@�X�V�ҁ@�@�@�@�@�R�����g<BR>
	 *�@�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|<BR>
	 *�@�@00.00�@�@2002/05/30�@�󗈁@�K�O�@�@�V�K�쐬<BR>
	 *<BR>
	 */
	public void setValue(
		double	prm_dDoub
	) {
		String  sStr;

		sStr	= Double.toString( prm_dDoub);

/*        //���͂����������ꍇ�A0��Ԃ�
		if ( sStr.equals( "" ) == true ) {
			dNum	= Double.valueOf( "0" );
		} else {
			sStr    = removeNonNumeric( sStr );
			dNum	= Double.valueOf( sStr );
		}
		return dNum;
*/
		this.setText2( sStr );
	}

	/**
	 *<BR>
	 *�@�^�C�v�@�F�@���[�U�[��`<BR>
	 *�@�������@�F�@���͔���<BR>
	 *�@���@�l�@�F�@<BR>
	 *<BR>
	 *�@�ύX����<BR>
	 *<BR>
	 *�@�@Version�@�@���@�t�@�@�@�X�V�ҁ@�@�@�@�@�R�����g<BR>
	 *�@�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|<BR>
	 *�@�@00.00�@�@2002/05/30�@�󗈁@�K�O�@�@�V�K�쐬<BR>
	 *<BR>
	 * @return 	0:OK 1:������  <0:�G���[
	 */
	public int checkValue(
	) {
		String		sStr;
		long		lNum;

		sStr	= getText();

		//++****************************
		//	null����
		//--****************************
		if ( sStr == null ) {
			return 1;
		}

		//++****************************
		//	�����͔��蔻��
		//--****************************
		if ( sStr.trim().length() == 0 ) {
			return 1;
		}

		//++****************************
		//	���l����
		//--****************************
		try {
//			lNum	= getValue();
			return 0;
		} catch ( Exception clsExcept ) {
			return -1;
		}
	}
	public void setMaxInputIntegerDigit(int maxInputIntegerDigit) {
		this.maxInputIntegerDigit = maxInputIntegerDigit;
	}
	public int getMaxInputIntegerDigit() {
		return maxInputIntegerDigit;
	}
	public int getMaxInputDecimalDigit() {
		return MaxInputDecimalDigit;
	}
	public void setMaxInputDecimalDigit(int MaxInputDecimalDigit) {
		this.MaxInputDecimalDigit = MaxInputDecimalDigit;
	}

	void this_actionPerformed(ActionEvent e) {

        }

        public boolean canNegative() {
            return this.canNegative;
        }

        public void setCanNegative(boolean canNegative) {
            this.canNegative = canNegative;
        }
}
