package client.essp.common.code.util;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

import client.essp.common.view.VWWorkArea;
import client.framework.view.vwcomp.IVWComponent;
import client.framework.view.vwcomp.IVWPopupEditorEvent;
import client.framework.view.vwcomp.VWJButton;
import client.framework.view.vwcomp.VWJPopupEditor;
import client.image.ComImage;
import com.wits.util.Parameter;
import com.wits.util.StringUtil;

public class VwJCodeButton extends VWJButton {
    public final static String PARAM_WORKAREA = "paramWorkArea";
    public final static String PARAM_TITLE = "paramTitle";
    public final static String PARAM_ICON = "paramIcon";
    public final static String DEFAULT_ICON = "edit.png";

    JFrame parent;
    VWWorkArea popupWorkArea = null;

    Parameter param = new Parameter();
    String popupWorkAreaClassName = null;
    String title;
    String iconName;

    public VwJCodeButton() {
        this(null);
    }

    public VwJCodeButton(JFrame parent) {
        this.parent = parent;
        iconName = DEFAULT_ICON;

        jbInit();
    }

    private void jbInit() {
        Icon img = new ImageIcon(ComImage.getImage(iconName));
        this.setIcon(img);

        this.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedThis(e);
            }
        });
    }

    public void actionPerformedThis(ActionEvent e) {
        if (popupWorkArea == null) {
            popupWorkArea = createWorkArea(popupWorkAreaClassName);

            if( popupWorkArea == null ){
                return;
            }else{
            }
        }
        popupWorkArea.setParameter(this.param);
        popupWorkArea.refreshWorkArea();

        VWJPopupEditor popupEditor = new VWJPopupEditor(parent, title,
            popupWorkArea,
            new IVWPopupEditorEvent() {
            public boolean onClickOK(ActionEvent e) {
                popupWorkArea.saveWorkArea();
                return popupWorkArea.isSaveOk();
            }

            public boolean onClickCancel(ActionEvent e) {
                return true;
            }
        });

        popupEditor.show();
    }

    /**
     * 1.参数value应该为Parameter类型
     * 2.其中有一部分参数给本button
     *      PARAM_WORKAREA = "paramWorkArea";
     *      PARAM_TITLE = "paramTitle";
     *      PARAM_ICON = "paramIcon";
     * 3.另一部分给弹出的workArea
     *
     * @param value Object
     */
    public void setUICValue(Object value) {
        if( value != null && value instanceof Parameter ){
            this.param = (Parameter)value;

            String className = StringUtil.nvl(param.get(PARAM_WORKAREA));
            setWorkArea(className);

            String imgName = StringUtil.nvl( param.get(PARAM_ICON));
            setIcon(imgName);

            this.title = StringUtil.nvl(param.get(PARAM_TITLE));
        }
    }

    public void setIcon(String imgName ){
        if ( imgName.equals("") == false && this.iconName.equals(imgName) == false) {
            iconName = imgName;
            Icon img = new ImageIcon(ComImage.getImage(iconName));
            this.setIcon(img);
        }
    }

    public void setWorkArea(String className ){
        if (className.equals(this.popupWorkAreaClassName) == false) {
            popupWorkAreaClassName = className;
            this.popupWorkArea = null;
        }
    }

    public void setWorkArea(String className,VWWorkArea workArea ){
        if (className.equals(this.popupWorkAreaClassName) == false) {
            popupWorkAreaClassName = className;
            this.popupWorkArea = workArea;
        }
    }

    private static VWWorkArea createWorkArea(String className) {
        Object obj = null;

        try {
            if (("").equals(className)) {
                throw new RuntimeException(
                    "Error in VwJCodeButton.createWorkArea(null) - cannot be 'null'");
            } else {
                Class clazz = Class.forName(className);
                obj = clazz.newInstance();
            }

            if (obj instanceof VWWorkArea) {
                return (VWWorkArea) obj;
            } else {
                throw new RuntimeException("The class -[" + className +
                                           "] not be instance of VWWorkArea.");
            }
        } catch (Exception ex) {
            System.out.println("Exception in VwJCodeButton.createWorkArea(" +
                               className + ")");
            System.out.println("[Error:]" + ex.getMessage());

            return null;
        }
    }

    public IVWComponent duplicate(){
        VwJCodeButton button = new VwJCodeButton();
        button.setText(this.getText());
        button.setIcon(this.getIcon());
        button.setVerticalAlignment(this.getVerticalAlignment());
        button.setPreferredSize(new Dimension(20,20));

        button.setParent(this.parent);
        button.popupWorkArea = this.popupWorkArea;

        return button;
    }

    public static void main(String[] args){
    }

    public void setParent(JFrame parent) {
        this.parent = parent;
    }
}
