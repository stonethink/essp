package client.essp.timesheet.report.utilizationRate.detail;

import com.wits.util.Parameter;
import com.wits.util.comDate;
import client.essp.common.excelUtil.VWJSExporterUtil;
import client.essp.common.view.VWTDWorkArea;
import client.essp.timesheet.period.VwTsPeriod;
import client.framework.view.common.comMSG;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import javax.swing.JButton;
import c2s.essp.timesheet.report.DtoUtilRateQuery;
import c2s.essp.timesheet.report.DtoUtilRateKey;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author tbh
 * @version 1.0
 */
public class VwUtilRate  extends VWTDWorkArea{
    private static final String actionIdExporter = "FTSPersonnelUseExporter";
    VwUtilRateUp vwUp = new VwUtilRateUp();
    VwUtilRateDown vwDown = new VwUtilRateDown();
    private int type = VwTsPeriod.PERIOD_MODEL_ANY;
    private Vector userItem = new Vector();
    private JButton queryBtn = new JButton();
    private JButton expBtn = new JButton();
    
    public VwUtilRate() {
        super(150);
        try {
            jbInit();
            addUICEvent();
        } catch (Exception ex) {
        }
    }

    //初始化
    private void jbInit() throws Exception {
        this.setVerticalSplit();
        this.setPreferredSize(new Dimension(650, 650));
        vwUp = new VwUtilRateUp();
        vwUp.setPreferredSize(new Dimension(650, 310));
        this.getTopArea().addTab("rsid.timesheet.utilRateQuery",vwUp);

        vwDown = new VwUtilRateDown();
        vwDown.setPreferredSize(new Dimension(650, 340));
        this.getDownArea().addTab("rsid.timesheet.utilRateList",vwDown);
    }

    public void addUICEvent() {
        //查询
        queryBtn.setText("rsid.timesheet.query");
        queryBtn.setSize(40,20);
        vwUp.queryButton = vwUp.getButtonPanel().addButton(queryBtn);
        vwUp.queryButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedQuery(e);
            }
        });
        vwUp.queryButton.setToolTipText("rsid.timesheet.query");    
        
        //導出
        expBtn = vwUp.getButtonPanel().addButton("export.png");
        expBtn.setToolTipText("rsid.common.export");
        expBtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                processExport();
            }
         });
        
        //員工集合隨著部門代號集合變化而變化
        vwUp.deptComBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectDeptId(e);
            }
        });
        
        //是否包含子部門
        vwUp.subCheckBox.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                selectSub(e);
            }
        });
    }

    /**
     * 导出报表
     *
     */
    protected void processExport() {
        DtoUtilRateQuery dtoQuery = vwUp.getDtoUtilRateQuery();
        Map<String, String> param = new HashMap<String, String>();
        if(checkError(dtoQuery)){
           return;
        }
        String beginDate = comDate.dateToString(dtoQuery.getBegin(),comDate.pattenDate);
        String endDate = comDate.dateToString(dtoQuery.getEnd(),comDate.pattenDate);
        String acntId = dtoQuery.getAcntId();
        String includeSub = dtoQuery.getIsSub().toString();
        type = vwUp.vwTsPeriodComp.TYPE;
        if(dtoQuery.getLoginId() != null && !"".equals(dtoQuery.getLoginId())){
          String loginId = dtoQuery.getLoginId();
          param.put(DtoUtilRateKey.DTO_LOGINID, loginId);
        }
        param.put(DtoUtilRateKey.DTO_BEGIN_DATE, beginDate);
        param.put(DtoUtilRateKey.DTO_END_DATE, endDate);
        param.put(DtoUtilRateKey.DTO_ACCOUNT_ID, acntId);
        param.put(DtoUtilRateKey.DTO_INCLUDE_SUB, includeSub);
        param.put(DtoUtilRateKey.DTO_UTIL_RATE_TYPE, String.valueOf(type));
        VWJSExporterUtil.excuteJSExporter(actionIdExporter, param);
    }
    
    /**
     * 校验开始和结束时间
     * @param dtoAtt
     * @return boolean
     */
    private boolean checkError(DtoUtilRateQuery dtoQuery) {
       Date begin = dtoQuery.getBegin();
       Date end = dtoQuery.getEnd();
       if(dtoQuery.getAcntId()== null){
           comMSG.dispErrorDialog("rsid.common.select.dept");
           return true;
       }
       if(begin == null){ 
           comMSG.dispErrorDialog("rsid.common.fill.begin");
           return true;
       }
       if(end == null){
           comMSG.dispErrorDialog("rsid.common.fill.end");
           return true;
       }
       if(begin.after(end)){
           comMSG.dispErrorDialog("rsid.common.beginlessEnd");
           return true;
       }
       return false;
    }
    
    //部門代號變化觸發事件
    private void selectDeptId(ActionEvent e) {
        DtoUtilRateQuery dtoQuery = vwUp.getDtoUtilRateQuery();
        vwUp.changeDept(dtoQuery.getAcntId());
        Parameter param = new Parameter();
        userItem = vwUp.userItem;
        param.put(DtoUtilRateKey.DTO_UTIL_RATE_TYPE, type);
        param.put(DtoUtilRateKey.DTO_TYPE_USER_LIST, userItem);
        param.put(DtoUtilRateKey.DTO_UNTIL_RATE_QUERY, new DtoUtilRateQuery());
        vwDown.setParameter(param);
    }
    
//  是否包含子部門
    private void selectSub(ActionEvent e){
        //如果包含子部門則灰掉人員屬性
         if(vwUp.subCheckBox.isSelected()){
             vwUp.userComBox.setEnabled(false);
             vwUp.userComBox.setSelectedIndex(0);
             vwUp.userLab.setEnabled(false);
         }else{
             vwUp.userComBox.setEnabled(true);
             vwUp.userLab.setEnabled(true);
         }
           
    }


    //查詢
    private void actionPerformedQuery(ActionEvent e) {
        DtoUtilRateQuery dtoQuery = vwUp.getDtoUtilRateQuery();
        Parameter param = new Parameter();
        param.put(DtoUtilRateKey.DTO_UNTIL_RATE_QUERY, dtoQuery);
        userItem = vwUp.userItem;
        type = vwUp.vwTsPeriodComp.TYPE;
        param.put(DtoUtilRateKey.DTO_TYPE_USER_LIST, vwUp.userItem);
        param.put(DtoUtilRateKey.DTO_UTIL_RATE_TYPE, type);
        vwDown.setParameter(param);
        vwDown.refreshWorkArea();
    }


    //设置参数
    public void setParameter(Parameter parameter) {
        vwUp.setParameter(parameter);
    }

    //刷新
    public void refreshWorkArea() {
        vwUp.refreshWorkArea();
    }

}
