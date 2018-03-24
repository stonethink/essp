package c2s.essp.cbs;

import c2s.dto.DtoBase;
import c2s.dto.DtoTreeNode;

public class DtoCbs extends DtoBase {
    public static final String DEFAULT_TYPE="SYSTEM";
    public static final String DEFAULT_ROOT="Cost";
    private String cbsType;
    DtoTreeNode cbsRoot;

    public DtoCbs() {
    }

    public String getCbsType() {
        return cbsType;
    }

    public void setCbsType(String cbsType) {
        this.cbsType = cbsType;
    }

    public DtoTreeNode getCbsRoot() {
        return cbsRoot;
    }

    public void setCbsRoot(DtoTreeNode cbsRoot) {
        this.cbsRoot = cbsRoot;
    }

    public boolean isCanEntry(String subjectCode){
        return true;
    }
}
