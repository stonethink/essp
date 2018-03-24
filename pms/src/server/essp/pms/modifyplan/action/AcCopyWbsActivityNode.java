package server.essp.pms.modifyplan.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;
import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.dto.ITreeNode;
import server.essp.pms.modifyplan.logic.LgAppendNode;
import c2s.essp.pms.wbs.DtoKEY;
import server.essp.pms.activity.logic.LgActivity;
import c2s.essp.pms.wbs.DtoWbsActivity;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class AcCopyWbsActivityNode extends AbstractESSPAction {
    public AcCopyWbsActivityNode() {
    }

    /**
     * executeAct
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param data TransactionData
     * @throws BusinessException
     * @todo Implement this server.framework.action.AbstractBusinessAction
     *   method
     */
    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData transData) throws
        BusinessException {

         InputInfo inputInfo = transData.getInputInfo();
         ReturnInfo returnInfo = transData.getReturnInfo();
         //���Ƶ�Դ
         ITreeNode strItreeNode =
               (ITreeNode)inputInfo.getInputObj("strItreeNode");
         //���Ƶ�Ŀ��
         ITreeNode destITreeNode =
               (ITreeNode)inputInfo.getInputObj("destItreeNode");
         //��ȡ���б�־
         String isCut = (String)inputInfo.getInputObj("isCut");

         LgAppendNode lgAppendNode = new LgAppendNode();
         //ִ��Դ�ڵ㵽Ŀ��ڵ�ĸ���
         //���Ƶ����ݰ���strItreeNode�������ӣ��ڵ����ݺ������������
         ITreeNode resultNode =
              lgAppendNode.appendNode(strItreeNode,destITreeNode);

        //������б�־Ϊtrue����ɾ��Դ�ڵ㼰�������Ϣ
        if( isCut!= null && "true".equals(isCut)){
            LgActivity lgActivity = new LgActivity();
            DtoWbsActivity dtoWbsActivity =
                      (DtoWbsActivity) strItreeNode.getDataBean();
            lgActivity.delete(dtoWbsActivity);
        }

          if(resultNode == null){
              returnInfo.setReturnObj(DtoKEY.WBSTREE,destITreeNode);
              returnInfo.setReturnObj("hasCopy","false");
          }else{
              returnInfo.setReturnObj(DtoKEY.WBSTREE,resultNode);
              returnInfo.setReturnObj("hasCopy","true");
          }
    }
}
