package client.framework.view.jmscomp;

import java.awt.*;
import javax.swing.JTextField;
import java.awt.event.*;
import java.text.*;
import javax.swing.*;
import javax.swing.border.*;
import client.framework.view.common.*;

/**
 * <p>タイトル: JMsComp </p>
 * <p>説明: Javax.Swing継承のオリジナルコンポーネント群</p>
 * <p>著作権: milestone Copyright (c) 2002</p>
 * <p>会社名: マイルストーン株式会社</p>
 * @author 未入力
 * @version 1.0
 */

public class JMsNumber extends JTextField {

    /**		入力文字判定用	*/
    public int         _iInputCharType;

    /**		フィールドエラー判定用	*/
    private String		_sField_Error   = null;

    /**		setEnabledでの最終要求値	*/
    private boolean		_bEnabled_Save;

        /**		プロテクト・クリア・フラグ	*/
        private boolean _bProtectClearFlag;
        private boolean canNegative = false; //add by xh

    BorderLayout borderLayout1 = new BorderLayout();
    private int MaxInputIntegerDigit;
    private int maxInputIntegerDigit;
    Border border1;

    public JMsNumber() {
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
     *　タイプ　：　初期化<BR>
     *　処理名　：　初期値設定処理<BR>
     *　備　考　：　<BR>
     *<BR>
     *　変更履歴<BR>
     *<BR>
     *　　Version　　日　付　　　更新者　　　　　コメント<BR>
     *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
     *　　00.00　　2002/05/31　宝来　幸弘　　新規作成<BR>
     *<BR>
     */
    private void jbInit() throws Exception {
        border1 = BorderFactory.createLineBorder(Color.black,2);
        this.setBorder(BorderFactory.createLoweredBevelBorder());
        this.setDisabledTextColor(Color.black);
        this.setHorizontalAlignment(SwingConstants.RIGHT);
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
     *　　00.00　　2002/05/31　宝来　幸弘　　新規作成<BR>
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
        setFont( DefaultComp.NUMBER_FONT );
        this.setSelectedTextColor( DefaultComp.FOREGROUND_COLOR_SELECT );
        this.setSelectionColor( DefaultComp.BACKGROUND_COLOR_SELECT );
        this.setDisabledTextColor( DefaultComp.DISABLED_FONT_COLOR );

        //++****************************
        //	プロパティ初期値
        //--****************************
        this.setMaxInputIntegerDigit( DefaultComp.NUMBER_MAX_INTEGER_DIGIT );
        this.setValue( 0 );
        this.setFont(DefaultComp.NUMBER_FONT);
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
                //ここのENTERイベントで問題を発生するので、削除するはずです。comFORM.setEnterOrder()を参照ください
        //Enterキー押下の場合TABキー押下にイベントを変更
//		if ( e.getKeyCode() == KeyEvent.VK_ENTER ) {
//			this.transferFocus();
//		}
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
     *　　00.00　　2002/05/31　宝来　幸弘　　新規作成<BR>
     *<BR>
     */
    void this_focusGained(FocusEvent e) {
        String    sOldStr;

        //++****************************
        //	入力不可時、フィールド移動
        //--****************************
        if ( isEnabled() == false ) {
            return;
        }

        //++****************************
        //	入力変換（→直接入力）
        //--****************************
        getInputContext().setCharacterSubsets( null );

        //++****************************
        //	背景色設定
        //--****************************
        _setBackgroundColor();

        //++****************************
        //	表示内容保存
        //--****************************
        sOldStr    = this.getText();

                //modify
        /*setDocument(new InputDocument( 100,//','がついた場合に、どのくらい増えるかわからないので100にセット
                                       4,
                                       getMaxInputIntegerDigit(),
                                       0 ));  //整数フィールドなので、小数桁0
                */
               InputDocument document = new InputDocument( 100,//','がついた場合に、どのくらい増えるかわからないので100にセット
                                                                           4,
                                                                           getMaxInputIntegerDigit(),
                                                                           0 );  //整数フィールドなので、小数桁0
               document.setCanNegative(this.canNegative());
               setDocument(document);

        //++****************************
        //	表示内容復活
        //--****************************
        this.setText( removeNonNumeric( sOldStr ) );

        //++****************************
        //	背景色設定
        //--****************************
        setBackground( DefaultComp.BACKGROUND_COLOR_INPUT_ACTIVE );

        //++****************************
        //	全選択状態
        //--****************************
        this.selectAll();

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
        String  sStr1;
        String  sStr2;
        double  dvalue;

        //++****************************
        //	背景色設定
        //--****************************
        _setBackgroundColor();

        if ( getText().trim().equals( "-" ) == false ) {
           if ( getText().trim().equals( "." ) == false ) {

                StringBuffer sbuff = new StringBuffer();
                FieldPosition fpos = new FieldPosition(DecimalFormat.INTEGER_FIELD);

//System.out.println( "focusLost getText()  : " + getText()  );
                this.setText2( getText() );
           }
        }

        //++****************************
        //	選択状態を解除
        //--****************************
        this.setSelectionStart(0);
        setSelectionEnd(0);

    }

    /**
     *<BR>
     *　タイプ　：　プロパティ[ setText ]: set<BR>
     *　処理名　：　プロパティ[ setText ]の設定メソッド<BR>
     *　備　考　：　setTextのオーバーライド関数<BR>
     *<BR>
     *　変更履歴<BR>
     *<BR>
     *　　Version　　日　付　　　更新者　　　　　コメント<BR>
     *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
     *　　00.00　　2002/05/30　宝来　幸弘　　新規作成<BR>
     *<BR>
     */
    public void setText (String sStr ) {

            //modify
        /*setDocument(new InputDocument( 100,//','がついた場合に、どのくらい増えるかわからないので100にセット
                                       4,
                                       getMaxInputIntegerDigit(),
                                       0 ));  //整数フィールドなので、小数桁0
                */
               InputDocument document = new InputDocument( 100,//','がついた場合に、どのくらい増えるかわからないので100にセット
                                                                           4,
                                                                           getMaxInputIntegerDigit(),
                                                                           0 );  //整数フィールドなので、小数桁0
               document.setCanNegative(this.canNegative());
               setDocument(document);
        super.setText( sStr );
  }



    /**
     *<BR>
     *　タイプ　：　プロパティ[ setText ]: set<BR>
     *　処理名　：　プロパティ[ setText ]の設定メソッド<BR>
     *　備　考　：　setTextのオーバーライド関数<BR>
     *<BR>
     *　変更履歴<BR>
     *<BR>
     *　　Version　　日　付　　　更新者　　　　　コメント<BR>
     *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
     *　　00.00　　2002/05/30　宝来　幸弘　　新規作成<BR>
     *<BR>
     */
    public void setText2 (String sStr ) {

        //modify
        /*setDocument(new InputDocument( 100,//','がついた場合に、どのくらい増えるかわからないので100にセット
                                           0,
                                           getMaxInputIntegerDigit(),
                                           0 ));  //整数フィールドなので、小数桁0
            */
           InputDocument document = new InputDocument( 100,//','がついた場合に、どのくらい増えるかわからないので100にセット
                                                                       0,
                                                                       getMaxInputIntegerDigit(),
                                                                       0 );  //整数フィールドなので、小数桁0
           document.setCanNegative(this.canNegative());
           setDocument(document);

        super.setText( fromatFractionDigits( removeNonNumeric( sStr ) ) );
  }

    /**
     *<BR>
     *　タイプ　：　ユーザー関数<BR>
     *　処理名　：　文字列操作処理<BR>
     *　備　考　：　数字及び「.」以外を文字列から外す<BR>
     *<BR>
     *　変更履歴<BR>
     *<BR>
     *　　Version　　日　付　　　更新者　　　　　コメント<BR>
     *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
     *　　00.00　　2002/05/30　宝来　幸弘　　新規作成<BR>
     *<BR>
     */
    private String removeNonNumeric( String oldStr ){
        StringBuffer newStr = new StringBuffer();

        boolean bFrg  = false;
        char char2;

        for( int i=0 ; i < oldStr.length() ; i++ ){
            char chr = oldStr.charAt(i);
            //if( Character.isDigit( chr ) ){ これでは全角の数字も通してしまう
            if( ( '0' <= chr && chr <= '9' ) || chr == '.' || chr == '-' ){
                try {
                    newStr.append( chr );
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }

        return( newStr.toString() );
    }

    /**
     *<BR>
     *　タイプ　：　ユーザー関数<BR>
     *　処理名　：　文字列数値列変更操作<BR>
     *　備　考　：　<BR>
     *<BR>
     *　変更履歴<BR>
     *<BR>
     *　　Version　　日　付　　　更新者　　　　　コメント<BR>
     *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
     *　　00.00　　2002/05/30　宝来　幸弘　　新規作成<BR>
     *<BR>
     */
    public String fromatFractionDigits (String prm_sStr ) {
        String  sStr1;
        String  sStr2;
        double  dvalue;
        StringBuffer sbuff = new StringBuffer();
        FieldPosition fpos = new FieldPosition(DecimalFormat.INTEGER_FIELD);

        if ( prm_sStr.equals( "" ) == false ) {

            //dvalue  = Long.parseLong( prm_sStr ); replaced by yery on 2004/12/22
                        dvalue  = Long.parseLong( prm_sStr.replaceAll(",",""));
            DecimalFormat df = new DecimalFormat();

            df.setMinimumFractionDigits( 0 );
            df.format( dvalue, sbuff, fpos );

            return sbuff.toString();
        } else {
            return "";
        }

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
            setForeground( DefaultComp.FOREGROUND_COLOR_NORMAL );
        }
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
    public void clearText(
    ) {
        //++********************************
        //	表示値をクリアする
        //--********************************
        this.setText( "" );
    }


    /**
     *<BR>
     *　タイプ　：　ユーザー関数<BR>
     *　処理名　：　表示データ取得処理<BR>
     *　備　考　：　<BR>
     *<BR>
     *　変更履歴<BR>
     *<BR>
     *　　Version　　日　付　　　更新者　　　　　コメント<BR>
     *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
     *　　00.00　　2002/05/30　宝来　幸弘　　新規作成<BR>
     *<BR>
     */
    public long getValue(
    ) {
        long	lValue;
        Long    ltemp;
        String  sStr;

        sStr	= getText();

        //入力が無かった場合、0を返す
        if ( sStr.equals( "" ) == true ) {
            ltemp	= Long.valueOf( "0" );
        } else {
            sStr    = removeNonNumeric( sStr );
            ltemp	= Long.valueOf( sStr );
        }

        lValue	= ltemp.longValue();

        return lValue;
    }



    /**
     *<BR>
     *　タイプ　：　ユーザー関数<BR>
     *　処理名　：　表示データ取得処理<BR>
     *　備　考　：　<BR>
     *<BR>
     *　変更履歴<BR>
     *<BR>
     *　　Version　　日　付　　　更新者　　　　　コメント<BR>
     *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
     *　　00.00　　2002/05/30　宝来　幸弘　　新規作成<BR>
     *<BR>
     */
    public void setValue(
        long	prm_lValue
    ) {
        String  sStr;

        sStr	= Long.toString( prm_lValue );
        this.setText2( sStr );
    }


    /**
     *<BR>
     *　タイプ　：　ユーザー関数<BR>
     *　処理名　：　表示データ取得処理<BR>
     *　備　考　：　<BR>
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
     *　タイプ　：　ユーザー定義<BR>
     *　処理名　：　入力判定<BR>
     *　備　考　：　<BR>
     *<BR>
     *　変更履歴<BR>
     *<BR>
     *　　Version　　日　付　　　更新者　　　　　コメント<BR>
     *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
     *　　00.00　　2002/05/30　宝来　幸弘　　新規作成<BR>
     *<BR>
     * @return 	0:OK 1:未入力  <0:エラー
     */
    public int checkValue(
    ) {
        String		sStr;
        long		lNum;

        sStr	= getText();

        //++****************************
        //	null判定
        //--****************************
        if ( sStr == null ) {
            return 1;
        }

        //++****************************
        //	未入力判定判定
        //--****************************
        if ( sStr.trim().length() == 0 ) {
            return 1;
        }

        //++****************************
        //	数値判定
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

        public boolean canNegative(){
            return this.canNegative;
        }

        public void setCanNegative(boolean canNegative){
            this.canNegative = canNegative;
    }

}
