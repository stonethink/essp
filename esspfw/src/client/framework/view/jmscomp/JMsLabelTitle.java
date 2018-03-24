package client.framework.view.jmscomp;

import java.awt.*;
import javax.swing.*;
import javax.swing.JTextField;
import javax.swing.border.*;
import javax.swing.BorderFactory;
import client.framework.view.common.*;

/**
 * <p>タイトル: JMsComp </p>
 * <p>説明: Javax.Swing継承のオリジナルコンポーネント群</p>
 * <p>著作権: milestone Copyright (c) 2002</p>
 * <p>会社名: マイルストーン株式会社</p>
 * @author 未入力
 * @version 1.0
 */

public class JMsLabelTitle extends JTextField {
	BorderLayout borderLayout1 = new BorderLayout();
	TitledBorder titledBorder1;
	TitledBorder titledBorder2;
	Border border1;
	TitledBorder titledBorder3;

	public JMsLabelTitle() {
		try {
			jbInit();
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}

		try {
			initBeanUser();
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}

	}

	/**
	 *<BR>
	 *　タイプ　：　初期化<BR>
	 *　処理名　：　初期値設定処理<BR>
	 *　備　考　：　<BR>
	 *<BR>
	 *　変更履歴<BR>
	 *<BR>
	 *　　Version　　日　付　　　更新者　　　　　コメント<BR>
	 *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
	 *　　00.00　　2002/05/30　宝来　幸弘　　新規作成<BR>
	 *<BR>
	 */
	private void jbInit() throws Exception {
		titledBorder1 = new TitledBorder("");
		titledBorder2 = new TitledBorder("");
		border1 = BorderFactory.createLineBorder(new Color(255, 151, 255),2);
		titledBorder3 = new TitledBorder("");
		this.setBackground(Color.orange);
		this.setBorder(null);
		this.setHorizontalAlignment(SwingConstants.CENTER);
		this.setLayout(borderLayout1);
	}

	/**
	 *<BR>
	 *　タイプ　：　初期化<BR>
	 *　処理名　：　ユーザ初期値設定処理<BR>
	 *　備　考　：　<BR>
	 *<BR>
	 *　変更履歴<BR>
	 *<BR>
	 *　　Version　　日　付　　　更新者　　　　　コメント<BR>
	 *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
	 *　　00.00　　2002/05/30　宝来　幸弘　　新規作成<BR>
	 *<BR>
	 */
	private void initBeanUser() throws Exception {

		//++****************************
		//	状態設定
		//--****************************
		this.setEnabled( false );

		//++****************************
		//	文字情報設定
		//--****************************
		setFont ( DefaultComp.LABEL_TITLE_FONT );
        this.setFont(DefaultComp.LABEL_TITLE_FONT);

		//++****************************
		//	色設定
		//--****************************
		setDisabledTextColor ( DefaultComp.LABEL_TITLE_FOREGROUND_COLOR );
		setBackground ( DefaultComp.LABEL_TITLE_BACKGROUND_COLOR );

	}




}