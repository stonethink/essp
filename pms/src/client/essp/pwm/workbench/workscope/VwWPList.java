package client.essp.pwm.workbench.workscope;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import c2s.essp.pwm.wp.DtoPwWp;
import c2s.essp.pwm.wp.DtoWSWp;
import client.essp.common.view.VWTableWorkArea;
import client.essp.pwm.wp.FPW01000PwWp;
import client.framework.model.VMColumnConfig;
import client.framework.view.vwcomp.IVWPopupEditorEvent;
import client.framework.view.vwcomp.VWJPopupEditor;
import client.framework.view.vwcomp.VWJText;
import com.wits.util.Parameter;
import client.framework.view.vwcomp.VWUtil;
import com.wits.util.StringUtil;

public class VwWPList extends VWTableWorkArea implements
    IVWPopupEditorEvent  {
    private List wpList;

    FPW01000PwWp fPW01000PwWp;
    /**
     * default constructor
     */
    public VwWPList() {
        Object[][] configs = new Object[][] { {"Work Package",
                             "scopeInfo", VMColumnConfig.UNEDITABLE, new VWJText()}
        };
        try {
            super.jbInit(configs, DtoWSWp.class);
            getTable().getTableHeader().setPreferredSize(new Dimension(100, 0)); //不显示表头
        } catch (Exception ex) {
            log.error(ex);
        }

        //setPreferredSize(new Dimension(200, 200));

        //拖放事件
        (new ScopeDragSource(getTable())).create();

        getTable().addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if( e.getClickCount() == 2 ){
                    mouseClickedWp();
                }
            }
        });
    }
    /////// 段3，获取数据并刷新画面
    protected void resetUI() {
        getTable().setRows(wpList);
    }

    public void setParameter(Parameter param){
        super.setParameter(param);
        wpList = (List)param.get("wpList");
        if( wpList == null ){
            wpList = new ArrayList();
        }
    }

    private void mouseClickedWp() {
        DtoWSWp dtoWSWp = (DtoWSWp)this.getSelectedData();
        if( dtoWSWp == null ){
            return;
        }

        if( fPW01000PwWp == null ){
            fPW01000PwWp = new FPW01000PwWp();
        }

        DtoPwWp wp = fPW01000PwWp.getGeneralWorkArea().getPwWp(dtoWSWp.getWpRid());

        if (wp != null) {
            Parameter param1 = new Parameter();
            param1.put("dtoPwWp", wp);
            this.fPW01000PwWp.setParameter(param1);
        } else {
            this.fPW01000PwWp.setParameter(new Parameter());
        }


        fPW01000PwWp.refreshWorkArea();
        VWJPopupEditor popupEditor = new VWJPopupEditor(getParentWindow(), "Work Package"
                        , fPW01000PwWp, this);
                    popupEditor.show();
    }


    public boolean onClickOK(ActionEvent e) {
        fPW01000PwWp.saveWorkAreaDirect();
        boolean isSaveOk = fPW01000PwWp.isSaveOk();
        if( isSaveOk == true ){
            DtoPwWp dto = fPW01000PwWp.getGeneralWorkArea().getDto();

            String wpCode = StringUtil.nvl(dto.getWpCode());
            String wpName = StringUtil.nvl(dto.getWpName());

            DtoWSWp selectWp = (DtoWSWp)getSelectedData();
            if( selectWp != null ){
                selectWp.setWpCode( wpCode );
                selectWp.setWpName( wpName );
            }
        }

        return isSaveOk;
    }

    public boolean onClickCancel(ActionEvent e) {
        return true;
    }

}
