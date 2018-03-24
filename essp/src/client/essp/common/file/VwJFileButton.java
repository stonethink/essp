
package client.essp.common.file;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import c2s.dto.FileInfo;
import client.essp.common.view.VWWorkArea;
import client.framework.common.Global;
import client.framework.view.common.DefaultComp;
import client.framework.view.common.comMSG;
import client.framework.view.vwcomp.IVWComponent;
import client.framework.view.vwcomp.VWJButton;
import client.framework.view.vwcomp.VWJLabel;
import client.image.ComImage;
import client.net.Download;
import client.net.Upload;
import com.wits.util.StringUtil;
import com.wits.util.TestPanel;
import netscape.javascript.JSException;
import netscape.javascript.JSObject;
import validator.Validator;
import validator.ValidatorResult;
import javax.swing.JOptionPane;
import java.awt.event.*;

/**
 * <p>Title: </p>
 * <p>Description: 现在的处理是显示loginId, 而非name</p>
 * 不再使用Human类
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Enovation</p>
 * @author yery
 * @version 1.0
 */

public class VwJFileButton extends JPanel implements IVWComponent {
    String module;
    String acntCode;
    String fileId;
    String fileName;

    VWJLabel textComp = new VWJLabel();
    VWJButton buttonComp = new VWJButton();
    boolean showButton = true;

    Download download = new Download();

    boolean bReshap = false;
    int offset = 0;

    public VwJFileButton(String module) {
        this(module, null, true );
    }

    public VwJFileButton(String module, boolean showButton) {
        this(module, null ,showButton );
    }

    public VwJFileButton(String module, String acntCode) {
        this( module, acntCode ,true );
    }

    public VwJFileButton(String module, String acntCode, boolean showButton) {
        this.module = module;
        this.acntCode = acntCode;
        fileId = null;
        fileName = null;
        this.showButton = showButton;

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
        textComp.setBorder(UIManager.getBorder("TextField.border"));

        this.add(textComp, BorderLayout.CENTER);

        if( showButton ){
            buttonComp.setText("");
            buttonComp.setIcon(new ImageIcon(ComImage.getImage("openDoc.gif")));
            this.add(buttonComp, BorderLayout.EAST);
        }
    }

    private void addUICEvent() {
        textComp.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if( isFileEnable() ){
                    if (e.isMetaDown() == true) {
                        mouseClickedRightThis(e);
                    } else {
                        mouseClickedLeftThis(e);
                    }
                }else{
                    JOptionPane.showMessageDialog(VwJFileButton.this,"No file.");
                }
            }

            public void mousePressed(MouseEvent e) {
            }

            public void mouseReleased(MouseEvent e) {
            }

            public void mouseEntered(MouseEvent e) {
                if( isFileEnable() ){
                    mouseEnteredThis(e);
                }
            }

