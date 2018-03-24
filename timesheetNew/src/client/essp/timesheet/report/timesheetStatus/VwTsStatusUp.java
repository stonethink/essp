/*
 * Created on 2007-11-27
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package client.essp.timesheet.report.timesheetStatus;

import java.awt.Rectangle;
import java.util.Date;
import java.util.Vector;

import client.framework.model.VMComboBox;

import c2s.dto.DtoComboItem;
import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.timesheet.report.DtoSalaryWkHrQuery;
import c2s.essp.timesheet.report.DtoTsStatusQuery;


/**
 * <p>Title: </p>
 *
 * <p>Description:VwTimesheetStatusUp </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author tubaohui
 * @version 1.0
 */
public class VwTsStatusUp extends VwTsStatusUpBase{
        static final String actionIdLoadSite="FTSLoadSiteInfo";   
        private Boolean isPMO = false;
        public VwTsStatusUp()  {
            try {
                jbInit();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        //初始化
        public void jbInit() throws Exception {
            super.jbInit();
          
            siteLab.setBounds(new Rectangle(250,25,80,20));
            siteLab.setText("rsid.timesheet.site");
            siteCombox.setBounds(new Rectangle(330,25,100,20));
            
            add(siteLab);
            add(siteCombox);
         }

    

    //得到查詢條件的DTO
    public DtoTsStatusQuery getDtoTsStatusQuery(){
        dtoQuery = new DtoTsStatusQuery();
        dtoQuery.setBeginDate((Date)inputBegin.getUICValue());
        dtoQuery.setEndDate((Date)inputEnd.getUICValue());
        dtoQuery.setSite((String)siteCombox.getUICValue());
        dtoQuery.setIsDeptQuery(false);
        return dtoQuery;
    }
    
    protected void resetUI() {
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(actionIdLoadSite);
        ReturnInfo returnInfo = this.accessData(inputInfo);
        if (returnInfo.isError()) return;
	       siteItem = (Vector) returnInfo.getReturnObj(
	               DtoSalaryWkHrQuery.DTO_SITE_LIST);
	       
	       isPMO = (Boolean)returnInfo.getReturnObj(DtoSalaryWkHrQuery.
	                DTO_IS_PMO);
	       if(isPMO){
	    	   DtoComboItem dtoComFirst = new DtoComboItem("Select All",null);
	           siteItem.add(0,dtoComFirst);
	           siteCombox.setEnabled(true);
	           siteLab.setEnabled(true);
	       }else{
	           siteCombox.setEnabled(false);
	           siteLab.setEnabled(false);
	       }
	       VMComboBox itemSite = new VMComboBox(siteItem);
	       siteCombox.setModel(itemSite);
         super.resetUI();
    }
 }
