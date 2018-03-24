package client.framework.view.jmscomp;

import java.awt.*;
import javax.swing.*;
import javax.swing.JTextField;
import java.awt.event.*;
import javax.swing.border.*;
import client.framework.view.common.*;

/**
 * <p>�^�C�g��: JMsComp�@�i�}�C���X�g�[���R���|�[�l���g�j</p>
 * <p>����: javax.swing �p���̃R���|�[�l���g�Q</p>
 * <p>���쌠: milestone Copyright (c) 2002</p>
 * <p>��Ж�: �}�C���X�g�[���������</p>
 * @author HORAI Yukihiro
 * @version 1.0
 */

public class JMsText extends JTextField {

    /** �ϊ����[�h�w��萔*/
//	public static final String IM_NONE        = "�w��Ȃ�";
//	public static final String IM_HIRAGANA    = "�Ђ炪��";
//	public static final String IM_HALFKANA    = "���p�J�i";
//	public static final String IM_FULLASCII   = "�S�p�p��";
//	public static final String IM_OFF         = "���ړ���";

    /**		���͕�������p	*/
    public int         _iInputCharType;

    /**		�t�B�[���h�G���[����p	*/
    private String		_sField_Error   = null;

    /**		setEnabled�ł̍ŏI�v���l	*/
    private boolean		_bEnabled_Save;

    /**		�v���e�N�g�E�N���A�E�t���O	*/
    private boolean		_bProtectClearFlag;

    BorderLayout borderLayout1 = new BorderLayout();
    private String inputMode;
    private int maxByteLength;
    private String inputChar;
    private boolean input2ByteOk = true;
    private boolean convUpperCase;
    Border border1;
    private boolean maxNextFoucs;

    private String  psText_Save = "";
    private boolean keyType;

    public JMsText() {

        try {
            jbInit();
        }
        catch(Exception e) {
            e.printStackTrace();
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

        border1 = BorderFactory.createCompoundBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED,Color.white,Color.white,new Color(156, 156, 158),new Color(109, 109, 110)),BorderFactory.createEmptyBorder(0,1,0,0));
        this.setLayout(borderLayout1);
        this.setBorder(border1);
        this.setHorizontalAlignment(SwingConstants.LEFT);
        this.setFont( DefaultComp.TEXT_FONT );
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
        //setConvUpperCase( false );
        //this.setInput2ByteOk( false );
        //this.setInputMode( IM_OFF );
        //this.setInputChar( "�ʏ핶���Ɛ����̂�" );
        //this.setMaxByteLength( DefaultComp.TEXT_MAX_BYTE_LENGTH );
/* DEL S   20040803 Tsuka ***
        this.isFocusTraversable();
 * DEL E   20040804 Tsuka **/
        //this.isFocusable();
        //this.setMaxNextFoucs( false );



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
     *<BR>
     */


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
        //	IME���[�h�̎����ύX
        //--****************************
        changeInputMode();

        //�\�����e�̈ꎞ�Αޔ�
        sStr    = this.getText();

        //++****************************
        //	���͕�������𔻒�
        //--****************************
        _InputChar();

