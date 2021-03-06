package client.framework.view.jmscomp;

import java.awt.*;
import javax.swing.*;
import javax.swing.JTextField;
import javax.swing.border.*;
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
		this.setEnabled( false );

		//++****************************
		//	¶îñÝè
		//--****************************
		setFont ( DefaultComp.LABEL_TITLE_FONT );
        this.setFont(DefaultComp.LABEL_TITLE_FONT);

		//++****************************
		//	FÝè
		//--****************************
		setDisabledTextColor ( DefaultComp.LABEL_TITLE_FOREGROUND_COLOR );
		setBackground ( DefaultComp.LABEL_TITLE_BACKGROUND_COLOR );

	}




}