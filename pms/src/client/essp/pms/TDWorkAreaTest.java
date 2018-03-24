package client.essp.pms;

import client.essp.common.view.VWTDWorkArea;
import com.wits.util.TestPanel;
import javax.swing.JSplitPane;
import client.essp.common.view.VWWorkArea;
import javax.swing.JButton;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class TDWorkAreaTest extends VWTDWorkArea {
    public TDWorkAreaTest() {
        super(200);
//        super(JSplitPane.HORIZONTAL_SPLIT,true);
        this.setSize(900,700);
        this.setHorizontalSplit();
        this.setVisible(true);
//        JSplitPane js=(JSplitPane)this.getComponent(0);
//        js.setOneTouchExpandable(true);
//        js.setDividerSize(8);
        this.setOneTouchExpandable();
//        this.setTopComponent(new JButton("TEST"));
//        this.setBottomComponent(new JButton("TEST2"));
//        this.setOneTouchExpandable(true);

    }

    public static void main(String[] args) {
        TDWorkAreaTest tdworkareatest = new TDWorkAreaTest();
        VWWorkArea wa=new VWWorkArea();
        wa.setSize(900,700);
        wa.add(tdworkareatest);
        TestPanel.show(wa);

    }
}
