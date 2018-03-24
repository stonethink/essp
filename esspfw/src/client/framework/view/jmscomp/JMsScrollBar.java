package client.framework.view.jmscomp;

import java.awt.*;
import javax.swing.JScrollBar;

/**
 * <p>タイトル: JMsComp </p>
 * <p>説明: Javax.Swing継承のオリジナルコンポーネント群</p>
 * <p>著作権: milestone Copyright (c) 2002</p>
 * <p>会社名: マイルストーン株式会社</p>
 * @author 未入力
 * @version 1.0
 */

public class JMsScrollBar extends JScrollBar {
	BorderLayout borderLayout1 = new BorderLayout();

	public JMsScrollBar() {
		try {
			jbInit();
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	private void jbInit() throws Exception {
		this.setLayout(null);
	}
}