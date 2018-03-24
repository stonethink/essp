/*
 * Created on 2007-11-30
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package client.essp.timesheet.report.timesheetStatus;

import java.util.List;
import java.util.Map;

import javax.swing.JButton;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.timesheet.report.DtoTsStatusQuery;
import client.essp.common.TableUitl.TableColumnChooseDisplay;
import client.essp.common.view.VWTableWorkArea;

import com.wits.util.Parameter;
/**
 * <p>Title: </p>
 *
 * <p>Description: VwTsStatusDownBase</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author tubaohui
 * @version 1.0
 */
public class VwTsStatusDownBase extends VWTableWorkArea{
        static final String actionIdQuery = "FTSStatusQuery";
        private DtoTsStatusQuery dtoQuery = new DtoTsStatusQuery();
        private  JButton buttonDisp = new JButton();
        public List queryList;
        public Map resultMap;
    
        public VwTsStatusDownBase() {
        }  

        //Ö¢îµÇ˜ﬂf
        public void setParameter(Parameter param) {
         dtoQuery = (DtoTsStatusQuery) param.get(DtoTsStatusQuery.DTO_TS_QUERY);
         queryList = (List)param.get(DtoTsStatusQuery.DTO_RESULT_LIST);
         super.setParameter(param);
       }
   
       //resetUI
       protected void resetUI() {
           getModel().setRows(queryList);
           if(queryList != null && queryList.size()>0){
               getTable().setSelectRow(0);
           }
           
    //     Display
           TableColumnChooseDisplay chooseDisplay =
                   new TableColumnChooseDisplay(this.getTable(), this);
           buttonDisp = chooseDisplay.getDisplayButton();
           this.getButtonPanel().addButton(buttonDisp);
       }
   
   //≤È—Ø
       protected void query(){   
           InputInfo inputInfo = new InputInfo();
           inputInfo.setActionId(this.actionIdQuery);
           inputInfo.setInputObj(DtoTsStatusQuery.DTO_TS_QUERY, dtoQuery);
           ReturnInfo returnInfo = accessData(inputInfo);
           if (returnInfo.isError() == false) {
               resultMap = (Map) returnInfo.getReturnObj(DtoTsStatusQuery.DTO_RESULT_LIST);
            } else {
               resultMap = null;
           }
       }
}

