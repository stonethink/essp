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
     * ���ݴ��������ȡLeave Code�б�, Seq����
     * @return List
     */
    public List listLeaveCodeValue();

    /**
     * ��ȡJob Code���ڵ�Bean
     * @param isLeavelType String
     * @return TsCodeValue
     */
    public TsCodeValue getRootJobCodeValue(String isLeavelType);

    /**
     * ���ݸ��ڵ�Rid��ȡ�����ӽڵ�, Seq����
     * @param parentRid Long
     * @param isLeaveType String
     * @return List
     */
    public List listJobCodeValueByParentRid(Long parentRid, String isLeaveType);

    /**
     * ����Rid��ȡCodeValue��¼
     * @param rid Long
     * @return TsCodeValue
     */
    public TsCodeValue getCodeValue(Long rid);


    /**
     * ����һ���ٱ�ļ�¼
     * @param tsJobCode TsCodeValue
     */
    public void add(TsCodeValue tsCodeValue);

    /**
     * �õ�JobCode����SEQ
     * @param parentRid Long
     * @param isLeaveType String
     * @return Long
     */
    public Long getJobCodeMaxSeq(Long parentRid, String isLeaveType);

    /**
     * �õ�Leave Code����SEQ
     * @return Long
     */
    public Long getLeaveCodeMaxSeq();


    /**
     * ����һ���ٱ�ļ�¼
     * @param jobCode TsCodeValue
     */
    public void update(TsCodeValue tsCodeValue);

    /**
     * ����isEnable����
     * @param rid Long
     * @param isEnable Boolean
     */
    public void updateEnable(Long rid, Boolean isEnable);

    /**
     * ɾ��һ���ٱ��¼
     * @param codeValue TsCodeValue
     */
    public void delete(TsCodeValue tsCodeValue);

    /**
     * ����������¼�е�SEQ
     * @param curJobCode TsCodeValue
     * @param jobCode TsCodeValue
     */
    public void setSeq(Long codeValueRid, Long seq);

    /**
     * ��ȡ��һ��LeaveCodeValue
     * @param seq Long
     * @return TsCodeValue
     */
    public TsCodeValue getUpSeqLeaveCodeValue(Long seq);

    /**
     * ��ȡ��һ��JobCodeValue
     * @param parentRid Long
     * @param seq Long
     * @param isLeaveType String
     * @return TsCodeValue
     */
    public TsCodeValue getUpSeqJobCodeValue(Long parentRid, Long seq, String isLeaveType);

    /**
     * ��ȡ��һ��LeaveCodeValue
     * @param seq Long
     * @return TsCodeValue
     */
    public TsCodeValue getDownSeqLeaveCodeValue(Long seq);

    /**
     * ��ȡ��һ��JobCodeValue
     * @param parentRid Long
     * @param seq Long
     * @param isLeaveType String
     * @return TsCodeValue
     */
    public TsCodeValue getDownSeqJobCodeValue(Long parentRid, Long seq, String isLeaveType);

    /**
     * �г����е���Ч�ٱ��¼
     * @return TsCodeValue
     */
    public List getLeaveCode();
    
    /**
     * ��������Ĺ��������ҵ��Ƿ���ڴ˹�������
     * @param workCode
     * @return
     */
    public TsCodeValue isExistWorkCode(String workCode);
    
    /**
     * �г���ĳ�����ڵ���ĳ��seq֮��������ӽڵ�
     * @param parentRid Long
     * @param seq Long
     * @return
     */
    public List<TsCodeValue> getChildrenAfterSeq(Long parentRid, Long seq);
    
    /**
     * ����typeRid��name����
     * @param typeRid Long
     * @param name String
     * @return TsCodeValue
     */
    public TsCodeValue findByTypeRidName( Long typeRid,String name);
    
    public TsCodeValue findCodeValue(Long rid);

}
