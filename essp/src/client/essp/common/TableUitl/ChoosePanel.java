package client.essp.common.TableUitl;

import java.awt.Component;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.UIManager;

import client.essp.common.view.VWWorkArea;
import client.framework.view.vwcomp.VWJButton;
import client.image.ComImage;
import com.wits.util.TestPanel;
import client.essp.common.view.ui.MessageUtil;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Enovation</p>
 * @author Xuxi.Chen
 * @version 1.0
 */

public class ChoosePanel extends VWWorkArea {

    Set selectSet = new HashSet();
    Vector fullVector = new Vector();

    JPopupMenu jPopupMenu1 = new JPopupMenu();
    JMenuItem jMenuItemShow = new JMenuItem();
    JMenuItem jMenuItemHide = new JMenuItem();
    JMenuItem jMenuItemUp = new JMenuItem();
    JMenuItem jMenuItemDown = new JMenuItem();

    VWJButton jButtonUp = new VWJButton();
    VWJButton jButtonDown = new VWJButton();
    VWJButton jButtonShow = new VWJButton();
    VWJButton jButtonHide = new VWJButton();

    JList jListMain = new JList();

    public ChoosePanel(Object[] fullObjects, Object[] oldSelects) {
        try {
            for(int i = 0; i< fullObjects.length; i++){
                fullVector.add(fullObjects[i]);
            }
            //改为按照配置文件的先后顺序来列出列表
            //modify by: Robin.Zhang
//            for(int i = 0; i< fullObjects.length; i++){
//                if(!fullVector.contains(fullObjects[i])){
//                   fullVector.add(fullObjects[i]);
//                }
//            }

            //fullVector = TableModelColDisplay.convertToVector(fullObjects);
            jListMain = new JList(fullVector);
            for(int i = 0; i< oldSelects.length; i++){
                this.selectSet.add(oldSelects[i]);
            }

            jbInit();
            addUICEvent();
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    void jbInit() throws Exception {
//        this.setLayout( new BorderLayout() );
        jMenuItemShow.setText(" Show ");
        jMenuItemHide.setText(" Hide ");
        jMenuItemUp.setText("  Up  ");
        jMenuItemDown.setText(" Down ");

        jListMain.setCellRenderer(new SelectColumnCellRenderer());

        jButtonUp.setMargin(new Insets(0, 8, 0, 8));
        jButtonUp.setText("  Up  ");

        jButtonDown.setMargin(new Insets(0, 5, 0, 5));
        jButtonDown.setText(" Down ");

        jButtonShow.setMargin(new Insets(0, 5, 0, 5));
        jButtonShow.setText(" Show ");

        jButtonHide.setMargin(new Insets(0, 5, 0, 5));
        jButtonHide.setText(" Hide ");

        JScrollPane jScrollPane = new JScrollPane();
        jScrollPane.getViewport().add(jListMain, null);

        VWWorkArea listWorkArea = new VWWorkArea();
        listWorkArea.getButtonPanel().addButton( jButtonUp );
        listWorkArea.getButtonPanel().addButton( jButtonDown );
        listWorkArea.getButtonPanel().addButton( jButtonShow );
        listWorkArea.getButtonPanel().addButton( jButtonHide );
        listWorkArea.add( jScrollPane );

        jPopupMenu1.add(jMenuItemShow);
        jPopupMenu1.add(jMenuItemHide);
        jPopupMenu1.add(jMenuItemUp);
        jPopupMenu1.add(jMenuItemDown);

        this.addTab("Columns", listWorkArea);
    }

    private void addUICEvent(){
        jMenuItemShow.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                jMenuItemShow_actionPerformed(e);
            }
        });

        jMenuItemHide.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                jMenuItemHide_actionPerformed(e);
            }
        });

        jMenuItemUp.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                jMenuItemUp_actionPerformed(e);
            }
        });

        jMenuItemDown.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                jMenuItemUp_actionPerformed(e);
            }
        });

        jButtonUp.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                jButtonUp_actionPerformed(e);
            }
        });

        jButtonDown.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                jButtonDown_actionPerformed(e);
            }
        });

        jButtonShow.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                jButtonShow_actionPerformed(e);
            }
        });

        jButtonHide.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                jButtonHide_actionPerformed(e);
            }
        });

        jListMain.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e) {
                jListMain_mouseClicked(e);
            }
        });

    }


    public java.util.Set getShowSet(){
        return this.selectSet;
    }

    public Object[] getShowObjects(){
        Vector v = new Vector();
        for(int i = 0; i< fullVector.size(); i++){
            Object aObj = fullVector.get(i);
            if(this.selectSet.contains(aObj)){
                v.add(aObj);
            }
        }
        Object[] arrRtn = new Object[v.size()];
        v.toArray(arrRtn);
        return arrRtn;
    }

    void jListMain_mouseClicked(MouseEvent e) {
        if(e.isMetaDown()){
            this.jPopupMenu1.show(jListMain, e.getX(), e.getY() );
        }
    }

    void jMenuItemShow_actionPerformed(ActionEvent e) {
        Object[] objSels = this.jListMain.getSelectedValues();
        for(int i = 0; i< objSels.length; i++){
            this.selectSet.add(objSels[i]);
        }
        this.jListMain.updateUI();
    }

    void jMenuItemHide_actionPerformed(ActionEvent e) {
        Object[] objSels = this.jListMain.getSelectedValues();
        for(int i = 0; i< objSels.length; i++){
            this.selectSet.remove(objSels[i]);
        }
        this.jListMain.updateUI();
    }

    void jMenuItemUp_actionPerformed(ActionEvent e) {
        int index = this.jListMain.getSelectedIndex();
        Vector list = this.fullVector;
        if(index > 0 && index < list.size()){
            Object objSel = list.get(index);
            Object exgObj = list.get(index -1);
            list.set(index, exgObj);
            list.set(index -1 , objSel);
            this.jListMain.setSelectedIndex(index -1);
            this.jListMain.updateUI();
        }
    }

    void jMenuItemDown_actionPerformed(ActionEvent e) {
        int index = this.jListMain.getSelectedIndex();
        Vector list = this.fullVector;
        if(index >= 0 && index < list.size() -1){
            Object objSel = list.get(index);
            Object exgObj = list.get(index +1);
            list.set(index, exgObj);
            list.set(index +1 , objSel);
            this.jListMain.setSelectedIndex(index +1);
            this.jListMain.updateUI();
        }
    }

    void jButtonUp_actionPerformed(ActionEvent e) {
        this.jMenuItemUp_actionPerformed(e);
    }

    void jButtonDown_actionPerformed(ActionEvent e) {
        this.jMenuItemDown_actionPerformed(e);
    }

    void jButtonShow_actionPerformed(ActionEvent e) {
        this.jMenuItemShow_actionPerformed(e);
    }

    void jButtonHide_actionPerformed(ActionEvent e) {
        this.jMenuItemHide_actionPerformed(e);
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

            if (selectSet.contains(value)) {
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
        String fullObject[] = new String[]{"a","b","c","d"};
        String selectObject[] = new String[]{"a","c"};
        TestPanel.show( new ChoosePanel(fullObject, selectObject) );
    }

}

