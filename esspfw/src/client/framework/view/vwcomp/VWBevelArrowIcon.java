package client.framework.view.vwcomp;

import java.awt.*;

import javax.swing.Icon;
import javax.swing.UIManager;

public class VWBevelArrowIcon implements Icon {

	public static final int DIRECTION_UP = 0;

	public static final int DIRECTION_DOWN = 1;

	private static final int DEFAULT_SIZE = 11;

	private Color leftEdgeColor;

	private Color rightEdgeColor;

	private Color fillColor;

	private int size;

	private int direction;
	
	public VWBevelArrowIcon(boolean upArrow) {
		this(upArrow ? DIRECTION_UP : DIRECTION_DOWN, false, true);
	}

	public VWBevelArrowIcon(int direction, boolean isRaisedView,
			boolean isPressedView) {
		if (isRaisedView) {
			if (isPressedView) {
				init(UIManager.getColor("controlLtHighlight"),
						UIManager.getColor("controlDkShadow"),
						UIManager.getColor("controlShadow"),
						DEFAULT_SIZE,
						direction);
			} else {
				init(UIManager.getColor("controlHighlight"),
						UIManager.getColor("controlShadow"),
						UIManager.getColor("control"), 
						DEFAULT_SIZE, 
						direction);
			}
		} else if (isPressedView) {
			init(UIManager.getColor("controlDkShadow"),
					UIManager.getColor("controlLtHighlight"),
					UIManager.getColor("controlShadow"),
					DEFAULT_SIZE,
					direction);
		} else {
			init(UIManager.getColor("controlShadow"),
					UIManager.getColor("controlHighlight"),
					UIManager.getColor("control"),
					DEFAULT_SIZE,
					direction);
		}
	}

	public VWBevelArrowIcon(Color edge1, Color edge2, Color fillColor, int size,
			int direction) {
		init(edge1, edge2, fillColor, size, direction);
	}

	private void init(Color edge1, Color edge2, Color fillColor, int size,
			int direction) {
		this.leftEdgeColor = edge1;
		this.rightEdgeColor = edge2;
		this.fillColor = fillColor;
		this.size = size;
		this.direction = direction;
	}

	private void drawDownArrow(Graphics g, int xo, int yo) {
		g.setColor(leftEdgeColor);
		g.drawLine(xo, yo, (xo + size) - 1, yo);
		g.drawLine(xo, yo + 1, (xo + size) - 3, yo + 1);
		g.setColor(rightEdgeColor);
		g.drawLine((xo + size) - 2, yo + 1, (xo + size) - 1, yo + 1);
		int x = xo + 1;
		int y = yo + 2;
		for (int dx = size - 6; y + 1 < yo + size; dx -= 2) {
			g.setColor(leftEdgeColor);
			g.drawLine(x, y, x + 1, y);
			g.drawLine(x, y + 1, x + 1, y + 1);
			if (dx > 0) {
				g.setColor(fillColor);
				g.drawLine(x + 2, y, x + 1 + dx, y);
				g.drawLine(x + 2, y + 1, x + 1 + dx, y + 1);
			}
			g.setColor(rightEdgeColor);
			g.drawLine(x + dx + 2, y, x + dx + 3, y);
			g.drawLine(x + dx + 2, y + 1, x + dx + 3, y + 1);
			x++;
			y += 2;
		}

		g.setColor(leftEdgeColor);
		g.drawLine(xo + size / 2, (yo + size) - 1, xo + size / 2,
				(yo + size) - 1);
	}

	private void drawUpArrow(Graphics g, int xo, int yo) {
		g.setColor(leftEdgeColor);
		int x = xo + size / 2;
		g.drawLine(x, yo, x, yo);
		x--;
		int y = yo + 1;
		for (int dx = 0; y + 3 < yo + size; dx += 2) {
			g.setColor(leftEdgeColor);
			g.drawLine(x, y, x + 1, y);
			g.drawLine(x, y + 1, x + 1, y + 1);
			if (dx > 0) {
				g.setColor(fillColor);
				g.drawLine(x + 2, y, x + 1 + dx, y);
				g.drawLine(x + 2, y + 1, x + 1 + dx, y + 1);
			}
			g.setColor(rightEdgeColor);
			g.drawLine(x + dx + 2, y, x + dx + 3, y);
			g.drawLine(x + dx + 2, y + 1, x + dx + 3, y + 1);
			x--;
			y += 2;
		}

		g.setColor(leftEdgeColor);
		g.drawLine(xo, (yo + size) - 3, xo + 1, (yo + size) - 3);
		g.setColor(rightEdgeColor);
		g.drawLine(xo + 2, (yo + size) - 2, (xo + size) - 1, (yo + size) - 2);
		g.drawLine(xo, (yo + size) - 1, xo + size, (yo + size) - 1);
	}

	public void paintIcon(Component c, Graphics g, int x, int y) {
		switch (direction) {
		case DIRECTION_DOWN:
			drawDownArrow(g, x, y);
			break;
		case DIRECTION_UP:
			drawUpArrow(g, x, y);
			break;
		}
	}
	
	public int getIconHeight() {
		return size;
	}

	public int getIconWidth() {
		return size;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
}
