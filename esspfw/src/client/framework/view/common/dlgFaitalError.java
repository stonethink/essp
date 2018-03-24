package client.framework.view.common;

import java.awt.*;
import javax.swing.*;
import client.framework.view.jmscomp.*;
import java.awt.event.*;
import javax.swing.border.*;

/**
 * <p>タイトル: 共通関数群</p>
 * <p>説明: </p>
 * <p>著作権: Copyright (c) 2002</p>
 * <p>会社名: </p>
 * @author 未入力
 * @version 1.0
 */

public class dlgFaitalError extends JDialog {

	private	String	_sTitle;
	private	int		_iResult;

	private String	_sErrInfoFormID;
	private String	_sErrInfoModuleID;
	private String	_sErrInfoIF;
	private String	_sErrInfoOperation;
	private String	_sRetInfoMessage;
	private Exception _clsExcept;

	JMsButton BTN_OK = new JMsButton();
	JMsGroupTitle jMsGroupTitle1 = new JMsGroupTitle();
	JTextArea jMsLabelTitle4 = new JTextArea();
	JMsLabelTitle jMsLabelTitle5 = new JMsLabelTitle();
	JMsLabelTitle jMsLabelTitle6 = new JMsLabelTitle();
	JMsLabelTitle jMsLabelTitle7 = new JMsLabelTitle();
	JMsGroupTitle jMsGroupTitle2 = new JMsGroupTitle();
	JMsDisp DSP_ErrInfoFormID = new JMsDisp();
	JMsDisp DSP_ErrInfoModuleID = new JMsDisp();
	JMsDisp DSP_ErrInfoIF = new JMsDisp();
	JMsDisp DSP_ErrInfoOperation = new JMsDisp();
	JMsLabelTitle jMsLabelTitle8 = new JMsLabelTitle();
	JMsLabelTitle jMsLabelTitle9 = new JMsLabelTitle();
	TitledBorder titledBorder1;
	JTextArea LBL_RetInfoMessage = new JTextArea();
	TitledBorder titledBorder2;
	TitledBorder titledBorder3;
	JTextArea LBL_InfoSystem = new JTextArea();
	JMsLabelTitle jMsLabelTitle10 = new JMsLabelTitle();
	JMsLabelTitle jMsLabelTitle11 = new JMsLabelTitle();

	public dlgFaitalError (
		Frame		frame,
		String		title,
		boolean		modal,
		String		prm_sErrInfoFormID,
		String		prm_sErrInfoModuleID,
		String		prm_sErrInfoIF,
		String		prm_sErrInfoOperation,
		String		prm_sRetInfoMessage,
		Exception	prm_clsExcept
	) {
		super(frame, title, modal);
		_sErrInfoFormID		= prm_sErrInfoFormID;
		_sErrInfoModuleID	= prm_sErrInfoModuleID;
		_sErrInfoIF		= prm_sErrInfoIF;
		_sErrInfoOperation	= prm_sErrInfoOperation;
		_sRetInfoMessage	= prm_sRetInfoMessage;
		_clsExcept		= prm_clsExcept;

		try {
			jbInit();
			initUser();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}

	void jbInit() throws Exception {
		titledBorder1 = new TitledBorder("");
		titledBorder2 = new TitledBorder("");
		titledBorder3 = new TitledBorder("");
		BTN_OK.setToolTipText("");
		BTN_OK.setText("Close");
		BTN_OK.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BTN_OK_actionPerformed(e);
			}
		});
		BTN_OK.setBounds(new Rectangle(327, 485, 104, 32));
		this.getContentPane().setLayout(null);
		this.getContentPane().setBackground(new Color(240, 230, 245));
		jMsGroupTitle1.setText("エラー発生箇所");
		jMsGroupTitle1.setBounds(new Rectangle(36, 95, 123, 20));
		jMsLabelTitle4.setBounds(new Rectangle(60, 25, 340, 40));
		jMsLabelTitle4.setToolTipText("");
		jMsLabelTitle4.setBackground(new Color(236, 233, 216));
		jMsLabelTitle4.setDisabledTextColor(Color.black);
		jMsLabelTitle4.setBorder(BorderFactory.createLineBorder(Color.black));
		jMsLabelTitle4.setText("                    処理継続不可能なエラーが発生しました。\n                    システム管理者へ連絡してください。");
