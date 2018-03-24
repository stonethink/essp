package server.essp.pms.wbs.logic;

import itf.seq.IAcntSeqUtil;

public class AcntSeqUtilImpl  implements IAcntSeqUtil{
    public AcntSeqUtilImpl() {
    }

    public Long getRootRid(Long codeRid, Class pojo){
        return LgAcntSeq.getRootRid(codeRid, pojo);
    }
    public void setRootRid(Long codeRid, Class pojo , Long rootRid){
        LgAcntSeq.setRootRid(codeRid, pojo, rootRid);
    }
}
