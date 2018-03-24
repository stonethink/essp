package client.essp.timesheet.weeklyreport.common;

import java.math.BigDecimal;

import javax.swing.SwingConstants;
import javax.swing.text.Document;

import client.framework.view.jmscomp.RegularExpressionInputDocument;
import client.framework.view.vwcomp.IVWComponent;
import client.framework.view.vwcomp.VWJText;
import validator.Validator;

/**
 * <p>Title: VWJTowNumReal</p>
 *
 * <p>Description: 可以输入两个数字的VWJReal控件
 *                格式为：nnn.n/nn.n</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public class VWJTwoNumReal extends VWJText {
    //正则表达式, 可用“/”隔开的两个浮点数
    private final static String EXPRESSION
            = "^-?\\d+(\\.\\d*)?(/(\\d+(\\.\\d*)?)?)?$";

    private final static int DEFAULT_INTERGER_DIGIT = 4;

    private Validator validator;
    private int maxInputDecimalDigit;
    private int maxInputIntegerDigit = DEFAULT_INTERGER_DIGIT;
    private boolean canNegative = false;
    private boolean canSecondNumNegative = false;
    private boolean canInputSecondNum = true;

    public VWJTwoNumReal() {
        super();
        this.setHorizontalAlignment(SwingConstants.RIGHT);
    }

    public void setText(String prm_sStr) {
        if (prm_sStr == null) {
            super.setText("");
            return;
        }
        String[] numValues = prm_sStr.split("/");
        if (numValues.length == 1) {
            super.setText(fromat(prm_sStr));
        } else if(numValues.length == 2) {
            prm_sStr = fromat(numValues[0]) + "/" + fromat(numValues[1]);
            super.setText(prm_sStr);
        }
    }


    public void setValidator(Validator validator) {
        this.validator = validator;
    }

    public void setMaxInputDecimalDigit(int maxInputDecimalDigit) {
        this.maxInputDecimalDigit = maxInputDecimalDigit;
        resetMyDocumnet();
    }

    public void setMaxInputIntegerDigit(int maxInputIntegerDigit) {
        this.maxInputIntegerDigit = maxInputIntegerDigit;
        resetMyDocumnet();
    }

    public void setCanNegative(boolean canNegative) {
        this.canNegative = canNegative;
        resetMyDocumnet();
    }

    public void setCanInputSecondNum(boolean canInputSecondNum) {
        this.canInputSecondNum = canInputSecondNum;
    }

    public void setDocument(Document doc) {
        resetMyDocumnet();
    }

    private void resetMyDocumnet() {
        super.setDocument(new RegularExpressionInputDocument(getExpression()));
    }

    /**
     * 根据参数设置生成正则表达式
     * @return String
     */
    private String getExpression() {
        String floatExpression = "\\d{0," + getMaxInputIntegerDigit()
                                 + "}(\\.\\d{0," + getMaxInputDecimalDigit() + "})?";
        //开始
        String expression = "^";
        if(canNegative()) {
            expression += "-?";
        }
        expression += floatExpression;
        
        String secNumExp = floatExpression;
        if(this.isCanSecondNumNegative()) {
        	secNumExp = "-?" + secNumExp;
        }
        if(canInputSecondNum()) {
            expression += "(/(" + secNumExp + ")?)?";
        }
        //结束
        expression += "$";
        return expression;
    }


    public String fromatFractionDigits (String prm_sStr ) {

        String[] prms = prm_sStr.split("/");
        String value = fromat(prms[0]);
        if(prms.length > 1) {
            value += fromat(prms[1]);
        }
        return value;
    }

    public String fromat(String prm_sStr ) {
        if (prm_sStr != null && prm_sStr.equals("") == false) {
            String fmtStr = prm_sStr;
            if(this.canNegative() == false) {
                fmtStr = fmtStr.replace("-", "");
            }
            BigDecimal bdValue;
            try {
                bdValue = new BigDecimal(fmtStr);
            } catch (Exception e) {
                return "";
            }
            return bdValue.setScale(getMaxInputDecimalDigit(),
                             BigDecimal.ROUND_HALF_EVEN).toString();
        } else {
            return "";
        }
    }
    public IVWComponent duplicate() {
        VWJTwoNumReal comp = new VWJTwoNumReal();
        comp.setName(this.getName());
        comp.setMaxInputDecimalDigit(this.getMaxInputDecimalDigit());
        comp.setMaxInputIntegerDigit(this.getMaxInputIntegerDigit());
        comp.setCanNegative(this.canNegative());
        comp.setDtoClass(this.getDtoClass());
        comp.setValidator(this.validator);
        comp.setFont(this.getFont());
        comp.setUICValue(this.getUICValue());

        return comp;
    }

    public int getMaxInputDecimalDigit() {
        return maxInputDecimalDigit;
    }

    public int getMaxInputIntegerDigit() {
        return maxInputIntegerDigit;
    }

    public boolean canNegative() {
        return canNegative;
    }

    public boolean canInputSecondNum() {
        return canInputSecondNum;
    }

	public boolean isCanSecondNumNegative() {
		return canSecondNumNegative;
	}

	public void setCanSecondNumNegative(boolean canSecondNumNegative) {
		this.canSecondNumNegative = canSecondNumNegative;
		resetMyDocumnet();
	}
}
