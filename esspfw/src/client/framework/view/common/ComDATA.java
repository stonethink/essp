package client.framework.view.common;

import java.net.URLEncoder;
import java.text.*;
import java.io.OutputStreamWriter;
import java.util.Vector;
import java.util.ResourceBundle;
import java.util.Locale;
import java.util.Enumeration;
import client.framework.view.jmscomp.*;


/**
 *<BR>
 *　概要<BR>
 *<BR>
 *　　共通モジュール（データ関連）<BR>
 *<BR>
 *　変更履歴<BR>
 *<BR>
 *　　Version　　日　付　　　更新者　　　　　コメント<BR>
 *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
 *　　00.00　　2004/06/21　Tsukamoto　　新規作成<BR>
 *<BR>
 */
public class ComDATA {
       public static final String FILENAME="ftp";

	/**
	 *<BR>
	 *　タイプ　：　ユーザー定義<BR>
	 *　処理名　：　整数文字列型判定<BR>
	 *　備　考　：　指定文字列が整数型文字列かを判定する。<BR>
	 *<BR>
	 *　変更履歴<BR>
	 *<BR>
	 *　　Version　　日　付　　　更新者　　　　　コメント<BR>
	 *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
	 *　　00.00　　2004/06/21　Tsukamoto　　新規作成<BR>
	 *<BR>
	 * @param	sParam　　IN　：　判定文字列
	 * @return	true　：　ＯＫ<BR>false　：　エラー
	 */
	public static boolean checkNumber( String sParam2 ) {
		Long	lNum;
		String sParam = sParam2.trim();

		try {
			lNum	= Long.valueOf( sParam );
		} catch ( Exception clsExcept ) {
			return false;
		}

		return true;
	}

	/**
	 *<BR>
	 *　タイプ　：　ユーザー定義<BR>
	 *　処理名　：　実数文字列型判定<BR>
	 *　備　考　：　指定文字列が実数型文字列かを判定する。<BR>
	 *<BR>
	 *　変更履歴<BR>
	 *<BR>
	 *　　Version　　日　付　　　更新者　　　　　コメント<BR>
	 *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
	 *　　00.00　　2004/06/21　Tsukamoto　　新規作成<BR>
	 *<BR>
	 * @param	sParam　　IN　：　判定文字列
	 * @return	true　：　ＯＫ<BR>false　：　エラー
	 */
	public static boolean checkReal( String sParam2 ) {
		Double	dNum;
		String sParam = sParam2.trim();

		try {
			dNum	=  Double.valueOf( sParam );
		} catch ( Exception clsExcept ) {
			return false;
		}

		return true;
	}

	/**
	 *<BR>
	 *　タイプ　：　ユーザー定義<BR>
	 *　処理名　：　文字長判定<BR>
	 *　備　考　：　指定文字列が指定文字長かを判定する。<BR>
	 *<BR>
	 *　変更履歴<BR>
	 *<BR>
	 *　　Version　　日　付　　　更新者　　　　　コメント<BR>
	 *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
	 *　　00.00　　2004/06/21　Tsukamoto　　新規作成<BR>
	 *<BR>
	 * @param	sParam 　　IN　：　判定文字列
	 * @param	prm_iLength　IN　：　文字長
	 * @return	true　：　ＯＫ<BR>false　：　エラー
	 */
	public static boolean checkMustLength( String sPara, int iLength ) {
		try {
			if ( sPara.trim().length() != iLength ) {
				return false;
			}
		} catch ( Exception clsExcept ) {
			return false;
		}

		return true;

	}

	/**
	 *<BR>
	 *　タイプ　：　ユーザー定義<BR>
	 *　処理名　：　必須入力判定<BR>
	 *　備　考　：　<BR>
	 *<BR>
	 *　変更履歴<BR>
	 *<BR>
	 *　　Version　　日　付　　　更新者　　　　　コメント<BR>
	 *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
	 *　　00.00　　2004/06/21　Tsukamoto　　新規作成<BR>
	 *<BR>
	 * @param	sParam　　　IN　：　判定文字列
	 * @return	true　：　ＯＫ<BR>false　：　エラー
	 */
	public static boolean checkMustInput( String sPara ) {
		try {
			if ( sPara.trim().length() == 0 ) {
				return false;
			} else {
				return true;
			}
		} catch ( Exception clsExcept ) {
			return false;
		}
	}

	/**
	 *<BR>
	 *　タイプ　：　ユーザー定義<BR>
	 *　処理名　：　右スペース削除処理<BR>
	 *　備　考　：　<BR>
	 *<BR>
	 *　変更履歴<BR>
	 *<BR>
	 *　　Version　　日　付　　　更新者　　　　　コメント<BR>
	 *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
	 *　　00.00　　2004/06/21　Tsukamoto　　新規作成<BR>
	 *<BR>
	 * @param	sParam　　　IN　：　処理文字列
	 * @return	処理結果文字列
	 */
	public static String trimRight( String sParam ) {
		String	sStr;
		int	iLen;
		boolean	bNextFlag;

		sStr = sParam;

		try {
			bNextFlag	= true;
			while ( bNextFlag == true ) {
				bNextFlag	= false;
				iLen = sStr.length();
				if ( iLen > 0 ) {
					if (( sStr.lastIndexOf( " " )  == iLen - 1 )
					||  ( sStr.lastIndexOf( "　" ) == iLen - 1 )
					) {
						bNextFlag	= true;
						sStr = sStr.substring( 0, iLen - 1 );
					}
				}
			}
			return sStr;
		} catch ( Exception clsExcept ) {
			return "";
		}
	}

