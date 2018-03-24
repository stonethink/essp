package server.essp.projectpre.ui.common.locale;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.Globals;

import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;
import c2s.dto.TransactionData;

public class AcChangeLocal extends AbstractESSPAction {

	public void executeAct(HttpServletRequest request, HttpServletResponse response,
			TransactionData arg2) throws BusinessException {
		String language = request.getParameter("language");
		if (language!=null){
			Locale locale;
			if(language.equalsIgnoreCase("en")){
			   locale = new Locale("en");
			   request.getSession().setAttribute(Globals.LOCALE_KEY,locale);
			}else{
			   locale = new Locale("zh");
			   request.getSession().setAttribute(Globals.LOCALE_KEY,locale);
			}
		}
		

	}

}
