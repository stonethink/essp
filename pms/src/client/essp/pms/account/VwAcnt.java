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
    VwBudgetMain vwPropBudget; //建议预算
    VwCostMain vwCost;
    VwAcntCodeList vwAcntCodeList;

    VwAccountGeneral vwAcntGenManager;
    VwAccountGeneral vwAcntGenEmp;
    VWWorkArea workAreaManager = null;
    VWWorkArea workAreaEmp = null;

    boolean isEmp;
    DtoPmsAcnt pmsAcc =null;
    /**
     * 针对于每条Account记录所有的卡片有：
     *    General
     *    Baseline
     *    Labor Resources
     *    Non-Labor Resources
     *    Budget(第一阶段不做)
     *    Key Personnel
     *    Code
     *    Issue
     * 针对于不同角色，Card组合情形：
     * 1、作为EBS管理者或SEPG成员，能显示和修改全部的卡片，并且对General可以进行修改；
     * 2、作为项目经理(PM)，能显示全部的卡片，但对General不可以进行修改，其余卡片均可以修改；
     * 3、作为项目组成员(Participant),仅能显示General卡片
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

    /////// 段2，设置参数：获取传入参数
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

    ///////段3，获取数据并刷新画面
    //最外层的workArea不需要刷新自己
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

            Parameter param7 = new Parameter(); //建议预算参数
            DtoBudgetParam budgetParam2 = new DtoBudgetParam();
            budgetParam2.setAcntRid(pmsAcc.getRid());
            budgetParam2.setBudgetType(CbsConstant.PROPOSED_BUDGET); //项目经理负责建议预算,EBS Manager负责期望预算
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
            if (pmsAcc.isPm()) { //PM负责Cost卡片
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


    /////// 段5，保存数据
    public void saveWorkArea() {
        if (this.refreshFlag == true) {
            this.getTopArea().getSelectedWorkArea().saveWorkArea();
        }
    }

}
