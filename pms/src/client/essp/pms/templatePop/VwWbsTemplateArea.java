package client.essp.pms.templatePop;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import client.essp.common.TableUitl.TableColumnChooseDisplay;
import client.essp.common.view.VWGeneralWorkArea;
import client.framework.model.VMComboBox;
import client.framework.view.vwcomp.VWJComboBox;
import com.wits.util.Parameter;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright:显示在弹出窗口的关于template的区域包含一个可选择的ComBobox和一个树 Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author wuyi
 * @version 1.0
 */
public class VwWbsTemplateArea extends VWGeneralWorkArea {
    private VWJComboBox selectTemplate;
    private VwWbsTemplateTree tree;
    JButton expandBtn = null;
    JButton detailBtn = null;
    JButton columnBtn = null;

    public VwWbsTemplateArea() {
        selectTemplate = new VWJComboBox();
        tree = new VwWbsTemplateTree();
        this.add(selectTemplate, BorderLayout.NORTH);
        this.add(tree, BorderLayout.CENTER);

        addUICEvent();

        addDataToBox();

    }

    protected void addUICEvent() {

        //另一中监视ComboBox的方法
        selectTemplate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                select(e);
            }

        });
        //detail button
        detailBtn = this.getButtonPanel().addButton("detail.png");
        detailBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //执行弹出窗口，弹出详细的WBS或Activity的卡片框
                tree.popDetailWindow();
            }
        });
       detailBtn.setToolTipText("show detail");
        //Display
        TableColumnChooseDisplay chooseDisplay = new TableColumnChooseDisplay(tree.getTreeTable(), tree);
        columnBtn = chooseDisplay.getDisplayButton();
        getButtonPanel().addButton(columnBtn);
        columnBtn.setToolTipText("display");

        expandBtn = this.getButtonPanel().addButton("expand.png");
        expandBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tree.getTreeTable().expandsRow();
            }
        });
        expandBtn.setToolTipText("expand");

        this.getButtonPanel().addLoadButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                select(e);
            }
        });
    }


    /**
     * addDataToBox
     */
    private void addDataToBox() {
        //调用这个类的函数，将数据送到combo中
        VMComboBox comboTemplate = new VMComboBox(this.listTemplate());
        selectTemplate.setVMComboBox(comboTemplate);
        if (selectTemplate.getItemCount() > 0) {
            selectTemplate.setSelectedIndex(0);
        }
    }


    /**
     * 用来形成对3个ComboBox填充的数据，3个ComboBox相互依存!!
     *
     * @return Object
     */
    private Vector listTemplate() {
        Vector v = new Vector();
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId("FWbsGLPopTemlplateComboAction");

        ReturnInfo returnInfo = accessData(inputInfo);
        v = (Vector) returnInfo.getReturnObj("comboTemplate");

        return v;
    }

    public void select(ActionEvent e) {
        Parameter param = new Parameter();

        Long s = (Long) selectTemplate.getUICValue();
        param.put("inAcntRid", s);
        this.tree.setParameter(param);
        tree.refreshWorkArea();
        tree.getTreeTable().expandRow(0);

    }

    public void refreshWorkArea() {
        this.tree.refreshWorkArea();
    }


//    public static void main(String args[]) {
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
//
//        VWWorkArea workArea = new VWWorkArea();
//        VwWbsTemplateArea tree = new VwWbsTemplateArea();
//        workArea.addTab("Template", tree);
//        TestPanel.show(workArea);
//
//        workArea.refreshWorkArea();
//    }


}
