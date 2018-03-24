package client.framework.view.common;

import java.util.Calendar;
import java.text.*;
import org.apache.log4j.*;

/**
 *<BR>
 *　概要<BR>
 *<BR>
 *　　共通モジュール（日付関連）<BR>
 *<BR>
 *　変更履歴<BR>
 *<BR>
 *　　Version　　日　付　　　更新者　　　　　コメント<BR>
 *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
 *　　00.00　　2004/06/21　Tsukamoto　　新規作成<BR>
 *　　00.01　　2004/11/16　Yery     　　\u589E加getCurrentTime<BR>
 *<BR>
 */
public class ComDATE
{
        static Category log = Category.getInstance(ComDATE.class.getName());

        /**
         *<BR>
         *　タイプ　：　ユーザー関数<BR>
         *　処理名　：　文字列数値列変更操作<BR>
         *　備　考　：　日付"/"編集
         *<BR>
         *　変更履歴<BR>
         *<BR>
         *　　Version　　日　付　　　更新者　　　　　コメント<BR>
         *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
         *　　00.00　　2002/05/30　宝来　幸弘　　新規作成<BR>
         *<BR>
         */
        public static String tofromatDate (String sDate2 ) {
                String	sResult;
                String sDate=sDate2;
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
         *　処理名　：　年取得処理<BR>
         *　備　考　：　年月日から年を取得する（空／NULLの場合は、""を返す）
         *<BR>
         *　変更履歴<BR>
         *<BR>
         *　　Version　　日　付　　　更新者　　　　　コメント<BR>
         *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
         *　　00.00　　2003/03/10　宝来　幸弘　　新規作成<BR>
         *<BR>
         * @param	sDate　　　IN　：　年月日
         */
        public static String getYear( String sDate ) {
                if ( ComDATA.nvl( sDate ).equals( "" ) == true ) {
                        return "";
                }
                return  sDate.substring( 0, 4 );
        }

        /**
         *<BR>
         *　タイプ　：　ユーザー定義<BR>
         *　処理名　：　年取得処理<BR>
         *　備　考　：　年月日から月を取得する（空／NULLの場合は、""を返す）
         *<BR>
         *　変更履歴<BR>
         *<BR>
         *　　Version　　日　付　　　更新者　　　　　コメント<BR>
         *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
         *　　00.00　　2003/03/10　宝来　幸弘　　新規作成<BR>
         *<BR>
         * @param	sDate　　　IN　：　年月日
         */
        public static String getMonth( String sDate ) {
                if ( ComDATA.nvl( sDate ).equals( "" ) == true ) {
                        return "";
                }
                return  sDate.substring( 4, 6 );
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
         * @param   iYear　IN　	：　年データ
         * @param   iMonth　IN　：　月データ
         * @param   iDay　IN　	：　日データ
         * @return 	trueかfalseを返す。
         */
        public static boolean checkDate(
                String  sInDate
        ){
                int	iYear  = Integer.parseInt( sInDate.substring( 0, 4 ));
                int	iMonth = Integer.parseInt( sInDate.substring( 4, 6 ));
                int	iDay   = Integer.parseInt( sInDate.substring( 6, 8 ));
                int	iHizuke;

                //++****************************************
                //	年の判定（1900～2050の範囲内であるか？）
                //--****************************************
                if (( iYear < 1900 ) || ( iYear > 2050 )) {
                        return false;
                }

                //++****************************************
                //	月の判定（1～12月の範囲内であるか？）
                //--****************************************
                if (( iMonth < 1 ) || ( iMonth > 12 )) {
                        return false;
                }

                //++**********************************************
                //	日の判定（1～月の最大日数の範囲内であるか？）
                //--**********************************************
                iHizuke = getDate2( iYear, iMonth );
                if (( iDay < 1 ) || ( iDay > iHizuke )) {
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
         * @param   iYear2　IN　	：　年データ
         * @param   iMonth2　IN　：　月データ
         * @return 	月の最大日数を返す。
         */
         private static int getDate2( int iYear2, int iMonth2 ){
                //++***********************************
                //	月の判定（最大日数が３０日？）
                //--***********************************
                if ( iMonth2 == 4 || iMonth2 == 6 || iMonth2 == 9 || iMonth2 == 11 ) {
                        return 30;
                }

                //++***********************************
                //	月の判定（2月以外の月か？）
                //--***********************************
                if ( iMonth2 != 2 ) {
                        return 31;
                }

                //++***********************************
                //	月の判定（うるう年の２月か？）
                //--***********************************
                if ( iYear2%400 == 0 || ((iYear2%100 != 0)&&(iYear2%4 == 0 ))) {
                        return 29;
                } else {
                        return 28;
                }
        }

        /**
         *<BR>
         *　タイプ　：　ユーザー定義<BR>
         *　処理名　：　日付け計算処理<BR>
         *　備　考　：　任意の日数後が何日にあたるのかを計算する<BR>
         *<BR>
         *　変更履歴<BR>
         *<BR>
         *　　Version　　日　付　　　更新者　　　　　コメント<BR>
         *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
         *　　00.01　　2002/09/10　宝来　幸弘　　新規作成<BR>
         *<BR>
         * @param	Date　　	  IN　：　初期日付け
         * @param	iAfterDay　　 IN　：　加算日数（マイナス入力もＯＫ）
         * @return	処理結果　　sRetDate　　加算後日付け
         */
        public static String afterDate(
                String	date1,
                int	iAfterDay
        ) {
                String	sRetDate = "";
                Calendar cal = Calendar.getInstance();

                cal.set( Integer.parseInt( date1.substring( 0, 4 )),
                         Integer.parseInt( date1.substring( 4, 6 ))-1,
                         Integer.parseInt( date1.substring( 6, 8 ))
                );

                cal.add( Calendar.DATE , iAfterDay );

                String	sRetYear  = ComDATA.zeroFormat( cal.get( Calendar.YEAR ), 4 );
                String	sRetMonth = ComDATA.zeroFormat( cal.get( Calendar.MONTH ) + 1,2 );
                String	sRetDay   = ComDATA.zeroFormat( cal.get( Calendar.DATE ),2 );

                sRetDate = sRetYear + sRetMonth + sRetDay;

                return sRetDate;
        }

        /**
         *<BR>
         *　タイプ　：　ユーザー定義<BR>
         *　処理名　：　日付け計算処理<BR>
         *　備　考　：　任意の日数後（月日指定）が何日にあたるのかを計算する<BR>
         *<BR>
         *　変更履歴<BR>
         *<BR>
         *　　Version　　日　付　　　更新者　　　　　コメント<BR>
         *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
         *　　00.01　　2002/09/10　宝来　幸弘　　新規作成<BR>
         *<BR>
         * @param	Date　　	  IN　：　初期日付け
         * @param	iAfterMonth　 IN　：　加算月数（マイナス入力もＯＫ）
         * @param	iAfterDay　　 IN　：　加算日数（マイナス入力もＯＫ）
         * @return	処理結果　　sRetDate　　加算後日付け
         */
        public static String afterMonthDate(
                String	date,
                int	iAfterMonth,
                int	iAfterDay
        ) {
                String	sRetDate = "";
                Calendar cal = Calendar.getInstance();

                cal.set( Integer.parseInt( date.substring( 0, 4 )),
                         Integer.parseInt( date.substring( 4, 6 ))-1,
                         Integer.parseInt( date.substring( 6, 8 ))
                );

                cal.add( Calendar.MONTH , iAfterMonth );
                cal.add( Calendar.DATE , iAfterDay );

                String	sRetYear  = ComDATA.zeroFormat( cal.get( Calendar.YEAR ), 4 );
                String	sRetMonth = ComDATA.zeroFormat( cal.get( Calendar.MONTH ) + 1,2 );
                String	sRetDay   = ComDATA.zeroFormat( cal.get( Calendar.DATE ),2 );

                sRetDate = sRetYear + sRetMonth + sRetDay;

                return sRetDate;
        }

        /**
         *<BR>
         *　タイプ　：　ユーザー定義<BR>
         *　処理名　：　日付け計算処理<BR>
         *　備　考　：　任意の日数後（年月日指定）が何日にあたるのかを計算する<BR>
         *<BR>
         *　変更履歴<BR>
         *<BR>
         *　　Version　　日　付　　　更新者　　　　　コメント<BR>
         *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
         *　　00.01　　2002/09/10　宝来　幸弘　　新規作成<BR>
         *<BR>
         * @param	Date　　	  IN　：　初期日付け
         * @param	iAfterYear　  IN　：　加算年数（マイナス入力もＯＫ）
         * @param	iAfterMonth　 IN　：　加算月数（マイナス入力もＯＫ）
         * @param	iAfterDay　　 IN　：　加算日数（マイナス入力もＯＫ）
         * @return	処理結果　　sRetDate　　加算後日付け
         */
        public static String afterYMD(
                String	date,
                int	iAfterYear,
                int	iAfterMonth,
                int	iAfterDay
        ) {

                String	sRetDate = "";
                Calendar cal = Calendar.getInstance();

                cal.set( Integer.parseInt( date.substring( 0, 4 )),
                         Integer.parseInt( date.substring( 4, 6 ))-1,
                         Integer.parseInt( date.substring( 6, 8 ))
                        );

                cal.add( Calendar.YEAR , iAfterYear );
                cal.add( Calendar.MONTH , iAfterMonth );
                cal.add( Calendar.DATE , iAfterDay );

                String	sRetYear  = ComDATA.zeroFormat( cal.get( Calendar.YEAR ), 4 );
                String	sRetMonth = ComDATA.zeroFormat( cal.get( Calendar.MONTH ) + 1,2 );
                String	sRetDay   = ComDATA.zeroFormat( cal.get( Calendar.DATE ),2 );

                sRetDate = sRetYear + sRetMonth + sRetDay;

                return sRetDate;
        }

        /**
         *<BR>
         *　タイプ　：　ユーザー定義<BR>
         *　処理名　：　本日日付文字列取得<BR>
         *　備　考　：　<BR>
         *<BR>
         *　変更履歴<BR>
         *<BR>
         *　　Version　　日　付　　　更新者　　　　　コメント<BR>
         *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
         *　　00.00　　2000/09/07　宝来　幸弘　　新規作成<BR>
         *<BR>
         * @param	なし
         * @return	本日日付文字列( YYYYMMDD )
         */
        public static String getTodayStr( ) {
                SimpleDateFormat clsFormat;
                clsFormat = new SimpleDateFormat( "yyyyMMdd" );
                return clsFormat.format( new java.util.Date() );
        }

        /**
         *<BR>
         *　タイプ　：　ユーザー定義<BR>
         *　処理名　：　期間日数取得<BR>
         *　備　考　：　入力された年の期間日数を返す。<BR>
         *<BR>
         *　変更履歴<BR>
         *<BR>
         *　　Version　　日　付　　　更新者　　　　　コメント<BR>
         *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
         *　　00.00　　2004/08/24　Tsuka　　新規作成<BR>
         *<BR>
         * @param   Year1　IN　：　開始年データ
         * @param   Year2　IN　：　終了年データ
         * @return 	期間日数を返す。
         */
         public static long getDays( String yearS, String yearE ){
                int  yy = 0;
                int  mm = 0;
                int  dd = 0;
                long iMSDays  = 0L;
                long iMSStart = 0L;
                long iMSEnd   = 0L;
                String buff = new String("");

                log.debug("getDays / prm : " + yearS + " , " + yearE);

                Calendar cl = Calendar.getInstance();

                // 日付(string)を年・月・日(int)に変換
                // 開始日
                buff = new String( yearS.toCharArray() , 0 ,4 );
                yy = Integer.valueOf( buff ).intValue();
                buff = new String( yearS.toCharArray() , 4 ,2 );
                mm = Integer.valueOf( buff ).intValue();
                buff = new String( yearS.toCharArray() , 6 ,2 );
                dd = Integer.valueOf( buff ).intValue();
                cl.set( yy , mm - 1 , dd );
                iMSStart = (cl.getTime()).getTime();
                log.debug("getDays / MS_start : " + iMSStart);

                // 終了日
                buff = new String( yearE.toCharArray() , 0 ,4 );
                yy = Integer.valueOf( buff ).intValue();
                buff = new String( yearE.toCharArray() , 4 ,2 );
                mm = Integer.valueOf( buff ).intValue();
                buff = new String( yearE.toCharArray() , 6 ,2 );
                dd = Integer.valueOf( buff ).intValue();
                cl.set( yy , mm - 1 , dd );
                iMSEnd = (cl.getTime()).getTime();
                log.debug("getDays / MS_end : " + iMSEnd);

                // 開始日>終了日の場合エラー
                if(iMSStart > iMSEnd) {
                        iMSDays = -1;
                } else {
                        // オブジェクト間の日数（ミリ秒を１日のミリ秒で割る）を求める
                        iMSDays = Math.abs(iMSStart - iMSEnd ) / 86400000L;
                        log.debug("getDays / MS_days : " + iMSDays);
                }
                return iMSDays;
        }

        /**
         *<BR>
         *　タイプ　：　ユーザー定義<BR>
         *　処理名　：　現在時間日付文字列取得<BR>
         *　備　考　：　<BR>
         *<BR>
         *　変更履歴<BR>
         *<BR>
         *　　Version　　日　付　　　更新者　　　　　コメント<BR>
         *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
         *　　00.00　　2004/11/16　Yery　　新規作成<BR>
         *<BR>
         * @param	なし
         * @return	現在時間日付文字列( yy/MM/dd HH:mm:ss )
         */
        public static String getCurrentTime( ) {
                SimpleDateFormat clsFormat;
                clsFormat = new SimpleDateFormat( "yy/MM/dd　HH:mm:ss" );
                return clsFormat.format( new java.util.Date() );
        }

}
