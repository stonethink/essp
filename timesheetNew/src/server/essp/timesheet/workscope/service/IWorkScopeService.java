package server.essp.timesheet.workscope.service;

import java.util.List;
/**
 * <p>Title: IWorkScopeService类</p>
 *
 * <p>Description:从P3和ESSP中获得符合条件的数据 </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author tbh
 * @version 1.0
 */
public interface IWorkScopeService {

    /**
     * 得到ESSP和P3中的项目集合的差,如果得到的P3中的项目在TS_ACCOUNT中存在取出来,否则跳过
     * @param loginId String
     * @return List
     */
    public List getAccountList(String loginId);

    /**
     * 列出为的假别List
     * @param leaveCodeTypeRid Long
     * @return List
     */
    public List getCodeLeave(Long leaveCodeTypeRid);

    /**
     * 得到符合条件的CODEVALUE记录
     * 1.根据项目代号到TSACCOUNT中得到相应的CodeTypeRid,根据CodeTypeRid到CodeRelation中查询符合条件的记录
     * 2.如果存在且得到的CODEVALUE结点是叶子则根据codeTypeRid到CODEVALUE中得到相应的记录，
     * 3.否则跳过不再到CODETYPE中查询
     * @param accountId String
     * @return List
     */
    public List getJobCode(Long codeTypeRid);
    
    /**
     * 根据ActivityId获取ActivityShowName,如果没找到则返回空字符串
     * @param activityId Long
     * @return String
     */
    public String getActivityShowName(Long activityId);
    
    /**
     * 根据acntRid获取AccountShowName,如果没找到则返回空字符串
     * @param acntRid Long
     * @return String
     */
    public String getAccountShowName(Long acntRid);
}
