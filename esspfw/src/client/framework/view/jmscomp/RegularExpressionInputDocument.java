package client.framework.view.jmscomp;

import javax.swing.text.BadLocationException;
import java.util.regex.Pattern;
import javax.swing.text.AttributeSet;
import java.util.regex.Matcher;
import java.util.Scanner;

/**
 * <p>Title: RegularExpressionInputDocument</p>
 *
 * <p>Description: 用正则表达式来限制输入</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public class RegularExpressionInputDocument extends InputDocument {

    private Pattern pattern;

    public RegularExpressionInputDocument(String expression) {
        super(100, InputDocument.ALL, true);
        setExpression(expression);
    }

    /**
     * 设置正则表达式
     * @param expression String
     */
    public void setExpression(String expression) {
        pattern = Pattern.compile(expression);
    }

    /**
     * 检查字符插入后是否符合表达式，如果不符合就拦截
     * @param iOff int
     * @param sStr String
     * @param att AttributeSet
     * @throws BadLocationException
     */
    public void insertString(int iOff, String sStr, AttributeSet att) throws
            BadLocationException {

        Matcher matcher = pattern.matcher(getNewText(iOff, sStr));
        if (matcher.matches()) {
            super.insertString(iOff, sStr, att);
        }
    }

    /**
     * 获取接受输入后的文本
     * @param iOff int
     * @param sStr String
     * @return String
     */
    private String getNewText(int iOff, String sStr) {
        String oldText = "";
        try {
            oldText = this.getText(0, this.getLength());
        } catch (BadLocationException ex) {
        }
        if (oldText == null || oldText.equals("")) {
            return sStr;
        } else if (oldText.length() == iOff) {
            return oldText + sStr;
        } else if (iOff == 0) {
            return sStr + oldText;
        } else {
            return oldText.substring(0, iOff) + sStr +
                    oldText.substring(iOff);
        }
    }

    public static void main(String[] args) {
        Pattern p = Pattern.compile("*");
        Scanner in = new Scanner(System.in);
        while (true) {
            System.out.println("Please Enter:");
            String test = in.nextLine();
            Matcher matcher = p.matcher(test);
            System.out.println(matcher.matches());
        }
    }

}
