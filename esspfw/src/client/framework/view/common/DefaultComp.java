package client.framework.view.common;

import java.awt.*;
import javax.swing.*;

public class DefaultComp
{
    public static final String	FIELD_ERROR		= "E";		//	����״̬
    public static final String	FIELD_PROTECT		= "P";		//	����״̬

    public static final Color	INPUT_ERROR_FONT_COLOR	= Color.red;  //text����ʱ����ʾ��ɫ
    public static final Color	DISABLED_FONT_COLOR	= Color.black; //text������ʱ����ʾ��ɫ

    //ͳһ�Ŀؼ��߶�
    public static final int TEXT_HEIGHT = 20;

    //   Ĭ������
    public static final Font	DEFUALT_FONT		= new java.awt.Font("����",java.awt.Font.PLAIN,12);
    //������
    //б��
    public static final Font	DEFUALT_ITALIC_FONT		= new java.awt.Font("����",java.awt.Font.ITALIC,12);
    public static final Font	DEFUALT_BOLD_FONT		= new java.awt.Font("����",java.awt.Font.BOLD,12);
    //  ��{�t�H���g
    public static final String	DEFUALT_FONT_TYPE	= "Arial";


    //  �\���ʒu
    public static final int		ALIGNMENT_LEFT		= SwingConstants.LEFT;
    public static final int		ALIGNMENT_RIGHT		= SwingConstants.RIGHT;
    public static final int		ALIGNMENT_TOP		= SwingConstants.TOP;
    public static final int		ALIGNMENT_CENTER	= SwingConstants.CENTER;

    //  ��{�F(��ʔw�i�F�j
    public static final Color	BACKGROUND_COLOR_PANEL	=  new java.awt.Color(255,228,181);

    //��������Ŀؼ��ı���ɫ
    public static final	Color	BACKGROUND_COLOR_INPUT_ACTIVE	=  new java.awt.Color(134,230,255);

    public static final	Color	BACKGROUND_COLOR_ENABLED	= Color.white;
    public static final	Color	BACKGROUND_COLOR_DISABLED	= new java.awt.Color(228,226,210);
    public static final	Color	BACKGROUND_COLOR_ERROR		= Color.red;
    public static final	Color	BACKGROUND_COLOR_NORMAL     = Color.white;

    public static final	Color	FOREGROUND_COLOR_NORMAL		= Color.black; //text����ʱ��ʾ����ɫ
    public static final	Color	FOREGROUND_COLOR_ERROR		= Color.red; //text����ʱ��ʾ����ɫ
    public static final	Color	FOREGROUND_COLOR_INACT		= java.awt.Color.black;

    //  ��ѡ��ʱ��foreground color �� background color
    public static final	Color	FOREGROUND_COLOR_SELECT		= Color.white;
    public static final	Color	BACKGROUND_COLOR_SELECT		= new java.awt.Color(0,0,118);

    //�@�|�b�v�A�b�v�w�i
    public static final Color   BACKGROUND_COLOR_POPUP		= new java.awt.Color(255,255,196);

    //++********************************
    //	JMsLabel
    //--********************************
    //	�������
    public static final Font	LABEL_FONT	= DEFUALT_FONT;

    //++********************************
    //	JMsLabelTitle
    //--********************************
    //	�������
    public static final Font	LABEL_TITLE_FONT = DEFUALT_FONT;

    //	�F
    public static final	Color	LABEL_TITLE_FOREGROUND_COLOR	= Color.white;
    public static final	Color	LABEL_TITLE_BACKGROUND_COLOR	= new Color(205, 92, 92);

    //	�g�̎��
    public static final	int	LABEL_TITLE_BORDER_WIDTH	= 1;

    //++********************************
    //	JMsGroupTitle
    //--********************************
    //	�������
    public static final	Font	GROUP_TITLE_FONT		= DEFUALT_FONT;

    //	�F
    public static final	Color	GROUP_TITLE_BACKGROUND_COLOR	= new Color(205, 92, 92);
    public static final	Color	GROUP_TITLE_FOREGROUND_COLOR	= java.awt.Color.white;

    //++********************************
    //	JMsFormTitle
    //--********************************
    //	�������
    public static final	Font	FORM_TITLE_FONT			= new java.awt.Font("Arial",java.awt.Font.BOLD,16);

    //	�F
    public static final	Color	FORM_TITLE_BACKGROUND_COLOR	= new Color(0,128,0);
    public static final	Color	FORM_TITLE_FOREGROUND_COLOR	= java.awt.Color.white;

    //++********************************
    //	JMsText
    //--********************************
    public static final	Font	TEXT_FONT			= DEFUALT_FONT; //	����

    //
    public static final	int	TEXT_ALIGNMENT_H		= ALIGNMENT_LEFT;
    public static final	int	TEXT_ALIGNMENT_V		= ALIGNMENT_CENTER;

