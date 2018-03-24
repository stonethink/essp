package client.essp.pwm.workbench;

import java.awt.BorderLayout;
import java.awt.Dimension;
import client.essp.common.view.VWWorkArea;
import client.essp.pwm.workbench.workitem.VwWorkItem;
import com.wits.util.Parameter;
import client.framework.common.Global;
import java.util.Calendar;
import c2s.essp.pwm.workbench.DtoKey;
import java.util.Date;
import client.essp.tc.weeklyreport.VwWeeklyReport;
import client.framework.view.event.DataChangedListener;
import java.util.List;

public class VwWorkbench extends VWWorkArea {
    /**
     * define control variable
     */
    private boolean refreshFlag = false;

    private VwLeftBench vwLeftBench = new VwLeftBench();
    private VWWorkArea rightBench = new VWWorkArea();
    private VwWorkItem vwWorkItem = new VwWorkItem();
    private VwWeeklyReport vwWeeklyReport = new VwWeeklyReport();

    /**
     * default constructor
     */
    public VwWorkbench() {
        try {
            jbInit();
        } catch (Exception ex) {
        }

        addUICEvent();
    }

    //Component initialization
    private void jbInit() throws Exception {
        this.setPreferredSize(new Dimension(800, 600));
        this.add(vwLeftBench,BorderLayout.WEST);

        rightBench.addTab("Daily Report", vwWorkItem);
        rightBench.addTab("Weekly Report", vwWeeklyReport);
        this.add(rightBench,BorderLayout.CENTER);
    }

    private void addUICEvent(){
        vwLeftBench.getPanelCalendar().addDataChangedListener(new DataChangedListener(){
            public void processDataChanged(){
                processDataSelecCalendar();
            }
        });

        vwLeftBench.vwWorkScope.vwAccountList.addDataChangedListener(new DataChangedListener(){
            public void processDataChanged(){
                vwWorkItem.vwWorkItemList.setScopeList(vwLeftBench.vwWorkScope.vwAccountList.getScopeList());
            }
        });
    }

    void processDataSelecCalendar(){
        //System.out.println("processDataSelecCalendar");
        vwWorkItem.saveWorkArea();

        /*if( vwWorkItem.isSaveOk() == false ){

            //如果保存不成功，则日历还原
            vwLeftBench.getPanelCalendar().setSelectCalendar((Calendar)oldData);
        }else{*/

            //刷新日报list
            Parameter param = new Parameter();
            List  list = (List)vwLeftBench.getPanelCalendar().getSelectCalendars();
            if(list.size()>0){
                param.put(DtoKey.SELECTED_DATE,
                          ((Calendar) list.get(list.size() - 1)).getTime());
                vwWorkItem.setParameter(param);
                vwWorkItem.refreshWorkArea();

                vwWeeklyReport.setParameter(param);
                rightBench.getSelectedWorkArea().refreshWorkArea();
            }


        /*}*/
    }

    /////// 段2，设置参数：获取传入参数
    public void setParameter(Parameter para) {
        vwLeftBench.setParameter(para);

        vwWorkItem.setParameter(para);
        vwWeeklyReport.setParameter(para);

        this.refreshFlag = true;
    }

    /////// 段3，获取数据并刷新画面
    public void refreshWorkArea() {
        if (refreshFlag == true) {
            vwLeftBench.refreshWorkArea();

            //开始时显示今天的日报
            Date today = Global.todayDate;
            Parameter param = new Parameter();
            param.put(DtoKey.SELECTED_DATE, today);
            vwWorkItem.setParameter(param);
            vwWorkItem.refreshWorkArea(); //日报不管是不是当前卡片都要刷新，因为它有cache机制

            vwWeeklyReport.setParameter(param);
//            vwWeeklyReport.refreshWorkArea();

            rightBench.getSelectedWorkArea().refreshWorkArea();
            this.refreshFlag = false;
        }
    }

    /////// 段5，保存数据
    public void saveWorkArea() {
        vwLeftBench.saveWorkArea();
//        if(vwWorkItem.isSaveOk()){
//            vwWorkItem.saveWorkArea();
//        }
        if(rightBench.getSelectedWorkArea().isSaveOk()){
           rightBench.getSelectedWorkArea().saveWorkArea();
       }
    }

    public boolean isSaveOk(){
        return vwLeftBench.isSaveOk() && rightBench.getSelectedWorkArea().isSaveOk();
    }
}
