/*
 * Created on 2008-3-17
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package client.essp.timesheet.report.timesheetStatus;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.Vector;
import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.timesheet.report.DtoTsStatusQuery;
import client.framework.model.VMComboBox;
import com.wits.util.Parameter;

public class VwTsStatusUpForDept extends VwTsStatusUpBase{
    private DtoTsStatusQuery dtoQuery = new DtoTsStatusQuery();
    static final String actionIdInitDept="FTSStatusListDept";

    public VwTsStatusUpForDept()  {
        try {
            jbInit();
            addUICEvent();
        } catch (Exception ex) {
            
        }
    }

    //初始化
    public void jbInit() throws Exception {
        super.jbInit();
      
        deptLab.setBounds(new Rectangle(250,25,80,20));
        deptLab.setText("rsid.timesheet.dept");
        deptComBox.setBounds(new Rectangle(330,25,200,20));
        
        subCheckBox.setBounds(new Rectangle(630,25,25,20));
        subLab.setBounds(new Rectangle(540,25,90,20));
        subLab.setText("rsid.timesheet.includeSub");
        subCheckBox.setEnabled(true);
       
        this.add(deptLab);
        this.add(deptComBox);
        this.add(subCheckBox);
        this.add(subLab);
     }

    private void  addUICEvent(){
        deptComBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedSelectDeptCom();
            }
        });
    }
    
    private void actionPerformedSelectDeptCom(){
         String deptId = (String)deptComBox.getUICValue();
         if(deptId == null || "".equals(deptId.trim())){
             subCheckBox.setEnabled(false);
             subCheckBox.setSelected(false);
         }else{
             subCheckBox.setEnabled(true);
         }
    }
    
    /**
    * 刷新界面
    */
   protected void resetUI() {
       super.resetUI();
       InputInfo inputInfo = new InputInfo();
       inputInfo.setActionId(this.actionIdInitDept);
       ReturnInfo returnInfo = accessData(inputInfo);
       if(returnInfo.isError()==false){
         deptItem = (Vector) returnInfo.getReturnObj(
               DtoTsStatusQuery.DTO_DEPTLIST);
         VMComboBox itemDept = new VMComboBox(deptItem);
         deptComBox.setModel(itemDept);
       }
   }

   //參數設置
    public void setParameter(Parameter param) {
        super.setParameter(param);
    }

   //得到查詢條件的DTO
   public DtoTsStatusQuery getDtoTsStatusQuery(){
       dtoQuery = new DtoTsStatusQuery();
       dtoQuery.setBeginDate((Date)inputBegin.getUICValue());
       dtoQuery.setEndDate((Date)inputEnd.getUICValue());
       dtoQuery.setIsSub(subCheckBox.isSelected());
       dtoQuery.setDeptId((String)deptComBox.getUICValue());
       dtoQuery.setIsDeptQuery(true);
       return dtoQuery;
   }
 }

