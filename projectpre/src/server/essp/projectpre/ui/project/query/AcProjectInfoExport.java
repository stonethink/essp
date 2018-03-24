/*
 * Created on 2007-4-6
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.projectpre.ui.project.query;

import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;
import c2s.dto.TransactionData;

import com.wits.util.Parameter;
import com.wits.util.comDate;
/**
 * ����ѯ��ר�����ϵ���Ϊexcel�ļ�
 * @author wenhaizheng
 */
public class AcProjectInfoExport extends AbstractESSPAction { 
    /**
     * ����excel�ļ�
     * @param request
     * @param response
     * @param data
     * @throws BusinessException
     */
    public void executeAct(HttpServletRequest request, HttpServletResponse response, TransactionData data) throws BusinessException {
        //��sesion��ȡ��ר����ѯ��SQL��䣨������Ҫ���õ�����������
        Map hqlMap = (Map)request.getSession().getAttribute("HQLMAP");
        AfSearchCondition af = (AfSearchCondition)request.getSession().getAttribute("SearchCondition");
        Map conditionMap = new HashMap();
        if(af!=null){
            conditionMap = af.getConditionMap();
        } else {
            throw new BusinessException("error.logic.export");
        }
        Parameter inputData = new Parameter();
        inputData.put("HQLMAP", hqlMap);
        inputData.put("conditionMap", conditionMap);
        ResultExport exporter = new ResultExport("PP_Acnt_Info_"
                +comDate.dateToString(new Date(), "yyyyMMddHHmmss")
                +".xls");
        try {
            OutputStream out = response.getOutputStream();
            exporter.webExport(response,out,inputData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    } 


}
