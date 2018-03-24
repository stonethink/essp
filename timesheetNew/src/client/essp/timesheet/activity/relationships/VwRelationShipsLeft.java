package client.essp.timesheet.activity.relationships;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import c2s.dto.DtoBase;
import client.essp.common.view.VWTableWorkArea;
import client.framework.model.VMColumnConfig;
import client.framework.view.vwcomp.VWJRadioButton;
import com.wits.util.IVariantListener;
import client.framework.view.vwcomp.VWJText;
import c2s.dto.ReturnInfo;
import java.util.List;
import c2s.dto.InputInfo;
import com.wits.util.Parameter;
import java.util.ArrayList;
import c2s.essp.timesheet.activity.DtoActivityKey;
import client.framework.view.vwcomp.VWUtil;
import java.awt.Dimension;
/**
 * <p>Title: VwRelationShipsLeft</p>
 *
 * <p>Description: RelationShipList卡片</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author TBH
 * @version 1.0
 */
public class VwRelationShipsLeft extends VWTableWorkArea implements
    IVariantListener {
    static final String actionIdInit = "FTSRelationShipsList";
    Object[][] configs = null;
    static final String treeColumnName = "Job Code";
    VWJRadioButton preJobRadio = new VWJRadioButton();
    VWJRadioButton afterJobRadio = new VWJRadioButton();
    private Long activityRid = null;
    JButton btnFresh = new JButton();
    public VwRelationShipsLeft() {
        try {
            jbInit();
            addUICEvent();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //初始化
    private void jbInit() throws Exception {
        preJobRadio.setSelected(true);
        VWJText text = new VWJText();
        preJobRadio.setText("rsid.timesheet.preJob");
        afterJobRadio.setText("rsid.timesheet.afterJob");
        preJobRadio.setPreferredSize(new Dimension(100, 20));
        afterJobRadio.setPreferredSize(new Dimension(100, 20));
        configs = new Object[][] { {"rsid.timesheet.jobCode", "activityId",
                  VMColumnConfig.UNEDITABLE, text}, {"rsid.timesheet.jobName", "activityName",
                  VMColumnConfig.UNEDITABLE, text}, {"rsid.timesheet.projectId", "projectId",
                  VMColumnConfig.UNEDITABLE, text}, {"rsid.timesheet.relationType", "type",
                  VMColumnConfig.UNEDITABLE, text}, {"rsid.timesheet.lag", "lag",
                  VMColumnConfig.UNEDITABLE, text},
        };

        super.jbInit(configs, DtoBase.class);
        //设置初始列宽
        JTableHeader header = this.getTable().getTableHeader();
//      可排序
        this.getTable().setSortable(true);
        TableColumnModel tcModel = header.getColumnModel();
        tcModel.getColumn(0).setPreferredWidth(200);
    }

   //重置
    protected void resetUI() {
        VWUtil.clearUI(this);
        if (activityRid == null) {
            return;
        }

        boolean isPre = false;
        if (preJobRadio.isSelected()) {
            isPre = true;
        }
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(this.actionIdInit);
        inputInfo.setInputObj(DtoActivityKey.DTO_RID, activityRid);
        inputInfo.setInputObj("IsPre", isPre);
        ReturnInfo returnInfo = accessData(inputInfo);
        if (returnInfo.isError() == true) {
            return;
        }
        List relationShipslist = (List) returnInfo.getReturnObj(DtoActivityKey.RELATION_LIST);
        if (relationShipslist == null) {
            relationShipslist = new ArrayList();
        }
        getTable().setRows(relationShipslist);
        if (relationShipslist != null && relationShipslist.size() > 0) {
            getTable().setSelectRow(0);
        }
    }

   //事件处理
    private void addUICEvent() {
        getButtonPanel().add(preJobRadio);
        getButtonPanel().add(afterJobRadio);
        preJobRadio.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedSelectPre();
            }
        });
        afterJobRadio.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedSelectAft();
            }
        });
        
        //刷新
        btnFresh = this.getButtonPanel().addButton("refresh.png");
        btnFresh.setToolTipText("rsid.common.refresh");
        btnFresh.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
                    actionPerformedFefrsh(e);
              }
          });
    }
    private void actionPerformedFefrsh(ActionEvent e){
        resetUI();
  }

    //前置作业
    public void actionPerformedSelectPre() {
        if (preJobRadio.isSelected()) {
            afterJobRadio.setSelected(false);
        }
        preJobRadio.setSelected(true);
        resetUI();
    }

    //后置作业
    public void actionPerformedSelectAft() {
        if (afterJobRadio.isSelected()) {
            preJobRadio.setSelected(false);
        }
        afterJobRadio.setSelected(true);
        resetUI();
    }

    //参数设置
   public void setParameter(Parameter param){
       activityRid = (Long)param.get(DtoActivityKey.DTO_RID);
       super.setParameter(param);
   }

    public void dataChanged(String varName, Object data) {
    }
}
