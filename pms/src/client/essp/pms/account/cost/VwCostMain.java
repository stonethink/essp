package client.essp.pms.account.cost;

import c2s.essp.cbs.CbsConstant;
import client.essp.cbs.budget.VwAccountPrice;
import client.essp.common.view.VWGeneralWorkArea;
import com.wits.util.Parameter;

public class VwCostMain extends VWGeneralWorkArea {

    VwAcntCostList vwCost;
    VwAccountPrice vwAccountPrice;
    VwAcntActivityCostList vwActivityCost;


    Long acntRid;
    Boolean isReadOnly;
    public VwCostMain() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void jbInit() throws Exception {
        vwCost = new VwAcntCostList();
        vwAccountPrice = new VwAccountPrice();
        vwActivityCost = new VwAcntActivityCostList();

        this.addTab("Labor Cost", vwCost);
        this.addTab("Activity Cost", vwActivityCost);
        this.addTab("Price", vwAccountPrice);
    }

    public void setParameter(Parameter param) {
        super.setParameter(param);
        acntRid = (Long) param.get("acntRid");
        isReadOnly = (Boolean) param.get("isReadOnly");
    }

    public void resetUI() {
        if (acntRid != null) {
            Parameter param = new Parameter();
            param.put("acntRid", acntRid);
            param.put("isReadOnly", isReadOnly);
            vwCost.setParameter(param);
            vwActivityCost.setParameter(param);

            param.put("currency", "USD"); //设置任意币种,Cost卡片里不能修改价格
            param.put("isCanModifyPrice", Boolean.FALSE);
            param.put("priceScope", CbsConstant.SCOPE_ACCOUNT);
            vwAccountPrice.setParameter(param);

            vwCost.resetUI();
            vwActivityCost.resetUI();
            vwAccountPrice.resetUI();
        }
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
         DtoLaborBudget budget1 = new DtoLaborBudget();
         VwCostMain resource = new VwCostMain();
         w1.addTab("Cost", resource);
         TestPanel.show(w1);
         //resource.refreshWorkArea();

     }*/

}
