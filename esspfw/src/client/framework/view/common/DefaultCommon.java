package client.framework.view.common;
public class DefaultCommon
{
//■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
//  本番反映時の注意事項
//　（１）接続先のURLを変更すること
//　（２）バッチ用のDBの接続先を変更する事
//■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■

        public static final String	JDBC_SYBASE_URL		= "jdbc:sybase:Tds:10.5.2.33:3000/";
        public static final String	SERVLET_URL_IP		= "http://localhost:8080/DENPUN/";
//	public static final String	JDBC_SYBASE_URL		= "jdbc:sybase:Tds:10.1.10.90:3000/";
//	public static final String	SERVLET_URL_IP		= "http://10.1.10.90:8080/SHIITAKE/";
//	public static final String	SERVLET_URL_IP		= "http://192.168.1.98:8080/";

        //ユーザとパスワード
        public static final String	JDBC_SYBASE_USER	= "zennoh";
        public static final String	JDBC_SYBASE_PASS	= "zennoh";

        //実行モード   0:ローカルアクセス(開発)  1:サーバアクセス本番
        public static final int	OPER_EXEC_MODE		= 0;

        //リソースのローカル上でのフルパス
        public static final String	SYSTEM_ROOT		= "D:\\Tomcat50\\webapps\\DENPUN\\WEB-INF\\classes\\";
        //rootディレクトリ
        public static final String	ROOT_DIRECTORY		= "D:\\Tomcat50\\webapps\\DENPUN\\";
        //システム開発モード(MS:0）
        public static final int	SYSTEM_MODE		= 1;
        public static final String	URL			= "";			//URL
        public static final String	SERVLET_URL		= "";
        public static final String	LOCAL_ADDRESS		= "";			//ローカルホスト
        public static final String	LOCAL_COM_ADDRESS	= "";			//ローカルホスト共通
        public static final String	LOCAL_CLOSE_ADDRESS	= "";			//ENDページ
        public static final String	APPLET_SERVLET		= "AppServlet";		//アプレットサーブレットの場所
        public static final String	REPORT_SERVLET		= "PrintServlet";	//レポートサーブレットの場所

        public static final String	VB_CONECTSTRING		= "denpun";
        public static final String	VB_PROVIDER		= "MSDASQL";
        public static final String	VB_DB_DATASOURCE	 = "denpun";
        public static final String	VB_DB_USER		= "zennoh";
        public static final String	VB_DB_PASS		= "zennoh";


        //++****************************************************
        //	業務関連
        //--****************************************************
        //会社マスタ　会社コード
        public static final String	SAGAMI_KAICD	= "000";
        //月初めの日を設定
        public static final int 	START_MONTH	= 21;
        //++****************************************************
        //	処理モード
        //--****************************************************
        public static final	String	MODE_OPER_STR_NEW	= "登録";
        public static final	String	MODE_OPER_STR_MODIFY	= "修正";
        public static final	String	MODE_OPER_STR_DELETE	= "削除";
        public static final	String	MODE_OPER_STR_PREVIEW	= "照会";

        public static final	String	BUTTON_STR_MODE_NEW	= "登録";
        public static final	String	BUTTON_STR_MODE_MODIFY	= "修正";
        public static final	String	BUTTON_STR_MODE_DELETE	= "削除";
        public static final	String	BUTTON_STR_MODE_PREVIEW	= "照会";

        public static final	int	OPR_LIST_SELECT_NEW	= 0;	// 登録
        public static final	int	OPR_LIST_SELECT_MODIFY	= 1;	// 修正
        public static final	int	OPR_LIST_SELECT_DELETE	= 2;	// 削除
        public static final	int	OPR_LIST_SELECT_PREVIEW	= 3;	// 新規分割

        public static final	int	MODE_OPER_INIT		= 0;	// 初期状態
        public static final	int	MODE_OPER_NEW		= 1;	// 新規
        public static final	int	MODE_OPER_MODIFY	= 2;	// 修正
        public static final	int	MODE_OPER_DELETE	= 3;	// 削除
        public static final	int	MODE_OPER_PREVIEW	= 4;	// 削除
        public static final	int	MODE_OPER_CHECK		= 5;	// データ判定
        public static final	int	MODE_OPER_LIST		= 6;	// 帳票出力
        public static final	int	MODE_OPER_EXEC		= 7;	// 実行
        public static final	int	MODE_OPER_GET		= 8;	// データ抽出

