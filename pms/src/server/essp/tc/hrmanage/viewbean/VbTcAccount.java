package server.essp.tc.hrmanage.viewbean;

/**
 * ��������ÿ����Ŀ�Ļ��ܼ�¼
 * type���Զ�ӦΪ��Ŀ������
 * jobCode��Ӧ��Ŀ��ͳ������
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
