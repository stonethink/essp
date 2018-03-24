package client.framework.view.jmscomp;

import java.awt.*;
import client.framework.view.common.*;

/**
 * <p>タイトル: JMsComp </p>
 * <p>説明: Javax.Swing継承のオリジナルコンポーネント群</p>
 * <p>著作権: milestone Copyright (c) 2002</p>
 * <p>会社名: マイルストーン株式会社</p>
 * @author 未入力
 * @version 1.0
 */

public class JMsDisp extends JMsText {
	BorderLayout borderLayout1 = new BorderLayout();

	public JMsDisp() {
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
		setEnabled ( false );

		//++****************************
		//	文字情報設定
		//--****************************
		setFont( DefaultComp.DISP_DATA_FONT );

		//++****************************
		//	色設定
		//--****************************
		setBackground ( DefaultComp.DISP_DATA_BACKGROUND_COLOR );
		this.setDisabledTextColor ( DefaultComp.DISP_DATA_INACT_FOREGROUND_COLOR );
   }

   /**
    *<BR>
    *　タイプ　：　<BR>
    *　処理名　：　最大バイトにより、情報の表示を自動的に切られます。<BR>
    *　備　考　：　<BR>
    *<BR>
    *　変更履歴<BR>
    *<BR>
    *　　Version　　日　付　　　更新者　　　　　コメント<BR>
    *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
    *　　00.00　　2004/12/17　Yery 　　新規作成<BR>
    *<BR>
    */
   public void setText(String text) {
     String sNewText="";
     if(text==null) {
       sNewText="";
     }

     if(text != null && text.equals("")==false) {
       int iMaxLength = this.getMaxByteLength();
       if(iMaxLength>0) {
         byte[] bText = text.getBytes();
         if (bText.length > iMaxLength) {
           sNewText = new String(bText, 0, iMaxLength);
         }
         else {
           sNewText = text;
         }
       } else {
         sNewText = text;
       }
     }

     super.setText(sNewText);
   }

}