        //++****************************************************
        //	処理区分
        //--****************************************************
        public static final	String	SERVER_IF_OPKBN_GET	= "INQ";	// 抽出要求
        public static final	String	SERVER_IF_OPKBN_NEW	= "ADD";	// 追加要求
        public static final	String	SERVER_IF_OPKBN_MODIFY	= "UPD";	// 更新要求
        public static final	String	SERVER_IF_OPKBN_DELETE	= "DEL";	// 削除要求
        public static final	String	SERVER_IF_OPKBN_CHECK	= "chk";	// チェック要求
        public static final	String	SERVER_IF_OPKBN_LIST	= "LST";	// 帳票出力要求

        public static final	String	SERVER_IF_REQ_MODE_CHECK	= "0";	// チェック要求
        public static final	String	SERVER_IF_REQ_MODE_EXEC		= "1";	// 処理実行要求
        public static final	String	SERVER_IF_REQ_MODE_EXEC_AUTO	= "2";	// 処理実行要求

        //++****************************************************
        //	選択範囲
        //--****************************************************
        public static final	String	SERVER_IF_RANGE_NONE	= "0";	// 指定なし
        public static final	String	SERVER_IF_RANGE_FROM	= "1";	// From のみ
        public static final	String	SERVER_IF_RANGE_TO	= "2";	// To のみ
        public static final	String	SERVER_IF_RANGE_BOTH	= "3";	// 両方指定

        //++****************************************************
        //	IF_xxx_E 区分
        //--****************************************************
        public static final	String	SERVER_IF_INPUT_NONE	= "N";	// 未入力状態
        public static final	String	SERVER_IF_INPUT_PROTECT	= "P";	// 入力不可
        public static final	String	SERVER_IF_INPUT_ERROR	= "E";	// エラー

        //++****************************************************
        //	名称検索関係
        //--****************************************************
        //  出荷者
        public static final String GET_MSTNAME_SYU	= "SYU";
        //  販売先
        public static final String GET_MSTNAME_HAN	= "HAN";
        //  銘柄
        public static final String GET_MSTNAME_MEI	= "MEI";
        //  品名
        public static final String GET_MSTNAME_HIN	= "HIN";
        //  用途ユーザー
        public static final String GET_MSTNAME_USR	= "USR";
        //  受渡場所
        public static final String GET_MSTNAME_UKE	= "UKE";
        //  コードテーブル
        public static final String GET_MSTNAME_COD	= "COD";
        //++****************************************************
        //	コード検索関係（抽出条件無し）
        //--****************************************************
        //  出荷者
        public static final String CODE_SEEK_SYU = "SYU";
        public static final String NAME_SEEK_SYU = "出荷者";

        //  販売先
        public static final String CODE_SEEK_HAN = "HAN";
        public static final String NAME_SEEK_HAN = "販売先";

        //  銘柄
        public static final String CODE_SEEK_MEI = "MEI";
        public static final String NAME_SEEK_MEI = "銘柄";

        //  品名
        public static final String CODE_SEEK_HIN = "HIN";
        public static final String NAME_SEEK_HIN = "品名";

        //  用途ユーザー
        public static final String CODE_SEEK_USR = "USR";
        public static final String NAME_SEEK_USR = "ユーザー";

        //  受渡場所
        public static final String CODE_SEEK_UKE = "UKE";
        public static final String NAME_SEEK_UKE = "受渡場所";

        //  コードテーブル
        public static final String CODE_SEEK_COD = "COD";
        public static final String NAME_SEEK_COD = "";

        //++****************************************************
        //	コンボ関連
        //--****************************************************
        //コンボ受信最大件数
        public  static final	int	COMBOX_MAX_NUM	= 100;

        //++****************************************************
        //	和暦・西暦変換関連
        //  <<天皇陛下がご逝去された場合新しい年号と元年の西暦を追加>>
        //--****************************************************
        //年号
        public static final char NENGOU_TAISHOU = 'T';
        public static final char NENGOU_SHOUWA  = 'S';
        public static final char NENGOU_HEISEI  = 'H';
        //デフォルトの年号
        public static final char NENGOU_DEFAULT = 'H';
        //西暦マイナス和暦
        //元年の西暦-1
        public static final int WAREKI_TAISHOU = 1911;
        public static final int WAREKI_SHOUWA  = 1925;
        public static final int WAREKI_HEISEI  = 1988;

        //デフォルトの西暦
        public static final int WAREKI_DEFAULT = 1988;
        public static final boolean   FTP_OK   = false;

