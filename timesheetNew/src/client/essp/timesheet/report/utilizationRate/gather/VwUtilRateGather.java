package client.essp.timesheet.report.utilizationRate.gather;

import com.wits.util.Parameter;
import com.wits.util.comDate;
import client.essp.common.excelUtil.VWJSExporterUtil;
import client.essp.common.view.VWTDWorkArea;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import c2s.essp.timesheet.report.DtoUtilRateQuery;
import java.util.Vector;
import c2s.essp.timesheet.report.DtoUtilRateKey;
import client.essp.common.view.VWGeneralWorkArea;
import client.framework.view.common.comMSG;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import javax.swing.JButton;
import c2s.dto.ITreeNode;

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
public class VwUtilRateGather extends VWTDWorkArea{
    private static final String actionIdExporter = "FTSUtilRateGatherExporter";;
    VwUtilRateGatherUp vwUp = new VwUtilRateGatherUp();
    VwUtilRateGatherDown vwDown = new VwUtilRateGatherDown();
    private Vector deptItem = new Vector();
    private JButton queryBtn = new JButton();
    private JButton expBtn = new JButton();
    public VwUtilRateGather() {
        super(150);
        try {
            jbInit();
        } catch (Exception ex) {
        }
        addUICEvent();
    }

    //初始化
    private void jbInit() throws Exception {
        this.setVerticalSplit();
        this.setPreferredSize(new Dimension(650, 650));
        vwUp = new VwUtilRateGatherUp();
        vwUp.setPreferredSize(new Dimension(650, 300));
        this.getTopArea().addTab("rsid.timesheet.utilRateGather",vwUp);
    }

    public void addUICEvent() {
        //查询
        queryBtn.setText("rsid.timesheet.query");
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
        String  acntId = dtoQuery.getAcntId();
        String loginId = dtoQuery.getLoginId();
        param.put(DtoUtilRateKey.DTO_LOGINID, loginId);
        param.put(DtoUtilRateKey.DTO_BEGIN_DATE, beginDate);
        param.put(DtoUtilRateKey.DTO_END_DATE, endDate);
        param.put(DtoUtilRateKey.DTO_ACCOUNT_ID, acntId);
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
           vwUp.foucsOnData(DtoUtilRateKey.DTO_ACCOUNT_ID);
           return true;
       }
       if(begin == null){ 
           comMSG.dispErrorDialog("rsid.common.fill.begin");
           vwUp.foucsOnData(DtoUtilRateKey.DTO_BEGIN_DATE);
           return true;
       }
       if(end == null){
           comMSG.dispErrorDialog("rsid.common.fill.end");
           vwUp.foucsOnData(DtoUtilRateKey.DTO_END_DATE);
           return true;
       }
       if(begin.after(end)){
           comMSG.dispErrorDialog("rsid.common.beginlessEnd");
           vwUp.foucsOnData(DtoUtilRateKey.DTO_BEGIN_DATE);
           return true;
       }
       return false;
    }
    

   //查\uFFFD\uFFFD
    private void actionPerformedQuery(ActionEvent e) {
        DtoUtilRateQuery dtoQuery = vwUp.getDtoUtilRateQuery();
        Parameter param = new Parameter();
        param.put(DtoUtilRateKey.DTO_UNTIL_RATE_QUERY, dtoQuery);
        deptItem = vwUp.DeptItem;
        param.put(DtoUtilRateKey.DTO_DEPT_VECTOR, deptItem);
        param.put(DtoUtilRateKey.DTO_DEPT_LIST,null);
        this.add(vwDown);
        vwDown.setParameter(param);
        Boolean flag = checkError(dtoQuery);
        if(!flag){
        vwDown.queryUtilRateGather();
        Map deptList = vwDown.deptList;
        VWGeneralWorkArea downArea = new VWGeneralWorkArea();
        if(deptList != null){
           Iterator iter = deptList.keySet().iterator();
           while(iter.hasNext()){
             vwDown = new VwUtilRateGatherDown();
             String year = (String) iter.next();
             ITreeNode root = (ITreeNode) deptList.get(year);
             param.put(DtoUtilRateKey.DTO_DEPT_LIST, root);
             param.put(DtoUtilRateKey.DTO_UNTIL_RATE_QUERY, dtoQuery);
             param.put(DtoUtilRateKey.DTO_DEPT_VECTOR, deptItem);
             vwDown.setParameter(param);
             downArea.addTab(year, vwDown);
             vwDown.refreshWorkArea();
           }
            this.setDownArea(downArea);
         }
        }
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
