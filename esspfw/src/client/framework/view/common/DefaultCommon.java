package client.framework.view.common;
public class DefaultCommon
{
//������������������������������������������������������������������������������������������������������
//  �{�Ԕ��f���̒��ӎ���
//�@�i�P�j�ڑ����URL��ύX���邱��
//�@�i�Q�j�o�b�`�p��DB�̐ڑ����ύX���鎖
//������������������������������������������������������������������������������������������������������

        public static final String	JDBC_SYBASE_URL		= "jdbc:sybase:Tds:10.5.2.33:3000/";
        public static final String	SERVLET_URL_IP		= "http://localhost:8080/DENPUN/";
//	public static final String	JDBC_SYBASE_URL		= "jdbc:sybase:Tds:10.1.10.90:3000/";
//	public static final String	SERVLET_URL_IP		= "http://10.1.10.90:8080/SHIITAKE/";
//	public static final String	SERVLET_URL_IP		= "http://192.168.1.98:8080/";

        //���[�U�ƃp�X���[�h
        public static final String	JDBC_SYBASE_USER	= "zennoh";
        public static final String	JDBC_SYBASE_PASS	= "zennoh";

        //���s���[�h   0:���[�J���A�N�Z�X(�J��)  1:�T�[�o�A�N�Z�X�{��
        public static final int	OPER_EXEC_MODE		= 0;

        //���\�[�X�̃��[�J����ł̃t���p�X
        public static final String	SYSTEM_ROOT		= "D:\\Tomcat50\\webapps\\DENPUN\\WEB-INF\\classes\\";
        //root�f�B���N�g��
        public static final String	ROOT_DIRECTORY		= "D:\\Tomcat50\\webapps\\DENPUN\\";
        //�V�X�e���J�����[�h(MS:0�j
        public static final int	SYSTEM_MODE		= 1;
        public static final String	URL			= "";			//URL
        public static final String	SERVLET_URL		= "";
        public static final String	LOCAL_ADDRESS		= "";			//���[�J���z�X�g
        public static final String	LOCAL_COM_ADDRESS	= "";			//���[�J���z�X�g����
        public static final String	LOCAL_CLOSE_ADDRESS	= "";			//END�y�[�W
        public static final String	APPLET_SERVLET		= "AppServlet";		//�A�v���b�g�T�[�u���b�g�̏ꏊ
        public static final String	REPORT_SERVLET		= "PrintServlet";	//���|�[�g�T�[�u���b�g�̏ꏊ

        public static final String	VB_CONECTSTRING		= "denpun";
        public static final String	VB_PROVIDER		= "MSDASQL";
        public static final String	VB_DB_DATASOURCE	 = "denpun";
        public static final String	VB_DB_USER		= "zennoh";
        public static final String	VB_DB_PASS		= "zennoh";


        //++****************************************************
        //	�Ɩ��֘A
        //--****************************************************
        //��Ѓ}�X�^�@��ЃR�[�h
        public static final String	SAGAMI_KAICD	= "000";
        //�����߂̓���ݒ�
        public static final int 	START_MONTH	= 21;
        //++****************************************************
        //	�������[�h
        //--****************************************************
        public static final	String	MODE_OPER_STR_NEW	= "�o�^";
        public static final	String	MODE_OPER_STR_MODIFY	= "�C��";
        public static final	String	MODE_OPER_STR_DELETE	= "�폜";
        public static final	String	MODE_OPER_STR_PREVIEW	= "�Ɖ�";

        public static final	String	BUTTON_STR_MODE_NEW	= "�o�^";
        public static final	String	BUTTON_STR_MODE_MODIFY	= "�C��";
        public static final	String	BUTTON_STR_MODE_DELETE	= "�폜";
        public static final	String	BUTTON_STR_MODE_PREVIEW	= "�Ɖ�";

        public static final	int	OPR_LIST_SELECT_NEW	= 0;	// �o�^
        public static final	int	OPR_LIST_SELECT_MODIFY	= 1;	// �C��
        public static final	int	OPR_LIST_SELECT_DELETE	= 2;	// �폜
        public static final	int	OPR_LIST_SELECT_PREVIEW	= 3;	// �V�K����

        public static final	int	MODE_OPER_INIT		= 0;	// �������
        public static final	int	MODE_OPER_NEW		= 1;	// �V�K
        public static final	int	MODE_OPER_MODIFY	= 2;	// �C��
        public static final	int	MODE_OPER_DELETE	= 3;	// �폜
        public static final	int	MODE_OPER_PREVIEW	= 4;	// �폜
        public static final	int	MODE_OPER_CHECK		= 5;	// �f�[�^����
        public static final	int	MODE_OPER_LIST		= 6;	// ���[�o��
        public static final	int	MODE_OPER_EXEC		= 7;	// ���s
        public static final	int	MODE_OPER_GET		= 8;	// �f�[�^���o

