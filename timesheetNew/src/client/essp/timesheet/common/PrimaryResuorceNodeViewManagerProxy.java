package client.essp.timesheet.common;

import java.awt.Color;
import java.awt.Font;

import javax.swing.Icon;

import c2s.dto.DtoUtil;
import c2s.dto.IDto;
import c2s.dto.ITreeNode;
import client.framework.common.Global;
import client.framework.view.vwcomp.NodeViewManager;

public class PrimaryResuorceNodeViewManagerProxy extends NodeViewManager {
	
	private String propertyName;
	private NodeViewManager innerNVM;
	
	public PrimaryResuorceNodeViewManagerProxy (final String primaryResourcePropertyName) {
		this(new NodeViewManager(), primaryResourcePropertyName);
	}
	
	public PrimaryResuorceNodeViewManagerProxy (NodeViewManager innerNVM, final String primaryResourcePropertyName) {
		this.innerNVM = innerNVM;
		propertyName = primaryResourcePropertyName;
	}
	
	public Color getOddBackground() {
		IDto dto = this.getDto();
		Object primaryResource = null;
		try {
			primaryResource = DtoUtil.getProperty(dto, propertyName);
		} catch (Exception e) {
		}
		if(primaryResource instanceof String) {
			String manager = (String) primaryResource;
			if(Global.userId.toUpperCase().equals(manager.toUpperCase())) {
				return Color.PINK;
			}
		}
        return innerNVM.getOddBackground();
    }

    public Color getEvenBackground() {
    	IDto dto = this.getDto();
		Object primaryResource = null;
		try {
			primaryResource = DtoUtil.getProperty(dto, propertyName);
		} catch (Exception e) {
		}
		if(primaryResource instanceof String) {
			String manager = (String) primaryResource;
			if(Global.userId.toUpperCase().equals(manager.toUpperCase())) {
				return Color.PINK;
			}
		}
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

    public Color getForeground() {
        return innerNVM.getForeground();
    }

    public Icon getIcon() {
        return innerNVM.getIcon();
    }

    public void setTextFont(Font textFont) {
    	innerNVM.setTextFont(textFont);
    }

    public Font getTextFont() {
        return innerNVM.getTextFont();
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