	/**
	 *<BR>
	 *　タイプ　：　ユーザー定義<BR>
	 *　処理名　：　左スペース削除処理<BR>
	 *　備　考　：　<BR>
	 *<BR>
	 *　変更履歴<BR>
	 *<BR>
	 *　　Version　　日　付　　　更新者　　　　　コメント<BR>
	 *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
	 *　　00.00　　2004/06/21　Tsukamoto　　新規作成<BR>
	 *<BR>
	 * @param	sParam　　　IN　：　処理文字列
	 * @return	処理結果文字列
	 */
	public static String trimLeft( String sParam ) {
		String	sStr;
		boolean	bNextFlag;

		sStr = sParam;

		try {
			bNextFlag = true;
			while ( bNextFlag == true ) {
				bNextFlag = false;
				if (( sStr.indexOf( " " )  == 0 )
				||  ( sStr.indexOf( "　" ) == 0 )
				) {
					bNextFlag = true;
					sStr = sStr.substring( 1 );
				}
			}
			return sStr;
		} catch ( Exception clsExcept ) {
			return "";
		}
	}

	/**
	 *<BR>
	 *　タイプ　：　ユーザー定義<BR>
	 *　処理名　：　数値データゼロ埋め処理<BR>
	 *　備　考　：　数値データが必要桁数に満たない場合左からゼロ埋めする<BR>
	 *<BR>
	 *　変更履歴<BR>
	 *<BR>
	 *　　Version　　日　付　　　更新者　　　　　コメント<BR>
	 *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
	 *　　00.00　　2002/07/19　松浦　寿彦　　新規作成<BR>
	 *　　00.00　　2003/01/06　宝来　幸弘　　parmeterをStringに変更<BR>
	 *<BR>
	 * @param	iValue		:数値データ
	 * @param	beam		:必要桁数
	 * @return	sValue		:ゼロ埋め後データ
	 */
	public static String  zeroFormat( String sValue2, int beam ){
		int iLength;
		String sValue=sValue2;

		iLength = sValue.length();
		for(int i = iLength; i < beam; i++){
			sValue = 0 + sValue;
		}

		return sValue;
	}

	/**
	 *<BR>
	 *　タイプ　：　ユーザー定義<BR>
	 *　処理名　：　数値データゼロ埋め処理<BR>
	 *　備　考　：　数値データが必要桁数に満たない場合左からゼロ埋めする<BR>
	 *<BR>
	 *　変更履歴<BR>
	 *<BR>
	 *　　Version　　日　付　　　更新者　　　　　コメント<BR>
	 *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
	 *　　00.00　　2002/07/19　松浦　寿彦　　新規作成<BR>
	 *<BR>
	 * @param	iValue		:数値データ
	 * @param	beam		:必要桁数
	 * @return	sValue		:ゼロ埋め後データ
	 */
	public static String  zeroFormat( int iValue, int beam ){
		String sValue = new String();
		int iLength;

		sValue = String.valueOf(iValue);
		iLength = sValue.length();
		for(int i = iLength; i < beam; i++){
			sValue = 0 + sValue;
		}
		return sValue;
	}


	/**
	 *<BR>
	 *　タイプ　：　ユーザー定義<BR>
	 *　処理名　：　数値データゼロ埋め処理<BR>
	 *　備　考　：　数値データが必要桁数に満たない場合左からゼロ埋めする<BR>
	 *<BR>
	 *　変更履歴<BR>
	 *<BR>
	 *　　Version　　日　付　　　更新者　　　　　コメント<BR>
	 *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
	 *　　00.00　　2002/07/19　松浦　寿彦　　新規作成<BR>
	 *<BR>
	 * @param	iValue		:数値データ
	 * @param	beam		:必要桁数
	 * @return	sValue		:ゼロ埋め後データ
	 */
	public static String  zeroFormat( long lValue, int beam){
		String sValue = new String();
		int iLength;

		sValue = String.valueOf(lValue);
		iLength = sValue.length();
		for(int i = iLength; i < beam; i++){
			sValue = 0 + sValue;
		}
		return sValue;
	}

	/**
	 *<BR>
	 *　タイプ　：　ユーザー定義<BR>
	 *　処理名　：　文字データスペース埋め処理<BR>
	 *　備　考　：　文字データが必要桁数に満たない場合、スペース埋めする<BR>
	 *<BR>
	 *　変更履歴<BR>
	 *<BR>
	 *　　Version　　日　付　　　更新者　　　　　コメント<BR>
	 *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
	 *　　00.00　　2004/09/02　庄崎　浩司　　新規作成<BR>
	 *<BR>
	 * @param	prm_sValue	:文字データ
	 * @param	prm_ibeam	:必要桁数
	 * @return	sValue		:スペース埋め後データ
	 */

	public static String  spaceFormat( String sValue2, int ibeam ){
		int iLength;
		String sValue=sValue2;

		byte[] bValue = sValue.getBytes();
		iLength = bValue.length;
		for(int i = iLength; i < ibeam; i++){
			sValue = sValue + " ";
		}

		return sValue;
	}

