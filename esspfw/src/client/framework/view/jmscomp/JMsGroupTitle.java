package client.framework.view.jmscomp;

import java.awt.*;
import javax.swing.*;
import javax.swing.JTextField;
import client.framework.view.common.*;

/**
 * <p>^Cg: JMsComp </p>
 * <p>à¾: Javax.Swingp³ÌIWiR|[lgQ</p>
 * <p>ì : milestone Copyright (c) 2002</p>
 * <p>ïÐ¼: }CXg[®ïÐ</p>
 * @author ¢üÍ
 * @version 1.0
 */

public class JMsGroupTitle extends JTextField {
	BorderLayout borderLayout1 = new BorderLayout();

	public JMsGroupTitle() {
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
		this.setBorder(null);
		this.setHorizontalAlignment(SwingConstants.CENTER);
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
		//	óÔÝè
		//--****************************
		this.setEnabled ( false );

		//++****************************
		//	¶îñÝè
		//--****************************
		setFont ( DefaultComp.GROUP_TITLE_FONT );

		//++****************************
		//	FÝè
		//--****************************
		this.setDisabledTextColor ( DefaultComp.GROUP_TITLE_FOREGROUND_COLOR );
		setBackground ( DefaultComp.GROUP_TITLE_BACKGROUND_COLOR );

	}


}


