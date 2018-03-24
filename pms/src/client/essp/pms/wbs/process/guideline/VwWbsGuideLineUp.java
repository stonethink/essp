package client.essp.pms.wbs.process.guideline;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

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
import client.framework.view.vwcomp.VWJComboBox;
import client.framework.view.vwcomp.VWJLabel;
import client.framework.view.vwcomp.VWJTextArea;
import com.wits.util.Parameter;
import com.wits.util.StringUtil;
import c2s.dto.DtoComboItem;

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
public class VwWbsGuideLineUp extends VWGeneralWorkArea {

    private static final String actionSave = "FWbsGuideLineSaveAction";
    private static final String actionGLDescription =
        "FWbsGuideLineDescriptionAction";
    private static final String actionGLLoad =
        "FWbsGuideLineLoadAction";
    private static final String actionGLTemplate = "FWbsGLTemlplateComboAction";
    private static final String actionGLWbs = "FWbsGLWbsComboAction";
    private static final String actionCopyCheckPoint =
        "FWbsGLCopyCheckPointAction";


    private VWJTextArea ekit;

    DtoWbsActivity dtoActivity = null;

    VwWbsPopSelect popSelect = null;
    VWJPanel vwjPanelUp = new VWJPanel();
    VWJPanel vwjPanelDown = new VWJPanel();

    VWJLabel vwjLabelTemplate = new VWJLabel();
    VWJLabel vwjLabelWbs = new VWJLabel();
    VWJLabel vwjLabelDescription = new VWJLabel();

    VWJComboBox vwjComboBoxTemplate = new VWJComboBox();
    VWJComboBox vwjComboBoxWbs = new VWJComboBox();

    private Long inAcntRid;
    private Long inWbsRid;

