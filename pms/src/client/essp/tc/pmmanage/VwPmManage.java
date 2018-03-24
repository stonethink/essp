package client.essp.tc.pmmanage;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.util.Date;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.tc.weeklyreport.DtoTcKey;
import client.essp.common.view.VWGeneralWorkArea;
import client.essp.common.view.VWWorkArea;
import client.essp.tc.common.TDWorkAreaSplitHeightSetter;
import client.essp.tc.common.TableListener;
import client.essp.tc.common.VWJDatePeriod;
import client.essp.tc.pmmanage.onMonth.VwTcOnMonth;
import client.essp.tc.pmmanage.onWeek.VMTableTcOnWeek;
import client.essp.tc.pmmanage.onWeek.VwTcOnWeek;
import client.framework.view.event.DataChangedListener;
import client.framework.view.vwcomp.IVWAppletParameter;
import com.wits.util.Parameter;
import com.wits.util.TestPanel;
import com.wits.util.ProcessVariant;
import com.wits.util.IVariantListener;
import java.awt.Component;
import client.framework.common.Global;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class VwPmManage extends VWGeneralWorkArea implements IVWAppletParameter {

    public VwPeriod vwPeriod = null;
    VwTcOnWeek vwTcOnWeek = null;
    VwTcOnMonth vwTcOnMonth = null;

    VWWorkArea mainWorkArea = null;
    CardLayout cardLayout = null;

    public VwPmManage() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        addUICEvent();
    }

    private void jbInit() throws Exception {
        vwPeriod = new VwPeriod();

        vwTcOnWeek = new VwTcOnWeek();
        vwTcOnMonth = new VwTcOnMonth();
        mainWorkArea = new VWWorkArea();
        cardLayout = new CardLayout();
        mainWorkArea.setLayout(cardLayout);
        mainWorkArea.add(vwTcOnWeek, String.valueOf(VWJDatePeriod.PERIOD_ONE_WEEK));
        mainWorkArea.add(vwTcOnMonth, String.valueOf(VWJDatePeriod.PERIOD_ONE_MONTH));

        VWWorkArea w = new VWWorkArea();
        w.setLayout(new BorderLayout());
        w.add(vwPeriod, BorderLayout.NORTH);
        w.add(mainWorkArea, BorderLayout.CENTER);
        w.getButtonPanel().add(vwTcOnWeek.vwTcListTempOnWeek.vwTcListOnWeek.getButtonPanel());
        w.getButtonPanel().add(vwTcOnMonth.vwTcListTempOnMonth.vwTcListOnMonth.getButtonPanel());
        vwTcOnWeek.vwTcListTempOnWeek.vwTcListOnWeek.getButtonPanel().setVisible(true);
        vwTcOnMonth.vwTcListTempOnMonth.vwTcListOnMonth.getButtonPanel().setVisible(false);

        this.addTab("TimeCard", w);
    }

    /**
     * 定义界面：定义界面事件
     */
    private void addUICEvent() {
        this.vwPeriod.addDataChangedListener(new DataChangedListener() {
            public void processDataChanged() {
                //account选择
                processDataChangedPeriod();
            }
        });

        ((VMTableTcOnWeek)this.vwTcOnWeek.vwTcListTempOnWeek.vwTcListOnWeek.getTable().getModel()).addTableListeners(new TableListener() {
            public void processTableChanged(String eventType) {
                if (TableListener.EVENT_SUM_DATA_CHANGED.equals(eventType)) {

                    //刷新统计行
                    vwTcOnMonth.vwTcListTempOnMonth.vwTcListOnMonth.setRefreshFlag();
                }else if(TableListener.EVENT_SUM_DATA_CHANGED.equals(eventType)) {

                    //刷新统计行
                    vwTcOnMonth.vwTcListTempOnMonth.vwTcListOnMonth.setRefreshFlag();
                }

            }
        });

        //项目选择事件
        ProcessVariant.addDataListener("account", new IVariantListener(){
            public void dataChanged(String varName,Object data){
                if( "account".equals( varName ) == true ){
                    Long acntRid = (Long)data;
                    vwPeriod.setSelectedAcnt(acntRid);
                }
            }
        });
    }

    protected void processDataChangedPeriod() {
        setParameter(new Parameter());

        if( isItVisible() ){
            refreshWorkArea();
        }
    }

    private boolean isItVisible(){
        Component comp = this;
        do{
            if( comp.isVisible() == false ){
                return false;
            }
        }while( (comp = comp.getParent())!=null );
        return true;
    }

    protected void resetUI(){
//        //for test
//        vwPeriod.refresh();

        Parameter param = new Parameter();
        param.put(DtoTcKey.ACNT_RID, vwPeriod.getSelectAcnt());
        param.put(DtoTcKey.BEGIN_PERIOD, vwPeriod.dtePeriod.getBeginPeriod());
        param.put(DtoTcKey.END_PERIOD, vwPeriod.dtePeriod.getEndPeriod());

        if (vwPeriod.dtePeriod.getTheType() == VWJDatePeriod.PERIOD_ONE_WEEK) {
            TDWorkAreaSplitHeightSetter.set(vwTcOnWeek, vwTcOnMonth);
            vwTcOnWeek.vwTcListTempOnWeek.vwTcListOnWeek.getButtonPanel().setVisible(true);
            vwTcOnMonth.vwTcListTempOnMonth.vwTcListOnMonth.getButtonPanel().setVisible(false);
            cardLayout.show(mainWorkArea, String.valueOf(VWJDatePeriod.PERIOD_ONE_WEEK));

            vwTcOnWeek.setParameter(param);
            vwTcOnWeek.refreshWorkArea();
        } else if (vwPeriod.dtePeriod.getTheType() == VWJDatePeriod.PERIOD_ONE_MONTH) {
            TDWorkAreaSplitHeightSetter.set(vwTcOnMonth, vwTcOnWeek);
            vwTcOnWeek.vwTcListTempOnWeek.vwTcListOnWeek.getButtonPanel().setVisible(false);
            vwTcOnMonth.vwTcListTempOnMonth.vwTcListOnMonth.getButtonPanel().setVisible(true);
            cardLayout.show(mainWorkArea, String.valueOf(VWJDatePeriod.PERIOD_ONE_MONTH));

            vwTcOnMonth.setParameter(param);
            vwTcOnMonth.refreshWorkArea();
        }
    }

    protected ReturnInfo accessDataT(InputInfo inputInfo) {
        ReturnInfo returnInfo = new ReturnInfo();
        returnInfo.setReturnObj(DtoTcKey.ACNT_Name, "essp");
        returnInfo.setReturnObj(DtoTcKey.ACNT_RID, new Long(1));
        returnInfo.setReturnObj(DtoTcKey.TODAY_DATE, Global.todayDate);
        return returnInfo;
    }

    public String[][] getParameterInfo() {
        return new String[0][0];
    }

    public static void main(String args[]) {
        VWWorkArea workArea = new VwPmManage();

        TestPanel.show(workArea);

        Parameter param = new Parameter();
        workArea.setParameter(param);
        workArea.refreshWorkArea();
    }

}
