package client.essp.tc.pmmanage.onWeek;

import java.util.Date;

import c2s.essp.tc.weeklyreport.DtoTcKey;
import c2s.essp.tc.weeklyreport.DtoWeeklyReport;
import c2s.essp.tc.weeklyreport.DtoWeeklyReportSumByPm;
import client.essp.common.view.VWTDWorkArea;
import client.essp.common.view.VWWorkArea;
import client.essp.tc.common.TableListener;
import client.essp.tc.common.TcLayout;
import client.essp.tc.weeklyreport.VMTableWeeklyReport;
import client.essp.tc.weeklyreport.VwWeeklyReportBase;
import client.essp.tc.weeklyreport.VwWeeklyReportListOnWeekByPm;
import client.essp.tc.weeklyreport.VwWeeklyReportOnWeek;
import client.framework.view.event.RowSelectedListener;
import client.framework.view.event.RowSelectedLostListener;
import com.wits.util.Parameter;
import com.wits.util.TestPanel;
import client.framework.common.Global;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class VwTcOnWeek extends VWTDWorkArea {

    private boolean refreshFlag = false;

    public VwTcListTempOnWeek vwTcListTempOnWeek;
    public VwWeeklyReportBase vwWeeklyReport;

    public VwTcOnWeek() {
        this(new VwTcListTempOnWeek(), new VwWeeklyReportOnWeek());
    }

    public VwTcOnWeek(VwTcListTempOnWeek vwTcOnWeek, VwWeeklyReportBase vwWeeklyReport) {
        super(200);

        try {
            this.vwTcListTempOnWeek = vwTcOnWeek;
            this.vwWeeklyReport = vwWeeklyReport;
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        addUICEvent();
    }

    private void jbInit() throws Exception {
        vwTcListTempOnWeek.setBottomSpace(0);
        vwWeeklyReport.setBottomSpace(0);
        vwWeeklyReport.setReduceOrder(TcLayout.REDUCE_FROM_DOWN_TO_TOP);
        vwWeeklyReport.vwOvertimeList.setMinTableHeight(3);
        vwWeeklyReport.vwOvertimeList.setMaxTableHeight(3);

        this.getTopArea().add(vwTcListTempOnWeek);
        this.getDownArea().addTab("General", vwWeeklyReport);
    }

    /**
     * ������棺��������¼�
     */
    protected void addUICEvent() {
        //����ı��
        final VMTableTcOnWeek vmTableTcOnWeek = (VMTableTcOnWeek)this.vwTcListTempOnWeek.vwTcListOnWeek.getModel();
        final VMTableTcSumOnWeek vmTableTcSumOnWeek = (VMTableTcSumOnWeek)this.vwTcListTempOnWeek.vwTcListSumOnWeek.getModel();
        //����ı��
        final VMTableWeeklyReport vmTableWeeklyReportOnWeek = (VMTableWeeklyReport)this.vwWeeklyReport.vwWeeklyReportList.getModel();

        //�������ݹ���
        vmTableTcOnWeek.setHoursOnWeekInTheAcnt(vmTableWeeklyReportOnWeek.getHoursOnWeekInTheAcnt());
        vmTableTcOnWeek.setHoursOnWeek(vmTableWeeklyReportOnWeek.getHoursOnWeek());

        //Row lost Selected
        this.vwTcListTempOnWeek.vwTcListOnWeek
                .getTable().addRowSelectedLostListener(new
                                                       RowSelectedLostListener() {
            public boolean processRowSelectedLost(int oldSelectedRow,
                                                  Object oldSelectedNode) {
                return processRowSelectedLostList(oldSelectedRow,
                                                  oldSelectedNode);
            }
        });

        //RowSelected
        this.vwTcListTempOnWeek.vwTcListOnWeek
                .getTable().addRowSelectedListener(new
                                                   RowSelectedListener() {
            public void processRowSelected() {
                processRowSelectedList();
            }
        });

        vmTableTcOnWeek.addTableListeners(new TableListener() {
            public void processTableChanged(String eventType) {
                if (TableListener.EVENT_TOTAL_CONFIRM_DATA_CHANGED.equals(eventType)) {

                    DtoWeeklyReportSumByPm dtoSum = (DtoWeeklyReportSumByPm) vwTcListTempOnWeek.vwTcListOnWeek.getSelectedData();
                    if (dtoSum != null) {
                        String confirmStatus = dtoSum.getConfirmStatus();

                        boolean isSaveOk = true;
                        if( DtoWeeklyReport.STATUS_CONFIRMED.equals(confirmStatus) ){
                            //���pm confirmedһ���˵��ܱ������ȱ�������˵��ܱ�
                            vwWeeklyReport.vwWeeklyReportList.saveWorkArea();
                            isSaveOk = vwWeeklyReport.vwWeeklyReportList.isSaveOk();
                        }

                        if (isSaveOk == true) {

                            vwTcListTempOnWeek.vwTcListOnWeek.confirm();

                            //confirm��λ�ı仯�����������ܱ����Ŀɱ༭״̬
                            ((VwWeeklyReportListOnWeekByPm) vwWeeklyReport.vwWeeklyReportList).setConfirmStatus(confirmStatus);
                        }
                    }
                }
            }
        });

        vmTableTcSumOnWeek.addTableListeners(new TableListener() {
            public void processTableChanged(String eventType) {
                if (TableListener.EVENT_TOTAL_CONFIRM_DATA_CHANGED.equals(eventType)) {

//                    //�ȱ��浱ǰ��
//                    String confirmStatus = vmTableTcSumOnWeek.getConfirmStatus();
//                    int selectedRow = vwTcListTempOnWeek.vwTcListOnWeek.getTable().getSelectedRow();
//                    vmTableTcOnWeek.setValueAt(confirmStatus, selectedRow, vmTableTcOnWeek.CONFIRM_COLUMN_INDEX);

                        String confirmStatus = vmTableTcSumOnWeek.getConfirmStatus();

                    boolean isSaveOk = true;
                    if (isSaveOk == true) {

                        //confirm all
                        boolean isOk = vwTcListTempOnWeek.vwTcListOnWeek.confirmAll(vmTableTcSumOnWeek.getConfirmStatus());
                        if(isOk == false) {
                            vmTableTcSumOnWeek.rollbackStatus();
                            return;
                        }

                        //confirm��λ�ı仯�����������ܱ����Ŀɱ༭״̬
                        ((VwWeeklyReportListOnWeekByPm) vwWeeklyReport.vwWeeklyReportList).setConfirmStatus(confirmStatus);
                    }
                    if (DtoWeeklyReport.STATUS_CONFIRMED.equals(confirmStatus)) {
                        vwWeeklyReport.vwWeeklyReportList.saveWorkArea();
                        isSaveOk = vwWeeklyReport.vwWeeklyReportList.isSaveOk();
                    }
                }
            }
        });

        vmTableWeeklyReportOnWeek.addTableListeners(new TableListener() {
            public void processTableChanged(String eventType) {
                if (eventType.equals(TableListener.EVENT_SUM_DATA_CHANGED) == true) {
                    //�������ʱ����λӰ���������ʱ����λ
                    vwTcListTempOnWeek.vwTcListOnWeek.refreshSumHours();
                }else if(eventType.equals(TableListener.EVENT_REFRESH_ACTION) == true) {
                    //ˢ��������ʱ,Ҫˢ�µ�ǰѡ�е���
                    vwTcListTempOnWeek.vwTcListOnWeek.refreshRow();
                }
            }
        });
//
//        vwWeeklyReport.vwOvertimeList.addTableListeners(new TableListener() {
//            public void processTableChanged(String eventType) {
//                if (eventType.equals(TableListener.EVENT_SUM_DATA_CHANGED) == true) {
//                    //�������ʱ����λӰ���������ʱ����λ
//                    vwTcListTempOnWeek.vwTcListOnWeek.refreshSumHours();
//                }
//            }
//        });
    }

    /////// ��2�����ò�������ȡ�������
    public void setParameter(Parameter para) {
        this.vwTcListTempOnWeek.setParameter(para);
        this.refreshFlag = true;
    }

    /////// ��3����ȡ���ݲ�ˢ�»���
    public void refreshWorkArea() {
        if (refreshFlag == true) {

            vwTcListTempOnWeek.refreshWorkArea();

            this.refreshFlag = false;
        }
    }

    /////// ��4���¼�����
    public boolean processRowSelectedLostList(int oldSelectedRow,
                                              Object oldSelectedNode) {
        this.getDownArea().getSelectedWorkArea().saveWorkArea();
        return this.getDownArea().getSelectedWorkArea().isSaveOk();
    }

    public void processRowSelectedList() {
        Parameter param = new Parameter();
        DtoWeeklyReportSumByPm dto = (DtoWeeklyReportSumByPm) vwTcListTempOnWeek.vwTcListOnWeek.getSelectedData();
        if (dto != null) {
            Long acntRid = dto.getAcntRid();
            String userId = dto.getUserId();
            Date beginPeriod = dto.getBeginPeriod();
            Date endPeriod = dto.getEndPeriod();
            String confirmStatus = dto.getConfirmStatus();

            param.put(DtoTcKey.ACNT_RID, acntRid);
            param.put(DtoTcKey.USER_ID, userId);
            param.put(DtoTcKey.BEGIN_PERIOD, beginPeriod);
            param.put(DtoTcKey.END_PERIOD, endPeriod);
            param.put(DtoTcKey.CONFIRM_STATUS, confirmStatus);
            vwWeeklyReport.setParameter(param);
        } else {
            vwWeeklyReport.setParameter(param);
        }

        this.getDownArea().getSelectedWorkArea().refreshWorkArea();
    }


    /////// ��5����������
    public void saveWorkArea() {
        vwTcListTempOnWeek.saveWorkArea();
        if (vwTcListTempOnWeek.isSaveOk() == true) {
            this.getDownArea().getSelectedWorkArea().saveWorkArea();
        }
    }

    public boolean isSaveOk() {
        return vwTcListTempOnWeek.isSaveOk()
                && this.getDownArea().getSelectedWorkArea().isSaveOk();
    }

    public static void main(String args[]) {
        VWWorkArea workArea = new VwTcOnWeek();

        TestPanel.show(workArea);

        Parameter param = new Parameter();
        param.put(DtoTcKey.BEGIN_PERIOD, Global.todayDate);
        param.put(DtoTcKey.END_PERIOD,Global.todayDate);
        param.put(DtoTcKey.ACNT_RID, new Long(1));
        workArea.setParameter(param);
        workArea.refreshWorkArea();
    }

}
