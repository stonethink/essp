package client.essp.common.excelUtil;

import java.util.Iterator;
import java.util.Map;

import netscape.javascript.JSObject;
import c2s.essp.common.excelUtil.IExcelParameter;
import client.framework.common.Global;

public class VWJSExporterUtil {
	
	public final static int RESULT_SUCESS = 0;
	public final static int RESULT_FAIL = 1;
    
    public static int excuteJSExporter(final String actionId, Map<String, String> param) {
    	StringBuffer sb = new StringBuffer(Global.appRoot);
    	sb.append(IExcelParameter.DEFAULT_EXCEL_JSP_ADDRESS);
        sb.append("?");
        sb.append(IExcelParameter.ACTION_ID);
        sb.append("=");
        sb.append(actionId);
        sb.append(param2Str(param));
    	return excuteJSExporter(sb.toString());
    }
    
    public static int excuteJSExporter(String urlAddress) {
    	if(urlAddress == null || "".equals(urlAddress)) {
    		return RESULT_FAIL;
    	}
        JSObject win = JSObject.getWindow(Global.applet);
        win.eval(getScript(urlAddress));
        return RESULT_SUCESS;
    }

    private static String getScript(String urlAddress){
        StringBuffer sb = new StringBuffer("window.open(\"");
        sb.append(urlAddress);
        sb.append(" \" , \"\", \"toolbar =yes, menubar=yes, scrollbars=yes, resizable=yes, status=yes,width=800,height=600,top=10,left=10\");");
        return sb.toString();
    }
    
    private static String param2Str(Map<String, String> param) {
    	if(param == null || param.isEmpty()) {
    		return "";
    	}
    	StringBuffer sb = new StringBuffer();
    	Iterator<String> iter = param.keySet().iterator();
    	while(iter.hasNext()) {
    		String key = iter.next();
    		String value = param.get(key);
    		sb.append("&");
    		sb.append(key);
    		sb.append("=");
    		sb.append(value == null ? "" : value);
    	}
    	return sb.toString();
    }
}
