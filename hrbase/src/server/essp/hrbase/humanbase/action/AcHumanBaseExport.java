package server.essp.hrbase.humanbase.action;

import java.io.OutputStream;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import server.essp.framework.action.AbstractESSPAction;
import server.essp.hrbase.humanbase.form.AfHumanBaseQuery;
import server.essp.hrbase.humanbase.form.AfSearchCondition;
import server.essp.hrbase.humanbase.service.IHumanBaseSevice;
import server.framework.common.BusinessException;
import c2s.dto.TransactionData;

import com.wits.util.Parameter;

public class AcHumanBaseExport extends AbstractESSPAction {

	public void executeAct(HttpServletRequest request,
			HttpServletResponse response, TransactionData data)
			throws BusinessException {
		AfHumanBaseQuery af = (AfHumanBaseQuery)this.getForm();
		Map conditionMap = new HashMap();
		AfSearchCondition afColumns = (AfSearchCondition)request.getSession().getAttribute("HrSearchCondition");
		if(afColumns == null) {
			afColumns = new AfSearchCondition();
			afColumns.createDefaultSelect();
			request.getSession().setAttribute("HrSearchCondition", afColumns);
		}
		if(af!=null){
            conditionMap = afColumns.getConditionMap();
        } else {
            throw new BusinessException("error.logic.export");
        }
    	IHumanBaseSevice service = (IHumanBaseSevice)this.getBean("humanBaseSevice");
        List resultList = service.getHrBaseByCondition(af);
        List vbList = service.beanList2VbList(resultList);
		Parameter inputData = new Parameter();
		inputData.put("list", vbList);
		inputData.put("conditionMap", conditionMap);
		HumanBaseExporter exporter = new HumanBaseExporter();
		try {
            OutputStream out = response.getOutputStream();
            exporter.webExport(response,out,inputData);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

}
