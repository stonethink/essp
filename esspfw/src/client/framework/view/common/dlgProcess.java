package client.framework.view.common;

import java.awt.*;
import javax.swing.*;
import client.framework.view.jmscomp.*;
import java.awt.event.*;

/**
 * <p>タイトル: 共通関数群</p>
 * <p>説明: </p>
 * <p>著作権: Copyright (c) 2002</p>
 * <p>会社名: </p>
 * @author 未入力
 * @version 1.0
 */

public class dlgProcess extends JDialog {

	private	String	_sTitle;
	private	String	_sMessage;
	private	int		_iResult;
	private Frame   frame;

	JMsLabel LBL_Message = new JMsLabel();
	JButton jButton1 = new JButton();
	JTextField jTextField1 = new JTextField();

	public dlgProcess(Frame frame,  boolean modal, String title, String prm_sMessage) {
		super(frame, title, modal);
		this.frame = frame;
		_sMessage  = prm_sMessage;

		try {
			jbInit();
			initUser();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}

	public dlgProcess() {
		this(null, false,"","");
	}

	void jbInit() throws Exception {
		this.getContentPane().setLayout(null);
		LBL_Message.setBounds(new Rectangle(1, 11, 294, 51));
		jButton1.setBounds(new Rectangle(85, 96, 97, 29));
		jButton1.setText("jButton1");
		jTextField1.setText("jTextField1");
		jTextField1.setBounds(new Rectangle(200, 185, 120, 43));
		this.getContentPane().add(LBL_Message, "aaaaaaaaaaaaa");
		this.getContentPane().add(jButton1, null);
		this.getContentPane().add(jTextField1, null);
		this.setBackground(new Color(255, 255, 130));
	}

	void BTN_OK_actionPerformed(ActionEvent e) {
		dispose();
	}

	/*
	 * ユーザ定義初期化処理.
	 */
	protected void initUser () {
		setBounds (344,250,300,167);

		//画面の中央に配置
		java.awt.Dimension d1 = this.getToolkit().getScreenSize();
		java.awt.Dimension d2 = this.getSize();
		this.setLocation(d1.width / 2 - d2.width / 2, d1.height / 2 - d2.height / 2);
	}

	public void showDialog() {
		this.setVisible( true );
	}
}
