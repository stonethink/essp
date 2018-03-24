package client.essp.pms.activity.process.guideline;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.common.code.DtoKey;
import c2s.essp.pms.account.DtoAcntKEY;
import c2s.essp.pms.wbs.DtoWbsActivity;
import c2s.essp.pms.wbs.DtoWbsGuideLine;
import client.essp.common.view.VWGeneralWorkArea;
import client.essp.common.view.VWJPanel;
import client.essp.pms.templatePop.VwWbsPopSelect;
import client.framework.model.VMComboBox;
import client.framework.view.vwcomp.VWJCheckBox;
import client.framework.view.vwcomp.VWJComboBox;
import client.framework.view.vwcomp.VWJLabel;
import client.framework.view.vwcomp.VWJTextArea;
import com.wits.util.Parameter;
import com.wits.util.StringUtil;

/**
 * <p>Title: </p>
 *
 * <p>Description: 完整的GuideLine卡片，包含Description组建</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author wuyi
 * @version 1.0
 */
public class VwAcGuideLineUp extends VWGeneralWorkArea {


    private static final String actionAcGLSave = "FWbsGAcGuideLineSaveAction";
    private static final String actionAcGLDescription =
        "FWbsAcGuideLineDescriptionAction";
    private static final String actionAcGLLoad =
        "FWbsAcGuideLineLoadAction";
    private static final String actionGLTemplate = "FWbsGLTemlplateComboAction";
    private static final String actionGLActivity = "FWbsGLActivityComboAction";
    public static final String actionGetQualityFlag =
        "FWbsGLActivityQualityFlagAction";

    private VWJTextArea ekit;

    private JButton btnTemplate;

    DtoWbsActivity dtoActivity = null;
    VwWbsPopSelect popSelect = null;
    VWJPanel vwjPanelUp = new VWJPanel();
    VWJPanel vwjPanelDown = new VWJPanel();

    VWJLabel vwjLabelTemplate = new VWJLabel();
    VWJLabel vwjLabelWbs = new VWJLabel();
    VWJLabel vwjLabelActivity = new VWJLabel();
    VWJLabel vwjLabelIsActivity = new VWJLabel();
    VWJLabel vwjLabelDescription = new VWJLabel();


    VWJComboBox vwjComboBoxTemplate = new VWJComboBox();

    VWJComboBox vwjComboBoxActivity = new VWJComboBox();
    VWJCheckBox vwjCheckBoxIsActivity = new VWJCheckBox();

    private Long inAcntRid;

    private Long inActivityRid;
    private DtoWbsActivity dataBean;


    public VwAcGuideLineUp() {

        try {
            jbInit();
            addUICEvent();

        } catch (Exception e) {

        }
    }

    /**
     * jbInit
     */
    private void jbInit() {

        ekit = new VWJTextArea();
        ekit.setPreferredSize(new Dimension(100, 300));
        this.setLayout(null);

        vwjLabelTemplate.setText("Reference OSP/Template");
        vwjLabelTemplate.setBounds(new Rectangle(14, 26, 142, 20));

        vwjLabelActivity.setText("Reference Activity");
        vwjLabelActivity.setBounds(new Rectangle(329, 26, 119, 20));
        vwjLabelIsActivity.setText("Is Quality Activity");
        vwjLabelIsActivity.setBounds(new Rectangle(657, 26, 122, 20));

        vwjLabelDescription.setText("Description");
        vwjLabelDescription.setBounds(new Rectangle(48, 90, 277, 21));

        vwjComboBoxTemplate.setBounds(new Rectangle(158, 25, 150, 24));

        vwjComboBoxActivity.setBounds(new Rectangle(453, 24, 150, 24));

        vwjCheckBoxIsActivity.setText("");
        vwjCheckBoxIsActivity.setBounds(new Rectangle(615, 26, 30, 20));

        this.add(vwjLabelWbs);
        this.add(vwjCheckBoxIsActivity);
        this.add(vwjComboBoxTemplate);
        this.add(vwjComboBoxActivity);
        this.add(vwjLabelActivity);
        this.add(vwjLabelTemplate);
        this.add(vwjLabelIsActivity);
    }

