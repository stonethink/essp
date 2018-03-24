package client.framework.view.common;

import java.awt.*;
import javax.swing.*;
import client.framework.view.jmscomp.*;
import java.awt.event.*;
import client.framework.common.Constant;

/**
 * <p>タイトル: 共通関数群</p>
 * <p>説明: </p>
 * <p>著作権: Copyright (c) 2002</p>
 * <p>会社名: </p>
 * @author 未入力
 * @version 1.0
 */

public class dlgProcessYN extends JDialog {
	JMsButton BTN_OK = new JMsButton();
	JMsButton BTN_Cancel = new JMsButton();
	JMsLabel LBL_Message = new JMsLabel();

	private	String	_sMessage;
	private	int	_iResult;

	public dlgProcessYN(Frame frame, String title, boolean modal , String prm_sMessage) {
		super(frame, title, modal);
		_sMessage = prm_sMessage;

		try {
			jbInit();
			InitUser();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}

	public dlgProcessYN() {
		this(null, "", false,"");
	}

	void jbInit() throws Exception {
        BTN_OK.setFont(Constant.DEFAULT_BUTTON_FONT);
		BTN_OK.setText("OK");
		BTN_OK.setActionCommand("OK");
    BTN_OK.setBounds(new Rectangle(31, 57, 104, 32));
		BTN_OK.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BTN_OK_actionPerformed(e);
			}
		});
		this.getContentPane().setLayout(null);
        BTN_Cancel.setFont(Constant.DEFAULT_BUTTON_FONT);
		BTN_Cancel.setActionCommand("Cancel");
    BTN_Cancel.setBounds(new Rectangle(151, 58, 104, 32));
		BTN_Cancel.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BTN_Cancel_actionPerformed(e);
			}
		});
		BTN_Cancel.setText("Cancel");
		BTN_Cancel.setToolTipText("");
		LBL_Message.setText("1234567890123456789012345678901234567890");
		LBL_Message.setToolTipText("");
		LBL_Message.setHorizontalAlignment(SwingConstants.CENTER);
		LBL_Message.setBounds(new Rectangle(4, 11, 285, 39));
		this.getContentPane().setBackground(new Color(240, 230, 245));
		this.getContentPane().add(LBL_Message, null);
		this.getContentPane().add(BTN_OK, null);
		this.getContentPane().add(BTN_Cancel, null);
	}

	void InitUser() throws Exception {
		LBL_Message.setText( _sMessage );
		BTN_OK.requestFocus();
		_iResult	= -1;

		setBounds (344,250,304,167);

		//画面の中央に配置
		java.awt.Dimension d1 = this.getToolkit().getScreenSize();
		java.awt.Dimension d2 = this.getSize();
		this.setLocation(d1.width / 2 - d2.width / 2, d1.height / 2 - d2.height / 2);
		BTN_OK.setDefaultCapable( true );
		BTN_OK.setRequestFocusEnabled( true );
	}

	void BTN_OK_actionPerformed(ActionEvent e) {
		_iResult	= 1;
	}

	void BTN_Cancel_actionPerformed(ActionEvent e) {
		_iResult	= -1;
		dispose();
	}

	public int showDialog(
	) {
		this.setVisible( true );
		return _iResult;
	}

	class wAdapter extends WindowAdapter {
		public void windowDeactivated( WindowEvent e ) {
			if(dlgProcessYN.this.isShowing() == false){
				dlgProcessYN.this.setVisible( true );
			}
		}
    }
}
