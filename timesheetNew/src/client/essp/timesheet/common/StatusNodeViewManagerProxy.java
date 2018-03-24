package client.essp.timesheet.common;

import java.awt.Color;
import java.awt.Font;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import c2s.dto.IDto;
import c2s.dto.ITreeNode;
import c2s.essp.timesheet.step.IDtoActivityFilterData;
import client.framework.view.vwcomp.NodeViewManager;
import client.image.ComImage;

public class StatusNodeViewManagerProxy extends NodeViewManager {
	private final static Icon ICON_NORMAL = new ImageIcon(ComImage.getImage("acceptable.gif"));
	private final static Icon ICON_DELAY = new ImageIcon(ComImage.getImage("critical.gif"));
	private final static Icon ICON_AHEAD = new ImageIcon(ComImage.getImage("exceptional.gif"));
	private String propertyName;
	private NodeViewManager innerNVM;
	
	public StatusNodeViewManagerProxy (final String statusPropertyName) {
		this(new NodeViewManager(), statusPropertyName);
	}
	
	public StatusNodeViewManagerProxy (NodeViewManager innerNVM, final String statusPropertyName) {
		this.innerNVM = innerNVM;
		propertyName = statusPropertyName;
	}
	
	/**
	 * 根据不同状态返回不同颜色
	 */
    public Color getForeground() {
    	if(propertyName.equals(this.getDataName())) {
			if(IDtoActivityFilterData.STATUS_AHEAD.equals(this.getDataValue())) {
				return Color.BLUE;
			} else if(IDtoActivityFilterData.STATUS_DELAY.equals(this.getDataValue())) {
				return Color.RED;
			} else if(IDtoActivityFilterData.STATUS_NORMAL.equals(this.getDataValue())) {
				return Color.GREEN;
			}
		}
        return innerNVM.getForeground();
    }

	/**
	 * 改为粗体
	 */
	public Font getTextFont() {
		Font f = innerNVM.getTextFont();
    	if(propertyName.equals(this.getDataName())) {
    		return f.deriveFont(Font.BOLD);
    	}
		return f;
	}
	
	
	/*
	 * 以下方法直接调用被代理对象
	 */
	
	public Color getOddBackground() {
        return innerNVM.getOddBackground();
    }

    public Color getEvenBackground() {
        return innerNVM.getEvenBackground();
    }
    
    public Color getBackground() {
        return innerNVM.getBackground();
    }


    public Color getSelectBackground() {
        return innerNVM.getSelectBackground();
    }

    public Color getSelectForeground() {
        return innerNVM.getSelectForeground();
    }


    public Icon getIcon() {
    	if(propertyName.equals(this.getDataName())) {
			if(IDtoActivityFilterData.STATUS_AHEAD.equals(this.getDataValue())) {
				return ICON_AHEAD;
			} else if(IDtoActivityFilterData.STATUS_DELAY.equals(this.getDataValue())) {
				return ICON_DELAY;
			} else if(IDtoActivityFilterData.STATUS_NORMAL.equals(this.getDataValue())) {
				return ICON_NORMAL;
			}
		}
        return innerNVM.getIcon();
    }

    public void setTextFont(Font textFont) {
    	innerNVM.setTextFont(textFont);
    }

    public void setNode(Object node) {
    	innerNVM.setNode(node);
    }

    public ITreeNode getNode() {
    	return innerNVM.getNode();
    }

    public IDto getDto(){
    	return innerNVM.getDto();
    }

	/**
	 * @return Returns the dataName.
	 */
	public String getDataName() {
		return innerNVM.getDataName();
	}

	/**
	 * @param dataName The dataName to set.
	 */
	public void setDataName(String dataName) {
		innerNVM.setDataName(dataName);
	}

	/**
	 * @return Returns the dataValue.
	 */
	public Object getDataValue() {
		return innerNVM.getDataValue();
	}

	/**
	 * @param dataValue The dataValue to set.
	 */
	public void setDataValue(Object dataValue) {
		innerNVM.setDataValue(dataValue);
	}
}