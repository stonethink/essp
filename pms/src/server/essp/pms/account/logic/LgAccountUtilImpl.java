package server.essp.pms.account.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import c2s.dto.DtoComboItem;
import c2s.dto.DtoUtil;
import c2s.essp.common.account.IDtoAccount;
import c2s.essp.pms.account.DtoPmsAcnt;
import db.essp.pms.LaborResource;
import itf.account.IAccountUtil;
import server.essp.framework.logic.AbstractESSPLogic;
import server.essp.pms.account.labor.logic.LgAccountLaborRes;
import server.framework.common.BusinessException;
import server.framework.taglib.util.SelectOptionImpl;
import c2s.essp.pms.account.DtoAcntLoaborRes;
import c2s.essp.common.user.DtoUser;
import javax.sql.RowSet;
import server.essp.pms.account.keyperson.logic.LgAccountKeyPersonnelBase;
import db.essp.pms.Keypesonal;
import itf.hr.LgHrUtilImpl;
import com.wits.util.StringUtil;

public class LgAccountUtilImpl extends AbstractESSPLogic implements
    IAccountUtil {

    public LgAccountUtilImpl() {
    }


    /**
     * 依据项目记录的RID，获取项目信息
     * @param accID Long
     * @throws BusinessException
     * @return c2s.essp.common.account.IDtoAccount
     */
    public IDtoAccount getAccountByRID(Long acntRID) throws BusinessException {
        LgAccountBase lgAcntBase = new LgAccountBase();
        IDtoAccount dtoAccount =  new DtoPmsAcnt();
        try {
            Object obj = lgAcntBase.load(acntRID);
            DtoUtil.copyProperties(dtoAccount, obj);
        }
        catch (Exception ex) {
            throw new BusinessException(ex);
        }
        return dtoAccount;
    }


    /**
     * 依据项目Code，获取项目信息
     * @param accID Long
     * @throws BusinessException
     * @return c2s.essp.common.account.IDtoAccount
     */
    public IDtoAccount getAccountByCode(String acntCode) throws
        BusinessException {
        LgAccountBase lgAcntBase = new LgAccountBase();
        IDtoAccount dtoAccount =  new DtoPmsAcnt();
        try {
            DtoUtil.copyProperties(dtoAccount, lgAcntBase.load(acntCode));
        }
        catch (Exception ex) {
            throw new BusinessException(ex);
        }
        return dtoAccount;
    }


    /**
     * 获取所有Normal的项目清单(即项目状态为：Initial/Approved)
     * @throws BusinessException
     * @return List{DtoAccount}
     */
    public List listAccounts() throws BusinessException {
        LgAccountList lg = new LgAccountList();
        List list = lg.listAccounts();
        return copyDtoPmsAcnts2DtoAccounts(list);
    }


    /**
     * 依据所传入项目状态的列表，获取所有的项目清单
     * @param sAcntStatus List
     * @throws BusinessException
     * @return List{DtoAccount}
     */
    public List listAccounts(List sAcntStatus) throws BusinessException {
        LgAccountList lg = new LgAccountList();
        List list = lg.listAccounts(sAcntStatus);
        return copyDtoPmsAcnts2DtoAccounts(list);
    }


    /**
     * 获取所有的项目清单,包括各种项目状态
     * @throws BusinessException
     * @return List{DtoAccount}
     */
    public List listAllAccounts() throws BusinessException {
        LgAccountList lg = new LgAccountList();
        List list = lg.listAllAccounts();
        return copyDtoPmsAcnts2DtoAccounts(list);
    }


    /**
     * 依据用户ID获取其所有能查看和修改的项目状态为Normal(即项目状态为：Initial/Approved)的项目清单，
     * 1.获取作为项目经理的项目
     * 2.获取作为EBS所管理的项目
     * 3.获取作为参与者的项目
     *
     * @param loginID String
     * @throws BusinessException
     * @return List{DtoAccount}
     */
    public List listAccountsByLoginID(String loginID) throws BusinessException {
        LgAccountList lg = new LgAccountList();
        List list = lg.listAccountsByLoginID(loginID);
        return copyDtoPmsAcnts2DtoAccounts(list);
    }


    /**
     * 依据用户ID获取其所有能查看和修改的项目状态为所选条件的项目清单，
     * 1.获取作为项目经理的项目
     * 2.获取作为EBS所管理的项目
     * 3.获取作为参与者的项目{DtoAccount}
     *
     * @param loginID String
     * @param sAcntStatus List
     * @throws BusinessException
     * @return List{DtoAccount}
     */
    public List listAccountsByLoginID(String loginID, List sAcntStatus) throws
        BusinessException {
        LgAccountList lg = new LgAccountList();
        List list = lg.listAccountsByLoginID(loginID, sAcntStatus);
        return copyDtoPmsAcnts2DtoAccounts(list);
    }


    /**
     * 依据用户ID获取其所有能查看和修改的全部项目清单，
     * 1.获取作为项目经理的项目
     * 2.获取作为EBS所管理的项目
     * 3.获取作为参与者的项目{DtoAccount}
     *
     * @param loginID String
     * @param sAcntStatus List
     * @throws BusinessException
     * @return List{DtoAccount}
     */
    public List listAllAccountsByLoginID(String loginID) throws
        BusinessException {
        LgAccountList lg = new LgAccountList();
        List list = lg.listAllAccountsByLoginID(loginID);
        return copyDtoPmsAcnts2DtoAccounts(list);
    }


    /**
     * 依据用户ID获取其作为Key Personal的项目清单，且项目状态为Normal(即项目状态为：Initial/Approved)
     *
     * @param loginID String
     * @throws BusinessException
     * @return List{DtoAccount}
     */
    public List listKeyPersonalAccountsByLoginID(String loginID) throws
        BusinessException {
        return null;
    }

    public List listManagerAccounts(List userAccountList) throws
        BusinessException {
        if (userAccountList == null) {
            return null;
        }
        List list = new ArrayList();
        int iSize = userAccountList.size();
        for (int i = 0; i < iSize; i++) {
            IDtoAccount dto = (IDtoAccount) userAccountList.get(i);
            if (dto.isManagement()) {
                list.add(dto);
            }
        }

        return list;
    }

    public List listPMAccounts(List userAccountList) throws
        BusinessException {
        if (userAccountList == null) {
            return null;
        }
        List list = new ArrayList();
        int iSize = userAccountList.size();
        for (int i = 0; i < iSize; i++) {
            IDtoAccount dto = (IDtoAccount) userAccountList.get(i);
            if (dto.isPm()) {
                list.add(dto);
            }
        }
        return list;
    }

    public List listParticipantAccounts(List userAccountList) throws
        BusinessException {
        if (userAccountList == null) {
            return null;
        }
        List list = new ArrayList();
        int iSize = userAccountList.size();
        for (int i = 0; i < iSize; i++) {
            IDtoAccount dto = (IDtoAccount) userAccountList.get(i);
            if (dto.isParticipant()) {
                list.add(dto);
            }
        }
        return list;
    }

    public Vector listComboAccounts() throws BusinessException {
        List list = listAccounts();
        return listComboList(list, "");
    }

    public List listOptAccounts() throws BusinessException {
        List list = listAccounts();
        return listOptList(list, "");
    }

    public Vector listComboAccountsDefaultStyle() throws BusinessException {
        List list = listAccounts();
        return listComboList(list, IAccountUtil.SHOWSTYLE_DASHED);
    }

    public List listOptAccountsDefaultStyle() throws BusinessException {
        List list = listAccounts();
        return listOptList(list, IAccountUtil.SHOWSTYLE_DASHED);
    }

    public Vector listComboAccounts(String loginID) throws BusinessException {
        List list = listAccountsByLoginID(loginID);
        return listComboList(list, "");
    }

    public List listOptAccounts(String loginID) throws BusinessException {
        List list = listAccountsByLoginID(loginID);
        return listOptList(list, "");
    }

    public Vector listComboAccountsDefaultStyle(String loginID) throws
        BusinessException {
        List list = listAccountsByLoginID(loginID);
        return listComboList(list, IAccountUtil.SHOWSTYLE_DASHED);
    }

    public List listOptAccountsDefaultStyle(String loginID) throws
        BusinessException {
        List list = listAccountsByLoginID(loginID);
        return listOptList(list, IAccountUtil.SHOWSTYLE_DASHED);
    }

//    public Vector listComboManagerAccounts(String loginID) throws
//        BusinessException {
//        return null;
//    }
//
//    public List listOptManagerAccounts(String loginID) throws BusinessException {
//        return null;
//    }
//
//    public Vector listComboPMAccounts(String loginID) throws BusinessException {
//        return null;
//    }
//
//    public List listOptPMAccounts(String loginID) throws BusinessException {
//        return null;
//    }
//
//    public Vector listComboParticipantAccounts(String loginID) throws
//        BusinessException {
//        return null;
//    }
//
//    public List listOptParticipantAccounts(String loginID) throws
//        BusinessException {
//        return null;
//    }

    public Vector listComboKeyPersonalAccounts(String loginID) throws
        BusinessException {
        List list = listKeyPersonalAccountsByLoginID(loginID);
        return listComboList(list, "");
    }

    public List listOptKeyPersonalAccounts(String loginID) throws
        BusinessException {
        List list = listKeyPersonalAccountsByLoginID(loginID);
        return listOptList(list, "");
    }

    public Vector listComboKeyPersonalAccountsDefaultStyle(String loginID) throws
        BusinessException {
        List list = listKeyPersonalAccountsByLoginID(loginID);
        return listComboList(list, IAccountUtil.SHOWSTYLE_DASHED);
    }

    public List listOptKeyPersonalAccountsDefaultStyle(String loginID) throws
        BusinessException {
        List list = listKeyPersonalAccountsByLoginID(loginID);
        return listOptList(list, IAccountUtil.SHOWSTYLE_DASHED);
    }


    /** @link dependency */
    /*# LgAccountList lnkLgAccountList; */
    public Vector listComboList(List list, String style) throws
        BusinessException {
        //List list = listAccounts();
        if (list == null) {
            return null;
        }
        int iSize = list.size();
        Vector vector = new Vector(iSize);
        for (int i = 0; i < iSize; i++) {
            IDtoAccount dto = (IDtoAccount) list.get(i);
            String dispName = "";
            if (IAccountUtil.SHOWSTYLE_DASHED.equals(style)) {
                dispName = dto.getId() + " -- " +
                           dto.getName();
            } else if (SHOWSTYLE_BRACKET.equals(style)) {
                dispName = "(" + dto.getId() + ")" +
                           dto.getName();
            } else {
                dispName = dto.getName();
            }
            DtoComboItem ci = new DtoComboItem(dispName,
                                               dto.getRid(), dto);
            vector.add(ci);
        }
        return vector;
    }

    public List listOptList(List list, String style) throws BusinessException {
        //List list = listAccounts();
        if (list == null) {
            return null;
        }
        int iSize = list.size();
        List optList = new ArrayList(iSize);
        for (int i = 0; i < iSize; i++) {
            IDtoAccount dto = (IDtoAccount) list.get(i);
            String dispName = "";
            if (IAccountUtil.SHOWSTYLE_DASHED.equals(style)) {
                dispName = dto.getId() + " -- " +
                           dto.getName();
            } else if (SHOWSTYLE_BRACKET.equals(style)) {
                dispName = "(" + dto.getId() + ")" +
                           dto.getName();
            } else if (IAccountUtil.SHOWSTYLE_ONLYCODE.equals(style)){
                dispName = dto.getId();
            }else{
                dispName = dto.getName();
            }
            String sRid = "" + dto.getRid();
            SelectOptionImpl ci = new SelectOptionImpl(dispName, sRid);
            optList.add(ci);
        }
        return optList;
    }

    private static List copyDtoPmsAcnts2DtoAccounts(List pmsAcntList) {
//        if (pmsAcntList == null) {
//            return null;
//        }
//        int iSize = pmsAcntList.size();
//        List userAccountList = new ArrayList(iSize);
//
//        for(int i=0; i< iSize;i++){
//            DtoPmsAcnt dtoPmsAcnt = (DtoPmsAcnt)pmsAcntList.get(i);
//
//
//        }
        List userAccountList = null;
//        try {
//            userAccountList = DtoUtil.list2List(pmsAcntList, DtoAccount.class);
//        } catch (Exception ex) {
//        }
        userAccountList = pmsAcntList;
        return userAccountList;
    }

    public String listLaborResourceLoginIdStr(Long acntRid) throws
        BusinessException {
        LgAccountLaborRes lg = new LgAccountLaborRes();
        List resources = lg.listComboLaborRes(acntRid);
        String result = "";
        if(resources != null || resources.size() > 0 ){
            for(int i = 0;i < resources.size(); i ++){
                DtoComboItem res = (DtoComboItem) resources.get(i);
                if(result.equals("")){   //modify by lipengxu getItemName --> getItemValue
                    result = res.getItemValue().toString();
                }else{
                    result = result + "," + res.getItemValue().toString();
                }
            }
        }
        return result;
    }

    /**
     * list labor's loginId, name, type and domain split by "&".
     * @param acntRid Long
     * @return String
     * @throws BusinessException
     */
    public String listLaborResourceStr(Long acntRid) throws
        BusinessException {
        LgAccountLaborRes lg = new LgAccountLaborRes();
        List resources = lg.listLaborRes(acntRid);
        String result = "";
        if(resources != null || resources.size() > 0 ){
            for(int i = 0;i < resources.size(); i ++){
                LaborResource res = (LaborResource) resources.get(i);
                if(res != null) {
                    String sub_item = StringUtil.nvl(res.getLoginId()) + "*" +
                                      StringUtil.nvl(res.getEmpName()) + "*" +
                                      DtoUser.USER_TYPE_EMPLOYEE + "*" + "wh";
                    if (result.equals("")) {
                        result = sub_item;
                    } else {
                        result = result + "," + sub_item;
                    }
                }
            }
        }
        return result;
    }

    /**
     * list keyperson's loginId, name, type and domain split by "&".
     * @param accountRid Long
     * @return String
     * @throws BusinessException
     */
    public String listKeyPersonInfoStr(Long accountRid) throws BusinessException{
        String result="";
        LgAccountKeyPersonnelBase lgakpb=new LgAccountKeyPersonnelBase();
        List resources=lgakpb.listKeyPersonnel(accountRid);
        if(resources != null || resources.size() > 0 ){
           for(int i = 0;i < resources.size(); i ++){
               Keypesonal kp = (Keypesonal) resources.get(i);
               if (kp != null) {
                   String res = StringUtil.nvl(kp.getLoginId()) + "*" +
                                StringUtil.nvl(kp.getUserName()) + "*" +
                                DtoUser.USER_TYPE_CUST + "*" + "wh";
                   if (result.equals("")) {
                       result = res;
                   } else {
                       result = result + "," + res;
                   }
               }
           }
       }

        return result;
    }



    public String listKeyPersonByAccountStr(Long accountRid) throws BusinessException{
        String result="";
        LgAccountKeyPersonnelBase lgakpb=new LgAccountKeyPersonnelBase();
        List resources=lgakpb.listKeyPersonnel(accountRid);
        if(resources != null || resources.size() > 0 ){
           for(int i = 0;i < resources.size(); i ++){
               String res = ((Keypesonal) resources.get(i)).getLoginId();
               if(result.equals("")){
                   result = res;
               }else{
                   result = result + "," + res;
               }
           }
       }

        return result;
    }

    public List listLaborResourceByAcntRid(Long acntRid)throws
        BusinessException{
        LgAccountLaborRes lg = new LgAccountLaborRes();
        List list = lg.listLaborResDto(acntRid);
        int iSize = list.size();
        List result = new ArrayList(iSize);
        for (int i = 0; i < iSize; i++) {
            DtoAcntLoaborRes laborResource = (DtoAcntLoaborRes) list.get(i);
            if ((!laborResource.getResStatus().equals(DtoAcntLoaborRes.
                RESOURCE_STATUS_OUT)) &&
                laborResource.getLoginidStatus().equals(DtoAcntLoaborRes.
                LOGINID_STATUS_IN)) {
                DtoUser user = new DtoUser();
                user.setUserLoginId(laborResource.getLoginId());
                user.setUserName(laborResource.getEmpName());
                //user.setEmail(laborResource.gete);
                user.setJobCode(laborResource.getJobcodeId());
                user.setUserType(DtoUser.USER_TYPE_EMPLOYEE);
                result.add(user);
            }
        }
        return result;
    }


    final static String HR_ACCOUNT_SCOPE_TABLE = "essp_hr_account_scope_t";
    /** 用户登录表*/
    public static final String LOGIN_TABLE = LgHrUtilImpl.LOGIN_TABLE;
    private ArrayList hrAcntRidList;
    private ArrayList hrAcntNameList;
    public List getUserListInHrAcnt(Long acntRid) throws BusinessException {
        List userList = new ArrayList();
        String sql = "select login.login_id as loginid " +
                     "from " + LOGIN_TABLE + " login " +
                     "left join " + HR_ACCOUNT_SCOPE_TABLE + " acntScope on login.user_id = acntScope.SCOPE_CODE " +
                     "where acntScope.ACCOUNT_ID = " + acntRid.longValue() + " order by lower(loginid)";

        try {
            RowSet rset = getDbAccessor().executeQuery(sql);
            while (rset.next()) {
                String loginId = rset.getString("loginid");
                userList.add(loginId);
            }
        } catch (Exception ex) {
            throw new BusinessException("E000", "Error when get user in account - " + acntRid, ex);
        }
        return userList;
    }

    private void listHrAcntByLoginId(){
        hrAcntRidList = new ArrayList();
        hrAcntNameList = new ArrayList();
        String loginId = this.getUser().getUserLoginId();

        try {

            String sql = "select t.account_id as acntRid   from essp_hr_account_view_t t left join "+LgHrUtilImpl.LOGIN_TABLE+" e on RTRIM(t.account_manager) = e.user_id where e.login_id = '" +
                    loginId + "'";
            log.info("listHrAcntByLoginId" + sql);
            RowSet rset = getDbAccessor().executeQuery(sql);
            while (rset.next()) {
                long acntRid = rset.getLong("acntRid");

                IDtoAccount acnt = getAccountByRID(new Long(acntRid));
                hrAcntRidList.add(acnt.getRid());
                hrAcntNameList.add(acnt.getId() + " -- " + acnt.getName());
            }
        } catch (Exception ex) {
            throw new BusinessException("E000","Error when get the account list of user - " + loginId + "." ,ex);
        }
    }

    public List getHrAcntRidList() {
        if( hrAcntRidList == null ) {
            listHrAcntByLoginId();
        }
        return hrAcntRidList;
    }

    public List getHrAcntNameList() {
        if( hrAcntNameList == null ) {
            listHrAcntByLoginId();
        }
        return hrAcntNameList;
    }

    public List getHrAcntSelectOptions() {
        if( hrAcntRidList == null || hrAcntNameList == null){
            listHrAcntByLoginId();
        }
        List options = new ArrayList();
        for(int i = 0;i < hrAcntRidList.size() ;i ++) {
            Long rid = (Long) hrAcntRidList.get(i);
            String acntName = (String) hrAcntNameList.get(i);
            SelectOptionImpl op = new SelectOptionImpl();
            op.setLabel(acntName);
            op.setValue(rid.toString());
            options.add(op);
        }
        return options;
    }


    public static void main(String[] args){
        LgAccountUtilImpl lg = new LgAccountUtilImpl();
        lg.getUserListInHrAcnt(new Long(6022));
//        System.out.println("aaa"+lg.getAccountByRID(new Long()));
}

}
