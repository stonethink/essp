package client.framework.view.jmscomp;

import java.awt.*;
import javax.swing.JLabel;
import client.framework.view.common.*;

/**
 * <p>タイトル: JMsComp </p>
 * <p>説明: Javax.Swing継承のオリジナルコンポーネント群</p>
 * <p>著作権: milestone Copyright (c) 2002</p>
 * <p>会社名: マイルストーン株式会社</p>
 * @author 未入力
 * @version 1.0
 */

public class JMsLabel extends JLabel {
	BorderLayout borderLayout1 = new BorderLayout();

	public JMsLabel() {
		try {
			jbInit();
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	private void jbInit() throws Exception {
		this.setLayout(borderLayout1);
		this.setFont( DefaultComp.DEFUALT_BOLD_FONT );
		this.setForeground( Color.black );
		//this.setDisplayedMnemonic('0');
	}
}
