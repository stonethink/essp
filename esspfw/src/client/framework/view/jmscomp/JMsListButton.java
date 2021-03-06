package client.framework.view.jmscomp;

import java.awt.*;
import javax.swing.JButton;
import java.awt.event.*;
import javax.swing.BorderFactory;
import javax.swing.border.*;
import client.framework.view.common.*;

/**
 * <p>^Cg: JMsComp </p>
 * <p>à¾: Javax.Swingp³ÌIWiR|[lgQ</p>
 * <p>ì : milestone Copyright (c) 2002</p>
 * <p>ïÐ¼: }CXg[®ïÐ</p>
 * @author ¢üÍ
 * @version 1.0
 */


public class JMsListButton extends JButton {
	BorderLayout borderLayout1 = new BorderLayout();
	TitledBorder titledBorder1;

	/**		CORBAÊMf[^ IF_xxx_E tB[hl	*/
	private String		_sField_IF_xxx_E	= null;

	/**		setEnabledÅÌÅIvl	*/
	private boolean		_bEnabled_Save = true;

	public JMsListButton() {
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
	 *@^Cv@F@ú»<BR>
	 *@¼@F@úlÝè<BR>
	 *@õ@l@F@<BR>
	 *<BR>
	 *@ÏXð<BR>
	 *<BR>
	 *@@Version@@ú@t@@@XVÒ@@@@@Rg<BR>
	 *@|||||||||||||||||||||||||||||||||||<BR>
	 *@@00.00@@2002/05/30@ó@KO@@VKì¬<BR>
	 *<BR>
	 */
	private void jbInit() throws Exception {
		titledBorder1 = new TitledBorder("");
		this.setBorder(BorderFactory.createRaisedBevelBorder());
		this.setText("~");
		this.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyPressed(KeyEvent e) {
					this_keyPressed(e);
			}
		});
		this.setLayout(borderLayout1);

		//++****************************
		//	úlÝè
		//--****************************
		_sField_IF_xxx_E	= "";

	}

	/**
	 *<BR>
	 *@^Cv@F@vpeB[ Field_IF_xxx_E ]: set<BR>
	 *@¼@F@vpeB[ Field_IF_xxx_E ]ÌÝè\bh<BR>
	 *@õ@l@F@<BR>
	 *<BR>
	 *@ÏXð<BR>
	 *<BR>
	 *@@Version@@ú@t@@@XVÒ@@@@@Rg<BR>
	 *@|||||||||||||||||||||||||||||||||||<BR>
	 *@@00.00@@2000/09/04@º@iê@@VKì¬<BR>
	 *<BR>
	 */
	public void setField_Error (
		String		prm_sValue
	) {
		//++******************************
		//	üÍlðÛ¶
		//--******************************
		_sField_IF_xxx_E	= prm_sValue;

		//++************************
		//	üÍÂÛÝè
		//--************************
		if ( _sField_IF_xxx_E.equals( DefaultComp.FIELD_PROTECT ) == true ) {
			//++********************************
			//	üÍsÂÉÝè
			//--********************************
			super.setEnabled( false );
		} else {
			//++********************************
			//	ÅIsetEnabledÝèvlÉÝè
			//--********************************
			super.setEnabled( _bEnabled_Save );
		}

	}

	/**
	 *<BR>
	 *@^Cv@F@vpeB[ Enabled ]: set<BR>
	 *@¼@F@vpeB[ Enabled ]ÌÝè\bh<BR>
	 *@õ@l@F@setEnabledÌI[o[ChÖ<BR>
	 *<BR>
	 *@ÏXð<BR>
	 *<BR>
	 *@@Version@@ú@t@@@XVÒ@@@@@Rg<BR>
	 *@|||||||||||||||||||||||||||||||||||<BR>
	 *@@00.00@@2000/09/04@º@iê@@VKì¬<BR>
	 *<BR>
	 */
	public void setEnabled(
		boolean		prm_bValue
	) {

		//++****************************
		//	vlÛ¶
		//--****************************
		_bEnabled_Save	= prm_bValue;

		//++**************************************
		//	üÍsÂÌêALZ
		//--**************************************
		if ( _sField_IF_xxx_E.equals( DefaultComp.FIELD_PROTECT ) == true ) {
			return;
		}

		//++****************************
		//	üÍsÂÝè
		//--****************************
		super.setEnabled( prm_bValue );

	}


	/**
	 *<BR>
	 *@^Cv@F@ú»<BR>
	 *@¼@F@[UúlÝè<BR>
	 *@õ@l@F@<BR>
	 *<BR>
	 *@ÏXð<BR>
	 *<BR>
	 *@@Version@@ú@t@@@XVÒ@@@@@Rg<BR>
	 *@|||||||||||||||||||||||||||||||||||<BR>
	 *@@00.00@@2002/05/30@ó@KO@@VKì¬<BR>
	 *<BR>
	 */
	private void initBeanUser() throws Exception {

		//++****************************
		//	¶îñÝè
		//--****************************
//        this.setRequestFocusEnabled(false);
//        this.setFocusPainted( false );
		this.setDefaultCapable( false );

		setFont( DefaultComp.BUTTON_FONT );

	}

	void this_keyPressed(KeyEvent e) {
		if( e.getKeyCode() == KeyEvent.VK_ENTER ){
			this.doClick();
		}
	}

}
