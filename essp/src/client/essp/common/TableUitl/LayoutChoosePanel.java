package client.essp.common.TableUitl;

import java.awt.Component;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;

import client.essp.common.view.VWWorkArea;
import client.essp.common.view.ui.MessageUtil;
import client.framework.view.vwcomp.VWJButton;
import client.image.ComImage;

import com.wits.util.TestPanel;

/**
 * <p>Title: TableLayoutChooseDisplay</p>
 *
 * <p>Description: Layout choose table</p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public class LayoutChoosePanel extends VWWorkArea {
	
	private List<LayoutListener> layoutChangeListeners = new ArrayList<LayoutListener>();

	private String openLayout;
	private Vector fullVector = new Vector();

	private VWJButton jButtonOpen = new VWJButton();
	private JList jListMain = new JList();

    public LayoutChoosePanel(Collection<String> fullLayouts, String oldLayout) {
        this(fullLayouts, oldLayout, null);
    }
    
    public LayoutChoosePanel(Collection<String> fullLayouts, String oldLayout, LayoutListener l) {
        try {
        	fullVector = new Vector(fullLayouts);
        	openLayout = oldLayout;
        	jListMain = new JList(fullVector);
        	jListMain.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        	this.addLayoutListener(l);
            jbInit();
            addUICEvent();
        }
        catch(Exception ex) {
            log.error(ex);
        }
    }

    void jbInit() throws Exception {

        jListMain.setCellRenderer(new SelectColumnCellRenderer());

        jButtonOpen.setMargin(new Insets(0, 5, 0, 5));
        jButtonOpen.setText(" Open ");


        JScrollPane jScrollPane = new JScrollPane();
        jScrollPane.getViewport().add(jListMain, null);

        VWWorkArea listWorkArea = new VWWorkArea();
        listWorkArea.getButtonPanel().addButton( jButtonOpen );
        listWorkArea.add( jScrollPane );

        this.addTab("Columns", listWorkArea);
    }

    private void addUICEvent(){

    	jButtonOpen.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                actionPerformedOpen(e);
            }
        });

    }
    
    public void addLayoutListener(LayoutListener l) {
    	if(l != null) {
    		layoutChangeListeners.add(l);
    	}
    }
    
    private void fireLayoutChanged(String name) {
    	for(LayoutListener l : layoutChangeListeners) {
    		l.setLayout(name);
    	}
    }

    public String getOpenLayout(){
        return this.openLayout;
    }
    
    public void setOpenLayout(String name) {
    	this.openLayout = name;
    }

    void actionPerformedOpen(ActionEvent e) {
        Object[] objSels = this.jListMain.getSelectedValues();
        if(objSels != null && objSels.length >= 1) {
        	openLayout = (String) objSels[0];
        }
        this.jListMain.updateUI();
        this.fireLayoutChanged(openLayout);
    }
    
    public class SelectColumnCellRenderer extends DefaultListCellRenderer {
        public Icon hideIcon = new ImageIcon(ComImage.getImage("hide.gif"));
        public Icon showIcon = new ImageIcon(ComImage.getImage("show.gif"));

        public Component getListCellRendererComponent(
            JList list,
            Object value,
            int index,
            boolean isSelected,
            boolean cellHasFocus) {
            setComponentOrientation(list.getComponentOrientation());
            if (isSelected) {
                setBackground(list.getSelectionBackground());
                setForeground(list.getSelectionForeground());
            }
            else {
                setBackground(list.getBackground());
                setForeground(list.getForeground());
            }

            if (openLayout == value) {
                setIcon( showIcon);
            }
            else {
                setIcon(hideIcon);
            }
            String valueStr = (value == null) ? "" : value.toString();
            setText( MessageUtil.getMessage(valueStr));

            setEnabled(list.isEnabled());
            setFont(list.getFont());
            setBorder( (cellHasFocus) ?
                      UIManager.getBorder("List.focusCellHighlightBorder") :
                      noFocusBorder);

            return this;
        }

    }

    public static void main(String args[]){
        Vector fullV = new Vector();
        fullV.add("a");
        fullV.add("b");
        fullV.add("c");
        fullV.add("d");
        fullV.add("e");
        fullV.add("f");
        TestPanel.show( new LayoutChoosePanel(fullV, "c") );
    }

}

