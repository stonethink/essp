package server.essp.tc.attcompare;

import server.framework.action.AbstractBusinessAction;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import c2s.dto.TransactionData;
import server.framework.common.BusinessException;
import server.essp.framework.action.AbstractESSPAction;
import c2s.essp.tc.nonattend.DtoNonattend;
import server.essp.tc.hrmanage.logic.LgNonattend;

public class AcCompareResultDel extends AbstractESSPAction {

    /**
     * 删除被选中的旷工差勤记录
     */

    public void executeAct(
            HttpServletRequest request, HttpServletResponse response, TransactionData data) throws BusinessException {

        String[] rid = request.getParameterValues("noattRid");
        LgNonattend lgNoAtt = new LgNonattend();

        //先对rid进行处理，把重复的值去掉
        String[] ridDelDou = new String[rid.length];
        int index = 0;
        for (int i = 0; i < rid.length; i++) {
            if (!this.isExistString(ridDelDou, rid[i])&&!rid[i].equals("")) {
                ridDelDou[index++] = rid[i];
            }
        }

        for (int i = 0; i < ridDelDou.length; i++) {
            if (!rid[i].equals("") && ridDelDou[i] != null) {
                //删除此条记录
                DtoNonattend dtono = new DtoNonattend();
                dtono.setRid(new Long(ridDelDou[i]));
                lgNoAtt.delete(dtono);
            }
        }
        data.getReturnInfo().setForwardID("sucessDEL");

    }

    /**
     * 用于查找某个字符串是否在一个字符串数组中
     * @param dest String[]
     * @param str String
     * return boolean
     */

    public boolean isExistString(String[] dest, String str) {
        boolean flag = false;
        for (int i = 0; i < dest.length; i++) {
            if (str.equals(dest[i])) {
                flag = true;
            }
        }
        return flag;
    }

}
