package client.essp.pms.activity.process.guideline;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

import c2s.essp.common.code.DtoKey;
import c2s.essp.pms.account.DtoAcntKEY;
import c2s.essp.pms.wbs.DtoWbsActivity;
import client.essp.common.view.VWJPanel;
import client.essp.common.view.VWTDWorkArea;
import client.essp.pms.templatePop.VwWbsPopSelect;
import client.framework.view.vwcomp.VWJLabel;
import com.wits.util.Parameter;
import java.awt.event.MouseAdapter;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author wuyi
 * @version 1.0
 */
public class VwAcGuideLine extends VWTDWorkArea {
    VwAcGuideLineUp upGuideLine;
    VWJPanel vwjPanelDown = new VWJPanel();
    VWJLabel vwjLabelDescription = new VWJLabel();
    public JButton btnTemplate;
    public JButton btnSave;
    boolean isReadOnly = true;


    public VwAcGuideLine() {
        super(70);
        try {
            jbInit();
            addUICEvent();
        } catch (Exception e) {

        }
        (new AcTemplateDropTarget(this)).create();

    }

    /**
     * jbInit
     */
    private void jbInit() {
        this.setPreferredSize(new Dimension(700, 470));
        upGuideLine = new VwAcGuideLineUp();

        vwjLabelDescription.setText("Description");
        vwjLabelDescription.setBounds(new Rectangle(48, 90, 277, 21));
        vwjPanelDown.add(vwjLabelDescription, BorderLayout.NORTH);

        vwjPanelDown.add(upGuideLine.getEkit(), BorderLayout.CENTER);

        this.getTopArea().add(upGuideLine);
        this.getDownArea().add(vwjPanelDown);
    }

    public void setParameter(Parameter param) {
        DtoWbsActivity dataBean = (DtoWbsActivity) param.get(DtoKey.DTO);
        String entryFunType = (String) param.get("entryFunType");
        this.upGuideLine.setParameter(param);
        //从PMS功能进来，只读
        isReadOnly = dataBean.isReadonly() || DtoAcntKEY.PMS_ACCOUNT_FUN.equals(entryFunType);

        if (isReadOnly) {
            if(btnTemplate != null) {
                this.btnTemplate.setVisible(false);
            }
            this.getButtonPanel().setButtonVisible(0, false);
        } else {
            if(btnTemplate != null) {
                this.btnTemplate.setVisible(true);
            }
            this.getButtonPanel().setButtonVisible(0, true);
        }

    }

    public void refreshWorkArea() {
        upGuideLine.refreshWorkArea();
    }

    public void addUICEvent() {
        btnTemplate = this.getButtonPanel().addButton("applyTemplate.png");
        //给template按钮增加鼠标监听，使点击该按钮以后弹出template选择对话框
        btnTemplate.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                popTemplate(e);
            }
        });
        btnTemplate.setToolTipText("apply template");

        btnSave = this.getButtonPanel().addSaveButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                upGuideLine.actionPerformedSave();
            }
        });
        this.getButtonPanel().addLoadButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                upGuideLine.actionPerformedLoad();
            }
        });
    }

    public void popTemplate(MouseEvent e) {
        if (upGuideLine.popSelect == null) {
            Container c = upGuideLine.getMyParentWindow();
            upGuideLine.popSelect = VwWbsPopSelect.createInstance( c);
        } else {
            upGuideLine.popSelect.showPopSelect();
        }
    }

    public VwAcGuideLineUp getUpGuideLine() {
        return upGuideLine;
    }

}
