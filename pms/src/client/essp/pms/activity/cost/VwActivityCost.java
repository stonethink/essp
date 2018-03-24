package client.essp.pms.activity.cost;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import c2s.dto.ITreeNode;
import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.pms.activity.DtoActivityCost;
import c2s.essp.pms.wbs.DtoKEY;
import c2s.essp.pms.wbs.DtoWbsActivity;
import client.essp.common.view.VWTableWorkArea;
import client.essp.common.view.VWWorkArea;
import client.framework.model.VMColumnConfig;
import client.framework.model.VMTable2;
import client.framework.view.vwcomp.VWJReal;
import client.framework.view.vwcomp.VWJTable;
import client.framework.view.vwcomp.VWJText;
import com.wits.util.Parameter;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class VwActivityCost extends VWTableWorkArea {
    static final String actionIdActivityCostGet =
        "FWbsActivityCostGetAction";
    DtoActivityCost activityCost;
    ITreeNode node;
    public VwActivityCost() {
        try {
            jbInit();
            addUICEvent();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void addUICEvent() {
        this.getButtonPanel().addLoadButton(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                actionPerformedLoad(event);
            }
        });
    }


    protected void jbInit() throws Exception {
        //设置标题栏位这是一个构造java里的表的标题的必须有的个对象组，可以看相关的文档

        VWJReal inputReal = new VWJReal();
        inputReal.setMaxInputDecimalDigit(2);
        inputReal.setMaxInputIntegerDigit(8);
        inputReal.setCanNegative(true);

        Object[][] configs = new Object[][] { {"JobCode", "jobCode",
                             VMColumnConfig.UNEDITABLE, new VWJText()},
                             {"Price", "price",
                             VMColumnConfig.UNEDITABLE,
                             inputReal}, {"Budget PH",
                             "budgetPh", VMColumnConfig.UNEDITABLE, inputReal},
                             {"Budget Amt", "budgetAmt",
                             VMColumnConfig.UNEDITABLE,
                             inputReal}, {"Actual PH", "actualPh",
                             VMColumnConfig.UNEDITABLE,
                             inputReal}, {"Actual Amt", "actualAmt",
                             VMColumnConfig.UNEDITABLE, inputReal},
                             {"Remain Amt", "remain",
                             VMColumnConfig.UNEDITABLE, inputReal},

        };

        try {
            model = new VMTable2(configs);
            model.setDtoType(DtoActivityCost.class);
            table = new VWJTable(model);
            this.add(table.getScrollPane(), null);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void setParameter(Parameter param) {
        super.setParameter(param);

        node = (ITreeNode) param.get(DtoKEY.WBSTREE);
    }


    public void resetUI() {
        List activityCost = new ArrayList();
        if (node != null) {
            DtoWbsActivity dataBean = (DtoWbsActivity) node.getDataBean();

            String acntRid = dataBean.getAcntRid().toString();
            String activityId = dataBean.getActivityRid().toString();


            if (acntRid != null && activityId != null &&dataBean.isActivity() ) {
                InputInfo inputInfo = new InputInfo();
                inputInfo.setActionId(this.actionIdActivityCostGet);
                inputInfo.setInputObj("acntRid", acntRid);
                inputInfo.setInputObj("activityId", activityId);

                ReturnInfo returnInfo = accessData(inputInfo);
                if (!returnInfo.isError()) {
                    activityCost = (List) returnInfo.getReturnObj(
                        "activityCost");
                    this.getTable().setRows(activityCost);
                }
            }
        }

    }

    public void actionPerformedLoad(ActionEvent event) {
        resetUI();
        this.refreshWorkArea();
    }

//    public static void main(String[] args) {
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
//        VWWorkArea w1 = new VWWorkArea();
//        VwActivityCost tt = new VwActivityCost();
//        w1.addTab("Labor Resource", tt);
//        TestPanel.show(w1);
//    }

}