	/**
	 *<BR>
	 *　タイプ　：　ユーザー定義<BR>
	 *　処理名　：　コード入力判定処理<BR>
	 *　備　考　：　エラー時メッセージを表示時し、該当フィールドにフォーカスを設定<BR>
	 *<BR>
	 *　変更履歴<BR>
	 *<BR>
	 *　　Version　　日　付　　　更新者　　　　　コメント<BR>
	 *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
	 *　　00.00　　2002/12/26　宝来　幸弘　　新規作成<BR>
	 *<BR>
	 * @param	なし
	 * @return	true　：　ＯＫ<BR>false　：　エラーあり
	 */
	public static boolean checkInput( JMsText jmsText, String msg ) {
		String	sCheckStr;	// 判定文字列
		int	iMaxLength;	// 最大文字数
		boolean	bRet;		// 戻り値

		//++************************
		//	桁数判定
		//--************************
		sCheckStr	= jmsText.getText().trim();
		iMaxLength	= jmsText.getMaxByteLength();
		if ( sCheckStr.trim().length() > 0 ) {
			bRet = ComDATA.checkMustLength( sCheckStr, iMaxLength );
			if ( bRet == false ) {
				jmsText.setErrorField( true );
				comMSG.dispErrorDialog( msg + "は " + String.valueOf( iMaxLength ) + " 桁必須入力です。" );
				comFORM.setFocus( jmsText );
				return false;
			}
		}

		return true;
	}

	/**
	 *<BR>
	 *　タイプ　：　ユーザー定義<BR>
	 *　処理名　：　コード入力判定処理（日付）<BR>
	 *　備　考　：　エラー時メッセージを表示時し、該当フィールドにフォーカスを設定<BR>
	 *<BR>
	 *　変更履歴<BR>
	 *<BR>
	 *　　Version　　日　付　　　更新者　　　　　コメント<BR>
	 *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
	 *　　00.00　　2002/12/26　宝来　幸弘　　新規作成<BR>
	 *<BR>
	 * @param	なし
	 * @return	true　：　ＯＫ<BR>false　：　エラーあり
	 */
	public static boolean checkInput( JMsDate jmsDate, String msg ) {
		int iRet ; //判定用

		iRet = jmsDate.checkValue();
		if ( iRet < 0 ) {
			jmsDate.setErrorField( true );
			comMSG.dispErrorDialog( msg + "の入力値が不正です。" );
			comFORM.setFocus( jmsDate );
			return false;
		}
		return true;
	}

	/**
	 *<BR>
	 *　タイプ　：　ユーザー定義<BR>
	 *　処理名　：　name = value 書式設定処理<BR>
	 *　備　考　：　<BR>
	 *<BR>
	 *　変更履歴<BR>
	 *<BR>
	 *　　Version　　日　付　　　更新者　　　　　コメント<BR>
	 *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
	 *　　00.00　　2002/06/13　宝来　幸弘　　新規作成<BR>
	 *<BR>
	 * @param	prm_name　　　 IN　：　name部
	 * @param	prm_value　　　IN　：　value部
	 * @param	prm_sep　　　IN　：　
	 * @return	prm_name=
	 */
	public static String encString(String name, String value ) {
		String sep;
		String enc = "SJIS";
		sep = "&";

		try {
			StringBuffer sb = new StringBuffer();
			sb.append( URLEncoder.encode(name, enc) + "=" + URLEncoder.encode(value,enc) + sep );
			return sb.toString();
		} catch(Exception e) {
			return "";
		}
	}


	/**
	 *<BR>
	 *　タイプ　：　ユーザー定義<BR>
	 *　処理名　：　name = value 書式設定処理<BR>
	 *　備　考　：　配列型でクライアントサーバ間通信を行うときに使用
	 *<BR>
	 *　変更履歴<BR>
	 *<BR>
	 *　　Version　　日　付　　　更新者　　　　　コメント<BR>
	 *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
	 *　　00.00　　2002/06/13　宝来　幸弘　　新規作成<BR>
	 *<BR>
	 * @param	prm_name　　　 IN　：　name部
	 * @param	prm_value　　　IN　：　value部
	 * @param	prm_sep　　　IN　：　
	 * @return	prm_name=
	 */
	public static String encStringAry(String name, int count, String value ) {
		String sep;
		String enc = "SJIS";
		sep = "&";

		try {
			StringBuffer sb = new StringBuffer();
			sb.append( URLEncoder.encode(name, enc) +
				   "[" + String.valueOf( count ) + "]" +
				   "=" + URLEncoder.encode(value, enc) + sep );
			return sb.toString();
		} catch(Exception e) {
			return "";
		}
	}

	/**
	 *<BR>
	 *　タイプ　：　ユーザー定義<BR>
	 *　処理名　：　name = value 書式設定処理<BR>
	 *　備　考　：　配列型でクライアントサーバ間通信を行うときに使用
	 *<BR>
	 *　変更履歴<BR>
	 *<BR>
	 *　　Version　　日　付　　　更新者　　　　　コメント<BR>
	 *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
	 *　　00.00　　2002/12/30　YangChuan　　新規作成<BR>
	 *<BR>
	 * @param	name　　　 IN　：　name部
	 * @param	value　　　IN　：　value部
	 * @param	sep　　　IN　：　
	 * @return	name=
	 */
	public static String encStringAry2(String name, int count, int count1, String value ) {
		String sep;
		String enc = "SJIS";
		sep = "&";

		try {
			StringBuffer sb = new StringBuffer();
			sb.append( URLEncoder.encode(name, enc) +
				   "[" + String.valueOf( count ) + "]" +
				   "[" + String.valueOf( count1 ) + "]" +
				   "=" + URLEncoder.encode(value, enc) + sep );
			return sb.toString();
		} catch(Exception e) {
			return "";
		}
	}

	/**
	 *<BR>
	 *　タイプ　：　ユーザー定義<BR>
	 *　処理名　：　name = value 書式設定処理<BR>
	 *　備　考　：　配列型でクライアントサーバ間通信を行うときに使用
	 *<BR>
	 *　変更履歴<BR>
	 *<BR>
	 *　　Version　　日　付　　　更新者　　　　　コメント<BR>
	 *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
	 *　　00.00　　2002/06/13　宝来　幸弘　　新規作成<BR>
	 *<BR>
	 * @param	name　　　 IN　：　name部
	 * @param	value　　　IN　：　value部
	 * @param	sep　　　IN　：　
	 * @return	name=
	 */
	public static String encStringAry( String name, int count ) {
		return name + "[" + String.valueOf( count ) + "]";
	}

