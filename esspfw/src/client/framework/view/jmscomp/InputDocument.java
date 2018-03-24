package client.framework.view.jmscomp;

import javax.swing.text.*;

public class InputDocument extends PlainDocument {

    public static final int ALL = 0; //全て
    public static final int NUMERIC = 1; //数字のみ
    public static final int ALPHA = 2; //通常文字のみ
    public static final int ALPHA_NUMERIC = 3; //通常文字と数字のみ
    public static final int INTEGER = 4; //整数（数字、＋、－）
    public static final int FLOAT = 5; //小数（数字、＋、－、小数点）

    int itype = ALL;
    boolean b2byte = false;
    boolean canNegative = true;
    int periodCount = 0;

    int giLimit; // 桁数制限
    int iMaxInputIntegerDigit = 0;
    int iMaxInputDecimalDigit = 0;

    String[] gsLimitedStr = {"\'", ";"}; // 禁止文字列配列


    /**
     *<BR>
     *　タイプ　：　ユーザー定義<BR>
     *　処理名　：　InputDocument<BR>
     *　備　考　：　<BR>
     *<BR>
     *　変更履歴<BR>
     *<BR>
     *　　Version　　日　付　　　更新者　　　　　コメント<BR>
     *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
     *　　00.00　　2002/05/28　宝来　幸弘　　新規作成<BR>
     *<BR>
     * @param   iLimitDir　　IN　：　最大入力byte数
     * @param   type　　     IN　：　コンポーントタイプ
     * @param   　　　　　　　IN　：　ディレクトリ名
     * @param   　　　　　　　IN　：　ディレクトリ名
     * @return  true　：　存在<BR>false　：　存在しない
     */
    public InputDocument(int prm_iLimit,
                         int prm_type,
                         boolean prm_b2byte
            ) {
        if (prm_iLimit == -1) {
            this.giLimit = 999;
        } else {
            this.giLimit = prm_iLimit;
        }

        this.itype = prm_type;
        this.b2byte = prm_b2byte;
    }

    /**
     *<BR>
     *　タイプ　：　ユーザー定義<BR>
     *　処理名　：　InputDocument<BR>
     *　備　考　：　<BR>
     *<BR>
     *　変更履歴<BR>
     *<BR>
     *　　Version　　日　付　　　更新者　　　　　コメント<BR>
     *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
     *　　00.00　　2002/05/28　宝来　幸弘　　新規作成<BR>
     *<BR>
     * @param   iLimitDir　　IN　：　最大入力byte数
     * @param   type　　     IN　：　コンポーントタイプ（シンボル参照）
     * @param   　　　　　　　IN　：　ディレクトリ名
     * @param   　　　　　　　IN　：　ディレクトリ名
     * @return  true　：　存在<BR>false　：　存在しない
     */
    public InputDocument(int prm_iLimit,
                         int prm_type,
                         int prm_iMaxInteger,
                         int prm_iMaxDecimal
            ) {
        //-1の場合、入力桁数を999に置き換え
        if (prm_iLimit == -1) {
            this.giLimit = 999;
        } else {
            this.giLimit = prm_iLimit;
        }

//2002.07.09  MS(HORAI)
//型が整数型の場合、ＭＡＸ桁数を変更
        if (prm_type == INTEGER) {
            this.giLimit = prm_iMaxInteger;
        }
//2002.07.09  MS(HORAI)



        this.itype = prm_type;

        if (prm_iMaxInteger == -1) {
            this.iMaxInputIntegerDigit = 999;
        } else {
            this.iMaxInputIntegerDigit = prm_iMaxInteger;
        }

        if (prm_iMaxDecimal == -1) {
            this.iMaxInputDecimalDigit = 999;
        } else {
            this.iMaxInputDecimalDigit = prm_iMaxDecimal;
        }

    }


