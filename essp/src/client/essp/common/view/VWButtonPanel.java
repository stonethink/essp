package client.essp.common.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import client.image.ComImage;
import org.apache.log4j.Category;
import java.util.List;
import java.util.ArrayList;

public class VWButtonPanel extends JPanel {
    protected Category log = Category.getInstance("client");

    private List buttonList = new ArrayList();

    public VWButtonPanel() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //Component initialization
    private void jbInit() throws Exception {
        FlowLayout flowLayout1 = new FlowLayout();
        flowLayout1.setAlignment(FlowLayout.RIGHT);
        flowLayout1.setHgap(2);
        flowLayout1.setVgap(0);

        this.setLayout(flowLayout1);
        this.setOpaque(true);
    }

    public void setButtonVisible(int i, boolean visible ){
        if( i >= 0 && i < this.buttonList.size() ){
            ( (JButton)buttonList.get(i) ).setVisible( visible );
        }
    }

    public void setButtonEnabled(int i, boolean enabled ){
        if( i >= 0 && i < this.buttonList.size() ){
            ( (JButton)buttonList.get(i) ).setEnabled( enabled );
        }
    }

    //don't call this function
    public void setBounds(int x, int y, int w, int h) {
        super.setBounds(x, y, w, 21);
    }

    public JButton addButton(JButton button) {
        this.add(button);
        buttonList.add(button);
        return button;
    }

    public JButton addButton(String buttonIcon) {
        ImageIcon img = new ImageIcon(ComImage.getImage(buttonIcon));

        JButton button = new JButton();
        button.setMaximumSize(new Dimension(20, 20));
        button.setMinimumSize(new Dimension(20, 20));
        button.setPreferredSize(new Dimension(20, 20));
        button.setText("");
        button.setIcon(img);

        //set the icon's name as tooltip
        String[] ary = buttonIcon.split(".");
        if (ary.length > 1) {
            button.setToolTipText(ary[0]);
        }

        addButton(button);
        return button;
    }

    public JButton addSaveButton(ActionListener lis) {
        JButton b = addButton("save.png");
        b.setToolTipText("rsid.common.save");
        b.addActionListener(lis);
        return b;
    }

    public JButton addDelButton(ActionListener lis) {
        JButton b = addButton("del.png");
        b.setToolTipText("rsid.common.delete");
        b.addActionListener(lis);
        return b;
    }

    public JButton addAddButton(ActionListener lis) {
        JButton b = addButton("add.png");
        b.setToolTipText("rsid.common.add");
        b.addActionListener(lis);
        return b;
    }

    public JButton addLoadButton(ActionListener lis) {
        JButton b = addButton("refresh.png");
        b.setToolTipText("rsid.common.refresh");
        b.addActionListener(lis);
        return b;
    }

    public JButton addEditButton(ActionListener lis) {
        JButton b = addButton("edit.png");
        b.setToolTipText("rsid.common.edit");
        b.addActionListener(lis);
        return b;
    }

}
