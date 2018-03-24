package server.essp.timesheet.account.labor.service;

import java.util.List;
import c2s.essp.timesheet.labor.DtoLaborResource;

/**
 * <p>Title: labor resource service interface</p>
 *
 * <p>Description: 与项目人力资源相关的业务逻辑服务</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public interface ILaborService {

    /**
     * 列出指定项目下的人力资源
     * @param AccountRid Long
     * @return List
     */
    public List listLabor(Long accountRid);

    /**
     * 把指定人力资源从项目中删除
     * @param labor DtoLaborResource
     */
    public void deleteLabor(DtoLaborResource labor);

    /**
     * 为某项目增加人力资源
     * @param labor DtoLaborResource
     */
    public void addLabor(DtoLaborResource labor);
    
    /**
     * 增加多个人员
     * @param loginIds
     * @param rid
     */
    public List addLabors(String loginIds, Long rid);

    /**
     * 保存人力资源信息
     * @param labor DtoLaborResource
     */
    public void saveLabor(DtoLaborResource labor);
    
    /**
     * 按专案代码查询人员
     * @param accountId
     * @return
     */
    public List getPersonInProject(String accountId);
}