/* ADD S   20040803 Tsuka **/
		jMsLabelTitle4.setEditable(false);
		jMsLabelTitle4.setFocusable(false);
/* ADD E   20040803 Tsuka **/
		jMsLabelTitle5.setBounds(new Rectangle(45, 154, 108, 20));
		jMsLabelTitle5.setText("モジュール");
		jMsLabelTitle5.setToolTipText("        this.setSize(new Dimension(743, 587));");
		jMsLabelTitle6.setBounds(new Rectangle(45, 185, 108, 20));
		jMsLabelTitle6.setToolTipText("        this.setSize(new Dimension(743, 587));");
		jMsLabelTitle6.setText("ＩＦ");
		jMsLabelTitle7.setBounds(new Rectangle(45, 216, 108, 20));
		jMsLabelTitle7.setText("オペレーション");
		jMsLabelTitle7.setToolTipText("        this.setSize(new Dimension(743, 587));");
		jMsGroupTitle2.setBounds(new Rectangle(34, 259, 123, 20));
		jMsGroupTitle2.setText("エラー情報");
		DSP_ErrInfoFormID.setRequestFocusEnabled(false);
		DSP_ErrInfoFormID.setBounds(new Rectangle(166, 133, 157, 20));
		DSP_ErrInfoFormID.setBounds(new Rectangle(160, 126, 157, 20));
		DSP_ErrInfoModuleID.setBounds(new Rectangle(169, 125, 157, 20));
		DSP_ErrInfoModuleID.setBounds(new Rectangle(160, 155, 157, 20));
		DSP_ErrInfoModuleID.setRequestFocusEnabled(false);
		DSP_ErrInfoIF.setRequestFocusEnabled(false);
		DSP_ErrInfoIF.setBounds(new Rectangle(166, 164, 157, 20));
		DSP_ErrInfoIF.setBounds(new Rectangle(160, 184, 157, 20));
		DSP_ErrInfoOperation.setRequestFocusEnabled(false);
		DSP_ErrInfoOperation.setBounds(new Rectangle(166, 164, 157, 20));
		DSP_ErrInfoOperation.setBounds(new Rectangle(160, 214, 157, 20));
		jMsLabelTitle8.setBounds(new Rectangle(45, 286, 108, 20));
		jMsLabelTitle8.setToolTipText("");
		jMsLabelTitle8.setText("メッセージ");
		jMsLabelTitle9.setBounds(new Rectangle(45, 125, 108, 20));
		jMsLabelTitle9.setToolTipText("        this.setSize(new Dimension(743, 587));");
		jMsLabelTitle9.setText("画面ＩＤ");
		LBL_RetInfoMessage.setBorder(BorderFactory.createLoweredBevelBorder());
		LBL_RetInfoMessage.setToolTipText("");
		LBL_RetInfoMessage.setBackground(new Color(228, 226, 210));
		LBL_RetInfoMessage.setDisabledTextColor(Color.black);
		LBL_RetInfoMessage.setBounds(new Rectangle(60, 314, 340, 55));
		LBL_InfoSystem.setBorder(BorderFactory.createLoweredBevelBorder());
		LBL_InfoSystem.setBackground(new Color(228, 226, 210));
		LBL_InfoSystem.setDisabledTextColor(Color.black);
		LBL_InfoSystem.setBounds(new Rectangle(60, 378, 340, 81));
		jMsLabelTitle10.setBounds(new Rectangle(17, 103, 412, 144));
		jMsLabelTitle10.setBorder(BorderFactory.createLineBorder(Color.black));
		jMsLabelTitle10.setDisabledTextColor(Color.black);
		jMsLabelTitle10.setBackground(new Color(236, 233, 216));
		jMsLabelTitle10.setToolTipText("");
		jMsLabelTitle11.setBounds(new Rectangle(16, 266, 412, 209));
		jMsLabelTitle11.setToolTipText("");
		jMsLabelTitle11.setBackground(new Color(236, 233, 216));
		jMsLabelTitle11.setDisabledTextColor(Color.black);
		jMsLabelTitle11.setBorder(BorderFactory.createLineBorder(Color.black));
		this.getContentPane().add(jMsGroupTitle1, null);
		this.getContentPane().add(DSP_ErrInfoFormID, null);
		this.getContentPane().add(DSP_ErrInfoModuleID, null);
		this.getContentPane().add(jMsLabelTitle5, null);
		this.getContentPane().add(DSP_ErrInfoIF, null);
		this.getContentPane().add(jMsLabelTitle6, null);
		this.getContentPane().add(jMsLabelTitle7, null);
		this.getContentPane().add(DSP_ErrInfoOperation, null);
		this.getContentPane().add(jMsLabelTitle8, null);
		this.getContentPane().add(jMsGroupTitle2, null);
		this.getContentPane().add(jMsLabelTitle4, null);
		this.getContentPane().add(jMsLabelTitle9, null);
		this.getContentPane().add(LBL_RetInfoMessage, null);
		this.getContentPane().add(LBL_InfoSystem, null);
		this.getContentPane().add(BTN_OK, null);
