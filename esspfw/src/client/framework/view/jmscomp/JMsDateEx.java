package client.framework.view.jmscomp;

import java.awt.*;
import javax.swing.*;
import javax.swing.JTextField;
import java.awt.event.*;
import client.framework.view.common.*;

public class JMsDateEx extends JTextField {
  private static Color dlgSeekColor = new Color(255, 255, 196);
  private static Color dlgMsgColor = new Color(255, 255, 255);

  public final static String _DATA_PRO_YYYYMMDD = "YYYYMMDD"; //added by stone 20050504
  public final static String _DATA_PRO_YYMMDD = "YYMMDD";
  public final static String _DATA_PRO_YYMM = "YYMM";
  public final static String _DATA_PRO_YY = "YY";
  public final static int _DATA_TYPE_YYMMDD = 1;
  public final static int _DATA_TYPE_YYMM = 2;
  public final static int _DATA_TYPE_YY = 3;
  public final static int _MAX_LENGTH = 8; //modify by stone 20050504

  private BorderLayout borderLayout1 = new BorderLayout();
  private String _sField_Error = null;
  /**	�v���e�N�g�E�N���A�E�t���O	*/
  private boolean _bProtectClearFlag;
  /**		setEnabled�ł̍ŏI�v���l	*/
  private boolean _bEnabled_Save;
  private boolean _bFocus;
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
   *�@�@00.00�@�@2004/11/24�@Yery�@�@�V�K�쐬<BR>
   *<BR>
   */