    /**
     * addUICEvent
     */
    private void addUICEvent() {

        vwjComboBoxTemplate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                templateChanged();
            }

        });

        vwjComboBoxActivity.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                activityChanged();
            }

        });

    }

    //页面刷新－－－－－
    protected void resetUI() {
        addDataToBox();
        clearUI();
        reLoadData(this.inAcntRid, this.inActivityRid);
    }

    //得到查询所依靠的数据--account
    public void setParameter(Parameter param) {
        super.setParameter(param);
        //得到GuideLine所需要的accountid;
        dataBean = (DtoWbsActivity) param.get(DtoKey.DTO);
        String entryFunType = (String) param.get("entryFunType");
        //得到GuideLine所需要的accountid;
        this.inAcntRid = dataBean.getAcntRid();
        this.inActivityRid = dataBean.getActivityRid();
        //从PMS进来，只读
        boolean isOnlyRead = dataBean.isReadonly() || DtoAcntKEY.PMS_ACCOUNT_FUN.equals(entryFunType);;
        if (isOnlyRead) {

            this.vwjComboBoxTemplate.setEnabled(false);
            this.vwjComboBoxActivity.setEnabled(false);
            vwjCheckBoxIsActivity.setEnabled(false);
            this.ekit.setEnabled(false);
        } else {

            this.vwjComboBoxTemplate.setEnabled(true);
            this.vwjComboBoxActivity.setEnabled(true);
            vwjCheckBoxIsActivity.setEnabled(true);
            this.ekit.setEnabled(true);
        }

    }

    /**
     * 查找Activity对应的Guideline,并和界面绑定
     */
    public void reLoadData(Long accountRid, Long activityRid) {

        Long refAcntRid;
        Long refActivityRid;
        String description = "";
        InputInfo inputInfo = new InputInfo();
        inputInfo.setInputObj("accountRid", accountRid);
        inputInfo.setInputObj("activityRid", activityRid);
        inputInfo.setActionId(actionAcGLLoad);

        ReturnInfo returnInfo = this.accessData(inputInfo);
        if (returnInfo.isError()) {
            return ;
        }

        DtoWbsGuideLine dto = (DtoWbsGuideLine) returnInfo.getReturnObj(
            DtoKey.DTO);
        if(dto != null) {
            description = dto.getDescription();
            refAcntRid = dto.getRefAcntRid();
            refActivityRid = dto.getRefActivityRid();
            this.vwjComboBoxTemplate.setUICValue(refAcntRid);
            this.vwjComboBoxActivity.setUICValue(refActivityRid);
        } else {
            this.vwjComboBoxTemplate.setUICValue(null);
            this.vwjComboBoxActivity.setUICValue(null);
        }
        if (accountRid != null && activityRid != null) {
            setIsQualityActivity(accountRid, activityRid);
        }
        ekit.setText(StringUtil.nvl(description));
    }

    private void clearUI() {
        this.vwjCheckBoxIsActivity.setSelected(false);
        this.ekit.setText("");
    }

    /**
     * 设置Activity或引用的Activity是否为Quality Activity
     * @param refActivityRid Long
     */
    private void setIsQualityActivity(Long acntRid, Long activityRid) {
        InputInfo inputInfo = new InputInfo();
        inputInfo.setInputObj("acntRid", acntRid);
        inputInfo.setInputObj("activityRid", activityRid);
        inputInfo.setActionId(actionGetQualityFlag);

        ReturnInfo returnInfo = this.accessData(inputInfo);
        String flag = (String) returnInfo.getReturnObj("flag");

        if (flag != null && flag.equals("1")) {
            this.vwjCheckBoxIsActivity.setSelected(true);
        } else {
            this.vwjCheckBoxIsActivity.setSelected(false);
        }
    }


    /**
     * addDataToBox
     */
    private void addDataToBox() {
        //调用这个类的函数，将数据送到combo中
        VMComboBox comboTemplate = new VMComboBox(this.listTemplate());
        comboTemplate.addNullElement();
        vwjComboBoxTemplate.setVMComboBox(comboTemplate);
    }

    /**
     * 列出所有的模版或OSP
     * @return Object
     */
    private Vector listTemplate() {
        Vector v = new Vector();
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(actionGLTemplate);
        ReturnInfo returnInfo = accessData(inputInfo);
        v = (Vector) returnInfo.getReturnObj("comboTemplate");
        return v;

    }
    /**
     * 列出所选模版或OSP的Activity
     * @return Object
     */
    private Vector listActivity() {
        Vector v = new Vector();
        if(vwjComboBoxTemplate.getUICValue() != null) {
            InputInfo inputInfo = new InputInfo();
            inputInfo.setInputObj("selectedTemplate",
                                  vwjComboBoxTemplate.getUICValue());
            inputInfo.setActionId(actionGLActivity);
            ReturnInfo returnInfo = accessData(inputInfo);
            v = (Vector) returnInfo.getReturnObj("comboActivity");
        }
        return v;
    }

    //ComboBox事件处理
    //选择template改变的时候，通过查询得到的新的数据，用这个新的数据来填充vwjComboBoxActivity
    public void templateChanged() {
        VMComboBox comboActivity = new VMComboBox(this.listActivity());
        vwjComboBoxActivity.setVMComboBox(comboActivity);
        //触发Activity改变事件
        activityChanged();
    }

    /**
     * 引用的Activity改变时，查找引用的Description
     * @param e ActionEvent
     */
    private void activityChanged() {
        if (this.vwjComboBoxActivity.getUICValue() != null) {
            setOnlyDescription();
        } else {
            this.ekit.setText("");
        }
    }

    private void setOnlyDescription() {
        Long acntRid;
        Long aactivityRid;
        String description = "";
        acntRid = (Long)this.vwjComboBoxTemplate.getUICValue();
        aactivityRid = (Long)this.vwjComboBoxActivity.getUICValue();
        setIsQualityActivity(acntRid, aactivityRid);

        InputInfo inputInfo = new InputInfo();
        inputInfo.setInputObj("accountRid", acntRid);
        inputInfo.setInputObj("activityRid", aactivityRid);
        inputInfo.setActionId(actionAcGLDescription);

        ReturnInfo returnInfo = this.accessData(inputInfo);
        description = (String) returnInfo.getReturnObj(
            "description");
        this.ekit.setText(description);
    }


    public void actionPerformedSave() {
        //得到用户所选定的内容，准备存储
        DtoWbsGuideLine dto = new DtoWbsGuideLine();

        //this.vwjCheckBoxIsActivity.getUICValue();
        Long refAcntRid = (Long) vwjComboBoxTemplate.getUICValue();
        Long refActivityRid = (Long) vwjComboBoxActivity.getUICValue();

        dto.setRefAcntRid(refAcntRid);
        dto.setRefActivityRid(refActivityRid);
        dto.setAcntRid(this.inAcntRid);
        dto.setActivityRid(this.inActivityRid);
        if (vwjCheckBoxIsActivity != null && vwjCheckBoxIsActivity.isSelected()) {
            dto.setIsQuality("1");
        } else {
            dto.setIsQuality("0");
        }

        String description = this.ekit.getText();
        dto.setDescription(description);

        InputInfo inputInfo = new InputInfo();
        inputInfo.setInputObj("guideLineInfo", dto);
        inputInfo.setActionId(actionAcGLSave);

        this.accessData(inputInfo);
    }
    //刷新界面
    public void actionPerformedLoad() {
        this.resetUI();
    }

    /**
     * 取得父窗口句柄
     * @return Frame
     */
    protected Container getMyParentWindow() {
        java.awt.Container c = this.getParent();

        while (c != null) {
            if ((c instanceof java.awt.Frame) || (c instanceof java.awt.Dialog)) {
                return c;
            }

            c = c.getParent();
        }

        return null;
    }


    public VWJTextArea getEkit() {
        return ekit;
    }
    public VWJComboBox getVwjComboBoxActivity() {
        return vwjComboBoxActivity;
    }

    public VWJComboBox getVwjComboBoxTemplate() {
        return vwjComboBoxTemplate;
    }

    public VWJCheckBox getVwjCheckBoxIsActivity() {
        return vwjCheckBoxIsActivity;
    }
}
