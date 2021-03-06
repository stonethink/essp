package client.framework.view.jmscomp;

import java.awt.*;
import javax.swing.JComboBox;
import javax.swing.BorderFactory;
import java.awt.event.*;
import javax.swing.border.*;
import javax.swing.*;
import client.framework.view.common.*;

/**
 * <p>^Cg: JMsComp </p>
 * <p>à¾: Javax.Swingp³ÌIWiR|[lgQ</p>
 * <p>ì : milestone Copyright (c) 2002</p>
 * <p>ïÐ¼: }CXg[®ïÐ</p>
 * @author ¢üÍ
 * @version 1.0
 */

public class JMsComboBox extends JComboBox {
	BorderLayout borderLayout1 = new BorderLayout();


	private String		_sField_Error	= null;
	/**		setEnabledÅÌÅIvl	*/
	private boolean		_bEnabled_Save;
	/**		OñüÍl	*/
	private String		_sText_Save;
	/**		L[tO	*/
	private boolean		_bKeyFlag;
	/**		veNgENAEtO	*/
	private boolean		_bProtectClearFlag;
	private boolean		_bSelectByKey;
	private boolean		_bNoSelect;
	private boolean		_bFocus;
	Border border1;


	public JMsComboBox() {
		try {
			jbInit();
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}

		try {
			InitUser();
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}

	}

