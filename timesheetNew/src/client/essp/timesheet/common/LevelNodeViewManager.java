package client.essp.timesheet.common;

import java.awt.Color;

import c2s.essp.timesheet.report.ISumLevel;
import client.framework.view.vwcomp.NodeViewManager;

public class LevelNodeViewManager extends NodeViewManager {
	
	public static final Color[] levelColors = new Color[10];
	
	public Color getBackground() {
		return getColor(super.getBackground());
    }
	
	public Color getOddBackground() {
		return getColor(super.getOddBackground());
    }

    public Color getEvenBackground() {
    	return getColor(super.getEvenBackground());
    }
    
    private Color getColor(Color defaultColor) {
    	int level = -1;
		if(this.getNode() != null) {
			Object data = this.getNode().getDataBean();
			if(data instanceof ISumLevel) {
				level = ((ISumLevel)data).getSumLevel();
			} else {
				level = this.getNode().getLevel();
			}
    	} else if(this.getDto() instanceof ISumLevel) {
    		ISumLevel levelBean = (ISumLevel) this.getDto();
    		level = levelBean.getSumLevel();
    	}
		
		if(level > -1 && level < levelColors.length) {
			 return levelColors[level];
		}
		return defaultColor;
    }
    
    static {
        levelColors[0] = new Color(204, 255, 255);
        levelColors[1] = new Color(255, 204, 153);
        levelColors[2] = Color.green;
        levelColors[3] = new Color(150, 150, 153);
        levelColors[4] = Color.ORANGE;
        levelColors[5] = Color.magenta;
        levelColors[6] = Color.PINK;
        levelColors[7] = Color.cyan;
        levelColors[8] = Color.YELLOW;
        levelColors[9] = Color.LIGHT_GRAY;
    }
}
