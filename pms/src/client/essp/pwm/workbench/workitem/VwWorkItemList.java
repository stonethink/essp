



package client.essp.pwm.workbench.workitem;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableColumn;

import c2s.dto.DtoUtil;
import c2s.dto.IDto;
import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.pwm.workbench.DtoKey;
import c2s.essp.pwm.workbench.DtoPwWkitem;
import c2s.essp.pwm.workbench.DtoPwWkitemPeriod;
import client.essp.common.view.VWWorkArea;
import client.essp.pwm.workbench.period.VwPeriodDef;
import client.framework.common.Constant;
import client.framework.model.VMColumnConfig;
import client.framework.view.common.comMSG;
import client.framework.view.vwcomp.IVWPopupEditorEvent;
import client.framework.view.vwcomp.VWJDisp;
import client.framework.view.vwcomp.VWJPopupEditor;
import client.framework.view.vwcomp.VWJReal;
import client.framework.view.vwcomp.VWJTable;
import client.framework.view.vwcomp.VWJText;
import com.wits.util.Parameter;
import com.wits.util.StringUtil;
import com.wits.util.TestPanel;
import com.wits.util.comDate;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import client.framework.view.vwcomp.VWJButton;
import java.util.*;
import c2s.essp.pwm.wp.DtoWSAccount;
import c2s.essp.pwm.wp.IDtoScope;
import c2s.essp.pwm.wp.DtoWSActivity;
import c2s.essp.pwm.wp.DtoWSWp;
import client.framework.common.Global;
import c2s.essp.pwm.PwmUtil;
import java.math.BigDecimal;

public class VwWorkItemList extends VwWorkItemListBase {
    //parameter
    Date selectedDate = null;

    /**
     * define common data
     */
    List wkitemList = new ArrayList();
    List scopeList = new ArrayList();

    VMTableWorkItem modelWkItem = null;
    private boolean isSaveOk = true;
    private boolean isParameterValid = false;
    CellEditorTimeBar cellEditorTimeBar = new CellEditorTimeBar();
    CellRenderTimeBar cellRenderTimeBar = new CellRenderTimeBar();
    HeaderRenderTimeBar headerRenderTimeBar = new HeaderRenderTimeBar();

    VwDailyReportList vwDailyReportList = null;
    VwWorkItemBelongTo vwWorkItemBelongTo = null;
    VwPeriodDef vwPeriodDef = null;

    VWJPopupEditor poputEditor;
    public VwWorkItemList() {

        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        addUICEvent();
        setEnabledMode();
    }

    private void jbInit() throws Exception {
        VWJText text = new VWJText();

        Object[][] configs = new Object[][] { {"Work Item", "wkitemName", VMColumnConfig.EDITABLE, text}
                             , {"Time Range", "", VMColumnConfig.EDITABLE, null}
                             , {"Work Hour", "wkitemWkhours", VMColumnConfig.EDITABLE, new VWJReal()}
                             , {"Belong To", "wkitemBelongto", VMColumnConfig.UNEDITABLE, new VWJDisp()}
        };

        this.modelWkItem = new VMTableWorkItem(configs);
        model = modelWkItem;
        table = new VWJTable(model, new WkitemNodeViewManager());
        modelWkItem.setTable(table);
        this.add(table.getScrollPane());

        setRenderAndEditor();

        getTable().setRowHeight(22);
        getTable().getTableHeader().setReorderingAllowed(false);
        //        getTable().getColumnModel().getColumn(0).setMinWidth(250);
        //        getTable().getColumnModel().getColumn(0).setMaxWidth(250);
        getTable().getColumnModel().getColumn(0).setPreferredWidth(250);
        //        getTable().getColumnModel().getColumn(1).setMinWidth(300);
        //        getTable().getColumnModel().getColumn(1).setMaxWidth(300);
        getTable().getColumnModel().getColumn(1).setPreferredWidth(300);
        //        getTable().getColumnModel().getColumn(2).setMinWidth(80);
        //        getTable().getColumnModel().getColumn(2).setMaxWidth(80);
        getTable().getColumnModel().getColumn(2).setPreferredWidth(80);
        //        getTable().getColumnModel().getColumn(3).setMinWidth(150);
        //        getTable().getColumnModel().getColumn(3).setMaxWidth(150);
        getTable().getColumnModel().getColumn(3).setPreferredWidth(150);
        getTable().getTableHeader().setReorderingAllowed(false);

        adjScales();

        //接受拖放事件
        (new WkitemDropTarget(getTable())).create();
    }

