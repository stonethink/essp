package client.essp.pms.account.tailor;


import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.pms.account.DtoPmsAcnt;
import client.essp.common.view.VWGeneralWorkArea;
import client.framework.view.vwcomp.VWJLabel;
import com.hexidec.ekit.EkitPanel;
import com.wits.util.IVariantListener;
import com.wits.util.Parameter;
import java.awt.FlowLayout;
import client.essp.common.view.VWWorkArea;
import com.wits.util.TestPanel;
import java.awt.BorderLayout;
import client.framework.view.vwcomp.VWJTextArea;

/**
 * <p>Title: </p>
 *
 * <p>Description: 完整的GuideLine卡片上面的部分，绑定Description组建</p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author wuyi
 * @version 1.0
 */
public class VwTailor extends VWGeneralWorkArea implements
    IVariantListener {

    private static final String actionSave = "FAccountTailorSaveAction";
    private static final String actionGetDescription =
        "FAccountTailorDescriptionAction";

    private VWJTextArea ekit;

    private Long acntRid;
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

        this.setPreferredSize(new Dimension(700, 470));
        this.setLayout(new BorderLayout());
        ekit = new VWJTextArea();
//        ekit.setPreferredSize(new Dimension(600, 300));
//        ekit.setBounds(1, 30, 600, 300);

        this.add(ekit);



    }


    /**
     * addUICEvent
     */
    private void addUICEvent() {
        this.getButtonPanel().addSaveButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedSave();
            }
        }
        );
        this.getButtonPanel().addLoadButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resetUI();
            }

        });
    }

    //得到查询所依靠的数据--account
    public void setParameter(Parameter param) {
        super.setParameter(param);
        //得到GuideLine所需要的accountid;
        this.acntRid = (Long) (param.get("acntRid"));

    }


    public void actionPerformedSave() {
        //得到用户所选定的内容，准备存储
        DtoPmsAcnt dto = new DtoPmsAcnt();

        String description = this.ekit.getText();
//            dto.setDescription(description);

        InputInfo inputInfo = new InputInfo();
        inputInfo.setInputObj("acntRid", acntRid);
        inputInfo.setInputObj("description", description);
        inputInfo.setActionId(actionSave);

        //测试
        System.out.print(description + "保存中：）\n");

        ReturnInfo returnInfo = this.accessData(inputInfo);

    }


    //重新设置description
    public void setDescription(Long accoutRid) {

        InputInfo inputInfo = new InputInfo();
        inputInfo.setInputObj("acntRid", accoutRid);
        inputInfo.setActionId(actionGetDescription);

        ReturnInfo returnInfo = this.accessData(inputInfo);
        String s = (String) returnInfo.getReturnObj("description");
        if (s != null) {
            this.ekit.setText(s);
        }
        if (s == null || s == "") {
//            JOptionPane.showMessageDialog(null, "No description");
            this.ekit.setText("");
        }
    }


    protected void resetUI() {
        setDescription(this.acntRid);
    }


    public static void main(String args[]) {

//        VWWorkArea workArea = new VWWorkArea();
//        VwTailor tree = new VwTailor();
//        workArea.addTab("Template", tree);
//        workArea.setPreferredSize(new Dimension(250, 500));
//        TestPanel.show(workArea);
//
//        workArea.refreshWorkArea();
    }

    public void dataChanged(String string, Object object) {
    }

    public VWJTextArea getEkit() {
        return ekit;
    }
}
