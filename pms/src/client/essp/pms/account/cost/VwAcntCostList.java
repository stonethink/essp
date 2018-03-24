package client.essp.pms.account.cost;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.pms.account.cost.DtoAcntCost;
import client.essp.common.view.VWTableWorkArea;
import client.framework.model.VMColumnConfig;
import client.framework.view.vwcomp.IVWAppletParameter;
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
public class VwAcntCostList extends VWTableWorkArea implements
    IVWAppletParameter {


    static final String actionAcntCostList = "FAccountCostListAction";
    private DtoAcntCost dto = new DtoAcntCost();

    public VwAcntCostList() {
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
        Object[][] configs = new Object[][] { {"Job Code", "jobCode",
                             VMColumnConfig.UNEDITABLE, new VWJText()},
                             {"Currency", "currency", VMColumnConfig.UNEDITABLE,
                             new VWJText()}, {"Price", "price",
                             VMColumnConfig.UNEDITABLE, inputReal}, {"Budget(PM)",
                             "budgetPm", VMColumnConfig.UNEDITABLE, inputReal},
                             {"Actual(PM)", "actualPm", VMColumnConfig.UNEDITABLE,
                             inputReal}, {"Remain(PM)", "remainPm",
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
            model.setDtoType(DtoAcntCost.class);
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
        if (dto.getAcntRid() != null) {
            inputInfo.setInputObj("acntcost", dto);
        }
        inputInfo.setActionId(actionAcntCostList);

        ReturnInfo returnInfo = accessData(inputInfo);
        if (returnInfo.isError() == false) {
            acntCost = (List) returnInfo.getReturnObj("acntcost");
            this.getTable().setRows(acntCost);
        }
    }

    public void setParameter(Parameter param) {
        super.setParameter(param);
        dto.setAcntRid((Long) param.get("acntRid"));

    }

    /* public static void main(String[] args) {
         Global.todayDateStr = "2005-12-03";
         Global.todayDatePattern = "yyyy-MM-dd";
         Global.userId = "stone.shi";
         DtoUser user = new DtoUser();
         user.setUserID(Global.userId);
         user.setUserLoginId(Global.userId);
         HttpServletRequest request = new HttpServletRequestMock();
         HttpSession session = request.getSession();
         session.setAttribute(DtoUser.SESSION_USER, user);

         VWWorkArea w1 = new VWWorkArea();
         VwAcntCostList resource = new VwAcntCostList();
         w1.addTab("Labor Cost", resource);
         TestPanel.show(w1);
         resource.refreshWorkArea();

     }*/

    public String[][] getParameterInfo() {
        return null;
    }


}
