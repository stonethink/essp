package client.essp.tc.test;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.border.LineBorder;

public class TcLineBorder extends LineBorder {
    boolean showTop = true;
    boolean showBottom = true;
    boolean showLeft = true;
    boolean showRight = true;

    public TcLineBorder(Color color) {
        super(color);
    }

    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Color oldColor = g.getColor();
        int i;

    /// PENDING(klobad) How/should do we support Roundtangles?
        g.setColor(lineColor);
        for (i = 0; i < thickness; i++) {
            drawHalfRect(g, x + i, y + i, width - i - i - 1, height - i - i - 1);
        }

        g.setColor(oldColor);
    }

    public void drawHalfRect(Graphics g, int x, int y, int width, int height) {
        if ((width < 0) || (height < 0)) {
            return;
        }

        if (height == 0 || width == 0) {
            if( showBottom && showTop ){
                g.drawLine(x, y, x + width, y + height);
            }
        } else {
            if( showTop ) g.drawLine(x, y, x + width - 1, y);
            if( showRight ) g.drawLine(x + width, y, x + width, y + height - 1);
            if( showBottom ) g.drawLine(x + width, y + height, x + 1, y + height);
            if( showLeft ) g.drawLine(x, y + height, x, y + 1);
        }
    }

    public void setShowBottom(boolean showBottom) {
        this.showBottom = showBottom;
    }

    public void setShowLeft(boolean showLeft) {
        this.showLeft = showLeft;
    }

    public void setShowRight(boolean showRight) {
        this.showRight = showRight;
    }

    public void setShowTop(boolean showTop) {
        this.showTop = showTop;
    }
}
