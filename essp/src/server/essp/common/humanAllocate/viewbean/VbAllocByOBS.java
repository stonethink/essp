package server.essp.common.humanAllocate.viewbean;

import java.util.*;

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
public class VbAllocByOBS {
        /**
         * 所有的组织
         */
   private  List orgnizations = new ArrayList();
        /**
         * 查询结果
         */
    private  List detail = new ArrayList();
    private String selectOrg;
    private String isIncludeSubOrg;
    public List getDetail() {
        return detail;
    }
    public List getOrgnizations() {
        return orgnizations;
    }

    public String getSelectOrg() {
        return selectOrg;
    }

    public String getIsIncludeSubOrg() {
        return isIncludeSubOrg;
    }

    public void setDetail(List detail) {
        this.detail = detail;
    }
    public void setOrgnizations(List orgnizations) {
        this.orgnizations = orgnizations;
    }

    public void setSelectOrg(String selectOrg) {
        this.selectOrg = selectOrg;
    }

    public void setIsIncludeSubOrg(String isIncludeSubOrg) {
        this.isIncludeSubOrg = isIncludeSubOrg;
    }
}
