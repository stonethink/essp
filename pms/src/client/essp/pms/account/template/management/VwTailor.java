package client.essp.pms.account.template.management;

import client.essp.common.view.VWGeneralWorkArea;
import java.awt.*;
import client.framework.view.vwcomp.VWJLabel;
import client.framework.view.vwcomp.VWJTextArea;
import com.wits.util.Parameter;
import c2s.dto.ReturnInfo;
import client.essp.common.view.VWWorkArea;
import com.wits.util.IVariantListener;
import java.awt.event.ActionEvent;
import c2s.dto.InputInfo;
import com.wits.util.TestPanel;
import com.hexidec.ekit.EkitPanel;
import java.awt.event.ActionListener;

public class VwTailor extends VWGeneralWorkArea implements
    IVariantListener {

    private static final String actionGetDescription =
        "FAccountTailorDescriptionAction";
    private Parameter para;
    private VWJTextArea ekit;
    private boolean isParameterValid;

    VWJLabel vwjLabelDescription = new VWJLabel();


    public VwTailor() {

        try {
            jbInit();
            addUICEvent();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * jbInit
     */
    private void jbInit() {
        para = new Parameter();
        this.setPreferredSize(new Dimension(700, 470));
        ekit = new VWJTextArea();
//        ekit.setPreferredSize(new Dimension(100, 300));
        ekit.setBounds(1, 30, 600, 300);
        this.setLayout(null);
        ekit.setEnabled(false);
        this.add(ekit);

        vwjLabelDescription.setText("Description：");
        vwjLabelDescription.setBounds(new Rectangle(1, 1, 200, 25));
        this.add(vwjLabelDescription);
    }

    /**
     * addUICEvent
     */
    private void addUICEvent() {
//        this.getButtonPanel().addSaveButton(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                actionPerformedSave();
//            }
//        }
//        );
//        this.getButtonPanel().addLoadButton(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                resetUI();
//            }
//
//        });
    }


    //得到查询所依靠的数据--account
    public void setParameter(Parameter param) {
        super.setParameter(param);
        if (this.para == null) {
            this.para = new Parameter();
        }
        this.para = param;
    }

    public void refresh() {
        resetUI();
    }

    public void resetUI() {
        Long templateRid = (Long)this.para.get("acntrid");
        if (templateRid == null) {
            this.ekit.setText("");
            para.put("TailorDescription", "");
            return;
        } else {
            InputInfo inputInfo = new InputInfo();
            inputInfo.setInputObj("acntRid", templateRid);
            inputInfo.setActionId(actionGetDescription);

            ReturnInfo returnInfo = this.accessData(inputInfo);
            String s = (String) returnInfo.getReturnObj("description");
            if (s != null) {
                this.ekit.setText(s);
                para.put("TailorDescription", s);
            }
            if (s == null || s == "") {
                this.ekit.setText("");
                para.put("TailorDescription", "");
            }
        }
    }

    public void actionPerformedSave() {
        //得到用户所选定的内容，准备存储
        para.put("TailorDescription", this.ekit.getText());
    }

    public static void main(String args[]) {

        VWWorkArea workArea = new VWWorkArea();
        VwTailor tree = new VwTailor();
        workArea.addTab("Template", tree);
        workArea.setPreferredSize(new Dimension(250, 500));
        TestPanel.show(workArea);

        workArea.refreshWorkArea();
    }

    public void dataChanged(String string, Object object) {
    }

    public VWJTextArea getEkit() {
        return ekit;
    }
}
