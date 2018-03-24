package client.essp.timesheet.approval;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import c2s.dto.*;
import c2s.essp.timesheet.approval.DtoTsApproval;
import c2s.essp.timesheet.weeklyreport.DtoTimeSheetPeriod;
import client.essp.common.view.VWGeneralWorkArea;
import client.essp.timesheet.period.VwTsPeriod;
import client.framework.model.VMComboBox;
import client.framework.view.vwcomp.*;

import com.wits.util.Parameter;
import com.wits.util.TestPanel;

public class VwWorkGeneral extends VWGeneralWorkArea implements TsPeriodChangeListener {
    private VwTsPeriod tsPeriod = new VwTsPeriod();
    private DtoTimeSheetPeriod period;
    private VWJLabel lblAccount = null;
    private VWJComboBox cmbAccount = null;
    private VWJLabel lblView = null;
    private VWJRadioButton rbAll = null;
    private VWJRadioButton rbToDeal = null;
    private VWJRadioButton rbDealed = null;
    private VWButtonGroup grpQueryWay = null;
    private List<AccountOrPeriodChangeListener> accountOrPeriodChangeListeners = new ArrayList();
    private boolean isPMApproval = true;
    private final static String actionId_LoadAccounts = "FTSLoadAccounts";
    private String queryWay = DtoTsApproval.DTO_QUERY_WAY_TODEAL;
    public VwWorkGeneral(boolean isPM) {
        isPMApproval = isPM;
        jbInit(isPM);
        addUICEvent(isPM);
        
    }

    private void jbInit(boolean isPM){
        this.setLayout(null);
        tsPeriod.setBounds(new Rectangle(10, 9, 310, 22));
        tsPeriod.setMultiFlag(true);
        if(isPM){
            lblAccount = new VWJLabel();
            cmbAccount = new VWJComboBox();
            lblAccount.setText("rsid.timesheet.account");
            lblAccount.setBounds(new Rectangle(330, 11, 100, 20));
            cmbAccount.setBounds(new Rectangle(400, 11, 150, 20));
            this.add(lblAccount);
            this.add(cmbAccount);
        }
        lblView = new VWJLabel();
        lblView.setText("rsid.timesheet.view");
        lblView.setBounds(new Rectangle(20, 40, 50, 20));
        
        rbToDeal = new VWJRadioButton();
        rbToDeal.setText("rsid.timesheet.toApproved");
        rbToDeal.setBounds(new Rectangle(70, 40, 100, 20));
        rbToDeal.setSelected(true);
        
        rbDealed = new VWJRadioButton();
        rbDealed.setText("rsid.timesheet.approved");
        rbDealed.setBounds(new Rectangle(170, 40, 100, 20));
        
        rbAll = new VWJRadioButton();
        rbAll.setText("rsid.timesheet.all");
        rbAll.setBounds(new Rectangle(270, 40, 100, 20));
        
        grpQueryWay = new VWButtonGroup();
        
        grpQueryWay.add(rbAll);
        grpQueryWay.add(rbToDeal);
        grpQueryWay.add(rbDealed);
        
        this.add(tsPeriod);
        this.add(lblView);
        this.add(rbAll);
        this.add(rbToDeal);
        this.add(rbDealed);
    }
    /**
     * 事件处理
     */
    private void addUICEvent(boolean isPM) {
    	if (isPM) {
			cmbAccount.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					performeAction();
				}
			});
		}
    	rbToDeal.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				queryWay = DtoTsApproval.DTO_QUERY_WAY_TODEAL;
				performeAction();
			}
    	});
    	rbDealed.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				queryWay = DtoTsApproval.DTO_QUERY_WAY_DEALED;
				performeAction();
			}
    	});
    	rbAll.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				queryWay = DtoTsApproval.DTO_QUERY_WAY_ALL;
				performeAction();
			}
    	});
        tsPeriod.addTsChangeListener(this);
    }
    private void performeAction(){
        if(period == null) {
            return;
        }
        for(AccountOrPeriodChangeListener l : accountOrPeriodChangeListeners){
        	Long anctRid = null;
        	if(cmbAccount != null && cmbAccount.getUICValue() != null) {
        		anctRid = (Long)cmbAccount.getUICValue();
        	}
            l.performeAccountOrPeriodChanged(anctRid, this.period, queryWay);
        }
    }
  /**
    * 加载当前用户为主管的Project或Dept
    *
    * @return Vector<DtoComboItem>
    */
   protected Vector<DtoComboItem> loadAccounts() {
       Vector accountItems = null;
       InputInfo inputInfo = new InputInfo();
       inputInfo.setActionId(actionId_LoadAccounts);
       ReturnInfo returnInfo = this.accessData(inputInfo);
       if(returnInfo.isError() == false) {
           accountItems = (Vector) returnInfo.getReturnObj(
                   DtoTsApproval.DTO_COMBOX_ITEM);
       }
       return accountItems;
   }
   public Parameter getPara(){
       Parameter para = new Parameter();
       Long acntRid = new Long(-1);
       if(isPMApproval){
           acntRid = (Long) cmbAccount.getUICValue();
       }
       Date begin = tsPeriod.getBeginDate();
       Date end = tsPeriod.getEndDate();
       para.put("acntRid", acntRid);
       para.put("begin", begin);
       para.put("end", end);
       para.put("tsId", tsPeriod.getTsId());
       para.put("queryWay", DtoTsApproval.DTO_QUERY_WAY_TODEAL);
       return para;
   }

    // Methods
    public void setParameter(Parameter param) {
        super.setParameter(param);
        tsPeriod.setPeriodMode(VwTsPeriod.PERIOD_MODEL_TS);
        tsPeriod.setParameter(param);
    }

    public void refreshWorkArea() {
    	super.refreshWorkArea();
        tsPeriod.refreshWorkArea();
    }


    protected void resetUI() {
        if(isPMApproval) {
            VMComboBox item = new VMComboBox(loadAccounts());
            cmbAccount.setModel(item);
            cmbAccount.setEnabled(true);
        }
    }


    public static void main(String[] args) {
          VwWorkGeneral vwworkgeneral = new VwWorkGeneral(true);
          TestPanel.show(vwworkgeneral);
    }

    public void addAccountOrPeriodListeners(AccountOrPeriodChangeListener l) {
        accountOrPeriodChangeListeners.add(l);
    }

    public VwTsPeriod getTsPeriod() {
        return tsPeriod;
    }

    public void performePeriodChanged(DtoTimeSheetPeriod period) {
        this.period = period;
        performeAction();
    }

}
