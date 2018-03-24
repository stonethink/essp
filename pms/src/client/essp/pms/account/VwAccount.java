package client.essp.pms.account;

import java.awt.AWTEvent;
import java.awt.Dimension;

import c2s.essp.cbs.CbsConstant;
import c2s.essp.cbs.budget.DtoBudgetParam;
import c2s.essp.pms.account.DtoAcntKEY;
import c2s.essp.pms.account.DtoPmsAcnt;
import client.essp.common.util.TestPanelParam;
import client.essp.common.view.VWTDWorkArea;
import client.essp.common.view.VWWorkArea;
import client.essp.pms.account.baseline.VwAcntBLMain;
import client.essp.cbs.budget.VwBudgetMain;
import client.essp.pms.account.code.VwAcntCodeList;
import client.essp.pms.account.cost.VwCostMain;
import client.essp.pms.account.keyperson.VwAcntKeyPersonnel;
import client.essp.pms.account.labor.VwAcntLaborResources;
import client.essp.pms.account.noneLabor.VwAcntNoneLaborRes;
import client.framework.view.event.DataChangedListener;
import client.framework.view.event.RowSelectedListener;
import client.framework.view.event.RowSelectedLostListener;
import client.framework.view.vwcomp.IVWAppletParameter;
import com.wits.util.Parameter;
import client.essp.pms.account.budget.VwAccountBudgetMain;
import client.essp.pms.account.pcb.VwAccountPCB;
import client.essp.pms.account.tailor.*;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class VwAccount extends VWTDWorkArea implements IVWAppletParameter {
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
    VwAccountList vwAccList;
    VwAccountGeneral vwAcntGen;
    VwAcntBLMain vwAcntBLMain;
    VwAcntLaborResources vwAcntLaborResourcesBase;
    VwAcntNoneLaborRes vwAcntNoneLaborRes;
    VwAcntKeyPersonnel vwAcntKeyPersonnel;
    VwAccountBudgetMain vwPropBudget; //����Ԥ��
    VwCostMain vwCost;
    VwAcntCodeList vwAcntCodeList;

    VwAccountPCB vwAccountPcb;
    VwTailor vwTailor;

    VwAccountGeneral vwAcntGenManager;
    VwAccountGeneral vwAcntGenEmp;
    VWWorkArea workAreaManager = null;
    VWWorkArea workAreaEmp = null;

    boolean isEmp;

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
    public VwAccount() {
        super(200);
        enableEvents(AWTEvent.WINDOW_EVENT_MASK);

        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        addUICEvent();
    }


    /** @link dependency
     * @stereotype run-time*/
    /*# EbsNodeViewManager lnkEbsNodeViewManager; */
    //Component initialization
    private void jbInit() throws Exception {
        this.setPreferredSize(new Dimension(700, 470));

        vwAccList = new VwAccountList();
        this.getTopArea().addTab("Account", vwAccList);

        this.vwAcntGen = new VwAccountGeneral();
        this.vwAcntBLMain = new VwAcntBLMain();
        this.vwAcntLaborResourcesBase = new VwAcntLaborResources();
        this.vwAcntNoneLaborRes = new VwAcntNoneLaborRes();
        this.vwAcntKeyPersonnel = new VwAcntKeyPersonnel();
        vwPropBudget = new VwAccountBudgetMain();
        vwCost = new VwCostMain();
        vwAcntCodeList = new VwAcntCodeList();
        vwAccountPcb=new VwAccountPCB();
        vwTailor=new VwTailor();
        //down area of manager
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
        workAreaManager.addTab("PCB",vwAccountPcb);
        workAreaManager.addTab("Tailor",vwTailor);
        //down area of employee
        workAreaEmp = new VWWorkArea();
        vwAcntGenEmp = new VwAccountGeneral();
        workAreaEmp.addTab("General", vwAcntGenEmp, true);

        this.getDownArea().addTab("General", vwAcntGen, true);
        isEmp = true;
    }

    /**
     * ������棺��������¼�
     */
    private void addUICEvent() {
        this.vwAccList.getTable().addRowSelectedLostListener(new
            RowSelectedLostListener() {
            public boolean processRowSelectedLost(int oldSelectedRow,
                                                  Object oldSelectedData) {
                return processRowSelectedLostAccList(oldSelectedRow,
                    oldSelectedData);
            }
        });

        this.vwAccList.getTable().addRowSelectedListener(new
            RowSelectedListener() {
            public void processRowSelected() {
                processRowSelectedAccList();
            }
        });

        this.vwAcntGen.addDataChangedListener(new DataChangedListener() {
            public void processDataChanged() {
                vwAccList.getTable().refreshTable();
            }
        });

        this.vwAcntGenEmp.addDataChangedListener(new DataChangedListener() {
            public void processDataChanged() {
                vwAccList.getTable().refreshTable();
            }
        });

        this.vwAcntGenManager.addDataChangedListener(new DataChangedListener() {
            public void processDataChanged() {
                vwAccList.getTable().refreshTable();
            }
        });

    }

    /////// ��2�����ò�������ȡ�������
    public void setParameter(Parameter param) {
        entryFunType = (String) param.get(DtoAcntKEY.ACCOUNT_ENTRY_FUN_TYPE);
        log.debug("entryFunType=" + entryFunType);
        if (entryFunType == null || entryFunType.length() == 0) {
            entryFunType = DtoAcntKEY.PMS_ACCOUNT_FUN;
        }

        this.refreshFlag = true;
    }

    public String[][] getParameterInfo() {
        String[][] parameterInfo = { {"entryFunType", "", "entryFunType"}
        };
        return parameterInfo;
    }

    ///////��3����ȡ���ݲ�ˢ�»���
    //������workArea����Ҫˢ���Լ�
    public void refreshWorkArea() {
        if (refreshFlag == true) {
            Parameter param = new Parameter();
            param.put(DtoAcntKEY.ACCOUNT_ENTRY_FUN_TYPE, this.entryFunType);
            vwAccList.setParameter(param);
            vwAccList.refreshWorkArea();
            this.refreshFlag = false;
        }
    }

    /////// ��4���¼�����
    public boolean processRowSelectedLostAccList(int oldSelectedRow,
                                                 Object oldSelectedData) {
        this.getDownArea().getSelectedWorkArea().saveWorkArea();
        return this.getDownArea().getSelectedWorkArea().isSaveOk();
    }

    public void processRowSelectedAccList() {
        DtoPmsAcnt pmsAcc = (DtoPmsAcnt)this.vwAccList.getSelectedData();

        if (pmsAcc != null) {
            if ((pmsAcc.isPm() || pmsAcc.isManagement() ||
                 DtoAcntKEY.SEPG_ACCOUNT_FUN.equals(entryFunType))
                ) {
                if (isEmp == true) {
                    isEmp = false;
                    this.vwAcntGen = vwAcntGenManager;
                    this.setDownArea(workAreaManager);
                }
                //�����SEPG��ݽ����Ļ���isManagement=true
                //��ʹ����Ŀ�Ƭ�����޸�
                //modified by:Robin
                if(DtoAcntKEY.SEPG_ACCOUNT_FUN.equals(entryFunType)){
                    pmsAcc.setManagement(true);
                }
            } else {
                if (isEmp == false) {
                    isEmp = true;
                    this.vwAcntGen = vwAcntGenEmp;
                    this.setDownArea(workAreaEmp);
                }
            }

            /*
                         Parameter param1 = new Parameter();
                         param1.put(DtoAcntKEY.DTO, pmsAcc);
                         this.vwAcntGen.setParameter(param1);
             */
//            System.out.println("pmsAcc.getRid()="+pmsAcc.getRid());

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
//            budgetParam2.setFromDate(pmsAcc.getPlannedStart());
//            budgetParam2.setToDate(pmsAcc.getPlannedFinish());
            budgetParam2.setCurrency(pmsAcc.getCurrency());
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

            Parameter paramPcb=new Parameter();
            paramPcb.put("acntRid",pmsAcc.getRid());
            this.vwAccountPcb.setParameter(paramPcb);

            Parameter paramTailor=new Parameter();
            paramTailor.put("acntRid",pmsAcc.getRid());
            vwTailor.setParameter(paramTailor);

//            vwTailor.getEkit().setHTMLText(pmsAcc.getAcntTailor());


        } else {
            this.vwAcntGen.setParameter((DtoPmsAcnt)null);
            this.vwAcntBLMain.setParameter(new Parameter());
            this.vwAcntLaborResourcesBase.setParameter(new Parameter());
            this.vwAcntNoneLaborRes.setParameter(new Parameter());
            this.vwAcntKeyPersonnel.setParameter(new Parameter());
            this.vwAcntCodeList.setParameter(new Parameter());
            vwAcntGenEmp.setParameter((DtoPmsAcnt)null);
            this.setDownArea(workAreaEmp);
            isEmp = true;
        }

        this.getDownArea().getSelectedWorkArea().refreshWorkArea();
    }


    /////// ��5����������
    public void saveWorkArea() {
        if (this.refreshFlag == true) {
            vwAccList.saveWorkArea();
            this.getDownArea().getSelectedWorkArea().saveWorkArea();
        }
    }

    public static void main(String[] args) {


        /*Global.todayDateStr = "2005-12-03";
        Global.todayDatePattern = "yyyy-MM-dd";
        Global.userId = "stone.shi";
        DtoUser user = new DtoUser();
        user.setUserID(Global.userId);
        user.setUserLoginId(Global.userId);
        HttpServletRequest request = new HttpServletRequestMock();
        HttpSession session = request.getSession();
        session.setAttribute(DtoUser.SESSION_USER, user);*/

        VWWorkArea workArea = new VwAccount();
        Parameter param = new Parameter();
        param.put("entryFunType", DtoAcntKEY.PMS_ACCOUNT_FUN);
        workArea.setParameter(param);



        TestPanelParam.show(workArea);
        workArea.refreshWorkArea();
    }

}

