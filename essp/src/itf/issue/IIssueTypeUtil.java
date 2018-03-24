package itf.issue;

import java.util.List;
import java.util.Vector;

public interface IIssueTypeUtil {
    ////////////查找Issue类型的选项
    public List getIssueTypeOptions();
    public Vector getIssueTypeComboItems();

    ////////////查找Issue类型对应的Priority选项
    public List getPriorityOptions(String type);
    public Vector getPriorityComboItems(String type);

    /////////////查找Issue类型对应的Status选项
    public List getStatusOptions(String type);
    public Vector getStatusComboItem(String type);

    ////////////查找Issue类型对应的Scope选项
    public List getScopeOptions(String type, String userType);
    public Vector getScopeComboItems(String type, String userType);
}
