package client.framework.view.jmscomp;

import java.beans.*;

/**
 * <p>タイトル: JMsComp </p>
 * <p>説明: Javax.Swing継承のオリジナルコンポーネント群</p>
 * <p>著作権: milestone Copyright (c) 2002</p>
 * <p>会社名: マイルストーン株式会社</p>
 * @author 未入力
 * @version 1.0
 */

public class DateType extends PropertyEditorSupport {

	public DateType() {
	}
	private static String[] tagStrings = { "YYYYMMDD", "HHMMSS", "HHMM", "YYYY", "YYYYMM", "MM", "DD","MMDD","YYMMDD","YYMM"};
	public String[] getTags() {
		return tagStrings;
	}
	public String getJavaInitializationString() {
		return "\"" + getAsText() + "\"";
	}
	public void setAsText(String text) throws IllegalArgumentException {
		setValue(text);
	}
}