  public JMsDateEx() {
    try {
      jbInit();
    } catch (Exception ex) {
      ex.printStackTrace();
    }

    try {
      initBeanUser();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    this.setBorder(BorderFactory.createLoweredBevelBorder());
    this.setHorizontalAlignment(SwingConstants.CENTER);
    this.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        this_mouseClicked(e);
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
   *�@�@00.00�@�@2004/11/24�@Yery�@�@�V�K�쐬<BR>
   *<BR>
   */
  private void initBeanUser() throws Exception {

    //++****************************
    //	�����l�ݒ�
    //--****************************
    this._sField_Error = "";
    _sField_Error = "";
//		setKey( false );
//		setProtectClear( false );


    //++****************************
    //	�������ݒ�
    //--****************************
//		setAutoIME( true );
//    setInputStyle ( DefaultComp.INPUT_STYLE );
    setFont(DefaultComp.DATE_FONT);
    this.setSelectedTextColor(DefaultComp.FOREGROUND_COLOR_SELECT);
    this.setSelectionColor(DefaultComp.BACKGROUND_COLOR_SELECT);
    this.setDisabledTextColor(DefaultComp.DISABLED_FONT_COLOR);

    //++****************************
    //	�v���p�e�B�����l
    //--****************************
    this.setText("");

    //++****************************
    //	�w�i�F�ݒ�
    //--****************************
    _setBackgroundColor();

    //++****************************
    //	���̑��ݒ�
    //--****************************
    setEnabled(true);

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
    if (isEnabled() == true) {
      setBackground(DefaultComp.BACKGROUND_COLOR_ENABLED);
    } else {
      setBackground(DefaultComp.BACKGROUND_COLOR_DISABLED);
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
   *�@�@00.00�@�@2002/05/30�@�󗈁@�K�O�@�@�V�K�쐬<BR>
   *<BR>
   */
  void this_focusGained(FocusEvent e) {
    String sStr;
    _bFocus = true;
    //++****************************
    //	���͕s���A�t�B�[���h�ړ�
    //--****************************
    if (isEnabled() == false) {
      return;
    }

    //++****************************
    //	���͕ϊ��i�����ړ��́j
    //--****************************
    getInputContext().setCharacterSubsets(null);

    //�\�����e�̈ꎞ�Αޔ�
    sStr = this.getText();
    sStr = removeNonNumeric(getText());

    //++****************************
    //	�����}���`���̐���
    //--****************************
    setDocument(new InputDocument(_MAX_LENGTH, //�ő���͌���
                                  1, //�R���|�[�l���g�^�C�v(�����̂݁j
                                  false //�Qbyte������true/false
                ));

    //�\�����e��ޔ����畜��
    this.setText(sStr);

    //++****************************
    //	�S�I�����
    //--****************************
    this.selectAll();

    //++****************************
    //	�w�i�F�ݒ�
    //--****************************
    setBackground(DefaultComp.BACKGROUND_COLOR_INPUT_ACTIVE);
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
    String sStr;
    String sOldsStr;
//    String sType = _DATA_PRO_YYMMDD; ////modify by stone 20050504
//    int iMaxLeng = 10; //modify by stone 20050504
    String sType = _DATA_PRO_YYYYMMDD; ////modify by stone 20050504
    int iMaxLeng = 10; //modify by stone 20050504
    _bFocus = false;
    //++****************************
    //	���͐����l�ݒ�
    //--****************************
    sOldsStr = getText();
    //���t�^�C�v�ɂ��ő���͌����̔���
    if (sType.equals(_DATA_PRO_YYYYMMDD) == true) {
      iMaxLeng = 10;
    } else if (sType.equals(_DATA_PRO_YYMMDD) == true) {
      iMaxLeng = 8;
    } else if (sType.equals(_DATA_PRO_YYMM) == true) {
      iMaxLeng = 5;
    } else if (sType.equals(_DATA_PRO_YY) == true) {
      iMaxLeng = 2;
    }

    //++****************************
    //	�����}���`���̐���
    //--****************************
    setDocument(new InputDocument(iMaxLeng, //�ő���͌���
                                  0, //�R���|�[�l���g�^�C�v(�����̂݁j
                                  false //�Qbyte������true/false
                ));

    //++****************************
    //	�\��������ݒ�
    //--****************************
    try {
      sStr = _getOutputText(sOldsStr);
      setText(sStr);
    } catch (Exception clsExcept) {
      setText("");
    }

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

  public void setValueText(String prm_sValue) {
    String sStr;
    String sValue;

    try {
      sValue = prm_sValue.trim();
      if (sValue.equals("")) {
        setText("");
        return;
      }

      //++******************************
      //	�Ó�������
      //--******************************
      if (_checkValue(sValue) != 0) {
        setText("");
        return;
      }

      //++******************************
      //	�Y�������ɕϊ����ݒ�
      //--******************************
      if (_bFocus == true) {
        setText(sValue);
      } else {
        sStr = _getOutputText(sValue);
        setText(sStr);
      }
    } catch (Exception clsExcept) {
      setText("");
    }

    //++****************************
    //	�ύX����
    //--****************************
//		_checkModified();

  }

  public String getValueText() {
    String sStr;
    if (_bFocus == true) {
      sStr = getText();
    } else {
      sStr = _getInputText(getText());
    }

    if (sStr == null) {
      return "";
    } else {
      return sStr;
    }
  }

  private String _getOutputText(String prm_sDate) {

    String sTemp;
    String sResult;
    int iPos;

    if (prm_sDate == null || prm_sDate.trim().equals("") == true) {
      return "";
    }

    iPos = prm_sDate.indexOf("/");
    if (iPos >= 0) {
      return prm_sDate;
    }

    sTemp = prm_sDate + "        ";
    switch (prm_sDate.trim().length()) {
      //modify by stone 20050504
      case 8:
        sResult = sTemp.substring(0, 4)
            + "/"
            + sTemp.substring(4, 6)
            + "/"
            + sTemp.substring(6, 8)
            ;
        break;

      case 6:
        sResult = sTemp.substring(0, 2)
            + "/"
            + sTemp.substring(2, 4)
            + "/"
            + sTemp.substring(4, 6)
            ;
        break;

      case 4:
        sResult = sTemp.substring(0, 2)
            + "/"
            + sTemp.substring(2, 4);
        break;

      case 2:
        sResult = sTemp.substring(0, 2);
        break;
      default:
        sResult = "";
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
  private String _getInputText(String prm_sDate) {

    String sTemp;
    String sResult;
    int iPos;

    iPos = prm_sDate.indexOf("/");
    if (iPos < 0) {
      return prm_sDate;
    }

    return this.removeNonNumeric(prm_sDate.trim());
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
    return _checkValue(getValueText());
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
  private int _checkValue(String prm_sValue) {
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
      switch (sValue.length()) {
        //modify by stone 20050504
        case 8:
          iYear = Integer.valueOf(sValue.substring(0, 4)).intValue();
          iMonth = Integer.valueOf(sValue.substring(4, 6)).intValue();
          iDay = Integer.valueOf(sValue.substring(6, 8)).intValue();

          bResult = _checkDate(iYear, iMonth, iDay);
          if (bResult == true) {
            return 0;
          } else {
            return -1;
          }
        case 6:
          iYear = 2000 + Integer.valueOf(sValue.substring(0, 2)).intValue();
          iMonth = Integer.valueOf(sValue.substring(2, 4)).intValue();
          iDay = Integer.valueOf(sValue.substring(4, 6)).intValue();

          //++***********************************
          //	���͂��ꂽ�N���������������`�F�b�N
          //--***********************************
          bResult = _checkDate(iYear, iMonth, iDay);
          if (bResult == true) {
            return 0;
          } else {
            return -1;
          }
        case 4:
          iYear = 2000 + Integer.valueOf(sValue.substring(0, 2)).intValue();
          iMonth = Integer.valueOf(sValue.substring(2, 4)).intValue();
          iDay = 1;

          //++***********************************
          //	���͂��ꂽ�N���������������`�F�b�N
          //--***********************************
          bResult = _checkDate(iYear, iMonth, iDay);
          if (bResult == true) {
            return 0;
          } else {
            return -1;
          }
        case 2:
          iYear = 2000 + Integer.valueOf(sValue.substring(0, 2)).intValue();
          iMonth = 1;
          iDay = 1;

          //++***********************************
          //	���͂��ꂽ�N���������������`�F�b�N
          //--***********************************
          bResult = _checkDate(iYear, iMonth, iDay);
          if (bResult == true) {
            return 0;
          } else {
            return -1;
          }
        default:
          return -1;
      }
    } catch (Exception clsExcept) {
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

      int prm_iYear,
      int prm_iMonth,
      int prm_iDay
      ) {
    int iHizuke;

    //++****************************************
    //	�N�̔���i1990�`2020�͈͓̔��ł��邩�H�j
    //--****************************************
    // 20040405 edit shimada. sql �ɂ�����ŏ��l(1900/01/01)�ƍő�l(2040/12/31)�̊ԂɕύX�B
    //if (( prm_iYear < 1990 ) || ( prm_iYear > 2020 )) {
    if ( (prm_iYear < 1900) || (prm_iYear > 2040)) {
      return false;
    }

//++****************************************
//	���̔���i1�`12���͈͓̔��ł��邩�H�j
//--****************************************
    if ( (prm_iMonth < 1) || (prm_iMonth > 12)) {
      return false;
    }

//++**********************************************
//	���̔���i1�`���̍ő�����͈͓̔��ł��邩�H�j
//--**********************************************
    iHizuke = _getDate(prm_iYear, prm_iMonth);
    if ( (prm_iDay < 1) || (prm_iDay > iHizuke)) {
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
      int prm_iYear2,
      int prm_iMonth2
      ) {
    //++***********************************
    //	���̔���i�ő�������R�O���H�j
    //--***********************************
    if (prm_iMonth2 == 4 || prm_iMonth2 == 6 || prm_iMonth2 == 9 || prm_iMonth2 == 11) {
      return 30;
    }

    //++***********************************
    //	���̔���i2���ȊO�̌����H�j
    //--***********************************
    if (prm_iMonth2 != 2) {
      return 31;
    }

    //++***********************************
    //	���̔���i���邤�N�̂Q�����H�j
    //--***********************************
    if (prm_iYear2 % 400 == 0 || ( (prm_iYear2 % 100 != 0) && (prm_iYear2 % 4 == 0))) {
      return 29;
    } else {
      return 28;
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
  public String getField_Error() {

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
  public void setField_Error(
      String prm_sValue
      ) {
    //++******************************
    //	���͒l��ۑ�
    //--******************************
    _sField_Error = prm_sValue;

    //++************************
    //	�����F�ݒ�
    //--************************
    if (_sField_Error.equals(DefaultComp.FIELD_ERROR) == true) {
      setForeground(DefaultComp.FOREGROUND_COLOR_ERROR);
    } else {
      setForeground(DefaultComp.FOREGROUND_COLOR_NORMAL);
    }

    //++************************
    //	���͉ېݒ�
    //--************************
    if (_sField_Error.equals(DefaultComp.FIELD_PROTECT) == true) {
      //++********************************
      //	�v���e�N�g���̃N���A����
      //--********************************
      if (_bProtectClearFlag == true) {
        setValueText("");
      }

      //++********************************
      //	���͕s�ɐݒ�
      //--********************************
      super.setEnabled(false);
      _setBackgroundColor();
    } else {
      //++********************************
      //	�ŏIsetEnabled�ݒ�v���l�ɐݒ�
      //--********************************
      super.setEnabled(_bEnabled_Save);
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
      boolean prm_bValue
      ) {

    //++****************************
    //	�v���l�ۑ�
    //--****************************
    _bEnabled_Save = prm_bValue;

    //++**************************************
    //	���͕s�̏ꍇ�A�����L�����Z��
    //--**************************************
    if (_sField_Error.equals(DefaultComp.FIELD_PROTECT) == true) {
      return;
    }

    //++****************************
    //	���͕s�ݒ�
    //--****************************
    super.setEnabled(prm_bValue);
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
      boolean prm_bError
      ) {
    //++********************************
    //	�v���e�N�g��Ԃ̏ꍇ�A�����Ȃ�
    //--********************************
    if (_sField_Error.equals(DefaultComp.FIELD_PROTECT) == true) {
      return;
    }

    //++********************************
    //	�G���[��Ԑݒ�
    //--********************************
    if (prm_bError == true) {
      setField_Error(DefaultComp.FIELD_ERROR);
    } else {
      setField_Error("");
    }
  }

  private String removeNonNumeric(String oldStr) {
    StringBuffer newStr = new StringBuffer();

    boolean bFrg = false;
    char char2;

    for (int i = 0; i < oldStr.length(); i++) {
      char chr = oldStr.charAt(i);
      //if( Character.isDigit( chr ) ){ ����ł͑S�p�̐������ʂ��Ă��܂�
      if ('0' <= chr && chr <= '9') {
        try {
			newStr.append(chr);
		} catch (Exception e) {
			e.printStackTrace();
		}
      }
    }

    return (newStr.toString());
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
  void this_keyPressed(KeyEvent e) {
    //Enter�L�[�����̏ꍇTAB�L�[�����ɃC�x���g��ύX
    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
      this.transferFocus();
    }
  }

  void this_mouseClicked(MouseEvent e) {
    if (e.getClickCount() == 2 && this.isEnabled() && this.isEditable()) {
      dlgClarenderEx clsDialog;
      Frame clsFrame;
      String sReturnDate = "";

      ComGlobal.g_clsCurrentPanel = null;

      //++****************************
      //	�_�C�A���O�\����Ԃɐݒ�
      //--****************************
      ComGlobal.g_clsCurrentPanel = ComGlobal.gClsCurrentPanelNative;
      clsFrame = comFORM.getBaseFrame();
      clsDialog = new dlgClarenderEx(clsFrame, "�J�����_�[", true);
      clsDialog.getContentPane().setBackground(dlgSeekColor);
      ComGlobal.g_clsDialogCalender = clsDialog;

      //++****************************
      //	�_�C�A���O��\��
      //--****************************
      try {
        sReturnDate = clsDialog.showDialog(this);
      } finally {
        if (clsDialog.isVisible() == true) {
          clsDialog.dispose();
        }
      }

      //modify by stone 20050504
//      this.setText(sReturnDate);
      setValueText(sReturnDate);
      comFORM.setFocus(this);
      return;

    }
  }

}

class dlgClarenderEx extends dlgClarender {
  public dlgClarenderEx(Frame frame, String title, boolean modal) {
    super(frame, title, modal);
  }

  public String showDialog(JMsDateEx jmsDate) {
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    pbSelect = false;
    if (jmsDate.checkValue() == 0) {
      psNewDate = jmsDate.getValueText();
      if (psNewDate.length() == 2) {
        psNewDate = "20" + psNewDate + "0101";
      } else if (psNewDate.length() == 4) {
        psNewDate = "20" + psNewDate + "01";
      } else if (psNewDate.length() == 6) {
        psNewDate = "20" + psNewDate;
      }
    } else {
      psNewDate = "";
    }

    psOldDate = psNewDate;
    _setCalendar();
    this.setVisible(true);
    /**
     * Add by yery on 2004/11/22
     */
//    if(psNewDate!=null && !psNewDate.equals("")) {
//      psNewDate = psNewDate.substring(2);
//    }
    return psNewDate;
  }
}
