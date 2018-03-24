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
     * ��ȡ������ut rate��������ݣ���֯���ͽṹ��
     * @return DtoTreeNode
     */
    public DtoTreeNode getUtRateTree(boolean includeSubOrg) {
        loadData();
        DtoTreeNode root = getOrgTree();

        //�����������֤���ڵ��������ʱ�����ӽڵ������Ѿ��������
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
     * ���ظ����ŵ�ֱ�ӹ�ʱ����ӹ�ʱ�����빤ʱ��
     */
    private void loadData() {
        directMap = getDirectWorkHours();
        indirectMap = getIndirectWorkHours();
        incomeMap = getIncomeWorkHours();
    }

    /**
     * ����ָ�����ŵ�ֱ�ӹ�ʱ����ӹ�ʱ�����빤ʱ��
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
     * �ۼ��Ӳ��ŵ�ֱ�ӹ�ʱ����ӹ�ʱ�����빤ʱ�������š�
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
     * ��ȡ��֯�ṹ��
     *   1. �Ե�ǰSiteΪ���ڵ�
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
     * �ݹ鹹�첿����
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
     * ��ȡ��ӹ�ʱ��
     *  1.���޲�����Ա��������������Ŀ�µ����й�ʱ��
     *  2. �����Ż��ܹ�ʱ��key:���Ŵ���, value: ��ʱ��
     * @return Map
     */
    private Map getIndirectWorkHours() {
        return getWorkHours(false, null);
    }

    /**
     * ��ȡֱ�ӹ�ʱ��
     *   1.���в�����Ա��������������Ŀ�µ����й�ʱ��
     *   2. �����Ż��ܹ�ʱ��key:���Ŵ���, value: ��ʱ��
     * @return Map
     */
    private Map getDirectWorkHours() {
        return getWorkHours(true, null);
    }

    /**
     * ��ȡ���빤ʱ��
     *  1.���в�����Ա���ڡ�Project��������Ŀ�µĹ�ʱ��
     *  2. �����Ż��ܹ�ʱ��key:���Ŵ���, value: ��ʱ��
     * @return Map
     */
    private Map getIncomeWorkHours() {
        return getWorkHours(true, true);
    }

    /**
     * ��ȡ���ŷ�����ܹ�ʱ��
     * @param isProductiviy boolean ��Ա�Ƿ��в���
     * @param isProjectType boolean ��Ŀ�Ƿ�ΪProject����
     * @return Map �����Ż��ܹ�ʱ��key:���Ŵ���, value: ��ʱ��
     */
    private Map getWorkHours(Boolean isProductiviy, Boolean isProjectType) {
        StringBuffer buffSql = new StringBuffer(WORK_HOURS_SQL_HEAD);

        if(isProductiviy != null) {
            String productivity = isProductiviy ? "1" : "0";
            buffSql.append("and h.productivity = '" + productivity + "' ");
        }

        //ֻ����Full Time����Ա��
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
     * /��ȡSite ��߲���
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
