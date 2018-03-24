package client.framework.view.vwcomp;

import java.awt.Color;
import java.awt.Font;

import javax.swing.Icon;

import c2s.dto.ITreeNode;
import client.framework.view.common.DefaultComp;
import c2s.dto.IDto;

public class NodeViewManager {
    Object node;
    String dataName;
    Object dataValue;

    public static final Color TableLineFore = new Color(0, 0, 0); //行前景色
    public static final Color TableLineOddBack = new Color(255, 255, 255); //奇数行背景色
//public static final Color CGridGray = new Color(234,234,234);//标准4
    public static final Color TableLineEvenBack = new Color(234, 234, 234); //偶数行背景色
    public static final Color TableSelectFocusFore = Color.white;
    public static final Color TableSelectFocusBack = new Color(49, 106, 197);
    public static final Color TableSelectFore = Color.white;
    public static final Color TableSelectBack = new Color(49, 106, 197);
    public Font textFont = DefaultComp.TEXT_FONT;

    public Color getBackground() {
        return Color.white;
    }

    public Color getOddBackground() {
        return NodeViewManager.TableLineOddBack;
    }

    public Color getEvenBackground() {
        return NodeViewManager.TableLineEvenBack;
    }

    public Color getSelectBackground() {
        return NodeViewManager.TableSelectBack;
    }

    public Color getSelectForeground() {
        return NodeViewManager.TableSelectFore;
    }

    public Color getForeground() {
        return NodeViewManager.TableLineFore;
    }

    public Icon getIcon() {
        return null;
    }

    public void setTextFont(Font textFont) {
        this.textFont = textFont;
    }

    public Font getTextFont() {
        return this.textFont;
    }

    public void setNode(Object node) {
        this.node = node;
    }

    public ITreeNode getNode() {
        if( node instanceof ITreeNode ){
            return (ITreeNode)this.node;
        }else{
            return null;
        }
    }

    public IDto getDto(){
        if( node instanceof IDto ){
            return (IDto)node;
        }else{
            return null;
        }
    }

	/**
	 * @return Returns the dataName.
	 */
	public String getDataName() {
		return dataName;
	}

	/**
	 * @param dataName The dataName to set.
	 */
	public void setDataName(String dataName) {
		this.dataName = dataName;
	}

	/**
	 * @return Returns the dataValue.
	 */
	public Object getDataValue() {
		return dataValue;
	}

	/**
	 * @param dataValue The dataValue to set.
	 */
	public void setDataValue(Object dataValue) {
		this.dataValue = dataValue;
	}
}