    protected void setRenderAndEditor() {
        TableColumn columnTime = getTable().getColumnModel().getColumn(1);
        columnTime.setHeaderRenderer(headerRenderTimeBar);
        columnTime.setCellRenderer(cellRenderTimeBar);
        columnTime.setCellEditor(cellEditorTimeBar);
    }

    private void addUICEvent() {
        //捕获事件－－－－
        getModel().addTableModelListener(new TableModelListener() {
            public void tableChanged(TableModelEvent e) {
                tableModelChanged(e);
            }
        });

        this.getTable().addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                mouseClickedTable(e);
            }
        });
    }

    protected void tableModelChanged(TableModelEvent e) {
        //System.out.println("tableModelChanged() - " + e.getType() );
        adjScales();
    }

    public void adjScales() {
        //System.out.println("adjScales()");
        int oldMin = (int)this.headerRenderTimeBar.getPanelTimeBar().getTimeBar().
                     getMinValue();
        int oldMax = (int)this.headerRenderTimeBar.getPanelTimeBar().getTimeBar().
                     getMaxValue() - 1;
        int[] timeRange = modelWkItem.getMinStartAndMaxFinish();
        int minHour = timeRange[0];
        int maxHour = timeRange[1];

        if (minHour != oldMin || maxHour != oldMax) {
            this.cellRenderTimeBar.getPanelTimeBar().getTimeBar().setMaxValue(maxHour + 1);
            this.cellRenderTimeBar.getPanelTimeBar().getTimeBar().setMinValue(minHour);
            this.cellEditorTimeBar.getPanelTimeBar().getTimeBar().setMaxValue(maxHour + 1);
            this.cellEditorTimeBar.getPanelTimeBar().getTimeBar().setMinValue(minHour);
            this.headerRenderTimeBar.getPanelTimeBar().getTimeBar().setMaxValue(maxHour + 1);
            this.headerRenderTimeBar.getPanelTimeBar().getTimeBar().setMinValue(minHour);
            this.getTable().getTableHeader().repaint();
            this.getTable().repaint();
        }
    }

    protected void actionPerformedDel(ActionEvent e) {
        if (getSelectedData() == null) {
            return;
        }

        int option = comMSG.dispConfirmDialog("Do you delete the data?");
        if (option == Constant.OK) {
            getTable().deleteRow();
        }
    }

    protected void actionPerformedSave(ActionEvent e) {
        if (checkModified()) {
            if (validateData() == true) {
                saveData(DtoKey.UPDATE);
            }
        }
    }

    public void actionPerformedLoad(ActionEvent e) {
        resetUI(DtoKey.RELOAD);
    }

    protected void actionPerformedBtnDailyReport(ActionEvent e) {
        if (checkHaveDailyReport()) {
            if (validateData() == true) {
                boolean isOk = saveData(DtoKey.SUBMIT_DAILY_REPORT);
                if (isOk) {
                    comMSG.dispMessageDialog(this, "Submit dialy report ok!");
                }
            }
        }
    }

    protected void actionPerformedCustom(ActionEvent e) {
        DtoPwWkitem selectDto = (DtoPwWkitem)this.getSelectedData();
        if( selectDto == null ){
            comMSG.dispErrorDialog("Please select the work item to duplicate.\r\nYou'd better choose a saved work item.");
            return;
        }

        if( selectDto.isChanged() == true ){
            comMSG.dispMessageDialog("Please save the work item to duplicate first.");
            return;
        }

         if( vwPeriodDef == null ){
             vwPeriodDef = new VwPeriodDef();
         }

         vwPeriodDef.setStartDate(this.selectedDate);

         poputEditor = new VWJPopupEditor(
             getParentWindow(), "Customize"
             , vwPeriodDef
             , new IVWPopupEditorEvent() {
             public boolean onClickOK(ActionEvent e) {
                 return true;
             }

             public boolean onClickCancel(ActionEvent e) {
                 return true;
             }
         });
         JButton btnDelCustom = null;

         btnDelCustom = new VWJButton("Delete Period");
         btnDelCustom.setToolTipText("Delete repeat.");
         this.getButtonPanel().add(btnDelCustom);
         btnDelCustom.addActionListener(new ActionListener() {
             public void actionPerformed(ActionEvent e) {
                 actionPerformedDelCustom(e);
             }
        });
        Dimension dim = new Dimension(104, 32);
        btnDelCustom.setPreferredSize(dim);
        btnDelCustom.setSize(dim);
        poputEditor.getButtonPanel().add(btnDelCustom);

         int i = poputEditor.showConfirm();
         if( i == Constant.OK ){
             Iterator it = vwPeriodDef.getPeriodList();
             if( it.hasNext() == false ){
                 return;
             }

             DtoPwWkitemPeriod dtoPeriod = new DtoPwWkitemPeriod();
             DtoUtil.copyBeanToBean(dtoPeriod, selectDto);
             while( it.hasNext() ){
                 Calendar day = (Calendar)it.next();
                 if( day != null ){
                     dtoPeriod.add(day);
                     //System.out.println(comDate.dateToString(day.getTime(), "yyyy-MM-dd"));
                 }
             }

             InputInfo inputInfo = new InputInfo();
             inputInfo.setActionId(DtoKey.ACTION_WKITEM_LIST);
             inputInfo.setFunId(DtoKey.CUSTOM);
             inputInfo.setInputObj(DtoKey.SELECTED_DATE, this.selectedDate);
             inputInfo.setInputObj(DtoKey.DTO, dtoPeriod);
             ReturnInfo returnInfo = accessData(inputInfo);
             if( returnInfo.isError() == false ){
                 selectDto.setOp(IDto.OP_NOCHANGE);
                 comMSG.dispMessageDialog(getParent(), "Repeat ok.");
             }
         }
    }

    protected void actionPerformedDelCustom(ActionEvent e){
        if( poputEditor != null ){
            poputEditor.dispose();
        }

        DtoPwWkitem dto = (DtoPwWkitem)this.getSelectedData();
        if( dto == null ){
            return;
        }

        if( dto.isInsert() == true ){
            int option = comMSG.dispConfirmDialog("Do you delete the data?");
            if( option == Constant.OK ){
                getTable().deleteRow();
            }
        }else{

            int option = comMSG.dispConfirmDialog("Do you delete the work items copied from the selected work item?");
            if (option == Constant.OK) {
                InputInfo inputInfo = new InputInfo();
                inputInfo.setActionId(DtoKey.ACTION_WKITEM_LIST);
                inputInfo.setFunId(DtoKey.DEL_CUSTOM);
                inputInfo.setInputObj(DtoKey.DTO, dto);

                ReturnInfo returnInfo = accessData(inputInfo);
                if (returnInfo.isError() == false) {
                    resetUI(DtoKey.RELOAD);
                    comMSG.dispMessageDialog(getParent(), "Delete finish.");

                }
            }
        }
    }

    public void initWorkItemFromScope(){
        for (Iterator iter = scopeList.iterator(); iter.hasNext(); ) {
            DtoWSAccount account = (DtoWSAccount)iter.next();

            if( account.getActivityList() != null ){
                for (Iterator iter2 = account.getActivityList().iterator(); iter2.hasNext(); ) {
                    DtoWSActivity item = (DtoWSActivity) iter2.next();
                    if( isExist(item) == false ){
                        addInitWkitem(item);
                    }
                }
            }

            if( account.getWpList() != null ){
                for (Iterator iter2 = account.getWpList().iterator(); iter2.hasNext(); ) {
                    DtoWSWp item = (DtoWSWp) iter2.next();
                    if( isExist(item) == false ){
                        addInitWkitem(item);
                    }
                }
            }
        }

        this.getTable().setRows(this.wkitemList);
    }

    private boolean isExist(IDtoScope scope){
        boolean isExist = false;
        for (Iterator iter2 = wkitemList.iterator(); iter2.hasNext(); ) {
            DtoPwWkitem wkitem = (DtoPwWkitem) iter2.next();

            if (wkitem.getActivityId() != null
                && wkitem.getActivityId().equals(scope.getActivityRid())) {
                return true;
            }

            if (wkitem.getWpId() != null
                && wkitem.getWpId().equals(scope.getWpRid())) {
                return true;
            }
        }

        return false;
    }

    private void addInitWkitem(IDtoScope scope){
        DtoPwWkitem dto = new DtoPwWkitem();
        dto.setActivityId(scope.getActivityRid());
        dto.setProjectId(scope.getAcntRid());
        dto.setWpId(scope.getWpRid());
        dto.setWkitemBelongto(scope.getScopeInfo());
        dto.setWkitemDate(this.selectedDate);
        dto.setWkitemOwner(Global.userId);
        dto.setWkitemName("");

        Calendar minHMS = PwmUtil.getMinHMS();
        minHMS.set(Calendar.HOUR_OF_DAY, 8);
        dto.setWkitemStarttime(minHMS.getTime());
        dto.setWkitemFinishtime(minHMS.getTime());
        dto.setWkitemWkhours(new BigDecimal(0));
        dto.setWkitemIsdlrpt("0");
        dto.setOp(IDto.OP_INSERT);

        this.wkitemList.add(dto);
    }

    protected void mouseClickedLblSelDate(MouseEvent e) {
        if (e.getClickCount() == 2) {
            if (vwDailyReportList == null) {
                vwDailyReportList = new VwDailyReportList();
            }

            Parameter p = new Parameter();
            p.put(DtoKey.SELECTED_DATE, this.selectedDate);
            vwDailyReportList.setParameter(p);
            vwDailyReportList.refreshWorkArea();

            vwDailyReportList.setPreferredSize(new Dimension(640, 480));
            javax.swing.JOptionPane.showMessageDialog(this, vwDailyReportList, "Daily Rreport", 1, null);
        }
    }

    protected void mouseClickedTable(MouseEvent e) {
                if( e.getClickCount() != 2 ){
                        return ;
                }

        int col = getTable().getSelectedColumn();
        if (col != 3) {
            return;
        }

        //click 'belong to'
        if( getTable().getSelectedRow() < 0 ){
            return;
        }

        DtoPwWkitem selectWkitem = (DtoPwWkitem) getTable().getSelectedData();
        Long acntRid = null;
        Long activityRid = null;
        String wpName = "";

        if (selectWkitem != null) {
            acntRid = selectWkitem.getProjectId();
            activityRid = selectWkitem.getActivityId();
            if (selectWkitem.getWpId() != null) {

                //如果wpId不为null,则belongTo为wp的name.所以这里可以这样取
                wpName = selectWkitem.getWkitemBelongto();
            }
        }

        if (vwWorkItemBelongTo == null) {
            vwWorkItemBelongTo = new VwWorkItemBelongTo();
        }

        vwWorkItemBelongTo.setParameter(acntRid, activityRid, wpName);
        VWJPopupEditor poputEditor = new VWJPopupEditor(
            getParentWindow(), "Belong To"
            , vwWorkItemBelongTo
            , new IVWPopupEditorEvent() {

            public boolean onClickOK(ActionEvent e) {
//                DtoPwWkitem dto = (DtoPwWkitem) getTable().getSelectedData();
//                if (dto != null) {
//                    Long acntRid = vwWorkItemBelongTo.getAcntRid();
//                    if (StringUtil.nvl(dto.getProjectId()).equals(
//                        StringUtil.nvl(acntRid)) == false) {
//                        dto.setProjectId(acntRid);
//                        dto.setOp(IDto.OP_MODIFY);
//                    }
//
//                    Long activityRid = vwWorkItemBelongTo.getActivityRid();
//                    if (StringUtil.nvl(dto.getActivityId()).equals(
//                        StringUtil.nvl(activityRid)) == false) {
//                        dto.setActivityId(activityRid);
//                        dto.setOp(IDto.OP_MODIFY);
//                    }
//
//                    String belongTo = vwWorkItemBelongTo.getBelongTo();
//                    if (StringUtil.nvl(dto.getWkitemBelongto()).equals(
//                        StringUtil.nvl(belongTo)) == false) {
//                        dto.setWkitemBelongto(vwWorkItemBelongTo.getBelongTo());
//                        dto.setOp(IDto.OP_MODIFY);
//                    }
//                }
                return true;
            }

            public boolean onClickCancel(ActionEvent e) {
                return true;
            }
        });

        int i = poputEditor.showConfirm();
        if( i == Constant.OK ){

            String belongTo = vwWorkItemBelongTo.getBelongTo();
            getModel().setValueAt(belongTo, getTable().getSelectedRow(), 3);

            DtoPwWkitem dto = (DtoPwWkitem) getTable().getSelectedData();
            if (dto != null) {
                Long newAcntRid = vwWorkItemBelongTo.getAcntRid();
                if (StringUtil.nvl(dto.getProjectId()).equals(
                    StringUtil.nvl(newAcntRid)) == false) {
                    dto.setProjectId(newAcntRid);
                    dto.setOp(IDto.OP_MODIFY);
                }

                Long newActivityRid = vwWorkItemBelongTo.getActivityRid();
                if (StringUtil.nvl(dto.getActivityId()).equals(
                    StringUtil.nvl(newActivityRid)) == false) {
                    dto.setActivityId(newActivityRid);
                    dto.setOp(IDto.OP_MODIFY);
                }
            }

        }
    }

    private boolean checkHaveDailyReport() {
        boolean checkFlage = false;
        StringBuffer msg = new StringBuffer("");
        for (int i = 0; i < wkitemList.size(); i++) {
            DtoPwWkitem dto = (DtoPwWkitem) wkitemList.get(i);

            if (StringUtil.nvl(dto.getWkitemName()).equals("") == true) {
                msg.append("The " + (i + 1) +
                           " row： Must input work item.\r\n");
            }
            if (dto.isChanged() == true
                || dto.isdlrpt() == false
                ) {
                checkFlage = true;
            }
        }
        if (msg.toString().equals("") == false) {
            comMSG.dispErrorDialog(msg.toString());
            return false;
        } else {
            return checkFlage;
        }
    }

    private boolean checkModified() {
        for (Iterator it = this.wkitemList.iterator();
                           it.hasNext(); ) {
            DtoPwWkitem dto = (DtoPwWkitem) it.next();

            if (dto.isChanged() == true) {
                return true;
            }
        }

        return false;
    }

    private boolean validateData() {
        boolean bValid = true;
        StringBuffer msg = new StringBuffer("");

//        for (int i = 0; i < this.getModel().getRows().size(); i++) {
//            DtoPwWkitem dto = (DtoPwWkitem)this.getModel().getRows().get(i);
//            if (StringUtil.nvl(dto.getWkitemName()).equals("") == true) {
//                msg.append("Row " + (i + 1) + " ： Must input the name of work item.\r\n");
//                bValid = false;
//            }
//        }

        if (bValid == false) {
            comMSG.dispErrorDialog(msg.toString());
        }

        return bValid;
    }

    private boolean saveData(String funId) {
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(DtoKey.ACTION_WKITEM_LIST);
        inputInfo.setFunId(funId);
        inputInfo.setInputObj(DtoKey.WKITEM_LIST, wkitemList);
        inputInfo.setInputObj(DtoKey.SELECTED_DATE, this.selectedDate);

        ReturnInfo returnInfo = accessData(inputInfo);
        if (returnInfo.isError() == false) {
            wkitemList = (List) returnInfo.getReturnObj(DtoKey.WKITEM_LIST);

            this.getTable().setRows(wkitemList);
            wkitemList=getModel().getDtoList();
            return true;
        } else {
            return false;
        }
    }

    public void setParameter(Parameter p) {
        super.setParameter(p);
        selectedDate = (Date) p.get(DtoKey.SELECTED_DATE);
        if (selectedDate == null) {
            isParameterValid = false;
        } else {
            isParameterValid = true;
        }

        modelWkItem.setWkitemDate(selectedDate);
    }

    //页面刷新－－－－－
    protected void resetUI() {
        resetUI(DtoKey.SELECT);
    }

    protected void resetUI(String funId) {
        //System.out.println("resetUI()");
        setEnabledMode();

        if (isParameterValid == false) {
            wkitemList = new ArrayList();
            getTable().setRows(wkitemList);
            wkitemList=getModel().getDtoList();
            return;
        }

        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(DtoKey.ACTION_WKITEM_LIST);
        inputInfo.setFunId(funId);
        inputInfo.setInputObj(DtoKey.SELECTED_DATE, this.selectedDate);

        ReturnInfo returnInfo = accessData(inputInfo);

        if (returnInfo.isError() == false) {
            wkitemList = (List) returnInfo.getReturnObj(DtoKey.WKITEM_LIST);
            getTable().setRows(wkitemList);
            wkitemList=getModel().getDtoList();
        }

    }

    private void setEnabledMode() {

        if (true == true) {
            for (int i = 0; i < modelWkItem.getColumnConfigs().size()-1; i++) {
                VMColumnConfig config = (VMColumnConfig) modelWkItem.getColumnConfigs().get(i);
                config.setEditable(VMColumnConfig.EDITABLE);
            }
        } else {
            for (int i = 0; i < modelWkItem.getColumnConfigs().size()-1; i++) {
                VMColumnConfig config = (VMColumnConfig) modelWkItem.getColumnConfigs().get(i);
                config.setEditable(VMColumnConfig.UNEDITABLE);
            }
        }
    }

    public void saveWorkArea() {
        saveData(DtoKey.SAVE_TEMP_DATA);
        isSaveOk = true;
    }

    public boolean isSaveOk() {
        return this.isSaveOk;
    }

    public void setScopeList(List scopeList){
        this.scopeList = scopeList;
    }

    public static void main(String args[]) {
        VWWorkArea w1 = new VWWorkArea();
        VwWorkItemList workArea = new VwWorkItemList();

        w1.addTab("Dialy Report", workArea);
        TestPanel.show(w1);

        Parameter param = new Parameter();
        Calendar c = Calendar.getInstance();
        c.set(2005, 9, 10);
        param.put("selectedDate", c.getTime());
        workArea.setParameter(param);
        workArea.refreshWorkArea();
    }

}
