package server.essp.common.humanAllocate.viewbean;
import java.util.*;

public class VbAllocInAD {
        /**
         * 所有的组织
         */
   private  List domainList = new ArrayList();
        /**
         * 查询结果
         */
    private  List detail = new ArrayList();
    public List getDetail() {
        return detail;
    }
    public List getDomainList() {
        return domainList;
    }
    public void setDetail(List detail) {
        this.detail = detail;
    }
    public void setDomainList(List domainList) {
        this.domainList = domainList;
    }
}