    public static final int		TEXT_MAX_BYTE_LENGTH	= -1; //��󳤶�
    //
    public static final int		PASS_MAX_BYTE_LENGTH	= 4;

    //++********************************
    //	JMsDisp
    //--********************************
    //	�������
    public static final	Font	DISP_DATA_FONT				= DEFUALT_FONT;
    public static final	Color	DISP_DATA_INACT_FOREGROUND_COLOR	= java.awt.Color.black;
    public static final	Color	DISP_DATA_BACKGROUND_COLOR		= new java.awt.Color(228,226,210);

    //++********************************
    //	JMsDispNumber
    //--********************************
    //	�������
    public static final	Font	DISP_NUMBER_FONT			= DEFUALT_FONT;

    //	�F
    public static final	Color	DISP_NUMBER_BACKGROUND_COLOR		= new java.awt.Color(228,226,210);
    public static final	Color	DISP_NUMBER_INACT_FOREGROUND_COLOR	= java.awt.Color.black;

    //	���͏���
    public static final	int	DISP_NUMBER_MAX_INTEGER_DIGIT		= 7;

    //	�\���`��
    public static final	String	DISP_NUMBER_OUTPUT_FORMAT		= "#,##0";

    //++********************************
    //	JMsDispReal
    //--********************************
    //	�������
    public static final	Font	DISP_REAL_FONT				= DEFUALT_FONT;

    //	�\���g
    public static final	int	DISP_REAL_BORDER_WIDTH			= 1;

    //	�F
    public static final	Color	DISP_REAL_BACKGROUND_COLOR		= new java.awt.Color(228,226,210);
    public static final	Color	DISP_REAL_INACT_FOREGROUND_COLOR	= java.awt.Color.black;

    //	���͏���
    public static final	int	DISP_REAL_MAX_DECIMAL_DIGIT		= 7;
    public static final	int	DISP_REAL_MAX_INTEGER_DIGIT		= 2;

    //	�\���`��
    public static final	String	DISP_REAL_OUTPUT_FORMAT			= "#,##0.00";

    //++********************************
    //	JMsDispDate
    //--********************************
    //	�������
    public static final	Font	DISP_DATE_FONT				= DEFUALT_FONT;

    //	�\���g
    public static final	int	DISP_DATE_BORDER_WIDTH			= 1;

    //	�F
    public static final	Color	DISP_DATE_BACKGROUND_COLOR		= new java.awt.Color(228,226,210);
    public static final	Color	DISP_DATE_INACT_FOREGROUND_COLOR	= java.awt.Color.black;

    //++********************************
    //	JMsButton
    //--********************************
    //	�������
    public static final	Font	BUTTON_FONT				= DEFUALT_FONT;

    //++********************************
    //	JMsButtonCombo
    //--********************************
    //	�������
    public static final	Font	BUTTON_COMBO_FONT			= DEFUALT_FONT;
    public static final	String	BUTTON_COMBO_TEXT			= "Run";

    //++********************************
    //	JMsCheckBox
    //--********************************
    //	�������
    public static final	Font	CHECK_BOX_FONT				= DEFUALT_FONT;

    //++********************************
    //	JMsRadioButton
    //--********************************
    //	�������
    public static final	Font	RADIO_BUTTON_FONT			= DEFUALT_FONT;

    //++********************************
    //	JMsDate
    //--********************************
    //	�������
    public static final	Font	DATE_FONT				= DEFUALT_FONT;
    public static final	int		DATE_MAX_BYTE_LENGTH		= -1;

    //++********************************
    //	JMsNumber
    //--********************************
    //	�������
    public static final	Font	NUMBER_FONT				= DEFUALT_FONT;

    //	���͏���
    public static final	int	NUMBER_MAX_INTEGER_DIGIT		= -1;

    //	�\���`��
    public static final	String	NUMBER_OUTPUT_FORMAT			= "#,##0";

    //++********************************
    //	JMsReal
    //--********************************
    //	�������
    public static final	Font	REAL_FONT				= DEFUALT_FONT;

    //	���͏���
        public static final	int 	REAL_MAX_DECIMAL_DIGIT		= 2;
    public static final	int	REAL_MAX_INTEGER_DIGIT		= 7;

    //	�\���`��
    public static final	String	REAL_OUTPUT_FORMAT			= "#,##0.00";

    //++********************************
    //	JMsComboBox
    //--********************************
    //	�������
    public static final	Font	COMBO_BOX_FONT				= DEFUALT_FONT;

    //++********************************
    //	JMsLine
    //--********************************
    //	�F
    public static final	Color	LINE_BACKGROUND_COLOR			= Color.black;

    //++********************************
    //	JMsFrameLine
    //--********************************
    //	�\���g
    public static final	int		FRAME_LINE_BORDER_WIDTH			= 1;

}
