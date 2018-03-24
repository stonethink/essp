package client.essp.timesheet.weeklyreport;

import client.framework.view.vwcomp.VWJButton;
import client.framework.view.vwcomp.IVWComponent;
import javax.swing.JPanel;
import validator.Validator;
import validator.ValidatorResult;
import client.framework.view.vwcomp.VWJText;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import com.wits.util.TestPanel;
import client.framework.view.common.DefaultComp;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import client.essp.common.view.VWWorkArea;

public class VWJLaunchButton extends JPanel implements IVWComponent {
    private VWJText textComp = new VWJText();
    private VWJButton buttonComp = new VWJButton();

  public VWJLaunchButton(){
      try {
          jbInit();
          addUICEvent();
      } catch (Exception e) {
          e.printStackTrace();
      }
  }

  private void jbInit() throws Exception {
      this.setLayout(new BorderLayout());

      textComp.setText("");
      textComp.setEditable(false);
      textComp.setEnabled(false);
      buttonComp.setText("rsid.common.launch");
      buttonComp.setToolTipText("rsid.common.launch");
      this.add(textComp, BorderLayout.CENTER);
      this.add(buttonComp, BorderLayout.EAST);
  }
  private void addUICEvent() {
     buttonComp.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
             actionPerformedButton(e);
         }
     });
 }
 public void actionPerformedButton(ActionEvent e){
     System.out.println( "begin actionPerformedButton()" );
     String filePath = textComp.getText();
     filePath = "\""+filePath+"\"";
     Runtime rt = Runtime.getRuntime();
     String cmd = "cmd /c Start \"\" ";

  try {
//        System.out.println( "begin open file: "+filePath );
//        System.out.println( cmd+filePath );
      rt.exec(cmd+filePath);
  } catch (Exception ex) {
      ex.printStackTrace();
  }
     System.out.println( "end actionPerformedButton" );
 }



  public void setUICValue(Object sValue) {
      textComp.setText(sValue.toString());
  }

  public Object getUICValue() {
      return null;
  }

  public void setDtoClass(Class _class) {
  }

  public void setValidator(Validator validator) {
  }

  public boolean validateValue() {
      return false;
  }

  public ValidatorResult getValidatorResult() {
      return null;
  }

  public void setErrorField(boolean _boolean) {
  }

  public IVWComponent duplicate() {
      return null;
  }

  public boolean isBReshap() {
      return false;
  }

  public void setBReshap(boolean _boolean) {
  }

  public int getOffset() {
      return 0;
  }

  public void setOffset(int _int) {
  }

  public int getHorizontalAlignment() {
      return SwingConstants.CENTER;
  }
  public static void main(String[] args) {
     VWJLaunchButton vwjLaunchbutton = new VWJLaunchButton();
     vwjLaunchbutton.setBounds( 100,150,200,DefaultComp.TEXT_HEIGHT );
     vwjLaunchbutton.setUICValue("d:\\a.txt");
      VWWorkArea workArea = new VWWorkArea();
      workArea.setLayout( null );
      workArea.add( vwjLaunchbutton );
      TestPanel.show(workArea);
 }

}
