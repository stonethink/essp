/*
 * Created on 2006-11-9
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.projectpre.ui.dept.query;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.projectpre.db.Bd;
import server.essp.projectpre.service.bd.IBdService;
import server.essp.projectpre.ui.dept.query.VbQueryData;
import server.framework.common.BusinessException;
import server.framework.taglib.util.SelectOptionImpl;
import c2s.dto.TransactionData;

public class AcQueryData extends AbstractESSPAction{


    private List tmpList = null;
    private String defaultStr = "--please select--";
    private SelectOptionImpl defaultOption = new SelectOptionImpl(defaultStr, "");
    /**
     * ��ʼ����ѯҳ��
     */
    public void executeAct(HttpServletRequest request,
            HttpServletResponse response, TransactionData data)
            throws BusinessException {
        
        VbQueryData view = new VbQueryData();
        view.setAchieveBelong("");
        view.setAcntId("");
        view.setAcntName("");
        view.setApplicantName("");
        view.setBDMName("");
        view.setDeptManager("");
        view.setTCSName("");
        //��ȡBd���м�¼��BD_name,BD_code��λ
        IBdService logicBd = (IBdService) this.getBean("BdCodeLogic");
        
        Bd bd = null;
        SelectOptionImpl bdOption = null;
        List bdList = new ArrayList();
        bdList.add(defaultOption);
        tmpList = logicBd.listAllEabled();
        
        for (int i=0;i<tmpList.size();i++) {
            bd = (Bd) tmpList.get(i);
            bdOption = new SelectOptionImpl(bd.getBdName(),bd.getBdCode());
            bdList.add(bdOption);           
        }      
        view.setBdList(bdList);     
        // ����Ҫ���ص����ݷŵ�Request�У�����ҵ����������ݲ�����ŵ�Session��
        request.setAttribute(server.framework.common.Constant.VIEW_BEAN_KEY, view);

    }
}
