package server.essp.common.humanAllocate.action;

import itf.account.AccountFactory;
import itf.account.IAccountUtil;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import server.essp.common.ldap.LDAPUtil;
import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;
import c2s.dto.TransactionData;
import c2s.essp.common.account.IDtoAccount;
import c2s.essp.common.user.DtoUser;

import com.wits.util.IgnoreCaseStringComparator;
import com.wits.util.StringUtil;


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
public class AcUserAlloc extends AbstractESSPAction {
    public static final String ALLOC_SINGLE_USER = "single";
    public static final String ALLOC_MULTI_USER = "multi";
    public void executeAct(HttpServletRequest request,
                           HttpServletResponse httpServletResponse,
                           TransactionData transactionData) throws BusinessException {
        String type = request.getParameter("type");
        String oldValue = request.getParameter("oldValue");
        String requestType = request.getParameter("requestType");
        String showKeyPerson = request.getParameter("showKeyPerson");
        if( !ALLOC_SINGLE_USER.equalsIgnoreCase(type) && !ALLOC_MULTI_USER.equals(type) )
            throw new BusinessException("","illegal parameter when allocate users!");

        String acntRid = StringUtil.nvl(request.getParameter("acntRid"));
        String requestStr = StringUtil.nvl(request.getParameter("requestStr"));
        if(!"".equals(acntRid)){
            AccountFactory acntFactory = new AccountFactory();
            IAccountUtil acntUtil = acntFactory.create();
            IDtoAccount account = acntUtil.getAccountByRID(new Long(acntRid));
            request.setAttribute("acntId", account.getId());
            //if(ALLOC_MULTI_USER.equals(type)){
                String labors = acntUtil.listLaborResourceStr(new Long(acntRid));
                String allHuman = labors;
                if("true".equalsIgnoreCase(showKeyPerson)) {
                    String keyPerson = acntUtil.listKeyPersonInfoStr(new
                            Long(acntRid));
                    if(!"".equals(StringUtil.nvl(keyPerson))) {
                        allHuman = labors + "," + keyPerson;
                    }
                }
                String []allPerson=allHuman.split(",");
                List list = Arrays.asList(allPerson);
                Set set = new HashSet(list);
//                System.out.println("所有的人数："+allPerson.length+",转换后的人数:"+set.size());
                IgnoreCaseStringComparator myCom=new IgnoreCaseStringComparator();
                Object []sortObj=new Object[set.size()];
                set.toArray(sortObj);
                Arrays.sort(sortObj,myCom);
                String all="";

                if(!set.isEmpty()||set != null || set.size() > 0 ){
                    for(int i=0;i<sortObj.length;i++){
                        if(all.equals("")){
                            all=(String)sortObj[i];
                        }
                        else{
                            all=all+","+(String)sortObj[i];
                        }
                    }
                   }

                request.setAttribute("labors", all);
            //}
        }else{
            request.setAttribute("acntId", "No Account!");
            request.setAttribute("labors", "");
        }
        if(requestType!=null) {
//            if(requestType.equals("ad")) {
//
//                List domainList = LDAPUtil.getDomainIdList();
//                AfUserAllocInAD vbAD = new AfUserAllocInAD();
//                vbAD.setDomainList(domainList);
//                request.setAttribute("webVoAD",vbAD);
//            }
            request.setAttribute("requestType", requestType);
        } else {
            requestType = "db";
            request.setAttribute("requestType",requestType);
        }
        if(oldValue!=null) {
            String values = "";
            String[] oldValues = oldValue.split(",");
            LDAPUtil ldap = new LDAPUtil();
            for(int i=0;i<oldValues.length;i++) {
                DtoUser user = ldap.findUser(oldValues[i]);
                if(user != null) {
                    String oneValue = StringUtil.nvl(user.getUserLoginId()) + "*" +
                                      StringUtil.nvl(user.getUserName()) + "*" +
                                      DtoUser.USER_TYPE_EMPLOYEE + "*" +
                                      user.getDomain();
                    if ("".equals(values)) {
                        values = oneValue;
                    } else {
                        values = values + "," + oneValue;
                    }
                }
            }
            request.setAttribute("oldValues", values);
        }
        if(requestStr!=null) {
            if("".equals(requestStr)) {
                requestStr = "Allocate " + type +
                             " user"+(type.equals("single")?"":"s")+" in " +
                             requestType.toUpperCase();
            }
            request.setAttribute("requestStr", requestStr);
        }
        request.setAttribute("type",type);
        transactionData.getReturnInfo().setForwardID("success");
    }
}
