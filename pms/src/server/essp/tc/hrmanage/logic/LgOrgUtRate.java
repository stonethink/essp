package server.essp.tc.hrmanage.logic;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.RowSet;

import c2s.dto.DtoTreeNode;
import c2s.essp.tc.hrmanage.DtoOrgUtRate;
import c2s.essp.tc.weeklyreport.DtoWeeklyReport;
import com.wits.util.Config;
import com.wits.util.comDate;
import itf.orgnization.IOrgnizationUtil;
import itf.orgnization.OrgnizationFactory;
import server.essp.framework.logic.AbstractESSPLogic;
import server.framework.common.BusinessException;
import c2s.essp.common.calendar.WrokCalendarFactory;
import c2s.essp.common.calendar.WorkCalendar;
import java.util.Calendar;
import java.math.BigDecimal;

public class LgOrgUtRate extends AbstractESSPLogic {


    private Long hrAcntRid;
    private Date beginDate;
    private Date endDate;
    private Map directMap;
    private Map indirectMap;
    private Map incomeMap;
    private float monthWorkHours;

    public LgOrgUtRate(Long hrAcntRid, Date begin, Date end) {
        super();
        this.hrAcntRid = hrAcntRid;
        this.beginDate = begin;
        this.endDate = end;
        WorkCalendar wc = WrokCalendarFactory.serverCreate();
        Calendar cBegin = Calendar.getInstance();
        cBegin.setTime(beginDate);
        Calendar[] month = wc.getBEMonthDay(cBegin);
        monthWorkHours = wc.getWorkHours(month[0], month[1]);
    }

    /**
     * 获取各部门ut rate及相关数据，组织树型结构。
     * @return DtoTreeNode
     */
    public DtoTreeNode getUtRateTree(boolean includeSubOrg) {
        loadData();
        DtoTreeNode root = getOrgTree();

        //后序遍历，保证父节点加载数据时，其子节点数据已经加载完毕
        List nodeList = root.listAllChildrenByPostOrder();
        nodeList.add(root);
        for(int i = 0; i < nodeList.size(); i++) {
            DtoTreeNode node = (DtoTreeNode) nodeList.get(i);
            DtoOrgUtRate dto = (DtoOrgUtRate) node.getDataBean();
            fillDtoData(dto);
            if(includeSubOrg) {
                sumChildrenData(dto, node.children());
            }
        }
        return root;
    }

    /**
     * 加载各部门的直接工时，间接工时，收入工时。
     */
    private void loadData() {
        directMap = getDirectWorkHours();
        indirectMap = getIndirectWorkHours();
        incomeMap = getIncomeWorkHours();
    }

    /**
     * 填入指定部门的直接工时，间接工时，收入工时。
     * @param dto DtoOrgUtRate
     */
    private void fillDtoData(DtoOrgUtRate dto) {
        String unitCode = dto.getUnitCode();

        Double directHours = (Double) directMap.get(unitCode);
        Double indirectHours = (Double) indirectMap.get(unitCode);
        Double incomeHours = (Double) incomeMap.get(unitCode);

        dto.setDirectWH(directHours);
        dto.setIndirectWH(indirectHours);
        dto.setIncomeWH(incomeHours);

        dto.setMonthWorkHours(new BigDecimal(monthWorkHours));
    }

    /**
     * 累加子部门的直接工时，间接工时，收入工时到父部门。
     * @param parent DtoOrgUtRate
     * @param children List
     */
    private void sumChildrenData(DtoOrgUtRate parent, List children) {
        if(children == null) return;

        for (int i = 0; i < children.size(); i++) {
            DtoTreeNode node = (DtoTreeNode) children.get(i);
            DtoOrgUtRate child = (DtoOrgUtRate) node.getDataBean();

            parent.setDirectWH(parent.getDirectWH() + child.getDirectWH());
            parent.setIndirectWH(parent.getIndirectWH() + child.getIndirectWH());
            parent.setIncomeWH(parent.getIncomeWH() + child.getIncomeWH());
        }
    }

    /**
     * 获取组织结构树
     *   1. 以当前Site为根节点
     * @return ITreeNode
     */
    private DtoTreeNode getOrgTree() {
        IOrgnizationUtil orgUtil = OrgnizationFactory.create();
        String rootId = getSiteOrgId();
        String rootCode = orgUtil.getOrgCode(rootId);
        String rootName = orgUtil.getOrgName(rootId);

        DtoOrgUtRate dtoRoot = new DtoOrgUtRate();
        dtoRoot.setUnitId(rootId);
        dtoRoot.setUnitCode(rootCode);
        dtoRoot.setUnitName(rootName);

        DtoTreeNode rootNode = new DtoTreeNode(dtoRoot);
        loadChildren(rootNode, rootId);

        return rootNode;
    }

