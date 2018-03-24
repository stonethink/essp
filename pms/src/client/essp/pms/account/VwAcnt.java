package client.essp.pms.account;

import java.awt.AWTEvent;
import java.awt.Dimension;
import c2s.essp.pms.account.DtoAcntKEY;
import c2s.essp.pms.account.DtoPmsAcnt;
import client.essp.common.view.VWTDWorkArea;
import client.essp.common.view.VWWorkArea;
import client.essp.pms.account.baseline.VwAcntBLMain;
import client.essp.pms.account.keyperson.VwAcntKeyPersonnel;
import client.essp.pms.account.labor.VwAcntLaborResources;
import client.essp.pms.account.noneLabor.VwAcntNoneLaborRes;
import com.wits.util.Parameter;
import client.essp.cbs.budget.VwBudgetMain;
import c2s.essp.cbs.budget.DtoBudgetParam;
import c2s.essp.cbs.CbsConstant;
import client.essp.cbs.cost.VwCostMain;
import client.essp.pms.account.code.VwAcntCodeList;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class VwAcnt extends VWTDWorkArea {
    /**
     * input parameters
     */
    String entryFunType = DtoAcntKEY.PMS_ACCOUNT_FUN; //PmsAccountFun, EbsAccountFun,SepgAccountFun
//   String userId = "";
//   String userName = "";
//   String userType = DtoUser.USER_TYPE_EMPLOYEE;

    /**
     * define control variable
     */
    private boolean refreshFlag = false;

    /**
     * define common data (globe)
     */
    VwAccountGeneral vwAcntGen;
    VwAcntBLMain vwAcntBLMain;
    VwAcntLaborResources vwAcntLaborResourcesBase;
    VwAcntNoneLaborRes vwAcntNoneLaborRes;
    VwAcntKeyPersonnel vwAcntKeyPersonnel;
    VwBudgetMain vwPropBudget; //����Ԥ��
    VwCostMain vwCost;
    VwAcntCodeList vwAcntCodeList;

    VwAccountGeneral vwAcntGenManager;
    VwAccountGeneral vwAcntGenEmp;
    VWWorkArea workAreaManager = null;
    VWWorkArea workAreaEmp = null;

    boolean isEmp;
    DtoPmsAcnt pmsAcc =null;
    /**
     * �����ÿ��Account��¼���еĿ�Ƭ�У�
     *    General
     *    Baseline
     *    Labor Resources
     *    Non-Labor Resources
     *    Budget(��һ�׶β���)
     *    Key Personnel
     *    Code
     *    Issue
     * ����ڲ�ͬ��ɫ��Card������Σ�
     * 1����ΪEBS�����߻�SEPG��Ա������ʾ���޸�ȫ���Ŀ�Ƭ�����Ҷ�General���Խ����޸ģ�
     * 2����Ϊ��Ŀ����(PM)������ʾȫ���Ŀ�Ƭ������General�����Խ����޸ģ����࿨Ƭ�������޸ģ�
     * 3����Ϊ��Ŀ���Ա(Participant),������ʾGeneral��Ƭ
     */

    /**
     * default constructor
     */
    public VwAcnt() {
        super(450);
        enableEvents(AWTEvent.WINDOW_EVENT_MASK);
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /** @link dependency
     * @stereotype run-time*/
    /*# EbsNodeViewManager lnkEbsNodeViewManager; */
    //Component initialization
    private void jbInit() throws Exception {
        this.setPreferredSize(new Dimension(780, 450));

        this.vwAcntGen = new VwAccountGeneral();
        this.vwAcntBLMain = new VwAcntBLMain();
        this.vwAcntLaborResourcesBase = new VwAcntLaborResources();
        this.vwAcntNoneLaborRes = new VwAcntNoneLaborRes();
        this.vwAcntKeyPersonnel = new VwAcntKeyPersonnel();
        vwPropBudget = new VwBudgetMain();
        vwCost = new VwCostMain();
        vwAcntCodeList = new VwAcntCodeList();
        //top area of manager
        workAreaManager = new VWWorkArea();
        vwAcntGenManager = new VwAccountGeneral();
        workAreaManager.addTab("General", vwAcntGenManager, true);
        workAreaManager.addTab("Baseline", vwAcntBLMain, true);
        workAreaManager.addTab("Labor Resource",
                               vwAcntLaborResourcesBase);
        workAreaManager.addTab("None Labor Resource",
                               vwAcntNoneLaborRes);
        workAreaManager.addTab("Key Person", vwAcntKeyPersonnel);
        workAreaManager.addTab("Budget", vwPropBudget);
        workAreaManager.addTab("Cost", vwCost);
        workAreaManager.addTab("Code", vwAcntCodeList);
        //top area of employee
        workAreaEmp = new VWWorkArea();
        vwAcntGenEmp = new VwAccountGeneral();

        workAreaEmp.addTab("General", vwAcntGenEmp, true);

        this.getTopArea().addTab("General", vwAcntGen, true);
        isEmp = true;
    }

    /////// ��2�����ò�������ȡ�������
    public void setParameter(Parameter param) {
        entryFunType = (String) param.get("entryFunType");
        log.debug("entryFunType=" + entryFunType);
        if (entryFunType == null || entryFunType.length() == 0) {
            entryFunType = DtoAcntKEY.PMS_ACCOUNT_FUN;
        }
        pmsAcc = (DtoPmsAcnt) param.get("dtoAccount");
        this.refreshFlag = true;
        resetUI();
    }

    ///////��3����ȡ���ݲ�ˢ�»���
    //������workArea����Ҫˢ���Լ�
    public void refreshWorkArea() {
        if (refreshFlag == true) {
            Parameter param = new Parameter();
            param.put("entryFunType", this.entryFunType);
            this.refreshFlag = false;
        }
    }

    public void resetUI() {

        if (pmsAcc != null) {
            if ((pmsAcc.isPm() || pmsAcc.isManagement())
                ) {
                if (isEmp == true) {
                    isEmp = false;
                    this.vwAcntGen = vwAcntGenManager;
                    this.setTopArea(workAreaManager);
                }
            } else {
                if (isEmp == false) {
                    isEmp = true;
                    this.vwAcntGen = vwAcntGenEmp;
                    this.setTopArea(workAreaEmp);
                }
            }

            this.vwAcntGen.setParameter(pmsAcc);

            Parameter param2 = new Parameter();
            param2.put("dtoAccount", pmsAcc);
            this.vwAcntBLMain.setParameter(param2);

            Parameter param3 = new Parameter();
            param3.put("dtoAccount", pmsAcc);
            this.vwAcntLaborResourcesBase.setParameter(param3);

            Parameter param4 = new Parameter();
            param4.put(DtoAcntKEY.ACNT_RID, pmsAcc.getRid());
            this.vwAcntNoneLaborRes.setParameter(param4);

            Parameter param5 = new Parameter();
            param5.put("dtoAccount", pmsAcc);
            this.vwAcntKeyPersonnel.setParameter(param5);

            Parameter param7 = new Parameter(); //����Ԥ�����
            DtoBudgetParam budgetParam2 = new DtoBudgetParam();
            budgetParam2.setAcntRid(pmsAcc.getRid());
            budgetParam2.setBudgetType(CbsConstant.PROPOSED_BUDGET); //��Ŀ��������Ԥ��,EBS Manager��������Ԥ��
            budgetParam2.setIsCanModifyPrice(false);
            if (pmsAcc.isPm()) {
                budgetParam2.setIsReadOnly(false);
            } else {
                budgetParam2.setIsReadOnly(true);
            }
            budgetParam2.setIsManager(pmsAcc.isManagement());
            param7.put("budgetParam", budgetParam2);
            this.vwPropBudget.setParameter(param7);

            Parameter param8 = new Parameter();
            param8.put("acntRid", pmsAcc.getRid());
            if (pmsAcc.isPm()) { //PM����Cost��Ƭ
                param8.put("isReadOnly", Boolean.FALSE);
            } else {
                param8.put("isReadOnly", Boolean.TRUE);
            }
            this.vwCost.setParameter(param8);

            Parameter param9 = new Parameter();
            param9.put("dtoAccount", pmsAcc);
            this.vwAcntCodeList.setParameter(param9);

        } else {
            this.vwAcntGen.setParameter((DtoPmsAcnt)null);
            this.vwAcntBLMain.setParameter(new Parameter());
            this.vwAcntLaborResourcesBase.setParameter(new Parameter());
            this.vwAcntNoneLaborRes.setParameter(new Parameter());
            this.vwAcntKeyPersonnel.setParameter(new Parameter());
            this.vwAcntCodeList.setParameter(new Parameter());
        }

        this.getTopArea().getSelectedWorkArea().refreshWorkArea();
    }


    /////// ��5����������
    public void saveWorkArea() {
        if (this.refreshFlag == true) {
            this.getTopArea().getSelectedWorkArea().saveWorkArea();
        }
    }

}
