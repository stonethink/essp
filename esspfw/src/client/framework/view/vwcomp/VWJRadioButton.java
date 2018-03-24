package client.framework.view.vwcomp;

import client.framework.view.jmscomp.JMsRadioButton;
import java.awt.event.FocusEvent;
import client.framework.view.common.DefaultComp;

public class VWJRadioButton extends JMsRadioButton {
    public VWJRadioButton() {
        super();

        //add by lipengxu add focus background color
        this.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(FocusEvent e) {
                this_focusGained(e);
            }

            public void focusLost(FocusEvent e) {
                this_focusLost(e);
            }
        });

    }

    void this_focusGained(FocusEvent e) {
        setBackground(DefaultComp.BACKGROUND_COLOR_INPUT_ACTIVE);
    }

    void this_focusLost(FocusEvent e) {
        setBackground(DefaultComp.BACKGROUND_COLOR_ENABLED);
    }

}
