package server.essp.timesheet.outwork.logic;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import server.essp.common.syscode.LgSysParameter;
import server.framework.taglib.util.SelectContentBase;
import server.framework.taglib.util.UpSelectList;
import db.essp.code.SysParameter;
import db.essp.timesheet.TsAccount;
import db.essp.timesheet.TsLaborResource;

public class EvectionTypeSelectImpl extends SelectContentBase{

    private static ArrayList list = null;

    public Object getDefualt() {
        return new UpSelectList( -1, "-1", "--Please Select Type--", "-1");
    }

    public ArrayList getList() {
        return list;
    }


    public void getData() throws SQLException {
    	list = new ArrayList();
    	java.util.Locale locale = (java.util.Locale)getSession().getAttribute(org.apache.struts.Globals.LOCALE_KEY);
    	boolean chinese = locale.getLanguage().equalsIgnoreCase("zh");
    	LgSysParameter lgParam = new LgSysParameter();
    	List pList = lgParam.listSysParas("EVECTION_TYPE");
    	for(int i = 0; i < pList.size(); i ++) {
    		SysParameter param = (SysParameter) pList.get(i);
    		String name = chinese ? param.getDescription() : param.getName();
    		list.add(new UpSelectList(i, param.getComp_id().getCode(), name , ""));
    	}
    }

}