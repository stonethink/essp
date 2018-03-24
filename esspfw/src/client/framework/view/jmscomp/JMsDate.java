package client.framework.view.jmscomp;

import java.awt.*;
import javax.swing.*;
import javax.swing.JTextField;
import java.awt.event.*;
import client.framework.view.common.*;

/**
 * <p>�^�C�g��: JMsComp </p>
 * <p>����: Javax.Swing�p���̃I���W�i���R���|�[�l���g�Q</p>
 * <p>���쌠: milestone Copyright (c) 2002</p>
 * <p>��Ж�: �}�C���X�g�[���������</p>
 * @author ������
 * @version 1.0
 */

public class JMsDate extends JTextField {
	BorderLayout borderLayout1 = new BorderLayout();


	private String		_sField_Error	= null;

	/**		setEnabled�ł̍ŏI�v���l	*/
	private boolean		_bEnabled_Save;

	/**		���͒l�ύX�t���O�l	*/
	private boolean		_bModified;

	/**		�L�[�t���O	*/
	private boolean		_bKeyFlag;

	/**		�v���e�N�g�E�N���A�E�t���O	*/
	private boolean		_bProtectClearFlag;

	private int				_iDataType;
	private	boolean			_bFocus;


	public	 static final String		_DATA_PRO_YMD			= "YYYYMMDD";
	public	 static final String		_DATA_PRO_HMS			= "HHMMSS";
	public	 static final String		_DATA_PRO_HM			= "HHMM";
	public	 static final String		_DATA_PRO_YYYY			= "YYYY";
	public	 static final String		_DATA_PRO_YM			= "YYYYMM";
	public	 static final String		_DATA_PRO_MM			= "MM";
	public	 static final String		_DATA_PRO_DD			= "DD";
        //add by yery on 2004/11/12
    public	 static final String            _DATA_PRO_MMDD                  = "MMDD";
        //add by yery on 2004/11/22
    public	 static final String            _DATA_PRO_YYMMDD                = "YYMMDD";
    public	 static final String            _DATA_PRO_YYMM                  = "YYMM";
    //add by xr on 2005/11/22
	private	 final int		_DATA_TYPE_YMD			= 1;
	private	 final int		_DATA_TYPE_HMS			= 2;
	private	 final int		_DATA_TYPE_HM			= 3;
	private	 final int		_DATA_TYPE_YYYY			= 4;
	private	 final int		_DATA_TYPE_YM			= 5;
	private	 final int		_DATA_TYPE_MM			= 6;
	private	 final int		_DATA_TYPE_DD			= 7;
        //add by yery on 2004/11/12
        private	 final int		_DATA_TYPE_MMDD			= 8;
        //add by yery on 2004/11/22
        private final int            _DATA_TYPE_YYMMDD                = 9;
        private final int            _DATA_TYPE_YYMM                  = 10;

	private	 final int		_MAX_INPUT_LENGTH_YMD	= 8;
	private	 final int		_MAX_INPUT_LENGTH_HMS	= 6;
	private	 final int		_MAX_INPUT_LENGTH_HM	= 4;
	private	 final int		_MAX_INPUT_LENGTH_YYYY	= 4;
	private	 final int		_MAX_INPUT_LENGTH_YM	= 6;
	private	 final int		_MAX_INPUT_LENGTH_MM	= 2;
	private	 final int		_MAX_INPUT_LENGTH_DD	= 2;
        //add by yery on 2004/11/12
        private	 final int		_MAX_INPUT_LENGTH_MMDD	= 4;
        //add by yery on 2004/11/22
        private	 final int		_MAX_INPUT_LENGTH_YYMMDD= 6;
        private	 final int		_MAX_INPUT_LENGTH_YYMM	= 4;

	private String dataType;
	private int maxByteLength;
	private String  psText_Save = "";
	private boolean keyType;


