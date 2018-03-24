package client.essp.common.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class VWJSplitLeft
    extends JPanel {
  private IExpender expender=null;
  private boolean isExpand=false;
  private static final int ICON_WIDTH=15;
  private static final int ICON_HEIGHT=15;
  JLabel clickButton = new JLabel();
  Cursor oldCursor=null;
  ImageIcon noExpendImg=null;
  ImageIcon expendImg=null;
  Cursor currCursor=new Cursor(Cursor.HAND_CURSOR);
  public VWJSplitLeft() {
    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void setExpender(IExpender expender) {
    this.expender=expender;
  }

  private void jbInit() throws Exception {
    //jLabel1.setText("jLabel1");
    noExpendImg=new ImageIcon(this.getClass().getResource("noExpend.png"));
    expendImg=new ImageIcon(this.getClass().getResource("expend.png"));
    this.setLayout(null);
    clickButton.setBounds(10, 228, ICON_WIDTH, ICON_HEIGHT);
    clickButton.addMouseListener(new VWJSplitLeftMouseAdapter(this));
    clickButton.setIcon(noExpendImg);
    this.setBackground(new Color(148, 170, 214));
    this.add(clickButton);
  }

  public void setBounds(int x,int y,int width,int height) {
    super.setBounds(x,y,width,height);
    clickButton.setBounds((this.getWidth()-ICON_WIDTH)/2, (this.getHeight()-ICON_HEIGHT)/2, ICON_WIDTH, ICON_HEIGHT);
    //setSize(width,height);
    //updateUI();
  }

  void onMouseExited(MouseEvent e) {
    //System.out.println("mouseExited");
    this.setCursor(oldCursor);
  }

  void onMouseEntered(MouseEvent e) {
    //System.out.println("mouseEntered");
    oldCursor=this.getCursor();
    this.setCursor(currCursor);
  }

  void onMouseClicked(MouseEvent e) {
    isExpand=!isExpand;

    if(isExpand==true) {
      clickButton.setIcon(expendImg);
    } else {
      clickButton.setIcon(noExpendImg);
    }

    if(expender!=null) {
      expender.expend(isExpand);
    }
  }
  public boolean isIsExpand() {
    return isExpand;
  }
}

class VWJSplitLeftMouseAdapter extends java.awt.event.MouseAdapter {
  VWJSplitLeft adaptee;

  VWJSplitLeftMouseAdapter(VWJSplitLeft adaptee) {
    this.adaptee = adaptee;
  }
  public void mouseExited(MouseEvent e) {
    adaptee.onMouseExited(e);
  }
  public void mouseEntered(MouseEvent e) {
    adaptee.onMouseEntered(e);
  }
  public void mouseClicked(MouseEvent e) {
    adaptee.onMouseClicked(e);
  }
}
