package server.essp.timesheet.code.codevalue.dao;

import java.util.List;
import db.essp.timesheet.TsCodeValue;

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
public interface ICodeValueDao {

    /**
     * 根据传入参数获取Leave Code列表, Seq升序
     * @return List
     */
    public List listLeaveCodeValue();

    /**
     * 获取Job Code根节点Bean
     * @param isLeavelType String
     * @return TsCodeValue
     */
    public TsCodeValue getRootJobCodeValue(String isLeavelType);

    /**
     * 根据父节点Rid获取所有子节点, Seq升序
     * @param parentRid Long
     * @param isLeaveType String
     * @return List
     */
    public List listJobCodeValueByParentRid(Long parentRid, String isLeaveType);

    /**
     * 根据Rid获取CodeValue记录
     * @param rid Long
     * @return TsCodeValue
     */
    public TsCodeValue getCodeValue(Long rid);


    /**
     * 新增一条假别的记录
     * @param tsJobCode TsCodeValue
     */
    public void add(TsCodeValue tsCodeValue);

    /**
     * 得到JobCode最大的SEQ
     * @param parentRid Long
     * @param isLeaveType String
     * @return Long
     */
    public Long getJobCodeMaxSeq(Long parentRid, String isLeaveType);

    /**
     * 得到Leave Code最大的SEQ
     * @return Long
     */
    public Long getLeaveCodeMaxSeq();


    /**
     * 更新一条假别的记录
     * @param jobCode TsCodeValue
     */
    public void update(TsCodeValue tsCodeValue);

    /**
     * 更新isEnable属性
     * @param rid Long
     * @param isEnable Boolean
     */
    public void updateEnable(Long rid, Boolean isEnable);

    /**
     * 删除一条假别记录
     * @param codeValue TsCodeValue
     */
    public void delete(TsCodeValue tsCodeValue);

    /**
     * 交换两条记录中的SEQ
     * @param curJobCode TsCodeValue
     * @param jobCode TsCodeValue
     */
    public void setSeq(Long codeValueRid, Long seq);

    /**
     * 获取上一个LeaveCodeValue
     * @param seq Long
     * @return TsCodeValue
     */
    public TsCodeValue getUpSeqLeaveCodeValue(Long seq);

    /**
     * 获取上一个JobCodeValue
     * @param parentRid Long
     * @param seq Long
     * @param isLeaveType String
     * @return TsCodeValue
     */
    public TsCodeValue getUpSeqJobCodeValue(Long parentRid, Long seq, String isLeaveType);

    /**
     * 获取下一个LeaveCodeValue
     * @param seq Long
     * @return TsCodeValue
     */
    public TsCodeValue getDownSeqLeaveCodeValue(Long seq);

    /**
     * 获取下一个JobCodeValue
     * @param parentRid Long
     * @param seq Long
     * @param isLeaveType String
     * @return TsCodeValue
     */
    public TsCodeValue getDownSeqJobCodeValue(Long parentRid, Long seq, String isLeaveType);

    /**
     * 列出所有的有效假别记录
     * @return TsCodeValue
     */
    public List getLeaveCode();
    
    /**
     * 根据输入的工作代码找到是否存在此工作代码
     * @param workCode
     * @return
     */
    public TsCodeValue isExistWorkCode(String workCode);
    
    /**
     * 列出在某个父节点下某个seq之后的所有子节点
     * @param parentRid Long
     * @param seq Long
     * @return
     */
    public List<TsCodeValue> getChildrenAfterSeq(Long parentRid, Long seq);
    
    /**
     * 根据typeRid和name查找
     * @param typeRid Long
     * @param name String
     * @return TsCodeValue
     */
    public TsCodeValue findByTypeRidName( Long typeRid,String name);
    
    public TsCodeValue findCodeValue(Long rid);

}
