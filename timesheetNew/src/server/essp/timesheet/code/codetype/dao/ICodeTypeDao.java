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
     * 根据假别类型得到CodeType记录
     * @param isLeaveType String
     * @return List
     */
    public List listCodeType(String isLeaveType);

    /**
     * 新增一条记录
     * @param codeType TsCodeType
     */
    public void add(TsCodeType codeType);

    /**
     * 更新一条记录
     * @param codeType TsCodeType
     */
    public void update(TsCodeType codeType);

    /**
     * 获取最大的Seq
     * @return Long
     */
    public Long getMaxSeq();

    /**
     * 设置seq
     * @param rid Long
     * @param seq Long
     */
    public void setSeq(Long rid, Long seq);

    /**
     * 删除一条CodeType记录
     * @param rid Long
     */
    public void delete(TsCodeType codeType);

    /**
     * 查询上一个seq的CodeType资料
     * @param seq Long
     * @return TsCodeType
     */
    public TsCodeType getUpSeqCodeType(Long seq);

    /**
     * 查询下一个seq的CodeType资料
     * @param seq Long
     * @return TsCodeType
     */
    public TsCodeType getDownSeqCodeType(Long seq);



    /**
     * 根据RID得到对应的CodeType
     * @param rid Long
     * @return TsCodeType
     */
    public TsCodeType getCodeType(Long rid);

}
