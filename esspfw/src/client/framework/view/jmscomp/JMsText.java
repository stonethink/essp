package client.framework.view.jmscomp;

import java.awt.*;
import javax.swing.*;
import javax.swing.JTextField;
import java.awt.event.*;
import javax.swing.border.*;
import client.framework.view.common.*;

/**
 * <p>タイトル: JMsComp　（マイルストーンコンポーネント）</p>
 * <p>説明: javax.swing 継承のコンポーネント群</p>
 * <p>著作権: milestone Copyright (c) 2002</p>
 * <p>会社名: マイルストーン株式会社</p>
 * @author HORAI Yukihiro
 * @version 1.0
 */

public class JMsText extends JTextField {

    /** 変換モード指定定数*/
//	public static final String IM_NONE        = "指定なし";
//	public static final String IM_HIRAGANA    = "ひらがな";
//	public static final String IM_HALFKANA    = "半角カナ";
//	public static final String IM_FULLASCII   = "全角英数";
//	public static final String IM_OFF         = "直接入力";

    /**		入力文字判定用	*/
    public int         _iInputCharType;

    /**		フィールドエラー判定用	*/
    private String		_sField_Error   = null;

    /**		setEnabledでの最終要求値	*/
    private boolean		_bEnabled_Save;

    /**		プロテクト・クリア・フラグ	*/
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
     *　タイプ　：　初期化<BR>
     *　処理名　：　初期値設定処理<BR>
     *　備　考　：　<BR>
     *<BR>
     *　変更履歴<BR>
     *<BR>
     *　　Version　　日　付　　　更新者　　　　　コメント<BR>
     *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
     *　　00.00　　2002/05/30　宝来　幸弘　　新規作成<BR>
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
     *　タイプ　：　初期化<BR>
     *　処理名　：　ユーザ初期値設定処理<BR>
     *　備　考　：　<BR>
     *<BR>
     *　変更履歴<BR>
     *<BR>
     *　　Version　　日　付　　　更新者　　　　　コメント<BR>
     *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
     *　　00.00　　2002/05/30　宝来　幸弘　　新規作成<BR>
     *<BR>
     */
    private void initBeanUser() throws Exception {

        //++****************************
        //	初期値設定
        //--****************************
        this._sField_Error	= "";
        _sField_Error	= "";
//		setKey( false );
//		setProtectClear( false );


        //++****************************
        //	文字情報設定
        //--****************************
//		setAutoIME( true );
//		setInputStyle ( defComponent.INPUT_STYLE );
        setFont( DefaultComp.TEXT_FONT );
        this.setSelectedTextColor( DefaultComp.FOREGROUND_COLOR_SELECT );
        this.setSelectionColor( DefaultComp.BACKGROUND_COLOR_SELECT );
        this.setDisabledTextColor( DefaultComp.DISABLED_FONT_COLOR );

        //++****************************
        //	プロパティ初期値
        //--****************************
        //setConvUpperCase( false );
        //this.setInput2ByteOk( false );
        //this.setInputMode( IM_OFF );
        //this.setInputChar( "通常文字と数字のみ" );
        //this.setMaxByteLength( DefaultComp.TEXT_MAX_BYTE_LENGTH );
/* DEL S   20040803 Tsuka ***
        this.isFocusTraversable();
 * DEL E   20040804 Tsuka **/
        //this.isFocusable();
        //this.setMaxNextFoucs( false );



        //++****************************
        //	背景色設定
        //--****************************
        _setBackgroundColor();

        //++****************************
        //	その他設定
        //--****************************
//		setSelectedInFocus( true );
        setEnabled( true );
//		setModified( false );

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
        if ( isEnabled() == true ) {
            setBackground( DefaultComp.BACKGROUND_COLOR_ENABLED );
        } else {
            setBackground( DefaultComp.BACKGROUND_COLOR_DISABLED );
            this.setForeground(DefaultComp.FOREGROUND_COLOR_NORMAL);
        }
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
        String  sStr;

        //++****************************
        //	入力不可時、フィールド移動
        //--****************************
        if ( isEnabled() == false ) {
            return;
        }

        //++****************************
        //	IMEモードの自動変更
        //--****************************
        changeInputMode();

        //表示内容の一時対退避
        sStr    = this.getText();

        //++****************************
        //	入力文字制御を判定
        //--****************************
        _InputChar();

        //++****************************
        //	文字挿入形式の制御
        //--****************************
        setDocument(new InputDocument( getMaxByteLength(),  //最大入力桁数
                                       _iInputCharType,     //コンポーネントタイプ
                                       isInput2ByteOk()     //２byte文字のtrue/false
                                       ));


         //表示内容を退避から復元
         this.setText( sStr );


        //++****************************
        //	全選択状態
        //--****************************
        this.selectAll();

        //++****************************
        //	背景色設定
        //--****************************
        setBackground( DefaultComp.BACKGROUND_COLOR_INPUT_ACTIVE );
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
        String  sStr;
        //++****************************
        //	背景色設定
        //--****************************
        _setBackgroundColor();

        //++****************************
        //	大文字変換処理
        //--****************************
//		if ( this.isConvUpperCase() == true ) {
//			sStr	= getText().toUpperCase();
//			setText( sStr );
//		}

        //++****************************
        //	選択状態を解除
        //--****************************
        this.setSelectionStart(0);
        setSelectionEnd(0);
    }