    /**
     *<BR>
     *　処理名　：　insertString<BR>
     *　備　考　：　<BR>
     *<BR>
     *　変更履歴<BR>
     *<BR>
     *　　Version　　日　付　　　更新者　　　　　コメント<BR>
     *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
     *　　00.00　　2002/05/28　宝来　幸弘　　新規作成<BR>
     *<BR>
     * @param   最大入力文字数（byte)、
     * @return  なし
     */
    public void insertString(int iOff, String sStr, AttributeSet att) throws
            BadLocationException {
        String sCheckedStr = ""; //チェック済み文字列
        StringBuffer sbDspNowSeisu = new StringBuffer(); //現在表示整数部
        StringBuffer sbDspNowSyosu = new StringBuffer(); //現在表示小数部
        StringBuffer sbDspAftSeisu = new StringBuffer(); //挿入後整数部
        StringBuffer sbDspAftSyosu = new StringBuffer(); //挿入後小数部
        StringBuffer sbDspAftAll = new StringBuffer(); //入力後仮文字列
        boolean bDecPFlg = false; //小数点有無フラグ
        char chTemp; //１文字格納用
        int iDecPoint = 0; //小数点表示桁
        String sDspNowAll; //現在表示全体格納用
        int iPassByteLengthSeisu = 0; //追加入力可能数（整数）
        int iPassByteLengthSyosu = 0; //追加入力可能数（小数）
        int i;
        int j;
        boolean bRet;

        //add to support the none negative input
        String[] limitedStr = gsLimitedStr;
        if( this.canNegative() == false ){
            limitedStr = new String[gsLimitedStr.length+1];
            System.arraycopy(gsLimitedStr,0,limitedStr,0,gsLimitedStr.length);
            limitedStr[gsLimitedStr.length]="-";
        }
        String sLimitCheckedStr = deleteLimitedString(sStr, limitedStr);

        // 入力制限文字列削除
//        String sLimitCheckedStr = deleteLimitedString(sStr, gsLimitedStr);

        // 2byte文字制限
        if (b2byte == false) {
            bRet = check2Byte(sStr);
            if (bRet == true) {
                return;
            }
        }

        // 文字が空なら終了
        if (sLimitCheckedStr.length() == 0) {
            return;
        }

        // FEPが起動しているとき入力確定まではデフォルトの処理
        if ((att != null) &&
            (att.getAttribute(StyleConstants.ComposedTextAttribute) != null)) {
            super.insertString(iOff, sStr, att);
            return;
        }

        /* 文字列長制限 */
        // Documentオブジェクトの文字列のバイト配列を取得
        String sLenCheckStr = getText(0, getLength());
        byte[] bLenCheckStr = sLenCheckStr.getBytes();

        // 入力可能なバイト数取得
        int iPassByteLength = giLimit - bLenCheckStr.length;

//horai+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

        //コンポーネントタイプが実数タイプに設定されている場合
        if (itype == FLOAT) {

            sDspNowAll = getText(0, getLength());

            //文字数分回しながら１文字ずつ判定
            for (i = 0; i < sDspNowAll.length(); i++) {
                chTemp = sDspNowAll.charAt(i);
                if (chTemp != ',') {
                    if (chTemp == '.') {
//                        iPoffset = i;
                        bDecPFlg = true;
                    }

                    if (bDecPFlg == false) {
                        try {
                            sbDspNowSeisu = (StringBuffer) sbDspNowSeisu.append(
                                    chTemp);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        if (chTemp != '.') {
                            try {
                                sbDspNowSyosu = (StringBuffer) sbDspNowSyosu.
                                                append(chTemp);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }

//System.out.println( " 表示数列（整数） : " +  sbDspNowSeisu );
//System.out.println( " 表示数列（小数） : " +  sbDspNowSyosu );

            // 追加入力可能な整数列数を計算（★パラメータで最大を設定すること）
            iPassByteLengthSeisu = iMaxInputIntegerDigit -
                                   sbDspNowSeisu.toString().length();

            // 追加入力可能な小数列数を計算（★パラメータで最大を設定すること）
            iPassByteLengthSyosu = iMaxInputDecimalDigit -
                                   sbDspNowSyosu.toString().length();

//System.out.println( " 追加入力可能文字数（整数） : " +  iPassByteLengthSeisu );
//3System.out.println( " 追加入力可能文字数（小数） : " +  iPassByteLengthSyosu );

            //「表示部」「挿入部」「表示部」の並びで文字列を作成
            for (i = 0; i < iOff; i++) {
                chTemp = sDspNowAll.charAt(i);
                try {
                    sbDspAftAll = (StringBuffer) sbDspAftAll.append(chTemp);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            for (i = 0; i < sStr.length(); i++) {
                chTemp = sStr.charAt(i);
                try {
                    sbDspAftAll = (StringBuffer) sbDspAftAll.append(chTemp);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            for (i = iOff; i < sDspNowAll.length(); i++) {
                chTemp = sDspNowAll.charAt(i);
                try {
                    sbDspAftAll = (StringBuffer) sbDspAftAll.append(chTemp);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
//System.out.println( " 入力後の表示 : " + sbDspAftAll  );

            //小数点有無フラグを初期化
            bDecPFlg = false;

            //文字数分回しながら１文字ずつ判定
            for (i = 0; i < sbDspAftAll.toString().length(); i++) {
                chTemp = sbDspAftAll.toString().charAt(i);
                if (chTemp != ',') {
                    if (chTemp == '.') {
                        bDecPFlg = true;
                    }

                    if (bDecPFlg == false) {
                        try {
                            sbDspAftSeisu = (StringBuffer) sbDspAftSeisu.append(
                                    chTemp);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        if (chTemp != '.') {
                            try {
                                sbDspAftSyosu = (StringBuffer) sbDspAftSyosu.
                                                append(chTemp);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                } else {
                    iPassByteLength = iPassByteLength + 1;
                }
            }

//System.out.println( " 挿入後数列（整数） : " +  sbDspAftSeisu );
//System.out.println( " 挿入後数列（小数） : " +  sbDspAftSyosu );

            //挿入後整数桁数が、設定値を超える場合終了
            if (sbDspAftSeisu.toString().length() > iMaxInputIntegerDigit) {
//System.out.println( "整数部が設定値を超えたため終了" );
                return;
            }

            //挿入後小数桁数が、設定値を超える場合終了
            if (sbDspAftSyosu.toString().length() > iMaxInputDecimalDigit) {
//System.out.println( "小数部が設定値を超えたため終了" );
                return;
            }

        }

//horai-----------------------------------------------------------------------------
//System.out.println( " iPassByteLength : " + iPassByteLength  );
//System.out.println( " sLimitCheckedStr.length() : " + sLimitCheckedStr.length()  );
//System.out.println( " sLimitCheckedStr : " + sLimitCheckedStr  );

        for (i = 0; i < sLimitCheckedStr.length(); i++) {

            char ch = sStr.charAt(i);

            //数字（全角の数字も含む）
            if (itype == NUMERIC && Character.isDigit(ch) == false) {
                return;
            }

            //通常文字（全角文字含む）記号は含まない
            if (itype == ALPHA && Character.isLetter(ch) == false) {
                return;
            }

            //通常文字と数字だけ。記号は含まない。
            if (itype == ALPHA_NUMERIC && Character.isLetterOrDigit(ch) == false) {
                return;
            }

            //整数（数字、+、-）
            if (itype == INTEGER) {
                if (iOff == 0) {
                    if (Character.isDigit(ch) == false && ch != '-') {
                        return;
                    }
                }

                if (iOff > 0) {
                    if (Character.isDigit(ch) == false) {
                        return;
                    }
                }
            }

            //小数（数字、+、-、小数点）
            if (itype == FLOAT) {
                if (i == 0) {
                    if (iOff == 0) {
//horai+++++++++++++++++++++++++++++++++++++++++++++++++++
//入力の挿入先が先頭の場合（setText( "****" )含む）で入力文字の最初が '-' では無い場合return;
                        if (Character.isDigit(sStr.charAt(0)) == false &&
                            ch != '-') {
//System.out.println( "check-point-1" );
                            return;
                        }
                    }
//horai---------------------------------------------------

//                    if( Character.isDigit(ch)==false && ch!='-'  ) {
//                      return;
//                    }
                }

                if (iOff > 0) {
                    if (ch == '.') {

                        sDspNowAll = getText(0, getLength());

                        //文字数分回しながら１文字ずつ判定
                        for (j = 0; j < sDspNowAll.length(); j++) {
                            chTemp = sDspNowAll.charAt(j);
                            if (chTemp == '.') {
                                return;
                            }
                        }

                    } else {
                        if (Character.isDigit(ch) == false) {
//System.out.println( "check-point-2" );
                            return;
                        }
                    }
                }
            }

            String sSplitStr = sLimitCheckedStr.substring(i, i + 1);
            iPassByteLength -= sSplitStr.getBytes().length;

            if (iPassByteLength >= 0) {
                // 変更反映

                super.insertString(iOff + i, sSplitStr, att);
            } else {
                return;
            }
        }
    }


    private String deleteLimitedString(String sStr, String[] sLimitedStrings) {
        if (sStr == null) {
            return sStr;
        }

        String sLimitCheckStr = new String(sStr); // チェック用文字列作成
        try {

            // 基本禁止文字を削除
            // 改行コードが混入している場合改行コード以降は切り捨て
            int iLF = sLimitCheckStr.indexOf("\n");
            if (iLF >= 0) {
                sLimitCheckStr = sLimitCheckStr.substring(0, iLF);
            }
            int iCR = sLimitCheckStr.indexOf("\r");
            if (iCR >= 0) {
                sLimitCheckStr = sLimitCheckStr.substring(0, iCR);
            }

            // TABコードが混入している場合スペースに変換
            int iTAB = sLimitCheckStr.indexOf("\t");
            if (iTAB >= 0) {
                sLimitCheckStr = sLimitCheckStr.replace('\t', ' ');
            }

            // 禁止文字列チェック開始
            for (int i = 0; i < sLimitedStrings.length; i++) {
                int iLim; // 禁止文字列のインデックス
                while ((iLim = sLimitCheckStr.indexOf(sLimitedStrings[i])) >= 0) {
                    // 1文字なら空文字を返す
                    if (sLimitCheckStr.length() == 1) {
                        return new String("");
                        // 禁止文字を削除
                    } else {
                        StringBuffer sb = new StringBuffer(sLimitCheckStr);
                        sb = (StringBuffer) sb.deleteCharAt(iLim);
                        sLimitCheckStr = sb.toString();
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return sLimitCheckStr;
    }

    /**
     * 機能     : 全角文字存在判定
     * 戻り値   : 全角あり：true  全角なし:false
     * 引き数   :　チェック対象文字列
     * 機能説明 :
     * 備考     : 数値、日付
     */
    private boolean check2Byte(String prm_sCheck) {

        byte[] btValue;
        try {
            // Stringを日本語EUCのbyte[]に変換する。
            btValue = prm_sCheck.getBytes("EUCJIS");

            // 全角文字がふくまれている場合
            for (int i = 0; i < btValue.length; i++) {
                if (btValue[i] < 0) { // btValue[i]の8ビット目が立っている。
                    return true;
                }
            }
        } catch (Exception Err) {
        }

        return false;
    }

    public boolean canNegative() {
        return this.canNegative;
    }

    public void setCanNegative(boolean canNegative) {
        this.canNegative = canNegative;
    }
}
