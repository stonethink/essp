package client.essp.timesheet.admin.code.codevalue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import c2s.dto.ITreeNode;
import c2s.essp.timesheet.code.DtoCodeValue;
import client.essp.common.view.VWTDWorkArea;
import client.framework.view.event.RowSelectedListener;
import client.framework.view.vwcomp.VWJTreeTable;
import com.wits.util.Parameter;
import client.framework.view.event.DataChangedListener;

/**
 * <p>Title: Code Value Main View</p>
 *
 * <p>Description: 全局Code Value维护界面</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public class VwCodeValue extends VWTDWorkArea {

    private VwCodeValueList vwCodeValueList;
    private VwCodeValueGeneral vwCodeValueGeneral = new VwCodeValueGeneral();
    private VWJTreeTable treeTable;

    public VwCodeValue() {
        super(300);

        try {
            jbInit();
        } catch (Exception ex) {
            log.error(ex);
        }
        addUICEvent();
    }

    /**
     * 初始化页面
     */
    private void jbInit() {
        vwCodeValueList = new VwCodeValueList();
        treeTable = vwCodeValueList.getTreeTable();
        this.getTopArea().addTab("rsid.timesheet.jobCode", vwCodeValueList);
        this.getDownArea().addTab("rsid.common.general", vwCodeValueGeneral);
    }

    /**
     * 事件
     */
    private void addUICEvent() {
        treeTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if(e.getValueIsAdjusting() == false) {
					processCodeValueSelected();
				}
            }
        });

        vwCodeValueGeneral.addDataChangedListener(new DataChangedListener() {
            public void processDataChanged() {
                vwCodeValueList.processSaveTree();
            }
        });

        vwCodeValueGeneral.addEnableCheckBoxListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AbstractButton btn = ((AbstractButton)e.getSource());
                vwCodeValueList.processdEnableCheck(btn.isSelected());
            }
        });
    }

    /**
     * 选中行处理
     */
    private void processCodeValueSelected() {
        ITreeNode node = treeTable.getSelectedNode();
        Parameter param = new Parameter();
        if(node != null) {
            param.put(DtoCodeValue.DTO, node.getDataBean());
        }
        vwCodeValueGeneral.setParameter(param);
        vwCodeValueGeneral.refreshWorkArea();
    }

    /**
     * 传递参数
     * @param param Parameter
     */
    public void setParameter(Parameter param) {
        vwCodeValueList.setParameter(param);
    }

    /**
     * 传递刷新
     */
    public void refreshWorkArea() {
        vwCodeValueList.refreshWorkArea();
    }
}