        //++****************************************************
        //	�����敪
        //--****************************************************
        public static final	String	SERVER_IF_OPKBN_GET	= "INQ";	// ���o�v��
        public static final	String	SERVER_IF_OPKBN_NEW	= "ADD";	// �ǉ��v��
        public static final	String	SERVER_IF_OPKBN_MODIFY	= "UPD";	// �X�V�v��
        public static final	String	SERVER_IF_OPKBN_DELETE	= "DEL";	// �폜�v��
        public static final	String	SERVER_IF_OPKBN_CHECK	= "chk";	// �`�F�b�N�v��
        public static final	String	SERVER_IF_OPKBN_LIST	= "LST";	// ���[�o�͗v��

        public static final	String	SERVER_IF_REQ_MODE_CHECK	= "0";	// �`�F�b�N�v��
        public static final	String	SERVER_IF_REQ_MODE_EXEC		= "1";	// �������s�v��
        public static final	String	SERVER_IF_REQ_MODE_EXEC_AUTO	= "2";	// �������s�v��

        //++****************************************************
        //	�I��͈�
        //--****************************************************
        public static final	String	SERVER_IF_RANGE_NONE	= "0";	// �w��Ȃ�
        public static final	String	SERVER_IF_RANGE_FROM	= "1";	// From �̂�
        public static final	String	SERVER_IF_RANGE_TO	= "2";	// To �̂�
        public static final	String	SERVER_IF_RANGE_BOTH	= "3";	// �����w��

        //++****************************************************
        //	IF_xxx_E �敪
        //--****************************************************
        public static final	String	SERVER_IF_INPUT_NONE	= "N";	// �����͏��
        public static final	String	SERVER_IF_INPUT_PROTECT	= "P";	// ���͕s��
        public static final	String	SERVER_IF_INPUT_ERROR	= "E";	// �G���[

        //++****************************************************
        //	���̌����֌W
        //--****************************************************
        //  �o�׎�
        public static final String GET_MSTNAME_SYU	= "SYU";
        //  �̔���
        public static final String GET_MSTNAME_HAN	= "HAN";
        //  ����
        public static final String GET_MSTNAME_MEI	= "MEI";
        //  �i��
        public static final String GET_MSTNAME_HIN	= "HIN";
        //  �p�r���[�U�[
        public static final String GET_MSTNAME_USR	= "USR";
        //  ��n�ꏊ
        public static final String GET_MSTNAME_UKE	= "UKE";
        //  �R�[�h�e�[�u��
        public static final String GET_MSTNAME_COD	= "COD";
        //++****************************************************
        //	�R�[�h�����֌W�i���o���������j
        //--****************************************************
        //  �o�׎�
        public static final String CODE_SEEK_SYU = "SYU";
        public static final String NAME_SEEK_SYU = "�o�׎�";

        //  �̔���
        public static final String CODE_SEEK_HAN = "HAN";
        public static final String NAME_SEEK_HAN = "�̔���";

        //  ����
        public static final String CODE_SEEK_MEI = "MEI";
        public static final String NAME_SEEK_MEI = "����";

        //  �i��
        public static final String CODE_SEEK_HIN = "HIN";
        public static final String NAME_SEEK_HIN = "�i��";

        //  �p�r���[�U�[
        public static final String CODE_SEEK_USR = "USR";
        public static final String NAME_SEEK_USR = "���[�U�[";

        //  ��n�ꏊ
        public static final String CODE_SEEK_UKE = "UKE";
        public static final String NAME_SEEK_UKE = "��n�ꏊ";

        //  �R�[�h�e�[�u��
        public static final String CODE_SEEK_COD = "COD";
        public static final String NAME_SEEK_COD = "";

        //++****************************************************
        //	�R���{�֘A
        //--****************************************************
        //�R���{��M�ő匏��
        public  static final	int	COMBOX_MAX_NUM	= 100;

        //++****************************************************
        //	�a��E����ϊ��֘A
        //  <<�V�c�É������������ꂽ�ꍇ�V�����N���ƌ��N�̐����ǉ�>>
        //--****************************************************
        //�N��
        public static final char NENGOU_TAISHOU = 'T';
        public static final char NENGOU_SHOUWA  = 'S';
        public static final char NENGOU_HEISEI  = 'H';
        //�f�t�H���g�̔N��
        public static final char NENGOU_DEFAULT = 'H';
        //����}�C�i�X�a��
        //���N�̐���-1
        public static final int WAREKI_TAISHOU = 1911;
        public static final int WAREKI_SHOUWA  = 1925;
        public static final int WAREKI_HEISEI  = 1988;

        //�f�t�H���g�̐���
        public static final int WAREKI_DEFAULT = 1988;
        public static final boolean   FTP_OK   = false;