    public VwWbsGuideLineUp() {

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
        vwjLabelTemplate.setBounds(new Rectangle(20, 23, 150, 20));
        vwjLabelWbs.setText("Reference WBS");
        vwjLabelWbs.setBounds(new Rectangle(401, 24, 96, 20));

        vwjLabelDescription.setText("Description");
        vwjLabelDescription.setBounds(new Rectangle(48, 90, 277, 21));

        vwjComboBoxTemplate.setBounds(new Rectangle(172, 23, 150, 24));
        vwjComboBoxWbs.setBounds(new Rectangle(508, 23, 150, 24));

        addDataToBox();

        this.add(vwjComboBoxTemplate);
        this.add(vwjComboBoxWbs);
        this.add(vwjLabelTemplate);
        this.add(vwjLabelWbs);

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

        vwjComboBoxWbs.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                wbsChanged();
            }
        });
    }

    //得到查询所依靠的数据--account
    public void setParameter(Parameter param) {
        super.setParameter(param);
        DtoWbsActivity dataBean = (DtoWbsActivity) param.get(DtoKey.DTO);
        String entryFunType = (String) param.get("entryFunType");
        //得到GuideLine所需要的accountid;
        this.inAcntRid = dataBean.getAcntRid();
        this.inWbsRid = dataBean.getWbsRid();
        //从PMS进来时，不能修该卡片
        boolean isOnlyRead = dataBean.isReadonly() ||
                             DtoAcntKEY.PMS_ACCOUNT_FUN.equals(entryFunType);
        ;

        if (isOnlyRead) {
            this.vwjComboBoxTemplate.setEnabled(false);
            this.vwjComboBoxWbs.setEnabled(false);
            this.ekit.setEnabled(false);
        } else {
            this.vwjComboBoxTemplate.setEnabled(true);
            this.vwjComboBoxWbs.setEnabled(true);
            this.ekit.setEnabled(true);
        }

    }


    /**
     * 获取template/OSP和Wbs下拉框内容
     */
    private void addDataToBox() {
        //调用这个类的函数，将数据送到combo中
        VMComboBox comboTemplate = new VMComboBox(this.listTemplate());
        DtoComboItem nullItem = comboTemplate.addNullElement();
        vwjComboBoxTemplate.setVMComboBox(comboTemplate);
        vwjComboBoxTemplate.setSelectedItem(nullItem);

        VMComboBox comboWbs = new VMComboBox(this.listWbs());
        vwjComboBoxWbs.setVMComboBox(comboWbs);

    }


    /**
     * 列出所有的模版
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
     * 列出所选Template/OSP对应的WBS
     * @return Vector
     */
    private Vector listWbs() {
        Object templateRid = this.vwjComboBoxTemplate.getUICValue();
        if (templateRid == null) {
            return new Vector();
        }
        InputInfo inputInfo = new InputInfo();
        inputInfo.setInputObj("selectedTemplate", templateRid);
        inputInfo.setActionId(actionGLWbs);
        ReturnInfo returnInfo = accessData(inputInfo);
        Vector v = (Vector) returnInfo.getReturnObj("comboWbs");

        return v;
    }

    public void actionPerformedSave() {
        //得到用户所选定的内容，准备存储
        DtoWbsGuideLine dto = new DtoWbsGuideLine();

        Long refAcntRid = (Long) vwjComboBoxTemplate.getUICValue();
        Long refWbsRid = (Long) vwjComboBoxWbs.getUICValue();
        dto.setRefAcntRid(refAcntRid);
        dto.setRefWbsRid(refWbsRid);
        dto.setAcntRid(this.inAcntRid);
        dto.setWbsRid(this.inWbsRid);

        String description = this.ekit.getText();
        dto.setDescription(description);

        InputInfo inputInfo = new InputInfo();
        inputInfo.setInputObj("guideLineInfo", dto);
        inputInfo.setActionId(actionSave);

        this.accessData(inputInfo);
    }

    public void actionPerformedLoad() {
        this.resetUI();
    }

    //ComboBox事件处理
    //选择template改变的时候，通过查询得到的新的数据，用这个新的数据来填充vwjComboBoxActivity
    public void templateChanged() {
        VMComboBox comboWbs = new VMComboBox(this.listWbs());
        vwjComboBoxWbs.setVMComboBox(comboWbs);

        //触发Wbs改变事件
        wbsChanged();
    }

    /**
     * 改变参考的WBS时，查找对应的Reference Description
     * @param e ActionEvent
     */
    public void wbsChanged() {
        if (this.vwjComboBoxWbs.getUICValue() != null) {
            setOnlyDescription();
        } else {
            this.ekit.setText("");
        }
    }

    /**
     * setOnlyDescription
     */
    public void setOnlyDescription() {
        Long acntRid;
        Long awbsRid;

        acntRid = (Long)this.vwjComboBoxTemplate.getUICValue();
        awbsRid = (Long)this.vwjComboBoxWbs.getUICValue();

        String desc = getWbsGuidelinDescription(acntRid, awbsRid);
        this.ekit.setText(desc);
    }

    /**
     * 查找当前WBS的Guideline信息,并跟界面绑定
     * @param accoutRid Long
     * @param awbsRid Long
     * @return boolean
     */
    private boolean reloadData(Long accoutRid, Long awbsRid) {
        Long refAcntRid;
        Long refWbsRid;
        String description;

        DtoWbsGuideLine dto = getWbsGuideline(accoutRid,awbsRid);
        if (dto == null) {
            this.vwjComboBoxTemplate.setUICValue(null);
            this.vwjComboBoxWbs.setUICValue(null);
            return true;
        }

        description = dto.getDescription();
        refAcntRid = dto.getRefAcntRid();
        refWbsRid = dto.getRefWbsRid();
        this.vwjComboBoxTemplate.setUICValue(refAcntRid);
        this.vwjComboBoxWbs.setUICValue(refWbsRid);
        this.ekit.setText(StringUtil.nvl(description));
        return true;
    }

    //页面刷新－－－－－
    protected void resetUI() {
        addDataToBox();
        this.ekit.setText("");
        reloadData(this.inAcntRid, this.inWbsRid);
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

    /**
     * 根据WBS和AcntRid查找其Guideline数据
     * @param acntRid Long
     * @param awbsRid Long
     * @return DtoWbsGuideLine
     */
    private DtoWbsGuideLine getWbsGuideline(Long acntRid, Long awbsRid) {
         InputInfo inputInfo = new InputInfo();
         inputInfo.setInputObj("accountRid", acntRid);
         inputInfo.setInputObj("wbsRid", awbsRid);
         inputInfo.setActionId(actionGLLoad);

         ReturnInfo returnInfo = this.accessData(inputInfo);

        return (DtoWbsGuideLine) returnInfo.getReturnObj(
             "dto");
    }

    /**
     * 根据WBS和AcntRid查找其Guideline Description数据
     * @param acntRid Long
     * @param awbsRid Long
     * @return DtoWbsGuideLine
     */
    private String getWbsGuidelinDescription(Long acntRid, Long awbsRid) {
         InputInfo inputInfo = new InputInfo();
         inputInfo.setInputObj("accountRid", acntRid);
         inputInfo.setInputObj("wbsRid", awbsRid);
         inputInfo.setActionId(this.actionGLDescription);

         ReturnInfo returnInfo = this.accessData(inputInfo);

        return (String) returnInfo.getReturnObj(
             "description");
    }


    public VWJTextArea getEkit() {
       return ekit;
   }
   public VWJComboBox getVwjComboBoxTemplate() {
       return vwjComboBoxTemplate;
   }
   public VWJComboBox getVwjComboBoxWbs() {
       return vwjComboBoxWbs;
   }

}
