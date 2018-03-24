package client.essp.common.TableUitl;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import client.image.ComImage;

/**
 * <p>
 * Title: TableLayoutChooseDisplay
 * </p>
 * 
 * <p>
 * Description: Table column layout
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2008
 * </p>
 * 
 * <p>
 * Company: WistronITS
 * </p>
 * 
 * @author lipengxu
 * @version 1.0
 */
public class TableLayoutChooseDisplay implements LayoutListener {
	private List<LayoutListener> layoutChangeListeners = new ArrayList<LayoutListener>();

	private Map<String, String[]> dataMap = new TreeMap<String, String[]>();

	private TableColumnChooseDisplay columnChoose;
	
	private JButton btnDisplay;
	
	private String openLayout;

	public TableLayoutChooseDisplay(TableColumnChooseDisplay columnChoose) {
		this.columnChoose = columnChoose;
	}

	public void addLayout(String name, String[] columnNames) {
		if(openLayout == null) {
			openLayout = name;
		}
		dataMap.put(name, columnNames);
	}

	public String[] getLayout(String name) {
		return dataMap.get(name);
	}

	public void setLayout(String name) {
		if(name == null || name.equals(openLayout) || !dataMap.containsKey(name)) {
			return;
		}
		openLayout = name;
		String[] columnNames = dataMap.get(name);
		if (columnNames == null) {
			return;
		}
		columnChoose.setSelectColumn(columnNames);
		columnChoose.setColumnConfigOfModel();
		fireLayoutChanged(name);
	}

	public void addLayoutListener(LayoutListener l) {
		if (l != null) {
			layoutChangeListeners.add(l);
		}
	}

	private void fireLayoutChanged(String name) {
		for (LayoutListener l : layoutChangeListeners) {
			l.setLayout(name);
		}
	}
	
	public JButton getDisplayButton() {
        btnDisplay = new JButton();
        btnDisplay.setIcon(new ImageIcon(ComImage.getImage("layout.png")));
        btnDisplay.setMaximumSize(new Dimension(20, 20));
        btnDisplay.setMinimumSize(new Dimension(20, 20));
        btnDisplay.setPreferredSize(new Dimension(20, 20));
        btnDisplay.setText("");
        btnDisplay.setToolTipText("rsid.common.layout");

        btnDisplay.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showChooseDialog(columnChoose.parentPanel);
            }
        });

        return this.btnDisplay;
    }
	
	private boolean showChooseDialog(Component parentPanel) {
		LayoutChoosePanel choosePanel = new LayoutChoosePanel(dataMap.keySet(), openLayout, this);
        if (this.dataMap.size() > 18) {
            choosePanel.setPreferredSize(new Dimension(280, 400));
        } else {
            choosePanel.setPreferredSize(new Dimension(280, 200));
        }
        if (JOptionPane.showConfirmDialog(parentPanel, choosePanel,
                                          "Select Layout To Display",
                                          JOptionPane.OK_CANCEL_OPTION)
            == JOptionPane.OK_OPTION) {
            this.setLayout(choosePanel.getOpenLayout());
            return true;
        } else {
            return false;
        }
    }
}