    /**
     * 递归构造部门树
     * @param rootNode DtoTreeNode
     * @param parentId String
     */
    private void loadChildren(DtoTreeNode rootNode, String parentId) {
        String sql = SUB_ORGS_SQL_HEAD;
        if(parentId != null && !"".equals(parentId)) {
            sql += "where t.parent_id = '" + parentId + "'";
        }
        sql += SUB_ORGS_SQL_ORDER_BY;
        List children = getDbAccessor().executeQueryToList(sql, DtoOrgUtRate.class);
        for(int i = 0; i < children.size(); i++) {
            DtoOrgUtRate child = (DtoOrgUtRate) children.get(i);
            DtoTreeNode node = new DtoTreeNode(child);
            loadChildren(node, child.getUnitId());
            rootNode.addChild(node);
        }
    }

    /**
     * 获取间接工时数
     *  1.“无产能人员”在所有类型项目下的所有工时。
     *  2. 按部门汇总工时，key:部门代码, value: 工时数
     * @return Map
     */
    private Map getIndirectWorkHours() {
        return getWorkHours(false, null);
    }

    /**
     * 获取直接工时数
     *   1.“有产能人员”在所有类型项目下的所有工时。
     *   2. 按部门汇总工时，key:部门代码, value: 工时数
     * @return Map
     */
    private Map getDirectWorkHours() {
        return getWorkHours(true, null);
    }

    /**
     * 获取收入工时数
     *  1.“有产能人员”在“Project”类型项目下的工时。
     *  2. 按部门汇总工时，key:部门代码, value: 工时数
     * @return Map
     */
    private Map getIncomeWorkHours() {
        return getWorkHours(true, true);
    }

    /**
     * 获取部门分组汇总工时数
     * @param isProductiviy boolean 人员是否有产能
     * @param isProjectType boolean 项目是否为Project类型
     * @return Map 按部门汇总工时，key:部门代码, value: 工时数
     */
    private Map getWorkHours(Boolean isProductiviy, Boolean isProjectType) {
        StringBuffer buffSql = new StringBuffer(WORK_HOURS_SQL_HEAD);

        if(isProductiviy != null) {
            String productivity = isProductiviy ? "1" : "0";
            buffSql.append("and h.productivity = '" + productivity + "' ");
        }

        //只导出Full Time性质员工
        buffSql.append("and h.types = '0' ");

        if(isProjectType != null) {
            String isEquals = isProjectType ? "=" : "!=";
            buffSql.append("and a.acnt_type " + isEquals + " '" + PROJECT_TYPE + "' ");
        }

        if(hrAcntRid != null) {
            buffSql.append("and s.account_id = " + hrAcntRid + " ");
        }

        if(beginDate != null) {
            String beginSql = "and to_char(t.end_period,'" + comDate.pattenDate + "')"
                              + " >= '" + comDate.dateToString(beginDate) + "' ";
            buffSql.append(beginSql);
        }

        if(endDate != null) {
            String endSql = "and to_char(t.begin_period,'" + comDate.pattenDate + "')"
                              + " <= '" + comDate.dateToString(endDate) + "' ";
            buffSql.append(endSql);
        }

        buffSql.append(WORK_HOURS_SQL_GROUP_BY);
        buffSql.append(WORK_HOURS_SQL_ORDER_BY);

        Map map = new HashMap();
        try {
            RowSet rs = getDbAccessor().executeQuery(buffSql.toString());
            while (rs.next()) {
                map.put(rs.getString("code"), rs.getDouble("value"));
            }
        } catch (SQLException ex) {
            throw new BusinessException("LgOrgUtRate", "get work hours error", ex);
        }
        return map;
    }

    /**
     * /获取Site 最高部门
     * @return String
     */
    public String getSiteOrgId() {
        Config viewAllDeptConf = new Config("viewAllDeptConfig");
        return viewAllDeptConf.getValue("site_Code");
    }

    final static String PROJECT_TYPE = "Project";

    private final static String SUB_ORGS_SQL_HEAD =
        "select t.unit_id, t.unit_code,t.name as unit_name, t.parent_id "
        + "from essp_upms_unit t ";
    private final static String SUB_ORGS_SQL_ORDER_BY =
        " order by t.unit_code";

    private final static String WORK_HOURS_SQL_HEAD =
        "select u.unit_code as code,sum(t.sat_hour + t.sun_hour + t.mon_hour + t.tue_hour + t.wed_hour + t.thu_hour + t.fri_hour) as value "
        + "from tc_weekly_report_sum t  "
        + "left join pms_acnt a on t.acnt_rid = a.rid "
        + "left join essp_upms_unit u on a.acnt_organization = u.unit_id "
        + "left join (essp_hr_employee_main_t h  "
        + "left join upms_loginuser l on h.code = l.user_id "
        + "left join essp_hr_account_scope_t s on h.code = s.scope_code ) on t.user_id = l.login_id "
        + "where t.confirm_status = '" + DtoWeeklyReport.STATUS_CONFIRMED + "' ";
    private final static String WORK_HOURS_SQL_GROUP_BY =  "group by u.unit_code ";
    private final static String WORK_HOURS_SQL_ORDER_BY =  "order by u.unit_code desc ";


}
