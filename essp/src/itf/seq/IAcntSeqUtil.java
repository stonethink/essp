package itf.seq;

public interface IAcntSeqUtil {
    Long getRootRid(Long codeRid, Class pojo);
    void setRootRid(Long codeRid, Class pojo , Long rootRid);
}
