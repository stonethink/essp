package client.framework.view.jmscomp;

import java.awt.*;
import javax.swing.JRadioButton;
import javax.swing.BorderFactory;
import client.framework.view.common.*;

/**
 * <p>^Cg: JMsComp </p>
 * <p>à¾: Javax.Swingp³ÌIWiR|[lgQ</p>
 * <p>ì : milestone Copyright (c) 2002</p>
 * <p>ïÐ¼: }CXg[®ïÐ</p>
 * @author ¢üÍ
 * @version 1.0
 */

public class JMsRadioButton extends JRadioButton {
	BorderLayout borderLayout1 = new BorderLayout();
    private boolean keyType;
    private boolean  pbSelect_Save = false;

	public JMsRadioButton() {
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
		//this.setBackground( DefaultComp.BACKGROUND_COLOR_PANEL );
		this.setBorder(BorderFactory.createLoweredBevelBorder());
		this.setLayout(borderLayout1);
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
		setFont( DefaultComp.DEFUALT_BOLD_FONT );
	}
    public void setKeyType(boolean keyType) {
        this.keyType = keyType;
    }
    public boolean isKeyType() {
        return keyType;
    }

	/**
	 *<BR>
	 *@^Cv@F@[U[Ö<BR>
	 *@¼@F@L[ÚÏX`FbN\bh<BR>
	 *@õ@l@F@<BR>
	 *<BR>
	 *@ÏXð<BR>
	 *<BR>
	 *@@Version@@ú@t@@@XVÒ@@@@@Rg<BR>
	 *@|||||||||||||||||||||||||||||||||||<BR>
	 *@@00.00@@2003/03/24@ó@KO@@VKì¬<BR>
	 *<BR>
	 */
	public boolean checkModified(
	) {
		boolean		bState;

		bState	= isSelected();
		if ( pbSelect_Save != bState ) {
			return true;
		} else {
            return false;
        }
	}

	public void clearModified(
	) {
		pbSelect_Save	= isSelected();
	}
}
