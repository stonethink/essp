package itf.issue;

import java.util.List;
import java.util.Vector;

public interface IIssueTypeUtil {
    ////////////����Issue���͵�ѡ��
    public List getIssueTypeOptions();
    public Vector getIssueTypeComboItems();

    ////////////����Issue���Ͷ�Ӧ��Priorityѡ��
    public List getPriorityOptions(String type);
    public Vector getPriorityComboItems(String type);

    /////////////����Issue���Ͷ�Ӧ��Statusѡ��
    public List getStatusOptions(String type);
    public Vector getStatusComboItem(String type);

    ////////////����Issue���Ͷ�Ӧ��Scopeѡ��
    public List getScopeOptions(String type, String userType);
    public Vector getScopeComboItems(String type, String userType);
}
