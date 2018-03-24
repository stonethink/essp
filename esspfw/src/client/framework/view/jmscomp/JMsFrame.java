package client.framework.view.jmscomp;

import java.awt.*;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.border.*;

public class JMsFrame extends JLabel {
	BorderLayout borderLayout1 = new BorderLayout();
	Border border1;

	public JMsFrame() {
		try {
			jbInit();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	private void jbInit() throws Exception {
		border1 = BorderFactory.createLineBorder(Color.black,2);
		this.setBorder(BorderFactory.createEtchedBorder());
		this.setText( "" );
	}
}