/* DEL S   20040803 Tsuka ***
		this.getContentPane().add(jMsLabelTitle10, null);
		this.getContentPane().add(jMsLabelTitle11, null);
 * DEL E   20040803 Tsuka **/
	}

	void BTN_OK_actionPerformed(ActionEvent e) {
		_iResult = 1;
		dispose();
	}

	/*
	 * ユーザ定義初期化処理.
	 */
	protected void initUser () {
		setBounds (500,250,455,565);
		DSP_ErrInfoFormID.setText( _sErrInfoFormID );
		DSP_ErrInfoModuleID.setText( _sErrInfoModuleID );
		DSP_ErrInfoIF.setText( _sErrInfoIF );
		DSP_ErrInfoOperation.setText( _sErrInfoOperation );

		this.LBL_InfoSystem.setText( ComDATA.newLine( _sRetInfoMessage, 45 ) );
		this.LBL_RetInfoMessage.setText( ComDATA.newLine( _clsExcept, 45 ) );
/* ADD S   20040803 Tsuka **/
		this.LBL_InfoSystem.setEditable(false);
		this.LBL_InfoSystem.setFocusable(false);
		this.LBL_RetInfoMessage.setEditable(false);
		this.LBL_RetInfoMessage.setFocusable(false);
/* ADD E   20040803 Tsuka **/

		_iResult	= -1;

		//画面の中央に配置
		java.awt.Dimension d1 = this.getToolkit().getScreenSize();
		java.awt.Dimension d2 = this.getSize();
		this.setLocation(d1.width / 2 - d2.width / 2, d1.height / 2 - d2.height / 2);
		BTN_OK.setDefaultCapable( true );
		BTN_OK.setRequestFocusEnabled( true );
		BTN_OK.requestFocus();
	}

	public void showDialog() {
/* DEL S   20040803 Tsuka ***
		this.addWindowListener( new wAdapter() );
 * DEL E   20040803 Tsuka **/
		this.setVisible( true );
	}
	class wAdapter extends WindowAdapter {
		public void windowDeactivated( WindowEvent e ) {
			if(dlgFaitalError.this.isShowing() == false){
			    dlgFaitalError.this.setVisible( true );
			}
		}
	}
}
