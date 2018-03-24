package client.essp.common.view;

import javax.swing.*;
public class VWJToolBar extends JToolBar{
  public VWJToolBar() {
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }
  private void jbInit() throws Exception {

  }

  public JButton addNavigationButton(String imageName,
                                     String actionCommand,
                                     String toolTipText,
                                     String altText) {
      //Look for the image.
      java.net.URL imageURL=null;
      String imgLocation=null;
      if(imageName!=null && imageName.trim().equals("")==false) {
        imgLocation = "toolbarButtonGraphics/navigation/"
            + imageName
            + ".gif";
        imageURL = this.getClass().getResource(imgLocation);
      }

      //Create and initialize the button.
      JButton button = new JButton();
      button.setActionCommand(actionCommand);
      button.setToolTipText(toolTipText);
      //button.addActionListener(this);

      if (imageURL != null) {                      //image found
          button.setIcon(new ImageIcon(imageURL, altText));
      } else {                                     //no image found
          button.setText(altText);
          System.err.println("Resource not found: "
                             + imgLocation);
      }

      return button;
  }

}
