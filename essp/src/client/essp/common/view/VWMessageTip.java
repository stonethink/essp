package client.essp.common.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.Timer;

import com.wits.util.TestPanel;

/**
 * 在parent的附近显示msg信息，并在show_time时间后自动消失
 */
public class VWMessageTip {
    final static int SHOW_TIME = 3; //seconds
    Popup popup = null;
    JLabel label = null;

    public VWMessageTip() {
    }

    public void show(Component parent, String msg) {
        label = new JLabel(" " +msg+" ");
        //label.setBackground(new Color(251,247,204));
        label.setBorder(BorderFactory.createLineBorder(Color.black));
        label.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (popup != null) {
                    popup.hide();
                    popup = null;
                }
            }
        });

        Point p = getLocation(parent);
        if( p == null ){
            return;
        }

        PopupFactory factory = PopupFactory.getSharedInstance();
        popup = factory.getPopup(parent, label, p.x,p.y);
        popup.show();

        //SHOW_TIME后自动消失
        Timer delayTime = new Timer(1000 * SHOW_TIME, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (popup != null) {
                    popup.hide();
                    popup = null;
                }
            }
        });
        delayTime.setRepeats(false);
        delayTime.start();
    }

    private Point getLocation(Component parentComp){
        if( parentComp == null || label == null ){
            return null;
        }

        Point p = null;
        try {
            p = new Point();

            int x0 = parentComp.getLocationOnScreen().x;
            int y0 = parentComp.getLocationOnScreen().y;
            int w0 = parentComp.getWidth();
            int h0 = parentComp.getHeight();

            int w1 = guessWidth(this.label.getText());

            if (w1 > w0) {

                //防止msg太多，显示到屏幕外
                p.x = x0 + w0 - w1 - 10;
            } else {

                //防止buttonPanel太长，显示到屏幕中间
                p.x = x0;

                int minX = x0 + w0 - 200;
                if (p.x < minX) {
                    p.x = minX;
                }
            }

            p.y = y0 + h0 + 3;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

        return p;
    }

    int guessWidth(String text){
        return text.length() * 6;
    }

    public static void main(String args[]) {
        VWWorkArea w = new VWGeneralWorkArea();

        final VWWorkArea sw = new VWGeneralWorkArea();
        sw.getButtonPanel().addSaveButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                (new VWMessageTip()).show(sw.getButtonPanel(), "ABCDEFGGGG");
            }
        });

        w.addTab("Show Message tip",sw);
        TestPanel.show(w);
    }
}