        //��������������������������������������������������������������������������������
        //add by yery on 2004/11/15 {
        //��ʊ�F�̐ݒ�
        public static final java.awt.Color COLOR_TITLE_BG = new java.awt.Color(0x02,0xB0,0x02);
        public static final java.awt.Color COLOR_MAIN_BG = new java.awt.Color(0x99,0xE0,0x99);
        public static final java.awt.Color COLOR_BOTTOM_BG = new java.awt.Color(0x02,0xB0,0x02);
        public static final java.awt.Color COLOR_BUTTON = new java.awt.Color(192,192,192);
        public static final java.awt.Color COLOR_BCBBUTTON = new java.awt.Color(150,150,150);
        public static final java.awt.Color COLOR_DISP = new java.awt.Color(192,192,192);
        public static final java.awt.Color COLOR_LABEL = new java.awt.Color(255,255,153);

        //�R�[�h�e�[�u���̃R�[�h�敪�ݒ�
        public static final String KBN_CODEMANAGE="00"; //�R�[�h�Ǘ��敪
        public static final String KBN_HONJIGYOUSYO="01";  //�{���Ə�
        public static final String KBN_HANBAI="02";  //�̔��敪
        public static final String KBN_KEIYAKU="03";  //�_��`��
        public static final String KBN_TOUKYUU="04";  //�����R�[�h
        public static final String KBN_NISUGATA="05";  //�׎p�R�[�h
        public static final String KBN_NYOUME="06";  //�ʖڃR�[�h
        public static final String KBN_TATENEBASYO="07";  //���l�ꏊ
        public static final String KBN_KEN="08";  //08���R�[�h
        public static final String KBN_SEKIKI="09";  //�ϊ��R�[�h
        public static final String KBN_DAIKINKESAIJYOUKEN="10";  //������Ϗ���
        public static final String KBN_TOMOKEISISUU="11";  //���v����
        public static final String KBN_SYOGAKARI="12";  //���|�R�[�h
        public static final String KBN_TATEKAESYOGAKARI="13";  //���֏��|�R�[�h
        public static final String KBN_JIKO="14";  //���̃R�[�h
        public static final String KBN_SEISANKUBUN="15";  //���Z�敪
        public static final String KBN_TEISAI="16";  //�����敪
        public static final String KBN_TORIATSUKAI="17";  //�戵�敪
        public static final String KBN_RENDOUTORIATSUKAI="18";  //�A���戵�敪
        public static final String KBN_KOUJYO="19";  //�T���敪
        public static final String KBN_TYAKUEKIATSUKAITEN="20";  //���w���X�R�[�h
        public static final String KBN_YUSOUATSUKAITEN="21";  //�A�����X�R�[�h
        public static final String KBN_YUSOUSYUDAN="22";  //�A����i�R�[�h
        public static final String KBN_SYUKKAYOTEI="23";  //�o�ח\��敪
        public static final String KBN_DENPYOU="24";  //�`�[�敪�E�����R�[�h
        public static final String KBN_TEKIYOU="25";  //�E�v�敪
        public static final String KBN_TOUHOKU="26";  //���k�敪
        public static final String KBN_KIHYOU="27";  //�N�[�敪
        public static final String KBN_KIHYOUSIJI="28";  //�N�[�w���敪
        public static final String KBN_YOUTO="29";  //�p�r�R�[�h
        public static final String KBN_KISAN="30";  //�N�Z�敪
        public static final String KBN_KURIAGE="31";  //�J��敪
        public static final String KBN_SYUKKORYOUFUTANSAKI="32";  //�o�ɗ����S��敪
        public static final String KBN_CODECONTROLLDATE="33"; //�R���g���[�����t
        public static final String KBN_DENPYO="34"; //�`�[�敪
        public static final String KBN_DENPYOUSYORI="35"; //�`�[����
        public static final String KBN_TAXRATE="36"; //����ŗ�
        public static final String KBN_MEIGARA="51";  //�����R�[�h
        public static final String KBN_GEPPOU="52";  //����E�i���R�[�h
        public static final String KBN_TYOUHYOU="91";  //���[����
        // } add by yery on 2004/11/15
        //��������������������������������������������������������������������������������

        //��������������������������������������������������������������������������������
        //add by yery on 2004/11/19 {
        //�̔��敪�̐ݒ�
        public static final String HANBAI_COMMON = "1"; //��ʔ̔�
        public static final String HANBAI_01=""; //�����p���Ј���
        public static final String HANBAI_02=""; //�����p����
        public static final String HANBAI_03=""; //��ʗp���Ј���
        public static final String HANBAI_04=""; //��ʗp����
        public static final String HANBAI_OTHER=""; //���̑��̔�
        // } add by yery on 2004/11/15
        //��������������������������������������������������������������������������������

        public static final String BACKUP_DIR="D:/Backup"; //�ޯ�����
        public static final String RPT_SYSTEM  = "D:/Tomcat50/Webapps/RPT_SYSTEM/NIGHT"; //���|�[�g�o�c�e�̏ꏊ
}
