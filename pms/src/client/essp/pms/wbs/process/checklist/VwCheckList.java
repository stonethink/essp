package client.essp.pms.wbs.process.checklist;

import java.awt.AWTEvent;
import java.awt.Dimension;

import c2s.essp.common.code.DtoKey;
import c2s.essp.pms.qa.DtoQaCheckPoint;
import c2s.essp.pms.wbs.DtoWbsActivity;
import client.essp.common.view.VWTDWorkArea;
import client.framework.view.event.RowSelectedListener;
import client.framework.view.event.RowSelectedLostListener;
import com.wits.util.Parameter;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author lipeng.xu
 * @version 1.0
 */
public class VwCheckList extends VWTDWorkArea {

    /**
     * define control variable
     */
    private boolean refreshFlag = false;
    private String entryFunType;

    /**
     * define common data (globe)
     */
    DtoWbsActivity dtoWbsActivity;

    /////// 段1，定义界面：定义界面（定义控件，设置控件名称，光标控制顺序）、定义界面事件
    VwCheckPointList vwCheckPointList;
    VwCheckActionList vwCheckActionList;

    /**
     * default constructor
     */
    public VwCheckList() {
//        super(350);
        super(500);
        enableEvents(AWTEvent.WINDOW_EVENT_MASK);
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        addUICEvent();
    }

    //Component initialization
    private void jbInit() throws Exception {
        this.setPreferredSize(new Dimension(700, 470));
        this.setHorizontalSplit();

        vwCheckPointList = new VwCheckPointList();
        vwCheckActionList = new VwCheckActionList();

        this.getTopArea().addTab("Check Point", vwCheckPointList);
        this.getDownArea().addTab("Check Action", vwCheckActionList);
    }

    /**
     * 定义界面：定义界面事件
     */
    private void addUICEvent() {
        this.vwCheckPointList.getTable().addRowSelectedLostListener(new RowSelectedLostListener() {
            public boolean processRowSelectedLost(int oldSelectedRow, Object oldSelectedData) {
                return processRowSelectedLostList(oldSelectedRow, oldSelectedData);
            }
        });

        this.vwCheckPointList.getTable().addRowSelectedListener(new RowSelectedListener() {
            public void processRowSelected() {
                processRowSelectedList();
            }
        });
    }

    /////// 段2，设置参数：获取传入参数
    public void setParameter(Parameter param) {
        dtoWbsActivity = (DtoWbsActivity) param.get(DtoKey.DTO);
        entryFunType = (String) param.get("entryFunType");
        this.refreshFlag = true;
    }

    /////// 段3，获取数据并刷新画面
    //最外层的workArea不需要刷新自己
    public void refreshWorkArea() {
        if (this.refreshFlag == true) {
            Parameter param = new Parameter();
            param.put(DtoKey.DTO, dtoWbsActivity);
            param.put("entryFunType", entryFunType);
            vwCheckPointList.setParameter(param);
            vwCheckPointList.refreshWorkArea();

            this.refreshFlag = false;
        }
    }

    public void fireNeedRefresh() {
        refreshFlag = true;
    }


    /////// 段4，事件处理
    public boolean processRowSelectedLostList(int oldSelectedRow, Object oldSelectedData){
        this.vwCheckActionList.saveWorkArea();
        return this.vwCheckActionList.isSaveOk();
    }

    public void processRowSelectedList() {
        DtoQaCheckPoint dataBean = (DtoQaCheckPoint)this.vwCheckPointList.getSelectedData();
        Parameter param = new Parameter();
        param.put(DtoKey.DTO, dtoWbsActivity);
        param.put(DtoQaCheckPoint.DTO_PMS_CHECK_POINT, dataBean);
        param.put("entryFunType", entryFunType);
        vwCheckActionList.setParameter(param);
        this.getDownArea().getSelectedWorkArea().refreshWorkArea();
    }

    /////// 段5，保存数据
    public void saveWorkArea() {
        vwCheckPointList.saveWorkArea();
        if( vwCheckPointList.isSaveOk() == true ){
            this.getDownArea().getSelectedWorkArea().saveWorkArea();
        }
    }

    public boolean isSaveOk(){
        return vwCheckPointList.isSaveOk()
            && this.getDownArea().getSelectedWorkArea().isSaveOk();
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
//
//
//        VWWorkArea workArea = new VwCheckList();
//
//        DtoWbsActivity dtoWbsActivity = new DtoWbsActivity();
//        dtoWbsActivity.setAcntRid(new Long(111));
//        dtoWbsActivity.setActivityRid(new Long(111));
//        dtoWbsActivity.setActivity(true);
//
//        Parameter param = new Parameter();
//        param.put(DtoKey.DTO, dtoWbsActivity);
//        workArea.setParameter(param);
//        workArea.refreshWorkArea();
//        TestPanel.show(workArea);
    }

}
