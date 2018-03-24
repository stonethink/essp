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
  /**	プロテクト・クリア・フラグ	*/
  private boolean _bProtectClearFlag;
  /**		setEnabledでの最終要求値	*/
  private boolean _bEnabled_Save;
  private boolean _bFocus;
  /**
   *<BR>
   *　タイプ　：　初期化<BR>
   *　処理名　：　初期値設定処理<BR>
   *　備　考　：　<BR>
   *<BR>
   *　変更履歴<BR>
   *<BR>
   *　　Version　　日　付　　　更新者　　　　　コメント<BR>
   *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
   *　　00.00　　2004/11/24　Yery　　新規作成<BR>
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
   *　タイプ　：　初期化<BR>
   *　処理名　：　ユーザ初期値設定処理<BR>
   *　備　考　：　<BR>
   *<BR>
   *　変更履歴<BR>
   *<BR>
   *　　Version　　日　付　　　更新者　　　　　コメント<BR>
   *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
   *　　00.00　　2004/11/24　Yery　　新規作成<BR>
   *<BR>
   */
  private void initBeanUser() throws Exception {

    //++****************************
    //	初期値設定
    //--****************************
    this._sField_Error = "";
    _sField_Error = "";
//		setKey( false );
//		setProtectClear( false );


    //++****************************
    //	文字情報設定
    //--****************************
//		setAutoIME( true );
//    setInputStyle ( DefaultComp.INPUT_STYLE );
    setFont(DefaultComp.DATE_FONT);
    this.setSelectedTextColor(DefaultComp.FOREGROUND_COLOR_SELECT);
    this.setSelectionColor(DefaultComp.BACKGROUND_COLOR_SELECT);
    this.setDisabledTextColor(DefaultComp.DISABLED_FONT_COLOR);

    //++****************************
    //	プロパティ初期値
    //--****************************
    this.setText("");

    //++****************************
    //	背景色設定
    //--****************************
    _setBackgroundColor();

    //++****************************
    //	その他設定
    //--****************************
    setEnabled(true);

  }

  /**
   *<BR>
   *　タイプ　：　ユーザー関数<BR>
   *　処理名　：　背景色設定処理<BR>
   *　備　考　：　<BR>
   *<BR>
   *　変更履歴<BR>
   *<BR>
   *　　Version　　日　付　　　更新者　　　　　コメント<BR>
   *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
   *　　00.00　　2002/05/30　宝来　幸弘　　新規作成<BR>
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
   *　タイプ　：　イベント<BR>
   *　処理名　：　フォーカス時の処理<BR>
   *　備　考　：　<BR>
   *<BR>
   *　変更履歴<BR>
   *<BR>
   *　　Version　　日　付　　　更新者　　　　　コメント<BR>
   *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
   *　　00.00　　2002/05/30　宝来　幸弘　　新規作成<BR>
   *<BR>
   */
  void this_focusGained(FocusEvent e) {
    String sStr;
    _bFocus = true;
    //++****************************
    //	入力不可時、フィールド移動
    //--****************************
    if (isEnabled() == false) {
      return;
    }

    //++****************************
    //	入力変換（→直接入力）
    //--****************************
    getInputContext().setCharacterSubsets(null);

    //表示内容の一時対退避
    sStr = this.getText();
    sStr = removeNonNumeric(getText());

    //++****************************
    //	文字挿入形式の制御
    //--****************************
    setDocument(new InputDocument(_MAX_LENGTH, //最大入力桁数
                                  1, //コンポーネントタイプ(数字のみ）
                                  false //２byte文字のtrue/false
                ));

    //表示内容を退避から復元
    this.setText(sStr);

    //++****************************
    //	全選択状態
    //--****************************
    this.selectAll();

    //++****************************
    //	背景色設定
    //--****************************
    setBackground(DefaultComp.BACKGROUND_COLOR_INPUT_ACTIVE);
  }

  /**
   *<BR>
   *　タイプ　：　イベント<BR>
   *　処理名　：　ロスとフォーカス時の処理<BR>
   *　備　考　：　<BR>
   *<BR>
   *　変更履歴<BR>
   *<BR>
   *　　Version　　日　付　　　更新者　　　　　コメント<BR>
   *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
   *　　00.00　　2002/05/30　宝来　幸弘　　新規作成<BR>
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
    //	入力制限値設定
    //--****************************
    sOldsStr = getText();
    //日付タイプによる最大入力桁数の判定
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
    //	文字挿入形式の制御
    //--****************************
    setDocument(new InputDocument(iMaxLeng, //最大入力桁数
                                  0, //コンポーネントタイプ(数字のみ）
                                  false //２byte文字のtrue/false
                ));

    //++****************************
    //	表示文字列設定
    //--****************************
    try {
      sStr = _getOutputText(sOldsStr);
      setText(sStr);
    } catch (Exception clsExcept) {
      setText("");
    }

    //++****************************
    //	背景色設定
    //--****************************
    _setBackgroundColor();

    //++****************************
    //	選択状態を解除
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
      //	妥当性判定
      //--******************************
      if (_checkValue(sValue) != 0) {
        setText("");
        return;
      }

      //++******************************
      //	該当書式に変換し設定
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
    //	変更判定
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
   *　タイプ　：　ユーザー定義<BR>
   *　処理名　：　表示日付データ変換<BR>
   *　備　考　：　内部日付データを表示日付データに変換する。<BR>
   *<BR>
   *　変更履歴<BR>
   *<BR>
   *　　Version　　日　付　　　更新者　　　　　コメント<BR>
   *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
   *　　00.00　　2002/06/01　宝来　幸弘　　新規作成<BR>
   *<BR>
   * @param   prm_sDate　IN　：　内部日付データ
   * @return  表示日付データ
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
   *　タイプ　：　ユーザー定義<BR>
   *　処理名　：　日付、時間データチェック<BR>
   *　備　考　：　入力された日付、時間データが正しいかチェックする。<BR>
   *<BR>
   *　変更履歴<BR>
   *<BR>
   *　　Version　　日　付　　　更新者　　　　　コメント<BR>
   *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
   *　　00.00　　2002/06/01　宝来　幸弘　　新規作成<BR>
   *<BR>
   */
  public int checkValue(
      ) {
    return _checkValue(getValueText());
  }

  /**
   *<BR>
   *　タイプ　：　ユーザー定義<BR>
   *　処理名　：　日付、時間データチェック<BR>
   *　備　考　：　入力された日付、時間データが正しいかチェックする。<BR>
   *<BR>
   *　変更履歴<BR>
   *<BR>
   *　　Version　　日　付　　　更新者　　　　　コメント<BR>
   *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
   *　　00.00　　2002/06/01　宝来　幸弘　　新規作成<BR>
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
      //	Null時、未入力エラー
      //--***********************************
      if (prm_sValue == null) {
        return 1;
      }
      //++***********************************
      //	未入力判定
      //--***********************************
      sValue = prm_sValue.trim();
      if (sValue.equals("") == true) {
        return 1;
      }
      //++***********************************
      //	タイプ毎に判定
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
          //	入力された年月日が正しいかチェック
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
          //	入力された年月日が正しいかチェック
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
          //	入力された年月日が正しいかチェック
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
   *　タイプ　：　ユーザー定義<BR>
   *　処理名　：　日付データチェック<BR>
   *　備　考　：　入力された日付データが正しいかチェックする。<BR>
   *<BR>
   *　変更履歴<BR>
   *<BR>
   *　　Version　　日　付　　　更新者　　　　　コメント<BR>
   *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
   *　　00.00　　2002/06/01　宝来　幸弘　　新規作成<BR>
   *<BR>
   * @param   prm_iYear　IN　	：　年データ
   * @param   prm_iMonth　IN　：　月データ
   * @param   prm_iDay　IN　	：　日データ
   * @return 	trueかfalseを返す。
   */
  private boolean _checkDate(

      int prm_iYear,
      int prm_iMonth,
      int prm_iDay
      ) {
    int iHizuke;

    //++****************************************
    //	年の判定（1990～2020の範囲内であるか？）
    //--****************************************
    // 20040405 edit shimada. sql における最小値(1900/01/01)と最大値(2040/12/31)の間に変更。
    //if (( prm_iYear < 1990 ) || ( prm_iYear > 2020 )) {
    if ( (prm_iYear < 1900) || (prm_iYear > 2040)) {
      return false;
    }

//++****************************************
//	月の判定（1～12月の範囲内であるか？）
//--****************************************
    if ( (prm_iMonth < 1) || (prm_iMonth > 12)) {
      return false;
    }

//++**********************************************
//	日の判定（1～月の最大日数の範囲内であるか？）
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
   *　タイプ　：　ユーザー定義<BR>
   *　処理名　：　月の最大日数の出力<BR>
   *　備　考　：　入力された年と月によってその月の最大日数を返す。<BR>
   *<BR>
   *　変更履歴<BR>
   *<BR>
   *　　Version　　日　付　　　更新者　　　　　コメント<BR>
   *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
   *　　00.00　　2002/06/01　宝来　幸弘　　新規作成<BR>
   *<BR>
   * @param   prm_iYear2　IN　	：　年データ
   * @param   prm_iMonth2　IN　：　月データ
   * @return 	月の最大日数を返す。
   */
  private int _getDate(
      int prm_iYear2,
      int prm_iMonth2
      ) {
    //++***********************************
    //	月の判定（最大日数が３０日？）
    //--***********************************
    if (prm_iMonth2 == 4 || prm_iMonth2 == 6 || prm_iMonth2 == 9 || prm_iMonth2 == 11) {
      return 30;
    }

    //++***********************************
    //	月の判定（2月以外の月か？）
    //--***********************************
    if (prm_iMonth2 != 2) {
      return 31;
    }

    //++***********************************
    //	月の判定（うるう年の２月か？）
    //--***********************************
    if (prm_iYear2 % 400 == 0 || ( (prm_iYear2 % 100 != 0) && (prm_iYear2 % 4 == 0))) {
      return 29;
    } else {
      return 28;
    }
  }

  /**
   *<BR>
   *　タイプ　：　プロパティ[ Field_IF_xxx_E ]: get<BR>
   *　処理名　：　プロパティ[ Field_IF_xxx_E ]の取得メソッド<BR>
   *　備　考　：　<BR>
   *<BR>
   *　変更履歴<BR>
   *<BR>
   *　　Version　　日　付　　　更新者　　　　　コメント<BR>
   *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
   *　　00.00　　2002/06/01　宝来　幸弘　　新規作成<BR>
   *<BR>
   */
  public String getField_Error() {

    //++******************************
    //	戻り値設定
    //--******************************
    return _sField_Error;

  }

  /**
   *<BR>
   *　タイプ　：　プロパティ[ Field_IF_xxx_E ]: set<BR>
   *　処理名　：　プロパティ[ Field_IF_xxx_E ]の設定メソッド<BR>
   *　備　考　：　<BR>
   *<BR>
   *　変更履歴<BR>
   *<BR>
   *　　Version　　日　付　　　更新者　　　　　コメント<BR>
   *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
   *　　00.00　　2002/06/01　宝来　幸弘　　新規作成<BR>
   *<BR>
   */
  public void setField_Error(
      String prm_sValue
      ) {
    //++******************************
    //	入力値を保存
    //--******************************
    _sField_Error = prm_sValue;

    //++************************
    //	文字色設定
    //--************************
    if (_sField_Error.equals(DefaultComp.FIELD_ERROR) == true) {
      setForeground(DefaultComp.FOREGROUND_COLOR_ERROR);
    } else {
      setForeground(DefaultComp.FOREGROUND_COLOR_NORMAL);
    }

    //++************************
    //	入力可否設定
    //--************************
    if (_sField_Error.equals(DefaultComp.FIELD_PROTECT) == true) {
      //++********************************
      //	プロテクト時のクリア処理
      //--********************************
      if (_bProtectClearFlag == true) {
        setValueText("");
      }

      //++********************************
      //	入力不可に設定
      //--********************************
      super.setEnabled(false);
      _setBackgroundColor();
    } else {
      //++********************************
      //	最終setEnabled設定要求値に設定
      //--********************************
      super.setEnabled(_bEnabled_Save);
      _setBackgroundColor();
    }

  }

  /**
   *<BR>
   *　タイプ　：　プロパティ[ Enabled ]: set<BR>
   *　処理名　：　プロパティ[ Enabled ]の設定メソッド<BR>
   *　備　考　：　setEnabledのオーバーライド関数<BR>
   *<BR>
   *　変更履歴<BR>
   *<BR>
   *　　Version　　日　付　　　更新者　　　　　コメント<BR>
   *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
   *　　00.00　　2002/06/01　宝来　幸弘　　新規作成<BR>
   *<BR>
   */
  public void setEnabled(
      boolean prm_bValue
      ) {

    //++****************************
    //	要求値保存
    //--****************************
    _bEnabled_Save = prm_bValue;

    //++**************************************
    //	入力不可の場合、処理キャンセル
    //--**************************************
    if (_sField_Error.equals(DefaultComp.FIELD_PROTECT) == true) {
      return;
    }

    //++****************************
    //	入力不可設定
    //--****************************
    super.setEnabled(prm_bValue);
    _setBackgroundColor();

  }

  /**
   *<BR>
   *　タイプ　：　ユーザー関数<BR>
   *　処理名　：　エラー状態設定処理<BR>
   *　備　考　：　<BR>
   *<BR>
   *　変更履歴<BR>
   *<BR>
   *　　Version　　日　付　　　更新者　　　　　コメント<BR>
   *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
   *　　00.00　　2002/06/01　宝来　幸弘　　新規作成<BR>
   *<BR>
   */
  public void setErrorField(
      boolean prm_bError
      ) {
    //++********************************
    //	プロテクト状態の場合、処理なし
    //--********************************
    if (_sField_Error.equals(DefaultComp.FIELD_PROTECT) == true) {
      return;
    }

    //++********************************
    //	エラー状態設定
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
      //if( Character.isDigit( chr ) ){ これでは全角の数字も通してしまう
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
   *　タイプ　：　イベント<BR>
   *　処理名　：　キー押下時の処理<BR>
   *　備　考　：　Enterキー押下でフィールド移動<BR>
   *<BR>
   *　変更履歴<BR>
   *<BR>
   *　　Version　　日　付　　　更新者　　　　　コメント<BR>
   *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
   *　　00.00　　2002/05/30　宝来　幸弘　　新規作成<BR>
   *<BR>
   */
  void this_keyPressed(KeyEvent e) {
    //Enterキー押下の場合TABキー押下にイベントを変更
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
      //	ダイアログ表示状態に設定
      //--****************************
      ComGlobal.g_clsCurrentPanel = ComGlobal.gClsCurrentPanelNative;
      clsFrame = comFORM.getBaseFrame();
      clsDialog = new dlgClarenderEx(clsFrame, "カレンダー", true);
      clsDialog.getContentPane().setBackground(dlgSeekColor);
      ComGlobal.g_clsDialogCalender = clsDialog;

      //++****************************
      //	ダイアログを表示
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
