package client.essp.pms.quality.activity;

import client.essp.common.view.VWTDWorkArea;
import client.essp.common.view.VWWorkArea;
import c2s.essp.pms.quality.activity.DtoTestRound;
import java.util.List;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import client.framework.view.event.RowSelectedListener;
import c2s.essp.pms.quality.activity.DtoQualityActivity;
import com.wits.util.Parameter;
import com.wits.util.ProcessVariant;
import com.wits.util.IVariantListener;

public class VWQualityActivityMain extends VWTDWorkArea implements IVariantListener {
    final String ACTIONID_GENERAL_SAVE = "FQuGeneralSaveAction";
    VWQualityActivityList vwQualityActivityList;
    VWGeneral vwGeneral;
    VWTestRecord vwTestRecord;
    VWActivityQualityConclusion vwConclusion;
    private boolean refreshFlag = false;
    private Long inAcntRid;

    public VWQualityActivityMain() {
        super(200);
        try {
            jbInit();
            addUIEvent();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void jbInit() throws Exception {
        vwQualityActivityList = new VWQualityActivityList();
        vwGeneral = new VWGeneral();
        vwTestRecord = new VWTestRecord();
        vwConclusion = new VWActivityQualityConclusion();

        this.getTopArea().addTab("QualityActivity", vwQualityActivityList);

        this.getDownArea().addTab("General", vwGeneral);
        this.getDownArea().addTab("TestRecord", vwTestRecord);
        this.getDownArea().addTab("Conclusion", vwConclusion);
    }

    private void addUIEvent() {
        //TestStat表格数据改变时,更新DefectNum栏位
        this.vwTestRecord.getModel().addTableModelListener(new
            TableModelListener() {
            public void tableChanged(TableModelEvent e) {
                int fDefectNum = 0;
                List list = vwTestRecord.getModel().getDtoList();
                for (int j = 0; j < list.size(); j++) {
                    DtoTestRound dtoTestRound = (DtoTestRound) list.get(j);
                    if (dtoTestRound.isDelete()) {
                        fDefectNum -= dtoTestRound.getDefectNum().intValue();
                    } else {
                        if (dtoTestRound.getDefectNum() != null) {
                            fDefectNum += dtoTestRound.getDefectNum().intValue();
                        }
                    }
                }
                String s = vwGeneral.ActualSize.getUICValue().toString();
                Double actualDefectRate = new Double(0.00);
                if (s != null && s.length() > 0 &&
                    Double.valueOf(s).doubleValue() != 0.0) {
                    double actualSiza = Double.valueOf(s).doubleValue();
                    actualDefectRate = new Double(fDefectNum / actualSiza);
                    vwGeneral.ActualDefectRate.setUICValue(actualDefectRate);
                }
                vwGeneral.ActualDefectNum.setUICValue(new Long(fDefectNum));
//                Long defectNum = new Long(fDefectNum);
//                vwQualityActivityList.updateQualityactivity(defectNum,
//                    actualDefectRate);
                vwConclusion.fireNeedRefresh();
            }
        });
        vwQualityActivityList.getTable().addRowSelectedListener(new
            RowSelectedListener() {
            public void processRowSelected() {
                processRowSelectedList();
            }
        });
        ProcessVariant.addDataListener("account", this);
    }

    public void refreshWorkArea() {
        if(refreshFlag) {
            vwQualityActivityList.refreshWorkArea();
            refreshFlag = false;
        }
    }
    public void dataChanged(String name, Object value) {
        if (name.equals("account")) {
            Long acntRid = null;
            if (value instanceof String) {
                acntRid = new Long((String) value);
            } else if (value instanceof Long) {
                acntRid = (Long) value;
            }
            if (acntRid != null) {
                if (this.inAcntRid == null ||
                    acntRid.longValue() != inAcntRid.longValue()) {
                    this.inAcntRid = acntRid;
                    this.refreshFlag = true;
                    ProcessVariant.set("accountChanged", Boolean.TRUE);
                }
            }
        }

    }


    public void processRowSelectedList() {

        DtoQualityActivity qaParameter = (DtoQualityActivity)
                                         vwQualityActivityList
                                         .getTable().getSelectedData();
        if(qaParameter == null) {
            this.getDownArea().enableTabOnly("General");
            this.getDownArea().setSelected("General");
            this.vwGeneral.setEnable(false);
        } else {
            this.getDownArea().enableAllTabs();
             this.vwGeneral.setEnable(true);
            Parameter param = new Parameter();
            param.put("qaParameter", qaParameter);
            param.put("List", vwQualityActivityList);
            this.vwGeneral.setParameter(param);
            this.vwTestRecord.setParameter(param);
            this.vwConclusion.setParameter(param);
            this.getDownArea().getSelectedWorkArea().refreshWorkArea();
        }
    }


    public static void main(String[] args) {
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
//        session.setAttribute(IDtoAccount.SESSION_KEY, new Long(6042));
//
//        VWWorkArea w1 = new VWWorkArea();
//        VWQualityActivityMain resource = new VWQualityActivityMain();
//        w1.addTab("TEST", resource);
//        TestPanel.show(w1);
//        resource.refreshWorkArea();

    }


}