	/**
	 *<BR>
	 *　タイプ　：　ユーザー定義<BR>
	 *　処理名　：　name = value 書式設定処理<BR>
	 *　備　考　：　配列型でクライアントサーバ間通信を行うときに使用
	 *<BR>
	 *　変更履歴<BR>
	 *<BR>
	 *　　Version　　日　付　　　更新者　　　　　コメント<BR>
	 *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
	 *　　00.00　　2002/12/30　YangChuan　　新規作成<BR>
	 *<BR>
	 * @param	name　　　 IN　：　name部
	 * @param	value　　　IN　：　value部
	 * @param	sep　　　IN　：　
	 * @return	name=
	 */
	public static String encStringAry2( String name, int count, int count1 ) {
		return name + "[" + String.valueOf( count ) + "]" + "[" + String.valueOf( count1 ) + "]";
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
	public static String tofromatNumber (String sParam ) {
		String	sStr1;
		String	sStr2;
		double	dvalue;
		StringBuffer sbuff = new StringBuffer();
		FieldPosition fpos = new FieldPosition(DecimalFormat.INTEGER_FIELD);

		if ( sParam.equals( "" ) == false ) {

			dvalue	= Long.parseLong( sParam );
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
	public static String tofromatReal (String sParam , int   iDegit) {
		String	sStr1;
		String	sStr2;
		double	dvalue;
		StringBuffer sbuff = new StringBuffer();
		FieldPosition fpos = new FieldPosition(DecimalFormat.INTEGER_FIELD);

		if ( sParam.equals( "" ) == false ) {

			dvalue	= Double.parseDouble( sParam );
			DecimalFormat df = new DecimalFormat();

			df.setMinimumFractionDigits( iDegit );
			df.format( dvalue, sbuff, fpos );

			return sbuff.toString();
		} else {
			return "";
		}
	}

	/**
	 *<BR>
	 *　タイプ　：　ユーザー定義<BR>
	 *　処理名　：　null変換処理<BR>
	 *　備　考　：　<BR>
	 *<BR>
	 *　変更履歴<BR>
	 *<BR>
	 *　　Version　　日　付　　　更新者　　　　　コメント<BR>
	 *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
	 *　　00.01　　2002/08/06　宝来　幸弘　　新規作成<BR>
	 *<BR>
	 */
	public static final String nvl(String sprm) {
		if (sprm == null) {
			return "";
		} else {
			return sprm;
		}
	}

	/**
	 *<BR>
	 *　タイプ　：　ユーザー定義<BR>
	 *　処理名　：　端数処理<BR>
	 *　備　考　：　<BR>
	 *<BR>
	 *　変更履歴<BR>
	 *<BR>
	 *　　Version　　日　付　　　更新者　　　　　コメント<BR>
	 *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
	 *　　00.00　　2003/03/05　宝来　幸弘　　新規作成<BR>
	 *<BR>
	 * @param	dNum　　　　 IN　：　処理値
	 * @param	sHassuKbn　　IN　：　端数区分(1:切捨て、2:四捨五入、3:切り上げ)
	 * @return	処理結果
	 */
	public static long calcHasu( double dNum, String sHassuKbn ) {
		String	sKbn;

		sKbn	= sHassuKbn.trim();
		if ( sKbn.equals( "2" ) == true ) {
			//	四捨五入
			return round( dNum );
		} else if ( sKbn.equals( "1" ) == true ) {
			//	切り捨て
			return roundDown( dNum );
		} else if ( sKbn.equals( "3" ) == true ) {
			//	切り上げ
			return roundUp( dNum );
		} else {
			//	四捨五入
			return round( dNum );
		}
	}

	/**
	 *<BR>
	 *　タイプ　：　ユーザー定義<BR>
	 *　処理名　：　四捨五入処理<BR>
	 *　備　考　：　<BR>
	 *<BR>
	 *　変更履歴<BR>
	 *<BR>
	 *　　Version　　日　付　　　更新者　　　　　コメント<BR>
	 *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
	 *　　00.00　　2000/10/31　宝来　幸弘　　新規作成<BR>
	 *<BR>
	 * @param	dNum　　IN　：　処理値
	 * @return	処理結果
	 */
	public static long round( double dNum ) {
		long	lResult;
		double	dDbl;

		if ( dNum < 0 ) {
			dDbl = ( -1 ) * dNum;
			dDbl = dDbl + 0.5;
			lResult = (int)dDbl;
			lResult = ( -1 ) * lResult;
		} else {
			lResult = Math.round( dNum );
		}
		return lResult;
	}

	/**
	 *<BR>
	 *　タイプ　：　ユーザー定義<BR>
	 *　処理名　：　切り捨て処理<BR>
	 *　備　考　：　<BR>
	 *<BR>
	 *　変更履歴<BR>
	 *<BR>
	 *　　Version　　日　付　　　更新者　　　　　コメント<BR>
	 *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
	 *　　00.00　　2000/10/31　宝来　幸弘　　新規作成<BR>
	 *<BR>
	 * @param	dNum　　IN　：　処理値
	 * @return	処理結果
	 */
	public static long roundDown( double dNum ) {
		if ( dNum < 0 ) {
			return (long)Math.ceil( dNum );
		} else {
			return (long)Math.floor( dNum );
		}
	}

	/**
	 *<BR>
	 *　タイプ　：　ユーザー定義<BR>
	 *　処理名　：　切り上げ処理<BR>
	 *　備　考　：　<BR>
	 *<BR>
	 *　変更履歴<BR>
	 *<BR>
	 *　　Version　　日　付　　　更新者　　　　　コメント<BR>
	 *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
	 *　　00.00　　2000/10/31　宝来　幸弘　　新規作成<BR>
	 *<BR>
	 * @param	dNum　　IN　：　処理値
	 * @return	処理結果
	 */
	public static long roundUp( double dNum ) {
		if ( dNum < 0 ) {
			return (long)Math.floor( dNum );
		} else {
			return (long)Math.ceil( dNum );
		}
	}

	/**
	 *<BR>
	 *　タイプ　：　ユーザー定義<BR>
	 *　処理名　：　端数処理処理<BR>
	 *　備　考　：　<BR>
	 *<BR>
	 *　変更履歴<BR>
	 *<BR>
	 *　　Version　　日　付　　　更新者　　　　　コメント<BR>
	 *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
	 *　　00.00　　2003/01/11　宝来　幸弘　　新規作成<BR>
	 *<BR>
	 * @param	dNum　　IN　：　処理値
	 * @return	処理結果
	 */
	public static double pow( double dNum, long lKeta ) {
		double dTemp;
		double dAns;
		dTemp = Math.pow( 10 , ( double )lKeta );
		dAns = ( double )ComDATA.round( dNum * dTemp ) / dTemp;
		return dAns;
	}

	/**
	 *<BR>
	 *　タイプ　：　ユーザー定義<BR>
	 *　処理名　：　文字列列改行処理<BR>
	 *　備　考　：　
	 *<BR>
	 *　変更履歴<BR>
	 *<BR>
	 *　　Version　　日　付　　　更新者　　　　　コメント<BR>
	 *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
	 *　　00.00　　2002/08/21　松浦　寿彦　　新規作成<BR>
	 *<BR>
	 * @param	string　　　 IN　： 改行したい文字列　
	 * @param	berm  　　　 IN　： 改行する文字数
	 * @return	sbString		OUT :　改行文字列
	 */
	public static String newLine( String string, int berm ){
		int  i;
		int  iEndCountUp = 0;
		long lLinrLength = 0;
		long lCharLength = 0;
		StringBuffer sbString = new StringBuffer( string );

		lCharLength = string.length();
		lLinrLength = lCharLength/berm;

		for(i=1; i <= lLinrLength; i++){
			sbString.insert( (berm*i+iEndCountUp), "\n" );
			iEndCountUp++;
		}
		return String.valueOf( sbString );
	}

	/**
	 *<BR>
	 *　タイプ　：　ユーザー定義<BR>
	 *　処理名　：　文字列列改行処理<BR>
	 *　備　考　：　例外文字列に改行処理を行う
	 *<BR>
	 *　変更履歴<BR>
	 *<BR>
	 *　　Version　　日　付　　　更新者　　　　　コメント<BR>
	 *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
	 *　　00.00　　2002/08/21　松浦　寿彦　　新規作成<BR>
	 *<BR>
	 * @param	exception	IN　： 改行したい例外文字列　
	 * @param	berm  　　　 IN　： 改行する文字数
	 * @return	sbString		OUT :　改行文字列
	 */
	public static String newLine( Exception exception, int berm ){
		return newLine( exception.toString(), berm );
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
	public static String tofromatDate (String sDate2 ) {
		String sDate=sDate2;
		String	sResult;
		sDate = sDate + "        ";
		sResult	= sDate.substring( 0, 4 )
				+ "/"
				+ sDate.substring( 4, 6 )
				+ "/"
				+ sDate.substring( 6, 8 )
				;

		return sResult;
	}

	/**
	 *<BR>
	 *　タイプ　：　ユーザー定義<BR>
	 *　処理名　：　西暦を和暦に変換<BR>
	 *　備　考　：　<BR>
	 *<BR>
	 *　変更履歴<BR>
	 *<BR>
	 *　　Version　　日　付　　　更新者　　　　　コメント<BR>
	 *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
	 *　　00.00　　2003/02/06　松浦　寿彦　　新規作成<BR>
	 *<BR>
	 * @param   sSeireki      :西暦
	 */
	public static String toWaReki(String sSeireki){

		int	iStrCount = 0;	//取得文字列数
		int	iYear	  = 0;	//西暦
		int	iWareki	  = 0;	//和暦
		String	sWareki	  = "";	//和暦
		String	sNengou	  = "";	//年号

		//空の場合（NULLも同様）は、""を返す
		if ( ComDATA.nvl( sSeireki ).equals( "" ) == true ) {
			return "";
		}

		//文字数を取得
		iStrCount = sSeireki.length();

		//文字数が4文字より少なければエラー
		if( iStrCount < 4){
			return sSeireki;
		}

		//西暦を取得
		iYear = Integer.parseInt( sSeireki.substring( 0, 4 ));
		if( DefaultCommon.WAREKI_HEISEI < iYear ) {
			sNengou = String.valueOf( DefaultCommon.NENGOU_HEISEI );
			iWareki = iYear - DefaultCommon.WAREKI_HEISEI;
		} else if( DefaultCommon.WAREKI_SHOUWA < iYear ) {
			sNengou = String.valueOf( DefaultCommon.NENGOU_SHOUWA );
			iWareki = iYear - DefaultCommon.WAREKI_SHOUWA;
		} else if( DefaultCommon.WAREKI_TAISHOU < iYear ) {
			sNengou = String.valueOf( DefaultCommon.NENGOU_TAISHOU );
			iWareki = iYear - DefaultCommon.WAREKI_TAISHOU;
		} else {
			sNengou = String.valueOf( DefaultCommon.NENGOU_DEFAULT );
			iWareki = iYear - DefaultCommon.WAREKI_DEFAULT;
		}
		sWareki = sNengou + ComDATA.zeroFormat(String.valueOf( iWareki ), 2);
		if( iStrCount >= 5 ){
			sWareki = sWareki + sSeireki.substring(4);
		}

		return sWareki;
	}

	/**
	 *<BR>
	 *　タイプ　：　ユーザー定義<BR>
	 *　処理名　：　和暦を西暦に変換<BR>
	 *　備　考　：　<BR>
	 *<BR>
	 *　変更履歴<BR>
	 *<BR>
	 *　　Version　　日　付　　　更新者　　　　　コメント<BR>
	 *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
	 *　　00.00　　2003/02/06　松浦　寿彦　　新規作成<BR>
	 *<BR>
	 * @param   sWareki      :和暦
	 */
	public static String toSeiReki( String sWareki ){

		int iStrCount	 = 0;	//取得文字列数
		int iFlg	 = 0;	//先頭文字列判定フラグ
		char cNengou	 = ' ';	//年号
		int iFirstYear	 = 0;	//元年
		int iSeireki	 = 0;	//西暦
		String  sSeireki = "";	//西暦

		//空の場合（NULLも同様）は、""を返す
		if ( ComDATA.nvl( sWareki ).equals( "" ) == true ) {
			return "";
		}

		//文字数を取得
		iStrCount = sWareki.length();

		if( iStrCount <= 0 ){
			return sWareki;
		}

		//先頭文字以外に文字が含まれていたらエラー
		for( int i=1;  i < iStrCount; i++ ){
			if( Character.isDigit( sWareki.charAt(i)) == false ){
				return sWareki;
			}
		}

		//先頭文字を取得
		cNengou = sWareki.toUpperCase().charAt(0);
		if ( Character.isDigit( cNengou ) == true ){
			if( iStrCount < 2){
				return sWareki;
			}
			//先頭文字が数値の場合デフォルトの西暦値を使用
				iFirstYear  = DefaultCommon.WAREKI_DEFAULT;
			iFlg = 0;
		} else {
			//先頭文字と年号を比較し元年の西暦を取得該当がない場合デフォルト
			switch( cNengou ){
				case DefaultCommon.NENGOU_HEISEI:
					iFirstYear  = DefaultCommon.WAREKI_HEISEI;
					break;
				case DefaultCommon.NENGOU_SHOUWA:
					iFirstYear = DefaultCommon.WAREKI_SHOUWA;
					break;
				case DefaultCommon.NENGOU_TAISHOU:
					iFirstYear = DefaultCommon.WAREKI_TAISHOU;
					break;
				default:
					iFirstYear = DefaultCommon.WAREKI_DEFAULT;
					break;
			}
			iFlg = 1;
		}
		//西暦を出す
		iSeireki = iFirstYear + Integer.parseInt( sWareki.substring( 0+iFlg, 2+iFlg ));
		sSeireki = String.valueOf( iSeireki );
		//月日が存在する場合はそれをつなげる
		if( iStrCount >= 2+iFlg ){
			sSeireki = sSeireki + sWareki.substring( 2+iFlg );
		}
		return sSeireki;
	}

	/**
	 *<BR>
	 *　タイプ　：　レコード編集処理<BR>
	 *　処理名　：　品種マスタ項目編集<BR>
	 *　備　考　：　<BR>
	 *<BR>
	 *　変更履歴<BR>
	 *<BR>
	 *　　Version　　日　付　　　更新者　　　　　コメント<BR>
	 *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
	 *　　00.00　　2003/04/17　 牧野英一 　　  新規作成<BR>
	 *<BR>
	  * @param     iRecLayout[]  IN:レコードレイアウト
	  * @param     sInputRec     IN:入力レコード
	  * @param     iIRecCnt      IN:入力レコードカウント
	  * @return    pvHnsmstRec :各要素に分割したベクター配列
	  */
	public static Vector getLayoutData(int[]     iRecLayout,
					   String  sInputRec,
					   int     iIRecCnt,
					   OutputStreamWriter writer
					   ) {
		int iYousoSuu = 0;

		//ファイルレイアウトに半角と全角が混在する項目があるため、一旦バイトに変換しカラム位置で項目ごとに分割する
		iYousoSuu = iRecLayout.length;
		byte[] b = sInputRec.getBytes();
		int iLengCnt = 0;
		Vector  vecKOUMOKU;
		vecKOUMOKU = new Vector();
		try{
			for ( int i = 0; i <= iYousoSuu - 1; i++ ){
				byte[] byteTmp = new byte[iRecLayout[i]];
				for ( int j = 0; j <= ( iRecLayout[i] - 1 ); j++){
					byteTmp[j] = b[ j + iLengCnt ];
				}
				iLengCnt += iRecLayout[i];
				String sStringTmp = new String( byteTmp );
				vecKOUMOKU.addElement( sStringTmp );
			}
		}catch( ArrayIndexOutOfBoundsException e ){
			System.out.println( "レコードが短すぎます 行番= "+ iIRecCnt );
			System.out.println( sInputRec );
		}
		return vecKOUMOKU;
	}

	/**
	 *<BR>
	 *　タイプ　：　ユーザー定義<BR>
	 *　処理名　：　IFファイル名取得<BR>
	 *　備　考　：　<BR>
	 *<BR>
	 *　変更履歴<BR>
	 *<BR>
	 *　　Version　　日　付　　　更新者　　　　　コメント<BR>
	 *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
	 *　　00.01　　2004/08/30　Tsuka　　新規作成<BR>
         *   00.02    2005/01/14 Yery     File Pathを修正
	 *<BR>
 	 * @param   syori　　  IN　：　処理名称
	 * @return  ファイル名
	 */
	public static String getFileName( String syori ) {
		String sFilename = "";
                String sPropertiFile = FILENAME;
                //プロパティーファイルの読込
		System.out.println( "GetFilename/syori : " + syori);

                /**
                 * File Pathを修正
                 */
                try {
                  ResourceBundle resources = ResourceBundle.getBundle(FILENAME,
                      Locale
                      .getDefault());
                  System.out.println( "GetFilenames/load");

                  Enumeration keys = resources.getKeys();

                  while (keys.hasMoreElements()) {
                    String sKey = (String) keys.nextElement();
                    String sValue = resources.getString(sKey).trim();
                    if(sKey.equals(syori)) {
                      sFilename=sValue;
                      break;
                    }
                  }
                }
                catch (Exception e) {
                  e.printStackTrace();
                  sFilename = "";
                }
		System.out.println( "GetFilenames/Filename : " + sFilename);
		return sFilename;
	}


        /**
         *<BR>
         *　タイプ　：　ユーザー定義<BR>
         *　処理名　：　ホスト送信用数値符号変換処理<BR>
         *　備　考　：　最後の桁に符号コード変換<BR>
         *<BR>
         *　変更履歴<BR>
         *<BR>
         *　　Version　　日　付　　　更新者　　　　　コメント<BR>
         *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
         *　　00.00　　2004/09/02　庄崎　浩司　　新規作成<BR>
         *　　00.01　　2004/12/22　庄崎　浩司　　プラス(F)用追加<BR>
         *<BR>
         * @param	sValue	:文字データ
         * @param	iKbn	:プラス(F)= 0, プラス = 1, マイナス = 2
         * @return	sValue		:変換後データ
         */
        public static String  toSign( String sValue, int iKbn ){
                int	iLength;
                char	cCode1;
                char  cCode2 = ' ';

                cCode1 = sValue.charAt( sValue.length() - 1 );
                //ins 2004.12.22 str
                if ( iKbn == 0 ) {
                        switch ( cCode1 ) {
                                case '0':
                                        cCode2 = 0x30;
                                        break;
                                case '1':
                                        cCode2 = 0x31;
                                        break;
                                case '2':
                                        cCode2 = 0x32;
                                        break;
                                case '3':
                                        cCode2 = 0x33;
                                        break;
                                case '4':
                                        cCode2 = 0x34;
                                        break;
                                case '5':
                                        cCode2 = 0x35;
                                        break;
                                case '6':
                                        cCode2 = 0x36;
                                        break;
                                case '7':
                                        cCode2 = 0x37;
                                        break;
                                case '8':
                                        cCode2 = 0x38;
                                        break;
                                case '9':
                                        cCode2 = 0x39;
                                        break;
                                default:
                        }
                } else if ( iKbn == 1 ) {
                //ins 2004.12.22 end
                        switch ( cCode1 ) {
                                case '0':
                                        cCode2 = 0x7B;
                                        break;
                                case '1':
                                        cCode2 = 0x41;
                                        break;
                                case '2':
                                        cCode2 = 0x42;
                                        break;
                                case '3':
                                        cCode2 = 0x43;
                                        break;
                                case '4':
                                        cCode2 = 0x44;
                                        break;
                                case '5':
                                        cCode2 = 0x45;
                                        break;
                                case '6':
                                        cCode2 = 0x46;
                                        break;
                                case '7':
                                        cCode2 = 0x47;
                                        break;
                                case '8':
                                        cCode2 = 0x48;
                                        break;
                                case '9':
                                        cCode2 = 0x49;
                                        break;
                                default:
                        }
                } else {
                        switch ( cCode1 ) {
                                case '0':
                                        cCode2 = 0x7D;
                                        break;
                                case '1':
                                        cCode2 = 0x4A;
                                        break;
                                case '2':
                                        cCode2 = 0x4B;
                                        break;
                                case '3':
                                        cCode2 = 0x4C;
                                        break;
                                case '4':
                                        cCode2 = 0x4D;
                                        break;
                                case '5':
                                        cCode2 = 0x4E;
                                        break;
                                case '6':
                                        cCode2 = 0x4F;
                                        break;
                                case '7':
                                        cCode2 = 0x50;
                                        break;
                                case '8':
                                        cCode2 = 0x51;
                                        break;
                                case '9':
                                        cCode2 = 0x52;
                                        break;
                                default:
                        }
                }

                return sValue.substring( 0, sValue.length() - 1 ) + cCode2;
        }


        /**
         *<BR>
         *　タイプ　：　ユーザー定義<BR>
         *　処理名　：　全角入力判定処理<BR>
         *　備　考　：　<BR>
         *<BR>
         *　変更履歴<BR>
         *<BR>
         *　　Version　　日　付　　　更新者　　　　　コメント<BR>
         *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
         *　　00.00　　2004/11/29　Yery　　新規作成<BR>
         *<BR>
         * @param	text	:文字データ
         * @param	msg   	:JMsTextの名
         * @return	sValue		:true　：　ＯＫ<BR>false　：　エラーあり
         */

        public static boolean checkFullWidthInput(String text,String msg) {
          String	sCheckStr;	// 判定文字列
          boolean	bRet=true;	// 戻り値

          //++************************
          //	全角入力判定処理
          //--************************
          sCheckStr = text;
          int iCharNumbers=sCheckStr.length();
          int iByteLength=sCheckStr.getBytes().length;
          if(iCharNumbers!=iByteLength/2) {
                  bRet=false;
                  //textはブランクとすると、エラーメッセージを表示しません。
                  if(msg!=null && msg.trim().length()>0) {
                    comMSG.dispErrorDialog(msg + "は全角で入力してください。");
                  }
          }
          return bRet;
        }

        /**
         *<BR>
         *　タイプ　：　ユーザー定義<BR>
         *　処理名　：　全角入力判定処理<BR>
         *　備　考　：　<BR>
         *<BR>
         *　変更履歴<BR>
         *<BR>
         *　　Version　　日　付　　　更新者　　　　　コメント<BR>
         *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
         *　　00.00　　2004/11/29　Yery　　新規作成<BR>
         *<BR>
         * @param	text	:文字データ
         * @param	msg   	:JMsTextの名
         * @return	sValue		:true　：　ＯＫ<BR>false　：　エラーあり
         */

        public static boolean checkFullWidthInput(JMsText text,String msg) {
          String	sCheckStr;	// 判定文字列
          boolean	bRet=true;	// 戻り値

          //++************************
          //	全角入力判定処理
          //--************************
          sCheckStr = text.getText();
          int iCharNumbers=sCheckStr.length();
          int iByteLength=sCheckStr.getBytes().length;
          if(iCharNumbers!=iByteLength/2) {
                  bRet=false;
                  text.setErrorField( true );
                  //textはブランクとすると、エラーメッセージを表示しません。
                  if(msg!=null && msg.trim().length()>0) {
                    comMSG.dispErrorDialog(msg + "は全角で入力してください。");
                    comFORM.setFocus(text);
                  }
          }
          return bRet;
        }

        /**
         *<BR>
         *　タイプ　：　ユーザー定義<BR>
         *　処理名　：　全角を半角に変換<BR>
         *　備　考　：　<BR>
         *<BR>
         *　変更履歴<BR>
         *<BR>
         *　　Version　　日　付　　　更新者　　　　　コメント<BR>
         *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
         *　　00.00　　2004/11/29　Yery　　新規作成<BR>
         *<BR>
         * @param halfWidthText String
         * @return String
         */
        public static String toFullWidthText(String halfWidthText) {
          java.util.ResourceBundle resources = java.util.ResourceBundle.getBundle("com_zennoh/common/charset",
              java.util.Locale
              .getDefault());

          java.util.Enumeration keys = resources.getKeys();
          java.util.HashMap ht=new java.util.HashMap();

          while (keys.hasMoreElements()) {
            String sKey = (String) keys.nextElement();
            String sValue = resources.getString(sKey).trim();
            ht.put(sKey,sValue);
          }

          if(halfWidthText!=null) {
            StringBuffer sbTemp = new StringBuffer();
            try {
              //System.out.println("before:"+halfWidthText);
              //halfWidthText = new String(halfWidthText.getBytes("SJIS"));
              //System.out.println("after: "+halfWidthText);

              for (int i = 0; i < halfWidthText.length(); i++) {
                String c = halfWidthText.substring(i,i+1);
                /**
                 * 半角
                 */
                if (c.getBytes().length == 1) {
                  byte[] bTexts = c.getBytes();
                  //Character character=new  Character(c);
                  String sHex = Integer.toHexString(bTexts[0]).toUpperCase();
                  if(sHex.length()>2) {
                    sHex=sHex.substring(sHex.length()-2);
                  }
                  String sKey = "0x"+sHex;

                  String sValue = (String) ht.get(sKey);

                  if (sValue != null) {
                    sbTemp.append(sValue);
                  }
                  else {
                    sbTemp.append(c);
                  }
                }
                /**
                 * 全角
                 */
                else {
                  sbTemp.append(c);
                }
              }
              return sbTemp.toString();
            } catch(Exception e) {
              return null;
            }
          } else {
            return null;
          }
        }

        /**
         *<BR>
         *　タイプ　：　ユーザー定義<BR>
         *　処理名　：　半角を全角に変換<BR>
         *　備　考　：　<BR>
         *<BR>
         *　変更履歴<BR>
         *<BR>
         *　　Version　　日　付　　　更新者　　　　　コメント<BR>
         *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
         *　　00.00　　2004/12/24　Yery　　新規作成<BR>
         *<BR>
         * @param halfWidthText String
         * @return String
         */
        public static String toHalfWidthText(String fullWidthText) {
          java.util.ResourceBundle resources = java.util.ResourceBundle.getBundle("com_zennoh/common/charset",
              java.util.Locale
              .getDefault());

          java.util.Enumeration keys = resources.getKeys();
          java.util.HashMap ht=new java.util.HashMap();

          while (keys.hasMoreElements()) {
            String sKey = (String) keys.nextElement();
            String sValue = resources.getString(sKey).trim();
            ht.put(sValue,sKey);
          }

          if(fullWidthText!=null) {
            StringBuffer sbTemp = new StringBuffer();
            try {
              //System.out.println("before:"+halfWidthText);
              //halfWidthText = new String(halfWidthText.getBytes("SJIS"));
              //System.out.println("after: "+halfWidthText);

              for (int i = 0; i < fullWidthText.length(); i++) {
                String c = fullWidthText.substring(i,i+1);
                /**
                 * 全角
                 */
                boolean isFind=false;

                if (c.getBytes().length == 2) {
                  String sKey=(String)ht.get(c);
                  if(sKey!=null) {
                    byte[] bTexts=new byte[1];
                    bTexts[0]=(byte)(Integer.parseInt(sKey.substring(2),16)&0xFF);
                    String sText=new String(bTexts);
                    sbTemp.append(sText);
                  } else {
                    sbTemp.append(c);
                  }
                }
                /**
                 * 半角
                 */
                else {
                  sbTemp.append(c);
                }
              }
              return sbTemp.toString();
            } catch(Exception e) {
              e.printStackTrace();
              return null;
            }
          } else {
            return null;
          }
        }
}