    /**
     *<BR>
     *　処理名　：　文字変換モードの切り替え<BR>
     *　備　考　：　画面プロパティより InputModeを取得<BR>
     *<BR>
     *　変更履歴<BR>
     *<BR>
     *　　Version　　日　付　　　更新者　　　　　コメント<BR>
     *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
     *　　00.00　　2002/05/30　宝来　幸弘　　新規作成<BR>
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
     *　処理名　：　パラメータ変換処理<BR>
     *　備　考　：getconvUpperCase()で取得した値からbooleanに判定<BR>
     *<BR>
     *　変更履歴<BR>
     *<BR>
     *　　Version　　日　付　　　更新者　　　　　コメント<BR>
     *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
     *　　00.00　　2002/05/30　宝来　幸弘　　新規作成<BR>
     *<BR>
     */
    public void _InputChar() {
//		if ( getInputChar().equals( "全て" ) == true ) {
//			_iInputCharType = 0;
//		} else if ( getInputChar().equals( "数字のみ" ) == true ) {
//			_iInputCharType = 1;
//		} else if ( getInputChar().equals( "通常文字のみ" ) == true ) {
//			_iInputCharType = 2;
//		} else if ( getInputChar().equals( "通常文字と数字のみ" ) == true ) {
//			_iInputCharType = 3;
//		} else {
//			_iInputCharType = 2;
//		}
    }


    /**
     *<BR>
     *　タイプ　：　プロパティ[ Field_Error ]: get<BR>
     *　処理名　：　プロパティ[ Field_Error ]の取得メソッド<BR>
     *　備　考　：　<BR>
     *<BR>
     *　変更履歴<BR>
     *<BR>
     *　　Version　　日　付　　　更新者　　　　　コメント<BR>
     *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
     *　　00.00　　2002/05/30　宝来　幸弘　　新規作成<BR>
     *<BR>
     */
    public String getField_Error () {
        //++******************************
        //	戻り値設定
        //--******************************
        return this._sField_Error;
    }

