package server.essp.issue.common.logic;

import server.framework.logic.*;
import server.essp.issue.common.viewbean.VbFilledBy;
import java.util.List;
import c2s.essp.pms.account.DtoAcntKeyPersonnel;
import c2s.essp.common.user.DtoUser;
import java.util.ArrayList;
import server.essp.pms.account.keyperson.logic.LgAccountKeyPersonnel;
import server.framework.taglib.util.SelectOptionImpl;

public class LgFilledBy extends AbstractBusinessLogic {
    public List getFilledByListOptions(String accountRid,DtoUser user) {
        List dtoList = getFilledByList(accountRid,user);
        List results=new ArrayList();
        SelectOptionImpl option = new SelectOptionImpl(
            "  ----  Please Select  ----  ", "");
        results.add(option);
        for(int i=0;i<dtoList.size();i++) {
            DtoAcntKeyPersonnel dto = (DtoAcntKeyPersonnel) dtoList.get(i);
            option = new SelectOptionImpl(dto.getUserName(), dto.getLoginId());
            results.add(option);
        }
        return results;
    }

    public List getFilledByListOptions(String accountRid,String user) {
        List dtoList = getFilledByList(accountRid,user);
        List results=new ArrayList();
        SelectOptionImpl option = new SelectOptionImpl(
            "  ----  Please Select  ----  ", "");
        results.add(option);
        for(int i=0;i<dtoList.size();i++) {
            DtoAcntKeyPersonnel dto = (DtoAcntKeyPersonnel) dtoList.get(i);
            option = new SelectOptionImpl(dto.getUserName(), dto.getLoginId());
            results.add(option);
        }
        return results;
    }

    public List getFilledByList(String accountRid,String user) {
        Long lAccountRid=Long.valueOf(accountRid);
        List results = null;
        LgAccountKeyPersonnel logic = new LgAccountKeyPersonnel();
        results = logic.listAccountCustomer(lAccountRid);
        boolean hasFind=false;
        for(int i=0;i<results.size();i++) {
            DtoAcntKeyPersonnel dto = (DtoAcntKeyPersonnel) results.get(i);
            if(user!=null && dto.getLoginId().equals(user)) {
                hasFind=true;
                break;
            }
        }
        if(user!=null && !hasFind) {
            DtoAcntKeyPersonnel dto = new DtoAcntKeyPersonnel();
            dto.setAcntRid(lAccountRid);
            dto.setEmail("");
            dto.setFax("");
            dto.setPhone("");
            dto.setUserName(user);
            dto.setLoginId(user);
            dto.setTypeName("");
            results.add(dto);
        }
        return results;
    }

    public List getFilledByList(String accountRid,DtoUser user) {
        Long lAccountRid=Long.valueOf(accountRid);
        List results = null;
        LgAccountKeyPersonnel logic = new LgAccountKeyPersonnel();
        results = logic.listAccountCustomer(lAccountRid);
        boolean hasFind=false;
        for(int i=0;i<results.size();i++) {
            DtoAcntKeyPersonnel dto = (DtoAcntKeyPersonnel) results.get(i);
            if(dto.getLoginId().equals(user.getUserLoginId())) {
                hasFind=true;
                break;
            }
        }
        if(!hasFind) {
            DtoAcntKeyPersonnel dto = new DtoAcntKeyPersonnel();
            dto.setAcntRid(lAccountRid);
            dto.setEmail(user.getEmail());
            dto.setFax(user.getFax());
            dto.setPhone(user.getPhone());
            dto.setLoginId(user.getUserLoginId());
            dto.setUserName(user.getUserName());
            dto.setTypeName(user.getUserType());
            results.add(dto);
        }
        return results;
    }

    public VbFilledBy getFilledByInfo(String accountRid,String filledBy) {
        Long lAccountRid=Long.valueOf(accountRid);
        VbFilledBy viewBean=new VbFilledBy();
        viewBean.setLoginid(filledBy);
        LgAccountKeyPersonnel logic = new LgAccountKeyPersonnel();
        List persons = logic.listAccountCustomer(lAccountRid);
        for(int i=0;i<persons.size();i++) {
            DtoAcntKeyPersonnel dto=(DtoAcntKeyPersonnel)persons.get(i);
            if(dto.getLoginId().equals(filledBy)) {
                viewBean.setName(dto.getUserName());
                viewBean.setPhone(dto.getPhone());
                viewBean.setFax(dto.getFax());
                viewBean.setEmail(dto.getEmail());
                break;
            }
        }
        return viewBean;
    }
}
