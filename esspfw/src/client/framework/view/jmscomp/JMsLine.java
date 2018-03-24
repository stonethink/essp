package client.framework.view.jmscomp;

import java.awt.*;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.border.*;

public class JMsLine extends JLabel {
	BorderLayout borderLayout1 = new BorderLayout();
	Border border1;

	public JMsLine() {
		try {
			jbInit();
			super.setText( "" );
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	private void jbInit() throws Exception {
		border1 = BorderFactory.createLineBorder(Color.white,2);
		this.setBackground(Color.gray);
		this.setBorder(border1);
		this.setText("     " );
	}
}