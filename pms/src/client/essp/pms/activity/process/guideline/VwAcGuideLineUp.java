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
 * <p>Description: ������GuideLine��Ƭ������Description�齨</p>
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

    //ҳ��ˢ�£���������
    protected void resetUI() {
        addDataToBox();
        clearUI();
        reLoadData(this.inAcntRid, this.inActivityRid);
    }

    //�õ���ѯ������������--account
    public void setParameter(Parameter param) {
        super.setParameter(param);
        //�õ�GuideLine����Ҫ��accountid;
        dataBean = (DtoWbsActivity) param.get(DtoKey.DTO);
        String entryFunType = (String) param.get("entryFunType");
        //�õ�GuideLine����Ҫ��accountid;
        this.inAcntRid = dataBean.getAcntRid();
        this.inActivityRid = dataBean.getActivityRid();
        //��PMS������ֻ��
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
     * ����Activity��Ӧ��Guideline,���ͽ����
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
     * ����Activity�����õ�Activity�Ƿ�ΪQuality Activity
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
        //���������ĺ������������͵�combo��
        VMComboBox comboTemplate = new VMComboBox(this.listTemplate());
        comboTemplate.addNullElement();
        vwjComboBoxTemplate.setVMComboBox(comboTemplate);
    }

    /**
     * �г����е�ģ���OSP
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
     * �г���ѡģ���OSP��Activity
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

    //ComboBox�¼�����
    //ѡ��template�ı��ʱ��ͨ����ѯ�õ����µ����ݣ�������µ����������vwjComboBoxActivity
    public void templateChanged() {
        VMComboBox comboActivity = new VMComboBox(this.listActivity());
        vwjComboBoxActivity.setVMComboBox(comboActivity);
        //����Activity�ı��¼�
        activityChanged();
    }

    /**
     * ���õ�Activity�ı�ʱ���������õ�Description
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
        //�õ��û���ѡ�������ݣ�׼���洢
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
    //ˢ�½���
    public void actionPerformedLoad() {
        this.resetUI();
    }

    /**
     * ȡ�ø����ھ��
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
