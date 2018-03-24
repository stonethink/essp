package client.essp.timesheet.report.humanreport;

import java.math.BigDecimal;

import javax.swing.SwingConstants;
import javax.swing.text.Document;

import validator.Validator;
import client.essp.timesheet.weeklyreport.common.VWJTwoNumReal;
import client.framework.view.jmscomp.RegularExpressionInputDocument;
import client.framework.view.vwcomp.IVWComponent;
import client.framework.view.vwcomp.VWJText;

public class VWJ2RealNumForReport extends VWJText {
	//正则表达式, 可用“/”隔开的两个浮点数
    private final static String EXPRESSION
            = "^-?\\d+(\\.\\d*)?(/(\\d+(\\.\\d*)?)?)?$";

    private final static int DEFAULT_INTERGER_DIGIT = 4;

    private Validator validator;
    private int maxInputDecimalDigit;
    private int maxInputIntegerDigit = DEFAULT_INTERGER_DIGIT;
    private boolean canNegative = false;
    private boolean canNegative2 = false;
    private boolean canInputSecondNum = true;

    public VWJ2RealNumForReport() {
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
            prm_sStr = fromat(numValues[0]) + "/" + fromat2(numValues[1]);
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
    public void setCanNegative2(boolean canNegative2) {
        this.canNegative2 = canNegative2;
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
        if(canInputSecondNum()) {
        	if(canNegative2()) {
        		expression += "(/(-?" + floatExpression + ")?)?";
        	} else {
        		expression += "(/(" + floatExpression + ")?)?";
        	}
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
    public String fromat2(String prm_sStr ) {
        if (prm_sStr != null && prm_sStr.equals("") == false) {
            String fmtStr = prm_sStr;
            if(this.canNegative2() == false) {
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
    public boolean canNegative2() {
        return canNegative2;
    }

    public boolean canInputSecondNum() {
        return canInputSecondNum;
    }
}
