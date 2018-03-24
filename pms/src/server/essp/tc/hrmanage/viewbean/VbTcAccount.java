package server.essp.tc.hrmanage.viewbean;

/**
 * 人力报表每个项目的汇总记录
 * type属性对应为项目的类型
 * jobCode对应项目的统计特性
 *
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Rong.Xiao
 * @version 1.0
 */
public class VbTcAccount extends VbTcLabor {
    private String manager;
    private String orgId ;
    public void setStatType(String stat){
        setJobCode(stat);
    }
    public String getStatType(){
        return getJobCode();
    }

    public int hashCode(){
        return getAccountId().hashCode();
    }

    public String getManager() {
        return manager;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }


}
