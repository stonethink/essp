/*
 * Created on 2008-1-29
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package client.essp.timesheet.report.attvariant;

import java.util.List;
import java.util.Map;
import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.timesheet.report.DtoAttVariantQuery;
import client.essp.common.view.VWTableWorkArea;
import com.wits.util.Parameter;

/**
 * VwAttVariantDown
 * @author TBH
 */
public class VwAttVariantDownBase extends VWTableWorkArea{
        static final String actionIdQuery = "FTAttVariantQuery";
        private List queryList;
        private DtoAttVariantQuery dtoQuery = new DtoAttVariantQuery();
        Map resultMap;
        
        public VwAttVariantDownBase() {
        }

        //…¢”µ‚÷ßf
        public void setParameter(Parameter param) {
           super.setParameter(param);
           dtoQuery = (DtoAttVariantQuery) param.get(DtoAttVariantQuery.
                 DTO_ATTVARIANT_QUERY);
           queryList = (List)param.get(DtoAttVariantQuery.DTO_RESULT_LIST);
       }
   
       //²éÔƒ
       public void query(){
               InputInfo inputInfo = new InputInfo();
               inputInfo.setActionId(this.actionIdQuery);
               inputInfo.setInputObj(DtoAttVariantQuery.DTO_ATTVARIANT_QUERY,
                       dtoQuery);
               ReturnInfo returnInfo = accessData(inputInfo);
               if (returnInfo.isError() == false) {
                   resultMap = (Map) returnInfo.getReturnObj(DtoAttVariantQuery.
                           DTO_RESULT_LIST);
               } else {
                   resultMap = null;
               }
       }
   
       protected void resetUI() {
           getModel().setRows(queryList);
           if(queryList != null && queryList.size()>0){
               getTable().setSelectRow(0);
           }
       }
   
}