        //++****************************
        //	�����}���`���̐���
        //--****************************
        setDocument(new InputDocument( getMaxByteLength(),  //�ő���͌���
                                       _iInputCharType,     //�R���|�[�l���g�^�C�v
                                       isInput2ByteOk()     //�Qbyte������true/false
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
    public void this_focusLost(FocusEvent e) {
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
     *�@�������@�F�@�����ϊ����[�h�̐؂�ւ�<BR>
     *�@���@�l�@�F�@��ʃv���p�e�B��� InputMode���擾<BR>
     *<BR>
     *�@�ύX����<BR>
     *<BR>
     *�@�@Version�@�@���@�t�@�@�@�X�V�ҁ@�@�@�@�@�R�����g<BR>
     *�@�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|<BR>
     *�@�@00.00�@�@2002/05/30�@�󗈁@�K�O�@�@�V�K�쐬<BR>
     *<BR>
     */
    public void changeInputMode() {
//		String    sStr;
//		String     sType;
//
//		sType = getInputMode();
//	   if (sType.equals( IM_NONE ) == false ) {
//			Character.Subset[] subsets = null;
//			if (  sType.equals( IM_HIRAGANA ) ==true ) {
//				subsets = new Character.Subset[] {java.awt.im.InputSubset.KANJI};
//			} else if (  sType.equals( IM_HALFKANA ) ==true ) {
//				subsets = new Character.Subset[] {java.awt.im.InputSubset.HALFWIDTH_KATAKANA};
//			} else if (  sType.equals( IM_FULLASCII ) ==true ) {
//				subsets = new Character.Subset[] {java.awt.im.InputSubset.FULLWIDTH_LATIN};
//			} else {
//				subsets = null;
//			}
//            if(getInputContext()!=null) {
//                getInputContext().setCharacterSubsets(subsets);
//            }
//		}
    }




    /**
     *<BR>
     *�@�������@�F�@�p�����[�^�ϊ�����<BR>
     *�@���@�l�@�FgetconvUpperCase()�Ŏ擾�����l����boolean�ɔ���<BR>
     *<BR>
     *�@�ύX����<BR>
     *<BR>
     *�@�@Version�@�@���@�t�@�@�@�X�V�ҁ@�@�@�@�@�R�����g<BR>
     *�@�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|<BR>
     *�@�@00.00�@�@2002/05/30�@�󗈁@�K�O�@�@�V�K�쐬<BR>
     *<BR>
     */
    public void _InputChar() {
//		if ( getInputChar().equals( "�S��" ) == true ) {
//			_iInputCharType = 0;
//		} else if ( getInputChar().equals( "�����̂�" ) == true ) {
//			_iInputCharType = 1;
//		} else if ( getInputChar().equals( "�ʏ핶���̂�" ) == true ) {
//			_iInputCharType = 2;
//		} else if ( getInputChar().equals( "�ʏ핶���Ɛ����̂�" ) == true ) {
//			_iInputCharType = 3;
//		} else {
//			_iInputCharType = 2;
//		}
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
    public void setText(
        String		prm_sStr
    ) {
        if ( prm_sStr != null ) {
            if ( this.isConvUpperCase() == true ) {
                super.setText( prm_sStr.toUpperCase() );
            } else {
                super.setText( prm_sStr );
            }
        }
//		_checkModified();
    }


    /**
     *<BR>
     *�@�������@�F�@�p�����[�^�擾�E�ݒ菈��<BR>
     *�@���@�l�@�F�@jbuilder��<BR>
     *<BR>
     *�@�ύX����<BR>
     *<BR>
     *�@�@Version�@�@���@�t�@�@�@�X�V�ҁ@�@�@�@�@�R�����g<BR>
     *�@�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|<BR>
     *�@�@00.00�@�@2002/05/30�@�󗈁@�K�O�@�@�V�K�쐬<BR>
     *<BR>
     */
    public void setInputMode(String inputMode) {
        this.inputMode = inputMode;
    }
    public String getInputMode() {
        return inputMode;
    }
    public void setMaxByteLength(int maxByteLength) {
        this.maxByteLength = maxByteLength;
    }
    public int getMaxByteLength() {
        return maxByteLength;
    }
    public void setInputChar(String inputChar) {
        this.inputChar = inputChar;
    }
    public String getInputChar() {
        return inputChar;
    }
    public void setInput2ByteOk(boolean input2ByteOk) {
        this.input2ByteOk = input2ByteOk;
    }
    public boolean isInput2ByteOk() {
        return input2ByteOk;
    }
    public void setConvUpperCase(boolean convUpperCase) {
        this.convUpperCase = convUpperCase;
    }
    public boolean isConvUpperCase() {
        return convUpperCase;
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
        int iMax = 0;
        int iInp = 0;
                //������ENTER�C�x���g�Ŗ��𔭐�����̂ŁA�폜����͂��ł��BcomFORM.setEnterOrder()���Q�Ƃ�������
//		if ( e.getKeyCode() == KeyEvent.VK_ENTER ) {
//			this.transferFocus();
//		}
        /* else {
System.out.println( "VK_0 : " +  e.VK_0 );
System.out.println( "VK_9 : " +  e.VK_9 );
System.out.println( "VK_A : " +  e.VK_A );
System.out.println( "VK_Z : " +  e.VK_Z );

            //�ő包�����͂��ꂽ�ꍇ�ɁA���̃t�B�[���h�ɔ�Ԏd�g�݁i2002.12.26  horai�j

            if ( this.isMaxNextFoucs() == true ) {
                if ((( e.getKeyCode() >= 48 ) && ( e.getKeyCode() <= 57 ))
                      || (( e.getKeyCode() >= 65 ) && ( e.getKeyCode() <= 90 ))) {
//                if (( e.getKeyCode() >= 48 ) && ( e.getKeyCode() <= 57 )) {
                        iMax = this.getMaxByteLength();
                        iInp = this.getText().length();
                        if ( ( iMax - 1 )  <= iInp ) {
                            this.transferFocus();
                        }

                }
            }
        }
        */
    }


    public void setMaxNextFoucs(boolean maxNextFoucs) {
        this.maxNextFoucs = maxNextFoucs;
    }
    public boolean isMaxNextFoucs() {
        return maxNextFoucs;
    }


///////////////////////////////////////////////////////////////////////////////////////
    public boolean checkModified(
    ) {
        String		sCode;
System.out.println( "old : " + psText_Save );
System.out.println( "now : " + getText() );
        sCode	= getText();
        if ( psText_Save.equals( sCode ) == false ) {
            return true;
        } else {
            return false;
        }
    }

    public void clearModified(
    ) {
        psText_Save	= getText();
    }
    public void setKeyType(boolean keyType) {
        this.keyType = keyType;
    }
    public boolean isKeyType() {
        return keyType;
    }


/*    public boolean isManagingFocus() {
        return true;
    }
*/

}
