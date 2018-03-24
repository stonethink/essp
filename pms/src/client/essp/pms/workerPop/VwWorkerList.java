package client.essp.pms.workerPop;

import java.util.ArrayList;
import java.util.List;

import c2s.dto.DtoUtil;
import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.pms.account.DtoAcntLoaborRes;
import c2s.essp.pms.wbs.DtoActivityWorker;
import client.essp.common.view.VWTableWorkArea;
import client.essp.pms.activity.wp.WorkersDragSource;
import client.framework.model.VMColumnConfig;
import client.framework.view.vwcomp.VWJText;
import com.wits.util.Parameter;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class VwWorkerList extends VWTableWorkArea {
    public static final String ACTIONID_WORKS_LIST =
        "FAcntLaborResourceListAction";
    protected List wkrList = new ArrayList();
    protected Long acntRid = null;
    public VwWorkerList() {
        Object[][] configs = { {"Login Name", "loginId",
                             VMColumnConfig.UNEDITABLE, new VWJText(),
                             Boolean.FALSE}, {"Name", "empName",
                             VMColumnConfig.UNEDITABLE, new VWJText(),
                             Boolean.FALSE}, };
        try {
            super.jbInit(configs, DtoActivityWorker.class);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        getTable().setSortable(true);
//        getTable().getTableHeader().setPreferredSize(new Dimension(100, 0)); //不显示表头
        //拖放事件
        (new WorkersDragSource(getTable())).create();
    }

    public void setParameter(Parameter param) {
        this.acntRid = (Long) param.get("acntRid");
    }

    public void resetUI() {
        if (acntRid == null) {
            return;
        }
        wkrList = new ArrayList();
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(this.ACTIONID_WORKS_LIST);
        inputInfo.setInputObj("acntRid", acntRid);
        ReturnInfo returnInfo = accessData(inputInfo);
        if (returnInfo.isError() == false) {
            List laborResourceList = (List) returnInfo.getReturnObj(
                "laborResourceList");
         for(int i = 0;i < laborResourceList.size();i++) {
             DtoAcntLoaborRes loabor = (DtoAcntLoaborRes)laborResourceList.get(i);
             if(loabor.getResStatus().equals(DtoAcntLoaborRes.RESOURCE_STATUS_IN)) {
                DtoActivityWorker worker = new DtoActivityWorker();
                try {
                     DtoUtil.copyProperties(worker, loabor);
                 } catch (Exception ex) {
                     ex.printStackTrace();
                 }
                 wkrList.add(worker);
             }
         }
         this.getTable().setRows(this.wkrList);
        }
    }


}
