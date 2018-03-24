package client.framework.view.common;

import java.awt.*;

public class ComGlobal
{
	public static	String		g_sTermID		 = null; // 端末ＩＤ
	public static	String		g_sMessage		 = null;
	public static	java.awt.Frame	g_clsFrame		 = null;
	public static	java.awt.Panel	g_clsCurrentPanel	 = null;
	public static	java.awt.Panel	gClsCurrentPanelNative = null;
	public static	String		g_sMenuID		 = null; // 現在、選択中のメニューＩＤ

	//++********************************
	//	ダイアログ情報
	//--********************************
	public static	Dialog	g_clsDialogInput		= null;	// 入力サブ画面ダイアログ
	public static	Dialog	g_clsDialogMsg			= null;	// メッセージダイアログ
	public static	Dialog	g_clsDialogFaitalError		= null;	// 継続不可通知ダイアログ
	public static	Dialog	g_clsDialogFaitalErrorServer	= null;	// 通信継続不可通知ダイアログ
	public static	Dialog	g_clsDialogCalender 		= null;	// カレンダーダイアログ
        public static	Dialog	g_clsDialogCodeSeek		= null;	// コード検索ダイアログ
	public static	Dialog	g_clsDialogSubForm		= null;	// サブ画面ダイアログ
	public static	Dialog	gClsDialogSubForm2		= null;	// サブ画面ダイアログ２
	public static	Dialog	gClsDialogSubForm3		= null;	// サブ画面ダイアログ３
	public static	Dialog	g_clsDialogProcessYN		= null;	// 処理中ダイアログ２
	public static	String	g_sProcessYN			= "";	// 処理中ダイアログ２
	public static	Dialog	g_clsDialogProcess		= null;	// 処理中ダイアログ２
	public static	Dialog	g_clsDialogPrint		= null;	// 印刷ダイアログ


	//++********************************
	//	入力者情報
	//--********************************
	public static	String	g_sOPDATE			= "";	// 処理日
	public static	String	g_sOPDATEYM			= "";	// 当年処理年月
	public static	String	gSOperFormId			= "";	// フォームＩＤ

        //add by yery
        public static   String          g_sBATOPDATE        = "";       //  バッチ処理年月
        public static   String          g_sKKKYM            = "";       //  買掛処理年月
        //バッチ用
        public static   String          gSBatId               = "";       //  バッチプログラムID
        public static   String          gSBatTanCd            = "";       //  バッチ宣言担当者
        public static   String          gSBatStartDate        = "";       //  バッチ開始日
        public static   String          gSBatStartTime        = "";       //  バッチ開始時刻

}
