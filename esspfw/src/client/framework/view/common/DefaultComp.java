package client.framework.view.common;

import java.awt.*;
import javax.swing.*;

public class DefaultComp
{
    public static final String	FIELD_ERROR		= "E";		//	错误状态
    public static final String	FIELD_PROTECT		= "P";		//	保护状态

    public static final Color	INPUT_ERROR_FONT_COLOR	= Color.red;  //text错误时的显示颜色
    public static final Color	DISABLED_FONT_COLOR	= Color.black; //text不可输时的显示颜色

    //统一的控件高度
    public static final int TEXT_HEIGHT = 20;

    //   默认字体
    public static final Font	DEFUALT_FONT		= new java.awt.Font("宋体",java.awt.Font.PLAIN,12);
    //粗体字
    //斜体
    public static final Font	DEFUALT_ITALIC_FONT		= new java.awt.Font("宋体",java.awt.Font.ITALIC,12);
    public static final Font	DEFUALT_BOLD_FONT		= new java.awt.Font("宋体",java.awt.Font.BOLD,12);
    //  婎杮僼僅儞僩
    public static final String	DEFUALT_FONT_TYPE	= "Arial";


    //  昞帵埵抲
    public static final int		ALIGNMENT_LEFT		= SwingConstants.LEFT;
    public static final int		ALIGNMENT_RIGHT		= SwingConstants.RIGHT;
    public static final int		ALIGNMENT_TOP		= SwingConstants.TOP;
    public static final int		ALIGNMENT_CENTER	= SwingConstants.CENTER;

    //  婎杮怓(夋柺攚宨怓乯
    public static final Color	BACKGROUND_COLOR_PANEL	=  new java.awt.Color(255,228,181);

    //正在输入的控件的背景色
    public static final	Color	BACKGROUND_COLOR_INPUT_ACTIVE	=  new java.awt.Color(134,230,255);

    public static final	Color	BACKGROUND_COLOR_ENABLED	= Color.white;
    public static final	Color	BACKGROUND_COLOR_DISABLED	= new java.awt.Color(228,226,210);
    public static final	Color	BACKGROUND_COLOR_ERROR		= Color.red;
    public static final	Color	BACKGROUND_COLOR_NORMAL     = Color.white;

    public static final	Color	FOREGROUND_COLOR_NORMAL		= Color.black; //text正常时显示的颜色
    public static final	Color	FOREGROUND_COLOR_ERROR		= Color.red; //text错误时显示的颜色
    public static final	Color	FOREGROUND_COLOR_INACT		= java.awt.Color.black;

    //  被选中时的foreground color 和 background color
    public static final	Color	FOREGROUND_COLOR_SELECT		= Color.white;
    public static final	Color	BACKGROUND_COLOR_SELECT		= new java.awt.Color(0,0,118);

    //丂億僢僾傾僢僾攚宨
    public static final Color   BACKGROUND_COLOR_POPUP		= new java.awt.Color(255,255,196);

    //++********************************
    //	JMsLabel
    //--********************************
    //	暥帤忣曬
    public static final Font	LABEL_FONT	= DEFUALT_FONT;

    //++********************************
    //	JMsLabelTitle
    //--********************************
    //	暥帤忣曬
    public static final Font	LABEL_TITLE_FONT = DEFUALT_FONT;

    //	怓
    public static final	Color	LABEL_TITLE_FOREGROUND_COLOR	= Color.white;
    public static final	Color	LABEL_TITLE_BACKGROUND_COLOR	= new Color(205, 92, 92);

    //	榞偺庬椶
    public static final	int	LABEL_TITLE_BORDER_WIDTH	= 1;

    //++********************************
    //	JMsGroupTitle
    //--********************************
    //	暥帤忣曬
    public static final	Font	GROUP_TITLE_FONT		= DEFUALT_FONT;

    //	怓
    public static final	Color	GROUP_TITLE_BACKGROUND_COLOR	= new Color(205, 92, 92);
    public static final	Color	GROUP_TITLE_FOREGROUND_COLOR	= java.awt.Color.white;

    //++********************************
    //	JMsFormTitle
    //--********************************
    //	暥帤忣曬
    public static final	Font	FORM_TITLE_FONT			= new java.awt.Font("Arial",java.awt.Font.BOLD,16);

    //	怓
    public static final	Color	FORM_TITLE_BACKGROUND_COLOR	= new Color(0,128,0);
    public static final	Color	FORM_TITLE_FOREGROUND_COLOR	= java.awt.Color.white;

    //++********************************
    //	JMsText
    //--********************************
    public static final	Font	TEXT_FONT			= DEFUALT_FONT; //	字体

    //
    public static final	int	TEXT_ALIGNMENT_H		= ALIGNMENT_LEFT;
    public static final	int	TEXT_ALIGNMENT_V		= ALIGNMENT_CENTER;

    public static final int		TEXT_MAX_BYTE_LENGTH	= -1; //最大长度
    //
    public static final int		PASS_MAX_BYTE_LENGTH	= 4;

