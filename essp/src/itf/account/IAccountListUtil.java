package itf.account;

import java.util.List;
import server.framework.common.BusinessException;
import server.framework.logic.IBusinessLogic;
import java.util.Vector;

/**
 * <p>Title: </p>
 *
 * <p>Description: 获取Account List</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public interface IAccountListUtil extends IBusinessLogic {

    /**
     * 项目状态的Display Name显示风格
     * 未设定的Default值为SHOWSTYLE_DASHED
     * @param style String
     */
    public void setShowStyle(String style);

    //// I：获取项目清单,不依据Login ID
    /**
     * 获取全部项目清单，包括：Normal & Closed
     * @return List
     * @throws BusinessException
     */
    public List listAllAccounts() throws BusinessException;

    /**
     * 获取项目清单的主要查询条件有以下：
     * A, 项目状态　STATUS( Normal/Closed)
     * B, 项目属性  ATTRIBUTE(1－客户；2－研发；3-部门)
     * C, 项目类型  TYPE(Project/Management/Sales/Administration/HR/Finance/SSE/Others)
     */

    /**
     * 项目状态的查询条件
     * 项目状态　STATUS( Normal/Closed)
     * 未设置时Default条件为Normal
     * @param status String[]
     */
    public void statusCondition(String[] status);


    /**
     * 项目类型的查询条件
     * 项目类型  TYPE(Project/Management/Sales/Administration/HR/Finance/SSE/Others)
     * 未设置时Default条件为全部项目类型
     * @param types String[]
     */
    public void typeCondition(String[] types);

    /**
     * 项目范围的查询条件
     * 项目范围  INNER(O/I/)
     * 未设置时Default条件为全部项目类型
     * @param types String[]
     */
    public void innerCondition(String[] inner);
    /**
     * 依据设定的查询条件获取项目清单，并且查询条件之间是采用取交集，如：
     * 设定了状态条件和属性条件后是两者的交集，即同时需要满足两者的条件才会筛选出。
     * 如果任何条件均未设置，即查询出正常状态的所有项目。
     * @return List
     * @throws BusinessException
     */
    public List listAccounts() throws BusinessException;

    /**
     * 查询方式同上，但查询的结果以Combo方式返回,即：
     * List {DtoComboItem(dispName, dto.getRid(), dto)},其中dispName格式依据show style来设定
     * @return Vector
     * @throws BusinessException
     */
    public Vector listComboAccounts() throws BusinessException;

    /**
     * 查询方式同上，但查询的结果以SelectOption方式返回,即：
     * List {SelectOptionImpl(dispName, sRid)},其中dispName格式依据show style来设定
     * @return List
     * @throws BusinessException
     */
    public List listOptAccounts() throws BusinessException;

    ///////////////////////////////////////////////////////////////////////////
    //// II：依据Login ID获取相应的项目清单
    /**
     * 依据Login ID获取项目清单的主要查询条件，在以上基础上补充以下类型：
     * D, 项目相关人类型及Login ID　　PERSON_TYPE,　现在有项目经理,業務經理,BD主管,工時表簽核者,業務代表等
     * E, 作为项目参与人员
     * F, 作为项目的Key Person
     */

    /**
     * 用户类型的查询条件，目前支持以下类型的用户进行查询，且所有类型是进行或运算：
     * 如果未作任何设定，即查询出作为项目经理及项目参与者相关的项目。
     * @param userTypes String[]
     */
    public void userTypeCondition(String[] userTypes);

    /**
     * 依据设定的查询条件及loginID所对应的用户类型获取项目清单
     * @param loginID String
     * @return List
     * @throws BusinessException
     */
    public List listAccounts(String loginID) throws BusinessException;

    /**
     * 查询方式同上，但查询的结果以Combo方式返回,即：
     * List {DtoComboItem(dispName, dto.getRid(), dto)},其中dispName格式依据show style来设定
     * @param loginID String
     * @return Vector
     * @throws BusinessException
     */
    public Vector listComboAccounts(String loginID) throws BusinessException;

    /**
     * 查询方式同上，但查询的结果以SelectOption方式返回,即：
     * List {SelectOptionImpl(dispName, sRid)},其中dispName格式依据show style来设定
     * @param loginID String
     * @return List
     * @throws BusinessException
     */
    public List listOptAccounts(String loginID) throws BusinessException;
}