        //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
        //add by yery on 2004/11/15 {
        //画面顔色の設定
        public static final java.awt.Color COLOR_TITLE_BG = new java.awt.Color(0x02,0xB0,0x02);
        public static final java.awt.Color COLOR_MAIN_BG = new java.awt.Color(0x99,0xE0,0x99);
        public static final java.awt.Color COLOR_BOTTOM_BG = new java.awt.Color(0x02,0xB0,0x02);
        public static final java.awt.Color COLOR_BUTTON = new java.awt.Color(192,192,192);
        public static final java.awt.Color COLOR_BCBBUTTON = new java.awt.Color(150,150,150);
        public static final java.awt.Color COLOR_DISP = new java.awt.Color(192,192,192);
        public static final java.awt.Color COLOR_LABEL = new java.awt.Color(255,255,153);

        //コードテーブルのコード区分設定
        public static final String KBN_CODEMANAGE="00"; //コード管理区分
        public static final String KBN_HONJIGYOUSYO="01";  //本事業所
        public static final String KBN_HANBAI="02";  //販売区分
        public static final String KBN_KEIYAKU="03";  //契約形態
        public static final String KBN_TOUKYUU="04";  //等級コード
        public static final String KBN_NISUGATA="05";  //荷姿コード
        public static final String KBN_NYOUME="06";  //量目コード
        public static final String KBN_TATENEBASYO="07";  //建値場所
        public static final String KBN_KEN="08";  //08県コード
        public static final String KBN_SEKIKI="09";  //積期コード
        public static final String KBN_DAIKINKESAIJYOUKEN="10";  //代金決済条件
        public static final String KBN_TOMOKEISISUU="11";  //共計次数
        public static final String KBN_SYOGAKARI="12";  //諸掛コード
        public static final String KBN_TATEKAESYOGAKARI="13";  //立替諸掛コード
        public static final String KBN_JIKO="14";  //事故コード
        public static final String KBN_SEISANKUBUN="15";  //精算区分
        public static final String KBN_TEISAI="16";  //訂正区分
        public static final String KBN_TORIATSUKAI="17";  //取扱区分
        public static final String KBN_RENDOUTORIATSUKAI="18";  //連動取扱区分
        public static final String KBN_KOUJYO="19";  //控除区分
        public static final String KBN_TYAKUEKIATSUKAITEN="20";  //着駅扱店コード
        public static final String KBN_YUSOUATSUKAITEN="21";  //輸送扱店コード
        public static final String KBN_YUSOUSYUDAN="22";  //輸送手段コード
        public static final String KBN_SYUKKAYOTEI="23";  //出荷予定区分
        public static final String KBN_DENPYOU="24";  //伝票区分・処理コード
        public static final String KBN_TEKIYOU="25";  //摘要区分
        public static final String KBN_TOUHOKU="26";  //東北区分
        public static final String KBN_KIHYOU="27";  //起票区分
        public static final String KBN_KIHYOUSIJI="28";  //起票指示区分
        public static final String KBN_YOUTO="29";  //用途コード
        public static final String KBN_KISAN="30";  //起算区分
        public static final String KBN_KURIAGE="31";  //繰上区分
        public static final String KBN_SYUKKORYOUFUTANSAKI="32";  //出庫料負担先区分
        public static final String KBN_CODECONTROLLDATE="33"; //コントロール日付
        public static final String KBN_DENPYO="34"; //伝票区分
        public static final String KBN_DENPYOUSYORI="35"; //伝票処理
        public static final String KBN_TAXRATE="36"; //消費税率
        public static final String KBN_MEIGARA="51";  //銘柄コード
        public static final String KBN_GEPPOU="52";  //月報・品名コード
        public static final String KBN_TYOUHYOU="91";  //帳票名称
        // } add by yery on 2004/11/15
        //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■

        //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
        //add by yery on 2004/11/19 {
        //販売区分の設定
        public static final String HANBAI_COMMON = "1"; //一般販売
        public static final String HANBAI_01=""; //糖化用自社引取
        public static final String HANBAI_02=""; //糖化用交換
        public static final String HANBAI_03=""; //一般用自社引取
        public static final String HANBAI_04=""; //一般用交換
        public static final String HANBAI_OTHER=""; //その他販売
        // } add by yery on 2004/11/15
        //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■

        public static final String BACKUP_DIR="D:/Backup"; //ﾊﾞｯｸｱｯﾌﾟ
        public static final String RPT_SYSTEM  = "D:/Tomcat50/Webapps/RPT_SYSTEM/NIGHT"; //レポートＰＤＦの場所
}