	private void jbInit() throws Exception {
//		border1 = BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(),border1);
		this.setBorder(BorderFactory.createLoweredBevelBorder());
		this.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				this_mouseClicked(e);
			}
		});

		this.registerKeyboardAction (new keyAction(), KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

		this.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusGained(FocusEvent e) {
				this_focusGained(e);
			}
			public void focusLost(FocusEvent e) {
				this_focusLost(e);
			}
		});
	}

	private void InitUser() throws Exception {
		//++****************************
		//	úlÝè
		//--****************************
		_sField_Error	    = "";
		_bSelectByKey		= false;
		_bNoSelect			= false;
//		_bFocus				= false;
//		setKey( false );
//		setProtectClear( false );

		//++****************************
		//	¶îñÝè
		//--****************************
		setFont ( DefaultComp.COMBO_BOX_FONT );
//        this.setModel( DefaultComp.DISABLED_FONT_COLOR );

		//++****************************
		//	wiFÝè
		//--****************************
		_setBackgroundColor();

		//++****************************
		//	»Ì¼Ýè
		//--****************************
		setEnabled( true );
	}

	/**
	 *<BR>
	 *@^Cv@F@[U[Ö<BR>
	 *@¼@F@wiFÝè<BR>
	 *@õ@l@F@<BR>
	 *<BR>
	 *@ÏXð<BR>
	 *<BR>
	 *@@Version@@ú@t@@@XVÒ@@@@@Rg<BR>
	 *@|||||||||||||||||||||||||||||||||||<BR>
	 *@@00.00@@2002/05/30@ó@KO@@VKì¬<BR>
	 *<BR>
	 */
	public void _setBackgroundColor(
	) {
		if ( isEnabled() == true ) {
			setBackground( DefaultComp.BACKGROUND_COLOR_ENABLED );
		} else {
			setBackground( DefaultComp.BACKGROUND_COLOR_DISABLED );
			this.setForeground(DefaultComp.FOREGROUND_COLOR_NORMAL);
		}
	}

	/**
	 *<BR>
	 *@^Cv@F@Cxg<BR>
	 *@¼@F@tH[JXÌ<BR>
	 *@õ@l@F@<BR>
	 *<BR>
	 *@ÏXð<BR>
	 *<BR>
	 *@@Version@@ú@t@@@XVÒ@@@@@Rg<BR>
	 *@|||||||||||||||||||||||||||||||||||<BR>
	 *@@00.00@@2002/06/24    ó@KO@@VKì¬<BR>
	 *<BR>
	 */
	void this_focusGained(FocusEvent e) {

	   String  sStr;

		//++****************************
		//	üÍsÂAtB[hÚ®
		//--****************************
		if ( isEnabled() == false ) {
			return;
		}

		//++****************************
		//	óÔÝè
		//--****************************
		_bFocus		= true;
		_bSelectByKey		= false;

		//++****************************
		//	wiFÝè
		//--****************************
//		setBackground( DefaultComp.BACKGROUND_COLOR_INPUT_ACTIVE );

	}


	/**
	 *<BR>
	 *@^Cv@F@Cxg<BR>
	 *@¼@F@XÆtH[JXÌ<BR>
	 *@õ@l@F@<BR>
	 *<BR>
	 *@ÏXð<BR>
	 *<BR>
	 *@@Version@@ú@t@@@XVÒ@@@@@Rg<BR>
	 *@|||||||||||||||||||||||||||||||||||<BR>
	 *@@00.00@@2002/05/30@ó@KO@@VKì¬<BR>
	 *<BR>
	 */
	public void this_focusLost(FocusEvent e) {
		String  sStr;

		//++****************************
		//	wiFÝè
		//--****************************
//		_setBackgroundColor(); //modify by stone

		//++****************************
		//	óÔÝè
		//--****************************
		_bFocus		= false;

	}
	/**
	 *<BR>
	 *@^Cv@F@vpeB[ Field_ERROR ]: get<BR>
	 *@¼@F@vpeB[ Field_ERROR ]Ìæ¾\bh<BR>
	 *@õ@l@F@<BR>
	 *<BR>
	 *@ÏXð<BR>
	 *<BR>
	 *@@Version@@ú@t@@@XVÒ@@@@@Rg<BR>
	 *@|||||||||||||||||||||||||||||||||||<BR>
	 *@@00.00@@2000/09/05@Tä@ëq@@VKì¬<BR>
	 *<BR>
	 */
	public String getField_ERROR () {

		//++******************************
		//	ßèlÝè
		//--******************************
		return _sField_Error;

	}

	/**
	 *<BR>
	 *@^Cv@F@vpeB[ Field_ERROR ]: set<BR>
	 *@¼@F@vpeB[ Field_ERROR ]ÌÝè\bh<BR>
	 *@õ@l@F@<BR>
	 *<BR>
	 *@ÏXð<BR>
	 *<BR>
	 *@@Version@@ú@t@@@XVÒ@@@@@Rg<BR>
	 *@|||||||||||||||||||||||||||||||||||<BR>
	 *@@00.00@@2000/09/05@Tä@ëq@@VKì¬<BR>
	 *<BR>
	 */
	public void setField_ERROR (
		String		prm_sValue
	) {
		//++******************************
		//	üÍlðÛ¶
		//--******************************
		_sField_Error	= prm_sValue;

		//++************************
		//	¶FÝè
		//--************************
		if ( _sField_Error.equals( DefaultComp.FIELD_ERROR ) == true ) {
			setForeground( DefaultComp.FOREGROUND_COLOR_ERROR );
		} else {
			setForeground( DefaultComp.FOREGROUND_COLOR_NORMAL );
		}

		//++************************
		//	üÍÂÛÝè
		//--************************
		if ( _sField_Error.equals( DefaultComp.FIELD_PROTECT ) == true ) {
			//++********************************
			//	veNgÌNA
			//--********************************
			if ( _bProtectClearFlag == true ) {
				this.setSelectedIndex( -1 );
//				setText( "" );
			}

			//++********************************
			//	üÍsÂÉÝè
			//--********************************
			super.setEnabled( false );
			_setBackgroundColor();
		} else {
			//++********************************
			//	ÅIsetEnabledÝèvlÉÝè
			//--********************************
			super.setEnabled( _bEnabled_Save );
			_setBackgroundColor();
		}

	}


	public boolean checkSelectByKey(
	) {
		return _bSelectByKey;
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
	 *@@00.00@@2000/09/05@Tä@ëq@@VKì¬<BR>
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
		if ( _sField_Error.equals( DefaultComp.FIELD_PROTECT ) == true ) {
			return;
		}

		//++****************************
		//	üÍsÂÝè
		//--****************************
		super.setEnabled( prm_bValue );
		_setBackgroundColor();

	}

	/**
	 *<BR>
	 *@^Cv@F@Cxg<BR>
	 *@¼@F@L[ºÌ<BR>
	 *@õ@l@F@EnterL[ºÅtB[hÚ®<BR>
	 *<BR>
	 *@ÏXð<BR>
	 *<BR>
	 *@@Version@@ú@t@@@XVÒ@@@@@Rg<BR>
	 *@|||||||||||||||||||||||||||||||||||<BR>
	 *@@00.00@@2002/06/24    ó@KO@@VKì¬<BR>
	 *<BR>
	 * @param   Èµ
	 * @return  Èµ
	 */
	protected void this_keyPressed(KeyEvent e) {
		switch ( e.getKeyCode() ) {
			case KeyEvent.VK_ENTER:
				//	tB[hÖÚ®
				transferFocus();
				break;

			case KeyEvent.VK_UP:
			case KeyEvent.VK_DOWN:
				_bSelectByKey		= true;
				break;
/*
			case KeyEvent.VK_DELETE:
			case KeyEvent.VK_BACK_SPACE:
				//++******************************
				//	¢IðÂ\ÈêA¢IðÉÝè
				//--******************************
				if ( _bNoSelect == true ) {
					select( -1 );
				}
			break;

*/
		}
	}

	void this_mouseClicked(MouseEvent e) {
		_bSelectByKey		= false;
	}



	/**
	 *<BR>
	 *@^Cv@F@vpeB[ Field_IF_xxx_E ]: get<BR>
	 *@¼@F@vpeB[ Field_IF_xxx_E ]Ìæ¾\bh<BR>
	 *@õ@l@F@<BR>
	 *<BR>
	 *@ÏXð<BR>
	 *<BR>
	 *@@Version@@ú@t@@@XVÒ@@@@@Rg<BR>
	 *@|||||||||||||||||||||||||||||||||||<BR>
	 *@@00.00@@2000/09/05@Tä@ëq@@VKì¬<BR>
	 *<BR>
	 */
	public String getField_Error () {

		//++******************************
		//	ßèlÝè
		//--******************************
		return _sField_Error;

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
	 *@@00.00@@2000/09/05@Tä@ëq@@VKì¬<BR>
	 *<BR>
	 */
	public void setField_Error (
		String		prm_sValue
	) {
		//++******************************
		//	üÍlðÛ¶
		//--******************************
		_sField_Error	= prm_sValue;

		//++************************
		//	¶FÝè
		//--************************
		if ( _sField_Error.equals( DefaultComp.FIELD_ERROR ) == true ) {
			setForeground( DefaultComp.FOREGROUND_COLOR_ERROR );
		} else {
			setForeground( DefaultComp.FOREGROUND_COLOR_NORMAL );
		}

		//++************************
		//	üÍÂÛÝè
		//--************************
		if ( _sField_Error.equals( DefaultComp.FIELD_PROTECT ) == true ) {
			//++********************************
			//	veNgÌNA
			//--********************************
			if ( _bProtectClearFlag == true ) {
//				select( -1 );
//				setText( "" );
			}

			//++********************************
			//	üÍsÂÉÝè
			//--********************************
			super.setEnabled( false );
			_setBackgroundColor();
		} else {
			//++********************************
			//	ÅIsetEnabledÝèvlÉÝè
			//--********************************
			super.setEnabled( _bEnabled_Save );
			_setBackgroundColor();
		}

	}
}

class keyAction	extends	AbstractAction
{
	public	keyAction (){
	}

	public	void	actionPerformed (ActionEvent	e)
	{
		JComponent combo = (JComponent) e.getSource();
		combo.transferFocus();
	}
}
