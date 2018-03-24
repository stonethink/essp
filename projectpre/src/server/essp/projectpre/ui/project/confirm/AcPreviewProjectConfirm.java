package server.essp.projectpre.ui.project.confirm;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.wits.util.comDate;
import c2s.dto.TransactionData;
import c2s.essp.common.account.IDtoAccount;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.projectpre.db.Acnt;
import server.essp.projectpre.db.AcntApp;
import server.essp.projectpre.db.AcntPerson;
import server.essp.projectpre.db.AcntTechinfo;
import server.essp.projectpre.db.Parameter;
import server.essp.projectpre.service.account.IAccountService;
import server.essp.projectpre.service.accountapplication.IAccountApplicationService;
import server.essp.projectpre.service.parameter.IParameterService;
import server.framework.common.BusinessException;
import server.framework.common.Constant;

/**
 * 预览待结案的专案资料的Action
 * @author Stephen.zheng
 *
 *
 */
public class AcPreviewProjectConfirm extends AbstractESSPAction {
    
	/**
	 * 预览待结案的专案资料页面
	 *   1.获取相关人员
	 *   2.获取技术资料
	 *   3.将数据设置到前台ViewBean
	 * @param request
	 * @param response
	 * @param data
	 * @throws BusinessException
	 */
    public void executeAct(HttpServletRequest request, HttpServletResponse response, 
                    TransactionData data) throws BusinessException {
              String acntId = null;
              Long rid = null;
              Acnt acnt = new Acnt();
              if(request.getParameter("acntId")!=null){
                 acntId = request.getParameter("acntId");
                 
              }
              if(request.getParameter("rid")!=null) {
                  rid = Long.valueOf(request.getParameter("rid"));
                  IAccountApplicationService acntAppLogic = (IAccountApplicationService) this.getBean("AccountApplicationLogic");
                  AcntApp acntApp = acntAppLogic.loadByRid(rid);
                  acntId = acntApp.getAcntId();
                 
              }
              
              IAccountService  logic = (IAccountService) this.getBean("AccountLogic");
              acnt = logic.loadByAcntId(acntId, "1");
               AfProjectConfirm af = new AfProjectConfirm();
               
               IAccountApplicationService acntAppLogic = (IAccountApplicationService) this.getBean("AccountApplicationLogic");
               AcntApp acntApp = acntAppLogic.loadByTypeAcntId(server.essp.projectpre.service.constant.Constant.PROJECTCONFIRMAPP, acntId);
               if(acntApp!=null){
               af.setComment(acntApp.getRemark());
               }
               
                
               //获取相关人员
               af = getPerson(acnt.getRid(), af);
               
               //获取技术资料
               af = getTechInfo(acnt.getRid(), af);
               
               af.setAcntId(acnt.getAcntId());
               af.setAcntName(acnt.getAcntName());
               af.setAcntFullName(acnt.getAcntFullName());
               af.setAcntBrief(acnt.getAcntBrief());
               IParameterService parameterLogic = (IParameterService)this.getBean("ParameterLogic");
               Parameter parameter = parameterLogic.loadByKindCode(server.essp.projectpre.service.constant.Constant.PROJEC_TYPE, acnt.getAcntAttribute());
               if(parameter!=null){
               af.setAcntAttribute(parameter.getName());
               }
               String date = comDate.dateToString(acnt.getAcntPlannedStart(), "yyyy-MM-dd")+"~"+comDate.dateToString(acnt.getAcntPlannedFinish(), "yyyy-MM-dd");
               
               af.setAcntAnticpated(date);
              
               
               request.setAttribute(Constant.VIEW_BEAN_KEY,af);
              
               

        
    }
    /**
     * 获取相关人员的信息
     * @param Rid 当前戴结案专案的Rid
     * @param af 当前的ActionForm
     * @return 含有人员信息的ActionForm
     */
    private AfProjectConfirm getPerson(Long Rid, AfProjectConfirm af) {
        IAccountService acntLogic = (IAccountService) this.getBean("AccountLogic");
        
        AcntPerson acntPerson = acntLogic.loadByRidFromPerson(Rid, IDtoAccount.USER_TYPE_APPLICANT);
        if(acntPerson!=null) {
        af.setApplicant(acntPerson.getName());
        }
        
        acntPerson = acntLogic.loadByRidFromPerson(Rid, IDtoAccount.USER_TYPE_LEADER);
        if(acntPerson!=null) {
        af.setLeader(acntPerson.getName());
        }
        
        acntPerson = acntLogic.loadByRidFromPerson(Rid, IDtoAccount.USER_TYPE_PM);
        if(acntPerson!=null) {
        af.setPMName(acntPerson.getName());
        }
        
        acntPerson = acntLogic.loadByRidFromPerson(Rid, IDtoAccount.USER_TYPE_TC_SIGNER);
        if(acntPerson!=null) {
        af.setTCSName(acntPerson.getName());
        }
        
        acntPerson = acntLogic.loadByRidFromPerson(Rid, IDtoAccount.USER_TYPE_BD_MANAGER);
        if(acntPerson!=null) {
        af.setBDName(acntPerson.getName());
        }
        
        return af;
        
    }
    /**
     * 获取相关的技术资料
     * @param Rid 当前待结案专案的Rid
     * @param af 当前的ActionForm
     * @return 含有技术资料的ActionForm
     */
    private AfProjectConfirm getTechInfo(Long Rid, AfProjectConfirm af) {
        
        IAccountService acntLogic = (IAccountService) this.getBean("AccountLogic");
        
        List productList = acntLogic.listByRidKindFromTechInfo(Rid, server.essp.projectpre.service.constant.Constant.PRODUCT_TYPE);
        List productAttributeList = acntLogic.listByRidKindFromTechInfo(Rid, server.essp.projectpre.service.constant.Constant.PRODUCT_ATTRIBUTE);
        List workItemList = acntLogic.listByRidKindFromTechInfo(Rid, server.essp.projectpre.service.constant.Constant.WORK_ITEM);
        
        String productStr = null;
        String ProductAttributeStr = null;
        String workItemStr = null;
        
        for(int i=0;i<productList.size();i++) {
            AcntTechinfo techInfo = (AcntTechinfo) productList.get(i); 
            productStr = productStr +","+ techInfo.getId().getCode() ;
        }
        if(productStr!=null) {
        af.setProductType(productStr.substring(5));
        }
        
        for(int i=0;i<productAttributeList.size();i++) {
            AcntTechinfo techInfo = (AcntTechinfo) productAttributeList.get(i); 
            ProductAttributeStr = ProductAttributeStr +","+ techInfo.getId().getCode() ;
        }
        if(ProductAttributeStr!=null) {
        af.setProductAttribute(ProductAttributeStr.substring(5));
        }
        
        for(int i=0;i<workItemList.size();i++) {
            AcntTechinfo techInfo = (AcntTechinfo) workItemList.get(i); 
            workItemStr = workItemStr +","+ techInfo.getId().getCode() ;
        }
        if(workItemStr!=null) {
        af.setWorkItem(workItemStr.substring(5));
        }
        
        return af;
    }
    

}
