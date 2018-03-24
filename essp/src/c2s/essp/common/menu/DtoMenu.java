package c2s.essp.common.menu;

import java.util.*;

/**
 * <p>Title:</p>
 *
 * <p>Description: �û��ɿ������в˵�,��Ϊ�����㼶</p>
 *   ��һ��Ϊ��ϵͳ,ÿ����ϵͳ�°�������һ�����˵��������е��Ӳ˵���
 * <p>Copyright: Copyright (c) 2004</p>
 *
 * <p>Company: Enovation</p>
 *
 * @author not attributable
 * @version 1.0
 */
public class DtoMenu {
        /**
         * ϵͳ���ĸ��ڵ�ID
         */
        public static final String ROOT_FUNCTIONID = "M000000000";
        /**
         * ��¼��ǰϵͳ�û�(���������Լ�ID��¼��,Ҳ������ͨ��������Ȩ�������¼��)�Ķ�Ӧ�˵���
         */
        public static final String SESSION_MENU = "menu";
        /**
         * ��¼��¼ϵͳ���û�(���Լ�ID��¼��,������ͨ�������¼��)�Ķ�Ӧ�˵���
         */
        public final static String SESSION_LOGIN_MENU = "loginMenu";
        /**
         * ��¼��¼ϵͳ���û����пɴ�����Ա�Ĳ˵���,��Map����Session,KeyΪ�����˵�loginID,Value:DtoMenu����
         */
        public final static String SESSION_AGENT_MENUS = "agentMenus";
    /**
     * ������ϵͳ�˵�����Map��,Key:��ϵͳID,Value:��ϵͳ��ӦDtoSubSysMenu����
     */
    private List subSystem = new ArrayList();
    public List getSubSystem() {
        return subSystem;
    }
    public void setSubSystem(List subSys) {
        subSystem = subSys;
    }
    /**
     * �����ϵͳ�����еĲ˵���
     * @param systemId String
     * @return List
     */
    public List getSubSystemMenus(String systemId){
        if(subSystem == null || systemId == null)
            return null;
        for(int i = 0;i < subSystem.size() ;i ++){
            DtoSubSystem sub = (DtoSubSystem) subSystem.get(i);
            if(systemId.equals(sub.getRoot().getFunctionID())){
                return sub.getMenus();
            }
        }
        return null;
    }
}