            public void mouseExited(MouseEvent e) {
                if( isFileEnable() ){
                    mouseExitedThis(e);
                }
            }
        });

        buttonComp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedUpload(e);
            }
        });
    }

    public String getFileUrl(){
        //like: http://localhost/essp/upload/PWM/002645W/book.txt
        String url = Global.appRoot + "/" + FileInfo.getFileRoot() + "/" + module + "/" + acntCode + "/" + fileId;
        if( getFileExtend().equals("") == false ){
            url += "." + getFileExtend();
        }

        return url;
    }

    public String getFileExtend(){
        if( fileName != null ){
            int i = fileName.lastIndexOf(".");
            if( i >= 0 ){
                return fileName.substring(i+1);
            }
        }

        return "";
    }
    protected void mouseClickedLeftThis(MouseEvent e) {
        //JOptionPane.showMessageDialog(getParent(), "haha");

        try {
            JSObject win = JSObject.getWindow(Global.applet);
            System.out.println(getFileUrl());
            win.eval("window.open(\"" + getFileUrl() + "\");");
        } catch (JSException ex) {
            //ex.printStackTrace();
        }

    }

    protected void mouseClickedRightThis(MouseEvent e) {
        /*
                 MenuItem itemSaveAs = new MenuItem("Save as");
                 itemSaveAs.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedDownLoad();
            }
                 });
                 PopupMenu popupMenu = new PopupMenu();
                 popupMenu.add(itemSaveAs);
                 if (popupMenu.getParent() == null) {
            textComp.add(popupMenu);
                 }

                 popupMenu.show(textComp, e.getX()+2, e.getY()+2);
         */
    }

    protected void actionPerformedDownLoad(){
        try {
            if (download.showFileDialog(this, module, acntCode, fileId, fileName)) {
                System.out.println("begin to download");
                download.execute();
                System.out.println("OK");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            comMSG.dispErrorDialog("Download fail.");
        }
    }

    protected void mouseEnteredThis(MouseEvent e) {
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        textComp.setForeground(Color.BLUE);
        textComp.updateUI();
    }

    protected void mouseExitedThis(MouseEvent e) {
        setCursor(Cursor.getDefaultCursor());
        textComp.setForeground(Color.black);
        textComp.updateUI();

        FocusEvent fe = new FocusEvent(this,FocusEvent.FOCUS_LOST);
        processFocusEvent(fe);
    }

    protected void actionPerformedUpload(ActionEvent e) {
        try {
            Upload uploader = new Upload();
            if (uploader.showFileDialog(this, this.module, this.acntCode, fileId)) {
                uploader.execute();
            }

            FileInfo fileInfo = uploader.getFileInfo();
            if (fileInfo != null) {
                String sFileInfo = StringUtil.nvl(fileInfo.getId())
                                   + "@" + StringUtil.nvl(fileInfo.getFilename());
                System.out.println(sFileInfo);

                setUICValue(sFileInfo);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            comMSG.dispErrorDialog("Upload fail.");
        }
    }

    protected java.awt.Frame getParentWindow() {
        java.awt.Container c = this.getParent();

        while (c != null) {
            if (c instanceof java.awt.Frame) {
                return (java.awt.Frame) c;
            }

            c = c.getParent();
        }

        return null;
    }
    /**
     * 为控件设值.
     * value = "fileId@fileName"
     */
    public void setUICValue(Object value) {
        String sValue = StringUtil.nvl(value);
        String[] arr = sValue.split("@");

        this.fileId = null;
        this.fileName = null;
        if (arr.length == 1) {
            fileId = arr[0];
        } else if (arr.length > 1) {
            fileId = arr[0];
            fileName = arr[1];
        }

        this.textComp.setText(fileName);
    }

    public Object getUICValue() {
        if( StringUtil.nvl(this.fileId).equals("") || StringUtil.nvl(this.fileName).equals("")  ){
            return null;
        }else{
            return StringUtil.nvl(this.fileId) + "@" +
                    StringUtil.nvl(this.fileName);
        }
    }

    public boolean isFileEnable(){
        //System.out.println("isFileEnable: [" + this.fileId + "][" + this.fileName + "]" );
        if( StringUtil.nvl(this.fileId).equals("") == true || StringUtil.nvl(this.fileName).equals("") == true  ){
            //System.out.println("return false");
            return false;
        }else{
            //System.out.println("return true");
            return true;
        }
    }

    public void setBounds(int x, int y, int w, int h) {
        if (isBReshap() == true) {
            int newX = Math.max(x, getOffset());
            super.setBounds(newX, y, w - (newX - x), h);
        } else {
            super.setBounds(x, y, w, h);
        }
    }

    public void setEnabled(boolean prm_bValue) {
        this.buttonComp.setEnabled(prm_bValue);
    }

    public void setValidator(Validator validator) {}

    public boolean validateValue() {
        return true;
    }

    public ValidatorResult getValidatorResult() {
        return null;
    }

    public void setErrorField(boolean flag) {}

    public VWJLabel getTextComp() {
        return this.textComp;
    }

    public VWJButton getButtonComp() {
        return this.buttonComp;
    }

    public void setButtonComp(VWJButton button) {
        this.remove(buttonComp);
        this.buttonComp = button;
        this.add(buttonComp);
    }

    public void setDtoClass(Class dtoClass) {}

    public Class getDtoClass() {
        return Object.class;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }


    public boolean isBReshap() {
        return bReshap;
    }

    public String getAcntCode() {
        return acntCode;
    }

    public String getFileId() {
        return fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public String getModule() {
        return module;
    }

    public void setBReshap(boolean bReshap) {
        this.bReshap = bReshap;
    }

    public void setAcntCode(String acntCode) {
        this.acntCode = acntCode;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public int getHorizontalAlignment() {
        return SwingConstants.CENTER;
    }

    public boolean isEnabled() {
        return this.buttonComp.isEnabled();
    }

    public String paramString(){
        return "module[" +module+ "] -- accountCode[" +acntCode+ "]"
            + "\r\n\tfileId[" +fileId+ "] -- fileName[" +fileName+ "]"
            + "\r\nfileUrl[" +getFileUrl()+ "]";
    }

    public IVWComponent duplicate() {
        VwJFileButton buttonCopy = new VwJFileButton(this.module, this.acntCode, showButton);
        return buttonCopy;
    }

    public static void main(String[] args) {

        VwJFileButton btn2 = new VwJFileButton("ESSP");
        btn2.setBounds(100, 150, 200, DefaultComp.TEXT_HEIGHT);

        VWWorkArea workArea = new VWWorkArea();
        workArea.setLayout(null);
        workArea.add(btn2);

        TestPanel.show(workArea);
        btn2.setUICValue("F00001@book.txt");
    }


}