	public JMsDate() {
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
	 *�@�@00.00�@�@2002/06/01�@�󗈁@�K�O�@�@�V�K�쐬<BR>
	 *<BR>
	 */
	private void jbInit() throws Exception {
		this.setBorder(BorderFactory.createLoweredBevelBorder());
		this.setHorizontalAlignment(SwingConstants.CENTER);
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
				onKeyPressed(e);
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
	 *�@�@00.00�@�@2002/06/01�@�󗈁@�K�O�@�@�V�K�쐬<BR>
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
//		setInputStyle ( DefaultComp.INPUT_STYLE );
		setFont( DefaultComp.DATE_FONT );
		this.setSelectedTextColor( DefaultComp.FOREGROUND_COLOR_SELECT );
		this.setSelectionColor( DefaultComp.BACKGROUND_COLOR_SELECT );
		this.setDisabledTextColor( DefaultComp.DISABLED_FONT_COLOR );

		//++****************************
		//	�v���p�e�B�����l
		//--****************************
		setDataType( _DATA_PRO_YMD );
		this.setText( "" );


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
	protected void onKeyPressed(KeyEvent e) {
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
		String  sType;
		int     iMaxLeng;

		//++****************************
		//	���͕s���A�t�B�[���h�ړ�
		//--****************************
		if ( isEnabled() == false ) {
			return;
		}

                this._bFocus = true; //add

		//++****************************
		//	���͕ϊ��i�����ړ��́j
		//--****************************
        if(getInputContext()!=null) {
            getInputContext().setCharacterSubsets(null);
        }


		//�\�����e�̈ꎞ�Αޔ�
		sStr    = this.getText();

		//++****************************
		//	���͎�������ݒ�
		//--****************************
/*		if ( _bFocus == false ) {
			try {
				_bFocus = true;
				sStr = _getInputText( sStr );
//				setText( sStr );
			} catch( Exception clsExcept ) {
				setText( "" );
			}
		}
*/
		sStr    =   removeNonNumeric( getText() );



		//���t�^�C�v���擾
		sType   =   this.getDataType();

		//���t�^�C�v�ɂ��ő���͌����̔���
		if ( sType.equals( _DATA_PRO_YMD ) == true ) {
			iMaxLeng  =   _MAX_INPUT_LENGTH_YMD;
		} else if ( sType.equals( _DATA_PRO_HMS ) == true ) {
			iMaxLeng  =   _MAX_INPUT_LENGTH_HMS;
		} else if ( sType.equals( _DATA_PRO_HM ) == true ) {
			iMaxLeng  =   _MAX_INPUT_LENGTH_HM;
		} else if ( sType.equals( _DATA_PRO_YYYY ) == true ) {
			iMaxLeng  =   _MAX_INPUT_LENGTH_YYYY;
		} else if ( sType.equals( _DATA_PRO_YM ) == true ) {
			iMaxLeng  =   _MAX_INPUT_LENGTH_YM;
		} else if ( sType.equals( _DATA_PRO_MM ) == true ) {
			iMaxLeng  =   _MAX_INPUT_LENGTH_MM;
		} else if ( sType.equals( _DATA_PRO_DD ) == true ) {
			iMaxLeng  =   _MAX_INPUT_LENGTH_DD;
                } else if ( sType.equals( _DATA_PRO_MMDD ) == true ) { //add by yery on 2004/11/12
			iMaxLeng  =   _MAX_INPUT_LENGTH_MMDD;
		} else if( sType.equals( _DATA_PRO_YYMMDD ) == true ) { //add by yery on 2004/11/22
                       iMaxLeng  =   _MAX_INPUT_LENGTH_YYMMDD;
                } else if( sType.equals( _DATA_PRO_YYMM ) == true ) { //add by yery on 2004/11/22
                       iMaxLeng  =   _MAX_INPUT_LENGTH_YYMM;
                } else {
			//�v���p�e�B�̐ݒ肪����Ă����ꍇ��yyyymmdd�Ƃ���
			iMaxLeng  = _MAX_INPUT_LENGTH_YMD;
		}

		//++****************************
		//	�����}���`���̐���
		//--****************************
		setDocument(new InputDocument( iMaxLeng,  //�ő���͌���
									   1,     //�R���|�[�l���g�^�C�v(�����̂݁j
									   false     //�Qbyte������true/false
									   ));


		 //�\�����e��ޔ����畜��
		 this.setText( sStr );


		//++****************************
		//	���͐����l�ݒ�
		//--****************************
//		_setStatus_Input();


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
	public void this_focusLost(FocusEvent e) {
		String  sStr;
		String  sOldsStr;
		String  sType   = _DATA_PRO_YMD;
		int     iMaxLeng;

		//++****************************
		//	���͐����l�ݒ�
		//--****************************
		try {
//			setEnableType (com.fujitsu.jbk.gui.JFEnableType.TYPE_ALL);
			setMaxByteLength( DefaultComp.DATE_MAX_BYTE_LENGTH );
//			setMaxLength( -1 );
		} catch( Exception clsExcept ) {
		}
                this._bFocus = false; //add

		sOldsStr    =   getText();

		//���t�^�C�v�ɂ��ő���͌����̔���
		if ( sType.equals( _DATA_PRO_YMD ) == true ) {
			iMaxLeng  =   _MAX_INPUT_LENGTH_YMD + 2;
		} else if ( sType.equals( _DATA_PRO_HMS ) == true ) {
			iMaxLeng  =   _MAX_INPUT_LENGTH_HMS +2;
		} else if ( sType.equals( _DATA_PRO_HM ) == true ) {
			iMaxLeng  =   _MAX_INPUT_LENGTH_HM + 1;
		} else if ( sType.equals( _DATA_PRO_YYYY ) == true ) {
			iMaxLeng  =   _MAX_INPUT_LENGTH_YYYY;
		} else if ( sType.equals( _DATA_PRO_YM ) == true ) {
			iMaxLeng  =   _MAX_INPUT_LENGTH_YM + 1;
		} else if ( sType.equals( _DATA_PRO_MM ) == true ) {
			iMaxLeng  =   _MAX_INPUT_LENGTH_MM;
		} else if ( sType.equals( _DATA_PRO_DD ) == true ) {
			iMaxLeng  =   _MAX_INPUT_LENGTH_DD;
                } else if ( sType.equals( _DATA_PRO_MMDD ) == true ) {
                        iMaxLeng  =   _MAX_INPUT_LENGTH_MMDD + 1;
                } else if ( sType.equals( _DATA_PRO_YYMMDD ) == true ) {
                        iMaxLeng  =   _MAX_INPUT_LENGTH_YYMMDD + 2;
                } else if ( sType.equals( _DATA_PRO_YYMM ) == true ) {
                        iMaxLeng  =   _MAX_INPUT_LENGTH_YYMM + 1;
                } else {
			//�v���p�e�B�̐ݒ肪����Ă����ꍇ��yyyymmdd�Ƃ���
			iMaxLeng   = _MAX_INPUT_LENGTH_YMD + 2;
		}

		//++****************************
		//	�����}���`���̐���
		//--****************************
		setDocument(new InputDocument( iMaxLeng,  //�ő���͌���
									   0,     //�R���|�[�l���g�^�C�v(�����̂݁j
									   false     //�Qbyte������true/false
									   ));


		//++****************************
		//	�\��������ݒ�
		//--****************************
		try {
			sStr	= _getOutputText( sOldsStr );
			setText( sStr );
		} catch( Exception clsExcept ) {
			setText( "" );
		}

		//++******************************
		//	�Ó�������
		//--******************************
//		if ( _checkValue( getText() ) != 0 ) {
//			this.setErrorField( true );
//		} else {
//          this.setErrorField( false );
//        }

		//++****************************
		//	�w�i�F�ݒ�
		//--****************************
		_setBackgroundColor();

		//++****************************
		//	�I����Ԃ�����
		//--****************************
		this.setSelectionStart(0);
		setSelectionEnd(0);
	}



	public void setValueText(
		String		prm_sValue
	) {
		String		sStr;
		String		sValue;

		try {
			sValue	= prm_sValue.trim();
			if (sValue.equals( "" ) ) {
				setText( "" );
				return;
			}

                        //++******************************
                        //	add by yery on 2004/11/26
                        //--******************************
                        if(this.getDataType().equals(_DATA_PRO_YYMMDD)) {
                          if(sValue.length()==8) {
                            sValue=sValue.substring(2);
                          }
                        }


			//++******************************
			//	�Ó�������
			//--******************************
			if ( _checkValue( sValue ) != 0 ) {
				setText( "" );
				return;
			}



			//++******************************
			//	�Y�������ɕϊ����ݒ�
			//--******************************
			if ( _bFocus == true ) {

			//				_setStatus_Input();
				setText( sValue );
			} else {
//				setEnableType (com.fujitsu.jbk.gui.JFEnableType.TYPE_ALL );
				setMaxByteLength( DefaultComp.DATE_MAX_BYTE_LENGTH );
//				setMaxLength( -1 );
				sStr	= _getOutputText( sValue );
				setText( sStr );


			}
		} catch ( Exception clsExcept ) {
			setText( "" );
		}

		//++****************************
		//	�ύX����
		//--****************************
//		_checkModified();

	}
/*
	private void _setStatus_Input(
	) {
		try {
			switch ( _iDataType ) {
				case _DATA_TYPE_YMD:
					setMaxByteLength( _MAX_INPUT_LENGTH_YMD );
					break;
				case _DATA_TYPE_HMS:
					setMaxByteLength( _MAX_INPUT_LENGTH_HMS );
					break;
				case _DATA_TYPE_HM:
					setMaxByteLength( _MAX_INPUT_LENGTH_HM );
					break;
				case _DATA_TYPE_YYYY:
					setMaxByteLength( _MAX_INPUT_LENGTH_YYYY );
					break;
				case _DATA_TYPE_YM:
					setMaxByteLength( _MAX_INPUT_LENGTH_YM );
					break;
				case _DATA_TYPE_MM:
					setMaxByteLength( _MAX_INPUT_LENGTH_MM );
					break;
				case _DATA_TYPE_DD:
					setMaxByteLength( _MAX_INPUT_LENGTH_DD );
					break;
			}
//			setEnableType (com.fujitsu.jbk.gui.JFEnableType.TYPE_DIGIT);
		} catch( Exception clsExcept ) {
		}
	}
*/

	public String getValueText(
	) {
		String		sStr;

		if ( _bFocus == true ) {
			sStr = getText();
		} else {
			sStr = _getInputText( getText() );
		}

		if ( sStr == null ) {
			return "";
		} else {
			return sStr;
		}

	}


	private String _getOutputText(
		String		prm_sDate
	) {

		String	sTemp;
		String	sResult;
		int		iPos1;
		int		iPos2;

		if ( prm_sDate.trim().equals( "" ) == true ) {
			return "";
		}

		iPos1	= prm_sDate.indexOf( "/" );
		iPos2	= prm_sDate.indexOf( ":" );
		if ( ( iPos1 >= 0 ) || ( iPos2 >= 0) ) {
			return prm_sDate;
		}

		sTemp	= prm_sDate + "        ";
		switch ( _iDataType ) {
			case _DATA_TYPE_YMD:
				sResult	= sTemp.substring( 0, 4 )
						+ "/"
						+ sTemp.substring( 4, 6 )
						+ "/"
						+ sTemp.substring( 6, 8 )
						;
				break;

			case _DATA_TYPE_HMS:
				sResult	= sTemp.substring( 0, 2 )
						+ ":"
						+ sTemp.substring( 2, 4 )
						+ ":"
						+ sTemp.substring( 4, 6 )
						;
				break;

			case _DATA_TYPE_HM:
				sResult	= sTemp.substring( 0, 2 )
						+ ":"
						+ sTemp.substring( 2, 4 )
						;
				break;

			case _DATA_TYPE_YYYY:
				sResult	= sTemp.substring( 0, 4 );
				break;

			case _DATA_TYPE_YM:
				sResult	= sTemp.substring( 0, 4 )
						+ "/"
						+ sTemp.substring( 4, 6 )
						;
				break;

			case _DATA_TYPE_MM:
				sResult	= sTemp.substring( 0, 2 );
				break;

			case _DATA_TYPE_DD:
				sResult	= sTemp.substring( 0, 2 );
				break;

                      //add by yery on 2004/11/12
                       case _DATA_TYPE_MMDD:
                               sResult	= sTemp.substring( 0, 2 )+"/"+sTemp.substring( 2, 4 );
                               break;

                      //add by yery on 2004/11/22
                      case _DATA_TYPE_YYMMDD:
                               sResult	= sTemp.substring( 0, 2 )+"/"+sTemp.substring( 2, 4 )+"/"+sTemp.substring( 4, 6 );
                               break;
                               //add by yery on 2004/11/22
                      case _DATA_TYPE_YYMM:
                               sResult	= sTemp.substring( 0, 2 )+"/"+sTemp.substring( 2, 4 );
                               break;
                      default:
				sResult	= "";
		}

		return sResult;
	}

	/**
	 *<BR>
	 *�@�^�C�v�@�F�@���[�U�[��`<BR>
	 *�@�������@�F�@�\�����t�f�[�^�ϊ�<BR>
	 *�@���@�l�@�F�@�������t�f�[�^��\�����t�f�[�^�ɕϊ�����B<BR>
	 *<BR>
	 *�@�ύX����<BR>
	 *<BR>
	 *�@�@Version�@�@���@�t�@�@�@�X�V�ҁ@�@�@�@�@�R�����g<BR>
	 *�@�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|<BR>
	 *�@�@00.00�@�@2002/06/01�@�󗈁@�K�O�@�@�V�K�쐬<BR>
	 *<BR>
	 * @param   prm_sDate�@IN�@�F�@�������t�f�[�^
	 * @return  �\�����t�f�[�^
	 */
	private String _getInputText(
		String		prm_sDate
	) {

		String	sTemp;
		String	sResult;
		int		iPos1;
		int		iPos2;

		iPos1	= prm_sDate.indexOf( "/" );
		iPos2	= prm_sDate.indexOf( ":" );
		if ( ( iPos1 < 0 ) && ( iPos2 < 0) ) {
			return prm_sDate;
		}


		sTemp	= prm_sDate + "          ";
		switch ( _iDataType ) {
			case _DATA_TYPE_YMD:
				sResult	= sTemp.substring( 0, 4 )
						+ sTemp.substring( 5, 7 )
						+ sTemp.substring( 8, 10 )
						;
				break;

			case _DATA_TYPE_HMS:
				sResult	= sTemp.substring( 0, 2 )
						+ sTemp.substring( 3, 5 )
						+ sTemp.substring( 6, 8 )
						;
				break;

			case _DATA_TYPE_HM:
				sResult	= sTemp.substring( 0, 2 )
						+ sTemp.substring( 3, 5 )
						;
				break;

			case _DATA_TYPE_YYYY:
				sResult	= sTemp.substring( 0, 4 );
				break;

			case _DATA_TYPE_YM:
				sResult	= sTemp.substring( 0, 4 )
						+ sTemp.substring( 5, 7 )
						;
				break;

			case _DATA_TYPE_MM:
				sResult	= sTemp.substring( 0, 2 );
				break;

			case _DATA_TYPE_DD:
				sResult	= sTemp.substring( 0, 2 );
				break;
                       //add by yery on 2004/11/12
                       case _DATA_TYPE_MMDD:
                                sResult	= sTemp.substring( 0, 2 )+sTemp.substring( 3, 5 );
                                break;

                       //add by yery on 2004/11/22
                       case _DATA_TYPE_YYMMDD:
                                sResult	= sTemp.substring( 0, 2 )+sTemp.substring( 3, 5 )+sTemp.substring( 6, 8 );
                                break;

                       //add by yery on 2004/11/22
                       case _DATA_TYPE_YYMM:
                                sResult	= sTemp.substring( 0, 2 )+sTemp.substring( 3, 5 );
                                break;

			default:
				sResult	= "";
		}

		return sResult.trim();
	}


	/**
	 *<BR>
	 *�@�^�C�v�@�F�@���[�U�[��`<BR>
	 *�@�������@�F�@���t�A���ԃf�[�^�`�F�b�N<BR>
	 *�@���@�l�@�F�@���͂��ꂽ���t�A���ԃf�[�^�����������`�F�b�N����B<BR>
	 *<BR>
	 *�@�ύX����<BR>
	 *<BR>
	 *�@�@Version�@�@���@�t�@�@�@�X�V�ҁ@�@�@�@�@�R�����g<BR>
	 *�@�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|<BR>
	 *�@�@00.00�@�@2002/06/01�@�󗈁@�K�O�@�@�V�K�쐬<BR>
	 *<BR>
	 */
	public int checkValue(
	) {
		return _checkValue( getValueText() );
	}

	/**
	 *<BR>
	 *�@�^�C�v�@�F�@���[�U�[��`<BR>
	 *�@�������@�F�@���t�A���ԃf�[�^�`�F�b�N<BR>
	 *�@���@�l�@�F�@���͂��ꂽ���t�A���ԃf�[�^�����������`�F�b�N����B<BR>
	 *<BR>
	 *�@�ύX����<BR>
	 *<BR>
	 *�@�@Version�@�@���@�t�@�@�@�X�V�ҁ@�@�@�@�@�R�����g<BR>
	 *�@�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|<BR>
	 *�@�@00.00�@�@2002/06/01�@�󗈁@�K�O�@�@�V�K�쐬<BR>
	 *<BR>
	 */
        private int _checkValue(
            String prm_sValue
            ) {
          String sValue;
          int iYear;
          int iMonth;
          int iDay;
          int iHour;
          int iMinute;
          int iScond;
          boolean bResult;
          try {
            //++***********************************
            //	Null���A�����̓G���[
            //--***********************************
            if (prm_sValue == null) {
              return 1;
            }
            //++***********************************
            //	�����͔���
            //--***********************************
            sValue = prm_sValue.trim();
            if (sValue.equals("") == true) {
              return 1;
            }
            //++***********************************
            //	�^�C�v���ɔ���
            //--***********************************
            switch (_iDataType) {
              case _DATA_TYPE_YMD:
                iYear = Integer.valueOf(sValue.substring(0, 4)).intValue();
                iMonth = Integer.valueOf(sValue.substring(4, 6)).intValue();
                iDay = Integer.valueOf(sValue.substring(6, 8)).intValue();
                //++***********************************
                //	���͂��ꂽ�N���������������`�F�b�N
                //--***********************************
                bResult = _checkDate(iYear, iMonth, iDay);
                if (bResult == true) {
                  return 0;
                }
                else {
                  return -1;
                }
              case _DATA_TYPE_HMS:
                iHour = Integer.valueOf(sValue.substring(0, 2)).intValue();
                iMinute = Integer.valueOf(sValue.substring(2, 4)).intValue();
                iScond = Integer.valueOf(sValue.substring(4, 6)).intValue();
                //++***********************************
                //	���͂��ꂽ�����b�����������`�F�b�N
                //--***********************************
                bResult = _checkTime(iHour, iMinute, iScond);
                if (bResult == true) {
                  return 0;
                }
                else {
                  return -1;
                }
              case _DATA_TYPE_HM:
                iHour = Integer.valueOf(sValue.substring(0, 2)).intValue();
                iMinute = Integer.valueOf(sValue.substring(2, 4)).intValue();
                iScond = 0;
                //++***********************************
                //	���͂��ꂽ�����b�����������`�F�b�N
                //--***********************************
                bResult = _checkTime(iHour, iMinute, iScond);
                if (bResult == true) {
                  return 0;
                }
                else {
                  return -1;
                }
              case _DATA_TYPE_YYYY:
                iYear = Integer.valueOf(sValue.substring(0, 4)).intValue();
                iMonth = 1;
                iDay = 1;
                //++***********************************
                //	���͂��ꂽ�N���������������`�F�b�N
                //--***********************************
                bResult = _checkDate(iYear, iMonth, iDay);
                if (bResult == true) {
                  return 0;
                }
                else {
                  return -1;
                }
              case _DATA_TYPE_YM:
                iYear = Integer.valueOf(sValue.substring(0, 4)).intValue();
                iMonth = Integer.valueOf(sValue.substring(4, 6)).intValue();
                iDay = 1;
                //++***********************************
                //	���͂��ꂽ�N���������������`�F�b�N
                //--***********************************
                bResult = _checkDate(iYear, iMonth, iDay);
                if (bResult == true) {
                  return 0;
                }
                else {
                  return -1;
                }
              case _DATA_TYPE_MM:
                iYear = 2000;
                iMonth = Integer.valueOf(sValue).intValue();
                iDay = 1;
                //++***********************************
                //	���͂��ꂽ�N���������������`�F�b�N
                //--***********************************
                bResult = _checkDate(iYear, iMonth, iDay);
                if (bResult == true) {
                  return 0;
                }
                else {
                  return -1;
                }
              case _DATA_TYPE_DD:
                iYear = 2000;
                iMonth = 1;
                iDay = Integer.valueOf(sValue).intValue();
                //++***********************************
                //	���͂��ꂽ�N���������������`�F�b�N
                //--***********************************
                bResult = _checkDate(iYear, iMonth, iDay);
                if (bResult == true) {
                  return 0;
                }
                else {
                  return -1;
                }
                //add by yery on 2004/11/12
              case _DATA_TYPE_MMDD:
                iYear = 2000;
                iMonth = Integer.valueOf(sValue.substring(0, 2)).intValue();
                iDay = Integer.valueOf(sValue.substring(2, 4)).intValue();
                //++***********************************
                //	���͂��ꂽ�N���������������`�F�b�N
                //--***********************************
                bResult = _checkDate(iYear, iMonth, iDay);
                if (bResult == true) {
                  return 0;
                }
                else {
                  return -1;
                }
                //add by yery on 2004/11/22
              case _DATA_TYPE_YYMMDD:
                iYear = 2000+Integer.valueOf(sValue.substring(0, 2)).intValue();
                iMonth = Integer.valueOf(sValue.substring(2, 4)).intValue();
                iDay = Integer.valueOf(sValue.substring(4, 6)).intValue();
                //++***********************************
                //	���͂��ꂽ�N���������������`�F�b�N
                //--***********************************
                bResult = _checkDate(iYear, iMonth, iDay);
                if (bResult == true) {
                  return 0;
                }
                else {
                  return -1;
                }
                //add by yery on 2004/11/22
              case _DATA_TYPE_YYMM:
                iYear = 2000+Integer.valueOf(sValue.substring(0, 2)).intValue();
                iMonth = Integer.valueOf(sValue.substring(2, 4)).intValue();
                iDay = 1;
                //++***********************************
                //	���͂��ꂽ�N���������������`�F�b�N
                //--***********************************
                bResult = _checkDate(iYear, iMonth, iDay);
                if (bResult == true) {
                  return 0;
                }
                else {
                  return -1;
                }
              default:
                return -1;
            }
          }
          catch (Exception clsExcept) {
            return -1;
          }
        }

	/**
	 *<BR>
	 *�@�^�C�v�@�F�@���[�U�[��`<BR>
	 *�@�������@�F�@���t�f�[�^�`�F�b�N<BR>
	 *�@���@�l�@�F�@���͂��ꂽ���t�f�[�^�����������`�F�b�N����B<BR>
	 *<BR>
	 *�@�ύX����<BR>
	 *<BR>
	 *�@�@Version�@�@���@�t�@�@�@�X�V�ҁ@�@�@�@�@�R�����g<BR>
	 *�@�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|<BR>
	 *�@�@00.00�@�@2002/06/01�@�󗈁@�K�O�@�@�V�K�쐬<BR>
	 *<BR>
	 * @param   prm_iYear�@IN�@	�F�@�N�f�[�^
	 * @param   prm_iMonth�@IN�@�F�@���f�[�^
	 * @param   prm_iDay�@IN�@	�F�@���f�[�^
	 * @return 	true��false��Ԃ��B
	 */
	private boolean _checkDate(
		int		prm_iYear,
		int		prm_iMonth,
		int		prm_iDay
	){
		int		iHizuke;

		//++****************************************
		//	�N�̔���i1990�`2020�͈͓̔��ł��邩�H�j
		//--****************************************
		// 20040405 edit shimada. sql �ɂ�����ŏ��l(1900/01/01)�ƍő�l(2040/12/31)�̊ԂɕύX�B
		//if (( prm_iYear < 1990 ) || ( prm_iYear > 2020 )) {
//		if (( prm_iYear < 1900 ) || ( prm_iYear > 2040 )) {
//			return false;
//		}
        if(prm_iYear<0) {
            return false;
        }

		//++****************************************
		//	���̔���i1�`12���͈͓̔��ł��邩�H�j
		//--****************************************
		if (( prm_iMonth < 1 ) || ( prm_iMonth > 12 )) {
			return false;
		}

		//++**********************************************
		//	���̔���i1�`���̍ő�����͈͓̔��ł��邩�H�j
		//--**********************************************
		iHizuke = _getDate( prm_iYear, prm_iMonth );
		if (( prm_iDay < 1 ) || ( prm_iDay > iHizuke )) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 *<BR>
	 *�@�^�C�v�@�F�@���[�U�[��`<BR>
	 *�@�������@�F�@���̍ő�����̏o��<BR>
	 *�@���@�l�@�F�@���͂��ꂽ�N�ƌ��ɂ���Ă��̌��̍ő������Ԃ��B<BR>
	 *<BR>
	 *�@�ύX����<BR>
	 *<BR>
	 *�@�@Version�@�@���@�t�@�@�@�X�V�ҁ@�@�@�@�@�R�����g<BR>
	 *�@�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|<BR>
	 *�@�@00.00�@�@2002/06/01�@�󗈁@�K�O�@�@�V�K�쐬<BR>
	 *<BR>
	 * @param   prm_iYear2�@IN�@	�F�@�N�f�[�^
	 * @param   prm_iMonth2�@IN�@�F�@���f�[�^
	 * @return 	���̍ő������Ԃ��B
	 */
	 private int _getDate(
		int		prm_iYear2,
		int		prm_iMonth2
	){
		//++***********************************
		//	���̔���i�ő�������R�O���H�j
		//--***********************************
		if ( prm_iMonth2 == 4 || prm_iMonth2 == 6 || prm_iMonth2 == 9 || prm_iMonth2 == 11 ) {
			return 30;
		}

		//++***********************************
		//	���̔���i2���ȊO�̌����H�j
		//--***********************************
		if ( prm_iMonth2 != 2 ) {
			return 31;
		}

		//++***********************************
		//	���̔���i���邤�N�̂Q�����H�j
		//--***********************************
		if ( prm_iYear2%400 == 0 || ((prm_iYear2%100 != 0)&&(prm_iYear2%4 == 0 ))) {
			return 29;
		} else {
			return 28;
		}
	}

	/**
	 *<BR>
	 *�@�^�C�v�@�F�@���[�U�[��`<BR>
	 *�@�������@�F�@���ԃf�[�^�`�F�b�N<BR>
	 *�@���@�l�@�F�@���͂��ꂽ���ԃf�[�^�����������`�F�b�N����B<BR>
	 *<BR>
	 *�@�ύX����<BR>
	 *<BR>
	 *�@�@Version�@�@���@�t�@�@�@�X�V�ҁ@�@�@�@�@�R�����g<BR>
	 *�@�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|<BR>
	 *�@�@00.00�@�@2002/06/01�@�󗈁@�K�O�@�@�V�K�쐬<BR>
	 *<BR>
	 * @param   prm_iHour�@IN�@	�F�@���f�[�^
	 * @param   prm_iMinute�@IN�@�F�@���f�[�^
	 * @param   prm_iScond�@IN�@	�F�@�b�f�[�^
	 * @return 	true��false��Ԃ��B
	 */
	 private boolean _checkTime(
		int		prm_iHour,
		int		prm_iMinute,
		int		prm_iScond
	){
		//++***********************************
		//	���Ԃ̔���i����0�`23���ł��邩�H�j
		//--***********************************
		if (( prm_iHour < 0 ) || ( prm_iHour > 23 )) {
			return false;
		}

		//++***********************************
		//	���Ԃ̔���i����0�`59���ł��邩�H�j
		//--***********************************
		if (( prm_iMinute < 0 ) || ( prm_iMinute > 59 )) {
			return false;
		}

		//++***********************************
		//	���Ԃ̔���i�b��0�`59�b�ł��邩�H�j
		//--***********************************
		if (( prm_iScond < 0 ) || ( prm_iScond > 59 )) {
			return false;
		} else {
			return true;
		}
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
	 *�@�@00.00�@�@2002/06/01�@�󗈁@�K�O�@�@�V�K�쐬<BR>
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
	 *�@�@00.00�@�@2002/06/01�@�󗈁@�K�O�@�@�V�K�쐬<BR>
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
				setValueText( "" );
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
	 *�@�@00.00�@�@2002/06/01�@�󗈁@�K�O�@�@�V�K�쐬<BR>
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
	 *�@�@00.00�@�@2002/06/01�@�󗈁@�K�O�@�@�V�K�쐬<BR>
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




	private String removeNonNumeric( String oldStr ){
		StringBuffer newStr = new StringBuffer();

		boolean bFrg  = false;
		char char2;

		for( int i=0 ; i<oldStr.length() ; i++ ){
			char chr = oldStr.charAt(i);
			//if( Character.isDigit( chr ) ){ ����ł͑S�p�̐������ʂ��Ă��܂�
			if(  '0' <= chr && chr <= '9' ) {
				try {
					newStr.append( chr );
				} catch (Exception e) {

					e.printStackTrace();
				}
			}
		}

		return( newStr.toString() );
	}





	public void setDataType( String dataType) {
		this.dataType = dataType;

		//++************************
		//	�l��ۑ�
		//--************************
		if ( dataType.equals( _DATA_PRO_YMD ) == true ) {
			_iDataType  =   _DATA_TYPE_YMD;
		} else if ( dataType.equals( _DATA_PRO_HMS ) == true ) {
			_iDataType  =   _DATA_TYPE_HMS;
		} else if ( dataType.equals( _DATA_PRO_HM ) == true ) {
			_iDataType  =   _DATA_TYPE_HM;
		} else if ( dataType.equals( _DATA_PRO_YYYY ) == true ) {
			_iDataType  =   _DATA_TYPE_YYYY;
		} else if ( dataType.equals( _DATA_PRO_YM ) == true ) {
			_iDataType  =   _DATA_TYPE_YM;
		} else if ( dataType.equals( _DATA_PRO_MM ) == true ) {
			_iDataType  =   _DATA_TYPE_MM;
		} else if ( dataType.equals( _DATA_PRO_DD ) == true ) {
			_iDataType  =   _DATA_TYPE_DD;
                } else if ( dataType.equals( _DATA_PRO_MMDD ) == true ) {
			_iDataType  =   _DATA_TYPE_MMDD;
                } else if ( dataType.equals( _DATA_PRO_YYMMDD ) == true ) {
                       _iDataType  =   _DATA_TYPE_YYMMDD;
                } else if ( dataType.equals( _DATA_PRO_YYMM ) == true ) {
                       _iDataType  =   _DATA_TYPE_YYMM;
                } else {
			//�v���p�e�B�̐ݒ肪����Ă����ꍇ��yyyymmdd�Ƃ���
			_iDataType	= _DATA_TYPE_YMD;
		}
	}




	public String getDataType() {
		return dataType;
	}
	public void setMaxByteLength(int maxByteLength) {
		this.maxByteLength = maxByteLength;
	}
	public int getMaxByteLength() {
		return maxByteLength;
	}
	public void setKeyType(boolean keyType) {
		this.keyType = keyType;
	}
	public boolean isKeyType() {
		return keyType;
	}

	/**
	 *<BR>
	 *�@�^�C�v�@�F�@���[�U�[�֐�<BR>
	 *�@�������@�F�@�L�[���ڕύX�`�F�b�N���\�b�h<BR>
	 *�@���@�l�@�F�@<BR>
	 *<BR>
	 *�@�ύX����<BR>
	 *<BR>
	 *�@�@Version�@�@���@�t�@�@�@�X�V�ҁ@�@�@�@�@�R�����g<BR>
	 *�@�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|<BR>
	 *�@�@00.00�@�@2003/03/24�@�󗈁@�K�O�@�@�V�K�쐬<BR>
	 *<BR>
	 */
	public boolean checkModified(
	) {
		String		sCode;

		sCode	= getValueText();
		if ( psText_Save.equals( sCode ) == false ) {
			return true;
		} else {
			return false;
		}
	}

	public void clearModified(
	) {
		psText_Save	= getValueText();
	}




}
