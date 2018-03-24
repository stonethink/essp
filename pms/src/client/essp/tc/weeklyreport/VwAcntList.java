package client.essp.tc.weeklyreport;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.UIManager;
import javax.swing.table.TableColumn;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.tc.weeklyreport.DtoTcKey;
import c2s.essp.tc.weeklyreport.DtoWeeklyReportSumByWorker;
import client.essp.common.view.VWTableWorkArea;
import client.essp.common.view.VWWorkArea;
import client.essp.tc.common.TableListener;
import client.essp.tc.common.TcLineBorder;
import client.framework.model.VMColumnConfig;
import client.framework.model.VMTable2;
import client.framework.view.vwcomp.NodeViewManager;
import client.framework.view.vwcomp.VWJReal;
import client.framework.view.vwcomp.VWJTable;
import client.framework.view.vwcomp.VWJText;
import com.wits.util.Parameter;
import com.wits.util.TestPanel;

public class VwAcntList extends VWTableWorkArea {
    static final String actionIdList = "FTCAcntListAction";

    /**
     * define common data
     */
    private List acntList;
    private Date beginPeriod;
    private Date endPeriod;
    private String userId;
    boolean isParameterValid = true;

    public VwAcntList() {
        VWJText text = new VWJText();
        VWJReal real = new VWJReal();
        real.setMaxInputDecimalDigit(2);
        Object[][] configs = new Object[][] {
                             {"Account", "acntName", VMColumnConfig.UNEDITABLE, text},
                             {"Status", "confirmStatus", VMColumnConfig.UNEDITABLE, text},
                             {"Work Hour", "actualHour", VMColumnConfig.UNEDITABLE, text},
                             {"Comments", "comments", VMColumnConfig.UNEDITABLE, text},
        };

        try {
            model = new VMTable2(configs);
            model.setDtoType(DtoWeeklyReportSumByWorker.class);

            NodeViewManager nodeViewManager = new NodeViewManager() {
                public Color getSelectBackground() {
                    return new Color(200, 200, 200);
                }

                public Color getSelectForeground() {
                    return getForeground();
                }
            };
            table = new VWJTable(model, nodeViewManager);

            this.add(table.getScrollPane(), null);

//            this.getButtonPanel().addLoadButton(new ActionListener(){
//                public void actionPerformed(ActionEvent e){
//                    resetUI();
//                }
//            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        initUI();
    }

    //���ñ������
    private void initUI() {
        //����header�������
        HeaderRenderOvertime headerRenderOvertime = new HeaderRenderOvertime();
        for (int i = 0; i < getModel().getColumnCount(); i++) {
            TableColumn column = getTable().getColumnModel().getColumn(i);
            column.setHeaderRenderer(headerRenderOvertime);
        }
        //���������ı������񱳾�ɫͬ,����������Ĳ��־Ϳ�������
        table.getTableHeader().setBackground(table.getBackground());

        //���ù������ı߿�
        TcLineBorder tableBorder = new TcLineBorder(UIManager.getColor(
                "Table.gridColor"));
        tableBorder.setShowBottom(false);
        tableBorder.setShowRight(false);
        table.getScrollPane().setBorder(tableBorder);
    }

    public void actionPerformedLoad() {
        resetUI();
    }

    public void setParameter(Parameter param) {
        super.setParameter(param);
        this.beginPeriod = (Date) (param.get(DtoTcKey.BEGIN_PERIOD));
        this.endPeriod = (Date) (param.get(DtoTcKey.END_PERIOD));
        this.userId = (String) (param.get(DtoTcKey.USER_ID));
        if( beginPeriod == null || endPeriod ==  null ){
            isParameterValid = false;
        }else{
            isParameterValid = true;
        }
    }

    //ҳ��ˢ�£���������
    protected void resetUI() {
        if (isParameterValid == false) {

            acntList = new ArrayList();
            getTable().setRows(acntList);

            this.setVisible(false);
            fireTableChanged(TableListener.EVENT_ROW_COUNT_CHANGED);
        } else {

            InputInfo inputInfo = new InputInfo();

            inputInfo.setActionId(this.actionIdList);
            inputInfo.setInputObj(DtoTcKey.BEGIN_PERIOD, this.beginPeriod);
            inputInfo.setInputObj(DtoTcKey.END_PERIOD, this.endPeriod);
            inputInfo.setInputObj(DtoTcKey.USER_ID,this.userId);
            ReturnInfo returnInfo = accessData(inputInfo);

            if (returnInfo.isError() == false) {
                acntList = (List) returnInfo.getReturnObj(DtoTcKey.
                        WEEKLY_REPORT_LIST);

                getTable().setRows(acntList);

                if (acntList.size() > 0) {
                    this.setVisible(true);
                } else {
                    this.setVisible(false);
                }
                fireTableChanged(TableListener.EVENT_ROW_COUNT_CHANGED);
            }
        }
    }

    /**
     * "�������仯"�¼�
     * table�Ĵ�С����������̬�仯
     */
    TableListener tableListener = null;
    protected void fireTableChanged(String eventType) {
        if (tableListener != null) {
            tableListener.processTableChanged(eventType);
        }
    }

    public void addTableListeners(TableListener listener) {
        this.tableListener = listener;
    }

    //�õ���ĸ߶�
    public int getPreferredTableHeight() {
        int actualHeight = getTable().getRowCount() * VWJTable.TABLE_ROW_HEIGHT + TableWeeklyReport.HEADER_HEIGHT - 4;
        return actualHeight > getMinmizeTableHeight() ? actualHeight : getMinmizeTableHeight();
    }

    //�õ������С�߶�
    int minTableHeight = 2; //���ٿ�����ʾ��������Ĭ�Ͽ���ʾ4��
    public int getMinmizeTableHeight() {
        int rowNum = getTable().getRowCount();
        if( rowNum > minTableHeight ){
            rowNum = minTableHeight;
        }
        return rowNum * VWJTable.TABLE_ROW_HEIGHT + TableWeeklyReport.HEADER_HEIGHT - 4;
    }

    //�õ�������߶�
    int maxTableHeight = 3; //��������ʾ������,Ĭ�Ͽ���ʾ8��
    public int getMaxmizeTableHeight() {
        return maxTableHeight * VWJTable.TABLE_ROW_HEIGHT + TableWeeklyReport.HEADER_HEIGHT - 4;
    }

    public void setMaxTableHeight(int rowNum) {
        this.maxTableHeight = rowNum;
        this.setMaximumSize(new Dimension( -1, getMaxmizeTableHeight()));
    }

    public void setMinTableHeight(int rowNum) {
        this.minTableHeight = rowNum;
        this.setMinimumSize(new Dimension( -1, getMinmizeTableHeight()));
    }

    public static void main(String args[]) {
        VWWorkArea w1 = new VWWorkArea();
        VwAcntList workArea = new VwAcntList();

        w1.addTab("Account", workArea);
        TestPanel.show(w1);

        Parameter param = new Parameter();
        param.put(DtoTcKey.BEGIN_PERIOD, new Date(105,11,03));
        param.put(DtoTcKey.END_PERIOD, new Date(105,11,9));
        workArea.setParameter(param);
        workArea.refreshWorkArea();
    }

}
