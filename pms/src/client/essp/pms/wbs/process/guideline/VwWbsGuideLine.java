package client.essp.pms.wbs.process.guideline;

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
 * <p>Description:装载一个VwWbsGuideLine和一个与VwWbsGuideLine绑定了的 EkitPanel</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author wuyi
 * @version 1.0
 */
public class VwWbsGuideLine extends VWTDWorkArea {
    VwWbsGuideLineUp upGuideLine;
    VWJPanel vwjPanelDown = new VWJPanel();
    VWJLabel vwjLabelDescription = new VWJLabel();
    public JButton btnTemplate;
    public JButton btnSave;
     boolean isReadOnly;

    public VwWbsGuideLine() {
        super(70);
        try {
            jbInit();
            addUICEvent();

        } catch (Exception e) {

        }
        (new WbsTemplateDropTarget(this)).create();

    }

    /**
     * jbInit
     */
    private void jbInit() {
        this.setPreferredSize(new Dimension(700, 470));
        upGuideLine = new VwWbsGuideLineUp();

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
        }
        );

//        btnTemplate = this.getButtonPanel().addButton("applyTemplate.png");
//        btnTemplate.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                upGuideLine.actionPerformedSelect();
//            }
//        });

        //给template按钮增加鼠标监听，使点击该按钮以后弹出template选择对话框
//        btnTemplate.addMouseListener(new MouseAdapter() {
//            public void mouseClicked(MouseEvent e) {
//                popTemplate(e);
//            }
//        });

        this.getButtonPanel().addLoadButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                upGuideLine.actionPerformedLoad();
            }
        }
        );

        upGuideLine.vwjComboBoxTemplate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                upGuideLine.templateChanged();
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

//    public static void main(String args[]) {
//
//        Global.todayDateStr = "2005-12-03";
//        Global.todayDatePattern = "yyyy-MM-dd";
//        Global.userId = "stone.shi";
//        DtoUser user = new DtoUser();
//        user.setUserID(Global.userId);
//        user.setUserLoginId(Global.userId);
//        HttpServletRequest request = new HttpServletRequestMock();
//        HttpSession session = request.getSession();
//        session.setAttribute(DtoUser.SESSION_USER, user);
//
//        VWWorkArea workArea = new VWWorkArea();
//        VwWbsGuideLine tree = new VwWbsGuideLine();
//        workArea.add(tree);
//        workArea.setPreferredSize(new Dimension(250, 500));
//        TestPanel.show(workArea);
//
//        workArea.refreshWorkArea();
//    }

    public VwWbsGuideLineUp getUpGuideLine() {
        return upGuideLine;
    }

}