    /**
     *<BR>
     *　タイプ　：　プロパティ[ Field_Error ]: set<BR>
     *　処理名　：　プロパティ[ Field_Error ]の設定メソッド<BR>
     *　備　考　：　<BR>
     *<BR>
     *　変更履歴<BR>
     *<BR>
     *　　Version　　日　付　　　更新者　　　　　コメント<BR>
     *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
     *　　00.00　　2002/05/30　宝来　幸弘　　新規作成<BR>
     *<BR>
     */
    public void setField_Error (
        String		prm_sValue
    ) {
        //++******************************
        //	入力値を保存
        //--******************************
        _sField_Error	= prm_sValue;

        //++************************
        //	文字色設定
        //--************************
        if ( _sField_Error.equals( DefaultComp.FIELD_ERROR ) == true ) {
            setForeground( DefaultComp.FOREGROUND_COLOR_ERROR );
        } else {
            setForeground( DefaultComp.FOREGROUND_COLOR_NORMAL );
        }

        //++************************
        //	入力可否設定
        //--************************
        if ( _sField_Error.equals( DefaultComp.FIELD_PROTECT ) == true ) {
            //++********************************
            //	プロテクト時のクリア処理
            //--********************************
            if ( _bProtectClearFlag == true ) {
                setText( "" );
            }

            //++********************************
            //	入力不可に設定
            //--********************************
            super.setEnabled( false );
            _setBackgroundColor();
        } else {
            //++********************************
            //	最終setEnabled設定要求値に設定
            //--********************************
            super.setEnabled( _bEnabled_Save );
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
     *　　00.00　　2002/05/30　宝来　幸弘　　新規作成<BR>
     *<BR>
     */
    public void setEnabled(
        boolean		prm_bValue
    ) {

        //++****************************
        //	要求値保存
        //--****************************
        _bEnabled_Save	= prm_bValue;

        //++**************************************
        //	入力不可の場合、処理キャンセル
        //--**************************************
        if ( _sField_Error.equals( DefaultComp.FIELD_PROTECT ) == true ) {
            return;
        }

        //++****************************
        //	入力不可設定
        //--****************************
        super.setEnabled( prm_bValue );
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
     *　　00.00　　2002/05/30　宝来　幸弘　　新規作成<BR>
     *<BR>
     */
    public void setErrorField(
        boolean		prm_bError
    ) {
        //++********************************
        //	プロテクト状態の場合、処理なし
        //--********************************
        if ( _sField_Error.equals( DefaultComp.FIELD_PROTECT ) == true ) {
            return;
        }

        //++********************************
        //	エラー状態設定
        //--********************************
        if ( prm_bError == true ) {
            setField_Error( DefaultComp.FIELD_ERROR );
        } else {
            setField_Error( "" );
        }
    }






    /**
     *<BR>
     *　タイプ　：　setTextのオーバーライド関数<BR>
     *　備　考　：　大文字変換プロパティを調べ、変換後setText<BR>
     *<BR>
     *　変更履歴<BR>
     *<BR>
     *　　Version　　日　付　　　更新者　　　　　コメント<BR>
     *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
     *　　00.00　　2002/05/30　宝来　幸弘　　新規作成<BR>
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
     *　処理名　：　パラメータ取得・設定処理<BR>
     *　備　考　：　jbuilderに<BR>
     *<BR>
     *　変更履歴<BR>
     *<BR>
     *　　Version　　日　付　　　更新者　　　　　コメント<BR>
     *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
     *　　00.00　　2002/05/30　宝来　幸弘　　新規作成<BR>
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
         *　タイプ　：　イベント<BR>
         *　処理名　：　キー押下時の処理<BR>
         *　備　考　：　Enterキー押下でフィールド移動<BR>
         *<BR>
         *　変更履歴<BR>
         *<BR>
         *　　Version　　日　付　　　更新者　　　　　コメント<BR>
         *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
         *　　00.00　　2002/05/30　宝来　幸弘　　新規作成<BR>
         *   00.01   2004/11/30  Yery        Enterイベントの処理を削除
         *<BR>
         */
    protected void this_keyPressed(KeyEvent e) {
        int iMax = 0;
        int iInp = 0;
                //ここのENTERイベントで問題を発生するので、削除するはずです。comFORM.setEnterOrder()を参照ください
//		if ( e.getKeyCode() == KeyEvent.VK_ENTER ) {
//			this.transferFocus();
//		}
        /* else {
System.out.println( "VK_0 : " +  e.VK_0 );
System.out.println( "VK_9 : " +  e.VK_9 );
System.out.println( "VK_A : " +  e.VK_A );
System.out.println( "VK_Z : " +  e.VK_Z );

            //最大桁数入力された場合に、次のフィールドに飛ぶ仕組み（2002.12.26  horai）

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
