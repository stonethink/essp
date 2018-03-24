package server.essp.timesheet.code.codetype.dao;

import java.util.List;
import db.essp.timesheet.TsCodeType;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author not attributable
 * @version 1.0
 */
public interface ICodeTypeDao {

    /**
     * ���ݼٱ����͵õ�CodeType��¼
     * @param isLeaveType String
     * @return List
     */
    public List listCodeType(String isLeaveType);

    /**
     * ����һ����¼
     * @param codeType TsCodeType
     */
    public void add(TsCodeType codeType);

    /**
     * ����һ����¼
     * @param codeType TsCodeType
     */
    public void update(TsCodeType codeType);

    /**
     * ��ȡ����Seq
     * @return Long
     */
    public Long getMaxSeq();

    /**
     * ����seq
     * @param rid Long
     * @param seq Long
     */
    public void setSeq(Long rid, Long seq);

    /**
     * ɾ��һ��CodeType��¼
     * @param rid Long
     */
    public void delete(TsCodeType codeType);

    /**
     * ��ѯ��һ��seq��CodeType����
     * @param seq Long
     * @return TsCodeType
     */
    public TsCodeType getUpSeqCodeType(Long seq);

    /**
     * ��ѯ��һ��seq��CodeType����
     * @param seq Long
     * @return TsCodeType
     */
    public TsCodeType getDownSeqCodeType(Long seq);



    /**
     * ����RID�õ���Ӧ��CodeType
     * @param rid Long
     * @return TsCodeType
     */
    public TsCodeType getCodeType(Long rid);

}