    //++********************************
    //	JMsDisp
    //--********************************
    //	暥帤忣曬
    public static final	Font	DISP_DATA_FONT				= DEFUALT_FONT;
    public static final	Color	DISP_DATA_INACT_FOREGROUND_COLOR	= java.awt.Color.black;
    public static final	Color	DISP_DATA_BACKGROUND_COLOR		= new java.awt.Color(228,226,210);

    //++********************************
    //	JMsDispNumber
    //--********************************
    //	暥帤忣曬
    public static final	Font	DISP_NUMBER_FONT			= DEFUALT_FONT;

    //	怓
    public static final	Color	DISP_NUMBER_BACKGROUND_COLOR		= new java.awt.Color(228,226,210);
    public static final	Color	DISP_NUMBER_INACT_FOREGROUND_COLOR	= java.awt.Color.black;

    //	擖椡忦審
    public static final	int	DISP_NUMBER_MAX_INTEGER_DIGIT		= 7;

    //	昞帵宍幃
    public static final	String	DISP_NUMBER_OUTPUT_FORMAT		= "#,##0";

    //++********************************
    //	JMsDispReal
    //--********************************
    //	暥帤忣曬
    public static final	Font	DISP_REAL_FONT				= DEFUALT_FONT;

    //	昞帵榞
    public static final	int	DISP_REAL_BORDER_WIDTH			= 1;

    //	怓
    public static final	Color	DISP_REAL_BACKGROUND_COLOR		= new java.awt.Color(228,226,210);
    public static final	Color	DISP_REAL_INACT_FOREGROUND_COLOR	= java.awt.Color.black;

    //	擖椡忦審
    public static final	int	DISP_REAL_MAX_DECIMAL_DIGIT		= 7;
    public static final	int	DISP_REAL_MAX_INTEGER_DIGIT		= 2;

    //	昞帵宍幃
    public static final	String	DISP_REAL_OUTPUT_FORMAT			= "#,##0.00";

    //++********************************
    //	JMsDispDate
    //--********************************
    //	暥帤忣曬
    public static final	Font	DISP_DATE_FONT				= DEFUALT_FONT;

    //	昞帵榞
    public static final	int	DISP_DATE_BORDER_WIDTH			= 1;

    //	怓
    public static final	Color	DISP_DATE_BACKGROUND_COLOR		= new java.awt.Color(228,226,210);
    public static final	Color	DISP_DATE_INACT_FOREGROUND_COLOR	= java.awt.Color.black;

    //++********************************
    //	JMsButton
    //--********************************
    //	暥帤忣曬
    public static final	Font	BUTTON_FONT				= DEFUALT_FONT;

    //++********************************
    //	JMsButtonCombo
    //--********************************
    //	暥帤忣曬
    public static final	Font	BUTTON_COMBO_FONT			= DEFUALT_FONT;
    public static final	String	BUTTON_COMBO_TEXT			= "Run";

    //++********************************
    //	JMsCheckBox
    //--********************************
    //	暥帤忣曬
    public static final	Font	CHECK_BOX_FONT				= DEFUALT_FONT;

    //++********************************
    //	JMsRadioButton
    //--********************************
    //	暥帤忣曬
    public static final	Font	RADIO_BUTTON_FONT			= DEFUALT_FONT;

    //++********************************
    //	JMsDate
    //--********************************
    //	暥帤忣曬
    public static final	Font	DATE_FONT				= DEFUALT_FONT;
    public static final	int		DATE_MAX_BYTE_LENGTH		= -1;

    //++********************************
    //	JMsNumber
    //--********************************
    //	暥帤忣曬
    public static final	Font	NUMBER_FONT				= DEFUALT_FONT;

    //	擖椡忦審
    public static final	int	NUMBER_MAX_INTEGER_DIGIT		= -1;

    //	昞帵宍幃
    public static final	String	NUMBER_OUTPUT_FORMAT			= "#,##0";

    //++********************************
    //	JMsReal
    //--********************************
    //	暥帤忣曬
    public static final	Font	REAL_FONT				= DEFUALT_FONT;

    //	擖椡忦審
        public static final	int 	REAL_MAX_DECIMAL_DIGIT		= 2;
    public static final	int	REAL_MAX_INTEGER_DIGIT		= 7;

    //	昞帵宍幃
    public static final	String	REAL_OUTPUT_FORMAT			= "#,##0.00";

    //++********************************
    //	JMsComboBox
    //--********************************
    //	暥帤忣曬
    public static final	Font	COMBO_BOX_FONT				= DEFUALT_FONT;

    //++********************************
    //	JMsLine
    //--********************************
    //	怓
    public static final	Color	LINE_BACKGROUND_COLOR			= Color.black;

    //++********************************
    //	JMsFrameLine
    //--********************************
    //	昞帵榞
    public static final	int		FRAME_LINE_BORDER_WIDTH			= 1;

}
