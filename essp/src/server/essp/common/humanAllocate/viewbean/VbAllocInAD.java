package server.essp.common.humanAllocate.viewbean;
import java.util.*;

public class VbAllocInAD {
        /**
         * ���е���֯
         */
   private  List domainList = new ArrayList();
        /**
         * ��ѯ���
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
