package client.essp.pms.account.cost;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.pms.account.cost.DtoAcntActivityCost;
import c2s.essp.pms.account.cost.DtoAcntCost;
import client.essp.common.view.VWTableWorkArea;
import client.framework.model.VMColumnConfig;
import client.framework.view.vwcomp.VWJReal;
import client.framework.view.vwcomp.VWJTable;
import client.framework.view.vwcomp.VWJText;
import com.wits.util.Parameter;
import client.framework.model.VMTable2;

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
public class VwAcntActivityCostList extends VWTableWorkArea {
    static final String actionAcntActivityCostList =
        "FAccountActivityCostListAction";
    private DtoAcntCost dto = new DtoAcntCost();

    public VwAcntActivityCostList() {
        try {
            jbInit();
            addUIEvent();
            //resetUI();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void addUIEvent() {
        this.getButtonPanel().addLoadButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedLoad(e);
            }
        });

    }


    protected void jbInit() throws Exception {
        //设置标题栏位这是一个构造java里的表的标题的必须有的个对象组，可以看相关的文档

        VWJReal inputReal = new VWJReal();
        inputReal.setMaxInputDecimalDigit(2);
        inputReal.setMaxInputIntegerDigit(8);
        inputReal.setCanNegative(true);

        Object[][] configs = new Object[][] { {"Activity ID", "code",
                             VMColumnConfig.UNEDITABLE, new VWJText()},
                             {"Activity Name", "activityName",
                             VMColumnConfig.UNEDITABLE,
                             new VWJText()}, {"Budget(PH)",
                             "budgetPh", VMColumnConfig.UNEDITABLE, inputReal},
                             {"Actual(PH)", "actualPh", VMColumnConfig.UNEDITABLE,
                             inputReal}, {"Remain(PH)", "remainPh",
                             VMColumnConfig.UNEDITABLE, inputReal},
                             {"Budget(AMT)", "budgetAmt",
                             VMColumnConfig.UNEDITABLE,
                             inputReal}, {"Actual(AMT)", "actualAmt",
                             VMColumnConfig.UNEDITABLE, inputReal},
                             {"Remain(AMT)", "remainAmt",
                             VMColumnConfig.UNEDITABLE, inputReal},

        };

        try {
            model = new VMTable2(configs);
            model.setDtoType(DtoAcntActivityCost.class);
            table = new VWJTable(model, new AcntCostListNodeViewManager());
            this.add(table.getScrollPane(), null);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void actionPerformedLoad(ActionEvent e) {
        resetUI();
        this.refreshWorkArea();
    }


    public void resetUI() {
        List acntCost = new ArrayList();

        InputInfo inputInfo = new InputInfo();
        inputInfo.setInputObj("acntactivitycost", dto);
        inputInfo.setActionId(actionAcntActivityCostList);

        ReturnInfo returnInfo = accessData(inputInfo);
        if (returnInfo.isError() == false) {
            acntCost = (List) returnInfo.getReturnObj("acntactivitycost");
            this.getTable().setRows(acntCost);
        }
    }

    public void setParameter(Parameter param) {
        super.setParameter(param);
        dto.setAcntRid((Long) param.get("acntRid"));
        //isReadOnly = (Boolean) param.get("isReadOnly");
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
//        VwAcntActivityCostList resource = new VwAcntActivityCostList();
//        w1.addTab("Activity Cost", resource);
//        Parameter param1 = new Parameter();
//        param1.put("acntRid", new Long(6022));
//        resource.setParameter(param1);
//            TestPanel.show(w1);
//            resource.refreshWorkArea();
//
//    }


}
